// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/AeValidatingTraversingB4PDefVisitor.java,v 1.1 2008/01/09 23:22:03 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.def.visitors;

import org.activebpel.rt.b4p.def.AeB4PBaseDef;
import org.activebpel.rt.ht.def.AeHtBaseDef;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * A traversing visitor that accepts and applies a validation rule visitor against the 
 * def being validated/visited.  This B4P visitor will visit/traverse both B4P defs and 
 * HT defs. 
 */
public class AeValidatingTraversingB4PDefVisitor extends AeAbstractTraversingB4PDefVisitor
{
   /** Validation rule to be visited. */
   private IAeHtDefVisitor mRule; 
   
   /**
    * C'tor
    * 
    * @param aRule
    */
   public AeValidatingTraversingB4PDefVisitor(IAeHtDefVisitor aRule)
   {
      super();
      setRule(aRule);
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visitB4PBaseDef(org.activebpel.rt.b4p.def.AeB4PBaseDef)
    */
   protected void visitB4PBaseDef(AeB4PBaseDef aB4PBaseDef)
   {
      if (getRule() instanceof IAeB4PDefVisitor)
      {
         aB4PBaseDef.accept(getRule());
      }
      super.visitB4PBaseDef(aB4PBaseDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visitHtBaseDef(org.activebpel.rt.ht.def.AeHtBaseDef)
    */
   protected void visitHtBaseDef(AeHtBaseDef aHtBaseDef)
   {
      aHtBaseDef.accept(getRule());
      super.visitHtBaseDef(aHtBaseDef);
   }

   /**
    * @return Returns the rule.
    */
   protected IAeBaseXmlDefVisitor getRule()
   {
      return mRule;
   }

   /**
    * @param aRule the rule to set
    */
   protected void setRule(IAeHtDefVisitor aRule)
   {
      mRule = aRule;
   }
   
}
