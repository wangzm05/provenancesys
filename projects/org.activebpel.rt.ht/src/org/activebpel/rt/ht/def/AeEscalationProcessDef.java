//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeEscalationProcessDef.java,v 1.4 2008/03/14 20:45:32 EWittmann Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.IAeNamedDef;

/**
 * AE extension to HT that allows the user to invoke a process when the deadline fires.
 */
public class AeEscalationProcessDef extends AeHtBaseDef implements IAeNamedDef, IAeConditionParentDef
{
   /** 'condition' element */
   private AeEscalationProcessConditionDef mCondition;
   /** 'name' attribute */
   private String mName;
   /** 'service' that we're invoking */
   private String mService;
   /** optional expression def that produces data to pass to the process */
   private AeProcessDataExpressionDef mExpressionDef;
   
   /**
    * C'tor.
    */
   public AeEscalationProcessDef()
   {
   }
   
   /**
    * @see org.activebpel.rt.ht.def.IAeConditionParentDef#getConditionDef()
    */
   public AeConditionDef getConditionDef()
   {
      return mCondition;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeConditionParentDef#setConditionDef(org.activebpel.rt.ht.def.AeConditionDef)
    */
   public void setConditionDef(AeConditionDef aCondition)
   {
      if (aCondition instanceof AeEscalationProcessConditionDef)
         mCondition = (AeEscalationProcessConditionDef) aCondition;
      else if (aCondition != null)
         mCondition = new AeEscalationProcessConditionDef(aCondition);
      else mCondition = null;
      assignParent(mCondition);
   }

   /**
    * @return value of the 'name' attribute.
    */
   public String getName()
   {
      return mName;
   }

   /**
    * @param aName value of the 'name' attribute to set.
    */
   public void setName(String aName)
   {
      mName = aName;
   }

   /**
    * @see org.activebpel.rt.ht.def.AeHtBaseDef#accept(org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor)
    */
   public void accept(IAeHtDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
   
   /**
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      AeEscalationProcessDef def = (AeEscalationProcessDef)super.clone();
      
      if (getConditionDef() != null)
         def.setConditionDef((AeConditionDef) getConditionDef().clone());
      if (getExpressionDef() != null)
         def.setExpressionDef((AeProcessDataExpressionDef) getExpressionDef().clone());
         
      return def;
   }
   
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeEscalationProcessDef))
         return false;
      
      AeEscalationProcessDef otherDef = (AeEscalationProcessDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.getConditionDef(), getConditionDef());
      same &= AeUtil.compareObjects(otherDef.getExpressionDef(), getExpressionDef());
      same &= AeUtil.compareObjects(otherDef.getService(), getService());
      same &= AeUtil.compareObjects(otherDef.getName(), getName());
      
      return same;
   }

   /**
    * @return the service
    */
   public String getService()
   {
      return mService;
   }

   /**
    * @param aService the service to set
    */
   public void setService(String aService)
   {
      mService = aService;
   }

   /**
    * @return the expressionDef
    */
   public AeProcessDataExpressionDef getExpressionDef()
   {
      return mExpressionDef;
   }

   /**
    * @param aExpressionDef the expressionDef to set
    */
   public void setExpressionDef(AeProcessDataExpressionDef aExpressionDef)
   {
      mExpressionDef = aExpressionDef;
      assignParent(aExpressionDef);
   }
}