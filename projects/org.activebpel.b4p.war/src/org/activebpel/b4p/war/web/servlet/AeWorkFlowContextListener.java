//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/servlet/AeWorkFlowContextListener.java,v 1.1 2008/01/11 15:05:52 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.web.servlet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.activebpel.b4p.war.AeWorkFlowApplicationFactory;
import org.activebpel.b4p.war.AeWorkFlowApplicationFileConfiguration;
import org.activebpel.b4p.war.web.IAeWorkFlowWebConstants;
import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeCloser;
import org.activebpel.rt.util.AeUtil;

/**
 * WorkFlow Application context listener.
 */
public class AeWorkFlowContextListener implements ServletContextListener
{

   /**
    * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
    */
   public void contextInitialized(ServletContextEvent aEvent)
   {
      String configFilename = AeUtil.getSafeString( aEvent.getServletContext().getInitParameter(IAeWorkFlowWebConstants.CONFIG_FILENAME) );
      String resourcePath  = "/WEB-INF/" + configFilename; //$NON-NLS-1$
      BufferedReader reader = null;
      try
      {
         InputStream in = aEvent.getServletContext().getResourceAsStream(resourcePath);
         reader = new BufferedReader (new InputStreamReader(in));
         AeWorkFlowApplicationFileConfiguration config = new AeWorkFlowApplicationFileConfiguration(reader);
         AeWorkFlowApplicationFactory.setConfiguration(config);
      }
      catch(Throwable t)
      {
         AeException.logError(t);
      }
      finally
      {
         AeCloser.close(reader);
      }

      // call get stylesheet store instance to initialize it (e.g. cache background threads etc.)
      AeWorkFlowApplicationFactory.getStyleSheetStore();
   }


   /**
    * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
    */
   public void contextDestroyed(ServletContextEvent aServletContextEvent)
   {
      //Notify any listeners of the shutdown.
      AeWorkFlowApplicationFactory.getShutdownListenerRegistry().notifyShutdown();
   }

}
