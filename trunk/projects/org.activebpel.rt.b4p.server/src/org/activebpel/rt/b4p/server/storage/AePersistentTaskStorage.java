//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/AePersistentTaskStorage.java,v 1.9 2008/03/20 22:36:26 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage;

import java.util.Map;
import java.util.Set;

import org.activebpel.rt.b4p.server.storage.filter.AeTaskFilter;
import org.activebpel.rt.bpel.server.engine.storage.AeAbstractStorage;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageProvider;
import org.activebpel.rt.ht.api.AeGetTasksParam;
import org.activebpel.rt.ht.api.IAeHtApiTaskList;
import org.w3c.dom.Element;

/**
 * A provider-based implementation of a task storage.  This class delegates all of the database
 * calls to an instance of IAeTaskStorageProvider.  The purpose of this class is to encapsulate
 * storage 'logic' so that it can be shared across multiple storage implementations (such as SQL
 * and Tamino).
 */
public class AePersistentTaskStorage extends AeAbstractStorage implements IAeTaskStorage
{
   /**
    * Constructs a provider-based task storage with the given engine
    * config info and provider.
    *
    * @param aConfig
    * @param aProvider
    */
   public AePersistentTaskStorage(Map aConfig, IAeStorageProvider aProvider)
   {
      super(aProvider);
   }

   /**
    * Convenience method to get the storage provider cast to a queue storage provider.
    */
   protected IAeTaskStorageProvider getTaskStorageProvider()
   {
      return (IAeTaskStorageProvider) getProvider();
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.IAeTaskStorage#deleteTask(long)
    */
   public void deleteTask(long aProcessId) throws AeStorageException
   {
      getTaskStorageProvider().deleteTask(aProcessId);
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.IAeTaskStorage#insertTask(long, org.w3c.dom.Element)
    */
   public void insertTask(long aProcessId, Element aTaskInstanceElement) throws AeStorageException
   {
      getTaskStorageProvider().insertTask(aProcessId, aTaskInstanceElement);
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.IAeTaskStorage#updateTask(long, org.w3c.dom.Element)
    */
   public void updateTask(long aProcessId, Element aTaskInstanceElement) throws AeStorageException
   {
      getTaskStorageProvider().updateTask(aProcessId, aTaskInstanceElement);
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.IAeTaskStorage#listMyTaskAbstracts(java.lang.String, java.util.Set, org.activebpel.rt.ht.api.AeGetTasksParam)
    */
   public IAeHtApiTaskList listMyTaskAbstracts(String aPrincipalName, Set aRoles, AeGetTasksParam aGetTasksParam) throws AeStorageException
   {
      IAeTaskFilterFactory factory = new AeTaskFilterFactory();
      AeTaskFilter filter = factory.createFilter(aPrincipalName, aRoles, aGetTasksParam);
      return getTaskStorageProvider().listTasks(filter);
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.IAeTaskStorage#listMyTasks(java.lang.String, java.util.Set, org.activebpel.rt.ht.api.AeGetTasksParam)
    */
   public IAeHtApiTaskList listMyTasks(String aPrincipalName, Set aRoles, AeGetTasksParam aGetTasksParam) throws AeStorageException
   {
      return listMyTaskAbstracts(aPrincipalName, aRoles, aGetTasksParam);
   }

}
