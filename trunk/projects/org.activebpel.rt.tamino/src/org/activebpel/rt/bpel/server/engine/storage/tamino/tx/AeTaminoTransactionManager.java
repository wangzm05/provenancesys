//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/tx/AeTaminoTransactionManager.java,v 1.4 2007/08/17 00:57:36 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2006 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.bpel.server.engine.storage.tamino.tx;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.tx.AeXMLDBTransactionManager;
import org.activebpel.rt.bpel.server.engine.transaction.AeTransactionException;
import org.activebpel.rt.bpel.server.engine.transaction.IAeTransaction;

/**
 * Transaction manager implementation for the Tamino storage.
 */
public class AeTaminoTransactionManager extends AeXMLDBTransactionManager
{
   /**
    * Overrides method to return a Tamino implementation.
    * 
    * @see org.activebpel.rt.bpel.server.engine.transaction.AeTransactionManager#createTransaction()
    */
   protected IAeTransaction createTransaction() throws AeTransactionException
   {
      return new AeTaminoTransaction();
   }
}
