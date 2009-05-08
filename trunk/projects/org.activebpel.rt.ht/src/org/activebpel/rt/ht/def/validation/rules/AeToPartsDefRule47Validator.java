// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeToPartsDefRule47Validator.java,v 1.2 2008/02/15 17:40:56 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeEscalationDef;
import org.activebpel.rt.ht.def.AeToPartsDef;

/**
 * Element toParts are not allowed for reassignment.
 */
public class AeToPartsDefRule47Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartsDef)
    */
   public void visit(AeToPartsDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeToPartsDef aDef)
   {
      AeEscalationDef escalation = (AeEscalationDef) aDef.getParentDef();
      
      if (escalation.getReassignment() != null)
      {
         reportProblem(AeMessages.getString("AeToPartsDefRule47Validator.0"), aDef); //$NON-NLS-1$
      }
   }

}
