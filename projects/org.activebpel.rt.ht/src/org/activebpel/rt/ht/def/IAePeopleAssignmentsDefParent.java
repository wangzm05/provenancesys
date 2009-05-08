//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAePeopleAssignmentsDefParent.java,v 1.3 2007/12/18 04:04:34 mford Exp $
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
 * Parent elements of the 'peopleAssignments' element must implement this interface
 */
public interface IAePeopleAssignmentsDefParent
{
   /**
    * Assigns a people assignments def to its parent.
    * 
    * @param aPeopleAssignments - the peopleAssignments element to be set
    */
   public void setPeopleAssignments(AePeopleAssignmentsDef aPeopleAssignments);
   
   /**
    * Getter for the people assignments def
    */
   public AePeopleAssignmentsDef getPeopleAssignments();
}
