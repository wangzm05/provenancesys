// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeAbstractGenericHumanRoleDefRule6Validator.java,v 1.3 2008/02/15 17:40:56 EWittmann Exp $
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
import org.activebpel.rt.ht.def.AeAbstractGenericHumanRoleDef;
import org.activebpel.rt.ht.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.ht.def.AeExcludedOwnersDef;
import org.activebpel.rt.ht.def.AePotentialOwnersDef;
import org.activebpel.rt.ht.def.AeRecipientsDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTaskInitiatorDef;
import org.activebpel.rt.ht.def.AeTaskStakeHoldersDef;

/**
 * Must have a fromDef
 */
public class AeAbstractGenericHumanRoleDefRule6Validator extends AeAbstractHtValidator
{

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      executeRule(aDef, AeMessages.getString("AeAbstractGenericHumanRoleDefRule6Validator.0")); //$NON-NLS-1$
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePotentialOwnersDef)
    */
   public void visit(AePotentialOwnersDef aDef)
   {
      //Tasks are allowed to have an empty potential owners element if nomination is being used.
      if ( !(aDef.getParentDef().getParentDef() instanceof AeTaskDef) )
      {
         executeRule(aDef, AeMessages.getString("AeAbstractGenericHumanRoleDefRule6Validator.1")); //$NON-NLS-1$
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeExcludedOwnersDef)
    */
   public void visit(AeExcludedOwnersDef aDef)
   {
      executeRule(aDef, AeMessages.getString("AeAbstractGenericHumanRoleDefRule6Validator.2")); //$NON-NLS-1$
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeRecipientsDef)
    */
   public void visit(AeRecipientsDef aDef)
   {
      executeRule(aDef, AeMessages.getString("AeAbstractGenericHumanRoleDefRule6Validator.3")); //$NON-NLS-1$
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskInitiatorDef)
    */
   public void visit(AeTaskInitiatorDef aDef)
   {
      executeRule(aDef, AeMessages.getString("AeAbstractGenericHumanRoleDefRule6Validator.4")); //$NON-NLS-1$
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskStakeHoldersDef)
    */
   public void visit(AeTaskStakeHoldersDef aDef)
   {
      executeRule(aDef, AeMessages.getString("AeAbstractGenericHumanRoleDefRule6Validator.5")); //$NON-NLS-1$
      super.visit(aDef);
   }
   
   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeAbstractGenericHumanRoleDef aDef, String aRoleName)
   {
      if (aDef.getFrom() == null)
      {
         String message = AeMessages.format("AeAbstractGenericHumanRoleDefRule6Validator.6", new Object[] {aRoleName}); //$NON-NLS-1$
         reportProblem(message, aDef);
      }
   }

}
