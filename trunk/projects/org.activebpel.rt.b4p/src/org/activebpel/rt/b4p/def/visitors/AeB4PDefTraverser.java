// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/AeB4PDefTraverser.java,v 1.8 2008/03/14 20:46:52 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
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
import org.activebpel.rt.b4p.def.AeReferenceOverrideElementsDef;
import org.activebpel.rt.b4p.def.AeScheduledActionsDef;
import org.activebpel.rt.ht.def.visitors.AeHtDefTraverser;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * The external iterator for the IAeB4PDefVisitor interface. Provides the logic for traversing the BPEL4People
 * definition objects.
 */
public class AeB4PDefTraverser extends AeHtDefTraverser implements IAeB4PDefTraverser
{
   /**
    * Default c'tor.
    */
   public AeB4PDefTraverser()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefTraverser#traverse(org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeB4PHumanInteractionsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getLogicalPeopleGroupsDef(), aVisitor);
      callAccept(aDef.getTasksDef(), aVisitor);
      callAccept(aDef.getNotificationsDef(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefTraverser#traverse(org.activebpel.rt.b4p.def.AePeopleActivityDef,
    *      org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AePeopleActivityDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      // TODO (JB) add remoteTask remoteNotification
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getTask(), aVisitor);
      callAccept(aDef.getLocalTask(), aVisitor);
      callAccept(aDef.getNotification(), aVisitor);
      callAccept(aDef.getLocalNotification(), aVisitor);
      callAccept(aDef.getScheduledActions(), aVisitor);
      callAccept(aDef.getAttachmentPropagation(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefTraverser#traverse(org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef,
    *      org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeB4PPeopleAssignmentsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getProcessInitiator(), aVisitor);
      callAccept(aDef.getProcessStakeholders(), aVisitor);
      callAccept(aDef.getBusinessAdministrators(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefTraverser#traverse(org.activebpel.rt.b4p.def.AeBusinessAdministratorsDef,
    *      org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeBusinessAdministratorsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getFrom(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefTraverser#traverse(org.activebpel.rt.b4p.def.AeProcessInitiatorDef,
    *      org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeProcessInitiatorDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getFrom(), aVisitor);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefTraverser#traverse(org.activebpel.rt.b4p.def.AeProcessStakeholdersDef,
    *      org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeProcessStakeholdersDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getFrom(), aVisitor);
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefTraverser#traverse(org.activebpel.rt.b4p.def.AeLocalTaskDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeLocalTaskDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      traverseReferenceOverrideElements(aDef, aVisitor);
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefTraverser#traverse(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeB4PLocalNotificationDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      traverseReferenceOverrideElements(aDef, aVisitor);
   }


   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefTraverser#traverse(org.activebpel.rt.b4p.def.AeScheduledActionsDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeScheduledActionsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getDeferActivation(), aVisitor);
      callAccept(aDef.getExpiration(), aVisitor);
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefTraverser#traverse(org.activebpel.rt.b4p.def.AeAttachmentPropagationDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeAttachmentPropagationDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefTraverser#traverse(org.activebpel.rt.b4p.def.AeDeferActivationDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeDeferActivationDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getForDef(), aVisitor);
      callAccept(aDef.getUntilDef(), aVisitor);
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefTraverser#traverse(org.activebpel.rt.b4p.def.AeExpirationDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeExpirationDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getForDef(), aVisitor);
      callAccept(aDef.getUntilDef(), aVisitor);
   }
   
  /**
   * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefTraverser#traverse(org.activebpel.rt.b4p.def.AeB4PForDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
   */
  public void traverse(AeB4PForDef aDef, IAeBaseXmlDefVisitor aVisitor)
  {
     traverseDocumentationDefs(aDef, aVisitor);
     traverseExtensionDefs(aDef, aVisitor);
  }
  
 /**
  * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefTraverser#traverse(org.activebpel.rt.b4p.def.AeB4PUntilDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
  */
  public void traverse(AeB4PUntilDef aDef, IAeBaseXmlDefVisitor aVisitor)
  {
     traverseDocumentationDefs(aDef, aVisitor);
     traverseExtensionDefs(aDef, aVisitor);
  }
   
   /**
    * Called to traverse any extension defs that may exist.
    * @param aDef
    * @param aVisitor
    */
   protected void traverseReferenceOverrideElements(AeReferenceOverrideElementsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      callAccept(aDef.getPriority(), aVisitor);
      callAccept(aDef.getPeopleAssignments(), aVisitor);
   }
}
