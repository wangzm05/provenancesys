// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/engine/IAeB4PManager.java,v 1.7 2008/03/11 03:10:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.impl.engine;

import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.IAeManager;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.w3c.dom.Element;

/**
 * The interface for the B4P custom engine manager.
 */
public interface IAeB4PManager extends IAeManager
{
   /** name of the manager. used for looking up values in the engine config */
   public static final String MANAGER_NAME = "BPEL4PeopleManager"; //$NON-NLS-1$
   
   /**
    * Executes the given task.
    * 
    * TODO (b4p) we shouldn't be passing the lifecycle context here, but right now it's heavily used by the server impl
    * 
    * @param aTask
    * @param aAttachmentContainer
    * @param aContext
    * @throws AeBusinessProcessException
    */
   public void executeTask(AeTask aTask, IAeAttachmentContainer aAttachmentContainer, IAeActivityLifeCycleContext aContext) throws AeBusinessProcessException;
   
   /**
    * Executes the given notification.
    * 
    * TODO (b4p) we shouldn't be passing the lifecycle context here, but right now it's heavily used by the server impl
    * 
    * @param aNotification
    * @param aAttachmentContainer TODO
    * @param aContext
    * @throws AeBusinessProcessException
    */
   public void executeNotification(AeNotification aNotification, IAeAttachmentContainer aAttachmentContainer, IAeActivityLifeCycleContext aContext) throws AeBusinessProcessException;
   
   /**
    * Called by the LPG impl to evaluate a logical people group.  The return
    * value depends on how the LPG was deployed (possibly via the PDD or some
    * other mechanism determined by the manager impl).
    * 
    * @param aBpelObject
    * @param aLPGDef
    */
   public Element evaluateLogicalPeopleGroup(IAeBpelObject aBpelObject, AeLogicalPeopleGroupDef aLPGDef);
   
   /**
    * Cancels the execution of the people activity's task and cleans up any processes
    * @param aProcessId
    * @param aPeopleActivityId
    * @param aTransmissionId
    * @throws AeBusinessProcessException 
    */
   public void cancelTask(long aProcessId, int aPeopleActivityId, long aTransmissionId) throws AeBusinessProcessException;
   
   /**
    * Callback from the people activity when it receives its task response
    * @param aProcessId
    * @param aPeopleActivityId
    * @param aTransmissionId
    */
   public void taskResponseReceived(long aProcessId, int aPeopleActivityId, long aTransmissionId);
}
