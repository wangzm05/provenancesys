// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/AeHtApiTask.java,v 1.5 2008/02/17 21:51:26 mford Exp $
// ///////////////////////////////////////////////////////////////////////////
// PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc. Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
// ///////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.api;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;

import org.activebpel.rt.ht.def.AeGroupDef;
import org.activebpel.rt.ht.def.AeGroupsDef;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.AeUserDef;
import org.activebpel.rt.ht.def.AeUsersDef;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;

/**
 * A Data object that contains the information about a wsht api:tTaskAbstract type
 * and api:tTask type.
 */
public class AeHtApiTask implements Serializable
{
   /** Process Id. */
   private long mProcessId;
   /** Task Ref URI. */
   private String mId;
   /** The task type TASK, NOTIFICATION. */
   private String mTaskType = "TASK"; //$NON-NLS-1$
   /** Task namespace. */
   private QName mQName;
   /** Task status */
   private String mStatus;
   /** Task priority. */
   private int mPriority;
   // fixme (PJ) change references of  AeUserDef and AeOrganizationalEntityDef to use xml nodes.
   /** Task initiator - applicable only for wsht api:tTask type. */
   private AeUserDef mTaskInitiator;
   /** Stake holders - applicable only for wsht api:tTask type. */
   private AeOrganizationalEntityDef mTaskStakeholders;
   /** Potential owners - applicable only for wsht api:tTask type. */
   private AeOrganizationalEntityDef mPotentialOwners;
   /** Excluded Owners - we overload this field for notifications to track who has removed the notification from their inbox */
   private AeOrganizationalEntityDef mExcludedOwners;
   /** businessAdministrators - applicable only for wsht api:tTask type. */
   private AeOrganizationalEntityDef mBusinessAdministrators;
   /** Task actualOwner - applicable only for wsht api:tTask type.  */
   private AeUserDef mActualOwner;
   /** notificationRecipients - applicable only for wsht api:tTask type. */
   private AeOrganizationalEntityDef mNotificationRecipients;
   /** Creation time. */
   private AeSchemaDateTime mCreatedOn;
   /** Created by - applicable only for wsht api:tTask type.  */
   private String mCreatedBy;
   /** Activation time. */
   private AeSchemaDateTime mActivationTime;
   /** Expire time. */
   private AeSchemaDateTime mExpirationTime;
   /** Can the task be skipped. */
   private boolean mIsSkipable;
   /** Start by date time */
   private AeSchemaDateTime mStartBy;
   /** Complete by */
   private AeSchemaDateTime mCompleteBy;
   /** Task presentation name */
   private String mPresentationName;
   /** Task presentation subject */
   private String mPresentationSubject;
   /** Flag indicates that the task has rendering methods */
   private boolean mRenderingMethodExists;
   /** Has output flag. */
   private boolean mHasOutput;
   /** Has fault flag. */
   private boolean mHasFault;
   /** Flag indicates that the task has attachments */
   private boolean mHasAttachments;
   /** Flag indicates that the task has comments */
   private boolean mHasComments;
   /** Last escalation time if task has been escalated */
   private AeSchemaDateTime mEscalationTime;
   /** Task primary search by meta data  - applicable only for wsht api:tTask type.  */
   private String mPrimarySearchBy;
   /** Last modified time */
   private AeSchemaDateTime mModifiedTime;
   /** User who last modified task. */
   private String mModifiedBy;

   /**
    * Default c'tor.
    */
   public AeHtApiTask()
   {
   }

   /**
    * @return the processId
    */
   public long getProcessId()
   {
      return mProcessId;
   }

   /**
    * @param aProcessId the processId to set
    */
   public void setProcessId(long aProcessId)
   {
      mProcessId = aProcessId;
   }

   /**
    * @return the id
    */
   public String getId()
   {
      return mId;
   }

   /**
    * @param aId the id to set
    */
   public void setId(String aId)
   {
      mId = aId;
   }

   /**
    * @return the taskType
    */
   public String getTaskType()
   {
      return mTaskType;
   }

   /**
    * @param aTaskType the taskType to set
    */
   public void setTaskType(String aTaskType)
   {
      mTaskType = aTaskType;
   }

   /**
    * @return the qName
    */
   public QName getName()
   {
      return mQName;
   }

   /**
    * @param aName the qName to set
    */
   public void setName(QName aName)
   {
      mQName = aName;
   }

   /**
    * @return the status
    */
   public String getStatus()
   {
      return mStatus;
   }

