//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/AeInternalClientService.java,v 1.5.4.1 2008/04/21 16:04:36 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.service;

import java.net.URL;

import javax.activation.DataHandler;

import org.activebpel.b4p.war.AeMessages;
import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.api.AeAttachmentInfo;
import org.activebpel.rt.ht.api.AeGetTasksParam;
import org.activebpel.rt.ht.api.IAeHtWsIoConstants;
import org.activebpel.rt.ht.api.io.AeHtAttachmentInfoDeserializerBase;
import org.w3c.dom.Element;

/**
 * Implements the internal ae specific task client operations.
 */
public class AeInternalClientService extends AeHtClientService implements IAeTaskAeClientService
{

   /**
    * Ctor.
    * @param aEndpoint
    */
   public AeInternalClientService(URL aEndpoint)
   {
      super(aEndpoint);
   }

   /**
    * Ctor
    * @param aEndpoint
    * @param aUsername
    * @param aPassword
    */
   public AeInternalClientService(URL aEndpoint, String aUsername, String aPassword)
   {
      super(aEndpoint, aUsername, aPassword);
   }

   /**
    * @see org.activebpel.b4p.war.service.IAeTaskAeClientService#authenticate()
    */
   public boolean authenticate() throws AeTaskServiceException
   {
      try
      {
         Element authorizeRequestElem = createElementWithText(null, IAeHtWsIoConstants.AEB4P_TASKSTATE_NAMESPACE, "tss", "authorizeRequest", null); //$NON-NLS-1$ //$NON-NLS-2$
         Element authorizeResponse = sendMessageElement(authorizeRequestElem);
         return authorizeResponse != null;
      }
      catch(AeTaskServiceException tse)
      {
         // fixme (PJ) catch axis http related faults where faultCode = QName({http://xml.apache.org/axis/}HTTP) and details could be <HttpErrorCode xmlns="http://xml.apache.org/axis/">401</HttpErrorCode>
         throw tse;
      }
      catch(Exception e)
      {
         throw new AeTaskServiceException(e);
      }
   }

   /**
    * @see org.activebpel.b4p.war.service.IAeTaskAeClientService#getTasks(org.activebpel.rt.ht.api.AeGetTasksParam)
    */
   public Element getTasks(AeGetTasksParam aParam) throws AeTaskServiceException
   {
      AeGetTasksSerializer serializer = new AeGetTasksSerializer(aParam);
      return sendRequest(serializer);
   }

   /**
    * @see org.activebpel.b4p.war.service.IAeTaskAeClientService#getTaskInstance(java.lang.String)
    */
   public Element getTaskInstance(String aIdentifier) throws AeTaskServiceException
   {
      Element requestElem = createElementWithText(null, IAeHtWsIoConstants.AEB4P_TASKSTATE_NAMESPACE, "tss", "getTaskInstance", null); //$NON-NLS-1$ //$NON-NLS-2$
      createElementWithText( requestElem, IAeHtWsIoConstants.AEB4P_NAMESPACE, "aeb4p", "identifier", aIdentifier); //$NON-NLS-1$ //$NON-NLS-2$
      Element responseElem = sendMessageElement(requestElem);
      return responseElem;
   }

   /**
    * @see org.activebpel.b4p.war.service.IAeTaskAeClientService#getAttachmentData(java.lang.String, java.lang.String)
    */
   public AeAttachmentData getAttachmentData(String aIdentifier, String aAttachmentId)
      throws AeTaskServiceException
   {
      Element getAttachmentByIdElem = createElementWithText(null, IAeHtWsIoConstants.AEB4P_TASKSTATE_NAMESPACE, "tss", "getAttachmentById", null); //$NON-NLS-1$ //$NON-NLS-2$
      createElementWithText( getAttachmentByIdElem, IAeHtWsIoConstants.AEB4P_NAMESPACE, "aeb4p", "identifier", aIdentifier); //$NON-NLS-1$ //$NON-NLS-2$
      createElementWithText( getAttachmentByIdElem, IAeHtWsIoConstants.AEB4P_TASKSTATE_NAMESPACE, "aeb4p", "attachmentId", aAttachmentId); //$NON-NLS-1$ //$NON-NLS-2$

      // send request
      AeHtClientRequest request = new AeHtClientRequest(getAttachmentByIdElem);
      AeHtClientResponse response = sendClientRequest(request);
      if (response.getDocument() != null && response.getDataHandlers() != null && response.getDataHandlers().length > 0)
      {
         return createAttachmentData(response.getDocument().getDocumentElement(), response.getDataHandlers()[0], aIdentifier, aAttachmentId);
      }
      else
      {
         String args [] = {aAttachmentId, aIdentifier};
         throw new AeTaskServiceException(AeMessages.format("AeInternalClientService.AttachmentNotFoundMessage", args)); //$NON-NLS-1$
      }
   }

   /**
    * Retrieves and returns a simple wrapper around attachment.
    * @param aGetAttachmentByIdResponseEle
    * @param aHandler
    * @param aIdentifier
    * @param aAttachmentId
    * @throws AeTaskServiceException
    */
   protected AeAttachmentData createAttachmentData(Element aGetAttachmentByIdResponseEle, DataHandler aHandler,
         String aIdentifier, String aAttachmentId) throws AeTaskServiceException
   {
      try
      {
         AeGetAttachmentByIdResponseDeserializer des = new AeGetAttachmentByIdResponseDeserializer(aIdentifier, aGetAttachmentByIdResponseEle);
         AeAttachmentInfo info = des.getAttachmentInfo();
         AeAttachmentData data = new AeAttachmentData(info, aHandler);
         return data;
      }
      catch(Exception aex)
      {
         throw new AeTaskServiceException(aex);
      }
   }

   /**
    * &lt;tsst:getAttachmentByIdResponse&gt; deserializer.
    */
   private class AeGetAttachmentByIdResponseDeserializer extends AeHtAttachmentInfoDeserializerBase
   {
      /** ht attachmment info. */
      private AeAttachmentInfo mInfo;
      /**
       * Ctor.
       * @param aGetAttachmentByIdResponseEle
       */
      protected AeGetAttachmentByIdResponseDeserializer(String aTaskId, Element aGetAttachmentByIdResponseEle)
      {
         setTaskId(aTaskId);
         setElement(aGetAttachmentByIdResponseEle);
      }

      /**
       * @return deserialized AeAttachmentInfo
       * @throws AeException
       */
      protected AeAttachmentInfo getAttachmentInfo() throws AeException
      {
         if (mInfo == null)
         {
            mInfo = deserializeAttachmentInfo(getTaskId() , selectElement(getElement(), "//htda:attachmentInfo") ); //$NON-NLS-1$
         }
         return mInfo;
      }
   }

}
