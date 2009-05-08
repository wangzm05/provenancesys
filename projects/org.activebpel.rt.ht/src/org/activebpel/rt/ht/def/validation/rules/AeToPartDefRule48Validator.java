// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeToPartDefRule48Validator.java,v 1.2 2008/02/15 17:40:57 EWittmann Exp $
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
import org.activebpel.rt.ht.def.AeEscalationDef;
import org.activebpel.rt.ht.def.AeToPartDef;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * part name matches message part
 */
public class AeToPartDefRule48Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartDef)
    */
   public void visit(AeToPartDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * rule logic
    * @param aDef
    */
   protected void executeRule(AeToPartDef aDef)
   {
      AeBaseXmlDef contextDef = null;
      // toPart is a special case where the notification interface is not within any of its parents,
      // so to handle this will we find the AeEscalationDef and find its local notification or notification
      // and ignore this rule for reassignments
      
      AeEscalationDef parent = (AeEscalationDef) aDef.getParentDef().getParentDef();

      if (parent.getLocalNotification() != null)
      {
         contextDef = parent.getLocalNotification();
      }
      else if (parent.getNotification() != null)
      {
         contextDef = parent.getNotification();
      } 
      
      if (contextDef != null)
      {
         try
         {
            Message inputMessage = getValidationContext().getInputMessage(contextDef);
            
            if(inputMessage != null && inputMessage.getPart(aDef.getName()) == null)
            {
               reportProblem(AeMessages.getString("AeToPartDefRule48Validator.0"), aDef); //$NON-NLS-1$
            }
         }
         catch (AeException ex)
         {
            reportException(ex, aDef);
         }
      }
   }
   
}
