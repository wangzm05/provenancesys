// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/AeAbstractTraversingB4PDefVisitor.java,v 1.4 2007/11/16 02:43:14 jbik Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.def.visitors;

import org.activebpel.rt.b4p.def.AeAttachmentPropagationDef;
import org.activebpel.rt.b4p.def.AeB4PBaseDef;
import org.activebpel.rt.b4p.def.AeB4PForDef;
import org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef;
import org.activebpel.rt.b4p.def.AeB4PUntilDef;
import org.activebpel.rt.b4p.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef;
import org.activebpel.rt.b4p.def.AeDeferActivationDef;
import org.activebpel.rt.b4p.def.AeExpirationDef;
import org.activebpel.rt.b4p.def.AeLocalTaskDef;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef;
import org.activebpel.rt.b4p.def.AeProcessInitiatorDef;
import org.activebpel.rt.b4p.def.AeProcessStakeholdersDef;
import org.activebpel.rt.b4p.def.AeScheduledActionsDef;
import org.activebpel.rt.ht.def.visitors.AeAbstractTraversingHtDefVisitor;
import org.activebpel.rt.ht.def.visitors.AeHtTraversalVisitor;

/**
 * A base class for traversing B4P def visitors.
 */
public abstract class AeAbstractTraversingB4PDefVisitor extends AeAbstractTraversingHtDefVisitor implements IAeB4PDefVisitor
{
   /**
    * Default c'tor.
    */
   public AeAbstractTraversingB4PDefVisitor()
   {
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractTraversingHtDefVisitor#createTraversalVisitor()
    */
   protected AeHtTraversalVisitor createTraversalVisitor()
   {
      return new AeB4PTraversalVisitor(new AeB4PDefTraverser(), this);
   }

   /**
    * Called by the visit methods.
    * @param aB4PBaseDef
    */
   protected void visitB4PBaseDef(AeB4PBaseDef aB4PBaseDef)
   {
      // no-op - useful for subclasses that want to do a common behavior
      // for all B4P defs.
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef)
    */
   public void visit(AeB4PHumanInteractionsDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitB4PBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AePeopleActivityDef)
    */
   public void visit(AePeopleActivityDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitB4PBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitB4PBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeProcessInitiatorDef)
    */
   public void visit(AeProcessInitiatorDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitB4PBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeProcessStakeholdersDef)
    */
   public void visit(AeProcessStakeholdersDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitB4PBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef)
    */
   public void visit(AeB4PPeopleAssignmentsDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitB4PBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeLocalTaskDef)
    */
   public void visit(AeLocalTaskDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitB4PBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
    */
   public void visit(AeB4PLocalNotificationDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitB4PBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeScheduledActionsDef)
    */
   public void visit(AeScheduledActionsDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitB4PBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeAttachmentPropagationDef)
    */
   public void visit(AeAttachmentPropagationDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitB4PBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeDeferActivationDef)
    */
   public void visit(AeDeferActivationDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitB4PBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeExpirationDef)
    */
   public void visit(AeExpirationDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitB4PBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PForDef)
    */
   public void visit(AeB4PForDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitB4PBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PUntilDef)
    */
   public void visit(AeB4PUntilDef aDef)
   {
      visitBaseXmlDef(aDef);
      visitB4PBaseDef(aDef);
   }
}
