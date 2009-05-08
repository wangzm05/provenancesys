// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/task/data/AeProcessTaskRequest.java,v 1.5 2008/02/16 22:29:48 mford Exp $
// ///////////////////////////////////////////////////////////////////////////
// PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc. Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
// ///////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.task.data;

import org.activebpel.rt.ht.def.AeTaskDef;

/**
 * Models the process task request message sent to people activity life cycle
 * process
 */
public class AeProcessTaskRequest extends AePLBaseRequest
{
   /** HT Def */
   private AeTaskDef mTaskDef;
   
   /**
    * @return the taskDef
    */
   public AeTaskDef getTaskDef()
   {
      return mTaskDef;
   }

   /**
    * @param aTaskDef the taskDef to set
    */
   public void setTaskDef(AeTaskDef aTaskDef)
   {
      mTaskDef = aTaskDef;
   }
}
