//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/IAeHtApiTaskList.java,v 1.2 2008/02/27 19:33:59 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api;

import java.util.List;

/**
 * Defines a list of task entries (ws-ht api:tTask types).
 */
public interface IAeHtApiTaskList
{

   /**
    * @return total number of tasks
    */
   public int size();   
   
   /**
    * Number of tasks that matched the request search (filter).
    * @return the totalTasks
    */
   public int getTotalTasks();   
   
   /**
    * @return the tasksList
    */
   public List getTasks();   
   
}
