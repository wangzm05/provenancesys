// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeB4PLocalNotificationRule52Validator.java,v 1.3 2008/02/21 22:07:12 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef;
import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;

/**
 * people assignment overrides merged with referenced notification's people 
 * assignments must produce a strategy for recipients and business administrators
 */
public class AeB4PLocalNotificationRule52Validator extends AeAbstractB4PValidator
{
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
    */
   public void visit(AeB4PLocalNotificationDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * rule logic
    * @param aDef
    */
   protected void executeRule(AeB4PLocalNotificationDef aDef)
   {
      AeNotificationDef notification = getValidationContext().findNotification(aDef, aDef.getReference());
      
      if (notification != null)
      {
         AePeopleAssignmentsDef mergedPA = null;
         
         if (notification.getPeopleAssignments() != null)
         {
            mergedPA = notification.getPeopleAssignments().merge(aDef.getPeopleAssignments());
         }
         else
         {
            mergedPA = aDef.getPeopleAssignments();
         }
         
         if (mergedPA != null && mergedPA.getRecipients() == null)
         {
            reportProblem(AeMessages.getString("AeB4PLocalNotificationRule52Validator.0"), aDef); //$NON-NLS-1$
         }
      }
   }
}
