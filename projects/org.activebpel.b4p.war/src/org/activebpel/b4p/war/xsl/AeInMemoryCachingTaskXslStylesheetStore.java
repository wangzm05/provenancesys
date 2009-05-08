//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/xsl/AeInMemoryCachingTaskXslStylesheetStore.java,v 1.3 2008/02/20 15:58:19 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.xsl;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import org.activebpel.b4p.war.service.AeHtCredentials;
import org.activebpel.rt.util.AeLRUObjectCache;
import org.xml.sax.InputSource;

/**
 * Extends the AeTaskXslStylesheetStore to provide a LRU based in-memory
 * caching of the xml input sources.
 */
public class AeInMemoryCachingTaskXslStylesheetStore extends AeAbstractCachingTaskXslStylesheetStore
{
   /** Cache containing XSL Source objects keyed by URL. */
   private AeLRUObjectCache mXslInputSourceCache;

   /**
    * Overrides method to create an in memory cache.
    * @see org.activebpel.b4p.war.xsl.AeAbstractCachingTaskXslStylesheetStore#createCache()
    */
   protected void createCache()
   {
      int size = Math.max( getCacheSize(), 1);
      mXslInputSourceCache = new AeLRUObjectCache( size );
   }

   /**
    * Overrides method to set the max cache size in the internal LRU
    * @see org.activebpel.b4p.war.xsl.AeAbstractCachingTaskXslStylesheetStore#updateCacheSize(int, int)
    */
   protected void updateCacheSize(int aOldSize, int aNewSize)
   {
      int size = Math.max( aNewSize, 1);
      if (mXslInputSourceCache != null)
      {
         mXslInputSourceCache.setMaxSize(size);
      }
   }

   /**
    * @return LRU cache containg the xsl source objects.
    */
   protected AeLRUObjectCache getXslInputSourceCache()
   {
      return mXslInputSourceCache;
   }

   /**
    * Overrides method to clear the input source data cache.
    * @see org.activebpel.b4p.war.xsl.AeAbstractCachingTaskXslStylesheetStore#invalidateCache()
    */
   public void invalidateCache()
   {
      getXslInputSourceCache().clear();
   }

   /**
    * Overrides to create and return an input source given the URL. This method first checks to see if
    * the input source data is cached. If the data is available in the cache,
    * then the InputSource is created from the cached data, otherwise the
    * content will be downloaded and cached prior to returning an input source.
    *
    * @see org.activebpel.b4p.war.xsl.AeAbstractCachingTaskXslStylesheetStore#internalCreateURLSource(java.net.URL)
    */
   protected InputSource internalCreateURLSource(URL aUrl, AeHtCredentials aCredentials) throws FileNotFoundException, Exception
   {
      // Check cache (for a local file based source).
      AeXslContentCacheData data = (AeXslContentCacheData) getXslInputSourceCache().get(aUrl.toString());
      if (data == null )
      {
         // fetch content
         byte content[] = fetchContent(aUrl, aCredentials);
         // cache data
         data = new AeXslContentCacheData( content, aUrl.toString() );
         getXslInputSourceCache().cache(aUrl.toString(), data);
      }
      InputSource is = new InputSource(new ByteArrayInputStream(data.getBytes()));
      is.setSystemId(data.getSystemId());
      return is;
   }

   /**
    * Wrapper which is used to cache xsl content
    * in memory.
    */
   private class AeXslContentCacheData
   {
      /** system id based on the url. */
      private String mSystemId;
      /** xsl data */
      private byte mBytes[];

      /**
       * Ctor.
       * @param aBytes
       * @param aSystemId
       */
      public AeXslContentCacheData(byte aBytes[], String aSystemId)
      {
         mBytes = aBytes;
         mSystemId = aSystemId;
      }

      /**
       * @return xsl content.
       */
      public byte[] getBytes()
      {
         return mBytes;
      }

      /**
       * @return input source system id.
       */
      public String getSystemId()
      {
         return mSystemId;
      }
   }

}
