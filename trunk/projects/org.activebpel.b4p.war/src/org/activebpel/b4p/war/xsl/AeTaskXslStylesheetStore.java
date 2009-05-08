//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/xsl/AeTaskXslStylesheetStore.java,v 1.8 2008/03/21 21:06:33 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.xsl;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import org.activebpel.b4p.war.AeWorkFlowApplicationFactory;
import org.activebpel.b4p.war.service.AeHtCredentials;
import org.activebpel.rt.AeException;
import org.activebpel.rt.base64.BASE64Encoder;
import org.activebpel.rt.util.AeLRUObjectCache;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * Implements a simple store.
 */
public class AeTaskXslStylesheetStore implements IAeTaskXslStylesheetStore
{
   /** Default xsl used to render the generic work item. */
   private final static String DEFAULT_PRESENTATION_XSL_URI = "urn:/aeb4p/rendering/xsl/ae_default_task.xsl"; //$NON-NLS-1$
   /** Default xsl used to render the generic work item. */
   private final static String DEFAULT_NOTIFICATION_XSL_URI = "urn:/aeb4p/rendering/xsl/ae_default_notification.xsl"; //$NON-NLS-1$   
   /** Default xsl used to create the command document. */
   private final static String DEFAULT_COMMAND_XSL_URI = "urn:/aeb4p/rendering/xsl/ae_default_param2command.xsl"; //$NON-NLS-1$  
   
   /** The generic task detail sl renderings used by AE. */
   private final static AeXslTaskRenderingHint DEFAULT_PRESENTATION_RENDERINGS = new AeXslTaskRenderingHint(DEFAULT_PRESENTATION_XSL_URI, DEFAULT_COMMAND_XSL_URI);
   /** The generic notifications xsl renderings used by AE. */
   private final static AeXslTaskRenderingHint DEFAULT_NOTIFICATION_RENDERINGS = new AeXslTaskRenderingHint(DEFAULT_NOTIFICATION_XSL_URI, DEFAULT_COMMAND_XSL_URI);
   
   /** Cache containing AeXslTaskRenderingHint objects keyed by task ref. */
   private AeLRUObjectCache mRenderingHintCache = new AeLRUObjectCache(1000);

   /**
    * @return LRU cache containg the rendering hints.
    */
   protected AeLRUObjectCache getRenderingHintCache()
   {
      return mRenderingHintCache;
   }

   /**
    * Attemps to located a cached AeXslTaskRenderingHint object.
    * Returns <code>null</code> if not found.
    * @param aTaskId
    */
   protected AeXslTaskRenderingHint findRenderingHint(String aTaskId)
   {
      return (AeXslTaskRenderingHint) getRenderingHintCache().get(aTaskId);
   }

   /**
    * Attempts to find the xsl rendering hint given the task reference and aet:customRenderings element.
    * Returns <code>null</code> if not found.
    * @param aTaskId
    * @param aTrtRenderingElem
    * @return AeXslTaskRenderingHint containing uris for presentation and command xsl.
    */
   protected AeXslTaskRenderingHint findRenderingHint(String aTaskId, Element aTrtRenderingElem)
   {
      AeXslTaskRenderingHint renderHint = null;
      if (aTrtRenderingElem != null)
      {
         try
         {
            Element xslRenderElem = (Element)AeXPathUtil.selectSingleNode(aTrtRenderingElem, "hdt:rendering/trt:xsl", AeXslTaskConstants.NSS_MAP); //$NON-NLS-1$
            if (xslRenderElem != null)
            {
               String presentation = AeXPathUtil.selectText(xslRenderElem, "trt:presentation-xsl", AeXslTaskConstants.NSS_MAP); //$NON-NLS-1$
               String command = AeXPathUtil.selectText(xslRenderElem, "trt:command-xsl", AeXslTaskConstants.NSS_MAP); //$NON-NLS-1$
               if (AeUtil.isNullOrEmpty(command))
               {
                  command = DEFAULT_COMMAND_XSL_URI;
               }
               if (AeUtil.notNullOrEmpty(presentation))                  
               {
                  renderHint = new AeXslTaskRenderingHint(presentation,command);
                  getRenderingHintCache().cache(aTaskId, renderHint);
               }
            }
         }
         catch(Exception e)
         {
            AeException.logError(e);
         }
      }

      return renderHint;
   }

