// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/engine/AeTaskResponseData.java,v 1.4 2008/03/03 20:23:15 mford Exp $
// ///////////////////////////////////////////////////////////////////////////
// PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc. Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
// ///////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.impl.engine;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.IAeProcessTaskConstants;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.io.AeHtIO;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Represents a simulated task response.
 */
public class AeTaskResponseData
{
   private static Map sNamespaceMap = new HashMap();
   static
   {
      sNamespaceMap.put("tlc", IAeProcessTaskConstants.TASK_LIFECYCLE_NS); //$NON-NLS-1$
      sNamespaceMap.put("trt", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
   }

   /** The location path of the people activity. */
   private String mLocationPath;
   /** The task status. */
   private String mStatus;
   /** The task response message data. */
   private IAeMessageData mMessageData;
   /** The fault String. */
   private String mFaultName;
   /** The task priority. */
   private int mPriority;
   /** The task priority. */
   private String mTaskInitiator;
   /** The task stakeholders. */
   private AeOrganizationalEntityDef mTaskStakeholders;
   /** The task potential owners. */
   private AeOrganizationalEntityDef mPotentialOwners;
   /** The task excluded owners. */
   private AeOrganizationalEntityDef mExcludedOwners;
   /** The task business admins. */
   private AeOrganizationalEntityDef mBusinessAdministrators;
   /** The notification recipients. */
   private AeOrganizationalEntityDef mNotificationRecipients;
   /** The task owner. */
   private String mActualOwner;
   
   /**
    * C'tor.
    *
    * @param aLocationPath
    * @param aStatus
    */
   public AeTaskResponseData(String aLocationPath, String aStatus)
   {
      setLocationPath(aLocationPath);
      setStatus(aStatus);
   }
   
   /**
    * C'tor.
    *
    * @param aLocationPath
    * @param aStatus
    * @param aMessageData
    * @param aFaultName
    */
   public AeTaskResponseData(String aLocationPath, String aStatus, IAeMessageData aMessageData,
         String aFaultName)
   {
      this(aLocationPath, aStatus);
      setMessageData(aMessageData);
      setFaultName(aFaultName);
   }

   /**
    * @return Returns the status.
    */
   public String getStatus()
   {
      return mStatus;
   }

   /**
    * @param aStatus the status to set
    */
   protected void setStatus(String aStatus)
   {
      mStatus = aStatus;
   }

   /**
    * @return Returns the messageData.
    */
   public IAeMessageData getMessageData()
   {
      return mMessageData;
   }

   /**
    * @param aMessageData the messageData to set
    */
   public void setMessageData(IAeMessageData aMessageData)
   {
      mMessageData = aMessageData;
   }

   /**
    * @return Returns the faultName.
    */
   public String getFaultName()
   {
      return mFaultName;
   }

   /**
    * @param aFaultName the faultName to set
    */
   public void setFaultName(String aFaultName)
   {
      mFaultName = aFaultName;
   }

   /**
    * @return Returns the locationPath.
    */
   public String getLocationPath()
   {
      return mLocationPath;
   }

   /**
    * @param aLocationPath the locationPath to set
    */
   protected void setLocationPath(String aLocationPath)
   {
      mLocationPath = aLocationPath;
   }

   /**
    * Serialize the task response to a Document.
    */
   public Document serialize()
   {
      try
      {
         InputStream iStream = getClass().getResourceAsStream("processTaskResponse.xml"); //$NON-NLS-1$
         Document doc = new AeXMLParserBase(true, false).loadDocument(iStream, null);

         // Set the status in the result.
         Element statusElem = (Element) AeXPathUtil.selectSingleNode(doc, "/tlc:processTaskResponse/tlc:metadata/trt:status", sNamespaceMap); //$NON-NLS-1$
         AeXmlUtil.addText(statusElem, getStatus());

         // Set the createdOn in the result.
         Element createdOnElem = (Element) AeXPathUtil.selectSingleNode(doc, "/tlc:processTaskResponse/tlc:metadata/trt:createdOn", sNamespaceMap); //$NON-NLS-1$
         AeXmlUtil.addText(createdOnElem, new AeSchemaDateTime(new Date()).toString());

         // Output when not a fault
         if (getFaultName() == null && getMessageData() != null)
         {
            serializeOutputMessage(doc);
         }
         
         // Fault name
         if (getFaultName() != null)
         {
            serializeFault(doc);
         }

         serializeContextData(doc);
         
         return doc;
      }
      catch (AeException ex)
      {
         AeException.logError(ex);
         return null;
      }
   }

   /**
    * Serialize the fault information to the document.
    * 
    * @param aDocument
    * @throws AeException
    */
   protected void serializeFault(Document aDocument) throws AeException
   {
      Element faultElem = (Element) AeXPathUtil.selectSingleNode(aDocument, "/tlc:processTaskResponse/trt:fault", sNamespaceMap); //$NON-NLS-1$
      faultElem.setAttribute("name", getFaultName()); //$NON-NLS-1$
      
      if (getMessageData() != null)
      {
         // note: we only support single-part element style faults, but
         // we'll just go through all of the parts assuming there is at
         // most one.
         for (Iterator iter = getMessageData().getPartNames(); iter.hasNext(); )
         {
            String partName = (String) iter.next();
            Object partData = getMessageData().getData(partName);
            // Only support single-part element style faults
            if (partData instanceof Node)
            {
               Node node = (Node) partData;
               if (node instanceof Document)
                  node = ((Document) node).getDocumentElement();
               Node importedNode = aDocument.importNode(node, true);
               faultElem.appendChild(importedNode);
            }
         }
      }
   }

   /**
    * Serialize the output message to the document.
    * 
    * @param aDocument
    * @throws AeException
    */
   protected void serializeOutputMessage(Document aDocument) throws AeException
   {
      Element outputElem = (Element) AeXPathUtil.selectSingleNode(aDocument, "/tlc:processTaskResponse/trt:output", sNamespaceMap); //$NON-NLS-1$
  
      for (Iterator iter = getMessageData().getPartNames(); iter.hasNext(); )
      {
         String partName = (String) iter.next();
         Element partElem = aDocument.createElementNS(IAeB4PConstants.AEB4P_NAMESPACE, "trt:part"); //$NON-NLS-1$
         partElem.setAttribute("name", partName); //$NON-NLS-1$
         outputElem.appendChild(partElem);
         
         Object partData = getMessageData().getData(partName);
         if (partData instanceof Node)
         {
            Node node = (Node) partData;
            if (node instanceof Document)
               node = ((Document) node).getDocumentElement();
            Node importedNode = aDocument.importNode(node, true);
            partElem.appendChild(importedNode);
         }
         else
         {
            AeXmlUtil.addText(partElem, String.valueOf(partData));
         }
      }
   }
   
   /**
    * Serializes context metadata to the document
    * 
    * @param aDocument
    * @throws AeException
    */
   protected void serializeContextData(Document aDocument) throws AeException
   {
      // Set the priority in the result.
      Element elem = (Element) AeXPathUtil.selectSingleNode(aDocument, "/tlc:processTaskResponse/tlc:metadata/trt:priority", sNamespaceMap); //$NON-NLS-1$
      if (getPriority() > 0)
         AeXmlUtil.addText(elem, String.valueOf(getPriority()));

      // Set the task initiator
      if (getTaskInitiator() != null)
      {
         elem = (Element) AeXPathUtil.selectSingleNode(aDocument, "/tlc:processTaskResponse/tlc:metadata/trt:taskInitiator", sNamespaceMap); //$NON-NLS-1$
         AeXmlUtil.addText(elem, getTaskInitiator());
      }

      // Set the task stakeholders
      elem = (Element) AeXPathUtil.selectSingleNode(aDocument, "/tlc:processTaskResponse/tlc:metadata/trt:taskStakeholders", sNamespaceMap); //$NON-NLS-1$
      serializeOrganizationalEntity(elem, getTaskStakeholders());

      // Set the potential owners
      elem = (Element) AeXPathUtil.selectSingleNode(aDocument, "/tlc:processTaskResponse/tlc:metadata/trt:potentialOwners", sNamespaceMap); //$NON-NLS-1$
      serializeOrganizationalEntity(elem, getPotentialOwners());

      // Set the excluded owners
      elem = (Element) AeXPathUtil.selectSingleNode(aDocument, "/tlc:processTaskResponse/tlc:metadata/trt:excludedOwners", sNamespaceMap); //$NON-NLS-1$
      serializeOrganizationalEntity(elem, getExcludedOwners());

      // Set the business admins
      elem = (Element) AeXPathUtil.selectSingleNode(aDocument, "/tlc:processTaskResponse/tlc:metadata/trt:businessAdministrators", sNamespaceMap); //$NON-NLS-1$
      serializeOrganizationalEntity(elem, getBusinessAdministrators());
      
      // Set the notify recipients
      elem = (Element) AeXPathUtil.selectSingleNode(aDocument, "/tlc:processTaskResponse/tlc:metadata/trt:notificationRecipients", sNamespaceMap); //$NON-NLS-1$
      serializeOrganizationalEntity(elem, getNotificationRecipients());
      
      // Set the actual owner
      if (getActualOwner() != null)
      {
         elem = (Element) AeXPathUtil.selectSingleNode(aDocument, "/tlc:processTaskResponse/tlc:metadata/trt:actualOwner", sNamespaceMap); //$NON-NLS-1$
         AeXmlUtil.addText(elem, getActualOwner());
      }
      
   }
   
   /**
    * Serializes an organizational entity to the document
    * 
    * @param aTarget
    * @param aDef
    * @throws AeException
    */
   protected void serializeOrganizationalEntity(Element aTarget, AeOrganizationalEntityDef aDef) throws AeException
   {
      if (aDef == null)
         return;
      
      AeOrganizationalEntityDef orgDef = aDef;
      
      Element orgElement = AeHtIO.serialize2Element(orgDef);
      AeXmlUtil.copyNodeContents(orgElement, aTarget);
   }
   
   /**
    * @return the namespaceMap
    */
   public static Map getNamespaceMap()
   {
      return sNamespaceMap;
   }

   /**
    * @param aNamespaceMap the namespaceMap to set
    */
   public static void setNamespaceMap(Map aNamespaceMap)
   {
      sNamespaceMap = aNamespaceMap;
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
   public void setPriority(int aPriority)
   {
      mPriority = aPriority;
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
   public void setTaskInitiator(String aTaskInitiator)
   {
      mTaskInitiator = aTaskInitiator;
   }

   /**
    * @return the taskStakeholders
    */
   public AeOrganizationalEntityDef getTaskStakeholders()
   {
      return mTaskStakeholders;
   }

   /**
    * @param aTaskStakeholders the taskStakeholders to set
    */
   public void setTaskStakeholders(AeOrganizationalEntityDef aTaskStakeholders)
   {
      mTaskStakeholders = aTaskStakeholders;
   }

   /**
    * @return the potentialOwners
    */
   public AeOrganizationalEntityDef getPotentialOwners()
   {
      return mPotentialOwners;
   }

   /**
    * @param aPotentialOwners the potentialOwners to set
    */
   public void setPotentialOwners(AeOrganizationalEntityDef aPotentialOwners)
   {
      mPotentialOwners = aPotentialOwners;
   }

   /**
    * @return the excludedOwners
    */
   public AeOrganizationalEntityDef getExcludedOwners()
   {
      return mExcludedOwners;
   }

   /**
    * @param aExcludedOwners the excludedOwners to set
    */
   public void setExcludedOwners(AeOrganizationalEntityDef aExcludedOwners)
   {
      mExcludedOwners = aExcludedOwners;
   }

   /**
    * @return the businessAdministrators
    */
   public AeOrganizationalEntityDef getBusinessAdministrators()
   {
      return mBusinessAdministrators;
   }

   /**
    * @param aBusinessAdministrators the businessAdministrators to set
    */
   public void setBusinessAdministrators(AeOrganizationalEntityDef aBusinessAdministrators)
   {
      mBusinessAdministrators = aBusinessAdministrators;
   }

   /**
    * @return the notificationRecipients
    */
   public AeOrganizationalEntityDef getNotificationRecipients()
   {
      return mNotificationRecipients;
   }

   /**
    * @param aNotificationRecipients the notificationRecipients to set
    */
   public void setNotificationRecipients(AeOrganizationalEntityDef aNotificationRecipients)
   {
      mNotificationRecipients = aNotificationRecipients;
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
   public void setActualOwner(String aActualOwner)
   {
      mActualOwner = aActualOwner;
   }
}
