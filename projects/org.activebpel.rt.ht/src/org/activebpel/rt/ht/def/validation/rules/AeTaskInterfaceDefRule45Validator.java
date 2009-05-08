// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeTaskInterfaceDefRule45Validator.java,v 1.3 2008/02/15 17:40:56 EWittmann Exp $
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
import org.activebpel.rt.ht.def.AeTaskInterfaceDef;
import org.activebpel.rt.util.AeUtil;

/**
 * The element and its portType and operation attributes are mandatory
 *
 * The WSDL operation is a one-way operation and the task asynchronously returns output data. 
 * In this case, a callback one-way operation MUST be specified, using the responsePortType 
 * and responseOperation attributes. This callback operation is invoked when the task has 
 * finished. 
 * 
 * The WSDL operation is a request-response operation. In this case, the responsePortType 
 * and responseOperation attributes MUST NOT be specified.
 */
public class AeTaskInterfaceDefRule45Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskInterfaceDef)
    */
   public void visit(AeTaskInterfaceDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeTaskInterfaceDef aDef)
   {
      // get mandatory port type and operation
      QName portType = aDef.getPortType();
      String operationName = aDef.getOperation();
      
      if (AeUtil.notNullOrEmpty(portType) && AeUtil.notNullOrEmpty(operationName))
      {
         try
         {
            Operation operation = getValidationContext().getOperation(portType, operationName);
            
            boolean isRequestResponse = operation.getStyle().equals(OperationType.REQUEST_RESPONSE);
            boolean isOneWay = operation.getStyle().equals(OperationType.ONE_WAY);
            
            if (isRequestResponse && AeUtil.notNullOrEmpty(aDef.getResponseOperation()) && aDef.getResponsePortType() != null)
            {
               reportProblem(AeMessages.getString("AeTaskInterfaceDefRule45Validator.0"), //$NON-NLS-1$
                     aDef);
            }
            else if (isOneWay)
            {
               // if responsePortType and responseOperation attributes are specified, then check to make
               // sure its a one-way operation
               if (AeUtil.notNullOrEmpty(aDef.getResponseOperation()) && aDef.getResponsePortType() != null)
               {   
                  Operation respOperation = getValidationContext().getOperation(aDef.getResponsePortType(), aDef.getResponseOperation());
                  
                  if (!respOperation.getStyle().equals(OperationType.ONE_WAY))
                  {
                     reportProblem(AeMessages.getString("AeTaskInterfaceDefRule45Validator.1"), //$NON-NLS-1$
                           aDef);
                  }
               }
               else
               {
                  reportProblem(AeMessages.getString("AeTaskInterfaceDefRule45Validator.2"), //$NON-NLS-1$
                        aDef);
               }
            }
            else
            {
               // TAKE NO ACTION
            }
         }
         catch (AeException ex)
         {
            reportProblem(ex.getMessage(), aDef);
         }
      }
      else
      {
         reportProblem(AeMessages.getString("AeTaskInterfaceDefRule45Validator.3"), aDef); //$NON-NLS-1$
      }
      
   }
   
}
