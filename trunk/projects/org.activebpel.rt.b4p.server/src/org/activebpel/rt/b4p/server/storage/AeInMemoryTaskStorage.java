//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/AeInMemoryTaskStorage.java,v 1.5 2008/03/20 22:36:26 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activebpel.rt.b4p.server.storage.filter.AeTaskFilter;
import org.activebpel.rt.b4p.server.storage.inmemory.AeInMemoryWhereClauseBuildingVisitor;
import org.activebpel.rt.b4p.server.storage.inmemory.AeTaskFilterUtil;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.ht.api.AeGetTasksParam;
import org.activebpel.rt.ht.api.AeHtApiTask;
import org.activebpel.rt.ht.api.AeHtApiTaskList;
import org.activebpel.rt.ht.api.IAeGetTasksFilterGenericHumanRoles;
import org.activebpel.rt.ht.api.IAeHtApiTaskList;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.AeUserDef;
import org.w3c.dom.Element;

/**
 * Simple in-memory implementation of a task storage.
 */
public class AeInMemoryTaskStorage implements IAeTaskStorage
{
   /** Storage map */
   private Map mStore = new LinkedHashMap();

   /**
    * Ctor.
    */
   public AeInMemoryTaskStorage()
   {
   }

   /**
    * @return the store
    */
   protected Map getStore()
   {
      return mStore;
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.IAeTaskStorage#deleteTask(long)
    */
   public synchronized void deleteTask(long aProcessId) throws AeStorageException
   {
      getStore().remove( new Long(aProcessId) );
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.IAeTaskStorage#insertTask(long, org.w3c.dom.Element)
    */
   public synchronized void insertTask(long aProcessId, Element aTaskInstanceElement) throws AeStorageException
   {
      updateTask(aProcessId, aTaskInstanceElement);
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.IAeTaskStorage#updateTask(long, org.w3c.dom.Element)
    */
   public synchronized void updateTask(long aProcessId, Element aTaskInstanceElement) throws AeStorageException
   {
      try
      {
         AeHtApiTask task = AeTrtTaskInstanceDeserializer.INSTANCE.deserialize(aProcessId, aTaskInstanceElement);
         getStore().put( new Long(aProcessId), task);
      }
      catch(Exception e)
      {
         throw new AeStorageException(e);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.IAeTaskStorage#listMyTaskAbstracts(java.lang.String, java.util.Set, org.activebpel.rt.ht.api.AeGetTasksParam)
    */
   public synchronized IAeHtApiTaskList listMyTaskAbstracts(String aPrincipalName, Set aRoles, AeGetTasksParam aGetTasksParam) throws AeStorageException
   {

      IAeTaskFilterFactory factory = new AeTaskFilterFactory();
      AeTaskFilter filter = factory.createFilter(aPrincipalName, aRoles, aGetTasksParam);

      // result set container
      // add each element as a task object to resultset if it matched the request criteria
      Iterator it = getStore().values().iterator();
      List filteredTasks = new ArrayList();
      while (it.hasNext())
      {
         AeHtApiTask task  = (AeHtApiTask)  it.next();
         // check user permission
         if ( !matchesACL(filter, task) )
         {
            continue;
         }
         // where clause
         if ( !matchesWhereClause(filter, task) )
         {
            continue;
         }
         filteredTasks.add(task);
      }

      // sort result
      AeTaskFilterUtil.sortTasks(filteredTasks, filter.getOrderByFields());
      // num matched query
      int numMatched = filteredTasks.size();
      // create result set to return
      AeHtApiTaskList rval = new AeHtApiTaskList(numMatched);      
      // add results for requested range [listStart : listStart+maxReturn]
      int count = 0;
      for (int i = filter.getListStart(); i < filteredTasks.size() && count < filter.getMaxReturn(); i++)
      {
         rval.add( (AeHtApiTask) filteredTasks.get(i) );
         count++;
      }
      return rval;
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.IAeTaskStorage#listMyTasks(java.lang.String, java.util.Set, org.activebpel.rt.ht.api.AeGetTasksParam)
    */
   public synchronized IAeHtApiTaskList listMyTasks(String aPrincipalName, Set aRoles, AeGetTasksParam aGetTasksParam) throws AeStorageException
   {
      return listMyTaskAbstracts(aPrincipalName, aRoles, aGetTasksParam);
   }

   /**
    * Returns true if given tasks should be added to the resultset based on filter where clause.
    * @param aFilter
    * @param aTask
    */
   protected boolean matchesWhereClause(AeTaskFilter aFilter, AeHtApiTask aTask)
   {
      boolean rval = true;
      if (aFilter.getWhereClause() != null)
      {
         AeInMemoryWhereClauseBuildingVisitor visitor = new AeInMemoryWhereClauseBuildingVisitor(aFilter, aTask);
         aFilter.getWhereClause().accept(visitor);
         rval = visitor.isMatch();
      }
      return rval;
   }

   /**
    * Returns true if given tasks should be added to the resultset based on user permission
    * @param aFilter
    * @param aTask
    */
   protected boolean matchesACL(AeTaskFilter aFilter, AeHtApiTask aTask)
   {
      // Excluded list.
      if ( contains(aFilter.getPrincipal(), aFilter.getWorkQueues(), aTask.getExcludedOwners()) )
      {
         return false;
      }

      // GHR - inclusion list
      Iterator it = aFilter.getGenericHumanRoles().iterator();
      boolean match = false;
      while (!match && it.hasNext() )
      {
         String ghr = (String) it.next();
         if (IAeGetTasksFilterGenericHumanRoles.GHR_ACTUAL_OWNER.equals(ghr))
         {
            match = matches(aFilter.getPrincipal(),  aTask.getActualOwner());
         }
         else if (IAeGetTasksFilterGenericHumanRoles.GHR_INITIATOR.equals(ghr))
         {
            match = matches(aFilter.getPrincipal(),  aTask.getTaskInitiator());
         }
         else if (IAeGetTasksFilterGenericHumanRoles.GHR_STAKEHOLDERS.equals(ghr))
         {
            match = contains(aFilter.getPrincipal(), aFilter.getWorkQueues(), aTask.getTaskStakeholders() );
         }
         else if (IAeGetTasksFilterGenericHumanRoles.GHR_POTENTIAL_OWNERS.equals(ghr))
         {
            match = contains(aFilter.getPrincipal(), aFilter.getWorkQueues(), aTask.getPotentialOwners() );
         }
         else if (IAeGetTasksFilterGenericHumanRoles.GHR_BUSINESS_ADMINISTRATORS.equals(ghr))
         {
            match = contains(aFilter.getPrincipal(), aFilter.getWorkQueues(), aTask.getBusinessAdministrators() );
         }
         else if (IAeGetTasksFilterGenericHumanRoles.GHR_NOTIFICATION_RECIPIENTS.equals(ghr))
         {
            match = contains(aFilter.getPrincipal(), aFilter.getWorkQueues(), aTask.getNotificationRecipients() );
         }
      }
      return match;
   }

   /**
    * Returns true if the OrganizationalEntity user matches the principal or a OrganizationalEntity group matches of any of the given roles.
    * @param aPrincipalName
    * @param aRoles
    * @param aOrgEntity
    * @return true if a match is found.
    */
   protected boolean contains(String aPrincipalName, List aRoles, AeOrganizationalEntityDef aOrgEntity)
   {
      if (aOrgEntity != null && aOrgEntity.getUsers() != null && aOrgEntity.getUsers().getUser(aPrincipalName) != null)
      {
         // principal found in OrganizationalEntity Users.
         return true;
      }
      // check if any of the roles are found in the groups
      else if (aOrgEntity != null && aOrgEntity.getGroups() != null)
      {
         Set groupNames = aOrgEntity.getGroups().getGroupNames();
         Iterator it = aRoles.iterator();
         while (it.hasNext())
         {
            if (groupNames.contains( (String)it.next() ) )
            {
               return true;
            }
         }
      }
      return false;
   }

   /**
    * Returns true if the principal names matches user def.
    * @param aPrincipalName
    * @param aUserDef
    * @return
    */
   protected boolean matches(String aPrincipalName, AeUserDef aUserDef)
   {
      return aUserDef != null && aPrincipalName.equals( aUserDef.getValue() );
   }

}
