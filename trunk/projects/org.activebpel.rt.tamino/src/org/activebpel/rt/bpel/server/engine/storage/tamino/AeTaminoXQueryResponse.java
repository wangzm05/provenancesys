// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/AeTaminoXQueryResponse.java,v 1.1 2007/08/17 00:57:36 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.bpel.server.engine.storage.tamino;

import com.softwareag.tamino.db.api.objectModel.TIteratorException;
import com.softwareag.tamino.db.api.objectModel.TNoSuchXMLObjectException;
import com.softwareag.tamino.db.api.objectModel.TXMLObject;
import com.softwareag.tamino.db.api.objectModel.TXMLObjectIterator;
import com.softwareag.tamino.db.api.response.TResponse;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse;
import org.w3c.dom.Element;

/**
 * A Tamino implementation of an XQuery response.
 */
public class AeTaminoXQueryResponse implements IAeXMLDBXQueryResponse
{
   /** Iterator over the XML objects. */
   private TXMLObjectIterator mXMLIterator;

   /**
    * C'tor.
    * 
    * @param aResponse
    */
   public AeTaminoXQueryResponse(TResponse aResponse)
   {
      if (aResponse.hasQueryContent() && aResponse.hasFirstXMLObject())
      {
         setXMLIterator(aResponse.getXMLObjectIterator());
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse#hasNextElement()
    */
   public boolean hasNextElement()
   {
      if (getXMLIterator() != null)
         return getXMLIterator().hasNext();
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse#nextElement()
    */
   public Element nextElement()
   {
      try
      {
         TXMLObject xmlObject = getXMLIterator().next();
         return (Element) xmlObject.getElement();
      }
      catch (TNoSuchXMLObjectException ex)
      {
         throw new RuntimeException(ex);
      }
      catch (TIteratorException ex)
      {
         throw new RuntimeException(ex);
      }
   }

   /**
    * @return Returns the xMLIterator.
    */
   protected TXMLObjectIterator getXMLIterator()
   {
      return mXMLIterator;
   }

   /**
    * @param aIterator the xMLIterator to set
    */
   protected void setXMLIterator(TXMLObjectIterator aIterator)
   {
      mXMLIterator = aIterator;
   }
}
