// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/visitors/AeHtDefTraverser.java,v 1.13 2008/02/17 21:51:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def.visitors;

import org.activebpel.rt.ht.def.AeAbstractDeadlineDef;
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
import org.activebpel.rt.ht.def.AeParameterDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.ht.def.AePotentialOwnersDef;
import org.activebpel.rt.ht.def.AePresentationElementsDef;
import org.activebpel.rt.ht.def.AePresentationParameterDef;
import org.activebpel.rt.ht.def.AePresentationParametersDef;
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
import org.activebpel.rt.xml.def.visitors.AeBaseXmlDefTraverser;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * The external iterator for the IAeHtDefVisitor interface. Provides the logic for traversing the Human Task
 * definition objects.
 */
public class AeHtDefTraverser extends AeBaseXmlDefTraverser implements IAeHtDefTraverser
{
   /**
    * Default c'tor.
    */
   public AeHtDefTraverser()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeHumanInteractionsDef,
    *      org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeHumanInteractionsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getExtensionsDef(), aVisitor);
      callAccept(aDef.getImportDefs(), aVisitor);
      callAccept(aDef.getLogicalPeopleGroups(), aVisitor);
      callAccept(aDef.getTasks(), aVisitor);
      callAccept(aDef.getNotifications(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeExtensionsDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeExtensionsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getExtensionDefs(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeExtensionDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeExtensionDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeLogicalPeopleGroupsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getLogicalPeopleGroupDefs(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeLogicalPeopleGroupDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getParameterDefs().iterator(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeFromDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeFromDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getArgumentDefs().iterator(), aVisitor);
      callAccept(aDef.getLiteral(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeImportDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeImportDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeLiteralDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeLiteralDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeToPartsDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeToPartsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getToPartDefs(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeToPartDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeToPartDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AePresentationParameterDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AePresentationParameterDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AePresentationParametersDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AePresentationParametersDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getPresentationParameterDefs(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeArgumentDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeArgumentDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeBusinessAdministratorsDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeBusinessAdministratorsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getFrom(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeExcludedOwnersDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeExcludedOwnersDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getFrom(), aVisitor);
   }

   public void traverse(AePotentialOwnersDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getFrom(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeRecipientsDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeRecipientsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getFrom(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeTaskInitiatorDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeTaskInitiatorDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getFrom(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeTaskStakeHoldersDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeTaskStakeHoldersDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getFrom(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeStartDeadlineDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeStartDeadlineDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDeadlineDef(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeCompletionDeadlineDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeCompletionDeadlineDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDeadlineDef(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeUntilDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeUntilDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeDeadlinesDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeDeadlinesDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getStartDeadlineDefs(), aVisitor);
      callAccept(aDef.getCompletionDeadlineDefs(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeDelegationDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeDelegationDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getFrom(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeDescriptionDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeDescriptionDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AePresentationElementsDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AePresentationElementsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getNameDefs(), aVisitor);
      callAccept(aDef.getPresentationParameters(), aVisitor);
      callAccept(aDef.getSubjectDefs(), aVisitor);
      callAccept(aDef.getDescriptionDefs(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeLocalNotificationDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeLocalNotificationDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getPriority(), aVisitor);
      callAccept(aDef.getPeopleAssignments(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeNotificationsDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeNotificationsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getNotificationDefs(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeNotificationDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeNotificationDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getNotificationInterfaceDef(), aVisitor);
      callAccept(aDef.getPriority(), aVisitor);
      callAccept(aDef.getPeopleAssignments(), aVisitor);
      callAccept(aDef.getPresentationElements(), aVisitor);
      callAccept(aDef.getRenderings(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeNotificationInterfaceDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeNotificationInterfaceDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeEscalationDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeEscalationDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getConditionDef(), aVisitor);
      callAccept(aDef.getToParts(), aVisitor);
      callAccept(aDef.getNotification(), aVisitor);
      callAccept(aDef.getLocalNotification(), aVisitor);
      callAccept(aDef.getReassignment(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeEscalationProcessDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeEscalationProcessDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getConditionDef(), aVisitor);
      callAccept(aDef.getExpressionDef(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeProcessDataExpressionDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeProcessDataExpressionDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeRenderingDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeRenderingDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeRenderingsDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeRenderingsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getRenderingDefs(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeTaskDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeTaskDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getTaskInterfaceDef(), aVisitor);
      callAccept(aDef.getPriority(), aVisitor);
      callAccept(aDef.getPeopleAssignments(), aVisitor);
      callAccept(aDef.getDelegation(), aVisitor);
      callAccept(aDef.getPresentationElements(), aVisitor);
      callAccept(aDef.getOutcome(), aVisitor);
      callAccept(aDef.getSearchBy(), aVisitor);
      callAccept(aDef.getRenderings(), aVisitor);
      callAccept(aDef.getDeadlines(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeTasksDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeTasksDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getTaskDefs(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeTaskInterfaceDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeTaskInterfaceDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeReassignmentDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeReassignmentDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getPotentialOwners(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeParameterDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeParameterDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AePeopleAssignmentsDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AePeopleAssignmentsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getPotentialOwners(), aVisitor);
      callAccept(aDef.getExcludedOwners(), aVisitor);
      callAccept(aDef.getTaskInitiator(), aVisitor);
      callAccept(aDef.getTaskStakeholders(), aVisitor);
      callAccept(aDef.getBusinessAdministrators(), aVisitor);
      callAccept(aDef.getRecipients(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeOutcomeDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeOutcomeDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeOrganizationalEntityDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeOrganizationalEntityDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getGroups(), aVisitor);
      callAccept(aDef.getUsers(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeNameDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeNameDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeSubjectDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeSubjectDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeSearchByDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeSearchByDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeGroupDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeGroupDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeGroupsDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeGroupsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getGroupDefs(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeUserDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeUserDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeUsersDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeUsersDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getUserDefs(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeForDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeForDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeConditionDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeConditionDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AePriorityDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AePriorityDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Called to traverse a Deadline def.
    * @param aDef
    * @param aVisitor
    */
   protected void traverseDeadlineDef(AeAbstractDeadlineDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getEscalationProcessDefs(), aVisitor);
      callAccept(aDef.getFor(), aVisitor);
      callAccept(aDef.getUntil(), aVisitor);
      callAccept(aDef.getEscalationDefs(), aVisitor);
   }
}
