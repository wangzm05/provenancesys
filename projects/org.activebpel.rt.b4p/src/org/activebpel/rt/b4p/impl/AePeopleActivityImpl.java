//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/AePeopleActivityImpl.java,v 1.24 2008/03/28 01:52:15 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.IAeProcessTaskConstants;
import org.activebpel.rt.b4p.def.AeAttachmentPropagationDef;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.b4p.impl.engine.AeNotification;
import org.activebpel.rt.b4p.impl.engine.AeTask;
import org.activebpel.rt.b4p.impl.engine.IAeB4PManager;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.IAeInvokeActivityAdapter;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef;
import org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef;
import org.activebpel.rt.bpel.impl.AeBPELMessageDataValidator;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.AeBpelState;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.IAeMessageValidator;
import org.activebpel.rt.bpel.impl.activity.AeAbstractExtensionActivityImpl;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext;
import org.activebpel.rt.bpel.impl.activity.IAeImplAdapter;
import org.activebpel.rt.bpel.impl.activity.IAeMessageConsumerParentAdapter;
import org.activebpel.rt.bpel.impl.activity.IAeMessageProducerParentAdapter;
import org.activebpel.rt.bpel.impl.activity.IAeMessageValidationAdapter;
import org.activebpel.rt.bpel.impl.activity.support.AeFault;
import org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumer;
import org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducer;
import org.activebpel.rt.message.AeMessageData;
import org.activebpel.rt.message.AeMessagePartTypeInfo;
import org.activebpel.rt.message.AeMessagePartsMap;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Adapter to handle behavior for people activity invocation
 */
