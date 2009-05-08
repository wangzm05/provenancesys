//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/AeB4PHtDefDeflationVisitor.java,v 1.13 2008/03/26 14:52:40 EWittmann Exp $
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

import org.activebpel.rt.ht.def.AeAbstractDeadlineDef;
import org.activebpel.rt.ht.def.AeCompletionDeadlineDef;
import org.activebpel.rt.ht.def.AeConditionDef;
import org.activebpel.rt.ht.def.AeEscalationDef;
import org.activebpel.rt.ht.def.AeEscalationProcessDef;
import org.activebpel.rt.ht.def.AeForDef;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AeLocalNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationInterfaceDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.ht.def.AePotentialOwnersDef;
import org.activebpel.rt.ht.def.AePresentationElementsDef;
import org.activebpel.rt.ht.def.AePresentationParametersDef;
import org.activebpel.rt.ht.def.AePriorityDef;
import org.activebpel.rt.ht.def.AeProcessDataExpressionDef;
import org.activebpel.rt.ht.def.AeReassignmentDef;
import org.activebpel.rt.ht.def.AeRecipientsDef;
import org.activebpel.rt.ht.def.AeStartDeadlineDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTaskInterfaceDef;
import org.activebpel.rt.ht.def.AeToPartsDef;
import org.activebpel.rt.ht.def.AeUntilDef;
import org.activebpel.rt.ht.def.IAePriorityDefParent;
import org.activebpel.rt.util.AeUtil;

/**
 * Visitor used to deflate the HtDef if the def has empty containers, such as, {@link AePeopleAssignmentsDef} having an
 * {@link AePotentialOwnersDef} whereby the {@link AePotentialOwnersDef} does not have any owners defined.
 */
