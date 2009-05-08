//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/invoke/AeTaskStorageListTaskAbstractsDeserializer.java,v 1.2 2008/02/09 00:08:35 PJayanetti Exp $
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
import org.activebpel.rt.util.AeXPathUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Deserializes tss:listTaskAbstracts element
 */
public class AeTaskStorageListTaskAbstractsDeserializer extends AeTaskStorageListDeserializerBase
{
   /**
    * Ctor.
    * @param aListTaskAbstractsDocument
    */
   public AeTaskStorageListTaskAbstractsDeserializer(Document aListTaskAbstractsDocument)
   {
      super(aListTaskAbstractsDocument);
   }

   /**
    * Overrides method to return htdt:getMyTaskAbstracts element.
    * @see org.activebpel.rt.b4p.server.storage.invoke.AeTaskStorageListDeserializerBase#getHtdTaskListElement()
    */
   protected Element getHtdTaskListElement() throws AeException
   {
      Element ele = (Element) AeXPathUtil.selectSingleNode(getElement(), "htdt:getMyTaskAbstracts", getNamespaceMap()); //$NON-NLS-1$
      return ele;
   }

}
