//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeUserDef.java,v 1.4 2008/02/26 18:39:26 mford Exp $
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
 * impl. for the 'user' element Def.
 */
public class AeUserDef extends AeHtBaseDef
{
   /** 'user' value */
   private String mValue;
   
   /**
    * Ctor 
    */
   public AeUserDef()
   {
   }
   
   /**
    * Ctor
    * @param aUser
    */
   public AeUserDef(String aUser)
   {
      setValue(aUser);
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
      if (! (aOther instanceof AeUserDef))
         return false;
      
      AeUserDef otherDef = (AeUserDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.getValue(), getValue());
      
      return same;
   }
 }