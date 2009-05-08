//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/AePersistentB4PManager.java,v 1.2 2008/02/02 19:11:36 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server;

import java.util.Map;

import org.activebpel.rt.b4p.server.storage.IAeTaskStorage;
import org.activebpel.rt.bpel.server.engine.storage.AePersistentStoreFactory;

/**
 * B4P managers with storage support for quering inbox task listings.
 */
public class AePersistentB4PManager extends AeServerB4PManager
{
   /**
    * Ctor.
    * @param aConfig
    */
   public AePersistentB4PManager(Map aConfig)
   {
      super(aConfig);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.AeManagerAdapter#create()
    */
   public void create() throws Exception
   {
      setStorage((IAeTaskStorage) AePersistentStoreFactory.getInstance().getCustomStorage("AeB4PTaskStorage")); //$NON-NLS-1$
   }
}
