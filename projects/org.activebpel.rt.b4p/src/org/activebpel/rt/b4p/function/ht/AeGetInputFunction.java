//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/ht/AeGetInputFunction.java,v 1.5 2008/02/27 20:56:43 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function.ht;

import java.util.List;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.ht.IAeHtFunctionContext;
import org.activebpel.rt.ht.IAeHtFunctionNames;

/**
 * An Implementation of HT XPath extension function getInput
 */
public class AeGetInputFunction extends AeTaskPropertyFunction
{
   /**
    * C'tor
    */
   public AeGetInputFunction()
   {
      super(IAeHtFunctionNames.INPUT_FUNCTION_NAME);
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {
      if (aArgs.size() == 0 || aArgs.size() > 2)
      {
         throw new AeFunctionCallException(AeMessages.getString("AeGetInputFunction.IncorrectNumberOfArguments")); //$NON-NLS-1$
      }
      IAeHtFunctionContext context = (IAeHtFunctionContext)aContext.getEvaluationContext();
      if (context == null)
         throw new AeMissingEvaluationContextException(IAeHtFunctionNames.INPUT_FUNCTION_NAME);
      String partName = getStringArg(aArgs, 0);
      String taskName = getTaskName(aArgs, 1);
      return context.getInput(partName, taskName);
   }
}
