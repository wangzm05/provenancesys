// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/visitors/IAeHtDefVisitor.java,v 1.3 2008/01/11 01:49:26 mford Exp $
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
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * Visitor interface for Human Task Definition classes.
 */
public interface IAeHtDefVisitor extends IAeBaseXmlDefVisitor
{
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeHumanInteractionsDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeArgumentDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeBusinessAdministratorsDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeCompletionDeadlineDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeUntilDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeForDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeDeadlinesDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeDelegationDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeDescriptionDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeLogicalPeopleGroupsDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeLogicalPeopleGroupDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeNameDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeFromDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeImportDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeExtensionsDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeExtensionDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeLiteralDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeToPartsDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeToPartDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AePresentationParameterDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AePresentationParametersDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeParameterDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AePresentationElementsDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeLocalNotificationDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeNotificationsDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeNotificationDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeNotificationInterfaceDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeOrganizationalEntityDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeOutcomeDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeReassignmentDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeRecipientsDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeEscalationDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeEscalationProcessDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeProcessDataExpressionDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeRenderingDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeRenderingsDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeSubjectDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeSearchByDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeStartDeadlineDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeTaskDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeTasksDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeTaskInterfaceDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeConditionDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeExcludedOwnersDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AePotentialOwnersDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AePeopleAssignmentsDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AePriorityDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeTaskInitiatorDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeTaskStakeHoldersDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeGroupDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeGroupsDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeUserDef aDef);

   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeUsersDef aDef);

}
