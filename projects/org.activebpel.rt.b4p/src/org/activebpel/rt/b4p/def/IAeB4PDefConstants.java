// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/IAeB4PDefConstants.java,v 1.6 2007/12/14 01:17:46 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def;

import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.ht.def.IAeHtDefConstants;

/**
 * Contains the list of BPEL4People Constants.
 */
public interface IAeB4PDefConstants extends IAeHtDefConstants, IAeB4PConstants
{   
   
   /** The BPEL4People schema file name. */
   public static final String B4P_SCHEMA_RESOURCE         = "/ws-bpel4people.xsd";  //$NON-NLS-1$
   
   // BPEL4People element constants
   public static final String TAG_ATTACHMENT_PROPAGATION  = "attachmentPropagation"; //$NON-NLS-1$
   public static final String TAG_DEFER_ACTIVATION        = "deferActivation"; //$NON-NLS-1$
   public static final String TAG_FROM_PROCESS            = "fromProcess"; //$NON-NLS-1$
   public static final String TAG_EXPIRATION              = "expiration"; //$NON-NLS-1$
   public static final String TAG_LOCAL_TASK              = "localTask"; //$NON-NLS-1$
   public static final String TAG_PEOPLE_ACTIVITY         = "peopleActivity"; //$NON-NLS-1$
   public static final String TAG_PROCESS_INITIATOR       = "processInitiator"; //$NON-NLS-1$
   public static final String TAG_PROCESS_STAKE_HOLDERS   = "processStakeholders"; //$NON-NLS-1$
   public static final String TAG_REMOTE_NOTIFICATION     = "remoteNotification"; //$NON-NLS-1$
   public static final String TAG_REMOTE_TASK             = "remoteTask"; //$NON-NLS-1$
   public static final String TAG_SCHEDULED_ACTIONS       = "scheduledActions"; //$NON-NLS-1$
   public static final String TAG_TO_PROCESS              = "toProcess"; //$NON-NLS-1$
   
   public static final String ATTR_SKIPABLE               = "isSkipable"; //$NON-NLS-1$
   public static final String ATTR_INPUTVARIABLE          = "inputVariable"; //$NON-NLS-1$
   public static final String ATTR_OUTPUTVARIABLE         = "outputVariable"; //$NON-NLS-1$
}
