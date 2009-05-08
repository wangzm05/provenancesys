//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/AeServerB4PManager.java,v 1.16 2008/03/28 01:52:58 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.IAeProcessTaskConstants;
import org.activebpel.rt.b4p.impl.engine.AeAbstractB4PManager;
import org.activebpel.rt.b4p.impl.engine.AeNotification;
import org.activebpel.rt.b4p.impl.engine.AeTask;
import org.activebpel.rt.b4p.impl.request.AePeopleActivityNotificationRequestBuilder;
import org.activebpel.rt.b4p.impl.request.AePeopleActivityTaskRequestBuilder;
import org.activebpel.rt.b4p.server.function.AeB4PInternalCustomFunctionContext;
import org.activebpel.rt.b4p.server.recovery.AeRecoveryB4PManager;
import org.activebpel.rt.b4p.server.storage.AeInMemoryTaskStorage;
import org.activebpel.rt.b4p.server.storage.IAeTaskStorage;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.config.AeFunctionContextExistsException;
import org.activebpel.rt.bpel.config.IAeConfigChangeListener;
import org.activebpel.rt.bpel.config.IAeUpdatableEngineConfig;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.IAeProcessPlan;
import org.activebpel.rt.bpel.impl.activity.AeExtensionActivityUtil;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext;
import org.activebpel.rt.bpel.impl.activity.IAeImplAdapter;
import org.activebpel.rt.bpel.impl.function.AeInvalidFunctionContextException;
import org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker;
import org.activebpel.rt.bpel.server.deploy.AeDeploymentUtil;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveryAwareManager;
import org.activebpel.rt.message.AeMessageData;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.schema.AeSchemaDuration;
import org.activebpel.wsio.receive.AeMessageContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Server runtime version of the B4P manager which respsonsible for deploying custom
 * ae functions related to the execution of B4P processes.
 */
public class AeServerB4PManager extends AeAbstractB4PManager implements IAeServerB4PManager, IAeConfigChangeListener
{
   /** Path to the manager's map */
   public static final String PATH_TO_MGR_MAP = "CustomManagers/" + MANAGER_NAME + "/"; //$NON-NLS-1$ //$NON-NLS-2$

   /**
    * Tasks store.
    */
   private IAeTaskStorage mStorage;

   /** name of the process to execute for the lifecycle service */
   private String mTaskServiceName;
   /** name of the process to execute for the notification service */
   private String mNotificationServiceName;
   /** duration to use for task finalization */
   private AeSchemaDuration mFinalizationDuration;

   /**
    * Ctor.
    * @param aConfig
    */
   public AeServerB4PManager(Map aConfig)
   {
      super(aConfig);
      updateFromConfig(aConfig);
      listenForConfigChanges();
   }
   
   /**
    * registers as listener for config changes
    */
   protected void listenForConfigChanges()
   {
      // broken out in a separate method for override in junit
      AeEngineFactory.getEngineConfig().getUpdatableEngineConfig().addConfigChangeListener( this );
   }

   /**
    * @see org.activebpel.rt.bpel.config.IAeConfigChangeListener#updateConfig(org.activebpel.rt.bpel.config.IAeUpdatableEngineConfig)
    */
   public void updateConfig(IAeUpdatableEngineConfig aConfig)
   {
      Map taskManagerMap = (Map) aConfig.getEntryByPath(PATH_TO_MGR_MAP);
      updateFromConfig(taskManagerMap);
   }

   /**
    * Gets called once after the manager has been instantiated. If the manager runs
    * into any kind of fatal error during create then it should throw an exception which will
    * halt the startup of the application.
    */
   public void create() throws Exception
   {
      setStorage( new AeInMemoryTaskStorage() );
   }

   /**
    * @param aStorage the storage to set
    */
   protected void setStorage(IAeTaskStorage aStorage)
   {
      mStorage = aStorage;
   }

   /**
    * @see org.activebpel.rt.b4p.server.IAeServerB4PManager#getTaskStorage()
    */
   public IAeTaskStorage getTaskStorage()
   {
      return mStorage;
   }

   /**
    * Overrides method to deploy bprs.
    * @see org.activebpel.rt.b4p.impl.engine.AeAbstractB4PManager#deployResources()
    */
   protected void deployResources() throws IOException, AeException, AeInvalidFunctionContextException
   {
      // call base to deploy custom functions.
      super.deployResources();
      deployBprs();
   }

   /**
    * @see org.activebpel.rt.b4p.impl.engine.AeAbstractB4PManager#addFunctionContext(org.activebpel.rt.bpel.config.IAeUpdatableEngineConfig)
    */
   protected void addFunctionContext(IAeUpdatableEngineConfig aEngineConfig) throws AeInvalidFunctionContextException, AeFunctionContextExistsException
   {
      // call base class to add WS-HT B4P functions
      super.addFunctionContext(aEngineConfig);
      // add ae internal functions
      aEngineConfig.addNewFunctionContext("AeServerB4PManager", IAeB4PConstants.AEB4P_NAMESPACE,  AeB4PInternalCustomFunctionContext.class.getName(), null); //$NON-NLS-1$
   }

