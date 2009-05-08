//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/invoke/AeTaskStorageInvokeHandler.java,v 1.3 2008/02/17 21:36:33 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage.invoke;

import java.util.HashMap;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.IAeProcessTaskConstants;
import org.activebpel.rt.b4p.server.IAeServerB4PManager;
import org.activebpel.rt.b4p.server.storage.AeTaskStoreData;
import org.activebpel.rt.b4p.server.storage.IAeB4PStorageConstants;
import org.activebpel.rt.b4p.server.storage.IAeTaskStorage;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.bpel.wsio.invoke.AeWSIInvokeHandlerBase;
import org.activebpel.rt.ht.api.IAeHtApiTaskList;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.wsio.AeWebServiceMessageData;
import org.activebpel.wsio.IAeWebServiceMessageData;
import org.activebpel.wsio.invoke.AeInvokeResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * A custom invoke handler that stores task information in the
 * Task Storage.
 */
public class AeTaskStorageInvokeHandler extends AeWSIInvokeHandlerBase
{
   /** The StoreTaskMessage QName. */
   private static final QName STORE_TASK_MESSAGE = new QName(IAeB4PStorageConstants.TASK_STORAGE_NS, "StoreTaskMessage"); //$NON-NLS-1$
   /** Delete task message name */
   private static final QName DELETE_TASK_MESSAGE = new QName(IAeB4PStorageConstants.TASK_STORAGE_NS, "DeleteTaskMessage"); //$NON-NLS-1$
   /** list task abstracts message name */
   private static final QName LIST_TASK_ABSTRACTS_MESSAGE = new QName(IAeB4PStorageConstants.TASK_STORAGE_NS, "ListTaskAbstractsMessage"); //$NON-NLS-1$
   /** list task abstracts message name */
   private static final QName LIST_TASK_ABSTRACTS_RESPONSE_MESSAGE = new QName(IAeB4PStorageConstants.TASK_STORAGE_NS, "ListTaskAbstractsResponseMessage"); //$NON-NLS-1$
   /** list tasks message name */
   private static final QName LIST_TASKS_MESSAGE = new QName(IAeB4PStorageConstants.TASK_STORAGE_NS, "ListTasksMessage"); //$NON-NLS-1$
   /** list tasks message name */
   private static final QName LIST_TASKS_RESPONSE_MESSAGE = new QName(IAeB4PStorageConstants.TASK_STORAGE_NS, "ListTasksResponseMessage"); //$NON-NLS-1$
   
   ///** store task fault message name */
   //private static final QName STORE_FALUT_MESSAGE = new QName(IAeB4PStorageConstants.TASK_STORAGE_NS, "TaskStorageFaultMessage"); //$NON-NLS-1$
   /** EmptyMessage type name */
   private static final QName EMPTY_MESSAGE = new QName(IAeB4PStorageConstants.TASK_STORAGE_NS, "EmptyMessage"); //$NON-NLS-1$


   /**
    * Convenience method to get the IAeTaskStorage from the B4P manager.
    * @throws Exception
    */
   protected IAeTaskStorage getStorage() throws Exception
   {
      IAeServerB4PManager taskManager = (IAeServerB4PManager) AeEngineFactory.getEngine().getCustomManager(IAeProcessTaskConstants.B4P_MANAGER_KEY);
      return taskManager.getTaskStorage();
   }

   /**
    * Convenience method to set tns:EmptyMessage message as response.
    */
   protected void setEmptyResponse(AeInvokeResponse aResponse) throws Exception
   {
       AeWebServiceMessageData respMsgData = new AeWebServiceMessageData(EMPTY_MESSAGE, new HashMap());
       aResponse.setMessageData( respMsgData );
   }

