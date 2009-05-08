//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/AeB4PGetLogicalPeopleGroupFunction.java,v 1.4 2008/02/29 04:16:41 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function;

import java.util.List;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.impl.lpg.AeLogicalPeopleGroupImpl;
import org.activebpel.rt.b4p.impl.lpg.AeLogicalPeopleGroupImplFinder;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.AeUsersDef;
import org.activebpel.rt.ht.def.io.AeHtIO;
import org.activebpel.rt.xml.def.AeXmlDefUtil;
import org.w3c.dom.Element;

/**
 * This function finds evaluated lpg given a lpg name 
 */
public class AeB4PGetLogicalPeopleGroupFunction extends AeAbstractBpelFunction
{
   public static String FUNCTION_NAME = "getLogicalPeopleGroup"; //$NON-NLS-1$
   
   /**
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs)
         throws AeFunctionCallException
   {
      try
      {
         if (aArgs.size() != 1)
         {
            throw new AeFunctionCallException(AeMessages.getString("AeB4PGetLogicalPeopleGroupFunction.INVALID_ARGS_COUNT")); //$NON-NLS-1$
         }
         String argName = (String) aArgs.get(0);
         QName lpgName = AeXmlDefUtil.parseQName(aContext.getAbstractBpelObject().getDefinition(), argName);
         return getLPGValue(aContext.getAbstractBpelObject(), lpgName);
      }
      catch (Throwable t)
      {
         AeException.logError(t);
         return null;
      }
   }

   /**
    * Finds the LPG and evaluates it.
    * @param aContext
    * @param lpgName
    * @throws AeFunctionCallException
    */
   protected Object getLPGValue(IAeBpelObject aBpelObject, QName lpgName) throws AeFunctionCallException
   {
      AeLogicalPeopleGroupImplFinder lpgFinder = new AeLogicalPeopleGroupImplFinder();
      try
      {
         AeLogicalPeopleGroupImpl impl = lpgFinder.find(aBpelObject, lpgName);
         Element elem = impl != null ? impl.evaluate(aBpelObject) : null;
         if (elem == null)
         {
            AeOrganizationalEntityDef def = new AeOrganizationalEntityDef();
            def.setUsers(new AeUsersDef());
            return AeHtIO.serialize2Element(def);
         }
         return elem;
      }
      catch (AeBusinessProcessException ex)
      {
         throw new AeFunctionCallException(ex.getLocalizedMessage());
      }
   }
}
