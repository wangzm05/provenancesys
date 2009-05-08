//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/xsl/command/AeXslAttachmentsCommand.java,v 1.3 2008/02/13 06:55:07 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.xsl.command;

import java.util.Map;

import org.activebpel.b4p.war.AeUploadFileItem;
import org.activebpel.b4p.war.xsl.AeXslTaskRenderingErrors;
import org.w3c.dom.Element;

/**
 * Class which implements the add and deletion of attachments.
 */
public class AeXslAttachmentsCommand extends AeAbstractXslTaskCommand
{

   /**
    * @see org.activebpel.b4p.war.xsl.command.AeAbstractXslTaskCommand#internalExecute(org.w3c.dom.Element, java.lang.String, java.lang.String, java.util.Map, org.activebpel.b4p.war.xsl.AeXslTaskRenderingErrors)
    */
   protected void internalExecute(Element aCommandElement, String aPrincipalName, String aTaskId,
         Map aFileMap, AeXslTaskRenderingErrors aErrors) throws Throwable
   {
      String name = aCommandElement.getAttribute("name"); //$NON-NLS-1$
      if ("addAttachment".equals(name)) //$NON-NLS-1$
      {
         AeUploadFileItem fileItem = (AeUploadFileItem) aFileMap.get(aCommandElement.getAttribute("id")); //$NON-NLS-1$
         if (fileItem != null)
         {
            try
            {
               getHtClientService().addAttachment(aTaskId, fileItem.getName(), fileItem.getContentType(), fileItem.getFile());
            }
            finally
            {
               // delete file.
               try
               {
                  fileItem.getFile().delete();
               }
               catch(Throwable ignore)
               {
               }
            }
         }
      }
   }
}
