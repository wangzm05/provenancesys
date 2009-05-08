// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeNotificationDefRule29Validator.java,v 1.2 2008/02/15 17:40:57 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import java.util.Iterator;

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.IAeNotificationDefParent;
import org.activebpel.rt.util.AeUtil;

/**
 * Notification name must be unique within parent.
 * 
 * Note: although AeEscalationDef objects contain a AeNotificationDef, there will always only
 * be one and schema validation should report when there is more than one AeNotificationDef
 * in a escalation element.
 */
public class AeNotificationDefRule29Validator extends AeAbstractHtValidator
{

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * execute rule
    * 
    * @param aDef
    */
   protected void executeRule(AeNotificationDef aDef)
   {
      IAeNotificationDefParent parent = (IAeNotificationDefParent) aDef.getParentXmlDef();
      
      for (Iterator iter = parent.getNotificationDefs(); iter.hasNext(); )
      {
         AeNotificationDef notification = (AeNotificationDef) iter.next();
         
         if (notification != aDef && AeUtil.compareObjects(notification.getName(), aDef.getName()))
         {
            reportProblem(AeMessages.getString("AeNotificationDefRule29Validator.0"), aDef); //$NON-NLS-1$
         }
      }
   
   }

}
