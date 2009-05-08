//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/IAeTaskFilterFactory.java,v 1.1 2008/03/20 22:36:26 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage;

import java.util.Set;

import org.activebpel.rt.b4p.server.storage.filter.AeTaskFilter;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.ht.api.AeGetTasksParam;

/**
 * Factory that is responsible for creation of the storage search filter.
 */
public interface IAeTaskFilterFactory
{
   /**
    * Creates task filter given the task listing request information.
    * @param aPrincipalName
    * @param aRoles
    * @param aGetTasksParam
    * @return AeTaskFilter
    * @throws AeStorageException
    */
   public AeTaskFilter createFilter(String aPrincipalName, Set aRoles, AeGetTasksParam aGetTasksParam) throws AeStorageException; 
}
