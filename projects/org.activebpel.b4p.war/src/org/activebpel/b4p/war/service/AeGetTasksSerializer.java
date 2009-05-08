//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/AeGetTasksSerializer.java,v 1.2 2008/03/19 19:29:17 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.service;

import java.util.Collection;
import java.util.Iterator;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.api.AeGetTasksParam;
import org.activebpel.rt.ht.api.io.AeGetMyTasksSerializer;
import org.activebpel.rt.util.AeUtil;
import org.w3c.dom.Element;

/**
 * Serializes the task listing request into a tsst:getTasks element.
 * This is the custom AE task listing request which supports paging
 * and column sorting.
 */
public class AeGetTasksSerializer extends AeGetMyTasksSerializer
{
   public static final String TASK_STATE_WSDL_NS = "http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-state-wsdl.xsd"; //$NON-NLS-1$
   public static final String TASK_STATE_WSDL_PREFIX = "tsst"; //$NON-NLS-1$

   /**
    * Ctor
    * @param aGetTasksParam
    */
   public AeGetTasksSerializer(AeGetTasksParam aGetTasksParam)
   {
      super(aGetTasksParam);
   }

   /**
    * Overrides method to return tsst:getTasks element.
    * @see org.activebpel.rt.ht.api.io.AeGetMyTaskAbstractsSerializer#serialize(org.w3c.dom.Element)
    */
   public Element serialize(Element aParentElement) throws AeException
   {
      // create top level tsst:getTasks element
      Element root = createRootElement(aParentElement, TASK_STATE_WSDL_NS, TASK_STATE_WSDL_PREFIX, "getTasks"); //$NON-NLS-1$;
      // add wsht api getMyTasks element
      super.serialize(root);
      // start index
      createElementWithText(root, TASK_STATE_WSDL_NS, TASK_STATE_WSDL_PREFIX, "taskIndexOffset", String.valueOf(getGetTasksParam().getTaskIndexOffset())); //$NON-NLS-1$
      // order by elements (optional)
      Collection orderBys = getGetTasksParam().getOrderBys();
      if (AeUtil.notNullOrEmpty( orderBys ) )
      {
         Element orderByElement = createElementWithText(root, TASK_STATE_WSDL_NS, TASK_STATE_WSDL_PREFIX, "orderBy", null); //$NON-NLS-1$
         for (Iterator iter = orderBys.iterator(); iter.hasNext();)
         {
            String fieldName = (String) iter.next();
            createElementWithText(orderByElement, TASK_STATE_WSDL_NS, TASK_STATE_WSDL_PREFIX, "fieldId", fieldName); //$NON-NLS-1$
         }
      }
      // search by (optional)
      if (AeUtil.notNullOrEmpty( getGetTasksParam().getSearchBy() ))
      {
         createElementWithText(root, TASK_STATE_WSDL_NS, TASK_STATE_WSDL_PREFIX, "searchBy", AeUtil.getSafeString( getGetTasksParam().getSearchBy() )); //$NON-NLS-1$
      }
      return root;
   }

}
