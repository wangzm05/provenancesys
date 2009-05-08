// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeB4PLiteralArgumentValidator.java,v 1.2 2008/02/29 18:43:05 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import java.util.HashSet;
import java.util.Set;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.expr.def.AeScriptFuncDef;
import org.activebpel.rt.ht.IAeHtFunctionNames;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.ht.def.IAeHtExpressionDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsresource.validation.rules.AeRulesUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * Report problems if any functions use expressions as arguments
 * 
 * This rule won't run for except/intersect/union functions
 */
public class AeB4PLiteralArgumentValidator extends AeAbstractB4PValidator
{
   /** Functions that are excluded from this validator */
   private static Set sExclusions;
   
   /**
    * Setup the exclusion list
    */
   static
   {
      if (sExclusions == null)
      {
         sExclusions = new HashSet();
         sExclusions.add(IAeHtFunctionNames.UNION_FUNCTION_NAME);
         sExclusions.add(IAeHtFunctionNames.INTERSECT_FUNCTION_NAME);
         sExclusions.add(IAeHtFunctionNames.EXCEPT_FUNCTION_NAME);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.validation.rules.AeAbstractB4PValidator#individualFunctionValidation(org.activebpel.rt.ht.def.IAeHtExpressionDef, org.activebpel.rt.expr.def.AeScriptFuncDef)
    */
   protected void individualFunctionValidation(IAeHtExpressionDef aDef, AeScriptFuncDef aFunction)
   {
      //don't execute if the function is in the excluded list
      if (sExclusions.contains(aFunction.getName()))
      {
         return;
      }
      
      if ((AeUtil.compareObjects(IAeHtDefConstants.DEFAULT_HT_NS, aFunction.getNamespace()) ||
                AeUtil.compareObjects(IAeB4PConstants.B4P_NAMESPACE, aFunction.getNamespace()) ))
      {
         int index = AeRulesUtil.findFirstNonLiteralArgument(aFunction);
        
         if (index >= 0)
         {
            reportProblem(AeMessages.format("AeB4PLiteralArgumentValidator.LITERAL_ARGUMENTS", //$NON-NLS-1$
                           new Object[] {String.valueOf(index), aFunction.getName()}), (AeBaseXmlDef) aDef); 
         }
      }
   }
}
