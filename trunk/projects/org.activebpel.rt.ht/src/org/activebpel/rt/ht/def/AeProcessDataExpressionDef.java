// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeProcessDataExpressionDef.java,v 1.2 2008/02/17 21:51:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2006 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def;

import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;

/**
 * Models the 'condition' bpel construct that is a child of the 'escalation' construct.
 */
public class AeProcessDataExpressionDef extends AeAbstractExpressionDef
{
   /**
    * Default c'tor.
    */
   public AeProcessDataExpressionDef()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.ht.def.AeHtBaseDef#accept(org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor)
    */
   public void accept(IAeHtDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
