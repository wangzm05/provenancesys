//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/AeCreateHumanTaskContextFunction.java,v 1.4.4.2 2008/04/14 21:24:48 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function; 

import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.def.io.AeB4PIO;
import org.activebpel.rt.b4p.function.ht.AeTaskStateBasedHtFunctionContext;
import org.activebpel.rt.b4p.impl.request.AePARequestSerializer;
import org.activebpel.rt.b4p.impl.task.data.AeHumanTaskContext;
import org.activebpel.rt.b4p.server.function.eval.AeB4PEvalFunction;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerVariableResolver;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;
import org.activebpel.rt.ht.IAeHtFunctionContext;
import org.activebpel.rt.ht.def.AeAbstractGenericHumanRoleDef;
import org.activebpel.rt.ht.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.ht.def.AeRecipientsDef;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.def.AeBaseDefNamespaceContext;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Creates the htp:humanTaskContext element for a notification that is executed
 * from the escalation process. This element contains the priority and assignment
 * overrides for the notification. The state machine requires this context to
 * be present. It is typically created by the execution of a peopleActivity but
 * in the case of an escalation notification it is created on the fly. This is 
 * because the escalation may pull values from the currently executing task
 * in order to populate its assignments or other data.
 * 
 *  Function params are as follows:
 *  
 *  ht:notification
 *  trt:input
 *  trt:taskInstance
 *  trt:processVariables
 *  
 *  Function needs to produce an htp:humanTaskContext which includes the following:
 *  htp:priority
 *  htp:peopleAssignments
 *      htp:recipients?
 *      htp:businessAdministrators?
 */
public class AeCreateHumanTaskContextFunction extends AeAbstractBpelFunction
{
   /** constant for the name of the function */
   public static String FUNCTION_NAME = "createHumanTaskContext"; //$NON-NLS-1$
   
   /** notification def */
   private AeNotificationDef mNotificationDef;
   /** eval function */
   private AeB4PEvalFunction mEvalFunction;
   /** context we're producing */
   private AeHumanTaskContext mHumanTaskContext;
   
   /**
    * Ctor
    */
   public AeCreateHumanTaskContextFunction()
   {
      super();
      
      setEvalFunction(new AeB4PEvalFunction());
      setHumanTaskContext(new AeHumanTaskContext());
      getHumanTaskContext().setPeopleAssignments(new AePeopleAssignmentsDef());
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs)
         throws AeFunctionCallException
   {
      // fixme (MF-b4p) validate args
      
      Element notificationElement = (Element) aArgs.get(0);
      try
      {
         AeNotificationDef notificationDef = (AeNotificationDef) AeB4PIO.deserializeFragment(notificationElement);
         setNotificationDef(notificationDef);
         
         Element input = (Element) aArgs.get(1);
         
         Element parentTaskData = (Element) aArgs.get(2);
         
         Element processVariables = (Element) aArgs.get(3);
         
         IAeHtFunctionContext parentTaskContext = new AeTaskStateBasedHtFunctionContext(parentTaskData, null);
         
         IAeHtFunctionContext htFunctionContext = new AeEscalationNotificationHTFunctionContext(notificationDef.getName(), getHumanTaskContext(), input, parentTaskContext);
         
         IAeExpressionRunnerVariableResolver resolver = getEvalFunction().toVariableResolver(aContext, processVariables);
         getEvalFunction().setVariableResolver(resolver);
         getEvalFunction().setHtFunctionContext(htFunctionContext);
         
         // eval the priority expression
         Number number = evalPriority(aContext);
         getHumanTaskContext().setPriority(number.intValue());
         
         // eval recipients
         AeAbstractGenericHumanRoleDef roleDef = getNotificationDef().getPeopleAssignments().getRecipients();
         Element orgEntity = evalRole(aContext, roleDef);
         if (orgEntity != null)
         {
            AeRecipientsDef recipientsDef = new AeRecipientsDef();
            AeExtensionElementDef extensionElementDef = new AeExtensionElementDef(orgEntity);
            recipientsDef.addExtensionElementDef(extensionElementDef);
            getHumanTaskContext().getPeopleAssignments().setRecipients(recipientsDef);
         }
         
         // eval businessAdministrators
         roleDef = getNotificationDef().getPeopleAssignments().getBusinessAdministrators();
         orgEntity = evalRole(aContext, roleDef);
         if (orgEntity != null)
         {
            AeBusinessAdministratorsDef businessAdminDef = new AeBusinessAdministratorsDef();
            AeExtensionElementDef extensionElementDef = new AeExtensionElementDef(orgEntity);
            businessAdminDef.addExtensionElementDef(extensionElementDef);
            getHumanTaskContext().getPeopleAssignments().setBusinessAdministrators(businessAdminDef);
         }
         Document doc = AeXmlUtil.newDocument();
         Element element = AePARequestSerializer.addHumanTaskContext(doc, getHumanTaskContext());
         return element;
      }
      catch (AeException e)
      {
         throw new AeFunctionCallException(e);
      }
      catch(Throwable t)
      {
         t.printStackTrace();
         throw new AeFunctionCallException(t);
      }
   }

