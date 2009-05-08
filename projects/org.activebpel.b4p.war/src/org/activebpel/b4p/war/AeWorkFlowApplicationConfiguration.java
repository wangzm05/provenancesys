//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/AeWorkFlowApplicationConfiguration.java,v 1.6 2008/02/20 15:58:19 PJayanetti Exp $
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

import org.activebpel.rt.AeException;
import org.activebpel.rt.config.AeConfiguration;
import org.activebpel.rt.util.AeUtil;

/**
 * Base class for WorkFlow application configuration.
 */
public abstract class AeWorkFlowApplicationConfiguration extends AeConfiguration
{
   /** xsl store entry name. **/
   public static final String STYLESHEET_STORE_ENTRY_NAME = "StyleSheetStore"; //$NON-NLS-1$
   
   public static final String HT_TASK_CLIENT_SERVICE_URL = "HtTaskClientServiceUrl"; //$NON-NLS-1$
   public static final String AE_TASK_CLIENT_SERVICE_URL = "AeTaskClientServiceUrl"; //$NON-NLS-1$
   public static final String XSL_CATALOG_URL = "XslCatalogUrl"; //$NON-NLS-1$
   public static final String ENDPOINT_USERNAME = "Username"; //$NON-NLS-1$
   public static final String ENDPOINT_PASSWORD = "Password"; //$NON-NLS-1$
   public static final String CACHE_SIZE = "CacheSize"; //$NON-NLS-1$
   public static final String CACHE_DURATION = "CacheDurationMins"; //$NON-NLS-1$

   /**
    * Returns the ws endpoint url.
    * @return end point url.
    */
   public String getHtClientServiceEndpoint()
   {
      return getEntry(HT_TASK_CLIENT_SERVICE_URL);
   }

   /**
    * Sets the endpoint.
    * @param aEndpoint
    */
   public void setTaskServiceEndpoint(String aEndpoint)
   {
      setEntry(HT_TASK_CLIENT_SERVICE_URL, AeUtil.getSafeString(aEndpoint));
   }

   /**
    * Returns the endpoint URL is if one is specified and it is valid.
    * Otherwise <code>null</code> is returned.
    * @return task client service url.
    */
   public URL getHtClientServiceEndpointURL()
   {
      if (AeUtil.notNullOrEmpty(getHtClientServiceEndpoint()))
      {
         try
         {
            URL url = new URL( getHtClientServiceEndpoint() );
            return url;
         }
         catch(Throwable t)
         {
            AeException.logWarning(t.getMessage());
         }
      }
      return null;
   }
   
   /**
    * Returns the xsl catalog servlet URL is if one is specified and it is valid.
    * If the XslCatalogUrl is not specified in the configuration, then it is 
    * calculated using the task Service endpoint URL assuming the catalog url
    * is based on the path /active-bpel/catalog.
    * @return Catalog servlet url.
    */
   public URL getXslCatalogEndpointURL()
   {
      String endpoint = getEntry(XSL_CATALOG_URL);
      if (AeUtil.isNullOrEmpty( endpoint ))
      {
         endpoint = getActiveBpelContextEndpoint() + "/taskxsl";  //$NON-NLS-1$
      }      
      return createURL(endpoint);
   }  
   
   /**
    * Returns the endpoint url to the task client configuration service
    */
   public URL getAeClientServiceEndpointURL()
   {
      return getServiceEndpointFromConfigEntry(AE_TASK_CLIENT_SERVICE_URL, "AeB4PTaskClient-aeTaskOperations"); //$NON-NLS-1$
   }    
      
   /**
    * Returns the ActiveBPEL engine service endpoint URL given the key to the config
    * entry. If the entry url is not defined in the configuration, then the
    * endpoint url will be derived using the default service endpoint and given service name.
    * @param entryKey
    * @param aDefaultServiceName
    * @return endpoint url.
    */
   protected URL getServiceEndpointFromConfigEntry(String entryKey, String aDefaultServiceName)   
   {
      String endpoint = getEntry(entryKey);
      if (AeUtil.isNullOrEmpty( endpoint ))
      {
         // create url based using task client service url
         endpoint = deriveServiceEndpoint(aDefaultServiceName);
      }
      return createURL(endpoint);
   }
   
