//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/IAeHtClientServiceFactory.java,v 1.1 2008/02/20 15:58:19 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.service;

import java.net.URL;

/**
 * Factory to create client services
 */
public interface IAeHtClientServiceFactory
{
   /**
    * Creates and return the IAeTaskHtClientService.
    * @param aUsername
    * @param aPassword
    * @return IAeHtClientService instance
    */
   public IAeTaskHtClientService createHtClientService(URL aEndpoint, String aUsername, String aPassword);

   /**
    * Creates and return the IAeTaskAeClientService.
    * @param aUsername
    * @param aPassword
    */
   public IAeTaskAeClientService createAeClientService(URL aEndpoint, String aUsername, String aPassword);
}
