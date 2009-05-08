// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeOutcomeDefRule35Validator.java,v 1.2 2008/02/15 17:40:57 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import javax.wsdl.Message;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeOutcomeDef;

/**
 * part name exists within the output message
 */
public class AeOutcomeDefRule35Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeOutcomeDef)
    */
   public void visit(AeOutcomeDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeOutcomeDef aDef)
   {  
      try
      {
         Message outputMessage = getValidationContext().getOutputMessage(aDef);
         
         if (outputMessage != null && outputMessage.getPart(aDef.getPart()) == null)
         {
            reportProblem(AeMessages.getString("AeOutcomeDefRule35Validator.0"), aDef); //$NON-NLS-1$
         }
      }
      catch (AeException ex)
      {
         reportException(ex, aDef);
      }
   }
}
