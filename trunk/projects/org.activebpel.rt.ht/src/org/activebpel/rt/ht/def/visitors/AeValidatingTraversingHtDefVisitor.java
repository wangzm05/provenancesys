// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/visitors/AeValidatingTraversingHtDefVisitor.java,v 1.1 2008/01/09 23:17:19 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.visitors;

import org.activebpel.rt.ht.def.AeHtBaseDef;

/**
 * A traversing visitor that accepts and applies a validation rule visitor against the 
 * def being validated/visited.  This human interaction visitor will visit/traverse 
 * only the Ht Def. 
 */
public class AeValidatingTraversingHtDefVisitor extends AeAbstractTraversingHtDefVisitor
{
   /** Validation rule to be visited. */
   private IAeHtDefVisitor mRule; 
   
   /**
    * C'tor
    * 
    * @param aRule
    */
   public AeValidatingTraversingHtDefVisitor(IAeHtDefVisitor aRule)
   {
      super();
      setRule(aRule);
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
   protected IAeHtDefVisitor getRule()
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
