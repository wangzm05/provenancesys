//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/AeHtClientResponse.java,v 1.2 2008/02/17 21:08:40 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.service;

import javax.activation.DataHandler;

import org.w3c.dom.Document;

/**
 * Encapsulates ht client service response data.
 */
public class AeHtClientResponse
{
   /**
    * Document response
    */
   private Document mDocument;

   /**
    * Optional attachments.
    */
   private DataHandler[] mDataHandlers;

   /**
    * Default ctor
    * @param aResponse
    */
   public AeHtClientResponse(Document aResponse)
   {
      setDocument(aResponse);
   }

   /**
    * Constucts response with attachment data handler.
    * @param aResponse
    * @param aDataHandler
    */
   public AeHtClientResponse(Document aResponse, DataHandler aDataHandler)
   {
      this(aResponse);
      DataHandler[] dataHandlers = new DataHandler[1];
      setDataHandlers(dataHandlers);
   }
   
   /**
    * Returns true if the response is empty (void).
    * The response document is <code>null</code> for empty or void response.
    */
   public boolean isEmpty()
   {
      return getDocument() == null;
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
