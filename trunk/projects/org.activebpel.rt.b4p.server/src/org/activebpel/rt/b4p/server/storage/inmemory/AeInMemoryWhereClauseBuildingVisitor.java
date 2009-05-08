//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/inmemory/AeInMemoryWhereClauseBuildingVisitor.java,v 1.1 2008/03/20 22:36:26 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage.inmemory;

import java.util.ArrayList;
import java.util.List;

import org.activebpel.rt.b4p.server.storage.IAeTaskFilterConstants;
import org.activebpel.rt.b4p.server.storage.filter.AeAbstractTraversingWhereConditionVisitor;
import org.activebpel.rt.b4p.server.storage.filter.AeCompositeWhereCondition;
import org.activebpel.rt.b4p.server.storage.filter.AeTaskFilter;
import org.activebpel.rt.b4p.server.storage.filter.AeWhereCondition;
import org.activebpel.rt.ht.api.AeHtApiTask;
import org.activebpel.rt.util.AeUtil;

/**
 * Visitor that returns a final boolean if a single task should
 * be included in a result set.
 */
public class AeInMemoryWhereClauseBuildingVisitor extends AeAbstractTraversingWhereConditionVisitor
{
   /** Search filter */
   private AeTaskFilter mFilter;
   /** Current task */
   private AeHtApiTask mTask;

   /**
    * Ctor.
    * @param aFilter
    * @param aTask
    */
   public AeInMemoryWhereClauseBuildingVisitor(AeTaskFilter aFilter, AeHtApiTask aTask)
   {
      super();
      push(new ArrayList());
      mFilter = aFilter;
      mTask = aTask;
   }

   /***
    * Final result after evaluating the where clause.
    * @return
    */
   public boolean isMatch()
   {
      if (getConditions().isEmpty())
      {
         // empty where condition is same as include task in result set. (ignore where condition)
         return true;
      }
      else
      {
         return ((Boolean) getConditions().get(0)).booleanValue();
      }
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.filter.AeAbstractWhereConditionVisitor#visit(org.activebpel.rt.b4p.server.storage.filter.AeCompositeWhereCondition)
    */
   public void visit(AeCompositeWhereCondition aCondition)
   {
      push( new ArrayList() );
      super.visit(aCondition);

      // get list of boolean values that were evaluated when visiting child Where conditions.
      List childConditions = pop();

      if (!childConditions.isEmpty())
      {
         // get first value
         boolean compositeEval = ((Boolean) childConditions.get(0)).booleanValue();
         // continue eval from 1+ to eval AND/OR op.
         for (int i = 1; i < childConditions.size(); i++)
         {
            boolean childEval = ((Boolean) childConditions.get(i)).booleanValue();
            if (IAeTaskFilterConstants.OPERATOR_OR.equals(aCondition.getOperator()))
            {
               compositeEval = compositeEval || childEval;
            }
            else
            {
               // default is AND
               compositeEval = compositeEval && childEval;
            }
         }
         getConditions().add(new Boolean(compositeEval));
      }
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.filter.AeAbstractWhereConditionVisitor#visit(org.activebpel.rt.b4p.server.storage.filter.AeWhereCondition)
    */
   public void visit(AeWhereCondition aCondition)
   {
      // eval only if the column is support otherwise ignore (no-op).
      if ( AeTaskFilterUtil.isColumnSupported( aCondition.getColumn() ) )
      {
         String value1 = AeUtil.getSafeString( getColumnValue(aCondition.getColumn()) );
         String value2 = AeUtil.getSafeString( aCondition.getValue() );
         boolean eval = evalOperator(value1, value2, aCondition.getOperator());
         getConditions().add( new Boolean(eval) );
      }
      super.visit(aCondition);
   }

   /**
    * @return the filter
    */
   protected AeTaskFilter getFilter()
   {
      return mFilter;
   }

   /**
    * @return the task
    */
   protected AeHtApiTask getTask()
   {
      return mTask;
   }

   /**
    * Compares the two String values based on the given operator.
    * @param aValue1
    * @param aValue2
    * @param aOperator as defined in IAeTaskFilterConstants.
    * @return
    */
   public boolean evalOperator(String aValue1, String aValue2, String aOperator)
   {
      // Note: value1/value2 maybe empty.
      if (IAeTaskFilterConstants.OPERATOR_EQ.equals(aOperator))
      {
         return aValue1.equals(aValue2);
      }
      else if (IAeTaskFilterConstants.OPERATOR_NEQ.equals(aOperator))
      {
         return !aValue1.equals(aValue2);
      }
      else if (IAeTaskFilterConstants.OPERATOR_LIKE.equals(aOperator))
      {
         return aValue1.equalsIgnoreCase(aValue2);
      }
      else if (IAeTaskFilterConstants.OPERATOR_LT.equals(aOperator))
      {
         return aValue1.compareTo(aValue2) < 0;
      }
      else if (IAeTaskFilterConstants.OPERATOR_LTE.equals(aOperator))
      {
         return aValue1.compareTo(aValue2) <= 0;
      }
      else if (IAeTaskFilterConstants.OPERATOR_GT.equals(aOperator))
      {
         return aValue1.compareTo(aValue2) > 0;
      }
      else if (IAeTaskFilterConstants.OPERATOR_GTE.equals(aOperator))
      {
         return aValue1.compareTo(aValue2) >= 0;
      }

      // unsupported operation
      return false;
   }

   /**
    * Returns column value or <code>null</code> if not supported.
    * The supported column types are status, primary search by and the task type.
    * @param aColumnName name defined in IAeTaskFilterConstants.
    * @return column value
    */
   protected String getColumnValue(String aColumnName)
   {
      return AeTaskFilterUtil.getColumnStringValue(getTask(), aColumnName);
   }
}
