//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAeLocalNotificationDef.java,v 1.1 2007/12/18 04:04:34 mford Exp $
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
 * Interface for local notifications. This element appears in ht and b4p so we
 * need a common interface to share code that deals with local notifications. 
 */
public interface IAeLocalNotificationDef extends IAeLocalResourceDef
{
   /**
    * Getter for the notification def that was inlined.
    */
   public AeNotificationDef getInlineNotificationDef();
}
 