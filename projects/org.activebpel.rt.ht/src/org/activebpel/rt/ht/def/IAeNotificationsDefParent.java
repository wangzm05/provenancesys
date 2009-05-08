//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAeNotificationsDefParent.java,v 1.2 2008/03/14 20:45:25 EWittmann Exp $
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
 * Parent elements of the 'notifications' element must implement this interface
 */
public interface IAeNotificationsDefParent
{
   /**
    * @param aNotifications - the notifications element to be set
    */
   public void setNotificationsDef(AeNotificationsDef aNotifications);
}
