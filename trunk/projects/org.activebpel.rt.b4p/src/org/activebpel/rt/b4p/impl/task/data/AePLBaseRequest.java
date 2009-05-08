//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/task/data/AePLBaseRequest.java,v 1.5 2008/02/21 17:14:57 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.task.data;

/**
 * Base request class for task and notification request classes
 */
public class AePLBaseRequest
{
   /** Initial state def */
   private AeInitialState mInitialState;
   /** People activity process id */
   private long mPeopleActivityProcessId;
   /** PA instance id within the process. E.g. location id.**/
   private int mPeopleActivityInstanceId;
   /** Map of Input, Output and Faults */
   private AeInterfaceMetadata mInterfaceMetadata;

   /**
    * @return the initialStateDef
    */
   public AeInitialState getInitialState()
   {
      return mInitialState;
   }

   /**
    * @param aInitialStateDef the initialStateDef to set
    */
   public void setInitialState(AeInitialState aInitialStateDef)
   {
      mInitialState = aInitialStateDef;
   }

   /**
    * @return the peopleActivityProcessId
    */
   public long getPeopleActivityProcessId()
   {
      return mPeopleActivityProcessId;
   }

   /**
    * @param aPeopleActivityProcessId the peopleActivityProcessId to set
    */
   public void setPeopleActivityProcessId(long aPeopleActivityProcessId)
   {
      mPeopleActivityProcessId = aPeopleActivityProcessId;
   }
   
   /**
    * Returns the instance id.
    * @return id
    */
   public int getPeopleActivityInstanceId()
   {
      return mPeopleActivityInstanceId;
   }
   
   /**
    * Sets the instance id. E.g. implementation location id.
    * @param aPeopleActivityInstanceId
    */
   public void setPeopleActivityInstanceId(int aPeopleActivityInstanceId)
   {
      mPeopleActivityInstanceId = aPeopleActivityInstanceId;
   }

   /**
    * @return the interfaceMetadata
    */
   public AeInterfaceMetadata getInterfaceMetadata()
   {
      return mInterfaceMetadata;
   }
   /**
    * @param aInterfaceMetadata the interfaceMetadata to set
    */
   public void setInterfaceMetadata(AeInterfaceMetadata aInterfaceMetadata)
   {
      mInterfaceMetadata = aInterfaceMetadata;
   }
}
