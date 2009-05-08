//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/AeAxisClientBase.java,v 1.3 2008/02/13 06:55:07 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;

import org.activebpel.rt.AeException;
import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.apache.axis.AxisFault;
import org.apache.axis.attachments.AttachmentPart;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPBodyElement;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.soap.MessageFactoryImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Base axis document-literal ws client used to access the task API web service
 */
public abstract class AeAxisClientBase
{

   /** Service endpoint. */
   private URL mEndpoint;
   /** Username. */
   private String mUsername;
   /** Password. */
   private String mPassword;

   /**
    * Default ctor.
    * @param aEndpoint
    */
   protected AeAxisClientBase(URL aEndpoint)
   {
      mEndpoint = aEndpoint;
   }

   /**
    * Constructs the client with username and password.
    * @param aEndpoint
    * @param aUsername
    * @param aPassword
    */
   protected AeAxisClientBase(URL aEndpoint, String aUsername, String aPassword)
   {
      mEndpoint = aEndpoint;
      mUsername = aUsername;
      mPassword = aPassword;
   }

   /**
    *
    * @return Returns endpoint url.
    */
   protected URL getEndpoint()
   {
      return mEndpoint;
   }

   /**
    *
    * @return returns username
    */
   protected String getPassword()
   {
      return mPassword;
   }

   /**
    * @return return password
    */
   protected String getUsername()
   {
      return mUsername;
   }

   /**
    * Creates a NS element and adds it to the parent element.
    * @param aParentNode
    * @param aNameSpace
    * @param aPrefix
    * @param aElementName
    * @param aText
    * @return element
    */
   protected Element createElementWithText(Node aParentNode, String aNameSpace, String aPrefix,
         String aElementName, String aText)
   {
      Node parent = aParentNode == null? AeXmlUtil.newDocument() : aParentNode;
      Element ele = AeXmlUtil.addElementNS(parent, aNameSpace,
            aPrefix + ":" + aElementName, aText); //$NON-NLS-1$
      ele.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:" + aPrefix, aNameSpace); //$NON-NLS-1$
      return ele;
   }
   
   /**
    * Creates and returns axis service call.
    * @return axis call.
    * @throws Exception
    */
   protected Call createCall() throws Exception
   {
      Service service = new Service();
      Call call = (Call) service.createCall();
      call.setTargetEndpointAddress(getEndpoint());
      if (AeUtil.notNullOrEmpty(mUsername) && AeUtil.notNullOrEmpty(mPassword))
      {
         call.setUsername(mUsername);
         call.setPassword(mPassword);
         call.getMessageContext().setProperty("HTTPPreemptive", "true"); //$NON-NLS-1$ //$NON-NLS-2$
      }
      return call;
   }

   /**
    * Sends the doc-lit request to the endpoint
    * @param aPrincipalName principal name
    * @param aRequest request document with optional attachments
    * @return Response containing response document and attachments (if any).
    * @throws Exception
    */
   protected AeHtClientResponse sendRequest(String aPrincipalName, AeHtClientRequest aRequest) throws Exception
   {
      aRequest.addExtensionHeader("principal", aPrincipalName); //$NON-NLS-1$
      return sendRequest(aRequest);
   }

