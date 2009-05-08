//$Header: /Development/AEDevelopment/projects/ddl.org.activebpel/src/org/activebpel/ddl/storage/sql/upgrade/AeSQLUpgrader1_0_8_4_QueuedReceiveTable.java,v 1.2 2005/10/17 20:43:08 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.ddl.storage.sql.upgrade;

import org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig;

/**
 * Generates the correlation hash for the duplicate receives.
 */
public class AeSQLUpgrader1_0_8_4_QueuedReceiveTable extends AeSQLUpgrader1_0_8_3_QueuedReceiveTable
{
   /**
    * Constructs a queue storage upgrader.
    * 
    * @param aUpgradeName
    * @param aSQLConfig
    */
   public AeSQLUpgrader1_0_8_4_QueuedReceiveTable(String aUpgradeName, AeSQLConfig aSQLConfig)
   {
      super(aUpgradeName, aSQLConfig);
   }
   
   /**
    * @see org.activebpel.ddl.storage.sql.upgrade.AeSQLUpgrader1_0_8_3_QueuedReceiveTable#getQueryName()
    */
   protected String getQueryName()
   {
      return IAeUpgraderSQLKeys.GET_DUPE_QUEUED_RECEIVES;
   }
}
