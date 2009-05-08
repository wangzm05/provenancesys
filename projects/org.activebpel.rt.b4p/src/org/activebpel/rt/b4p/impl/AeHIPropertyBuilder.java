//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/AeHIPropertyBuilder.java,v 1.3 2008/02/13 01:52:47 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef;
import org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor;
import org.activebpel.rt.bpel.impl.IAeImplStateNames;
import org.activebpel.rt.ht.IAeWSHTConstants;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationsDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTasksDef;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.graph.AeXmlDefGraphNodeProperty;
import org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNodeProperty;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Builder that builds the properties for HI extension element
 */
public class AeHIPropertyBuilder extends AeAbstractB4PDefVisitor
{

   /** State document */
   private Document mStateDocument;
   /** property name constants */
   private static final String PORT_TYPE = "portType"; //$NON-NLS-1$
   private static final String OPERATION = "operation"; //$NON-NLS-1$
   
   /** Properties */
   private IAeXmlDefGraphNodeProperty[] mProps;
   
   /** Namespace Map for xPath queries */
   private static final Map sNSMap = new HashMap();
   static
   {
      sNSMap.put("aeExt", "urn:extension:state");  //$NON-NLS-1$//$NON-NLS-2$
      sNSMap.put("htd", IAeWSHTConstants.WSHT_NAMESPACE); //$NON-NLS-1$
      sNSMap.put(IAeB4PConstants.B4P_PREFIX, IAeB4PConstants.B4P_NAMESPACE); 
   }
   
   /**
    * This method calls respective property builder method  
    * @param aDef
    * @param aStateDoc
    */
   public IAeXmlDefGraphNodeProperty[] createProperties(AeBaseXmlDef aDef, Document aStateDoc)
   {
      setStateDocument(aStateDoc);
      callAccept(aDef);
      return mProps;
   }


   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef)
    */
   public void visit(AeLogicalPeopleGroupDef aDef)
   {
      if (getStateDocument() == null)
         return;
      
      String locPath = aDef.getLocationPath().substring(0, aDef.getLocationPath().indexOf("/humanInteractions")); //$NON-NLS-1$
      Element elem = null;
      // Find appropriate scope to look for extensions based on the location path of extension
      if (isProcessLevel(locPath))
      {
         elem = getStateDocument().getDocumentElement();
      }
      else
      {
         String query = "//bpelObject[@locationPath='"+locPath+"']"; //$NON-NLS-1$ //$NON-NLS-2$
         elem = (Element) AeXPathUtil.selectSingleNodeIgnoreException(getStateDocument().getDocumentElement(), query, sNSMap);
      }
      List lpgs = AeXPathUtil.selectNodesIgnoreException(elem, "aeExt:ExtState/b4p:logicalPeopleGroups/b4p:logicalPeopleGroup", sNSMap); //$NON-NLS-1$
      for(Iterator iter=lpgs.iterator(); iter.hasNext(); )
      {
         Element lpg = (Element) iter.next();
         String name = lpg.getAttribute("name"); //$NON-NLS-1$
         String state = lpg.getAttribute("state"); //$NON-NLS-1$
         Element orgEntity = (Element) AeXPathUtil.selectSingleNodeIgnoreException(lpg, "htd:organizationalEntity", sNSMap); //$NON-NLS-1$
         if (name.equals(aDef.getName()))
         {
            mProps = new AeXmlDefGraphNodeProperty[3];
            mProps[0] = new AeXmlDefGraphNodeProperty(name, AeXMLParserBase.documentToString(orgEntity, true), true);
            mProps[1] = getStatePropertyToRemove();
            mProps[2] = new AeXmlDefGraphNodeProperty(IAeImplStateNames.STATE_STATE, state, false);
            return;
         }
      }
      mProps = new AeXmlDefGraphNodeProperty[1];
      mProps[0] = getStatePropertyToRemove();
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      mProps = new AeXmlDefGraphNodeProperty[3];
      mProps[0] = new AeXmlDefGraphNodeProperty(PORT_TYPE, aDef.getTaskInterfaceDef().getPortType().toString(), false);
      mProps[1] = new AeXmlDefGraphNodeProperty(OPERATION, aDef.getTaskInterfaceDef().getOperation(), false);
      mProps[2] = getStatePropertyToRemove(); 
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef)
    */
   public void visit(AeB4PHumanInteractionsDef aDef)
   {
      setRemoveState();
   }


   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef)
    */
   public void visit(AeLogicalPeopleGroupsDef aDef)
   {
      setRemoveState();
   }


   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      mProps = new AeXmlDefGraphNodeProperty[3];
      mProps[0] = new AeXmlDefGraphNodeProperty(PORT_TYPE, aDef.getNotificationInterfaceDef().getPortType().toString(), false);
      mProps[1] = new AeXmlDefGraphNodeProperty(OPERATION, aDef.getNotificationInterfaceDef().getOperation(), false);
      mProps[2] = getStatePropertyToRemove(); 
   }


   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationsDef)
    */
   public void visit(AeNotificationsDef aDef)
   {
      setRemoveState();
   }


   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTasksDef)
    */
   public void visit(AeTasksDef aDef)
   {
      setRemoveState();
   }

   /**
    * Sets remove flag for current state property
    */
   private void setRemoveState()
   {
      mProps = new AeXmlDefGraphNodeProperty[1];
      mProps[0] = getStatePropertyToRemove(); 
   }
   /**
    * Calls accept on def
    * @param aDef
    */
   public void callAccept(AeBaseXmlDef aDef)
   {
      if (aDef != null)
         aDef.accept(this);
   }
   /**
    * Returns true if the locationPath is pointing at process level
    * @param aLocationPath
    */
   private boolean isProcessLevel(String aLocationPath)
   {
      if (aLocationPath.endsWith("process")) //$NON-NLS-1$
         return true;
      else
         return false;
   }
   
   /**
    * Sets state property to remove
    */
   private AeXmlDefGraphNodeProperty getStatePropertyToRemove()
   {
      AeXmlDefGraphNodeProperty prop = new AeXmlDefGraphNodeProperty(IAeImplStateNames.STATE_STATE, null, false);
      prop.setRemove(true);
      return prop;
   }
   
   /**
    * @return the stateDocument
    */
   protected Document getStateDocument()
   {
      return mStateDocument;
   }


   /**
    * @param aStateDocument the stateDocument to set
    */
   protected void setStateDocument(Document aStateDocument)
   {
      mStateDocument = aStateDocument;
   }
   

}
