//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeGetMyTasksSerializer.java,v 1.1 2008/02/09 00:03:17 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api.io;

import org.activebpel.rt.ht.api.AeGetTasksParam;
import org.w3c.dom.Element;

/**
 * Serializes a AeGetTasksParam into ht api:getMyTasks element.
 */
public class AeGetMyTasksSerializer extends AeGetMyTaskAbstractsSerializer
{
   /**
    * Ctor
    * @param aGetTasksParam
    */
   public AeGetMyTasksSerializer(AeGetTasksParam aGetTasksParam)
   {
      super(aGetTasksParam);
   }

   /**
    * Overrides method to create a ht api:getMyTasks element
    * @see org.activebpel.rt.ht.api.io.AeGetMyTaskAbstractsSerializer#createRequestElement(org.w3c.dom.Element)
    */
   protected Element createRequestElement(Element aParentElement)
   {
      return createRootElement(aParentElement, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "getMyTasks"); //$NON-NLS-1$
   }
}
