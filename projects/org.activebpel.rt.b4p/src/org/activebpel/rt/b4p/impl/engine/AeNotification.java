// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/engine/AeNotification.java,v 1.2 2008/02/16 22:29:48 mford Exp $
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
 * Passed to the B4P manager when a notification needs to be
 * executed.
 */
public class AeNotification extends AeB4PQueueObject
{
   /**
    * C'tor.
    * 
    * @param aProcessId
    * @param aLocationId
    * @param aLocationPath
    * @param aMessageData
    * @param aPeopleActivityDef
    */
   public AeNotification(long aProcessId, int aLocationId, String aLocationPath, IAeMessageData aMessageData,
         AePeopleActivityDef aPeopleActivityDef)
   {
      super(aProcessId, aLocationId, aLocationPath, aMessageData, aPeopleActivityDef);
   }
}
