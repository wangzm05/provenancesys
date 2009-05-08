//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/request/AeLocalNotificationInliner.java,v 1.3 2008/02/21 17:14:57 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.request;

import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext;
import org.activebpel.rt.ht.def.AeEscalationDef;
import org.activebpel.rt.ht.def.AeLocalNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationDef;

/**
 * This class finds all local notifications inside a PA 
 */
public class AeLocalNotificationInliner extends AePABaseVisitor
{
   /**
    * @param aContext
    */
   public AeLocalNotificationInliner(IAeActivityLifeCycleContext aContext)
   {
      super(aContext);
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeLocalNotificationDef)
    */
   public void visit(AeLocalNotificationDef aDef)
   {
      AeAssignNotificationOverrides notificationOverrideVisitor = new AeAssignNotificationOverrides();
      aDef.accept(notificationOverrideVisitor);
      AeNotificationDef notificationDef = notificationOverrideVisitor.getNotificationDef();
      if (aDef != null)
      {
         AeEscalationDef escalationDef = (AeEscalationDef) aDef.getParentXmlDef();
         escalationDef.addNotification(notificationDef);
         escalationDef.setLocalNotification(null);
      }
   }
}
