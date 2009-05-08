//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/IAeB4PAlarmDef.java,v 1.1 2007/12/14 01:17:46 mford Exp $
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
 * Describes b4p defs that contain alarm data, either a for or until def.
 */
public interface IAeB4PAlarmDef
{

   /**
    * @param aDef the def to be set on the parent
    */
   public void setForDef(AeB4PForDef aDef);
   
   /**
    * Getter for the for def
    */
   public AeB4PForDef getForDef();

   /**
    * @param aDef the def to be set on the parent
    */
   public void setUntilDef(AeB4PUntilDef aDef);
   
   /**
    * Getter for the until def
    */
   public AeB4PUntilDef getUntilDef();
}
