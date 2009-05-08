//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/bean/AeTaskLoginBean.java,v 1.2 2008/02/17 21:08:40 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.web.bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activebpel.b4p.war.service.AeTaskServiceException;
import org.activebpel.b4p.war.service.IAeTaskAeClientService;
import org.activebpel.b4p.war.web.IAeWorkFlowWebConstants;
import org.activebpel.rt.util.AeUtil;

/**
 * Bean responsible for logining a user by forwarding the
 * request to the server.
 */
public class AeTaskLoginBean extends AeWorkflowBeanBase
{
   /** user name. */
   private String mUsername;
   /** User password */
   private String mPassword;

   /**
    * @return the userName
    */
   public String getUsername()
   {
      return mUsername;
   }

   /**
    * @param aUsername the userName to set
    */
   public void setUsername(String aUsername)
   {
      mUsername = aUsername;
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
   public void setPassword(String aPassword)
   {
      mPassword = aPassword;
   }

   /**
    * JSP setter to initiate login process.
    * @param aAuthenticate
    */
   public void setAuthenticate(String aAuthenticate)
   {
      if ("true".equals(aAuthenticate)) //$NON-NLS-1$
      {
         authenticateUser();
      }
   }

   /**
    * @return true if the user is authorized.
    */
   public boolean isAuthorized()
   {
      return isAuthorized( getRequestOrThrow() );
   }

   /**
    * Authenicates the user against the server.
    */
   protected void authenticateUser()
   {
      // remove current session if needed
      invalidateSession();
      // short return if username or password is not given.
      if (AeUtil.isNullOrEmpty( getUsername() ) || AeUtil.isNullOrEmpty( getPassword() ))
      {
         return;
      }
      try      
      {
         IAeTaskAeClientService aeService = getAeClientService( getUsername(), getPassword() );
         // if successful, then set user info in http session
         if (aeService.authenticate())
         {
            authorizeUser();
         }
      }
      catch(AeTaskServiceException tse)
      {
         setError(tse);
      }
      catch(Throwable t)
      {
         setError(t);
      }
   }

   /**
    * Creates a new authenticated user session.
    */
   protected void authorizeUser()
   {
      AeUserSession user = new AeUserSession( getUsername(), getPassword() );
      HttpServletRequest request = getRequestOrThrow();
      HttpSession session = request.getSession(true);
      session.setAttribute(IAeWorkFlowWebConstants.USER_SESSION, user);
   }

   /**
    * Invalidates the current session.
    */
   protected void invalidateSession()
   {
      HttpServletRequest request = getRequestOrThrow();
      HttpSession session = request.getSession();
      if (session != null)
      {
         session.removeAttribute(IAeWorkFlowWebConstants.USER_SESSION);
         session.invalidate();
      }
   }

}
