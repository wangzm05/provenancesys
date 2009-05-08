//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/servlet/AeWorkFlowRenderTaskXslServlet.java,v 1.5.4.1 2008/04/11 17:59:21 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.web.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activebpel.b4p.war.AeMessages;
import org.activebpel.b4p.war.AeUploadFileItem;
import org.activebpel.b4p.war.service.AeHtCredentials;
import org.activebpel.b4p.war.service.AeTaskFaultException;
import org.activebpel.b4p.war.web.bean.AeUserSession;
import org.activebpel.b4p.war.xsl.AeImportNotFoundTransformerException;
import org.activebpel.b4p.war.xsl.AeXslTaskInputParameters;
import org.activebpel.b4p.war.xsl.AeXslTaskRenderingErrors;
import org.activebpel.b4p.war.xsl.AeXslTaskRequestProcessor;
import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeUtil;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.w3c.dom.Document;

/**
 *<p>
 * AeWorkFlowRenderTaskXslServlet is responsible for handling AE xls task rendering method.
 * This servlet fetches the Task document from tbe AB4People for server to look up the
 * two required XSL style sheets (for redering the output and processing the form data input).
 *</p>
 *<p>
 * If the HTTP method is POST, then it is assumed that some data was submitted by the user.
 * The form data is first converted to a simple xml document containing form data name
 * and value parameters.
 *</p>
 *<p>
 * The form data parameter document is then transformed into a task command xml document
 * using the xsl style sheet specified by the task. The transformed command document is
 * then executed. (E.g. claim task, setoutput etc.)
 *</p>
 *<p>
 * Finally the task document (which contains the task def, state and permissions) is transformed
 * using the presentation xsl stylesheet to render the final html (or other markup).
 *</p>
 */
