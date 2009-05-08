//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/AeB4PGetProcessRoleFunction.java,v 1.1 2008/02/13 07:44:43 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function; 

import java.util.List;

import org.activebpel.rt.b4p.def.io.AeB4PIO;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.AeUserDef;

public abstract class AeB4PGetProcessRoleFunction extends AeAbstractBpelFunction
{

   public Object call(IAeFunctionExecutionContext aContext, List aArgs)
         throws AeFunctionCallException
   {
      AeOrganizationalEntityDef org = getProcessInitiatorAsOrganizationEntity(aContext);
      return AeB4PIO.serialize2Element(org);
   }

   protected AeOrganizationalEntityDef getProcessInitiatorAsOrganizationEntity(
         IAeFunctionExecutionContext aContext)
   {
      AeUserDef userDef = getProcessInitiator(aContext);
      AeOrganizationalEntityDef org = new AeOrganizationalEntityDef(userDef);
      return org;
   }

   protected AeUserDef getProcessInitiator(IAeFunctionExecutionContext aContext)
   {
      AeUserDef userDef = new AeUserDef();
      String processInitiator = aContext.getAbstractBpelObject().getProcess().getProcessInitiator();
      userDef.setValue(processInitiator);
      return userDef;
   }
}
 