// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeDescriptionDefRule13Validator.java,v 1.2 2008/02/15 17:40:56 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeDescriptionDef;
import org.activebpel.rt.ht.def.AePresentationElementsDef;
import org.activebpel.rt.util.AeUtil;

/**
 * As descriptions may exist with different content types, it is allowed to 
 * specify multiple description elements having the same value for attribute 
 * xml:lang, but their content types MUST be different.
 */
public class AeDescriptionDefRule13Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDescriptionDef)
    */
   public void visit(AeDescriptionDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeDescriptionDef aDef)
   {
      Set mDescriptionSet = new HashSet();
      AePresentationElementsDef presDef = (AePresentationElementsDef) aDef.getParentDef();
      
      for (Iterator iter = presDef.getDescriptionDefs(); iter.hasNext();)
      {
         AeDescriptionDef desc = (AeDescriptionDef) iter.next();
         if (aDef != desc)
         {
            mDescriptionSet.add(new AeDescriptionKey(desc.getLanguage(), desc.getContentType()));
         }
      }
      
      if (mDescriptionSet.contains(new AeDescriptionKey(aDef.getLanguage(), aDef.getContentType())))
      {
         reportProblem(AeMessages.getString("AeDescriptionDefRule13Validator.0"), aDef); //$NON-NLS-1$
      }
   }

   /**
    * Helper inner class. This represents a unique key for language and content type. 
    */
   private class AeDescriptionKey
   {
      /** language value */
      private String mLanguage;
      /** content type */
      private String mContentType;
      
      /**
       * C'tor 
       * @param aLanguage
       * @param aContentType
       */
      public AeDescriptionKey(String aLanguage, String aContentType)
      {
         
         mLanguage = AeUtil.getSafeString(aLanguage);
         mContentType = AeUtil.getSafeString(aContentType);
      }

      /**
       * @see java.lang.Object#equals(java.lang.Object)
       */
      public boolean equals(Object aObj)
      {
         if ( !(aObj instanceof AeDescriptionKey) )
            return false;
         
         AeDescriptionKey inKey = (AeDescriptionKey) aObj;
        
         return (AeUtil.compareObjects(this.getLanguage(), inKey.getLanguage()) && 
               AeUtil.compareObjects(this.getContentType(), inKey.getContentType()));
      }
      
      /**
       * @see java.lang.Object#hashCode()
       */
      public int hashCode()
      {
         return getLanguage().hashCode() ^ getContentType().hashCode();
      }

      /**
       * @return Returns the language.
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
       * @return Returns the contentType.
       */
      public String getContentType()
      {
         return mContentType;
      }

      /**
       * @param aContentType the contentType to set
       */
      public void setContentType(String aContentType)
      {
         mContentType = aContentType;
      }
   }
   
}
