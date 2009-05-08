//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/ht/AeHTSetBaseFunction.java,v 1.4 2008/02/27 20:56:43 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function.ht;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;
import org.activebpel.rt.ht.IAeWSHTConstants;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.AeUserDef;
import org.activebpel.rt.ht.def.AeUsersDef;
import org.activebpel.rt.ht.def.io.AeHtIO;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * An Implementation of HT XPath extension function union
 */
public abstract class AeHTSetBaseFunction extends AeAbstractBpelFunction
{
   protected abstract void doSetOperation(Set aUserSetOne, Set aUserSetTwo);
   
   /**
    * C'tor
    * @param aFunctionName
    */
   protected AeHTSetBaseFunction(String aFunctionName)
   {
      super(aFunctionName);
   }
   
   /**
    * C'tor
    */
   protected AeHTSetBaseFunction()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {
      if (aArgs.size() != 2)
      {
         throw new AeFunctionCallException(AeMessages.getString("AeHTSetBaseFunction.IncorrectNumberOfArgs")); //$NON-NLS-1$
      }
      Set userSetOne = convertOrgEntityToSet(((Element) aArgs.get(0)));
      Set userSetTwo = convertOrgEntityToSet(((Element) aArgs.get(1)));
      doSetOperation(userSetOne, userSetTwo);
      return convertSetToOrgEntity(userSetOne);
   }

   /**
    * Returns a Set of users in the OrganizationalEntity Element 
    * @param aOrgEntity
    */
   private Set convertOrgEntityToSet(Element aOrgEntity)
   {
      Map nsMap = Collections.singletonMap("htd", IAeWSHTConstants.WSHT_NAMESPACE); //$NON-NLS-1$
      try
      {
         List users = AeXPathUtil.selectNodes(aOrgEntity, "//htd:user", nsMap); //$NON-NLS-1$
         Set userSet = new HashSet();
         for(Iterator iter=users.iterator(); iter.hasNext(); )
         {
            userSet.add(AeXmlUtil.getText((Element)iter.next()));
         }
         return userSet;
      }
      catch (AeException ex)
      {
         throw new RuntimeException(ex.getLocalizedMessage());
      }
   }
   
   /**
    * Returns an OrganizationalEntity Element of the users in aSet 
    * @param aSet
    */
   private Element convertSetToOrgEntity(Set aSet)
   {
      AeOrganizationalEntityDef orgEntity = new AeOrganizationalEntityDef();
      orgEntity.setUsers(new AeUsersDef());
      for(Iterator iter=aSet.iterator(); iter.hasNext(); )
      {
         AeUserDef def = new AeUserDef();
         def.setValue((String)iter.next());
         orgEntity.getUsers().addUser(def);
         
      }
      return AeHtIO.serialize2Element(orgEntity);
   }
      
}
