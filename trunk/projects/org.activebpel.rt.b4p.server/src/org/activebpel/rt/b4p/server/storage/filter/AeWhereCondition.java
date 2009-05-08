// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/filter/AeWhereCondition.java,v 1.2.4.1 2008/04/21 16:08:59 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.server.storage.filter;

/**
 * A simple where clause.  Contains column name, operator, and value.
 */
public class AeWhereCondition extends AeAbstractWhereCondition
{
   /** The column name. */
   private String mColumn;
   /** The operator: >, &lt;, =, !=, etc...*/
   private String mOperator;
   /** The value of the column. */
   private String mValue;

   /**
    * C'tor.
    *
    * @param aColumn
    * @param aOperator
    * @param aValue
    */
   public AeWhereCondition(String aColumn, String aOperator, String aValue)
   {
      setColumn(aColumn);
      setOperator(aOperator);
      setValue(aValue);
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
    * @return Returns the operator.
    */
   public String getOperator()
   {
      return mOperator;
   }

   /**
    * @param aOperator the operator to set
    */
   protected void setOperator(String aOperator)
   {
      mOperator = aOperator;
   }

   /**
    * @return Returns the value.
    */
   public String getValue()
   {
      return mValue;
   }

   /**
    * @param aValue the value to set
    */
   protected void setValue(String aValue)
   {
      mValue = aValue;
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.filter.IAeWhereCondition#accept(org.activebpel.rt.b4p.server.storage.filter.IAeWhereConditionVisitor)
    */
   public void accept(IAeWhereConditionVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
