//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/sql/IAeTaskColumns.java,v 1.2 2008/02/07 22:52:13 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage.sql;

/**
 * Defines constants for task column names.
 */
public interface IAeTaskColumns
{
   public static final String PROCESS_ID = "ProcessId"; //$NON-NLS-1$
   public static final String NAME = "Name"; //$NON-NLS-1$
   public static final String TARGET_NAMESPACE = "TargetNamespace"; //$NON-NLS-1$
   public static final String PRESENTATION_NAME = "PresentationName"; //$NON-NLS-1$
   public static final String SUMMARY = "Summary"; //$NON-NLS-1$
   public static final String CREATION_TIME_MILLIS = "CreationTimeMillis"; //$NON-NLS-1$
   public static final String STATE = "State"; //$NON-NLS-1$
   public static final String PRIORITY = "Priority"; //$NON-NLS-1$
   public static final String OWNER = "Owner"; //$NON-NLS-1$
   public static final String COMPLETION_TIME_MILLIS = "CompletionTimeMillis"; //$NON-NLS-1$
   public static final String LAST_MODIFIED_TIME_MILLIS = "LastModifiedTimeMillis"; //$NON-NLS-1$
   public static final String EXPIRATION_DATE_MILLIS = "ExpirationDateMillis"; //$NON-NLS-1$
   public static final String HAS_ATTACHMENTS = "HasAttachments"; //$NON-NLS-1$
   public static final String HAS_COMMENTS = "HasComments"; //$NON-NLS-1$
   public static final String LAST_ESCALATED_TIME_MILLIS = "LastEscalatedTimeMillis"; //$NON-NLS-1$
   public static final String HAS_RENDERINGS = "HasRenderings"; //$NON-NLS-1$
   public static final String CREATED_BY = "CreatedBy"; //$NON-NLS-1$
   public static final String TASK_INITIATOR = "TaskInitiator"; //$NON-NLS-1$
   public static final String TASK_STAKEHOLDERS = "TaskStakeholders"; //$NON-NLS-1$
   public static final String POTENTIAL_OWNERS = "PotentialOwners"; //$NON-NLS-1$
   public static final String BUSINESS_ADMINS = "BusinessAdministrators"; //$NON-NLS-1$
   public static final String NOTIFICATION_RECIPIENTS = "NotificationRecipients"; //$NON-NLS-1$
   public static final String HAS_OUTPUT = "HasOutput"; //$NON-NLS-1$
   public static final String HAS_FAULT = "HasFault"; //$NON-NLS-1$
   public static final String IS_SKIPABLE = "IsSkipable"; //$NON-NLS-1$
   public static final String PRIMARY_SEARCH_BY = "PrimarySearchBy"; //$NON-NLS-1$
   public static final String START_BY_MILLIS = "StartByMillis"; //$NON-NLS-1$
   public static final String COMPLETE_BY_MILLIS = "CompleteByMillis"; //$NON-NLS-1$
   public static final String TASK_TYPE = "TaskType"; //$NON-NLS-1$
   public static final String ACTIVATION_TIME_MILLIS = "ActivationTimeMillis"; //$NON-NLS-1$
   
   public static final String TYPE = "Type"; //$NON-NLS-1$
   public static final String EXCLUDE_FLAG = "ExcludeFlag"; //$NON-NLS-1$

}