public class AeB4PHtDefDeflationVisitor extends AeAbstractTraversingB4PDefVisitor
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePeopleAssignmentsDef)
    */
   public void visit(AePeopleAssignmentsDef aDef)
   {
      if (aDef.getPotentialOwners() != null)
      {
         if (! aDef.getPotentialOwners().getFrom().isDefined())
            aDef.setPotentialOwners(null);
      }

      if (aDef.getExcludedOwners() != null)
      {
         if (! aDef.getExcludedOwners().getFrom().isDefined())
            aDef.setExcludedOwners(null);
      }
      
      if (aDef.getTaskInitiator() != null)
      {
         if (! aDef.getTaskInitiator().getFrom().isDefined())
            aDef.setTaskInitiator(null);
      }
      
      if (aDef.getTaskStakeholders() != null)
      {
         if (! aDef.getTaskStakeholders().getFrom().isDefined())
            aDef.setTaskStakeholders(null);
      }
      
      if (aDef.getBusinessAdministrators() != null)
      {
         if (! aDef.getBusinessAdministrators().getFrom().isDefined())
            aDef.setBusinessAdministrators(null);
      }
      
      if (aDef.getRecipients() != null)
      {
         if (! aDef.getRecipients().getFrom().isDefined())
            aDef.setRecipients(null);
      }
      
      super.visit(aDef);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationElementsDef)
    */
   public void visit(AePresentationElementsDef aDef)
   {
      if ( aDef.getPresentationParameters() != null )
      {
         AePresentationParametersDef paramsDef = aDef.getPresentationParameters();
         if ( paramsDef != null && !paramsDef.getPresentationParameterDefs().hasNext() )
         {
            aDef.setPresentationParameters(null);
         }
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePriorityDef)
    */
   public void visit(AePriorityDef aDef)
   {
      if ( AeUtil.isNullOrEmpty(aDef.getExpression()) )
      {
         ((IAePriorityDefParent)aDef.getParentXmlDef()).setPriority(null);      
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationInterfaceDef)
    */
   public void visit(AeNotificationInterfaceDef aDef)
   {
      if ( AeUtil.isNullOrEmpty(aDef.getPortType()) && AeUtil.isNullOrEmpty(aDef.getOperation())  )
      {
         ((AeNotificationDef)aDef.getParentXmlDef()).setInterface(null);
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskInterfaceDef)
    */
   public void visit(AeTaskInterfaceDef aDef)
   {
      if ( AeUtil.isNullOrEmpty(aDef.getPortType()) && AeUtil.isNullOrEmpty(aDef.getOperation()) && 
           AeUtil.isNullOrEmpty(aDef.getResponsePortType()) && AeUtil.isNullOrEmpty(aDef.getResponseOperation()) )
      {
         ((AeTaskDef)aDef.getParentXmlDef()).setInterface(null);
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartsDef)
    */
   public void visit(AeToPartsDef aDef)
   {
      if ( aDef.getToPartsCount() == 0 )
      {
         AeEscalationDef escDef = (AeEscalationDef)aDef.getParentXmlDef();
         escDef.setToParts(null);         
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeConditionDef)
    */
   public void visit(AeConditionDef aDef)
   {
      if ( AeUtil.isNullOrEmpty(aDef.getExpression()) )
      {
         if ( aDef.getParentXmlDef() instanceof AeEscalationDef )
         {
            AeEscalationDef escDef = (AeEscalationDef)aDef.getParentXmlDef();
            escDef.setConditionDef(null);
         }
         else if ( aDef.getParentXmlDef() instanceof AeEscalationProcessDef )
         {
            AeEscalationProcessDef procDef = (AeEscalationProcessDef)aDef.getParentXmlDef();
            procDef.setConditionDef(null);
         }
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeProcessDataExpressionDef)
    */
   public void visit(AeProcessDataExpressionDef aDef)
   {
      if ( AeUtil.isNullOrEmpty(aDef.getExpression()) )
      {
         AeEscalationProcessDef escDef = (AeEscalationProcessDef)aDef.getParentXmlDef();
         escDef.setExpressionDef(null);
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      if (aDef.getSearchBy() != null && ! aDef.getSearchBy().isDefined())
         aDef.setSearchBy(null);

      if (aDef.getDelegation() != null && ! aDef.getDelegation().isDefined())
         aDef.setDelegation(null);
      
      if (aDef.getOutcome() != null && ! aDef.getOutcome().isDefined())
         aDef.setOutcome(null);
      
      if (aDef.getDeadlines() != null && ! aDef.getDeadlines().isDefined())
         aDef.setDeadlines(null);
      
      if (aDef.getRenderings() != null && aDef.getRenderings().getRenderingsCount() == 0 )
         aDef.setRenderings(null);
      
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      if (aDef.getRenderings() != null && aDef.getRenderings().getRenderingsCount() == 0 )
         aDef.setRenderings(null);
      
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLocalNotificationDef)
    */
   public void visit(AeLocalNotificationDef aDef)
   {
      AePeopleAssignmentsDef peopleDef = aDef.getPeopleAssignments();
      if ( peopleDef != null )
      {
         AeRecipientsDef recDef = peopleDef.getRecipients();
         if  ( recDef == null || recDef.getFrom() == null || !recDef.getFrom().isDefined() )
         {
            aDef.setPeopleAssignments(null);
         }
      }
      if ( (new QName("")).equals(aDef.getReference()) ) //$NON-NLS-1$
      {
         aDef.setReference(null);
      }
      
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeReassignmentDef)
    */
   public void visit(AeReassignmentDef aDef)
   {
      AePotentialOwnersDef potDef = aDef.getPotentialOwners();
      if ( potDef != null )
      {
         AeFromDef fromDef = potDef.getFrom();
         if ( !fromDef.isDefined() )
         {
            aDef.setPotentialOwners(null);
         }
      }
      
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeStartDeadlineDef)
    */
   public void visit(AeStartDeadlineDef aDef)
   {
      deflateDeadlineCondition(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeCompletionDeadlineDef)
    */
   public void visit(AeCompletionDeadlineDef aDef)
   {
      deflateDeadlineCondition(aDef);
      super.visit(aDef);
   }
   
   /**
    * Convenience method to deflate a deadline def's conditions (until and for).
    * @param aDef
    */
   private void deflateDeadlineCondition(AeAbstractDeadlineDef aDef)
   {
      AeForDef forDef = aDef.getFor();
      if ( forDef != null && AeUtil.isNullOrEmpty(forDef.getExpression()) )
         aDef.setFor(null);
      
      AeUntilDef untilDef = aDef.getUntil();
      if ( untilDef != null && AeUtil.isNullOrEmpty(untilDef.getExpression()) )
         aDef.setUntil(null);
   }

}
