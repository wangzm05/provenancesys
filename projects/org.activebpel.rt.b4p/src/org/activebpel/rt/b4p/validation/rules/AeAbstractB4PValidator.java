// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeAbstractB4PValidator.java,v 1.6 2008/03/03 01:36:46 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor;
import org.activebpel.rt.b4p.def.visitors.finders.AeB4PPeopleActivityFinder;
import org.activebpel.rt.b4p.validation.IAeB4PValidationContext;
import org.activebpel.rt.expr.def.AeScriptFuncDef;
import org.activebpel.rt.expr.def.IAeExpressionParseResult;
import org.activebpel.rt.expr.validation.IAeExpressionValidationResult;
import org.activebpel.rt.ht.def.AeAbstractExpressionDef;
import org.activebpel.rt.ht.def.IAeHtExpressionDef;
import org.activebpel.rt.wsresource.validation.IAeWSResourceProblemReporter;
import org.activebpel.rt.wsresource.validation.IAeWSResourceValidationContext;
import org.activebpel.rt.wsresource.validation.IAeWSResourceValidator;
import org.activebpel.rt.wsresource.validation.rules.AeRulesUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * Base class for all B4P validation rules.
 */
public class AeAbstractB4PValidator extends AeAbstractB4PExpressionDefVisitor implements IAeWSResourceValidator
{
   /** Resource model. */
   private AeBaseXmlDef mResourceModel;
   /** Validation context. */
   private IAeB4PValidationContext mValidationContext;
   /** Problem handler. */
   private IAeWSResourceProblemReporter mProblemReporter;
   /** if set to true all unhandled exceptions will be reported */
   private static boolean sReportUnhandledExceptions = false;
   
   /**
    * @see org.activebpel.rt.wsresource.validation.IAeWSResourceValidator#validate(java.lang.Object, org.activebpel.rt.wsresource.validation.IAeWSResourceValidationContext, org.activebpel.rt.wsresource.validation.IAeWSResourceProblemReporter)
    */
   public void validate(Object aResourceModel, IAeWSResourceValidationContext aContext,
         IAeWSResourceProblemReporter aErrorReporter)
   {
      setResourceModel((AeBaseXmlDef) aResourceModel);
      setValidationContext((IAeB4PValidationContext) aContext);
      setProblemReporter(aErrorReporter);

      getResourceModel().accept(this);
   }
   
   /**
    * Delegate the reportProblem to call the Problem Reporter instance.
    * 
    * @param aMessage
    * @param aDef
    */
   protected void reportProblem(String aMessage, AeBaseXmlDef aDef)
   {
      getProblemReporter().reportProblem(aMessage, aDef);
   }
   
   /**
    * Delegate the reportProblem to call the Problem Reporter instance.
    *
    * @param aException
    * @param aDef
    */
   protected void reportException(Exception aException, AeBaseXmlDef aDef)
   {
      if (sReportUnhandledExceptions)
      {
         getProblemReporter().reportProblem(aException.getMessage(), aDef);
      }
   }
   
   /**
    * @return Returns the resourceModel.
    */
   protected AeBaseXmlDef getResourceModel()
   {
      return mResourceModel;
   }

   /**
    * @param aResourceModel the resourceModel to set
    */
   protected void setResourceModel(AeBaseXmlDef aResourceModel)
   {
      mResourceModel = aResourceModel;
   }

   /**
    * @return Returns the validationContext.
    */
   protected IAeB4PValidationContext getValidationContext()
   {
      return mValidationContext;
   }

   /**
    * @param aValidationContext the validationContext to set
    */
   protected void setValidationContext(IAeB4PValidationContext aValidationContext)
   {
      mValidationContext = aValidationContext;
   }

   /**
    * @return Returns the problemReporter.
    */
   protected IAeWSResourceProblemReporter getProblemReporter()
   {
      return mProblemReporter;
   }