   /**
    * Sends the doc-lit with optional attachments request to the endpoint and return an array with one or more
    * Objects. The response document is entry null if return type is null (void)
    *
    * @param aRequest request document with optional attachments
    * @return Response containing response document and attachments (if any).
    * @throws Exception
    */
   protected AeHtClientResponse sendRequest(AeHtClientRequest aRequest) throws Exception
   {
      SOAPBodyElement input[] = new SOAPBodyElement[1];
      input[0] = new SOAPBodyElement(aRequest.getDocument().getDocumentElement());
      Call call = createCall();
      Vector results = null;
      List attachmentResults = new ArrayList();
      try
      {
         // add attachments, if any
         DataHandler[] dataHandlers = aRequest.getDataHandlers();
         if (dataHandlers.length > 0)
         {
            // Construct the Axis MessageFactoryImpl explicitly instead of
            // going through SAAJ javax.xml.soap.MessageFactory.newInstance(),
            // because some of the application servers (notably WebLogic) don't
            // provide a SAAJ implementation that handles attachments the way
            // we want.
            MessageFactory factory = new MessageFactoryImpl();
            SOAPMessage msg = factory.createMessage();

            call.setProperty(Call.ATTACHMENT_ENCAPSULATION_FORMAT, Call.ATTACHMENT_ENCAPSULATION_FORMAT_MIME);
            for (int i = 0; i < dataHandlers.length; i++)
            {
               if (dataHandlers[i] != null)
               {
                  AttachmentPart ap = (AttachmentPart)msg.createAttachmentPart(dataHandlers[i]);
                  ap.setContentId(dataHandlers[i].getDataSource().getName());
                  call.addAttachmentPart(ap);
               }
            }
         }

         // build ae extension headers.
         Iterator extHeaders = aRequest.getExtensionHeaders().entrySet().iterator();
         while ( extHeaders.hasNext() )
         {
            Map.Entry mapEntry =  (Map.Entry) extHeaders.next();
            QName headerName = new QName(IAeConstants.ABX_NAMESPACE_URI, (String) mapEntry.getKey());
            SOAPHeaderElement header = new SOAPHeaderElement(headerName, (String) mapEntry.getValue());
            call.addHeader(header);
         }

         // invoke call
         results = (Vector) call.invoke(input);
         Iterator it = call.getMessageContext().getResponseMessage().getAttachments();
         while (it.hasNext())
         {
            AttachmentPart attPart = (AttachmentPart)it.next();
            attachmentResults.add(attPart.getDataHandler());
         }
      }
      catch(AxisFault fault)
      {
         throw createTaskServiceException(fault);
      }

      // get response document
      Document rdoc = null;
      if (results != null && results.size() > 0)
      {
         SOAPBodyElement elem  = (SOAPBodyElement) results.get(0);
         rdoc = AeXmlUtil.getOwnerDocument( elem.getAsDOM() );
      }

      // response attachments, if any
      DataHandler[] dataHandlers = new DataHandler[attachmentResults.size()];
      attachmentResults.toArray(dataHandlers);

      AeHtClientResponse response = new AeHtClientResponse(rdoc);
      response.setDataHandlers( dataHandlers );
      return response;
   }

   /**
    * Creates a activation data handler
    * @param aAttachmentData
    * @param aContentId
    * @param aContentType
    * @throws Exception
    */
   protected DataHandler createAttachmentDataHandler(Object aAttachmentData, String aContentId, String aContentType) throws Exception
   {
      DataSource dataSource;
      if ( aAttachmentData instanceof File )
      {
         File file = (File) aAttachmentData;
         if (!file.exists())
         {
            throw new FileNotFoundException(file.getAbsolutePath());
         }
         dataSource = new FileDataSource(file);
      }
      else
      {
         throw new AeException("Unsupported attachment content object type"); //$NON-NLS-1$
      }
      DataHandler dataHandler = new DataHandler( new AeTaskDataSourceWrapper(aContentId, aContentType, dataSource));
      return dataHandler;
   }

   /**
    * Maps given exception as a TaskFault is if the exception is an AxisFault with
    * fault code of http://www.activebpel.org/humanworkflow/2007/01/tasks/taskService.wsdl.
    * @param aError
    * @return AeTaskServiceException or original exception if the error cannot be mapped.
    */
   protected Exception createTaskServiceException(Exception aError)
   {
      if (!(aError instanceof AxisFault))
      {
         return aError;
      }
      AxisFault fault = (AxisFault) aError;
      Element details[] = fault.getFaultDetails();
      if (details == null || details.length == 0)
      {
         return fault;
      }
      AeTaskServiceException rval = createTaskServiceException(details[0], fault);
      return rval;
   }

   /**
    * Passes the document element for the fault data
    * @param aFaultDetailDoc
    * @param aError
    */
   protected AeTaskServiceException createTaskServiceException(Document aFaultDetailDoc, Exception aError)
   {
      return createTaskServiceException(aFaultDetailDoc.getDocumentElement(), aError);
   }

   /**
    * Maps the fault error document and fault code to a AeTaskServiceException.
    * @param aFaultDetail
    * @param aError
    * @return AeTaskServiceException
    */
   protected AeTaskServiceException createTaskServiceException(Element aFaultDetail, Exception aError)
   {
      return new AeTaskServiceException(aError.getMessage(), aError);
   }


}
