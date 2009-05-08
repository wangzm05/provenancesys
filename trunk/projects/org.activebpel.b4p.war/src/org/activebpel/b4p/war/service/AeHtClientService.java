//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/AeHtClientService.java,v 1.7 2008/02/17 21:08:40 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.service;

import java.net.URL;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;

import org.activebpel.rt.ht.api.AeGetTasksParam;
import org.activebpel.rt.ht.api.IAeHtWsIoConstants;
import org.activebpel.rt.ht.api.io.AeB4PTaskStateSimpleRequestSerializer;
import org.activebpel.rt.ht.api.io.AeGetMyTaskAbstractsSerializer;
import org.activebpel.rt.ht.api.io.AeGetMyTasksSerializer;
import org.activebpel.rt.ht.api.io.AeHtAddAttachmentsRequestSerializer;
import org.activebpel.rt.ht.api.io.AeHtSerializerBase;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;


/**
 * AeHtClientService is a doc-literal ws client which implements the wsht task client operations
 * port type.
 */
public class AeHtClientService extends AeAxisClientBase implements IAeTaskHtClientService
{
   // fault element names
   private static final QName ILLEGAL_ACCESS_ELEMENT = new QName(IAeHtWsIoConstants.WSHT_API_XSD_NAMESPACE, "illegalAccess"); //$NON-NLS-1$
   private static final QName ILLEGAL_STATE_ELEMENT = new QName(IAeHtWsIoConstants.WSHT_API_XSD_NAMESPACE, "illegalState"); //$NON-NLS-1$
   private static final QName ILLEGAL_ARGUMENT_ELEMENT = new QName(IAeHtWsIoConstants.WSHT_API_XSD_NAMESPACE, "illegalArgument"); //$NON-NLS-1$
   private static final QName RECIPIENT_NOT_ALLOWED_ELEMENT = new QName(IAeHtWsIoConstants.WSHT_API_XSD_NAMESPACE, "recipientNotAllowed"); //$NON-NLS-1$
   // identity service fault
   private static final QName IDENTITY_SERVICE_FAULT_ELEMENT = new QName(IAeHtWsIoConstants.IDENTITY_SERVICE_NAMESPACE, "identityFault"); //$NON-NLS-1$

   /**
    * Ctor.
    * @param aEndpoint
    */
   public AeHtClientService(URL aEndpoint)
   {
      super(aEndpoint);
   }

   /**
    * Constructs the client with username and password.
    * @param aEndpoint
    * @param aUsername
    * @param aPassword
    */
   public AeHtClientService(URL aEndpoint, String aUsername, String aPassword)
   {
      super(aEndpoint, aUsername, aPassword);
   }

   /**
    * @see org.activebpel.b4p.war.service.AeAxisClientBase#createTaskServiceException(org.w3c.dom.Element, java.lang.Exception)
    */
   protected AeTaskServiceException createTaskServiceException(Element aFaultDetail, Exception aError)
   {
      //return new AeTaskServiceException(aError.getMessage(), aError);
      if (aFaultDetail == null)
      {
         return new AeTaskServiceException(aError.getMessage(), aError);
      }

      QName rootElementName = AeXmlUtil.getElementType(aFaultDetail);

      AeTaskServiceException rval = null;
      if ( ILLEGAL_ACCESS_ELEMENT.equals(rootElementName))
      {
         rval = new AeTaskFaultException(AeTaskFaultException.ILLEGAL_ACCESS, AeXmlUtil.getText(aFaultDetail));
      }
      else if (ILLEGAL_ARGUMENT_ELEMENT.equals(rootElementName))
      {
         rval = new AeTaskFaultException(AeTaskFaultException.ILLEGAL_ARGUMENT, AeXmlUtil.getText(aFaultDetail));
      }
      else if (RECIPIENT_NOT_ALLOWED_ELEMENT.equals(rootElementName))
      {
         rval = new AeTaskFaultException(AeTaskFaultException.RECIPIENT_NOT_ALLOWED, AeXmlUtil.getText(aFaultDetail));
      }
      else if (ILLEGAL_STATE_ELEMENT.equals(rootElementName))
      {
         String state = AeXPathUtil.selectText(aFaultDetail, "htdat:status", "htdat", IAeHtWsIoConstants.WSHT_API_XSD_NAMESPACE); //$NON-NLS-1$ //$NON-NLS-2$
         String message = AeXPathUtil.selectText(aFaultDetail, "htdat:message", "htdat", IAeHtWsIoConstants.WSHT_API_XSD_NAMESPACE); //$NON-NLS-1$ //$NON-NLS-2$
         AeTaskFaultException exception = new AeTaskFaultException(AeTaskFaultException.ILLEGAL_STATE, message);
         exception.setState(state);
         rval = exception;
      }
      else if (IDENTITY_SERVICE_FAULT_ELEMENT.equals(rootElementName))
      {
         String code = AeXPathUtil.selectText(aFaultDetail, "aeidsvc:code", "aeidsvc", IAeHtWsIoConstants.IDENTITY_SERVICE_NAMESPACE); //$NON-NLS-1$ //$NON-NLS-2$
         String message = AeXPathUtil.selectText(aFaultDetail, "aeidsvc:message", "aeidsvc",IAeHtWsIoConstants.IDENTITY_SERVICE_NAMESPACE); //$NON-NLS-1$ //$NON-NLS-2$
         rval = new AeTaskServiceException(message + " (code: " + code + ")" , aError); //$NON-NLS-1$ //$NON-NLS-2$
      }
      else if (AeUtil.notNullOrEmpty(aError.getMessage()) )
      {
         rval = new AeTaskServiceException(aError.getMessage(), aError);
      }
      else
      {
         rval = new AeTaskServiceException(AeXmlUtil.serialize(aFaultDetail), aError);
      }
      return rval;
   }

