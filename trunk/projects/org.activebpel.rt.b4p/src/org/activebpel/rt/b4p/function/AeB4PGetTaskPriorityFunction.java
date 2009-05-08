//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/AeB4PGetTaskPriorityFunction.java,v 1.2 2008/03/11 03:10:26 mford Exp $
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
 * This function finds priority of task inside a people activity 
 */
public class AeB4PGetTaskPriorityFunction extends AeAbstractB4PFunction
{
   public static String FUNCTION_NAME = "getTaskPriority"; //$NON-NLS-1$

   /**
    * @see org.activebpel.rt.b4p.function.AeAbstractB4PFunction#getValueFromImpl(java.lang.Object)
    */
   protected Object getValueFromImpl(Object aImpl)
   {
      int priority = ((AePeopleActivityImpl) aImpl).getState().getPriority();
      return new Integer(priority); 
   }
}
