// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeInputRule102Validator.java,v 1.2 2008/02/29 18:36:38 dvilaverde Exp $
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

import org.activebpel.rt.expr.def.IAeExpressionParseResult;
import org.activebpel.rt.ht.IAeHtFunctionNames;
import org.activebpel.rt.ht.def.AeAbstractExpressionDef;
import org.activebpel.rt.ht.def.IAeHtDefConstants;

/**
 * optional task name must resolve to immediately enclosing task def. i.e. parent task 
 * for a notification within an escalation handler.
 */
public class AeInputRule102Validator extends AeAbstractHtExpressionValidator
{
   /** function QName being validated*/
   private static QName sFunctionName = new QName(IAeHtDefConstants.DEFAULT_HT_NS, IAeHtFunctionNames.INPUT_FUNCTION_NAME);
   
   /**
    * @see org.activebpel.rt.ht.def.validation.rules.AeAbstractHtExpressionValidator#validateHtExpression(org.activebpel.rt.ht.def.AeAbstractExpressionDef, org.activebpel.rt.expr.def.IAeExpressionParseResult)
    */
   public void validateHtExpression(AeAbstractExpressionDef aDef, IAeExpressionParseResult aParseResult)
   {  
      validateOptionalTaskNameArgument(aDef, aParseResult, sFunctionName, 1, 2);
   }
}