   /**
    * Evaluates the priority expression or returns 0 if there wasn't one
    * @param aContext
    * @throws AeException
    */
   protected Number evalPriority(IAeFunctionExecutionContext aContext) throws AeException
   {
      Number number;
      if (getNotificationDef().getPriority() != null)
      {
         getEvalFunction().setNamespaceContext(new AeBaseDefNamespaceContext(getNotificationDef().getPriority()));
         getEvalFunction().setExpressionLanguage(getNotificationDef().getPriority().getExpressionLanguage());
         getEvalFunction().setExpression(getNotificationDef().getPriority().getExpression());
         
         // FIXMEQ Spec mandates integer here but we could do better with an implicit cast/conversion
         number = (Number) getEvalFunction().eval(aContext);
      }
      else
      {
         // default is 0
         number = new Integer(0);
      }
      return number;
   }

   /**
    * Evals the role if there's an expression 
    * @param aContext
    * @param roleDef
    * @throws AeException
    */
   protected Element evalRole(IAeFunctionExecutionContext aContext, AeAbstractGenericHumanRoleDef roleDef) throws AeException
   {
      Element orgEntity = null;
      if (roleDef != null)
      {
         if (roleDef.getFrom().isExpression())
         {
            getEvalFunction().setNamespaceContext(new AeBaseDefNamespaceContext(roleDef.getFrom()));
            getEvalFunction().setExpressionLanguage(roleDef.getFrom().getExpressionLanguage());
            getEvalFunction().setExpression(roleDef.getFrom().getExpression());
            orgEntity = (Element) getEvalFunction().eval(aContext);
         }
         else if (roleDef.getFrom().isLiteral() && !roleDef.getFrom().getLiteral().getChildNodes().isEmpty())
         {
            return (Element) roleDef.getFrom().getLiteral().getChildNodes().get(0);
         }
      }
      return orgEntity;
   }

   /**
    * @return the notificationDef
    */
   protected AeNotificationDef getNotificationDef()
   {
      return mNotificationDef;
   }

   /**
    * @param aNotificationDef the notificationDef to set
    */
   protected void setNotificationDef(AeNotificationDef aNotificationDef)
   {
      mNotificationDef = aNotificationDef;
   }

   /**
    * @return the evalFunction
    */
   protected AeB4PEvalFunction getEvalFunction()
   {
      return mEvalFunction;
   }

   /**
    * @param aEvalFunction the evalFunction to set
    */
   protected void setEvalFunction(AeB4PEvalFunction aEvalFunction)
   {
      mEvalFunction = aEvalFunction;
   }

   /**
    * @return the humanTaskContext
    */
   protected AeHumanTaskContext getHumanTaskContext()
   {
      return mHumanTaskContext;
   }

   /**
    * @param aHumanTaskContext the humanTaskContext to set
    */
   protected void setHumanTaskContext(AeHumanTaskContext aHumanTaskContext)
   {
      mHumanTaskContext = aHumanTaskContext;
   }
}
 