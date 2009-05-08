//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/AePAGraphNodeAdapter.java,v 1.7 2008/03/17 19:37:43 vvelusamy Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.b4p.impl.request.AePARequestUtil;
import org.activebpel.rt.bpel.impl.AeBpelState;
import org.activebpel.rt.bpel.impl.IAeImplStateNames;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.graph.AeXmlDefGraphNode;
import org.activebpel.rt.xml.def.graph.AeXmlDefGraphNodeProperty;
import org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNode;
import org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNodeAdapter;
import org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNodeProperty;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This adapter provides properties and icon for people activity   
 */
public class AePAGraphNodeAdapter implements IAeXmlDefGraphNodeAdapter
{

   /** def object */
   private AePeopleActivityDef mDef;
   
   /** property constants */
   private static final String TASK_TYPE_NAME = "taskType"; //$NON-NLS-1$
   private static final String TASK_TYPE_LOCAL = AeMessages.getString("AePAGraphNodeAdapter.TaskType.Local"); //$NON-NLS-1$
   private static final String NOTIFICATION_TYPE_NAME = "notificationType"; //$NON-NLS-1$
   private static final String NOTIFICATION_TYPE_LOCAL = AeMessages.getString("AePAGraphNodeAdapter.NotificationType.Local"); //$NON-NLS-1$
   private static final String TASK_TYPE_INLINE = AeMessages.getString("AePAGraphNodeAdapter.TaskType.Inline"); //$NON-NLS-1$
   private static final String NOTIFICATION_TYPE_INLINE = AeMessages.getString("AePAGraphNodeAdapter.NotificationType.Inline"); //$NON-NLS-1$
   private static final String LOCAL_REFERENCE = "localReference"; //$NON-NLS-1$
   private static final String TASK_NAME = "taskName"; //$NON-NLS-1$
   private static final String NOTIFICATION_NAME = "notificationName"; //$NON-NLS-1$
   private static final String INPUT_VARIABLE = "inputVariable"; //$NON-NLS-1$
   private static final String ACTUAL_OWNER = "actualOwner"; //$NON-NLS-1$
   private static final String TASK_PRIORITY = "priority"; //$NON-NLS-1$
   private static final String OUTPUT_VARIABLE = "outputVariable"; //$NON-NLS-1$
   
