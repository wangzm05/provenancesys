//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/AeTaskFilterFactory.java,v 1.1 2008/03/20 22:36:26 PJayanetti Exp $
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.server.AeMessages;
import org.activebpel.rt.b4p.server.storage.filter.AeCompositeWhereCondition;
import org.activebpel.rt.b4p.server.storage.filter.AeTaskFilter;
import org.activebpel.rt.b4p.server.storage.filter.AeTaskFilterOrderByClause;
import org.activebpel.rt.b4p.server.storage.filter.AeWhereCondition;
import org.activebpel.rt.b4p.server.storage.filter.IAeWhereCondition;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.ht.api.AeGetTasksParam;
import org.activebpel.rt.ht.api.IAeGetTasksFilterGenericHumanRoles;
import org.activebpel.rt.ht.api.IAeGetTasksFilterOrderByFields;
import org.activebpel.rt.util.AeUtil;

/**
 * Factory which creates a basic filter for persistent storage.
 */
public class AeTaskFilterFactory implements IAeTaskFilterFactory
{
   private static final Pattern sConditionClausePattern = Pattern.compile("Task.([a-zA-Z]+)[ ]*([=!><][=]?)[ ]*['\"]?([^'\"]*)['\"]?"); //$NON-NLS-1$

   /** Mapping from sort column field id in the filter to storage column name. */
   private static Map sSortFieldIdToColumnMap = new HashMap();

