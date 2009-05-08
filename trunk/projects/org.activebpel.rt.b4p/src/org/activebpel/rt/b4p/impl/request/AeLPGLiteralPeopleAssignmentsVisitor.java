//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/request/AeLPGLiteralPeopleAssignmentsVisitor.java,v 1.1 2008/02/05 01:46:43 ppatruni Exp $
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

import org.activebpel.rt.ht.def.AeAbstractGenericHumanRoleDef;
import org.activebpel.rt.ht.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.ht.def.AeDelegationDef;
import org.activebpel.rt.ht.def.AeExcludedOwnersDef;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.ht.def.AePotentialOwnersDef;
import org.activebpel.rt.ht.def.AeRecipientsDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTaskInitiatorDef;
import org.activebpel.rt.ht.def.AeTaskStakeHoldersDef;
import org.w3c.dom.Element;

/**
 * This class traverses task def and produce people assignments for all literal and LPG assignments
 */
public class AeLPGLiteralPeopleAssignmentsVisitor extends AePABaseVisitor
{
   /** People assignments def object */
   private AePeopleAssignmentsDef mPeopleAssignmentsDef;
   
   /** LPG Map */
   private Map mLPGMap;

   /**
    * C'tor that accepts LPG Map
    * @param aMap
    */
   public AeLPGLiteralPeopleAssignmentsVisitor(Map aMap)
   {
      mLPGMap = aMap;
   }
   
   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      callAccept(aDef.getPeopleAssignments());
      callAccept(aDef.getDelegation());
   }
   
   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      callAccept(aDef.getPeopleAssignments());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDelegationDef)
    */
   public void visit(AeDelegationDef aDef)
   {
      AeFromDef fromDef = aDef.getFrom(); 
      if ( (fromDef == null) || (fromDef.isExpression()) ) 
         return;

      // Get organizational entity as an element
      Element orgEntity = getOrgEntity(fromDef);
      
      AeFromDef from = AePARequestUtil.wrapOrgEntityAsLiteralInFromDef(orgEntity);
      aDef.setFrom(from);
   }
   
   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      Element orgEntity = resolveLPGAndLiteral((AeAbstractGenericHumanRoleDef) aDef);
      getPeopleAssignmentsDef().setBusinessAdministrators((AeBusinessAdministratorsDef) AePARequestUtil.getHumanRoleHTProtocolStyle(aDef, orgEntity));
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeExcludedOwnersDef)
    */
   public void visit(AeExcludedOwnersDef aDef)
   {
      Element orgEntity = resolveLPGAndLiteral((AeAbstractGenericHumanRoleDef) aDef);
      getPeopleAssignmentsDef().setExcludedOwners((AeExcludedOwnersDef) AePARequestUtil.getHumanRoleHTProtocolStyle(aDef, orgEntity));
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AePotentialOwnersDef)
    */
   public void visit(AePotentialOwnersDef aDef)
   {
      Element orgEntity = resolveLPGAndLiteral((AeAbstractGenericHumanRoleDef) aDef);
      getPeopleAssignmentsDef().setPotentialOwners((AePotentialOwnersDef) AePARequestUtil.getHumanRoleHTProtocolStyle(aDef, orgEntity));
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeTaskInitiatorDef)
    */
   public void visit(AeTaskInitiatorDef aDef)
   {
      Element orgEntity = resolveLPGAndLiteral((AeAbstractGenericHumanRoleDef) aDef);
      getPeopleAssignmentsDef().setTaskInitiator((AeTaskInitiatorDef) AePARequestUtil.getHumanRoleHTProtocolStyle(aDef, orgEntity));
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeTaskStakeHoldersDef)
    */
   public void visit(AeTaskStakeHoldersDef aDef)
   {
      Element orgEntity = resolveLPGAndLiteral((AeAbstractGenericHumanRoleDef) aDef);
      getPeopleAssignmentsDef().setTaskStakeholders((AeTaskStakeHoldersDef) AePARequestUtil.getHumanRoleHTProtocolStyle(aDef, orgEntity));
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeRecipientsDef)
    */
   public void visit(AeRecipientsDef aDef)
   {
      Element orgEntity = resolveLPGAndLiteral((AeAbstractGenericHumanRoleDef) aDef);
      getPeopleAssignmentsDef().setRecipients((AeRecipientsDef) AePARequestUtil.getHumanRoleHTProtocolStyle(aDef, orgEntity));
   }

   /**
    * Returns cloned generic human role def object
    * @param aDef
    */
   private Element resolveLPGAndLiteral(AeAbstractGenericHumanRoleDef aDef)
   {
      // If the from assignment is an expression then return the def unchanged
      // If the from assignment is LPG then resolved LPG to an organizational entity.
      // If the from assignment is a literal then extracts oranizational entity

      AeFromDef fromDef = aDef.getFrom();
      
      if (fromDef.isExpression())
         return null;
      
      Element orgEntity = getOrgEntity(fromDef);
      return orgEntity;
   }

   /**
    * Return organizational entity as an Element when the assignment in the from def is LPG or literal
    * @param aDef
    */
   private Element getOrgEntity(AeFromDef aDef)
   {
      Element orgEntity = null;
      
      if (aDef.isLPG())
      {
         orgEntity = (Element)getLPGMap().get(aDef.getInlineLogicalPeopleGroupDef());
      }
      else if (aDef.isLiteral())
      {
         orgEntity = (Element)aDef.getLiteral().getChildNodes().get(0);
      }
      return orgEntity;
   }
   
   /**
    * @return the lPGMap
    */
   protected Map getLPGMap()
   {
      return mLPGMap;
   }
   /**
    * @param aMap the lPGMap to set
    */
   protected void setLPGMap(Map aMap)
   {
      mLPGMap = aMap;
   }

   /**
    * @return the peopleAssignmentsDef
    */
   public AePeopleAssignmentsDef getPeopleAssignmentsDef()
   {
      if (mPeopleAssignmentsDef == null)
         mPeopleAssignmentsDef = new AePeopleAssignmentsDef();
      
      return mPeopleAssignmentsDef;
   }
}
