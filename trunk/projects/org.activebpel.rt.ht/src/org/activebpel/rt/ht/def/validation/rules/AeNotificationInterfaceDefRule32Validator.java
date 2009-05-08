// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeNotificationInterfaceDefRule32Validator.java,v 1.3 2008/02/15 17:40:57 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import javax.wsdl.Operation;
import javax.wsdl.OperationType;
import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeNotificationInterfaceDef;

/**
 * port type / operation resolves to a one-way operation
 */
public class AeNotificationInterfaceDefRule32Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationInterfaceDef)
    */
   public void visit(AeNotificationInterfaceDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeNotificationInterfaceDef aDef)
   {
      QName  portType = aDef.getPortType();
      String operationName = aDef.getOperation();
      
      try
      {
         Operation operation = getValidationContext().getOperation(portType, operationName);
         if (!operation.getStyle().equals(OperationType.ONE_WAY))
         {
            reportProblem(AeMessages.getString("AeNotificationInterfaceDefRule32Validator.0"), aDef); //$NON-NLS-1$
         }
      }
      catch (AeException ex)
      {
         reportProblem(ex.getMessage(), aDef);
      }
   }
}
