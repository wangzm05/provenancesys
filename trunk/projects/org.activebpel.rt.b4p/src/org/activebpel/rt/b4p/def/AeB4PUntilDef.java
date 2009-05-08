//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeB4PUntilDef.java,v 1.1 2007/11/16 02:43:14 jbik Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def;

import org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor;

/**
 * Impl. for 'until' element Def.
 */
public class AeB4PUntilDef extends AeB4PExpressionBaseDef
{

   /**
    * @see org.activebpel.rt.b4p.def.AeB4PBaseDef#accept(org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor)
    */
   public void accept(IAeB4PDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

}
