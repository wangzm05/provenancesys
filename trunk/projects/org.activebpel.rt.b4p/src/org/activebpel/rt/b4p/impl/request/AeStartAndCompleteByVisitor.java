//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/request/AeStartAndCompleteByVisitor.java,v 1.2 2008/02/17 21:36:03 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor;
import org.activebpel.rt.b4p.function.ht.AeHTExtensionFunctionFactory;
import org.activebpel.rt.b4p.function.ht.AePeopleActivityBasedHtFunctionContext;
import org.activebpel.rt.b4p.impl.task.data.AeInitialState;
import org.activebpel.rt.b4p.impl.task.data.AePLBaseRequest;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.function.IAeFunctionFactory;
import org.activebpel.rt.bpel.impl.activity.AeDelegatingFunctionFactory;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext;
import org.activebpel.rt.ht.def.AeAbstractDeadlineDef;
import org.activebpel.rt.ht.def.AeCompletionDeadlineDef;
import org.activebpel.rt.ht.def.AeDeadlinesDef;
import org.activebpel.rt.ht.def.AeStartDeadlineDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;
import org.activebpel.rt.xml.schema.AeSchemaDuration;

/**
 * This is a visitor that traverses all start and completion deadlines in a taskdef and set the 
 * earliest start and complete dates in InitialState 
 */
public class AeStartAndCompleteByVisitor extends AeAbstractTraversingB4PDefVisitor
{
   /** lifecycle context */
   private IAeActivityLifeCycleContext mContext;
   /** initial state object */
   private AePLBaseRequest mRequest;
   /** start deadlines */
   private List mStartDeadlineDates = new ArrayList();
   /** complete deadlines */
   private List mCompleteDeadlineDates = new ArrayList();
   /** business exception object */
   private AeBusinessProcessException mException;
   /** function factory */
   private IAeFunctionFactory mDelegatingFuncFactory;
   /** function context */
   private AePeopleActivityBasedHtFunctionContext mFunctionContext;
   
   
   /**
    * Ctor
    * @param aContext
    * @param aRequest
    */
   public AeStartAndCompleteByVisitor(IAeActivityLifeCycleContext aContext, AePLBaseRequest aRequest)
   {
      mContext = aContext;
      mRequest = aRequest;
      mDelegatingFuncFactory = new AeDelegatingFunctionFactory(new AeHTExtensionFunctionFactory(), getContext().getContextFunctionFactory());
      mFunctionContext = new AePeopleActivityBasedHtFunctionContext(mRequest);
   }

   /**
    * @param aDef
    * @throws AeBusinessProcessException
    */
   public void setStartAndCompleteBy(AeTaskDef aDef) throws AeBusinessProcessException
   {
      AeDeadlinesDef deadlinesDef = aDef.getDeadlines();
      if (deadlinesDef == null)
         return;
      
      visit(deadlinesDef);
      if (getException() != null)
         throw getException();
      
      // Sort start and completion deadlines if there are more than one of them
      if (getStartDeadlineDates().size() > 1)
         Collections.sort(getStartDeadlineDates());

      if (getCompleteDeadlineDates().size() > 1)
         Collections.sort(getCompleteDeadlineDates());

      
      // Set the earliest startdealine as startBy
      if (getStartDeadlineDates().size() > 0)
         getInitialState().setStartBy(new AeSchemaDateTime((Date) getStartDeadlineDates().get(0)));

      // Set the earliest completion deadline as completeBy
      if (getCompleteDeadlineDates().size() > 0)
         getInitialState().setCompleteBy(new AeSchemaDateTime((Date) getCompleteDeadlineDates().get(0)));

   }
   
   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeCompletionDeadlineDef)
    */
   public void visit(AeCompletionDeadlineDef aDef)
   {
      Date date = convertToDateTime(aDef);
      if (date != null)
         getCompleteDeadlineDates().add(date);
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeStartDeadlineDef)
    */
   public void visit(AeStartDeadlineDef aDef)
   {
      Date date = convertToDateTime(aDef);
      if (date != null)
         getStartDeadlineDates().add(date);
   }

   /**
    * Converts a duration or deadline expression into Date from abstract deadline def 
    * @param aDef
    * @throws AeBusinessProcessException 
    */
   private Date convertToDateTime(AeAbstractDeadlineDef aDef)
   {
      try
      {
         if ( (aDef.getFor() != null) && (AeUtil.notNullOrEmpty(aDef.getFor().getExpression())) )
         {
            AeExpressionDef def = AePARequestUtil.createExpressionDef(aDef.getFor().getExpression(), aDef.getFor().getExpressionLanguage(), getContext().getProcess().getBPELNamespace());
            AeSchemaDuration duration = getContext().executeDurationExpression(def, getFunctionContext(), getDelegatingFuncFactory());
            return duration.toDeadline();
         }
         else if ( (aDef.getUntil() != null) && (AeUtil.notNullOrEmpty(aDef.getUntil().getExpression())) )
         {
            AeExpressionDef def = AePARequestUtil.createExpressionDef(aDef.getUntil().getExpression(), aDef.getUntil().getExpressionLanguage(), getContext().getProcess().getBPELNamespace());
            Date date = getContext().executeDeadlineExpression(def, getFunctionContext(), getDelegatingFuncFactory());
            return date;
         }
      }
      catch(AeBusinessProcessException ex)
      {
         setException(ex);
      }
      return null;
   }   
   
   /**
    * @return the initialState
    */
   protected AeInitialState getInitialState()
   {
      return mRequest.getInitialState();
   }

   /**
    * @return the startDeadlineDates
    */
   protected List getStartDeadlineDates()
   {
      return mStartDeadlineDates;
   }

   /**
    * @param aStartDeadlineDates the startDeadlineDates to set
    */
   protected void setStartDeadlineDates(List aStartDeadlineDates)
   {
      mStartDeadlineDates = aStartDeadlineDates;
   }

   /**
    * @return the completeDeadlineDates
    */
   protected List getCompleteDeadlineDates()
   {
      return mCompleteDeadlineDates;
   }

   /**
    * @param aCompleteDeadlineDates the completeDeadlineDates to set
    */
   protected void setCompleteDeadlineDates(List aCompleteDeadlineDates)
   {
      mCompleteDeadlineDates = aCompleteDeadlineDates;
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
    * @return the delegatingFuncFactory
    */
   protected IAeFunctionFactory getDelegatingFuncFactory()
   {
      return mDelegatingFuncFactory;
   }

   /**
    * @return the functionContext
    */
   protected AePeopleActivityBasedHtFunctionContext getFunctionContext()
   {
      return mFunctionContext;
   }
   
   
}
