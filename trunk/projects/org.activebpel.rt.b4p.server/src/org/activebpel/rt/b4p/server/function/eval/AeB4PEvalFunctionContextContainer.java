//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/eval/AeB4PEvalFunctionContextContainer.java,v 1.1 2008/02/11 17:09:24 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function.eval;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.function.AeB4PFunctionContext;
import org.activebpel.rt.bpel.function.AeFunctionContextContainer;
import org.activebpel.rt.bpel.function.IAeFunctionContext;
import org.activebpel.rt.bpel.function.IAeFunctionContextLocator;

/**
 * Container for custom function contexts.
 */
public class AeB4PEvalFunctionContextContainer extends AeFunctionContextContainer
{
   /** WSB4P function context. */
   private static final IAeFunctionContext WSB4P_FUN_CTX = new AeB4PFunctionContext();
   
   /**
    * Ctor.
    * @param aLocator
    */
   public AeB4PEvalFunctionContextContainer(IAeFunctionContextLocator aLocator)
   {
      super(aLocator);
   }
   
   /** 
    * Overrides method to restric namespace to WSHT and WSB4P namespace.
    * @see org.activebpel.rt.bpel.function.AeFunctionContextContainer#getFunctionContextNamespaces()
    */
   public Collection getFunctionContextNamespaces()
   {
      List list = new LinkedList();
      list.add(IAeB4PConstants.B4P_NAMESPACE);
      return list;
   }
   
   /**
    * Overrides method to restrict to WSHT and WSB4P namespace.
    * @see org.activebpel.rt.bpel.function.AeFunctionContextContainer#getFunctionContext(java.lang.String)
    */
   public IAeFunctionContext getFunctionContext(String aNamespace)
   {
      IAeFunctionContext ctx = null;
      if (IAeB4PConstants.B4P_NAMESPACE.equals(aNamespace))
      {
         ctx = WSB4P_FUN_CTX;
      }  
      return ctx;
   }   
}
