// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeB4PPeopleActivityContext.java,v 1.1 2008/01/11 20:01:29 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.def;

import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * provides a gateway from a PeopleActivity element to enclosing 
 * HumanInteractions elements
 */
public class AeB4PPeopleActivityContext extends AeAbstractB4PContext
{
   /**
    * C'tor
    * 
    * @param aDef
    */
   public AeB4PPeopleActivityContext(AeBaseXmlDef aDef)
   {
      super(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.AeAbstractB4PContext#getParentDef()
    */
   protected AeBaseXmlDef getParentDef()
   {
      return getExtensionElementDef();
   }
}
