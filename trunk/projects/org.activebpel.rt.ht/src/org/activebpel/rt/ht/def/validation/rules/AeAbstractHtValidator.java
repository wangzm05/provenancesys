// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeAbstractHtValidator.java,v 1.6 2008/02/15 17:40:57 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import org.activebpel.rt.ht.def.validation.IAeHtValidationContext;
import org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor;
import org.activebpel.rt.wsresource.validation.IAeWSResourceProblemReporter;
import org.activebpel.rt.wsresource.validation.IAeWSResourceValidationContext;
import org.activebpel.rt.wsresource.validation.IAeWSResourceValidator;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * Base class for all HT validation rules.
 */
public abstract class AeAbstractHtValidator extends AeAbstractHtDefVisitor implements IAeWSResourceValidator
{
   /** Resource model. */
   private AeBaseXmlDef mResourceModel;
   /** Validation context. */
   private IAeHtValidationContext mValidationContext;
   /** the traverser used for validation of the Def */
   private IAeBaseXmlDefVisitor mTraverser;
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
      setValidationContext((IAeHtValidationContext) aContext);
      setProblemReporter(aErrorReporter);

      setTraverser(getValidationContext().createRuleTraverser(this));

      getResourceModel().accept(getTraverser());
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
   protected IAeHtValidationContext getValidationContext()
   {
      return mValidationContext;
   }

   /**
    * @param aValidationContext the validationContext to set
    */
   protected void setValidationContext(IAeHtValidationContext aValidationContext)
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
    * @return Returns the traverser.
    */
   public IAeBaseXmlDefVisitor getTraverser()
   {
      return mTraverser;
   }

   /**
    * @param aTraverser the traverser to set
    */
   public void setTraverser(IAeBaseXmlDefVisitor aTraverser)
   {
      mTraverser = aTraverser;
   }

}
