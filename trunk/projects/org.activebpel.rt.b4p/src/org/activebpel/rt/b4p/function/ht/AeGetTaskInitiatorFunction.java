//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/ht/AeGetTaskInitiatorFunction.java,v 1.4 2008/02/07 02:05:37 EWittmann Exp $
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

import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.ht.IAeHtFunctionContext;
import org.activebpel.rt.ht.IAeHtFunctionNames;

/**
 * An Implementation of HT XPath extension function getTaskInitiator
 */
public class AeGetTaskInitiatorFunction extends AeTaskPropertyFunction
{
   /**
    * C'tor
    */
   public AeGetTaskInitiatorFunction()
   {
      super(IAeHtFunctionNames.TASK_INITIATOR_FUNCTION_NAME);
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {
      IAeHtFunctionContext context = (IAeHtFunctionContext)aContext.getEvaluationContext();
      if (context == null)
         throw new AeMissingEvaluationContextException(IAeHtFunctionNames.TASK_INITIATOR_FUNCTION_NAME);
      String taskName = getTaskName(aArgs, 0);
      return context.getTaskInitiator(taskName);
   }
}
