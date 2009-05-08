//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeRenderingsDef.java,v 1.5 2008/01/17 23:45:58 JPerrotto Exp $$
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
 * Impl. for 'renderings' Def.
 */
public class AeRenderingsDef extends AeHtBaseDef
{
   /** 'rendering' elements associated with this element */
   protected List mRenderingList = new ArrayList();

   /**
    * Adds a child rendering def to the list of renderings.
    *
    * @param aRendering
    */
   public void addRendering(AeRenderingDef aRendering)
   {
      mRenderingList.add(aRendering);
      assignParent(aRendering);
   }

   /**
    * @return iterator for the 'rendering' Def objects
    */
   public Iterator getRenderingDefs()
   {
      return mRenderingList.iterator();
   }

   /**
    * Gets the number of renderings defined.
    * 
    * @return int 
    */
   public int getRenderingsCount()
   {
      return mRenderingList.size();
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
      AeRenderingsDef def = (AeRenderingsDef)super.clone();
      def.mRenderingList = new ArrayList();
      
      try
      {
         if (mRenderingList.size() > 0)
            def.mRenderingList = AeCloneUtil.deepClone(mRenderingList);
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
      if (! (aOther instanceof AeRenderingsDef))
         return false;

      AeRenderingsDef otherDef = (AeRenderingsDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.mRenderingList, mRenderingList);
      
      return same;
   }
}