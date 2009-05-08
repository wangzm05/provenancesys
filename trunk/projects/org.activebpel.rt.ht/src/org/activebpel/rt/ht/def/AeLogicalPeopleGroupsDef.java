//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeLogicalPeopleGroupsDef.java,v 1.6 2007/12/30 01:02:10 mford Exp $$
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
 * Impl. for the 'logicalPeopleGroups' Def object.
 */
public class AeLogicalPeopleGroupsDef extends AeHtBaseDef
{
   /** list of associated 'logicalPeopleGroup' Def objects */
   private List mLogicalPeopleGroupList = new ArrayList();
   
   /**
    * Gets a logical people group by its name or null if not found here
    * @param aName
    */
   public AeLogicalPeopleGroupDef getLogicalPeopleGroup(String aName)
   {
      for( Iterator itr = getLogicalPeopleGroupDefs(); itr.hasNext();)
      {
         AeLogicalPeopleGroupDef def = (AeLogicalPeopleGroupDef)itr.next();
         if(aName.equals(def.getName()))
         {
            return def;
         }
      }
      return null;
   }
   
   /**
    * Gets the number of logical people groups
    */
   public int getCount()
   {
      return mLogicalPeopleGroupList.size();
   }
   
   /**
    * Adds a logical people group to the end of the def
    * @param aLogicalPeopleGroupDef
    */
   public void addLogicalPeopleGroup(AeLogicalPeopleGroupDef aLogicalPeopleGroupDef)
   {
      addLogicalPeopleGroup(getCount(), aLogicalPeopleGroupDef);
   }

   /**
    * Adds a logical people group at the specified index
    *
    * @param aIndex
    * @param aLogicalPeopleGroup
    */
   public void addLogicalPeopleGroup(int aIndex, AeLogicalPeopleGroupDef aLogicalPeopleGroup)
   {
      mLogicalPeopleGroupList.add(aIndex, aLogicalPeopleGroup);
      assignParent(aLogicalPeopleGroup);
   }
   
   /**
    * Removes the logical people group from the collection of children
    * @param aDef
    */
   public void removeLogicalPeopleGroup(AeLogicalPeopleGroupDef aDef)
   {
      if (mLogicalPeopleGroupList.remove(aDef))
      {
         aDef.setParentXmlDef(null);
      }
   }

   /**
    * @return iterator for LogicalPeopleGroupList
    */
   public Iterator getLogicalPeopleGroupDefs()
   {
      return mLogicalPeopleGroupList.iterator();
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
      AeLogicalPeopleGroupsDef def = (AeLogicalPeopleGroupsDef)super.clone();
      def.mLogicalPeopleGroupList = new ArrayList();
      
      try
      {
         if (mLogicalPeopleGroupList.size() > 0)
            def.mLogicalPeopleGroupList = AeCloneUtil.deepClone(mLogicalPeopleGroupList); 
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
      if (! (aOther instanceof AeLogicalPeopleGroupsDef))
         return false;
      
      AeLogicalPeopleGroupsDef otherDef = (AeLogicalPeopleGroupsDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.mLogicalPeopleGroupList, mLogicalPeopleGroupList);
      
      return same;
   }
}