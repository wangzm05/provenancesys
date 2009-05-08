//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/recovery/AeCancelTaskItem.java,v 1.1 2008/03/11 03:07:53 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.recovery; 

import org.activebpel.rt.b4p.IAeProcessTaskConstants;
import org.activebpel.rt.b4p.impl.engine.IAeB4PManager;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.server.engine.recovery.recovered.AeRecoveredLocationIdItem;

/**
 * Recovered item for sending the cancel signal to a b4p task lifecycle process 
 */
public class AeCancelTaskItem extends AeRecoveredLocationIdItem 
{
   /** transmission id */
   private long mTransmissionId; 
   
   /**
    * Ctor
    * @param aProcessId
    * @param aLocationId
    * @param aTransmissionId
    */
   public AeCancelTaskItem(long aProcessId, int aLocationId, long aTransmissionId)
   {
      super(aProcessId, aLocationId);
      setTransmissionId(aTransmissionId);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#queueItem(org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal)
    */
   public void queueItem(IAeBusinessProcessEngineInternal aTargetEngine)
         throws AeBusinessProcessException
   {
      IAeB4PManager b4pManager = (IAeB4PManager) aTargetEngine.getCustomManager(IAeProcessTaskConstants.B4P_MANAGER_KEY);
      b4pManager.cancelTask(getProcessId(), getLocationId(), getTransmissionId());
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#isRemoval()
    */
   public boolean isRemoval()
   {
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
 