//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/AePeopleActivityStateImpl.java,v 1.9 2008/03/11 03:10:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.IAeProcessTaskConstants;
import org.activebpel.rt.b4p.def.io.AeB4PIO;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.activity.AeAbstractExtensionActivityImpl;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleAdapter;
import org.activebpel.rt.ht.IAeWSHTConstants;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class contains task response metadata
 */
public class AePeopleActivityStateImpl extends AeAbstractExtensionActivityImpl
{
   /** Actual Owner and other task generic human roles from task response metadata */
   private String mActualOwner;
   private Element mPotentialOwners;
   private Element mBusinessAdmins;
   private String mTaskInitiator;
   private Element mTaskStakeHolder;
   private int mPriority;

   /** map of ns prefixes to ns values */
   protected static final Map sNSMap = new HashMap();
   static
   {
      sNSMap.put("trt", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
      sNSMap.put("tlc", IAeProcessTaskConstants.TASK_LIFECYCLE_NS); //$NON-NLS-1$
      sNSMap.put("tpa", IAeProcessTaskConstants.TASK_LC_PROCESS_NAMESPACE); //$NON-NLS-1$
      sNSMap.put("htd", IAeWSHTConstants.WSHT_NAMESPACE); //$NON-NLS-1$
      sNSMap.put(IAeB4PConstants.B4P_PREFIX, IAeB4PConstants.B4P_NAMESPACE); 
      sNSMap.put("api", IAeProcessTaskConstants.WSHT_API_NS); //$NON-NLS-1$
   };
   
   /**
    * saves task response metadata into instance variables
    * @param aTaskResponse
    * @throws AeBusinessProcessException 
    */
   protected void setStateFromResponse(Element aTaskResponse)
   {
      // Extract priority
      Element priorityElem = (Element) AeXPathUtil.selectSingleNodeIgnoreException(aTaskResponse, "tlc:metadata/trt:priority", sNSMap); //$NON-NLS-1$
      String priority = AeXmlUtil.getText(priorityElem);
      if (priority != null)
      {
         setPriority(new Integer(priority).intValue());
      }
      // Extract Potential Owners
      setPotentialOwners(createOrganizationEntity(aTaskResponse, "tlc:metadata/trt:potentialOwners")); //$NON-NLS-1$
      // Extract Task Stake holders
      setTaskStakeHolder(createOrganizationEntity(aTaskResponse, "tlc:metadata/trt:taskStakeholders")); //$NON-NLS-1$
      // Extract Business Admins 
      setBusinessAdmins(createOrganizationEntity(aTaskResponse, "tlc:metadata/trt:businessAdministrators")); //$NON-NLS-1$
      // Extract actual owner
      setActualOwner(AeXmlUtil.getText((Element) AeXPathUtil.selectSingleNodeIgnoreException(aTaskResponse, "tlc:metadata/trt:actualOwner", sNSMap))); //$NON-NLS-1$
      // Extract task initiator
      setTaskInitiator(AeXmlUtil.getText((Element) AeXPathUtil.selectSingleNodeIgnoreException(aTaskResponse, "tlc:metadata/trt:taskInitiator", sNSMap))); //$NON-NLS-1$

   }
   
   /**
    * Creates and returns organizational entity  
    * @param aTaskResponse
    * @param aPath
    */
   private Element createOrganizationEntity(Element aTaskResponse, String aPath)
   {
      Element orgEntityElement = null;
      Element elem = (Element) AeXPathUtil.selectSingleNodeIgnoreException(aTaskResponse, aPath, sNSMap);
      if (elem != null)
      {
         try
         {
            AeOrganizationalEntityDef orgEntityDef = AeB4PIO.deserializeAsOrganizationalEntity(elem);
            if (!orgEntityDef.isEmpty())
            {
               orgEntityElement = AeB4PIO.serialize2Element(orgEntityDef); 
            }
         }
         catch (AeException e)
         {
            // invalid org entities are ignored
         }
      }
      return orgEntityElement;
   }
   /**
    * @return the potetialOwners
    */
   public Element getPotentialOwners()
   {
      return mPotentialOwners;
   }
   /**
    * @param aPotentialOwners the potetialOwners to set
    */
   protected void setPotentialOwners(Element aPotentialOwners)
   {
      mPotentialOwners = aPotentialOwners;
   }
   /**
    * @return the businessAdmins
    */
   public Element getBusinessAdmins()
   {
      return mBusinessAdmins;
   }
   /**
    * @param aBusinessAdmins the businessAdmins to set
    */
   protected void setBusinessAdmins(Element aBusinessAdmins)
   {
      mBusinessAdmins = aBusinessAdmins;
   }
   /**
    * @return the taskStakeHolder
    */
   public Element getTaskStakeHolders()
   {
      return mTaskStakeHolder;
   }
   /**
    * @param aTaskStakeHolder the taskStakeHolder to set
    */
   protected void setTaskStakeHolder(Element aTaskStakeHolder)
   {
      mTaskStakeHolder = aTaskStakeHolder;
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.AeAbstractExtensionActivityImpl#restore(org.w3c.dom.Element)
    */
   public void restore(Element aElement)
   {
      Element elem = AeXmlUtil.findSubElement(aElement, new QName(IAeActivityLifeCycleAdapter.EXTENSION_STATE_NAMESPACE, IAeActivityLifeCycleAdapter.EXTENSION_STATE_ELEMENT));
      // restore priority
     Element priority = (Element) AeXPathUtil.selectSingleNodeIgnoreException(elem, "trt:taskPriority", sNSMap); //$NON-NLS-1$
      if (priority != null)
         setPriority(Integer.parseInt(AeXmlUtil.getText(priority)));

      // restore actual owner
      Element actualOwner = (Element) AeXPathUtil.selectSingleNodeIgnoreException(elem, "trt:actualOwner", sNSMap); //$NON-NLS-1$
      if (actualOwner != null)
         setActualOwner(AeXmlUtil.getText(actualOwner));

      // restore task initiator
      Element taskInitiator = (Element) AeXPathUtil.selectSingleNodeIgnoreException(elem, "trt:taskInitiator", sNSMap); //$NON-NLS-1$
      if (taskInitiator != null)
         setTaskInitiator(AeXmlUtil.getText(taskInitiator));
      
      // restore business admins
      setBusinessAdmins((Element) AeXPathUtil.selectSingleNodeIgnoreException(elem, "trt:businessAdministrators/htd:organizationalEntity", sNSMap)); //$NON-NLS-1$
      // restore potential owners
      setPotentialOwners((Element) AeXPathUtil.selectSingleNodeIgnoreException(elem, "trt:potentialOwners/htd:organizationalEntity", sNSMap)); //$NON-NLS-1$
      // restore task stakeholders
      setTaskStakeHolder((Element) AeXPathUtil.selectSingleNodeIgnoreException(elem, "trt:taskStakeholders/htd:organizationalEntity", sNSMap)); //$NON-NLS-1$
   }
   /**
    * @see org.activebpel.rt.bpel.impl.activity.AeAbstractExtensionActivityImpl#save(org.w3c.dom.Element)
    */
   public void save(Element aElement)
   {
      // add actual owner
      if (getActualOwner() != null)
         AeXmlUtil.addElementNS(aElement, IAeB4PConstants.AEB4P_NAMESPACE, "trt:actualOwner", getActualOwner()); //$NON-NLS-1$
      // add task initiator
      if (getTaskInitiator() != null)
         AeXmlUtil.addElementNS(aElement, IAeB4PConstants.AEB4P_NAMESPACE, "trt:taskInitiator", getTaskInitiator()); //$NON-NLS-1$
      // add business admins
      if (getBusinessAdmins() != null)
      {
         Node node = aElement.getOwnerDocument().importNode(getBusinessAdmins(), true);
         AeXmlUtil.addElementNS(aElement, IAeB4PConstants.AEB4P_NAMESPACE, "trt:businessAdministrators").appendChild(node); //$NON-NLS-1$
      }
      // add potential owners
      if (getPotentialOwners() != null)
      {
         Node node = aElement.getOwnerDocument().importNode(getPotentialOwners(), true);
         AeXmlUtil.addElementNS(aElement, IAeB4PConstants.AEB4P_NAMESPACE, "trt:potentialOwners").appendChild(node); //$NON-NLS-1$
      }
      // add task stakeholders
      if (getTaskStakeHolders() != null)
      {
         Node node = aElement.getOwnerDocument().importNode(getTaskStakeHolders(), true);
         AeXmlUtil.addElementNS(aElement, IAeB4PConstants.AEB4P_NAMESPACE, "trt:taskStakeholders").appendChild(node); //$NON-NLS-1$
      }
      // add task priority
      AeXmlUtil.addElementNS(aElement, IAeB4PConstants.AEB4P_NAMESPACE, "trt:taskPriority", String.valueOf(getPriority())); //$NON-NLS-1$
   }
   
   /**
    * @return the actualOwner
    */
   public String getActualOwner()
   {
      return mActualOwner;
   }
   /**
    * @param aActualOwner the actualOwner to set
    */
   protected void setActualOwner(String aActualOwner)
   {
      mActualOwner = aActualOwner;
   }
   /**
    * @return the taskInitiator
    */
   public String getTaskInitiator()
   {
      return mTaskInitiator;
   }
   /**
    * @param aTaskInitiator the taskInitiator to set
    */
   protected void setTaskInitiator(String aTaskInitiator)
   {
      mTaskInitiator = aTaskInitiator;
   }

   /**
    * @return the priority
    */
   public int getPriority()
   {
      return mPriority;
   }
   /**
    * @param aPriority the priority to set
    */
   protected void setPriority(int aPriority)
   {
      mPriority = aPriority;
   }
}