   /**
    * Implements the 'storeTask' operation.
    *
    * @param aMessageData
    * @param aResponse
    * @throws Exception
    */
   public void storeTask(IAeWebServiceMessageData aMessageData, AeInvokeResponse aResponse) throws Exception
   {
      compareExpectedMessageType(aMessageData, STORE_TASK_MESSAGE, "storeTask"); //$NON-NLS-1$
      AeTaskStoreDataDeserializer des = new AeTaskStoreDataDeserializer( extractMessagePartDocument(aMessageData, "storeTaskData")); //$NON-NLS-1$
      AeTaskStoreData data = des.getTaskStoreData();
      IAeTaskStorage storage = getStorage();
      if (data.isUpdate())
      {
         storage.updateTask(data.getProcessId(), data.getTaskInstanceElement());
      }
      else
      {
         storage.insertTask(data.getProcessId(), data.getTaskInstanceElement());
      }
      // Build and return response message.
      setEmptyResponse(aResponse);
   }

    /**
    * Implements the 'deleteTask' operation.
    *
    * @param aMessageData
    * @param aResponse
    * @throws Exception
    */
   public void deleteTask(IAeWebServiceMessageData aMessageData, AeInvokeResponse aResponse) throws Exception
   {
      compareExpectedMessageType(aMessageData, DELETE_TASK_MESSAGE, "deleteTask"); //$NON-NLS-1$
      Document deleteTaskDataDom =  extractMessagePartDocument(aMessageData, "deleteTaskData"); //$NON-NLS-1$
      long processId = AeUtil.parseLong( AeXmlUtil.getText(deleteTaskDataDom.getDocumentElement()), -1);
      IAeTaskStorage storage = getStorage();
      storage.deleteTask( processId );
      // Build and return response message.
      setEmptyResponse(aResponse);
   }

   /**
    * Returns list of tasks per given query. This method implements the ws-ht htda:getMyTaskAbstracts.
    * @param aMessageData
    * @param aResponse
    * @throws Exception
    */
   public void listTaskAbstracts(IAeWebServiceMessageData aMessageData, AeInvokeResponse aResponse) throws Exception
   {
      compareExpectedMessageType(aMessageData, LIST_TASK_ABSTRACTS_MESSAGE, "listTaskAbstracts"); //$NON-NLS-1$
      AeTaskStorageListTaskAbstractsDeserializer des = new AeTaskStorageListTaskAbstractsDeserializer( extractMessagePartDocument(aMessageData, "listTaskAbstracts")); //$NON-NLS-1$
      IAeTaskStorage storage = getStorage();
      IAeHtApiTaskList taskList = storage.listMyTaskAbstracts(des.getPrincipal(), des.getRoles(),  des.getTasksParam() );
      AeTaskStorageListTaskAbstractsSerializer ser = new AeTaskStorageListTaskAbstractsSerializer(taskList);
      Element responseEle = ser.serialize();
      setResponseData(aResponse, LIST_TASK_ABSTRACTS_RESPONSE_MESSAGE, "listTaskAbstractsResponse", responseEle.getOwnerDocument()); //$NON-NLS-1$
   }
   
   /**
    * Returns list of tasks per given query. This method implements the ws-ht htda:getMyTasks. 
    * @param aMessageData
    * @param aResponse
    * @throws Exception
    */
   public void listTasks(IAeWebServiceMessageData aMessageData, AeInvokeResponse aResponse) throws Exception
   {
      compareExpectedMessageType(aMessageData, LIST_TASKS_MESSAGE, "listTasks"); //$NON-NLS-1$
      AeTaskStorageListTasksDeserializer des = new AeTaskStorageListTasksDeserializer( extractMessagePartDocument(aMessageData, "listTasks")); //$NON-NLS-1$
      IAeTaskStorage storage = getStorage();
      IAeHtApiTaskList taskList = storage.listMyTasks(des.getPrincipal(), des.getRoles(),  des.getTasksParam() );
      AeTaskStorageListTasksSerializer ser = new AeTaskStorageListTasksSerializer(taskList);
      Element responseEle = ser.serialize();
      setResponseData(aResponse, LIST_TASKS_RESPONSE_MESSAGE, "listTasksResponse", responseEle.getOwnerDocument()); //$NON-NLS-1$
   }      
}
