//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/io/writers/def/AeHtDefWriterVisitor.java,v 1.11 2008/02/17 21:51:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def.io.writers.def;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.activebpel.rt.ht.def.AeAbstractExpressionDef;
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
import org.activebpel.rt.ht.def.AeTextNodeDef;
import org.activebpel.rt.ht.def.AeToPartDef;
import org.activebpel.rt.ht.def.AeToPartsDef;
import org.activebpel.rt.ht.def.AeUntilDef;
import org.activebpel.rt.ht.def.AeUserDef;
import org.activebpel.rt.ht.def.AeUsersDef;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.ht.def.IAeMixedContentElement;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeDocumentationDef;
import org.activebpel.rt.xml.def.AeExtensionAttributeDef;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.activebpel.rt.xml.def.IAeBaseXmlDefConstants;
import org.activebpel.rt.xml.def.io.writers.AeAbstractDefWriter;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * Impl. of the def visitor that serializes the WS-HT def model to a DOM.
 */
public class AeHtDefWriterVisitor extends AeAbstractDefWriter implements IAeHtDefVisitor, IAeHtDefConstants,
      IAeBaseXmlDefConstants
{
   /** mapping of namespaces to preferred prefixes */
   private static final Map sPreferredPrefixes = Collections.singletonMap(IAeHtDefConstants.DEFAULT_HT_NS, "wsht"); //$NON-NLS-1$

   /**
    * Constructor for wsht writer visitor
    * @param aDef
    * @param aParentElement
    * @param aNamespace
    * @param aTagName
    */
   public AeHtDefWriterVisitor(AeBaseXmlDef aDef, Element aParentElement, String aNamespace, String aTagName)
   {
      super(aDef, aParentElement, aNamespace, aTagName, sPreferredPrefixes);
   }

   /**
    * @see org.activebpel.rt.xml.def.io.writers.IAeDefWriter#createElement(org.activebpel.rt.xml.def.AeBaseXmlDef,
    *      org.w3c.dom.Element)
    */
   public Element createElement(AeBaseXmlDef aBaseDef, Element aParentElement)
   {
      aBaseDef.accept(this);
      return getElement();
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeHumanInteractionsDef)
    */
   public void visit(AeHumanInteractionsDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(ATTR_TARGET_NAMESPACE, aDef.getTargetNamespace());
      setAttribute(ATTR_QUERY_LANGUAGE, aDef.getQueryLanguage());
      setAttribute(ATTR_EXPRESSION_LANGUAGE, aDef.getExpressionLanguage());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeArgumentDef)
    */
   public void visit(AeArgumentDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(TAG_NAME, aDef.getName());
      writeExpressionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeCompletionDeadlineDef)
    */
   public void visit(AeCompletionDeadlineDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeUntilDef)
    */
   public void visit(AeUntilDef aDef)
   {
      writeStandardAttributes(aDef);
      writeExpressionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDeadlinesDef)
    */
   public void visit(AeDeadlinesDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDelegationDef)
    */
   public void visit(AeDelegationDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(ATTR_POTENTIAL_DELEGATEES, aDef.getPotentialDelegatees());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDescriptionDef)
    */
   public void visit(AeDescriptionDef aDef)
   {
      writeStandardAttributes(aDef);
      setLanguage(aDef.getLanguage());
      setAttribute(ATTR_CONTENT_TYPE, aDef.getContentType());
      writeMixedTextDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef)
    */
   public void visit(AeLogicalPeopleGroupsDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef)
    */
   public void visit(AeLogicalPeopleGroupDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(TAG_NAME, aDef.getName());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(TAG_LOGICAL_PEOPLE_GROUP, aDef.getLogicalPeopleGroup());
      setAttribute(ATTR_EXPRESSION_LANGUAGE, aDef.getExpressionLanguage());
      writeMixedTextDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeImportDef)
    */
   public void visit(AeImportDef aDef)
   {
      writeStandardAttributes(aDef);

      setAttribute(ATTR_NAMESPACE, aDef.getNamespace());
      setAttribute(ATTR_LOCATION, aDef.getLocation());
      setAttribute(ATTR_IMPORT_TYPE, aDef.getImportType());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeExtensionsDef)
    */
   public void visit(AeExtensionsDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeExtensionDef)
    */
   public void visit(AeExtensionDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(ATTR_NAMESPACE, aDef.getNamespace());
      setAttribute(ATTR_MUST_UNDERSTAND, booleanToString(aDef.isMustUnderstand()));
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLiteralDef)
    */
   public void visit(AeLiteralDef aDef)
   {
      writeStandardAttributes(aDef);

      for (Iterator iter = aDef.getChildNodes().iterator(); iter.hasNext(); )
      {
         Node node = (Node) iter.next();
         Node importedNode = getElement().getOwnerDocument().importNode(node, true);
         getElement().appendChild(importedNode);
         if (importedNode.getNodeType() == Node.ELEMENT_NODE)
            AeXmlUtil.removeDuplicateNSDecls((Element) importedNode);
      }
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartsDef)
    */
   public void visit(AeToPartsDef aDef)
   {
      writeStandardAttributes(aDef);

   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartDef)
    */
   public void visit(AeToPartDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(TAG_NAME, aDef.getName());
      setAttribute(ATTR_EXPRESSION_LANGUAGE, aDef.getExpressionLanguage());
      writeMixedTextDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationElementsDef)
    */
   public void visit(AePresentationElementsDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationParameterDef)
    */
   public void visit(AePresentationParameterDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(TAG_NAME, aDef.getName());
      setAttribute(ATTR_TYPE, aDef.getType());
      writeMixedTextDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationParametersDef)
    */
   public void visit(AePresentationParametersDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(ATTR_EXPRESSION_LANGUAGE, aDef.getExpressionLanguage());
   }

  

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLocalNotificationDef)
    */
   public void visit(AeLocalNotificationDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(ATTR_REFERENCE, aDef.getReference());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationsDef)
    */
   public void visit(AeNotificationsDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(TAG_NAME, aDef.getName());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationInterfaceDef)
    */
   public void visit(AeNotificationInterfaceDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(ATTR_PORT_TYPE, aDef.getPortType());
      setAttribute(ATTR_OPERATION, aDef.getOperation());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeReassignmentDef)
    */
   public void visit(AeReassignmentDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeRecipientsDef)
    */
   public void visit(AeRecipientsDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationDef)
    */
   public void visit(AeEscalationDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(TAG_NAME, aDef.getName());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationProcessDef)
    */
   public void visit(AeEscalationProcessDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(TAG_NAME, aDef.getName());
      setAttribute(TAG_SERVICE, aDef.getService());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeProcessDataExpressionDef)
    */
   public void visit(AeProcessDataExpressionDef aDef)
   {
      writeStandardAttributes(aDef);
      writeExpressionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeRenderingDef)
    */
   public void visit(AeRenderingDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(ATTR_TYPE, aDef.getType());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeRenderingsDef)
    */
   public void visit(AeRenderingsDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(TAG_NAME, aDef.getName());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTasksDef)
    */
   public void visit(AeTasksDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskInterfaceDef)
    */
   public void visit(AeTaskInterfaceDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(ATTR_PORT_TYPE, aDef.getPortType());
      setAttribute(ATTR_OPERATION, aDef.getOperation());
      setAttribute(ATTR_RESPONSE_PORT_TYPE, aDef.getResponsePortType());
      setAttribute(ATTR_RESPONSE_OPERATION, aDef.getResponseOperation());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeConditionDef)
    */
   public void visit(AeConditionDef aDef)
   {
      writeStandardAttributes(aDef);
      writeExpressionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePotentialOwnersDef)
    */
   public void visit(AePotentialOwnersDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskInitiatorDef)
    */
   public void visit(AeTaskInitiatorDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskStakeHoldersDef)
    */
   public void visit(AeTaskStakeHoldersDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeExcludedOwnersDef)
    */
   public void visit(AeExcludedOwnersDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeForDef)
    */
   public void visit(AeForDef aDef)
   {
      writeStandardAttributes(aDef);
      writeExpressionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNameDef)
    */
   public void visit(AeNameDef aDef)
   {
      writeStandardAttributes(aDef);
      setLanguage(aDef.getLanguage());
      writeMixedTextDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeParameterDef)
    */
   public void visit(AeParameterDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(TAG_NAME, aDef.getName());
      setAttribute(ATTR_TYPE, aDef.getType());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeOrganizationalEntityDef)
    */
   public void visit(AeOrganizationalEntityDef aDef)
   {
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeOutcomeDef)
    */
   public void visit(AeOutcomeDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(ATTR_PART, aDef.getPart());
      setAttribute(ATTR_QUERY_LANGUAGE, aDef.getQueryLanguage());
      writeMixedTextDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeSubjectDef)
    */
   public void visit(AeSubjectDef aDef)
   {
      writeStandardAttributes(aDef);
      setLanguage(aDef.getLanguage());
      writeMixedTextDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeSearchByDef)
    */
   public void visit(AeSearchByDef aDef)
   {
      writeStandardAttributes(aDef);
      writeExpressionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeStartDeadlineDef)
    */
   public void visit(AeStartDeadlineDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePeopleAssignmentsDef)
    */
   public void visit(AePeopleAssignmentsDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AePriorityDef)
    */
   public void visit(AePriorityDef aDef)
   {
      writeStandardAttributes(aDef);
      writeExpressionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeGroupDef)
    */
   public void visit(AeGroupDef aDef)
   {
      writeStandardAttributes(aDef);
      writeText(aDef.getValue());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeGroupsDef)
    */
   public void visit(AeGroupsDef aDef)
   {
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeUserDef)
    */
   public void visit(AeUserDef aDef)
   {
      writeStandardAttributes(aDef);
      writeText(aDef.getValue());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeUsersDef)
    */
   public void visit(AeUsersDef aDef)
   {
   }

   /**
    * @see org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor#visit(org.activebpel.rt.xml.def.AeExtensionAttributeDef)
    */
   public void visit(AeExtensionAttributeDef aDef)
   {
      // Note: the extension attribute def is skipped in the registry
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor#visit(org.activebpel.rt.xml.def.AeExtensionElementDef)
    */
   public void visit(AeExtensionElementDef aDef)
   {
      // Note: a special writer is used to write out the extension element def.
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor#visit(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   public void visit(AeBaseXmlDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor#visit(org.activebpel.rt.xml.def.AeDocumentationDef)
    */
   public void visit(AeDocumentationDef aDef)
   {
      writeStandardAttributes(aDef);

      setLanguage(aDef.getLanguage());
      writeText(aDef.getValue());
   }

   /**
    * Visits an expression base def in order to write out the expressionLanguage attribute and the value of
    * the expression.
    * @param aDef
    */
   protected void writeExpressionDef(AeAbstractExpressionDef aDef)
   {
      setAttribute(ATTR_EXPRESSION_LANGUAGE, aDef.getExpressionLanguage());
      writeMixedTextDef(aDef);
   }

   /**
    * Serializes mixed content
    * @param aDef
    */
   protected void writeMixedTextDef(IAeMixedContentElement aDef)
   {
      for (Iterator itr = aDef.getMixedTextDef(); itr.hasNext();)
      {
         AeTextNodeDef node = (AeTextNodeDef)itr.next();
         if ( node.isCData() )
         {
            CDATASection cdataNode = getElement().getOwnerDocument().createCDATASection(node.getValue());
            getElement().appendChild(cdataNode);
         }
         else
         {
            Text textNode = getElement().getOwnerDocument().createTextNode(node.getValue());
            getElement().appendChild(textNode);
         }
      }
   }
}
