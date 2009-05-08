// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/visitors/AeHtTraversalVisitor.java,v 1.5 2008/02/17 21:51:26 mford Exp $
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
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeDocumentationDef;
import org.activebpel.rt.xml.def.AeExtensionAttributeDef;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * A specialized visitor that takes another visitor and a traversal object and calls the appropriate traverse
 * method during each visitation.
 */
public class AeHtTraversalVisitor implements IAeHtDefVisitor
{
   /** Visitor that gets passed to the traversal object */
   private IAeBaseXmlDefVisitor mVisitor;
   /** Traversal object */
   private IAeHtDefTraverser mTravers;

   /**
    * Accepts both traversal object and visitor.
    * @param aTravers - used to traverse the underlying object model
    * @param aVisitor - passed to traverse object for subsequent visitation
    */
   public AeHtTraversalVisitor(IAeHtDefTraverser aTravers, IAeBaseXmlDefVisitor aVisitor)
   {
      setTravers(aTravers);
      setVisitor(aVisitor);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeHumanInteractionsDef)
    */
   public void visit(AeHumanInteractionsDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef)
    */
   public void visit(AeLogicalPeopleGroupsDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef)
    */
   public void visit(AeLogicalPeopleGroupDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeImportDef)
    */
   public void visit(AeImportDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeExtensionDef)
    */
   public void visit(AeExtensionDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeExtensionsDef)
    */
   public void visit(AeExtensionsDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLiteralDef)
    */
   public void visit(AeLiteralDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeOutcomeDef)
    */
   public void visit(AeOutcomeDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeArgumentDef)
    */
   public void visit(AeArgumentDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeCompletionDeadlineDef)
    */
   public void visit(AeCompletionDeadlineDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeUntilDef)
    */
   public void visit(AeUntilDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDeadlinesDef)
    */
   public void visit(AeDeadlinesDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDelegationDef)
    */
   public void visit(AeDelegationDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDescriptionDef)
    */
   public void visit(AeDescriptionDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

 

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationElementsDef)
    */
   public void visit(AePresentationElementsDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationParameterDef)
    */
   public void visit(AePresentationParameterDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationParametersDef)
    */
   public void visit(AePresentationParametersDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartsDef)
    */
   public void visit(AeToPartsDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartDef)
    */
   public void visit(AeToPartDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLocalNotificationDef)
    */
   public void visit(AeLocalNotificationDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationsDef)
    */
   public void visit(AeNotificationsDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationInterfaceDef)
    */
   public void visit(AeNotificationInterfaceDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeOrganizationalEntityDef)
    */
   public void visit(AeOrganizationalEntityDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeRecipientsDef)
    */
   public void visit(AeRecipientsDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeReassignmentDef)
    */
   public void visit(AeReassignmentDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationDef)
    */
   public void visit(AeEscalationDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationProcessDef)
    */
   public void visit(AeEscalationProcessDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeProcessDataExpressionDef)
    */
   public void visit(AeProcessDataExpressionDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeRenderingDef)
    */
   public void visit(AeRenderingDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeRenderingsDef)
    */
   public void visit(AeRenderingsDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTasksDef)
    */
   public void visit(AeTasksDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskInterfaceDef)
    */
   public void visit(AeTaskInterfaceDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeConditionDef)
    */
   public void visit(AeConditionDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeParameterDef)
    */
   public void visit(AeParameterDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePotentialOwnersDef)
    */
   public void visit(AePotentialOwnersDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskInitiatorDef)
    */
   public void visit(AeTaskInitiatorDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeStartDeadlineDef)
    */
   public void visit(AeStartDeadlineDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeForDef)
    */
   public void visit(AeForDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePeopleAssignmentsDef)
    */
   public void visit(AePeopleAssignmentsDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePriorityDef)
    */
   public void visit(AePriorityDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeGroupDef)
    */
   public void visit(AeGroupDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeGroupsDef)
    */
   public void visit(AeGroupsDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeUserDef)
    */
   public void visit(AeUserDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeUsersDef)
    */
   public void visit(AeUsersDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskStakeHoldersDef)
    */
   public void visit(AeTaskStakeHoldersDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeExcludedOwnersDef)
    */
   public void visit(AeExcludedOwnersDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNameDef)
    */
   public void visit(AeNameDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeSubjectDef)
    */
   public void visit(AeSubjectDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeSearchByDef)
    */
   public void visit(AeSearchByDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * Setter for the other visitor we're using
    * @param aVisitor
    */
   public void setVisitor(IAeBaseXmlDefVisitor aVisitor)
   {
      mVisitor = aVisitor;
   }

   /**
    * Getter for the other visitor we're using
    */
   public IAeBaseXmlDefVisitor getVisitor()
   {
      return mVisitor;
   }

   /**
    * Setter for the traversal object
    * @param aTravers
    */
   public void setTravers(IAeHtDefTraverser aTravers)
   {
      mTravers = aTravers;
   }

   /**
    * Getter for the traversal object
    */
   public IAeHtDefTraverser getTravers()
   {
      return mTravers;
   }

   /**
    * @see org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor#visit(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   public void visit(AeBaseXmlDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor#visit(org.activebpel.rt.xml.def.AeExtensionElementDef)
    */
   public void visit(AeExtensionElementDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor#visit(org.activebpel.rt.xml.def.AeExtensionAttributeDef)
    */
   public void visit(AeExtensionAttributeDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor#visit(org.activebpel.rt.xml.def.AeDocumentationDef)
    */
   public void visit(AeDocumentationDef aDef)
   {
      getTravers().traverse(aDef, getVisitor());
   }
}
