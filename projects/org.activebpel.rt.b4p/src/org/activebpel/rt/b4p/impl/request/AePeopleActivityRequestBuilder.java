//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/request/AePeopleActivityRequestBuilder.java,v 1.1.4.2 2008/04/14 21:25:30 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.request; 

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.def.AeAttachmentPropagationDef;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.b4p.impl.task.data.AeHumanTaskContext;
import org.activebpel.rt.b4p.impl.task.data.AeInitialState;
import org.activebpel.rt.b4p.impl.task.data.AeInterfaceMetadata;
import org.activebpel.rt.b4p.impl.task.data.AePLBaseRequest;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeExpressionLanguageFactory;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.ht.def.IAeVariableUsageContainer;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * order of ops for task execution
 * 
 * + create request
 * + create initial state
 * x AeAssignTaskOverrides visitor (inlining of task)
 * x get inlined task from override visitor
 * T run local notification inliner (task only)
 * + createLPGMap() (important to run after notification inliner)
 * + resolveLiteralAndLPGAssignments()
 * + createHumanContext()
 * + addProcessVariables()
 * + resolveAssignmentExpressions()
 * T scheduledActions() (task only)
 * T start + complete by (task only)
 * T AeConvertLPGToLiteral.convert(lpgMap, taskDef); (task only)
 * + inlineExpressionLanguage
 * x request.setTaskDef(taskDef);
 * + setAttachments(aDef, taskContext)
 * + setCorrelationValuesOnRequest()
 * x AeInterfaceMetadata
 * 
 */
/**
 * Builds a request message for the lifecycle process, either for a task or a 
 * notification. The following steps are taken during this preprocessing:
 * 
 *  <table>
 *  <tr><th>What is being done</th>          <th>Why it's being done</th></tr>
 *  
 *  <tr><td>createInitialState</td>          <td>create the initial state object that houses 
 *                                               the initial state for the task/notification</td></tr>
 *                                               
 *  <tr><td>inlinePeopleActivityTaskData</td><td>We want to convert the peopleActivity's AeLocalTaskDef 
 *                                               or AeB4PLocalNotificationDef to their inline equivalent. 
 *                                               If the peopleActivity has an AeTaskDef or AeNotificationDef
 *                                               then no conversion happens. In either case, a clone is made 
 *                                               of the def since we'll be making further modifications to it
 *                                               and we don't want to affect the cached copy that is common
 *                                               to all process instances.</td></tr>
 *                                               
 *  <tr><td>createLPGMap</td>                <td>We need to create a map of all of the LPG's that could be
 *                                               used during the execution of the task/notification. This
 *                                               map will be used in some steps below.</td></tr>
 *                                               
 *  <tr><td>resolveLiteralAndLPGAssignments</td><td>The request message sent to the lifecycle process includes
 *                                               the standard ht-protocol element: humanTaskContext. This element
 *                                               can include overrides for assignments. These overrides are used
 *                                               to override the assignments in the task or notification. The 
 *                                               current impl of the lifecycle process pulls all of its assignments
 *                                               from this context, effectively ignoring any assignments in the
 *                                               task or notification. This was done to keep the BPEL simpler and
 *                                               not have it need to check for the assignments in different elements.
 *                                               Part of this implementation is to replace any LPG assignments with
 *                                               the evaluated LPG, effectively converting these LPG style assignments
 *                                               to literals.</td></tr>
 *                                               
 *  <tr><td>createHumanContext</td>          <td>creates the human context object that contains all of the assignment
 *                                               data and other overrides.</td></tr>
 *                                               
 *  <tr><td>addProcessVariables</td>         <td>Constellation 1 and 2 allow the user to reference process variables in
 *                                               the task definition. A process variable snapshot is created in order 
 *                                               to pass the values for process variables that are referenced in these
 *                                               expressions. This is only necessary for expressions that execute
 *                                               outside of the context of the peopleActivity's process. These expressions
 *                                               include presentation params, escalation conditions, assignments for 
 *                                               notifications created from escalations, and priorities for escalation
 *                                               notifications.</td></tr>
 *                                               
 *  <tr><td>resolveAssignmentExpressions</td><td>Any expression based assignemnts within the peopleActivity's task/notification
 *                                               are executed immediately and converted to literals within the human task context.</td></tr>
 *                                               
 *  <tr><td>inlineExpressionLanguage</td>    <td>Expressions that are executed outside of the peopleActivity's process rely 
 *                                               on a custom function similar to Javascript's eval(). In cases where the 
 *                                               expression language attribute is missing from an expression element, the
 *                                               language comes from the enclosing process definition or humanInteraction
 *                                               in the case of Constellation 3. Since these expressions will execute outside
 *                                               of the original's process context, we need to inline the expression language
 *                                               in order to know how to eval the expression inside the escalation or state machine.</td></tr>
 *                                               
 *  <tr><td>setAttachments</td>              <td>The peopleActivity will handle the propagation of attachments to the task.
 *                                               The code here is merely to set the flag on the expected return attachments.
 *                                               We're either going to want the new attachments, all attachments, or none.</td></tr>
 *                                               
 *  <tr><td>setCorrelationValuesOnRequest</td><td>The peopleActivity passes its process id and pa location id in order to setup 
 *                                                a correlationSet. If the peopleActivity is terminated for any reason, it will
 *                                                propagate an exit message to the lifecycle.</td></tr>
 *                                                
 *  <tr><td>createInterfaceMetadata</td>      <td>The interface metadata is used to validate the calls to getInput(), 
 *                                                getOutput(), and setFault(). These hints are also used for our own
 *                                                inbox</td></tr>
 *  </table>
 */
