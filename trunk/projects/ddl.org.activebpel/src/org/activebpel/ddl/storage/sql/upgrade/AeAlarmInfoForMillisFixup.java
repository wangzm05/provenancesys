//$Header: /Development/AEDevelopment/projects/ddl.org.activebpel/src/org/activebpel/ddl/storage/sql/upgrade/AeAlarmInfoForMillisFixup.java,v 1.1 2005/03/17 21:44:29 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.ddl.storage.sql.upgrade;

import java.util.Date;

/**
 * Represents alarm information found in the database prior to populating the DeadlineMillis column. Used
 * during upgrade from version 1.0 to version 1.1 of ActiveBPEL.
 */
public class AeAlarmInfoForMillisFixup
{
   /** The alarm's process id. */
   private long mProcessId;
   /** The alarm's location path id. */
   private int mLocPathId;
   /** The alarm's deadline. */
   private Date mDeadline;

   /**
    * Simple constructor.
    * 
    * @param aProcessId
    * @param aLocPathId
    * @param aDeadline
    */
   public AeAlarmInfoForMillisFixup(long aProcessId, int aLocPathId, Date aDeadline)
   {
      setProcessId(aProcessId);
      setLocPathId(aLocPathId);
      setDeadline(aDeadline);
   }

   /**
    * Getter for the deadline.
    */
   public Date getDeadline()
   {
      return mDeadline;
   }

   /**
    * Setter for the deadline.
    * 
    * @param aDeadline
    */
   public void setDeadline(Date aDeadline)
   {
      mDeadline = aDeadline;
   }

   /**
    * Getter for the location path id.
    */
   public int getLocPathId()
   {
      return mLocPathId;
   }

   /**
    * Setter for the location path id.
    * 
    * @param aLocPathId
    */
   public void setLocPathId(int aLocPathId)
   {
      mLocPathId = aLocPathId;
   }

   /**
    * Getter for the process id.
    */
   public long getProcessId()
   {
      return mProcessId;
   }

   /**
    * Setter for the process id.
    * 
    * @param aProcessId
    */
   public void setProcessId(long aProcessId)
   {
      mProcessId = aProcessId;
   }
}