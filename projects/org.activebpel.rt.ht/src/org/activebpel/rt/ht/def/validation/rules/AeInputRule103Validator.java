// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeInputRule103Validator.java,v 1.3 2008/03/03 22:44:54 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import javax.xml.namespace.QName;

import org.activebpel.rt.expr.def.AeScriptFuncDef;
import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.IAeHtFunctionNames;
import org.activebpel.rt.ht.def.AeAbstractExpressionDef;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.util.AeUtil;

/**
 * partName is required for single part messages
 */
public class AeInputRule103Validator extends AeAbstractHtExpressionValidator
{
   /** function QName being validated*/
   private static QName sFunctionName = new QName(IAeHtDefConstants.DEFAULT_HT_NS, IAeHtFunctionNames.INPUT_FUNCTION_NAME);
   
   /**
    * @see org.activebpel.rt.ht.def.validation.rules.AeAbstractHtExpressionValidator#individualFunctionValidation(org.activebpel.rt.ht.def.AeAbstractExpressionDef, org.activebpel.rt.expr.def.AeScriptFuncDef)
    */
   protected void individualFunctionValidation(AeAbstractExpressionDef aDef, AeScriptFuncDef aFunction)
   {
      if (AeUtil.compareObjects(sFunctionName, aFunction.getQName()))
      {
         // report a problem if there is not at least 1 argument
         if (aFunction.getArgs().size() < 1)
         {
            reportProblem(AeMessages.getString("AeInputRule103Validator.0"), aDef);  //$NON-NLS-1$
         }
      }
   }
}
