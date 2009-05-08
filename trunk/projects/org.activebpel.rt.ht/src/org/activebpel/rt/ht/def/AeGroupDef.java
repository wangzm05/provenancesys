//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeGroupDef.java,v 1.4 2008/03/03 01:32:29 mford Exp $
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
 * Impl. for the 'group' element Def
 */
public class AeGroupDef extends AeHtBaseDef
{
   /** 'group' value */
   private String mValue;
   
   /**
    * Ctor
    */
   public AeGroupDef()
   {
      
   }
   
   /**
    * Ctor
    * @param aGroup
    */
   public AeGroupDef(String aGroup)
   {
      setValue(aGroup);
   }

   /**
    * @return the value
    */
   public String getValue()
   {
      return mValue;
   }

   /**
    * @param aValue the value to set
    */
   public void setValue(String aValue)
   {
      mValue = aValue;
   }

   /**
    * @see org.activebpel.rt.ht.def.AeHtBaseDef#accept(org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor)
    */
   public void accept(IAeHtDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
   
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeGroupDef))
         return false;
      
      AeGroupDef otherDef = (AeGroupDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.getValue(), getValue());
      
      return same;
   }
}