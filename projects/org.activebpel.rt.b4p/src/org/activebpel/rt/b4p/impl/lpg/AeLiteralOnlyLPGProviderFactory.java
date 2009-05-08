// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/lpg/AeLiteralOnlyLPGProviderFactory.java,v 1.1 2008/02/27 20:56:43 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.impl.lpg;

import org.w3c.dom.Element;

/**
 * A simple LPG provider factory that only knows how to create a literal
 * provider.
 */
public class AeLiteralOnlyLPGProviderFactory implements IAeLogicalPeopleGroupProviderFactory
{
   /**
    * @see org.activebpel.rt.b4p.impl.lpg.IAeLogicalPeopleGroupProviderFactory#createProvider(org.w3c.dom.Element)
    */
   public IAeLogicalPeopleGroupProvider createProvider(Element aLPGElement)
   {
      return new AeLiteralLogicalPeopleGroupProvider();
   }
}
