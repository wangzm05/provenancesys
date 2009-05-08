//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/io/readers/AeAbstractHtReaderRegistry.java,v 1.14.4.2 2008/04/14 21:26:06 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def.io.readers;

import javax.xml.namespace.QName;

import org.activebpel.rt.ht.def.AeArgumentDef;
import org.activebpel.rt.ht.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.ht.def.AeCompletionDeadlineDef;
import org.activebpel.rt.ht.def.AeConditionDef;
import org.activebpel.rt.ht.def.AeDeadlinesDef;
import org.activebpel.rt.ht.def.AeDelegationDef;
import org.activebpel.rt.ht.def.AeDescriptionDef;
import org.activebpel.rt.ht.def.AeEscalationDef;
import org.activebpel.rt.ht.def.AeEscalationProcessConditionDef;
import org.activebpel.rt.ht.def.AeEscalationProcessDef;
import org.activebpel.rt.ht.def.AeExcludedOwnersDef;
import org.activebpel.rt.ht.def.AeExtensionDef;
import org.activebpel.rt.ht.def.AeExtensionsDef;
import org.activebpel.rt.ht.def.AeForDef;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AeGroupDef;
import org.activebpel.rt.ht.def.AeGroupsDef;
import org.activebpel.rt.ht.def.AeHumanInteractionsDef;
import org.activebpel.rt.ht.def.AeImportDef;
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
import org.activebpel.rt.ht.def.AeProcessDataExpressionDef;
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
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.xml.def.AeDocumentationDef;
import org.activebpel.rt.xml.def.io.readers.AeDefReaderRegistry;
import org.activebpel.rt.xml.def.io.readers.IAeReaderFactory;

/**
 * Abstract registry for reading HT defs from xml
 */
public abstract class AeAbstractHtReaderRegistry extends AeDefReaderRegistry implements IAeHtDefConstants
{
   /**
    * Ctor
    * @param aDefaultNamespace
    * @param aFactory
    */
   public AeAbstractHtReaderRegistry(String aDefaultNamespace, IAeReaderFactory aFactory)
   {
      super(aDefaultNamespace, aFactory);
      setExtensionReader(new AeHTExtensionElementReader());
   }

