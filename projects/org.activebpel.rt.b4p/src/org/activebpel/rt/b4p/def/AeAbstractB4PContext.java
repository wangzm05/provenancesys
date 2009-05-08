// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeAbstractB4PContext.java,v 1.2 2008/02/17 21:36:03 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.def;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.activebpel.rt.xml.def.IAeExtensionObject;
import org.activebpel.rt.xml.def.IAeGetBaseXmlDefAdapter;

/**
 * Provides a gateway to enclosing the enclosing HumanInteractions Def
 * 
 */
public abstract class AeAbstractB4PContext implements IAeB4PContext
{
   /** The extension element which we provide context for */
   private AeBaseXmlDef mExtDef;
   
   /**
    * C'tor
    * 
    * @param aDef
    */
   public AeAbstractB4PContext(AeBaseXmlDef aDef)
   {
      setExtensionElementDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.IAeB4PContext#getEnclosingHumanInteractionsDef()
    */
   public AeB4PHumanInteractionsDef getEnclosingHumanInteractionsDef()
   {
      AeB4PHumanInteractionsDef enclosingDef = null;
      AeBaseXmlDef def = getParentDef();
      
      while (def.getParentXmlDef() != null)
      {   
         def = def.getParentXmlDef();
         
         AeExtensionElementDef extDef = def.getExtensionElementDef(
               new QName(IAeB4PConstants.B4P_NAMESPACE, IAeB4PDefConstants.TAG_HUMAN_INTERACTIONS));
         
         if(extDef != null)
         {
            IAeExtensionObject extObj = extDef.getExtensionObject();
            IAeGetBaseXmlDefAdapter adapter = (IAeGetBaseXmlDefAdapter) extObj.getAdapter(IAeGetBaseXmlDefAdapter.class);
            enclosingDef = (AeB4PHumanInteractionsDef) adapter.getExtensionAsBaseXmlDef();
            break;
         }
      }
      
      return enclosingDef;
   }
   
   /**
    * @return the AeBaseXmlDef where the search for the enclosing humanInteractions def is to begin.
    */
   protected abstract AeBaseXmlDef getParentDef();

   /**
    * @return Returns the extension element def.
    */
   protected AeBaseXmlDef getExtensionElementDef()
   {
      return mExtDef;
   }

   /**
    * @param aDef set the extension element def
    */
   protected void setExtensionElementDef(AeBaseXmlDef aDef)
   {
      mExtDef = aDef;
   }

}