   static
   {
      sSortFieldIdToColumnMap.put(IAeGetTasksFilterOrderByFields.FID_NAME, IAeTaskFilterConstants.COLUMN_NAME);
      sSortFieldIdToColumnMap.put(IAeGetTasksFilterOrderByFields.FID_NAMESPACE, IAeTaskFilterConstants.COLUMN_TARGET_NS);
      sSortFieldIdToColumnMap.put(IAeGetTasksFilterOrderByFields.FID_PRESENTATION_NAME, IAeTaskFilterConstants.COLUMN_PRES_NAME);
      sSortFieldIdToColumnMap.put(IAeGetTasksFilterOrderByFields.FID_SUMMARY, IAeTaskFilterConstants.COLUMN_PRES_SUBJECT);
      sSortFieldIdToColumnMap.put(IAeGetTasksFilterOrderByFields.FID_CREATED, IAeTaskFilterConstants.COLUMN_CREATED_ON);
      sSortFieldIdToColumnMap.put(IAeGetTasksFilterOrderByFields.FID_PRIORITY, IAeTaskFilterConstants.COLUMN_PRIORITY);
      sSortFieldIdToColumnMap.put(IAeGetTasksFilterOrderByFields.FID_STATE, IAeTaskFilterConstants.COLUMN_STATUS);
      sSortFieldIdToColumnMap.put(IAeGetTasksFilterOrderByFields.FID_OWNER, IAeTaskFilterConstants.COLUMN_OWNER);
      sSortFieldIdToColumnMap.put(IAeGetTasksFilterOrderByFields.FID_MODIFIED, IAeTaskFilterConstants.COLUMN_MODIFIED_ON);
      sSortFieldIdToColumnMap.put(IAeGetTasksFilterOrderByFields.FID_EXPIRATION, IAeTaskFilterConstants.COLUMN_EXPIRATION_TIME);
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.IAeTaskFilterFactory#createFilter(java.lang.String, java.util.Set, org.activebpel.rt.ht.api.AeGetTasksParam)
    */
   public AeTaskFilter createFilter(String aPrincipalName, Set aRoles, AeGetTasksParam aGetTasksParam)
         throws AeStorageException
   {
      AeTaskFilter filter = new AeTaskFilter(aPrincipalName);
      if ( AeUtil.notNullOrEmpty(aGetTasksParam.getGenericHumanRole()) )
      {
         filter.addGenericHumanRole( aGetTasksParam.getGenericHumanRole() );
      }
      else
      {
         // Action to take if role is not specified:
         // Add default roles to simulate All Open filter (potential owners +  notification recipients_
         filter.addGenericHumanRole( IAeGetTasksFilterGenericHumanRoles.GHR_POTENTIAL_OWNERS );
         filter.addGenericHumanRole( IAeGetTasksFilterGenericHumanRoles.GHR_NOTIFICATION_RECIPIENTS );
         filter.addGenericHumanRole( IAeGetTasksFilterGenericHumanRoles.GHR_ACTUAL_OWNER );
      }
      if (AeUtil.notNullOrEmpty(aGetTasksParam.getWorkQueue()))
      {
         if (!aRoles.contains( aGetTasksParam.getWorkQueue() ) )
         {
            // FIXMEQ (wsht) should be illegal access fault
            throw new AeStorageException( AeMessages.getString("AePersistentTaskStorage.IllegalWorkQueueError")); //$NON-NLS-1$
         }
         filter.addWorkQueue( aGetTasksParam.getWorkQueue() );
      }
      else
      {
         filter.addWorkQueue( aRoles );
      }

      // start index
      filter.setListStart(aGetTasksParam.getTaskIndexOffset());
      // number of tasks to return
      if (aGetTasksParam.getMaxTasks() > 0)
      {
         filter.setMaxReturn( aGetTasksParam.getMaxTasks() );
      }

      try
      {
         IAeWhereCondition whereCondition = createWhereCondition(aGetTasksParam);
         filter.setWhereClause(whereCondition);
      }
      catch(AeException e)
      {
         throw new AeStorageException(e);
      }

      // order by
      filter.setOrderByFields( createOrderByClause(aGetTasksParam) );
      return filter;
   }

   /**
    * Creates the where condition from the list of statuses, the simple
    * getMyTasks where clause, and the getMyTasks formatted create-on
    * clause.
    *
    * @param aGetTasksParam
    */
   protected IAeWhereCondition createWhereCondition(AeGetTasksParam aGetTasksParam) throws AeException
   {
      List statusList = new ArrayList();
      statusList.addAll( aGetTasksParam.getStatusSet() );
      AeCompositeWhereCondition whereCondition = new AeCompositeWhereCondition();

      // Status conditions.
      if (AeUtil.notNullOrEmpty(statusList))
      {
         AeCompositeWhereCondition statusCondition = new AeCompositeWhereCondition();
         statusCondition.setOperator(IAeTaskFilterConstants.OPERATOR_OR);

         for (Iterator iter = statusList.iterator(); iter.hasNext(); )
         {
            String status = (String) iter.next();
            statusCondition.addCondition(new AeWhereCondition(IAeTaskFilterConstants.COLUMN_STATUS, IAeTaskFilterConstants.OPERATOR_EQ, status));
         }

         whereCondition.addCondition(statusCondition);
      }

      // where by
      String whereClause = aGetTasksParam.getWhereClause();
      if (AeUtil.notNullOrEmpty(whereClause))
      {
         Matcher matcher = sConditionClausePattern.matcher(whereClause);
         if (matcher.matches())
         {
            String colName = matcher.group(1);
            String operator = matcher.group(2);
            String value = matcher.group(3);
            whereCondition.addCondition(new AeWhereCondition(colName, operator, value));
         }
      }

      // filter by task type if "ALL" is not selected.
      if (!"ALL".equals(aGetTasksParam.getTaskType())) //$NON-NLS-1$
      {
         String taskType = AeTaskTypes.getTaskTypeFromApiListingType(aGetTasksParam.getTaskType());
         whereCondition.addCondition(new AeWhereCondition(IAeTaskFilterConstants.COLUMN_TASK_TYPE, IAeTaskFilterConstants.OPERATOR_EQ, taskType) );
      }

      // created on filter
      String createdOn = aGetTasksParam.getCreateOnClauseAsString();
      if (AeUtil.notNullOrEmpty(createdOn))
      {
         Matcher matcher = sConditionClausePattern.matcher(createdOn);
         if (matcher.matches())
         {
            String colName = matcher.group(1);
            String operator = matcher.group(2);
            String value = matcher.group(3);

            if (!IAeTaskFilterConstants.COLUMN_CREATED_ON.equals(colName))
            {
               throw new AeException(AeMessages.getString("AePersistentTaskStorage.IllegalCreatedOnColumn")); //$NON-NLS-1$
            }
            if (IAeTaskFilterConstants.OPERATOR_EQ.equals(value) || IAeTaskFilterConstants.OPERATOR_NEQ.equals(value))
            {
               // fixme (PJ) (wsht) should be illegal argument fault
               throw new AeException(AeMessages.getString("AePersistentTaskStorage.IllegalCreatedOnOperator")); //$NON-NLS-1$
            }

            whereCondition.addCondition(new AeWhereCondition(colName, operator, value));
         }
      }

      // search by
      if ( AeUtil.notNullOrEmpty( aGetTasksParam.getSearchBy() ) )
      {
         whereCondition.addCondition(new AeWhereCondition(IAeTaskFilterConstants.COLUMN_PRIMARY_SEARCH_BY, IAeTaskFilterConstants.OPERATOR_LIKE, aGetTasksParam.getSearchBy().trim()));
      }
      return whereCondition;
   }

   /**
    * Creates a list of order by clause filters.
    * @param aGetTasksParam
    * @return list of <code>AeTaskFilterOrderByClause</code>.
    * @throws AeException
    */
   protected List createOrderByClause(AeGetTasksParam aGetTasksParam)
   {
      if (AeUtil.isNullOrEmpty( aGetTasksParam.getOrderBys() ))
      {
         return Collections.EMPTY_LIST;
      }

      List orderBysClauses = new ArrayList();
      for (Iterator iter = aGetTasksParam.getOrderBys().iterator(); iter.hasNext(); )
      {
         // orderByString: e.g. Priority, -Owner  = priority ascending, then owner, descending.
         String orderByString = (String) iter.next();
         boolean descending = orderByString.startsWith("-"); //$NON-NLS-1$
         if (descending)
         {
            orderByString = orderByString.substring(1);
         }
         String column = (String) sSortFieldIdToColumnMap.get(orderByString);
         orderBysClauses.add(new AeTaskFilterOrderByClause(column, descending ? IAeTaskFilterConstants.DIRECTION_DESC : IAeTaskFilterConstants.DIRECTION_ASC));
      }
      return orderBysClauses;
   }


}
