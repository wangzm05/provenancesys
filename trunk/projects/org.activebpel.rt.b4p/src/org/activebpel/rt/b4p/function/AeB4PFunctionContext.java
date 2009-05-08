//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/AeB4PFunctionContext.java,v 1.5 2008/02/13 07:44:43 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function;

import org.activebpel.rt.bpel.function.AeUnresolvableException;
import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.impl.function.AeAbstractFunctionContext;

/**
 * Class that implements <code>FunctionContext</code> impls that
 * implement the WS-B4P extension functions
 */
public class AeB4PFunctionContext extends AeAbstractFunctionContext
{
   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionContext#getFunction(java.lang.String)
    */
   public IAeFunction getFunction(String aFunctionName) throws AeUnresolvableException
   {
      if (AeB4PGetProcessInitiatorFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeB4PGetProcessInitiatorFunction();
      }
      
      if (AeB4PGetBusinessAdministrators.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeB4PGetBusinessAdministrators();
      }

      if (AeB4PGetProcessStakeholders.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeB4PGetProcessStakeholders();
      }

      if (AeB4PGetActualOwnerFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeB4PGetActualOwnerFunction();
      }

      if (AeB4PGetPotentialOwnersFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeB4PGetPotentialOwnersFunction();
      }

      if (AeB4PGetAdministratorsFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeB4PGetAdministratorsFunction();
      }

      if (AeB4PGetTaskPriorityFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeB4PGetTaskPriorityFunction();
      }

      if (AeB4PGetTaskStakeholdersFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeB4PGetTaskStakeholdersFunction();
      }

      if (AeB4PGetTaskInitiatorFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeB4PGetTaskInitiatorFunction();
      }

      if (AeB4PGetLogicalPeopleGroupFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeB4PGetLogicalPeopleGroupFunction();
      }
      
      throw new AeUnresolvableException(formatFunctionNotFoundErrorMsg(aFunctionName));
   }
}
