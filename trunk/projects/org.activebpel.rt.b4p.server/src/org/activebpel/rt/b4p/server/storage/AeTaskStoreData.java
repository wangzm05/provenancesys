//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/AeTaskStoreData.java,v 1.1 2008/02/02 19:11:36 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage;

import org.w3c.dom.Element;

/**
 * Simple struct which represents the tss:storeTaskData element as defined in
 * the tasks storage wsdl.
 */
public class AeTaskStoreData
{
   /** process id */
   private long mProcessId;
   /** trt:taskInstance element */
   private Element mTaskInstanceElement;
   /** Indicates if the data is meant for updating the store.*/
   private boolean mUpdate;
   
   /**
    * Ctor
    * @param aProcessId
    * @param aTaskInstanceElement
    * @param aUpdate
    */
   public AeTaskStoreData(long aProcessId, Element aTaskInstanceElement, boolean aUpdate)
   {
      mProcessId = aProcessId;
      mTaskInstanceElement = aTaskInstanceElement;
      mUpdate = aUpdate;
   }

   /**
    * @return the processId
    */
   public long getProcessId()
   {
      return mProcessId;
   }

   /**
    * The task runtime element (trt:taskInstance).
    * @return the taskInstanceElement
    */
   public Element getTaskInstanceElement()
   {
      return mTaskInstanceElement;
   }

   /**
    * Returns true if this data is meant for an update
    * @return the update
    */
   public boolean isUpdate()
   {
      return mUpdate;
   }
}
