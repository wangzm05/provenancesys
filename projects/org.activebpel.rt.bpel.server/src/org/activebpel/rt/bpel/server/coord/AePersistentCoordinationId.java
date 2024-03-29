//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/AePersistentCoordinationId.java,v 1.3 2006/02/24 16:37:30 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2005 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.bpel.server.coord;


/**
 * Class to construct a coordination id based on a id value generated by the storage layer.
 */
public class AePersistentCoordinationId implements IAeCoordinationId
{
   /**
    * context id.
    */
   private String mIdentifier = null;
   
   /**
    * primary key associated in the storage layer.
    */
   private long mProcessId;

   /**
    * Default constructor 
    * 
    * @param aProcessId
    * @param aIdentifier
    */
   public AePersistentCoordinationId(long aProcessId, String aIdentifier)
   {
      mProcessId = aProcessId;
      mIdentifier = aIdentifier;
   }
   
   /**
    * Returns the coordination context id.
    */
   public String getIdentifier()
   {
      return mIdentifier;
   }   
   
   /**
    * @return Returns the process id associated with this identifier.
    */
   public long getProcessId()
   {
      return mProcessId;
   }
}
