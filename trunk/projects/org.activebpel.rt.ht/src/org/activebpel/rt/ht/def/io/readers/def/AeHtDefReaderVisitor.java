//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/io/readers/def/AeHtDefReaderVisitor.java,v 1.19 2008/03/14 20:45:25 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def.io.readers.def;

import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.ht.def.AeAbstractDeadlineDef;
import org.activebpel.rt.ht.def.AeAbstractExpressionDef;
import org.activebpel.rt.ht.def.AeAbstractParameterListDef;
import org.activebpel.rt.ht.def.AeArgumentDef;
import org.activebpel.rt.ht.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.ht.def.AeCompletionDeadlineDef;
import org.activebpel.rt.ht.def.AeConditionDef;
import org.activebpel.rt.ht.def.AeDeadlinesDef;
import org.activebpel.rt.ht.def.AeDelegationDef;
import org.activebpel.rt.ht.def.AeDescriptionDef;
import org.activebpel.rt.ht.def.AeEscalationDef;
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
import org.activebpel.rt.ht.def.IAeConditionParentDef;
import org.activebpel.rt.ht.def.IAeFromDefParent;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.ht.def.IAeLogicalPeopleGroupsDefParent;
import org.activebpel.rt.ht.def.IAeNotificationDefParent;
import org.activebpel.rt.ht.def.IAeNotificationsDefParent;
import org.activebpel.rt.ht.def.IAePeopleAssignmentsDefParent;
import org.activebpel.rt.ht.def.IAePotentialOwnersParent;
import org.activebpel.rt.ht.def.IAePresentationElementsDefParent;
import org.activebpel.rt.ht.def.IAePriorityDefParent;
import org.activebpel.rt.ht.def.IAeRenderingsDefParent;
import org.activebpel.rt.ht.def.IAeTaskDefParent;
import org.activebpel.rt.ht.def.IAeTasksDefParent;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeDocumentationDef;
import org.activebpel.rt.xml.def.AeExtensionAttributeDef;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.activebpel.rt.xml.def.IAeBaseXmlDefConstants;
import org.activebpel.rt.xml.def.io.readers.AeAbstractReportingDefReader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Implementation of the reader visitor logic for WS-HT
 */
