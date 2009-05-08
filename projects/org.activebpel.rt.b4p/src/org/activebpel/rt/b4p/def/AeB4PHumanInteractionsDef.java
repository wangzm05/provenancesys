//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeB4PHumanInteractionsDef.java,v 1.6 2008/03/14 20:46:52 EWittmann Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def;

import org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationsDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTasksDef;
import org.activebpel.rt.ht.def.IAeLogicalPeopleGroupsDefParent;
import org.activebpel.rt.ht.def.IAeNotificationsDefParent;
import org.activebpel.rt.ht.def.IAeTasksDefParent;
import org.activebpel.rt.util.AeUtil;

/**
 * Impl. for BPEL4People 'humanInteractions' element Def
 */
public class AeB4PHumanInteractionsDef extends AeB4PBaseDef implements IAeLogicalPeopleGroupsDefParent, IAeTasksDefParent, IAeNotificationsDefParent, IAeB4PContextParent
{
   /** 'logicalPeopleGroups' element */
   private AeLogicalPeopleGroupsDef mLogicalPeopleGroups;
   /** 'tasks' element */
   private AeTasksDef mTasks;
   /** 'notifications' element */
   private AeNotificationsDef mNotifications;
   /** The context which provides ability to transition to enclosing containers */
   private IAeB4PContext mContext;

   /**
    * @return the 'logicalPeopleGroups' element Def object
    */
   public AeLogicalPeopleGroupsDef getLogicalPeopleGroupsDef()
   {
      return mLogicalPeopleGroups;
   }
   
   /**
    * Returns the enclosing Human Interactions definition or null if none defined.
    */
   public AeB4PHumanInteractionsDef getEnclosingHumanInteractionsDef()
   {
      return (getContext() != null ? getContext().getEnclosingHumanInteractionsDef() : null);
   }

   /**
    * @param aLogicalPeopleGroups the 'logicalPeopleGroups' element Def object to set.
    */
   public void setLogicalPeopleGroupsDef(AeLogicalPeopleGroupsDef aLogicalPeopleGroups)
   {
      mLogicalPeopleGroups = aLogicalPeopleGroups;
      assignParent(aLogicalPeopleGroups);
   }

   /**
    * @return the 'tasks' element Def object.
    */
   public AeTasksDef getTasksDef()
   {
      return mTasks;
   }

   /**
    * @param aTasks the 'tasks' element Def object to set.
    */
   public void setTasksDef(AeTasksDef aTasks)
   {
      mTasks = aTasks;
      assignParent(aTasks);
   }

   /**
    * @return the 'notifications' element Def object.
    */
   public AeNotificationsDef getNotificationsDef()
   {
      return mNotifications;
   }

   /**
    * @param aNotifications the 'notifications' element Def object to set.
    */
   public void setNotificationsDef(AeNotificationsDef aNotifications)
   {
      mNotifications = aNotifications;
      assignParent(aNotifications);
   }
   
   /**
    * Getter for the logical people group
    * @param aName
    */
   public AeLogicalPeopleGroupDef getLogicalPeopleGroupDef(String aName)
   {
      if(getLogicalPeopleGroupsDef() == null)
         return null;
      return getLogicalPeopleGroupsDef().getLogicalPeopleGroup(aName);
   }
   
   /**
    * Convenience method to lookup a notification Def 
    * @param aName
    */
   public AeNotificationDef getNotificationDef(String aName)
   {
      if (getNotificationsDef() == null)
      {
         return null;
      }
      return getNotificationsDef().getNotification(aName);
   }
   
   /**
    * Convenience method to lookup a task Def 
    * @param aName
    */
   public AeTaskDef getTaskDef(String aName)
   {
      if (getTasksDef() == null)
      {
         return null;
      }
      return getTasksDef().getTask(aName);
   }

   /**
    * @see org.activebpel.rt.b4p.def.AeB4PBaseDef#accept(org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor)
    */
   public void accept(IAeB4PDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
   
   /**
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      AeB4PHumanInteractionsDef def = (AeB4PHumanInteractionsDef)super.clone();
      if (getLogicalPeopleGroupsDef() != null)
         def.setLogicalPeopleGroupsDef((AeLogicalPeopleGroupsDef)getLogicalPeopleGroupsDef().clone());
      if (getTasksDef() != null)
         def.setTasksDef((AeTasksDef)getTasksDef().clone());
      if (getNotificationsDef() != null)
         def.setNotificationsDef((AeNotificationsDef)getNotificationsDef().clone());

      return def;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (!(aOther instanceof AeB4PHumanInteractionsDef))
         return false;

      AeB4PHumanInteractionsDef otherDef = (AeB4PHumanInteractionsDef)aOther;
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(otherDef.getLogicalPeopleGroupsDef(), getLogicalPeopleGroupsDef());
      same &= AeUtil.compareObjects(otherDef.getTasksDef(), getTasksDef());
      same &= AeUtil.compareObjects(otherDef.getNotificationsDef(), getNotificationsDef());

      return same;
   }

   /**
    * Sets the context which provides ability to transition to enclosing containers
    * @param aContext
    */
   public void setContext(IAeB4PContext aContext)
   {
      mContext = aContext;
   }
   
   /**
    * Getter for the context
    */
   public IAeB4PContext getContext()
   {
      return mContext;
   }
}
