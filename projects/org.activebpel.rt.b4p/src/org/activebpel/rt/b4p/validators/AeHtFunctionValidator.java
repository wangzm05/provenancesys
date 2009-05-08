// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validators/AeHtFunctionValidator.java,v 1.1 2008/02/29 18:43:30 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validators;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.bpel.def.validation.expr.functions.AeAbstractFunctionValidator;
import org.activebpel.rt.expr.def.AeScriptFuncDef;
import org.activebpel.rt.expr.validation.AeExpressionValidationResult;
import org.activebpel.rt.expr.validation.IAeExpressionValidationContext;

/**
 * Validator that checks to make sure HT functions are not used in BPEL.
 */
public class AeHtFunctionValidator extends AeAbstractFunctionValidator
{
   /**
    * @see org.activebpel.rt.expr.validation.functions.IAeFunctionValidator#validate(org.activebpel.rt.expr.def.AeScriptFuncDef, org.activebpel.rt.expr.validation.AeExpressionValidationResult, org.activebpel.rt.expr.validation.IAeExpressionValidationContext)
    */
   public void validate(AeScriptFuncDef aScriptFunction, AeExpressionValidationResult aResult,
         IAeExpressionValidationContext aValidationContext)
   {
      addError(aResult, AeMessages.getString("AeHtFunctionValidator.HT_FUNCTION_NOT_ALLOWED"), new Object[] {aScriptFunction.getName()}); //$NON-NLS-1$
      
   }
}
