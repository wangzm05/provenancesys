//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeGetAttachmentInfoResponseDeserializer.java,v 1.1 2008/01/18 22:51:53 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api.io;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.ht.api.AeAttachmentInfo;
import org.activebpel.rt.util.AeXPathUtil;
import org.w3c.dom.Element;

/**
 * Deserializes a wsht api:getAttachmentInfoResponse message element.
 */
public class AeGetAttachmentInfoResponseDeserializer extends AeHtAttachmentInfoDeserializerBase
{
   /** List of deserialized attachment infos. */
   private List mAttachmentInfoList;
   /**
    * Ctor.
    * @param aTaskId task id
    * @param aResponseElement the api:getAttachmentInfoResponse element.
    */
   public AeGetAttachmentInfoResponseDeserializer(String aTaskId, Element aResponseElement)
   {
      setTaskId(aTaskId);
      setElement(aResponseElement);
   }

   public List getAttachmentInfoList() throws Exception
   {
      if (mAttachmentInfoList == null)
      {
         mAttachmentInfoList = deserialize();
      }
      return mAttachmentInfoList;
   }

   /**
    * Deserialize a api:getAttachmentInfoResponse element
    * @return list of AeAttachmentInfo objects
    * @throws Exception
    */
   protected List deserialize() throws Exception
   {
      List rval = new ArrayList();
      List infoElemList = AeXPathUtil.selectNodes(getElement(), "htdt:info", getNamespaceMap()); //$NON-NLS-1$
      Iterator it = infoElemList.iterator();
      while ( it.hasNext() )
      {
         AeAttachmentInfo info = deserializeAttachmentInfo(getTaskId() ,(Element) it.next() );
         rval.add(info);
      }
      return rval;
   }
}
