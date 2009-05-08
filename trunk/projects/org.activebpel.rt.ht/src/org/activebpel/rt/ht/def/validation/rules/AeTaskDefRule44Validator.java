// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeTaskDefRule44Validator.java,v 1.3 2008/02/26 15:14:38 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.IAeTasksDefParent;
import org.activebpel.rt.ht.def.io.AeHtIO;
import org.activebpel.rt.xml.def.AeXmlDefUtil;

/**
 * AE: people assignment must have a strategy for business administrators
 * 
 * ======================================================================
 * 
 * WS-HT Spec: 
 *    Business administrators play the same role as task stakeholders but at task type
 *    level. Therefore, business administrators can perform the exact same operations as
 *    task stakeholders. Business administrators may also observe the progress of
 *    notifications. Compliant implementations MUST ensure that at runtime at least one
 *    person is associated with this role.
 * 
 */
public class AeTaskDefRule44Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      if (!shouldIgnoreTask(aDef))
      {
         executeRule(aDef);
      }
      super.visit(aDef);
   }
   
   /**
    * return a boolean indicating if the rule should be evaluated
    * @param aDef
    */
   protected boolean shouldIgnoreTask(AeTaskDef aDef)
   {
      return AeXmlDefUtil.isParentedByType(aDef, IAeTasksDefParent.class);
   }
   
   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeTaskDef aDef)
   {
      boolean baDefined = false;
      
      if(aDef.getPeopleAssignments() != null)
      {
         // get the business administrators def
         AeBusinessAdministratorsDef baDef = aDef.getPeopleAssignments().getBusinessAdministrators();
         if ( baDef != null)
         {
            AeFromDef from = baDef.getFrom();
            
            // if we are dealing with a literal, deserialize it and check if there is at least a user or group defined
            // otherwise, its an LPG or an expression
            if (from != null && from.isLiteral())
            {
               try
               {
                  AeOrganizationalEntityDef entity = (AeOrganizationalEntityDef) AeHtIO.deserialize(from.getLiteral());
                  
                  baDefined |= entity.getGroups() != null && entity.getGroups().size() >= 1;
                  baDefined |= entity.getUsers() != null && entity.getUsers().size() >= 1;
                  
               }
               catch (Exception ex)
               {
                  reportException(ex, aDef);
               }
            }
            else 
            {
               // LPG and expressions will not report a problem
               baDefined = from != null;
            }
         }
      }
      
      if (!baDefined)
      {
         reportProblem(AeMessages.getString("AeTaskDefRule44Validator.0"), aDef); //$NON-NLS-1$
      }
   }

}
