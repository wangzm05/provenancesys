//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/visitors/AeHtDefInlineTaskTraverser.java,v 1.1.4.2 2008/04/14 21:26:06 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def.visitors;

import org.activebpel.rt.ht.def.AeLocalNotificationDef;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * 
 */
public class AeHtDefInlineTaskTraverser extends AeHtDefTraverser
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeHtDefTraverser#traverse(org.activebpel.rt.ht.def.AeLocalNotificationDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void traverse(AeLocalNotificationDef aDef, IAeBaseXmlDefVisitor aVisitor)
   {
      super.traverse(aDef, aVisitor);
      callAccept(aDef.getInlineNotificationDef(), aVisitor);
   }

}
