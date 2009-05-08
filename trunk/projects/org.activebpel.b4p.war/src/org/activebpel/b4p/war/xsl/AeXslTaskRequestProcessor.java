//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/xsl/AeXslTaskRequestProcessor.java,v 1.7 2008/02/20 15:58:19 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.xsl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.activebpel.b4p.war.AeWorkFlowApplicationFactory;
import org.activebpel.b4p.war.service.AeHtCredentials;
import org.activebpel.b4p.war.service.IAeTaskAeClientService;
import org.activebpel.b4p.war.xsl.command.AeXslAeTaskOperationCommand;
import org.activebpel.b4p.war.xsl.command.AeXslAttachmentsCommand;
import org.activebpel.b4p.war.xsl.command.AeXslHtTaskOperationCommand;
import org.activebpel.b4p.war.xsl.command.IAeXslTaskCommand;
import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * AeXslTaskRequestProcessor is responsible for handling of the html form POST
 * and converting the request to a command document as well as fetching the
 * xsl stylesheets and rendering the output.
 */
public class AeXslTaskRequestProcessor
{
   /** Map containing command name to IAeXslTaskCommand impl instances.*/
   public static final Map COMMAND_MAP = new HashMap();
   static
   {
      COMMAND_MAP.put("taskOperation",      new AeXslHtTaskOperationCommand() ); //$NON-NLS-1$
      COMMAND_MAP.put("aeTaskOperation",    new AeXslAeTaskOperationCommand() ); //$NON-NLS-1$
      COMMAND_MAP.put("addAttachment",      new AeXslAttachmentsCommand() ); //$NON-NLS-1$
   }

   /** Task client and catalog access username and password */
   private AeHtCredentials mCredentials;
   /** Task reference id. */
   private String mTaskId;

   /**
    * Constructs the AeXslTaskRequestProcessor with the principal name and task
    * reference id.
    * @param aCredentials
    * @param aTaskId
    */
   public AeXslTaskRequestProcessor(AeHtCredentials aCredentials, String aTaskId)
   {
      setCredentials(aCredentials);
      setTaskId(aTaskId);
   }

   /**
    * Generates the output by transforming the task's presentation xsl with the task document as the
    * xml source.
    * @param aCommandReqDocument command request document.
    * @param aParameters html form request parameters
    * @param aErrors errors.
    * @return rendered output.
    * @throws Exception
    */
   public String renderOutput(Document aCommandReqDocument, AeXslTaskInputParameters aParameters, AeXslTaskRenderingErrors aErrors) throws Exception
   {

      String principalName = aParameters.getPrincipalName();
      String taskId = aParameters.getTaskId();
      // get source xml - i.e. the task document via ws call.
      IAeTaskAeClientService aeService =  AeWorkFlowApplicationFactory.createAeClientService( getCredentials() );
      Element getTaskInstanceRespEle = aeService.getTaskInstance(taskId);
      Source xmlSource = new DOMSource(getTaskInstanceRespEle.getOwnerDocument());
      // get the presentation xsl based on rendering hints.
      Element renderingsElem = (Element)AeXPathUtil.selectSingleNode(getTaskInstanceRespEle, "trt:renderings", AeXslTaskConstants.NSS_MAP); //$NON-NLS-1$
      // FIXMEPJ look into passing credentials one time via ctor.
      IAeTaskXslStylesheetStore stylesheetStore = AeWorkFlowApplicationFactory.getStyleSheetStore();
      Source xslSource = stylesheetStore.getTaskRenderingStylesheet(taskId, renderingsElem, isNotification(getTaskInstanceRespEle), getCredentials());

      // set up parameters for the tranformation.
      Map params = new HashMap();
      params.put("principalName", principalName); //$NON-NLS-1$
      params.put("parameterDoc", aParameters.getDocument()); //$NON-NLS-1$
      params.put("commandDoc", (aCommandReqDocument!=null? aCommandReqDocument : AeXmlUtil.newDocument())); //$NON-NLS-1$
      params.put("errorDoc", aErrors.getDocument()); //$NON-NLS-1$

      // tranform document.
      Document dom = (Document) stylesheetStore.doTransform(xslSource, xmlSource, params, getCredentials());
      Element result = dom.getDocumentElement();
      return AeXmlUtil.serialize(result);
   }

