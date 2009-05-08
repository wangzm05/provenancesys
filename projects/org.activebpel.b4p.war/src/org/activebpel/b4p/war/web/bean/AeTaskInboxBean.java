//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/bean/AeTaskInboxBean.java,v 1.12 2008/03/31 20:11:08 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.web.bean;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.activebpel.b4p.war.service.AeTaskFaultException;
import org.activebpel.b4p.war.service.IAeTaskAeClientService;
import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.api.AeGetTasksParam;
import org.activebpel.rt.ht.api.IAeGetTasksFilterGenericHumanRoles;
import org.activebpel.rt.ht.api.IAeGetTasksFilterStates;
import org.activebpel.rt.ht.api.IAeGetTasksFilterTaskType;
import org.activebpel.rt.util.AeUtil;
import org.w3c.dom.Element;

/**
 * Task listing inbox bean.
 */
public class AeTaskInboxBean extends AeTaskApiBeanBase
{
   // Roles based on UI.
   public static final String ROLE_DEFAULT = "default";  //$NON-NLS-1$
   public static final String ROLE_OWNER = "owner";  //$NON-NLS-1$
   public static final String ROLE_POTENTIAL_OWNER = "potentialowner";  //$NON-NLS-1$
   public static final String ROLE_ADMIN = "admin";  //$NON-NLS-1$
   public static final String ROLE_INITIATOR = "initiator";  //$NON-NLS-1$
   public static final String ROLE_STAKEHOLDER = "stakeholder";  //$NON-NLS-1$
   public static final String ROLE_NOTIFICATIONS = "notificationrecipients";  //$NON-NLS-1$

   // Filters
   public static final String FILTER_NOTIFICATIONS = "notifications"; //$NON-NLS-1$
   public static final String FILTER_OPEN = "open"; //$NON-NLS-1$
   // all open includes notifications
   public static final String FILTER_ALL_OPEN = "allopen"; //$NON-NLS-1$
   public static final String FILTER_UNLCLAIMED = "unclaimed"; //$NON-NLS-1$
   public static final String FILTER_RESERVED = "reserved"; //$NON-NLS-1$
   public static final String FILTER_STARTED = "started"; //$NON-NLS-1$
   public static final String FILTER_CLOSED = "closed"; //$NON-NLS-1$
   public static final String FILTER_SUSPENED = "suspended"; //$NON-NLS-1$
   public static final String FILTER_COMPLETED = "completed"; //$NON-NLS-1$
   public static final String FILTER_EXITED = "exited"; //$NON-NLS-1$
   public static final String FILTER_FAILED = "failed"; //$NON-NLS-1$
   public static final String FILTER_OBSOLETE = "obsolete"; //$NON-NLS-1$
   public static final String FILTER_ERROR = "error"; //$NON-NLS-1$

   public static final String FILTER_COOKIE_NAME = "ae.workflow.inbox.filter";  //$NON-NLS-1$
   public static final String ROLE_COOKIE_NAME = "ae.workflow.inbox.role";  //$NON-NLS-1$
   public static final String ORDERBY_COOKIE_NAME = "ae.workflow.inbox.orderby";  //$NON-NLS-1$
   public static final String SEARCHBY_COOKIE_NAME = "ae.workflow.inbox.searchby";  //$NON-NLS-1$

   public static Map UIROLE_TO_GHR_MAP = new HashMap();
   static
   {
      UIROLE_TO_GHR_MAP.put(ROLE_DEFAULT,           ""); //$NON-NLS-1$
      UIROLE_TO_GHR_MAP.put(ROLE_OWNER,             IAeGetTasksFilterGenericHumanRoles.GHR_ACTUAL_OWNER);
      UIROLE_TO_GHR_MAP.put(ROLE_POTENTIAL_OWNER,   IAeGetTasksFilterGenericHumanRoles.GHR_POTENTIAL_OWNERS);
      UIROLE_TO_GHR_MAP.put(ROLE_ADMIN,             IAeGetTasksFilterGenericHumanRoles.GHR_BUSINESS_ADMINISTRATORS);
      UIROLE_TO_GHR_MAP.put(ROLE_INITIATOR,         IAeGetTasksFilterGenericHumanRoles.GHR_INITIATOR);
      UIROLE_TO_GHR_MAP.put(ROLE_STAKEHOLDER,       IAeGetTasksFilterGenericHumanRoles.GHR_STAKEHOLDERS);
      UIROLE_TO_GHR_MAP.put(ROLE_NOTIFICATIONS,     IAeGetTasksFilterGenericHumanRoles.GHR_NOTIFICATION_RECIPIENTS);
   }

   /** Inbox request filter. */
   private AeTaskFilter mFilter;
   /** Search result set. */
   private AeTaskListResult mListResult;
   /** Current filter */
   private String mCurrentFilter;
   /** Current role */
   private String mCurrentRole;

