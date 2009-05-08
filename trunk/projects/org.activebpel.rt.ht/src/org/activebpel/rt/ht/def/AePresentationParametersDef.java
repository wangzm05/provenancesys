//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AePresentationParametersDef.java,v 1.5 2008/02/17 21:51:26 mford Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeCloneUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * Impl. for 'presentationParameters' element Def.
 */
public class AePresentationParametersDef extends AeHtBaseDef
{
   /** list of 'presentationParameter' element Def objects */
   private List mPresentationParameterList = new ArrayList();

   /** Expression language attribute. */
   private String mExpressionLanguage;

   /**
    * Gets the expression language.
    */
   public String getExpressionLanguage()
   {
      return mExpressionLanguage;
   }

   /**
    * Sets the expression language.
    * @param aExpressionLanguage
    */
   public void setExpressionLanguage(String aExpressionLanguage)
   {
      mExpressionLanguage = aExpressionLanguage;
   }

   /**
    * @param aParameter Def object to add.
    */
   public void addPresentationParameter(AePresentationParameterDef aParameter)
   {
      mPresentationParameterList.add(aParameter);
      assignParent(aParameter);
   }

   /**
    * @return iterator for the 'parameter' Def objects.
    */
   public Iterator getPresentationParameterDefs()
   {
      return mPresentationParameterList.iterator();
   }

   /**
    * Removes all parameter def objects.
    */
   public void removeAllPresentationParameters()
   {
      mPresentationParameterList.clear();
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
      AePresentationParametersDef def = (AePresentationParametersDef)super.clone();
      def.mPresentationParameterList = new ArrayList();
      
      try
      {
         if (mPresentationParameterList.size() > 0)
            def.mPresentationParameterList = AeCloneUtil.deepClone(mPresentationParameterList);
      }
      catch (CloneNotSupportedException ex)
      {
         // This should never happen in a closed system
         AeException.logError(ex);
         throw new InternalError(ex.getLocalizedMessage());
      }
      
      return def;
   }
   
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AePresentationParametersDef))
         return false;
      
      AePresentationParametersDef otherDef = (AePresentationParametersDef)aOther;
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(otherDef.mPresentationParameterList, mPresentationParameterList);
      
      return same;
   }

   /**
    * Overrides method to produce a useful string representation for this object.
    * 
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      // Generate a comma separated list of parameter names.
      StringBuffer results = new StringBuffer("");  //$NON-NLS-1$
      boolean firstLoop = true;
      for (Iterator itr = getPresentationParameterDefs(); itr.hasNext(); )
      {
         AePresentationParameterDef def = (AePresentationParameterDef)itr.next();
         if ( firstLoop )
         {
            firstLoop = false;
            results.append("(").append(def.getName()); //$NON-NLS-1$
         }
         else
         {
            results.append(", ").append(def.getName()); //$NON-NLS-1$
         }
      }
      
      if ( !firstLoop )
         results.append( ')' );

      return results.toString();
   }
}