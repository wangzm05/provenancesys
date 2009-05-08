//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeGroupsDef.java,v 1.8 2008/03/20 22:30:27 PJayanetti Exp $$
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
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeCloneUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * Impl. for the 'groups' element Def
 */
public class AeGroupsDef extends AeHtBaseDef
{
   /** List of associated 'group' element Def objects. */
   private List mGroupList = new ArrayList();
   
   /**
    * Ctor 
    */
   public AeGroupsDef()
   {
   }
   
   /**
    * Ctor
    * @param aGroup
    */
   public AeGroupsDef(String aGroup)
   {
      addGroup(new AeGroupDef(aGroup));
   }
   

   /**
    * @param aGroup the 'group element Def object to add.
    */
   public void addGroup(AeGroupDef aGroup)
   {
      mGroupList.add(aGroup);
      assignParent(aGroup);
   }

   public void addAll(List aListOfGroups)
   {
      LinkedHashSet set = new LinkedHashSet(mGroupList);
      for (Iterator it = aListOfGroups.iterator(); it.hasNext();)
      {
         AeGroupDef groupDef = (AeGroupDef) it.next();
         if (!set.contains(groupDef))
         {
            addGroup((AeGroupDef) groupDef.clone());
         }
      }
   }

   
   /**
    * @return number of group entries.
    */
   public int size()
   {
      return mGroupList.size();
   }

   /**
    * @return the 'group' element Def object
    */
   public Iterator getGroupDefs()
   {
      return mGroupList.iterator();
   }

   /**
    * Returns a collection of groupnames defined in each of the GroupDefs.
    * @return
    */
   public Set getGroupNames()
   {
      if (size() > 0)
      {
         Set groupNames = new LinkedHashSet();
         Iterator iter = getGroupDefs();
         while (iter.hasNext())
         {
            AeGroupDef group = (AeGroupDef)iter.next();
            groupNames.add( group.getValue() );
         }
         return groupNames;
      }
      else
      {
         return Collections.EMPTY_SET;
      }
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
      AeGroupsDef def = (AeGroupsDef)super.clone();
      def.mGroupList = new ArrayList();

      try
      {
         if (mGroupList.size() > 0)
            def.mGroupList = AeCloneUtil.deepClone(mGroupList);
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
      if (! (aOther instanceof AeGroupsDef))
         return false;

      AeGroupsDef otherDef = (AeGroupsDef)aOther;
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(otherDef.mGroupList, mGroupList);

      return same;
   }
}