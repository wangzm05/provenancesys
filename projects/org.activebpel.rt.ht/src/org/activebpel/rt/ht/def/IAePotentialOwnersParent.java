//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAePotentialOwnersParent.java,v 1.1.4.1 2008/04/21 16:15:16 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

/**
* Parent elements of the 'potentialOwners' element must implement this interface
* <p>
* NOTE: The exception is the 'peopleAssigments' element.
* </p>
*/
public interface IAePotentialOwnersParent
{
   /**
    * @param aPotentialOwners - the potentialOwners element to be set
    */
   public void setPotentialOwners(AePotentialOwnersDef aPotentialOwners);
}
