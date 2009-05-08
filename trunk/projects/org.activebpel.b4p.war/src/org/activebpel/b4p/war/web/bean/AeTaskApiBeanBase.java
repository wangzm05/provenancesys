//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/bean/AeTaskApiBeanBase.java,v 1.6 2008/03/19 19:29:17 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.web.bean;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activebpel.rt.ht.api.AeGetTasksParam;
import org.activebpel.rt.util.AeUtil;

/**
 * Base bean class for task api related functions.
 */
public class AeTaskApiBeanBase extends AeWorkflowBeanBase
{
   /** Current user principal name */
   private String mPrincipal;

   /**
    * @return the principal
    */
   public String getPrincipal()
   {
      return mPrincipal;
   }

   /**
    * @param aPrincipal the principal to set
    */
   public void setPrincipal(String aPrincipal)
   {
      mPrincipal = aPrincipal;
   }

   /**
    * Concats the principal name (if it exists) to the cookie name.
    * @param aCookieName
    * @return cookie name contacted with the principal name.
    */
   protected String getPrincipalCookieName(String aCookieName)
   {
      if (AeUtil.notNullOrEmpty( getPrincipal()) )
      {
         // create cookie name on a per principal basis by concating
         // principal name to cookie name. The spaces and @ characters are also
         // replaced with the underscore character so that the cookie name if valid (per RFC 2109)
         return aCookieName + "." + getPrincipal().replace(' ', '_').replace('@', '_'); //$NON-NLS-1$
      }
      else
      {
         return aCookieName;
      }
   }

   /**
    * Convenience method to add a cookie. This method prefixes the cookie name with the principal name.
    * @param aResponse
    * @param aCookieName
    * @param aCookieValue
    */
   protected void addCookie(HttpServletResponse aResponse, String aCookieName, String aCookieValue)
   {
      Cookie cookie = new Cookie(getPrincipalCookieName(aCookieName), aCookieValue);
      // 30 day cookie
      cookie.setMaxAge( 30 * 24 * 3600 );
      aResponse.addCookie(cookie);
   }

   /**
    * Convenience method to access a cookie. The member variable mRequest must be set via
    * setRequestResponse() method prior to invoking this method.
    *
    * @param aCookieName name of cookie
    * @return <code>cookie</code> if found or <code>null</code> otherwise.
    */
   protected Cookie getCookie(String aCookieName)
   {
      if ( getRequest() != null)
      {
         return getCookie(getRequest(), aCookieName);
      }
      else
      {
         return null;
      }
   }

   /**
    * Convenience method to access a cookie. The principal name (if available) is appended to
    * the cookie name to provide a mechanism  to 'filter' by principal name.
    * @param aRequest http request.
    * @param aCookieName name of cookie
    * @return <code>cookie</code> if found or <code>null</code> otherwise.
    */
   protected Cookie getCookie(HttpServletRequest aRequest, String aCookieName)
   {
      String name = getPrincipalCookieName(aCookieName);
      Cookie cookies [] = aRequest.getCookies ();
      if (cookies != null)
      {
         for (int i = 0; i < cookies.length; i++)
         {
            if (cookies[i].getName().equals(name))
            {
               return cookies[i];
            }
         }
      }
      return null;
   }

   /**
    * Convenience method to add a cookie. The member variable mResponse must be set via
    * setRequestResponse() method prior to invoking this method.
    * @param aCookieName
    * @param aCookieValue
    */
   protected void addCookie(String aCookieName, String aCookieValue)
   {
      if (getResponse() != null)
      {
         addCookie( getResponse(), aCookieName, aCookieValue);
      }
   }

   /**
    * Convenience method to build api task listing request
    * given inbox request filter.
    * @param aFilter request filter
    */
   protected AeGetTasksParam createGetTasksParam(AeTaskFilter aFilter)
   {
      AeGetTasksParam param = new AeGetTasksParam();
      param.setTaskIndexOffset(aFilter.getStartIndex());
      param.setTaskType(aFilter.getTaskType());
      param.setMaxTasks(aFilter.getPageSize());
      param.setGenericHumanRole(aFilter.getRole());
      param.addStatus(aFilter.getStatusSet());
      param.setOrderBys(aFilter.getOrderBys());
      param.setSearchBy(aFilter.getSearchBy());
      return param;      
   }

}
