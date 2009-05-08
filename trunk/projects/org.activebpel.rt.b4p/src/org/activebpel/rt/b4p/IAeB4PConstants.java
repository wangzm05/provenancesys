//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/IAeB4PConstants.java,v 1.6 2008/02/06 03:49:22 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p;

import javax.xml.namespace.QName;

import org.activebpel.rt.IAeConstants;

/**
 * BPEL-4-People constants.
 */
public interface IAeB4PConstants
{
   /** Default BPEL4People NS */
   public static final String B4P_NAMESPACE = "http://www.example.org/BPEL4People"; //$NON-NLS-1$
   public static final String B4P_PREFIX    = "b4p"; //$NON-NLS-1$
   
   public static final String AEB4P_NAMESPACE = "http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd"; //$NON-NLS-1$
   public static final String AEB4P_PREFIX = "aeb4p"; //$NON-NLS-1$

   /** B4P LGP constants */
   public static final String HI_STATE_ELEMENT="logicalPeopleGroups"; //$NON-NLS-1$
   
   /** name of the people assignments extension element */
   public static final QName PEOPLE_ASSIGNMENTS = new QName(B4P_NAMESPACE, "peopleAssignments");  //$NON-NLS-1$
   
   /** name of the implicit variable created to store b4p attachments */
   public static final String ATTACHMENTS_VARIABLE = "_peopleActivityAttachments"; //$NON-NLS-1$
   /** name of the type of implicit variable created to store b4p attachments */
   public static final QName ATTACHMENTS_VARIABLE_TYPE = new QName(IAeConstants.W3C_XML_SCHEMA, "anyType"); //$NON-NLS-1$
   
   public static final QName FAULT_NAME_TASK_EXPIRED = new QName(IAeB4PConstants.B4P_NAMESPACE, "taskExpired"); //$NON-NLS-1$
   public static final QName FAULT_NAME_NON_RECOVERABLE_ERROR = new QName(IAeB4PConstants.B4P_NAMESPACE, "nonRecoverableError"); //$NON-NLS-1$
}
