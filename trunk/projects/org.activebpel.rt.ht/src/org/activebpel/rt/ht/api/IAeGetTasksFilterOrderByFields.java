//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/IAeGetTasksFilterOrderByFields.java,v 1.1 2008/02/27 19:33:59 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api;

/**
 * List of field names which are used to select the order-by
 * for the inbox listings column sort functionality
 */
public interface IAeGetTasksFilterOrderByFields
{
   //
   // Sort column field IDs.
   //
   /** field id for name. */
   public static String FID_NAME = "Name"; //$NON-NLS-1$
   /** field id for namespace. */
   public static String FID_NAMESPACE = "Namespace"; //$NON-NLS-1$
   /** field id for presentation name. */
   public static String FID_PRESENTATION_NAME = "PresentationName"; //$NON-NLS-1$
   /** field id for summary. */
   public static String FID_SUMMARY = "Summary"; //$NON-NLS-1$
   /** field id for creation date. */
   public static String FID_CREATED = "Created"; //$NON-NLS-1$
   /** field id for state. */
   public static String FID_STATE = "State"; //$NON-NLS-1$
   /** field id for priority. */
   public static String FID_PRIORITY = "Priority"; //$NON-NLS-1$
   /** field id for owner. */
   public static String FID_OWNER = "Owner"; //$NON-NLS-1$
   /** field id for modified date. */
   public static String FID_MODIFIED = "Modified"; //$NON-NLS-1$
   /** field id for expirationdate. */
   public static String FID_EXPIRATION = "Expiration"; //$NON-NLS-1$   
   
}
