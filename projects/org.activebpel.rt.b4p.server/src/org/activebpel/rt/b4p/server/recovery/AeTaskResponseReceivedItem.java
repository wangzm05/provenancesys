//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/recovery/AeTaskResponseReceivedItem.java,v 1.1 2008/03/11 03:07:53 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.recovery; 

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.server.engine.recovery.recovered.AeRecoveredLocationIdItem;

/**
 * Recovered response data for a task. This class is used a key into the recovered
 * items set to optionally remove a previously recovered people activity execution.
 * 
 */
public class AeTaskResponseReceivedItem extends AeRecoveredLocationIdItem
{
   /** transmission id */
   private long mTransmissionId;
   
   /**
    * Ctor
    * @param aProcessId
    * @param aLocationId
    * @param aTransmissionId
    */
   public AeTaskResponseReceivedItem(long aProcessId, int aLocationId, long aTransmissionId)
   {
      super(aProcessId, aLocationId);
      setTransmissionId(aTransmissionId);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#isRemoval()
    */
   public boolean isRemoval()
   {
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#queueItem(org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal)
    */
   public void queueItem(IAeBusinessProcessEngineInternal aTargetEngine)
         throws AeBusinessProcessException
   {
      // side effect of implementing the interface. This object is only used as
      // a key to lookup and remove a previously recovered task execution
      throw new UnsupportedOperationException();
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.AeRecoveredLocationIdItem#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if( super.equals(aOther) )
      {
         AeRecoveredPeopleActivityTaskItem execTask = (AeRecoveredPeopleActivityTaskItem) aOther;
         return execTask.getContext().getTransmission().getTransmissionId() == getTransmissionId();
      }
      return false;
   }

   /**
    * @return the transmissionId
    */
   public long getTransmissionId()
   {
      return mTransmissionId;
   }

   /**
    * @param aTransmissionId the transmissionId to set
    */
   public void setTransmissionId(long aTransmissionId)
   {
      mTransmissionId = aTransmissionId;
   }
}
 