//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/task/data/AeProcessTaskResponse.java,v 1.1 2007/12/14 22:55:06 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.task.data;

import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.message.IAeMessageData;

/**
 * Models the process task response message from the people activity life cycle process 
 */
public class AeProcessTaskResponse
{
   /** response identifier */
   private String mIdentifier;
   /** Status of task execution */
   private String mStatus;
   /** output from task execution */
   private IAeMessageData mOutput;
   /** name of fault (if any) thrown by the task */
   private IAeFault mFault;
   /**
    * @return the identifier
    */
   public String getIdentifier()
   {
      return mIdentifier;
   }
   /**
    * @param aIdentifier the identifier to set
    */
   public void setIdentifier(String aIdentifier)
   {
      mIdentifier = aIdentifier;
   }
   /**
    * @return the status
    */
   public String getStatus()
   {
      return mStatus;
   }
   /**
    * @param aStatus the status to set
    */
   public void setStatus(String aStatus)
   {
      mStatus = aStatus;
   }
   /**
    * @return the output
    */
   public IAeMessageData getOutput()
   {
      return mOutput;
   }
   /**
    * @param aOutput the output to set
    */
   public void setOutput(IAeMessageData aOutput)
   {
      mOutput = aOutput;
   }
   /**
    * @return the fault
    */
   public IAeFault getFault()
   {
      return mFault;
   }
   /**
    * @param aFault to set
    */
   public void setFault(IAeFault aFault)
   {
      mFault = aFault;
   }
}
