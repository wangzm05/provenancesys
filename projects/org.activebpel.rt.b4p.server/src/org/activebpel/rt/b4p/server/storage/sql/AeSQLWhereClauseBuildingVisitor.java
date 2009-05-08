// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/sql/AeSQLWhereClauseBuildingVisitor.java,v 1.3 2008/03/20 22:36:26 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.server.storage.sql;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.activebpel.rt.b4p.server.storage.AeTaskFilterColumnValueConverter;
import org.activebpel.rt.b4p.server.storage.filter.AeAbstractTraversingWhereConditionVisitor;
import org.activebpel.rt.b4p.server.storage.filter.AeCompositeWhereCondition;
import org.activebpel.rt.b4p.server.storage.filter.AeWhereCondition;
import org.activebpel.rt.util.AeUtil;

/**
 * Visits the where condition, building a SQL clause.
 */
public class AeSQLWhereClauseBuildingVisitor extends AeAbstractTraversingWhereConditionVisitor
{
   /** List of params - one for every '?' in the generated query. */
   private List mParams = new ArrayList();
   /** Type converter. */
   private AeTaskFilterColumnValueConverter mTypeConverter;

   /**
    * C'tor.
    */
   public AeSQLWhereClauseBuildingVisitor()
   {
      super();
      setTypeConverter(new AeTaskFilterColumnValueConverter());
      push(new ArrayList());
   }

   /**
    * Gets the result.
    */
   public String getClause()
   {
      if (getConditions().isEmpty())
         return null;
      else
         return (String) getConditions().get(0);
   }


   /**
    * @see org.activebpel.rt.b4p.server.storage.filter.AeAbstractWhereConditionVisitor#visit(org.activebpel.rt.b4p.server.storage.filter.AeCompositeWhereCondition)
    */
   public void visit(AeCompositeWhereCondition aCondition)
   {
      push(new ArrayList());

      super.visit(aCondition);

      List conditions = pop();

      // It is unlikely that the list of conditions would be empty, but
      // it could happen if the composite where clause had no children
      if (!conditions.isEmpty())
      {
         String operator = convertOperator(aCondition.getOperator());
         String compositeCondition = "(" + AeUtil.joinToStringObjects(conditions, " " + operator + " ") + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
         getConditions().add(compositeCondition);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.filter.AeAbstractWhereConditionVisitor#visit(org.activebpel.rt.b4p.server.storage.filter.AeWhereCondition)
    */
   public void visit(AeWhereCondition aCondition)
   {
      String dbCol = convertColumn(aCondition.getColumn());
      if (dbCol != null)
      {
         String operator = convertOperator(aCondition);
         Object value = convertValue(aCondition);
         String valueStr = "?"; //$NON-NLS-1$
         if (value != null)
            getParams().add(value);
         else
            valueStr = "NULL"; //$NON-NLS-1$

         String condition = MessageFormat.format("{0} {1} {2}", new String[] { dbCol, operator, valueStr }); //$NON-NLS-1$
         getConditions().add(condition);
      }
      else
      {
         throw new RuntimeException("Unsupported column: " + aCondition.getColumn()); //$NON-NLS-1$
      }

      super.visit(aCondition);
   }

   /**
    * Converts a column from WS-HT format to ae-db format.
    *
    * @param aColumn
    */
   protected String convertColumn(String aColumn)
   {
      return (String) AeSQLTaskFilter.sColumnFieldMap.get(aColumn);
   }

   /**
    * Converts an operator from WS-HT format to sql format.
    *
    * @param aCondition
    */
   protected String convertOperator(AeWhereCondition aCondition)
   {
      if (aCondition.getValue() == null)
         return "IS"; //$NON-NLS-1$
      else
         return convertOperator(aCondition.getOperator());
   }

   /**
    * Converts an operator from WS-HT format to SQL format.
    *
    * @param aOperator
    */
   protected String convertOperator(String aOperator)
   {
      return (String) AeSQLTaskFilter.sOperatorMap.get(aOperator);
   }

   /**
    * Converts the value of the condition.  The return value will be
    * typed depending on the column type.
    *
    * @param aCondition
    */
   protected Object convertValue(AeWhereCondition aCondition)
   {
      return getTypeConverter().convertValue(aCondition.getColumn(), aCondition.getValue());
   }

   /**
    * @return Returns the params.
    */
   public List getParams()
   {
      return mParams;
   }

   /**
    * @param aParams the params to set
    */
   protected void setParams(List aParams)
   {
      mParams = aParams;
   }

   /**
    * @return Returns the typeConverter.
    */
   protected AeTaskFilterColumnValueConverter getTypeConverter()
   {
      return mTypeConverter;
   }

   /**
    * @param aTypeConverter the typeConverter to set
    */
   protected void setTypeConverter(AeTaskFilterColumnValueConverter aTypeConverter)
   {
      mTypeConverter = aTypeConverter;
   }
}
