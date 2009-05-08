//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/task/data/AeProcessNotificationRequest.java,v 1.1 2007/12/14 22:55:06 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.task.data;

import org.activebpel.rt.ht.def.AeNotificationDef;

/**
 * Models the process task request message sent to people activity life cycle process
 */
public class AeProcessNotificationRequest extends AePLBaseRequest
{
   /** HT Notification Def */
   private AeNotificationDef mNotificationDef;
   /**
    * @return the taskDef
    */
   public AeNotificationDef getNotificationDef()
   {
      return mNotificationDef;
   }
   /**
    * @param aNotificationDef the taskDef to set
    */
   public void setNotificationDef(AeNotificationDef aNotificationDef)
   {
      mNotificationDef = aNotificationDef;
   }
   
}
