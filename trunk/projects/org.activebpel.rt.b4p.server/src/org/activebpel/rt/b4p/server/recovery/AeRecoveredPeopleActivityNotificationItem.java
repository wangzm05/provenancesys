//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/recovery/AeRecoveredPeopleActivityNotificationItem.java,v 1.1 2008/03/11 03:07:53 mford Exp $
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
import org.activebpel.rt.b4p.impl.engine.AeNotification;
import org.activebpel.rt.b4p.impl.engine.IAeB4PManager;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext;

/**
 * Recovered execution of a people activity that sent a notification 
 */
public class AeRecoveredPeopleActivityNotificationItem extends AeRecoveredPeopleActivityExecution
{
   /** notification to be sent */
   private AeNotification mNotification;
   
   /**
    * Ctor
    * @param aNotification
    * @param aAttachmentContainer
    * @param aContext
    */
   protected AeRecoveredPeopleActivityNotificationItem(AeNotification aNotification, IAeAttachmentContainer aAttachmentContainer, IAeActivityLifeCycleContext aContext)
   {
      super(aNotification, aAttachmentContainer, aContext);
      setNotification(aNotification);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#queueItem(org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal)
    */
   public void queueItem(IAeBusinessProcessEngineInternal aTargetEngine)
         throws AeBusinessProcessException
   {
      IAeB4PManager b4pManager = (IAeB4PManager) aTargetEngine.getCustomManager(IAeProcessTaskConstants.B4P_MANAGER_KEY);
      b4pManager.executeNotification(getNotification(), getAttachmentContainer(), getContext());
   }

   /**
    * @return the notification
    */
   protected AeNotification getNotification()
   {
      return mNotification;
   }

   /**
    * @param aNotification the notification to set
    */
   protected void setNotification(AeNotification aNotification)
   {
      mNotification = aNotification;
   }
}
 