//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeGetMyTasksResponseSerializer.java,v 1.2 2008/02/09 00:03:17 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api.io;

import org.activebpel.rt.ht.api.AeHtApiTask;
import org.activebpel.rt.ht.api.IAeHtApiTaskList;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * Serializes list of wsht api tasks into a wsht api:getMyTasksResponse element.
 */
public class AeGetMyTasksResponseSerializer extends AeGetMyTaskAbstractsResponseSerializer
{
   /**
    * ctor.
    * @param aTaskList
    */
   public AeGetMyTasksResponseSerializer(IAeHtApiTaskList aTaskList)
   {
      super(aTaskList);
   }

   /**
    * Overrides method to create a getMyTasksResponse element.
    * @see org.activebpel.rt.ht.api.io.AeGetMyTaskAbstractsResponseSerializer#createResponseRootElement(org.w3c.dom.Element)
    */
   protected Element createResponseRootElement(Element aParentElement)
   {
      Element responseRootEle = createRootElement(aParentElement, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "getMyTasksResponse"); //$NON-NLS-1$
      return responseRootEle;
   }

   /**
    * Overrides method to create content for type api:tTask.
    * @see org.activebpel.rt.ht.api.io.AeGetMyTaskAbstractsResponseSerializer#serializeTaskContent(org.w3c.dom.Element, org.activebpel.rt.ht.api.AeHtApiTask)
    */
   protected void serializeTaskContent(Element aTaskElement, AeHtApiTask aTask)
   {
      // serialize content as per wsht api tTask schema type (note: its tTask and not tTaskAbstract).
      addTaskChildElementText(aTaskElement,"id", aTask.getId() ); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"taskType", aTask.getTaskType() ); //$NON-NLS-1$
      AeXmlUtil.addElementNSQName(aTaskElement, WSHT_API_NAMESPACE, WSHT_API_PREFIX, "name", aTask.getName(), false); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"status", aTask.getStatus() ); //$NON-NLS-1$
      if (aTask.getPriority() >= 0)
      {
         addTaskChildElementText(aTaskElement,"priority", String.valueOf( aTask.getPriority() ) ); //$NON-NLS-1$
      }

      // taskInitiator
      if (aTask.getTaskInitiator() != null)
      {
         addTaskChildElementText(aTaskElement,"taskInitiator", aTask.getTaskInitiator().getValue() ); //$NON-NLS-1$
      }
      // taskStakeholders
      serializeOrganizationalEntity(aTaskElement, aTask.getTaskStakeholders(),"taskStakeholders", WSHT_API_NAMESPACE, WSHT_API_PREFIX); //$NON-NLS-1$
      // potentialOwners
      serializeOrganizationalEntity(aTaskElement, aTask.getPotentialOwners(),"potentialOwners", WSHT_API_NAMESPACE, WSHT_API_PREFIX); //$NON-NLS-1$
      // businessAdministrators
      serializeOrganizationalEntity(aTaskElement, aTask.getBusinessAdministrators(),"businessAdministrators", WSHT_API_NAMESPACE, WSHT_API_PREFIX); //$NON-NLS-1$
      // actualOwner
      if (aTask.getActualOwner() != null)
      {
         addTaskChildElementText(aTaskElement,"actualOwner", aTask.getActualOwner().getValue() ); //$NON-NLS-1$
      }
      // notificationRecipients
      serializeOrganizationalEntity(aTaskElement, aTask.getNotificationRecipients(),"notificationRecipients", WSHT_API_NAMESPACE, WSHT_API_PREFIX); //$NON-NLS-1$

      // note: createdOn elem is required. All other datetime elems are optional.
      addTaskChildElementDate(aTaskElement,"createdOn", aTask.getCreatedOn() ); //$NON-NLS-1$
      addTaskChildElementText(aTaskElement,"createdBy", aTask.getCreatedBy() ); //$NON-NLS-1$
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
      addTaskChildElementText(aTaskElement,"primarySearchBy", aTask.getPrimarySearchBy() ); //$NON-NLS-1$
      // extensible elems can go here.

   }
}
