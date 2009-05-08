// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/io/writers/AeB4PDelegatingDefWriterVisitor.java,v 1.5 2008/02/17 21:36:03 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.io.writers;

import org.activebpel.rt.b4p.def.AeAttachmentPropagationDef;
import org.activebpel.rt.b4p.def.AeB4PForDef;
import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef;
import org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef;
import org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef;
import org.activebpel.rt.b4p.def.AeB4PUntilDef;
import org.activebpel.rt.b4p.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.b4p.def.AeDeferActivationDef;
import org.activebpel.rt.b4p.def.AeExpirationDef;
import org.activebpel.rt.b4p.def.AeLocalTaskDef;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.b4p.def.AeProcessInitiatorDef;
import org.activebpel.rt.b4p.def.AeProcessStakeholdersDef;
import org.activebpel.rt.b4p.def.AeScheduledActionsDef;
import org.activebpel.rt.b4p.def.visitors.AeB4PDefTraverser;
import org.activebpel.rt.b4p.def.visitors.AeB4PTraversalVisitor;
import org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor;
import org.activebpel.rt.ht.def.io.writers.AeHtDelegatingDefWriterVisitor;
import org.activebpel.rt.ht.def.visitors.AeHtTraversalVisitor;
import org.activebpel.rt.xml.def.io.IAeDefRegistry;

/**
 * Work horse class for traversing and serializing the BPEL4People Def object model.
 */
public class AeB4PDelegatingDefWriterVisitor extends AeHtDelegatingDefWriterVisitor implements IAeB4PDefVisitor
{
   /**
    * Constructor.
    * @param aRegistry to retrieve IAeHtDefWriter impls
    */
   public AeB4PDelegatingDefWriterVisitor(IAeDefRegistry aRegistry)
   {
      super(aRegistry);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractTraversingHtDefVisitor#createTraversalVisitor()
    */
   protected AeHtTraversalVisitor createTraversalVisitor()
   {
      return new AeB4PTraversalVisitor(new AeB4PDefTraverser(), this);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef)
    */
   public void visit(AeB4PHumanInteractionsDef aDef)
   {
      traverse(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AePeopleActivityDef)
    */
   public void visit(AePeopleActivityDef aDef)
   {
      traverse(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      traverse(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeProcessInitiatorDef)
    */
   public void visit(AeProcessInitiatorDef aDef)
   {
      traverse(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeProcessStakeholdersDef)
    */
   public void visit(AeProcessStakeholdersDef aDef)
   {
      traverse(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef)
    */
   public void visit(AeB4PPeopleAssignmentsDef aDef)
   {
      traverse(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeLocalTaskDef)
    */
   public void visit(AeLocalTaskDef aDef)
   {
      traverse(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
    */
   public void visit(AeB4PLocalNotificationDef aDef)
   {
      traverse(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeScheduledActionsDef)
    */
   public void visit(AeScheduledActionsDef aDef)
   {
      traverse(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeAttachmentPropagationDef)
    */
   public void visit(AeAttachmentPropagationDef aDef)
   {
      traverse(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeDeferActivationDef)
    */
   public void visit(AeDeferActivationDef aDef)
   {
      traverse(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeExpirationDef)
    */
   public void visit(AeExpirationDef aDef)
   {
      traverse(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PForDef)
    */
   public void visit(AeB4PForDef aDef)
   {
      traverse(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PUntilDef)
    */
   public void visit(AeB4PUntilDef aDef)
   {
      traverse(aDef);
   }
}
