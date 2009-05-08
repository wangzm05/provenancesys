// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/bpel/def/visitors/AeLPGVisitor.java,v 1.2 2008/03/14 20:46:52 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.bpel.def.visitors;

import java.util.ArrayList;
import java.util.Collection;

import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor;
import org.activebpel.rt.bpel.def.visitors.AeDefTraverser;
import org.activebpel.rt.bpel.def.visitors.AeTraversalVisitor;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.activebpel.rt.xml.def.IAeGetBaseXmlDefAdapter;

/**
 * Visitor that locates lpgs in process and scopes.
 */
public class AeLPGVisitor extends AeAbstractDefVisitor
{
   /** Holder for logical people groups collected by the visitor. */
   private Collection mLogicalPeopleGroups;

   /**
    * Default constructor.
    */
   protected AeLPGVisitor()
   {
      mLogicalPeopleGroups = new ArrayList();
      setTraversalVisitor(new AeTraversalVisitor(new AeDefTraverser(), this));
   }

   /**
    * Entry point to the visitor to make it visit the BPEL process and collect all logical people groups.
    * 
    * @param aProcessDef
    *            BPEL process def to visit.
    * @return Collection of logical people groups.
    */
   public static Collection getLogicalPeopleGroups(AeProcessDef aProcessDef)
   {
      AeLPGVisitor lpgVisitor = new AeLPGVisitor();
      aProcessDef.accept(lpgVisitor);
      return lpgVisitor.getLogicalPeopleGroups();
   }

   /**
    * If the extension element contains logical people groups, collect them.
    * 
    * @see org.activebpel.rt.xml.def.visitors.AeBaseXmlDefVisitor#visit(org.activebpel.rt.xml.def.AeExtensionElementDef)
    */
   public void visit(AeExtensionElementDef aDef)
   {
      if (null != aDef.getExtensionObject())
      {
         IAeGetBaseXmlDefAdapter adapter = (IAeGetBaseXmlDefAdapter) aDef.getExtensionObject().getAdapter(
                  IAeGetBaseXmlDefAdapter.class);
         if (null != adapter && adapter.getExtensionAsBaseXmlDef() instanceof AeB4PHumanInteractionsDef)
         {
            AeB4PHumanInteractionsDef humanInteractionsDef = (AeB4PHumanInteractionsDef) adapter
                     .getExtensionAsBaseXmlDef();
            AeLogicalPeopleGroupsDef logicalPeopleGroupsDef = humanInteractionsDef.getLogicalPeopleGroupsDef();
            if (null != logicalPeopleGroupsDef)
            {
               mLogicalPeopleGroups.addAll(AeUtil.toList(logicalPeopleGroupsDef.getLogicalPeopleGroupDefs()));
            }
         }
      }
   }

   /**
    * @return Returns the logicalPeopleGroups.
    */
   protected Collection getLogicalPeopleGroups()
   {
      return mLogicalPeopleGroups;
   }
}
