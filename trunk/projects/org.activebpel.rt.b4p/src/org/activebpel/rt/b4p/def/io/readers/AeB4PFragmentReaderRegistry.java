//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/io/readers/AeB4PFragmentReaderRegistry.java,v 1.4 2008/03/01 04:36:44 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.io.readers;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.def.io.readers.def.AeB4PDefReaderVisitor;
import org.activebpel.rt.ht.def.AeCompletionDeadlineDef;
import org.activebpel.rt.ht.def.AeDeadlinesDef;
import org.activebpel.rt.ht.def.AeEscalationDef;
import org.activebpel.rt.ht.def.AeEscalationProcessDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationsDef;
import org.activebpel.rt.ht.def.AeStartDeadlineDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTasksDef;
import org.activebpel.rt.ht.def.AeUserDef;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.io.readers.IAeReaderFactory;
import org.activebpel.rt.xml.def.io.readers.IAeReportingDefReader;
import org.w3c.dom.Element;

/**
 * Class used to read in fragments of B4P/HT def objects 
 */
public class AeB4PFragmentReaderRegistry extends AeAbstractB4PReaderRegistry
{
   private static final IAeReaderFactory sFactory = new IAeReaderFactory()
      {
         /**
          * @see org.activebpel.rt.xml.def.io.readers.IAeReaderFactory#createReportingDefReader(org.activebpel.rt.xml.def.AeBaseXmlDef,
          *      org.activebpel.rt.xml.def.AeBaseXmlDef, org.w3c.dom.Element)
          */
         public IAeReportingDefReader createReportingDefReader(AeBaseXmlDef aParentDef, AeBaseXmlDef aNewDef, Element aElement)
         {
            return new AeB4PDefReaderVisitor(aParentDef, aElement);
         }
      };

   /**
    * C'tor
    */
   public AeB4PFragmentReaderRegistry()
   {
      super(sFactory);
   }

   /**
    * @see org.activebpel.rt.b4p.def.io.readers.AeAbstractB4PReaderRegistry#initGenericElementRegistry()
    */
   protected void initGenericElementRegistry()
   {
      setDefaultNamespace(DEFAULT_HT_NS);
      getGenericReadersMap().put(makeDefaultQName(TAG_TASK),                  createReader(AeTaskDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_TASKS),                 createReader(AeTasksDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_LOGICAL_PEOPLE_GROUP),  createReader(AeLogicalPeopleGroupDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_LOGICAL_PEOPLE_GROUPS), createReader(AeLogicalPeopleGroupsDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_DEADLINES),             createReader(AeDeadlinesDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_START_DEADLINE),        createReader(AeStartDeadlineDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_COMPLETION_DEADLINE),   createReader(AeCompletionDeadlineDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_ESCALATION),            createReader(AeEscalationDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_NOTIFICATION),          createReader(AeNotificationDef.class));
      getGenericReadersMap().put(makeDefaultQName(TAG_NOTIFICATIONS),         createReader(AeNotificationsDef.class));
      getGenericReadersMap().put(new QName(AE_HT_EXTENSION_NAMESPACE, TAG_ESCALATION_PROCESS), createReader(AeEscalationProcessDef.class));
      getGenericReadersMap().put(new QName(AE_B4P_TASK_CUSTOM_NOTIFICATIONS_NS, TAG_ESCALATION_PROCESS), createReader(AeEscalationProcessDef.class));
      getGenericReadersMap().put(new QName(IAeHtDefConstants.DEFAULT_HT_NS, TAG_USER),         createReader(AeUserDef.class));
      super.initGenericElementRegistry();
   }
}