public abstract class AePeopleActivityRequestBuilder 
{
   /** activity life cycle context */
   private IAeActivityLifeCycleContext mContext;
   /** def for the people activity that is executing */
   private AePeopleActivityDef mPeopleActivityDef;
   /** instance id for the people activity, used in correlation */
   private int mPeopleActivityInstanceId;
   /** input for the people activity */
   private IAeMessageData mInputData;
   /** request object that we're creating */
   private AePLBaseRequest mRequest;
   /** inlined def for the people activity's data, will be a task or notification def */
   private AeBaseXmlDef mInlinedTaskDef;

   /**
    * C'tor that accepts life cycle context
    * @param aDef
    * @param aPaInstanceId
    * @param aData
    * @param aContext
    */
   public AePeopleActivityRequestBuilder(AePeopleActivityDef aDef, int aPaInstanceId, IAeMessageData aData, IAeActivityLifeCycleContext aContext)
   {
      setPeopleActivityDef(aDef);
      setContext(aContext);
      setPeopleActivityInstanceId(aPaInstanceId);
      setInputData(aData);
      setRequest(createRequest());
   }
   
   /**
    * Creates the base request object
    */
   protected abstract AePLBaseRequest createRequest();
   
   /**
    * Builds the request.
    * @throws AeBusinessProcessException
    */
   protected AePLBaseRequest buildBaseRequest() throws AeBusinessProcessException
   {
      AeInitialState initialState = createInitialState(); 
      getRequest().setInitialState(initialState);

      // Call Assign Task Overrides visitor to create a task that contains an aggregate of people assignments and priority.
      // If this is a local task then, we clone the inline task in that and then on the cloned task set the people
      // assignment and priority overrides. 
      // If this is a task then we just clone it.
      // After we get this cloned task we will not need the local task for priority and people assignments overrides.
      AeBaseXmlDef inlinedTaskDef = inlinePeopleActivityTaskData();
      setInlinedTaskDef(inlinedTaskDef);

      // Create a map of lpg def to org entity for all lpgs referred in people activity
      Map lpgMap = createLPGMap();

      // Resolve literal and LPG people assignments in task and delegation
      AePeopleAssignmentsDef paDefForContext = resolveLiteralAndLPGAssignments(lpgMap);
      
      // Create human task context with people assignments evaluated so far and set it on task request
      // we want to set people assignments here so they are available when executing people assignments from expressions.
      AeHumanTaskContext taskContext = createHumanContext(initialState, paDefForContext);

      addProcessVariables(initialState, (IAeVariableUsageContainer) inlinedTaskDef);
      
      // Resolve people assignments from expressions in task and delegation
      resolveAssignmentExpressions(paDefForContext);
      
      // Inline expression language on expressions
      inlineExpressionLanguage();
      
      // Set attachments
      setAttachments(taskContext);

      setCorrelationValuesOnRequest();
      
      AeInterfaceMetadata metadata = createInterfaceMetadata();
      getRequest().setInterfaceMetadata(metadata);

      return getRequest();
   }

