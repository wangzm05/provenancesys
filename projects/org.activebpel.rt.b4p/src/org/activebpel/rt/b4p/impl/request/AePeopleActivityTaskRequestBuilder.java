//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/request/AePeopleActivityTaskRequestBuilder.java,v 1.1.4.2 2008/04/14 21:25:30 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.request;

import java.util.Map;

import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.b4p.impl.engine.AeTask;
import org.activebpel.rt.b4p.impl.lpg.AeConvertLPGToLiteral;
import org.activebpel.rt.b4p.impl.task.data.AeInterfaceMetadata;
import org.activebpel.rt.b4p.impl.task.data.AePLBaseRequest;
import org.activebpel.rt.b4p.impl.task.data.AeProcessTaskRequest;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.w3c.dom.Document;

/**
 * This class builds a task request from the people activity def and input message for the people activity
 */
public class AePeopleActivityTaskRequestBuilder extends AePeopleActivityRequestBuilder
{
   /**
    * C'tor that accepts life cycle context
    * @param aDef
    * @param aPaInstanceId
    * @param aData
    * @param aContext
    */
   public AePeopleActivityTaskRequestBuilder(AePeopleActivityDef aDef, int aPaInstanceId, IAeMessageData aData, IAeActivityLifeCycleContext aContext)
   {
      super(aDef, aPaInstanceId, aData, aContext);
   }

   /**
    * Returns task request for task lifecycle process 
    * @throws AeBusinessProcessException
    */
   public AeProcessTaskRequest buildPLTaskRequest() throws AeBusinessProcessException
   {
      AeProcessTaskRequest taskRequest = (AeProcessTaskRequest) buildBaseRequest();

      // execute defer activation and expiration expressions and set them on
      // request.
      AePAExpressionResolver expressionResolver = new AePAExpressionResolver(getContext(), taskRequest.getInitialState().getHumanTaskContext().getPeopleAssignments(), getRequest());
      if (getPeopleActivityDef().getScheduledActions() != null)
         getPeopleActivityDef().getScheduledActions().accept(expressionResolver);

      // set startBy and completeBy
      AeStartAndCompleteByVisitor startAndCompleteByVisitor = new AeStartAndCompleteByVisitor(
            getContext(), taskRequest);
      startAndCompleteByVisitor.setStartAndCompleteBy((AeTaskDef) getInlinedTaskDef());

      // Set task on the request
      taskRequest.setTaskDef((AeTaskDef) getInlinedTaskDef());
      
      return taskRequest;
   }
   
   /**
    * @see org.activebpel.rt.b4p.impl.request.AePeopleActivityRequestBuilder#inlinePeopleActivityTaskData()
    */
   protected AeBaseXmlDef inlinePeopleActivityTaskData()
   {
      AeAssignTaskOverrides taskOverrideVisitor = new AeAssignTaskOverrides();
      getPeopleActivityDef().accept(taskOverrideVisitor);
      AeTaskDef taskDef = taskOverrideVisitor.getTaskDef();

      // extra step of inlining the local notifications
      AeLocalNotificationInliner localNotificationInliner = new AeLocalNotificationInliner(getContext());
      taskDef.accept(localNotificationInliner);
      
      return taskDef;
   }
   
   
   /**
    * Overrides the super to add fault names
    * @see org.activebpel.rt.b4p.impl.request.AePeopleActivityRequestBuilder#createInterfaceMetadata()
    */
   protected AeInterfaceMetadata createInterfaceMetadata()
   {
      AeInterfaceMetadata interfaceMetadata = super.createInterfaceMetadata();
      AeTaskDef taskDef = (AeTaskDef) getInlinedTaskDef();
      interfaceMetadata.setFaultNames(AeUtil.toList(taskDef.getTaskInterfaceDef().getFaults()));
      return interfaceMetadata;
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePeopleActivityRequestBuilder#createRequest()
    */
   protected AePLBaseRequest createRequest()
   {
      return new AeProcessTaskRequest();
   }
   
   /**
    * @see org.activebpel.rt.b4p.impl.request.AePeopleActivityRequestBuilder#createLPGMap()
    */
   protected Map createLPGMap()
   {
      Map lpgMap = super.createLPGMap();
      // Inline LPGs in any notifications of this task
      AeConvertLPGToLiteral.convert(lpgMap, getInlinedTaskDef());
      return lpgMap;
   }

   /**
    * Convenience method that serializes the task to a document, running all of
    * the 
    * @param aTask
    * @param aContext
    * @throws AeBusinessProcessException
    */
   public static Document serialize(AeTask aTask,  IAeActivityLifeCycleContext aContext)
         throws AeBusinessProcessException
   {
      AePeopleActivityTaskRequestBuilder taskBuilder = new AePeopleActivityTaskRequestBuilder(aTask.getPeopleActivityDef(), aTask.getLocationId(), aTask.getMessageData(), aContext);
      AeProcessTaskRequest req = taskBuilder.buildPLTaskRequest();
      AePARequestSerializer serializer = new AePARequestSerializer(aContext.getProcess().getEngine().getTypeMapping());
      Document doc = serializer.serialize(req);
      return doc;
   }  
}
