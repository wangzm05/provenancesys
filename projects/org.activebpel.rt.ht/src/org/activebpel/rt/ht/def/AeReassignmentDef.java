//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeReassignmentDef.java,v 1.4 2007/12/14 01:15:30 mford Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Impl. for the 'reassignment' element Def.
 */
public class AeReassignmentDef extends AeHtBaseDef implements IAePotentialOwnersParent
{
   /** 'potentialOwners' element  */
   private AePotentialOwnersDef mPotentialOwners;

   /**
    * @return 'potentialOwners' Def object
    */
   public AePotentialOwnersDef getPotentialOwners()
   {
      return mPotentialOwners;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAePotentialOwnersParent#setPotentialOwners(org.activebpel.rt.ht.def.AePotentialOwnersDef)
    */
   public void setPotentialOwners(AePotentialOwnersDef aPotentialOwners)
   {
      mPotentialOwners = aPotentialOwners;
      assignParent(aPotentialOwners);
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
      AeReassignmentDef def = (AeReassignmentDef)super.clone();
      if (getPotentialOwners() != null)
         def.setPotentialOwners((AePotentialOwnersDef)getPotentialOwners().clone());
         
      return def;
   }
   
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeReassignmentDef))
         return false;
      
      AeReassignmentDef otherDef = (AeReassignmentDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.getPotentialOwners(), getPotentialOwners());
      
      return same;
   }
}