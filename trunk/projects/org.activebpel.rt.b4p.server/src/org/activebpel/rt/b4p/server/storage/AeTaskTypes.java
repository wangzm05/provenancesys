// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/AeTaskTypes.java,v 1.4 2008/02/29 01:19:04 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.server.storage;

import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.ht.api.IAeGetTasksFilterTaskType;

/**
 * The task types:  TASK, NOTIFICATION
 */
public class AeTaskTypes
{
   /** String representing the created state. */
   public static String TYPE_TASK = "TASK"; //$NON-NLS-1$
   /** String representing the ready state. */
   public static String TYPE_NOTIFICATION = "NOTIFICATION"; //$NON-NLS-1$

   /*
    * Task Types
    */
   private static final Object[][] STATES =
   {
      {TYPE_TASK, new Integer(0)},
      {TYPE_NOTIFICATION, new Integer(1)},
   };

   private static Map sCodeToNameMap = new HashMap();
   private static Map sNameToCodeMap = new HashMap();

   static
   {
      for (int i = 0; i < STATES.length; i++)
      {
         Object name = STATES[i][0];
         Object code = STATES[i][1];

         sNameToCodeMap.put(name, code);
         sCodeToNameMap.put(code, name);
      }
   }

   /**
    * Gets the state code given a state name.
    *
    * @param aTaskTypeName
    */
   public static Integer getTaskTypeCode(String aTaskTypeName)
   {
      return ((Integer) sNameToCodeMap.get(aTaskTypeName));
   }

   /**
    * Gets the state code given a state name.
    * @param aTaskTypeName
    */
   public static int getTaskTypeCodeInt(String aTaskTypeName)
   {
      return getTaskTypeCode(aTaskTypeName).intValue();
   }

   /**
    * Gets the state name given the state code.
    *
    * @param aTaskTypeCode
    */
   public static String getTaskTypeName(int aTaskTypeCode)
   {
      return (String) sCodeToNameMap.get(new Integer(aTaskTypeCode));
   }

   /**
    * Maps ws-ht api listing type (getMyTasks or getMyTaskAbstracts) to either a TASK or NOTIFICATION.
    * @param aApiListingType
    */
   public static String getTaskTypeFromApiListingType(String aApiListingType)
   {
      // maps api getMyTasks or getMyTaskAbstracts listing type to task type.
      // api TASKS  (note plural) maps to TASK
      // api NOTIFICATIONS maps to NOTIFICATION
      if (IAeGetTasksFilterTaskType.TASKTYPE_NOTIFICATIONS.equals(aApiListingType))
      {
         return TYPE_NOTIFICATION;
      }
      else
      {
         // default
         return TYPE_TASK;
      }
   }
}
