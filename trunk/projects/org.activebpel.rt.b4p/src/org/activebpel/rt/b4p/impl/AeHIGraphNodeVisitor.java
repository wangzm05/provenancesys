//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/AeHIGraphNodeVisitor.java,v 1.2 2008/03/14 20:46:52 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl;

import java.util.Iterator;
import java.util.Stack;

import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef;
import org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor;
import org.activebpel.rt.ht.def.AeCompletionDeadlineDef;
import org.activebpel.rt.ht.def.AeDeadlinesDef;
import org.activebpel.rt.ht.def.AeEscalationDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationsDef;
import org.activebpel.rt.ht.def.AeStartDeadlineDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTasksDef;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.graph.AeXmlDefGraphNode;
import org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNode;

/**
 * Visitor to traverse the human interactions def to build graph node tree 
 */
public class AeHIGraphNodeVisitor extends AeAbstractB4PDefVisitor
{

   /** stack to hold parent graph node */
   private Stack mStack = new Stack();
   /** HI Graph node */
   private IAeXmlDefGraphNode mHIGraphNode;

   /**
    * Creates graph node tree for human interactions def
    * @param aDef
    */
   public IAeXmlDefGraphNode createXmlGraphNode(AeB4PHumanInteractionsDef aDef)
   {
      callAccept(aDef);
      return getHIGraphNode();
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeHumanInteractionsDef)
    */
   public void visit(AeB4PHumanInteractionsDef aDef)
   {
      mHIGraphNode = new AeXmlDefGraphNode(IAeHumanInteractionDisplayTags.HI_TAG, aDef, IAeHumanInteractionDisplayTags.HI_DISPLAY_NAME, IAeHumanInteractionDisplayTags.HI_ICON, true);
      mStack.push(mHIGraphNode);
      callAccept(aDef.getLogicalPeopleGroupsDef());
      callAccept(aDef.getTasksDef());
      callAccept(aDef.getNotificationsDef());
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef)
    */
   public void visit(AeLogicalPeopleGroupsDef aDef)
   {
      IAeXmlDefGraphNode node = new AeXmlDefGraphNode(IAeHumanInteractionDisplayTags.LPGS_TAG, aDef, IAeHumanInteractionDisplayTags.LPGS_DISPLAY_NAME, IAeHumanInteractionDisplayTags.LPGS_ICON, true); 
      addToParent(node, true);
      callAccept(aDef.getLogicalPeopleGroupDefs());
      mStack.pop();
   }   
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef)
    */
   public void visit(AeLogicalPeopleGroupDef aDef)
   {
      IAeXmlDefGraphNode node = new AeXmlDefGraphNode(aDef.getName(), aDef, aDef.getName(), IAeHumanInteractionDisplayTags.LPG_ICON, true);
      addToParent(node, false);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTasksDef)
    */
   public void visit(AeTasksDef aDef)
   {
      IAeXmlDefGraphNode node = new AeXmlDefGraphNode(IAeHumanInteractionDisplayTags.TASKS_TAG, aDef, IAeHumanInteractionDisplayTags.TASKS_DISPLAY_NAME, IAeHumanInteractionDisplayTags.TASKS_ICON, true); 
      addToParent(node, true);
      callAccept(aDef.getTaskDefs());
      mStack.pop();
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      IAeXmlDefGraphNode node = new AeXmlDefGraphNode(aDef.getName(), aDef, aDef.getName(), IAeHumanInteractionDisplayTags.TASK_ICON, true);
      addToParent(node, true);
      // fixme (P) Uncomment below code when we decide to traverse deadlines 
      //callAccept(aDef.getDeadlines());
      mStack.pop();
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDeadlinesDef)
    */
   public void visit(AeDeadlinesDef aDef)
   {
      IAeXmlDefGraphNode node = new AeXmlDefGraphNode(IAeHumanInteractionDisplayTags.DEADLINES_TAG, aDef, null, IAeHumanInteractionDisplayTags.DEADLINES_ICON, true);
      addToParent(node, true);
      callAccept(aDef.getStartDeadlineDefs());
      callAccept(aDef.getCompletionDeadlineDefs());
      mStack.pop();
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeStartDeadlineDef)
    */
   public void visit(AeStartDeadlineDef aDef)
   {
      IAeXmlDefGraphNode node = new AeXmlDefGraphNode(IAeHumanInteractionDisplayTags.START_DEADLINE_TAG, aDef, null, IAeHumanInteractionDisplayTags.START_DEADLINE_ICON, true);
      addToParent(node, true);
      callAccept(aDef.getEscalationDefs());
      mStack.pop();

   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationDef)
    */
   public void visit(AeEscalationDef aDef)
   {
      IAeXmlDefGraphNode node = new AeXmlDefGraphNode(aDef.getName(), aDef, null, IAeHumanInteractionDisplayTags.ESCALATION_ICON, true);
      addToParent(node, false);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeCompletionDeadlineDef)
    */
   public void visit(AeCompletionDeadlineDef aDef)
   {
      IAeXmlDefGraphNode node = new AeXmlDefGraphNode(IAeHumanInteractionDisplayTags.COMPLETTION_DEADLINE_TAG, aDef, null, IAeHumanInteractionDisplayTags.COMPLETTION_DEADLINE_ICON, true);
      addToParent(node, true);
      callAccept(aDef.getEscalationDefs());
      mStack.pop();
   }

   /**
    * Overrides method to 
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationsDef)
    */
   public void visit(AeNotificationsDef aDef)
   {
      IAeXmlDefGraphNode node = new AeXmlDefGraphNode(IAeHumanInteractionDisplayTags.NOTIFICATIONS_TAG, aDef, IAeHumanInteractionDisplayTags.NOTIFICATIONS_DISPLAY_NAME, IAeHumanInteractionDisplayTags.NOTIFICATIONS_ICON, true); 
      addToParent(node, true);
      callAccept(aDef.getNotificationDefs());
      mStack.pop();
   }

 
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      IAeXmlDefGraphNode node = new AeXmlDefGraphNode(aDef.getName(), aDef, aDef.getName(), IAeHumanInteractionDisplayTags.NOTIFICATION_ICON, true);
      addToParent(node, false);
   }

   /**
    * Adds aNode as a child to the node that is on top of stack
    * @param aNode
    * @param aIsParent
    */
   private void addToParent(IAeXmlDefGraphNode aNode, boolean aIsParent)
   {
      ((IAeXmlDefGraphNode) mStack.peek()).addChild(aNode);
      if (aIsParent)
         mStack.push(aNode);
   }

   
   /**
    * calls accept on the def when not null
    * @param aDef
    */
   protected void callAccept(AeBaseXmlDef aDef)
   {
      if (aDef != null)
         aDef.accept(this);
   }
   
   /**
    * Calls <code>accept</code> on each of the definition objects in the Iterator
    * @param aIterator
    */
   protected void callAccept(Iterator aIterator)
   {
      while (aIterator.hasNext())
      {
         AeBaseXmlDef def = (AeBaseXmlDef)aIterator.next();
         callAccept(def);
      }
   }

   /**
    * @return the hIGraphNode
    */
   protected IAeXmlDefGraphNode getHIGraphNode()
   {
      return mHIGraphNode;
   }

   /**
    * @param aGraphNode the hIGraphNode to set
    */
   protected void setHIGraphNode(IAeXmlDefGraphNode aGraphNode)
   {
      mHIGraphNode = aGraphNode;
   }


}
