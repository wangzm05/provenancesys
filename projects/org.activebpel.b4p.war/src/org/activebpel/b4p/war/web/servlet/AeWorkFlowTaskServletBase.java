//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/servlet/AeWorkFlowTaskServletBase.java,v 1.4 2008/02/17 21:08:40 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activebpel.b4p.war.AeMessages;
import org.activebpel.b4p.war.web.bean.AeUserSession;
import org.activebpel.rt.util.AeUtil;

/**
 * Base servlet for fetching task details, attachments and rendering information.
 */
public abstract class AeWorkFlowTaskServletBase extends HttpServlet
{

   /**
    * Returns the current AeUserSession only if the user is in session and has
    * credentials. Othewise, this method returns null.
    * @param aRequest
    */
   protected AeUserSession getAuthorizedUser(HttpServletRequest aRequest)
   {
      AeUserSession userSession = AeUserSession.getUserSession(aRequest);
      if (AeUserSession.isAuthorized(userSession) )
      {
         return userSession;
      }
      return null;
   }

   /**
    * Overrides to call doPost.
    * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse)
    */
   protected void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException,
         IOException
   {
      doPost(aRequest, aResponse);
   }

   /**
    * Overrides to extract principal name, taskId and invoke handleRequest method.
    * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse)
    */
   protected void doPost(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException,
         IOException
   {
      // check if principal exists.
      AeUserSession userSession = getAuthorizedUser(aRequest);
      if (userSession == null)
      {
         handleNotAuthenticated(aRequest, aResponse);
         return;

      }
      String principal = userSession.getPrincipalName();
      String taskId  = aRequest.getParameter("taskId"); //$NON-NLS-1$

      // check if taskref exists and attachment name exists.
      if (AeUtil.isNullOrEmpty(taskId))
      {
         aResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, AeMessages.getString("AeWorkFlowTaskServletBase.MISSING_TASKREF")); //$NON-NLS-1$
         return;
      }
      handleRequest(aRequest, aResponse, principal, taskId);
   }

   /**
    * Handles the http un-authorized state. This implemenation returns HTTP 401.
    * @param aRequest
    * @param aResponse
    * @throws ServletException
    * @throws IOException
    */
   protected void handleNotAuthenticated(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException
   {
      aResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, AeMessages.getString("AeWorkFlowTaskServletBase.UNAUTHORIZED")); //$NON-NLS-1$
   }

   /**
    * Processes the task related request.
    * @param aRequest
    * @param aResponse
    * @param aPrincipalName
    * @param aTaskRef
    * @throws ServletException
    * @throws IOException
    */
   protected abstract void handleRequest(HttpServletRequest aRequest, HttpServletResponse aResponse, String aPrincipalName, String aTaskRef) throws ServletException, IOException;


   /**
    * URL encodes the parameter value.
    * @param aParamValue
    * @return URL encoded value.
    */
   protected String encodeURIComponent(String aParamValue)
   {
      try
      {
         return URLEncoder.encode(aParamValue, "utf8"); //$NON-NLS-1$
      }
      catch(Exception e)
      {
         return aParamValue;
      }
   }

}
