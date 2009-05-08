// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/engine/AeB4PQueueObject.java,v 1.1 2008/02/16 22:29:48 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.impl.engine;

import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.message.IAeMessageData;

/**
 * Abstract base class for tasks and notifications.
 */
public class AeB4PQueueObject
{
   /** The bpel process id. */
   private long mProcessId;
   /** Location ID of the people activity. */
   private int mLocationId;
   /** The location path of the people activity. */
   private String mLocationPath;
   /** Task message data. */
   private IAeMessageData mMessageData;
   /** The people activity def. */
   private AePeopleActivityDef mPeopleActivityDef;

   /**
    * C'tor.
    * 
    * @param aProcessId
    * @param aLocationId
    * @param aLocationPath
    * @param aMessageData
    * @param aPeopleActivityDef
    */
   public AeB4PQueueObject(long aProcessId, int aLocationId, String aLocationPath,
         IAeMessageData aMessageData, AePeopleActivityDef aPeopleActivityDef)
   {
      setProcessId(aProcessId);
      setLocationId(aLocationId);
      setLocationPath(aLocationPath);
      setMessageData(aMessageData);
      setPeopleActivityDef(aPeopleActivityDef);
   }

   /**
    * @return Returns the locationId.
    */
   public int getLocationId()
   {
      return mLocationId;
   }

   /**
    * @param aLocationId the locationId to set
    */
   protected void setLocationId(int aLocationId)
   {
      mLocationId = aLocationId;
   }

   /**
    * @return Returns the data.
    */
   public IAeMessageData getMessageData()
   {
      return mMessageData;
   }

   /**
    * @param aData the data to set
    */
   protected void setMessageData(IAeMessageData aData)
   {
      mMessageData = aData;
   }

   /**
    * @return Returns the def.
    */
   public AePeopleActivityDef getPeopleActivityDef()
   {
      return mPeopleActivityDef;
   }

   /**
    * @param aDef the def to set
    */
   protected void setPeopleActivityDef(AePeopleActivityDef aDef)
   {
      mPeopleActivityDef = aDef;
   }

   /**
    * @return Returns the processId.
    */
   public long getProcessId()
   {
      return mProcessId;
   }

   /**
    * @param aProcessId the processId to set
    */
   protected void setProcessId(long aProcessId)
   {
      mProcessId = aProcessId;
   }

   /**
    * @return Returns the locationPath.
    */
   public String getLocationPath()
   {
      return mLocationPath;
   }

   /**
    * @param aLocationPath the locationPath to set
    */
   protected void setLocationPath(String aLocationPath)
   {
      mLocationPath = aLocationPath;
   }
}
