//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AePeopleActivityDef.java,v 1.12 2008/03/24 18:44:59 EWittmann Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def;

import java.util.Collections;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor;
import org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor;
import org.activebpel.rt.b4p.def.visitors.finders.AeTaskInterfaceFinder;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef;
import org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromPartsDef;
import org.activebpel.rt.bpel.def.activity.support.AeToPartsDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.IAeNotificationDefParent;
import org.activebpel.rt.ht.def.IAeTaskDefParent;
import org.activebpel.rt.message.AeMessagePartsMap;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.IAeNamedDef;

/**
 * Impl. for BPEL4People 'humanInteractions' element Def
 */
public class AePeopleActivityDef extends AeB4PBaseDef implements IAeTaskDefParent, IAeNotificationDefParent,
   IAeMessageDataConsumerDef, IAeMessageDataProducerDef, IAeNamedDef, IAeB4PContextParent
{
   /** true if the task can be skipped. */
   private boolean mSkipable;
   /** 'name' attribute */
   private String mName;
   /** 'inputVariable' attribute */
   private String mInputVariable;
   /** 'outputVariable' attribute */
   private String mOutputVariable;
   /** 'task' element */
   private AeTaskDef mTask;
   /** 'localTask' element */
   private AeLocalTaskDef mLocalTask;
   /** 'notification' element */
   private AeNotificationDef mNotification;
   /** 'notification' element */
   private AeB4PLocalNotificationDef mLocalNotification;
   /** 'scheduledActions' element */
   private AeScheduledActionsDef mScheduledActions;
   /** 'attachmentPropagation' element */
   private AeAttachmentPropagationDef mAttachmentPropagation;
   /** BPEL 'fromParts' element */
   private AeFromPartsDef mFromParts;
   /** BPEL 'toParts' element */
   private AeToPartsDef mToParts;

   /** message parts map for the message being consumed */
   private AeMessagePartsMap mConsumerMap;
   /** message parts map for the message being produced */
   private AeMessagePartsMap mProducerMap;
   /** strategy for consuming message */
   private String mConsumerStrategy;
   /** strategy for producing message */
   private String mProducerStrategy;
   /** optional variable for consuming message */
   private AeVariableDef mConsumerVariable;
   /** optional variable for producing message */
   private AeVariableDef mProducerVariable;

   /** context for the people activity (only used in designer)  */
   private IAeB4PContext mContext;

   /**
    * @see org.activebpel.rt.xml.def.IAeNamedDef#getName()
    */
   public String getName()
   {
      return mName;
   }

   /**
    * @param aName the name to set
    */
   public void setName(String aName)
   {
      mName = aName;
   }

   /**
    * @return the inputVariable
    */
   public String getInputVariable()
   {
      return mInputVariable;
   }

   /**
    * @param aInputVariable the inputVariable to set
    */
   public void setInputVariable(String aInputVariable)
   {
      mInputVariable = aInputVariable;
   }

   /**
    * @return the outputVariable
    */
   public String getOutputVariable()
   {
      return mOutputVariable;
   }

   /**
    * @param aOutputVariable the outputVariable to set
    */
   public void setOutputVariable(String aOutputVariable)
   {
      mOutputVariable = aOutputVariable;
   }

   /**
    * @return the 'task' element Def object.
    */
   public AeTaskDef getTask()
   {
      return mTask;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeTaskDefParent#addTask(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void addTask(AeTaskDef aTask)
   {
      mTask = aTask;
      assignParent(aTask);
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeTaskDefParent#getTaskDefs()
    */
   public Iterator getTaskDefs()
   {
      return Collections.singleton(getTask()).iterator();
   }

   /**
    * @return the 'localTask' element Def object.
    */
   public AeLocalTaskDef getLocalTask()
   {
      return mLocalTask;
   }

   /**
    * @param aLocalTask the 'localTask ' element Def to set
    */
   public void setLocalTask(AeLocalTaskDef aLocalTask)
   {
      mLocalTask = aLocalTask;
      assignParent(aLocalTask);
   }

   /**
    * @return the 'notification' element Def object.
    */
   public AeNotificationDef getNotification()
   {
      return mNotification;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeNotificationDefParent#getNotificationDefs()
    */
   public Iterator getNotificationDefs()
   {
      return Collections.singletonList(getNotification()).iterator();
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeNotificationDefParent#addNotification(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void addNotification(AeNotificationDef aNotification)
   {
      mNotification = aNotification;
      assignParent(aNotification);
   }

   /**
    * @return the localNotification
    */
   public AeB4PLocalNotificationDef getLocalNotification()
   {
      return mLocalNotification;
   }

   /**
    * @param aLocalNotification the localNotification to set
    */
   public void setLocalNotification(AeB4PLocalNotificationDef aLocalNotification)
   {
      mLocalNotification = aLocalNotification;
      assignParent(aLocalNotification);
   }

   /**
    * @return the scheduledActions
    */
   public AeScheduledActionsDef getScheduledActions()
   {
      return mScheduledActions;
   }

   /**
    * @param aScheduledActions the scheduledActions to set
    */
   public void setScheduledActions(AeScheduledActionsDef aScheduledActions)
   {
      mScheduledActions = aScheduledActions;
      assignParent(aScheduledActions);
   }

   /**
    * @return the attachmentPropagation
    */
   public AeAttachmentPropagationDef getAttachmentPropagation()
   {
      return mAttachmentPropagation;
   }

   /**
    * @param aAttachmentPropagation the attachmentPropagation to set
    */
   public void setAttachmentPropagation(AeAttachmentPropagationDef aAttachmentPropagation)
   {
      mAttachmentPropagation = aAttachmentPropagation;
      assignParent(aAttachmentPropagation);
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef#getFromPartsDef()
    */
   public AeFromPartsDef getFromPartsDef()
   {
      return mFromParts;
   }

   /**
    * @param aFromParts to be set
    */
   public void setFromPartsDef(AeFromPartsDef aFromParts)
   {
      mFromParts = aFromParts;
      // NOT assigning the PA as the parent since the fromParts are parented
      // by the child extension activity def in the bpel domain
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef#getToPartsDef()
    */
   public AeToPartsDef getToPartsDef()
   {
      return mToParts;
   }

   /**
    * @param aToParts to be set
    */
   public void setToPartsDef(AeToPartsDef aToParts)
   {
      mToParts = aToParts;
      // NOT assigning the PA as the parent since the toParts are parented
      // by the child extension activity def in the bpel domain
   }

   /**
    * @see org.activebpel.rt.b4p.def.AeB4PBaseDef#accept(org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor)
    */
   public void accept(IAeB4PDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef#getConsumerMessagePartsMap()
    */
   public AeMessagePartsMap getConsumerMessagePartsMap()
   {
      return mConsumerMap;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef#getMessageDataConsumerStrategy()
    */
   public String getMessageDataConsumerStrategy()
   {
      return mConsumerStrategy;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef#getMessageDataConsumerVariable()
    */
   public AeVariableDef getMessageDataConsumerVariable()
   {
      if (mConsumerVariable == null)
      {
         mConsumerVariable = AeDefUtil.getVariableByName(getOutputVariable(), (AeBaseDef)getParentXmlDef());
      }
      return mConsumerVariable;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef#setConsumerMessagePartsMap(org.activebpel.rt.message.AeMessagePartsMap)
    */
   public void setConsumerMessagePartsMap(AeMessagePartsMap aMap)
   {
      mConsumerMap = aMap;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef#setMessageDataConsumerStrategy(java.lang.String)
    */
   public void setMessageDataConsumerStrategy(String aStrategy)
   {
      mConsumerStrategy = aStrategy;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef#getMessageDataProducerStrategy()
    */
   public String getMessageDataProducerStrategy()
   {
      return mProducerStrategy;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef#getMessageDataProducerVariable()
    */
   public AeVariableDef getMessageDataProducerVariable()
   {
      if (mProducerVariable == null)
      {
         mProducerVariable = AeDefUtil.getVariableByName(getInputVariable(), (AeBaseDef)getParentXmlDef());
      }
      return mProducerVariable;
   }

   /**
    * Setter for the producer variable
    * @param aDef
    */
   public void setMessageDataProducerVariable(AeVariableDef aDef)
   {
      mProducerVariable = aDef;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef#getProducerMessagePartsMap()
    */
   public AeMessagePartsMap getProducerMessagePartsMap()
   {
      return mProducerMap;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef#setMessageDataProducerStrategy(java.lang.String)
    */
   public void setMessageDataProducerStrategy(String aStrategy)
   {
      mProducerStrategy = aStrategy;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef#setProducerMessagePartsMap(org.activebpel.rt.message.AeMessagePartsMap)
    */
   public void setProducerMessagePartsMap(AeMessagePartsMap aMap)
   {
      mProducerMap = aMap;
   }

   /**
    * @return the skipable
    */
   public boolean isSkipable()
   {
      return mSkipable;
   }

   /**
    * @param aSkipable the skipable to set
    */
   public void setSkipable(boolean aSkipable)
   {
      mSkipable = aSkipable;
   }

   /**
    * Gets the def for the people activity's task/notification data
    */
   public AeBaseXmlDef getTaskDataDef()
   {
      if (getLocalNotification() != null)
         return getLocalNotification();
      if (getLocalTask() != null)
         return getLocalTask();
      if (getNotification() != null)
         return getNotification();
      if (getTask() != null)
         return getTask();
      return null;
   }

   /**
    * Returns true if the people activity is configured with a task
    */
   public boolean isTask()
   {
      return getLocalTask() != null || getTask() != null;
   }

   /**
    * Returns true if the people activity is configured with a notification
    */
   public boolean isNotification()
   {
      return getLocalNotification() != null || getNotification() != null;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef#getConsumerOperation()
    */
   public String getConsumerOperation()
   {
      return AeTaskInterfaceFinder.findConsumerOperation(getTaskDataDef());
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef#getConsumerPortType()
    */
   public QName getConsumerPortType()
   {
      return AeTaskInterfaceFinder.findConsumerPortType(getTaskDataDef());
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef#getProducerOperation()
    */
   public String getProducerOperation()
   {
      return AeTaskInterfaceFinder.findProducerOperation(getTaskDataDef());
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef#getProducerPortType()
    */
   public QName getProducerPortType()
   {
      return AeTaskInterfaceFinder.findProducerPortType(getTaskDataDef());
   }

   /**
    * @return the context
    */
   public IAeB4PContext getContext()
   {
      return mContext;
   }

   /**
    * @param aContext the context to set
    */
   public void setContext(IAeB4PContext aContext)
   {
      mContext = aContext;
   }

   /**
    * @see org.activebpel.rt.b4p.def.AeB4PBaseDef#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (super.equals(aOther))
      {
         AePeopleActivityDef other = (AePeopleActivityDef) aOther;
         return AeUtil.compareObjects(getTask(), other.getTask()) &&
            AeUtil.compareObjects(getNotification(), other.getNotification()) &&
            AeUtil.compareObjects(getLocalNotification(), other.getLocalNotification()) &&
            AeUtil.compareObjects(getLocalTask(), other.getLocalTask()) &&
            AeUtil.compareObjects(getScheduledActions(), other.getScheduledActions()) &&
            AeUtil.compareObjects(getAttachmentPropagation(), other.getAttachmentPropagation()) &&
            AeUtil.compareObjects(getName(), other.getName()) &&
            isSkipable() == other.isSkipable() &&
            AeUtil.compareObjects(getInputVariable(), other.getInputVariable()) &&
            AeUtil.compareObjects(getOutputVariable(), other.getOutputVariable());
      }
      return false;
   }

   /**
    * @see org.activebpel.rt.xml.def.AeBaseXmlDef#clone()
    */
   public Object clone()
   {
      AePeopleActivityDef copy = (AePeopleActivityDef) super.clone();

      // task data
      if (getTask() != null)
         copy.addTask((AeTaskDef) getTask().clone());
      if (getNotification() != null)
         copy.addNotification((AeNotificationDef) getNotification().clone());
      if (getLocalNotification() != null)
         copy.setLocalNotification((AeB4PLocalNotificationDef) getLocalNotification().clone());
      if (getLocalTask() != null)
         copy.setLocalTask((AeLocalTaskDef) getLocalTask().clone());

      if (getScheduledActions() != null)
         copy.setScheduledActions((AeScheduledActionsDef) getScheduledActions().clone());

      if (getAttachmentPropagation() != null)
         copy.setAttachmentPropagation((AeAttachmentPropagationDef) getAttachmentPropagation().clone());

      // fromParts/toParts aren't being cloned. These aren't needed in designer.

      return copy;
   }

   /**
    * Sets new task data (or null) on the def. This will clear any previously
    * set task data (task, notification, local task, local notification) prior
    * to setting the new value.
    * @param aDef
    */
   public void setTaskDataDef(AeBaseXmlDef aDef)
   {
      new AeSetTaskDataVisitor(aDef);
   }

   /**
    * Clears out the previously set task data (if any) and then assigns the new
    * task data in place.
    */
   private class AeSetTaskDataVisitor extends AeAbstractB4PDefVisitor
   {
      /**
       * C'tor.
       *
       * @param aDef
       */
      public AeSetTaskDataVisitor(AeBaseXmlDef aDef)
      {
         setLocalNotification(null);
         setLocalTask(null);
         addTask(null);
         addNotification(null);

         if (aDef != null)
            aDef.accept(this);
      }

      /**
       * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
       */
      public void visit(AeB4PLocalNotificationDef aDef)
      {
         setLocalNotification(aDef);
      }

      /**
       * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeLocalTaskDef)
       */
      public void visit(AeLocalTaskDef aDef)
      {
         setLocalTask(aDef);
      }

      /**
       * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
       */
      public void visit(AeNotificationDef aDef)
      {
         addNotification(aDef);
      }

      /**
       * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
       */
      public void visit(AeTaskDef aDef)
      {
         addTask(aDef);
      }
   }

}
