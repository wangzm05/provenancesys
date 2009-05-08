//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeScheduledActionsDef.java,v 1.5 2007/12/26 17:34:03 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def;

import org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Impl. for 'scheduledActions' element Def.
 */
public class AeScheduledActionsDef extends AeB4PBaseDef
{
   /** 'deferActivation' element */
   private AeDeferActivationDef mDeferActivation;

   /** 'expiration' element */
   private AeExpirationDef mExpiration;
   
   /**
    * @return the deferActivation
    */
   public AeDeferActivationDef getDeferActivation()
   {
      return mDeferActivation;
   }

   /**
    * @param aDeferActivation the deferActivation to set
    */
   public void setDeferActivation(AeDeferActivationDef aDeferActivation)
   {
      mDeferActivation = aDeferActivation;
      assignParent(aDeferActivation);
   }

   /**
    * @return the expiration
    */
   public AeExpirationDef getExpiration()
   {
      return mExpiration;
   }

   /**
    * @param aExpiration the expiration to set
    */
   public void setExpiration(AeExpirationDef aExpiration)
   {
      mExpiration = aExpiration;
      assignParent(aExpiration);
   }

   /**
    * @see org.activebpel.rt.b4p.def.AeB4PBaseDef#accept(org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor)
    */
   public void accept(IAeB4PDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @see org.activebpel.rt.xml.def.AeBaseXmlDef#clone()
    */
   public Object clone()
   {
      AeScheduledActionsDef copy = (AeScheduledActionsDef) super.clone();
      
      if (getDeferActivation() != null)
         copy.setDeferActivation((AeDeferActivationDef) getDeferActivation().clone());
      if (getExpiration() != null)
         copy.setExpiration((AeExpirationDef) getExpiration().clone());
      
      return copy;
   }

   /**
    * @see org.activebpel.rt.b4p.def.AeB4PBaseDef#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (super.equals(aOther))
      {
         AeScheduledActionsDef other = (AeScheduledActionsDef) aOther;
         return AeUtil.compareObjects(getDeferActivation(), other.getDeferActivation()) &&
            AeUtil.compareObjects(getExpiration(), other.getExpiration());
         
      }
      return false;
   }
}
