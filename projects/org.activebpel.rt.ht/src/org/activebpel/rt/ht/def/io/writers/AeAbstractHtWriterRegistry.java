//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/io/writers/AeAbstractHtWriterRegistry.java,v 1.12 2008/03/01 04:39:57 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def.io.writers;

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
import org.activebpel.rt.xml.def.io.writers.AeBaseDefWriterRegistry;
import org.activebpel.rt.xml.def.io.writers.IAeDefWriterFactory;

/**
 * Abstract writer registry for Human Task defs
 */
public abstract class AeAbstractHtWriterRegistry extends AeBaseDefWriterRegistry implements IAeHtDefConstants
{
   /**
    * Ctor
    *
    * @param aDefaultNamespace
    * @param aFactory
    */
   public AeAbstractHtWriterRegistry(String aDefaultNamespace, IAeDefWriterFactory aFactory)
   {
      super(aDefaultNamespace, aFactory);
   }

   /**
    * inits the registry with mappings for def class to element qname. Contains
    * entries that are common to all versions of WS-HT.
    */
   protected void init()
   {
      super.init();

      registerWriter( AeArgumentDef.class,               TAG_ARGUMENT );
      registerWriter( AeBusinessAdministratorsDef.class, TAG_BUSINESS_ADMINISTRATORS );
      registerWriter( AeCompletionDeadlineDef.class,     TAG_COMPLETION_DEADLINE);
      registerWriter( AeConditionDef.class,              TAG_CONDITION);
      registerWriter( AeDeadlinesDef.class,              TAG_DEADLINES);
      registerWriter( AeDelegationDef.class,             TAG_DELEGATION);
      registerWriter( AeDescriptionDef.class,            TAG_DESCRIPTION);
      registerWriter( AeDocumentationDef.class,          TAG_DOCUMENTATION);
      registerWriter( AeEscalationDef.class,             TAG_ESCALATION);
      registerWriter( AeExcludedOwnersDef.class,         TAG_EXCLUDED_OWNERS);
      registerWriter( AeExtensionsDef.class,             TAG_EXTENSIONS);
      registerWriter( AeExtensionDef.class,              TAG_EXTENSION);
      registerWriter( AeForDef.class,                    TAG_FOR);
      registerWriter( AeFromDef.class,                   TAG_FROM);
      registerWriter( AeGroupDef.class,                  TAG_GROUP);
      registerWriter( AeGroupsDef.class,                 TAG_GROUPS);
      registerWriter( AeHumanInteractionsDef.class,      TAG_HUMAN_INTERACTIONS );
      registerWriter( AeImportDef.class,                 TAG_IMPORT);
      registerWriter( AeLiteralDef.class,                TAG_LITERAL);
      registerWriter( AeLocalNotificationDef.class,      TAG_LOCAL_NOTIFICATION);
      registerWriter( AeLogicalPeopleGroupsDef.class,    TAG_LOGICAL_PEOPLE_GROUPS);
      registerWriter( AeLogicalPeopleGroupDef.class,     TAG_LOGICAL_PEOPLE_GROUP);
      registerWriter( AeNameDef.class,                   TAG_NAME);
      registerWriter( AeNotificationDef.class,           TAG_NOTIFICATION);
      registerWriter( AeNotificationInterfaceDef.class,  TAG_INTERFACE);
      registerWriter( AeNotificationsDef.class,          TAG_NOTIFICATIONS);
      registerWriter( AeOrganizationalEntityDef.class,   TAG_ORGANIZATIONAL_ENTITY);
      registerWriter( AeOutcomeDef.class,                TAG_OUTCOME);
      registerWriter( AeParameterDef.class,              TAG_PARAMETER);
      registerWriter( AeReassignmentDef.class,           TAG_REASSIGNMENT);
      registerWriter( AeRecipientsDef.class,             TAG_RECIPIENTS);
      registerWriter( AeRenderingDef.class,              TAG_RENDERING);
      registerWriter( AeRenderingsDef.class,             TAG_RENDERINGS);
      registerWriter( AeSearchByDef.class,               TAG_SEARCH_BY);
      registerWriter( AeStartDeadlineDef.class,          TAG_START_DEADLINE);
      registerWriter( AeSubjectDef.class,                TAG_SUBJECT);
      registerWriter( AeTaskDef.class,                   TAG_TASK);
      registerWriter( AeTasksDef.class,                  TAG_TASKS);
      registerWriter( AeTaskInitiatorDef.class,          TAG_TASK_INITIATOR);
      registerWriter( AeTaskInterfaceDef.class,          TAG_INTERFACE);
      registerWriter( AeTaskStakeHoldersDef.class,       TAG_TASK_STAKE_HOLDERS);
      registerWriter( AeToPartDef.class,                 TAG_TO_PART);
      registerWriter( AeToPartsDef.class,                TAG_TO_PARTS);
      registerWriter( AePriorityDef.class,               TAG_PRIORITY);
      registerWriter( AePeopleAssignmentsDef.class,      TAG_PEOPLE_ASSIGNMENTS);
      registerWriter( AePotentialOwnersDef.class,        TAG_POTENTIAL_OWNERS);
      registerWriter( AePresentationElementsDef.class,   TAG_PRESENTATION_ELEMENTS);
      registerWriter( AePresentationParameterDef.class,  TAG_PRESENTATION_PARAMETER);
      registerWriter( AePresentationParametersDef.class, TAG_PRESENTATION_PARAMETERS);
      registerWriter( AeUntilDef.class,                  TAG_UNTIL);
      registerWriter( AeUserDef.class,                   TAG_USER);
      registerWriter( AeUsersDef.class,                  TAG_USERS);
      setDefaultNamespace(AE_HT_EXTENSION_NAMESPACE);
      registerWriter( AeEscalationProcessDef.class,      new QName(IAeHtDefConstants.AE_B4P_TASK_CUSTOM_NOTIFICATIONS_NS, TAG_ESCALATION_PROCESS));
      registerWriter( AeProcessDataExpressionDef.class,  new QName(IAeHtDefConstants.AE_B4P_TASK_CUSTOM_NOTIFICATIONS_NS, TAG_PROCESS_DATA));
      registerWriter( AeEscalationProcessConditionDef.class, new QName(IAeHtDefConstants.AE_B4P_TASK_CUSTOM_NOTIFICATIONS_NS, TAG_CONDITION));
   }
}
