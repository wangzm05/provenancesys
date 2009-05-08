//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/AeHtClientRequest.java,v 1.2 2008/02/07 22:37:49 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.activation.DataHandler;

import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Simple wrapper around request data.
 */
public class AeHtClientRequest
{
   /**
    * Document request
    */
   private Document mDocument;

   /** Optional ae extension header data. */
   private Map mExtensionHeaders;
   
   /**
    * Optional attachments.
    */
   private DataHandler[] mDataHandlers;

   /**
    * Default ctor
    * @param aRequest
    */
   public AeHtClientRequest(Document aRequest)
   {
      setDocument(aRequest);
   }
   
   /**
    * Ctor given the request element.
    * @param aRequestElement
    */
   public AeHtClientRequest(Element aRequestElement)
   {
      Document doc = null;
      // if the aRequestMessageElement is the root element of the document
      // then, send it as is. Otherise, create a new document with aRequestMessageElement 
      // as the root element.
      if (aRequestElement.getOwnerDocument().getDocumentElement() == aRequestElement)
      {
         doc = aRequestElement.getOwnerDocument();
      }
      else
      {
         doc = AeXmlUtil.newDocument();
         Node root = doc.importNode(aRequestElement, true);
         doc.appendChild(root);
      }      
      setDocument(doc);
   }   

   /**
    * Constucts request with attachment data handler.
    * @param aRequest
    * @param aDataHandler
    */
   public AeHtClientRequest(Document aRequest, DataHandler aDataHandler)
   {
      this(aRequest);
      DataHandler[] dataHandlers = new DataHandler[1];
      setDataHandlers(dataHandlers);
   }

   /**
    * Adds an AE extension header name and value.
    * @param aName
    * @param aValue
    */
   public void addExtensionHeader(String aName, String aValue)
   {
      if (mExtensionHeaders == null)
      {
         mExtensionHeaders = new LinkedHashMap();
      }
      mExtensionHeaders.put(aName, aValue);
   }

   /**
    * @return the document
    */
   public Document getDocument()
   {
      return mDocument;
   }
   /**
    * @param aDocument the document to set
    */
   public void setDocument(Document aDocument)
   {
      mDocument = aDocument;
   }
   
   /**
    * @return the extensionHeaders
    */
   public Map getExtensionHeaders()
   {
      if (mExtensionHeaders == null)
      {
         return Collections.EMPTY_MAP;
      }
      return mExtensionHeaders;
   }
   
   /**
    * @param aExtensionHeaders the extensionHeaders to set
    */
   public void setExtensionHeaders(Map aExtensionHeaders)
   {
      mExtensionHeaders = aExtensionHeaders;
   }

   /**
    * @return the dataHandlers
    */
   public DataHandler[] getDataHandlers()
   {
      if (mDataHandlers == null)
      {
         return new DataHandler[0];
      }
      return mDataHandlers;
   }

   /**
    * @param aDataHandlers the dataHandlers to set
    */
   public void setDataHandlers(DataHandler[] aDataHandlers)
   {
      mDataHandlers = aDataHandlers;
   }
}
