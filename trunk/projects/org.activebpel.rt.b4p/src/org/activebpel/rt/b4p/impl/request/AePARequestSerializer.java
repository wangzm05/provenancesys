//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/request/AePARequestSerializer.java,v 1.9.2.2 2008/04/14 21:25:30 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.request;

import java.util.Iterator;
import java.util.Set;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.IAeProcessTaskConstants;
import org.activebpel.rt.b4p.def.io.AeB4PIO;
import org.activebpel.rt.b4p.function.AeB4PProcessVariableCollectionSerializer;
import org.activebpel.rt.b4p.function.AeB4PProcessVariableSerializer;
import org.activebpel.rt.b4p.impl.task.data.AeHumanTaskContext;
import org.activebpel.rt.b4p.impl.task.data.AeInitialState;
import org.activebpel.rt.b4p.impl.task.data.AeInterfaceMetadata;
import org.activebpel.rt.b4p.impl.task.data.AeProcessNotificationRequest;
import org.activebpel.rt.b4p.impl.task.data.AeProcessTaskRequest;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.io.AeHtIO;
import org.activebpel.rt.message.AeMessagePartTypeInfo;
import org.activebpel.rt.message.AeMessagePartsMap;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.exolab.castor.xml.schema.XMLType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Class to serialize process task request object
 */
public class AePARequestSerializer
{
   private AeTypeMapping mTypeMapping;
   
   /**
    * C'tor
    * @param aTypeMapping
    */
   public AePARequestSerializer(AeTypeMapping aTypeMapping)
   {
      mTypeMapping = aTypeMapping;
   }

   /**
    * Serialize AeProcessTaskRequest to a Document
    * @param aRequest
    * @return Document process request document 
    */
   public Document serialize(AeProcessTaskRequest aRequest) throws AeBusinessProcessException
   {
      Document doc = AeXmlUtil.newDocument();
      
      // build root element
      Element processTaskRequestElem = AeXmlUtil.addElementNS(doc, IAeProcessTaskConstants.TASK_LIFECYCLE_NS,"tlc:processTaskRequest"); //$NON-NLS-1$
      processTaskRequestElem.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:htd", IAeProcessTaskConstants.WSHT_NS); //$NON-NLS-1$
      processTaskRequestElem.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:trt", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
      processTaskRequestElem.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:tlc", IAeProcessTaskConstants.TASK_LIFECYCLE_NS); //$NON-NLS-1$
      processTaskRequestElem.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:htp", IAeProcessTaskConstants.WSHT_PROTOCOL_NS); //$NON-NLS-1$
      
      // serialize task set on process request
      AeTaskDef taskDef = aRequest.getTaskDef();
      Element taskElem;
      taskElem = AeHtIO.serialize2Element(taskDef);
      
      Node tempElem = doc.importNode(taskElem, true); 
      processTaskRequestElem.appendChild(tempElem);
      // serialize initial state
      // serialize task interface metadata
      appendInitialState(processTaskRequestElem, aRequest.getInitialState());
      appendInterfaceMetadata(processTaskRequestElem, aRequest.getInterfaceMetadata());

      // append process id 
      AeXmlUtil.addElementNS(processTaskRequestElem, IAeProcessTaskConstants.TASK_LIFECYCLE_NS, "tlc:peopleActivityPid", String.valueOf(aRequest.getPeopleActivityProcessId())); //$NON-NLS-1$
      // append PA instance id 
      AeXmlUtil.addElementNS(processTaskRequestElem, IAeProcessTaskConstants.TASK_LIFECYCLE_NS, "tlc:peopleActivityId", String.valueOf(aRequest.getPeopleActivityInstanceId())); //$NON-NLS-1$
      
      return doc;
   }
   
