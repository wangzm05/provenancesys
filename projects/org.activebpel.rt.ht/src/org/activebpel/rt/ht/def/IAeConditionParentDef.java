//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAeConditionParentDef.java,v 1.1 2008/01/11 01:49:26 mford Exp $
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
 * A def that can have a condition as a child
 */
public interface IAeConditionParentDef
{
   /**
    * Setter for the condition
    * @param aDef
    */
   public void setConditionDef(AeConditionDef aDef);
   
   /**
    * Getter for the condition
    */
   public AeConditionDef getConditionDef();
}
 