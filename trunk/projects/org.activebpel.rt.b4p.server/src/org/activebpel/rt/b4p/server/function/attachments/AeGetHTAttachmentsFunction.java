//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/attachments/AeGetHTAttachmentsFunction.java,v 1.2 2008/02/17 21:36:32 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function.attachments;

import java.util.List;

import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;

/**
 * This class implements ht custom function for getHTAttachment
 */
public class AeGetHTAttachmentsFunction extends AeAbstractBpelFunction
{
   /** The name of the function implemented */
   public static final String FUNCTION_NAME = "getHTAttachments"; //$NON-NLS-1$

   /**
    * Ctor
    */
   public AeGetHTAttachmentsFunction()
   {
      super(FUNCTION_NAME);
   }


   /**
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {
      // Required arguments
      // aArgs[0] : A bpel variable name

      if (aArgs.size() != 1)
      {
         throwFunctionException(INVALID_PARAMS, FUNCTION_NAME);
      }
      
      String variableName = getStringArg(aArgs, 0);
      IAeVariable variable = getVariable(aContext.getAbstractBpelObject(), variableName);
      return AeHTAttachmentSerializer.serialize(aContext, variable.getAttachmentData());
   }

}
