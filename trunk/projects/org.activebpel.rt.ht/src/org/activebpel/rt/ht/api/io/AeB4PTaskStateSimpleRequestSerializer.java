//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeB4PTaskStateSimpleRequestSerializer.java,v 1.1 2008/01/18 22:51:53 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api.io;

import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * Serializer for task state wsdl requests that are based on a identifier type such as tsst:getTaskInstance request element.
 */
public class AeB4PTaskStateSimpleRequestSerializer extends AeHtSerializerWithIdentifierBase
{
   /**
    * Ctor.
    * @param aIdentifier task id
    */
   public AeB4PTaskStateSimpleRequestSerializer(String aRequestName, String aIdentifier)
   {
      super(aRequestName,aIdentifier);
   }

   public Element serialize(Element aParentElement) throws AeException
   {
      Element getTaskInstanceElement = createRootElement(aParentElement, AEB4P_TASKSTATE_NAMESPACE, AEB4P_TASKSTATE_PREFIX, getRequestName());
      AeXmlUtil.addText(getTaskInstanceElement, getIdentifier() );
      return getTaskInstanceElement;
   }

}
