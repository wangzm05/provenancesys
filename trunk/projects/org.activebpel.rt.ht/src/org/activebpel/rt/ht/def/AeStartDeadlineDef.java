//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeStartDeadlineDef.java,v 1.3 2007/12/30 01:02:10 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;

/**
 * Impl. for 'startDeadline' element Def
 */
public class AeStartDeadlineDef extends AeAbstractDeadlineDef
{
   /**
    * @see org.activebpel.rt.ht.def.AeHtBaseDef#accept(org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor)
    */
   public void accept(IAeHtDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.AeAbstractDeadlineDef#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      return aOther instanceof AeStartDeadlineDef && super.equals(aOther);
   }
}
