//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/tx/AeTaminoTransactionManagerFactory.java,v 1.4 2007/08/17 00:57:36 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2006 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.bpel.server.engine.storage.tamino.tx;

import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.tx.AeXMLDBTransactionManagerFactory;
import org.activebpel.rt.bpel.server.engine.transaction.IAeTransactionManager;

/**
 * Implements a transaction manager factory for the Tamino storage layer.
 */
public class AeTaminoTransactionManagerFactory extends AeXMLDBTransactionManagerFactory
{
   /**
    * Constructs transaction manager factory with the specified configuration.
    *
    * @param aConfigMap
    */
   public AeTaminoTransactionManagerFactory(Map aConfigMap)
   {
      super(aConfigMap);
   }

   /**
    * Overrides method to return a <code>AeTaminoTransactionManager</code> instance.
    * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransactionManagerFactory#createTransactionManager()
    */
   public IAeTransactionManager createTransactionManager()
   {
      return new AeTaminoTransactionManager();
   }
}
