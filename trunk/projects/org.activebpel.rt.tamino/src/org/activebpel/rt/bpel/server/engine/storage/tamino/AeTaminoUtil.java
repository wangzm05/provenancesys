//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/AeTaminoUtil.java,v 1.6 2007/08/17 00:57:36 EWittmann Exp $
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

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBUtil;

/**
 * A class with some static methods to do some standard Tamino related operations.
 */
public class AeTaminoUtil extends AeXMLDBUtil
{
   /**
    * Commits a connection (only if it is a local tx connection).
    * 
    * @param aConnection
    */
   public static void commit(TConnection aConnection) throws AeXMLDBException
   {
      if (aConnection != null && aConnection instanceof IAeTaminoLocalTxConnection)
      {
         IAeTaminoLocalTxConnection c = (IAeTaminoLocalTxConnection) aConnection;
         c.commit();
      }
   }

   /**
    * Rolls back a connection (only if it is a local tx connection).
    * 
    * @param aConnection
    */
   public static void rollback(TConnection aConnection) throws AeXMLDBException
   {
      if (aConnection != null && aConnection instanceof IAeTaminoLocalTxConnection)
      {
         IAeTaminoLocalTxConnection c = (IAeTaminoLocalTxConnection) aConnection;
         c.rollback();
      }
   }

   /**
    * Closes a Tamino connection.
    * 
    * @param aConnection
    */
   public static void close(TConnection aConnection)
   {
      try
      {
         if (aConnection != null)
         {
            aConnection.close();
         }
      }
      catch (Throwable t)
      {
         AeException.logError(t, t.getLocalizedMessage());
      }
   }
}
