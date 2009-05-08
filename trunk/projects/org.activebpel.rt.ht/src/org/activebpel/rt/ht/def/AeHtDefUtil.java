// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeHtDefUtil.java,v 1.1 2008/02/27 20:45:27 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def;

import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.IAeNamedDef;

/**
 * Static methods for dealing with HT defs.
 */
public class AeHtDefUtil
{
   /**
    * Finds the notification def that is in scope for the given base ht def.
    *
    * @param aDef
    */
   public static AeNotificationDef findInScopeNotificationDef(AeBaseXmlDef aDef)
   {
      AeBaseXmlDef def = aDef;
      while (!(def instanceof AeNotificationDef) && def != null)
         def = def.getParentXmlDef();
      return (AeNotificationDef) def;
   }

   /**
    * Returns the name of the in-scope notification.
    * 
    * @param aDef
    */
   public static String getInScopeNotificationName(AeBaseXmlDef aDef)
   {
      AeNotificationDef notificationDef = AeHtDefUtil.findInScopeNotificationDef(aDef);
      if (notificationDef != null)
         return notificationDef.getName();
      return null;
   }

   /**
    * Finds the task def that is in scope for the given base ht def.
    *
    * @param aDef
    */
   public static AeTaskDef findInScopeTaskDef(AeBaseXmlDef aDef)
   {
      AeBaseXmlDef def = aDef;
      while (!(def instanceof AeTaskDef) && def != null)
         def = def.getParentXmlDef();
      return (AeTaskDef) def;
   }

   /**
    * Returns the name of the in-scope task.
    * 
    * @param aDef
    */
   public static String getInScopeTaskName(AeBaseXmlDef aDef)
   {
      AeTaskDef taskDef = AeHtDefUtil.findInScopeTaskDef(aDef);
      if (taskDef != null)
         return taskDef.getName();
      return null;
   }
   
   /**
    * Finds the in-scope notification or task def given the context
    * def.  This method first looks for an in-scope notification, then
    * (if a notification is not found) looks for an in-scope task.
    * 
    * @param aDef
    */
   public static AeHtBaseDef findInScopeNotificationOrTaskDef(AeBaseXmlDef aDef)
   {
      AeNotificationDef notificationDef = AeHtDefUtil.findInScopeNotificationDef(aDef);
      if (notificationDef != null)
         return notificationDef;
      else
         return AeHtDefUtil.findInScopeTaskDef(aDef);
   }
   
   /**
    * Gets the in-scope notification or task and returns its name, or null if
    * nothing is in scope.
    * 
    * @param aDef
    */
   public static String getInScopeNotificationOrTaskName(AeBaseXmlDef aDef)
   {
      AeHtBaseDef notificationOrTaskDef = AeHtDefUtil.findInScopeNotificationOrTaskDef(aDef);
      if (notificationOrTaskDef != null)
         ((IAeNamedDef) notificationOrTaskDef).getName();
      return null;
   }
}
