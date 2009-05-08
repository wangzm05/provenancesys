// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/filter/AeWhereConditionTraversalVisitor.java,v 1.2 2008/02/17 21:36:33 mford Exp $
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
 * Traversal visitor for traversing a where condition.
 */
public class AeWhereConditionTraversalVisitor implements IAeWhereConditionVisitor
{
   /** The traverser to use. */
   private IAeWhereConditionTraverser mTraverser;

   /**
    * C'tor.
    * 
    * @param aTraverser
    */
   public AeWhereConditionTraversalVisitor(IAeWhereConditionTraverser aTraverser)
   {
      setTraverser(aTraverser);
   }

   /**
    * @see org.activebpel.rt.b4p.server.storage.filter.IAeWhereConditionVisitor#visit(org.activebpel.rt.b4p.server.storage.filter.AeCompositeWhereCondition)
    */
   public void visit(AeCompositeWhereCondition aCondition)
   {
      getTraverser().traverse(aCondition);
   }
   
   /**
    * @see org.activebpel.rt.b4p.server.storage.filter.IAeWhereConditionVisitor#visit(org.activebpel.rt.b4p.server.storage.filter.AeWhereCondition)
    */
   public void visit(AeWhereCondition aCondition)
   {
      getTraverser().traverse(aCondition);
   }

   /**
    * @return Returns the traverser.
    */
   protected IAeWhereConditionTraverser getTraverser()
   {
      return mTraverser;
   }

   /**
    * @param aTraverser the traverser to set
    */
   protected void setTraverser(IAeWhereConditionTraverser aTraverser)
   {
      mTraverser = aTraverser;
   }
}
