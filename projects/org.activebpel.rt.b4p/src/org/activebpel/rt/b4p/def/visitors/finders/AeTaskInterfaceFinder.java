//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/finders/AeTaskInterfaceFinder.java,v 1.4 2008/01/28 18:46:55 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.visitors.finders; 

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef;
import org.activebpel.rt.b4p.def.AeLocalTaskDef;
import org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTaskInterfaceDef;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * Finds the port type/operation for the task's data, whether it's an inline
 * task/notification or a local task/notification.
 */
public class AeTaskInterfaceFinder extends AeAbstractB4PDefVisitor
{
   /** port type */
   private QName mPortType;
   /** operation */
   private String mOperation;
   /** true if we're looking for the request data */
   private boolean mRequestFlag;
   
   /**
    * ctor 
    */
   private AeTaskInterfaceFinder()
   {
   }
   
   /**
    * Finds the consumer operation
    * @param aDef
    */
   public static String findConsumerOperation(AeBaseXmlDef aDef)
   {
      return findOperation(aDef, false);
   }

   /**
    * Finds the producer operation
    * @param aDef
    */
   public static String findProducerOperation(AeBaseXmlDef aDef)
   {
      return findOperation(aDef, true);
   }
   
   /**
    * Finds the producer port type
    * @param aDef
    */
   public static QName findProducerPortType(AeBaseXmlDef aDef)
   {
      return findPortType(aDef, true);
   }

   /**
    * Finds the consumer port type
    * @param aDef
    */
   public static QName findConsumerPortType(AeBaseXmlDef aDef)
   {
      return findPortType(aDef, false);
   }
   
   /**
    * Finds the operation, either producer or consumer depending on the flag
    * @param aDef
    * @param aRequestFlag
    */
   private static String findOperation(AeBaseXmlDef aDef, boolean aRequestFlag)
   {
      if (aDef != null)
      {
         AeTaskInterfaceFinder finder = new AeTaskInterfaceFinder();
         finder.setRequestFlag(aRequestFlag);
         aDef.accept(finder);
         return finder.getOperation();
      }
      return null;
   }
   
   /**
    * Finds the port type, either producer or consumer depending on the flag
    * @param aDef
    * @param aRequestFlag
    */
   private static QName findPortType(AeBaseXmlDef aDef, boolean aRequestFlag)
   {
      if (aDef != null)
      {
         AeTaskInterfaceFinder finder = new AeTaskInterfaceFinder();
         finder.setRequestFlag(aRequestFlag);
         aDef.accept(finder);
         return finder.getPortType();
      }
      return null;
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
    */
   public void visit(AeB4PLocalNotificationDef aDef)
   {
      if (aDef.getInlineNotificationDef() != null)
      {
         visitNotification(aDef.getInlineNotificationDef());
      }
      else
      {
         AeNotificationDef notificationDef = AeB4PResourceFinder.findNotification(aDef, aDef.getReference());
         visitNotification(notificationDef);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeLocalTaskDef)
    */
   public void visit(AeLocalTaskDef aDef)
   {
      if (aDef.getInlineTaskDef() != null)
      {
         visitTask(aDef.getInlineTaskDef());
      }
      else
      {
         AeTaskDef taskDef = AeB4PResourceFinder.findTask(aDef, aDef.getReference());
         visitTask(taskDef);
      }
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      visitNotification(aDef);
   }

   /**
    * Extracts the port type and operation from the notification
    * @param aDef
    */
   protected void visitNotification(AeNotificationDef aDef)
   {
      if (aDef != null && isRequestFlag() && aDef.getNotificationInterfaceDef() != null)
      {
         setPortType(aDef.getNotificationInterfaceDef().getPortType());
         setOperation(aDef.getNotificationInterfaceDef().getOperation());
      }
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      visitTask(aDef);
   }

   /**
    * Extracts the port type and operation from the task interface
    * @param aDef
    */
   protected void visitTask(AeTaskDef aDef)
   {
      if (aDef != null)
      {
         AeTaskInterfaceDef interDef = aDef.getTaskInterfaceDef();
         if (interDef != null)
         {
            if (isRequestFlag() || interDef.getResponsePortType() == null)
            {
               setPortType(interDef.getPortType());
               setOperation(interDef.getOperation());
            }
            else
            {
               setPortType(interDef.getResponsePortType());
               setOperation(interDef.getResponseOperation());
            }
         }
      }
   }

   /**
    * @return the portType
    */
   public QName getPortType()
   {
      return mPortType;
   }

   /**
    * @param aPortType the portType to set
    */
   public void setPortType(QName aPortType)
   {
      mPortType = aPortType;
   }

   /**
    * @return the operation
    */
   public String getOperation()
   {
      return mOperation;
   }

   /**
    * @param aOperation the operation to set
    */
   public void setOperation(String aOperation)
   {
      mOperation = aOperation;
   }

   /**
    * @return the requestFlag
    */
   public boolean isRequestFlag()
   {
      return mRequestFlag;
   }

   /**
    * @param aRequestFlag the requestFlag to set
    */
   public void setRequestFlag(boolean aRequestFlag)
   {
      mRequestFlag = aRequestFlag;
   }
} 