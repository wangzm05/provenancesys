// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validators/AeB4PLogicalPeopleGroupFunctionValidator.java,v 1.1 2008/02/29 18:43:30 dvilaverde Exp $
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

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.validation.AeB4PValidationContext;
import org.activebpel.rt.expr.def.AeScriptFuncDef;
import org.activebpel.rt.expr.validation.AeExpressionValidationResult;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * Validator for B4P function getLogicalPeopleGroup.
 * 
 * argument name must be resolved to an logical people group within scope 
 */
public class AeB4PLogicalPeopleGroupFunctionValidator extends AeAbstractB4PFunctionValidator
{
   /**
    * @see org.activebpel.rt.b4p.validators.AeAbstractB4PFunctionValidator#validate(org.activebpel.rt.expr.def.AeScriptFuncDef, org.activebpel.rt.expr.validation.AeExpressionValidationResult, org.activebpel.rt.b4p.validation.AeB4PValidationContext, AeBaseXmlDef)
    */
   public void validate(AeScriptFuncDef aScriptFunction, AeExpressionValidationResult aResult,
         AeB4PValidationContext aValidationContext, AeBaseXmlDef aDef)
   {
      if (aScriptFunction.getArgs().size() <= 0)
         return;
      
      QName name = new QName(String.valueOf(aScriptFunction.getArgs().get(0)));
      if (aValidationContext.findLogicalPeopleGroup(aDef, name) == null)
      {
         addError(aResult, AeMessages.getString("AeB4PLogicalPeopleGroupFunctionValidator.0"), new Object[] {aScriptFunction.getArgs().get(0)}); //$NON-NLS-1$
      }
   }

}
