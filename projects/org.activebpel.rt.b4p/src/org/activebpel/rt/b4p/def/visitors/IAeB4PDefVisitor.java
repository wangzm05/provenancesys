// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/IAeB4PDefVisitor.java,v 1.3 2007/11/16 02:43:14 jbik Exp $
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
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;

/**
 * Visitor interface for Human Task Definition classes.
 */
public interface IAeB4PDefVisitor extends IAeHtDefVisitor
{
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeB4PHumanInteractionsDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeB4PPeopleAssignmentsDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AePeopleActivityDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeLocalTaskDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeB4PLocalNotificationDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeScheduledActionsDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeAttachmentPropagationDef aDef);
   
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeBusinessAdministratorsDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeProcessInitiatorDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeProcessStakeholdersDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeDeferActivationDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeExpirationDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeB4PForDef aDef);
   
   /**
    * Visits the specified type of definition object.
    * @param aDef
    */
   public void visit(AeB4PUntilDef aDef);

}