   /**
    * @param aStatus the status to set
    */
   public void setStatus(String aStatus)
   {
      mStatus = aStatus;
   }

   /**
    * @return the priority
    */
   public int getPriority()
   {
      return mPriority;
   }

   /**
    * @param aPriority the priority to set
    */
   public void setPriority(int aPriority)
   {
      mPriority = aPriority;
   }

   /**
    * @return the taskInitiator
    */
   public AeUserDef getTaskInitiator()
   {
      return mTaskInitiator;
   }

   /**
    * Returns task initiator.
    */
   public String getTaskInitiatorAsString()
   {
      if (getTaskInitiator() != null)
      {
         return getTaskInitiator().getValue();
      }
      else
      {
         return null;
      }
   }

   /**
    * @param aTaskInitiator the taskInitiator to set
    */
   public void setTaskInitiator(AeUserDef aTaskInitiator)
   {
      mTaskInitiator = aTaskInitiator;
   }

   /**
    * Sets the task initiator
    * @param aTaskInitiator
    */
   public void setTaskInitiator(String aTaskInitiator)
   {
      AeUserDef user = new AeUserDef();
      user.setValue( aTaskInitiator );
      setTaskInitiator( user );
   }

   /**
    * @return the taskStakeholders
    */
   public AeOrganizationalEntityDef getTaskStakeholders()
   {
      return mTaskStakeholders;
   }

   /**
    * @param aTaskStakeholders the taskStakeholders to set
    */
   public void setTaskStakeholders(AeOrganizationalEntityDef aTaskStakeholders)
   {
      mTaskStakeholders = aTaskStakeholders;
   }

   /**
    * Sets collection of task stake holders.
    * @param aTaskStakeholders set of usernames or groupnames
    * @param aIsGroup true if set is a collection of groups.
    */
   public void setTaskStakeholders(Set aTaskStakeholders, boolean aIsGroup)
   {
      if (aTaskStakeholders.size() > 0)
      {
         setTaskStakeholders( createOrganizationalEntityDef(aTaskStakeholders, aIsGroup) );
      }
   }

   /**
    * @return the potentialOwners
    */
   public AeOrganizationalEntityDef getPotentialOwners()
   {
      return mPotentialOwners;
   }

   /**
    * @param aPotentialOwners the potentialOwners to set
    */
   public void setPotentialOwners(AeOrganizationalEntityDef aPotentialOwners)
   {
      mPotentialOwners = aPotentialOwners;
   }

   /**
    * Sets collection of potential owners.
    * @param aPotentialOwners set of usernames or groupnames
    * @param aIsGroup true if set is a collection of groups.
    */
   public void setPotentialOwners(Set aPotentialOwners, boolean aIsGroup)
   {
      if (aPotentialOwners.size() > 0)
      {
         setPotentialOwners( createOrganizationalEntityDef(aPotentialOwners, aIsGroup) );
      }
   }

   /**
    * Returns true if there are potential owners.
    */
   public boolean isHasPotentialOwners()
   {
      return getPotentialOwners() != null;
   }

   /**
    * @return the businessAdministrators
    */
   public AeOrganizationalEntityDef getBusinessAdministrators()
   {
      return mBusinessAdministrators;
   }

   /**
    * @param aBusinessAdministrators the businessAdministrators to set
    */
   public void setBusinessAdministrators(AeOrganizationalEntityDef aBusinessAdministrators)
   {
      mBusinessAdministrators = aBusinessAdministrators;
   }

   /**
    * Sets collection of task business administrators.
    * @param aBusinessAdministrators set of usernames or groupnames
    * @param aIsGroup true if set is a collection of groups.
    */
   public void setBusinessAdministrators(Set aBusinessAdministrators, boolean aIsGroup)
   {
      if (aBusinessAdministrators.size() > 0)
      {
         setBusinessAdministrators( createOrganizationalEntityDef(aBusinessAdministrators, aIsGroup) );
      }
   }

   /**
    * @return the actualOwner
    */
   public AeUserDef getActualOwner()
   {
      return mActualOwner;
   }

   /**
    * @return the actualOwner
    */
   public String getActualOwnerAsString()
   {
      if (getActualOwner() != null)
      {
         return getActualOwner().getValue();
      }
      else
      {
         return null;
      }
   }

   /**
    * @param aActualOwner the actualOwner to set
    */
   public void setActualOwner(AeUserDef aActualOwner)
   {
      mActualOwner = aActualOwner;
   }

