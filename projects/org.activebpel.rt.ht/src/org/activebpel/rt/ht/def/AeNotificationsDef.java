//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeNotificationsDef.java,v 1.6 2007/12/30 01:02:10 mford Exp $$
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
 * Impl. for the 'notifications' element Def.
 */
public class AeNotificationsDef extends AeHtBaseDef implements IAeNotificationDefParent
{
   /** List of associated 'notification' elements */
   private List mNotificationList = new ArrayList();
   
   /**
    * Gets the notification with the given name or null if not found
    * @param aName
    */
   public AeNotificationDef getNotification(String aName)
   {
      for( Iterator itr = getNotificationDefs(); itr.hasNext();)
      {
         AeNotificationDef nDef = (AeNotificationDef)itr.next();
         if(aName.equals(nDef.getName())) 
         {
            return nDef;
         }
      }
      return null;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeNotificationDefParent#addNotification(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void addNotification(AeNotificationDef aNotification)
   {
      addNotification(getCount(), aNotification);
   }

   /**
    * Adds a notification at the specified index
    * @param aIndex
    * @param aNotification
    */
   public void addNotification(int aIndex, AeNotificationDef aNotification)
   {
      mNotificationList.add(aIndex, aNotification);
      assignParent(aNotification);
   }

   /**
    * Removes the notification from the collection of children
    * @param aChild
    */
   public void removeNotification(AeNotificationDef aChild)
   {
      if (mNotificationList.remove(aChild))
      {
         aChild.setParentXmlDef(null);
      }
   }
   
   /**
    * Gets the number of notifications
    */
   public int getCount()
   {
      return mNotificationList.size();
   }

   /**
    * @return iterator for the list of 'notification' element Def objects.
    */
   public Iterator getNotificationDefs()
   {
      return mNotificationList.iterator();
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
      AeNotificationsDef def = (AeNotificationsDef)super.clone();
      def.mNotificationList = new ArrayList();
      
      try
      {
         if (mNotificationList.size() > 0)
            def.mNotificationList = AeCloneUtil.deepClone(mNotificationList); 
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
      if (! (aOther instanceof AeNotificationsDef))
         return false;
      
      AeNotificationsDef otherDef = (AeNotificationsDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.mNotificationList, mNotificationList);
      
      return same;
   }
}