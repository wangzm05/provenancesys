// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/filter/AeTaskFilter.java,v 1.1 2008/02/02 19:11:35 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.server.storage.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.activebpel.rt.bpel.impl.list.AeListingFilter;

/**
 * Specifies a filter object used in the selection of tasks.  Tasks can also
 * be returned in a sorted order using the field ids provided as constants.  When
 * field id is preceded by a hyphen (-) it is sorted in descending order.
 *
 * Here are some of the rules for task filtering:
 *
 * Principal: is used for ACL searching as well as specific owner searching when
 *     not in admin mode.
 * Roles: is used only for ACL searching
 * Select: the list of columns to return in the result
 * Where Clause: the list of where conditions - each condition is a column name,
 *     operator, and value
 * Order By: the list of columns to order the result by - each order by clause
 *     consists of a column name and direction (ASC or DESC)
 * Work Queues: the work queues to filter the query by
 * Generic Human Roles: the list of generic human roles to consider - this allows
 *     the filter to target potential owner, admin, etc
 */
public class AeTaskFilter extends AeListingFilter
{
   /** The principal. */
   private String mPrincipal;
   /** The list of columns to select/return. */
   private List mSelectColumns = new ArrayList();
   /** The where clauses. */
   private IAeWhereCondition mWhereClause;
   /** The order by fields. */
   private List mOrderByFields = new ArrayList();
   /** The generic human roles used to bound the query. */
   private List mGenericHumanRoles = new ArrayList();
   /** The ws-ht groups (work queues). */
   private List mWorkQueues = new ArrayList();

   /**
    * C'tor.
    *
    * @param aPrincipal
    */
   public AeTaskFilter(String aPrincipal)
   {
      setPrincipal(aPrincipal);
   }

   /**
    * @return Returns the principal.
    */
   public String getPrincipal()
   {
      return mPrincipal;
   }

   /**
    * @param aPrincipal the principal to set
    */
   public void setPrincipal(String aPrincipal)
   {
      mPrincipal = aPrincipal;
   }

   /**
    * Returns the order by columns.  Returns a list of AeTaskFilterOrderByClause
    * instances.
    *
    * @return the orderBy
    */
   public List getOrderByFields()
   {
      return mOrderByFields;
   }

   /**
    * Sets the order by columns.
    *
    * @param aOrderBy the orderBy to set
    */
   public void setOrderByFields(List aOrderBy)
   {
      mOrderByFields = aOrderBy;
   }

   /**
    * Adds the order by clause.s
    *
    * @param aClause
    */
   public void addOrderBy(AeTaskFilterOrderByClause aClause)
   {
      getOrderByFields().add(aClause);
   }

   /**
    * Returns true if the filter has an order, false otherwise.  If not supplied
    * a default ordering may be applied.
    */
   public boolean hasOrderBy()
   {
      return getOrderByFields().size() > 0;
   }

   /**
    * @return Returns the selectColumns.
    */
   public List getSelectColumns()
   {
      return mSelectColumns;
   }

   /**
    * Returns true if at least one column exists in the select
    * list.
    */
   public boolean hasSelectColumns()
   {
      return !getSelectColumns().isEmpty();
   }

   /**
    * @param aSelectColumns the selectColumns to set
    */
   protected void setSelectColumns(List aSelectColumns)
   {
      mSelectColumns = aSelectColumns;
   }

   /**
    * Returns true if the filter contains at least one where condition.
    */
   public boolean hasWhereClause()
   {
      return getWhereClause() != null;
   }

   /**
    * @return Returns the whereClause.
    */
   public IAeWhereCondition getWhereClause()
   {
      return mWhereClause;
   }

   /**
    * @param aWhereClause the whereClause to set
    */
   public void setWhereClause(IAeWhereCondition aWhereClause)
   {
      mWhereClause = aWhereClause;
   }

   /**
    * @return Returns the genericHumanRoles.
    */
   public List getGenericHumanRoles()
   {
      return mGenericHumanRoles;
   }

   /**
    * Adds a generic human role to the list.
    *
    * @param aGenericHumanRole
    */
   public void addGenericHumanRole(String aGenericHumanRole)
   {
      getGenericHumanRoles().add(aGenericHumanRole);
   }

   /**
    * @param aGenericHumanRoles the genericHumanRoles to set
    */
   public void setGenericHumanRoles(List aGenericHumanRoles)
   {
      mGenericHumanRoles = aGenericHumanRoles;
   }

   /**
    * @return Returns the workQueues.
    */
   public List getWorkQueues()
   {
      return mWorkQueues;
   }

   /**
    * Adds a work queue to the list of queues to search.
    *
    * @param aWorkQueue
    */
   public void addWorkQueue(String aWorkQueue)
   {
      getWorkQueues().add(aWorkQueue);
   }
   
   /**
    * Adds a collection of roles to the work queue list.
    * @param aWorkQueueCollection
    */
   public void addWorkQueue(Collection aWorkQueueCollection)
   {
      getWorkQueues().addAll(aWorkQueueCollection);
   }

}
