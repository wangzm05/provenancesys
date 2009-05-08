//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/inmemory/AeTaskFilterUtil.java,v 1.1 2008/03/20 22:36:26 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage.inmemory;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activebpel.rt.b4p.server.storage.IAeTaskFilterConstants;
import org.activebpel.rt.b4p.server.storage.filter.AeTaskFilterOrderByClause;
import org.activebpel.rt.ht.api.AeHtApiTask;

/**
 * Task filter related utility class.
 */
public class AeTaskFilterUtil
{

   /**
    * Collection of column names that are supported.
    */
   public static Set sSUPPORTED_COLUMNS = new HashSet();
   
   static
   {
      sSUPPORTED_COLUMNS.add(IAeTaskFilterConstants.COLUMN_STATUS);
      sSUPPORTED_COLUMNS.add(IAeTaskFilterConstants.COLUMN_PRIMARY_SEARCH_BY);
      sSUPPORTED_COLUMNS.add(IAeTaskFilterConstants.COLUMN_TASK_TYPE);
      sSUPPORTED_COLUMNS.add(IAeTaskFilterConstants.COLUMN_PRIORITY);
      sSUPPORTED_COLUMNS.add(IAeTaskFilterConstants.COLUMN_CREATED_ON);
      sSUPPORTED_COLUMNS.add(IAeTaskFilterConstants.COLUMN_EXPIRATION_TIME);
      sSUPPORTED_COLUMNS.add(IAeTaskFilterConstants.COLUMN_OWNER);
      sSUPPORTED_COLUMNS.add(IAeTaskFilterConstants.COLUMN_PRES_NAME);
      sSUPPORTED_COLUMNS.add(IAeTaskFilterConstants.COLUMN_PRES_SUBJECT);
   }

   /**
    * Returns true if the column name is supported by the in-memory storage.
    * @param aColumnName
    * @return true if column is supported.
    */
   public static boolean isColumnSupported(String aColumnName)
   {
      return sSUPPORTED_COLUMNS.contains(aColumnName);
   }
   
   /**
    * Returns column value or <code>null</code> if not supported.
    * The supported column types are status, primary search by and the task type.
    * @param aTask 
    * @param aColumnName name defined in IAeTaskFilterConstants.
    * @return column value
    */
   public static String getColumnStringValue(AeHtApiTask aTask, String aColumnName)
   {
      String rval = null;
      if (IAeTaskFilterConstants.COLUMN_STATUS.equals(aColumnName))
      {
         rval = aTask.getStatus();
      }
      else if (IAeTaskFilterConstants.COLUMN_PRIMARY_SEARCH_BY.equals(aColumnName))
      {
         rval = aTask.getPrimarySearchBy();
      }
      else if (IAeTaskFilterConstants.COLUMN_TASK_TYPE.equals(aColumnName))
      {
         rval = aTask.getTaskType();
      }
      else if (IAeTaskFilterConstants.COLUMN_PRIORITY.equals(aColumnName))
      {
         rval = String.valueOf( aTask.getPriority());
      }
      else if (IAeTaskFilterConstants.COLUMN_CREATED_ON.equals(aColumnName))
      {
         rval = aTask.getCreatedOn() != null ? aTask.getCreatedOn().toString() : ""; //$NON-NLS-1$
      }
      else if (IAeTaskFilterConstants.COLUMN_EXPIRATION_TIME.equals(aColumnName))
      {
         rval = aTask.getExpirationTime() != null ? aTask.getExpirationTime().toString() : ""; //$NON-NLS-1$
      }
      else if (IAeTaskFilterConstants.COLUMN_OWNER.equals(aColumnName))
      {
         rval = aTask.getActualOwnerAsString();
      }
      else if (IAeTaskFilterConstants.COLUMN_PRES_NAME.equals(aColumnName))
      {
         rval = aTask.getPresentationName();
      }
      else if (IAeTaskFilterConstants.COLUMN_PRES_SUBJECT.equals(aColumnName))
      {
         rval = aTask.getPresentationSubject();
      }      
      return rval;
   }
   
   /**
    * Sorts a list of AeHtApiTask.
    * @param aTasks list containing AeHtApiTask objects.
    * @param aOrderBysClauses list containing AeTaskFilterOrderByClause objects.
    */
   public static void sortTasks(List aTasks, List aOrderBysClauses)
   {
      // orderby list: support only the primary order by clause. 
      if (aOrderBysClauses != null && !aOrderBysClauses.isEmpty() )
      {
         AeTaskFilterOrderByClause orderBysClause = (AeTaskFilterOrderByClause)aOrderBysClauses.get(0);
         AeTaskComparator comparator = new AeTaskComparator(orderBysClause);
         Collections.sort(aTasks, comparator);
      }
   }
}
