//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/IAeTaskStorage.java,v 1.3 2008/02/17 21:36:32 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage;

import java.util.Set;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.IAeStorage;
import org.activebpel.rt.ht.api.AeGetTasksParam;
import org.activebpel.rt.ht.api.IAeHtApiTaskList;
import org.w3c.dom.Element;

/**
 * The interface implemented by task storage implementations.
 */
public interface IAeTaskStorage extends IAeStorage
{
   /**
    * Takes a 'trt:taskInstance' element and inserts its information into the
    * database.  This should only be called once when the Task is
    * created.
    *
    * @param aProcessId
    * @param aTaskInstanceElement the trt:taskInstance element.
    * @throws AeStorageException
    */
   public void insertTask(long aProcessId, Element aTaskInstanceElement) throws AeStorageException;

   /**
    * Updates the task information in the database.  This is called
    * whenever the task changes state.
    *
    * @param aProcessId
    * @param aTaskInstanceElement the trt:taskInstance element.
    * @throws AeStorageException
    */
   public void updateTask(long aProcessId, Element aTaskInstanceElement) throws AeStorageException;

   /**
    * Deletes the task information from the database. This is called when the
    * task is finalized.
    *
    * @param aProcessId
    * @throws AeStorageException
    */
   public void deleteTask(long aProcessId) throws AeStorageException;
   
   /**
    * Queries the storage layer and returns list of tasks.
    * @param aPrincipalName 
    * @param aRoles
    * @param aGetTasksParam
    * @throws AeStorageException
    */
   public IAeHtApiTaskList listMyTaskAbstracts(String aPrincipalName, Set aRoles, AeGetTasksParam aGetTasksParam) throws AeStorageException;

   /**
    * Queries the storage layer and returns list of tasks.
    * @param aPrincipalName 
    * @param aRoles
    * @param aGetTasksParam
    * @throws AeStorageException
    */
   public IAeHtApiTaskList listMyTasks(String aPrincipalName, Set aRoles, AeGetTasksParam aGetTasksParam) throws AeStorageException;
   
   
}
