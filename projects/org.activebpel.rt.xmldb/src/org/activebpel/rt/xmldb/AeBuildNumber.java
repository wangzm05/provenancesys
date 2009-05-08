// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/xmldb/AeBuildNumber.java,v 1.1 2007/08/17 00:40:55 ewittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.xmldb;

import java.io.IOException;
import java.util.Properties;

/**
 * Provides access to the build number information for this project.
 */
public class AeBuildNumber
{
   private static String mBuildNumber = ""; //$NON-NLS-1$
   private static String mBuildDate   = ""; //$NON-NLS-1$
   private static String mDBVersion   = ""; //$NON-NLS-1$

   static
   {
      try
      {
         Properties props = new Properties();
         props.load(new AeBuildNumber().getClass().getResourceAsStream("version.properties")); //$NON-NLS-1$
      
         mBuildNumber = props.getProperty("build.number"); //$NON-NLS-1$
         mBuildDate   = props.getProperty("build.date"); //$NON-NLS-1$
         mDBVersion   = props.getProperty("db.version"); //$NON-NLS-1$
      }
      catch (IOException ioe)
      {
      }
   }

   /**
    * Default constructor for a build resource file.
    */
   private AeBuildNumber()
   {
   }

   /**
    * Obtains the build number for this component.
    * 
    * @return a String value representing the build number
    */
   public final static String getBuildNumber() 
   {
      return mBuildNumber;
   }
   
   /**
    * Obtains the build date for this component.
    * 
    * @return a String value representing the build date
    */
   public final static String getBuildDate() 
   {
      return mBuildDate;
   }

   /**
    * Obtains the database version number.
    */
   public final static String getDBVersion()
   {
      return mDBVersion;
   }
}
