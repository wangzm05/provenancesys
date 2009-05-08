//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/eval/AeB4PEvalFunction.java,v 1.3.4.2 2008/04/14 21:24:48 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function.eval;

import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.function.ht.AeHTExtensionFunctionFactory;
import org.activebpel.rt.b4p.function.ht.AeTaskStateBasedHtFunctionContext;
import org.activebpel.rt.bpel.IAeExpressionLanguageFactory;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.activity.AeDelegatingFunctionFactory;
import org.activebpel.rt.bpel.impl.expr.AeExpressionRunnerContext;
import org.activebpel.rt.bpel.impl.expr.IAeExpressionRunner;
import org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext;
import org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerVariableResolver;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;
import org.activebpel.rt.ht.IAeHtFunctionContext;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.AeElementBasedNamespaceContext;
import org.activebpel.rt.xml.IAeNamespaceContext;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.w3c.dom.Element;

/**
 * Function that does an eval() given b4p or ht expression and expression language.
 * This function is used to execute the expressions within the task definition
 * that are expected to run outside of the context of the calling process. 
 * These expressions are limited to those within the escalation's conditions as
 * well as the state machine's presentation elements.
 * 
 * The eval will handle regular ht function calls as well as references to variables
 * that are defined in the calling process. The context for the ht function calls
 * will come from the task state data. The context for the variable resolution is
 * passed into the lifecycle process and propagating where necessary.
 * 
 * The params to this function are as follows:
 * 
 * expression (as a string or element)
 * process variables element
 * task instance element (optional)
 * expression lang (optional)
 * 
 * If the expression is passed as an element, then this element serves as the 
 * namespace context as well as a provider for the expression language if not
 * already defined.
 * 
 * Note that the last two arguments are optional and the code will handle passing
 * either argument, both, or none.
 */
public class AeB4PEvalFunction extends AeAbstractBpelFunction
{
   /** name of the function */
   public static final String FUNCTION_NAME = "eval"; //$NON-NLS-1$
   
   /** Expression that we're executing */
   private String mExpression;
   /** Specified expression language, null causes us to use the default language */
   private String mExpressionLanguage;
   /** Used to resolve namespaces within the expression */
   private IAeNamespaceContext mNamespaceContext;
   /** Used to resolve variable references within the expression. */
   private IAeExpressionRunnerVariableResolver mVariableResolver;
   /** Context used to provide data for ht functions */
   private IAeHtFunctionContext mHtFunctionContext;
   
   /**
    * ctor 
    */
   public AeB4PEvalFunction()
   {
      super(FUNCTION_NAME);
   }

   /**
    * Overrides method to evalulate given expression. This function takes one
    * required argument and a second option argument. The first parameter
    * is the expression string. The second (and option) parameter is a string
    * representing the expression language. If the language is not specified,
    * then the default language of the currently executing WSBPEL version is used.
    *
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {
      //  arg[0] = string or node expression
      //  arg[1] = aeb4p:processVariables element
      //  arg[2] = taskInstance element - provides context for ht functions (optional)
      //  arg[3] = expression lang uri (optional)

      // Validate that we have the proper number of arguments
      validateArgs(aArgs);

      // FIXMEPJ AeB4PEvalFunction add static analysis e.g. check num args

      // determine the expression
      String expression = getExpression(aArgs.get(0));
      validateExpression(expression);
      setExpression(expression);

      // determine the expression language (could still be null here)
      Element expressionEle = getElementArg(aArgs, 0);
      String languageUri = getExpressionLanguageUri(aArgs, expressionEle);
      setExpressionLanguage(languageUri);

      // create the namespace context using the default bpel object or the expression element
      IAeNamespaceContext namespaceContext = getNamespaceContext(aContext, expressionEle);
      setNamespaceContext(namespaceContext);
      
      // get element containing a snapshot of PeopleActivity process's variables that may
      // be referenced by the expression.
      Element processVariablesElement = getProcessVariablesSnapshotElement(aArgs);
      IAeExpressionRunnerVariableResolver varResolver = toVariableResolver(
            aContext, processVariablesElement);
      setVariableResolver(varResolver);
      
      // get the task instance element (could be null)
      Element taskInstance = getElementArg(aArgs, 2);
      if (taskInstance != null)
         setHtFunctionContext(new AeTaskStateBasedHtFunctionContext(taskInstance, null));
      
      try
      {
         return eval(aContext);
      }
      catch(AeFunctionCallException e)
      {
         throw e;
      }
      catch (AeException e)
      {
         throw new AeFunctionCallException(e);
      }
   }

   /**
    * Evaluates the expression using the given namespace context and process
    * variables element. 
    * 
    * @param aContext
    */
   public Object eval(IAeFunctionExecutionContext aContext) throws AeException
   {
      IAeExpressionLanguageFactory factory = aContext.getAbstractBpelObject().getProcess().getExpressionLanguageFactory();
      String defaultLanguageUri = factory.getBpelDefaultLanguage(aContext.getAbstractBpelObject().getBPELNamespace());

      String expressionLang = null;
      if (AeUtil.isNullOrEmpty(getExpressionLanguage()))
      {
         expressionLang = defaultLanguageUri;
      }
      else
      {
         expressionLang = getExpressionLanguage();
         validateExpressionLang(aContext, factory, expressionLang);
      }

      AeDelegatingFunctionFactory functionFactory = new AeDelegatingFunctionFactory(new AeHTExtensionFunctionFactory(), aContext.getFunctionFactory());
      
      // create runner context
      IAeExpressionRunnerContext evalRunnerCtx = new AeExpressionRunnerContext(aContext.getAbstractBpelObject(), getHtFunctionContext(),
                  expressionLang, getNamespaceContext(), functionFactory, getVariableResolver());

      IAeExpressionRunner runner = factory.createExpressionRunner(aContext.getAbstractBpelObject().getBPELNamespace(), expressionLang);
      Object rval = runner.executeExpression(evalRunnerCtx, getExpression());
      return rval;
   }

