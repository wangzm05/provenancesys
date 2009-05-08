// $Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/servlet/AeWorkFlowAttachmentDownloadServlet.java,v 1.5 2008/02/20 15:58:19 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.web.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activebpel.b4p.war.AeMessages;
import org.activebpel.b4p.war.AeWorkFlowApplicationFactory;
import org.activebpel.b4p.war.service.AeAttachmentData;
import org.activebpel.b4p.war.service.AeAttachmentNotFoundException;
import org.activebpel.b4p.war.service.AeHtCredentials;
import org.activebpel.b4p.war.service.IAeTaskAeClientService;
import org.activebpel.b4p.war.web.bean.AeUserSession;
import org.activebpel.rt.util.AeCloser;
import org.activebpel.rt.util.AeUtil;

/**
 * Responsible for downloading the contents of an attachment and streaming it to a client.
 */
public class AeWorkFlowAttachmentDownloadServlet extends AeWorkFlowTaskServletBase
{

   /**
    * Overrides method to fetch named attachment from the server and stream it to the client browser.
    * @see org.activebpel.b4p.war.web.servlet.AeWorkFlowTaskServletBase#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String, java.lang.String)
    */
   protected void handleRequest(HttpServletRequest aRequest, HttpServletResponse aResponse, String aPrincipalName, String aTaskId) throws ServletException, IOException
   {
      String attachmentId = aRequest.getParameter("attachmentId"); //$NON-NLS-1$
      if (AeUtil.isNullOrEmpty(attachmentId))
      {
         aResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, AeMessages.getString("AeWorkFlowAttachmentDownloadServlet.MISSING_ATTACHMENT_ID")); //$NON-NLS-1$
         return;
      }

      AeAttachmentData data = null;

      try
      {
         data = getAttachmentData(aRequest, aPrincipalName, aTaskId, attachmentId);
      }
      catch(AeAttachmentNotFoundException anfe)
      {
         aResponse.sendError(HttpServletResponse.SC_NOT_FOUND, AeMessages.getString("AeWorkFlowAttachmentDownloadServlet.ATTACHMENT_NOT_FOUND")); //$NON-NLS-1$
         return;
      }
      catch(Throwable t)
      {
         aResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, AeMessages.format("AeWorkFlowAttachmentDownloadServlet.GET_ATTACHMENT_ERROR", t.getMessage())); //$NON-NLS-1$
         return;
      }

      if (!data.isHasData())
      {
         aResponse.sendError(HttpServletResponse.SC_NO_CONTENT, AeMessages.getString("AeWorkFlowAttachmentDownloadServlet.GET_ATTACHMENT_ERROR")); //$NON-NLS-1$
         return;
      }
      streamAttachment(aResponse, data);
   }


   /**
    * Returns the AeAttachmentData for the given taskref and attachment name.
    * @param aRequest
    * @param aPrincipal
    * @param aTaskId
    * @param aAttachmentId
    * @return AeAttachmentData
    * @throws AeAttachmentNotFoundException
    * @throws Exception
    */
   protected AeAttachmentData getAttachmentData(HttpServletRequest aRequest, String aPrincipal, String aTaskId, String aAttachmentId) throws Exception
   {
      AeUserSession userSession = getAuthorizedUser(aRequest);
      if (userSession != null)
      {
         AeHtCredentials credentials = new AeHtCredentials(userSession.getPrincipalName(), userSession.getPassword() );
         IAeTaskAeClientService service = AeWorkFlowApplicationFactory.createAeClientService( credentials );
         AeAttachmentData data = service.getAttachmentData(aTaskId, aAttachmentId);
         return data;
      }
      else
      {
         return null;
      }
   }

   /**
    * Gets the attachment from the server and streams it to the response.
    * @param aResponse
    * @param aAttachmentItemId
    * @param mimeType
    * @throws IOException
    */
   private void streamAttachment(HttpServletResponse aResponse, AeAttachmentData aData) throws IOException
   {
      try
      {
         BufferedInputStream in = new BufferedInputStream(aData.getDataHandler().getInputStream());
         int inLength = -1;
         BufferedOutputStream out = new BufferedOutputStream(setupStream( aData.getContentType(),
               inLength, aData.getName(), aResponse));
         consume(in, out);
         out.flush();
         out.close();
      }
      catch (Exception ex)
      {
         aResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, AeMessages.format("AeWorkFlowTaskServletBase.ERROR", ex.getMessage())); //$NON-NLS-1$
      }
   }

   /**
    * Consumes the binary inputstream, writing its contents to the binary outputstream.
    * @param aReader
    * @param aWriter
    * @throws IOException
    */
   private void consume(BufferedInputStream aReader, BufferedOutputStream aWriter) throws IOException
   {
      try
      {
         byte[] buff = new byte[1024 * 4];
         int bytesRead;
         // Simple read/write loop.
         while (-1 != (bytesRead = aReader.read(buff, 0, buff.length)))
         {
            aWriter.write(buff, 0, bytesRead);
         }
      }
      finally
      {
         AeCloser.close(aReader);
      }
   }

   /**
    * Sets up the headers needed for the binary outputstream to do the streaming and returns the binary
    * outputstream.
    * @param aMimeType
    * @param aResponse
    * @return
    * @throws IOException
    */
   private ServletOutputStream setupStream( String aMimeType, int aLength,
         String aFileName, HttpServletResponse aResponse) throws IOException
   {
      aResponse.setContentType((aMimeType != null && aMimeType.length() > 0) ? aMimeType
            : "application/octet-stream"); //$NON-NLS-1$

      if ( aLength > 0 )
      {
         aResponse.setContentLength(aLength);
      }
      aResponse.setHeader("Content-disposition", "attachment;filename=" + aFileName); //$NON-NLS-1$ //$NON-NLS-2$
      return aResponse.getOutputStream();
   }
}
