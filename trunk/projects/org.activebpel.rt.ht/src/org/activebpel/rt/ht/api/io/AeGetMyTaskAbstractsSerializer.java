//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeGetMyTaskAbstractsSerializer.java,v 1.2 2008/02/15 01:49:40 PJayanetti Exp $
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

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.api.AeGetTasksParam;
import org.activebpel.rt.util.AeUtil;
import org.w3c.dom.Element;

/**
 * Serializes a AeGetTasksParam into ht api:getMyTaskAbstracts element.
 */
public class AeGetMyTaskAbstractsSerializer extends AeHtSerializerBase
{
   /** Request info */
   private AeGetTasksParam mGetTasksParam;

   /**
    * Ctor.
    * @param aGetTasksParam
    */
   public AeGetMyTaskAbstractsSerializer(AeGetTasksParam aGetTasksParam)
   {
      mGetTasksParam = aGetTasksParam;
   }

   /**
    * @return the getTasksParam
    */
   protected AeGetTasksParam getGetTasksParam()
   {
      return mGetTasksParam;
   }

   /**
    * Creates the ht api getMyTaskAbstracts element.
    * @param aParentElement
    * @return getMyTaskAbstracts element
    */
   protected Element createRequestElement(Element aParentElement)
   {
      return createRootElement(aParentElement, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "getMyTaskAbstracts"); //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.ht.api.io.AeHtSerializerBase#serialize(org.w3c.dom.Element)
    */
   public Element serialize(Element aParentElement) throws AeException
   {
      Element root = createRequestElement(aParentElement);
      // task type
      createElementWithText(root, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "taskType", getGetTasksParam().getTaskType()); //$NON-NLS-1$
      // generic human role
      if (AeUtil.notNullOrEmpty( getGetTasksParam().getGenericHumanRole() ))
      {
         createElementWithText(root, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "genericHumanRole", getGetTasksParam().getGenericHumanRole()); //$NON-NLS-1$
      }
      // work-queue
      if (AeUtil.notNullOrEmpty( getGetTasksParam().getWorkQueue() ))
      {
         createElementWithText(root, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "workQueue", getGetTasksParam().getWorkQueue()); //$NON-NLS-1$
      }
      // status (list)
      Iterator statusIter = getGetTasksParam().getStatusSet().iterator();
      while ( statusIter.hasNext() )
      {
         createElementWithText(root, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "status", (String) statusIter.next() ); //$NON-NLS-1$
      }
      // whereClause
      if (AeUtil.notNullOrEmpty( getGetTasksParam().getWhereClause() ))
      {
         createElementWithText(root, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "whereClause", getGetTasksParam().getWhereClause()); //$NON-NLS-1$
      }
      // createdOnClause
      if (AeUtil.notNullOrEmpty( getGetTasksParam().getCreateOnClauseAsString() ))
      {
         createElementWithText(root, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "createdOnClause", getGetTasksParam().getCreateOnClauseAsString()); //$NON-NLS-1$
      }
      // maxTasks
      if (getGetTasksParam().getMaxTasks() > 0)
      {
         createElementWithText(root, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "maxTasks", String.valueOf( getGetTasksParam().getMaxTasks() )); //$NON-NLS-1$
      }
      return root;
   }

}