   /**
    * Processes the html form input given the document containing the request.
    * @param aParameters Document containing (http) request information in aefp:parameters elements.
    * @param aErrors Container to report errors.
    * @param aFileMap map containing uploaded file items.
    * @return Document containing the processed commands.
    * @throws Exception
    */
   public Document processRequest(AeXslTaskInputParameters aParameters, AeXslTaskRenderingErrors aErrors, Map aFileMap) throws Exception
   {
      Document commandDoc = null;
      // Get xsl source to generate the command document. (used cached information)
      IAeTaskXslStylesheetStore stylesheetStore = AeWorkFlowApplicationFactory.getStyleSheetStore();
      Source xslSource = stylesheetStore.getTaskCommandStylesheet(aParameters.getTaskId(), getCredentials());
      if (xslSource == null)
      {
         // get default hints
         xslSource = stylesheetStore.getTaskCommandStylesheet(aParameters.getTaskId(), null, getCredentials());
      }
      
      Source xmlSource = new DOMSource(aParameters.getDocument());
      Map params = new HashMap();
      params.put("errorDom", aErrors.getDocument()); //$NON-NLS-1$
      commandDoc = (Document) stylesheetStore.doTransform(xslSource, xmlSource, params, getCredentials());
      processCommandDoc(commandDoc, aFileMap, aErrors);
      return commandDoc;
   }

   /**
    * Returns true if the task is a NOTIFICATION type.
    * @param aGetTaskInstanceRespEle
    * @returnt true if taskType is NOTIFICATION.
    */
   protected boolean isNotification(Element aGetTaskInstanceRespEle)
   {
      return "NOTIFICATION".equals(AeXPathUtil.selectText(aGetTaskInstanceRespEle, "trt:taskType", AeXslTaskConstants.NSS_MAP)); //$NON-NLS-1$ //$NON-NLS-2$
   }

   /**
    * Iterates the command document elements and executes the corresponding (mapped)
    * IAeXslTaskCommand objects.
    * @param aCommandDoc
    * @param aFileMap
    * @param aErrors
    * @throws Exception
    */
   protected void processCommandDoc(Document aCommandDoc, Map aFileMap, AeXslTaskRenderingErrors aErrors) throws Exception
   {
      Element root = aCommandDoc.getDocumentElement();
      String taskId = root.getAttribute("taskId"); //$NON-NLS-1$;

      //report all param errors
      List aetParamErrorNodeList = AeXPathUtil.selectNodes(root, "aefe:parameter-error", AeXslTaskConstants.NSS_MAP); //$NON-NLS-1$
      for (int i = 0; i < aetParamErrorNodeList.size(); i++)
      {
         Element element = (Element) aetParamErrorNodeList.get(i);
         aErrors.addParameterProcessError(element.getAttribute("name"), AeXmlUtil.getText(element)); //$NON-NLS-1$
      }

      // NOTE: Short return if there are param errors.
      // todo (PJ) exten behaviour as a attribute in xsl custom rendering hint. (i.e. abort on errors)
      if (aetParamErrorNodeList.size() > 0)
      {
         return;
      }

      List aetCommandNodeList = AeXPathUtil.selectNodes(root, "aetc:taskcommand", AeXslTaskConstants.NSS_MAP); //$NON-NLS-1$
      for (int i = 0; i < aetCommandNodeList.size(); i++)
      {
         try
         {
            Element element = (Element) aetCommandNodeList.get(i);
            IAeXslTaskCommand command = getCommand(element.getAttribute("name")); //$NON-NLS-1$
            if (command != null)
            {
               command.execute(getCredentials(), element, taskId, aFileMap, aErrors);
            }
         }
         catch(Exception e)
         {
            AeException.logError(e);
         }
      }
   }

   /**
    * Returns task command
    * @param aName
    * @return
    */
   protected IAeXslTaskCommand getCommand(String aName)
   {
      IAeXslTaskCommand command = (IAeXslTaskCommand) COMMAND_MAP.get(aName);
      return command;
   }

   /**
    * @return task reference id
    */
   protected String getTaskId()
   {
      return mTaskId;
   }

   /**
    * Sets the task reference id.
    * @param aTaskId task id.
    */
   protected void setTaskId(String aTaskId)
   {
      mTaskId = aTaskId;
   }

   /**
    * @return the credentials
    */
   protected AeHtCredentials getCredentials()
   {
      return mCredentials;
   }

   /**
    * @param aCredentials the credentials to set
    */
   protected void setCredentials(AeHtCredentials aCredentials)
   {
      mCredentials = aCredentials;
   }

}
