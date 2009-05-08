//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/request/AePARequestUtil.java,v 1.6.4.2 2008/04/14 21:25:30 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.request;

import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.impl.task.data.AeHumanTaskContext;
import org.activebpel.rt.b4p.impl.task.data.AeInitialState;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.AeBpelState;
import org.activebpel.rt.ht.def.AeAbstractGenericHumanRoleDef;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AeLiteralDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This utility class provide convenience methods for the task and notification request builder
 * and their helper visitors
 */
public class AePARequestUtil
{
   /**
    * Returns Organizational Entity as a literal in the from def
    * @param aOrgEntity
    */
   public static AeFromDef wrapOrgEntityAsLiteralInFromDef(Element aOrgEntity)
   {
      AeFromDef fromDef = new AeFromDef();
      AeLiteralDef literal = new AeLiteralDef();
      literal.addChildNode(aOrgEntity);
      fromDef.setLiteral(literal);
      return fromDef;
   }
   
   /**
    * builds initial state
    * @param aData
    */
   public static AeInitialState createInitialState(IAeMessageData aData, String aCreatedBy)
   {
      AeInitialState initialState = new AeInitialState();
      initialState.setInput(aData);
      initialState.setCreatedBy(aCreatedBy);
      return initialState;
   }
   
   /**
    * Builds human task context
    * @param aPAsDef
    * @param aIsSkippable
    */
   public static AeHumanTaskContext createHumanTaskContext(AePeopleAssignmentsDef aPAsDef, boolean aIsSkippable)
   {
      AeHumanTaskContext htContext = new AeHumanTaskContext();
      htContext.setPeopleAssignments(aPAsDef);
      htContext.setSkippable(aIsSkippable);
      return htContext;
   }   
   
   /**
    * Sets organizational entity as an extension element on a generic human role def
    * @param aDef
    * @param aOrgEntity
    */
   public static AeAbstractGenericHumanRoleDef getHumanRoleHTProtocolStyle(AeAbstractGenericHumanRoleDef aDef, Element aOrgEntity)
   {
      if (aOrgEntity == null)
         return null;
      
      AeAbstractGenericHumanRoleDef genericHumanRoleDef = (AeAbstractGenericHumanRoleDef) aDef.clone();
      AeExtensionElementDef extensionElemdef = new AeExtensionElementDef(aOrgEntity);
      genericHumanRoleDef.addExtensionElementDef(extensionElemdef);
      genericHumanRoleDef.setFrom(null);
      return genericHumanRoleDef;
   }

   /**
    * Constructs and returns AeExpressionDef
    * @param aExpression
    * @param aExpressionLanguage
    * @param aNamespace
    */
   public static AeExpressionDef createExpressionDef(String aExpression, String aExpressionLanguage,
         String aNamespace)
   {
      AeExpressionDef def = new AeExpressionDef();
      def.setExpression(aExpression);
      def.setExpressionLanguage(aExpressionLanguage);
      def.setBpelNamespace(aNamespace);
      return def;
   }
   
   /**
    * Utility method to get the state of a people activity, given its state document, path to the state attribute and an
    * NS prefix uri map.
    */
   public static AeBpelState getState(Document aStateDoc, String aStatePath, Map aNSMap)
   {
      String stateStr = null;
      try
      {
         stateStr = (String) AeXPathUtil.selectSingleObject(aStateDoc, aStatePath, aNSMap);
      }
      catch (AeException e)
      {
         AeException.logError(e);
      }

      AeBpelState state = null;
      if (null != stateStr)
      {
         try
         {
            state = AeBpelState.forName(stateStr);
         }
         catch (AeBusinessProcessException e)
         {
            // Do nothing
         }
      }

      return state;
   }
}
