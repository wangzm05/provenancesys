//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/visitors/AeHtLocationPathVisitor.java,v 1.3 2007/11/15 22:32:00 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def.visitors; 

import org.activebpel.rt.xml.def.IAePathSegmentBuilder;
import org.activebpel.rt.xml.def.visitors.AeLocationPathVisitor;

/**
 * Subclass that sets up the traversal in the ctor 
 */
public class AeHtLocationPathVisitor extends AeLocationPathVisitor
{
   /**
    * C'tor.
    */
   public AeHtLocationPathVisitor()
   {
      this(new AeHtDefPathSegmentBuilder());
   }

   /**
    * C'tor.
    * 
    * @param aSegmentPathBuilder
    */
   protected AeHtLocationPathVisitor(IAePathSegmentBuilder aSegmentPathBuilder)
   {
      super(aSegmentPathBuilder);
      setTraversalVisitor(createTraversalVisitor());
   }

   /**
    * Creates the traversal visitor.
    */
   protected AeHtTraversalVisitor createTraversalVisitor()
   {
      return new AeHtTraversalVisitor(new AeHtDefTraverser(), this);
   }
}
 