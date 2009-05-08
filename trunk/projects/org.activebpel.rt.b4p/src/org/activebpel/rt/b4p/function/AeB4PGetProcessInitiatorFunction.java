//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/AeB4PGetProcessInitiatorFunction.java,v 1.1 2008/02/13 07:44:43 mford Exp $
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

import org.activebpel.rt.b4p.def.io.AeB4PIO;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.ht.def.AeUserDef;


/**
 * This function gets the process initiator 
 */
public class AeB4PGetProcessInitiatorFunction extends AeB4PGetProcessRoleFunction
{
   public static String FUNCTION_NAME = "getProcessInitiator"; //$NON-NLS-1$
   
   /**
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs)
         throws AeFunctionCallException
   {
      AeUserDef user = getProcessInitiator(aContext);
      return AeB4PIO.serialize2Element(user);
   }
}