   /**
    * Sets the actual owner.
    * @param aActualOwner
    */
   public void setActualOwner(String aActualOwner)
   {
      AeUserDef user = new AeUserDef();
      user.setValue( aActualOwner );
      setActualOwner( user );
   }

   /**
    * @return the notificationRecipients
    */
   public AeOrganizationalEntityDef getNotificationRecipients()
   {
      return mNotificationRecipients;
   }

   /**
    * @param aNotificationRecipients the notificationRecipients to set
    */
   public void setNotificationRecipients(AeOrganizationalEntityDef aNotificationRecipients)
   {
      mNotificationRecipients = aNotificationRecipients;
   }

   /**
    * Sets collection of task notification recipients.
    * @param aNotificationRecipients set of usernames or groupnames
    * @param aIsGroup true if set is a collection of groups.
    */
   public void setNotificationRecipients(Set aNotificationRecipients, boolean aIsGroup)
   {
      if (aNotificationRecipients.size() > 0)
      {
         setNotificationRecipients( createOrganizationalEntityDef(aNotificationRecipients, aIsGroup) );
      }
   }

   /**
    * @return the createdOn
    */
   public AeSchemaDateTime getCreatedOn()
   {
      return mCreatedOn;
   }

   /**
    * @param aCreatedOn the createdOn to set
    */
   public void setCreatedOn(AeSchemaDateTime aCreatedOn)
   {
      mCreatedOn = aCreatedOn;
   }

   /**
    * @return the createdBy
    */
   public String getCreatedBy()
   {
      return mCreatedBy;
   }

   /**
    * @param aCreatedBy the createdBy to set
    */
   public void setCreatedBy(String aCreatedBy)
   {
      mCreatedBy = aCreatedBy;
   }

   /**
    * @return the activationTime
    */
   public AeSchemaDateTime getActivationTime()
   {
      return mActivationTime;
   }

   /**
    * @param aActivationTime the activationTime to set
    */
   public void setActivationTime(AeSchemaDateTime aActivationTime)
   {
      mActivationTime = aActivationTime;
   }

   /**
    * @return the expirationTime
    */
   public AeSchemaDateTime getExpirationTime()
   {
      return mExpirationTime;
   }

   /**
    * @param aExpirationTime the expirationTime to set
    */
   public void setExpirationTime(AeSchemaDateTime aExpirationTime)
   {
      mExpirationTime = aExpirationTime;
   }

   /**
    * @return the isSkipable
    */
   public boolean isSkipable()
   {
      return mIsSkipable;
   }

   /**
    * @param aIsSkipable the isSkipable to set
    */
   public void setSkipable(boolean aIsSkipable)
   {
      mIsSkipable = aIsSkipable;
   }

   /**
    * @return the startByExists
    */
   public boolean isStartByExists()
   {
      return getStartBy() != null;
   }

   /**
    * Sets the start by date
    * @param aStartBy
    */
   public void setStartBy(AeSchemaDateTime aStartBy)
   {
      mStartBy = aStartBy;
   }

   /**
    * Returns start by date time if it exist or null otherwise.
    */
   public AeSchemaDateTime getStartBy()
   {
      return mStartBy;
   }

   /**
    * @return the completeByExists
    */
   public boolean isCompleteByExists()
   {
      return getCompleteBy() != null;
   }

   /**
    * Sets the complete by date.
    * @param aCompleteBy
    */
   public void setCompleteBy(AeSchemaDateTime aCompleteBy)
   {
      mCompleteBy = aCompleteBy;
   }

   /**
    * Returns complete by date or null if not defined.
    */
   public AeSchemaDateTime getCompleteBy()
   {
      return mCompleteBy;
   }

   /**
    * @return the presentationName
    */
   public String getPresentationName()
   {
      return mPresentationName;
   }

   /**
    * @param aPresentationName the presentationName to set
    */
   public void setPresentationName(String aPresentationName)
   {
      mPresentationName = aPresentationName;
   }

   /**
    * @return the presentationSubject
    */
   public String getPresentationSubject()
   {
      return mPresentationSubject;
   }

   /**
    * @param aPresentationSubject the presentationSubject to set
    */
   public void setPresentationSubject(String aPresentationSubject)
   {
      mPresentationSubject = aPresentationSubject;
   }

   /**
    * @return the renderingMethodExists
    */
   public boolean isRenderingMethodExists()
   {
      return mRenderingMethodExists;
   }

