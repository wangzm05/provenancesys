//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/invoke/AeTaskStorageListTasksSerializer.java,v 1.2 2008/02/27 19:23:11 PJayanetti Exp $
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
import org.activebpel.rt.b4p.IAeProcessTaskConstants;
import org.activebpel.rt.b4p.server.storage.IAeB4PStorageConstants;
import org.activebpel.rt.ht.api.IAeHtApiTaskList;
import org.activebpel.rt.ht.api.io.AeGetMyTasksResponseSerializer;
import org.w3c.dom.Element;

/**
 * Responsible for serializing the list of tasks into a tss:listTasksResponse element.
 */
public class AeTaskStorageListTasksSerializer extends AeGetMyTasksResponseSerializer
{
   /**
    * Ctor.
    * @param aTaskList
    */
   public AeTaskStorageListTasksSerializer(IAeHtApiTaskList aTaskList)
   {
      super(aTaskList);
   }

   /**
    * Overrides method to serialize list of tasks into a tss:listTasksResponse element.
    * @see org.activebpel.rt.xml.AeXMLSerializerBase#serialize(org.w3c.dom.Element)
    */
   public Element serialize(Element aParentElement) throws AeException
   {
      // Create root element tss:listTasksResponse
      Element root = createRootElement(aParentElement, IAeB4PStorageConstants.TASK_STORAGE_NS, IAeB4PStorageConstants.TASK_STORAGE_PREFIX, "listTasksResponse"); //$NON-NLS-1$
      // serialize totalTasksCount
      createElementWithText(root, IAeProcessTaskConstants.TASK_STATE_WSDL_NS, "tsst", "taskTotalCount", String.valueOf(getTaskList().getTotalTasks())); //$NON-NLS-1$ //$NON-NLS-2$
      // serialize tasks into the htdt:getMyTasksResponse child element.
      super.serialize(root);
      return root;
   }
}
