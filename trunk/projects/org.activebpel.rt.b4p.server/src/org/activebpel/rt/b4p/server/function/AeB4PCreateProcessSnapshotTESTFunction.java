//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/AeB4PCreateProcessSnapshotTESTFunction.java,v 1.1 2008/01/22 14:50:54 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.activebpel.rt.b4p.function.AeB4PProcessVariableCollectionSerializer;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessInternal;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;
import org.activebpel.rt.bpel.impl.visitors.AeInScopeVariableFindingVisitor;
import org.w3c.dom.Element;

/**
 * Test function that gets a snapshot of all the variables in a process.
 */
public class AeB4PCreateProcessSnapshotTESTFunction extends AeAbstractBpelFunction
{
   /** name of the function */
   public static final String FUNCTION_NAME = "createVariableSnapshot"; //$NON-NLS-1$

   /**
    * Ctor
    */
   public AeB4PCreateProcessSnapshotTESTFunction()
   {
      super(FUNCTION_NAME);
   }


   /**
    * Overrides method to take a snapshot of all the process variables except whose name starts with "aeb4p_".
    * Returns a aeb4p:processVariables element.
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {

      IAeBusinessProcessInternal process = aContext.getAbstractBpelObject().getProcess();
      AeInScopeVariableFindingVisitor visitor = new AeInScopeVariableFindingVisitor(Collections.EMPTY_SET);
      try
      {
         ((AeAbstractBpelObject)process).accept(visitor);
      }
      catch (AeBusinessProcessException ex)
      {
         throw new AeFunctionCallException(ex);
      }
      // Collect set of process variables except for variables that start with "aeb4p_" prefix.
      Collection vars = visitor.getVariables();
      Set varsToSerialize = new HashSet();
      for (Iterator it = vars.iterator(); it.hasNext();)
      {
         IAeVariable v = (IAeVariable) it.next();
         if (v.getName().startsWith("aeb4p_")) //$NON-NLS-1$
         {
            continue;
         }
         varsToSerialize.add(v);
      }
      // create serializer.
      AeB4PProcessVariableCollectionSerializer varCollectionSerializer = new AeB4PProcessVariableCollectionSerializer(process.getEngine().getTypeMapping());
      try
      {
         // element result is a <aeb4p:processVariables> element.
         Element result = varCollectionSerializer.serializeVariables( varsToSerialize );
         return result;
      }
      catch (AeBusinessProcessException ex)
      {
         throw new AeFunctionCallException(ex);
      }
   }

}