   /**
    * @param aRenderingMethodExists the renderingMethodExists to set
    */
   public void setRenderingMethodExists(boolean aRenderingMethodExists)
   {
      mRenderingMethodExists = aRenderingMethodExists;
   }

   /**
    * @return the hasOutput
    */
   public boolean isHasOutput()
   {
      return mHasOutput;
   }

   /**
    * @param aHasOutput the hasOutput to set
    */
   public void setHasOutput(boolean aHasOutput)
   {
      mHasOutput = aHasOutput;
   }

   /**
    * @return the hasFault
    */
   public boolean isHasFault()
   {
      return mHasFault;
   }

   /**
    * @param aHasFault the hasFault to set
    */
   public void setHasFault(boolean aHasFault)
   {
      mHasFault = aHasFault;
   }

   /**
    * @return the hasAttachments
    */
   public boolean isHasAttachments()
   {
      return mHasAttachments;
   }

   /**
    * @param aHasAttachments the hasAttachments to set
    */
   public void setHasAttachments(boolean aHasAttachments)
   {
      mHasAttachments = aHasAttachments;
   }

   /**
    * @return the hasComments
    */
   public boolean isHasComments()
   {
      return mHasComments;
   }

   /**
    * @param aHasComments the hasComments to set
    */
   public void setHasComments(boolean aHasComments)
   {
      mHasComments = aHasComments;
   }

   /**
    * @return the escalated
    */
   public boolean isEscalated()
   {
      return getEscalationTime() != null;
   }

   /**
    * Sets the escalation time
    * @param aEscalationTime
    */
   public void setEscalationTime(AeSchemaDateTime aEscalationTime)
   {
      mEscalationTime = aEscalationTime;
   }

   /**
    * Returns last escalated time or null if not defined.
    */
   public AeSchemaDateTime getEscalationTime()
   {
      return mEscalationTime;
   }

   /**
    * @return the primarySearchBy
    */
   public String getPrimarySearchBy()
   {
      return mPrimarySearchBy;
   }

   /**
    * @param aPrimarySearchBy the primarySearchBy to set
    */
   public void setPrimarySearchBy(String aPrimarySearchBy)
   {
      mPrimarySearchBy = aPrimarySearchBy;
   }

   /**
    * @return the modifiedTime
    */
   public AeSchemaDateTime getModifiedTime()
   {
      return mModifiedTime;
   }

   /**
    * @param aModifiedTime the modifiedTime to set
    */
   public void setModifiedTime(AeSchemaDateTime aModifiedTime)
   {
      mModifiedTime = aModifiedTime;
   }

   /**
    * @return the modifiedBy
    */
   public String getModifiedBy()
   {
      return mModifiedBy;
   }

   /**
    * @param aModifiedBy the modifiedBy to set
    */
   public void setModifiedBy(String aModifiedBy)
   {
      mModifiedBy = aModifiedBy;
   }

   /**
    * Convenience method to create a <code>AeOrganizationalEntityDef</code> given
    * a collection of either usernames or groupnames.
    * @param aEntities
    * @param aIsGroup true if the entity collection is groupnames.
    * @return AeOrganizationalEntityDef
    */
   protected AeOrganizationalEntityDef createOrganizationalEntityDef(Set aEntities, boolean aIsGroup)
   {
      AeOrganizationalEntityDef orgEntityDef = new AeOrganizationalEntityDef();
      if (aIsGroup)
      {
         AeGroupsDef groupsDef = new AeGroupsDef();
         Iterator it = aEntities.iterator();
         while (it.hasNext())
         {
            AeGroupDef groupDef = new AeGroupDef();
            groupDef.setValue( (String) it.next() );
            groupsDef.addGroup( groupDef );
         }
         orgEntityDef.setGroups(groupsDef);
      }
      else
      {
         AeUsersDef usersDef = new AeUsersDef();
         Iterator it = aEntities.iterator();
         while (it.hasNext())
         {
            AeUserDef userDef = new AeUserDef();
            userDef.setValue( (String) it.next() );
            usersDef.addUser( userDef );
         }
         orgEntityDef.setUsers(usersDef);
      }
      return orgEntityDef;
   }

   /**
    * @return the excludedOwners
    */
   public AeOrganizationalEntityDef getExcludedOwners()
   {
      return mExcludedOwners;
   }

   /**
    * @param aExcludedOwners the excludedOwners to set
    */
   public void setExcludedOwners(AeOrganizationalEntityDef aExcludedOwners)
   {
      mExcludedOwners = aExcludedOwners;
   }
}
