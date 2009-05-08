//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeGetMyTasksDeserializer.java,v 1.1 2008/01/18 22:51:53 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api.io;

import org.w3c.dom.Element;

/**
 * Deserializes a ht api:getMyTasks element into a AeGetTasksParam.
 */
public class AeGetMyTasksDeserializer extends AeGetMyTaskAbstractsDeserializer
{
   /**
    * Ctor.
    * @param aElement
    */
   public AeGetMyTasksDeserializer(Element aElement)
   {
      super(aElement);
   }
}
