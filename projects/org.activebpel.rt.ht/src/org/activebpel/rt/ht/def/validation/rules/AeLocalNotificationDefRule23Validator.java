// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeLocalNotificationDefRule23Validator.java,v 1.3 2008/02/15 17:40:56 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeLocalNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;

/**
 * The peopleAssignments MUST include a people assignment for recipients   
 * and MAY include a people assignment for business administrators.
 */
public class AeLocalNotificationDefRule23Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLocalNotificationDef)
    */
   public void visit(AeLocalNotificationDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * rule logic 
    * 
    * @param aDef
    */
   protected void executeRule(AeLocalNotificationDef aDef)
   {
      AeNotificationDef refNotif = getValidationContext().findNotification(aDef, aDef.getReference());
      
      if (refNotif != null)
      {
         AePeopleAssignmentsDef mergedPA = null;
         
         if (refNotif.getPeopleAssignments() != null)
         {
            mergedPA = refNotif.getPeopleAssignments().merge(aDef.getPeopleAssignments());
         }
         else
         {
            mergedPA = aDef.getPeopleAssignments();
         }
         
         if (mergedPA != null && mergedPA.getRecipients() == null)
         {
            reportProblem(AeMessages.getString("AeLocalNotificationDefRule23Validator.0"), aDef); //$NON-NLS-1$
         }
      }
   }
}