   /**
    * @see org.activebpel.b4p.war.service.IAeTaskHtClientService#getMyTaskAbstracts(org.activebpel.rt.ht.api.AeGetTasksParam)
    */
   public Element getMyTaskAbstracts(AeGetTasksParam aParam) throws AeTaskServiceException
   {
      AeGetMyTaskAbstractsSerializer serializer = new AeGetMyTaskAbstractsSerializer(aParam);
      return sendRequest(serializer);
   }

   /**
    * @see org.activebpel.b4p.war.service.IAeTaskHtClientService#getMyTasks(org.activebpel.rt.ht.api.AeGetTasksParam)
    */
   public Element getMyTasks(AeGetTasksParam aParam) throws AeTaskServiceException
   {
      AeGetMyTasksSerializer serializer = new AeGetMyTasksSerializer(aParam);
      return sendRequest(serializer);
   }

   /**
    * Overrides method to
    * @see org.activebpel.b4p.war.service.IAeTaskHtClientService#executeRequest(org.w3c.dom.Element)
    */
   public Element executeRequest(Element aRequestMessageElement) throws AeTaskServiceException
   {
      return sendMessageElement(aRequestMessageElement);
   }

   /***
    * @see org.activebpel.b4p.war.service.IAeTaskHtClientService#addAttachment(java.lang.String, java.lang.String, java.lang.String, java.lang.Object)
    */
   public void addAttachment(String aIdentifier, String aAttachmentName, String aContentType, Object aData) throws AeTaskServiceException
   {
      AeHtAddAttachmentsRequestSerializer ser = new AeHtAddAttachmentsRequestSerializer(aIdentifier, aAttachmentName, aContentType);
      DataHandler[] dataHandlers = new DataHandler[1];
      try
      {
         dataHandlers[0] = createAttachmentDataHandler(aData, aAttachmentName, aContentType);
      }
      catch(Exception aex)
      {
         throw new AeTaskServiceException(aex);
      }
      sendRequest(ser, dataHandlers);
   }

   /**
    * Sends simple AE B4P task state wsdl message requests that are based on the identifier.
    * @param aRequestName
    * @param aIdentifier
    * @return response element if any or <code>null</code> otherwise.
    * @throws AeTaskServiceException
    */
   protected Element sendB4PTaskStateSimpleRequest(String aRequestName, String aIdentifier) throws AeTaskServiceException
   {
      AeB4PTaskStateSimpleRequestSerializer ser = new AeB4PTaskStateSimpleRequestSerializer(aRequestName, aIdentifier);
      return sendRequest(ser);
   }

   /**
    * Sends the message element generated by the serializer as document literal request to
    * the wsht api task client service.
    * @param aSerializer
    * @return response element if any or <code>null</code> otherwise.
    * @throws AeTaskServiceException
    */
   protected Element sendRequest(AeHtSerializerBase aSerializer) throws AeTaskServiceException
   {
      return sendRequest(aSerializer, null);
   }

   /**
    * Sends the message element generated by the serializer as document literal request to
    * the wsht api task client service.
    * @param aSerializer
    * @param aDataHandlers
    * @throws AeTaskServiceException
    */
   protected Element sendRequest(AeHtSerializerBase aSerializer,  DataHandler[] aDataHandlers) throws AeTaskServiceException
   {
      try
      {
         return sendMessageElement( aSerializer.serialize(), aDataHandlers );
      }
      catch(AeTaskServiceException atex)
      {
         throw atex;
      }
      catch(Exception aex)
      {
         throw new AeTaskServiceException(aex);
      }
   }

   /**
    * Sends the given message element (document-literal) to task service server.
    * @param aRequestMessageElement message element.
    * @return response element if any or <code>null</code> otherwise.
    * @throws AeTaskServiceException
    */

   protected Element sendMessageElement(Element aRequestMessageElement) throws AeTaskServiceException
   {
      return sendMessageElement(aRequestMessageElement, null);
   }

   /**
    * Sends the given message element (document-literal) to task service server.
    * @param aRequestMessageElement
    * @param aDataHandlers SOAP attachment data
    * @return response element if any or <code>null</code> otherwise.
    * @throws AeTaskServiceException
    */
   protected Element sendMessageElement(Element aRequestMessageElement, DataHandler[] aDataHandlers) throws AeTaskServiceException
   {
      AeHtClientRequest request = new AeHtClientRequest(aRequestMessageElement);
      request.setDataHandlers(aDataHandlers);
      AeHtClientResponse response = sendClientRequest(request);
      if (response.getDocument() != null)
      {
         return response.getDocument().getDocumentElement();
      }
      else
      {
         // void or one-way requests.
         return null;
      }
   }

   /**
    * Basic send request.
    * @param aClientRequest
    * @throws AeTaskServiceException
    */
   protected AeHtClientResponse sendClientRequest(AeHtClientRequest aClientRequest) throws AeTaskServiceException
   {
      AeHtClientResponse response;
      try
      {
         response = sendRequest(aClientRequest);
      }
      catch(AeTaskServiceException atex)
      {
         throw atex;
      }
      catch(Exception aex)
      {
         throw new AeTaskServiceException(aex);
      }
      return response;
   }
}
