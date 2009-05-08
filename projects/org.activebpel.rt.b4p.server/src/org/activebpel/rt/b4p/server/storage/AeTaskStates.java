// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/AeTaskStates.java,v 1.4 2008/02/29 01:19:04 PJayanetti Exp $
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

import org.activebpel.rt.ht.api.IAeGetTasksFilterStates;

/**
 * Represents a task state.
 */
public class AeTaskStates
{
   /*
    * Task States
    */
   private static final Object[][] STATES =
   {
      {IAeGetTasksFilterStates.STATE_CREATED,       new Integer(0)},
      {IAeGetTasksFilterStates.STATE_READY,         new Integer(1)},
      {IAeGetTasksFilterStates.STATE_RESERVED,      new Integer(2)},
      {IAeGetTasksFilterStates.STATE_IN_PROGRESS,   new Integer(3)},
      {IAeGetTasksFilterStates.STATE_COMPLETED,     new Integer(4)},
      {IAeGetTasksFilterStates.STATE_OBSOLETE,      new Integer(5)},
      {IAeGetTasksFilterStates.STATE_ERROR,         new Integer(6)},
      {IAeGetTasksFilterStates.STATE_FAILED,        new Integer(7)},
      {IAeGetTasksFilterStates.STATE_EXITED,        new Integer(8)},
      {IAeGetTasksFilterStates.STATE_SUSPENDED,     new Integer(9)},
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
    * @param aTaskStateName
    */
   public static Integer getTaskStateCode(String aTaskStateName)
   {
      return ((Integer) sNameToCodeMap.get(aTaskStateName));
   }
   
   /**
    * Gets the state code given a state name.
    *
    * @param aTaskStateName
    */
   public static int getTaskStateCodeInt(String aTaskStateName)
   {
      return getTaskStateCode(aTaskStateName).intValue();
   }
   

   /**
    * Gets the state name given the state code.
    *
    * @param aTaskStateCode
    */
   public static String getTaskStateName(int aTaskStateCode)
   {
      return (String) sCodeToNameMap.get(new Integer(aTaskStateCode));
   }

}
