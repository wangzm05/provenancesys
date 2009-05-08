// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/IAeTaskFilterConstants.java,v 1.4 2008/03/19 19:30:59 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.server.storage;

/**
 * Some constants used by the task filter.
 */
public interface IAeTaskFilterConstants
{
   public static final String COLUMN_OWNER = "Owner"; //$NON-NLS-1$
   public static final String COLUMN_MODIFIED_ON = "ModifiedOn"; //$NON-NLS-1$
   public static final String COLUMN_TARGET_NS = "TargetNS"; //$NON-NLS-1$

   public static final String COLUMN_ID = "ID"; //$NON-NLS-1$
   public static final String COLUMN_TASK_TYPE = "TaskType"; //$NON-NLS-1$
   public static final String COLUMN_NAME = "Name"; //$NON-NLS-1$
   public static final String COLUMN_STATUS = "Status"; //$NON-NLS-1$
   public static final String COLUMN_PRIORITY = "Priority"; //$NON-NLS-1$
   public static final String COLUMN_USER_ID = "UserId"; //$NON-NLS-1$
   public static final String COLUMN_GROUP = "Group"; //$NON-NLS-1$
   public static final String COLUMN_GENERIC_HUMAN_ROLE = "GenericHumanRole"; //$NON-NLS-1$
   public static final String COLUMN_CREATED_ON = "CreatedOn"; //$NON-NLS-1$
   public static final String COLUMN_ACTIVATION_TIME = "ActivationTime"; //$NON-NLS-1$
   public static final String COLUMN_EXPIRATION_TIME = "ExpirationTime"; //$NON-NLS-1$
   public static final String COLUMN_SKIPABLE = "Skipable"; //$NON-NLS-1$
   public static final String COLUMN_START_BY = "StartBy"; //$NON-NLS-1$
   public static final String COLUMN_COMPLETE_BY = "CompleteBy"; //$NON-NLS-1$
   public static final String COLUMN_PRES_NAME = "PresName"; //$NON-NLS-1$
   public static final String COLUMN_PRES_SUBJECT = "PresSubject"; //$NON-NLS-1$
   public static final String COLUMN_RENDERING_METH_NAME = "RenderingMethName"; //$NON-NLS-1$
   public static final String COLUMN_FAULT_MESSAGE = "FaultMessage"; //$NON-NLS-1$
   public static final String COLUMN_INPUT_MESSAGE = "InputMessage"; //$NON-NLS-1$
   public static final String COLUMN_OUTPUT_MESSAGE = "OutputMessage"; //$NON-NLS-1$
   public static final String COLUMN_ATTACHMENT_NAME = "AttachmentName"; //$NON-NLS-1$
   public static final String COLUMN_ATTACHMENT_TYPE = "AttachmentType"; //$NON-NLS-1$
   public static final String COLUMN_ESCALATED = "Escalated"; //$NON-NLS-1$
   public static final String COLUMN_PRIMARY_SEARCH_BY = "PrimarySearchBy"; //$NON-NLS-1$

   public static final String DIRECTION_ASC = "ASC"; //$NON-NLS-1$
   public static final String DIRECTION_DESC = "DESC"; //$NON-NLS-1$

   public static final String OPERATOR_EQ = "="; //$NON-NLS-1$
   public static final String OPERATOR_NEQ = "<>"; //$NON-NLS-1$
   public static final String OPERATOR_LT = "<"; //$NON-NLS-1$
   public static final String OPERATOR_GT = ">"; //$NON-NLS-1$
   public static final String OPERATOR_LTE = "<="; //$NON-NLS-1$
   public static final String OPERATOR_GTE = ">="; //$NON-NLS-1$
   public static final String OPERATOR_LIKE = "LIKE"; //$NON-NLS-1$

   public static final String OPERATOR_OR = "OR"; //$NON-NLS-1$
   public static final String OPERATOR_AND = "AND"; //$NON-NLS-1$
}
