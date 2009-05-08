//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeEscalationDef.java,v 1.9 2008/01/23 16:25:53 JPerrotto Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import java.util.Collections;
import java.util.Iterator;

import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.IAeNamedDef;

/**
 * Implementation for 'escalation' element Def.
 */
public class AeEscalationDef extends AeHtBaseDef 
      implements IAeNotificationDefParent, IAeNamedDef, IAeConditionParentDef
{
   /** 'condition' element */
   private AeConditionDef mCondition;
   /** 'notification' element */
   private AeNotificationDef mNotification;
   /** 'localNotification' element */
   private AeLocalNotificationDef mLocalNotification;
   /** 'reassignment' element */
   private AeReassignmentDef mReassignment;
   /** 'name' attribute */
   private String mName;
   /** 'toParts' element */
   private AeToPartsDef mToParts;

   /**
    * @see org.activebpel.rt.ht.def.IAeConditionParentDef#getConditionDef()
    */
   public AeConditionDef getConditionDef()
   {
      return mCondition;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeConditionParentDef#setConditionDef(org.activebpel.rt.ht.def.AeConditionDef)
    */
   public void setConditionDef(AeConditionDef aCondition)
   {
      mCondition = aCondition;
      assignParent(aCondition);
   }

   /**
    * Sets the 'toParts' element Def.
    * @param aToParts
    */
   public void setToParts(AeToPartsDef aToParts)
   {
      mToParts = aToParts;
      assignParent(aToParts);
   }

   /**
    * @return the 'toParts' element Def/
    */
   public AeToPartsDef getToParts()
   {
      return mToParts;
   }

   /**
    * @return the 'notification' element Def object.
    */
   public AeNotificationDef getNotification()
   {
      return mNotification;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeNotificationDefParent#getNotificationDefs()
    */
   public Iterator getNotificationDefs()
   {
      return Collections.singletonList(getNotification()).iterator();
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeNotificationDefParent#addNotification(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void addNotification(AeNotificationDef aNotification)
   {
      mNotification = aNotification;
      assignParent(aNotification);
   }

   /**
    * @return 'localNotification' element Def object.
    */
   public AeLocalNotificationDef getLocalNotification()
   {
      return mLocalNotification;
   }

   /**
    * @param aLocalNotification 'localNotification' element Def object to set
    */
   public void setLocalNotification(AeLocalNotificationDef aLocalNotification)
   {
      mLocalNotification = aLocalNotification;
      assignParent(aLocalNotification);
   }

   /**
    * @return 'reassigment' element Def object.
    */
   public AeReassignmentDef getReassignment()
   {
      return mReassignment;
   }

   /**
    * @param aReassignment 'reassigment' element Def object to set.
    */
   public void setReassignment(AeReassignmentDef aReassignment)
   {
      mReassignment = aReassignment;
      assignParent(aReassignment);
   }

   /**
    * @return value of the 'name' attribute.
    */
   public String getName()
   {
      return mName;
   }

   /**
    * @param aName value of the 'name' attribute to set.
    */
   public void setName(String aName)
   {
      mName = aName;
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
      AeEscalationDef def = (AeEscalationDef)super.clone();
      
      if (getConditionDef() != null)
         def.setConditionDef((AeConditionDef) getConditionDef().clone());
      if (getNotification() != null)
         def.addNotification((AeNotificationDef)getNotification().clone());
      if (getLocalNotification() != null)
         def.setLocalNotification((AeLocalNotificationDef)getLocalNotification().clone());
      if (getReassignment() != null)
         def.setReassignment((AeReassignmentDef)getReassignment().clone());
      if (getToParts() != null )
         def.setToParts((AeToPartsDef)getToParts().clone());
         
      return def;
   }
   
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeEscalationDef))
         return false;
      
      AeEscalationDef otherDef = (AeEscalationDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.getName(), getName());
      same &= AeUtil.compareObjects(otherDef.getConditionDef(), getConditionDef());
      same &= AeUtil.compareObjects(otherDef.getNotification(), getNotification());
      same &= AeUtil.compareObjects(otherDef.getLocalNotification(), getLocalNotification());
      same &= AeUtil.compareObjects(otherDef.getReassignment(), getReassignment());
      same &= AeUtil.compareObjects(otherDef.mToParts, mToParts);
      
      return same;
   }

}