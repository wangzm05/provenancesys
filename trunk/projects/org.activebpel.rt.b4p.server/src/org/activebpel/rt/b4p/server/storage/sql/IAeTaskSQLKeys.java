//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/sql/IAeTaskSQLKeys.java,v 1.2 2008/02/07 22:52:13 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage.sql;

/**
 * Constants for the Task storage SQL keys (keys into the SQLConfig object).
 */
public interface IAeTaskSQLKeys
{
   /** The SQL statement key for inserting a task. */
   public static final String INSERT_TASK = "InsertTask"; //$NON-NLS-1$
   /** The SQL statement key for inserting a task acl entry. */
   public static final String INSERT_TASK_ACL = "InsertACL"; //$NON-NLS-1$
   /** The SQL statement key for updating a task. */
   public static final String UPDATE_TASK = "UpdateTask"; //$NON-NLS-1$
   /** The SQL statement key for deleting a task. */
   public static final String DELETE_TASK = "DeleteTask"; //$NON-NLS-1$
   /** The SQL statement key for deleting some tasks. */
   public static final String DELETE_TASKS = "DeleteTasks"; //$NON-NLS-1$
   /** The SQL statement key for getting the list of tasks. */
   public static final String GET_TASKS = "GetTasks"; //$NON-NLS-1$

   public static final String GET_TASKS_ACL_SUBQUERY = "GetTasks.ACLSubSelect"; //$NON-NLS-1$
   public static final String GET_TASKS_EXCLUDE_SUBQUERY = "GetTasks.ExcludeSubSelect"; //$NON-NLS-1$
}
