//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/xsl/command/AeAbstractXslTaskCommand.java,v 1.6 2008/02/20 15:58:19 PJayanetti Exp $
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

import org.activebpel.b4p.war.AeWorkFlowApplicationFactory;
import org.activebpel.b4p.war.service.AeHtCredentials;
import org.activebpel.b4p.war.service.AeTaskFaultException;
import org.activebpel.b4p.war.service.IAeTaskAeClientService;
import org.activebpel.b4p.war.service.IAeTaskHtClientService;
import org.activebpel.b4p.war.xsl.AeXslTaskRenderingErrors;
import org.w3c.dom.Element;

/**
 * Base class for all xsl xml element based task commands.
 * Subclasses must implement <code>internalExecute()</code> abstract method
 */
public abstract class AeAbstractXslTaskCommand implements IAeXslTaskCommand
{
   /** Access principal or username and password. */
   private AeHtCredentials mCredentials;

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

   /**
    * Return HT client service
    * @return IAeHtClientService
    */
   protected IAeTaskHtClientService getHtClientService()
   {
      return AeWorkFlowApplicationFactory.createHtClientService( getCredentials() );
   }

   /**
    * Returns the ae task operations service.
    */
   protected IAeTaskAeClientService getAeClientService()
   {
      return AeWorkFlowApplicationFactory.createAeClientService( getCredentials() );
   }

   /**
    * Overrides method to invoke <code>internalExecute()</code> method. Exceptions that result from this
    * invoke are added to the <code>aErrorDoc</code> document.
    * @see org.activebpel.b4p.war.xsl.command.IAeXslTaskCommand#execute(org.activebpel.b4p.war.service.AeHtCredentials, org.w3c.dom.Element, java.lang.String, java.util.Map, org.activebpel.b4p.war.xsl.AeXslTaskRenderingErrors)
    */
   public void execute(AeHtCredentials aCredentials, Element aCommandElement, String aTaskId, Map aFileMap, AeXslTaskRenderingErrors aErrors)
   {
      setCredentials(aCredentials);
      String commandName = aCommandElement.getAttribute("name"); //$NON-NLS-1$
      try
      {
         internalExecute(aCommandElement, getCredentials().getUsername(), aTaskId, aFileMap, aErrors);
      }
      catch(AeTaskFaultException tfe)
      {
         aErrors.addCommandProcessFault(commandName, tfe);
      }
      catch(Throwable t)
      {
         aErrors.addCommandProcessError(commandName, t);
      }
   }

   /**
    * Subclasses must implement this method
    * @param aCommandElement command element
    * @param aPrincipalName principal name.
    * @param aTaskId task reference id.
    * @param aFileMap map containing uploaded file items.
    * @param aErrors Container for reporting errors.
    * @throws Throwable
    */
   protected abstract void internalExecute(Element aCommandElement, String aPrincipalName, String aTaskId, Map aFileMap,
         AeXslTaskRenderingErrors aErrors) throws Throwable;

}
