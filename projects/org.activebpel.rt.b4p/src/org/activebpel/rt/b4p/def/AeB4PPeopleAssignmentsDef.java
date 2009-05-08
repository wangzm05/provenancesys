//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeB4PPeopleAssignmentsDef.java,v 1.3 2008/01/15 18:16:00 mford Exp $$
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
 * Impl. for 'peopleAssigments' element Def.
 */
public class AeB4PPeopleAssignmentsDef extends AeB4PBaseDef implements IAeB4PContextParent
{
   /** The context which provides ability to transition to enclosing containers */
   private IAeB4PContext mContext;
   
   /** 'taskInitiator' element */
   private AeProcessInitiatorDef mProcessInitiator;
   /** 'potentialOwners' element */
   private AeProcessStakeholdersDef mProcessStakeholders;
   /** 'businessAdministrators' element */
   private AeBusinessAdministratorsDef mBusinessAdministrators;

   /**
    * @return the businessAdministrators
    */
   public AeBusinessAdministratorsDef getBusinessAdministrators()
   {
      return mBusinessAdministrators;
   }

   /**
    * @param aBusinessAdministrators the businessAdministrators to set
    */
   public void setBusinessAdministrators(AeBusinessAdministratorsDef aBusinessAdministrators)
   {
      mBusinessAdministrators = aBusinessAdministrators;
      assignParent(aBusinessAdministrators);
   }

   /**
    * @return the processInitiator
    */
   public AeProcessInitiatorDef getProcessInitiator()
   {
      return mProcessInitiator;
   }

   /**
    * @param aProcessInitiator the processInitiator to set
    */
   public void setProcessInitiator(AeProcessInitiatorDef aProcessInitiator)
   {
      mProcessInitiator = aProcessInitiator;
      assignParent(aProcessInitiator);
   }

   /**
    * @return the processStakeholders
    */
   public AeProcessStakeholdersDef getProcessStakeholders()
   {
      return mProcessStakeholders;
   }

   /**
    * @param aProcessStakeholders the processStakeholders to set
    */
   public void setProcessStakeholders(AeProcessStakeholdersDef aProcessStakeholders)
   {
      mProcessStakeholders = aProcessStakeholders;
      assignParent(aProcessStakeholders);
   }

   /**
    * @see org.activebpel.rt.b4p.def.AeB4PBaseDef#accept(org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor)
    */
   public void accept(IAeB4PDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      AeB4PPeopleAssignmentsDef paDef = (AeB4PPeopleAssignmentsDef)super.clone();
      if (getProcessInitiator() != null)
         paDef.setProcessInitiator((AeProcessInitiatorDef)getProcessInitiator().clone());
      if (getProcessStakeholders() != null)
         paDef.setProcessStakeholders((AeProcessStakeholdersDef)getProcessStakeholders().clone());
      if (getBusinessAdministrators() != null)
         paDef.setBusinessAdministrators((AeBusinessAdministratorsDef)getBusinessAdministrators().clone());

      return paDef;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (!(aOther instanceof AeB4PPeopleAssignmentsDef))
         return false;

      AeB4PPeopleAssignmentsDef otherDef = (AeB4PPeopleAssignmentsDef)aOther;
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(otherDef.getProcessInitiator(), getProcessInitiator());
      same &= AeUtil.compareObjects(otherDef.getProcessStakeholders(), getProcessStakeholders());
      same &= AeUtil.compareObjects(otherDef.getBusinessAdministrators(), getBusinessAdministrators());

      return same;
   }

   /**
    * Sets the context which provides ability to transition to enclosing containers
    * @param aContext
    */
   public void setContext(IAeB4PContext aContext)
   {
      mContext = aContext;
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.IAeB4PContextParent#getContext()
    */
   public IAeB4PContext getContext()
   {
      return mContext;
   }
}
