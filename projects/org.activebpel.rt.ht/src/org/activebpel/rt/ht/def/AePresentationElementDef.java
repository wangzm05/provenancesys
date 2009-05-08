//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AePresentationElementDef.java,v 1.2 2008/01/27 15:15:32 mford Exp $
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
 * Base class for Name, Subject, Description, all of which have xml:lang attrs 
 */
public abstract class AePresentationElementDef extends AeAbstractMixedTextDef
{
   /** xml:lang attribute */
   private String mLanguage;

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AePresentationElementDef))
         return false;
      
      AePresentationElementDef other = (AePresentationElementDef) aOther;
      
      boolean same = super.equals(other);
      same &= AeUtil.compareObjects(other.getLanguage(), getLanguage());
      
      return same;
   }

   /**
    * @return the language
    */
   public String getLanguage()
   {
      return mLanguage;
   }

   /**
    * @param aLanguage the language to set
    */
   public void setLanguage(String aLanguage)
   {
      mLanguage = aLanguage;
   }
   
   /**
    * Returns true if the language is specified
    */
   public boolean isLanguageSpecified()
   {
      return AeUtil.notNullOrEmpty(getLanguage());
   }
}
 