// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeExtensionDefRuleVisitor.java,v 1.1 2008/02/21 22:06:39 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import org.activebpel.rt.bpel.def.activity.AeChildExtensionActivityDef;
import org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor;
import org.activebpel.rt.bpel.def.visitors.AeDefTraverser;
import org.activebpel.rt.bpel.def.visitors.AeTraversalVisitor;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.activebpel.rt.xml.def.IAeExtensionObject;
import org.activebpel.rt.xml.def.IAeGetBaseXmlDefAdapter;

/**
 * Helper class to be used when visiting a <code>AeBaseDef</code> but need callbacks on 
 * extension objects. 
 */
public abstract class AeExtensionDefRuleVisitor extends AeAbstractDefVisitor
{
   /**
    * C'tor
    * @param aExpressionVisitor
    */
   public AeExtensionDefRuleVisitor()
   {
      setTraversalVisitor( new AeTraversalVisitor( new AeDefTraverser(), this ) );
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeChildExtensionActivityDef)
    */
   public void visit(AeChildExtensionActivityDef aDef)
   {
      processExtensionObject(aDef.getExtensionObject());
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.xml.def.visitors.AeBaseXmlDefVisitor#visit(org.activebpel.rt.xml.def.AeExtensionElementDef)
    */
   public void visit(AeExtensionElementDef aDef)
   {
      processExtensionObject(aDef.getExtensionObject());
      super.visit(aDef);
   }
   
   /**
    * Process the extension object to get the <code>AeBaseXmlDef</code> object and then 
    * call the acceptExtensionBaseXmlDef method.
    * 
    * @param aExtensionObject
    */
   protected void processExtensionObject(IAeExtensionObject aExtensionObject)
   {
      if (aExtensionObject != null)
      {
         IAeGetBaseXmlDefAdapter adapter = (IAeGetBaseXmlDefAdapter) aExtensionObject.getAdapter(IAeGetBaseXmlDefAdapter.class);
         if (adapter != null)
         {
            AeBaseXmlDef extXmlDef = adapter.getExtensionAsBaseXmlDef();
            acceptExtensionBaseXmlDef(extXmlDef);
         }
      }
   }
   
   /**
    * On visits to <code>AeChildExtensionActivityDef</code> and <code>AeExtensionElementDef</code>
    * this abstract method will be called with the dereferneced <code>AeBaseXmlDef</code>.
    * 
    * @param aDef
    */
   protected abstract void acceptExtensionBaseXmlDef(AeBaseXmlDef aDef);

}
