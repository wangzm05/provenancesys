//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/ht/AeTaskStateBasedHtFunctionContext.java,v 1.7.2.2 2008/04/14 21:25:30 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function.ht; 

import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.def.io.AeB4PIO;
import org.activebpel.rt.ht.IAeHtFunctionContext;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.AeUserDef;
import org.activebpel.rt.ht.def.io.AeHtIO;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * Implementation of the ht function context that pulls its data from a task
 * state DOM element. 
 */
public class AeTaskStateBasedHtFunctionContext implements IAeHtFunctionContext
{
   /** maps prefixes to namespaces */
   private static Map sMap = new HashMap();
   static
   {
      sMap.put("trt", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
      sMap.put("xsi", IAeConstants.W3C_XML_SCHEMA_INSTANCE); //$NON-NLS-1$
   };
   
   /** provides task state for the current task */
   private Element mTaskState;
   
   /** provides task state for the parent task. Used whenever the taskName param is not null and doesn't match our task name name */
   private Element mParentTaskState;
   
   /**
    * Ctor
    * 
    * @param aTaskState
    * @param aParentTaskState
    */
   public AeTaskStateBasedHtFunctionContext(Element aTaskState, Element aParentTaskState)
   {
      setTaskState(aTaskState);
      setParentTaskState(aParentTaskState);
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getActualOwner(java.lang.String)
    */
   public Element getActualOwner(String aTaskName)
   {
      return selectUser(aTaskName, "trt:context/trt:actualOwner[not(@xsi:nil='true')]"); //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getBusinessAdministrators(java.lang.String)
    */
   public Element getBusinessAdministrators(String aTaskName)
   {
      Element element = selectOrgEntity(aTaskName, "trt:context/trt:businessAdministrators[not(@xsi:nil='true')]"); //$NON-NLS-1$
      return element;
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getExcludedOwners(java.lang.String)
    */
   public Element getExcludedOwners(String aTaskName)
   {
      Element element = selectOrgEntity(aTaskName, "trt:context/trt:excludedOwners[not(@xsi:nil='true')]"); //$NON-NLS-1$
      return element;
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getInput(java.lang.String, java.lang.String)
    */
   public Object getInput(String aPartName, String aTaskName)
   {
      Element part  = selectElement(aTaskName, "trt:operational/trt:input/trt:part[@name='" + aPartName +"']"); //$NON-NLS-1$ //$NON-NLS-2$
      
      // static analysis should catch an invalid part arg
      if (part == null)
         return null;
      
      Object data = AeXmlUtil.getFirstSubElement(part);
      if (data == null)
      {
         // FIXMEQ (b4p-render) seems like we should be converting this type - need the info in the meta data - only for simple types
         data = AeXmlUtil.getText(part);
      }
      return data;
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getPotentialOwners(java.lang.String)
    */
   public Element getPotentialOwners(String aTaskName)
   {
      Element element = selectOrgEntity(aTaskName, "trt:context/trt:potentialOwners[not(@xsi:nil='true')]"); //$NON-NLS-1$
      return element;
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getTaskInitiator(java.lang.String)
    */
   public Element getTaskInitiator(String aTaskName)
   {
      Element element = selectUser(aTaskName, "trt:context/trt:taskInitiator[not(@xsi:nil='true')]"); //$NON-NLS-1$
      return element;
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getTaskPriority(java.lang.String)
    */
   public int getTaskPriority(String aTaskName)
   {
      String priorityStr = selectText(aTaskName, "trt:context/trt:priority"); //$NON-NLS-1$
      
      try
      {
         return Integer.parseInt(priorityStr);
      }
      catch(Exception e)
      {
         return 0;
      }
   }

   /**
    * @see org.activebpel.rt.ht.IAeHtFunctionContext#getTaskStakeholders(java.lang.String)
    */
   public Element getTaskStakeholders(String aTaskName)
   {
      Element element = selectOrgEntity(aTaskName, "trt:context/trt:taskStakeholders[not(@xsi:nil='true')]"); //$NON-NLS-1$
      return element;
   }

   /**
    * Selects an element from the given xpath and converts it to an organizational entity. 
    * @param aTaskName
    * @param aXPath
    */
   protected Element selectOrgEntity(String aTaskName, String aXPath)
   {
      Element element = selectElement(aTaskName, aXPath);
      Element converted = convertToOrgEntity(element);
      return converted;
   }
   
   /**
    * Converts the element to an org entity element
    * @param aElement
    */
   protected Element convertToOrgEntity(Element aElement)
   {
      AeOrganizationalEntityDef orgDef = null;
      if (aElement != null)
      {
         try
         {
            orgDef = AeB4PIO.deserializeAsOrganizationalEntity(aElement);
         }
         catch (AeException e)
         {
            // ignore the exception, we'll reply w/ an empty def below
         }
      }
      
      if (orgDef == null)
         orgDef = new AeOrganizationalEntityDef();
      
      return AeB4PIO.serialize2Element(orgDef);
   }
   
   /**
    * Selects a user element using the xpath provided and returns as an ht:user
    * element.
    * @param aTaskName
    * @param aXpath
    */
   protected Element selectUser(String aTaskName, String aXpath)
   {
      AeUserDef userDef = new AeUserDef();
      String userValue = selectText(aTaskName, aXpath);
      userDef.setValue(userValue);
      return AeHtIO.serialize2Element(userDef);
   }

   /**
    * Convenience method that selects text from the task state using the DOM context, 
    * ns map, and provided xpath
    * @param aTaskName
    * @param aXpath
    */
   protected String selectText(String aTaskName, String aXpath)
   {
      Element context = getTaskContext(aTaskName);
      return AeXPathUtil.selectText(context, aXpath, sMap);
   }

   /**
    * Convenience method that selects an element from the task state using the DOM
    * context, ns map, and provided xpath
    * @param aTaskName
    * @param aXpath
    */
   protected Element selectElement(String aTaskName, String aXpath)
   {
      Element context = getTaskContext(aTaskName);
      return (Element) AeXPathUtil.selectSingleNodeIgnoreException(context, aXpath, sMap);
   }

   /**
    * Gets the element to use as the context for the expression. If the task name
    * matches the name of the current task or if it's null then you get the 
    * current task. Otherwise, you'll get the parent task.
    * @param aTaskName
    */
   protected Element getTaskContext(String aTaskName)
   {
      Element context;
      if (aTaskName != null && !aTaskName.equals(getTaskName()))
      {
         context = getParentTaskState();
      }
      else
      {
         context = getTaskState();
      }
      return context;
   }

   /**
    * @return the taskState
    */
   protected Element getTaskState()
   {
      return mTaskState;
   }

   /**
    * @param aTaskState the taskState to set
    */
   protected void setTaskState(Element aTaskState)
   {
      mTaskState = aTaskState;
   }

   /**
    * @return the taskName
    */
   protected String getTaskName()
   {
      return AeXPathUtil.selectText(getTaskState(), "trt:name", sMap); //$NON-NLS-1$
   }

   /**
    * @return the parentTaskState
    */
   protected Element getParentTaskState()
   {
      return mParentTaskState;
   }

   /**
    * @param aParentTaskState the parentTaskState to set
    */
   protected void setParentTaskState(Element aParentTaskState)
   {
      mParentTaskState = aParentTaskState;
   }
}
 