   /** Namespace Map for xPath queries */
   private static final Map sNSMap = new HashMap();
   static
   {
      sNSMap.put("aeExt", "urn:extension:state");  //$NON-NLS-1$//$NON-NLS-2$
      sNSMap.put("trt", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
      sNSMap.put(IAeB4PConstants.B4P_PREFIX, IAeB4PConstants.B4P_NAMESPACE); 
   }

   /**
    * @param aDef
    */
   public AePAGraphNodeAdapter(AePeopleActivityDef aDef)
   {
      mDef = aDef;
   }

   /**
    * @see org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNodeAdapter#getIcon()
    */
   public String getIcon()
   {
      return null;
   }

   /**
    * @see org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNodeAdapter#getProperties(org.activebpel.rt.xml.def.AeBaseXmlDef, org.w3c.dom.Document, java.lang.String)
    */
   public IAeXmlDefGraphNodeProperty[] getProperties(AeBaseXmlDef aDef, Document aStateDoc, String aInstancePath)
   {
      AeHIPropertyBuilder builder = new AeHIPropertyBuilder();
      IAeXmlDefGraphNodeProperty[] props = builder.createProperties(aDef, aStateDoc);

      if (props != null)
         return props;
      
      if (isTask())
      {
         return getTaskProperties(aDef, aStateDoc, aInstancePath);
      }
      else
      {
         return getNotificationProperties(aDef, aStateDoc, aInstancePath);
      }
   }
   
   /**
    * Returns properties for people activity that has a task
    * @param aDef
    * @param aStateDoc
    * @param aInstancePath
    */
   private IAeXmlDefGraphNodeProperty[] getTaskProperties(AeBaseXmlDef aDef, Document aStateDoc, String aInstancePath)
   {
      List props = new ArrayList();

      // Task type property 
      String taskType = TASK_TYPE_LOCAL;
      if (getDef().getTask() != null)
          taskType = TASK_TYPE_INLINE;
      props.add(new AeXmlDefGraphNodeProperty(TASK_TYPE_NAME, taskType, false));
      
      // Task reference property for local task
      if (taskType.equals(TASK_TYPE_LOCAL))
         props.add(new AeXmlDefGraphNodeProperty(LOCAL_REFERENCE, getDef().getLocalTask().getReference().toString(), getDef().getLocalTask().getInlineTaskDef().getLocationPath())); 
      else
         props.add(new AeXmlDefGraphNodeProperty(TASK_NAME, getDef().getTask().getName(), false)); 

      // Input variable property
      props.add(new AeXmlDefGraphNodeProperty(INPUT_VARIABLE, getDef().getInputVariable(), true, false)); 
      
      // Output variable property
      props.add(new AeXmlDefGraphNodeProperty(OUTPUT_VARIABLE, getDef().getOutputVariable(), true, false)); 

      // Actual Owner and priority
      if (aStateDoc != null)
      {
         String query = "//bpelObject[@locationPath=\""+aInstancePath+"\"]/aeExt:ExtState/trt:actualOwner"; //$NON-NLS-1$ //$NON-NLS-2$
         Element actualOwner = (Element) AeXPathUtil.selectSingleNodeIgnoreException(aStateDoc, query, sNSMap);
         if (actualOwner != null)
            props.add(new AeXmlDefGraphNodeProperty(ACTUAL_OWNER, AeXmlUtil.getText(actualOwner), true, false));
      
         addPriority(props, aStateDoc, aInstancePath);
      }

      return toArray(props);
   }


   /**
    * Returns properties for people activity that has a notification
    * @param aDef
    * @param aStateDoc
    * @param aInstancePath
    */
   private IAeXmlDefGraphNodeProperty[] getNotificationProperties(AeBaseXmlDef aDef, Document aStateDoc, String aInstancePath)
   {
      List props = new ArrayList();

      // Notification type property 
      String notificationType = NOTIFICATION_TYPE_LOCAL;
      if (getDef().getNotification() != null)
          notificationType = NOTIFICATION_TYPE_INLINE;
      props.add(new AeXmlDefGraphNodeProperty(NOTIFICATION_TYPE_NAME, notificationType, false));
      
      // Notification reference property for local notification
      if (notificationType.equals(NOTIFICATION_TYPE_LOCAL))
      {
         props.add(new AeXmlDefGraphNodeProperty(LOCAL_REFERENCE, getDef().getLocalNotification().getReference().toString(), getDef().getLocalNotification().getInlineNotificationDef().getLocationPath())); 
      }
      else
         props.add(new AeXmlDefGraphNodeProperty(NOTIFICATION_NAME, getDef().getNotification().getName(), false)); 

      // Input variable property
      props.add(new AeXmlDefGraphNodeProperty(INPUT_VARIABLE, getDef().getInputVariable(), true, false)); 

      if (aStateDoc != null)
         addPriority(props, aStateDoc, aInstancePath);
      
      return toArray(props);
   }

   /**
    * Adds priority to the properties from the state document
    * @param aProps
    * @param aStateDoc
    * @param aInstancePath
    */
   private void addPriority(List aProps, Document aStateDoc, String aInstancePath)
   {
      String priorityPath = "//bpelObject[@locationPath=\"" + aInstancePath + "\"]/aeExt:ExtState/trt:taskPriority"; //$NON-NLS-1$//$NON-NLS-2$
      Element priority = (Element) AeXPathUtil.selectSingleNodeIgnoreException(aStateDoc, priorityPath, sNSMap);

      String statePath = "string(//bpelObject[@locationPath=\"" + aInstancePath + "\"]/@" + IAeImplStateNames.STATE_STATE + ")"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
      AeBpelState state = AePARequestUtil.getState(aStateDoc, statePath, sNSMap);

      // Display priority only if execution of the PA has completed (either successfully, or faulted).
      // TODO (vv): Should there be a null check for priority at all? Can priority be null if state is final?
      if (priority != null && state != null
               && (state.equals(AeBpelState.FAULTED) || state.equals(AeBpelState.FINISHED)))
         aProps.add(new AeXmlDefGraphNodeProperty(TASK_PRIORITY, AeXmlUtil.getText(priority), true, false));
   }
   
   /**
    * Converts list of properties to IAeXmlDefGraphNodeProperty array
    * @param aList
    * @return
    */
   private IAeXmlDefGraphNodeProperty[] toArray(List aList)
   {
      IAeXmlDefGraphNodeProperty[] props = new AeXmlDefGraphNodeProperty[aList.size()];
      int i=0;
      for(Iterator iter=aList.iterator(); iter.hasNext(); )
      {
         AeXmlDefGraphNodeProperty prop = (AeXmlDefGraphNodeProperty) iter.next();
         props[i++]=prop;
      }
      return props;
   }
   
   /**
    * @see org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNodeAdapter#getTreeNode()
    */
   public IAeXmlDefGraphNode getTreeNode()
   {
      if (null != mDef.getTask())
      {
         return createInlineTaskTreeNode(mDef.getTask());
      }
      else if (null != mDef.getNotification())
      {
         return createInlineNotificationTreeNode(mDef.getNotification());
      }
      return null;
   }
   
   /**
    * Creates graph node for an inline task.
    */
   private IAeXmlDefGraphNode createInlineTaskTreeNode(AeTaskDef aDef)
   {
      IAeXmlDefGraphNode node = new AeXmlDefGraphNode(aDef.getName(), aDef, aDef.getName(),
               IAeHumanInteractionDisplayTags.TASK_ICON, true);
      return node;
   }
   
   /**
    * Creates graph node for an inline notification.
    */
   private IAeXmlDefGraphNode createInlineNotificationTreeNode(AeNotificationDef aDef)
   {
      IAeXmlDefGraphNode node = new AeXmlDefGraphNode(aDef.getName(), aDef, aDef.getName(),
               IAeHumanInteractionDisplayTags.NOTIFICATION_ICON, true);
      return node;
   }
   
   /**
    * Returns true if people activity has a task or local task else returns false 
    */
   private boolean isTask()
   {
      if ( (getDef().getLocalTask() != null) || (getDef().getTask() != null) )
         return true;
      else
         return false;
         
       
   }
   
   /**
    * @return the def
    */
   protected AePeopleActivityDef getDef()
   {
      return mDef;
   }

   /**
    * @param aDef the def to set
    */
   protected void setDef(AePeopleActivityDef aDef)
   {
      mDef = aDef;
   }

}
