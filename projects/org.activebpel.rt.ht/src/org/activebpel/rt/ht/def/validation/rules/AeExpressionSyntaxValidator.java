// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeExpressionSyntaxValidator.java,v 1.3 2008/03/15 22:09:04 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import org.activebpel.rt.AeException;
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
import org.activebpel.rt.ht.def.AeToPartDef;
import org.activebpel.rt.ht.def.AeUntilDef;

/**
 * Validate the syntax for HT expressions.
 */
public class AeExpressionSyntaxValidator extends AeAbstractHtExpressionValidator
{
   /**
    * @see org.activebpel.rt.ht.def.validation.rules.AeAbstractHtExpressionValidator#visit(org.activebpel.rt.ht.def.AeArgumentDef)
    */
   public void visit(AeArgumentDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.rules.AeAbstractHtExpressionValidator#visit(org.activebpel.rt.ht.def.AeConditionDef)
    */
   public void visit(AeConditionDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.rules.AeAbstractHtExpressionValidator#visit(org.activebpel.rt.ht.def.AeForDef)
    */
   public void visit(AeForDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.rules.AeAbstractHtExpressionValidator#visit(org.activebpel.rt.ht.def.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.rules.AeAbstractHtExpressionValidator#visit(org.activebpel.rt.ht.def.AePresentationParameterDef)
    */
   public void visit(AePresentationParameterDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.rules.AeAbstractHtExpressionValidator#visit(org.activebpel.rt.ht.def.AePriorityDef)
    */
   public void visit(AePriorityDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.rules.AeAbstractHtExpressionValidator#visit(org.activebpel.rt.ht.def.AeProcessDataExpressionDef)
    */
   public void visit(AeProcessDataExpressionDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.rules.AeAbstractHtExpressionValidator#visit(org.activebpel.rt.ht.def.AeSearchByDef)
    */
   public void visit(AeSearchByDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.rules.AeAbstractHtExpressionValidator#visit(org.activebpel.rt.ht.def.AeToPartDef)
    */
   public void visit(AeToPartDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.rules.AeAbstractHtExpressionValidator#visit(org.activebpel.rt.ht.def.AeUntilDef)
    */
   public void visit(AeUntilDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * Rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeAbstractExpressionDef aDef)
   {
      try
      {
         validateHtExpression(aDef, getParseResult(aDef));
         
         // TODO: (DV) eventually there should be validation to ensure function arguments are literal
      }
      catch(AeException ex)
      {
         String message = AeMessages.format("AeExpressionSyntaxValidator.0",new Object[] {ex.getMessage()}); //$NON-NLS-1$
         reportProblem(message, aDef);
      }
   }
}
