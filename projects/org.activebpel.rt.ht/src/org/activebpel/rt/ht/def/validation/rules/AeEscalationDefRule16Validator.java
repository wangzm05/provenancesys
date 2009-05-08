// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeEscalationDefRule16Validator.java,v 1.4 2008/03/15 22:09:04 dvilaverde Exp $
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

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeAbstractDeadlineDef;
import org.activebpel.rt.ht.def.AeCompletionDeadlineDef;
import org.activebpel.rt.ht.def.AeEscalationDef;
import org.activebpel.rt.ht.def.AeStartDeadlineDef;

/** 
 * No Reassignment following any unconditioned reassignment
 */
public class AeEscalationDefRule16Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationDef)
    */
   public void visit(AeStartDeadlineDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeCompletionDeadlineDef)
    */
   public void visit(AeCompletionDeadlineDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeAbstractDeadlineDef aDef)
   {
      AeAbstractDeadlineDef deadline = aDef; 
      
      if (deadline.getEscalationCount() > 1)
      {
         boolean wasPreviousConditioned = true;
         
         // loop over the escalations looking for reassignments
         for (Iterator iter = deadline.getEscalationDefs(); iter.hasNext();)
         {
            AeEscalationDef escalation = (AeEscalationDef) iter.next();
            
            // if this is a reassignment then check to see if the reassignment is preceeded by an unconditioned reassignment
            if (escalation.getReassignment() != null) 
            {
               if (!wasPreviousConditioned)
               {
                  reportProblem(AeMessages.getString("AeEscalationDefRule16Validator.0"), aDef);//$NON-NLS-1$
                  break;
               }  
               // if escalation condition not null then set flag to true
               wasPreviousConditioned = escalation.getConditionDef() != null;
            }
         }
      }
   }
}
