//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/IAeProcessTaskConstants.java,v 1.6 2008/02/27 20:56:53 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p;

import javax.xml.namespace.QName;

import org.activebpel.rt.ht.IAeWSHTConstants;

/**
 * Constants used in AeProcessTaskRequestBuilder and AeProcessTaskRequestSerializer
 */
public interface IAeProcessTaskConstants
{
   public static final String B4P_MANAGER_KEY = "BPEL4PeopleManager"; //$NON-NLS-1$
   
   /**  Task ref urn format prefix. Eg: urn:aeb4p:task:[ProcessId] */
   public static final String TASK_ID_URN_PREFIX = "urn:b4p:"; //$NON-NLS-1$
   /** Notification ref urn format prefix. Eg: urn:aeb4p:notification:[ProcessId] */
   public static final String NOTIFICATION_ID_URN_PREFIX = "urn:b4pn:"; //$NON-NLS-1$
      
   /** namespaces used in processTaskRequest root element of request to life cycle process */
   public static final String TASK_LIFECYCLE_WSDL_NS = "http://www.activebpel.org/b4p/2007/10/wshumantask/taskLifecycle.wsdl"; //$NON-NLS-1$
   public static final String TASK_LIFECYCLE_NS = "http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-lifecycle-wsdl.xsd"; //$NON-NLS-1$
   public static final String NOTIFICATION_LIFECYCLE_WSDL_NS = "http://www.activebpel.org/b4p/2007/12/wshumantask/aeb4p-task-notification.wsdl"; //$NON-NLS-1$
   public static final String NOTIFICATION_LIFICYCLE_NS = "http://schemas.active-endpoints.com/b4p/wshumantask/2007/12/aeb4p-task-notifications-wsdl.xsd"; //$NON-NLS-1$
   public static final String WSHT_NS = IAeWSHTConstants.WSHT_NAMESPACE;
   public static final String WSHT_PROTOCOL_NS = "http://www.example.org/WS-HT/protocol"; //$NON-NLS-1$
   public static final String WSHT_API_NS = "http://www.example.org/WS-HT/api"; //$NON-NLS-1$
   //fixme (pj) : check with PP/MF - why does TASK_LC_PROCESS_NS = test_people_activity? Use TASK_LIFECYCLE_WSDL_NS instead.
   public static final String TASK_LC_PROCESS_NAMESPACE = "http://www.active-endpoints.com/wsdl/test/test_people_activity"; //$NON-NLS-1$
   public static final String TASK_STATE_WSDL_NS = "http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-state-wsdl.xsd"; //$NON-NLS-1$

   /** task response status constants */
   public static final String SUCCESS_RESPONSE = "COMPLETED"; //$NON-NLS-1$
   public static final String FAULT_RESPONSE = "FAILED"; //$NON-NLS-1$
   public static final String SKIPPED_RESPONSE = "OBSOLETE"; //$NON-NLS-1$
   public static final String TERMINATED_RESPONSE = "EXITED"; //$NON-NLS-1$

   
   /** task lifecycle request and response messages */
   public static final String TASK_REQUEST_PART_NAME = "processTaskRequest"; //$NON-NLS-1$
   public static final String TASK_RESPONSE_PART_NAME = "processTaskResponse"; //$NON-NLS-1$

   /** Notification request part name */
   public static final String NOTIFICATION_REQUEST_PART_NAME = "processNotificationRequest"; //$NON-NLS-1$

   /** Task request message type qname */
   public static final QName PROC_TASK_REQ_MSG = new QName(TASK_LIFECYCLE_WSDL_NS, "processTaskRequestMessage");  //$NON-NLS-1$
   /** Notification request message type qname */
   public static final QName PROC_NOTIFICATION_REQ_MSG = new QName(NOTIFICATION_LIFECYCLE_WSDL_NS, "processNotificationRequest");  //$NON-NLS-1$
   /** Task cancellation request message type qname */
   public static final QName CANCEL_TASK_REQ_MSG = new QName(TASK_LIFECYCLE_WSDL_NS, "cancelMessage");  //$NON-NLS-1$
   
   // Task Attachments Constants */  
   public static final String ATTACHMENTS_FROM_ALL = "all"; //$NON-NLS-1$
   public static final String ATTACHMENTS_FROM_NEW_ONLY = "newOnly"; //$NON-NLS-1$
}
