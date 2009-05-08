// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAeHtDefConstants.java,v 1.8 2008/03/01 04:39:57 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import org.activebpel.rt.ht.IAeWSHTConstants;
import org.activebpel.rt.xml.def.IAeBaseXmlDefConstants;

/**
 * Contains the list of BPEL Constants.
 */
public interface IAeHtDefConstants extends IAeBaseXmlDefConstants
{
   /** Default Human Task NS */
   public static final String DEFAULT_HT_NS               = IAeWSHTConstants.WSHT_NAMESPACE;
   public static final String DEFAULT_HT_PREFIX           = "htd"; //$NON-NLS-1$
   
   /** namespace for our extensions to HT */
   public static final String AE_HT_EXTENSION_NAMESPACE = "http://www.activebpel.org/2007/10/ht/extension"; //$NON-NLS-1$

   /** namespace for custom notification process defs. */
   public static final String AE_B4P_TASK_CUSTOM_NOTIFICATIONS_NS = "http://schemas.active-endpoints.com/b4p/wshumantask/2007/12/aeb4p-task-custom-notification.xsd"; //$NON-NLS-1$

   public static final String ATTR_NO                     = "no"; //$NON-NLS-1$
   public static final String ATTR_YES                    = "yes"; //$NON-NLS-1$

   // Human task element constants
   public static final String TAG_ARGUMENT                = "argument"; //$NON-NLS-1$
   public static final String TAG_BUSINESS_ADMINISTRATORS = "businessAdministrators"; //$NON-NLS-1$
   public static final String TAG_COMPLETION_DEADLINE     = "completionDeadline"; //$NON-NLS-1$
   public static final String TAG_CONDITION               = "condition"; //$NON-NLS-1$
   public static final String TAG_DEADLINES               = "deadlines"; //$NON-NLS-1$
   public static final String TAG_DELEGATION              = "delegation"; //$NON-NLS-1$
   public static final String TAG_DESCRIPTION             = "description"; //$NON-NLS-1$
   public static final String TAG_ESCALATION              = "escalation"; //$NON-NLS-1$
   public static final String TAG_EXCLUDED_OWNERS         = "excludedOwners"; //$NON-NLS-1$
   public static final String TAG_EXTENSIONS              = "extensions"; //$NON-NLS-1$
   public static final String TAG_EXTENSION               = "extension"; //$NON-NLS-1$
   public static final String TAG_FOR                     = "for"; //$NON-NLS-1$
   public static final String TAG_FROM                    = "from"; //$NON-NLS-1$
   public static final String TAG_GROUP                   = "group"; //$NON-NLS-1$
   public static final String TAG_GROUPS                  = "groups"; //$NON-NLS-1$
   public static final String TAG_HUMAN_INTERACTIONS      = "humanInteractions"; //$NON-NLS-1$
   public static final String TAG_IMPORT                  = "import"; //$NON-NLS-1$
   public static final String TAG_ESCALATION_PROCESS      = "escalationProcess"; //$NON-NLS-1$
   public static final String TAG_PROCESS_DATA            = "processData"; //$NON-NLS-1$
   public static final String TAG_INTERFACE               = "interface"; //$NON-NLS-1$
   public static final String TAG_LITERAL                 = "literal"; //$NON-NLS-1$
   public static final String TAG_LOCAL_NOTIFICATION      = "localNotification"; //$NON-NLS-1$
   public static final String TAG_LOGICAL_PEOPLE_GROUPS   = "logicalPeopleGroups"; //$NON-NLS-1$
   public static final String TAG_LOGICAL_PEOPLE_GROUP    = "logicalPeopleGroup"; //$NON-NLS-1$
   public static final String TAG_NAME                    = "name"; //$NON-NLS-1$
   public static final String TAG_NOTIFICATION            = "notification"; //$NON-NLS-1$
   public static final String TAG_NOTIFICATIONS           = "notifications"; //$NON-NLS-1$
   public static final String TAG_ORGANIZATIONAL_ENTITY   = "organizationalEntity"; //$NON-NLS-1$
   public static final String TAG_OUTCOME                 = "outcome"; //$NON-NLS-1$
   public static final String TAG_PARAMETER               = "parameter"; //$NON-NLS-1$
   public static final String TAG_PRESENTATION_ELEMENTS   = "presentationElements"; //$NON-NLS-1$
   public static final String TAG_PRESENTATION_PARAMETER  = "presentationParameter"; //$NON-NLS-1$
   public static final String TAG_PRESENTATION_PARAMETERS = "presentationParameters"; //$NON-NLS-1$
   public static final String TAG_PEOPLE_ASSIGNMENTS      = "peopleAssignments"; //$NON-NLS-1$
   public static final String TAG_PRIORITY                = "priority"; //$NON-NLS-1$
   public static final String TAG_POTENTIAL_OWNERS        = "potentialOwners"; //$NON-NLS-1$
   public static final String TAG_RECIPIENTS              = "recipients"; //$NON-NLS-1$
   public static final String TAG_REASSIGNMENT            = "reassignment"; //$NON-NLS-1$
   public static final String TAG_RENDERING               = "rendering"; //$NON-NLS-1$
   public static final String TAG_RENDERINGS              = "renderings"; //$NON-NLS-1$
   public static final String TAG_SEARCH_BY               = "searchBy"; //$NON-NLS-1$
   public static final String TAG_SERVICE                 = "service"; //$NON-NLS-1$
   public static final String TAG_START_DEADLINE          = "startDeadline"; //$NON-NLS-1$
   public static final String TAG_SUBJECT                 = "subject"; //$NON-NLS-1$
   public static final String TAG_TASK                    = "task"; //$NON-NLS-1$
   public static final String TAG_TASKS                   = "tasks"; //$NON-NLS-1$
   public static final String TAG_TASK_INITIATOR          = "taskInitiator"; //$NON-NLS-1$
   public static final String TAG_TASK_STAKE_HOLDERS      = "taskStakeholders"; //$NON-NLS-1$
   public static final String TAG_TO_PART                 = "toPart"; //$NON-NLS-1$
   public static final String TAG_TO_PARTS                = "toParts"; //$NON-NLS-1$
   public static final String TAG_UNTIL                   = "until"; //$NON-NLS-1$
   public static final String TAG_USER                    = "user"; //$NON-NLS-1$
   public static final String TAG_USERS                   = "users"; //$NON-NLS-1$


