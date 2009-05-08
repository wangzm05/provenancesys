//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/xsl/AeAbstractCachingTaskXslStylesheetStore.java,v 1.7 2008/02/20 15:58:19 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.xsl;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.activebpel.b4p.war.AeWorkFlowApplicationFactory;
import org.activebpel.b4p.war.IAeWorkflowApplicationShutdownListener;
import org.activebpel.b4p.war.service.AeHtCredentials;
import org.activebpel.rt.util.AeCloser;
import org.activebpel.rt.util.AeFileUtil;
import org.xml.sax.InputSource;

/**
 * Base class for a style sheet store which provides the necessary
 * framework to support a caching mechanism such as cache size
 * and frequency for invalidating the cache.
 */
public abstract class AeAbstractCachingTaskXslStylesheetStore extends AeTaskXslStylesheetStore
      implements IAeWorkflowApplicationShutdownListener
{
   /** Cache time to live, in minutes. */
   public static final int DEFAULT_CACHE_DURATION_MINS = 5;
   /** Maximum number of object in cache. */
   public static final int DEFAULT_MAX_SIZE = 500;
   /** Lock object. */
   public static final Object MUTEX = new Object();
   /** Schedule timer. */
   private Timer mTimer = new Timer(true);
   /** Interval when the cache will be invalidated. */
   private int mCacheDuration = DEFAULT_CACHE_DURATION_MINS;
   /** Maximum number of items to be cached. */
   private int mCacheSize = DEFAULT_MAX_SIZE;
   /** Current timer task instance that is used to invalidate the cache.*/
   private AeClearXslCacheTimerTask mClearCacheTimerTask;

   /**
    * Creates the internal cache and starts the cache invalidation timer.
    */
   protected AeAbstractCachingTaskXslStylesheetStore()
   {
      initCacheConfiguration();
      createCache();
      startInvalidationTimer();
      // add self as shutdown listener.
      AeWorkFlowApplicationFactory.getShutdownListenerRegistry().addListener(this);
   }
   
   /**
    * Initializes the xsl cache settings.
    */
   protected void initCacheConfiguration()
   {
      // get size and duration from config. A value of -1 means the settings not available.
      // (i.e. use defaults)
      int size = AeWorkFlowApplicationFactory.getConfiguration().getCacheSize();
      if (size >= 0)
      {
         mCacheSize = size;
      }
      int mins = AeWorkFlowApplicationFactory.getConfiguration().getCacheDurationMins();
      if (mins >= 0)
      {
         setCacheDuration(mins);
      }
   }

   /**
    * Overrides method to kill the internal timer tasks.
    * @see org.activebpel.b4p.war.IAeWorkflowApplicationShutdownListener#onWorkflowApplicationShutdown()
    */
   public void onWorkflowApplicationShutdown()
   {
      mTimer.cancel();
      mTimer = null;
   }

   /**
    * Timer used to create background task to invalidate the cache.
    * @return timer instance
    */
   protected Timer getTimer()
   {
      return mTimer;
   }

   /**
    * Starts the timer to invalidate the cache.
    */
   protected void startInvalidationTimer()
   {
      long durSecs = getCacheDuration() * 60000L;
      cancelInvalidationTimer();
      mClearCacheTimerTask = new AeClearXslCacheTimerTask();
      getTimer().schedule( mClearCacheTimerTask, durSecs, durSecs);
   }

   /**
    * Cancels the cache invalidation timer.
    */
   protected void cancelInvalidationTimer()
   {
      if (mClearCacheTimerTask != null )
      {
         mClearCacheTimerTask.cancel();
         mClearCacheTimerTask = null;
      }
   }

   /**
    * Returns the maximum number of items that should be cached.
    * @return max cache size.
    */
   protected int getCacheSize()
   {
      return mCacheSize;
   }

   /**
    * Sets the cache size.
    * @param aCacheSize
    */
   protected void setCacheSize(int aCacheSize)
   {
      if (aCacheSize > 0)
      {
         mCacheSize = aCacheSize;
      }
   }

   /**
    * Returns the time interval (in minutes) when the cache will invalidated.
    * @return interval, in minutes
    */
   protected int getCacheDuration()
   {
      return mCacheDuration;
   }

   /**
    * Sets the cache invalidation interval.
    * @param aCacheDuration duration in minutes.
    */
   protected void setCacheDuration(int aCacheDuration)
   {
      if (aCacheDuration >= 0)
      {
         mCacheDuration = aCacheDuration;
      }
   }

   /**
    * @return true if cache is enabled.
    */
   protected boolean isCacheEnabled()
   {
      return getCacheSize() > 0;
   }

   /**
    * Called to let the implementations create the cache.
    */
   protected abstract void createCache();

   /**
    * Called when the cache size has changed.
    * @param aOldSize current size
    * @param aNewSize new size;
    */
   protected abstract void updateCacheSize(int aOldSize, int aNewSize);

   /**
    * Clears the internal cache.
    */
   protected abstract void invalidateCache();

   /**
    * Overrides to create and return an input source given the URL. This method first checks to see if
    * the input source data is cached. If the data is available in the cache,
    * then the InputSource is created from the cached data, otherwise the
    * content will be downloaded and cached prior to returning an input source.
    *
    * @see org.activebpel.b4p.war.xsl.AeTaskXslStylesheetStore#createURLSource(java.net.URL)
    */
   protected InputSource createURLSource(URL aUrl, AeHtCredentials aCredentials) throws FileNotFoundException, Exception
   {
      if ( isCacheEnabled() )
      {
         // return input source from cache.
         // o = sub.get(key)
         // if o == null;
         return internalCreateURLSource(aUrl, aCredentials);
      }
      else
      {
         // if cache is disabled, then use the method in the base class to return content (uncached).
         return super.createURLSource(aUrl, aCredentials);
      }
   }

   /**
    * Retrieves the XSL content and return an byte array.
    * @param aUrl URL to the XSL content
    * @return xsl content data.
    * @throws FileNotFoundException
    * @throws Exception
    */
   protected byte [] fetchContent(URL aUrl, AeHtCredentials aCredentials) throws FileNotFoundException, Exception
   {
      // fetch content.
      ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
      BufferedInputStream bis = null;
      try
      {
         bis = new BufferedInputStream( getUrlInputStream(aUrl, aCredentials) );
         AeFileUtil.copy(bis, byteStream);
         return byteStream.toByteArray();
      }
      finally
      {
         AeCloser.close(bis);
         AeCloser.close(byteStream);
      }
   }

   /**
    * Creates and returns an input source from a cache given the URL. If the source is
    * not available in the cache, then the content will be fetched from the remote or local
    * location and added to the cache.
    * @param aUrl Input source URL
    * @return InputSource
    * @throws Exception
    */
   protected abstract InputSource internalCreateURLSource(URL aUrl, AeHtCredentials aCredentials) throws FileNotFoundException, Exception;

   /**
    * Timer task to clear the cache.
    */
   private class AeClearXslCacheTimerTask extends TimerTask
   {
      public void run()
      {
         synchronized(MUTEX)
         {
            invalidateCache();
         }
      }
   }
}
