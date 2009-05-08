//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeHtGetAttachmentsRequestSerializer.java,v 1.1 2008/01/18 22:51:53 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api.io;

import org.activebpel.rt.AeException;
import org.w3c.dom.Element;

/**
 * Creates a wsht api:GetAttachments request element
 */
public class AeHtGetAttachmentsRequestSerializer extends AeHtSimpleRequestSerializer
{
   /** Attachment name */
   private String mAttachmentName;

   /**
    * Ctor.
    * @param aIdentifier
    * @param aAttachmentName
    */
   public AeHtGetAttachmentsRequestSerializer(String aIdentifier, String aAttachmentName)
   {
      super("getAttachments", aIdentifier); //$NON-NLS-1$
      mAttachmentName = aAttachmentName;
   }

   /**
    * @return the attachmentName
    */
   protected String getAttachmentName()
   {
      return mAttachmentName;
   }

   /**
    * @see org.activebpel.rt.ht.api.io.AeHtSimpleRequestSerializer#serialize(org.w3c.dom.Element)
    */
   public Element serialize(Element aParentElement) throws AeException
   {
      // call base to create root element along with the child htdt:getAttachments element.
      Element commandElement = super.serialize(aParentElement);
      // add attachment name element
      createElementWithText(commandElement, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "attachmentName", getAttachmentName() ); //$NON-NLS-1$
      return commandElement;
   }
}
