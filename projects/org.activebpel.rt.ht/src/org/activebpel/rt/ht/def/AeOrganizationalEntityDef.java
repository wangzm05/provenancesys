//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeOrganizationalEntityDef.java,v 1.9 2008/03/03 01:32:29 mford Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import java.util.List;

import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Impl. for 'organizationalEntity' element Def.
 */
public class AeOrganizationalEntityDef extends AeHtBaseDef
{
   /** 'users' element */
   private AeUsersDef mUsers;
   /** 'groups' element */
   private AeGroupsDef mGroups;
   
   /**
    * C'tor.
    */
   public AeOrganizationalEntityDef()
   {
   }
   
   /**
    * Ctor
    * @param aUserDef
    */
   public AeOrganizationalEntityDef(AeUserDef aUserDef)
   {
      setUsers(new AeUsersDef());
      getUsers().addUser(aUserDef);
   }
   
   /**
    * Ctor
    * @param aUserDef - value for the user def
    */
   public AeOrganizationalEntityDef(String aUserDef)
   {
      this(new AeUserDef(aUserDef));
   }
   
   /**
    * Adds the org entity into this one. This may result in an invalid org entity
    * with a mix of users/groups.
    * @param aDef
    */
   public void add(AeOrganizationalEntityDef aDef)
   {
      if (aDef.getUsers() != null)
      {
         if (getUsers() == null)
         {
            setUsers(new AeUsersDef());
         }
         
         List userDefs = AeUtil.toList(aDef.getUsers().getUserDefs());
         getUsers().addAll(userDefs);
      }

      if (aDef.getGroups() != null)
      {
         if (getGroups() == null)
         {
            setGroups(new AeGroupsDef());
         }
         
         List groupDefs = AeUtil.toList(aDef.getGroups().getGroupDefs());
         getGroups().addAll(groupDefs);
      }
}

   /**
    * @return the 'users' element Def object
    */
   public AeUsersDef getUsers()
   {
      return mUsers;
   }

   /**
    * @param aUsers the 'users' element Def object to set.
    */
   public void setUsers(AeUsersDef aUsers)
   {
      mUsers = aUsers;
      assignParent(aUsers);
   }

   /**
    * @return the 'groups' element Def object
    */
   public AeGroupsDef getGroups()
   {
      return mGroups;
   }

   /**
    * @param aGroups the 'groups' element Def object to set.
    */
   public void setGroups(AeGroupsDef aGroups)
   {
      mGroups = aGroups;
      assignParent(aGroups);
   }
   
   /**
    * Returns true if there are no users or groups defined in this org entity
    */
   public boolean isEmpty()
   {
      return !hasUsers() && !hasGroups();
   }

   /**
    * Returns true if the org entity has groups
    */
   public boolean hasGroups()
   {
      return getGroups() != null && getGroups().size() > 0;
   }

   /**
    * Returns true if the org entity has users
    */
   public boolean hasUsers()
   {
      return getUsers() != null && getUsers().size() > 0;
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
      AeOrganizationalEntityDef entity = (AeOrganizationalEntityDef)super.clone();
      if (hasUsers())
         entity.setUsers((AeUsersDef)getUsers().clone());
      if (hasGroups())
         entity.setGroups((AeGroupsDef)getGroups().clone());
      
      return entity;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeOrganizationalEntityDef))
         return false;
      
      AeOrganizationalEntityDef otherDef = (AeOrganizationalEntityDef)aOther;
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(otherDef.getUsers(), getUsers()); 
      same &= AeUtil.compareObjects(otherDef.getGroups(), getGroups()); 
      
      return same; 
   }
}