   /**
    * Converts the element into a variable resolver.
    * @param aContext
    * @param aProcessVariablesElement
    * @throws AeFunctionCallException
    */
   public IAeExpressionRunnerVariableResolver toVariableResolver(
         IAeFunctionExecutionContext aContext, Element aProcessVariablesElement)
         throws AeFunctionCallException
   {
      // check element
      if (aProcessVariablesElement == null
            || !IAeB4PConstants.AEB4P_NAMESPACE.equals( aProcessVariablesElement.getNamespaceURI() )
            || !"processVariables".equals( aProcessVariablesElement.getLocalName() )) //$NON-NLS-1$
      {
            throwFunctionException(EXPECT_VALID_ARGUMENT, new String[]{FUNCTION_NAME, "aeb4p:processVariables"}); //$NON-NLS-1$
      }

      AeTypeMapping typeMapping = aContext.getAbstractBpelObject().getProcess().getEngine().getTypeMapping();
      // Special variable resolver that uses processVariablesElement as the data model to look up variables.
      IAeExpressionRunnerVariableResolver varResolver = new AeB4PEvalExpressionRunnerVariableResolver(aProcessVariablesElement, typeMapping);
      return varResolver;
   }

   /**
    * Validates that the factory is capable of executing the language
    * @param aContext
    * @param aFactory
    * @param aExpressionLang
    * @throws AeFunctionCallException
    */
   private void validateExpressionLang(IAeFunctionExecutionContext aContext,
         IAeExpressionLanguageFactory aFactory, String aExpressionLang)
         throws AeFunctionCallException
   {
      if (!aFactory.supportsLanguage(aContext.getAbstractBpelObject().getBPELNamespace(), aExpressionLang) )
      {
         throwFunctionException(UNSUPPORTED_LANG_URI, new String[]{aExpressionLang, aContext.getAbstractBpelObject().getBPELNamespace()});
      }
   }

   /**
    * Returns the expression string value. If the first argument is a element, then the expression is the element text.
    * Otherwise, the string value of the first argument is returned.
    * @param aExpressionArg
    * @throws AeFunctionCallException
    */
   protected String getExpression(Object aExpressionArg) throws AeFunctionCallException
   {
      if (aExpressionArg instanceof Element)
         return AeXmlUtil.getText((Element) aExpressionArg);
      else
         return (String) aExpressionArg;
   }

   /**
    * Returns the namespace context.
    * @param aContext
    * @param aExpressionElement
    * @return namespace context.
    * @throws AeFunctionCallException
    */
   protected IAeNamespaceContext getNamespaceContext(IAeFunctionExecutionContext aContext, Element aExpressionElement) throws AeFunctionCallException
   {
      IAeNamespaceContext namespaceContext;
      if (aExpressionElement != null)
      {
         namespaceContext = new AeElementBasedNamespaceContext(aExpressionElement);
      }
      else
      {
         namespaceContext = aContext.getAbstractBpelObject();
      }
      return namespaceContext;
   }

   /**
    * Returns the expression language URI based on the optional third argument.
    * If the optional language URI is not supplied, but the expression was given as element, then this method will
    * try to find expressionLanguage (attribute) URI via the expression context element or its enclosing
    * elements. If the URI is not found in the context element, then the default language URI is returned.
    *
    * @param aArgs
    * @param aExpressionElement
    * @return expression language URI.
    * @throws AeFunctionCallException
    */
   protected String getExpressionLanguageUri(List aArgs, Element aExpressionElement) throws AeFunctionCallException
   {
      String languageUri = null;
      // check for user overrides of language URI via third or fourth param arg.
      if (aArgs.size() == 4)
      {
         languageUri = getStringArg(aArgs, 3);
         if (AeUtil.notNullOrEmpty(languageUri))
         {
            return languageUri;
         }
      }
      
      if (aArgs.size() == 3 && aArgs.get(2) instanceof String)
      {
         languageUri = (String) aArgs.get(2);
         if (AeUtil.notNullOrEmpty(languageUri))
         {
            return languageUri;
         }
      }

      // check URI in expression context element (via expressionLanguage attribute)
      if (aExpressionElement != null)
      {
         languageUri = findExpressionLanguage(aExpressionElement);
      }
      return languageUri;
   }

