// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/lpg/IAeLogicalPeopleGroupProviderFactory.java,v 1.1 2008/02/01 22:41:37 EWittmann Exp $
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
 * Factory for creating logical people group providers.
 */
public interface IAeLogicalPeopleGroupProviderFactory
{
   /**
    * Given an LPG deployment element (deployed LPG - possibly from the PDD file
    * or a BUnit config), returns a provider that can provide a
    * htd:organizationalEntity.
    * 
    * @param aLPGElement
    */
   public IAeLogicalPeopleGroupProvider createProvider(Element aLPGElement);
}
