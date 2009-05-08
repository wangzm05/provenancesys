//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/request/AeAssignNotificationOverrides.java,v 1.3 2008/02/21 17:14:56 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.request;

import org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef;
import org.activebpel.rt.ht.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.ht.def.AeLocalNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AePriorityDef;
import org.activebpel.rt.ht.def.AeRecipientsDef;

/**
 * This visitor traverses people activity def and inlines referenced notification in Local notification
 * when Local notification is used. This class also accounts for priority and people assignment
 * overrides and the inlined notification contains an aggregate of people assignments.  
 * 
 */
public class AeAssignNotificationOverrides extends AePABaseVisitor
{

   /** Notification def object */
   private AeNotificationDef mNotificationDef;

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
    */
   public void visit(AeB4PLocalNotificationDef aDef)
   {
      AeNotificationDef notificationDef = (AeNotificationDef) aDef.getInlineNotificationDef().clone();
      notificationDef.setParentXmlDef(aDef.getParentXmlDef());
      setNotificationDef(notificationDef);
      callAccept(aDef.getPriority());
      callAccept(aDef.getPeopleAssignments());
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeLocalNotificationDef)
    */
   public void visit(AeLocalNotificationDef aDef)
   {
      AeNotificationDef notificationDef = (AeNotificationDef) aDef.getInlineNotificationDef().clone();
      notificationDef.setParentXmlDef(aDef.getParentXmlDef());
      setNotificationDef(notificationDef);
      callAccept(aDef.getPriority());
      callAccept(aDef.getPeopleAssignments());
   }
   
   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      AeNotificationDef notificationDef = (AeNotificationDef) aDef.clone();
      setNotificationDef(notificationDef);      
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      getNotificationDef().getPeopleAssignments().setBusinessAdministrators(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.impl.request.AePABaseVisitor#visit(org.activebpel.rt.ht.def.AeRecipientsDef)
    */
   public void visit(AeRecipientsDef aDef)
   {
      getNotificationDef().getPeopleAssignments().setRecipients(aDef);
   }

   /**
    * @return the notificationDef
    */
   protected AeNotificationDef getNotificationDef()
   {
      return mNotificationDef;
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePriorityDef)
    */
   public void visit(AePriorityDef aDef)
   {
      getNotificationDef().setPriority(aDef);
   }
   
   /**
    * @param aNotificationDef the notificationDef to set
    */
   protected void setNotificationDef(AeNotificationDef aNotificationDef)
   {
      mNotificationDef = aNotificationDef;
   }
   
}
