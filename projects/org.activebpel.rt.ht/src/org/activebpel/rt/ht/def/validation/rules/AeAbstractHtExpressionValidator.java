// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeAbstractHtExpressionValidator.java,v 1.6 2008/02/29 18:35:54 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.expr.def.AeScriptFuncDef;
import org.activebpel.rt.expr.def.IAeExpressionParseResult;
import org.activebpel.rt.expr.validation.IAeExpressionValidationResult;
import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeAbstractExpressionDef;
import org.activebpel.rt.ht.def.AeArgumentDef;
import org.activebpel.rt.ht.def.AeConditionDef;
import org.activebpel.rt.ht.def.AeForDef;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AePresentationParameterDef;
import org.activebpel.rt.ht.def.AePriorityDef;
import org.activebpel.rt.ht.def.AeProcessDataExpressionDef;
import org.activebpel.rt.ht.def.AeSearchByDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeToPartDef;
import org.activebpel.rt.ht.def.AeUntilDef;
import org.activebpel.rt.ht.def.validation.IAeHtValidationContext;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * Base class for all HT expression validation rules.
 */
public abstract class AeAbstractHtExpressionValidator extends AeAbstractHtValidator
{
   /**
    * Called by all ht expression visit methods.  Will invoke method individualFunctionValidation for every
    * function in the <code>IAeExpressionParseResult</code>
    * 
    * @param aDef
    * @param aParseResult
    */
   protected void validateHtExpression(AeAbstractExpressionDef aDef, IAeExpressionParseResult aParseResult)
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
    * validate the expression and report all problems via the problem reporter.
    * 
    * @param aDef
    */
   protected void validateExpressionAndReportProblems(AeAbstractExpressionDef aDef)
   {
      try
      {
         IAeHtValidationContext ctx = getValidationContext();
         String expressionLanguage = getValidationContext().getExpressionLanguage(aDef);
         IAeExpressionValidationResult vResult = ctx.validateExpression((AeBaseXmlDef) aDef, aDef.getExpression(), expressionLanguage );
         IAeExpressionParseResult parseResult = vResult.getParseResult();
         if (parseResult != null)
         {
            for (Iterator iter = parseResult.getParseErrors().iterator(); iter.hasNext();)
            {
               reportProblem(String.valueOf(iter.next()), (AeBaseXmlDef) aDef);
            }
         }
      }
      catch (Exception ex)
      {
         reportProblem(ex.getMessage(), aDef);
      }  
   }
                                                      
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeArgumentDef)
    */
   public void visit(AeArgumentDef aDef)
   {
      validateHtExpression(aDef, getParseResultNoExceptions(aDef));
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeConditionDef)
    */
   public void visit(AeConditionDef aDef)
   {
      validateHtExpression(aDef, getParseResultNoExceptions(aDef));
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeForDef)
    */
   public void visit(AeForDef aDef)
   {
      validateHtExpression(aDef, getParseResultNoExceptions(aDef));
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      validateHtExpression(aDef, getParseResultNoExceptions(aDef));
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationParameterDef)
    */
   public void visit(AePresentationParameterDef aDef)
   {
      validateHtExpression(aDef, getParseResultNoExceptions(aDef));
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePriorityDef)
    */
   public void visit(AePriorityDef aDef)
   {
      validateHtExpression(aDef, getParseResultNoExceptions(aDef));
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeProcessDataExpressionDef)
    */
   public void visit(AeProcessDataExpressionDef aDef)
   {
      validateHtExpression(aDef, getParseResultNoExceptions(aDef));
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeSearchByDef)
    */
   public void visit(AeSearchByDef aDef)
   {
      validateHtExpression(aDef, getParseResultNoExceptions(aDef));
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartDef)
    */
   public void visit(AeToPartDef aDef)
   {
      validateHtExpression(aDef, getParseResultNoExceptions(aDef));
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeUntilDef)
    */
   public void visit(AeUntilDef aDef)
   {
      validateHtExpression(aDef, getParseResultNoExceptions(aDef));
      super.visit(aDef);
   }
   
   /**
    * get the parse result for the expresssion, returning null instead of exceptions
    * @param aDef
    */
   protected IAeExpressionParseResult getParseResultNoExceptions(AeAbstractExpressionDef aDef)
   {
      try
      {
         return getParseResult(aDef);
      }
      catch(AeException ex)
      {
         return null;
      }
   }
   
   /**
    * get the parse result for the expresssion
    * 
    * @param aDef
    */
   protected IAeExpressionParseResult getParseResult(AeAbstractExpressionDef aDef) throws AeException
   { 
      IAeExpressionParseResult vParseResult = null;
      String expr = aDef.getExpression();
      String exprLang = aDef.getExpressionLanguage();
      try
      {
         IAeExpressionValidationResult vResult = getValidationContext().validateExpression(aDef, expr, exprLang);
         vParseResult = vResult.getParseResult();
      }
      catch (AeException aex)
      {
         throw aex;
      }
      catch (Exception ex)
      {
         reportException(ex, aDef);
      }
      return vParseResult;
   }
   
   /**
    * returns the first occurrance of a task that is in scope, beginning at the context def.
    * 
    * If an AeTaskDef is not found, then null is returned.
    * 
    * @param aContextDef
    */
   protected AeTaskDef findInScopeTask(AeBaseXmlDef aContextDef)
   {
      AeTaskDef task = null;
      AeBaseXmlDef def = aContextDef;
      
      while (def.getParentXmlDef() != null)
      {
         def = def.getParentXmlDef();
         if (def instanceof AeTaskDef)
         {
            task = (AeTaskDef) def;
            break;
         }
      }
      
      return task;
   }
   
   /**
    * Validate that the optional task name must resolve to immediately enclosing task def. 
    * i.e. parent task for a notification within an escalation handler.
    * 
    * Problems will be reported at the location path of the supplied <code>AeAbstractExpressionDef</code>
    * 
    * @param aDef
    * @param aParseResult
    * @param aFunctionName the function name to be validated in the expression
    * @param aArgumentIndex position of the optional task name argument within the function 
    * @param aArgumentCount total number of arguments allowed for function
    */
   protected void validateOptionalTaskNameArgument(AeAbstractExpressionDef aDef, IAeExpressionParseResult aParseResult, 
                                                   QName aFunctionName, int aArgumentIndex, int aArgumentCount)
   {
      if (aParseResult != null)
      {
         Set functions = aParseResult.getFunctions();
         
         for (Iterator iter = functions.iterator(); iter.hasNext();)
         {
            AeScriptFuncDef function = (AeScriptFuncDef) iter.next();
            
            if (AeUtil.compareObjects(aFunctionName, function.getQName()))
            {
               // if there is a literal task name argument then continue with validation
               
               if (function.isStringArgument(aArgumentIndex) && function.getArgs().size() == aArgumentCount)
               {
                  String argTaskName = function.getStringArgument(aArgumentIndex);
                  AeTaskDef task = findInScopeTask(aDef);

                  if (task == null || !AeUtil.compareObjects(task.getName(), argTaskName))
                  {
                     reportProblem(AeMessages.getString("AeAbstractHtExpressionValidator.0"), aDef); //$NON-NLS-1$
                  }
               }
            }
         }
      }  
   }
   
   /**
    * For every function in the expression this function will be called.
    * @param aDef
    * @param aFunction
    */
   protected void individualFunctionValidation(AeAbstractExpressionDef aDef, AeScriptFuncDef aFunction)
   {
      //NO-OP
   }
}