   // Human Task (WS-HT) Attribute constants
   public static final String ATTR_CONTENT_TYPE            = "contentType"; //$NON-NLS-1$
   public static final String ATTR_NAMESPACE               = "namespace"; //$NON-NLS-1$
   public static final String ATTR_TARGET_NAMESPACE        = "targetNamespace"; //$NON-NLS-1$
   public static final String ATTR_QUERY_LANGUAGE          = "queryLanguage"; //$NON-NLS-1$
   public static final String ATTR_EXPRESSION_LANGUAGE     = "expressionLanguage"; //$NON-NLS-1$
   public static final String ATTR_LOCATION                = "location"; //$NON-NLS-1$
   public static final String ATTR_IMPORT_TYPE             = "importType"; //$NON-NLS-1$
   public static final String ATTR_MUST_UNDERSTAND         = "mustUnderstand"; //$NON-NLS-1$
   public static final String ATTR_REFERENCE               = "reference"; //$NON-NLS-1$
   public static final String ATTR_PART                    = "part"; //$NON-NLS-1$
   public static final String ATTR_PORT_TYPE               = "portType"; //$NON-NLS-1$
   public static final String ATTR_POTENTIAL_DELEGATEES    = "potentialDelegatees"; //$NON-NLS-1$
   public static final String ATTR_OPERATION               = "operation"; //$NON-NLS-1$
   public static final String ATTR_RESPONSE_PORT_TYPE      = "responsePortType"; //$NON-NLS-1$
   public static final String ATTR_RESPONSE_OPERATION      = "responseOperation"; //$NON-NLS-1$
   public static final String ATTR_TYPE                    = "type"; //$NON-NLS-1$
   
   /** The value of "expressionLanguage" for XPath (also the default for BPEL 1.1). */
   public static final String BPWS_XPATH_EXPR_LANGUAGE_URI = "http://www.w3.org/TR/1999/REC-xpath-19991116"; //$NON-NLS-1$
   /** The value of "expressionLanguage" for XPath (also the default for BPEL 2.0). */
   public static final String WSBPEL_EXPR_LANGUAGE_URI = "urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0"; //$NON-NLS-1$
   /** The value of "expressionLanguage" for XQuery (extension language by AEI). */
   public static final String XQUERY_EXPR_LANGUAGE_URI = "urn:active-endpoints:expression-language:xquery1.0"; //$NON-NLS-1$
   /** The value of "expressionLanguage" for JavaScript (extension language by AEI). */
   public static final String JAVASCRIPT_EXPR_LANGUAGE_URI = "urn:active-endpoints:expression-language:javascript1.5"; //$NON-NLS-1$
}
