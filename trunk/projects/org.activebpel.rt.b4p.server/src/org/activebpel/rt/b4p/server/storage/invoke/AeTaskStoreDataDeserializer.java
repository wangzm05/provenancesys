//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/invoke/AeTaskStoreDataDeserializer.java,v 1.2 2008/02/17 21:36:33 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage.invoke;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.server.storage.AeTaskStoreData;
import org.activebpel.rt.util.AeXPathUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Deserializes a tss:storeTaskData element into a AeTaskStoreData object
 */
public class AeTaskStoreDataDeserializer extends AeTaskStorageDeserializerBase
{
   /** object representing the tss:storeTaskData element. */
   private AeTaskStoreData mTaskStoreData;

   /**
    * Ctor.
    * @param aStoreTaskDataDocument
    */
   public AeTaskStoreDataDeserializer(Document aStoreTaskDataDocument)
   {
      setElement(aStoreTaskDataDocument.getDocumentElement());
   }

   /**
    * Returns deserialize AeTaskStoreData data.
    */
   public AeTaskStoreData getTaskStoreData() throws AeException
   {
      if (mTaskStoreData == null)
      {
         long pid = AeXPathUtil.selectLong(getElement(), "tss:processId", getNamespaceMap()); //$NON-NLS-1$
         Element trtTaskEle = (Element) AeXPathUtil.selectSingleNode(getElement(), "trt:taskInstance", getNamespaceMap()); //$NON-NLS-1$
         boolean update = AeXPathUtil.selectBoolean(getElement(), "tss:update", getNamespaceMap()); //$NON-NLS-1$
         mTaskStoreData = new AeTaskStoreData(pid, trtTaskEle, update);
      }
      return mTaskStoreData;
   }
}
