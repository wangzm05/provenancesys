//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/recovery/AeRecoveryB4PManager.java,v 1.1 2008/03/11 03:07:53 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.recovery; 

import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.b4p.impl.engine.AeB4PQueueObject;
import org.activebpel.rt.b4p.impl.engine.AeNotification;
import org.activebpel.rt.b4p.impl.engine.AeTask;
import org.activebpel.rt.b4p.server.IAeServerB4PManager;
import org.activebpel.rt.b4p.server.storage.IAeTaskStorage;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.AeManagerAdapter;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext;
import org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker;
import org.activebpel.rt.bpel.server.engine.recovery.AeRecoveryConflictingRequestException;
import org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveredItemsSet;
import org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveryAwareManager;
import org.activebpel.rt.bpel.server.engine.recovery.journal.AeInvokeTransmittedJournalEntry;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.xml.schema.AeSchemaDuration;
import org.activebpel.wsio.invoke.IAeTransmission;
import org.w3c.dom.Element;

/**
 * Recovery version of the b4p manager. The manager will keep track of each 
 * people activity's task or notification that has been created/cancelled and 
 * send those signals to the engine after the process recovery is complete.
 * 
 * This manager will delegate calls not related to the execution of a people activity
 * to the underlying b4p server manager. This is necessary since this manager may
 * be accessed during the execution of the b4p impl processes (state machine, et al)
 */
public class AeRecoveryB4PManager extends AeManagerAdapter implements IAeServerB4PManager, IAeRecoveryAwareManager
{
   /** manager that we delegate non-PA calls to */
   private IAeServerB4PManager mDelegate;
   /** set of items we've recovered */
   private IAeRecoveredItemsSet mRecoveredItemsSet;
   
   /** list of invoke transmitted journals */
   private List mInvokeTransmittedEntries;
   
