//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/request/AeAssignTaskOverrides.java,v 1.2 2008/02/21 17:14:57 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.request;

import org.activebpel.rt.b4p.def.AeLocalTaskDef;
import org.activebpel.rt.ht.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.ht.def.AeExcludedOwnersDef;
import org.activebpel.rt.ht.def.AePotentialOwnersDef;
import org.activebpel.rt.ht.def.AePriorityDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTaskInitiatorDef;
import org.activebpel.rt.ht.def.AeTaskStakeHoldersDef;

/**
 * This visitor traverses people activity def and inlines referenced Task in Local Task
 * when Local Task is used. This class also accounts for priority and people assignment
 * overrides and the inlined task contains an aggregate of people assignments.  
 */
public class AeAssignTaskOverrides extends AePABaseVisitor
{
   /** Task def object */
   private AeTaskDef mTaskDef;
   
   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.b4p.def.AeLocalTaskDef)
    */
   public void visit(AeLocalTaskDef aDef)
   {
      AeTaskDef taskDef = (AeTaskDef) aDef.getInlineTaskDef().clone();
      taskDef.setParentXmlDef(aDef.getInlineTaskDef().getParentXmlDef());
      parentHtDef(taskDef);
      setTaskDef(taskDef);
      callAccept(aDef.getPriority());
      callAccept(aDef.getPeopleAssignments());
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      AeTaskDef taskDef = (AeTaskDef) aDef.clone();
      taskDef.setParentXmlDef(aDef.getParentXmlDef());
      parentHtDef(taskDef);
      setTaskDef(taskDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePriorityDef)
    */
   public void visit(AePriorityDef aDef)
   {
      getTaskDef().setPriority(aDef);
   }
   
   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      getTaskDef().getPeopleAssignments().setBusinessAdministrators(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeExcludedOwnersDef)
    */
   public void visit(AeExcludedOwnersDef aDef)
   {
      getTaskDef().getPeopleAssignments().setExcludedOwners(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AePotentialOwnersDef)
    */
   public void visit(AePotentialOwnersDef aDef)
   {
      getTaskDef().getPeopleAssignments().setPotentialOwners(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeTaskInitiatorDef)
    */
   public void visit(AeTaskInitiatorDef aDef)
   {
      getTaskDef().getPeopleAssignments().setTaskInitiator(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeTaskStakeHoldersDef)
    */
   public void visit(AeTaskStakeHoldersDef aDef)
   {
      getTaskDef().getPeopleAssignments().setTaskStakeholders(aDef);
   }


   /**
    * @return the taskDef
    */
   protected AeTaskDef getTaskDef()
   {
      return mTaskDef;
   }

   /**
    * @param aTaskDef the taskDef to set
    */
   protected void setTaskDef(AeTaskDef aTaskDef)
   {
      mTaskDef = aTaskDef;
   }

}
