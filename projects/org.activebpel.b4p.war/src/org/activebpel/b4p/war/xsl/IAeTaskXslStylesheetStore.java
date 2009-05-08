//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/xsl/IAeTaskXslStylesheetStore.java,v 1.3 2008/02/20 15:58:19 PJayanetti Exp $
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
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;

import org.activebpel.b4p.war.service.AeHtCredentials;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * The task xls style store provides access to the xsl sources needed for
 * rendering a task detail page as well as for processing the input data from the
 * task detail page.
 *
 * This interface is responsible for resolving a xsl rendering hint given
 * the aet:customRenderings element and retrieving the xsl source from
 * the remote location and or from a local cache.
 */
public interface IAeTaskXslStylesheetStore
{

   /**
    * Transforms the xml source given the xsl stylesheet.
    * @param aXslSource XSL for either the command or presentation rendering.
    * @param aXmlSource xml data source.
    * @param aParams tranform input parameters.
    * @param aCredentials catalog access credentials
    * @return transformed document.
    * @throws AeImportNotFoundTransformerException if an import cannot be resolved and located.
    * @throws TransformerException
    */
   public Node doTransform(Source aXslSource, Source aXmlSource, Map aParams, AeHtCredentials aCredentials) throws AeImportNotFoundTransformerException, TransformerException;

   /**
    * Returns the xsl source that is used transform the html form data parameter document
    * to a task command document containing task command declarations. The xsl source is based
    * on custom rendering hints.
    *
    * @param aTaskId taskId
    * @param aCustomerRenderingsElem aet:customerRenderings element
    * @param aCredentials catalog access credentials
    * @return xsl source or <code>null</code> if not found.    
    * @throws FileNotFoundException if xsl stylesheet cannot be located.
    */
   public Source getTaskCommandStylesheet(String aTaskId, Element aCustomerRenderingsElem, AeHtCredentials aCredentials) throws FileNotFoundException, Exception;
   
   /**
    * Returns a cached version of the xsl source or <code>null</code> if not available.
    * @param aTaskId
    * @param aCredentials catalog access credentials
    * @return xsl source
    * @throws FileNotFoundException if xsl stylesheet cannot be located.
    */
   public Source getTaskCommandStylesheet(String aTaskId, AeHtCredentials aCredentials) throws FileNotFoundException, Exception;

   /**
    * Returns the xsl source that is used transform the task document document
    * to html presentation The xsl source is based on custom rendering hints.
    *
    * @param aTaskId task id
    * @param aCustomerRenderingsElem element containing xsl rendering hints (location URIs of xsl resources).
    * @param aForNotification true if the default xsl source should be for the task notification.
    * @param aCredentials catalog access credentials
    * @return xsl source
    * @throws FileNotFoundException if xsl stylesheet cannot be located.
    */
   public Source getTaskRenderingStylesheet(String aTaskId, Element aCustomerRenderingsElem, boolean aForNotification, AeHtCredentials aCredentials) throws FileNotFoundException, Exception;
   /**
    * Returns a cached version of the xsl source or <code>null</code> if not available.
    * @param aTaskId
    * @param aCredentials catalog access credentials 
    * @return xsl source
    * @throws FileNotFoundException if xsl stylesheet cannot be located.
    */
   public Source getTaskRenderingStylesheet(String aTaskId, AeHtCredentials aCredentials) throws FileNotFoundException, Exception;

}
