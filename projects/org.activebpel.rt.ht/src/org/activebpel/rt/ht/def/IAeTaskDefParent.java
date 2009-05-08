//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAeTaskDefParent.java,v 1.2 2008/03/24 18:44:16 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import java.util.Iterator;

/**
 * Parent elements of the 'task' element must implement this interface
 */
public interface IAeTaskDefParent
{
   /**
    * Returns an iterator over the collection of task def children.
    */
   public Iterator getTaskDefs();

   /**
    * @param aTask - the task element to be set
    */
   public void addTask(AeTaskDef aTask);
}
