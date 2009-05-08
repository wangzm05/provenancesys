//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/AeEscalationNotificationHTFunctionContext.java,v 1.3.4.2 2008/04/14 21:24:48 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function; 

import java.util.Collections;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.impl.task.data.AeHumanTaskContext;
import org.activebpel.rt.ht.IAeHtFunctionContext;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.w3c.dom.Element;

/**
 * Version of the {@link IAeHtFunctionContext} that is used when creating the
 * <code>htp:humanTaskContext</code> for a notfication created by an escalation.
 * 
 * In this case, the notification's priority and peopleAssignments must be evaluated.
 * It's possible that the expressions in these defs will contain references to
 * the notification's input data, parent task's data, and processVariables.
 */
public class AeEscalationNotificationHTFunctionContext implements
      IAeHtFunctionContext
{
   /** maps prefixes to namespaces */
   private static Map sMap = Collections.singletonMap("trt", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
   private static final QName ORG_ENTITY = new QName(IAeHtDefConstants.DEFAULT_HT_NS, IAeHtDefConstants.TAG_ORGANIZATIONAL_ENTITY);
   
   /** name of the notification - used to determine if the notification fields the request or if the parent task handles it */
   private String mNotificationName;
   /** context */
   private AeHumanTaskContext mHumanTaskContext;
   /** input data for the notification */
   private Element mInput;
   /** context for the notification's parent task */
   private IAeHtFunctionContext mParentTaskContext;
   
   /**
    * @param aNotificationName
    * @param aContext
    * @param aInput
    * @param aParentTaskContext
    */
   public AeEscalationNotificationHTFunctionContext(String aNotificationName, AeHumanTaskContext aContext, Element aInput, IAeHtFunctionContext aParentTaskContext)
   {
      setNotificationName(aNotificationName);
      setHumanTaskContext(aContext);
      setInput(aInput);
      setParentTaskContext(aParentTaskContext);
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getActualOwner(java.lang.String)
    */
   public Element getActualOwner(String aTaskName)
   {
      // relying on static analysis to detect invalid task names.
      return getParentTaskContext().getActualOwner(aTaskName);
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getBusinessAdministrators(java.lang.String)
    */
   public Element getBusinessAdministrators(String aTaskName)
   {
      if (isNotification(aTaskName))
      {
         AeExtensionElementDef extElementDef = getHumanTaskContext().getPeopleAssignments().getBusinessAdministrators().getExtensionElementDef(ORG_ENTITY);
         return extElementDef.getExtensionElement();
      }
      else
      {
         return getParentTaskContext().getBusinessAdministrators(null);
      }
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getExcludedOwners(java.lang.String)
    */
   public Element getExcludedOwners(String aTaskName)
   {
      return getParentTaskContext().getExcludedOwners(null);
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getInput(java.lang.String, java.lang.String)
    */
   public Object getInput(String aPartName, String aTaskName)
   {
      if (isNotification(aTaskName))
      {
         Element part = (Element) AeXPathUtil.selectSingleNodeIgnoreException(getInput(), "trt:part[@name='" + aPartName +"']", sMap);  //$NON-NLS-1$ //$NON-NLS-2$
         Object data = AeXmlUtil.getFirstSubElement(part);
         if (data == null)
         {
            // FIXMEQ (b4p) seems like we should be converting this type - need the info in the meta data - only for simple types
            data = AeXmlUtil.getText(part);
         }
         return data;
      }
      else
      {
         return getParentTaskContext().getInput(aPartName, null);
      }
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getPotentialOwners(java.lang.String)
    */
   public Element getPotentialOwners(String aTaskName)
   {
      return getParentTaskContext().getPotentialOwners(null);
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getTaskInitiator(java.lang.String)
    */
   public Element getTaskInitiator(String aTaskName)
   {
      return getParentTaskContext().getTaskInitiator(null);
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getTaskPriority(java.lang.String)
    */
   public int getTaskPriority(String aTaskName)
   {
      if (isNotification(aTaskName))
         return getHumanTaskContext().getPriority();
      return getParentTaskContext().getTaskPriority(null);
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getTaskStakeholders(java.lang.String)
    */
   public Element getTaskStakeholders(String aTaskName)
   {
      return getParentTaskContext().getTaskStakeholders(null);
   }

   /**
    * Returns true if the taskName param points to the current task or is null.
    * @param aTaskName
    */
   protected boolean isNotification(String aTaskName)
   {
      return aTaskName == null || aTaskName.equals(getNotificationName());
   }

   /**
    * @return the humanTaskContext
    */
   protected AeHumanTaskContext getHumanTaskContext()
   {
      return mHumanTaskContext;
   }

   /**
    * @param aHumanTaskContext the humanTaskContext to set
    */
   protected void setHumanTaskContext(AeHumanTaskContext aHumanTaskContext)
   {
      mHumanTaskContext = aHumanTaskContext;
   }

   /**
    * @return the input
    */
   protected Element getInput()
   {
      return mInput;
   }

   /**
    * @param aInput the input to set
    */
   protected void setInput(Element aInput)
   {
      mInput = aInput;
   }

   /**
    * @return the parentTaskContext
    */
   protected IAeHtFunctionContext getParentTaskContext()
   {
      return mParentTaskContext;
   }

   /**
    * @param aParentTaskContext the parentTaskContext to set
    */
   protected void setParentTaskContext(IAeHtFunctionContext aParentTaskContext)
   {
      mParentTaskContext = aParentTaskContext;
   }

   /**
    * @return the notificationName
    */
   protected String getNotificationName()
   {
      return mNotificationName;
   }

   /**
    * @param aNotificationName the notificationName to set
    */
   protected void setNotificationName(String aNotificationName)
   {
      mNotificationName = aNotificationName;
   }
}
 