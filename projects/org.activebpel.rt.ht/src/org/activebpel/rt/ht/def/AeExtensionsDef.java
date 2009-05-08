package org.activebpel.rt.ht.def;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeCloneUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * Impl. for the 'extension' element Def.
 */
public class AeExtensionsDef extends AeHtBaseDef
{
   /** list of 'extension' Def objects. */
   private List mExtensionList = new ArrayList();

   /**
    * Add an 'extension' Def object.
    * @param aExtension
    */
   public void addExtension(AeExtensionDef aExtension)
   {
      mExtensionList.add(aExtension);
   }

   /**
    * @return iterator for the 'extension' Def objects.
    */
   public Iterator getExtensionDefs()
   {
      return mExtensionList.iterator();
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
      AeExtensionsDef def = (AeExtensionsDef)super.clone();
      def.mExtensionList = new ArrayList();
      
      try
      {
         if (mExtensionList.size() > 0)
            def.mExtensionList = AeCloneUtil.deepClone(mExtensionList); 
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
      if (! (aOther instanceof AeExtensionsDef))
         return false;
      
      AeExtensionsDef otherDef = (AeExtensionsDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.mExtensionList, mExtensionList);
      
      return same;
   }
}