   /**
    * Attempts to find the xsl rendering hint given the task reference and aet:customRenderings element.
    * Returns <strong>default rendering hints</strong> if the requested hints are not found.
    * @param aTaskId
    * @param aTrtRenderingElem
    * @param aForNotification which indicates that the rendering is mean for notification view.
    * @return AeXslTaskRenderingHint
    */
   protected AeXslTaskRenderingHint getRenderingHint(String aTaskId, Element aTrtRenderingElem, boolean aForNotification)
   {
      AeXslTaskRenderingHint hint = findRenderingHint(aTaskId, aTrtRenderingElem);
      if (hint == null)
      {
         hint = aForNotification? DEFAULT_NOTIFICATION_RENDERINGS : DEFAULT_PRESENTATION_RENDERINGS;
      }
      return hint;
   }

   /**
    * Clears the rendering hint cache.
    */
   public void clearRenderingCache()
   {
      getRenderingHintCache().clear();
   }

   /**
    * Overrides method to perform the xsl transformation using a custom URI resolver.
    * @see org.activebpel.b4p.war.xsl.IAeTaskXslStylesheetStore#doTransform(javax.xml.transform.Source, javax.xml.transform.Source, java.util.Map)
    */
   public Node doTransform(Source aXslSource, Source aXmlSource, Map aParams, AeHtCredentials aCredentials) throws TransformerException
   {
      return  AeXmlUtil.doTransform(aXslSource, aXmlSource, aParams, createURIResolver(aCredentials) );
   }

   /**
    * @return Creates and returns the custom xsl URI resolver used for transformations.
    */
   protected URIResolver createURIResolver(AeHtCredentials aCredentials)
   {
      return new AeTaskXslUriResolver(aCredentials);
   }

   /**
    * Overrides method to xsl source from the custom rendering hints. If the xls rendering
    * information is not available, then the default xsl source for the command transformation
    * is returned.
    * @see org.activebpel.b4p.war.xsl.IAeTaskXslStylesheetStore#getTaskCommandStylesheet(java.lang.String, org.w3c.dom.Element)
    */
   public Source getTaskCommandStylesheet(String aTaskId, Element aTrtRenderingElem, AeHtCredentials aCredentials) throws FileNotFoundException, Exception
   {
      AeXslTaskRenderingHint hint = getRenderingHint(aTaskId, aTrtRenderingElem, false);
      return getResolvedXslSource(hint.getCommandXslUri(), aCredentials);
   }

   /**
    * Overrides method to xsl source from an internal cache. Returns <code>null</code> if the xls source
    * information is not available in the cache.
    * @see org.activebpel.b4p.war.xsl.IAeTaskXslStylesheetStore#getTaskCommandStylesheet(java.lang.String)
    */
   public Source getTaskCommandStylesheet(String aTaskId, AeHtCredentials aCredentials) throws FileNotFoundException, Exception
   {
      AeXslTaskRenderingHint hint = findRenderingHint(aTaskId);
      if (hint != null)
      {
         return getResolvedXslSource(hint.getCommandXslUri(), aCredentials);
      }
      else
      {
         return null;
      }
   }

   /**
    * Overrides method to xsl source from the custom rendering hints. If the xls rendering
    * information is not available, then the default xsl source for the presentation transformation
    * is returned.
    * @see org.activebpel.b4p.war.xsl.IAeTaskXslStylesheetStore#getTaskRenderingStylesheet(java.lang.String, org.w3c.dom.Element, boolean)
    */
   public Source getTaskRenderingStylesheet(String aTaskId, Element aTrtRenderingElem, boolean aForNotification, AeHtCredentials aCredentials) throws FileNotFoundException, Exception
   {
      AeXslTaskRenderingHint hint = getRenderingHint(aTaskId, aTrtRenderingElem, aForNotification);
      return getTaskRenderingStylesheet(hint, aCredentials);
   }

