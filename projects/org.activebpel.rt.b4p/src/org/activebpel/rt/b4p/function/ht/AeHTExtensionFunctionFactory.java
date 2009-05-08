//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/ht/AeHTExtensionFunctionFactory.java,v 1.4.4.2 2008/04/14 21:25:30 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function.ht;

import java.util.LinkedHashSet;
import java.util.Set;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.function.AeB4PGetLogicalPeopleGroupFunction;
import org.activebpel.rt.bpel.function.AeUnresolvableException;
import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.function.IAeFunctionFactory;
import org.activebpel.rt.ht.IAeHtFunctionNames;
import org.activebpel.rt.ht.IAeWSHTConstants;

/**
 * This implementations returns WS-HT specific functions.
 */
public class AeHTExtensionFunctionFactory implements IAeFunctionFactory
{
   // common error messages
   public static final String NO_FUNCTION_FOUND_ERROR = AeMessages.getString("AeHTExtensionFunctionFactory.0"); //$NON-NLS-1$

   /** List of namespaces this factory supports */
   private Set mHTExtensionFunctionNamespace;
   
   /*
    * initialize the namespace list 
    */
   {
      mHTExtensionFunctionNamespace = new LinkedHashSet();
      mHTExtensionFunctionNamespace.add(IAeWSHTConstants.WSHT_NAMESPACE);
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionFactory#getFunction(java.lang.String, java.lang.String)
    */
   public IAeFunction getFunction(String aNamespace, String aFunctionName) throws AeUnresolvableException
   {
      if (mHTExtensionFunctionNamespace.contains(aNamespace))
      {
         if (IAeHtFunctionNames.ACTUAL_OWNER_FUNCTION_NAME.equals(aFunctionName))
         {
            return new AeGetActualOwnerFunction();
         }
         else if (IAeHtFunctionNames.POTENTIAL_OWNERS_FUNCTION_NAME.equals(aFunctionName))
         {
            return new AeGetPotentialOwnersFunction();
         }
         else if (IAeHtFunctionNames.BUSINESS_ADMINISTRATORS_FUNCTION_NAME.equals(aFunctionName))
         {
            return new AeGetBusinessAdministratorsFunction();
         }
         else if (IAeHtFunctionNames.TASK_STAKEHOLDERS_FUNCTION_NAME.equals(aFunctionName))
         {
            return new AeGetTaskStakeholdersFunction();
         }
         else if (IAeHtFunctionNames.TASK_INITIATOR_FUNCTION_NAME.equals(aFunctionName))
         {
            return new AeGetTaskInitiatorFunction();
         }
         else if (IAeHtFunctionNames.EXCLUDED_OWNERS_FUNCTION_NAME.equals(aFunctionName))
         {
            return new AeGetExcludedOwnersFunction();
         }
         else if (IAeHtFunctionNames.TASK_PRIORITY_FUNCTION_NAME.equals(aFunctionName))
         {
            return new AeGetTaskPriorityFunction();
         }
         else if (IAeHtFunctionNames.INPUT_FUNCTION_NAME.equals(aFunctionName))
         {
            return new AeGetInputFunction();
         }
         else if (IAeHtFunctionNames.UNION_FUNCTION_NAME.equals(aFunctionName))
         {
            return new AeHTUnionFunction();
         }
         else if (IAeHtFunctionNames.INTERSECT_FUNCTION_NAME.equals(aFunctionName))
         {
            return new AeHTIntersectFunction();
         }
         else if (IAeHtFunctionNames.EXCEPT_FUNCTION_NAME.equals(aFunctionName))
         {
            return new AeHTExceptFunction();
         }
         else if (IAeHtFunctionNames.LOGICAL_PEOPLE_GROUP_FUNCTION_NAME.equals(aFunctionName))
         {
            return new AeB4PGetLogicalPeopleGroupFunction();
         }
      }
      throw new AeUnresolvableException(AeMessages.format(NO_FUNCTION_FOUND_ERROR, aFunctionName));
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionFactory#getFunctionContextNamespaceList()
    */
   public Set getFunctionContextNamespaceList()
   {
      return mHTExtensionFunctionNamespace;
   }

}
