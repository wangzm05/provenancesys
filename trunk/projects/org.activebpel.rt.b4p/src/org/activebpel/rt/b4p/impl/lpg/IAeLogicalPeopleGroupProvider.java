//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/lpg/IAeLogicalPeopleGroupProvider.java,v 1.2 2008/02/01 22:41:37 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.lpg; 

import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.w3c.dom.Element;

/**
 * Interface that provides organizational entity elements for the logical
 * people groups. This data comes from the PDD. 
 */
public interface IAeLogicalPeopleGroupProvider
{
   /**
    * Returns a tOrganizationalEntity for the given LPG
    * 
    * @param aDef
    * @param aLPGElement
    */
   public Element evaluate(AeLogicalPeopleGroupDef aDef, Element aLPGElement);
}
 