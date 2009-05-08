//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeTasksDef.java,v 1.8 2008/03/24 18:44:16 EWittmann Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeCloneUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * Impl. for 'tasks' element Def.
 */
public class AeTasksDef extends AeHtBaseDef implements IAeTaskDefParent
{
   /** List of 'task' element Def objects associated with this 'tasks' element Def object */
   protected List mTaskList = new ArrayList();
   
   /**
    * Returns the task with the given name or null if not found
    * @param aTaskName
    */
   public AeTaskDef getTask(String aTaskName)
   {
      for( Iterator itr = getTaskDefs(); itr.hasNext();)
      {
         AeTaskDef tDef = (AeTaskDef)itr.next();
         if(aTaskName.equals(tDef.getName()))
         {
            return tDef;
         }
      }
      return null;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeTaskDefParent#addTask(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void addTask(AeTaskDef task)
   {
      addTask(getCount(), task);
   }
   
   /**
    * Adds the task at the specified index
    * @param aIndex
    * @param aTask
    */
   public void addTask(int aIndex, AeTaskDef aTask)
   {
      mTaskList.add(aIndex, aTask);
      assignParent(aTask);
   }

   /**
    * Removes the task from the collection of children
    * @param aChild
    */
   public void removeTask(AeTaskDef aChild)
   {
      if (mTaskList.remove(aChild))
      {
         aChild.setParentXmlDef(null);
      }
   }
   
   public int getCount()
   {
      return mTaskList.size();
   }
   
   /**
    * @see org.activebpel.rt.ht.def.IAeTaskDefParent#getTaskDefs()
    */
   public Iterator getTaskDefs()
   {
      return mTaskList.iterator();
   }

   /**
    * @see org.activebpel.rt.ht.def.AeHtBaseDef#accept(org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor)
    */
   public void accept(IAeHtDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      AeTasksDef def = (AeTasksDef)super.clone();
      def.mTaskList = new ArrayList();
      
      try
      {
         if (mTaskList.size() > 0)
            def.mTaskList = AeCloneUtil.deepClone(mTaskList); 
      }
      catch (CloneNotSupportedException ex)
      {
         // This should never happen in a closed system
         AeException.logError(ex);
         throw new InternalError(ex.getLocalizedMessage());
      }
      
      return def;
   }
    
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeTasksDef))
         return false;
      
      AeTasksDef otherDef = (AeTasksDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.mTaskList, mTaskList);
      
      return same;
   }
}