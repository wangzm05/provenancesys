//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/recovery/AeRecoveredPeopleActivityTaskItem.java,v 1.1 2008/03/11 03:07:53 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.recovery; 

import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.b4p.IAeProcessTaskConstants;
import org.activebpel.rt.b4p.impl.engine.AeTask;
import org.activebpel.rt.b4p.impl.engine.IAeB4PManager;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext;

/**
 * Recovered execution of a people activity that created a task. 
 */
public class AeRecoveredPeopleActivityTaskItem extends AeRecoveredPeopleActivityExecution
{
   /** task data */
   private AeTask mTask;
   
   /**
    * Ctor
    * @param aTask
    * @param aAttachmentContainer
    * @param aContext
    */
   protected AeRecoveredPeopleActivityTaskItem(AeTask aTask, IAeAttachmentContainer aAttachmentContainer, IAeActivityLifeCycleContext aContext)
   {
      super(aTask, aAttachmentContainer, aContext);
      setTask(aTask);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#queueItem(org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal)
    */
   public void queueItem(IAeBusinessProcessEngineInternal aTargetEngine)
         throws AeBusinessProcessException
   {
      IAeB4PManager b4pManager = (IAeB4PManager) aTargetEngine.getCustomManager(IAeProcessTaskConstants.B4P_MANAGER_KEY);
      b4pManager.executeTask(getTask(), getAttachmentContainer(), getContext());
   }

   /**
    * @return the task
    */
   protected AeTask getTask()
   {
      return mTask;
   }

   /**
    * @param aTask the task to set
    */
   protected void setTask(AeTask aTask)
   {
      mTask = aTask;
   }
}
 