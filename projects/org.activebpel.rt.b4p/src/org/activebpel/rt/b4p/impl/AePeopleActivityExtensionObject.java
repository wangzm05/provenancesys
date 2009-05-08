//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/AePeopleActivityExtensionObject.java,v 1.12 2008/03/24 18:44:59 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl; 

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.def.AeB4PBaseDef;
import org.activebpel.rt.b4p.def.AeB4PPeopleActivityContext;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.b4p.def.IAeB4PContext;
import org.activebpel.rt.b4p.def.IAeB4PDefConstants;
import org.activebpel.rt.b4p.def.ext.AeAbstractB4PExtensionObject;
import org.activebpel.rt.b4p.def.io.AeB4PIO;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.AeVariablesDef;
import org.activebpel.rt.bpel.def.activity.AeChildExtensionActivityDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.validation.IAeExtensionUsageAdapter;
import org.activebpel.rt.bpel.def.visitors.IAeImplicitVariablesAdapter;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleAdapter;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeExtensionAttributeDef;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.activebpel.rt.xml.def.IAeAdapter;
import org.activebpel.rt.xml.def.IAeExtensionDefUnderstoodAdapter;
import org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNodeAdapter;

/**
 * People Activity extension object 
 */
public class AePeopleActivityExtensionObject extends AeAbstractB4PExtensionObject implements
      IAeExtensionDefUnderstoodAdapter, IAeImplicitVariablesAdapter, IAeExtensionUsageAdapter
{
   /** set of understood elements for the activity */
   private static Set sUnderstoodElements = new HashSet();
   static
   {
      sUnderstoodElements.add(new QName(IAeB4PConstants.B4P_NAMESPACE, IAeB4PDefConstants.TAG_DOCUMENTATION));
      sUnderstoodElements.add(new QName(IAeB4PConstants.B4P_NAMESPACE, IAeB4PDefConstants.TAG_LOCAL_TASK));
      sUnderstoodElements.add(new QName(IAeB4PConstants.B4P_NAMESPACE, IAeB4PDefConstants.TAG_REMOTE_TASK));
      sUnderstoodElements.add(new QName(IAeB4PConstants.B4P_NAMESPACE, IAeB4PDefConstants.TAG_LOCAL_NOTIFICATION));
      sUnderstoodElements.add(new QName(IAeB4PConstants.B4P_NAMESPACE, IAeB4PDefConstants.TAG_REMOTE_NOTIFICATION));
      sUnderstoodElements.add(new QName(IAeHtDefConstants.DEFAULT_HT_NS, IAeB4PDefConstants.TAG_TASK));
      sUnderstoodElements.add(new QName(IAeHtDefConstants.DEFAULT_HT_NS, IAeB4PDefConstants.TAG_NOTIFICATION));
      sUnderstoodElements.add(new QName(IAeB4PConstants.B4P_NAMESPACE, IAeB4PDefConstants.TAG_SCHEDULED_ACTIONS));
      sUnderstoodElements.add(new QName(IAeB4PConstants.B4P_NAMESPACE, IAeB4PDefConstants.TAG_ATTACHMENT_PROPAGATION));
   }
   
   /** set of understood attributes for the activity */
   private static Set sUnderstoodAttributes = new HashSet();
   static
   {
      sUnderstoodAttributes.add(new QName("", IAeB4PDefConstants.ATTR_SKIPABLE)); //$NON-NLS-1$
      sUnderstoodAttributes.add(new QName("", IAeB4PDefConstants.ATTR_INPUTVARIABLE)); //$NON-NLS-1$
      sUnderstoodAttributes.add(new QName("", IAeB4PDefConstants.ATTR_OUTPUTVARIABLE)); //$NON-NLS-1$
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.ext.AeAbstractB4PExtensionObject#getAdapter(java.lang.Class)
    */
   public IAeAdapter getAdapter(Class aClass)
   {
      if (getDef() != null)
      {
         if (aClass.isAssignableFrom(getDef().getClass()))
         {
            return ((AePeopleActivityDef)getDef());
         }
      }
      if (IAeActivityLifeCycleAdapter.class == aClass)
      {
         return new AePeopleActivityImpl((AePeopleActivityDef) getDef());
      }
      if (IAeXmlDefGraphNodeAdapter.class == aClass)
      {
         return new AePAGraphNodeAdapter((AePeopleActivityDef) getDef());
      }
      if (IAeImplicitVariablesAdapter.class == aClass)
         return this;
      return super.getAdapter(aClass);
   }
   
   /**
    * @see org.activebpel.rt.xml.def.IAeExtensionDefUnderstoodAdapter#isUnderstood(org.activebpel.rt.xml.def.AeExtensionAttributeDef)
    */
   public boolean isUnderstood(AeExtensionAttributeDef aExtensionAttributeDef)
   {
      return sUnderstoodAttributes.contains(aExtensionAttributeDef.getQName()); 
   }

   /**
    * @see org.activebpel.rt.xml.def.IAeExtensionDefUnderstoodAdapter#isUnderstood(org.activebpel.rt.xml.def.AeExtensionElementDef)
    */
   public boolean isUnderstood(AeExtensionElementDef aExtensionElementDef)
   {
      return sUnderstoodElements.contains(AeXmlUtil.getElementType(aExtensionElementDef.getExtensionElement())); 
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeImplicitVariablesAdapter#createImplicitVariables(org.activebpel.rt.bpel.def.activity.AeChildExtensionActivityDef)
    */
   public void createImplicitVariables(AeChildExtensionActivityDef aDef)
   {
      AeProcessDef processDef = AeDefUtil.getProcessDef(aDef);
      AeVariableDef def = processDef.getVariableDef(IAeB4PConstants.ATTACHMENTS_VARIABLE);
      if (def == null)
      {
         def = new AeVariableDef();
         def.setName(IAeB4PConstants.ATTACHMENTS_VARIABLE);
         def.setType(IAeB4PConstants.ATTACHMENTS_VARIABLE_TYPE);
         def.setImplicit(true);
         if (processDef.getVariablesDef() == null)
            processDef.setVariablesDef(new AeVariablesDef());
         processDef.getVariablesDef().addVariableDef(def);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAeExtensionUsageAdapter#getRequiredExtensions()
    */
   public List getRequiredExtensions()
   {
      return sRequiredExtensions;
   }

   /**
    * @see org.activebpel.rt.b4p.def.ext.AeAbstractB4PExtensionObject#deserialize(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   protected AeB4PBaseDef deserialize(AeBaseXmlDef aDef) throws AeException
   {
      AeChildExtensionActivityDef extensionDef = (AeChildExtensionActivityDef)aDef;
      return AeB4PIO.deserializePeopleActivity(extensionDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.ext.AeAbstractB4PExtensionObject#createB4PContext(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   protected IAeB4PContext createB4PContext(AeBaseXmlDef aDef)
   {
      return new AeB4PPeopleActivityContext((AeChildExtensionActivityDef) aDef);
   }
}