   /**
    * Default ctor.
    */
   public AeTaskInboxBean()
   {
   }

   /**
    * @return the filter
    */
   public AeTaskFilter getTaskFilter()
   {
      if (mFilter == null)
      {
         mFilter = new AeTaskFilter();
         mFilter.setPrincipal( getPrincipal() );
      }
      return mFilter;
   }

   /**
    * @return the listResult
    */
   public AeTaskListResult getListResult()
   {
      if (mListResult == null)
      {
         mListResult = createListResult();
      }
      return mListResult;
   }

   /**
    * @return role option from the http cookie.
    */
   public String getCookieRole()
   {
      Cookie cookie = getCookie(ROLE_COOKIE_NAME);
      if (cookie != null)
      {
         return cookie.getValue();
      }
      return ROLE_POTENTIAL_OWNER;
   }

   /**
    * Returns the current role value
    * @return current role.
    */
   public String getCurrentRole()
   {
      if (AeUtil.notNullOrEmpty(mCurrentRole))
      {
         return mCurrentRole;
      }
      else
      {
         return getCookieRole();
      }
   }

   /**
    * Set the filter role for the display.
    * @param aFilter
    */
   public void setRole(String aRole)
   {
      mCurrentRole = aRole;
      // add cookie if filter value is given and it is not equivalent to the current selection.
      if (AeUtil.notNullOrEmpty (aRole) && !aRole.equals( getCookieRole() ))
      {
         addCookie(ROLE_COOKIE_NAME, aRole);
      }

      String ghrRole = (String) UIROLE_TO_GHR_MAP.get(aRole);
      if (ghrRole != null)
      {
         getTaskFilter().setRole(ghrRole);
      }
   }

   /**
    * @return filter option from the http cookie.
    */
   public String getCookieFilter()
   {
      Cookie cookie = getCookie(FILTER_COOKIE_NAME);
      if (cookie != null)
      {
         return cookie.getValue();
      }
      return FILTER_OPEN;
   }

   /**
    * Returns the current filter value
    * @return current filter.
    */
   public String getCurrentFilter()
   {
      if (AeUtil.notNullOrEmpty(mCurrentFilter))
      {
         return mCurrentFilter;
      }
      else
      {
         return getCookieFilter();
      }
   }

   /**
    * Set the filter option for the display.
    * @param aFilter
    */
   public void setFilter(String aFilter)
   {
      mCurrentFilter = aFilter;
      // add cookie if filter value is given and it is not equivalent to the current selection.
      if (AeUtil.notNullOrEmpty (aFilter) && !aFilter.equals( getCookieFilter() ))
      {
         addCookie(FILTER_COOKIE_NAME, aFilter);
      }
      // reset ACL to actual owner
      getTaskFilter().setRole(IAeGetTasksFilterGenericHumanRoles.GHR_ACTUAL_OWNER);
      // clear status filter
      getTaskFilter().clearStatusSet();
      // list tasks by default.
      getTaskFilter().setTaskType(IAeGetTasksFilterTaskType.TASKTYPE_TASKS);

      if(FILTER_NOTIFICATIONS.equals(aFilter))
      {
         getTaskFilter().setTaskType(IAeGetTasksFilterTaskType.TASKTYPE_NOTIFICATIONS);
      }
      else if(FILTER_OPEN.equals(aFilter))
      {
         getTaskFilter().addStatus(AeTaskFilter.OPEN_STATUS_SET);
      }
      else if(FILTER_ALL_OPEN.equals(aFilter))
      {
         getTaskFilter().setTaskType(IAeGetTasksFilterTaskType.TASKTYPE_ALL);
         getTaskFilter().addStatus(AeTaskFilter.OPEN_STATUS_SET);
      }
      else if(FILTER_UNLCLAIMED.equals(aFilter))
      {
         getTaskFilter().setStatus(IAeGetTasksFilterStates.STATE_READY);
      }
      else if(FILTER_RESERVED.equals(aFilter))
      {
         getTaskFilter().setStatus(IAeGetTasksFilterStates.STATE_RESERVED);
      }
      else if(FILTER_STARTED.equals(aFilter))
      {
         getTaskFilter().setStatus(IAeGetTasksFilterStates.STATE_IN_PROGRESS);
      }
      else if(FILTER_CLOSED.equals(aFilter))
      {
         getTaskFilter().addStatus(AeTaskFilter.FINAL_STATUS_SET);
      }
      else if(FILTER_SUSPENED.equals(aFilter))
      {
         getTaskFilter().setStatus(IAeGetTasksFilterStates.STATE_SUSPENDED);
      }
      else if(FILTER_COMPLETED.equals(aFilter))
      {
         getTaskFilter().setStatus(IAeGetTasksFilterStates.STATE_COMPLETED);
      }
      else if(FILTER_FAILED.equals(aFilter))
      {
         getTaskFilter().setStatus(IAeGetTasksFilterStates.STATE_FAILED);
      }
      else if(FILTER_EXITED.equals(aFilter))
      {
         getTaskFilter().setStatus(IAeGetTasksFilterStates.STATE_EXITED);
      }
      else if(FILTER_OBSOLETE.equals(aFilter))
      {
         getTaskFilter().setStatus(IAeGetTasksFilterStates.STATE_OBSOLETE);
      }
      else if(FILTER_ERROR.equals(aFilter))
      {
         getTaskFilter().setStatus(IAeGetTasksFilterStates.STATE_ERROR);
      }
   }

