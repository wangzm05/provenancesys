// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/AeB4PValidator.java,v 1.6 2008/02/17 21:36:03 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation;

import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.def.AeB4PBaseDef;
import org.activebpel.rt.bpel.IAeExpressionLanguageFactory;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.validation.IAeValidationContext;
import org.activebpel.rt.expr.validation.functions.IAeFunctionValidatorFactory;
import org.activebpel.rt.wsdl.IAeContextWSDLProvider;
import org.activebpel.rt.wsresource.validation.AeWSResourceValidationEngine;
import org.activebpel.rt.wsresource.validation.IAeWSResourceProblemHandler;
import org.activebpel.rt.wsresource.validation.IAeWSResourceValidationContext;
import org.activebpel.rt.wsresource.validation.IAeWSResourceValidationPreferences;
import org.activebpel.rt.wsresource.validation.rules.AeWSResourceValidationRuleRegistry;

/**
 * This class is a holder for the static instance of the validation engine
 * which is lazily instantiated when validation is requested.
 */
public class AeB4PValidator
{
   /** Rule registry loaded with b4p rules */
   private static AeWSResourceValidationRuleRegistry sRuleRegistry;

   static
   {
      sRuleRegistry = new AeWSResourceValidationRuleRegistry();
      sRuleRegistry.loadRules(AeB4PValidator.class.getResource("b4p-rules.xml")); //$NON-NLS-1$
   }

   /**
    * Gets the static rule registry preloaded with rules for b4p.
    */
   protected static AeWSResourceValidationRuleRegistry getRuleRegistryInstance()
   {
      return sRuleRegistry;
   }

   /**
    * Validate the resource using the cached validation rules
    *
    * @param aB4PDef
    * @param aPreferences
    * @param aValidationContext
    */
   public static void validate(AeB4PBaseDef aB4PDef, IAeWSResourceValidationPreferences aPreferences,
         IAeValidationContext aValidationContext)
   {
      IAeContextWSDLProvider wsdlProvider = aValidationContext.getContextWSDLProvider();
      IAeExpressionLanguageFactory expFactory = aValidationContext.getExpressionLanguageFactory();
      IAeFunctionValidatorFactory functionFactory = aValidationContext.getFunctionValidatorFactory();

      IAeWSResourceValidationContext ctx = createContext(aB4PDef, wsdlProvider, expFactory, functionFactory);
      AeWSResourceValidationEngine engine = new AeWSResourceValidationEngine(getRuleRegistryInstance(), ctx);
      IAeWSResourceProblemHandler problemHandler = new AeB4PProblemHandler(aB4PDef, aValidationContext.getErrorReporter());

      engine.validate(aB4PDef, IAeB4PConstants.B4P_NAMESPACE, aPreferences, problemHandler);
   }

   /**
    * Creates a context
    *
    * @param aB4PDef
    */
   protected static IAeWSResourceValidationContext createContext(AeB4PBaseDef aB4PDef,
         IAeContextWSDLProvider aWSDLProvider, IAeExpressionLanguageFactory aExpressionFactory,
         IAeFunctionValidatorFactory aFunctionFactory)
   {
      AeProcessDef def = AeDefUtil.getProcessDef(aB4PDef);
      return new AeB4PValidationContext(def, aWSDLProvider, aExpressionFactory, aFunctionFactory);
   }
}