public class AeHtDefReaderVisitor extends AeAbstractReportingDefReader implements IAeHtDefVisitor,
      IAeHtDefConstants, IAeBaseXmlDefConstants
{
   /**
    * Constructor
    * @param aParentDef child will be added to this
    * @param aElement current element to read from
    */
   public AeHtDefReaderVisitor(AeBaseXmlDef aParentDef, Element aElement)
   {
      super(aParentDef, aElement);
   }

   /**
    * @see org.activebpel.rt.xml.def.io.readers.IAeReportingDefReader#read(org.activebpel.rt.xml.def.AeBaseXmlDef,
    *      org.w3c.dom.Element)
    */
   public void read(AeBaseXmlDef aDef, Element aElement)
   {
      aDef.accept(this);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeHumanInteractionsDef)
    */
   public void visit(AeHumanInteractionsDef aDef)
   {
      readAttributes(aDef);
      aDef.setNamespace(getCurrentElement().getNamespaceURI());
      aDef.setTargetNamespace(getAttribute(ATTR_TARGET_NAMESPACE));
      aDef.setExpressionLanguage(getAttribute(ATTR_EXPRESSION_LANGUAGE));
      aDef.setQueryLanguage(getAttribute(ATTR_QUERY_LANGUAGE));
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeArgumentDef)
    */
   public void visit(AeArgumentDef aDef)
   {
      readAttributes(aDef);
      readExpressionDef(aDef);
      aDef.setName(getAttribute(TAG_NAME));
      if (getParentXmlDef() != null)
         ((AeFromDef)getParentXmlDef()).addArgument(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((AePeopleAssignmentsDef)getParentXmlDef()).setBusinessAdministrators(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeStartDeadlineDef)
    */
   public void visit(AeStartDeadlineDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((AeDeadlinesDef)getParentXmlDef()).addStartDeadline(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeSearchByDef)
    */
   public void visit(AeSearchByDef aDef)
   {
      readAttributes(aDef);
      readExpressionDef(aDef);
      if (getParentXmlDef() != null)
         ((AeTaskDef)getParentXmlDef()).setSearchBy(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeSubjectDef)
    */
   public void visit(AeSubjectDef aDef)
   {
      readAttributes(aDef);
      aDef.setLanguage(getLanguage());
      aDef.readMixedText(getCurrentElement());
      if (getParentXmlDef() != null)
         ((AePresentationElementsDef)getParentXmlDef()).addSubject(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeCompletionDeadlineDef)
    */
   public void visit(AeCompletionDeadlineDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((AeDeadlinesDef)getParentXmlDef()).addCompletionDeadline(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeForDef)
    */
   public void visit(AeForDef aDef)
   {
      readAttributes(aDef);
      readExpressionDef(aDef);
      if (getParentXmlDef() != null)
         ((AeAbstractDeadlineDef)getParentXmlDef()).setFor(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeUntilDef)
    */
   public void visit(AeUntilDef aDef)
   {
      readAttributes(aDef);
      readExpressionDef(aDef);
      if (getParentXmlDef() != null)
         ((AeAbstractDeadlineDef)getParentXmlDef()).setUntil(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDeadlinesDef)
    */
   public void visit(AeDeadlinesDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((AeTaskDef)getParentXmlDef()).setDeadlines(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDelegationDef)
    */
   public void visit(AeDelegationDef aDef)
   {
      readAttributes(aDef);
      aDef.setPotentialDelegatees(getAttribute(ATTR_POTENTIAL_DELEGATEES));
      if (getParentXmlDef() != null)
         ((AeTaskDef)getParentXmlDef()).setDelegation(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDescriptionDef)
    */
   public void visit(AeDescriptionDef aDef)
   {
      readAttributes(aDef);
      aDef.setLanguage(getLanguage());
      aDef.readMixedText(getCurrentElement());
      aDef.setContentType(getAttribute(ATTR_CONTENT_TYPE));
      if (getParentXmlDef() != null)
         ((AePresentationElementsDef)getParentXmlDef()).addDescription(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef)
    */
   public void visit(AeLogicalPeopleGroupsDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((IAeLogicalPeopleGroupsDefParent)getParentXmlDef()).setLogicalPeopleGroupsDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef)
    */
   public void visit(AeLogicalPeopleGroupDef aDef)
   {
      readAttributes(aDef);
      aDef.setName(getAttribute(TAG_NAME));
      aDef.setReference(getAttributeQName(ATTR_REFERENCE));
      if (getParentXmlDef() != null)
         ((AeLogicalPeopleGroupsDef)getParentXmlDef()).addLogicalPeopleGroup(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      readAttributes(aDef);
      aDef.readMixedText(getCurrentElement());
      aDef.setExpressionLanguage(getAttribute(ATTR_EXPRESSION_LANGUAGE));
      aDef.setLogicalPeopleGroup(getAttributeQName(TAG_LOGICAL_PEOPLE_GROUP));
      if (getParentXmlDef() != null)
         ((IAeFromDefParent)getParentXmlDef()).setFrom(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeImportDef)
    */
   public void visit(AeImportDef aDef)
   {
      readAttributes(aDef);

      aDef.setNamespace(getAttribute(ATTR_NAMESPACE));
      aDef.setLocation(getAttribute(ATTR_LOCATION));
      aDef.setImportType(getAttribute(ATTR_IMPORT_TYPE));

      if (getParentXmlDef() != null)
         ((AeHumanInteractionsDef)getParentXmlDef()).addImport(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeExtensionsDef)
    */
   public void visit(AeExtensionsDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((AeHumanInteractionsDef)getParentXmlDef()).setExtensionsDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeExtensionDef)
    */
   public void visit(AeExtensionDef aDef)
   {
      readAttributes(aDef);
      aDef.setNamespace(getAttribute(ATTR_NAMESPACE));
      aDef.setMustUnderstand(getAttributeBoolean(ATTR_MUST_UNDERSTAND));
      if (getParentXmlDef() != null)
         ((AeExtensionsDef)getParentXmlDef()).addExtension(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLiteralDef)
    */
   public void visit(AeLiteralDef aDef)
   {
      readAttributes(aDef);
      addChildrenToLiteral(getCurrentElement(), aDef);
      if (getParentXmlDef() != null)
         ((AeFromDef)getParentXmlDef()).setLiteral(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartsDef)
    */
   public void visit(AeToPartsDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((AeEscalationDef)getParentXmlDef()).setToParts(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartDef)
    */
   public void visit(AeToPartDef aDef)
   {
      readAttributes(aDef);
      aDef.readMixedText(getCurrentElement());
      aDef.setName(getAttribute(TAG_NAME));
      aDef.setExpressionLanguage(getAttribute(ATTR_EXPRESSION_LANGUAGE));
      if (getParentXmlDef() != null)
         ((AeToPartsDef)getParentXmlDef()).addToPart(aDef);
   }
   

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationElementsDef)
    */
   public void visit(AePresentationElementsDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((IAePresentationElementsDefParent)getParentXmlDef()).setPresentationElements(aDef);
   }
   
  /**
   * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationParameterDef)
   */
   public void visit(AePresentationParameterDef aDef)
   {
      readAttributes(aDef);
      readExpressionDef(aDef);
      aDef.setName(getAttribute(TAG_NAME));
      aDef.setType(getAttributeQName(ATTR_TYPE)); 
      if (getParentXmlDef() != null)
         ((AePresentationParametersDef)getParentXmlDef()).addPresentationParameter(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationParametersDef)
    */
   public void visit(AePresentationParametersDef aDef)
   {
      readAttributes(aDef);
      aDef.setExpressionLanguage(getAttribute(ATTR_EXPRESSION_LANGUAGE));
      if (getParentXmlDef() != null)
         ((AePresentationElementsDef)getParentXmlDef()).setPresentationParameters(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeParameterDef)
    */
   public void visit(AeParameterDef aDef)
   {
      readAttributes(aDef);
      aDef.setName(getAttribute(TAG_NAME));
      aDef.setType(getAttributeQName(ATTR_TYPE));
      if (getParentXmlDef() != null)
         ((AeAbstractParameterListDef)getParentXmlDef()).addParameter(aDef);
   }


   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLocalNotificationDef)
    */
   public void visit(AeLocalNotificationDef aDef)
   {
      readAttributes(aDef);
      aDef.setReference(getAttributeQName(ATTR_REFERENCE));
      if (getParentXmlDef() != null)
         ((AeEscalationDef)getParentXmlDef()).setLocalNotification(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNameDef)
    */
   public void visit(AeNameDef aDef)
   {
      readAttributes(aDef);
      aDef.setLanguage(getLanguage());
      aDef.readMixedText(getCurrentElement());
      if (getParentXmlDef() != null)
         ((AePresentationElementsDef)getParentXmlDef()).addName(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationsDef)
    */
   public void visit(AeNotificationsDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((IAeNotificationsDefParent)getParentXmlDef()).setNotificationsDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      readAttributes(aDef);
      aDef.setName(getAttribute(TAG_NAME));
      if (getParentXmlDef() != null)
         ((IAeNotificationDefParent)getParentXmlDef()).addNotification(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationInterfaceDef)
    */
   public void visit(AeNotificationInterfaceDef aDef)
   {
      readAttributes(aDef);
      aDef.setPortType(getAttributeQName(ATTR_PORT_TYPE));
      aDef.setOperation(getAttribute(ATTR_OPERATION));
      if (getParentXmlDef() != null)
         ((AeNotificationDef)getParentXmlDef()).setInterface(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeOrganizationalEntityDef)
    */
   public void visit(AeOrganizationalEntityDef aDef)
   {
      readAttributes(aDef);
      
      // Organizational entity is a top level entity.
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeOutcomeDef)
    */
   public void visit(AeOutcomeDef aDef)
   {
      readAttributes(aDef);
      aDef.readMixedText(getCurrentElement());
      aDef.setPart(getAttribute(ATTR_PART));
      aDef.setQueryLanguage(getAttribute(ATTR_QUERY_LANGUAGE));
      if (getParentXmlDef() != null)
         ((AeTaskDef)getParentXmlDef()).setOutcome(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeReassignmentDef)
    */
   public void visit(AeReassignmentDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((AeEscalationDef)getParentXmlDef()).setReassignment(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeRecipientsDef)
    */
   public void visit(AeRecipientsDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((AePeopleAssignmentsDef)getParentXmlDef()).setRecipients(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationDef)
    */
   public void visit(AeEscalationDef aDef)
   {
      readAttributes(aDef);
      aDef.setName(getAttribute(TAG_NAME));
      if (getParentXmlDef() != null)
         ((AeAbstractDeadlineDef)getParentXmlDef()).addEscalation(aDef);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationProcessDef)
    */
   public void visit(AeEscalationProcessDef aDef)
   {
      readAttributes(aDef);
      aDef.setName(getAttribute(TAG_NAME));
      aDef.setService(getAttribute(TAG_SERVICE));
      if (getParentXmlDef() != null)
         ((AeAbstractDeadlineDef)getParentXmlDef()).addEscalationProcess(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeProcessDataExpressionDef)
    */
   public void visit(AeProcessDataExpressionDef aDef)
   {
      readExpressionDef(aDef);
      ((AeEscalationProcessDef)getParentXmlDef()).setExpressionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeRenderingDef)
    */
   public void visit(AeRenderingDef aDef)
   {
      readAttributes(aDef);
      aDef.setType(getAttributeQName(ATTR_TYPE));
      if (getParentXmlDef() != null)
         ((AeRenderingsDef)getParentXmlDef()).addRendering(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeRenderingsDef)
    */
   public void visit(AeRenderingsDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((IAeRenderingsDefParent)getParentXmlDef()).setRenderings(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      readAttributes(aDef);
      aDef.setName(getAttribute(TAG_NAME));
      if (getParentXmlDef() != null)
         ((IAeTaskDefParent)getParentXmlDef()).addTask(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTasksDef)
    */
   public void visit(AeTasksDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((IAeTasksDefParent)getParentXmlDef()).setTasksDef(aDef);
   }

   public void visit(AeTaskInterfaceDef aDef)
   {
      readAttributes(aDef);
      aDef.setPortType(getAttributeQName(ATTR_PORT_TYPE));
      aDef.setOperation(getAttribute(ATTR_OPERATION));
      aDef.setResponsePortType(getAttributeQName(ATTR_RESPONSE_PORT_TYPE));
      aDef.setResponseOperation(getAttribute(ATTR_RESPONSE_OPERATION));
      if (getParentXmlDef() != null)
         ((AeTaskDef)getParentXmlDef()).setInterface(aDef);
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
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor#visit(org.activebpel.rt.xml.def.AeExtensionAttributeDef)
    */
   public void visit(AeExtensionAttributeDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor#visit(org.activebpel.rt.xml.def.AeDocumentationDef)
    */
   public void visit(AeDocumentationDef aDef)
   {
      readAttributes(aDef);

      // NOTE: The source attribute is ignored as it is not defined for HT

      aDef.setLanguage(getLanguage());
      aDef.setValue(AeXmlUtil.getText(getCurrentElement()));
      if (getParentXmlDef() != null)
         getParentXmlDef().addDocumentationDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeConditionDef)
    */
   public void visit(AeConditionDef aDef)
   {
      readExpressionDef(aDef);
      if (getParentXmlDef() != null)
        ((IAeConditionParentDef)getParentXmlDef()).setConditionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePeopleAssignmentsDef)
    */
   public void visit(AePeopleAssignmentsDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((IAePeopleAssignmentsDefParent)getParentXmlDef()).setPeopleAssignments(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePotentialOwnersDef)
    */
   public void visit(AePotentialOwnersDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((IAePotentialOwnersParent)getParentXmlDef()).setPotentialOwners(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePriorityDef)
    */
   public void visit(AePriorityDef aDef)
   {
      readAttributes(aDef);
      readExpressionDef(aDef);
      if (getParentXmlDef() != null)
         ((IAePriorityDefParent)getParentXmlDef()).setPriority(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskInitiatorDef)
    */
   public void visit(AeTaskInitiatorDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((AePeopleAssignmentsDef)getParentXmlDef()).setTaskInitiator(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskStakeHoldersDef)
    */
   public void visit(AeTaskStakeHoldersDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((AePeopleAssignmentsDef)getParentXmlDef()).setTaskStakeholders(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(AeExcludedOwnersDef)
    */
   public void visit(AeExcludedOwnersDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((AePeopleAssignmentsDef)getParentXmlDef()).setExcludedOwners(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeGroupsDef)
    */
   public void visit(AeGroupsDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((AeOrganizationalEntityDef)getParentXmlDef()).setGroups(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeGroupDef)
    */
   public void visit(AeGroupDef aDef)
   {
      readAttributes(aDef);
      aDef.setValue(AeXmlUtil.getText(getCurrentElement()));
      if (getParentXmlDef() != null)
         ((AeGroupsDef)getParentXmlDef()).addGroup(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeUsersDef)
    */
   public void visit(AeUsersDef aDef)
   {
      readAttributes(aDef);
      if (getParentXmlDef() != null)
         ((AeOrganizationalEntityDef)getParentXmlDef()).setUsers(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeUserDef)
    */
   public void visit(AeUserDef aDef)
   {
      readAttributes(aDef);
      aDef.setValue(AeXmlUtil.getText(getCurrentElement()));
      if (getParentXmlDef() != null)
         ((AeUsersDef)getParentXmlDef()).addUser(aDef);
   }

   /**
    * Visits an expression base def in order to read the expression language and expression value.
    * @param aDef
    */
   protected void readExpressionDef(AeAbstractExpressionDef aDef)
   {
      aDef.setExpressionLanguage(getAttribute(ATTR_EXPRESSION_LANGUAGE));
      aDef.readMixedText(getCurrentElement());
   }

   /**
    * Adds the children of the given Element to the literal def.  Returns true if any children
    * were added to the literal def.
    * 
    * @param aElement
    * @param aLiteralDef
    */
   protected boolean addChildrenToLiteral(Element aElement, AeLiteralDef aLiteralDef)
   {
      List childNodes = getChildrenForLiteral(aElement);
      for (Iterator iter = childNodes.iterator(); iter.hasNext(); )
      {
         Node node = (Node) iter.next();
         aLiteralDef.addChildNode(node);
      }
      return AeUtil.notNullOrEmpty(childNodes);
   }
}