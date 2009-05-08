//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeBusinessAdministratorsDef.java,v 1.2 2007/10/23 12:34:34 ewittmann Exp $
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
 * Models the businessAdministrators b4p construct.
 */
public class AeBusinessAdministratorsDef extends AeAbstractGenericHumanRoleDef
{
   /**
    * Default c'tor.
    */
   public AeBusinessAdministratorsDef()
   {
   }

  /**
   * @see org.activebpel.rt.b4p.def.AeB4PBaseDef#accept(org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor)
   */
   public void accept(IAeB4PDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
