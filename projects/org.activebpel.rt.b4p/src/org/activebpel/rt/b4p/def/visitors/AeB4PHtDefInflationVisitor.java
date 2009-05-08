//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/AeB4PHtDefInflationVisitor.java,v 1.6 2008/03/11 14:40:34 JPerrotto Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.visitors;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef;
import org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef;
import org.activebpel.rt.b4p.def.AeLocalTaskDef;
import org.activebpel.rt.b4p.def.AeProcessInitiatorDef;
import org.activebpel.rt.b4p.def.AeProcessStakeholdersDef;
import org.activebpel.rt.ht.def.AeAbstractDeadlineDef;
import org.activebpel.rt.ht.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.ht.def.AeCompletionDeadlineDef;
import org.activebpel.rt.ht.def.AeConditionDef;
import org.activebpel.rt.ht.def.AeDeadlinesDef;
import org.activebpel.rt.ht.def.AeDelegationDef;
import org.activebpel.rt.ht.def.AeEscalationDef;
import org.activebpel.rt.ht.def.AeEscalationProcessDef;
import org.activebpel.rt.ht.def.AeExcludedOwnersDef;
import org.activebpel.rt.ht.def.AeForDef;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AeLocalNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationInterfaceDef;
import org.activebpel.rt.ht.def.AeOutcomeDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.ht.def.AePotentialOwnersDef;
import org.activebpel.rt.ht.def.AePresentationElementsDef;
import org.activebpel.rt.ht.def.AePresentationParametersDef;
import org.activebpel.rt.ht.def.AePriorityDef;
import org.activebpel.rt.ht.def.AeProcessDataExpressionDef;
import org.activebpel.rt.ht.def.AeReassignmentDef;
import org.activebpel.rt.ht.def.AeRecipientsDef;
import org.activebpel.rt.ht.def.AeRenderingsDef;
import org.activebpel.rt.ht.def.AeSearchByDef;
import org.activebpel.rt.ht.def.AeStartDeadlineDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTaskInitiatorDef;
import org.activebpel.rt.ht.def.AeTaskInterfaceDef;
import org.activebpel.rt.ht.def.AeTaskStakeHoldersDef;
import org.activebpel.rt.ht.def.AeToPartsDef;
import org.activebpel.rt.ht.def.AeUntilDef;
import org.activebpel.rt.ht.def.IAeFromDefParent;


/**
 * Visitor used to expand the HtDef to contain placeholders for editing the people assignments
 */
public class AeB4PHtDefInflationVisitor extends AeAbstractTraversingB4PDefVisitor
{
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
    */
   public void visit(AeB4PLocalNotificationDef aDef)
   {
      if (aDef.getPeopleAssignments() == null)
         aDef.setPeopleAssignments(new AePeopleAssignmentsDef());
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeLocalTaskDef)
    */
   public void visit(AeLocalTaskDef aDef)
   {
      if (aDef.getPeopleAssignments() == null)
         aDef.setPeopleAssignments(new AePeopleAssignmentsDef());
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      if (aDef.getPeopleAssignments() == null)
         aDef.setPeopleAssignments(new AePeopleAssignmentsDef());
      
      if (aDef.getPresentationElements() == null )
         aDef.setPresentationElements(new AePresentationElementsDef());
      
      if (aDef.getPriority() == null )
         aDef.setPriority(new AePriorityDef());
      
      if ( aDef.getTaskInterfaceDef() == null )
         aDef.setInterface(new AeTaskInterfaceDef());
      
      if (aDef.getSearchBy() == null)
         aDef.setSearchBy(new AeSearchByDef());

      if (aDef.getDelegation() == null)
         aDef.setDelegation(new AeDelegationDef());
      
      if (aDef.getOutcome() == null)
         aDef.setOutcome(new AeOutcomeDef());
      
      if (aDef.getDeadlines() == null)
         aDef.setDeadlines(new AeDeadlinesDef());
      
      if (aDef.getRenderings() == null)
         aDef.setRenderings(new AeRenderingsDef());
      
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      if (aDef.getPeopleAssignments() == null)
      {
         aDef.setPeopleAssignments(new AePeopleAssignmentsDef());
      }
      
      if (aDef.getPresentationElements() == null)
      {
         aDef.setPresentationElements(new AePresentationElementsDef());
      }
      
      if (aDef.getPriority() == null)
      {
         aDef.setPriority(new AePriorityDef());
      }
      
      if (aDef.getNotificationInterfaceDef() == null)
      {
         aDef.setInterface(new AeNotificationInterfaceDef());
      }

      if (aDef.getRenderings() == null)
      {
         aDef.setRenderings(new AeRenderingsDef());
      }
      
      super.visit(aDef);
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef)
    */
   public void visit(AeB4PPeopleAssignmentsDef aDef)
   {
      if (aDef.getProcessInitiator() == null)
         aDef.setProcessInitiator(new AeProcessInitiatorDef());
      if (aDef.getProcessStakeholders() == null)
         aDef.setProcessStakeholders(new AeProcessStakeholdersDef());
      if (aDef.getBusinessAdministrators() == null)
         aDef.setBusinessAdministrators(new org.activebpel.rt.b4p.def.AeBusinessAdministratorsDef());
      super.visit(aDef);
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeBusinessAdministratorsDef)
    */
   public void visit(org.activebpel.rt.b4p.def.AeBusinessAdministratorsDef aDef)
   {
      visitFromParentDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeProcessInitiatorDef)
    */
   public void visit(AeProcessInitiatorDef aDef)
   {
      visitFromParentDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeProcessStakeholdersDef)
    */
   public void visit(AeProcessStakeholdersDef aDef)
   {
      visitFromParentDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePeopleAssignmentsDef)
    */
   public void visit(AePeopleAssignmentsDef aDef)
   {
      // task specific roles
      if (aDef.getPotentialOwners() == null)
      {
         aDef.setPotentialOwners(new AePotentialOwnersDef());
      }
      
      if (aDef.getExcludedOwners() == null)
      {
         aDef.setExcludedOwners(new AeExcludedOwnersDef());
      }

      if (aDef.getTaskInitiator() == null)
      {
         aDef.setTaskInitiator(new AeTaskInitiatorDef());
      }

      if (aDef.getTaskStakeholders() == null)
      {
         aDef.setTaskStakeholders(new AeTaskStakeHoldersDef());
      }
      
      // common to both tasks and notifications
      if (aDef.getBusinessAdministrators() == null)
      {
         aDef.setBusinessAdministrators(new AeBusinessAdministratorsDef());
      }
      
      // notification specific roles
      if (aDef.getRecipients() == null)
      {
         aDef.setRecipients(new AeRecipientsDef());
      }
      
      super.visit(aDef);
   }
   
