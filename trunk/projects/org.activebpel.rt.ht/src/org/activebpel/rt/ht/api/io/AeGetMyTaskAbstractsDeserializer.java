//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeGetMyTaskAbstractsDeserializer.java,v 1.3 2008/02/27 19:33:59 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api.io;

import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.ht.api.AeGetTasksParam;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;
import org.w3c.dom.Element;

/**
 * Deserializes a ht api:getMyTaskAbstracts element into a AeGetTasksParam.
 */
public class AeGetMyTaskAbstractsDeserializer extends AeHtDeserializerBase
{

   /** Request info */
   private AeGetTasksParam mGetTasksParam;
   
   /**
    * Ctor.
    * @param aElement ws-ht api:getMyTaskAbstracts element
    */
   public AeGetMyTaskAbstractsDeserializer(Element aElement)
   {
      setElement(aElement);
   }
   
   /**
    * @return the getTasksParam
    */
   public AeGetTasksParam getGetTasksParam() throws Exception
   {
      if (mGetTasksParam == null)
      {
         mGetTasksParam = deserialize();
      }
      return mGetTasksParam;
   }
   
   /** 
    * Creates a AeGetTasksParam from ht api:getMyTaskAbstracts element 
    * @throws Exception
    */
   protected AeGetTasksParam deserialize() throws Exception
   {
      AeGetTasksParam param = new AeGetTasksParam();
      String value = getText( getElement(), "htdt:taskType"); //$NON-NLS-1$
      param.setTaskType(value);
      value = getText( getElement(), "htdt:genericHumanRole"); //$NON-NLS-1$
      param.setGenericHumanRole(value);
      value = getText( getElement(), "htdt:workQueue"); //$NON-NLS-1$      
      param.setWorkQueue(value);
      List statusList = getTextList( getElement(), "htdt:status"); //$NON-NLS-1$
      Iterator it = statusList.iterator();
      while (it.hasNext())
      {
         param.addStatus( (String) it.next() );
      }
      value = getText( getElement(), "htdt:whereClause"); //$NON-NLS-1$
      param.setWhereClause(value);
      AeSchemaDateTime dt = getDateTime( getElement(), "htdt:createdOnClause"); //$NON-NLS-1$
      param.setCreateOnClause(dt);
      // max tasks
      value = getText( getElement(), "htdt:maxTasks"); //$NON-NLS-1$
      if (AeUtil.notNullOrEmpty(value))
      {      
         param.setMaxTasks( AeUtil.parseInt(value, 0));
      }
      return param;
   }

}
