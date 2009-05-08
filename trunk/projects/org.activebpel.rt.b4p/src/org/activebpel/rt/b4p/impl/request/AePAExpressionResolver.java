//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/request/AePAExpressionResolver.java,v 1.9.4.2 2008/04/14 21:25:30 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.request;

import java.util.Date;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.def.AeAlarmDef;
import org.activebpel.rt.b4p.def.AeDeferActivationDef;
import org.activebpel.rt.b4p.def.AeExpirationDef;
import org.activebpel.rt.b4p.def.io.AeB4PIO;
import org.activebpel.rt.b4p.function.ht.AeHTExtensionFunctionFactory;
import org.activebpel.rt.b4p.function.ht.AePeopleActivityBasedHtFunctionContext;
import org.activebpel.rt.b4p.impl.task.data.AeHumanTaskContext;
import org.activebpel.rt.b4p.impl.task.data.AePLBaseRequest;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.function.IAeFunctionFactory;
import org.activebpel.rt.bpel.impl.activity.AeDelegatingFunctionFactory;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext;
import org.activebpel.rt.expr.AeExpressionResultConverter;
import org.activebpel.rt.ht.def.AeAbstractGenericHumanRoleDef;
import org.activebpel.rt.ht.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.ht.def.AeDelegationDef;
import org.activebpel.rt.ht.def.AeExcludedOwnersDef;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AeHtDefUtil;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.ht.def.AePotentialOwnersDef;
import org.activebpel.rt.ht.def.AePriorityDef;
import org.activebpel.rt.ht.def.AeRecipientsDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTaskInitiatorDef;
import org.activebpel.rt.ht.def.AeTaskStakeHoldersDef;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;
import org.activebpel.rt.xml.schema.AeSchemaDuration;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This visitor resolves expressions in priority def and people assignments in a task def
 */
public class AePAExpressionResolver extends AePABaseVisitor
{
   /** constant for org entity qname */
   private static final QName ORG_ENTITY_QNAME = new QName(IAeHtDefConstants.DEFAULT_HT_NS, IAeHtDefConstants.TAG_ORGANIZATIONAL_ENTITY);

   /** Task Request Object */
   private AePLBaseRequest mRequest;
   /** exception that can occur when executing an expression */
   private AeBusinessProcessException mException;
   /** People assignments def object */
   private AePeopleAssignmentsDef mPeopleAssignmentsDef;
   /** function factory */
   private IAeFunctionFactory mDelegatingFuncFactory;
   /** function context */
   private AePeopleActivityBasedHtFunctionContext mFunctionContext;

   /**
    * C'tor
    * @param aContext
    * @param aPeopleAssignmentsDef
    * @param aRequest
    */
   public AePAExpressionResolver(IAeActivityLifeCycleContext aContext, AePeopleAssignmentsDef aPeopleAssignmentsDef, AePLBaseRequest aRequest)
   {
      super(aContext);
      mPeopleAssignmentsDef = aPeopleAssignmentsDef;
      mRequest = aRequest;
      mDelegatingFuncFactory = new AeDelegatingFunctionFactory(new AeHTExtensionFunctionFactory(), getContext().getContextFunctionFactory());
      mFunctionContext = new AePeopleActivityBasedHtFunctionContext(aRequest);
   }

   /**
    * sets people assignments in task def as extension elements when the assignments are from expression
    * @param aDef
    * @throws AeBusinessProcessException
    */
   public void resolveExpressions(AeBaseXmlDef aDef) throws AeBusinessProcessException
   {
      aDef.accept(this);
      if (getException() != null)
         throw getException();
   }

