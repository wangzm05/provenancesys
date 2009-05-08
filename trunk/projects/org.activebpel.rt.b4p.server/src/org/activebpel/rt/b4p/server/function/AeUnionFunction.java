//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/AeUnionFunction.java,v 1.1 2008/03/03 01:37:45 mford Exp $
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
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.w3c.dom.Element;

/**
 * Performs a union on two org entities, even if the result is a mix of users and groups 
 */
public class AeUnionFunction extends AeAbstractBpelFunction
{
   /** name of the function */
   public static final String FUNCTION_NAME = "union"; //$NON-NLS-1$

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs)
         throws AeFunctionCallException
   {
      Element one = (Element) aArgs.get(0);
      Element two = (Element) aArgs.get(1);
      
      AeOrganizationalEntityDef result = union(one, two);
      
      return AeB4PIO.serialize2Element(result); 
   }

   /**
    * Performs a union of both elements into a single org entity
    * @param one
    * @param two
    */
   protected AeOrganizationalEntityDef union(Element one, Element two)
   {
      AeOrganizationalEntityDef result = new AeOrganizationalEntityDef();

      try
      {
         if (one != null)
         {
            AeOrganizationalEntityDef orgOne = AeB4PIO.deserializeAsOrganizationalEntity(one);
            result.add(orgOne);
         }
         if (two != null)
         {
            AeOrganizationalEntityDef orgTwo = AeB4PIO.deserializeAsOrganizationalEntity(two);
            result.add(orgTwo);
         }
      }
      catch (AeException e)
      {
         AeException.logError(e);
      }
      return result;
   }
}
 