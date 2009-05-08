//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/ext/AeLogicalPeopleGroupExtensionObject.java,v 1.5 2008/03/11 21:41:01 vvelusamy Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.ext; 


import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.impl.lpg.AeLogicalPeopleGroupCopyStrategy;
import org.activebpel.rt.b4p.impl.lpg.AeLogicalPeopleGroupImpl;
import org.activebpel.rt.b4p.impl.lpg.AeLogicalPeopleGroupImplFinder;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeStaticAnalysisException;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.def.activity.support.AeToDef;
import org.activebpel.rt.bpel.def.activity.support.IAeFromSpecExtension;
import org.activebpel.rt.bpel.def.activity.support.IAeToSpecExtension;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation;
import org.activebpel.rt.bpel.impl.activity.assign.IAeExtensionCopyStrategy;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeXmlDefUtil;
import org.activebpel.rt.xml.def.IAeAdapter;
import org.activebpel.rt.xml.def.IAeExtensionObject;

/**
 * Implements from-spec and/or to-spec attribute that reads/writes a value
 * to a logical people group.
 */
public class AeLogicalPeopleGroupExtensionObject implements IAeExtensionObject, IAeFromSpecExtension, IAeToSpecExtension
{
   /** name of the LPG attribute */
   private static final QName ATTR_LPG = new QName(IAeB4PConstants.B4P_NAMESPACE, "logicalPeopleGroup"); //$NON-NLS-1$
   
   /**
    * @see org.activebpel.rt.bpel.def.activity.support.IAeToSpecExtension#createCopyStrategy(org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation, org.activebpel.rt.bpel.def.activity.support.AeToDef)
    */
   public IAeExtensionCopyStrategy createCopyStrategy(IAeCopyOperation aCopyOperation, AeToDef aToDef) throws AeBusinessProcessException
   {
      QName lpgName = getLPGName(aToDef);
      
      AeLogicalPeopleGroupImpl impl = findLogicalPeopleGroup(aCopyOperation.getContext().getBpelObject(), lpgName);
      return new AeLogicalPeopleGroupCopyStrategy(impl);
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.support.IAeFromSpecExtension#executeFromSpec(org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation, org.activebpel.rt.bpel.def.activity.support.AeFromDef)
    */
   public Object executeFromSpec(IAeCopyOperation aCopyOperation, AeFromDef aDef) throws AeBusinessProcessException
   {
      IAeBpelObject bpelObject = aCopyOperation.getContext().getBpelObject();
      
      QName lpgName = getLPGName(aDef);
      AeLogicalPeopleGroupImpl lpg = findLogicalPeopleGroup(bpelObject, lpgName);
      if (lpg == null)
      {
         // fixme (MF) null means a static analysis failure, remove throw when impl'd
         throw new AeStaticAnalysisException("Unresolved logical people group reference: " + lpgName); //$NON-NLS-1$
      }
      return lpg.evaluate(bpelObject);      
   }
   
   /**
    * Finds the enclosing LPG or null if not found.
    * @param aBpelObject
    * @param aName
    * @throws AeBusinessProcessException
    */
   protected AeLogicalPeopleGroupImpl findLogicalPeopleGroup(IAeBpelObject aBpelObject, QName aName) throws AeBusinessProcessException
   {
      AeLogicalPeopleGroupImplFinder finder = new AeLogicalPeopleGroupImplFinder();
      return finder.find(aBpelObject, aName);
   }
   
   /**
    * @see org.activebpel.rt.xml.def.IAeExtensionObject#getAdapter(java.lang.Class)
    */
   public IAeAdapter getAdapter(Class aClass)
   {
      if (aClass.isAssignableFrom(getClass()))
         return this;
      return null;
   }

   /**
    * Decodes the attribute value for the LPG into a QName
    * @param aDef
    */
   protected QName getLPGName(AeBaseXmlDef aDef)
   {
      String name = aDef.getExtensionAttributeDef(ATTR_LPG).getValue();
      
      QName qname = AeXmlDefUtil.parseQName(aDef, name);
      return qname;
   }
   
   /*
    * @see org.activebpel.rt.bpel.def.activity.support.IAeSpecExtension#getPropertyName(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   public String getPropertyName()
   {
      return ATTR_LPG.getLocalPart();
   }
   
   /*
    * @see org.activebpel.rt.bpel.def.activity.support.IAeSpecExtension#getPropertyValue(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   public String getPropertyValue(AeBaseXmlDef aDef)
   {
      return getLPGName(aDef).getLocalPart();
   }
}
 