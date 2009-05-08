//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/IAeWorkFlowWebConstants.java,v 1.1 2008/01/11 15:05:48 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.web;

/**
 * Constants used with in the web application.
 */
public interface IAeWorkFlowWebConstants
{
   //
   // Web.xml initialization
   //
   
   /** App Name. */
   public static final String WS_APP_NAME = "ae.workflow.name"; //$NON-NLS-1$
   /** inbox context path. */
   public static final String INBOX_CTX_PATH = "ae.workflow.inbox.context.path"; //$NON-NLS-1$
   /** admin context path */
   public static final String ADMIN_CTX_PATH = "ae.workflow.admin.context.path"; //$NON-NLS-1$
   /** config filename */
   public static final String CONFIG_FILENAME = "ae.workflow.config.filename"; //$NON-NLS-1$
   
   
   //
   // Request and Session Attribute names 
   //
   
   public static final String APP_NAME = "aeWorkFlowApplicationName"; //$NON-NLS-1$
   public static final String PAGE_NAME = "aeWorkFlowPageName"; //$NON-NLS-1$
   public static final String USER_SESSION = "aeWorkFlowUserSession"; //$NON-NLS-1$
   public static final String ERROR_MESSAGE = "aeWorkFlowErrorMessage"; //$NON-NLS-1$
}
