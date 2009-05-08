// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeB4PHumanInteractionsContext.java,v 1.1 2008/01/11 20:01:29 dvilaverde Exp $
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
 * Provides a gateway from a HumanInteractions element to enclosing 
 * HumanInteractions elements
 */
public class AeB4PHumanInteractionsContext extends AeAbstractB4PContext
{
   /**
    * C'tor
    * 
    * @param aDef
    */
   public AeB4PHumanInteractionsContext(AeBaseXmlDef aDef)
   {
      super(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.AeAbstractB4PContext#getParentDef()
    */
   protected AeBaseXmlDef getParentDef()
   {
      // When starting at a AeB4PHumanInteractionsDef and searching for an enclosing 
      // AeB4PHumanInteractionsDef we must first get the parent xml def in order to step 
      // outside of the current scope in order to prevent the IAeB4PContext from finding 
      // the current AeB4PHumanInteractionsDef and prevent a stack overflow.
      return getExtensionElementDef().getParentXmlDef();
   }
   
}
