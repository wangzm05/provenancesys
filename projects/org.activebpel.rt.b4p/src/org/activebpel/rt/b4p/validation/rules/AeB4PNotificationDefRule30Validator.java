// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeB4PNotificationDefRule30Validator.java,v 1.1 2008/02/21 22:06:39 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef;
import org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.ht.def.AeLocalNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationsDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * warning if Notification not used (const 2 only)
 */
public class AeB4PNotificationDefRule30Validator extends AeAbstractB4PValidator
{
   /** set of all notifications */
   private Set mNotifications = new HashSet();
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationsDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * Rule logic
    * @param aDef
    */
   protected void executeRule(AeNotificationsDef aDef)
   {
      // collect a set of all the notifications 
      for (Iterator notifs = aDef.getNotificationDefs(); notifs.hasNext();)
      {
         AeNotificationDef def = (AeNotificationDef) notifs.next();
         getNotifications().add(def);
      }
      
      // find the containing AeActivityScope or AeProcessDef and visit to find references to notifications
      AeBaseDef def = AeDefUtil.getEnclosingScopeDef(aDef);
      
      final AeNotificationRule30Visitor extensionViz = new AeNotificationRule30Visitor();
      def.accept(new AeExtensionDefRuleVisitor() {
            protected void acceptExtensionBaseXmlDef(AeBaseXmlDef aDef)
            {
               aDef.accept(extensionViz);
            }
         }
      );
      
      // remove all found references only leaving the unused notifications
      getNotifications().removeAll(extensionViz.getFoundNotificationReferences());
      
      // report a problem on the unused notification
      for (Iterator unusedIter = getNotifications().iterator(); unusedIter.hasNext();)
      {
         AeNotificationDef notifcation = (AeNotificationDef) unusedIter.next();
         if (notifcation != null)
         {
            String message = AeMessages.format("AeB4PNotificationDefRule30Validator.NOTIFICATION_NOT_REFERENCED", new Object[] {notifcation.getName()}); //$NON-NLS-1$
            reportProblem(message, notifcation);
         }
      }
   }

   /**
    * @return Returns the notifications.
    */
   protected Set getNotifications()
   {
      return mNotifications;
   }
   
   /**
    * Helper inner class to find references to the HI notifications.
    */
   private class AeNotificationRule30Visitor extends AeAbstractTraversingB4PDefVisitor
   {
      /** set of found notification references */
      private Set mFoundNotificationReferences = new HashSet();

      /**
       * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
       */
      public void visit(AeB4PLocalNotificationDef aDef)
      {
         if (AeUtil.notNullOrEmpty(aDef.getReference()))
         {
            AeNotificationDef notifcation = getValidationContext().findNotification(aDef, aDef.getReference());
            getFoundNotificationReferences().add(notifcation);
         }
         super.visit(aDef);
      }

      /**
       * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLocalNotificationDef)
       */
      public void visit(AeLocalNotificationDef aDef)
      {
         if (AeUtil.notNullOrEmpty(aDef.getReference()))
         {
            AeNotificationDef notifcation = getValidationContext().findNotification(aDef, aDef.getReference());
            getFoundNotificationReferences().add(notifcation);
         }
         super.visit(aDef);
      }

      /**
       * @return Returns the foundNotificationReferences.
       */
      public Set getFoundNotificationReferences()
      {
         return mFoundNotificationReferences;
      }
   }
}
