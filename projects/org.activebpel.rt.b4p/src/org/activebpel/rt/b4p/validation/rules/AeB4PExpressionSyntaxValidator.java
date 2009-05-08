// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeB4PExpressionSyntaxValidator.java,v 1.1.4.1 2008/04/21 16:08:04 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import java.util.Iterator;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.expr.def.IAeExpressionParseResult;
import org.activebpel.rt.expr.validation.IAeExpressionValidationResult;
import org.activebpel.rt.ht.def.IAeHtExpressionDef;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * Validate the syntax for HT &amp; B4P expressions.
 */
public class AeB4PExpressionSyntaxValidator extends AeAbstractB4PValidator
{
   /**
    * @see org.activebpel.rt.b4p.validation.rules.AeAbstractB4PValidator#visitExpressionDef(org.activebpel.rt.ht.def.IAeHtExpressionDef)
    */
   protected void visitExpressionDef(IAeHtExpressionDef aDef)
   {
      String expr = aDef.getExpression();
      String exprLang = aDef.getExpressionLanguage();
      try
      {
         IAeExpressionValidationResult vResult = getValidationContext().validateExpression((AeBaseXmlDef)aDef, expr, exprLang);
         IAeExpressionParseResult parseResult = vResult.getParseResult();
         if (parseResult != null && parseResult.hasErrors())
         {
            for (Iterator errors = parseResult.getParseErrors().iterator(); errors.hasNext();)
            {
               String errorMsg = String.valueOf(errors.next());
               reportProblem(errorMsg, (AeBaseXmlDef) aDef);
            }
         }
      }
      catch (AeException aex)
      {
         reportProblem(AeMessages.format("AeB4PExpressionSyntaxValidator.EXPRESSION_SYNTAX",new Object[] {aex.getMessage()}), (AeBaseXmlDef) aDef); //$NON-NLS-1$
      }
      catch (Exception ex)
      {
         reportException(ex, (AeBaseXmlDef) aDef);
      }
   }

}
