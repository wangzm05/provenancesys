//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/AeB4PTaskFaultVisitor.java,v 1.1 2008/02/16 22:29:49 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.visitors; 

import java.util.Iterator;
import java.util.Map;

import javax.wsdl.Operation;
import javax.wsdl.PortType;

import org.activebpel.rt.bpel.AeWSDLDefHelper;
import org.activebpel.rt.ht.def.AeTaskInterfaceDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.IAeContextWSDLProvider;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;

/**
 * Visits the task's interface def and adds all of the fault names that are
 * defined for the operation.
 */
public class AeB4PTaskFaultVisitor extends AeAbstractTraversingB4PDefVisitor
{
   /** used to resolve the operation */
   private IAeContextWSDLProvider mProvider;
   
   /**
    * Ctor
    * @param aContextWSDLProvider
    */
   public AeB4PTaskFaultVisitor(IAeContextWSDLProvider aContextWSDLProvider)
   {
      setProvider(aContextWSDLProvider);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskInterfaceDef)
    */
   public void visit(AeTaskInterfaceDef aDef)
   {
      // A task uses a request-response style interface or two one-way interfaces
      // together to form a request-response style interface. In the case of the 
      // former (a single request-response interface), it is possible to have 
      // faults on the task. In the case of the latter (two one-ways) it is not
      // possible to have faults.
      
      // The code below will only consider the primary port type, not the response
      // port type when inlining fault info. It's up to another validation visitor
      // to ensure that the task's interface has been constructed properly from
      // a single request-response or two one-ways.
      
      // Collect fault names
      AeBPELExtendedWSDLDef def = AeWSDLDefHelper.getWSDLDefinitionForPortType(getProvider(), aDef.getPortType());
      
      // lots of short returns like this since we could be dealing with an invalid
      // definition. Our job is only to inline what we can, not to validate.
      if (def == null)
         return;
      PortType portType = def.getPortType(aDef.getPortType());
      if (portType == null)
         return;
      Operation operation = AeWSDLDefHelper.getOperation(getProvider(), portType.getQName(), aDef.getOperation());
      if (operation == null)
         return;
      Map faultsMap = operation.getFaults();
      if (AeUtil.notNullOrEmpty(faultsMap))
      {
         for(Iterator iter=faultsMap.keySet().iterator(); iter.hasNext(); )
         {
            String faultName = (String) iter.next();
            aDef.addFault(faultName);
         }
      }
   }
   
   /**
    * @return the provider
    */
   protected IAeContextWSDLProvider getProvider()
   {
      return mProvider;
   }

   /**
    * @param aProvider the provider to set
    */
   protected void setProvider(IAeContextWSDLProvider aProvider)
   {
      mProvider = aProvider;
   }
}
 