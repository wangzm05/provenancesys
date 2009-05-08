// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/visitors/IAeHtDefTraverser.java,v 1.5 2008/01/11 01:49:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
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
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefTraverser;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * Traversal interface for use in conjunction with HT definition object visitation. Each traverse method below
 * accepts a definition object and a visitor object. The method's responsibility is to decide how to traverse
 * the given definition object so each of its child objects (if any) will get visited.
 */
public interface IAeHtDefTraverser extends IAeBaseXmlDefTraverser
{
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeHumanInteractionsDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeLogicalPeopleGroupsDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeLogicalPeopleGroupDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeForDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeFromDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeOutcomeDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeImportDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeExtensionsDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeExcludedOwnersDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeExtensionDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeLiteralDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeToPartsDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeToPartDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeParameterDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AePeopleAssignmentsDef aDef, IAeBaseXmlDefVisitor aVisitor);

   
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AePresentationParameterDef aDef, IAeBaseXmlDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AePresentationParametersDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeArgumentDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeBusinessAdministratorsDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeStartDeadlineDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeCompletionDeadlineDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeConditionDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeDeadlinesDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeDelegationDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeDescriptionDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AePotentialOwnersDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AePresentationElementsDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AePriorityDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeNameDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeLocalNotificationDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeNotificationsDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeNotificationDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeNotificationInterfaceDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeOrganizationalEntityDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeSubjectDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeSearchByDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeGroupDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeGroupsDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeEscalationDef aDef, IAeBaseXmlDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeEscalationProcessDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeProcessDataExpressionDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeRecipientsDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeRenderingDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeRenderingsDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeTaskDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeTasksDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeTaskInitiatorDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeTaskInterfaceDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeTaskStakeHoldersDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeReassignmentDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeUserDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeUsersDef aDef, IAeBaseXmlDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeUntilDef aDef, IAeBaseXmlDefVisitor aVisitor);
}
