//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeGetMyTaskAbstractsResponseSerializer.java,v 1.3 2008/02/17 21:51:26 mford Exp $
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
import org.activebpel.rt.ht.api.AeHtApiTask;
import org.activebpel.rt.ht.api.IAeHtApiTaskList;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;
import org.w3c.dom.Element;

/**
 * Serializes list of wsht api tasks into a wsht api:getMyTaskAbstractsResponse element.
 */
public class AeGetMyTaskAbstractsResponseSerializer extends AeHtSerializerBase
{
   /** List of tasks.*/
   private IAeHtApiTaskList mTaskList;

   /**
    * Ctor.
    * @param aTaskList
    */
   public AeGetMyTaskAbstractsResponseSerializer(IAeHtApiTaskList aTaskList)
   {
      mTaskList = aTaskList;
   }

   /**
    * Returns task list.
    */
   protected IAeHtApiTaskList getTaskList()
   {
      return mTaskList;
   }

   /**
    * Convenience method to add WSHT_API_NAMESPACE namespace optional element with text.
    * The element is not created if the given text is null.
    * @param aTaskElement
    * @param aLocalName
    * @param aText
    */
   protected void addTaskChildElementText(Element aTaskElement, String aLocalName, String aText)
   {
      // add elem iff text is given.
      if (aText != null)
      {
         createElementWithText(aTaskElement, WSHT_API_NAMESPACE, WSHT_API_PREFIX, aLocalName, aText );
      }
   }

   /**
    * Convenience method to add WSHT_API_NAMESPACE namespace element for an optional schema date time.
    * The date time element is not created if the date time is null.
    * @param aTaskElement
    * @param aLocalName
    * @param aDateTime
    */
   protected void addTaskChildElementDate(Element aTaskElement, String aLocalName, AeSchemaDateTime aDateTime)
   {
      if (aDateTime != null)
      {
         addTaskChildElementText(aTaskElement, aLocalName, aDateTime.toString() );
      }
   }

   /**
    * Creates and returns the root response element.
    * @param aParentElement
    * @return root element e.g. wsht api:getMyTaskAbstractsResponse
    */
   protected Element createResponseRootElement(Element aParentElement)
   {
      Element responseRootEle = createRootElement(aParentElement, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "getMyTaskAbstractsResponse"); //$NON-NLS-1$
      return responseRootEle;
   }


   /**
    * Serializes the <code>AeHtApiTaskList</code> member value into a wsht api:getMyTaskAbstractsResponse
    * element.
    * @see org.activebpel.rt.ht.api.io.AeHtSerializerBase#serialize(org.w3c.dom.Element)
    */
   public Element serialize(Element aParentElement) throws AeException
   {
      Element responseRootEle = createResponseRootElement(aParentElement);
      serializeTasks(responseRootEle);
      return responseRootEle;
   }

   /**
    * Serialize the list of tasks.
    * @param aResponseRootEle
    */
   protected void serializeTasks(Element aResponseRootEle)
   {
      Iterator it = getTaskList().getTasks().iterator();
      while (it.hasNext())
      {
         AeHtApiTask task = (AeHtApiTask) it.next();
         Element taskElement = createElement(aResponseRootEle, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "taskAbstract"); //$NON-NLS-1$
         serializeTaskContent(taskElement, task);
      }
   }

   /**
    * Serialize wshtp api:taskAbstract child element i.e a api:tTaskAbstract type element.
    * @param aTaskElement the api:tTaskAbstract element.
    * @param aTask
    */
   protected void serializeTaskContent(Element aTaskElement, AeHtApiTask aTask)
   {
      // serialize content as per wsht api tTaskAbstract schema type.
      addTaskChildElementText(aTaskElement,"id", aTask.getId() ); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"taskType", aTask.getTaskType() ); //$NON-NLS-1$
      AeXmlUtil.addElementNSQName(aTaskElement, WSHT_API_NAMESPACE, WSHT_API_PREFIX, "name", aTask.getName(), false); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"status", aTask.getStatus() ); //$NON-NLS-1$
      if (aTask.getPriority() >= 0)
      {
         addTaskChildElementText(aTaskElement,"priority", String.valueOf( aTask.getPriority() ) ); //$NON-NLS-1$
      }
      // note: createdOn elem is required. All other datetime elems are optional.
      addTaskChildElementDate(aTaskElement,"createdOn", aTask.getCreatedOn() ); //$NON-NLS-1$
      addTaskChildElementDate(aTaskElement,"activationTime", aTask.getActivationTime() ); //$NON-NLS-1$
      addTaskChildElementDate(aTaskElement,"expirationTime", aTask.getExpirationTime() ); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"isSkipable", String.valueOf( aTask.isSkipable()) ); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"hasPotentialOwners", String.valueOf( aTask.isHasPotentialOwners()) ); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"startByExists", String.valueOf( aTask.isStartByExists()) ); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"completeByExists", String.valueOf( aTask.isCompleteByExists()) ); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"presentationName", aTask.getPresentationName() ); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"presentationSubject", aTask.getPresentationSubject() ); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"renderingMethodExists", String.valueOf( aTask.isRenderingMethodExists()) ); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"hasOutput", String.valueOf( aTask.isHasOutput()) ); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"hasFault", String.valueOf( aTask.isHasFault()) ); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"hasAttachments", String.valueOf( aTask.isHasAttachments()) ); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"hasComments", String.valueOf( aTask.isHasComments()) ); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"escalated", String.valueOf( aTask.isEscalated()) ); //$NON-NLS-1$

      // extensible elems can go here.
   }

}