   /**
    * Set the starting inbox for the display in the filter.
    * @param aStartIndex
    */
   public void setStartIndex(int aStartIndex)
   {
      if (aStartIndex < 0)
      {
         aStartIndex = 0;
      }
      getTaskFilter().setStartIndex(aStartIndex);
   }

   /**
    * Sets the page size i.e number of items to return per request.
    * @param aPageSize
    */
   public void setPageSize(int aPageSize)
   {
      getTaskFilter().setPageSize(aPageSize);
   }

   /**
    * @return search by option from the http cookie.
    */
   public String getCookieSearchBy()
   {
      Cookie cookie = getCookie(SEARCHBY_COOKIE_NAME);
      if (cookie != null)
      {
         return cookie.getValue();
      }
      return ""; //$NON-NLS-1$
   }

   /**
    * Sets the search by value.
    * @param aSearchBy
    */
   public void setSearchBy(String aSearchBy)
   {
      aSearchBy = AeUtil.getSafeString(aSearchBy);
      getTaskFilter().setSearchBy(aSearchBy);
      // add cookie if new value is different to current cookie value.
      if (!aSearchBy.equals( getCookieSearchBy() ))
      {
         addCookie(SEARCHBY_COOKIE_NAME, aSearchBy);
      }
   }

   /**
    * Bootstrap method to fetch list of tasks. This method is normally invoked from the JSP bean.
    * @param aFetch true if tasks should be fetched.
    */
   public void setFetchTasks(boolean aFetch)
   {
      if (aFetch)
      {
         // call getListResult to fetch inbox listings.
         getListResult();
      }
   }

   /**
    * Creates the resultset by invoking the service API.
    * @return the list result
    */
   protected AeTaskListResult createListResult()
   {
      AeTaskListResult listResult = null;
      try
      {
         AeGetTasksParam params = createGetTasksParam( getTaskFilter() );
         IAeTaskAeClientService service = getAeClientService();
         Element result = service.getTasks(params);
         listResult = new AeTaskListResult(result, getTaskFilter().getStartIndex(), getTaskFilter().getPageSize());
      }
      catch(AeTaskFaultException tfe)
      {
         setTaskFault(tfe);
      }
      catch(Throwable t)
      {
         AeException.logError(t);
         setError(t);
      }
      return listResult;
   }

   /**
    * Collection of order by field IDs. When field id is preceded by a hyphen (-) it is sorted in descending order.
    * @return Collection of order by strings.
    */
   public Collection getOrderBys()
   {
      return  getTaskFilter().getOrderBys();
   }

   /**
    * @return Returns a pipe (|) separated list of orderby field IDs.
    */
   public String getOrderByList()
   {
      return AeUtil.toString(getOrderBys(), '|');
   }

   /**
    * Sets the order order by list. The list is a pipe (|) delimited list of field IDs. When
    * field id is preceded by a hyphen (-) it is sorted in descending order.
    * @param aOrderByCsv pipe separated values of order by fields IDs.
    */
   public void setOrderByList(String aOrderByCsv)
   {
      List orderByList = AeUtil.toList(aOrderByCsv, "|"); //$NON-NLS-1$
      for (int i = 0; i < orderByList.size(); i++)
      {
         String orderBy = (String) orderByList.get(i);
         boolean asc = true;
         if (orderBy.startsWith("-")) //$NON-NLS-1$
         {
            orderBy = orderBy.substring(1);
            asc = false;
         }
         getTaskFilter().addOrderBy(orderBy, asc);
      }

      // add to cookie if order by value is given and it is not equivalent to the current orderby.
      if (AeUtil.notNullOrEmpty(aOrderByCsv) && !aOrderByCsv.equals(getCookieOrderByList()) )
      {
         addCookie(ORDERBY_COOKIE_NAME, aOrderByCsv);
      }
   }

   /**
    * @return orderby list from cookie..
    */
   public String getCookieOrderByList()
   {
      Cookie cookie = getCookie(ORDERBY_COOKIE_NAME);
      if (cookie != null)
      {
         return cookie.getValue();
      }
      return ""; //$NON-NLS-1$
   }

}
