//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/IAeGetTasksFilterStates.java,v 1.2 2008/02/29 01:22:56 PJayanetti Exp $
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
 * Constants listing valid states that can be used when listing tasks.
 */
public interface IAeGetTasksFilterStates
{
   /** String representing the created state. */
   public static String STATE_CREATED  = "CREATED"; //$NON-NLS-1$
   /** String representing the ready state. */
   public static String STATE_READY    = "READY"; //$NON-NLS-1$
   /** String representing the reserved state. */
   public static String STATE_RESERVED = "RESERVED"; //$NON-NLS-1$
   /** String representing the in progress (started) state. */
   public static String STATE_IN_PROGRESS = "IN_PROGRESS"; //$NON-NLS-1$
   /** String representing the completed state. */
   public static String STATE_COMPLETED = "COMPLETED"; //$NON-NLS-1$
   /** String representing the obsolete state. */
   public static String STATE_OBSOLETE = "OBSOLETE"; //$NON-NLS-1$
   /** String representing the error state. */
   public static String STATE_ERROR = "ERROR"; //$NON-NLS-1$
   /** String representing the failed state. */
   public static String STATE_FAILED = "FAILED"; //$NON-NLS-1$\
   /** String representing the exited state. */
   public static String STATE_EXITED = "EXITED"; //$NON-NLS-1$\
   /** String representing the exited state. */
   public static String STATE_SUSPENDED = "SUSPENDED"; //$NON-NLS-1$\

}
