// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeB4PLocalNotificationRule51Validator.java,v 1.3.4.1 2008/05/07 15:57:01 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import java.text.MessageFormat;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.def.AeBaseDefNamespaceContext;

/**
 * reference resolves to notification
 */
public class AeB4PLocalNotificationRule51Validator extends AeAbstractB4PValidator
{
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
    */
   public void visit(AeB4PLocalNotificationDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * rule logic 
    * @param aDef
    */
   protected void executeRule(AeB4PLocalNotificationDef aDef)
   {
      if (AeUtil.isNullOrEmpty(aDef.getReference()) || getValidationContext().findNotification(aDef, aDef.getReference()) == null)
      {
         String qName = aDef.getReference() == null ? "null" :  AeXmlUtil.encodeQName(aDef.getReference(), new AeBaseDefNamespaceContext(aDef), null); //$NON-NLS-1$
         String message = MessageFormat.format(AeMessages.getString("AeB4PLocalNotificationRule51Validator.0"), new Object[] {qName}); //$NON-NLS-1$
         
         reportProblem(message, aDef);
      }
   }
}
