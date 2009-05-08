//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/AeHtApiTaskList.java,v 1.3 2008/02/27 19:33:59 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Container for mainting a list of <code>AeHtApiTask</code> objects.
 */
public class AeHtApiTaskList implements IAeHtApiTaskList
{
   /** Task list. */
   private List mTasks;
   /** Total matched. */
   private int mTotalTasks;

   /**
    * Ctor.
    * @param aTotalTasks total tasks that matched the request filter.
    */
   public AeHtApiTaskList(int aTotalTasks)
   {
      setTotalTasks(aTotalTasks);
   }

   /**
    * @return the totalTasks
    */
   public int getTotalTasks()
   {
      return mTotalTasks;
   }

   /**
    * @param aTotalTasks the totalTasks to set
    */
   protected void setTotalTasks(int aTotalTasks)
   {
      mTotalTasks = aTotalTasks;
   }
   
   /** 
    * @return returns internal task collection list.
    */
   protected List internalGetTaskList()
   {
      if (mTasks == null)
      {
         mTasks = new ArrayList();
      }
      return mTasks;
       
   }
   /**
    * Add a task to list.
    * @param aTask
    */
   public void add(AeHtApiTask aTask)
   {
      internalGetTaskList().add(aTask);
   }
   
   /**
    * Adds a collection of tasks.
    * @param aHtApiTaskCollection
    */
   public void add(Collection aHtApiTaskCollection)
   {
      internalGetTaskList().addAll(aHtApiTaskCollection);
   }

   /**
    * @return total number of tasks
    */
   public int size()
   {
      return getTasks().size();
   }

   /**
    * @return the tasksList
    */
   public List getTasks()
   {
      if (mTasks != null)
      {
         return Collections.unmodifiableList(mTasks);
      }
      else
      {
         return Collections.EMPTY_LIST;
      }
   }

}
