//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/io/writers/AeAbstractB4PWriterRegistry.java,v 1.10 2008/02/07 02:07:36 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.io.writers;

import org.activebpel.rt.b4p.def.AeAttachmentPropagationDef;
import org.activebpel.rt.b4p.def.AeB4PForDef;
import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef;
import org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef;
import org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef;
import org.activebpel.rt.b4p.def.AeB4PUntilDef;
import org.activebpel.rt.b4p.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.b4p.def.AeDeferActivationDef;
import org.activebpel.rt.b4p.def.AeExpirationDef;
import org.activebpel.rt.b4p.def.AeLocalTaskDef;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.b4p.def.AeProcessInitiatorDef;
import org.activebpel.rt.b4p.def.AeProcessStakeholdersDef;
import org.activebpel.rt.b4p.def.AeScheduledActionsDef;
import org.activebpel.rt.b4p.def.IAeB4PDefConstants;
import org.activebpel.rt.xml.def.io.writers.AeBaseDefWriterRegistry;
import org.activebpel.rt.xml.def.io.writers.IAeDefWriterFactory;

/**
 * Abstract writer registry for BPEL4People Def objects
 */
public abstract class AeAbstractB4PWriterRegistry extends AeBaseDefWriterRegistry implements  IAeB4PDefConstants
{
   /**
    * Ctor
    * @param aDefaultNamespace
    * @param aFactory
    */
   public AeAbstractB4PWriterRegistry(String aDefaultNamespace, IAeDefWriterFactory aFactory)
   {
      super(aDefaultNamespace, aFactory);
   }

   /**
    * inits the registry with mappings for def class to element qname. Contains entries that are common to all
    * versions of BPEL4People.
    *
    * @see org.activebpel.rt.xml.def.io.writers.AeBaseDefWriterRegistry#init()
    */
   protected void init()
   {
      super.init();

      registerWriter(AeB4PHumanInteractionsDef.class,   TAG_HUMAN_INTERACTIONS);
      registerWriter(AeB4PPeopleAssignmentsDef.class,   TAG_PEOPLE_ASSIGNMENTS);
      registerWriter(AePeopleActivityDef.class,         TAG_PEOPLE_ACTIVITY);
      registerWriter(AeLocalTaskDef.class,              TAG_LOCAL_TASK);
      registerWriter(AeB4PLocalNotificationDef.class,   TAG_LOCAL_NOTIFICATION);
      registerWriter(AeBusinessAdministratorsDef.class, TAG_BUSINESS_ADMINISTRATORS);
      registerWriter(AeProcessInitiatorDef.class,       TAG_PROCESS_INITIATOR);
      registerWriter(AeProcessStakeholdersDef.class,    TAG_PROCESS_STAKE_HOLDERS);
      registerWriter(AeScheduledActionsDef.class,       TAG_SCHEDULED_ACTIONS);
      registerWriter(AeDeferActivationDef.class,        TAG_DEFER_ACTIVATION);
      registerWriter(AeExpirationDef.class,             TAG_EXPIRATION);
      registerWriter(AeB4PForDef.class,                 TAG_FOR);
      registerWriter(AeB4PUntilDef.class,               TAG_UNTIL);
      registerWriter(AeAttachmentPropagationDef.class,  TAG_ATTACHMENT_PROPAGATION);
   }
}
