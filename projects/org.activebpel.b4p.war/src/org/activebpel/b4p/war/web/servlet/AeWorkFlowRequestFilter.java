//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/servlet/AeWorkFlowRequestFilter.java,v 1.4 2008/02/06 03:00:09 PJayanetti Exp $
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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activebpel.b4p.war.AeWorkFlowApplicationFactory;
import org.activebpel.b4p.war.web.IAeWorkFlowWebConstants;
import org.activebpel.b4p.war.web.bean.AeUserSession;
import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeUtil;

/**
 * Filter to intercept all requests to the secured inbox. This filter
 * sets the application name in the request context and creates a AeUserSession
 * in the http session if one has not been already created for it.
 *
 */
public final class AeWorkFlowRequestFilter implements Filter
{
   /** Application name defined in the servlet config */
   private String mApplicationName;
   /** context path pattern for inbox. */
   private String mInboxCtxPath;
   /** path pattern for admin subcontext. */
   private String mAdminCtxPath;

   /**
    * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
    */
   public void init( FilterConfig aConfig) throws ServletException
   {
      ServletContext ctx = aConfig.getServletContext();
      mApplicationName = AeUtil.getSafeString( ctx.getInitParameter(IAeWorkFlowWebConstants.WS_APP_NAME) );
      String s = ctx.getInitParameter(IAeWorkFlowWebConstants.INBOX_CTX_PATH) ;
      if (AeUtil.notNullOrEmpty(s))
      {
         mInboxCtxPath = s;
      }
      else
      {
         mInboxCtxPath = "/inbox/"; //$NON-NLS-1$
      }
      s = ctx.getInitParameter(IAeWorkFlowWebConstants.ADMIN_CTX_PATH) ;
      if (AeUtil.notNullOrEmpty(s))
      {
         mAdminCtxPath = s;
      }
      else
      {
         mAdminCtxPath = "/admin/"; //$NON-NLS-1$
      }

   }

   /**
    * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
    */
   public void doFilter(ServletRequest aRequest, ServletResponse aResponse,
                        FilterChain aChain ) throws IOException, ServletException
   {
      HttpServletRequest req = (HttpServletRequest)aRequest;

      // check if configured. and if path is not admin.
      if (!isConfigured() &&  req.getRequestURI().indexOf(mAdminCtxPath) == -1)
      {
         AeException.logWarning("Application not configured.."); //$NON-NLS-1$
         HttpServletResponse res = (HttpServletResponse)aResponse;
         res.sendRedirect(req.getContextPath()+  "/errors/notifyconfigure.jsp"); //$NON-NLS-1$
         return;
      }

      // set the application name
      req.setAttribute(IAeWorkFlowWebConstants.APP_NAME, mApplicationName);

      //
      // AeUserSession (and principalName) is required for all authenticated pages.
      // Redirect to login page if not authorized.
      if ((req.getRequestURI().indexOf(mInboxCtxPath) != -1 || req.getRequestURI().indexOf(mAdminCtxPath) != -1)
            && !isAuthorized(req))
      {
         HttpServletResponse res = (HttpServletResponse)aResponse;
         res.sendRedirect(req.getContextPath()+  "/login.jsp"); //$NON-NLS-1$
         return;
      }
      aChain.doFilter( req, aResponse );
   }

   /**
    * Returns true if the application has been properly configured i.e. the endpoint url to the
    * bpel engine has been specified.
    * @return true if configured.
    */
   protected boolean isConfigured()
   {
      return AeWorkFlowApplicationFactory.hasConfiguration()
         && AeWorkFlowApplicationFactory.getConfiguration().getHtClientServiceEndpointURL() != null;
   }


   /**
    * Returns true if there is AeUserSession in the http session.
    * @param aRequest
    * @return true if authorized.
    */
   protected boolean isAuthorized(HttpServletRequest aRequest)
   {
      return AeUserSession.isAuthorized(aRequest);
   }

   /**
    * @see javax.servlet.Filter#destroy()
    */
   public void destroy()
   {
   }
}
