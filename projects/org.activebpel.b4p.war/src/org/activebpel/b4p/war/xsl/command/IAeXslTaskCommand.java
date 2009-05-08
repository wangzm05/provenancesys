//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/xsl/command/IAeXslTaskCommand.java,v 1.4 2008/02/20 15:58:19 PJayanetti Exp $
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

import org.activebpel.b4p.war.service.AeHtCredentials;
import org.activebpel.b4p.war.xsl.AeXslTaskRenderingErrors;
import org.w3c.dom.Element;

/**
 * Defines an interface wrapper for executing xml task commands (such as claim, setoutput data)
 */
public interface IAeXslTaskCommand
{
   public static final String ERROR_NS = "http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/errors";  //$NON-NLS-1$

   /**
    * Executes the command. Exceptions and errors are set in the aErrorDoc document.
    * @param aCredentials access credentials
    * @param aCommandElement
    * @param aTaskId task reference id.
    * @param aFileMap map containing uploaded files.
    * @param aErrors container to report processing errors.
    */
   public void execute(AeHtCredentials aCredentials, Element aCommandElement, String aTaskId, Map aFileMap, AeXslTaskRenderingErrors aErrors);
}
