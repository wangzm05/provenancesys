// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/sql/AeTaskSQLConfig.java,v 1.1 2008/02/02 19:11:35 PJayanetti Exp $
// ///////////////////////////////////////////////////////////////////////////
// PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc. Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
// ///////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig;

/**
 * Extension of <code>AeSQLConfig</code> to support task related sql files.
 */
public class AeTaskSQLConfig extends AeSQLConfig
{
   /**
    * Ctor takes db type and override map. Loads the task sql file.
    * 
    * @param aType
    * @param aOverrideMap
    */
   public AeTaskSQLConfig(String aType, Map aOverrideMap)
   {
      super(aType, aOverrideMap);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig#getStatementConfigFilenames()
    */
   protected List getStatementConfigFilenames()
   {
      // Note: not calling super.getStatementConfigFilenames() here because
      // we don't need/want access to the base statements found in common.xml.
      String taskSQL = "task-sql.xml"; //$NON-NLS-1$
      String dbSpecificTaskSQL = "task-" + mType + "-sql.xml"; //$NON-NLS-1$ //$NON-NLS-2$

      List list = new ArrayList();
      list.add(new AeFilenameClassTuple(taskSQL, AeTaskSQLConfig.class));
      list.add(new AeFilenameClassTuple(dbSpecificTaskSQL, AeTaskSQLConfig.class));

      return list;
   }
}
