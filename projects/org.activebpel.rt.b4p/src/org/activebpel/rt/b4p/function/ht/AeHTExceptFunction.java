//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/ht/AeHTExceptFunction.java,v 1.2 2008/01/25 21:42:37 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function.ht;

import java.util.Set;

import org.activebpel.rt.ht.IAeHtFunctionNames;

/**
 * An Implementation of HT XPath extension function except
 */
public class AeHTExceptFunction extends AeHTSetBaseFunction
{
   /**
    * C'tor
    */
   public AeHTExceptFunction()
   {
      super(IAeHtFunctionNames.EXCEPT_FUNCTION_NAME);
   }

   /**
    * @see org.activebpel.rt.b4p.function.ht.AeHTSetBaseFunction#doSetOperation(java.util.Set, java.util.Set)
    */
   protected void doSetOperation(Set aUserSetOne, Set aUserSetTwo)
   {
      aUserSetOne.removeAll(aUserSetTwo);
   }
}
