// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/filter/AeAbstractWhereConditionVisitor.java,v 1.3 2008/03/20 22:36:26 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.server.storage.filter;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Base class for where condition visitors.
 */
public class AeAbstractWhereConditionVisitor implements IAeWhereConditionVisitor
{
   /** Stack of lists of conditions. */
   private Stack mStack = new Stack();
   
   /**
    * C'tor.
    */
   public AeAbstractWhereConditionVisitor()
   {
   }
   
   /**
    * Visits the condition (hit for all conditions).
    * 
    * @param aCondition
    */
   protected void visitCondition(IAeWhereCondition aCondition)
   {
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.filter.IAeWhereConditionVisitor#visit(org.activebpel.rt.b4p.server.storage.filter.AeCompositeWhereCondition)
    */
   public void visit(AeCompositeWhereCondition aCondition)
   {
      visitCondition(aCondition);
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.filter.IAeWhereConditionVisitor#visit(org.activebpel.rt.b4p.server.storage.filter.AeWhereCondition)
    */
   public void visit(AeWhereCondition aCondition)
   {
      visitCondition(aCondition);
   }
   
   /**
    * Returns the most recent (top of the stack) list of conditions.
    */
   protected List getConditions()
   {
      if (mStack.size() > 0)
      {
         return (List) mStack.get(mStack.size() - 1);
      }
      else
      {
         return Collections.EMPTY_LIST;
      }
   }

   /**
    * Push a list onto the stack.
    *
    * @param aConditions
    */
   protected void push(List aConditions)
   {
      mStack.push(aConditions);
   }

   /**
    * Pop a composite condition off the stack.
    */
   protected List pop()
   {
      return (List) mStack.pop();
   }   
}
