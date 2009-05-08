//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/visitors/AeAbstractTraversingHtDefVisitor.java,v 1.2.4.2 2008/04/14 21:26:06 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def.visitors;

import org.activebpel.rt.ht.def.AeHumanInteractionsDef;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * Traversing implementation of an abstract HT def visitor.  Classes that
 * want to visit and traverse an HT tree should extend this class
 */
public abstract class AeAbstractTraversingHtDefVisitor extends AeAbstractHtDefVisitor implements IAeHtDefVisitor
{
   /** Used in conjunction with the traversal object to traverse the object model */
   private IAeBaseXmlDefVisitor mTraversalVisitor;
   /** Cached human interactions def for convenience. */
   private AeHumanInteractionsDef mHumanInteractions;

   /**
    * Default c'tor.
    */
   public AeAbstractTraversingHtDefVisitor()
   {
      setTraversalVisitor(createTraversalVisitor());
   }

   /**
    * Called by the c'tor to create the traversal visitor.
    */
   protected AeHtTraversalVisitor createTraversalVisitor()
   {
      return new AeHtTraversalVisitor(new AeHtDefTraverser(), this);
   }

   /**
    * @return the humanInteractions
    */
   public AeHumanInteractionsDef getHumanInteractions()
   {
      return mHumanInteractions;
   }

   /**
    * @param aHumanInteractions the humanInteractions to set
    */
   public void setHumanInteractions(AeHumanInteractionsDef aHumanInteractions)
   {
      mHumanInteractions = aHumanInteractions;
   }

   /**
    * Calls accept on the def object, passing in the traversal visitor.
    * @param aDef
    */
   protected void traverse(AeBaseXmlDef aDef)
   {
      aDef.accept(getTraversalVisitor());
   }

   /**
    * @see org.activebpel.rt.xml.def.visitors.AeBaseXmlDefVisitor#visitBaseXmlDef(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   protected void visitBaseXmlDef(AeBaseXmlDef aDef)
   {
      traverse(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeHumanInteractionsDef)
    */
   public void visit(AeHumanInteractionsDef aDef)
   {
      setHumanInteractions(aDef);
      
      super.visit(aDef);
   }

   /**
    * @return Returns the traversalVisitor.
    */
   protected IAeBaseXmlDefVisitor getTraversalVisitor()
   {
      return mTraversalVisitor;
   }

   /**
    * @param aTraversalVisitor the traversalVisitor to set
    */
   protected void setTraversalVisitor(IAeBaseXmlDefVisitor aTraversalVisitor)
   {
      mTraversalVisitor = aTraversalVisitor;
   }
}
