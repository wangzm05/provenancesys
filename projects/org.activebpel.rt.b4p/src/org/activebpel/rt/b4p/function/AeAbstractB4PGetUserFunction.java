//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/AeAbstractB4PGetUserFunction.java,v 1.2 2007/12/20 15:54:18 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function;

import org.activebpel.rt.ht.def.AeUserDef;
import org.activebpel.rt.ht.def.io.AeHtIO;
import org.w3c.dom.Element;

/**
 * Base class for getActualOwner and getTaskInitiator functions
 */
public abstract class AeAbstractB4PGetUserFunction extends AeAbstractB4PFunction
{
   /**
    * Returns user attached with the call() of this function 
    */
   protected abstract String getUser(Object aImpl);
   

   /**
    * @see org.activebpel.rt.b4p.function.AeAbstractB4PFunction#getValueFromImpl(java.lang.Object)
    */
   protected Object getValueFromImpl(Object aImpl)
   {
      String user = getUser(aImpl);
      AeUserDef def = new AeUserDef();
      def.setValue(user);
      Element elem = AeHtIO.serialize2Element(def); 
      return elem; 
   }
}
