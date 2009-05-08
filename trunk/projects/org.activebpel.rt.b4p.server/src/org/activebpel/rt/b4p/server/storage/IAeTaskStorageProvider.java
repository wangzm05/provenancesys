//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/IAeTaskStorageProvider.java,v 1.2 2008/02/17 21:36:32 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage;

import org.activebpel.rt.b4p.server.storage.filter.AeTaskFilter;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageProvider;
import org.activebpel.rt.ht.api.IAeHtApiTaskList;
import org.w3c.dom.Element;

/**
 * A task storage provider. This interface defines methods that the task storage will call in
 * order to store/read data in the underlying database.
 */
public interface IAeTaskStorageProvider extends IAeStorageProvider
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
    * @param aFilter
    * @throws AeStorageException
    */
   public IAeHtApiTaskList listTasks(AeTaskFilter aFilter) throws AeStorageException;

}