   /**
    * Convenience method to create a url from a string endpoint.
    * @param aEndpoint
    * @return URL or <code>null</code> if not able to parse aEndpoint into a URL.
    */
   protected URL createURL(String aEndpoint)
   {
      if (AeUtil.notNullOrEmpty( aEndpoint ))
      {
         try
         {
            URL url = new URL( aEndpoint );
            return url;
         }
         catch(Throwable t)
         {
            AeException.logWarning(t.getMessage());
         }
      }
      return null;      
   }      

   /**
    * 
    * Contructs a ActiveBPEL services endpoint given the service name. The endpoint
    * is derived using the default task api endpoint.
    * @param aServiceName
    * @return service endpoint or <code>null</code> if default endpoint is not available.
    */
   protected String deriveServiceEndpoint(String aServiceName)
   {
      return getActiveBpelContextEndpoint() + "/services/" + aServiceName;  //$NON-NLS-1$
   }
      
   /**
    * Returns the base path to the ActiveBPEL engine web context. For example,
    * http://localhost:8080/active-bpel.
    */
   protected String getActiveBpelContextEndpoint()
   {      
      String endpoint = AeUtil.getSafeString( getHtClientServiceEndpoint() );
      // strip tailing slash
      if (endpoint.endsWith("/")) //$NON-NLS-1$
      {
         endpoint = endpoint.substring(0, endpoint.length()-1);
      }
      // task service endpoint format is http://host:port/optional_path/active-bpel/services/SERVICE_NAME.
      // we want the path upto (inclusive) '/active-bpel' (i.e. the context).
      int idx = endpoint.lastIndexOf("/services/"); //$NON-NLS-1$
      if (idx > 0)
      {
         endpoint = endpoint.substring(0, idx);
      }
      return endpoint;
   }

   /**
    * Returns the ws endpoint url authorization username.
    * @return end point url.
    */
   public String getUsername()
   {
      return getEntry(ENDPOINT_USERNAME);
   }

   /**
    * Sets the username.
    * @param aUsername
    */
   public void setUsername(String aUsername)
   {
      setEntry(ENDPOINT_USERNAME, AeUtil.getSafeString(aUsername));
   }

   /**
    * Returns the ws endpoint url authorization password.
    * @return end point url.
    */
   public String getPassword()
   {
      String pw = AeUtil.getSafeString( getEntry(ENDPOINT_PASSWORD) ).trim();
      if (pw.length() > 0)
      {
         return AeWorkflowCryptoUtil.decryptString(pw);
      }
      else
      {
         return pw;
      }      
   }

   /**
    * Sets the password.
    * @param aPassword
    */
   public void setPassword(String aPassword)
   {
      setEntry(ENDPOINT_PASSWORD, AeWorkflowCryptoUtil.encryptString(AeUtil.getSafeString(aPassword)));
   }

   /**
    * Checks if username and password exist.
    * @return true if username and password are given.
    */
   public boolean hasAuthorizationCredentials()
   {
      return AeUtil.notNullOrEmpty( getUsername() ) && AeUtil.notNullOrEmpty( getPassword() );
   }
   
   /**
    * Returns the XSL cache size or -1 if not defined.
    * @return cache-size.
    */
   public int getCacheSize()
   {
      int size = AeUtil.parseInt( AeUtil.getSafeString( getEntry(CACHE_SIZE)), -1);
      if (size < -1)
      {
         size = -1;
      }
      return size;
   }
   
   /**
    * Returns the XSL cache invalidation timeout in minutes
    * @return timeout or -1 if not defined.
    */
   public int getCacheDurationMins()
   {
      int mins = AeUtil.parseInt( AeUtil.getSafeString( getEntry(CACHE_DURATION)), -1);
      if (mins < -1)
      {
         mins = -1;
      }
      return mins;
   }   

}
