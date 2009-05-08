//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/sql/AeSQLTaskFilter.java,v 1.6 2008/03/19 19:30:59 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2005 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.b4p.server.storage.AeGenericHumanRoles;
import org.activebpel.rt.b4p.server.storage.AeTaskACLEntry;
import org.activebpel.rt.b4p.server.storage.IAeTaskFilterConstants;
import org.activebpel.rt.b4p.server.storage.filter.AeTaskFilter;
import org.activebpel.rt.b4p.server.storage.filter.AeTaskFilterOrderByClause;
import org.activebpel.rt.b4p.server.storage.filter.IAeWhereCondition;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLFilter;
import org.activebpel.rt.ht.api.IAeGetTasksFilterGenericHumanRoles;
import org.activebpel.rt.util.AeUtil;

/**
 * Helper class to create a SQL statement that queries the database with the
 * conditions defined by an <code>AeTaskFilter</code>.
 */
public class AeSQLTaskFilter extends AeSQLFilter
{
   public static Map sColumnFieldMap = new HashMap();
   public static Map sOperatorMap = new HashMap();
   static
   {
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_TARGET_NS, IAeTaskColumns.TARGET_NAMESPACE);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_MODIFIED_ON, IAeTaskColumns.LAST_MODIFIED_TIME_MILLIS);

      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_ACTIVATION_TIME, IAeTaskColumns.CREATION_TIME_MILLIS);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_ATTACHMENT_NAME, null);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_ATTACHMENT_TYPE, null);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_CREATED_ON, IAeTaskColumns.CREATION_TIME_MILLIS);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_COMPLETE_BY, null);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_ESCALATED, null);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_EXPIRATION_TIME, IAeTaskColumns.EXPIRATION_DATE_MILLIS);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_FAULT_MESSAGE, null);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_GENERIC_HUMAN_ROLE, null);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_GROUP, null);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_ID, null);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_INPUT_MESSAGE, null);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_NAME, null);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_OUTPUT_MESSAGE, null);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_OWNER, IAeTaskColumns.OWNER);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_PRES_NAME, IAeTaskColumns.PRESENTATION_NAME);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_PRES_SUBJECT, IAeTaskColumns.SUMMARY);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_PRIMARY_SEARCH_BY, IAeTaskColumns.PRIMARY_SEARCH_BY);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_PRIORITY, IAeTaskColumns.PRIORITY);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_RENDERING_METH_NAME, null);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_SKIPABLE, null);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_START_BY, null);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_STATUS, IAeTaskColumns.STATE);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_TASK_TYPE, IAeTaskColumns.TASK_TYPE);
      sColumnFieldMap.put(IAeTaskFilterConstants.COLUMN_USER_ID, null);

      sOperatorMap.put(IAeTaskFilterConstants.OPERATOR_EQ, "="); //$NON-NLS-1$
      sOperatorMap.put(IAeTaskFilterConstants.OPERATOR_NEQ, "!="); //$NON-NLS-1$
      sOperatorMap.put(IAeTaskFilterConstants.OPERATOR_GT, ">"); //$NON-NLS-1$
      sOperatorMap.put(IAeTaskFilterConstants.OPERATOR_GTE, ">="); //$NON-NLS-1$
      sOperatorMap.put(IAeTaskFilterConstants.OPERATOR_LT, "<"); //$NON-NLS-1$
      sOperatorMap.put(IAeTaskFilterConstants.OPERATOR_LTE, "<="); //$NON-NLS-1$
      sOperatorMap.put(IAeTaskFilterConstants.OPERATOR_AND, "AND"); //$NON-NLS-1$
      sOperatorMap.put(IAeTaskFilterConstants.OPERATOR_OR, "OR"); //$NON-NLS-1$
      sOperatorMap.put(IAeTaskFilterConstants.OPERATOR_LIKE, "LIKE"); //$NON-NLS-1$
   }

   /**
    * Creates an ACL SQL clause given the type (principal/role) and
    * exclude flag.
    *
    * @param aNames
    * @param aExcludeFlag
    * @param aType
    * @param aGenericHumanRoles
    */
   private static String createACLClause(List aNames, boolean aExcludeFlag, int aType,
         List aGenericHumanRoles)
   {
      StringBuffer buffer = new StringBuffer();

      synchronized (buffer)
      {
         List list = new ArrayList();
         for (Iterator iter = aNames.iterator(); iter.hasNext(); iter.next())
            list.add("?"); //$NON-NLS-1$
         buffer.append("(AeB4PTaskACL.Name IN ("); //$NON-NLS-1$
         buffer.append(AeUtil.joinToStringObjects(list, ", ")); //$NON-NLS-1$
         buffer.append(") AND "); //$NON-NLS-1$

         buffer.append("AeB4PTaskACL.ExcludeFlag = "); //$NON-NLS-1$
         buffer.append(new Integer(aExcludeFlag ? 1 : 0));
         buffer.append(" AND "); //$NON-NLS-1$

         buffer.append("AeB4PTaskACL.Type = "); //$NON-NLS-1$
         buffer.append(aType);

         if (!aGenericHumanRoles.isEmpty())
         {
            buffer.append(" AND "); //$NON-NLS-1$
            buffer.append("AeB4PTaskACL.GenericHumanRole IN ("); //$NON-NLS-1$
            buffer.append(AeUtil.joinToStringObjects(aGenericHumanRoles, ", ")); //$NON-NLS-1$
            buffer.append(")"); //$NON-NLS-1$
         }

         buffer.append(")"); //$NON-NLS-1$

         return buffer.toString();
      }
   }

   /**
    * Constructor.
    *
    * @param aFilter
    * @param aConfig
    * @throws AeStorageException
    */
   public AeSQLTaskFilter(AeTaskFilter aFilter, AeSQLConfig aConfig) throws AeStorageException
   {
      super(aFilter, aConfig, AeSQLTaskStorageProvider.SQLSTATEMENT_PREFIX);

      // If filter contains an order by then apply it otherwise use default
      if(aFilter.hasOrderBy())
         buildOrderBy(aFilter);
      else
         setOrderBy(AeSQLTaskStorageProvider.SQL_ORDER_BY_PRIORITY_CTIME);

      setSelectClause(getSQLStatement(IAeTaskSQLKeys.GET_TASKS));
   }

   /**
    * Builds an orderby clause based on the task filter information.
    * @param aFilter
    */
   protected void buildOrderBy(AeTaskFilter aFilter)
   {
      // just in case no order by supplied then do a short return
      if(! aFilter.hasOrderBy())
         return;

      // loop through the order by fields and create the clause
      StringBuffer orderBy = new StringBuffer(" ORDER BY "); //$NON-NLS-1$
      synchronized (orderBy)
      {
         boolean first = true;
         for (Iterator iter = aFilter.getOrderByFields().iterator(); iter.hasNext();)
         {
            AeTaskFilterOrderByClause clause = (AeTaskFilterOrderByClause) iter.next();
            String dbCol = (String) sColumnFieldMap.get(clause.getColumn());

            if (dbCol != null)
            {
               if (first)
                  first = false;
               else
                  orderBy.append(", "); //$NON-NLS-1$

               orderBy.append(dbCol);
               orderBy.append(" "); //$NON-NLS-1$
               orderBy.append(clause.getDirection());
            }
            else
            {
               throw new RuntimeException("Column not supported in order-by: " + clause.getColumn()); //$NON-NLS-1$
            }
         }
      }
      // set the order by for the SQL statement
      setOrderBy(orderBy.toString());
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLFilter#processFilter()
    */
   protected void processFilter() throws AeStorageException
   {
      clearWhereClause();

      if (getFilter() != null)
      {
         AeTaskFilter filter = (AeTaskFilter) getFilter();

         appendWhereClauses(filter);
      }

      // Include ACL
      appendACLClause(true);
      // Exclude ACL
      appendACLClause(false);
   }

   /**
    * Appends where clauses extracted from the task filter.
    *
    * @param aFilter
    */
   protected void appendWhereClauses(AeTaskFilter aFilter)
   {
      IAeWhereCondition whereClause = aFilter.getWhereClause();
      if (whereClause != null)
      {
         AeSQLWhereClauseBuildingVisitor visitor = new AeSQLWhereClauseBuildingVisitor();
         whereClause.accept(visitor);
         String clause = visitor.getClause();
         if (clause != null)
            appendCondition(clause, visitor.getParams().toArray());
      }
   }

   /**
    * Appends the ACL clause.
    *
    * @param aInclude
    * @throws AeStorageException
    */
   protected void appendACLClause(boolean aInclude) throws AeStorageException
   {
      AeTaskFilter filter = (AeTaskFilter) getFilter();
      List genHumanRoles = null;
      if (aInclude)
      {
         genHumanRoles = filter.getGenericHumanRoles();
      }
      else
      {
         genHumanRoles = Collections.singletonList(IAeGetTasksFilterGenericHumanRoles.GHR_EXCLUDED_OWNERS);
      }
      appendACLClause(aInclude, genHumanRoles);
   }

   /**
    * Appends the ACL clause.
    *
    * @param aInclude
    * @param aGenHumanRoles aGenericHumanRoles to include
    * @throws AeStorageException
    */
   protected void appendACLClause(boolean aInclude, List aGenHumanRoles) throws AeStorageException
   {
      AeTaskFilter filter = (AeTaskFilter) getFilter();
      String principal = filter.getPrincipal();
      List workQueues = filter.getWorkQueues();

      boolean hasPrincipal = AeUtil.notNullOrEmpty(principal);
      boolean hasWorkQueues = !workQueues.isEmpty();
      boolean hasACLCriteria = hasPrincipal || hasWorkQueues;

      if (!hasACLCriteria)
         return;

      List genericHumanRoleCodes = new ArrayList();
      for (Iterator iter = aGenHumanRoles.iterator(); iter.hasNext(); )
      {
         String genHumanRole = (String) iter.next();
         int genHumanRoleCode = AeGenericHumanRoles.getGenericHumanRoleCode(genHumanRole);
         genericHumanRoleCodes.add(new Integer(genHumanRoleCode));
      }

      List params = new ArrayList();
      List aclClauses = new ArrayList();
      if (hasPrincipal)
      {
         aclClauses.add(createACLClause(Collections.singletonList(principal), !aInclude, AeTaskACLEntry.USER,
               genericHumanRoleCodes));
         params.add(principal);
      }
      if (hasWorkQueues)
      {
         aclClauses.add(createACLClause(workQueues, !aInclude, AeTaskACLEntry.GROUP, genericHumanRoleCodes));
         params.addAll(workQueues);
      }

      // Create the ACL condition
      StringBuffer buffer = new StringBuffer();
      synchronized (buffer)
      {
         if (!aInclude)
            buffer.append("NOT "); //$NON-NLS-1$
         buffer.append("EXISTS ("); //$NON-NLS-1$
         buffer.append(getSQLStatement(IAeTaskSQLKeys.GET_TASKS_ACL_SUBQUERY));
         buffer.append(" ("); //$NON-NLS-1$
         buffer.append(AeUtil.joinToStringObjects(aclClauses, " OR ")); //$NON-NLS-1$
         buffer.append("))"); //$NON-NLS-1$
      }

      if (buffer.length() > 0)
         appendCondition(buffer.toString(), params.toArray());
   }
}
