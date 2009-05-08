// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/ext/AeB4PHumanInteractionsExtensionObject.java,v 1.12 2008/03/24 18:44:59 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.def.ext;

import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.def.AeB4PBaseDef;
import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsContext;
import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef;
import org.activebpel.rt.b4p.def.IAeB4PContext;
import org.activebpel.rt.b4p.def.io.AeB4PIO;
import org.activebpel.rt.b4p.impl.AeHumanInteractionsGraphNodeAdapter;
import org.activebpel.rt.b4p.impl.AeHumanInteractionsImpl;
import org.activebpel.rt.bpel.def.validation.IAeExtensionUsageAdapter;
import org.activebpel.rt.bpel.impl.activity.IAeExtensionLifecycleAdapter;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.activebpel.rt.xml.def.IAeAdapter;
import org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNodeAdapter;

/**
 * Human Interactions extension object impl.
 */
public class AeB4PHumanInteractionsExtensionObject extends AeAbstractB4PExtensionObject implements IAeExtensionUsageAdapter
{
   /**
    * Getter for the def
    */
   public AeB4PHumanInteractionsDef getHumanInteractionsDef()
   {
      return (AeB4PHumanInteractionsDef) getDef();
   }

   /**
    * @see org.activebpel.rt.b4p.def.ext.AeAbstractB4PExtensionObject#getAdapter(java.lang.Class)
    */
   public IAeAdapter getAdapter(Class aClass)
   {
      if (aClass == IAeExtensionLifecycleAdapter.class)
         return new AeHumanInteractionsImpl(getHumanInteractionsDef());
      else if (aClass == IAeXmlDefGraphNodeAdapter.class)
         return new AeHumanInteractionsGraphNodeAdapter(getHumanInteractionsDef());
      return super.getAdapter(aClass);
   }

   /**
    * @see org.activebpel.rt.b4p.def.ext.AeAbstractB4PExtensionObject#deserialize(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   protected AeB4PBaseDef deserialize(AeBaseXmlDef aDef) throws AeException
   {
      // deserialize to Def model
      return (AeB4PBaseDef)AeB4PIO.deserialize(((AeExtensionElementDef)aDef).getExtensionElement());
   }

   /**
    * @see org.activebpel.rt.b4p.def.ext.AeAbstractB4PExtensionObject#createB4PContext(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   protected IAeB4PContext createB4PContext(AeBaseXmlDef aDef)
   {
      AeExtensionElementDef hiDef = (AeExtensionElementDef) aDef;
      return new AeB4PHumanInteractionsContext(hiDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAeExtensionUsageAdapter#getRequiredExtensions()
    */
   public List getRequiredExtensions()
   {
      return sRequiredExtensions;
   }
}
