// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/filter/AeAbstractTraversingWhereConditionVisitor.java,v 1.2 2008/02/17 21:36:32 mford Exp $
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
 * Base class for traversing visitors.
 */
public class AeAbstractTraversingWhereConditionVisitor extends AeAbstractWhereConditionVisitor
{
   /** Visitor used to traverse using the traverser. */
   private IAeWhereConditionVisitor mTraversalVisitor;

   /**
    * C'tor.
    */
   public AeAbstractTraversingWhereConditionVisitor()
   {
      setTraversalVisitor(new AeWhereConditionTraversalVisitor(createTraverser()));
   }

   /**
    * Creates the traverser to use to traverse the model.
    */
   protected IAeWhereConditionTraverser createTraverser()
   {
      return new AeWhereConditionTraverser(this);
   }
   
   /**
    * @see org.activebpel.rt.b4p.server.storage.filter.AeAbstractWhereConditionVisitor#visitCondition(org.activebpel.rt.b4p.server.storage.filter.IAeWhereCondition)
    */
   protected void visitCondition(IAeWhereCondition aCondition)
   {
      aCondition.accept(getTraversalVisitor());
   }

   /**
    * @return Returns the traversalVisitor.
    */
   protected IAeWhereConditionVisitor getTraversalVisitor()
   {
      return mTraversalVisitor;
   }

   /**
    * @param aTraversalVisitor the traversalVisitor to set
    */
   protected void setTraversalVisitor(IAeWhereConditionVisitor aTraversalVisitor)
   {
      mTraversalVisitor = aTraversalVisitor;
   }
}
