// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeB4PHumanInteractionsDefRule50Validator.java,v 1.2 2008/02/15 17:47:59 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;

/**
 * parented by scope or process
 */
public class AeB4PHumanInteractionsDefRule50Validator extends AeAbstractB4PValidator
{
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef)
    */
   public void visit(AeB4PHumanInteractionsDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * rule logic
    * 
    * @param aDef
    */
   private void executeRule(AeB4PHumanInteractionsDef aDef)
   {
      if ( !(aDef.getParentXmlDef().getParentXmlDef() instanceof AeProcessDef ||
            aDef.getParentXmlDef().getParentXmlDef() instanceof AeActivityScopeDef))
      {
         reportProblem(AeMessages.getString("AeB4PHumanInteractionsDefRule50Validator.0"), aDef); //$NON-NLS-1$
      }
   }
}
