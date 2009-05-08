//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/recovery/AeRecoveredPeopleActivityExecution.java,v 1.1 2008/03/11 03:07:53 mford Exp $
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
import org.activebpel.rt.b4p.impl.engine.AeB4PQueueObject;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext;
import org.activebpel.rt.bpel.server.engine.recovery.recovered.AeRecoveredLocationIdItem;

/**
 * Base class for the execution of a people activity. Subclasses will provide
 * specifics for task or notification execution.
 */
public abstract class AeRecoveredPeopleActivityExecution extends AeRecoveredLocationIdItem
{
   /** attachments for the task/notitication */
   private IAeAttachmentContainer mAttachmentContainer;
   /** context for the execution */
   private IAeActivityLifeCycleContext mContext;

   /**
    * Ctor
    * @param aQueueObject
    * @param aAttachmentContainer
    * @param aContext
    */
   protected AeRecoveredPeopleActivityExecution(AeB4PQueueObject aQueueObject, IAeAttachmentContainer aAttachmentContainer, IAeActivityLifeCycleContext aContext)
   {
      super(aQueueObject.getProcessId(), aQueueObject.getLocationId());
      setAttachmentContainer(aAttachmentContainer);
      setContext(aContext);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#isRemoval()
    */
   public boolean isRemoval()
   {
      return false;
   }

   /**
    * @return the attachmentContainer
    */
   protected IAeAttachmentContainer getAttachmentContainer()
   {
      return mAttachmentContainer;
   }

   /**
    * @param aAttachmentContainer the attachmentContainer to set
    */
   protected void setAttachmentContainer(IAeAttachmentContainer aAttachmentContainer)
   {
      mAttachmentContainer = aAttachmentContainer;
   }

   /**
    * @return the context
    */
   protected IAeActivityLifeCycleContext getContext()
   {
      return mContext;
   }

   /**
    * @param aContext the context to set
    */
   protected void setContext(IAeActivityLifeCycleContext aContext)
   {
      mContext = aContext;
   }
}
 