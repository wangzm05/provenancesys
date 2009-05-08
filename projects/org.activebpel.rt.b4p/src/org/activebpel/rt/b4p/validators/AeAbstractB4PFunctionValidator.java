// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validators/AeAbstractB4PFunctionValidator.java,v 1.3 2008/03/03 22:01:02 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validators;

import java.util.Collection;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.def.visitors.finders.AeB4PPeopleActivityFinder;
import org.activebpel.rt.b4p.validation.AeB4PValidationContext;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.validation.expr.functions.AeAbstractFunctionValidator;
import org.activebpel.rt.expr.def.AeScriptFuncDef;
import org.activebpel.rt.expr.validation.AeExpressionValidationResult;
import org.activebpel.rt.expr.validation.IAeExpressionValidationContext;
import org.activebpel.rt.wsresource.validation.rules.AeRulesUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * An abstract class for all validators that are validating B4P functions. This class
 * will provide the B4P function validator an instance of the <code>AeB4PValidationContext</code>.
 * 
 * Note: The <code>AeB4PValidationContext</code> currently can not resolve WSDL references
 * or validate expressions.
 */
public abstract class AeAbstractB4PFunctionValidator extends AeAbstractFunctionValidator
{
   /**
    * @see org.activebpel.rt.expr.validation.functions.IAeFunctionValidator#validate(org.activebpel.rt.expr.def.AeScriptFuncDef, org.activebpel.rt.expr.validation.AeExpressionValidationResult, org.activebpel.rt.expr.validation.IAeExpressionValidationContext)
    */
   public void validate(AeScriptFuncDef aScriptFunction, AeExpressionValidationResult aResult,
         IAeExpressionValidationContext aValidationContext)
   {
      // Don't do the validation if this isn't a BPEL def.  Rules validation will pickup other domains.
      if (!(aValidationContext.getBaseDef() instanceof AeBaseDef))
         return;
      
      AeProcessDef processDef = AeDefUtil.getProcessDef(aValidationContext.getBaseDef());
      
      // Create an instance of the AeB4PValidationContext to get access to the logical
      // people group resolving code, so this AeB4PValidationContext is created with null values 
      // for wsdl context provider and expression language factory.  If the b4p context is needed
      // to resolve anything WSDL or expression related, then somehow those null values
      // will need to be replaced with instances of wsdl context provider and expression language
      // factory.
      AeB4PValidationContext ctx = new AeB4PValidationContext(processDef, null, null, aValidationContext.getFunctionFactory());
      checkForLiteralArgs(aScriptFunction, aResult);
      validate(aScriptFunction, aResult, ctx, aValidationContext.getBaseDef());
   }

   /**
    * Validates the syntax of the function, adding any errors to the result
    * passed in.
    * @param aScriptFunction - function to validate
    * @param aResult - report errors on the result
    * @param aValidationContext - context for the enclosing expression
    * @param aDef - expression def
    */
   protected abstract void validate(AeScriptFuncDef aScriptFunction, AeExpressionValidationResult aResult,
         AeB4PValidationContext aValidationContext, AeBaseXmlDef aDef);
   
   
   /**
    * A method to validate the function's PeopleActivity argument is both resolved and in scope. 
    * 
    * Note: this will only work with functions that take a single argument 
    * 
    * @param aScriptFunction
    * @param aResult
    * @param aDef
    */
   protected void checkPeopleActivityArgumentReference(AeScriptFuncDef aScriptFunction, 
                                                       AeExpressionValidationResult aResult, AeBaseXmlDef aDef)
   {
      if (aScriptFunction.getArgs().size() <= 0 || aScriptFunction.getArgs().get(0) == AeScriptFuncDef.__EXPRESSION__)
         return;
      
      String argumentValue = String.valueOf(aScriptFunction.getArgs().get(0));
      Collection foundDefs  = AeB4PPeopleActivityFinder.findPeopleActivities(aDef, argumentValue);
      
      if (foundDefs == null || foundDefs.size() <= 0)
      {
         String[] args = new String[] {argumentValue, aScriptFunction.getName()};
         addWarning(aResult, AeMessages.getString("AeAbstractB4PFunctionValidator.0"), args); //$NON-NLS-1$
      }
      else if (foundDefs.size() >= 2)
      {
         String[] args = new String[] {argumentValue, aScriptFunction.getName()};
         addWarning(aResult, AeMessages.getString("AeAbstractB4PFunctionValidator.1"), args); //$NON-NLS-1$
      }
   }
   
   /**
    * Check to see if the arguments in the function are all literals.
    * @param aScriptFunction
    * @param aResult
    */
   protected void checkForLiteralArgs(AeScriptFuncDef aScriptFunction, AeExpressionValidationResult aResult)
   {
      int index = AeRulesUtil.findFirstNonLiteralArgument(aScriptFunction);
      if (index >= 0)
      {
         addError(aResult, AeMessages.getString("AeB4PLiteralArgumentValidator.LITERAL_ARGUMENTS"), //$NON-NLS-1$
                                                new Object[] {String.valueOf(index), aScriptFunction.getName()}); 
      }
   }
}
