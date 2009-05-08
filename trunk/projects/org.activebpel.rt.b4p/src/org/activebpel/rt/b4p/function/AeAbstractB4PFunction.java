//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/AeAbstractB4PFunction.java,v 1.4 2008/02/29 04:16:41 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function;

import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.impl.AePeopleActivityFinder;
import org.activebpel.rt.b4p.impl.AePeopleActivityImpl;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;

/**
 *  Base class for B4P Functions
 */
public abstract class AeAbstractB4PFunction extends AeAbstractBpelFunction
{
   /**
    * Retruns an Object of type expected by the call() of this function 
    */
   protected abstract Object getValueFromImpl(Object aImpl);

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {
      try
      {
         validateArguments(aArgs);
         
         String argName = (String) aArgs.get(0);
         Object impl = findImpl(aContext, argName);
         return getValueFromImpl(impl);
      }
      catch (Throwable t)
      {
         AeException.logError(t);
         return null;
      }
   }

   /**
    * Validates that the arguments are correct for this function.
    * 
    * @param aArgs
    * @throws AeFunctionCallException
    */
   protected void validateArguments(List aArgs) throws AeFunctionCallException
   {
      if (aArgs.size() != 1)
      {
         throw new AeFunctionCallException(AeMessages.getString("AeAbstractB4PFunction.INVALID_ARGS_COUNT")); //$NON-NLS-1$
      }
   }

   /**
    * Retruns an impl Object for the arg 
    * @param aContext
    * @param aArg
    * @throws AeFunctionCallException
    */
   protected Object findImpl(IAeFunctionExecutionContext aContext, String aArg) throws AeFunctionCallException
   {
      try
      {
         AePeopleActivityImpl impl = AePeopleActivityFinder.find(aContext.getAbstractBpelObject(), aArg);
         return impl; 
      }
      catch (AeBusinessProcessException ex)
      {
         throw new AeFunctionCallException(ex.getLocalizedMessage());
      }
   }

}
