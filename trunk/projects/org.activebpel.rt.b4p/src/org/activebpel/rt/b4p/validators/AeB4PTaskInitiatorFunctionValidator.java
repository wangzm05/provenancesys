// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validators/AeB4PTaskInitiatorFunctionValidator.java,v 1.1 2008/02/29 18:43:30 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validators;

import org.activebpel.rt.b4p.validation.AeB4PValidationContext;
import org.activebpel.rt.expr.def.AeScriptFuncDef;
import org.activebpel.rt.expr.validation.AeExpressionValidationResult;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * Validator for B4P function getTaskInitiator.
 * 
 * People activity name must be resolved to a people activity within scope. 
 * This reference cannot be ambiguous.
 */
public class AeB4PTaskInitiatorFunctionValidator extends AeAbstractB4PFunctionValidator
{
   /**
    * @see org.activebpel.rt.b4p.validators.AeAbstractB4PFunctionValidator#validate(org.activebpel.rt.expr.def.AeScriptFuncDef, org.activebpel.rt.expr.validation.AeExpressionValidationResult, org.activebpel.rt.b4p.validation.AeB4PValidationContext, org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   protected void validate(AeScriptFuncDef aScriptFunction, AeExpressionValidationResult aResult,
         AeB4PValidationContext aValidationContext, AeBaseXmlDef aDef)
   {
      checkPeopleActivityArgumentReference(aScriptFunction, aResult, aDef);
   }
}
