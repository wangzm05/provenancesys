//$Header$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.bpel.server.engine.storage.tamino.handlers;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBResponseHandler;
import org.w3c.dom.Element;

/**
 * This response handler counts the number of doc instances updated or deleted by an XQuery. It can optionally
 * count only doc instances of a specific doc type (configured via the constructor). If that parameter is
 * null, then the handler will count all documents.
 */
public class AeTaminoUpdateOrDeleteCountResponseHandler extends AeXMLDBResponseHandler
{
   /** The doc type to tally - can be null to indicate "all". */
   private String mTallyDocType;

   /**
    * Default constructor indicating "count all".
    */
   public AeTaminoUpdateOrDeleteCountResponseHandler()
   {
      this(null);
   }
   
   /**
    * Constructor which includes a doc type name to use when counting.
    * 
    * @param aTallyDocType
    */
   public AeTaminoUpdateOrDeleteCountResponseHandler(String aTallyDocType)
   {
      setTallyDocType(aTallyDocType);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBResponseHandler#handleResponse(org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse)
    */
   public Object handleResponse(IAeXMLDBXQueryResponse aResponse) throws AeXMLDBException
   {
      String tallyDocType = getTallyDocType();

      int count = 0;
      while (aResponse.hasNextElement())
      {
         Element elem = aResponse.nextElement();
         String name = elem.getAttribute("ino:doctype"); //$NON-NLS-1$
         if (tallyDocType == null || name.equals(tallyDocType))
         {
            count++;
         }

      }
      return new Integer(count);
   }

   /**
    * @return Returns the tallyDocType.
    */
   protected String getTallyDocType()
   {
      return mTallyDocType;
   }

   /**
    * @param aTallyDocType The tallyDocType to set.
    */
   protected void setTallyDocType(String aTallyDocType)
   {
      mTallyDocType = aTallyDocType;
   }
}
