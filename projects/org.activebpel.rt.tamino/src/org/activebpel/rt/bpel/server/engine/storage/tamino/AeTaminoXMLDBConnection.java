// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/AeTaminoXMLDBConnection.java,v 1.1 2007/08/17 00:57:35 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.bpel.server.engine.storage.tamino;

import com.softwareag.tamino.db.api.connection.TConnection;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection;

/**
 * Tamino implementation of an XMLDB connection.
 */
public class AeTaminoXMLDBConnection implements IAeXMLDBConnection
{
   /** The native Tamino connection. */
   private TConnection mTConnection;
   
   /**
    * C'tor.
    * 
    * @param aConnection
    */
   public AeTaminoXMLDBConnection(TConnection aConnection)
   {
      setTConnection(aConnection);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection#close()
    */
   public void close()
   {
      AeTaminoUtil.close(getTConnection());
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection#commit()
    */
   public void commit() throws AeXMLDBException
   {
      AeTaminoUtil.commit(getTConnection());
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection#rollback()
    */
   public void rollback() throws AeXMLDBException
   {
      AeTaminoUtil.rollback(getTConnection());
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection#getNativeConnection()
    */
   public Object getNativeConnection()
   {
      return getTConnection();
   }

   /**
    * @return Returns the tConnection.
    */
   protected TConnection getTConnection()
   {
      return mTConnection;
   }

   /**
    * @param aConnection the tConnection to set
    */
   protected void setTConnection(TConnection aConnection)
   {
      mTConnection = aConnection;
   }
}
