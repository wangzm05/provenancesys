//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/attachments/AeHtCopyAllMimeAttachmentsFunction.java,v 1.1.4.1 2008/04/21 16:08:59 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function.attachments;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.attachment.IAeAttachmentItem;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.function.attachment.AeAbstractAttachmentFunction;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class representing the function used by ht expression evaluators to handle the BPEL -
 * copyAllMimeAttachments(fromVarnames, toVarname, principalName) function call.
 * <p>
 * <em>Description:</em> Copies all MIME attachments from the variables identified by the fromVarnames to the
 * variable identified by the toVarname.
 * </p>
 * <em>Parameters:</em>
 * <ul>
 * <li>fromVarnames string containing the list of names of the variables to copy attachments from</li>
 * <li>toVarname string containing the name of the variable to copy attachments to</li>
 * </ul>
 * <p>
 * <em>Return:</em> list of &lt;trt:attachmentId&gt; elements in a container (wrapper)  &lt;attachmentIds&gt; element
 * </p>
 */
public class AeHtCopyAllMimeAttachmentsFunction extends AeAbstractAttachmentFunction
{
   /** The name of the function implemented */
   public static final String FUNCTION_NAME = "copyAllMimeAttachments"; //$NON-NLS-1$

   /**
    * Constructor.
    */
   public AeHtCopyAllMimeAttachmentsFunction()
   {
      super(FUNCTION_NAME);
   }

   /**
    * Overrides method to  copy all MIME attachments from the variables identified by the fromVarnames to the
    * variable identified by the toVarname and return a list of &lt;trt:attachmentId&gt; elements
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {
      // Required arguments
      // aArgs[0] : A bpel variable name
      // aArgs[1] : api:attachment element
      // validate argument count
      if (aArgs.size() != 2)
      {
         throwFunctionException(INVALID_PARAMS, FUNCTION_NAME);
      }
      // Get the from and to variable object from the first and second function argument
      String fromVariableNames = getStringArg(aArgs,0);
      String toVariableName = getStringArg(aArgs,1);

      // Resolve the toVariable first.
      IAeVariable toVariable = getVariable(aContext.getAbstractBpelObject(), toVariableName);

      // Now get the list of from variables
      Collection fromVariables = resolveVariables(aContext, fromVariableNames, toVariableName);

      // attachment id list container element.
      Document doc = AeXmlUtil.newDocument();
      Element attachmentIdListEle = AeXmlUtil.addElementNS(doc, IAeB4PConstants.AEB4P_NAMESPACE,"trt:attachmentIdList"); //$NON-NLS-1$
      attachmentIdListEle.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:trt", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$

      for (Iterator varIter = fromVariables.iterator(); varIter.hasNext(); )
      {
         IAeVariable fromVariable = (IAeVariable) varIter.next();
         // copy all attachments
         toVariable.getAttachmentData().addAll(fromVariable.getAttachmentData());
         // grab list of attachment-ids and add them into a list of <trt:attachmentId>.
         for(Iterator attItemIter = fromVariable.getAttachmentData().getAttachmentItems(); attItemIter.hasNext(); )
         {
            IAeAttachmentItem item = (IAeAttachmentItem) attItemIter.next();
            Element idElem = AeXmlUtil.addElementNS(attachmentIdListEle, IAeB4PConstants.AEB4P_NAMESPACE,
                     "trt:attachmentId", String.valueOf(item.getAttachmentId() ) ); //$NON-NLS-1$
            idElem.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:trt", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
         }
      }
      return attachmentIdListEle;
   }
}
