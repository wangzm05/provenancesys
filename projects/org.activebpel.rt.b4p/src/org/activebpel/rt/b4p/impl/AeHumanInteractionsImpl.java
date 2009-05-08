//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/AeHumanInteractionsImpl.java,v 1.9 2008/03/14 20:46:52 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl; 

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.IAeProcessTaskConstants;
import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef;
import org.activebpel.rt.b4p.impl.lpg.AeLogicalPeopleGroupImpl;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.AeAbstractExtensionImpl;
import org.activebpel.rt.ht.IAeWSHTConstants;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Provides runtime support for the human interactions elements - primarily the
 * initialization and state save/restore for the logical people groups.
 */
public class AeHumanInteractionsImpl extends AeAbstractExtensionImpl
{
   /** maps name of LPG to its impl object */
   private Map mLogicalPeopleGroups = new HashMap();
   /** def object */
   private AeB4PHumanInteractionsDef mDef;
   
   /** map of ns prefixes to ns values */
   protected static final Map sNSMap = new HashMap();
   static
   {
      sNSMap.put("trt", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
      sNSMap.put("tlc", IAeProcessTaskConstants.TASK_LIFECYCLE_NS); //$NON-NLS-1$
      sNSMap.put("tpa", IAeProcessTaskConstants.TASK_LC_PROCESS_NAMESPACE); //$NON-NLS-1$
      sNSMap.put("htd", IAeWSHTConstants.WSHT_NAMESPACE); //$NON-NLS-1$
      sNSMap.put(IAeB4PConstants.B4P_PREFIX, IAeB4PConstants.B4P_NAMESPACE); 
   };
   
   /**
    * Ctor
    * @param aDef
    */
   public AeHumanInteractionsImpl(AeB4PHumanInteractionsDef aDef)
   {
      setDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.AeAbstractExtensionImpl#onInstalled(org.activebpel.rt.bpel.impl.IAeBpelObject)
    */
   public void onInstalled(IAeBpelObject aBpelObject)
   {
      AeLogicalPeopleGroupsDef lGroupsDef = getDef().getLogicalPeopleGroupsDef();
      if (lGroupsDef == null)
         return;
      for(Iterator it=lGroupsDef.getLogicalPeopleGroupDefs(); it.hasNext();)
      {
         AeLogicalPeopleGroupDef def = (AeLogicalPeopleGroupDef) it.next();
         AeLogicalPeopleGroupImpl impl = new AeLogicalPeopleGroupImpl(def);
         mLogicalPeopleGroups.put(def.getName(), impl);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.AeAbstractExtensionImpl#onRestore(org.w3c.dom.Element)
    */
   public void onRestore(Element aElement)
   {
      QName hiQName = new QName(IAeB4PConstants.B4P_NAMESPACE, IAeB4PConstants.HI_STATE_ELEMENT);
      Element hiState = AeXmlUtil.findSubElement(aElement, hiQName);
      if (hiState == null)
         return;
      
      List lpgs = AeXPathUtil.selectNodesIgnoreException(hiState, IAeB4PConstants.B4P_PREFIX+":logicalPeopleGroup", sNSMap); //$NON-NLS-1$
      for(Iterator iter=lpgs.iterator(); iter.hasNext(); )
      {
         Element lpgElement = (Element) iter.next();
         Element orgEntity = (Element) AeXPathUtil.selectSingleNodeIgnoreException(lpgElement, "htd:organizationalEntity", sNSMap); //$NON-NLS-1$
         String name = lpgElement.getAttribute("name"); //$NON-NLS-1$
         AeLogicalPeopleGroupImpl impl = (AeLogicalPeopleGroupImpl)getLogicalPeopleGroups().get(name);
         impl.setAssignedValue(orgEntity);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.AeAbstractExtensionImpl#onSave(org.w3c.dom.Element)
    */
   public void onSave(Element aElement)
   {
      Element lpgElement = null;
      for(Iterator iter=getLogicalPeopleGroups().keySet().iterator(); iter.hasNext(); )
      {
         String lpgName = (String)iter.next();
         AeLogicalPeopleGroupImpl impl = getLogicalPeopleGroup(lpgName);
         Element orgEntity = impl.getAssignedValue();
         if (orgEntity != null)
         {
            if (lpgElement == null)
            {
               lpgElement = AeXmlUtil.addElementNS(aElement, IAeB4PConstants.B4P_NAMESPACE, IAeB4PConstants.B4P_PREFIX+":"+IAeB4PConstants.HI_STATE_ELEMENT); //$NON-NLS-1$
            }
            Element lpg = AeXmlUtil.addElementNS(lpgElement, IAeB4PConstants.B4P_NAMESPACE, IAeB4PConstants.B4P_PREFIX+":logicalPeopleGroup"); //$NON-NLS-1$
            lpg.setAttribute("name", lpgName); //$NON-NLS-1$
            Node node = lpg.getOwnerDocument().importNode(orgEntity, true);
            lpg.appendChild(node);
         }
      }
   }
   
   /**
    * Gets the LPG with the specified name or null if not contained within the
    * human interaction at this scope.
    * @param aName
    */
   public AeLogicalPeopleGroupImpl getLogicalPeopleGroup(String aName)
   {
      return (AeLogicalPeopleGroupImpl) getLogicalPeopleGroups().get(aName);
   }

   /**
    * @return the logicalPeopleGroups
    */
   protected Map getLogicalPeopleGroups()
   {
      return mLogicalPeopleGroups;
   }

   /**
    * @param aLogicalPeopleGroups the logicalPeopleGroups to set
    */
   protected void setLogicalPeopleGroups(Map aLogicalPeopleGroups)
   {
      mLogicalPeopleGroups = aLogicalPeopleGroups;
   }

   /**
    * @return the def
    */
   public AeB4PHumanInteractionsDef getDef()
   {
      return mDef;
   }

   /**
    * @param aDef the def to set
    */
   public void setDef(AeB4PHumanInteractionsDef aDef)
   {
      mDef = aDef;
   }
}
 