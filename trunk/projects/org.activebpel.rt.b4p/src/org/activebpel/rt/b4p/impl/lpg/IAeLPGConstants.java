// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/lpg/IAeLPGConstants.java,v 1.2 2008/02/27 20:56:43 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.impl.lpg;

import org.activebpel.rt.ht.IAeWSHTConstants;

/**
 * Constants associated with logical people groups.
 */
public interface IAeLPGConstants
{
   /** LPG namespace */
   public static String LPG_NAMESPACE = "http://www.activebpel.org/b4p/2007/10/wshumantask/deployments"; //$NON-NLS-1$

   /** Preferred LPG namespace prefix. */
   public static String LPG_PREFIX = "aeb4p"; //$NON-NLS-1$

   /** Human Task namespace */
   public static String HUMAN_TASK_NAMESPACE = IAeWSHTConstants.WSHT_NAMESPACE;

   /** Preferred human task namespace prefix. */
   public static String HT_PREFIX = "htd"; //$NON-NLS-1$

   /**
    * LOGICAL_PEOPLE_GROUPS element used in the serialized version of the LPG model.
    */
   public static String LOGICAL_PEOPLE_GROUPS = "logicalPeopleGroups"; //$NON-NLS-1$

   /**
    * LOGICAL_PEOPLE_GROUP element used in the serialized version of the LPG model.
    */
   public static String LOGICAL_PEOPLE_GROUP = "logicalPeopleGroup"; //$NON-NLS-1$

   /** LPG_NAME element used in the serialized version of the LPG model. */
   public static String LPG_NAME = "name"; //$NON-NLS-1$

   /** LPG_TYPE element used in the serialized version of the LPG model. */
   public static String LPG_TYPE = "type"; //$NON-NLS-1$

   /** LPG_LOCATION_PATH element used in the serialized version of the LPG model. */
   public static String LPG_LOCATION_PATH = "location"; //$NON-NLS-1$

   /**
    * LPG_ORGANIZATIONAL_ENTITY element used in the serialized version of the LPG model.
    */
   public static String LPG_ORGANIZATIONAL_ENTITY = "organizationalEntity"; //$NON-NLS-1$

   /** LPG_USERS element used in the serialized version of the LPG model. */
   public static String LPG_USERS = "users"; //$NON-NLS-1$

   /** LPG_USER element used in the serialized version of the LPG model. */
   public static String LPG_USER = "user"; //$NON-NLS-1$

   /** LPG_GROUPS element used in the serialized version of the LPG model. */
   public static String LPG_GROUPS = "groups"; //$NON-NLS-1$

   /** LPG_GROUP element used in the serialized version of the LPG model. */
   public static String LPG_GROUP = "group"; //$NON-NLS-1$
}