public class AePeopleActivityImpl extends AeAbstractExtensionActivityImpl 
implements IAeMessageConsumerParentAdapter, IAeMessageProducerParentAdapter, 
   IAeMessageValidationAdapter, IAeInvokeActivityAdapter
{
   /** people activity def */
   private AePeopleActivityDef mPeopleActivityDef;
   /** handles consuming the message */
   private IAeMessageDataConsumer mConsumer;
   /** handles producing a message */
   private IAeMessageDataProducer mProducer;
   /** message validator */
   private IAeMessageValidator mMessageValidator;
   /** people activity state impl */
   private AePeopleActivityStateImpl mState;
   
   /**
    * Ctor accepts def
    * @param aDef
    */
   public AePeopleActivityImpl(AePeopleActivityDef aDef)
   {
      setDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.AeAbstractExtensionActivityImpl#onStateChange(org.activebpel.rt.bpel.impl.AeBpelState, org.activebpel.rt.bpel.impl.AeBpelState)
    */
   public void onStateChange(AeBpelState aOldState, AeBpelState aNewState)
   {
      if (aNewState == AeBpelState.INACTIVE)
         setState(null);
      super.onStateChange(aOldState, aNewState);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleAdapter#execute(org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext)
    */
   public void execute(IAeActivityLifeCycleContext aContext) throws AeBusinessProcessException
   {
      long processId = aContext.getProcess().getProcessId();
      int instanceLocationId = aContext.getBpelObject().getLocationId();
      String locationPath = aContext.getBpelObject().getLocationPath();
      AePeopleActivityDef paDef = getDef();
      IAeMessageData data = aContext.produceMessage(this);

      // Validate the data that was produced.
      getMessageValidator().validateOutbound(aContext.getProcess(), getMessageDataProducerDef(), data, null);

      // Get the B4P manager and either call executeTask() or executeNotification()
      IAeBusinessProcessEngineInternal engine = aContext.getProcess().getEngine();
      IAeB4PManager b4pManager = getManager(engine);

      IAeAttachmentContainer attachments = getAttachments(aContext);
      if (getDef().isTask())
      {
         AeTask task = new AeTask(processId, instanceLocationId, locationPath, data, paDef);
         b4pManager.executeTask(task, attachments, aContext);
      }
      else
      {
         AeNotification notification = new AeNotification(processId, instanceLocationId, locationPath, data, paDef);
         b4pManager.executeNotification(notification, attachments, aContext);
      }
   }
   
   /**
    * Gets the B4P manager or faults
    * @param aEngine
    * @throws AeBusinessProcessException
    */
   protected IAeB4PManager getManager(IAeBusinessProcessEngineInternal aEngine)
         throws AeBusinessProcessException
   {
      IAeB4PManager b4pManager = (IAeB4PManager) aEngine.getCustomManager(IAeProcessTaskConstants.B4P_MANAGER_KEY);
      if (b4pManager == null)
         throw new AeBusinessProcessException(AeMessages.getString("AePeopleActivityImpl.ErrorExecutingPeopleActivity")); //$NON-NLS-1$
      return b4pManager;
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.AeAbstractExtensionActivityImpl#terminate(org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext)
    */
   public void terminate(IAeActivityLifeCycleContext aContext)
         throws AeBusinessProcessException
   {
      IAeB4PManager b4pManager = getManager(aContext.getProcess().getEngine());
      long pid = aContext.getProcess().getProcessId();
      int peopleId = aContext.getBpelObject().getLocationId();
      b4pManager.cancelTask(pid, peopleId, aContext.getTransmission().getTransmissionId());
      super.terminate(aContext); 
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.AeAbstractExtensionActivityImpl#onFault(org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext, org.activebpel.rt.bpel.IAeFault)
    */
   public void onFault(IAeActivityLifeCycleContext aContext, IAeFault aFault)
         throws AeBusinessProcessException
   {
      taskResponseReceived(aContext);
      // change the fault up to be the one defined in the spec
      AeFault fault = new AeFault(new QName(IAeB4PConstants.B4P_NAMESPACE, "nonRecoverableError"), (Element)null); //$NON-NLS-1$
      super.onFault(aContext, fault);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleAdapter#onMessage(org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext, org.activebpel.rt.message.IAeMessageData)
    */
   public void onMessage(IAeActivityLifeCycleContext aContext, IAeMessageData aMessageData) throws AeBusinessProcessException
   {
      taskResponseReceived(aContext);
      
      if (getDef().isNotification())
      {
         aContext.complete();
         return;
      }
      
      Document doc = (Document) aMessageData.getData(IAeProcessTaskConstants.TASK_RESPONSE_PART_NAME);
      Element status = (Element) AeXPathUtil.selectSingleNodeIgnoreException(doc.getDocumentElement(), "tlc:metadata/trt:status", AePeopleActivityStateImpl.sNSMap); //$NON-NLS-1$
      if (IAeProcessTaskConstants.SUCCESS_RESPONSE.equals(AeXmlUtil.getText(status)))
      {
         consumeResponse(aContext, doc, aMessageData.getAttachmentContainer());
         aContext.complete();
      }
      else if (IAeProcessTaskConstants.FAULT_RESPONSE.equals(AeXmlUtil.getText(status)))
      {
         // consume the task state
         getState().setStateFromResponse(doc.getDocumentElement());
         IAeFault fault = parseFaultData(doc.getDocumentElement());
         aContext.completedWithFault(fault);
      }
      else if (IAeProcessTaskConstants.SKIPPED_RESPONSE.equals(AeXmlUtil.getText(status)))
      {
         aContext.complete();
      }
      else if (IAeProcessTaskConstants.TERMINATED_RESPONSE.equals(AeXmlUtil.getText(status)))
      {
         QName faultName = IAeB4PConstants.FAULT_NAME_TASK_EXPIRED;
         IAeFault fault = new AeFault(faultName, AeXmlUtil.newDocument().getDocumentElement());
         aContext.completedWithFault(fault);
      }
   }

   /**
    * Notifies the manager that we've received our response for the task.
    * @param aContext
    * @throws AeBusinessProcessException
    */
   protected void taskResponseReceived(IAeActivityLifeCycleContext aContext)
         throws AeBusinessProcessException
   {
      IAeB4PManager b4pManager = getManager(aContext.getProcess().getEngine());
      long pid = aContext.getProcess().getProcessId();
      int locationId = aContext.getBpelObject().getLocationId();
      long transmissionId = aContext.getTransmission().getTransmissionId();
      b4pManager.taskResponseReceived(pid, locationId, transmissionId);
   }

   /**
    * Consume the output message data, attachments, and task metadata
    * @param aContext
    * @param aDocument
    * @param aAttachments
    * @throws AeBusinessProcessException
    * @throws AeBpelException
    */
   protected void consumeResponse(IAeActivityLifeCycleContext aContext,
         Document aDocument, IAeAttachmentContainer aAttachments) throws AeBusinessProcessException, AeBpelException
   {
      // FIXMEPP should have a deserializer for this document that produces a Java API so we don't need all of these DOM methods
      
      // validate the message
      IAeMessageData outputMessageData = parseMessageData(aContext, aDocument.getDocumentElement());
      getMessageValidator().validateInbound(aContext.getProcess(), getMessageDataConsumerDef(), outputMessageData, null);
      
      // consume the output message
      aContext.consumeMessage(this, outputMessageData);
      
      // consume the task state
      getState().setStateFromResponse(aDocument.getDocumentElement());
      
      // consume the attachments
      //Element attachments = (Element) AeXPathUtil.selectSingleNodeIgnoreException(aDocument.getDocumentElement(), "trt:attachments", AePeopleActivityStateImpl.sNSMap); //$NON-NLS-1$
      consumeAttachments(aContext, aAttachments);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleAdapter#getImplAdapter(java.lang.Class)
    */
   public IAeImplAdapter getImplAdapter(Class aClass)
   {
      if (aClass.isAssignableFrom(getClass()))
         return this;
      else if (IAeMessageValidator.class == aClass)
         return new AeBPELMessageDataValidator(false);

      return null;
   }

   /**
    * Build IAeMessageData from task response
    * @param aContext
    * @param aTaskResponse
    * @throws AeBusinessProcessException
    */
   private IAeMessageData parseMessageData(IAeActivityLifeCycleContext aContext, Element aTaskResponse) throws AeBusinessProcessException
   {
      Map partData = new HashMap();
      AeMessagePartsMap partsMap = getMessageDataConsumerDef().getConsumerMessagePartsMap();
      QName msgType = partsMap.getMessageType();
      for(Iterator iter=partsMap.getPartNames(); iter.hasNext(); )
      {
         String partName = (String) iter.next();
         String query = MessageFormat.format("trt:output/trt:part[@name=''{0}'']", new Object[] {partName}); //$NON-NLS-1$
         Element resp = (Element) AeXPathUtil.selectSingleNodeIgnoreException(aTaskResponse, query, AePeopleActivityStateImpl.sNSMap);
         if (resp != null)
         {
            AeMessagePartTypeInfo typeInfo = partsMap.getPartInfo(partName);
            boolean isSimpleType = typeInfo.getElementName() == null && typeInfo.getXMLType().isSimpleType();
            if (isSimpleType)
            {
               AeTypeMapping typeMapping = aContext.getProcess().getEngine().getTypeMapping();
   
               Object data = typeMapping.deserialize(typeInfo.getTypeName(), AeXmlUtil.getText(resp));
               partData.put(partName, data);
            }
            else
            {
               // clone the element before putting into the map
               Element partElement = AeXmlUtil.getFirstSubElement(resp);
               if (partElement != null)
               {
                  Document doc = AeXmlUtil.cloneElement(partElement).getOwnerDocument();
                  partData.put(partName, doc);
               }
            }
         }
      }
      AeMessageData messageData = new AeMessageData(msgType, partData);
      return messageData;
   }

   /**
    * constructs and returns IAeFault from the message
    * @param aFaultResponse
    */
   private IAeFault parseFaultData(Element aFaultResponse)
   {
      // fixme (P) (should be constructing IAeMessageData instead of Element)
      Element faultElem = (Element) AeXPathUtil.selectSingleNodeIgnoreException(aFaultResponse, "trt:fault", AePeopleActivityStateImpl.sNSMap); //$NON-NLS-1$
      String localPart = faultElem.getAttribute("name"); //$NON-NLS-1$
      String namespace = getDef().getConsumerPortType().getNamespaceURI();
      QName faultName = new QName(namespace, localPart);
      AeFault faultData = new AeFault(faultName, AeXmlUtil.getFirstSubElement(faultElem));
      return faultData;
   }

   /**
    * Consume attachments, i.e append them to the attachments variable
    * @param aContext
    * @param aAttachments
    * @throws AeBusinessProcessException
    */
   private void consumeAttachments(IAeActivityLifeCycleContext aContext, IAeAttachmentContainer aAttachments) throws AeBusinessProcessException
   {
      if (aAttachments == null)
         return;

      // Get list of attachment elements from the message
      IAeVariable var = (IAeVariable) aContext.findVariable(IAeB4PConstants.ATTACHMENTS_VARIABLE);
      IAeAttachmentContainer currentAttachments = var.getAttachmentData();
      currentAttachments.addAll(aAttachments);
      
   }
   
   /**
    * Returns attachments from the implicit variable based on the task propgation 
    * @param aContext
    */
   private IAeAttachmentContainer getAttachments(IAeActivityLifeCycleContext aContext)
   {
      AeAttachmentPropagationDef def = getDef().getAttachmentPropagation();
      
      // Return when the attachment propogation def is null
      if (def == null)
         return null;

      String from = def.getFromProcess();
      if (IAeProcessTaskConstants.ATTACHMENTS_FROM_ALL.equals(from))
      {
         IAeVariable var = (IAeVariable) aContext.findVariable(IAeB4PConstants.ATTACHMENTS_VARIABLE);
         return var.getAttachmentData();
      }
      else
         return null;
   }
   
   /**
    * @return the pAStateImpl
    */
   public AePeopleActivityStateImpl getState()
   {
      if (mState == null)
         mState = new AePeopleActivityStateImpl();
      return mState;
   }

   /**
    * @param aStateImpl the pAStateImpl to set
    */
   public void setState(AePeopleActivityStateImpl aStateImpl)
   {
      mState = aStateImpl;
   }

   /**
    * @return the peopleActivityDef
    */
   public AePeopleActivityDef getDef()
   {
      return mPeopleActivityDef;
   }

   /**
    * @param aPeopleActivityDef the peopleActivityDef to set
    */
   public void setDef(AePeopleActivityDef aPeopleActivityDef)
   {
      mPeopleActivityDef = aPeopleActivityDef;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageValidationAdapter#getMessageValidator()
    */
   public IAeMessageValidator getMessageValidator()
   {
      return mMessageValidator;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageValidationAdapter#setMessageValidator(org.activebpel.rt.bpel.impl.IAeMessageValidator)
    */
   public void setMessageValidator(IAeMessageValidator aMessageValidator)
   {
      mMessageValidator = aMessageValidator;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageProducerParentAdapter#getMessageDataProducerDef()
    */
   public IAeMessageDataProducerDef getMessageDataProducerDef()
   {
      return getDef();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageProducerParentAdapter#setMessageDataProducer(org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducer)
    */
   public void setMessageDataProducer(IAeMessageDataProducer aMessageDataProducer)
   {
      mProducer = aMessageDataProducer;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageProducerParentAdapter#getMessageDataProducer()
    */
   public IAeMessageDataProducer getMessageDataProducer()
   {
      return mProducer;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageConsumerParentAdapter#getMessageDataConsumerDef()
    */
   public IAeMessageDataConsumerDef getMessageDataConsumerDef()
   {
      return getDef();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageConsumerParentAdapter#setMessageDataConsumer(org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumer)
    */
   public void setMessageDataConsumer(IAeMessageDataConsumer aMessageDataConsumer)
   {
      mConsumer = aMessageDataConsumer;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageConsumerParentAdapter#getMessageDataConsumer()
    */
   public IAeMessageDataConsumer getMessageDataConsumer()
   {
      return mConsumer;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.AeAbstractExtensionActivityImpl#restore(org.w3c.dom.Element)
    */
   public void restore(Element aElement)
   {
      getState().restore(aElement);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.AeAbstractExtensionActivityImpl#save(org.w3c.dom.Element)
    */
   public void save(Element aElement)
   {
      getState().save(aElement);
   }

   /**
    * @see org.activebpel.rt.bpel.IAeInvokeActivity#isOneWay()
    */
   public boolean isOneWay()
   {
      return getDef().isNotification(); 
   }
}