   /**
    * Serializes AeProcessNotificationRequest to a Document
    * @param aRequest
    * Returns Document for notification request
    * @throws AeBusinessProcessException 
    */
   public Document serialize(AeProcessNotificationRequest aRequest) throws AeBusinessProcessException 
   {
      Document doc = AeXmlUtil.newDocument();
      
      // build root element
      Element procNotificationReqElem = AeXmlUtil.addElementNS(doc, IAeProcessTaskConstants.NOTIFICATION_LIFICYCLE_NS,"nt:processNotificationRequest"); //$NON-NLS-1$
      procNotificationReqElem.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:htd", IAeProcessTaskConstants.WSHT_NS); //$NON-NLS-1$
      procNotificationReqElem.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:trt", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
      procNotificationReqElem.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:nt", IAeProcessTaskConstants.NOTIFICATION_LIFICYCLE_NS); //$NON-NLS-1$
      procNotificationReqElem.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:tlc", IAeProcessTaskConstants.TASK_LIFECYCLE_NS); //$NON-NLS-1$
      procNotificationReqElem.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:htp", IAeProcessTaskConstants.WSHT_PROTOCOL_NS); //$NON-NLS-1$
      
      AeNotificationDef notificationDef = aRequest.getNotificationDef();
      Element notificationElem;
      notificationElem = AeHtIO.serialize2Element(notificationDef);
      Node tempElem = doc.importNode(notificationElem, true); 
      procNotificationReqElem.appendChild(tempElem);
      // serialize initial state
      appendInitialState(procNotificationReqElem, aRequest.getInitialState());
      appendInterfaceMetadata(procNotificationReqElem, aRequest.getInterfaceMetadata());

      // append process id 
      AeXmlUtil.addElementNS(procNotificationReqElem, IAeProcessTaskConstants.TASK_LIFECYCLE_NS, "tlc:peopleActivityPid", String.valueOf(aRequest.getPeopleActivityProcessId())); //$NON-NLS-1$
      // append PA instance id 
      AeXmlUtil.addElementNS(procNotificationReqElem, IAeProcessTaskConstants.TASK_LIFECYCLE_NS, "tlc:peopleActivityId", String.valueOf(aRequest.getPeopleActivityInstanceId())); //$NON-NLS-1$
      
      return doc;
      
   }
   
   /**
    * serialized AeInitialState to an Element
    * @param aDoc
    * @param aInilineState
    * @throws AeBusinessProcessException 
    */
   private void appendInitialState(Element aProcessTaskRequestElem, AeInitialState aInilineState) throws AeBusinessProcessException
   {
      // Create initialState Element
      Element initStateElem = AeXmlUtil.addElementNS(aProcessTaskRequestElem, IAeB4PConstants.AEB4P_NAMESPACE, "trt:initialState"); //$NON-NLS-1$
      Element inputElem = AeXmlUtil.addElementNS(initStateElem, IAeB4PConstants.AEB4P_NAMESPACE, "trt:input"); //$NON-NLS-1$
      
      // serialize input
      IAeMessageData msgData = aInilineState.getInput();
      AeB4PProcessVariableSerializer msgSerializer = new AeB4PProcessVariableSerializer(mTypeMapping);
      msgSerializer.serializeMessage(msgData, inputElem, IAeB4PConstants.AEB4P_NAMESPACE, "trt", "part", true);  //$NON-NLS-1$//$NON-NLS-2$
      
      // add created by
      AeXmlUtil.addElementNS(initStateElem, IAeB4PConstants.AEB4P_NAMESPACE, "trt:createdBy", aInilineState.getCreatedBy()); //$NON-NLS-1$      

      // add start by
      if (aInilineState.getStartBy() != null)
         AeXmlUtil.addElementNS(initStateElem, IAeB4PConstants.AEB4P_NAMESPACE, "trt:startBy", aInilineState.getStartBy().toString()); //$NON-NLS-1$

      // add complete by
      if (aInilineState.getCompleteBy() != null)
         AeXmlUtil.addElementNS(initStateElem, IAeB4PConstants.AEB4P_NAMESPACE, "trt:completeBy", aInilineState.getCompleteBy().toString()); //$NON-NLS-1$
      
      // add human task context
      AeHumanTaskContext htContext = aInilineState.getHumanTaskContext();
      Element htContextElem = addHumanTaskContext(initStateElem, htContext);
      
      // add deferActivation and expiration
      if (htContext.getDeferActivationTime() != null)
      {
         AeXmlUtil.addElementNS(htContextElem, IAeProcessTaskConstants.WSHT_PROTOCOL_NS, "htp:deferActivationTime", htContext.getDeferActivationTime().toString()); //$NON-NLS-1$
      }
      if (htContext.getExpiration() != null)
      {
         AeXmlUtil.addElementNS(htContextElem, IAeProcessTaskConstants.WSHT_PROTOCOL_NS, "htp:expirationTime", htContext.getExpiration().toString()); //$NON-NLS-1$
      }


      // add attahcments 
      if ( (aInilineState.getHumanTaskContext().getAttachments() != null) || (AeUtil.notNullOrEmpty(aInilineState.getHumanTaskContext().getTo())) ) 
      {
         Element attachments = AeXmlUtil.addElementNS(htContextElem, IAeProcessTaskConstants.WSHT_PROTOCOL_NS, "htp:attachments"); //$NON-NLS-1$
         // add returnAttachments Element
         if (AeUtil.notNullOrEmpty(aInilineState.getHumanTaskContext().getTo()))
         {
            String to = aInilineState.getHumanTaskContext().getTo();
            AeXmlUtil.addElementNS(attachments, IAeProcessTaskConstants.WSHT_PROTOCOL_NS, "htp:returnAttachments", to); //$NON-NLS-1$
         }
      }
      // add process variables
      Set processVars = aInilineState.getProcessVariables();
      if (processVars != null)
      {
         AeB4PProcessVariableCollectionSerializer serializer = new AeB4PProcessVariableCollectionSerializer(mTypeMapping);
         Element procVarsElem = serializer.serializeVariables(processVars);
         Node tempElem = htContextElem.getOwnerDocument().importNode(procVarsElem, true);
         initStateElem.appendChild(tempElem);
      }
   }

