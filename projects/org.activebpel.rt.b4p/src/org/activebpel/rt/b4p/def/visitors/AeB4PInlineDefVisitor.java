//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/AeB4PInlineDefVisitor.java,v 1.7.4.1 2008/04/21 16:08:04 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.visitors;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef;
import org.activebpel.rt.b4p.def.AeLocalTaskDef;
import org.activebpel.rt.b4p.def.visitors.finders.AeB4PResourceFinder;
import org.activebpel.rt.ht.def.AeEscalationDef;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AeLocalNotificationDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.util.AeUtil;

/**
 * implementation of B4P visitor to inline all references of the <code>localTask</code> and
 * <code>localNotification</code> elements.
 * <p>Note that the visitor that deserializes BPEL4People extension
 * elements must be run before this visitor runs.</p>
 */
public class AeB4PInlineDefVisitor extends AeAbstractTraversingB4PDefVisitor
{
   /**
    * C'tor
    */
   public AeB4PInlineDefVisitor()
   {
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLocalNotificationDef)
    */
   public void visit(AeLocalNotificationDef aDef)
   {
      AeNotificationDef notification = AeB4PResourceFinder.findNotification(aDef, aDef.getReference());
      aDef.setInlineNotificationDef(notification);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
    */
   public void visit(AeB4PLocalNotificationDef aDef)
   {
      AeNotificationDef notification = AeB4PResourceFinder.findNotification(aDef, aDef.getReference());
      aDef.setInlineNotificationDef(notification);
      super.visit(aDef);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLocalNotificationDef)
    */
   public void visit(AeLocalTaskDef aDef)
   {
      AeTaskDef taskDef = AeB4PResourceFinder.findTask(aDef, aDef.getReference());
      aDef.setInlineTaskDef(taskDef);
      super.visit(aDef);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      QName name = aDef.getLogicalPeopleGroup();
      if (AeUtil.notNullOrEmpty(name))
      {
         AeLogicalPeopleGroupDef lpg = AeB4PResourceFinder.findLogicalPeopleGroup(aDef, name);
         aDef.setInlineLogicalPeopleGroupDef(lpg);
      }
      super.visit(aDef);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationDef)
    */
   public void visit(AeEscalationDef aDef)
   {
      if (aDef.getLocalNotification() != null && AeUtil.notNullOrEmpty(aDef.getLocalNotification().getReference())) 
      {
         AeLocalNotificationDef localNotif = aDef.getLocalNotification();
         AeNotificationDef notification = AeB4PResourceFinder.findNotification(localNotif, localNotif.getReference());
         localNotif.setInlineNotificationDef(notification);
      }
      super.visit(aDef);
   }
}