public class AeWorkFlowRenderTaskXslServlet extends AeWorkFlowTaskServletBase
{
   /**
    * Overrides method to send the request to the task inbox welcome page.
    * @see org.activebpel.b4p.war.web.servlet.AeWorkFlowTaskServletBase#handleNotAuthenticated(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
   protected void handleNotAuthenticated(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException
   {
      aResponse.sendRedirect("/index.jsp"); //$NON-NLS-1$
   }

   /**
    * Overrides method to process html form data into task commands and output task detail
    * page mark up by transforming the task document with xsl stylesheet.
    * @see org.activebpel.b4p.war.web.servlet.AeWorkFlowTaskServletBase#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String, java.lang.String)
    */
   protected void handleRequest(HttpServletRequest aRequest, HttpServletResponse aResponse,
         String aPrincipalName, String aTaskId) throws ServletException, IOException
   {
      AeUserSession userSession = getAuthorizedUser(aRequest);

      //
      // map to hold simple name value sets
      Map paramMap = new HashMap();
      // map containing form data name and AeUploadFileItem (which represents an uploaded file).
      Map fileMap = new HashMap();
      try
      {
         // collect the name/value and name/AeUploadFileItem items from the servlet request.
         processFormParamsAndFileUploads(aRequest, paramMap, fileMap);
      }
      catch(Exception e)
      {
         throw new ServletException(e);
      }

      // create document to contain error messages.
      AeXslTaskRenderingErrors xslErrors = new AeXslTaskRenderingErrors(aPrincipalName, aTaskId);

      // convert html form data parameters to a document. (errors (messages) conversion will be added to the error document).
      AeXslTaskInputParameters xslHtmlParams = createXslHtmlParams(aRequest, aPrincipalName, aTaskId, paramMap, xslErrors);

      // Create processor to handle input request (commands) and rendering of output transformation.
      AeHtCredentials credentials = new AeHtCredentials(userSession.getPrincipalName(), userSession.getPassword() );
      AeXslTaskRequestProcessor xslTaskProcessor = new AeXslTaskRequestProcessor(credentials, aTaskId);

      try
      {
         Document commandDoc = null;
         // if the form was submitted, then process form data.
         if ("POST".equalsIgnoreCase(aRequest.getMethod())) //$NON-NLS-1$
         {
            commandDoc = xslTaskProcessor.processRequest(xslHtmlParams, xslErrors, fileMap);
         }
         // transform and send the output
         aResponse.setCharacterEncoding("UTF-8"); //$NON-NLS-1$
         aResponse.getWriter().write( xslTaskProcessor.renderOutput(commandDoc, xslHtmlParams, xslErrors) );
      }
      catch(FileNotFoundException fnfe)
      {
         aResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, AeMessages.format("AeWorkFlowRenderTaskXslServlet.XSL_FILE_NOT_FOUND", new String[] {fnfe.getMessage(), aTaskId })); //$NON-NLS-1$
      }
      catch(AeImportNotFoundTransformerException infe)
      {
         aResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, AeMessages.format("AeWorkFlowRenderTaskXslServlet.XSL_RESOURCE_NOT_FOUND", new String[] {infe.getMessage(), aTaskId })); //$NON-NLS-1$
      }
      catch(AeTaskFaultException e)
      {
         if (e.getCode() == AeTaskFaultException.ILLEGAL_ACCESS)
         {
            RequestDispatcher rd = getServletContext().getRequestDispatcher ("/inbox/illegalaccess.jsp"); //$NON-NLS-1$
            rd.forward(aRequest, aResponse); 
         }
         else
         {
            aResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, AeMessages.format("AeWorkFlowRenderTaskXslServlet.TASK_FAULT", new String[] {e.getMessage(), aTaskId })); //$NON-NLS-1$
         }
      }
      catch(Throwable t)
      {
         AeException.logError(t);
         aResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, AeMessages.format("AeWorkFlowRenderTaskXslServlet.ERROR", new String[] {t.getMessage(), aTaskId })); //$NON-NLS-1$
      }
   }

   /**
    * Creates the container for the request information, such as form data and html header values.
    * @param aRequest
    * @param aPrincipalName
    * @param aTaskId
    * @param aParamMap
    * @param aErrors
    */
   protected AeXslTaskInputParameters createXslHtmlParams(HttpServletRequest aRequest, String aPrincipalName, String aTaskId, Map aParamMap, AeXslTaskRenderingErrors aErrors)
   {
      String userAgent = AeUtil.getSafeString( aRequest.getHeader("user-agent") ).trim(); //$NON-NLS-1$
      String pathInfo = AeUtil.getSafeString( aRequest.getPathInfo()).trim() ;

      // Create collection of http request header names.
      Map headers = new HashMap();
      Enumeration enumerator = aRequest.getHeaderNames();
      while (enumerator.hasMoreElements())
      {
         String name = (String) enumerator.nextElement();
         String value = (String) aRequest.getHeader(name);
         if (AeUtil.notNullOrEmpty(value))
         {
            headers.put(name.toLowerCase(), value.trim());
         }
      }
      // fixme (PJ) add support for cookies (when processing input) and setting of cookies (when rendering output).
      return AeXslTaskInputParameters.createRequestParameters(aPrincipalName, aTaskId, aRequest.getMethod(), userAgent, pathInfo, headers, aErrors, aParamMap);
   }

   /**
    * Processes the HTTP request to parse the task file attachments.
    *
    * @param aRequest
    */
   protected void processFormParamsAndFileUploads(HttpServletRequest aRequest, Map aParamMap, Map aFileMap) throws Exception
   {
      if (!FileUploadBase.isMultipartContent(aRequest))
      {
         aParamMap.putAll( aRequest.getParameterMap() );
         return;
      }

      FileUploadBase upload = createFileUploader();
      // Iterate through all of the request items.
      List items = upload.parseRequest(aRequest);
      Iterator iter = items.iterator();

      while (iter.hasNext())
      {
         FileItem item = (FileItem) iter.next();
         // If it's not a Form Field, then it's an upload.
         if (!item.isFormField())
         {
            File tmpFile = AeUtil.getTempFile("aetaskattch", ".bin"); //$NON-NLS-1$ //$NON-NLS-2$
            tmpFile.deleteOnExit();
            item.write(tmpFile);
            long size = tmpFile.length();
            if (size > 0)
            {
               AeUploadFileItem addItem = new AeUploadFileItem(tmpFile, item.getContentType(), item.getName());
               aFileMap.put(item.getFieldName(), addItem);
            }
            else
            {
               tmpFile.delete();
            }
         }
         else if ( item.isFormField())
         {
            aParamMap.put(item.getFieldName(), item.getString());
         }
      } // while
   }

   /**
    * @return Creates and returns a Apache commons DiskFileUpload implementation.
    */
   protected FileUploadBase createFileUploader()
   {
      DiskFileUpload diskUpload = new DiskFileUpload();
      return diskUpload;
   }

}
