//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAePresentationElementsDefParent.java,v 1.1 2007/10/04 15:44:43 jbik Exp $
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
 * Parent elements of the 'presentationElements' element must implement this interface
 */
public interface IAePresentationElementsDefParent
{

   /**
    * @param aPresentationElements - the priority 'presentationElements' to be set
    */
   public void setPresentationElements(AePresentationElementsDef aPresentationElements);
}
