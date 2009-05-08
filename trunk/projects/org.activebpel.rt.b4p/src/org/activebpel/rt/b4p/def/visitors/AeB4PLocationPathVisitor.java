//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/AeB4PLocationPathVisitor.java,v 1.4 2008/02/17 21:36:03 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.visitors;

import org.activebpel.rt.ht.def.visitors.AeHtLocationPathVisitor;
import org.activebpel.rt.ht.def.visitors.AeHtTraversalVisitor;

/**
 * Extends ht location path visitor in order to set up different, b4p,
 * objects.
 */
public class AeB4PLocationPathVisitor extends AeHtLocationPathVisitor
{
   /**
    * C'tor.
    *
    * @param aLocationPath
    */
   public AeB4PLocationPathVisitor(String aLocationPath)
   {
      super(new AeB4PDefPathSegmentBuilder());
      setPath(aLocationPath);
   }

   /**
    * Overrides method to provide a b4p traversal visitor.
    *
    * @see org.activebpel.rt.ht.def.visitors.AeHtLocationPathVisitor#createTraversalVisitor()
    */
   protected AeHtTraversalVisitor createTraversalVisitor()
   {
      return new AeB4PTraversalVisitor(new AeB4PDefTraverser(), this);
   }
}
