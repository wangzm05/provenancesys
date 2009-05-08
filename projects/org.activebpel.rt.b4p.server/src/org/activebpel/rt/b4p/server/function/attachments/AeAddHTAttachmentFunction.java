//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/attachments/AeAddHTAttachmentFunction.java,v 1.1 2008/02/11 17:09:24 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function.attachments;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.attachment.IAeAttachmentItem;
import org.activebpel.rt.base64.Base64;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;
import org.activebpel.rt.bpel.impl.function.attachment.AeCreateAttachmentFunction;
import org.activebpel.rt.util.AeMimeUtil;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;
import org.activebpel.wsio.IAeWebServiceAttachment;
import org.w3c.dom.Element;

/**
 * This class implements custom ht function to add attachment. 
 */
public class AeAddHTAttachmentFunction extends AeAbstractBpelFunction
{
   /** The name of the function implemented */
   public static final String FUNCTION_NAME = "addHTAttachment"; //$NON-NLS-1$

   /** Map for xpath queries */
   private static final Map sNSMap = Collections.singletonMap("api", "http://www.example.org/WS-HT/api");  //$NON-NLS-1$//$NON-NLS-2$

   /**
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
      // Get varaible name arg
      String variableName = (String) aArgs.get(0);
      // Get attachment element arg
      Element apiAttachment = (Element) aArgs.get(1);
      // Extract elements inside api:attachmentInfo and create a Map of these element names to values 
      Map headers = createHeaderMap(apiAttachment);
      // Load attachment content
      String content = getContent(apiAttachment);

      AeCreateAttachmentFunction createAttachment = new AeCreateAttachmentFunction();
      IAeAttachmentItem attachment = createAttachment.addVariableAttachment(variableName, headers, content, aContext);
      // Return attachment id
      return new Long(attachment.getAttachmentId());
   }

   /**
    * Create a map of element names to their values inside attachmentInfo 
    * @param aApiAttachment
    */
   private Map createHeaderMap(Element aApiAttachment) throws AeFunctionCallException
   {
      Map headerMap = new HashMap();
      // Extract Attachment Name
      String name = AeXPathUtil.selectText(aApiAttachment, "api:attachmentInfo/api:name", sNSMap); //$NON-NLS-1$
      // Extract accessType
      String accessType = AeXPathUtil.selectText(aApiAttachment, "api:attachmentInfo/api:accessType", sNSMap); //$NON-NLS-1$
      // Extract Content Type
      String contentType = AeXPathUtil.selectText(aApiAttachment, "api:attachmentInfo/api:contentType", sNSMap); //$NON-NLS-1$
      // Extract attached at
      String attachedAt = AeXPathUtil.selectText(aApiAttachment, "api:attachmentInfo/api:attachedAt", sNSMap); //$NON-NLS-1$
      // Extract attached by
      String attachedBy = AeXPathUtil.selectText(aApiAttachment, "api:attachmentInfo/api:attachedBy", sNSMap); //$NON-NLS-1$

      accessType = accessType.equals(IAeHTAttachmentConstants.INLINE_ACCESS_TYPE)  ? IAeHTAttachmentConstants.MIME_ACCESS_TYPE : accessType;  
            
      if (AeUtil.isNullOrEmpty(contentType) || AeUtil.isNullOrEmpty(accessType))
         throwFunctionException(CREATE_ATTACHMENT_FAILED, getFunctionName());

      // Add attachment properties to Map
      if (IAeHTAttachmentConstants.MIME_ACCESS_TYPE.equals(accessType))
      {
         headerMap.put(AeMimeUtil.CONTENT_TYPE_ATTRIBUTE, contentType);
      }
      else
      {
         headerMap.put(AeMimeUtil.CONTENT_TYPE_ATTRIBUTE, "text/url"); //$NON-NLS-1$
         headerMap.put(IAeHTAttachmentConstants.CONTENT_TYPE, contentType); 
      }
      
      headerMap.put(IAeHTAttachmentConstants.ACCESS_TYPE, accessType);
      headerMap.put(IAeWebServiceAttachment.AE_CONTENT_LOCATION_MIME, AeUtil.notNullOrEmpty(name) ? name : ""); //$NON-NLS-1$
      headerMap.put(IAeHTAttachmentConstants.ATTACHED_AT, AeUtil.notNullOrEmpty(attachedAt) ? attachedAt : new AeSchemaDateTime(new Date()).toString());
      headerMap.put(IAeHTAttachmentConstants.ATTACHED_BY, AeUtil.notNullOrEmpty(attachedBy) ? attachedBy : "anonymous"); //$NON-NLS-1$

      return headerMap;
   }
   
   /**
    * Returns attachment content as base64 encoded string 
    * @param aApiAttachment
    * @throws AeFunctionCallException
    */
   private String getContent(Element aApiAttachment) throws AeFunctionCallException
   {
      String content = null;
      // Extract access Type and contetnt
      String accessType = AeXPathUtil.selectText(aApiAttachment, "api:attachmentInfo/api:accessType", sNSMap); //$NON-NLS-1$
      String attachmentContent = AeXPathUtil.selectText(aApiAttachment, "api:value", sNSMap); //$NON-NLS-1$      

      if ( (accessType == null) || (attachmentContent == null) )
         throwFunctionException(CREATE_ATTACHMENT_FAILED, getFunctionName());
      
      
      if (accessType.equals(IAeHTAttachmentConstants.INLINE_ACCESS_TYPE))
      {
         content = attachmentContent;
      }
      else if (accessType.equals(IAeHTAttachmentConstants.URL_ACCESS_TYPE))
      {
         content = Base64.encodeBytes(attachmentContent.getBytes());
      }
      return content;
   }
   
}
