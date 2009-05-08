// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/IAeHtFunctionNames.java,v 1.1 2008/01/25 21:37:34 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht;

/**
 * WS-Human Task supported function names
 */
public interface IAeHtFunctionNames
{
   /** the get task priority function name */
   public static final String TASK_PRIORITY_FUNCTION_NAME = "getTaskPriority"; //$NON-NLS-1$
   /** the get actual owner function name */
   public static final String ACTUAL_OWNER_FUNCTION_NAME = "getActualOwner"; //$NON-NLS-1$
   /** the get business administrators function name */
   public static final String BUSINESS_ADMINISTRATORS_FUNCTION_NAME = "getBusinessAdministrators"; //$NON-NLS-1$
   /** the get exluded owners function name */
   public static final String EXCLUDED_OWNERS_FUNCTION_NAME = "getExcludedOwners"; //$NON-NLS-1$
   /** the get input function name */
   public static final String INPUT_FUNCTION_NAME = "getInput"; //$NON-NLS-1$
   /** the get logical people group function name */
   public static final String LOGICAL_PEOPLE_GROUP_FUNCTION_NAME = "getLogicalPeopleGroup"; //$NON-NLS-1$
   /** the get potential owners function name */
   public static final String POTENTIAL_OWNERS_FUNCTION_NAME = "getPotentialOwners"; //$NON-NLS-1$
   /** the get task initiator function name */
   public static final String TASK_INITIATOR_FUNCTION_NAME = "getTaskInitiator"; //$NON-NLS-1$
   /** the get task stakeholders function name */
   public static final String TASK_STAKEHOLDERS_FUNCTION_NAME = "getTaskStakeholders"; //$NON-NLS-1$
   
   /** the get task stakeholders function name */
   public static final String UNION_FUNCTION_NAME = "union"; //$NON-NLS-1$
   /** the get task stakeholders function name */
   public static final String INTERSECT_FUNCTION_NAME = "intersect"; //$NON-NLS-1$
   /** the get task stakeholders function name */
   public static final String EXCEPT_FUNCTION_NAME = "except"; //$NON-NLS-1$
}
