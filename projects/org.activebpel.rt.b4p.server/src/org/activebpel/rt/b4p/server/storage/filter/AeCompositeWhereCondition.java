// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/filter/AeCompositeWhereCondition.java,v 1.2 2008/02/17 21:36:33 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.server.storage.filter;

import java.util.ArrayList;
import java.util.List;

import org.activebpel.rt.b4p.server.storage.IAeTaskFilterConstants;

/**
 * A composite where condition.  This is a condition that is made up of
 * other conditions.  These other conditions can either be AND'd or OR'd 
 * together.
 */
public class AeCompositeWhereCondition extends AeAbstractWhereCondition
{
   /** The list of conditions. */
   private List mConditions = new ArrayList();
   /** The operator - and/or. */
   private String mOperator = IAeTaskFilterConstants.OPERATOR_AND;
   
   /**
    * C'tor.
    */
   public AeCompositeWhereCondition()
   {
   }

   /**
    * @return Returns the conditions.
    */
   public List getConditions()
   {
      return mConditions;
   }
   
   /**
    * Adds a condition to the list.
    * 
    * @param aCondition
    */
   public void addCondition(IAeWhereCondition aCondition)
   {
      getConditions().add(aCondition);
   }

   /**
    * @param aConditions the conditions to set
    */
   protected void setConditions(List aConditions)
   {
      mConditions = aConditions;
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
   public void setOperator(String aOperator)
   {
      mOperator = aOperator;
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.filter.IAeWhereCondition#accept(org.activebpel.rt.b4p.server.storage.filter.IAeWhereConditionVisitor)
    */
   public void accept(IAeWhereConditionVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
