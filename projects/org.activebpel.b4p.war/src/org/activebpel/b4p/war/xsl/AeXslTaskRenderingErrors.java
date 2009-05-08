//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/xsl/AeXslTaskRenderingErrors.java,v 1.2 2008/02/17 21:08:40 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.xsl;

import org.w3c.dom.Document;

/**
 * A wrapper to hold errors and messages during xsl processing.
 * The errors are stored in a xml document based on the schema http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/errors
 * (prefixed with aefe)
 */
public class AeXslTaskRenderingErrors
{
   /** Error document. */
   private Document mDocument;

   /**
    * Constructs an error document with principal name and task reference id.
    * The aefe:error element will be the root element of the underlying document.
    * @param aPrincipalName principal name
    * @param aTaskId task reference id.
    */
   public AeXslTaskRenderingErrors(String aPrincipalName, String aTaskId)
   {
      mDocument = AeXslTaskUtil.createErrorDocument();
      mDocument.getDocumentElement().setAttribute("principalName", aPrincipalName); //$NON-NLS-1$
      mDocument.getDocumentElement().setAttribute("taskId", aTaskId); //$NON-NLS-1$
   }

   /**
    * Adds an error message which occured during processing of the html form data input parameters.
    * The message will be added to the aefe:parameter-error element.
    * @param aParameterName parameter name.
    * @param errorMessage message.
    */
   public void addParameterProcessError(String aParameterName, String errorMessage)
   {
      AeXslTaskUtil.createParameterErrorElement(getDocument().getDocumentElement(), aParameterName, errorMessage);
   }

   /**
    * Adds an error message which occured during processing of the html form data input parameters.
    * The message will be added to the aefe:parameter-error element.
    * @param aParameterName parameter name.
    * @param aThrowable error exception.
    */   
   public void addParameterProcessError(String aParameterName, Throwable aThrowable)
   {
      addParameterProcessError(aParameterName, AeXslTaskUtil.createExceptionMessage(aThrowable));
   }

   /**
    * Adds a taskFault message which occured during the processing of a task command.
    * The message will be added to the aefe:command-error element with type = 'taskfault'.
    * @param aCommandName name of command.
    * @param aFaultMessage message.
    */ 
   public void addCommandProcessFault(String aCommandName, String aFaultMessage)
   {
      AeXslTaskUtil.createCommandErrorElement(getDocument().getDocumentElement(), aCommandName, "taskfault", aFaultMessage); //$NON-NLS-1$
   }

   /**
    * Adds a taskFault message which occured during the processing of a task command.
    * The message will be added to the aefe:command-error element with type = 'taskfault'.
    * @param aCommandName name of command.
    * @param aThrowable task fault.
    */    
   public void addCommandProcessFault(String aCommandName, Throwable aThrowable)
   {
      addCommandProcessFault(aCommandName, AeXslTaskUtil.createExceptionMessage(aThrowable));
   }

   /**
    * Adds a error message which occured during the processing of a task command.
    * The message will be added to the aefe:command-error element with type = 'error'.
    * @param aCommandName name of command.
    * @param aErrorMessage message.
    */
   public void addCommandProcessError(String aCommandName, String aErrorMessage)
   {
      AeXslTaskUtil.createCommandErrorElement(getDocument().getDocumentElement(), aCommandName, "error", aErrorMessage); //$NON-NLS-1$
   }

   /**
    * Adds a error message which occured during the processing of a task command.
    * The message will be added to the aefe:command-error element with type = 'error'.
    * @param aCommandName name of command.
    * @param aThrowable exception.
    */   
   public void addCommandProcessError(String aCommandName, Throwable aThrowable)
   {
      addCommandProcessError(aCommandName, AeXslTaskUtil.createExceptionMessage(aThrowable));
   }

   /**
    * @return Document containing the error messages.
    */
   public Document getDocument()
   {
      return mDocument;
   }

}
