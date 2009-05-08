//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/finders/IAeHumanIteractionDefFinder.java,v 1.1 2007/12/18 04:06:28 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.visitors.finders; 

import java.util.Collection;

import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef;

/**
 * Interface for classes that find scoped resources with a human interaction.
 * Instances of this are used to find a specific resource like a logical people
 * group or build a list of all logical people groups that are in scope.
 */
public interface IAeHumanIteractionDefFinder
{
   /**
    * Finds the resource on the human interaction or returns null
    * @param aDef
    */
   public abstract void find(AeB4PHumanInteractionsDef aDef);
   
   /**
    * Returns true if the finder has completed its mission and found what it
    * was looking for.
    */
   public abstract boolean isDone();
   
   /**
    * Returns the results of the find operation.
    */
   public Collection getResults();

}
 