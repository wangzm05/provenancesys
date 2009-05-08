//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeDelegationDef.java,v 1.7 2008/01/11 15:09:15 PJayanetti Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Models the ht:delegation element.
 */
public class AeDelegationDef extends AeHtBaseDef implements IAeFromDefParent
{
   /** Child ht:from def. */
   private AeFromDef mFrom;
   /** 'potentialDelegatees' attribute */
   private String mPotentialDelegatees;

   /**
    * @see org.activebpel.rt.ht.def.AeAbstractMixedTextDef#isDefined()
    */
   public boolean isDefined()
   {
      boolean isDefined = mFrom != null && mFrom.isDefined();
      isDefined |= AeUtil.notNullOrEmpty(mPotentialDelegatees);
      
      return isDefined;
   }
   
   /**
    * Gets the from child.
    */
   public AeFromDef getFrom()
   {
      return mFrom;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeFromDefParent#setFrom(org.activebpel.rt.ht.def.AeFromDef)
    */
   public void setFrom(AeFromDef aFrom)
   {
      mFrom = aFrom;
      assignParent(aFrom);
   }

   /**
    * Gets the potentialDelegatees attribute value.
    */
   public String getPotentialDelegatees()
   {
      return mPotentialDelegatees;
   }

   /**
    * Sets the potentialDelegatees attribute value.
    * 
    * @param aPotentialDelegatees
    */
   public void setPotentialDelegatees(String aPotentialDelegatees)
   {
      mPotentialDelegatees = aPotentialDelegatees;
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
      AeDelegationDef def = (AeDelegationDef)super.clone();
      if (getFrom() != null)
         def.setFrom((AeFromDef)getFrom().clone()); 
      
      return def;
   }
   
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeDelegationDef))
         return false;
      
      AeDelegationDef otherDef = (AeDelegationDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.getPotentialDelegatees(), getPotentialDelegatees());
      same &= AeUtil.compareObjects(otherDef.getFrom(), getFrom());
      
      return same;
   }
   
   /**
    * Overrides method to produce a user friendly representation of this object. 
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      if ("other".equals(getPotentialDelegatees())) //$NON-NLS-1$
         return (getFrom() != null ?  getFrom().toString() : ""); //$NON-NLS-1$
      else if (AeUtil.isNullOrEmpty(getPotentialDelegatees()))
         return AeMessages.getString("AeDelegationDef.none");  //$NON-NLS-1$
      else
         return getPotentialDelegatees();
   }
}