   /**
    * Overrides method to xsl source from an internal cache. Returns <code>null</code> if the xls source
    * information is not available in the cache.
    * @see org.activebpel.b4p.war.xsl.IAeTaskXslStylesheetStore#getTaskRenderingStylesheet(java.lang.String)
    */
   public Source getTaskRenderingStylesheet(String aTaskId, AeHtCredentials aCredentials) throws FileNotFoundException, Exception
   {
      AeXslTaskRenderingHint hint = findRenderingHint(aTaskId);
      return getTaskRenderingStylesheet(hint, aCredentials);
   }
   
   /**
    * Returns the XLS source  given the AeXslTaskRenderingHint. If the rendering hint's uri scheme is either project:/ or
    * urn:/, this method delegates the request to the BPEL Engine's catalog, otherwise,
    * direct HTTP request is made. 
    * @param aHint true if the 
    * @param aCredentials xsl catalog access credentials.
    * @throws FileNotFoundException
    * @throws Exception
    */
   protected Source getTaskRenderingStylesheet(AeXslTaskRenderingHint aHint, AeHtCredentials aCredentials) throws FileNotFoundException, Exception
   {
      if (aHint != null)
      {
         return getResolvedXslSource(aHint.getPresentationXslUri(), aCredentials);
      }
      else
      {
         return null;
      }
   }    

   /**
    * Returns the XLS source given the URI. If the uri scheme is either project:/ or
    * urn:/, this method delegates the request to the BPEL Engine's catalog, otherwise,
    * direct HTTP request is made.
    * @param aUri
    * @param aCredentials catalog access credentials
    * @return xsl source
    * @throws FileNotFoundException if xsl resource is not available.
    * @throws Exception
    */
   protected Source getResolvedXslSource(String aUri, AeHtCredentials aCredentials) throws FileNotFoundException, Exception
   {
      // pass in username and password only when going to access resources from the engine catalog
      if (aUri.startsWith("project:/") || aUri.startsWith("urn:/")) //$NON-NLS-1$ //$NON-NLS-2$
      {
         // e.g: urn:/aeb4p/rendering/xsl/ae_default_task.xsl ==> http://localhost:8080/active-bpel/taskxsl/urn:/aeb4p/rendering/xsl/ae_default_task.xsl
         return getCatalogSource(aUri, aCredentials);
      }
      else if (aUri.startsWith( AeWorkFlowApplicationFactory.getConfiguration().getXslCatalogEndpointURL().toExternalForm() ) )
      {
         // import of a catalog resource since uri starts with the catalog uri
         // e.g. uri = http://localhost:8080/active-bpel/taskxsl/urn:/aeb4p/rendering/xsl/ae_base_taskdetail.xsl
         // which is normally imported by the urn:/aeb4p/rendering/xsl/ae_default_task.xsl resource.
         return getURLSource(aUri, aCredentials);
      }
      else
      {
         return getURLSource(aUri, aCredentials);
      }
   }
   
 
   /**
    * Returns the XSL source by retrieving the data from the BPEL engine catalog.
    * @param aResourceUri
    * @param aCredentials
    * @return xsl data source
    * @throws FileNotFoundException if content is not available in the catalog.
    * @throws Exception
    */
   protected Source getCatalogSource(String aResourceUri, AeHtCredentials aCredentials) throws FileNotFoundException, Exception
   {
      String serverURL = AeWorkFlowApplicationFactory.getConfiguration().getXslCatalogEndpointURL().toExternalForm()
               + "/" + aResourceUri; //$NON-NLS-1$
      return getURLSource(serverURL, aCredentials);
   }