   /**
    * @param aNode
    * @param aHTContext
    */
   public static Element addHumanTaskContext(Node aNode, AeHumanTaskContext aHTContext)
   {
      Element htContextElem = AeXmlUtil.addElementNS(aNode, IAeProcessTaskConstants.WSHT_PROTOCOL_NS, "htp:humanTaskContext"); //$NON-NLS-1$
      AeXmlUtil.addElementNS(htContextElem, IAeProcessTaskConstants.WSHT_PROTOCOL_NS, "htp:priority", String.valueOf(aHTContext.getPriority())); //$NON-NLS-1$
      AePeopleAssignmentsDef peopleASDef = aHTContext.getPeopleAssignments();
      if (peopleASDef != null)
      {
         Element paElem = AeB4PIO.serialize2Protocol(peopleASDef);
         Node tempElem = htContextElem.getOwnerDocument().importNode(paElem, true);
         htContextElem.appendChild(tempElem);
      }
      AeXmlUtil.addElementNS(htContextElem, IAeProcessTaskConstants.WSHT_PROTOCOL_NS, "htp:isSkipable", String.valueOf(aHTContext.isSkippable())); //$NON-NLS-1$
      return htContextElem;
   }
   
   /**
    * serializes AeTaskInterfaceMetadata and appends to taskRequestElement
    * @param aTaskElem
    * @param aMetadata
    */
   private void appendInterfaceMetadata(Element aTaskElem, AeInterfaceMetadata aMetadata)
   {
      // Create interfaceMetadata Element
      Element interfaceMetadataElem = AeXmlUtil.addElementNS(aTaskElem, IAeB4PConstants.AEB4P_NAMESPACE, "trt:interfaceMetadata"); //$NON-NLS-1$

      // Append input parts
      Element inputElem = AeXmlUtil.addElementNS(interfaceMetadataElem, IAeB4PConstants.AEB4P_NAMESPACE, "trt:input"); //$NON-NLS-1$
      AeMessagePartsMap inputParts = aMetadata.getInput();
      addParts(inputElem, inputParts);

      // Append output parts
      if (aMetadata.getOutput() != null)
      {
         Element outputElem = AeXmlUtil.addElementNS(interfaceMetadataElem, IAeB4PConstants.AEB4P_NAMESPACE, "trt:output"); //$NON-NLS-1$
         AeMessagePartsMap outputParts = aMetadata.getOutput();
         addParts(outputElem, outputParts);
      }

      // Append faults
      if (AeUtil.notNullOrEmpty(aMetadata.getFaultNames()))
      {
         Element faultElem = AeXmlUtil.addElementNS(interfaceMetadataElem, IAeB4PConstants.AEB4P_NAMESPACE, "trt:faults"); //$NON-NLS-1$
         for(Iterator iter = aMetadata.getFaultNames().iterator(); iter.hasNext(); )
         {
            Element part = AeXmlUtil.addElementNS(faultElem, IAeB4PConstants.AEB4P_NAMESPACE, "trt:fault"); //$NON-NLS-1$
            part.setAttribute("name", (String) iter.next()); //$NON-NLS-1$
         }
      }
   }

   /**
    * Adds all of the part elements
    * @param aElement
    * @param aPartsMap
    */
   protected void addParts(Element aElement, AeMessagePartsMap aPartsMap)
   {
      for(Iterator iter = aPartsMap.getPartNames(); iter.hasNext(); )
      {
         String partName = (String) iter.next();

         Element part = AeXmlUtil.addElementNS(aElement, IAeB4PConstants.AEB4P_NAMESPACE, "trt:part"); //$NON-NLS-1$
         part.setAttribute("name", partName); //$NON-NLS-1$
         
         String typeHint = getTypeHint(aPartsMap.getPartInfo(partName));
         part.setAttribute("typeHint", typeHint); //$NON-NLS-1$
      }
   }

   /**
    * Determines the type hint frm the part info
    * @param aPartInfo
    */
   protected String getTypeHint(AeMessagePartTypeInfo aPartInfo)
   {
      // FIXMEQ (b4p-render) should provide better rendering hints. Not bother now since need to handle this better for notifications fired from escalations. Probably need to inline this information into the def somewhere so it moves with the def through the system.
      XMLType xmlType = aPartInfo.getXMLType();
      if (xmlType == null || aPartInfo.getElementName() != null || AeXmlUtil.isComplexOrAny(xmlType))
         return "complex"; //$NON-NLS-1$
      return "string"; //$NON-NLS-1$
   }
}
