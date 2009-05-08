//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/IAeTaminoLocalTxConnection.java,v 1.1 2007/08/17 00:57:35 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.bpel.server.engine.storage.tamino;

import com.softwareag.tamino.db.api.connection.TConnection;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;


/**
 * An interface defining methods required to impl a local tx (commit-able) Tamino
 * connection.
 */
public interface IAeTaminoLocalTxConnection extends TConnection
{
   /**
    * Commits the transaction.
    * 
    * @throws AeXMLDBException
    */
   public void commit() throws AeXMLDBException;

   /**
    * Rolls back the transaction.
    * 
    * @throws AeXMLDBException
    */
   public void rollback() throws AeXMLDBException;

}
