//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/subprocess/dispatchers/AeDeregisterCoordination.java,v 1.1 2008/03/28 01:46:19 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.bpel.server.coord.subprocess.dispatchers; 

import org.activebpel.rt.bpel.impl.IAeBusinessProcessInternal;
import org.activebpel.rt.bpel.impl.IAeProcessManager;
import org.activebpel.wsio.IAeMessageAcknowledgeCallback;

/**
 * Deregisters the activity from the coordination. This happens whenever the
 * subprocess completes.
 */
public class AeDeregisterCoordination extends AeDurableSpCoordinationDispatcher
{
   /**
    * Ctor
    * @param aProcessManager
    * @param aProcessId
    * @param aJournalId
    * @param aCallback
    * @param aCallbackJournalId
    * @param aLocationPath
    * @param aCooridinationId
    */
   public AeDeregisterCoordination(IAeProcessManager aProcessManager,
         long aProcessId, long aJournalId,
         IAeMessageAcknowledgeCallback aCallback, long aCallbackJournalId, String aLocationPath,
         String aCooridinationId)
   {
      super(aProcessManager, aProcessId, aJournalId, aCallback, aCallbackJournalId, aLocationPath, aCooridinationId);
   }

   /**
    * @see org.activebpel.rt.bpel.server.coord.subprocess.AeDurableSpCoordinationDispatcher#dispatchBehavior(org.activebpel.rt.bpel.impl.IAeBusinessProcessInternal)
    */
   protected void dispatchBehavior(IAeBusinessProcessInternal aProcess)
   {
      aProcess.deregisterCoordination(getLocationPath(), getCoordinationId());
   }

   /**
    * @see org.activebpel.rt.bpel.server.coord.subprocess.AeDurableSpCoordinationDispatcher#journalDispatchBehavior()
    */
   protected long journalDispatchBehavior()
   {
      return getProcessManager().journalDeregisterCoordination(getProcessId(), getLocationPath(), getCoordinationId());
   }
} 