//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/IAeGetTasksFilterGenericHumanRoles.java,v 1.1 2008/02/27 19:33:59 PJayanetti Exp $
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
 * Constants listing valid values for generic human roles that can be used when listing tasks.
 */
public interface IAeGetTasksFilterGenericHumanRoles
{
   // FIXMEPJ (PJ) find where used  (bpel, state machine, bunit and SQL queries => use camelcase element names
   //
   // Generic human roles
   //
   public static final String GHR_INITIATOR = "INITIATOR"; //$NON-NLS-1$
   public static final String GHR_STAKEHOLDERS = "STAKEHOLDERS"; //$NON-NLS-1$
   public static final String GHR_POTENTIAL_OWNERS = "POTENTIAL_OWNERS"; //$NON-NLS-1$
   public static final String GHR_ACTUAL_OWNER = "OWNER"; //$NON-NLS-1$
   public static final String GHR_EXCLUDED_OWNERS = "EXCLUDED_OWNERS"; //$NON-NLS-1$
   public static final String GHR_BUSINESS_ADMINISTRATORS = "ADMINISTRATORS"; //$NON-NLS-1$
   public static final String GHR_NOTIFICATION_RECIPIENTS = "NOTIFICATION_RECIPIENTS"; //$NON-NLS-1$

}
