//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/request/AePeopleActivityNotificationRequestBuilder.java,v 1.1.4.2 2008/04/14 21:25:30 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.request;

import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.b4p.impl.engine.AeNotification;
import org.activebpel.rt.b4p.impl.task.data.AePLBaseRequest;
import org.activebpel.rt.b4p.impl.task.data.AeProcessNotificationRequest;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.w3c.dom.Document;

/**
 * This class builds notification request from the people activity def and input message of people activity.
 */
public class AePeopleActivityNotificationRequestBuilder extends AePeopleActivityRequestBuilder
{
   /**
    * C'tor that accepts life cycle context
    * @param aDef
    * @param aPaInstanceId
    * @param aData
    * @param aContext
    */
   public AePeopleActivityNotificationRequestBuilder(AePeopleActivityDef aDef, int aPaInstanceId, IAeMessageData aData, IAeActivityLifeCycleContext aContext)
   {
      super(aDef, aPaInstanceId, aData, aContext);
   }
   
   /**
    * @see org.activebpel.rt.b4p.impl.request.AePeopleActivityRequestBuilder#createRequest()
    */
   protected AePLBaseRequest createRequest()
   {
      return new AeProcessNotificationRequest();
   }

   /**
    * Returns notification request
    * @throws AeBusinessProcessException
    */
   public AeProcessNotificationRequest buildNotificationRequest() throws AeBusinessProcessException
   {
      AeProcessNotificationRequest notificationRequest = (AeProcessNotificationRequest) buildBaseRequest();

      // Set notification on the request
      notificationRequest.setNotificationDef((AeNotificationDef) getInlinedTaskDef());
       
      return (AeProcessNotificationRequest) notificationRequest;
   }
   
   /**
    * @see org.activebpel.rt.b4p.impl.request.AePeopleActivityRequestBuilder#inlinePeopleActivityTaskData()
    */
   protected AeBaseXmlDef inlinePeopleActivityTaskData()
   {
      AeAssignNotificationOverrides notificationOverrideVisitor = new AeAssignNotificationOverrides();
      getPeopleActivityDef().accept(notificationOverrideVisitor);
      AeNotificationDef notificationDef = notificationOverrideVisitor.getNotificationDef();
      return notificationDef;
   }

   /**
    * Convenience method that serializes the task to a document, running all of
    * the
    * @param aNotification
    * @param aContext
    * @throws AeBusinessProcessException
    */
   public static Document serialize(AeNotification aNotification,  IAeActivityLifeCycleContext aContext)
         throws AeBusinessProcessException
   {
      AePeopleActivityNotificationRequestBuilder notificationBuilder = new AePeopleActivityNotificationRequestBuilder(aNotification.getPeopleActivityDef(), aNotification.getLocationId(), aNotification.getMessageData(), aContext);
      AeProcessNotificationRequest req = notificationBuilder.buildNotificationRequest();
      AePARequestSerializer serializer = new AePARequestSerializer(aContext.getProcess().getEngine().getTypeMapping());
      Document doc = serializer.serialize(req);
      return doc;
   }
}
