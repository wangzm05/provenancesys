//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeAbstractParameterListDef.java,v 1.6 2008/02/17 21:51:26 mford Exp $
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
import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeCloneUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * Abstract impl of a parameter list to be extended by Def with parameter lists. This provides the capability
 * for different element Def's to have 'parameter' children.
 */
public abstract class AeAbstractParameterListDef extends AeHtBaseDef
{
   /** list of 'parameter' element Def objects */
   private List mParameterList;

   /**
    * @param aParameter Def object to add.
    */
   public void addParameter(AeParameterDef aParameter)
   {
      getParameterDefs().add(aParameter);
      assignParent(aParameter);
   }

   /**
    * @return iterator for the 'parameter' Def objects.
    */
   public List getParameterDefs()
   {
      if (mParameterList == null)
         mParameterList = new ArrayList();
      
      return mParameterList;
   }

   /**
    * Sets the parameter defs
    */
   public void setParameterDefs(List aParameterList)
   {
      mParameterList = aParameterList;
   }
   
   /**
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      AeAbstractParameterListDef params = (AeAbstractParameterListDef)super.clone();
      params.mParameterList = new ArrayList();
      
      try
      {
         if (mParameterList.size() > 0)
            params.mParameterList = AeCloneUtil.deepClone(mParameterList); 
      }
      catch (CloneNotSupportedException ex)
      {
         // This should never happen in a closed system
         AeException.logError(ex);
         throw new InternalError(ex.getLocalizedMessage());
      }
      
      return params;
   }
   
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeAbstractParameterListDef))
         return false;
      
      AeAbstractParameterListDef otherDef = (AeAbstractParameterListDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.mParameterList, mParameterList);
      
      return same;
   }
}
