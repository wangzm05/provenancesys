// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeNotificationDefRule31Validator.java,v 1.4 2008/03/26 19:40:21 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationsDef;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.AeRecipientsDef;
import org.activebpel.rt.ht.def.io.AeHtIO;
import org.activebpel.rt.xml.def.AeXmlDefUtil;

/**
 * people assignment must have a strategy for recipients and may have one for business administrators
 */
public class AeNotificationDefRule31Validator extends AeAbstractHtValidator
{  
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      if (!shouldIgnoreNotification(aDef))
      {
         executeRule(aDef);
      }
      super.visit(aDef);
   }
   
   /**
    * return a boolean indicating if the rule should be evaluated
    * @param aDef
    */
   protected boolean shouldIgnoreNotification(AeNotificationDef aDef)
   {
      return AeXmlDefUtil.isParentedByType(aDef, AeNotificationsDef.class);
   }

   /**
    * rule logic
    *  
    * @param aDef
    */
   protected void executeRule(AeNotificationDef aDef)
   {
      boolean recipientsDefined = false;

      if(aDef.getPeopleAssignments() != null)
      {
         // get the business administrators def
         AeRecipientsDef recipientsDef = aDef.getPeopleAssignments().getRecipients();
         if ( recipientsDef != null)
         {
            AeFromDef from = recipientsDef.getFrom();
            
            // if we are dealing with a literal, deserialize it and check if there is at least a user or group defined
            // otherwise, its an LPG or an expression
            if (from != null && from.isLiteral())
            {
               try
               {
                  AeOrganizationalEntityDef entity = (AeOrganizationalEntityDef) AeHtIO.deserialize(from.getLiteral());
                  recipientsDefined |= entity.getUsers() != null && entity.getUsers().size() >= 1;
                  recipientsDefined |= entity.getGroups() != null && entity.getGroups().size() >= 1;
               }
               catch (Exception ex)
               {
                  reportException(ex, aDef);
               }
            }
            else 
            {
               // logicialPeopleGroups and expressions will not report a problem
               recipientsDefined = from != null;
            }
         }
      }
      
      if (!recipientsDefined)
      {
         reportProblem("People assignment for notification must specify at least one recipient.", aDef); //$NON-NLS-1$
      }
   }
   
}
