// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/filter/AeWhereConditionTraverser.java,v 1.2 2008/02/17 21:36:33 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.server.storage.filter;

import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.util.AeUtil;

/**
 * Where condition traverser impl.
 */
public class AeWhereConditionTraverser implements IAeWhereConditionTraverser
{
   /** The model visitor. */
   private IAeWhereConditionVisitor mVisitor;

   /**
    * C'tor.
    * 
    * @param aVisitor
    */
   public AeWhereConditionTraverser(IAeWhereConditionVisitor aVisitor)
   {
      setVisitor(aVisitor);
   }

   /**
    * Calls 'accept' with the visitor.  Useful for overriding that
    * behavior by subclasses.
    * 
    * @param aCondition
    */
   protected void callAccept(IAeWhereCondition aCondition)
   {
      if (aCondition != null)
         aCondition.accept(getVisitor());
   }
   
   /**
    * Calls 'accept' on a list of conditions.
    * 
    * @param aConditions
    */
   protected void callAccept(List aConditions)
   {
      if (AeUtil.notNullOrEmpty(aConditions))
      {
         for (Iterator iter = aConditions.iterator(); iter.hasNext(); )
         {
            callAccept((IAeWhereCondition) iter.next());
         }
      }
   }
   
   /**
    * @see org.activebpel.rt.b4p.server.storage.filter.IAeWhereConditionTraverser#traverse(org.activebpel.rt.b4p.server.storage.filter.AeCompositeWhereCondition)
    */
   public void traverse(AeCompositeWhereCondition aCondition)
   {
      callAccept(aCondition.getConditions());
   }
   
   /**
    * @see org.activebpel.rt.b4p.server.storage.filter.IAeWhereConditionTraverser#traverse(org.activebpel.rt.b4p.server.storage.filter.AeWhereCondition)
    */
   public void traverse(AeWhereCondition aCondition)
   {
   }

   /**
    * @return Returns the visitor.
    */
   protected IAeWhereConditionVisitor getVisitor()
   {
      return mVisitor;
   }

   /**
    * @param aVisitor the visitor to set
    */
   protected void setVisitor(IAeWhereConditionVisitor aVisitor)
   {
      mVisitor = aVisitor;
   }
}
