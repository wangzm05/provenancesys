//$Header: /Development/AEDevelopment/projects/ddl.org.activebpel/src/org/activebpel/ddl/storage/sql/upgrade/AeQueuedReceive1_0_8_3.java,v 1.2 2005/07/19 19:24:38 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.ddl.storage.sql.upgrade;


/**
 * Represents a Queued Receive object in the database for version 1.0.8.3 of the DB (or at least the subset of
 * the object that is required for upgrading).
 */
public class AeQueuedReceive1_0_8_3
{
   /** The queued receive id. */
   private int mQueuedReceiveId;
   /** The queued receive's new match hash. */
   private int mNewMatchHash;
   /** The queued receive's new correlation hash. */
   private int mNewCorrelationHash;

   /**
    * Constructs the queued receive from the given id and hash values.
    * 
    * @param aQueuedReceiveId
    * @param aMatchHash
    * @param aCorrelationHash
    */
   public AeQueuedReceive1_0_8_3(int aQueuedReceiveId, int aMatchHash, int aCorrelationHash)
   {
      setQueuedReceiveId(aQueuedReceiveId);
      setNewMatchHash(aMatchHash);
      setNewCorrelationHash(aCorrelationHash);
   }
   
   /**
    * @return Returns the newCorrelationHash.
    */
   public int getNewCorrelationHash()
   {
      return mNewCorrelationHash;
   }

   /**
    * @param aNewCorrelationHash The newCorrelationHash to set.
    */
   protected void setNewCorrelationHash(int aNewCorrelationHash)
   {
      mNewCorrelationHash = aNewCorrelationHash;
   }

   /**
    * @return Returns the newMatchHash.
    */
   public int getNewMatchHash()
   {
      return mNewMatchHash;
   }

   /**
    * @param aNewMatchHash The newMatchHash to set.
    */
   protected void setNewMatchHash(int aNewMatchHash)
   {
      mNewMatchHash = aNewMatchHash;
   }

   /**
    * @return Returns the queuedReceiveId.
    */
   public int getQueuedReceiveId()
   {
      return mQueuedReceiveId;
   }

   /**
    * @param aQueuedReceiveId The queuedReceiveId to set.
    */
   protected void setQueuedReceiveId(int aQueuedReceiveId)
   {
      mQueuedReceiveId = aQueuedReceiveId;
   }
}