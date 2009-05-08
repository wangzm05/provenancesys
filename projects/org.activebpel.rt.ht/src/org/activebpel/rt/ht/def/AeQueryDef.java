//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeQueryDef.java,v 1.4 2008/01/03 16:16:25 rnaylor Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import org.activebpel.rt.util.AeUtil;

/**
 * Abstract 'tQuery' def Impl.
 */
public abstract class AeQueryDef extends AeAbstractMixedTextDef
{
   /** 'part' attribute value */
   private String mPart;
   /** 'queryLanguage' attribute value */
   private String mQueryLanguage;

   /**
    * @see org.activebpel.rt.ht.def.AeAbstractMixedTextDef#isDefined()
    */
   public boolean isDefined()
   {
      boolean isDefined = super.isDefined();
      isDefined &= AeUtil.notNullOrEmpty(mPart);
      
      return isDefined;
   }
   
   /**
    * @return the part
    */
   public String getPart()
   {
      return mPart;
   }

   /**
    * @param aPart the part to set
    */
   public void setPart(String aPart)
   {
      mPart = aPart;
   }

   /**
    * @return the queryLanguage
    */
   public String getQueryLanguage()
   {
      return mQueryLanguage;
   }

   /**
    * @param aQueryLanguage the queryLanguage to set
    */
   public void setQueryLanguage(String aQueryLanguage)
   {
      mQueryLanguage = aQueryLanguage;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeQueryDef))
         return false;
      
      AeQueryDef otherDef = (AeQueryDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.getPart(), getPart());
      same &= AeUtil.compareObjects(otherDef.getQueryLanguage(), getQueryLanguage());
      
      return same;
   }
}