//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/attachments/AeHTAttachmentSerializer.java,v 1.2 2008/02/13 07:00:37 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function.attachments;

import java.util.Date;
import java.util.Iterator;

import org.activebpel.rt.AeException;
import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.attachment.IAeAttachmentItem;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.IAeProcessTaskConstants;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.IAeAttachmentManager;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;
import org.activebpel.wsio.IAeWebServiceAttachment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Serializes attachments in the attachment container and producees trt:attachments element
 */
public class AeHTAttachmentSerializer
{
   /**
    * Serializes attachments in the attachment container
    * @param aContext
    * @param aAttachmentContainer
    * @throws AeFunctionCallException
    */
   protected static Element serialize(IAeFunctionExecutionContext aContext, IAeAttachmentContainer aAttachmentContainer) throws AeFunctionCallException
   {
      Document doc = AeXmlUtil.newDocument();

      IAeAttachmentManager attachmentManager = aContext.getAbstractBpelObject().getProcess().getEngine().getAttachmentManager();
      // build root element
      Element attachments = AeXmlUtil.addElementNS(doc, IAeB4PConstants.AEB4P_NAMESPACE,"trt:attachments"); //$NON-NLS-1$
      attachments.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:trt", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
      attachments.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:api", IAeProcessTaskConstants.WSHT_API_NS); //$NON-NLS-1$
      for (Iterator iter = aAttachmentContainer.iterator(); iter.hasNext();)
      {
         IAeAttachmentItem attachment = (IAeAttachmentItem)iter.next();
         long attachmentId = attachment.getAttachmentId();

         // Create attachment Element for each attachment
         Element attachmentElem = AeXmlUtil.addElementNS(attachments, IAeProcessTaskConstants.WSHT_API_NS, "api:attachment"); //$NON-NLS-1$
         // Create attachment Element for each attachment
         Element attachmentInfo = AeXmlUtil.addElementNS(attachmentElem, IAeProcessTaskConstants.WSHT_API_NS, "api:attachmentInfo"); //$NON-NLS-1$

         // add attachment name
         String name = attachment.getHeader(IAeWebServiceAttachment.AE_CONTENT_LOCATION_MIME);
         if ( AeUtil.isNullOrEmpty(name) )
         {
            name = attachment.getHeader(IAeWebServiceAttachment.AE_CONTENT_ID_MIME);
         }
         name = AeUtil.isNullOrEmpty(name) ? IAeHTAttachmentConstants.DEFAULT_NAME_PREFIX + attachmentId : name;

         if (AeUtil.notNullOrEmpty(name))
            AeXmlUtil.addElementNS(attachmentInfo, IAeProcessTaskConstants.WSHT_API_NS, "api:name", name); //$NON-NLS-1$

         // add attachment access type
         String accessType = attachment.getHeader(IAeHTAttachmentConstants.ACCESS_TYPE);
         accessType = AeUtil.isNullOrEmpty(accessType) ? IAeHTAttachmentConstants.MIME_ACCESS_TYPE : accessType;

         if (AeUtil.notNullOrEmpty(accessType))
            AeXmlUtil.addElementNS(attachmentInfo, IAeProcessTaskConstants.WSHT_API_NS, "api:accessType", accessType); //$NON-NLS-1$

         // add attachment content type -  look at "URL-Content-Type" first.
         String contentType = attachment.getHeader(IAeHTAttachmentConstants.CONTENT_TYPE);
         if (AeUtil.isNullOrEmpty(contentType))
            contentType = attachment.getHeader(IAeWebServiceAttachment.AE_CONTENT_TYPE_MIME);
         AeXmlUtil.addElementNS(attachmentInfo, IAeProcessTaskConstants.WSHT_API_NS, "api:contentType", contentType); //$NON-NLS-1$
         // add attachment attached at
         String attachedAt = attachment.getHeader(IAeWebServiceAttachment.ATTACHED_AT);
         try
         {
            Long attachmentTime = new Long(attachedAt);
            attachedAt = new AeSchemaDateTime(new Date(attachmentTime.longValue())).toString();
         }
         catch(NumberFormatException ex)
         {
            // ignore it , since this means that attachedAt is in schema date time format
         }
         AeXmlUtil.addElementNS(attachmentInfo, IAeProcessTaskConstants.WSHT_API_NS, "api:attachedAt", attachedAt); //$NON-NLS-1$

         // add attachment attached by
         String attachedBy = attachment.getHeader(IAeWebServiceAttachment.AE_ATTACHED_BY);
         attachedBy = AeUtil.isNullOrEmpty(attachedBy) ?  IAeHTAttachmentConstants.DEFAULT_ATTACHED_BY : attachedBy;
         AeXmlUtil.addElementNS(attachmentInfo, IAeProcessTaskConstants.WSHT_API_NS, "api:attachedBy", attachedBy); //$NON-NLS-1$

         AeXmlUtil.addElementNS(attachmentInfo, IAeB4PConstants.AEB4P_NAMESPACE, "trt:attachmentId", String.valueOf(attachmentId)); //$NON-NLS-1$

         try
         {
            byte[] value = AeUtil.toByteArray(attachmentManager.deserialize(attachmentId));
            String valueString = ""; //$NON-NLS-1$
            if (accessType.equals(IAeHTAttachmentConstants.URL_ACCESS_TYPE))
               valueString = new String(value);

            AeXmlUtil.addElementNS(attachmentElem, IAeProcessTaskConstants.WSHT_API_NS, "api:value", valueString); //$NON-NLS-1$
         }
         catch (AeException ex)
         {
            throw new AeFunctionCallException(ex.getLocalizedMessage());
         }
      }

      return attachments;
   }

}