   /**
    * Resolves priority expression and return priority as an xsd:nonNegativeInteger
    *
    * @param aDef
    */
   public void visit(AePriorityDef aDef)
   {
      try
      {
         AeExpressionDef expressionDef = AePARequestUtil.createExpressionDef(aDef.getExpression(), aDef.getExpressionLanguage(), getContext().getProcess().getBPELNamespace());
         Object result = executeExpressionWithContext(expressionDef);
         try
         {
            int priority = AeExpressionResultConverter.toNonNegativeInt(result);
            AeHumanTaskContext htContext = getRequest().getInitialState().getHumanTaskContext();
            htContext.setPriority(priority);
         }
         catch (IllegalArgumentException iae)
         {
            String message = AeMessages.format("AePAExpressionResolver.BadPriorityExpressionError", //$NON-NLS-1$
                  new Object[] { AeHtDefUtil.getInScopeNotificationOrTaskName(aDef) });
            throw new AeBusinessProcessException(message, iae);
         }
      }
      catch (AeBusinessProcessException ex)
      {
         setException(ex);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeDeferActivationDef)
    */
   public void visit(AeDeferActivationDef aDef)
   {
      try
      {
         getRequest().getInitialState().getHumanTaskContext().setDeferActivationTime(convertToSchemaDateTime(aDef));
      }
      catch (AeBusinessProcessException ex)
      {
         setException(ex);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeExpirationDef)
    */
   public void visit(AeExpirationDef aDef)
   {
      try
      {
         getRequest().getInitialState().getHumanTaskContext().setExpiration(convertToSchemaDateTime(aDef));
      }
      catch (AeBusinessProcessException ex)
      {
         setException(ex);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      callAccept(aDef.getPriority());
      callAccept(aDef.getPeopleAssignments());
      callAccept(aDef.getDelegation());
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      callAccept(aDef.getPriority());
      callAccept(aDef.getPeopleAssignments());
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDelegationDef)
    */
   public void visit(AeDelegationDef aDef)
   {
      AeFromDef fromDef = aDef.getFrom();
      if ( (fromDef == null) || (!fromDef.isExpression()) )
         return;

      try
      {
         // Get organizational entity as an element
         Element orgEntity = getOrgEntityInFromDef(fromDef);
         // Set this element as a literal on the from def in delegation def
         AeFromDef from = AePARequestUtil.wrapOrgEntityAsLiteralInFromDef(orgEntity);
         aDef.setFrom(from);
      }
      catch (AeBusinessProcessException ex)
      {
         setException(ex);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      Element orgEntity = resolvePeopleAssignExpressionToOrgEntity((AeAbstractGenericHumanRoleDef) aDef);
      AeBusinessAdministratorsDef def = (AeBusinessAdministratorsDef) AePARequestUtil.getHumanRoleHTProtocolStyle(aDef, orgEntity);
      getPeopleAssignmentsDef().setBusinessAdministrators(def != null ? def : getPeopleAssignmentsDef().getBusinessAdministrators());
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeExcludedOwnersDef)
    */
   public void visit(AeExcludedOwnersDef aDef)
   {
      Element orgEntity = resolvePeopleAssignExpressionToOrgEntityNoException((AeAbstractGenericHumanRoleDef) aDef);
      AeExcludedOwnersDef def = (AeExcludedOwnersDef) AePARequestUtil.getHumanRoleHTProtocolStyle(aDef, orgEntity);
      getPeopleAssignmentsDef().setExcludedOwners(def != null ? def : getPeopleAssignmentsDef().getExcludedOwners());
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AePotentialOwnersDef)
    */
   public void visit(AePotentialOwnersDef aDef)
   {
      Element orgEntity = resolvePeopleAssignExpressionToOrgEntityNoException((AeAbstractGenericHumanRoleDef) aDef);
      AePotentialOwnersDef def = (AePotentialOwnersDef) AePARequestUtil.getHumanRoleHTProtocolStyle(aDef, orgEntity);
      getPeopleAssignmentsDef().setPotentialOwners(def != null ? def : getPeopleAssignmentsDef().getPotentialOwners());
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeTaskInitiatorDef)
    */
   public void visit(AeTaskInitiatorDef aDef)
   {
      Element orgEntity = resolvePeopleAssignExpressionToOrgEntityNoException((AeAbstractGenericHumanRoleDef) aDef);
      AeTaskInitiatorDef def = (AeTaskInitiatorDef) AePARequestUtil.getHumanRoleHTProtocolStyle(aDef, orgEntity);
      getPeopleAssignmentsDef().setTaskInitiator(def != null ? def : getPeopleAssignmentsDef().getTaskInitiator());
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeTaskStakeHoldersDef)
    */
   public void visit(AeTaskStakeHoldersDef aDef)
   {
      Element orgEntity = resolvePeopleAssignExpressionToOrgEntityNoException((AeAbstractGenericHumanRoleDef) aDef);
      AeTaskStakeHoldersDef def = (AeTaskStakeHoldersDef) AePARequestUtil.getHumanRoleHTProtocolStyle(aDef, orgEntity);
      getPeopleAssignmentsDef().setTaskStakeholders(def != null ? def : getPeopleAssignmentsDef().getTaskStakeholders());
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeRecipientsDef)
    */
   public void visit(AeRecipientsDef aDef)
   {
      Element orgEntity = resolvePeopleAssignExpressionToOrgEntityNoException((AeAbstractGenericHumanRoleDef) aDef);
      AeRecipientsDef def = (AeRecipientsDef) AePARequestUtil.getHumanRoleHTProtocolStyle(aDef, orgEntity);
      getPeopleAssignmentsDef().setRecipients(def != null ? def : getPeopleAssignmentsDef().getRecipients());
   }

   /**
    * If the from assignment in the humman role is an expression then executes the expression
    * and sets organizational entity as an extension element in the human role.
    * @param aDef
    */
   private Element resolvePeopleAssignExpressionToOrgEntity(AeAbstractGenericHumanRoleDef aDef)
   {
      AeFromDef fromDef = aDef.getFrom();
      if ( (fromDef == null) || (!fromDef.isExpression()) )
         return null;

      try
      {
         Element orgEntity = getOrgEntityInFromDef(fromDef);
         return orgEntity;
      }
      catch (AeBusinessProcessException ex)
      {
         setException(ex);
         return null;
      }
   }

   /**
    * If the from assignment in the humman role is an expression then executes
    * the expression and sets organizational entity as an extension element in
    * the human role.  This variant ignores any error that may have occurred
    * and returns instead an empty orgEntity.
    *
    * @param aDef
    */
   private Element resolvePeopleAssignExpressionToOrgEntityNoException(AeAbstractGenericHumanRoleDef aDef)
   {
      AeFromDef fromDef = aDef.getFrom();
      if ( (fromDef == null) || (!fromDef.isExpression()) )
         return null;

      try
      {
         Element orgEntity = getOrgEntityInFromDef(fromDef);
         return orgEntity;
      }
      catch (AeBusinessProcessException ex)
      {
         return null;
      }
   }

   /**
    * Executes expression in the from def and returns organizational entity as
    * an Element. If the expression evaluates to something other than a valid
    * ht:tUser or ht:tOrganizationalEntity then the method will throw.
    * @param aDef
    * @throws AeBusinessProcessException
    */
   private Element getOrgEntityInFromDef(AeFromDef aDef) throws AeBusinessProcessException
   {
      // execute the expression to get our value
      AeExpressionDef expressionDef = AePARequestUtil.createExpressionDef(aDef.getExpression(), 
            aDef.getExpressionLanguage(), getContext().getProcess().getBPELNamespace());
      Object retValue = executeExpressionWithContext(expressionDef);

      // convert the value to an ht:organizationalEntity element.
      Element converted = null;
      String exceptionMessage = null;
      try
      {
         converted = convertToOrgEntity(retValue);
      }
      catch (AeException e)
      {
         // record the exception message for a detailed error message below
         exceptionMessage = e.getLocalizedMessage();
      }

      // if we couldn't convert it, then throw
      if (converted == null)
      {
         // report an error
         String exceptionDuringConversion = AeUtil.getSafeString(exceptionMessage);
         Object value = null;
         if (retValue instanceof Element)
            value = AeXMLParserBase.documentToString((Node) retValue);
         else
            value = retValue;

         Object[] args = { value, aDef.getExpression(), exceptionDuringConversion };
         String message = AeMessages.format("AePAExpressionResolver.convertToOrgEntity", args); //$NON-NLS-1$
         throw new AeBusinessProcessException(message);
      }

      return converted;
   }

   /**
    * Converts the value to an organizationalEntity element. The object passed in
    * can either be an element of type htd:tUser or an element of type htd:tOrganizationalEntity.
    * If it's something else then an exception is thrown.
    * @param aElement
    * @throws AeException
    */
   protected static Element convertToOrgEntity(Object aValue) throws AeException
   {
      // static so i can test from junit

      if (aValue instanceof Element)
      {
         Element element = (Element) aValue;
         // check for the tUser
         if (AeXmlUtil.getFirstSubElement(element) == null)
         {
            String userText = AeXmlUtil.getText(element);
            if (AeUtil.notNullOrEmpty(userText))
            {
               AeOrganizationalEntityDef def = new AeOrganizationalEntityDef(userText);
               return AeB4PIO.serialize2Element(def);
            }
         }
         else
         {
            boolean useExistingElement = AeXmlUtil.getElementType(element).equals(ORG_ENTITY_QNAME);

            // convert it to an org entity element
            AeOrganizationalEntityDef orgEntityDef = AeB4PIO.deserializeAsOrganizationalEntity(element);
            if (!orgEntityDef.isEmpty())
            {
               if (useExistingElement)
                  return element;
               else
                  return AeB4PIO.serialize2Element(orgEntityDef);
            }
            else
            {
               throw new AeException(AeMessages.getString("AePAExpressionResolver.EmptyOrgEntityError")); //$NON-NLS-1$
            }
         }
      }

      // if you get here, then the expression didn't produce a valid org entity
      return null;
   }

   /**
    * Converts a duration or deadline expression into Date
    * @param aDef
    * @throws AeBusinessProcessException
    */
   private AeSchemaDateTime convertToSchemaDateTime(AeAlarmDef aDef) throws AeBusinessProcessException
   {
      if (aDef.getForDef() != null)
      {
         AeExpressionDef def = AePARequestUtil.createExpressionDef(aDef.getForDef().getExpression(), aDef.getForDef().getExpressionLanguage(), getContext().getProcess().getBPELNamespace());
         AeSchemaDuration duration = getContext().executeDurationExpression(def, getFunctionContext(), getDelegatingFuncFactory());
         return new AeSchemaDateTime(duration.toDeadline());
      }
      else if (aDef.getUntilDef() != null)
      {
         AeExpressionDef def = AePARequestUtil.createExpressionDef(aDef.getUntilDef().getExpression(), aDef.getUntilDef().getExpressionLanguage(), getContext().getProcess().getBPELNamespace());
         Date date = getContext().executeDeadlineExpression(def, getFunctionContext(), getDelegatingFuncFactory());
         return new AeSchemaDateTime(date);
      }
      return null;
   }

   /**
    * Executes expression and returns result as an Element
    * @param aDef
    * @throws AeBusinessProcessException
    */
   private Object executeExpressionWithContext(AeExpressionDef aDef) throws AeBusinessProcessException
   {
      return getContext().execute(aDef, getFunctionContext(), getDelegatingFuncFactory());
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
    * @return the exception
    */
   protected AeBusinessProcessException getException()
   {
      return mException;
   }


   /**
    * @param aException the exception to set
    */
   protected void setException(AeBusinessProcessException aException)
   {
      mException = aException;
   }


   /**
    * @return the peopleAssignmentsDef
    */
   protected AePeopleAssignmentsDef getPeopleAssignmentsDef()
   {
      return mPeopleAssignmentsDef;
   }


   /**
    * @param aPeopleAssignmentsDef the peopleAssignmentsDef to set
    */
   protected void setPeopleAssignmentsDef(AePeopleAssignmentsDef aPeopleAssignmentsDef)
   {
      mPeopleAssignmentsDef = aPeopleAssignmentsDef;
   }

   /**
    * @return the delegatingFuncFactory
    */
   protected IAeFunctionFactory getDelegatingFuncFactory()
   {
      return mDelegatingFuncFactory;
   }

   /**
    * @param aDelegatingFuncFactory the delegatingFuncFactory to set
    */
   protected void setDelegatingFuncFactory(IAeFunctionFactory aDelegatingFuncFactory)
   {
      mDelegatingFuncFactory = aDelegatingFuncFactory;
   }

   /**
    * @return the functionContext
    */
   protected AePeopleActivityBasedHtFunctionContext getFunctionContext()
   {
      return mFunctionContext;
   }

   /**
    * @param aFunctionContext the functionContext to set
    */
   protected void setFunctionContext(AePeopleActivityBasedHtFunctionContext aFunctionContext)
   {
      mFunctionContext = aFunctionContext;
   }



}
