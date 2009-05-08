//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/invoke/AeTaskStorageListTaskAbstractsSerializer.java,v 1.1 2008/02/02 19:11:36 PJayanetti Exp $
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
import org.activebpel.rt.b4p.server.storage.IAeB4PStorageConstants;
import org.activebpel.rt.ht.api.IAeHtApiTaskList;
import org.activebpel.rt.ht.api.io.AeGetMyTaskAbstractsResponseSerializer;
import org.w3c.dom.Element;

/**
 * Responsible for serializing the list of tasks into a tss:listTaskAbstractsResponse element.
 */
public class AeTaskStorageListTaskAbstractsSerializer extends AeGetMyTaskAbstractsResponseSerializer
{
   /**
    * Ctor.
    * @param aTaskList
    */
   public AeTaskStorageListTaskAbstractsSerializer(IAeHtApiTaskList aTaskList)
   {
      super(aTaskList);
   }

   /**
    * Overrides method to serialize list of tasks into a tss:listTaskAbstractsResponse element.
    * @see org.activebpel.rt.xml.AeXMLSerializerBase#serialize(org.w3c.dom.Element)
    */
   public Element serialize(Element aParentElement) throws AeException
   {
      // Create root element tss:listTaskAbstractsResponse
      Element root = createRootElement(aParentElement, IAeB4PStorageConstants.TASK_STORAGE_NS, IAeB4PStorageConstants.TASK_STORAGE_PREFIX, "listTaskAbstractsResponse"); //$NON-NLS-1$
      // serialize tasks into the htdt:getMyTaskAbstracts child element.
      super.serialize(root);
      return root;
   }
}
