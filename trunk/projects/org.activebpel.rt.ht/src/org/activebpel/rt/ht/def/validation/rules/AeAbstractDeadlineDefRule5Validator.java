// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeAbstractDeadlineDefRule5Validator.java,v 1.2 2008/02/15 17:40:56 EWittmann Exp $
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
import org.activebpel.rt.ht.def.AeAbstractDeadlineDef;
import org.activebpel.rt.ht.def.AeCompletionDeadlineDef;
import org.activebpel.rt.ht.def.AeStartDeadlineDef;

/**
 * Must have children or any extensible element
 */
public class AeAbstractDeadlineDefRule5Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeCompletionDeadlineDef)
    */
   public void visit(AeCompletionDeadlineDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeStartDeadlineDef)
    */
   public void visit(AeStartDeadlineDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeAbstractDeadlineDef aDef)
   {
      // if any exist then a problem will not be reported 
      boolean hasChildren = (aDef.getEscalationProcessCount() > 0);
      hasChildren |= (aDef.getEscalationCount() > 0);
      hasChildren |= (aDef.getExtensionElementDefs().size() > 0);
      
      if (!hasChildren)
      {
         reportProblem(AeMessages.getString("AeAbstractDeadlineDefRule5Validator.0"), aDef); //$NON-NLS-1$
      }
   }
}
