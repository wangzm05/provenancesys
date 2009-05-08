//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/AeHtCredentials.java,v 1.1 2008/02/20 15:58:19 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.service;

/**
 * Simple strutcture to store the ht task service xsl stylesheet catalog
 * access credentials.
 */
public class AeHtCredentials
{  
   /** Username */
   private String mUsername;
   /** Password */
   private String mPassword;
   
   /**
    * Ctor
    * @param aUsername
    * @param aPassword
    */
   public AeHtCredentials(String aUsername, String aPassword)
   {
      mUsername = aUsername;
      mPassword = aPassword;
   }

   /**
    * @return the username
    */
   public String getUsername()
   {
      return mUsername;
   }

   /**
    * @return the password
    */
   public String getPassword()
   {
      return mPassword;
   }
}
