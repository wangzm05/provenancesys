//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/inmemory/AeTaskComparator.java,v 1.1 2008/03/20 22:36:26 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage.inmemory;

import java.util.Comparator;

import org.activebpel.rt.b4p.server.storage.IAeTaskFilterConstants;
import org.activebpel.rt.b4p.server.storage.filter.AeTaskFilterOrderByClause;
import org.activebpel.rt.ht.api.AeHtApiTask;
import org.activebpel.rt.util.AeUtil;

/**
 * Comparator used to sort in-memory task collection.
 */
public class AeTaskComparator implements Comparator
{
   /** Compare column name. */
   private String mColumnName;
   /** Descending sort order */
   private boolean mDescending;
   
   /**
    * Ctor.
    * @param aOrderByClause
    */
   public AeTaskComparator(AeTaskFilterOrderByClause aOrderByClause)
   {
      this(aOrderByClause.getColumn(), IAeTaskFilterConstants.DIRECTION_DESC.equals( aOrderByClause.getDirection() ) );
   }
   
   /** 
    * @param aColumnName
    * @param aDescending
    */
   public AeTaskComparator(String aColumnName, boolean aDescending)
   {
      mColumnName = aColumnName;
      mDescending = aDescending;
   }
   
   /**
    * Compares to tasks based on the selected column. 
    * @see java.util.Comparator#compare(T, T)
    */
   public int compare(Object aTask1, Object aTask2)
   {
      if (AeTaskFilterUtil.isColumnSupported(mColumnName)
            && (aTask1 instanceof AeHtApiTask) && (aTask2 instanceof AeHtApiTask))
      {
         String value1 = AeTaskFilterUtil.getColumnStringValue((AeHtApiTask)aTask1, mColumnName);
         String value2 = AeTaskFilterUtil.getColumnStringValue((AeHtApiTask)aTask2, mColumnName);
         int cmp = AeUtil.getSafeString(value1).compareTo( AeUtil.getSafeString(value2) );
         return mDescending? -1*cmp : cmp;
      }
      else
      {
         return 0;
      }
   }
}
