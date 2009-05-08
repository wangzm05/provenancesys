// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeToPartsDefRule118Validator.java,v 1.2.4.1 2008/04/21 16:15:16 ppatruni Exp $
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

import javax.wsdl.Message;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeEscalationDef;
import org.activebpel.rt.ht.def.AeToPartsDef;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * There MUST be a &lt;toPart&gt; element for every part in the WSDL message definition.
 */
public class AeToPartsDefRule118Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationDef)
    */
   public void visit(AeToPartsDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * rule logic
    * @param aDef
    */
   protected void executeRule(AeToPartsDef aDef)
   {
      AeBaseXmlDef contextDef = null;
      
      // toParts is a special case where the notification interface is not within any of its parents,
      // so to handle this will we find the AeEscalationDef and find its local notification or notification
      // and ignore this rule for reassignments
      
      AeEscalationDef parent = (AeEscalationDef) aDef.getParentDef();

      if (parent.getLocalNotification() != null)
      {
         contextDef = parent.getLocalNotification();
      }
      else if (parent.getNotification() != null)
      {
         contextDef = parent.getNotification();
      } 
      
      try
      {
         Message taskMessage = getValidationContext().getInputMessage(contextDef);
         
         if (taskMessage != null)
         {
            
            for (Iterator iter = taskMessage.getParts().keySet().iterator(); iter.hasNext();)
            {
               String part = String.valueOf(iter.next());
               if (aDef.getPart(part) == null)
               {
                  reportProblem(AeMessages.getString("AeToPartsDefRule118Validator.0"), aDef); //$NON-NLS-1$
               }
            }
         }
      }
      catch (AeException ex)
      {
         reportException(ex, aDef);
      }
   }
}
