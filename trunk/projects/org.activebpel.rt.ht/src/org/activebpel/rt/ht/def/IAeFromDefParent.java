//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAeFromDefParent.java,v 1.2 2008/01/15 18:05:45 mford Exp $
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
* Parent elements of the 'from' element must implement this interface
*/
public interface IAeFromDefParent
{

   /**
    * @param aFrom - the from def to be set
    */
   public void setFrom(AeFromDef aFrom);
   
   /**
    * Getter for the from def.
    */
   public AeFromDef getFrom();
}
