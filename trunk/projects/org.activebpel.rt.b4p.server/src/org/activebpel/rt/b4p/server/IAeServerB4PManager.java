//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/IAeServerB4PManager.java,v 1.7 2008/02/26 01:59:22 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server;

import org.activebpel.rt.b4p.impl.engine.IAeB4PManager;
import org.activebpel.rt.b4p.server.storage.IAeTaskStorage;
import org.activebpel.rt.xml.schema.AeSchemaDuration;

/**
 * Defines the server runtime interface for the B4P manager.
 * This interface specifically provides access to the task store.
 */
public interface IAeServerB4PManager extends IAeB4PManager
{
   /** Engine config key for the task service. */
   public static final String CFG_KEY_TASK_SERVICE = "TaskService"; //$NON-NLS-1$
   /** Engine config key for the notification service. */
   public static final String CFG_KEY_NOTIFICATION_SERVICE = "NotificationService"; //$NON-NLS-1$
   /** Engine config key for the finalization duration */
   public static final String CFG_KEY_FINALIZATION_DURATION = "FinalizationDuration"; //$NON-NLS-1$
   /** Default value to use for task finalization if not specified in config */
   public static final String DEFAULT_FINALIZATION_DURATION = "P1D"; //$NON-NLS-1$

   /**
    * Returns the B4P task storage.
    */
   public IAeTaskStorage getTaskStorage();

   /**
    * Returns the amount of time to keep a task process running after the task
    * has reached a final state.
    */
   public AeSchemaDuration getTaskFinalizationDuration();
}
