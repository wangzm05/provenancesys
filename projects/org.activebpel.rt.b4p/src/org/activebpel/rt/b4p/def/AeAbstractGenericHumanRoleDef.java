//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeAbstractGenericHumanRoleDef.java,v 1.4 2007/12/14 01:17:46 mford Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def;

import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.IAeFromDefParent;
import org.activebpel.rt.util.AeUtil;

/**
 * Impl for 'genericHumanRole' element types
 *
 * TODO: create interface to determine Def to model association
 */
public abstract class AeAbstractGenericHumanRoleDef extends AeB4PBaseDef implements IAeFromDefParent
{
   /** 'from' element */
   private AeFromDef mFrom;

   /**
    * 
    * @return the 'from' element
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
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      AeAbstractGenericHumanRoleDef def = (AeAbstractGenericHumanRoleDef)super.clone();
      if (getFrom() != null)
         def.setFrom((AeFromDef)getFrom().clone());
      
      return def;
   }
   
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeAbstractGenericHumanRoleDef))
         return false;
      
      AeAbstractGenericHumanRoleDef otherDef = (AeAbstractGenericHumanRoleDef)aOther;
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(otherDef.getFrom(), getFrom());
      
      return same;
   }
}