   /**
    * @param aProblemReporter the problemReporter to set
    */
   protected void setProblemReporter(IAeWSResourceProblemReporter aProblemReporter)
   {
      mProblemReporter = aProblemReporter;
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor#visitExpressionDef(org.activebpel.rt.ht.def.IAeHtExpressionDef)
    */
   protected void visitExpressionDef(IAeHtExpressionDef aDef)
   {
      validateExpression(aDef, getParseResult(aDef));
   }
   
   /**
    * validate the expression and report all errors via the error reporter.
    * 
    * @param aDef
    */
   protected void validateExpressionAndReportErrors(AeAbstractExpressionDef aDef)
   {
      try
      {
         IAeB4PValidationContext ctx = getValidationContext();
         String expressionLanguage = getValidationContext().getExpressionLanguage(aDef);
         IAeExpressionValidationResult vResult = ctx.validateExpression((AeBaseXmlDef) aDef, aDef.getExpression(), expressionLanguage );
         
         for (Iterator iter = vResult.getErrors().iterator(); iter.hasNext();)
         {
            reportProblem(String.valueOf(iter.next()), (AeBaseXmlDef) aDef);
         }
      }
      catch (Exception ex)
      {
         reportProblem(ex.getMessage(), aDef);
      }  
   }
   
   /**
    * get the parse result for the expresssion
    * 
    * @param aDef
    */
   protected IAeExpressionParseResult getParseResult(IAeHtExpressionDef aDef)
   { 
      IAeExpressionParseResult vParseResult = null;
      String expr = aDef.getExpression();
      String exprLang = aDef.getExpressionLanguage();
      try
      {
         IAeExpressionValidationResult vResult = getValidationContext().validateExpression((AeBaseXmlDef) aDef, expr, exprLang);
         vParseResult = vResult.getParseResult();
      }
      catch (Exception ex)
      {
         reportException(ex, (AeBaseXmlDef) aDef);
      }
      return vParseResult;
   }
   
   /**
    * Called by all ht expression visit methods.  Will invoke method individualFunctionValidation for every
    * function in the <code>IAeExpressionParseResult</code>
    * 
    * @param aDef
    * @param aParseResult
    */
   protected void validateExpression(IAeHtExpressionDef aDef, IAeExpressionParseResult aParseResult)
   {
      if (aParseResult != null)
      {
         Set functions = aParseResult.getFunctions();
         
         for (Iterator iter = functions.iterator(); iter.hasNext();)
         {
            AeScriptFuncDef function = (AeScriptFuncDef) iter.next();
            individualFunctionValidation(aDef, function);
         }
      }
   }
   
   /**
    * For every function in the expression this function will be called.
    * @param aDef
    * @param aFunction
    */
   protected void individualFunctionValidation(IAeHtExpressionDef aDef, AeScriptFuncDef aFunction)
   {
      //NO-OP
   }
   
   /**
    * A validation function that any single arg function accepting a People activity name
    * can use to check that the people activity is in scope and resolved.
    * 
    * @param aDef
    * @param aFunction
    */
   protected void validateInScopePeopleActivity(AeBaseXmlDef aDef, AeScriptFuncDef aFunction)
   {
      if (aFunction.getArgs().size() > 0 && AeRulesUtil.findFirstNonLiteralArgument(aFunction) < 0)
      {
         String argValue = String.valueOf(aFunction.getArgs().get(0));
         Collection results = AeB4PPeopleActivityFinder.findPeopleActivities(aDef, argValue);
         if (results == null || results.size() <= 0)
         {
            String[] args = new String[] {argValue, aFunction.getName()};
            reportProblem(AeMessages.format("AeAbstractB4PFunctionValidator.0", args), aDef); //$NON-NLS-1$
         }
         else if (results.size() >= 2)
         {
            String[] args = new String[] {argValue, aFunction.getName()};
            reportProblem(AeMessages.format("AeAbstractB4PFunctionValidator.1", args), aDef); //$NON-NLS-1$
         }
      }
   }
}
