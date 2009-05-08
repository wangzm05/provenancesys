//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/AeWorkFlowApplicationFactory.java,v 1.5 2008/02/20 15:58:19 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war;

import java.net.URL;
import java.util.Map;

import org.activebpel.b4p.war.service.AeHtClientServiceFactory;
import org.activebpel.b4p.war.service.AeHtCredentials;
import org.activebpel.b4p.war.service.IAeHtClientServiceFactory;
import org.activebpel.b4p.war.service.IAeTaskAeClientService;
import org.activebpel.b4p.war.service.IAeTaskHtClientService;
import org.activebpel.b4p.war.xsl.AeTaskXslStylesheetStore;
import org.activebpel.b4p.war.xsl.IAeTaskXslStylesheetStore;
import org.activebpel.rt.config.AeConfigurationUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * Factory to provide convenience access to the task service API
 * and the application configuration instance.
 */
public class AeWorkFlowApplicationFactory
{
   /** Application config. */
   private static AeWorkFlowApplicationConfiguration sConfiguration = null;

   /**
    * Stylesheet store
    */
   private static IAeTaskXslStylesheetStore sStylesheetStore = null;

   /**
    * application shutdown listener registry.
    */
   private static AeWorkflowApplicationShutdownListenerRegistry sShutdownListenerRegistry = new AeWorkflowApplicationShutdownListenerRegistry();

   /**
    * client service factory.
    */
   private static IAeHtClientServiceFactory sHtClientServiceFactory = null;


   /**
    * Returns the shutdown listener registry.
    */
   public static AeWorkflowApplicationShutdownListenerRegistry getShutdownListenerRegistry()
   {
      return sShutdownListenerRegistry;
   }


   /**
    * Returns IAeTaskXslStylesheetStore instance based on the configuration.
    * @return IAeTaskXslStylesheetStore instance.
    */
   public static IAeTaskXslStylesheetStore getStyleSheetStore()
   {
      if (sStylesheetStore == null)
      {
         // get the config entry that defines the classname.
         Map configEntry = null;
         if (getConfiguration() != null)
         {
            configEntry = getConfiguration().getMapEntry(AeWorkFlowApplicationConfiguration.STYLESHEET_STORE_ENTRY_NAME);
         }
         //if the config entry exists and it has a 'class' attribute entry, then create the
         // xsl store based on the class.
         if (configEntry != null && AeUtil.notNullOrEmpty( (String) configEntry.get(AeConfigurationUtil.CLASS_ENTRY)))
         {
            sStylesheetStore = createStyleSheetStore(configEntry);
         }
         else
         {
            // Default case.
            sStylesheetStore = new AeTaskXslStylesheetStore();
         }
      }
      return sStylesheetStore;
   }

   /**
    * Creates and returns IAeTaskXslStylesheetStore based on the class defined in the config entry.
    * E.g:
    * &lt;entry name="StyleSheetStore"&gt;
    *     &lt;entry name="Class" value ="com.activee.rt.workflow.war.web.xsl.AeTaskXslStylesheetStore" /&gt;
    * &lt;/entry&gt;
    *
    * @param aConfigMapEntry
    * @return IAeTaskXslStylesheetStore implementation.
    */
   protected static IAeTaskXslStylesheetStore createStyleSheetStore(Map aConfigMapEntry)
   {
      try
      {
         return (IAeTaskXslStylesheetStore) AeConfigurationUtil.createConfigSpecificClass(aConfigMapEntry);
      }
      catch(Throwable t)
      {
         throw new RuntimeException(t);
      }
   }

   /**
    * Returns config.
    * @return application configuration.
    */
   public static AeWorkFlowApplicationConfiguration getConfiguration()
   {
      return sConfiguration;
   }

   /**
    * Sets the configuration.
    * @param aConfiguration
    */
   public static void setConfiguration(AeWorkFlowApplicationConfiguration aConfiguration)
   {
      sConfiguration = aConfiguration;
   }

   /**
    * Returns true if there is a configuration.
    * @return true if config has been set.
    */
   public static boolean hasConfiguration()
   {
      return getConfiguration() != null;
   }

   /**
    * @return the htClientServiceFactory
    */
   public static IAeHtClientServiceFactory getHtClientServiceFactory()
   {
      if (sHtClientServiceFactory == null)
      {
         sHtClientServiceFactory = new AeHtClientServiceFactory();
      }
      return sHtClientServiceFactory;
   }

   /**
    * @param aHtClientServiceFactory the htClientServiceFactory to set
    */
   public static void setHtClientServiceFactory(IAeHtClientServiceFactory aHtClientServiceFactory)
   {
      sHtClientServiceFactory = aHtClientServiceFactory;
   }


   /**
    * Creates and return the IAeTaskHtClientService.
    * @param aUsername
    * @param aPassword
    * @return IAeHtClientService instance
    */
   public static IAeTaskHtClientService createHtClientService(AeHtCredentials aCredentials)
   {
      URL url = AeWorkFlowApplicationFactory.getConfiguration().getHtClientServiceEndpointURL();
      return getHtClientServiceFactory().createHtClientService( url, aCredentials.getUsername(), aCredentials.getPassword() );
   }

   /**
    * Creates and return the IAeTaskAeClientService.
    * @param aUsername
    * @param aPassword
    */
   public static IAeTaskAeClientService createAeClientService(AeHtCredentials aCredentials)
   {
      URL url = AeWorkFlowApplicationFactory.getConfiguration().getAeClientServiceEndpointURL();
      return getHtClientServiceFactory().createAeClientService( url, aCredentials.getUsername(), aCredentials.getPassword() );
   }
}
