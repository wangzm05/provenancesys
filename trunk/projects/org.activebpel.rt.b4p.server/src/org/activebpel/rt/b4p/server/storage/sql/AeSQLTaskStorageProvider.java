// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/sql/AeSQLTaskStorageProvider.java,v 1.6 2008/03/03 19:49:54 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.server.storage.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.server.storage.AeGenericHumanRoleSerializer;
import org.activebpel.rt.b4p.server.storage.AeGenericHumanRoles;
import org.activebpel.rt.b4p.server.storage.AeTaskACLEntry;
import org.activebpel.rt.b4p.server.storage.AeTaskStates;
import org.activebpel.rt.b4p.server.storage.AeTaskTypes;
import org.activebpel.rt.b4p.server.storage.AeTrtTaskInstanceDeserializer;
import org.activebpel.rt.b4p.server.storage.IAeTaskStorageProvider;
import org.activebpel.rt.b4p.server.storage.filter.AeTaskFilter;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageConfig;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeAbstractSQLStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeDbUtils;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig;
import org.activebpel.rt.ht.api.AeHtApiTask;
import org.activebpel.rt.ht.api.IAeHtApiTaskList;
import org.activebpel.rt.ht.def.AeGroupDef;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.AeUserDef;
import org.activebpel.rt.util.AeCloser;
import org.activebpel.rt.util.AeUtil;
import org.apache.commons.dbutils.ResultSetHandler;
import org.w3c.dom.Element;

/**
 * A SQL impl of a task storage provider.
 */
public class AeSQLTaskStorageProvider extends AeAbstractSQLStorageProvider implements IAeTaskStorageProvider
{
   /** The SQL statement prefix for all SQL statements used in this class. */
   public static final String SQLSTATEMENT_PREFIX = "TaskStorage."; //$NON-NLS-1$

   /** Order-by priority and then creation time. */
   public static final String SQL_ORDER_BY_PRIORITY_CTIME = " ORDER BY " + IAeTaskColumns.PRIORITY + " ASC, " + IAeTaskColumns.CREATION_TIME_MILLIS + " DESC"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

