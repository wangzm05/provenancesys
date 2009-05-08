//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/IAeB4PContextParent.java,v 1.1 2007/12/08 12:06:50 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def; 

/**
 * Parent interface for a def that can have a {@link IAeB4PContext} 
 */
public interface IAeB4PContextParent
{
   /**
    * Setter for the context
    * @param aContext
    */
   public void setContext(IAeB4PContext aContext);
   
   /**
    * Getter for the context
    */
   public IAeB4PContext getContext();
}
 