//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeRecipientsDef.java,v 1.3 2007/11/13 16:57:34 rnaylor Exp $$
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
 * Impl. for 'recipients' element Def.
 */
public class AeRecipientsDef extends AeAbstractGenericHumanRoleDef
{
   /**
    * @see org.activebpel.rt.ht.def.AeHtBaseDef#accept(org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor)
    */
   public void accept(IAeHtDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}