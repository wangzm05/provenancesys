//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/AeGetTasksParam.java,v 1.4 2008/03/19 19:35:38 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;

/**
 * Request class for htd api:getMyTaskAbstracts and api:getMyTasks
 * operations.
 */
public class AeGetTasksParam
{   
   /** Task type.*/
   private String mTaskType;
   /** Generic human role such as INITIATOR. */
   private String mGenericHumanRole;
   /** Work queue */
   private String mWorkQueue;
   /** Collection of applicable tasks states. */
   private Set mStatusSet;
   /** Single column where clause. */
   private String mWhereClause;
   /** Creation time where clause. */
   private AeSchemaDateTime mCreateOnClause;
   /** Maximum number of tasks to return */
   private int mMaxTasks;
   /** Listing start (zero based) index. */
   private int mTaskIndexOffset;
   /** List of columns to order by. Prefix col with a "-" to indicate descending order */
   private Set mOrderBy = new LinkedHashSet();
   /** Search by field. */
   private String mSearchBy;
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
    * @return the genericHumanRole
    */
   public String getGenericHumanRole()
   {
      return mGenericHumanRole;
   }
   /**
    * @param aGenericHumanRole the genericHumanRole to set
    */
   public void setGenericHumanRole(String aGenericHumanRole)
   {
      mGenericHumanRole = aGenericHumanRole;
   }
   /**
    * @return the workQueue
    */
   public String getWorkQueue()
   {
      return mWorkQueue;
   }
   /**
    * @param aWorkQueue the workQueue to set
    */
   public void setWorkQueue(String aWorkQueue)
   {
      mWorkQueue = aWorkQueue;
   }

   /**
    * @return the status set
    */
   public Set getStatusSet()
   {
      if (mStatusSet == null)
      {
         mStatusSet = new HashSet();
      }
      return mStatusSet;
   }

   /**
    * Adds a task status criteria to the status list.
    * @param aStatus task status.
    */
   public void addStatus(String aStatus)
   {
      if (AeUtil.isNullOrEmpty(aStatus))
      {
         return;
      }
      getStatusSet().add(aStatus);
   }

   /**
    * Add a collection of status values to filter by
    * @param aStatusCollection
    */
   public void addStatus(Collection aStatusCollection)
   {
      getStatusSet().addAll(aStatusCollection);
   }

   /**
    * @return the whereClause
    */
   public String getWhereClause()
   {
      return mWhereClause;
   }

   /**
    * @param aWhereClause the whereClause to set
    */
   public void setWhereClause(String aWhereClause)
   {
      mWhereClause = aWhereClause;
   }

   /**
    * @return the createOnClause
    */
   public AeSchemaDateTime getCreateOnClause()
   {
      return mCreateOnClause;
   }

   /**
    * @return the createOnClause
    */
   public String getCreateOnClauseAsString()
   {
      if (mCreateOnClause != null)
      {
         return mCreateOnClause.toString();
      }
      else
      {
         return null;
      }
   }

   /**
    * @param aCreateOnClause the createOnClause to set
    */
   public void setCreateOnClause(String aCreateOnClause)
   {
      setCreateOnClause( new AeSchemaDateTime(aCreateOnClause) );
   }

   /**
    * @param aCreateOnClause the createOnClause to set
    */
   public void setCreateOnClause(AeSchemaDateTime aCreateOnClause)
   {
      mCreateOnClause = aCreateOnClause;
   }

   /**
    * @return the maxTasks
    */
   public int getMaxTasks()
   {
      return mMaxTasks;
   }

   /**
    * @param aMaxTasks the maxTasks to set
    */
   public void setMaxTasks(int aMaxTasks)
   {
      mMaxTasks = aMaxTasks;
   }

   /**
    * @return the startIndex
    */
   public int getTaskIndexOffset()
   {
      return mTaskIndexOffset;
   }

   /**
    * @param aStartIndex the startIndex to set
    */
   public void setTaskIndexOffset(int aStartIndex)
   {
      mTaskIndexOffset = aStartIndex;
   }
   
   /**
    * Adds a list of order by field names. The field name is prefixed 
    * with a "-" to indicate descending order 
    * @param aOrderByFields
    */
   public void setOrderBys(Set aOrderByFields)
   {
      if (AeUtil.notNullOrEmpty(aOrderByFields))
      {
         mOrderBy.addAll(aOrderByFields);
      }
   }
   
   /**
    * Gets a read-only collection of the order bys
    */
   public Collection getOrderBys()
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
