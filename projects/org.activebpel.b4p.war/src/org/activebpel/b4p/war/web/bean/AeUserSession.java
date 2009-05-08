//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/bean/AeUserSession.java,v 1.4 2008/02/17 21:08:40 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.web.bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activebpel.b4p.war.web.IAeWorkFlowWebConstants;
import org.activebpel.rt.util.AeUtil;

/**
 * Wrapper containing user session information such as principal name.
 */
public class AeUserSession
{
   /** Current user's principal name. */
   private String mPrincipalName;
   /** User's display or fullname. */
   private String mDisplayName;
   /** User password */
   private String mPassword;

   /**
    * Default ctor for JSP useBeans.
    */
   public AeUserSession()
   {
   }
   
   /**
    * Constructs user object for the given principal name.
    * @param aPrincipalName
    * @param aPassword
    */
   public AeUserSession(String aPrincipalName, String aPassword)
   {
      setPrincipalName(aPrincipalName);
      setDisplayName(aPrincipalName);
      setPassword(aPassword);
   }

   /**
    * @return the principalName
    */
   public String getPrincipalName()
   {
      return mPrincipalName;
   }

   /**
    * @param aPrincipalName the principalName to set
    */
   protected void setPrincipalName(String aPrincipalName)
   {
      mPrincipalName = aPrincipalName;
   }

   /**
    * @return the displayName
    */
   public String getDisplayName()
   {
      return mDisplayName;
   }

   /**
    * @param aDisplayName the displayName to set
    */
   protected void setDisplayName(String aDisplayName)
   {
      mDisplayName = aDisplayName;
   }

   /**
    * @return the password
    */
   public String getPassword()
   {
      return mPassword;
   }

   /**
    * @param aPassword the password to set
    */
   protected void setPassword(String aPassword)
   {
      mPassword = aPassword;
   }
   
   /** 
    * @return true if the user login credentials are avaiable.
    */
   public boolean hasCredentials()
   {
      return AeUtil.notNullOrEmpty( getPrincipalName() ) && AeUtil.notNullOrEmpty( getPassword() );
   }
   
   /**
    * Returns true if aUserSession is not null and the username and password are set. 
    * @param aUserSession
    * @return true if the UserSession is not null and has crendentials.
    */
   public static boolean isAuthorized(AeUserSession aUserSession)
   {
      return aUserSession != null && aUserSession.hasCredentials();
   }
   
   /**
    * Returns true if UserSession stored in the http servlet session 
    * is not null and the username and password are set. 
    * @param aRequest
    * @return true if the UserSession exists and has crendentials.
    */
   public static boolean isAuthorized(HttpServletRequest aRequest)
   {
      AeUserSession userSession = getUserSession(aRequest);
      return isAuthorized(userSession);
   }
   
   /**
    * Returns the AeUseSession from the HttpSession or null if not available.
    * @param aRequest
    * @return AeUserSession or null.
    */
   public static AeUserSession getUserSession(HttpServletRequest aRequest)
   {
      AeUserSession userSession = null;
      HttpSession session = aRequest.getSession(false);
      if (session != null)
      {
         userSession = (AeUserSession) session.getAttribute(IAeWorkFlowWebConstants.USER_SESSION);
      }
      return userSession;
   }   
}
