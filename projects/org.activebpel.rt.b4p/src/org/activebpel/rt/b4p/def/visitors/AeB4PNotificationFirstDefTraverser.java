// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/AeB4PNotificationFirstDefTraverser.java,v 1.6 2008/03/14 20:46:52 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.visitors;

import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * The external iterator for the IAeB4PDefVisitor interface. Provides the logic for traversing the BPEL4People
 * definition objects, traversing notifications before tasks.
 */
public class AeB4PNotificationFirstDefTraverser extends AeB4PDefTraverser
{
   /**
    * Default c'tor.
    */
   public AeB4PNotificationFirstDefTraverser()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeB4PDefTraverser#traverse(org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef,
    *      org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeB4PHumanInteractionsDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      traverseDocumentationDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
      callAccept(aDef.getLogicalPeopleGroupsDef(), aVisitor);
      callAccept(aDef.getNotificationsDef(), aVisitor);
      callAccept(aDef.getTasksDef(), aVisitor);
   }
}
