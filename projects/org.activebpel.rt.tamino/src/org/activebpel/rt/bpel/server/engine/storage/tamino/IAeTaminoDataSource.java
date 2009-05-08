//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/IAeTaminoDataSource.java,v 1.4 2007/08/17 00:57:36 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.bpel.server.engine.storage.tamino;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource;

/**
 * An interface that defines the set of methods needed to give access to the
 * Tamino database.
 */
public interface IAeTaminoDataSource extends IAeXMLDBDataSource
{
   /** A key into the config map (the URL to the Tamino server). */
   public static final String URL = "URL";  //$NON-NLS-1$
   /** A key into the config map (the Tamino database to use). */
   public static final String DATABASE = "Database";  //$NON-NLS-1$
   /** A key into the config map (the Tamino collection to use). */
   public static final String COLLECTION = "Collection"; //$NON-NLS-1$
   /** A key into the config map (a windows domain to use for Tamino auth). */
   public static final String DOMAIN = "Domain"; //$NON-NLS-1$

   /**
    * Returns the name of the collection this data source is configured for.
    */
   public String getCollectionName();
}
