// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeLogicalPeopleGroupRule107Validator.java,v 1.3 2008/02/26 15:14:38 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import javax.xml.namespace.QName;

import org.activebpel.rt.expr.def.AeScriptFuncDef;
import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.IAeHtFunctionNames;
import org.activebpel.rt.ht.def.AeAbstractExpressionDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.util.AeXmlUtil;

/**
 * lpgName must resolve to an enclosing lpg
 */
public class AeLogicalPeopleGroupRule107Validator extends AeAbstractHtExpressionValidator
{
   /**
    * @see org.activebpel.rt.ht.def.validation.rules.AeAbstractHtExpressionValidator#individualFunctionValidation(org.activebpel.rt.ht.def.AeAbstractExpressionDef, org.activebpel.rt.expr.def.AeScriptFuncDef)
    */
   protected void individualFunctionValidation(AeAbstractExpressionDef aDef, AeScriptFuncDef aFunction)
   {
      if(IAeHtFunctionNames.LOGICAL_PEOPLE_GROUP_FUNCTION_NAME.equalsIgnoreCase(aFunction.getName()))
      {
         AeLogicalPeopleGroupDef lpg = null;
         
         // make sure the function has the required argument and that it is a string argument
         if (aFunction.getArgs().size() > 0)
         {
            //if its a string argument then resolve lpg, otherwise ignore
            if (aFunction.isStringArgument(0))
            {
               // resolve the namespace if the argument is prefixed and create a QName
               String argument = aFunction.getStringArgument(0);
               String prefix = AeXmlUtil.extractPrefix(argument);
               String localPart = AeXmlUtil.extractLocalPart(argument);
               QName lpgQName = new QName(getValidationContext().getNamespaceURI(prefix), localPart);
               
               lpg = getValidationContext().findLogicalPeopleGroup(aDef, lpgQName);
      
               if (lpg == null)
               {
                  reportProblem(AeMessages.getString("AeLogicalPeopleGroupRule107Validator.0"), aDef);  //$NON-NLS-1$
               }
            }
         }
         else
         {
            reportProblem(AeMessages.format("AeLogicalPeopleGroupRule107Validator.FUNCTION_MISSING_ARGUMENT", new Object[] {aFunction.getName()}), aDef);  //$NON-NLS-1$
         }
      }
   }

}
