//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeTextNodeDef.java,v 1.5 2008/02/17 21:51:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeUtil;

/**
 * A simple wrapper impl. for an xml Text node
 */
public class AeTextNodeDef implements Cloneable
{
   /** The value of the text element. */
   private String mTextValue;
   /** True - indicates wrapped as CDATA */
   private boolean mCData = false;

   /**
    * C'tor.
    * 
    * @param aTextValue
    * @param aCData
    */
   public AeTextNodeDef(String aTextValue, boolean aCData)
   {
      mTextValue = aTextValue;
      mCData = aCData;
   }
    
   /**
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return getValue();
   }
   
   /**
    * @return Returns the value.
    */
   public String getValue()
   {
      return mTextValue;
   }

   /**
    * @param aTextValue The value to set.
    */
   public void SetValue(String aTextValue)
   {
      mTextValue = aTextValue;
   }

   /**
    * @return the cData
    */
   public boolean isCData()
   {
      return mCData;
   }

   /**
    * @param aCData true if CDATA, false otherwise.
    */
   public void setCData(boolean aCData)
   {
      mCData = aCData;
   }

   /**
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      try
      {
         return super.clone();
      }
      catch (CloneNotSupportedException ex)
      {
         // This should never happen
         AeException.logError(ex);
         return new AeTextNodeDef(mTextValue, mCData);
      }
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeTextNodeDef))
         return false;
      
      AeTextNodeDef otherDef = (AeTextNodeDef)aOther;
      boolean same = AeUtil.compareObjects(otherDef.getValue(), getValue());
      same &= (otherDef.isCData() == isCData());
      
      return same; 
   }

   /**
     * @see java.lang.Object#hashCode()
     */
   public int hashCode()
   {
      int hashCode = (isCData() ? 1 : 0);
      if (getValue() != null)
         hashCode += getValue().hashCode();
       
      return hashCode;
   }
}