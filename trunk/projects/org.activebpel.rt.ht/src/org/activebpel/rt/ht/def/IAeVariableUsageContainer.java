//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAeVariableUsageContainer.java,v 1.1 2007/11/08 16:42:40 jbik Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import java.util.Set;

/**
 * Def objects that contain the used variables must implement this interface
 *
 */
public interface IAeVariableUsageContainer
{
   /**
    * Add the used variable Def object to the usage variable container
    * @param aLocationPath
    */
   public void addUsedVariable(String aLocationPath);
   
   /**
    * @return the set of used variables
    */
   public Set getUsedVariables();
}
