// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeToPartsDefRule46Validator.java,v 1.2 2008/02/15 17:40:56 EWittmann Exp $
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
import org.activebpel.rt.util.AeUtil;

/**
 * Element toParts required if notification interface differs from task interface.
 * 
 */
public class AeToPartsDefRule46Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationDef)
    */
   public void visit(AeEscalationDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * rule logic
    * @param aDef
    */
   protected void executeRule(AeEscalationDef aDef)
   {
      try
      {
         Message notificationMessage = null;
         Message taskMessage = getValidationContext().getInputMessage(aDef);
         
         if (aDef.getLocalNotification() != null)
         {
            notificationMessage = getValidationContext().getInputMessage(aDef.getLocalNotification());
         }
         else if (aDef.getNotification() != null)
         {
            notificationMessage = getValidationContext().getInputMessage(aDef.getNotification());
         }
         
         if (notificationMessage != null && taskMessage != null)
         {
            // if the escalation doesn't contain toParts elements and the task and notification
            // input messages are not of the same type then report a problem because the message
            // can't be implicitly passed between the task and notification
            
            if(aDef.getToParts() == null && !AeUtil.compareObjects(notificationMessage.getQName(), taskMessage.getQName()))
            {
               reportProblem(AeMessages.getString("AeToPartsDefRule46Validator.0"), aDef); //$NON-NLS-1$
            }
         }
      }
      catch (AeException ex)
      {
         reportException(ex, aDef);
      }
   }
}
