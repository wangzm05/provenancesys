// $Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/AeTaskWarBundle.java,v 1.1 2008/01/11 15:05:48 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;

/**
 * A resource bundle implemenation for the ActiveBPEL for People war.
 */
public class AeTaskWarBundle extends ResourceBundle
{
   
   static final String BUNDLE_SUFFIX = ".properties"; //$NON-NLS-1$
   
   /** The actual resource bundle that contains the resources. */
   private ResourceBundle mProxiedBundle;
   
   /** The first part of the resource bundle name */
   private String[] mBundlePrefix;
   
   /**
    * Constructs the resource bundle.
    */
   public AeTaskWarBundle(String aBundlePrefix, ServletContext aServletContext, ServletRequest aServletRequest)
   {
      mBundlePrefix = aBundlePrefix.split(","); //$NON-NLS-1$                 
      createBundle(aServletContext, aServletRequest);
   }
   
   /**
    * Constructs the resource bundle
    * 
    * @param aServletContext - servlet context
    * @param aServletRequest - servlet request
    */
   protected void createBundle(ServletContext aServletContext, ServletRequest aServletRequest)
   {
      InputStream is = null;
      try
      {
         is = getBundleInputStream(aServletContext, aServletRequest);
         mProxiedBundle = new PropertyResourceBundle(is);
      }
      catch (Exception e) 
      {
         e.printStackTrace();
      }
      finally
      {
         try
         {
            is.close();
         }
         catch (IOException ioe)
         {
            ioe.printStackTrace();
         }
      }     
   }

   /**
    * Returns the bundle as an input stream. The bundle may be a concatenation of bundles if more than
    * one was requested. 
    * 
    * @param aServletContext - Servlet context
    * @param aServletRequest - Object representing client request. Used to determine locale.
    * @return - Input stream for bundle
    */
   protected InputStream getBundleInputStream(ServletContext aServletContext, ServletRequest aServletRequest)
   {
      // An enumeration must be passed to SequenceInputStream for concatenation. So we will use 
      // vector.elements()
      Vector streamVector = new Vector();
      
      for (int i = 0; i < mBundlePrefix.length; i++)
      {
         streamVector.add(getLocalizedInputStream(mBundlePrefix[i], aServletContext, aServletRequest));  
      }
      
      return new SequenceInputStream(streamVector.elements());      
   }
   
   /*
    * Check the locale from the http request to determine the bundle name. If language and country
    * are specified we will look for a bundle with a language and country specification. If not found we
    * will look for a bundle with just the laguage spec. If no locale specific bundle is found we will look
    * for the default bundle.
    */
   protected InputStream getLocalizedInputStream(String aBundlePrefix, ServletContext aServletContext, ServletRequest aServletRequest)
   {
      InputStream is = null; 
              
      Locale locale = aServletRequest.getLocale();
      String country = locale.getCountry();
      String language = locale.getLanguage();
                
      // Look for bundle with country and language suffix. If not found, try just language.
      if (country.length() > 0)
      {
         is = aServletContext.getResourceAsStream(aBundlePrefix + "_" + language + "_" + country + BUNDLE_SUFFIX); //$NON-NLS-1$ //$NON-NLS-2$ 
         if (is == null)
            is = aServletContext.getResourceAsStream(aBundlePrefix + "_" + language + BUNDLE_SUFFIX); //$NON-NLS-1$           
       }
      // Look for bundle with language specification.
      else if (language.length() > 0)
      {
         is = aServletContext.getResourceAsStream(aBundlePrefix + "_" + language + BUNDLE_SUFFIX); //$NON-NLS-1$
      }
       
      // If the bundle isn't found yet, look for the non locale specific versions.
      if (is == null)
         is = aServletContext.getResourceAsStream(aBundlePrefix + BUNDLE_SUFFIX); 
        
       return is;
   }

   /**
    * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
    */
   protected Object handleGetObject(String key)
   {
      try
      {
         return mProxiedBundle.getObject(key);
      }
      catch(MissingResourceException mre)
      {
         return "!" + key + "!"; //$NON-NLS-1$ //$NON-NLS-2$
      }
   }

   /**
    * @see java.util.ResourceBundle#getKeys()
    */
   public Enumeration getKeys()
   {
      return mProxiedBundle.getKeys();
   }

}
