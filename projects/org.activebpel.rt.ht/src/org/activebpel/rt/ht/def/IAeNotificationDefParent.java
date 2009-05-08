//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAeNotificationDefParent.java,v 1.3 2008/01/21 22:02:44 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import java.util.Iterator;

/**
 * Parent elements of the 'notification' element must implement this interface
 */
public interface IAeNotificationDefParent
{
   /**
    * 
    * @param aNotification - the notification element to be set
    */
   public void addNotification(AeNotificationDef aNotification);
   
   /**
    * @return an Iterator of the notification defs
    */
   public Iterator getNotificationDefs();
}
