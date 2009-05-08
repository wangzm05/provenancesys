//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AePeopleAssignmentsDef.java,v 1.9 2008/02/27 20:45:26 EWittmann Exp $$
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

/**
 * Impl. for 'peopleAssigments' element Def.
 */
public class AePeopleAssignmentsDef extends AeHtBaseDef implements IAePotentialOwnersParent
{
   /** 'potentialOwners' element */
   private AePotentialOwnersDef mPotentialOwners;
   /** 'excludedOwners' element */
   private AeExcludedOwnersDef mExcludedOwners;
   /** 'taskInitiator' element */
   private AeTaskInitiatorDef mTaskInitiator;
   /** 'potentialOwners' element */
   private AeTaskStakeHoldersDef mTaskStakeholders;
   /** 'businessAdministrators' element */
   private AeBusinessAdministratorsDef mBusinessAdministrators;
   /** 'recipients' element */
   private AeRecipientsDef mRecipients;

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
    * @return the excludedOwners
    */
   public AeExcludedOwnersDef getExcludedOwners()
   {
      return mExcludedOwners;
   }

   /**
    * @param aExcludedOwners the excludedOwners to set
    */
   public void setExcludedOwners(AeExcludedOwnersDef aExcludedOwners)
   {
      mExcludedOwners = aExcludedOwners;
      assignParent(aExcludedOwners);
   }

   /**
    * @return the potentialOwners
    */
   public AePotentialOwnersDef getPotentialOwners()
   {
      return mPotentialOwners;
   }

   /**
    * @param aPotentialOwners the potentialOwners to set
    */
   public void setPotentialOwners(AePotentialOwnersDef aPotentialOwners)
   {
      mPotentialOwners = aPotentialOwners;
      assignParent(aPotentialOwners);
   }

   /**
    * @return the recipients
    */
   public AeRecipientsDef getRecipients()
   {
      return mRecipients;
   }

   /**
    * @param aRecipients the recipients to set
    */
   public void setRecipients(AeRecipientsDef aRecipients)
   {
      mRecipients = aRecipients;
      assignParent(aRecipients);
   }

   /**
    * @return the taskInitiator
    */
   public AeTaskInitiatorDef getTaskInitiator()
   {
      return mTaskInitiator;
   }

   /**
    * @param aTaskInitiator the taskInitiator to set
    */
   public void setTaskInitiator(AeTaskInitiatorDef aTaskInitiator)
   {
      mTaskInitiator = aTaskInitiator;
      assignParent(aTaskInitiator);
   }

   /**
    * @return the taskStakeholders
    */
   public AeTaskStakeHoldersDef getTaskStakeholders()
   {
      return mTaskStakeholders;
   }

   /**
    * @param aTaskStakeholders the taskStakeholders to set
    */
   public void setTaskStakeholders(AeTaskStakeHoldersDef aTaskStakeholders)
   {
      mTaskStakeholders = aTaskStakeholders;
      assignParent(aTaskStakeholders);
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
      AePeopleAssignmentsDef paDef = (AePeopleAssignmentsDef)super.clone();
      if (getPotentialOwners() != null)
         paDef.setPotentialOwners((AePotentialOwnersDef)getPotentialOwners().clone());
      if (getExcludedOwners() != null)
         paDef.setExcludedOwners((AeExcludedOwnersDef)getExcludedOwners().clone());
      if (getTaskInitiator() != null)
         paDef.setTaskInitiator((AeTaskInitiatorDef)getTaskInitiator().clone());
      if (getTaskStakeholders() != null)
         paDef.setTaskStakeholders((AeTaskStakeHoldersDef)getTaskStakeholders().clone());
      if (getBusinessAdministrators() != null)
         paDef.setBusinessAdministrators((AeBusinessAdministratorsDef)getBusinessAdministrators().clone());
      if (getRecipients() != null)
         paDef.setRecipients((AeRecipientsDef)getRecipients().clone());

      return paDef;
   }
   
   /**
    * Merge the people assignments from the second peopleAssignment into the first peopleAssignment.
    * If the first peopleAssignment is null then a peopleAssignment will be created.
    * 
    * @param aOverridingPeopleAssignment
    */
   public AePeopleAssignmentsDef merge(AePeopleAssignmentsDef aOverridingPeopleAssignment)
   {
      AePeopleAssignmentsDef peopleAssignment = (AePeopleAssignmentsDef) this.clone();
    
      // override recipients with those specified in the local notification
      if (aOverridingPeopleAssignment != null)
      {
         if (aOverridingPeopleAssignment.getRecipients() != null)
         {
            peopleAssignment.setRecipients((AeRecipientsDef) aOverridingPeopleAssignment.getRecipients().clone());
         }
         if (aOverridingPeopleAssignment.getBusinessAdministrators() != null)
         {
            peopleAssignment.setBusinessAdministrators((AeBusinessAdministratorsDef) aOverridingPeopleAssignment.getBusinessAdministrators().clone());
         }
         if (aOverridingPeopleAssignment.getTaskInitiator() != null)
         {
            peopleAssignment.setTaskInitiator((AeTaskInitiatorDef) aOverridingPeopleAssignment.getTaskInitiator().clone());
         }
         if (aOverridingPeopleAssignment.getTaskStakeholders() != null)
         {
            peopleAssignment.setTaskStakeholders((AeTaskStakeHoldersDef) aOverridingPeopleAssignment.getTaskStakeholders().clone());
         }
         if (aOverridingPeopleAssignment.getExcludedOwners() != null)
         {
            peopleAssignment.setExcludedOwners((AeExcludedOwnersDef) aOverridingPeopleAssignment.getExcludedOwners().clone());
         }
         if (aOverridingPeopleAssignment.getPotentialOwners() != null)
         {
            peopleAssignment.setPotentialOwners((AePotentialOwnersDef) aOverridingPeopleAssignment.getPotentialOwners().clone());
         }
      }
      
      return peopleAssignment;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (!(aOther instanceof AePeopleAssignmentsDef))
         return false;

      AePeopleAssignmentsDef otherDef = (AePeopleAssignmentsDef)aOther;
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(otherDef.getPotentialOwners(), getPotentialOwners());
      same &= AeUtil.compareObjects(otherDef.getExcludedOwners(), getExcludedOwners());
      same &= AeUtil.compareObjects(otherDef.getTaskInitiator(), getTaskInitiator());
      same &= AeUtil.compareObjects(otherDef.getTaskStakeholders(), getTaskStakeholders());
      same &= AeUtil.compareObjects(otherDef.getBusinessAdministrators(), getBusinessAdministrators());
      same &= AeUtil.compareObjects(otherDef.getRecipients(), getRecipients());

      return same;
   }
}