   /**
    * Returns a XSL source given the URL.The source may be created from cached data if
    * the cached data is available.
    * @param aServerURL
    * @return xsl Source.
    * @throws FileNotFoundException if the content is not available. E.g. HTTP 404 code.
    * @throws Exception
    */
   protected Source getURLSource(String aServerURL, AeHtCredentials aCredentials) throws FileNotFoundException, Exception
   {
      URL url = new URL(aServerURL);
      InputSource source = createURLSource(url, aCredentials);
      return convertInputToXslSource(source);
   }

   /**
    * Creates and returns an input source given the URL. The content is fetched
    * for each and every request (i.e. does not use any caching mechanisms).
    * Subclasses may override this method to provide support for caching.
    * @param aUrl Input source URL
    * @return InputSource
    * @throws Exception
    */
   protected InputSource createURLSource(URL aUrl, AeHtCredentials aCredentials) throws FileNotFoundException, Exception
   {
       InputSource source = new InputSource( getUrlInputStream(aUrl, aCredentials) );
       source.setSystemId( aUrl.toExternalForm() );
       return source;
   }

   /**
    * Converts the InputSource to an XSL Source
    * @param aSource
    */
   protected Source convertInputToXslSource(InputSource aSource)
   {
      // todo (PJ) remove convertInputToXslSource() method and refactor createURLSource(URL) to return a javax.transform.Source directly. 
      StreamSource source = new StreamSource( aSource.getByteStream() );
      source.setSystemId( aSource.getSystemId() );
      return source;
   }

   /**
    * Returns the input stream given the resource URL. If the resource is located
    * in the ActiveBPEL server catalog and the catalog is secured, then this
    * method sets the URL connection authorization header for the catalog endpoint.
    * @param aUrl
    * @param aCredentials xsl catalog access credentials (may be null for non-catalog access)
    * @return InputStream for the url.
    * @throws FileNotFoundException if resource cannot be located.
    * @throws Exception
    */
   protected InputStream getUrlInputStream(URL aUrl, AeHtCredentials aCredentials) throws FileNotFoundException, Exception
   {
      if (aCredentials != null && AeUtil.notNullOrEmpty( aCredentials.getUsername() ) )
      {
         // if credentials are given, then set BASIC auth header
         String creds = aCredentials.getUsername()
                           + ":" + aCredentials.getPassword(); //$NON-NLS-1$
         String auth = "Basic " + (new BASE64Encoder()).encode( creds.getBytes() ); //$NON-NLS-1$
         URLConnection con = aUrl.openConnection();
         con.setRequestProperty("Authorization", auth); //$NON-NLS-1$
         return con.getInputStream();
      }
      else
      {
         return aUrl.openStream();
      }
   }

   /**
    * Resolves any imports for the xsl.
    */
   private class AeTaskXslUriResolver implements URIResolver
   {
      /** Access username and password. */
      private AeHtCredentials mCredentials;
      
      /**
       * Ctor
       * @param aCredentials
       */
      public AeTaskXslUriResolver(AeHtCredentials aCredentials)
      {
         mCredentials = aCredentials;
      }
      
      /**
       * @return the credentials
       */
      protected AeHtCredentials getCredentials()
      {
         return mCredentials;
      }

      /**
       * Resolves the uri by creating an absolute uri and using the AE engine
       * catalog to locate resources if the uri starts with "urn:/" or "project:/"
       * protocol scheme.
       * @see javax.xml.transform.URIResolver#resolve(java.lang.String, java.lang.String)
       */
      public Source resolve(String aHref, String aBase) throws TransformerException
      {
         try
         {
            return getResolvedXslSource( AeUtil.resolveImport(aBase, aHref), getCredentials() );
         }
         catch(FileNotFoundException fnfe)
         {
            throw new AeImportNotFoundTransformerException(aBase, aHref, fnfe.getMessage());
         }
         catch(Exception e)
         {
            throw new TransformerException(e);
         }
      }
    }
}