   /**
    * @see org.activebpel.rt.xml.def.io.readers.AeDefReaderRegistry#initParentRegistry()
    */
   protected void initParentRegistry()
   {
      // maps readers for children of humanInteractions element
      registerReader( AeHumanInteractionsDef.class,      TAG_HUMAN_INTERACTIONS,      createReader(AeHumanInteractionsDef.class) );
      registerReader( AeHumanInteractionsDef.class,      TAG_EXTENSIONS,              createReader(AeExtensionsDef.class) );
      registerReader( AeHumanInteractionsDef.class,      TAG_IMPORT,                  createReader(AeImportDef.class) );
      registerReader( AeHumanInteractionsDef.class,      TAG_LOGICAL_PEOPLE_GROUPS,   createReader(AeLogicalPeopleGroupsDef.class) );
      registerReader( AeHumanInteractionsDef.class,      TAG_TASKS,                   createReader(AeTasksDef.class) );
      registerReader( AeHumanInteractionsDef.class,      TAG_NOTIFICATIONS,           createReader(AeNotificationsDef.class) );
      //maps readers for children of extensions element
      registerReader( AeExtensionsDef.class ,            TAG_EXTENSION,               createReader(AeExtensionDef.class));
      //maps readers for children of logicalPeopleGroups element
      registerReader( AeLogicalPeopleGroupsDef.class,    TAG_LOGICAL_PEOPLE_GROUP,    createReader(AeLogicalPeopleGroupDef.class));
      //maps readers for children of logicalPeopleGroup element
      registerReader( AeLogicalPeopleGroupDef.class,     TAG_PARAMETER,               createReader(AeParameterDef.class));
      //maps readers for children of tasks element
      registerReader( AeTasksDef.class,                  TAG_TASK,                    createReader(AeTaskDef.class));
      //maps readers for children of task element
      registerReader( AeTaskDef.class,                   TAG_INTERFACE,               createReader(AeTaskInterfaceDef.class));
      registerReader( AeTaskDef.class,                   TAG_PRIORITY,                createReader(AePriorityDef.class));
      registerReader( AeTaskDef.class,                   TAG_PEOPLE_ASSIGNMENTS,      createReader(AePeopleAssignmentsDef.class));
      registerReader( AeTaskDef.class,                   TAG_DELEGATION,              createReader(AeDelegationDef.class));
      registerReader( AeTaskDef.class,                   TAG_PRESENTATION_ELEMENTS,   createReader(AePresentationElementsDef.class));
      registerReader( AeTaskDef.class,                   TAG_OUTCOME,                 createReader(AeOutcomeDef.class));
      registerReader( AeTaskDef.class,                   TAG_SEARCH_BY,               createReader(AeSearchByDef.class));
      registerReader( AeTaskDef.class,                   TAG_RENDERINGS,              createReader(AeRenderingsDef.class));
      registerReader( AeTaskDef.class,                   TAG_DEADLINES,               createReader(AeDeadlinesDef.class));
      //maps readers for children of notifications element
      registerReader( AeNotificationsDef.class,          TAG_NOTIFICATION,            createReader(AeNotificationDef.class));
      //maps readers for children of notification element
      registerReader( AeNotificationDef.class,           TAG_INTERFACE,               createReader(AeNotificationInterfaceDef.class));
      registerReader( AeNotificationDef.class,           TAG_PRIORITY,                createReader(AePriorityDef.class));
      registerReader( AeNotificationDef.class,           TAG_PEOPLE_ASSIGNMENTS,      createReader(AePeopleAssignmentsDef.class));
      registerReader( AeNotificationDef.class,           TAG_PRESENTATION_ELEMENTS,   createReader(AePresentationElementsDef.class));
      registerReader( AeNotificationDef.class,           TAG_RENDERINGS,              createReader(AeRenderingsDef.class));
      //maps readers for children of peopleAssignments element
      registerReader( AePeopleAssignmentsDef.class,      TAG_POTENTIAL_OWNERS,        createReader(AePotentialOwnersDef.class));
      registerReader( AePeopleAssignmentsDef.class,      TAG_EXCLUDED_OWNERS,         createReader(AeExcludedOwnersDef.class));
      registerReader( AePeopleAssignmentsDef.class,      TAG_TASK_INITIATOR,          createReader(AeTaskInitiatorDef.class));
      registerReader( AePeopleAssignmentsDef.class,      TAG_TASK_STAKE_HOLDERS,      createReader(AeTaskStakeHoldersDef.class));
      registerReader( AePeopleAssignmentsDef.class,      TAG_BUSINESS_ADMINISTRATORS, createReader(AeBusinessAdministratorsDef.class));
      registerReader( AePeopleAssignmentsDef.class,      TAG_RECIPIENTS,              createReader(AeRecipientsDef.class));
      //maps readers for children of genericHumanRole group
      registerReader( AePotentialOwnersDef.class,        TAG_FROM,                    createReader(AeFromDef.class));
      registerReader( AeExcludedOwnersDef.class,         TAG_FROM,                    createReader(AeFromDef.class));
      registerReader( AeTaskInitiatorDef.class,          TAG_FROM,                    createReader(AeFromDef.class));
      registerReader( AeTaskStakeHoldersDef.class,       TAG_FROM,                    createReader(AeFromDef.class));
      registerReader( AeBusinessAdministratorsDef.class, TAG_FROM,                    createReader(AeFromDef.class));
      registerReader( AeRecipientsDef.class,             TAG_FROM,                    createReader(AeFromDef.class));
      //maps readers for children of delegation element
      registerReader( AeDelegationDef.class,             TAG_FROM,                    createReader(AeFromDef.class));
      //maps readers for children of from element
      registerReader( AeFromDef.class,                   TAG_ARGUMENT,                createReader(AeArgumentDef.class));
      registerReader( AeFromDef.class,                   TAG_LITERAL,                 createReader(AeLiteralDef.class));
      //maps readers for children of organiztionalEntity element
      registerReader( AeOrganizationalEntityDef.class,   TAG_GROUPS,                  createReader(AeGroupsDef.class));
      registerReader( AeOrganizationalEntityDef.class,   TAG_USERS,                   createReader(AeUsersDef.class));
      //maps readers for children of groups element
      registerReader( AeGroupsDef.class,                 TAG_GROUP,                   createReader(AeGroupDef.class));
      //maps readers for children of users element
      registerReader( AeUsersDef.class,                  TAG_USER,                    createReader(AeUserDef.class));
      //maps readers for children of presentationElements element
      registerReader( AePresentationElementsDef.class,   TAG_NAME,                    createReader(AeNameDef.class));
      registerReader( AePresentationElementsDef.class,   TAG_PRESENTATION_PARAMETERS, createReader(AePresentationParametersDef.class));
      registerReader( AePresentationElementsDef.class,   TAG_SUBJECT,                 createReader(AeSubjectDef.class));
      registerReader( AePresentationElementsDef.class,   TAG_DESCRIPTION,             createReader(AeDescriptionDef.class));
      //maps readers for children of presentationParameters element
      registerReader( AePresentationParametersDef.class, TAG_PRESENTATION_PARAMETER,  createReader(AePresentationParameterDef.class));
      //maps readers for children of renderings element
      registerReader( AeRenderingsDef.class,             TAG_RENDERING,               createReader(AeRenderingDef.class));
      //maps readers for children of deadlines element
      registerReader( AeDeadlinesDef.class,              TAG_START_DEADLINE,          createReader(AeStartDeadlineDef.class));
      registerReader( AeDeadlinesDef.class,              TAG_COMPLETION_DEADLINE,     createReader(AeCompletionDeadlineDef.class));
      //maps readers for children of deadline element
      registerReader( AeDeadlinesDef.class,              TAG_COMPLETION_DEADLINE,     createReader(AeCompletionDeadlineDef.class));
      
      //maps readers for children of startDeadline and completionDeadline elements
      registerReader( AeStartDeadlineDef.class,          TAG_FOR,                     createReader(AeForDef.class));
      registerReader( AeStartDeadlineDef.class,          TAG_UNTIL,                   createReader(AeUntilDef.class));
      registerReader( AeStartDeadlineDef.class,          TAG_ESCALATION,              createReader(AeEscalationDef.class));
      registerReader( AeStartDeadlineDef.class,          new QName(IAeHtDefConstants.AE_HT_EXTENSION_NAMESPACE, TAG_ESCALATION_PROCESS), createReader(AeEscalationProcessDef.class));
      registerReader( AeStartDeadlineDef.class,          new QName(IAeHtDefConstants.AE_B4P_TASK_CUSTOM_NOTIFICATIONS_NS, TAG_ESCALATION_PROCESS), createReader(AeEscalationProcessDef.class));
      registerReader( AeCompletionDeadlineDef.class,     TAG_FOR,                     createReader(AeForDef.class));
      registerReader( AeCompletionDeadlineDef.class,     TAG_UNTIL,                   createReader(AeUntilDef.class));
      registerReader( AeCompletionDeadlineDef.class,     TAG_ESCALATION,              createReader(AeEscalationDef.class));
      registerReader( AeCompletionDeadlineDef.class,     new QName(IAeHtDefConstants.AE_HT_EXTENSION_NAMESPACE, TAG_ESCALATION_PROCESS), createReader(AeEscalationProcessDef.class));
      registerReader( AeCompletionDeadlineDef.class,     new QName(IAeHtDefConstants.AE_B4P_TASK_CUSTOM_NOTIFICATIONS_NS, TAG_ESCALATION_PROCESS), createReader(AeEscalationProcessDef.class));

      //maps readers for custom escalation process defs
      registerReader( AeEscalationProcessDef.class,      new QName(IAeHtDefConstants.AE_HT_EXTENSION_NAMESPACE, TAG_PROCESS_DATA), createReader(AeProcessDataExpressionDef.class));
      registerReader( AeEscalationProcessDef.class,      new QName(IAeHtDefConstants.AE_B4P_TASK_CUSTOM_NOTIFICATIONS_NS, TAG_PROCESS_DATA), createReader(AeProcessDataExpressionDef.class));
      registerReader( AeEscalationProcessDef.class,      TAG_CONDITION,               createReader(AeEscalationProcessConditionDef.class));
      registerReader( AeEscalationProcessDef.class,      new QName(IAeHtDefConstants.AE_B4P_TASK_CUSTOM_NOTIFICATIONS_NS, TAG_CONDITION), createReader(AeEscalationProcessConditionDef.class));

      //maps readers for children of escalation element
      registerReader( AeEscalationDef.class,             TAG_CONDITION,               createReader(AeConditionDef.class));
      registerReader( AeEscalationDef.class,             TAG_TO_PARTS,                createReader(AeToPartsDef.class));
      registerReader( AeEscalationDef.class,             TAG_NOTIFICATION,            createReader(AeNotificationDef.class));
      registerReader( AeEscalationDef.class,             TAG_LOCAL_NOTIFICATION,      createReader(AeLocalNotificationDef.class));
      registerReader( AeEscalationDef.class,             TAG_REASSIGNMENT,            createReader(AeReassignmentDef.class));
      //maps readers for children of localNotification element
      registerReader( AeLocalNotificationDef.class,      TAG_PRIORITY,                createReader(AePriorityDef.class));
      registerReader( AeLocalNotificationDef.class,      TAG_PEOPLE_ASSIGNMENTS,      createReader(AePeopleAssignmentsDef.class));
      //maps readers for children of reassignment element    
      registerReader( AeReassignmentDef.class,           TAG_POTENTIAL_OWNERS,        createReader(AePotentialOwnersDef.class));
      //maps readers for children of toParts element    
      registerReader( AeToPartsDef.class,                TAG_TO_PART,                 createReader(AeToPartDef.class));
   }

   /**
    * Populates the mGenericReadersMap with any generic readers.
    */
   protected void initGenericElementRegistry()
   {
      getGenericReadersMap().put(makeDefaultQName(TAG_HUMAN_INTERACTIONS),    createReader(AeHumanInteractionsDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_DOCUMENTATION),         createReader(AeDocumentationDef.class));
      // Pseudo root when embedded in BPEL4People model
      getGenericReadersMap().put(makeDefaultQName(TAG_LOGICAL_PEOPLE_GROUPS), createReader(AeLogicalPeopleGroupsDef.class) );
      getGenericReadersMap().put(makeDefaultQName(TAG_TASKS),                 createReader(AeTasksDef.class) );
      getGenericReadersMap().put(makeDefaultQName(TAG_NOTIFICATIONS),         createReader(AeNotificationsDef.class) );
      getGenericReadersMap().put(makeDefaultQName(TAG_PRESENTATION_ELEMENTS), createReader(AePresentationElementsDef.class) );
      getGenericReadersMap().put(makeDefaultQName(TAG_ORGANIZATIONAL_ENTITY), createReader(AeOrganizationalEntityDef.class) );
   }
}