   /**
    * Sets the from def on the parent if it is null
    * @param aFromParentDef
    */
   protected void visitFromParentDef(IAeFromDefParent aFromParentDef)
   {
      if (aFromParentDef.getFrom() == null)
         aFromParentDef.setFrom(new AeFromDef());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      visitFromParentDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeExcludedOwnersDef)
    */
   public void visit(AeExcludedOwnersDef aDef)
   {
      visitFromParentDef(aDef);
   }
   
   /**
    * Overrides method to 
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePotentialOwnersDef)
    */
   public void visit(AePotentialOwnersDef aDef)
   {
      visitFromParentDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeRecipientsDef)
    */
   public void visit(AeRecipientsDef aDef)
   {
      visitFromParentDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskInitiatorDef)
    */
   public void visit(AeTaskInitiatorDef aDef)
   {
      visitFromParentDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskStakeHoldersDef)
    */
   public void visit(AeTaskStakeHoldersDef aDef)
   {
      visitFromParentDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationElementsDef)
    */
   public void visit(AePresentationElementsDef aDef)
   {
      if ( aDef.getPresentationParameters() == null )
      {
         aDef.setPresentationParameters(new AePresentationParametersDef());
      }
      
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationDef)
    */
   public void visit(AeEscalationDef aDef)
   {
      if ( aDef.getConditionDef() == null )
      {
         aDef.setConditionDef(new AeConditionDef());
      }
      
      if ( aDef.getToParts() == null )
      {
         aDef.setToParts(new AeToPartsDef());
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLocalNotificationDef)
    */
   public void visit(AeLocalNotificationDef aDef)
   {
      if ( aDef.getPeopleAssignments() == null )
      {
         AeFromDef fromDef = new AeFromDef();
         AeRecipientsDef recipientsDef = new AeRecipientsDef();
         AePeopleAssignmentsDef peopleAssignDef = new AePeopleAssignmentsDef();
         
         recipientsDef.setFrom(fromDef);
         peopleAssignDef.setRecipients(recipientsDef);
         
         aDef.setPeopleAssignments(peopleAssignDef);
      }
      if ( aDef.getReference() == null )
      {
         aDef.setReference(new QName("")); //$NON-NLS-1$
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeReassignmentDef)
    */
   public void visit(AeReassignmentDef aDef)
   {
      if ( aDef.getPotentialOwners() == null )
      {
         AeFromDef fromDef = new AeFromDef();
         AePotentialOwnersDef potDef = new AePotentialOwnersDef();

         potDef.setFrom(fromDef);
         
         aDef.setPotentialOwners(potDef);
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeCompletionDeadlineDef)
    */
   public void visit(AeCompletionDeadlineDef aDef)
   {
      inflatDeadlineCondition(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeStartDeadlineDef)
    */
   public void visit(AeStartDeadlineDef aDef)
   {
      inflatDeadlineCondition(aDef);
      super.visit(aDef);
   }

   /**
    * Convenience method to inflate a deadline def's conditions (until and for).
    * @param aDef
    */
   private void inflatDeadlineCondition(AeAbstractDeadlineDef aDef)
   {
      if ( aDef.getUntil() == null )
         aDef.setUntil(new AeUntilDef());
      
      if ( aDef.getFor() == null )
         aDef.setFor(new AeForDef());
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationProcessDef)
    */
   public void visit(AeEscalationProcessDef aDef)
   {
      if ( aDef.getService() == null )
      {
         aDef.setService(""); //$NON-NLS-1$
      }
      
      if ( aDef.getConditionDef() == null )
      {
         aDef.setConditionDef(new AeConditionDef());
      }
      
      if ( aDef.getExpressionDef() == null )
      {
         aDef.setExpressionDef(new AeProcessDataExpressionDef());
      }
      super.visit(aDef);
   }
   
}