   /**
    * Deploys the bprs
    * @throws IOException
    * @throws AeException
    */
   protected void deployBprs() throws IOException, AeException
   {
      deployBpr("aeb4ptasks.bpr"); //$NON-NLS-1$
   }

   /**
    * Deploys named bpr. The bpr should exist in the class path.
    * @param aBprName
    * @throws IOException
    * @throws AeException
    */
   protected void deployBpr(String aBprName) throws IOException, AeException
   {
      AeDeploymentUtil.deployBprWithErrorCheck(getClass(), aBprName);
   }

   /**
    * @see org.activebpel.rt.b4p.impl.engine.IAeB4PManager#cancelTask(long, int, long)
    */
   public void cancelTask(long aProcessId, int aPeopleActivityId, long aTransmissionId) throws AeBusinessProcessException
   {
      // fixme (MF-peopleActivity) replace all of this DOM building with some clean defs/jaxb
      Document doc = AeXmlUtil.newDocument();
      Element element = AeXmlUtil.addElementNS(doc, IAeProcessTaskConstants.TASK_LIFECYCLE_NS, "tlc:cancelTaskRequest"); //$NON-NLS-1$
      element.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:tlc", IAeProcessTaskConstants.TASK_LIFECYCLE_NS); //$NON-NLS-1$

      AeXmlUtil.addElementNS(element, IAeProcessTaskConstants.TASK_LIFECYCLE_NS, "tlc:peopleActivityPid", String.valueOf(aProcessId)); //$NON-NLS-1$
      AeXmlUtil.addElementNS(element, IAeProcessTaskConstants.TASK_LIFECYCLE_NS, "tlc:peopleActivityId", String.valueOf(aPeopleActivityId)); //$NON-NLS-1$

      Map partData = new HashMap();
      partData.put("peopleActivityPid", doc); //$NON-NLS-1$
      IAeMessageData msgData = new AeMessageData(IAeProcessTaskConstants.CANCEL_TASK_REQ_MSG, partData);
      AeMessageContext context = new AeMessageContext();
      context.setServiceName(getTaskServiceName());
      context.setOperation("cancel"); //$NON-NLS-1$

      getEngine().queueReceiveData(null, msgData, null, context);
   }

