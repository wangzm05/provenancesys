//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/bean/AeTaskFilter.java,v 1.7 2008/03/19 19:29:17 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.web.bean;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.activebpel.rt.ht.api.IAeGetTasksFilterStates;
import org.activebpel.rt.ht.api.IAeGetTasksFilterTaskType;


/**
 * Bean that represents the taskService wsdl tTaskFilter schema type.
 */
public class AeTaskFilter extends AeTaskRequestBase
{
   /** List of statuses that indicate that the task is in a final state.*/
   public static final Set FINAL_STATUS_SET = new HashSet();
   static
   {
      FINAL_STATUS_SET.add(IAeGetTasksFilterStates.STATE_COMPLETED);
      FINAL_STATUS_SET.add(IAeGetTasksFilterStates.STATE_OBSOLETE);
      FINAL_STATUS_SET.add(IAeGetTasksFilterStates.STATE_ERROR);
      FINAL_STATUS_SET.add(IAeGetTasksFilterStates.STATE_FAILED);
      FINAL_STATUS_SET.add(IAeGetTasksFilterStates.STATE_EXITED);
   }

   /** Status for which tasks are considered open.*/
   public static final Set OPEN_STATUS_SET = new HashSet();
   static
   {
      OPEN_STATUS_SET.add(IAeGetTasksFilterStates.STATE_READY);
      OPEN_STATUS_SET.add(IAeGetTasksFilterStates.STATE_RESERVED);
      OPEN_STATUS_SET.add(IAeGetTasksFilterStates.STATE_IN_PROGRESS);
      OPEN_STATUS_SET.add(IAeGetTasksFilterStates.STATE_SUSPENDED);
   }

   /** Search start index. */
   private int mStartIndex;
   /** Generic human role. */
   private String mRole;
   /** Set of status. */
   private Set mStatusSet;
   /** Page size. */
   private int mPageSize;
   /** List of columns to order by. Prefix col with a "-" to indicate descending order */
   private Set mOrderBy = new LinkedHashSet();
   /** Task Type */
   private String mTaskType = IAeGetTasksFilterTaskType.TASKTYPE_TASKS;
   /** Search by string */
   private String mSearchBy;

   /**
    * Default ctor.
    */
   public AeTaskFilter()
   {
   }

   /**
    * @return the taskType
    */
   public String getTaskType()
   {
      return mTaskType;
   }

   /**
    * @param aTaskType the taskType to set
    */
   public void setTaskType(String aTaskType)
   {
      mTaskType = aTaskType;
   }

   /**
    * @return the startIndex
    */
   public int getStartIndex()
   {
      return mStartIndex;
   }

   /**
    * @param aStartIndex the startIndex to set
    */
   public void setStartIndex(int aStartIndex)
   {
      mStartIndex = aStartIndex;
   }

   /**
    * Sets the given generic human role to the role set.
    * @param aRole
    */
   public void setRole(String aRole)
   {
      mRole = aRole;
   }

   /**
    * @return the roles
    */
   public String getRole()
   {
      return mRole;
   }

   /**
    * @return the roles
    */
   public Set getStatusSet()
   {
      if (mStatusSet == null)
      {
         mStatusSet = new LinkedHashSet();
      }
      return mStatusSet;
   }

   /**
    * Clears the currrent status.
    */
   public void clearStatusSet()
   {
      if (mStatusSet != null)
      {
         mStatusSet.clear();
      }
   }

   /**
    * Sets a single status to the filter. This method clears current collection
    * of statuses before setting the new one.
    * @param aStatus the state to set
    */
   public void setStatus(String aStatus)
   {
      clearStatusSet();
      getStatusSet().add(aStatus);
   }

   /**
    * Adds a status to the filter.
    * @param aStatus the state to set
    */
   public void addStatus(String aStatus)
   {
      getStatusSet().add(aStatus);
   }

   /**
    * Adds a status to the filter.
    * @param aStatusCollection the state to set
    */
   public void addStatus(Collection aStatusCollection)
   {
      getStatusSet().addAll(aStatusCollection);
   }

   /**
    * Returns the page size.
    * @return page size.
    */
   public int getPageSize()
   {
      return mPageSize;
   }

   /**
    * Sets the page size.
    * @param aPageSize
    */
   public void setPageSize(int aPageSize)
   {
      mPageSize = aPageSize;
   }

   /**
    * Adds the field for ordering.
    * @param aFieldName - name of the field
    * @param aAscending - true for ascending, false for descending
    * @throws IllegalStateException if field is added twice with conflicting flags
    */
   public void addOrderBy(String aFieldName, boolean aAscending)
   {
      String orderByField = aAscending ? aFieldName : "-" + aFieldName; //$NON-NLS-1$
      String assertionCheck = aAscending ? "-" + aFieldName : aFieldName; //$NON-NLS-1$

      if (mOrderBy.contains(assertionCheck))
      {
         throw new IllegalStateException("Field is already added with the opposite sort order:" + aFieldName); //$NON-NLS-1$
      }

      mOrderBy.add(orderByField);
   }

   /**
    * Gets a read-only collection of the order bys
    */
   public Set getOrderBys()
   {
      return Collections.unmodifiableSet(mOrderBy);
   }

   /**
    * @return the searchBy
    */
   public String getSearchBy()
   {
      return mSearchBy;
   }

   /**
    * @param aSearchBy the searchBy to set
    */
   public void setSearchBy(String aSearchBy)
   {
      mSearchBy = aSearchBy;
   }
}
