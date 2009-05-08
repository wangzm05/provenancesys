//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/AeGetFinalizationDuration.java,v 1.3 2008/02/26 01:59:22 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function;

import java.util.List;

import org.activebpel.rt.b4p.impl.engine.IAeB4PManager;
import org.activebpel.rt.b4p.server.IAeServerB4PManager;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;
import org.activebpel.rt.xml.schema.AeSchemaDuration;

/**
 * Simple pass through to the manager in order to get the finalization duration
 * for the task state machine.
 */
public class AeGetFinalizationDuration extends AeAbstractBpelFunction
{
   /** name of the function */
   public static final String FUNCTION_NAME = "getFinalizationDuration"; //$NON-NLS-1$

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs)
         throws AeFunctionCallException
   {
      IAeServerB4PManager b4pManager = (IAeServerB4PManager) aContext.getAbstractBpelObject().getProcess().getEngine().getCustomManager(IAeB4PManager.MANAGER_NAME);
      // manager could be null in test environment
      if (b4pManager == null)
      {
         return new AeSchemaDuration(IAeServerB4PManager.DEFAULT_FINALIZATION_DURATION);
      }
      return b4pManager.getTaskFinalizationDuration();
   }
}
