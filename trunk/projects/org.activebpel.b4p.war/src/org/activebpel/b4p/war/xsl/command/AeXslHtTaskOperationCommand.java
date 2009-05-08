//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/xsl/command/AeXslHtTaskOperationCommand.java,v 1.2 2008/02/13 06:55:07 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.xsl.command;

import java.util.Map;

import org.activebpel.b4p.war.xsl.AeXslTaskRenderingErrors;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * Command that is responsible to sending out ws-ht api task operation
 * commands to the HT server.
 */
public class AeXslHtTaskOperationCommand extends AeAbstractXslTaskCommand
{

   /**
    * @see org.activebpel.b4p.war.xsl.command.AeAbstractXslTaskCommand#internalExecute(org.w3c.dom.Element, java.lang.String, java.lang.String, java.util.Map, org.activebpel.b4p.war.xsl.AeXslTaskRenderingErrors)
    */
   protected void internalExecute(Element aCommandElement, String aPrincipalName, String aTaskId,
         Map aFileMap, AeXslTaskRenderingErrors aErrors) throws Throwable
   {
      // get ht element. Eg. htdt:claim
      Element htElement = AeXmlUtil.getFirstSubElement(aCommandElement);
      getHtClientService().executeRequest(htElement);
   }
}
