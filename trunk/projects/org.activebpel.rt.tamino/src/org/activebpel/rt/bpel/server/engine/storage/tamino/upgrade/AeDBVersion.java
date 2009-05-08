// $Header$
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2006 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.bpel.server.engine.storage.tamino.upgrade;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This class wraps a Database version number.  The DB version number will be of the form
 * x.x.x.x.  This class can be used to easily compare two versions, such as "is version 2.1.3
 * less than 2.1.1?".
 */
public class AeDBVersion implements Comparable
{
   /** The original version string. */
   private String mVersion;
   /** The list of version components. For example, version 3.1.3.8 will have a list like  [3, 1, 3, 8] */
   private List mVersionComponents;

   /**
    * Constructs a DB Version object from a DB version string.
    * 
    * @param aVersionString
    */
   public AeDBVersion(String aVersionString)
   {
      setVersion(aVersionString);
      parseVersionString(aVersionString);
   }

   /**
    * Returns true if this version represents an Enterprise installation.
    */
   public boolean isEnterprise()
   {
      return (getVersion().indexOf("Enterprise") != -1) || //$NON-NLS-1$
             (getVersion().indexOf("ActiveWebflow") != -1); //$NON-NLS-1$
   }
   
   /**
    * Parses the version string and stores the components in a list.
    * 
    * @param aVersionString
    */
   protected void parseVersionString(String aVersionString)
   {
      StringTokenizer tokenizer = new StringTokenizer(aVersionString, " "); //$NON-NLS-1$
      String ver = tokenizer.nextToken();

      List list = new LinkedList();
      tokenizer = new StringTokenizer(ver, "."); //$NON-NLS-1$
      while (tokenizer.hasMoreTokens())
      {
         list.add(new Integer(tokenizer.nextToken()));
      }
      setVersionComponents(list);
   }
   
   /**
    * @return Returns the versionComponents.
    */
   protected List getVersionComponents()
   {
      return mVersionComponents;
   }

   /**
    * @param aVersionComponents The versionComponents to set.
    */
   protected void setVersionComponents(List aVersionComponents)
   {
      mVersionComponents = aVersionComponents;
   }

   /**
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   public int compareTo(Object aOther)
   {
      AeDBVersion otherVer = (AeDBVersion) aOther;
      List otherComponents = otherVer.getVersionComponents();
      List myComponents = getVersionComponents();

      int rval = 0;
      for (int i = 0; i < Math.max(myComponents.size(), otherComponents.size()); i++)
      {
         // I'm out of components but the other guy isn't - so he is greater than me.
         if (myComponents.size() <= i && otherComponents.size() > i)
            return -1;

         // I have components remaining, but the other guys is out - so I am greater.
         else if (otherComponents.size() <= i && myComponents.size() > i)
            return 1;

         Integer myComponent = (Integer) myComponents.get(i);
         Integer otherComponent = (Integer) otherComponents.get(i);

         rval = myComponent.compareTo(otherComponent);

         // If a difference is found, break (no reason to continue testing).
         if (rval != 0)
         {
            break;
         }
      }
      return rval;
   }
   
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aObj)
   {
      return compareTo(aObj) == 0;
   }

   /**
    * @return Returns the version.
    */
   public String getVersion()
   {
      return mVersion;
   }

   /**
    * @param aVersion The version to set.
    */
   protected void setVersion(String aVersion)
   {
      mVersion = aVersion;
   }
   
   /**
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return getVersion();
   }
}
