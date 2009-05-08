//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/attachments/AeCopyHTAttachmentsByNameFunction.java,v 1.2 2008/02/17 21:36:32 mford Exp $
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

import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;

/**
 * This class implements custom ht copy by attachment name function
 */
public class AeCopyHTAttachmentsByNameFunction extends AeAbstractBaseHTAttachmentFunction
{

   /** The name of the function implemented */
   public static final String FUNCTION_NAME = "copyHTAttachmentsByName"; //$NON-NLS-1$
   
   /**
    * Ctor
    */
   public AeCopyHTAttachmentsByNameFunction()
   {
      super(FUNCTION_NAME);
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {
      // Required arguments
      // aArgs[0] : Source bpel variable name
      // aArgs[1] : Target bpel variable name
      // Optional arguments
      // aArgs[2] : attachment name

      // Call appropriate method on base class based on number of arguments
      if ((aArgs.size() < 2) || (aArgs.size() > 3))
      {
         throwFunctionException(INVALID_PARAMS, FUNCTION_NAME);
         return null;
      }
      else
      {
         String fromVariable = getStringArg(aArgs, 0);
         String toVariable = getStringArg(aArgs, 1);
         String attachmentName = aArgs.size() == 3 ? getStringArg(aArgs, 2) : null;
         IAeAttachmentContainer matchingAttachments = getAttachmentsByName(aContext.getAbstractBpelObject(), fromVariable, attachmentName);
         IAeAttachmentContainer toContainer = getAttachmentContainer(aContext.getAbstractBpelObject(), toVariable);
         // add matching attachments
         toContainer.addAll(matchingAttachments);
         return AeHTAttachmentSerializer.serialize(aContext, matchingAttachments);
      }
   }

}
