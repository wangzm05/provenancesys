// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeInputRule104Validator.java,v 1.3 2008/03/03 22:44:54 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import javax.wsdl.Message;
import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.expr.def.AeScriptFuncDef;
import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.IAeHtFunctionNames;
import org.activebpel.rt.ht.def.AeAbstractExpressionDef;
import org.activebpel.rt.ht.def.AeNotificationInterfaceDef;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.ht.def.IAeInterfaceDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * if present, partName must resolve to a part defined by the input message for the task
 */
public class AeInputRule104Validator extends AeAbstractHtExpressionValidator
{
   /** function QName being validated*/
   private static QName sFunctionName = new QName(IAeHtDefConstants.DEFAULT_HT_NS, IAeHtFunctionNames.INPUT_FUNCTION_NAME);
   
   /**
    * @see org.activebpel.rt.ht.def.validation.rules.AeAbstractHtExpressionValidator#individualFunctionValidation(org.activebpel.rt.ht.def.AeAbstractExpressionDef, org.activebpel.rt.expr.def.AeScriptFuncDef)
    */
   protected void individualFunctionValidation(AeAbstractExpressionDef aDef, AeScriptFuncDef aFunction)
   {
      if (AeUtil.compareObjects(sFunctionName, aFunction.getQName()))
      {
         try
         {
            Message message = null;
           
            IAeInterfaceDef ifcParent = getValidationContext().findInScopeInterface(aDef);
            
            // any content in arg 2 is a flag to go to the enclosing task, if not already
            
            if (aFunction.getArgs().size() == 2 && ifcParent instanceof AeNotificationInterfaceDef)
            {
               // get the notification parent def
               AeBaseXmlDef notifParent = ((AeBaseXmlDef) ifcParent).getParentXmlDef().getParentXmlDef();
               ifcParent = getValidationContext().findInScopeInterface(notifParent);           
            }
            
            message = getValidationContext().getInputMessage((AeBaseXmlDef) ifcParent);

            // report a problem if there is at least 1 argument and the message or message part wasnt resolved.
            if (aFunction.getArgs().size() > 0 && (message == null || message.getPart(aFunction.getStringArgument(0)) == null))
            {
               reportProblem(AeMessages.getString("AeInputRule104Validator.0"), aDef); //$NON-NLS-1$
            }
         }
         catch (AeException ex)
         {
            reportException(ex, aDef);
         }
      }
   }
 
}