   /**
    * Constructs a SQL task storage delegate given a engine config and
    * SQL config objects.
    *
    * @param aEngineConfig
    * @param aConfig
    */
   public AeSQLTaskStorageProvider(Map aEngineConfig, AeStorageConfig aConfig)
   {
      super(SQLSTATEMENT_PREFIX,
            new AeTaskSQLConfig(
                  ((AeSQLConfig) aConfig).getDatabaseType(),
                  aConfig.getConstantOverrides()));
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.IAeTaskStorageProvider#insertTask(long, org.w3c.dom.Element)
    */
   public void insertTask(long aProcessId, Element aTaskInstanceElement) throws AeStorageException
   {
      // Use a commit control connection because the insertTaskDataInternal()
      // method inserts multiple rows of data.
      Connection connection = getCommitControlConnection();
      try
      {
         AeHtApiTask task = AeTrtTaskInstanceDeserializer.INSTANCE.deserialize(aProcessId, aTaskInstanceElement);
         insertTaskDataInternal(task, connection);
         connection.commit();
      }
      catch (Exception e)
      {
         try
         {
            connection.rollback();
         }
         catch (SQLException ex)
         {
            AeException.logError(ex);
         }
         throw new AeStorageException(e);
      }
      finally
      {
         AeCloser.close(connection);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.IAeTaskStorageProvider#updateTask(long, org.w3c.dom.Element)
    */
   public void updateTask(long aProcessId, Element aTaskInstanceElement) throws AeStorageException
   {
      Connection connection = getCommitControlConnection();
      try
      {
         AeHtApiTask task = AeTrtTaskInstanceDeserializer.INSTANCE.deserialize(aProcessId, aTaskInstanceElement);
         removeTaskInternal(task.getProcessId(), connection);
         insertTaskDataInternal(task, connection);
         connection.commit();
      }
      catch (Exception e)
      {
         try
         {
            connection.rollback();
         }
         catch (SQLException ex)
         {
            AeException.logError(ex);
         }
         throw new AeStorageException(e);
      }
      finally
      {
         AeCloser.close(connection);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.IAeTaskStorageProvider#deleteTask(long)
    */
   public void deleteTask(long aProcessId) throws AeStorageException
   {
      Connection connection = getCommitControlConnection();
      try
      {
         removeTaskInternal(aProcessId, connection);
         connection.commit();
      }
      catch (Exception e)
      {
         try
         {
            connection.rollback();
         }
         catch (SQLException ex)
         {
            AeException.logError(ex);
         }
         throw new AeStorageException(e);
      }
      finally
      {
         AeCloser.close(connection);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.IAeTaskStorageProvider#listTasks(org.activebpel.rt.b4p.server.storage.filter.AeTaskFilter)
    */
   public IAeHtApiTaskList listTasks(AeTaskFilter aFilter) throws AeStorageException
   {
      try
      {
         AeSQLTaskFilter filter = createFilter(aFilter);
         String sql = filter.getSelectStatement();
         Object[] params = filter.getParams();
         // Construct a ResultSetHandler that converts the ResultSet to an AeTaskListResult.
         ResultSetHandler handler = createTaskListResultSetHandler(aFilter);
         // Run the query.
         return (IAeHtApiTaskList) getQueryRunner().query(sql, params, handler);
      }
      catch (SQLException ex)
      {
         throw new AeStorageException(ex);
      }
   }

   /**
    * Creates and returns ACL list.
    * @param aTaskData
    */
   protected List createAclList(AeHtApiTask aTaskData)
   {
      List rval = new ArrayList();
      rval.addAll( createAclList(AeGenericHumanRoles.GHR_CODE_POTENTIAL_OWNERS, aTaskData.getPotentialOwners() ) );
      rval.addAll( createAclList(AeGenericHumanRoles.GHR_CODE_NOTIFICATION_RECIPIENTS, aTaskData.getNotificationRecipients() ) );
      rval.addAll( createAclList(AeGenericHumanRoles.GHR_CODE_BUSINESS_ADMINISTRATORS, aTaskData.getBusinessAdministrators() ) );
      rval.addAll( createAclList(AeGenericHumanRoles.GHR_CODE_STAKEHOLDERS, aTaskData.getTaskStakeholders() ) );
      return rval;
   }

   /**
    * Creates and returns ACL list for given AeOrganizationalEntityDef.
    * @param aGenericHumanRoleType
    * @param aOrgEntityDef
    */
   protected List createAclList(int aGenericHumanRoleType, AeOrganizationalEntityDef aOrgEntityDef)
   {
      if (aOrgEntityDef == null)
      {
         return Collections.EMPTY_LIST;
      }

      List rval = new ArrayList();
      // create acl for groups, if any.
      if (aOrgEntityDef.getGroups() != null && aOrgEntityDef.getGroups().size() > 0)
      {
         Iterator it = aOrgEntityDef.getGroups().getGroupDefs();
         while ( it.hasNext() )
         {
            AeTaskACLEntry entry = new AeTaskACLEntry();
            entry.setName(( (AeGroupDef) it.next()).getValue() );
            entry.setType(AeTaskACLEntry.GROUP);
            entry.setGenericHumanRole(aGenericHumanRoleType);
            rval.add(entry);
         }
      }
      else
      {
         // default choice is users.
         if (aOrgEntityDef.getUsers() != null)
         {
            Iterator it = aOrgEntityDef.getUsers().getUserDefs();
            while ( it.hasNext() )
            {
               AeTaskACLEntry entry = new AeTaskACLEntry();
               entry.setName(( (AeUserDef) it.next()).getValue() );
               entry.setType(AeTaskACLEntry.USER);
               entry.setGenericHumanRole(aGenericHumanRoleType);
               rval.add(entry);
            }
         }
      }
      return rval;
   }

   /**
    * Inserts task data into the DB.
    *
    * @param aTaskData
    * @param aConnection
    * @throws AeStorageException
    */
   protected void insertTaskDataInternal(AeHtApiTask aTaskData, Connection aConnection) throws AeStorageException
   {
      insertTask(aConnection, aTaskData);
      List aclList = createAclList(aTaskData);
      long pid = aTaskData.getProcessId();
      insertTaskACL(aConnection, aclList, pid);
      
      // Add excluded owners
      List excludedOwners = createExcludedOwnersACL(aTaskData);
      insertTaskACL(aConnection, excludedOwners, pid);

      // Add the owner as an ACL entry with generic human role of ACTUAL_OWNER
      String owner = aTaskData.getActualOwnerAsString();
      if (AeUtil.notNullOrEmpty(owner))
      {
         AeTaskACLEntry entry = new AeTaskACLEntry();
         entry.setName(owner);
         entry.setType(AeTaskACLEntry.USER);
         entry.setExcludeFlag(false);
         entry.setGenericHumanRole(AeGenericHumanRoles.GHR_CODE_ACTUAL_OWNER);
         insertTaskACL(aConnection, aTaskData.getProcessId(), entry);
      }

      String initiator = aTaskData.getTaskInitiatorAsString();
      if (AeUtil.notNullOrEmpty(initiator))
      {
         AeTaskACLEntry entry = new AeTaskACLEntry();
         entry.setName(initiator);
         entry.setType(AeTaskACLEntry.USER);
         entry.setExcludeFlag(false);
         entry.setGenericHumanRole(AeGenericHumanRoles.GHR_CODE_INITIATOR);
         insertTaskACL(aConnection, aTaskData.getProcessId(), entry);
      }
   }

   /**
    * Creates ACL entries for excluded owners
    * @param aTaskData
    */
   private List createExcludedOwnersACL(AeHtApiTask aTaskData)
   {
      List excludedOwners = createAclList(AeGenericHumanRoles.GHR_CODE_EXCLUDED_OWNERS, aTaskData.getExcludedOwners() );
      for (Iterator it = excludedOwners.iterator(); it.hasNext();)
      {
         AeTaskACLEntry entry = (AeTaskACLEntry) it.next();
         entry.setExcludeFlag(true);
      }
      return excludedOwners;
   }

   /**
    * Inserts the task acl's in the list
    * @param aConnection
    * @param aAclList
    * @param aPid
    * @throws AeStorageException
    */
   protected void insertTaskACL(Connection aConnection, List aAclList, long aPid)
         throws AeStorageException
   {
      for (Iterator iter = aAclList.iterator(); iter.hasNext(); )
      {
         AeTaskACLEntry entry = (AeTaskACLEntry) iter.next();
         insertTaskACL(aConnection, aPid, entry);
      }
   }

   /**
    * Inserts a task into the Task table.
    *
    * @param aConnection
    * @param aTask
    * @throws AeStorageException
    */
   protected void insertTask(Connection aConnection, AeHtApiTask aTask) throws AeStorageException
   {
      Object[] params = new Object[] {
            new Long(aTask.getProcessId()),
            aTask.getName().getLocalPart(),
            getStringOrSqlNullVarchar(aTask.getName().getNamespaceURI()),
            AeUtil.isNullOrEmpty(aTask.getPresentationName()) ? "(none)" : aTask.getPresentationName(), //$NON-NLS-1$
            AeUtil.getSafeString(aTask.getPresentationSubject()),
            getDateAsLongOrSqlNull(aTask.getCreatedOn()),
            AeTaskStates.getTaskStateCode(aTask.getStatus()),
            new Integer(aTask.getPriority()),
            getStringOrSqlNullVarcharNoEmpty(aTask.getActualOwnerAsString()),
            getDateAsLongOrSqlNull(aTask.getCompleteBy()),
            getDateAsLongOrSqlNull(aTask.getModifiedTime()),
            getDateAsLongOrSqlNull(aTask.getExpirationTime()),
            new Integer(AeDbUtils.convertBooleanToInt(aTask.isHasAttachments())),
            new Integer(AeDbUtils.convertBooleanToInt(aTask.isHasComments())),
            getDateAsLongOrSqlNull(aTask.getEscalationTime()),
            new Integer(AeDbUtils.convertBooleanToInt(aTask.isRenderingMethodExists())),
            aTask.getCreatedBy(),
            getStringOrSqlNullVarchar(aTask.getTaskInitiatorAsString()),
            getStringOrSqlNullVarchar( serializeAsXmlString("taskStakeholders", aTask.getTaskStakeholders()) ), //$NON-NLS-1$
            getStringOrSqlNullVarchar( serializeAsXmlString("potentialOwners", aTask.getPotentialOwners()) ), //$NON-NLS-1$
            getStringOrSqlNullVarchar( serializeAsXmlString("businessAdministrators", aTask.getBusinessAdministrators()) ), //$NON-NLS-1$
            getStringOrSqlNullVarchar( serializeAsXmlString("notificationRecipients", aTask.getNotificationRecipients()) ),  //$NON-NLS-1$
            new Integer(AeDbUtils.convertBooleanToInt(aTask.isHasOutput())),
            new Integer(AeDbUtils.convertBooleanToInt(aTask.isHasFault())),
            new Integer(AeDbUtils.convertBooleanToInt(aTask.isSkipable())),
            getStringOrSqlNullVarchar(aTask.getPrimarySearchBy()),
            getDateAsLongOrSqlNull(aTask.getStartBy()),
            getDateAsLongOrSqlNull(aTask.getCompleteBy()),
            AeTaskTypes.getTaskTypeCode(aTask.getTaskType()),
            getDateAsLongOrSqlNull(aTask.getActivationTime()),
      };
      update(aConnection, IAeTaskSQLKeys.INSERT_TASK, params);
   }

   /**
    * Inserts a row into the Task ACL table.
    *
    * @param aConnection
    * @param aTaskACLEntry
    * @throws AeStorageException
    */
   protected void insertTaskACL(Connection aConnection, long aProcessId, AeTaskACLEntry aTaskACLEntry) throws AeStorageException
   {
      Object[] params = new Object[] {
            //ProcessId, Name, Type, ExcludeFlag, GenericHumanRole
            new Long(aProcessId),
            aTaskACLEntry.getName(),
            new Integer(aTaskACLEntry.getType()),
            new Boolean(aTaskACLEntry.isExcludeFlag()),
            new Integer(aTaskACLEntry.getGenericHumanRole())
      };
      update(aConnection, IAeTaskSQLKeys.INSERT_TASK_ACL, params);
   }

   /**
    * Removes a task by process ID.
    *
    * @param aProcessId
    * @param aConnection
    * @throws AeStorageException
    */
   protected void removeTaskInternal(long aProcessId, Connection aConnection) throws AeStorageException
   {
      Object[] params = { new Long(aProcessId) };
      update(aConnection, IAeTaskSQLKeys.DELETE_TASK, params);
   }

   /**
    * Serializes Organizational Entity as a xml string if it exists or null otherwise.
    * @param aGenericRole
    * @param aOrgEntityDef
    * @return xml string
    * @throws AeStorageException
    */
   protected String serializeAsXmlString(String aGenericRole, AeOrganizationalEntityDef aOrgEntityDef) throws AeStorageException
   {
      if (aOrgEntityDef != null)
      {
         try
         {
            AeGenericHumanRoleSerializer ser = new AeGenericHumanRoleSerializer(aGenericRole, aOrgEntityDef);
            return ser.serializeAsXmlString();
         }
         catch(Exception e)
         {
            throw new AeStorageException(e);
         }
      }
      else
      {
         return null;
      }
   }

   /**
    * Creates a SQL filter from the given task filter.
    *
    * @param aFilter
    * @throws AeStorageException
    */
   protected AeSQLTaskFilter createFilter(AeTaskFilter aFilter) throws AeStorageException
   {
      return new AeSQLTaskFilter(aFilter, getSQLConfig());
   }

   /**
    * Creates a SQL result set handler fromthe given task filter.
    *
    * @param aFilter
    * @throws AeStorageException
    */
   protected ResultSetHandler createTaskListResultSetHandler(AeTaskFilter aFilter) throws AeStorageException
   {
      return new AeSQLTaskListResultSetHandler(aFilter);
   }

}
