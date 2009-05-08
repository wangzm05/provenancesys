//$Header: /Development/AEDevelopment/projects/ddl.org.activebpel/src/org/activebpel/ddl/storage/sql/upgrade/AeUpgraderSQLConfig.java,v 1.1 2005/03/17 21:44:29 EWittmann Exp $
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
import org.activebpel.rt.bpel.server.engine.storage.sql.upgrade.AeAbstractUpgraderSQLConfig;

/**
 * A concrete impl of the abstract upgrader SQL config for ActiveBPEL.
 */
public class AeUpgraderSQLConfig extends AeAbstractUpgraderSQLConfig
{
   /**
    * Constructs the ActiveBPEL upgrader SQL config.
    * 
    * @param aDefaultConfig
    */
   public AeUpgraderSQLConfig(AeSQLConfig aDefaultConfig)
   {
      super(aDefaultConfig);
   }

}
