// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/AeAbstractSQLStorage.java,v 1.9 2005/03/17 21:11:57 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.bpel.server.engine.storage.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;

import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.util.AeCloser;
import org.activebpel.rt.util.AeUtil;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * Base class for SQL storage objects that extract sql statement from
 * <code>AeSQLConfig</code> object.
 */
abstract public class AeAbstractSQLStorage extends AeSQLObject
{
   /** SQL statement repository. */
   protected AeSQLConfig mConfig;
   /** SQL key prefix. */
   protected String mPrefix;
   
   /**
    * Constructor.
    * @param aConfig
    */
   protected AeAbstractSQLStorage( String aPrefix, AeSQLConfig aConfig )
   {
      mPrefix = aPrefix;
      mConfig = aConfig;
   }
   
   /**
    * Returns a SQL statement from the SQL configuration object. This
    * convenience method prepends the name of the statement with
    * "ProcessStorage.".
    *
    * @param aStatementName The name of the statement, such as "InsertProcess".
    * @return The SQL statement found for that name.
    * @throws AeStorageException If the SQL statement is not found.
    */
   protected String getSQLStatement(String aStatementName) 
   throws AeStorageException
   {
      String key = getPrefix() + aStatementName;
      String sql = getSQLConfig().getSQLStatement(key);

      if (AeUtil.isNullOrEmpty(sql))
      {
         throw new AeStorageException(MessageFormat.format(AeMessages.getString("AeAbstractSQLStorage.ERROR_0"), new Object[] {key})); //$NON-NLS-1$
      }
      return sql;
   }
   
   /**
    * Return sql key prefix.
    */
   protected String getPrefix()
   {
      return mPrefix;
   }
   
   /**
    * Accessor for sql config.
    */
   protected AeSQLConfig getSQLConfig()
   {
      return mConfig;
   }
   
   /**
    * Executes the udpate sql bound to the name provided and using the single param
    * @param aQueryName
    * @param aObject
    * @throws AeStorageException
    */
   protected int update(String aQueryName, Object aObject) throws AeStorageException
   {
      if (aObject instanceof Object[])
      {
         return update(aQueryName, (Object[])aObject);
      }
      Object[] params = {aObject};
      return update(aQueryName, params);
   }
   
   /**
    * Executes the update sql bound to the name provided and using the params
    * passed in.
    * @param aQueryName
    * @param aParams
    * @throws AeStorageException
    */
   protected int update(String aQueryName, Object[] aParams) throws AeStorageException
   {
      Connection conn = null;
      try
      {
         conn = getConnection();
         return update(conn, aQueryName, aParams);
      }
      finally
      {
         AeCloser.close(conn);
      }
   }

   /**
    * Executes the update with the provided connection. This method will NOT close
    * the connection.
    * @param aConnection It is the caller's responsibility to close the connection.
    * @param aQueryName
    * @param aParams
    * @throws AeStorageException
    */
   protected int update(Connection aConnection, String aQueryName, Object[] aParams) throws AeStorageException
   {
      try
      {
         String stmt = getSQLStatement(aQueryName);
         //By wangzm:
         System.out.println("***** update(): stmt="+stmt);
         
         return getQueryRunner().update(aConnection, stmt,aParams);
      }
      catch( SQLException sql )
      {
         throw new AeStorageException(AeMessages.getString("AeAbstractSQLStorage.ERROR_2") + aQueryName, sql); //$NON-NLS-1$
      }
   }
   
   /**
    * Executes the query with a connection that gets closed immediately after execution.
    * @param aQueryName
    * @param aParams
    * @param aHandler
    * @throws AeStorageException
    */
   protected Object query(String aQueryName, Object[] aParams, ResultSetHandler aHandler) throws AeStorageException
   {
      Connection conn = null;
      try
      {
         conn = getConnection();
         return query(conn, aQueryName, aParams, aHandler);
      }
      finally
      {
         AeCloser.close(conn);
      }
   }

   /**
    * Executes the query with the provided connection, leaving it up to the caller
    * to determine when to close the connection.
    * @param aConn
    * @param aQueryName
    * @param aParams
    * @param aHandler
    * @throws AeStorageException
    */
   protected Object query(Connection aConn, String aQueryName, Object[] aParams, ResultSetHandler aHandler) throws AeStorageException
   {
      try
      {
         String stmt = getSQLStatement(aQueryName);
         return getQueryRunner().query(aConn,stmt,aParams,aHandler);
      }
      catch( SQLException sql )
      {
         throw new AeStorageException(AeMessages.getString("AeAbstractSQLStorage.ERROR_2") + aQueryName, sql); //$NON-NLS-1$
      }
   }
   
   /**
    * Convenience method for executing a query
    * @param aQueryName
    * @param aParam
    * @param aHandler
    * @throws AeStorageException
    */
   protected Object query(String aQueryName, Object aParam, ResultSetHandler aHandler)
         throws AeStorageException
   {
      return query(aQueryName, new Object[] { aParam }, aHandler);
   }

   /**
    * @return Returns the config.
    */
   protected AeSQLConfig getConfig()
   {
      return mConfig;
   }

   /**
    * @param aConfig The config to set.
    */
   protected void setConfig(AeSQLConfig aConfig)
   {
      mConfig = aConfig;
   }

   /**
    * @param aPrefix The prefix to set.
    */
   protected void setPrefix(String aPrefix)
   {
      mPrefix = aPrefix;
   }
}
