//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/ht/AePeopleActivityBasedHtFunctionContext.java,v 1.2.4.2 2008/04/14 21:25:30 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function.ht;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.impl.task.data.AePLBaseRequest;
import org.activebpel.rt.ht.IAeHtFunctionContext;
import org.activebpel.rt.ht.IAeWSHTConstants;
import org.activebpel.rt.ht.def.AeAbstractGenericHumanRoleDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.w3c.dom.Element;

/**
 * Provides context for all of the ht task based functions that execute during
 * the preprocessing of a peopleActivity. The optional taskName attribute used
 * below is always ignored since there is no parent task context in this scenario. 
 */
public class AePeopleActivityBasedHtFunctionContext implements IAeHtFunctionContext
{
   private AePLBaseRequest mTaskRequest;

   private static final QName ORGANIZATIONAL_ENTITY = new QName(IAeWSHTConstants.WSHT_NAMESPACE, "organizationalEntity"); //$NON-NLS-1$
   private static final QName USER = new QName(IAeWSHTConstants.WSHT_NAMESPACE, "user"); //$NON-NLS-1$

   /**
    * C'tor that accepts AeProcessTaskRequest
    * @param aTaskRequest
    */
   public AePeopleActivityBasedHtFunctionContext(AePLBaseRequest aTaskRequest)
   {
      mTaskRequest = aTaskRequest;
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getActualOwner(java.lang.String)
    */
   public Element getActualOwner(String aTaskName)
   {
      return null;
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getBusinessAdministrators(java.lang.String)
    */
   public Element getBusinessAdministrators(String aTaskName)
   {
      return getExtensionElement(getPeopleAssignments().getBusinessAdministrators(), ORGANIZATIONAL_ENTITY);
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getExcludedOwners(java.lang.String)
    */
   public Element getExcludedOwners(String aTaskName)
   {
      return getExtensionElement(getPeopleAssignments().getExcludedOwners(), ORGANIZATIONAL_ENTITY);
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getInput(java.lang.String, java.lang.String)
    */
   public Object getInput(String aPartName, String aTaskName)
   {
      return getTaskRequest().getInitialState().getInput().getData(aPartName);
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getPotentialOwners(java.lang.String)
    */
   public Element getPotentialOwners(String aTaskName)
   {
      return getExtensionElement(getPeopleAssignments().getPotentialOwners(), ORGANIZATIONAL_ENTITY);
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getTaskInitiator(java.lang.String)
    */
   public Element getTaskInitiator(String aTaskName)
   {
      return getExtensionElement(getPeopleAssignments().getTaskInitiator(), USER);
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getTaskPriority(java.lang.String)
    */
   public int getTaskPriority(String aTaskName)
   {
      return getTaskRequest().getInitialState().getHumanTaskContext().getPriority();
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getTaskStakeholders(java.lang.String)
    */
   public Element getTaskStakeholders(String aTaskName)
   {
      return getExtensionElement(getPeopleAssignments().getTaskStakeholders(), ORGANIZATIONAL_ENTITY);
   }

   /**
    * Returns extension element for aElementName
    * @param aDef
    * @param aElementName
    * @return
    */
   private Element getExtensionElement(AeAbstractGenericHumanRoleDef aDef, QName aElementName)
   {
      AeExtensionElementDef def = aDef.getExtensionElementDef(aElementName);
      return def.getExtensionElement();
   }
   
   /**
    * Returns people assignment def in task request
    * @return
    */
   private AePeopleAssignmentsDef getPeopleAssignments()
   {
      return getTaskRequest().getInitialState().getHumanTaskContext().getPeopleAssignments();
   }

   /**
    * @return the taskRequest
    */
   protected AePLBaseRequest getTaskRequest()
   {
      return mTaskRequest;
   }

   /**
    * @param aTaskRequest the taskRequest to set
    */
   protected void setTaskRequest(AePLBaseRequest aTaskRequest)
   {
      mTaskRequest = aTaskRequest;
   }
}
