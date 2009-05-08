//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeProcessInitiatorDef.java,v 1.2 2007/10/23 12:34:34 ewittmann Exp $
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
 * Impl. for BPEL4People 'processInitiator' element Def 
 */
public class AeProcessInitiatorDef extends AeAbstractGenericHumanRoleDef
{
   /**
    * @see org.activebpel.rt.b4p.def.AeB4PBaseDef#accept(org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor)
    */
   public void accept(IAeB4PDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
