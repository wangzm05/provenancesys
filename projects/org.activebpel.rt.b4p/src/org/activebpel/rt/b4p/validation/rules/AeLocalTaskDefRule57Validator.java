// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeLocalTaskDefRule57Validator.java,v 1.2 2008/02/15 17:47:59 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.def.AeLocalTaskDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.ht.def.AeTaskDef;

/**
 * people assignment overrides merged with referenced tasks's people assignments 
 * must produce a strategy for business administrators
 */
public class AeLocalTaskDefRule57Validator extends AeAbstractB4PValidator
{
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeLocalTaskDef)
    */
   public void visit(AeLocalTaskDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * rule logic
    * @param aDef
    */
   protected void executeRule(AeLocalTaskDef aDef)
   {
      AeTaskDef task = getValidationContext().findTask(aDef, aDef.getReference());
      
      if (task != null)
      {
         AePeopleAssignmentsDef mergedPA = null;
            
         if (task.getPeopleAssignments() != null)
         {
            mergedPA = task.getPeopleAssignments().merge(aDef.getPeopleAssignments());
         }
         else
         {
            mergedPA = aDef.getPeopleAssignments();
         }
            
         if (mergedPA != null && mergedPA.getBusinessAdministrators() == null)
         {
            reportProblem(AeMessages.getString("AeLocalTaskDefRule57Validator.0"), aDef); //$NON-NLS-1$
         }
      }
   }
}
