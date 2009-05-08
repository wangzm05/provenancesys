// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/filter/AeTaskFilterOrderByClause.java,v 1.1 2008/02/02 19:11:35 PJayanetti Exp $
// ///////////////////////////////////////////////////////////////////////////
// PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc. Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
// ///////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.server.storage.filter;

/**
 * Simple order by clause, includes column name and sort direction.
 */
public class AeTaskFilterOrderByClause
{
   /** The column to order by. */
   private String mColumn;
   /** The sort direction (ASC/DESC). */
   private String mDirection;

   /**
    * C'tor.
    * 
    * @param aColumn
    * @param aDirection
    */
   public AeTaskFilterOrderByClause(String aColumn, String aDirection)
   {
      setColumn(aColumn);
      setDirection(aDirection);
   }
   
   /**
    * @return Returns the column.
    */
   public String getColumn()
   {
      return mColumn;
   }

   /**
    * @param aColumn the column to set
    */
   protected void setColumn(String aColumn)
   {
      mColumn = aColumn;
   }

   /**
    * @return Returns the direction.
    */
   public String getDirection()
   {
      return mDirection;
   }

   /**
    * @param aDirection the direction to set
    */
   protected void setDirection(String aDirection)
   {
      mDirection = aDirection;
   }
}
