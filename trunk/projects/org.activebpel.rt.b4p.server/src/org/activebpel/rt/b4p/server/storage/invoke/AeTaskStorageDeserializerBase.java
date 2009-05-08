//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/invoke/AeTaskStorageDeserializerBase.java,v 1.2 2008/02/27 19:23:11 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage.invoke;

import java.util.Map;

import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.IAeProcessTaskConstants;
import org.activebpel.rt.b4p.server.storage.IAeB4PStorageConstants;
import org.activebpel.rt.ht.api.IAeHtWsIoConstants;
import org.activebpel.rt.xml.AeXMLDeserializerBase;

/**
 * Base class for task storage data serializers,
 */
public class AeTaskStorageDeserializerBase extends AeXMLDeserializerBase
{
   /**
    * @see org.activebpel.rt.xml.AeXMLDeserializerBase#initNamespaceMap(java.util.Map)
    */
   protected void initNamespaceMap(Map aMap)
   {
      aMap.put("tss", IAeB4PStorageConstants.TASK_STORAGE_NS); //$NON-NLS-1$
      aMap.put("tsst", IAeProcessTaskConstants.TASK_STATE_WSDL_NS); //$NON-NLS-1$
      aMap.put(IAeHtWsIoConstants.WSHT_API_XSD_PREFIX, IAeHtWsIoConstants.WSHT_API_XSD_NAMESPACE);
      aMap.put("trt", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
   }
}
