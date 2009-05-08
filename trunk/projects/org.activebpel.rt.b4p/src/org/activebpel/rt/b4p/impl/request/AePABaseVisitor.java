//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/request/AePABaseVisitor.java,v 1.3 2008/02/29 18:42:28 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.request;

import java.util.Iterator;
import java.util.Stack;

import org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef;
import org.activebpel.rt.b4p.def.AeLocalTaskDef;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.b4p.def.AeScheduledActionsDef;
import org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext;
import org.activebpel.rt.ht.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.ht.def.AeCompletionDeadlineDef;
import org.activebpel.rt.ht.def.AeDeadlinesDef;
import org.activebpel.rt.ht.def.AeEscalationDef;
import org.activebpel.rt.ht.def.AeExcludedOwnersDef;
import org.activebpel.rt.ht.def.AeHtBaseDef;
import org.activebpel.rt.ht.def.AeLocalNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.ht.def.AePotentialOwnersDef;
import org.activebpel.rt.ht.def.AeReassignmentDef;
import org.activebpel.rt.ht.def.AeRecipientsDef;
import org.activebpel.rt.ht.def.AeStartDeadlineDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTaskInitiatorDef;
import org.activebpel.rt.ht.def.AeTaskStakeHoldersDef;
import org.activebpel.rt.ht.def.visitors.AeHtDefInlineTaskTraverser;
import org.activebpel.rt.ht.def.visitors.AeHtTraversalVisitor;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.visitors.AeDefAssignParentVisitor;

/**
 * Visitor to traverse AeProcessDef and collect all LPG Defs, execute them and produces a map
 */
public class AePABaseVisitor extends AeAbstractB4PDefVisitor
{
   /** activity life cycle context */
   private IAeActivityLifeCycleContext mContext;
   /** Stack to maintain the location path of scope */
   protected Stack mStack = new Stack();

   /**
    * Default C'tor
    */
   public AePABaseVisitor()
   {
      
   }

   /**
    * C'tor
    * @param aContext
    */
   public AePABaseVisitor(IAeActivityLifeCycleContext aContext)
   {
      setContext(aContext);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePeopleAssignmentsDef)
    */
   public void visit(AePeopleAssignmentsDef aDef)
   {
      mStack.push(aDef);
      callAccept(aDef.getPotentialOwners());
      callAccept(aDef.getBusinessAdministrators());
      callAccept(aDef.getTaskInitiator());
      callAccept(aDef.getTaskStakeholders());
      callAccept(aDef.getExcludedOwners());
      callAccept(aDef.getRecipients());
      mStack.pop();
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeRecipientsDef)
    */
   public void visit(AeRecipientsDef aDef)
   {
      callAccept(aDef.getFrom());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      callAccept(aDef.getFrom());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeExcludedOwnersDef)
    */
   public void visit(AeExcludedOwnersDef aDef)
   {
      callAccept(aDef.getFrom());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePotentialOwnersDef)
    */
   public void visit(AePotentialOwnersDef aDef)
   {
      callAccept(aDef.getFrom());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskInitiatorDef)
    */
   public void visit(AeTaskInitiatorDef aDef)
   {
      callAccept(aDef.getFrom());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskStakeHoldersDef)
    */
   public void visit(AeTaskStakeHoldersDef aDef)
   {
      callAccept(aDef.getFrom());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AePeopleActivityDef)
    */
   public void visit(AePeopleActivityDef aDef)
   {
      callAccept(aDef.getTask());
      callAccept(aDef.getLocalTask());
      callAccept(aDef.getNotification());
      callAccept(aDef.getLocalNotification());
      callAccept(aDef.getScheduledActions());
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeScheduledActionsDef)
    */
   public void visit(AeScheduledActionsDef aDef)
   {
      callAccept(aDef.getDeferActivation());
      callAccept(aDef.getExpiration());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationDef)
    */
   public void visit(AeEscalationDef aDef)
   {
      callAccept(aDef.getNotification());
      callAccept(aDef.getLocalNotification());
      callAccept(aDef.getReassignment());
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeReassignmentDef)
    */
   public void visit(AeReassignmentDef aDef)
   {
      mStack.push(aDef);
      callAccept(aDef.getPotentialOwners());
      mStack.pop();
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      callAccept(aDef.getPeopleAssignments());
      callAccept(aDef.getPriority());
      // look for escalations in task 
      AeDeadlinesDef dDef = aDef.getDeadlines();
      if (dDef != null)
      {
         callAccept(dDef.getStartDeadlineDefs());
         callAccept(dDef.getCompletionDeadlineDefs());
      }
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDeadlinesDef)
    */
   public void visit(AeDeadlinesDef aDef)
   {
      if (aDef != null)
      {
         callAccept(aDef.getStartDeadlineDefs());
         callAccept(aDef.getCompletionDeadlineDefs());
      }
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeCompletionDeadlineDef)
    */
   public void visit(AeCompletionDeadlineDef aDef)
   {
      callAccept(aDef.getEscalationDefs());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeStartDeadlineDef)
    */
   public void visit(AeStartDeadlineDef aDef)
   {
      callAccept(aDef.getEscalationDefs());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeLocalTaskDef)
    */
   public void visit(AeLocalTaskDef aDef)
   {
      callAccept(aDef.getPeopleAssignments());
      callAccept(aDef.getInlineTaskDef());
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      callAccept(aDef.getPeopleAssignments());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLocalNotificationDef)
    */
   public void visit(AeLocalNotificationDef aDef)
   {
      callAccept(aDef.getPeopleAssignments());
      callAccept(aDef.getInlineNotificationDef());
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
    */
   public void visit(AeB4PLocalNotificationDef aDef)
   {
      callAccept(aDef.getPeopleAssignments());
      callAccept(aDef.getInlineNotificationDef());
   }

   /**
    * Visits def assign parent visitor to parent htbase def hierarchy
    * @param aDef
    */
   protected void parentHtDef(AeHtBaseDef aDef)
   {
      AeDefAssignParentVisitor parentVisitor = new AeDefAssignParentVisitor();
      parentVisitor.setTraversalVisitor( new AeHtTraversalVisitor(new AeHtDefInlineTaskTraverser(), parentVisitor));
      aDef.accept(parentVisitor);
   }
   
   /**
    * @return the context
    */
   public IAeActivityLifeCycleContext getContext()
   {
      return mContext;
   }

   /**
    * @param aContext the context to set
    */
   public void setContext(IAeActivityLifeCycleContext aContext)
   {
      mContext = aContext;
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

}
