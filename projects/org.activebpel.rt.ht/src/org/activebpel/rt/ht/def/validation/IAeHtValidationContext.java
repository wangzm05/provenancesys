// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/IAeHtValidationContext.java,v 1.5 2008/02/17 21:51:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation;


import java.util.List;

import javax.wsdl.Message;
import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.expr.validation.IAeExpressionValidationResult;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeQueryDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.IAeHtExpressionDef;
import org.activebpel.rt.ht.def.IAeInterfaceDef;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.wsresource.validation.IAeWSResourceValidationContext;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * Interface defining the Human Interactions validation context.
 */
public interface IAeHtValidationContext extends IAeWSResourceValidationContext
{
   /**
    * The traversal visitor to be used for rules validation.
    * @param aRule
    */
   public IAeBaseXmlDefVisitor createRuleTraverser(IAeHtDefVisitor aRule);
   
   /**
    * Find the logical people group with the given name
    * 
    * @param aDef
    * @param aLPGName
    * @return the found LPG, otherwise null
    */
   public AeLogicalPeopleGroupDef findLogicalPeopleGroup(AeBaseXmlDef aDef, QName aLPGName);
   
   /**
    * Returns a list of parameterdefs defined for a logical people group, if none defined
    * then an empty list is returned.
    * 
    * @param aDef a Base Xml def used to find an LPG within scope.
    * @param aLPGName the logical people group QName
    * 
    * @return a List of parameters for the logical people group.
    */
   public List getLPGParameters(AeBaseXmlDef aDef, QName aLPGName);
   
   /**
    * Finds the notification with the given name
    * 
    * @param aContext
    * @param aNotificationName
    */
   public AeNotificationDef findNotification(AeBaseXmlDef aContext, QName aNotificationName);
   
 
   /**
    * Finds the task with the given name.
    * 
    * @param aContext
    * @param aTaskName
    */
   public AeTaskDef findTask(AeBaseXmlDef aContext, QName aTaskName);
   
   /**
    * This method resolves the portType and operation name from one of the supplied 
    * <code>AeBaseXmlDef</code> objects parents and then returns a WSDL message.
    * 
    * For example: if an <code>AePartDef</code> is supplied this method will resolve the parent
    * <code>AeEscalationDef</code> and retrieve the portType and operation name from the 
    * <code>AeNotificationInterfaceDef</code> and then find the <code>Operation</code> in
    * order to retrieve the output message.
    * 
    * An AeException is thrown if portType or operation can not be resolved.
    * 
    * @param aDef
    * @return a WSDL Message object
    */
   public Message getOutputMessage(AeBaseXmlDef aDef) throws AeException;
   
   /**
    * This method resolves the portType and operation name from one of the supplied 
    * <code>AeBaseXmlDef</code> objects parents and then returns a WSDL message.
    * 
    * For example: if an <code>AeOutcomeDef</code> is supplied this method will resolve the parent
    * <code>AeTaskDef</code> and retrieve the portType and operation name from the 
    * <code>AeTaskInterfaceDef</code> and then find the <code>Operation</code> in
    * order to retrieve the input message.
    * 
    * An AeException is thrown if portType or operation can not be resolved.
    * 
    * @param aDef
    * @return a WSDL Message object
    */
   public Message getInputMessage(AeBaseXmlDef aDef) throws AeException;
   
   /**
    * Validate the XPath Query
    *   
    * @param aQuery
    */
   public IAeExpressionValidationResult validateQuery(AeQueryDef aQuery) throws Exception;
 
   /**
    * Walk up the parent chain of the AeBaseXmlDef object to find a parent of an 'interface' element.
    * 
    * @param aContextDef
    */
   public IAeInterfaceDef findInScopeInterface(AeBaseXmlDef aContextDef);
   
   /**
    * validate the expression/xpath
    * 
    * @param aContextDef an AeBaseXmlDef used for setting the expression validation context
    * @param aExpression - the expression to be validated
    * @param aExpressionLanguage - the expression language
    * @throws Exception
    */
   public IAeExpressionValidationResult validateExpression(AeBaseXmlDef aContextDef, 
                                    String aExpression, 
                                    String aExpressionLanguage) throws Exception;
   
   /**
    * Find the expression language for the supplied def.  If the def doesn't specify
    * a expression language, pull it from the next exclosing def that does or use the 
    * default.
    * 
    * @param aDef
    */
   public String getExpressionLanguage(IAeHtExpressionDef aDef);
   
   
   /**
    * Get the namespace URI defined for the given namespace prefix.
    * @param aPrefix
    * @return empty String is return if the lookup fails.
    */
   public String getNamespaceURI(String aPrefix);
 }
