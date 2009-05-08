//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/tx/AeTaminoTransaction.java,v 1.5 2007/08/17 00:57:36 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2006 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.bpel.server.engine.storage.tamino.tx;

import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoDataSource;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.tx.AeXMLDBTransaction;

/**
 * Transaction implementation for Tamino.
 */
public class AeTaminoTransaction extends AeXMLDBTransaction
{
   /**
    * C'tor.
    */
   public AeTaminoTransaction()
   {
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.tx.AeXMLDBTransaction#getXMLDBDataSource()
    */
   protected IAeXMLDBDataSource getXMLDBDataSource()
   {
      return AeTaminoDataSource.MAIN;
   }
}
