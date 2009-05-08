//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/bean/AeWorkflowBeanBase.java,v 1.5 2008/02/20 15:58:19 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.web.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activebpel.b4p.war.AeMessages;
import org.activebpel.b4p.war.AeWorkFlowApplicationFactory;
import org.activebpel.b4p.war.service.AeHtCredentials;
import org.activebpel.b4p.war.service.AeTaskFaultException;
import org.activebpel.b4p.war.service.IAeTaskAeClientService;
import org.activebpel.b4p.war.service.IAeTaskHtClientService;
import org.activebpel.rt.util.AeUtil;

// FIXMEPJ add keyboard (sec 508) support for application.

/**
 * Base class for the task JSP beans.
 */
public abstract class AeWorkflowBeanBase
{
   /**
    * Request or command was successful.
    */
   public static final int SUCCESS = 0;
   /** Command failed. */
   public static final int FAILURE = 1;
   /** Error */
   public static final int ERROR = 2;

   /** Response status code */
   private int mStatusCode = SUCCESS;
   /**
    * List of error and other messages.
    */
   private List mMessages;

   /** Http servlet request. */
   private HttpServletRequest mRequest;
   /** Http servlet response. */
   private HttpServletResponse mResponse;


   /**
    * @return IAeTaskHtClientService instance
    */
   protected IAeTaskHtClientService getHtClientService()
   {
      try
      {
         AeUserSession user = getUserSession( getRequestOrThrow() );
         AeHtCredentials credentials = new AeHtCredentials(user.getPrincipalName(), user.getPassword() );
         IAeTaskHtClientService service = AeWorkFlowApplicationFactory.createHtClientService( credentials );
         return service;
      }
      catch(Exception e)
      {
         throw new RuntimeException(e);
      }
   }

   /**
    * @return IAeTaskAeClientService instance
    */
   protected IAeTaskAeClientService getAeClientService()
   {
      try
      {
         AeUserSession user = getUserSession( getRequestOrThrow() );
         return getAeClientService(user.getPrincipalName(), user.getPassword() );
      }
      catch(Exception e)
      {
         throw new RuntimeException(e);
      }
   }

   /**
    * @return IAeTaskAeClientService instance
    */
   protected IAeTaskAeClientService getAeClientService(String aUsername, String aPassword)
   {
      AeHtCredentials credentials = new AeHtCredentials(aUsername, aPassword );
      IAeTaskAeClientService service = AeWorkFlowApplicationFactory.createAeClientService( credentials );
      return service;
   }

   /**
    * @return Returns the status code.
    */
   public int getStatusCode()
   {
      return mStatusCode;
   }

   /**
    * Sets the response status code.
    * @param aStatusCode
    */
   public void setStatusCode(int aStatusCode)
   {
      mStatusCode = aStatusCode;
   }

   /**
    * List of error and other messages.
    * @return list of messages.
    */
   public List getMessages()
   {
      if (mMessages == null)
      {
         mMessages = new ArrayList();
      }
      return mMessages;
   }

   /**
    * @return the message
    */
   public String getMessage()
   {
      if (isHasMessage())
      {
         StringBuffer sb = new StringBuffer();
         Iterator it = getMessages().iterator();
         synchronized(sb)
         {
            while (it.hasNext() )
            {
               sb.append( (String) it.next() + "\n"); //$NON-NLS-1$
            }
         }
         return sb.toString();
      }
      else
      {
         return ""; //$NON-NLS-1$
      }
   }

   /**
    * Returns true if there is atleast one message.
    * @return true if there is at least a single message.
    */
   public boolean isHasMessage()
   {
      return getMessages().size() >  0;
   }

   /**
    * Returns true if there are more than one messages.
    * @return true if there two or more messages.
    */
   public boolean isHasMessages()
   {
      return getMessages().size() >  1;
   }

   /**
    * @param aMessage the message to set
    */
   public void setMessage(String aMessage)
   {
      if ( !getMessages().contains(aMessage))
      {
         getMessages().add(aMessage);
      }
   }

   /**
    * @return the error
    */
   public boolean isError()
   {
      return getStatusCode() == ERROR;
   }

   /**
    * Sets the error message from the throwable.
    * @param aError
    */
   public void setError(Throwable aError)
   {
      setStatusCode(ERROR);
      setMessage(aError);
   }

   /**
    * Sets the task service message in the bean, but marks the operation as a failure instead of an error.
    * Task service fault messages are displayed to the user.
    * @param aTaskFaultException
    */
   public void setTaskFault(AeTaskFaultException aTaskFaultException)
   {
      setStatusCode(FAILURE);
      // modify message that is displayed.
      String msg = AeMessages.format("AeWorkflowBeanBase.TASK_FAULT", createExceptionMessage(aTaskFaultException)); //$NON-NLS-1$
      setMessage(msg);
   }

   /**
    * Convenience method to return the message from the root cause of the error.
    * @param aError
    * @return root cause message.
    */
   protected String createExceptionMessage(Throwable aError)
   {
      if (aError.getCause() != null && AeUtil.notNullOrEmpty( aError.getCause().getMessage() ) )
      {
         return aError.getCause().getMessage();
      }
      else
      {
         return aError.getMessage();
      }
   }
   /**
    * Sets the message from the throwable.
    * @param aError
    */
   public void setMessage(Throwable aError)
   {
      setMessage ( createExceptionMessage(aError) );
   }

   /**
    * Called by the JSP set the request and response objects.
    * @param aRequest
    * @param aResponse
    */
   public void setRequestResponse(HttpServletRequest aRequest, HttpServletResponse aResponse)
   {
      mRequest = aRequest;
      mResponse = aResponse;
   }

   /**
    * @return Http servlet request if set or <code>null</code> otherwise.
    */
   protected HttpServletRequest getRequest()
   {
      return mRequest;
   }

   /**
    * @return http request if available or throws exception otherwise.
    */
   protected HttpServletRequest getRequestOrThrow()
   {
      HttpServletRequest request = getRequest();
      if (request != null)
      {
         return request;
      }
      throw new IllegalStateException(AeMessages.getString("AeWorkflowBeanBase.HTTP_REQUEST_NOT_AVAILABLE")); //$NON-NLS-1$
   }

   /**
    * @return Http servlet response if set or <code>null</code> otherwise.
    */
   protected HttpServletResponse getResponse()
   {
      return mResponse;
   }

   /**
    * Returns the AeUseSession from the HttpSession or null if not available.
    * @param aRequest
    * @return AeUserSession or null.
    */
   protected AeUserSession getUserSession(HttpServletRequest aRequest)
   {
      return AeUserSession.getUserSession(aRequest);
   }

   /**
    * Returns true if there is AeUserSession in the http session and the username and password are set.
    * @param aRequest
    * @return true if authorized.
    */
   protected boolean isAuthorized(HttpServletRequest aRequest)
   {
      AeUserSession userSession = getUserSession(aRequest);
      return AeUserSession.isAuthorized(userSession);
   }

}
