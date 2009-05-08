// $Header$
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2006 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.bpel.server.engine.storage.tamino.upgrade;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoConfig;
import org.activebpel.rt.bpel.server.engine.storage.upgrade.IAeStorageUpgrader;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl;

/**
 * A Tamino upgrader base class.  Upgraders are responsible for  
 */
public abstract class AeAbstractTaminoUpgrader extends AeAbstractTaminoStorageEx implements IAeStorageUpgrader
{
   /** The statement prefix for all statements used in this class. */
   private static final String STATEMENT_PREFIX = "Upgrade"; //$NON-NLS-1$

   /** The name of the schema being upgraded. */
   private String mSchemaName;
   
   /**
    * Creates a Tamino upgrader.
    */
   public AeAbstractTaminoUpgrader(String aSchemaName, IAeXMLDBStorageImpl aStorageImpl)
   {
      super(null, STATEMENT_PREFIX, aStorageImpl);
      setTaminoConfig(createTaminoConfig());
      setSchemaName(aSchemaName);
   }

   /**
    * Sets the config.
    * 
    * @param aConfig
    */
   protected void setTaminoConfig(AeTaminoConfig aConfig)
   {
      setXMLDBConfig(aConfig);
   }

   /**
    * Creates the Tamino config object.
    */
   protected abstract AeTaminoConfig createTaminoConfig();
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.upgrade.IAeStorageUpgrader#upgrade()
    */
   public abstract void upgrade() throws AeStorageException;

   /**
    * @return Returns the schemaName.
    */
   protected String getSchemaName()
   {
      return mSchemaName;
   }

   /**
    * @param aSchemaName The schemaName to set.
    */
   protected void setSchemaName(String aSchemaName)
   {
      mSchemaName = aSchemaName;
   }
}
