//$Header: /Development/AEDevelopment/projects/ddl.org.activebpel/src/org/activebpel/ddl/storage/sql/upgrade/IAeUpgraderSQLKeys.java,v 1.3 2005/10/17 20:43:08 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.ddl.storage.sql.upgrade;

/**
 * Constants for the storage upgrader SQL keys (keys into the SQLConfig object).
 */
public interface IAeUpgraderSQLKeys
{
   /** SQL statement key to get all alarms with bad deadlinemillis data (set to 0 after an upgrade). */
   public static final String GET_ALARMS_WITHBADDEADLINEMILLIS = "GetAlarmsWithBadDeadlineMillis"; //$NON-NLS-1$
   /** SQL statement key to update the DeadlineMillis column. */
   public static final String UPDATE_DEADLINEMILLIS = "UpdateDeadlineMillis"; //$NON-NLS-1$

   /** SQL statement key to get all queued receives. */
   public static final String GET_QUEUED_RECEIVES = "GetQueuedReceives"; //$NON-NLS-1$
   /** SQL statement key to update the DeadlineMillis column. */
   public static final String UPDATE_HASH_VALUES = "UpdateHashValues"; //$NON-NLS-1$

   /** SQL statement key to get all queued receives. */
   public static final String GET_DUPE_QUEUED_RECEIVES = "GetDupeQueuedReceives"; //$NON-NLS-1$
}