   /**
    * @see org.activebpel.rt.b4p.impl.engine.IAeB4PManager#executeNotification(org.activebpel.rt.b4p.impl.engine.AeNotification, org.activebpel.rt.attachment.IAeAttachmentContainer, org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext)
    */
   public void executeNotification(AeNotification aNotification, IAeAttachmentContainer aAttachmentContainer, IAeActivityLifeCycleContext aContext)
         throws AeBusinessProcessException
   {
      if (!isTransmitted(aContext, aNotification.getLocationId()))
      {
         Document doc = AePeopleActivityNotificationRequestBuilder.serialize(aNotification, aContext);
         queueReceiveData(aContext, doc, IAeProcessTaskConstants.PROC_NOTIFICATION_REQ_MSG, IAeProcessTaskConstants.NOTIFICATION_REQUEST_PART_NAME, aAttachmentContainer);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.impl.engine.IAeB4PManager#executeTask(org.activebpel.rt.b4p.impl.engine.AeTask, org.activebpel.rt.attachment.IAeAttachmentContainer, org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext)
    */
   public void executeTask(AeTask aTask, IAeAttachmentContainer aAttachmentContainer, IAeActivityLifeCycleContext aContext)
         throws AeBusinessProcessException
   {
      if (!isTransmitted(aContext, aTask.getLocationId()))
      {
         Document doc = AePeopleActivityTaskRequestBuilder.serialize(aTask, aContext);
         queueReceiveData(aContext, doc, IAeProcessTaskConstants.PROC_TASK_REQ_MSG, IAeProcessTaskConstants.TASK_REQUEST_PART_NAME, aAttachmentContainer);
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.AeManagerAdapter#getAdapter(java.lang.Class)
    */
   public IAeImplAdapter getAdapter(Class aAdapterInterface)
   {
      if (aAdapterInterface == IAeRecoveryAwareManager.class)
         return new AeRecoveryB4PManager(this);
      return super.getAdapter(aAdapterInterface);
   }

   /**
    * @return True if this invoke has already been transmitted.
    * @param aContext
    * @param aLocationId
    * @throws AeBusinessProcessException
    */
   protected boolean isTransmitted(IAeActivityLifeCycleContext aContext, int aLocationId) throws AeBusinessProcessException
   {
      try
      {
         IAeTransmissionTracker tracker = aContext.getProcess().getEngine().getTransmissionTracker();
         boolean transmitted = tracker.isTransmitted(aContext.getTransmission().getTransmissionId());
         if (!transmitted)
         {
            tracker.assignTransmissionId(aContext.getTransmission(), aContext.getProcess().getProcessId(), aLocationId);
         }
         return transmitted;
      }
      catch(Exception e)
      {
         throw new AeBusinessProcessException(e.getMessage(), e);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.impl.engine.AeAbstractB4PManager#getDeploymentExtensionsElement(org.activebpel.rt.bpel.impl.IAeBpelObject)
    */
   protected Element getDeploymentExtensionsElement(IAeBpelObject aBpelObject)
   {
      IAeProcessPlan plan = aBpelObject.getProcess().getProcessPlan();
      return plan.getExtensions();
   }

   /**
    * Calls queueReceiveData on aContext by sending oneWay as true for
    * notification request and false for task request
    *
    * @param aLifecycleContext
    * @param aDoc
    * @param aMessageType
    * @param aPartName
    * @param aAttachments
    * @throws AeBusinessProcessException
    */
   protected void queueReceiveData(IAeActivityLifeCycleContext aLifecycleContext, 
         Document aDoc, QName aMessageType, String aPartName, 
         IAeAttachmentContainer aAttachments)
         throws AeBusinessProcessException
   {
      Map partData = new HashMap();
      partData.put(aPartName, aDoc);
      IAeMessageData msgData = new AeMessageData(aMessageType, partData);
      AeMessageContext context = new AeMessageContext();
      msgData.setAttachmentContainer(aAttachments);
      if (aMessageType.equals(IAeProcessTaskConstants.PROC_TASK_REQ_MSG))
      {
         context.setServiceName(getTaskServiceName());
         context.setOperation("processTask"); //$NON-NLS-1$
         queueReceiveData(aLifecycleContext, msgData, context);
      }
      else if (aMessageType.equals(IAeProcessTaskConstants.PROC_NOTIFICATION_REQ_MSG))
      {
         context.setServiceName(getNotificationServiceName());
         context.setOperation("processNotification"); //$NON-NLS-1$
         queueReceiveData(aLifecycleContext, msgData, context);
      }
   }

   /**
    * Queue the receive data onto the engine.
    *
    * @param aLifecycleContext
    * @param aMessageData
    * @param aMessageContext
    * @param aOneWay
    * @throws AeBusinessProcessException
    */
   protected void queueReceiveData(IAeActivityLifeCycleContext aLifecycleContext, IAeMessageData aMessageData,
         AeMessageContext aMessageContext) throws AeBusinessProcessException
   {
      AeExtensionActivityUtil.queueReceiveData(aLifecycleContext.getBpelObject(), aMessageData, aMessageContext, false, aLifecycleContext.getTransmission().getTransmissionId());
   }

   /**
    * @return the notificationServiceName
    */
   protected String getNotificationServiceName()
   {
      return mNotificationServiceName;
   }

   /**
    * @param aNotificationServiceName the notificationServiceName to set
    */
   protected void setNotificationServiceName(String aNotificationServiceName)
   {
      mNotificationServiceName = aNotificationServiceName;
   }

   /**
    * @return the taskServiceName
    */
   protected String getTaskServiceName()
   {
      return mTaskServiceName;
   }

   /**
    * @param aTaskServiceName the taskServiceName to set
    */
   protected void setTaskServiceName(String aTaskServiceName)
   {
      mTaskServiceName = aTaskServiceName;
   }

   /**
    * Setter for the duration
    * @param aDuration
    */
   protected void setFinalizationDuration(AeSchemaDuration aDuration)
   {
      mFinalizationDuration = aDuration;
   }

   /**
    * @see org.activebpel.rt.b4p.server.IAeServerB4PManager#getTaskFinalizationDuration()
    */
   public AeSchemaDuration getTaskFinalizationDuration()
   {
      return mFinalizationDuration;
   }

   /**
    * Extracts the values from the map
    * @param aConfig
    */
   protected void updateFromConfig(Map aConfig)
   {
      setTaskServiceName((String) aConfig.get(IAeServerB4PManager.CFG_KEY_TASK_SERVICE));
      setNotificationServiceName((String) aConfig.get(IAeServerB4PManager.CFG_KEY_NOTIFICATION_SERVICE));
      
      String durationStr = (String) aConfig.get(IAeServerB4PManager.CFG_KEY_FINALIZATION_DURATION);
      if (AeUtil.isNullOrEmpty(durationStr))
      {
         durationStr = IAeServerB4PManager.DEFAULT_FINALIZATION_DURATION;
      }
      
      try
      {
         setFinalizationDuration(new AeSchemaDuration(durationStr));
      }
      catch(Exception e)
      {
         setFinalizationDuration(new AeSchemaDuration(IAeServerB4PManager.DEFAULT_FINALIZATION_DURATION));
      }
   }
}
