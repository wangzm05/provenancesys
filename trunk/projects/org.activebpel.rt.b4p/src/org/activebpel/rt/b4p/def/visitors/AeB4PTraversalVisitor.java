// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/AeB4PTraversalVisitor.java,v 1.4 2008/02/17 21:36:03 mford Exp $
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
import org.activebpel.rt.ht.def.visitors.AeHtTraversalVisitor;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * A specialized visitor that takes another visitor and a traversal object and calls the appropriate traverse
 * method during each visitation.
 */
public class AeB4PTraversalVisitor extends AeHtTraversalVisitor implements IAeB4PDefVisitor
{

   /**
    * Accepts both traversal object and visitor.
    * @param aTravers - used to traverse the underlying object model
    * @param aVisitor - passed to traverse object for subsequent visitation
    */
   public AeB4PTraversalVisitor(IAeB4PDefTraverser aTravers, IAeBaseXmlDefVisitor aVisitor)
   {
      super(aTravers, aVisitor);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef)
    */
   public void visit(AeB4PPeopleAssignmentsDef aDef)
   {
      ((IAeB4PDefTraverser)getTravers()).traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef)
    */
   public void visit(AeB4PHumanInteractionsDef aDef)
   {
      ((IAeB4PDefTraverser)getTravers()).traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AePeopleActivityDef)
    */
   public void visit(AePeopleActivityDef aDef)
   {
      ((IAeB4PDefTraverser)getTravers()).traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeHtTraversalVisitor#visit(org.activebpel.rt.ht.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      ((IAeB4PDefTraverser)getTravers()).traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeProcessInitiatorDef)
    */
   public void visit(AeProcessInitiatorDef aDef)
   {
      ((IAeB4PDefTraverser)getTravers()).traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeProcessStakeholdersDef)
    */
   public void visit(AeProcessStakeholdersDef aDef)
   {
      ((IAeB4PDefTraverser)getTravers()).traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeLocalTaskDef)
    */
   public void visit(AeLocalTaskDef aDef)
   {
      ((IAeB4PDefTraverser)getTravers()).traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
    */
   public void visit(AeB4PLocalNotificationDef aDef)
   {
      ((IAeB4PDefTraverser)getTravers()).traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeScheduledActionsDef)
    */
   public void visit(AeScheduledActionsDef aDef)
   {
      ((IAeB4PDefTraverser)getTravers()).traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeAttachmentPropagationDef)
    */
   public void visit(AeAttachmentPropagationDef aDef)
   {
      ((IAeB4PDefTraverser)getTravers()).traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeDeferActivationDef)
    */
   public void visit(AeDeferActivationDef aDef)
   {
      ((IAeB4PDefTraverser)getTravers()).traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeExpirationDef)
    */
   public void visit(AeExpirationDef aDef)
   {
      ((IAeB4PDefTraverser)getTravers()).traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PForDef)
    */
   public void visit(AeB4PForDef aDef)
   {
      ((IAeB4PDefTraverser)getTravers()).traverse(aDef, getVisitor());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PUntilDef)
    */
   public void visit(AeB4PUntilDef aDef)
   {
      ((IAeB4PDefTraverser)getTravers()).traverse(aDef, getVisitor());
   }
}
