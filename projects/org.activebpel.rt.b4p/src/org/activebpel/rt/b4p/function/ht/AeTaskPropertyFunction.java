//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/ht/AeTaskPropertyFunction.java,v 1.1 2008/02/05 04:25:47 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function.ht; 

import java.util.List;

import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;

/**
 * Base class for functions that pull values from a task instance 
 */
public abstract class AeTaskPropertyFunction extends AeAbstractBpelFunction
{
   protected AeTaskPropertyFunction(String aFunctionName)
   {
      super(aFunctionName);
   }

   protected String getTaskName(List aArgs, int aOffset) throws AeFunctionCallException
   {
      if (aArgs.size() > aOffset)
         return getStringArg(aArgs, aOffset);
      return null;
   }
}
 