   /**
    * Walks up the element heirarchy until the expression language attribute if found.
    * @param aElement
    * @return expression language uri or <code>null</code> if not found.
    */
   protected String findExpressionLanguage(Element aElement)
   {
      String languageUri = aElement.getAttribute("expressionLanguage");  //$NON-NLS-1$
      while (AeUtil.isNullOrEmpty(languageUri) && aElement.getParentNode() != null
            && aElement.getParentNode() instanceof Element)
      {
         aElement = (Element)aElement.getParentNode();
         languageUri = aElement.getAttribute("expressionLanguage");  //$NON-NLS-1$
      }
      return AeUtil.notNullOrEmpty(languageUri)? languageUri : null;
   }


   /**
    * Returns the  <code>aeb4p:processVariables</code> element containing a snapshot of
    * process variables.
    * @param aArgs
    * @return Element for <code>aeb4p:processVariables</code>
    * @throws AeFunctionCallException
    */
   protected Element getProcessVariablesSnapshotElement(List aArgs) throws AeFunctionCallException
   {
      // The aeb4p:processVariables element is the second argument.
      Element processVariablesElement = null;
      if (aArgs.get(1) instanceof Element)
      {
         processVariablesElement = (Element) aArgs.get(1);
      }
      return processVariablesElement;
   }

   /**
    * Gets the argument as an element or returns null if not set or not an element.
    * @param aArgs
    * @param aIndex
    */
   private Element getElementArg(List aArgs, int aIndex)
   {
      Element elementArg = null;
      if (aArgs.size() > aIndex && aArgs.get(aIndex) instanceof Element)
      {
         elementArg = (Element) aArgs.get(aIndex);
      }
      return elementArg;
   }

   /**
    * Validates that the expression was set.
    * 
    * @param aExpression
    * @throws AeFunctionCallException
    */
   private void validateExpression(String aExpression) throws AeFunctionCallException
   {
      // check if expression is given
      if (AeUtil.isNullOrEmpty(aExpression))
      {
         throwFunctionException(EXPECT_VALID_ARGUMENT, new String[]{FUNCTION_NAME, "expression"}); //$NON-NLS-1$
      }
   }

   /**
    * Validates the number of args for the function.
    * @param aArgs
    * @throws AeFunctionCallException
    */
   private void validateArgs(List aArgs) throws AeFunctionCallException
   {
      int numArgs = aArgs.size();
      if ( numArgs < 2 || numArgs > 4 )
      {
         throwFunctionException(INVALID_PARAMS, FUNCTION_NAME);
      }
   }

   /**
    * @return the expression
    */
   public String getExpression()
   {
      return mExpression;
   }

   /**
    * @param aExpression the expression to set
    */
   public void setExpression(String aExpression)
   {
      mExpression = aExpression;
   }

   /**
    * @return the expressionLanguage
    */
   public String getExpressionLanguage()
   {
      return mExpressionLanguage;
   }

   /**
    * @param aExpressionLanguage the expressionLanguage to set
    */
   public void setExpressionLanguage(String aExpressionLanguage)
   {
      mExpressionLanguage = aExpressionLanguage;
   }

   /**
    * @return the namespaceContext
    */
   public IAeNamespaceContext getNamespaceContext()
   {
      return mNamespaceContext;
   }

   /**
    * @param aNamespaceContext the namespaceContext to set
    */
   public void setNamespaceContext(IAeNamespaceContext aNamespaceContext)
   {
      mNamespaceContext = aNamespaceContext;
   }

   /**
    * @return the variableResolver
    */
   public IAeExpressionRunnerVariableResolver getVariableResolver()
   {
      return mVariableResolver;
   }

   /**
    * @param aVariableResolver the variableResolver to set
    */
   public void setVariableResolver(
         IAeExpressionRunnerVariableResolver aVariableResolver)
   {
      mVariableResolver = aVariableResolver;
   }

   /**
    * @return the htFunctionContext
    */
   public IAeHtFunctionContext getHtFunctionContext()
   {
      return mHtFunctionContext;
   }

   /**
    * @param aHtFunctionContext the htFunctionContext to set
    */
   public void setHtFunctionContext(IAeHtFunctionContext aHtFunctionContext)
   {
      mHtFunctionContext = aHtFunctionContext;
   }
}
