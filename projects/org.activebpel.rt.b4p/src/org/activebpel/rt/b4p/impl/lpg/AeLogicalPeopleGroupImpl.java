//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/lpg/AeLogicalPeopleGroupImpl.java,v 1.3 2008/02/26 01:54:13 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.lpg; 

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.IAeProcessTaskConstants;
import org.activebpel.rt.b4p.impl.engine.IAeB4PManager;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * Impl object for LPG's. Provides means of evaluating the people query in order
 * to produce an organizational entity. This impl will also save its state to
 * the process state.
 */
public class AeLogicalPeopleGroupImpl
{
   /** Def for the LPG */
   private AeLogicalPeopleGroupDef mDef;
   /** Value for the LPG - only set if the LPG has been assigned */
   private Element mAssignedValue;
   
   /**
    * Ctor
    * 
    * @param aDef
    */
   public AeLogicalPeopleGroupImpl(AeLogicalPeopleGroupDef aDef)
   {
      setDef(aDef);
   }
   
   /**
    * Evaluates the people query using the provider unless this LPG is in the 
    * 'assigned' state at which point it will return the previously assigned
    * value.
    * 
    * @param aBpelObject
    */
   public Element evaluate(IAeBpelObject aBpelObject) throws AeBusinessProcessException
   {
      if (getAssignedValue() == null)
      {
         IAeB4PManager b4pManager = getB4PManager(aBpelObject);
         Element newValue = b4pManager.evaluateLogicalPeopleGroup(aBpelObject, getDef());
         if (newValue != null)
         {
            newValue = AeXmlUtil.cloneElement(newValue);
         }
         return newValue;
      }
      else
      {
         return getAssignedValue();
      }
   }

   /**
    * Gets the b4p manager.
    * 
    * @param aBpelObject
    */
   protected IAeB4PManager getB4PManager(IAeBpelObject aBpelObject) throws AeBusinessProcessException
   {
      IAeBusinessProcessEngineInternal engine = aBpelObject.getProcess().getEngine();
      IAeB4PManager b4pManager = (IAeB4PManager) engine.getCustomManager(IAeProcessTaskConstants.B4P_MANAGER_KEY);
      if (b4pManager == null)
         throw new AeBusinessProcessException(AeMessages.getString("AeLogicalPeopleGroupImpl.EvalFailedError")); //$NON-NLS-1$
      return b4pManager;
   }
   
   /**
    * Assigns the element to the LPG
    * @param aElement
    */
   public void assign(Element aElement)
   {
      setAssignedValue(AeXmlUtil.cloneElement(aElement));
   }

   /**
    * Gets the current value. This will not cause the LPG to evaluate its query.
    * @return the value
    */
   public Element getAssignedValue()
   {
      return mAssignedValue;
   }

   /**
    * @param aValue the value to set
    */
   public void setAssignedValue(Element aValue)
   {
      mAssignedValue = aValue;
   }

   /**
    * @return the def
    */
   public AeLogicalPeopleGroupDef getDef()
   {
      return mDef;
   }

   /**
    * @param aDef the def to set
    */
   public void setDef(AeLogicalPeopleGroupDef aDef)
   {
      mDef = aDef;
   }
}
 