   /**
    * Ctor.
    * @param aConfig
    */
   public AeRecoveryB4PManager(IAeServerB4PManager aDelegate)
   {
      setDelegate(aDelegate);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveryAwareManager#setInvokeTransmittedEntries(java.util.List)
    */
   public void setInvokeTransmittedEntries(List aInvokeTransmittedEntries)
   {
      mInvokeTransmittedEntries = aInvokeTransmittedEntries;
   }

   /**
    * Removes the next invoke transmitted journal entry for the given location
    * id. If such a journal entry exists, then returns the associated
    * transmission id; otherwise, returns
    * {@link IAeTransmissionTracker#NULL_TRANSREC_ID}.
    *
    * @param aLocationId
    */
   protected long removeInvokeTransmittedEntry(int aLocationId) throws AeBusinessProcessException
   {
      // fixme (MF-b4p-recovery) replace the list of journal entries with an interface since this code is duplicated in q manager
      for (Iterator i = getInvokeTransmittedEntries().iterator(); i.hasNext(); )
      {
         AeInvokeTransmittedJournalEntry entry = (AeInvokeTransmittedJournalEntry) i.next();
         if (entry.getLocationId() == aLocationId)
         {
            // Remove this entry.
            i.remove();

            // Return its transmission id.
            return entry.getTransmissionId();
         }
      }

      return IAeTransmissionTracker.NULL_TRANSREC_ID;
   }


   /**
    * @see org.activebpel.rt.b4p.impl.engine.IAeB4PManager#taskResponseReceived(long, int, long)
    */
   public void taskResponseReceived(long aProcessId, int aPeopleActivityId,
         long aTransmissionId)
   {
      getRecoveredItemsSet().removeRecoveredItem(new AeTaskResponseReceivedItem(aProcessId, aPeopleActivityId, aTransmissionId));
   }

   /**
    * @see org.activebpel.rt.b4p.impl.engine.IAeB4PManager#cancelTask(long, int, long)
    */
   public void cancelTask(long aProcessId, int aPeopleActivityId, long aTransmissionId)
         throws AeBusinessProcessException
   {
      try
      {
         getRecoveredItemsSet().addRecoveredItem(new AeCancelTaskItem(aProcessId, aPeopleActivityId, aTransmissionId));
      }
      catch (AeRecoveryConflictingRequestException e)
      {
      }
   }

   /**
    * @see org.activebpel.rt.b4p.impl.engine.IAeB4PManager#executeNotification(org.activebpel.rt.b4p.impl.engine.AeNotification, org.activebpel.rt.attachment.IAeAttachmentContainer, org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext)
    */
   public void executeNotification(AeNotification aNotification, IAeAttachmentContainer aAttachmentContainer, IAeActivityLifeCycleContext aContext)
         throws AeBusinessProcessException
   {
      assignTransmissionId(aNotification, aContext.getTransmission());

      try
      {
         getRecoveredItemsSet().addRecoveredItem(new AeRecoveredPeopleActivityNotificationItem(aNotification, aAttachmentContainer, aContext));
      }
      catch (AeRecoveryConflictingRequestException e)
      {
      }
   }

   /**
    * @see org.activebpel.rt.b4p.impl.engine.IAeB4PManager#executeTask(org.activebpel.rt.b4p.impl.engine.AeTask, org.activebpel.rt.attachment.IAeAttachmentContainer, org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext, org.activebpel.wsio.invoke.IAeTransmission)
    */
   public void executeTask(AeTask aTask,
         IAeAttachmentContainer aAttachmentContainer,
         IAeActivityLifeCycleContext aContext)
         throws AeBusinessProcessException
   {
      assignTransmissionId(aTask, aContext.getTransmission());
      try
      {
         getRecoveredItemsSet().addRecoveredItem(new AeRecoveredPeopleActivityTaskItem(aTask, aAttachmentContainer, aContext));
      }
      catch (AeRecoveryConflictingRequestException e)
      {
      }
   }

   /**
    * @see org.activebpel.rt.b4p.server.IAeServerB4PManager#getTaskFinalizationDuration()
    */
   public AeSchemaDuration getTaskFinalizationDuration()
   {
      return getDelegate().getTaskFinalizationDuration();
   }

   /**
    * @see org.activebpel.rt.b4p.server.IAeServerB4PManager#getTaskStorage()
    */
   public IAeTaskStorage getTaskStorage()
   {
      return getDelegate().getTaskStorage();
   }

   /**
    * @see org.activebpel.rt.b4p.impl.engine.IAeB4PManager#evaluateLogicalPeopleGroup(org.activebpel.rt.bpel.impl.IAeBpelObject, org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef)
    */
   public Element evaluateLogicalPeopleGroup(IAeBpelObject aBpelObject,
         AeLogicalPeopleGroupDef aLPGDef)
   {
      return getDelegate().evaluateLogicalPeopleGroup(aBpelObject, aLPGDef);
   }

   /**
    * Assigns a transmission id from a previously recorded journal item or does
    * nothing if there is no previously recorded journal item.
    * @param aQueueObject
    * @param aTransmission
    * @throws AeBusinessProcessException
    */
   protected void assignTransmissionId(AeB4PQueueObject aQueueObject,
         IAeTransmission aTransmission) throws AeBusinessProcessException
   {
      long transmissionId = removeInvokeTransmittedEntry(aQueueObject.getLocationId());
      if (transmissionId != IAeTransmissionTracker.NULL_TRANSREC_ID)
      {
         // Restore the invoke's transmission.
         aTransmission.setTransmissionId(transmissionId);
      }
   }

   /**
    * @return the delegate
    */
   protected IAeServerB4PManager getDelegate()
   {
      return mDelegate;
   }

   /**
    * @param aDelegate the delegate to set
    */
   protected void setDelegate(IAeServerB4PManager aDelegate)
   {
      mDelegate = aDelegate;
   }

   /**
    * @return the recoveredItemsSet
    */
   public IAeRecoveredItemsSet getRecoveredItemsSet()
   {
      return mRecoveredItemsSet;
   }

   /**
    * @param aRecoveredItemsSet the recoveredItemsSet to set
    */
   public void setRecoveredItemsSet(IAeRecoveredItemsSet aRecoveredItemsSet)
   {
      mRecoveredItemsSet = aRecoveredItemsSet;
   }

   /**
    * @return the invokeTransmittedEntries
    */
   protected List getInvokeTransmittedEntries()
   {
      return mInvokeTransmittedEntries;
   }
}
 