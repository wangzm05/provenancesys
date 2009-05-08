//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/AeTaskACLEntry.java,v 1.1 2008/02/02 19:11:36 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage;

/**
 * Data object for representing a single entry in one of the task ACL database
 * tables.
 */
public class AeTaskACLEntry
{
   public static final int USER = 0;
   public static final int GROUP = 1;
   
   /** The principal/role name. */
   private String mName;
   /** The ACL entry type - principal or role. */
   private int mType;
   /** Excluded or included. */
   private boolean mExcludeFlag;
   /** The generic human role - potential owner, admin, etc... */
   private int mGenericHumanRole;

   /**
    * Default c'tor.
    */
   public AeTaskACLEntry()
   {
   }

   /**
    * @return Returns the name.
    */
   public String getName()
   {
      return mName;
   }

   /**
    * @param aName the name to set
    */
   public void setName(String aName)
   {
      mName = aName;
   }

   /**
    * @return Returns the type.
    */
   public int getType()
   {
      return mType;
   }

   /**
    * @param aType the type to set
    */
   public void setType(int aType)
   {
      mType = aType;
   }

   /**
    * @return Returns the genericHumanRole.
    */
   public int getGenericHumanRole()
   {
      return mGenericHumanRole;
   }

   /**
    * @param aGenericHumanRole the genericHumanRole to set
    */
   public void setGenericHumanRole(int aGenericHumanRole)
   {
      mGenericHumanRole = aGenericHumanRole;
   }

   /**
    * @return Returns the excludeFlag.
    */
   public boolean isExcludeFlag()
   {
      return mExcludeFlag;
   }

   /**
    * @param aExcludeFlag the excludeFlag to set
    */
   public void setExcludeFlag(boolean aExcludeFlag)
   {
      mExcludeFlag = aExcludeFlag;
   }
}
