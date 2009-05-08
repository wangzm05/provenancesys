//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAeLocalResourceDef.java,v 1.1 2007/12/18 04:04:34 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def; 

import javax.xml.namespace.QName;

/**
 * Interface defines common methods for local tasks and local notifications. 
 */
public interface IAeLocalResourceDef extends IAePeopleAssignmentsDefParent, IAePriorityDefParent
{
   /**
    * Gets the name of the notification that is being referenced by this def
    */
   public QName getReference();
   
   /**
    * Setter for the name of the notification that is being referenced by this def.
    * @param aRef
    */
   public void setReference(QName aRef);
}
 