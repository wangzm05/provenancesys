//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeUsersDef.java,v 1.9 2008/03/03 01:32:29 mford Exp $$
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
import java.util.LinkedHashSet;
import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeCloneUtil;
import org.activebpel.rt.util.AeUtil;

public class AeUsersDef extends AeHtBaseDef
{
   /** List of associated 'user' element Def objects */
   protected List mUserList = new ArrayList();

   /**
    * @param aUser the 'user' element Def object to add.
    */
   public void addUser(AeUserDef aUser)
   {
      mUserList.add(aUser);
      assignParent(aUser);
   }
   
   /**
    * Adds all of the users in the list to our list of users
    * @param aListOfUsers
    */
   public void addAll(List aListOfUsers)
   {
      LinkedHashSet set = new LinkedHashSet(mUserList);
      for (Iterator it = aListOfUsers.iterator(); it.hasNext();)
      {
         AeUserDef userDef = (AeUserDef) it.next();
         if (!set.contains(userDef))
         {
            addUser((AeUserDef) userDef.clone());
         }
      }
   }
   
   /**
    * Convenience method for adding user with the given name
    * @param aUser
    */
   public void addUser(String aUser)
   {
      AeUserDef userDef = new AeUserDef(aUser);
      mUserList.add(userDef);
      assignParent(userDef);
   }

   /**
    * @return number of user entries.
    */
   public int size()
   {
      return mUserList.size();
   }

   /**
    * @return the 'user' element Def object
    */
   public Iterator getUserDefs()
   {
      return mUserList.iterator();
   }
   
   /**
    * Gets the user by name
    * @param aUser
    */
   public AeUserDef getUser(String aUser)
   {
      int index = mUserList.indexOf(new AeUserDef(aUser));
      if (index != -1)
         return (AeUserDef) mUserList.get(index);
      return null;
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
      AeUsersDef users = (AeUsersDef)super.clone();
      users.mUserList = new ArrayList();

      try
      {
         if (mUserList.size() > 0)
            users.mUserList = AeCloneUtil.deepClone(mUserList);
      }
      catch (CloneNotSupportedException ex)
      {
         // This should never happen in a closed system
         AeException.logError(ex);
         throw new InternalError(ex.getLocalizedMessage());
      }

      return users;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeUsersDef))
         return false;

      AeUsersDef otherDef = (AeUsersDef)aOther;
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(otherDef.mUserList, mUserList);

      return same;
   }
}