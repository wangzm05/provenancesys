// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/AeGenericHumanRoles.java,v 1.2 2008/02/29 01:19:04 PJayanetti Exp $
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

import org.activebpel.rt.ht.api.IAeGetTasksFilterGenericHumanRoles;
import org.activebpel.rt.util.AeIntMap;

/**
 * Maps generic human roles in and out of the DB.
 */
public class AeGenericHumanRoles implements IAeTaskFilterConstants
{
   public static final int GHR_CODE_INITIATOR = 0;
   public static final int GHR_CODE_STAKEHOLDERS = 1;
   public static final int GHR_CODE_POTENTIAL_OWNERS = 2;
   public static final int GHR_CODE_ACTUAL_OWNER = 3;
   public static final int GHR_CODE_EXCLUDED_OWNERS = 4;
   public static final int GHR_CODE_BUSINESS_ADMINISTRATORS = 5;
   public static final int GHR_CODE_NOTIFICATION_RECIPIENTS = 6;

   /*
    * Generic human roles.
    */
   private static final Object[][] ROLE_MAP =
   {
      {IAeGetTasksFilterGenericHumanRoles.GHR_INITIATOR,               new Integer(GHR_CODE_INITIATOR)},
      {IAeGetTasksFilterGenericHumanRoles.GHR_STAKEHOLDERS,            new Integer(GHR_CODE_STAKEHOLDERS)},
      {IAeGetTasksFilterGenericHumanRoles.GHR_POTENTIAL_OWNERS,        new Integer(GHR_CODE_POTENTIAL_OWNERS)},
      {IAeGetTasksFilterGenericHumanRoles.GHR_ACTUAL_OWNER,            new Integer(GHR_CODE_ACTUAL_OWNER)},
      {IAeGetTasksFilterGenericHumanRoles.GHR_EXCLUDED_OWNERS,         new Integer(GHR_CODE_EXCLUDED_OWNERS)},
      {IAeGetTasksFilterGenericHumanRoles.GHR_BUSINESS_ADMINISTRATORS, new Integer(GHR_CODE_BUSINESS_ADMINISTRATORS)},
      {IAeGetTasksFilterGenericHumanRoles.GHR_NOTIFICATION_RECIPIENTS, new Integer(GHR_CODE_NOTIFICATION_RECIPIENTS)}
   };

   private static AeIntMap sCodeToNameMap = new AeIntMap();
   private static Map sNameToCodeMap = new HashMap();

   static
   {
      for (int i = 0; i < ROLE_MAP.length; i++)
      {
         Object name = ROLE_MAP[i][0];
         Object code = ROLE_MAP[i][1];

         sNameToCodeMap.put(name, code);
         sCodeToNameMap.put(code, name);
      }
   }

   /**
    * Gets the generic human role string from the DB code.
    *
    * @param aGHRCode
    */
   public static String getGenericHumanRole(int aGHRCode)
   {
      return (String) sCodeToNameMap.get(aGHRCode);
   }

   /**
    * Gets the generic human role code given the generic human role.
    *
    * @param aGenericHumanRole
    */
   public static int getGenericHumanRoleCode(String aGenericHumanRole)
   {
      return ((Integer) sNameToCodeMap.get(aGenericHumanRole)).intValue();
   }
}
