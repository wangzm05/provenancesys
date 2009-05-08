//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAePriorityDefParent.java,v 1.2 2007/12/18 04:04:34 mford Exp $
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
 * Parent elements of the'priority' element must implement this interface
 */
public interface IAePriorityDefParent
{
   /**
    * Getter for the priority def
    */
   public AePriorityDef getPriority();

   /**
    * @param aPriority - the priority element to be set
    */
   public void setPriority(AePriorityDef aPriority);
}
