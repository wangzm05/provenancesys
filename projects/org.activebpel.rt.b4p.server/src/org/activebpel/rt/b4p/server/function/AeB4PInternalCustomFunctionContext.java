//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/AeB4PInternalCustomFunctionContext.java,v 1.8.4.2 2008/04/14 21:24:48 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function;

import org.activebpel.rt.b4p.function.AeDurationToDeadlineFunction;
import org.activebpel.rt.b4p.function.AeToIdentityQueryFunction;
import org.activebpel.rt.b4p.server.function.attachments.AeAddHTAttachmentFunction;
import org.activebpel.rt.b4p.server.function.attachments.AeCopyHTAttachmentByIdFunction;
import org.activebpel.rt.b4p.server.function.attachments.AeCopyHTAttachmentsByNameFunction;
import org.activebpel.rt.b4p.server.function.attachments.AeGetHTAttachmentsFunction;
import org.activebpel.rt.b4p.server.function.attachments.AeHtCopyAllMimeAttachmentsFunction;
import org.activebpel.rt.b4p.server.function.attachments.AeRemoveHTAttachmentByIdFunction;
import org.activebpel.rt.b4p.server.function.attachments.AeRemoveHTAttachmentsByNameFunction;
import org.activebpel.rt.b4p.server.function.eval.AeB4PEvalFunction;
import org.activebpel.rt.bpel.function.AeUnresolvableException;
import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.impl.function.AeAbstractFunctionContext;

/**
 * Class that implements <code>FunctionContext</code> impls  for AeB4P 
 * internal functions. 
 */
public class AeB4PInternalCustomFunctionContext extends AeAbstractFunctionContext
{ 
   /** 
    * Overrides method to return aeb4p:eval() custom function. 
    * @see org.activebpel.rt.bpel.function.IAeFunctionContext#getFunction(java.lang.String)
    */
   public IAeFunction getFunction(String aFunctionName) throws AeUnresolvableException
   {
      if (AeB4PEvalFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeB4PEvalFunction();
      }
      else if (AeCreateHumanTaskContextFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeCreateHumanTaskContextFunction();
      }
      else if (AeB4PEvalTaskPresentationElementFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeB4PEvalTaskPresentationElementFunction();
      }
      else if (AeGetHTAttachmentsFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeGetHTAttachmentsFunction();
      }
      else if (AeAddHTAttachmentFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeAddHTAttachmentFunction();
      }
      else if (AeCopyHTAttachmentsByNameFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeCopyHTAttachmentsByNameFunction();
      }
      else if (AeRemoveHTAttachmentsByNameFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeRemoveHTAttachmentsByNameFunction();
      }
      else if (AeCopyHTAttachmentByIdFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeCopyHTAttachmentByIdFunction();
      }
      else if (AeRemoveHTAttachmentByIdFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeRemoveHTAttachmentByIdFunction();
      }
      else if (AeToIdentityQueryFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeToIdentityQueryFunction();
      }
      else if (AeDurationToDeadlineFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeDurationToDeadlineFunction();
      }
      else if (AeHtCopyAllMimeAttachmentsFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeHtCopyAllMimeAttachmentsFunction();
      }        
      else if (AeGetFinalizationDuration.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeGetFinalizationDuration();
      }
      else if (AeUnionFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         return new AeUnionFunction();
      }
      else if (AeB4PCreateProcessSnapshotTESTFunction.FUNCTION_NAME.equals(aFunctionName))
      {
         // fixme (PJ) remove AeB4PCreateProcessSnapshotTESTFunction function. This is currently used 
         // by bunit process AeOfflineB4PEvalFunctionBUnitTest::testEval()
         // (in process /test.org.activebpel.rt.b4p/bpel/engine-test/bpr-eval/bpel/aeb4p_eval_driver.bpel)
         return new AeB4PCreateProcessSnapshotTESTFunction();
      }
      throw new AeUnresolvableException(formatFunctionNotFoundErrorMsg(aFunctionName));
   }

}
