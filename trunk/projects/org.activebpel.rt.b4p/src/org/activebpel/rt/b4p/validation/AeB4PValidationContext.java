// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/AeB4PValidationContext.java,v 1.9 2008/02/17 21:36:03 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.def.visitors.AeValidatingTraversingB4PDefVisitor;
import org.activebpel.rt.b4p.def.visitors.finders.AeB4PResourceFinder;
import org.activebpel.rt.bpel.IAeExpressionLanguageFactory;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.expr.validation.AeExpressionValidationContext;
import org.activebpel.rt.expr.validation.IAeExpressionValidationContext;
import org.activebpel.rt.expr.validation.IAeExpressionValidationResult;
import org.activebpel.rt.expr.validation.IAeExpressionValidator;
import org.activebpel.rt.expr.validation.functions.IAeFunctionValidatorFactory;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeQueryDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.ht.def.IAeHtExpressionDef;
import org.activebpel.rt.ht.def.validation.AeHtValidationContext;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.IAeContextWSDLProvider;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * A validation context for B4P that provides access to the resources of
 * a BPEL process.
 */
public class AeB4PValidationContext extends AeHtValidationContext implements IAeB4PValidationContext
{
   /** the parent process def of the HT def */
   private AeProcessDef mProcessDef;
   /** Factory for expression validation */
   private IAeExpressionLanguageFactory mExpressionFactory;
   /** Factory for function validation */
   private IAeFunctionValidatorFactory mFunctionFactory;
   
   /**
    * C'tor
    * @param aDef
    * @param aProvider
    * @param aExpressionFactory
    * @param aFunctionFactory
    */
   public AeB4PValidationContext(AeProcessDef aDef, IAeContextWSDLProvider aProvider, 
                                 IAeExpressionLanguageFactory aExpressionFactory,
                                 IAeFunctionValidatorFactory aFunctionFactory)
   {
      super(aProvider);
      setProcessDef(aDef);
      setExpressionFactory(aExpressionFactory);
      setFunctionFactory(aFunctionFactory);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.validation.AeHtValidationContext#findLogicalPeopleGroup(org.activebpel.rt.xml.def.AeBaseXmlDef, javax.xml.namespace.QName)
    */
   public AeLogicalPeopleGroupDef findLogicalPeopleGroup(AeBaseXmlDef aDef, QName aLPGName)
   {
      return AeB4PResourceFinder.findLogicalPeopleGroup(aDef, aLPGName);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.validation.AeHtValidationContext#findNotification(org.activebpel.rt.xml.def.AeBaseXmlDef, javax.xml.namespace.QName)
    */
   public AeNotificationDef findNotification(AeBaseXmlDef aContext, QName aNotificationName)
   {
      return AeB4PResourceFinder.findNotification(aContext, aNotificationName);
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.AeHtValidationContext#findTask(org.activebpel.rt.xml.def.AeBaseXmlDef, javax.xml.namespace.QName)
    */
   public AeTaskDef findTask(AeBaseXmlDef aContext, QName aTaskName)
   {
      return AeB4PResourceFinder.findTask(aContext, aTaskName);
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.AeHtValidationContext#validateExpression(org.activebpel.rt.xml.def.AeBaseXmlDef, java.lang.String, java.lang.String)
    */
   public IAeExpressionValidationResult validateExpression(AeBaseXmlDef aContextDef, 
                                                            String aExpression, 
                                                            String aExpressionLanguage) throws Exception
   {
      IAeExpressionValidator validator = getExpressionFactory().createExpressionValidator(getProcessDef().getNamespace(), aExpressionLanguage);
      IAeExpressionValidationContext context = new AeExpressionValidationContext(aContextDef, getFunctionFactory(), getProcessDef().getNamespace());
      return validator.validateExpression(context, aExpression);
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.AeHtValidationContext#validateQuery(org.activebpel.rt.ht.def.AeQueryDef)
    */
   public IAeExpressionValidationResult validateQuery(AeQueryDef aQuery) throws Exception
   {
      // TODO (DV) XPath 1.0 is only supported right now, in the future the expression language specified 
      // in the QueryDef may need to be supported. 
      return validateExpression(aQuery, aQuery.getTextValue(), IAeHtDefConstants.WSBPEL_EXPR_LANGUAGE_URI);
   }

   /**
    * @see org.activebpel.rt.b4p.validation.IAeB4PValidationContext#getProcessDef()
    */
   public AeProcessDef getProcessDef()
   {
      return mProcessDef;
   }
   
   /**
    * @see org.activebpel.rt.ht.def.validation.AeHtValidationContext#getNamespaceURI(java.lang.String)
    */
   public String getNamespaceURI(String aPrefix)
   {
      return getProcessDef().getNamespace(aPrefix);
   }

   /**
    * @param aProcessDef the processDef to set
    */
   protected void setProcessDef(AeProcessDef aProcessDef)
   {
      mProcessDef = aProcessDef;
   }

   /**
    * @return Returns the aExpressionFactory.
    */
   protected IAeExpressionLanguageFactory getExpressionFactory()
   {
      return mExpressionFactory;
   }

   /**
    * @param aExpressionFactory the aExpressionFactory to set
    */
   protected void setExpressionFactory(IAeExpressionLanguageFactory aExpressionFactory)
   {
      mExpressionFactory = aExpressionFactory;
   }

   /**
    * @return Returns the mFunctionFactory.
    */
   protected IAeFunctionValidatorFactory getFunctionFactory()
   {
      return mFunctionFactory;
   }

   /**
    * @param aFunctionFactory the mFunctionFactory to set
    */
   protected void setFunctionFactory(IAeFunctionValidatorFactory aFunctionFactory)
   {
      this.mFunctionFactory = aFunctionFactory;
   }
   
   /**
    * @see org.activebpel.rt.ht.def.validation.AeHtValidationContext#getExpressionLanguage(org.activebpel.rt.ht.def.IAeHtExpressionDef)
    */
   public String getExpressionLanguage(IAeHtExpressionDef aDef)
   {
      String expressionLanguage = aDef.getExpressionLanguage();
      if (AeUtil.isNullOrEmpty(expressionLanguage))
      {
         expressionLanguage = getProcessDef().getExpressionLanguage();
      }
      
      if (AeUtil.isNullOrEmpty(expressionLanguage))
      {
         expressionLanguage = IAeBPELConstants.WSBPEL_EXPR_LANGUAGE_URI;
      }
      
      return expressionLanguage;
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.AeHtValidationContext#createRuleTraverser(org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor)
    */
   public IAeBaseXmlDefVisitor createRuleTraverser(IAeHtDefVisitor aRule)
   {
      return new AeValidatingTraversingB4PDefVisitor(aRule);
   }
}
