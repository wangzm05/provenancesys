//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/eval/AeB4PEvalFunctionFactory.java,v 1.1 2008/02/11 17:09:24 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function.eval;

import java.util.LinkedHashSet;
import java.util.Set;

import org.activebpel.rt.bpel.function.AeUnresolvableException;
import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.function.IAeFunctionFactory;

/**
 * Implements the function factory used when evaluating HT expressions.
 * This implementations returns WS-HT specific functions.
 */
public class AeB4PEvalFunctionFactory implements IAeFunctionFactory
{
   /** function context container instance. */
   private AeB4PEvalFunctionContextContainer mContainer;

   /**
    * Ctor.
    * @param aContainer
    */
   public AeB4PEvalFunctionFactory(AeB4PEvalFunctionContextContainer aContainer)
   {
      mContainer = aContainer;
   }

   /**
    * @return AeB4PEvalFunctionContextContainer function container instance.
    */
   protected AeB4PEvalFunctionContextContainer getFunctionContextContainer()
   {
      return mContainer;
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionFactory#getFunction(java.lang.String, java.lang.String)
    */
   public IAeFunction getFunction(String aNamespace, String aFunctionName) throws AeUnresolvableException
   {
      return getFunctionContextContainer().getFunctionContext(aNamespace).getFunction(aFunctionName);
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionFactory#getFunctionContextNamespaceList()
    */
   public Set getFunctionContextNamespaceList()
   {
      return new LinkedHashSet(getFunctionContextContainer().getFunctionContextNamespaces());
   }

}
