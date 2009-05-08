//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeHtAddAttachmentsRequestSerializer.java,v 1.2 2008/02/17 21:51:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api.io;

import org.activebpel.rt.AeException;
import org.w3c.dom.Element;

/**
 * Serializes ws-ht attachement request as a SOAP attachment accessType.
 */
public class AeHtAddAttachmentsRequestSerializer extends AeHtSimpleRequestSerializer
{
   /** Attachment name */
   private String mName;
   /** Content type. */
   private String mContentType;

   /**
    * Ctor
    * @param aIdentifier
    * @param aName
    * @param aContentType
    */
   public AeHtAddAttachmentsRequestSerializer(String aIdentifier, String aName, String aContentType)
   {
      super("addAttachment", aIdentifier); //$NON-NLS-1$
      setName(aName);
      setContentType(aContentType);
   }

   /**
    * @return the name
    */
   public String getName()
   {
      return mName;
   }

   /**
    * @param aName the name to set
    */
   public void setName(String aName)
   {
      mName = aName;
   }

   /**
    * @return the conentType
    */
   public String getContentType()
   {
      return mContentType;
   }

   /**
    * @param aContentType the conentType to set
    */
   public void setContentType(String aContentType)
   {
      mContentType = aContentType;
   }

   public Element serialize(Element aParentElement) throws AeException
   {
      // call base to create root element along with the child htdt:addAttachment element.
      Element commandElement = super.serialize(aParentElement);
      // add attachment name element
      createElementWithText(commandElement, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "name", getName() ); //$NON-NLS-1$
      // access type - currently setto SOAP
      createElementWithText(commandElement, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "accessType", "MIME" ); //$NON-NLS-1$ //$NON-NLS-2$
      // content-type
      createElementWithText(commandElement, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "contentType", getContentType() ); //$NON-NLS-1$
      // attachment - none here since its a SOAP attachment.
      createElementWithText(commandElement, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "attachment", "" ); //$NON-NLS-1$ //$NON-NLS-2$
      return commandElement;
   }
}
