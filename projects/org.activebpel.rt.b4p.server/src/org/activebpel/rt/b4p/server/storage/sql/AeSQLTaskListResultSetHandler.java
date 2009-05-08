//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/sql/AeSQLTaskListResultSetHandler.java,v 1.4 2008/02/27 19:23:11 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.IAeProcessTaskConstants;
import org.activebpel.rt.b4p.server.storage.AeGenericHumanRoleDeserializer;
import org.activebpel.rt.b4p.server.storage.AeTaskStates;
import org.activebpel.rt.b4p.server.storage.AeTaskTypes;
import org.activebpel.rt.b4p.server.storage.filter.AeTaskFilter;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeDbUtils;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeListingResultSetHandler;
import org.activebpel.rt.ht.api.AeHtApiTask;
import org.activebpel.rt.ht.api.AeHtApiTaskList;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;


/**
 * Helper class to convert a <code>ResultSet</code> to an
 * <code>AeTaskListResult</code>.
 */
public class AeSQLTaskListResultSetHandler extends AeListingResultSetHandler
{
   /**
    * Constructor.
    *
    * @param aFilter
    */
   public AeSQLTaskListResultSetHandler(AeTaskFilter aFilter)
   {
      super(aFilter);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeListingResultSetHandler#readRow(java.sql.ResultSet)
    */
   protected Object readRow(ResultSet aResultSet) throws SQLException
   {
      long processId = aResultSet.getLong(IAeTaskColumns.PROCESS_ID);
      String presentationName = aResultSet.getString(IAeTaskColumns.PRESENTATION_NAME);
      String name = aResultSet.getString(IAeTaskColumns.NAME);
      String targetNS = aResultSet.getString(IAeTaskColumns.TARGET_NAMESPACE);
      String summary = aResultSet.getString(IAeTaskColumns.SUMMARY);
      long creationTimeMillis = aResultSet.getLong(IAeTaskColumns.CREATION_TIME_MILLIS);
      int state = aResultSet.getInt(IAeTaskColumns.STATE);
      int priority = aResultSet.getInt(IAeTaskColumns.PRIORITY);
      String owner = aResultSet.getString(IAeTaskColumns.OWNER);
      AeSchemaDateTime completionTimeMillis = getSchemaDateTime(aResultSet, IAeTaskColumns.COMPLETION_TIME_MILLIS);
      AeSchemaDateTime lastModifiedTimeMillis = getSchemaDateTime(aResultSet, IAeTaskColumns.LAST_MODIFIED_TIME_MILLIS);
      AeSchemaDateTime expirationDate = getSchemaDateTime(aResultSet, IAeTaskColumns.EXPIRATION_DATE_MILLIS);
      boolean hasAttachments = AeDbUtils.convertIntToBoolean(aResultSet.getInt(IAeTaskColumns.HAS_ATTACHMENTS));
      boolean hasComments = AeDbUtils.convertIntToBoolean(aResultSet.getInt(IAeTaskColumns.HAS_COMMENTS));
      AeSchemaDateTime lastEscalated = getSchemaDateTime(aResultSet, IAeTaskColumns.LAST_ESCALATED_TIME_MILLIS);

      boolean hasRenderings = AeDbUtils.convertIntToBoolean(aResultSet.getInt(IAeTaskColumns.HAS_RENDERINGS));
      String createdBy = aResultSet.getString(IAeTaskColumns.CREATED_BY);

      String initiator = aResultSet.getString(IAeTaskColumns.TASK_INITIATOR);
      String stakeholders = aResultSet.getString(IAeTaskColumns.TASK_STAKEHOLDERS);
      String potOwners = aResultSet.getString(IAeTaskColumns.POTENTIAL_OWNERS);
      String businessAdmins = aResultSet.getString(IAeTaskColumns.BUSINESS_ADMINS);
      String notificationRecipients = aResultSet.getString(IAeTaskColumns.NOTIFICATION_RECIPIENTS);
      boolean hasOutput = AeDbUtils.convertIntToBoolean(aResultSet.getInt(IAeTaskColumns.HAS_OUTPUT));
      if (aResultSet.wasNull())
         hasOutput = false;
      boolean hasFault = AeDbUtils.convertIntToBoolean(aResultSet.getInt(IAeTaskColumns.HAS_FAULT));
      if (aResultSet.wasNull())
         hasFault = false;
      boolean isSkipable = AeDbUtils.convertIntToBoolean(aResultSet.getInt(IAeTaskColumns.IS_SKIPABLE));
      if (aResultSet.wasNull())
         isSkipable = false;
      String primarySearchBy = aResultSet.getString(IAeTaskColumns.PRIMARY_SEARCH_BY);
      AeSchemaDateTime startBy = getSchemaDateTime(aResultSet, IAeTaskColumns.START_BY_MILLIS);
      AeSchemaDateTime completeBy = getSchemaDateTime(aResultSet, IAeTaskColumns.COMPLETE_BY_MILLIS);
      String taskType = AeTaskTypes.TYPE_TASK;
      int taskTypeCode = aResultSet.getInt(IAeTaskColumns.TASK_TYPE);
      if (!aResultSet.wasNull())
         taskType = AeTaskTypes.getTaskTypeName(taskTypeCode);
      AeSchemaDateTime activationTime = getSchemaDateTime(aResultSet, IAeTaskColumns.ACTIVATION_TIME_MILLIS);

      String taskId = (taskType.equals(AeTaskTypes.TYPE_TASK) ?
               IAeProcessTaskConstants.TASK_ID_URN_PREFIX : IAeProcessTaskConstants.NOTIFICATION_ID_URN_PREFIX)
               + String.valueOf(processId);

      AeHtApiTask detail = new AeHtApiTask();
      detail.setProcessId(processId);
      detail.setId(taskId);
      detail.setTaskType(taskType);
      detail.setName(new QName(targetNS, name));
      detail.setStatus(AeTaskStates.getTaskStateName(state));
      detail.setPriority(priority);
      detail.setTaskInitiator(initiator);
      detail.setRenderingMethodExists(hasRenderings);
      detail.setCreatedBy(createdBy);

      try
      {
         detail.setTaskStakeholders( AeGenericHumanRoleDeserializer.INSTANCE.deserializeGenericHumanRole( stakeholders) );
         detail.setPotentialOwners( AeGenericHumanRoleDeserializer.INSTANCE.deserializeGenericHumanRole( potOwners) );
         detail.setBusinessAdministrators( AeGenericHumanRoleDeserializer.INSTANCE.deserializeGenericHumanRole( businessAdmins) );
         detail.setNotificationRecipients( AeGenericHumanRoleDeserializer.INSTANCE.deserializeGenericHumanRole( notificationRecipients) );
      }
      catch(Exception e)
      {
         AeException.logError(e);
         throw new SQLException(e.getMessage());
      }
      detail.setActualOwner(owner);
      detail.setCreatedOn(new AeSchemaDateTime(creationTimeMillis));
      detail.setActivationTime(activationTime);
      detail.setExpirationTime(expirationDate);
      detail.setSkipable(isSkipable);
      detail.setStartBy(startBy);
      detail.setCompleteBy(completionTimeMillis);
      detail.setCompleteBy(completeBy);
      detail.setPresentationName(presentationName);
      detail.setPresentationSubject(summary);
      detail.setHasOutput(hasOutput);
      detail.setHasFault(hasFault);
      detail.setHasAttachments(hasAttachments);
      detail.setHasComments(hasComments);
      detail.setModifiedTime(lastModifiedTimeMillis);
      detail.setEscalationTime(lastEscalated);
      detail.setPrimarySearchBy(primarySearchBy);
      return detail;
   }

   /**
    * Selects the date from the result set or returns null if there was no
    * date set
    * @param aResultSet
    * @param colName
    * @throws SQLException
    */
   private AeSchemaDateTime getSchemaDateTime(ResultSet aResultSet, String colName) throws SQLException
   {
      long lastModifiedTimeMillis = aResultSet.getLong(colName);
      if (aResultSet.wasNull())
      {
         return null;
      }
      return new AeSchemaDateTime(lastModifiedTimeMillis);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeListingResultSetHandler#convertToType(java.util.List)
    */
   protected Object convertToType(List aResults)
   {
      AeHtApiTaskList taskList = new AeHtApiTaskList( getRowCount() );
      taskList.add(aResults);
      return taskList;
   }
}
