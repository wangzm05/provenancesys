//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/request/AeLPGMapCreationVisitor.java,v 1.5.4.2 2008/04/14 21:25:30 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.request;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.impl.lpg.AeLogicalPeopleGroupImpl;
import org.activebpel.rt.b4p.impl.lpg.AeLogicalPeopleGroupImplFinder;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.AeScopeDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.bpel.def.util.AeLocationPathUtils;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleContext;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.w3c.dom.Element;

/**
 * Visitor to tranvers AeProcessDef and collect all LPG Defs, execute them and produces a map
 */
public class AeLPGMapCreationVisitor extends AePABaseVisitor
{
   /** Map to hold LPG to Organizational Entity Map */
   private Map mLPGMap;

   /**
    * C'tor
    * @param aContext
    */
   public AeLPGMapCreationVisitor(IAeActivityLifeCycleContext aContext)
   {
      super(aContext);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      if (AeUtil.notNullOrEmpty(aDef.getLogicalPeopleGroup()))
         extractLPGs(aDef.getLogicalPeopleGroup());
   }

   /**
    * @return the lPGMap
    */
   protected Map getLPGMap()
   {
      if (mLPGMap == null)
         mLPGMap = new HashMap();
      return mLPGMap;
   }

   /**
    * @param aMap the lPGMap to set
    */
   protected void setLPGMap(Map aMap)
   {
      mLPGMap = aMap;
   }

   /**
    * extract LPGs if present from the extension elements
    * @param aLPGName
    */
   protected void extractLPGs(QName aLPGName)
   {
      AeBaseXmlDef def = (AeBaseXmlDef) mStack.peek();
      AeLogicalPeopleGroupImpl impl;
      try
      {
         IAeBpelObject bpelObject = findBpelObject(def);
         impl = new AeLogicalPeopleGroupImplFinder().find(bpelObject, aLPGName);
         Element e = impl.evaluate(bpelObject);
         getLPGMap().put(impl.getDef(), e);
      }
      catch (AeBusinessProcessException ex)
      {
         AeException.logError(ex);
      }
   }

   /**
    * Traverses upto nearest scope of PA and returns Bpel Object of the scope/process
    * @param aDef
    */
   protected IAeBpelObject findBpelObject(AeBaseXmlDef aDef)
   {
      if (aDef == null)
         return null;
      
      AeBaseXmlDef def = aDef.getParentXmlDef();
      String locationPath = null;
      while(locationPath == null)
      {
         if ( (def instanceof AeScopeDef) || (def instanceof AeProcessDef) || (def instanceof AeActivityScopeDef))
         {
            locationPath = def.getLocationPath(); 
         }
         else
            def = def.getParentXmlDef();
      }
      
      String peopleActivityLocationPath = getContext().getBpelObject().getLocationPath();
      locationPath = AeLocationPathUtils.addInstanceInfo(peopleActivityLocationPath, locationPath);
      return getContext().getProcess().findBpelObject(locationPath);
   }
}
