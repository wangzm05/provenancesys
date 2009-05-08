// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/visitors/AeAbstractHtDefVisitor.java,v 1.5 2008/01/11 01:49:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.visitors;

import org.activebpel.rt.ht.def.AeArgumentDef;
import org.activebpel.rt.ht.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.ht.def.AeCompletionDeadlineDef;
import org.activebpel.rt.ht.def.AeConditionDef;
import org.activebpel.rt.ht.def.AeDeadlinesDef;
import org.activebpel.rt.ht.def.AeDelegationDef;
import org.activebpel.rt.ht.def.AeDescriptionDef;
import org.activebpel.rt.ht.def.AeEscalationDef;
import org.activebpel.rt.ht.def.AeExcludedOwnersDef;
import org.activebpel.rt.ht.def.AeExtensionDef;
import org.activebpel.rt.ht.def.AeExtensionsDef;
import org.activebpel.rt.ht.def.AeForDef;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AeGroupDef;
import org.activebpel.rt.ht.def.AeGroupsDef;
import org.activebpel.rt.ht.def.AeHtBaseDef;
import org.activebpel.rt.ht.def.AeHumanInteractionsDef;
import org.activebpel.rt.ht.def.AeImportDef;
import org.activebpel.rt.ht.def.AeEscalationProcessDef;
import org.activebpel.rt.ht.def.AeProcessDataExpressionDef;
import org.activebpel.rt.ht.def.AeLiteralDef;
import org.activebpel.rt.ht.def.AeLocalNotificationDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef;
import org.activebpel.rt.ht.def.AeNameDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationInterfaceDef;
import org.activebpel.rt.ht.def.AeNotificationsDef;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.AeOutcomeDef;
import org.activebpel.rt.ht.def.AePresentationParameterDef;
import org.activebpel.rt.ht.def.AePresentationParametersDef;
import org.activebpel.rt.ht.def.AeParameterDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.ht.def.AePotentialOwnersDef;
import org.activebpel.rt.ht.def.AePresentationElementsDef;
import org.activebpel.rt.ht.def.AePriorityDef;
import org.activebpel.rt.ht.def.AeReassignmentDef;
import org.activebpel.rt.ht.def.AeRecipientsDef;
import org.activebpel.rt.ht.def.AeRenderingDef;
import org.activebpel.rt.ht.def.AeRenderingsDef;
import org.activebpel.rt.ht.def.AeSearchByDef;
import org.activebpel.rt.ht.def.AeStartDeadlineDef;
import org.activebpel.rt.ht.def.AeSubjectDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTaskInitiatorDef;
import org.activebpel.rt.ht.def.AeTaskInterfaceDef;
import org.activebpel.rt.ht.def.AeTaskStakeHoldersDef;
import org.activebpel.rt.ht.def.AeTasksDef;
import org.activebpel.rt.ht.def.AeToPartDef;
import org.activebpel.rt.ht.def.AeToPartsDef;
import org.activebpel.rt.ht.def.AeUntilDef;
import org.activebpel.rt.ht.def.AeUserDef;
import org.activebpel.rt.ht.def.AeUsersDef;
import org.activebpel.rt.xml.def.visitors.AeBaseXmlDefVisitor;

/**
 * Abstract implementation of an HT def visitor.  Classes that wish to
 * visit but not traverse an HT def should extend this class.
 */
public abstract class AeAbstractHtDefVisitor extends AeBaseXmlDefVisitor implements IAeHtDefVisitor
{
   /**
    * Default c'tor.
    */
   public AeAbstractHtDefVisitor()
   {
   }

   /**
    * Called by all ht visit methods.
    *
    * @param aHtBaseDef
    */
   protected void visitHtBaseDef(AeHtBaseDef aHtBaseDef)
   {
      // No-op - useful when overridden by subclasses.
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeHumanInteractionsDef)
    */
   public void visit(AeHumanInteractionsDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeArgumentDef)
    */
   public void visit(AeArgumentDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeCompletionDeadlineDef)
    */
   public void visit(AeCompletionDeadlineDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeUntilDef)
    */
   public void visit(AeUntilDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeForDef)
    */
   public void visit(AeForDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDeadlinesDef)
    */
   public void visit(AeDeadlinesDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDelegationDef)
    */
   public void visit(AeDelegationDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDescriptionDef)
    */
   public void visit(AeDescriptionDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef)
    */
   public void visit(AeLogicalPeopleGroupsDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef)
    */
   public void visit(AeLogicalPeopleGroupDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNameDef)
    */
   public void visit(AeNameDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeImportDef)
    */
   public void visit(AeImportDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeExtensionsDef)
    */
   public void visit(AeExtensionsDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeExtensionDef)
    */
   public void visit(AeExtensionDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLiteralDef)
    */
   public void visit(AeLiteralDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartsDef)
    */
   public void visit(AeToPartsDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartDef)
    */
   public void visit(AeToPartDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeParameterDef)
    */
   public void visit(AeParameterDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationElementsDef)
    */
   public void visit(AePresentationElementsDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationParameterDef)
    */
   public void visit(AePresentationParameterDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationParametersDef)
    */
   public void visit(AePresentationParametersDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLocalNotificationDef)
    */
   public void visit(AeLocalNotificationDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationsDef)
    */
   public void visit(AeNotificationsDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationInterfaceDef)
    */
   public void visit(AeNotificationInterfaceDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeOrganizationalEntityDef)
    */
   public void visit(AeOrganizationalEntityDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeOutcomeDef)
    */
   public void visit(AeOutcomeDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeReassignmentDef)
    */
   public void visit(AeReassignmentDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeRecipientsDef)
    */
   public void visit(AeRecipientsDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationDef)
    */
   public void visit(AeEscalationDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationProcessDef)
    */
   public void visit(AeEscalationProcessDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeProcessDataExpressionDef)
    */
   public void visit(AeProcessDataExpressionDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeRenderingDef)
    */
   public void visit(AeRenderingDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeRenderingsDef)
    */
   public void visit(AeRenderingsDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeSubjectDef)
    */
   public void visit(AeSubjectDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeSearchByDef)
    */
   public void visit(AeSearchByDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeStartDeadlineDef)
    */
   public void visit(AeStartDeadlineDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTasksDef)
    */
   public void visit(AeTasksDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskInterfaceDef)
    */
   public void visit(AeTaskInterfaceDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeConditionDef)
    */
   public void visit(AeConditionDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeExcludedOwnersDef)
    */
   public void visit(AeExcludedOwnersDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePotentialOwnersDef)
    */
   public void visit(AePotentialOwnersDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePeopleAssignmentsDef)
    */
   public void visit(AePeopleAssignmentsDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePriorityDef)
    */
   public void visit(AePriorityDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskInitiatorDef)
    */
   public void visit(AeTaskInitiatorDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskStakeHoldersDef)
    */
   public void visit(AeTaskStakeHoldersDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeGroupDef)
    */
   public void visit(AeGroupDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeGroupsDef)
    */
   public void visit(AeGroupsDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeUserDef)
    */
   public void visit(AeUserDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeUsersDef)
    */
   public void visit(AeUsersDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitHtBaseDef(aDef);
   }
}
