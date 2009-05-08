//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeAlarmDef.java,v 1.1 2007/12/18 04:06:28 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def; 

/**
 * A def that contains defs to a scheduled alarm, either a for or an until. 
 */
public abstract class AeAlarmDef extends AeB4PBaseDef implements IAeB4PAlarmDef
{
   /** 'for' element */
   private AeB4PForDef mForDef;
   /** 'until' element */
   private AeB4PUntilDef mUntilDef;

   /**
    * @return the forDef
    */
   public AeB4PForDef getForDef()
   {
      return mForDef;
   }

   /**
    * @param aForDef the forDef to set
    */
   public void setForDef(AeB4PForDef aForDef)
   {
      mForDef = aForDef;
      assignParent(aForDef);
   }

   /**
    * @return the untilDef
    */
   public AeB4PUntilDef getUntilDef()
   {
      return mUntilDef;
   }

   /**
    * @param aUntilDef the untilDef to set
    */
   public void setUntilDef(AeB4PUntilDef aUntilDef)
   {
      mUntilDef = aUntilDef;
      assignParent(aUntilDef);
   }

   /**
    * @see org.activebpel.rt.xml.def.AeBaseXmlDef#clone()
    */
   public Object clone()
   {
      AeAlarmDef copy = (AeAlarmDef) super.clone();
      if (copy.getForDef() != null)
         copy.setForDef((AeB4PForDef) copy.getForDef().clone());
      if (copy.getUntilDef() != null)
         copy.setUntilDef((AeB4PUntilDef) copy.getUntilDef().clone());
      return copy;
   }
}
 