   /**
    * Creates the interface metadata.
    */
   protected AeInterfaceMetadata createInterfaceMetadata()
   {
      AeInterfaceMetadata metadata = new AeInterfaceMetadata();
      metadata.setInput(getPeopleActivityDef().getProducerMessagePartsMap());
      metadata.setOutput(getPeopleActivityDef().getConsumerMessagePartsMap());
      return metadata;
   }

   /**
    * Converts the peopleActivity's localTask or localNotification to an inline
    * task/notification. In either case, the resource is cloned before returning.
    */
   protected abstract AeBaseXmlDef inlinePeopleActivityTaskData();
   

   /**
    * creates the initial state object
    */
   protected AeInitialState createInitialState()
   {
      String createdBy = getContext().getProcess().getProcessInitiator();

      // Set input in intial state so it is available for evaluating expressions
      AeInitialState initialState = AePARequestUtil.createInitialState(getInputData(), createdBy);
      return initialState;
   }

   /**
    * creates the lpg map
    */
   protected Map createLPGMap()
   {
      AeLPGMapCreationVisitor lpgMapper = new AeLPGMapCreationVisitor(getContext());
      getPeopleActivityDef().accept(lpgMapper);
      Map lpgMap = lpgMapper.getLPGMap();
      return lpgMap;
   }

   /**
    * Resolves the literals and lpg assignments
    * @param aLpgMap
    */
   protected AePeopleAssignmentsDef resolveLiteralAndLPGAssignments(Map aLpgMap)
   {
      AeLPGLiteralPeopleAssignmentsVisitor lpgLiteralAssignVisitor = new AeLPGLiteralPeopleAssignmentsVisitor(aLpgMap);
      getInlinedTaskDef().accept(lpgLiteralAssignVisitor);
      AePeopleAssignmentsDef paDefForContext = lpgLiteralAssignVisitor.getPeopleAssignmentsDef();
      return paDefForContext;
   }

   /**
    * Creates the context object
    * @param aInitialState
    * @param aPeopleAssignmentsDef
    */
   protected AeHumanTaskContext createHumanContext(AeInitialState aInitialState, AePeopleAssignmentsDef aPeopleAssignmentsDef)
   {
      AeHumanTaskContext taskContext = AePARequestUtil.createHumanTaskContext(aPeopleAssignmentsDef, getPeopleActivityDef().isSkipable());
      aInitialState.setHumanTaskContext(taskContext);
      return taskContext;
   }

   /**
    * sets the return attachments flag on the context.
    * @param aTaskContext
    */
   protected void setAttachments(AeHumanTaskContext aTaskContext)
         throws AeBusinessProcessException
   {
      AeAttachmentPropagationDef def = getPeopleActivityDef().getAttachmentPropagation();
      
      if (def != null)
      {
         String to = def.getToProcess();
         aTaskContext.setTo(to);
      }
   }

   /**
    * Creates a process variables snapshot for the task
    * @param initialState
    * @param aVariableUsageContainer
    */
   protected void addProcessVariables(AeInitialState initialState, IAeVariableUsageContainer aVariableUsageContainer)
   {
      // create process variables set from the used variables in the task and set it in request
      Set processVariables = createProcessVariablesSet(aVariableUsageContainer.getUsedVariables());
      initialState.setProcessVariables(processVariables);
   }

   /**
    * find variables and build a set of AeVariables
    * @param aUsedVariables
    */
   private Set createProcessVariablesSet(Set aUsedVariables)
   {
      Set variables = null;
      for(Iterator iter=aUsedVariables.iterator(); iter.hasNext(); )
      {
         String location = (String) iter.next();
         IAeVariable var = getContext().getProcess().findProcessVariable(location);
         if (var != null)
         {
            if (variables == null)
               variables = new LinkedHashSet();
            variables.add(var);
         }
      }
      return variables != null? variables : Collections.EMPTY_SET;
   }

