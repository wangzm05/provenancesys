//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/io/readers/AeAbstractB4PReaderRegistry.java,v 1.10 2007/11/19 22:17:20 jbik Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.io.readers;

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
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.ht.def.AePriorityDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.io.readers.AeAbstractHtReaderRegistry;
import org.activebpel.rt.xml.def.AeDocumentationDef;
import org.activebpel.rt.xml.def.io.readers.IAeReaderFactory;

/**
 * Abstract Registry for reading BPEL4People defs from xml
 */
public abstract class AeAbstractB4PReaderRegistry extends AeAbstractHtReaderRegistry implements IAeB4PDefConstants
{
   /**
    * Ctor
    * @param aFactory
    */
   public AeAbstractB4PReaderRegistry(IAeReaderFactory aFactory)
   {
      super(B4P_NAMESPACE, aFactory);
   }

   /**
    * @see org.activebpel.rt.ht.def.io.readers.AeAbstractHtReaderRegistry#initParentRegistry()
    */
   protected void initParentRegistry()
   {
      setDefaultNamespace(DEFAULT_HT_NS);

      super.initParentRegistry();

      registerReader(AeProcessStakeholdersDef.class,    TAG_FROM,                   createReader(AeFromDef.class));
      registerReader(AeBusinessAdministratorsDef.class, TAG_FROM,                   createReader(AeFromDef.class));
      registerReader(AeProcessInitiatorDef.class,       TAG_FROM,                   createReader(AeFromDef.class));
      registerReader(AePeopleActivityDef.class,         TAG_TASK,                   createReader(AeTaskDef.class));
      registerReader(AePeopleActivityDef.class,         TAG_NOTIFICATION,           createReader(AeNotificationDef.class));
      registerReader(AeLocalTaskDef.class,              TAG_PRIORITY,               createReader(AePriorityDef.class));
      registerReader(AeB4PLocalNotificationDef.class,   TAG_PRIORITY,               createReader(AePriorityDef.class));
      registerReader(AeLocalTaskDef.class,              TAG_PEOPLE_ASSIGNMENTS,     createReader(AePeopleAssignmentsDef.class));
      registerReader(AeB4PLocalNotificationDef.class,   TAG_PEOPLE_ASSIGNMENTS,     createReader(AePeopleAssignmentsDef.class));
      
      setDefaultNamespace(B4P_NAMESPACE);
      registerReader(AePeopleActivityDef.class,         TAG_LOCAL_TASK,             createReader(AeLocalTaskDef.class));
      registerReader(AePeopleActivityDef.class,         TAG_LOCAL_NOTIFICATION,     createReader(AeB4PLocalNotificationDef.class));
     
      registerReader(AePeopleActivityDef.class,         TAG_SCHEDULED_ACTIONS,      createReader(AeScheduledActionsDef.class));
      registerReader(AePeopleActivityDef.class,         TAG_ATTACHMENT_PROPAGATION, createReader(AeAttachmentPropagationDef.class));
      registerReader(AeScheduledActionsDef.class,       TAG_DEFER_ACTIVATION,       createReader(AeDeferActivationDef.class));
      registerReader(AeScheduledActionsDef.class,       TAG_EXPIRATION,             createReader(AeExpirationDef.class));
      registerReader(AeDeferActivationDef.class,        TAG_FOR,                    createReader(AeB4PForDef.class));
      registerReader(AeDeferActivationDef.class,        TAG_UNTIL,                  createReader(AeB4PUntilDef.class));
      registerReader(AeExpirationDef.class,             TAG_FOR,                    createReader(AeB4PForDef.class));
      registerReader(AeExpirationDef.class,             TAG_UNTIL,                  createReader(AeB4PUntilDef.class));
   }

   /**
    * Populates the mGenericReadersMap with any generic readers.
    */
   protected void initGenericElementRegistry()
   {
      setDefaultNamespace(DEFAULT_HT_NS);
      super.initGenericElementRegistry();
      setDefaultNamespace(B4P_NAMESPACE);
      getGenericReadersMap().put(makeDefaultQName(TAG_DOCUMENTATION),            createReader(AeDocumentationDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_HUMAN_INTERACTIONS),       createReader(AeB4PHumanInteractionsDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_BUSINESS_ADMINISTRATORS),  createReader(AeBusinessAdministratorsDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_PEOPLE_ASSIGNMENTS),       createReader(AeB4PPeopleAssignmentsDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_PEOPLE_ACTIVITY),          createReader(AePeopleActivityDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_PROCESS_INITIATOR),        createReader(AeProcessInitiatorDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_PROCESS_STAKE_HOLDERS),    createReader(AeProcessStakeholdersDef.class));
   }

}