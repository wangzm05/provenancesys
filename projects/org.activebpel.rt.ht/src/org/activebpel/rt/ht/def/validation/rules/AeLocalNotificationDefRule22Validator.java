// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeLocalNotificationDefRule22Validator.java,v 1.3 2008/02/29 18:36:25 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import javax.xml.namespace.QName;

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeLocalNotificationDef;
import org.activebpel.rt.util.AeUtil;

/**
 * The referenced notification is resolved
 */
public class AeLocalNotificationDefRule22Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLocalNotificationDef)
    */
   public void visit(AeLocalNotificationDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeLocalNotificationDef aDef)
   {
      QName reference = aDef.getReference();
      
      if (AeUtil.isNullOrEmpty(reference) || getValidationContext().findNotification(aDef, reference) == null)
      {
         reportProblem(AeMessages.getString("AeLocalNotificationDefRule22Validator.0"), aDef); //$NON-NLS-1$
      }  
   }

}
