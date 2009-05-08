// $Header$
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2006 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.ddl.storage.tamino.upgrade;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoConfig;
import org.activebpel.rt.bpel.server.engine.storage.tamino.upgrade.AeAbstractTaminoUpgrader;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl;

/**
 * A Tamino upgrader that undefines/drops the AeReceivedItem doc type.
 */
public class AeTaminoReceivedItemUpgrader2_1 extends AeAbstractTaminoUpgrader
{
   /**
    * Default constructor.
    * 
    * @param aSchemaName
    * @param aStorageImpl
    */
   public AeTaminoReceivedItemUpgrader2_1(String aSchemaName, IAeXMLDBStorageImpl aStorageImpl)
   {
      super(aSchemaName, aStorageImpl);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.tamino.upgrade.AeAbstractTaminoUpgrader#createTaminoConfig()
    */
   protected AeTaminoConfig createTaminoConfig()
   {
      return null;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.tamino.upgrade.AeAbstractTaminoUpgrader#upgrade()
    */
   public void upgrade() throws AeStorageException
   {
      undefineReceivedItemDocType();
   }

   /**
    * Undefines the AeReceivedItem doc type.
    * 
    * @throws AeStorageException
    */
   protected void undefineReceivedItemDocType() throws AeStorageException
   {
      undefineDocType(getCollectionName(), getSchemaName(), "AeReceivedItem"); //$NON-NLS-1$
   }
}
