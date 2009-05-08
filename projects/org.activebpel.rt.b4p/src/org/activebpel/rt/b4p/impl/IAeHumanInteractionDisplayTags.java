//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/IAeHumanInteractionDisplayTags.java,v 1.2 2008/02/29 23:42:08 vvelusamy Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl;

/**
 * This interface defines display name tags for the outline view of a process on admin console
 */
public interface IAeHumanInteractionDisplayTags
{
   /** Human Interactions tags */
   public static final String HI_TAG = "humanInteractions"; //$NON-NLS-1$
   public static final String HI_DISPLAY_NAME = "Human Interactions"; //$NON-NLS-1$
   public static final String HI_ICON = "hi"; //$NON-NLS-1$
   /** Logical people groups tags */
   public static final String LPGS_TAG = "logicalPeopleGroups"; //$NON-NLS-1$
   public static final String LPGS_DISPLAY_NAME = "Logical People Groups"; //$NON-NLS-1$
   public static final String LPGS_ICON = "lpg"; //$NON-NLS-1$
   /** Logical People group tags*/
   public static final String LPG_ICON = "lpg"; //$NON-NLS-1$
   /** Tasks tags */
   public static final String TASKS_TAG = "tasks"; //$NON-NLS-1$
   public static final String TASKS_DISPLAY_NAME = "Tasks"; //$NON-NLS-1$
   public static final String TASKS_ICON = "task"; //$NON-NLS-1$
   /** Notifications tags */
   public static final String NOTIFICATIONS_TAG = "notifications"; //$NON-NLS-1$
   public static final String NOTIFICATIONS_DISPLAY_NAME = "Notifications"; //$NON-NLS-1$
   public static final String NOTIFICATIONS_ICON = "notification"; //$NON-NLS-1$
   /** Task tags */
   public static final String TASK_ICON = "task"; //$NON-NLS-1$
   /** Notification tags */
   public static final String NOTIFICATION_ICON = "notification"; //$NON-NLS-1$
   /** Deadlines tags */
   public static final String DEADLINES_TAG = "deadlines"; //$NON-NLS-1$
   public static final String DEADLINES_ICON = ""; //$NON-NLS-1$
   /** Start Deadline tags */
   public static final String START_DEADLINE_TAG = "startDeadline"; //$NON-NLS-1$
   public static final String START_DEADLINE_ICON = ""; //$NON-NLS-1$
   /** Completion Deadline tags */
   public static final String COMPLETTION_DEADLINE_TAG = "completionDeadline"; //$NON-NLS-1$
   public static final String COMPLETTION_DEADLINE_ICON = ""; //$NON-NLS-1$

   /** Escalation tags */
   public static final String ESCALATION_ICON = "escalation"; //$NON-NLS-1$

}
