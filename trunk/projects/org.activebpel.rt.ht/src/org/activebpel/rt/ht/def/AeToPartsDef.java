//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeToPartsDef.java,v 1.8 2008/02/17 21:51:26 mford Exp $$
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
import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeCloneUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * Impl. for 'toParts' element Def.
 */
public class AeToPartsDef extends AeHtBaseDef
{
   /** List of associated 'toPart' element Def objects. */
   protected List mToPartList = new ArrayList();

   /**
    * @param aToPart the 'toPart' element Def object to add.
    */
   public void addToPart(AeToPartDef aToPart)
   {
      mToPartList.add(aToPart);
      assignParent(aToPart);
   }

   /**
    * @return iterator for the list of 'toPart' element Def objects.
    */
   public Iterator getToPartDefs()
   {
      return mToPartList.iterator();
   }

   /**
    * Convenience method to return the number of toParts in the list.
    * @return int
    */
   public int getToPartsCount()
   {
      return mToPartList.size();
   }
   
   /**
    * Removes all toParts.
    */
   public void removeToParts()
   {
      mToPartList.clear();   
   }
   
   /**
    * Returns a AeToPartDef by the part name
    * 
    * @param aPartName
    */
   public AeToPartDef getPart(String aPartName)
   {
      AeToPartDef foundPart = null;
      
      for (Iterator parts = getToPartDefs(); parts.hasNext();)
      {
         AeToPartDef part = (AeToPartDef) parts.next();
         if (part.getName().equalsIgnoreCase(aPartName))
         {
            foundPart = part;
            break;
         }
      }  
      
      return foundPart;
   }
   
   /**
    * @param aVisitor
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
      AeToPartsDef def = (AeToPartsDef)super.clone();
      def.mToPartList = new ArrayList();
      
      try
      {
         if (mToPartList.size() > 0)
            def.mToPartList = AeCloneUtil.deepClone(mToPartList);
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
      if (! (aOther instanceof AeToPartsDef))
         return false;
      
      AeToPartsDef otherDef = (AeToPartsDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.mToPartList, mToPartList);
      
      return same;
   }

   /**
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return getDisplayText(this);
   }
   
   /**
    * Convenience method for getting the display text string for this def.
    * @param aPartsDef
    * @return String
    */
   public static String getDisplayText(AeToPartsDef aPartsDef)
   {
      if ( aPartsDef != null)
         return AeMessages.format( "AeToPartsDef.PartsCount", new Integer(aPartsDef.getToPartsCount()));  //$NON-NLS-1$ 

      return AeMessages.getString("AeToPartsDef.None"); //$NON-NLS-1$
   }
  
}