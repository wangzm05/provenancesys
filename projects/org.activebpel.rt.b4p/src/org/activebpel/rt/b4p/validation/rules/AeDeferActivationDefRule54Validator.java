// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeDeferActivationDefRule54Validator.java,v 1.2 2008/02/15 17:47:59 EWittmann Exp $
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
import org.activebpel.rt.b4p.def.AeDeferActivationDef;

/**
 * has for or until
 */
public class AeDeferActivationDefRule54Validator extends AeAbstractB4PValidator
{
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeDeferActivationDef)
    */
   public void visit(AeDeferActivationDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * rule logic
    * @param aDef
    */
   protected void executeRule(AeDeferActivationDef aDef)
   {
      if ( (aDef.getForDef() == null && aDef.getUntilDef() == null) || 
            (aDef.getForDef() != null && aDef.getUntilDef() != null) )
      {
         reportProblem(AeMessages.getString("AeDeferActivationDefRule54Validator.0"), aDef); //$NON-NLS-1$
      }
   }
}
