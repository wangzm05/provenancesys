//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/AeB4PGetActualOwnerFunction.java,v 1.2 2008/03/11 03:10:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function;

import org.activebpel.rt.b4p.impl.AePeopleActivityImpl;


/**
 * This function finds actual owner inside a people activity 
 */
public class AeB4PGetActualOwnerFunction extends AeAbstractB4PGetUserFunction
{
   public static String FUNCTION_NAME = "getActualOwner"; //$NON-NLS-1$

   /**
    * @see org.activebpel.rt.b4p.function.AeAbstractB4PGetUserFunction#getUser(java.lang.Object)
    */
   protected String getUser(Object aImpl)
   {
      return ((AePeopleActivityImpl) aImpl).getState().getActualOwner();
   }

}
