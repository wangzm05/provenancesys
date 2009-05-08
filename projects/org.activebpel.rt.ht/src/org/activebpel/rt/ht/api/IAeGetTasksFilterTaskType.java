//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/IAeGetTasksFilterTaskType.java,v 1.1 2008/02/29 01:22:56 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api;

/**
 * Constants for task type.
 */
public interface IAeGetTasksFilterTaskType
{
   /** Task type - list boths tasks and notifications. */
   public static String TASKTYPE_ALL = "ALL"; //$NON-NLS-1$
   /** List only tasks */
   public static String TASKTYPE_TASKS = "TASKS"; //$NON-NLS-1$
   /** List only notifications */
   public static String TASKTYPE_NOTIFICATIONS = "NOTIFICATIONS"; //$NON-NLS-1$
}