   /**
    * Executes the expressions for the people assignments
    * @param aPeopleAsssignmentsDef
    */
   protected void resolveAssignmentExpressions(AePeopleAssignmentsDef aPeopleAsssignmentsDef) throws AeBusinessProcessException
   {
      AePAExpressionResolver expressionResolver = new AePAExpressionResolver(getContext(), aPeopleAsssignmentsDef, getRequest());
      expressionResolver.resolveExpressions(getInlinedTaskDef());
   }

   /**
    * visits each of the expression defs and adds the expression lang attr if missing
    * @throws AeBusinessProcessException
    */
   protected void inlineExpressionLanguage() throws AeBusinessProcessException
   {
      AeExpressionLanguageVisitor exprLangVisitor = new AeExpressionLanguageVisitor(getDefaultExprLanguage());
      getInlinedTaskDef().accept(exprLangVisitor);
   }

   /**
    * Returns default expression language of the process
    * @throws AeBusinessProcessException
    */
   private String getDefaultExprLanguage() throws AeBusinessProcessException
   {
      // Set expression Language on condition, searchby and presentation param defs
      IAeExpressionLanguageFactory exprLangFactory;
      try
      {
         exprLangFactory = getContext().getProcess().getEngine().getEngineConfiguration().getExpressionLanguageFactory();
      }
      catch (AeException ex)
      {
         throw new AeBusinessProcessException(ex.getLocalizedMessage());
      }
      return exprLangFactory.getBpelDefaultLanguage(getContext().getProcess().getBPELNamespace());
   }

   /**
    * Sets the correlation values on the request object
    */
   protected void setCorrelationValuesOnRequest()
   {
      // set pid
      getRequest().setPeopleActivityProcessId((getContext().getProcess().getProcessId()));
      // set PA instance id
      getRequest().setPeopleActivityInstanceId(getPeopleActivityInstanceId());
   }

   /**
    * @return the peopleActivityDef
    */
   protected AePeopleActivityDef getPeopleActivityDef()
   {
      return mPeopleActivityDef;
   }

   /**
    * @param aPeopleActivityDef the peopleActivityDef to set
    */
   protected void setPeopleActivityDef(AePeopleActivityDef aPeopleActivityDef)
   {
      mPeopleActivityDef = aPeopleActivityDef;
   }

   /**
    * @return the peopleActivityInstanceId
    */
   protected int getPeopleActivityInstanceId()
   {
      return mPeopleActivityInstanceId;
   }

   /**
    * @param aPeopleActivityInstanceId the peopleActivityInstanceId to set
    */
   protected void setPeopleActivityInstanceId(int aPeopleActivityInstanceId)
   {
      mPeopleActivityInstanceId = aPeopleActivityInstanceId;
   }

   /**
    * @return the request
    */
   protected AePLBaseRequest getRequest()
   {
      return mRequest;
   }

   /**
    * @param aRequest the request to set
    */
   protected void setRequest(AePLBaseRequest aRequest)
   {
      mRequest = aRequest;
   }

   /**
    * @return the inputData
    */
   protected IAeMessageData getInputData()
   {
      return mInputData;
   }

   /**
    * @param aInputData the inputData to set
    */
   protected void setInputData(IAeMessageData aInputData)
   {
      mInputData = aInputData;
   }

   /**
    * @return the inlinedTaskDef
    */
   protected AeBaseXmlDef getInlinedTaskDef()
   {
      return mInlinedTaskDef;
   }

   /**
    * @param aInlinedTaskDef the inlinedTaskDef to set
    */
   protected void setInlinedTaskDef(AeBaseXmlDef aInlinedTaskDef)
   {
      mInlinedTaskDef = aInlinedTaskDef;
   }

   /**
    * @return the context
    */
   protected IAeActivityLifeCycleContext getContext()
   {
      return mContext;
   }

   /**
    * @param aContext the context to set
    */
   protected void setContext(IAeActivityLifeCycleContext aContext)
   {
      mContext = aContext;
   }
}
 