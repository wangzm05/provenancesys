// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/AeTaskFilterColumnValueConverter.java,v 1.3 2008/02/15 17:48:36 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.server.storage;

import org.activebpel.rt.xml.schema.AeSchemaDateTime;

/**
 * Class used to convert from String to specific data type for
 * querying in the database.  For example, if the column being
 * queried is Priority, this class would convert the value from
 * a String to an Integer.
 */
public class AeTaskFilterColumnValueConverter
{
   /**
    * C'tor.
    */
   public AeTaskFilterColumnValueConverter()
   {
   }

   /**
    * Convert from String to column-specific type.
    *
    * FIXMEQ (wsht) Model the where condition column as a class and let that model convert the value
    *
    * @param aColumnName
    * @param aValue
    */
   public Object convertValue(String aColumnName, String aValue)
   {
      if (IAeTaskFilterConstants.COLUMN_PRIORITY.equals(aColumnName))
      {
         return new Integer(aValue);
      }
      else if (IAeTaskFilterConstants.COLUMN_STATUS.equals(aColumnName))
      {
         return AeTaskStates.getTaskStateCode(aValue);
      }
      else if (IAeTaskFilterConstants.COLUMN_CREATED_ON.equals(aColumnName))
      {
         return new Long(new AeSchemaDateTime(aValue).toDate().getTime());
      }
      else if (IAeTaskFilterConstants.COLUMN_TASK_TYPE.equals(aColumnName))
      {
         return AeTaskTypes.getTaskTypeCode(aValue);
      }
      return aValue;
   }
}
