// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AePotentialOwnersRule74Validator.java,v 1.1 2008/02/29 18:43:21 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.function.AeB4PGetPotentialOwnersFunction;
import org.activebpel.rt.expr.def.AeScriptFuncDef;
import org.activebpel.rt.ht.def.IAeHtExpressionDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * name must be resolved to a people activity within scope. This reference cannot be ambiguous.
 */
public class AePotentialOwnersRule74Validator extends AeAbstractB4PValidator
{
   /** QName for the function being validated */
   private static QName sFunctionName = new QName(IAeB4PConstants.B4P_NAMESPACE, AeB4PGetPotentialOwnersFunction.FUNCTION_NAME);
   
   /**
    * @see org.activebpel.rt.b4p.validation.rules.AeAbstractB4PValidator#individualFunctionValidation(org.activebpel.rt.ht.def.IAeHtExpressionDef, org.activebpel.rt.expr.def.AeScriptFuncDef)
    */
   protected void individualFunctionValidation(IAeHtExpressionDef aDef, AeScriptFuncDef aFunction)
   {
      if (AeUtil.compareObjects(sFunctionName, aFunction.getQName()))
      {
         validateInScopePeopleActivity((AeBaseXmlDef) aDef, aFunction);
      }
   }
}
