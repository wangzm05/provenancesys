//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/AeTrtTaskInstanceDeserializer.java,v 1.6 2008/03/19 19:30:59 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage;

import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.ht.api.AeHtApiTask;
import org.activebpel.rt.ht.api.io.AeHtDeserializerBase;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXPathUtil;
import org.w3c.dom.Element;

/**
 * Simple deserializer which creates a AeHtApiTask from a trt:taskInstance element.
 */
public class AeTrtTaskInstanceDeserializer extends AeHtDeserializerBase
{
   /** static instance of deserializer */
   public static final AeTrtTaskInstanceDeserializer INSTANCE = new AeTrtTaskInstanceDeserializer();
   
   /**
    * @see org.activebpel.rt.xml.AeXMLDeserializerBase#initNamespaceMap(java.util.Map)
    */
   protected void initNamespaceMap(Map aMap)
   {
      super.initNamespaceMap(aMap);
      aMap.put("trt", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
   }

   /**
    * Creates a AeHtApiTask from a trt:taskInstance element
    * @param aProcessId
    * @param aTaskInstanceElement
    * @throws AeException
    */
   public AeHtApiTask deserialize(long aProcessId, Element aTaskInstanceElement) throws AeException
   {
      // create ws-ht api:tTask data
      AeHtApiTask task = new AeHtApiTask();
      task.setProcessId(aProcessId);
      task.setId( getText(aTaskInstanceElement, "trt:identifier") ); //$NON-NLS-1$
      task.setTaskType( getText(aTaskInstanceElement, "trt:taskType") ); //$NON-NLS-1$
      String name =  getText(aTaskInstanceElement, "trt:name") ; //$NON-NLS-1$
      String ns =  getText(aTaskInstanceElement, "trt:targetNamespace") ; //$NON-NLS-1$
      task.setName( new QName(ns, name) );
      task.setStatus( getText(aTaskInstanceElement, "trt:context/trt:status") ); //$NON-NLS-1$
      task.setPriority(AeUtil.parseInt(getText(aTaskInstanceElement, "trt:context/trt:priority"),-1) ); //$NON-NLS-1$);
      task.setTaskInitiator( getText(aTaskInstanceElement, "trt:context/trt:taskInitiator") ); //$NON-NLS-1$)
      task.setTaskStakeholders( deserializeOrganizationalEntity( selectElement(aTaskInstanceElement, "trt:context/trt:taskStakeholders") ) ); //$NON-NLS-1$
      task.setPotentialOwners( deserializeOrganizationalEntity( selectElement(aTaskInstanceElement, "trt:context/trt:potentialOwners") ) ); //$NON-NLS-1$
      task.setExcludedOwners( deserializeOrganizationalEntity( selectElement(aTaskInstanceElement, "trt:context/trt:excludedOwners") ) ); //$NON-NLS-1$
      task.setBusinessAdministrators( deserializeOrganizationalEntity( selectElement(aTaskInstanceElement, "trt:context/trt:businessAdministrators") ) ); //$NON-NLS-1$
      task.setActualOwner( getText(aTaskInstanceElement, "trt:context/trt:actualOwner") ); //$NON-NLS-1$)
      task.setNotificationRecipients( deserializeOrganizationalEntity( selectElement(aTaskInstanceElement, "trt:context/trt:recipients") ) ); //$NON-NLS-1$
      task.setCreatedOn( getDateTime(aTaskInstanceElement, "trt:context/trt:createdOn") ); //$NON-NLS-1$)
      task.setCreatedBy( getText(aTaskInstanceElement, "trt:context/trt:createdBy") ); //$NON-NLS-1$)
      task.setActivationTime( getDateTime(aTaskInstanceElement, "trt:context/trt:activationTime") ); //$NON-NLS-1$)
      task.setExpirationTime( getDateTime(aTaskInstanceElement, "trt:context/trt:expirationTime") ); //$NON-NLS-1$)
      task.setSkipable(AeXPathUtil.selectBoolean(aTaskInstanceElement,  "trt:context/trt:isSkipable", getNamespaceMap())); //$NON-NLS-1$
      task.setStartBy( getDateTime(aTaskInstanceElement, "trt:context/trt:startByMillis") ); //$NON-NLS-1$)
      task.setCompleteBy( getDateTime(aTaskInstanceElement, "trt:context/trt:completeByMillis") ); //$NON-NLS-1$)
      task.setPresentationName( getText(aTaskInstanceElement, "trt:presentation/trt:name") ); //$NON-NLS-1$)
      task.setPresentationSubject( getText(aTaskInstanceElement, "trt:presentation/trt:subject") ); //$NON-NLS-1$)
      task.setRenderingMethodExists( selectElement(aTaskInstanceElement, "trt:renderings") != null); //$NON-NLS-1$
      task.setHasOutput( selectElement(aTaskInstanceElement, "trt:operational/trt:output/trt:part") != null); //$NON-NLS-1$
      task.setHasFault( selectElement(aTaskInstanceElement, "trt:operational/trt:fault") != null); //$NON-NLS-1$
      task.setHasAttachments( selectElement(aTaskInstanceElement, "trt:operational/trt:attachments/htda:attachment") != null); //$NON-NLS-1$
      task.setHasComments( selectElement(aTaskInstanceElement, "trt:operational/trt:comments/htda:comment") != null); //$NON-NLS-1$
      task.setEscalationTime( getDateTime(aTaskInstanceElement, "trt:context/trt:lastEscalatedTime") ); //$NON-NLS-1$)
      task.setModifiedTime( getDateTime(aTaskInstanceElement, "trt:context/trt:lastModifiedTime") ); //$NON-NLS-1$)
      task.setModifiedBy( getText(aTaskInstanceElement, "trt:context/trt:lastModifiedBy") ); //$NON-NLS-1$)      
      task.setSkipable( AeUtil.toBoolean( getText(aTaskInstanceElement, "trt:context/trt:isSkipable") ) ); //$NON-NLS-1$
      task.setPrimarySearchBy( getText(aTaskInstanceElement, "trt:context/trt:primarySearchBy") ); //$NON-NLS-1$
      return task;
   }
}
