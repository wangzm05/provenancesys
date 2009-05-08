// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/IAeB4PDefTraverser.java,v 1.3 2007/11/16 02:43:14 jbik Exp $
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
import org.activebpel.rt.b4p.def.AeScheduledActionsDef;
import org.activebpel.rt.ht.def.visitors.IAeHtDefTraverser;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * Traversal interface for use in conjunction with BPEL4People definition object visitation. Each traverse method below
 * accepts a definition object and a visitor object. The method's responsibility is to decide how to traverse
 * the given definition object so each of its child objects (if any) will get visited.
 */
public interface IAeB4PDefTraverser extends IAeHtDefTraverser
{
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeBusinessAdministratorsDef aDef, IAeBaseXmlDefVisitor aVisitor);
  
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeB4PHumanInteractionsDef aDef, IAeBaseXmlDefVisitor aVisitor);
   
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AePeopleActivityDef aDef, IAeBaseXmlDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeB4PPeopleAssignmentsDef aDef, IAeBaseXmlDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeProcessInitiatorDef aDef, IAeBaseXmlDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeProcessStakeholdersDef aDef, IAeBaseXmlDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeLocalTaskDef aDef, IAeBaseXmlDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeB4PLocalNotificationDef aDef, IAeBaseXmlDefVisitor aVisitor);
   
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeScheduledActionsDef aDef, IAeBaseXmlDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeAttachmentPropagationDef aDef, IAeBaseXmlDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeDeferActivationDef aDef, IAeBaseXmlDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeExpirationDef aDef, IAeBaseXmlDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeB4PForDef aDef, IAeBaseXmlDefVisitor aVisitor);
   /**
    * Traverses the definition object, calling <code>accept</code> on each of the object's child objects
    * that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeB4PUntilDef aDef, IAeBaseXmlDefVisitor aVisitor);
}
