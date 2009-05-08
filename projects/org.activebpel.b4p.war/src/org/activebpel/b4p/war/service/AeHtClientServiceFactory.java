//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/AeHtClientServiceFactory.java,v 1.1 2008/02/20 15:58:19 PJayanetti Exp $
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
 * Default factory implementation.
 */
public class AeHtClientServiceFactory implements IAeHtClientServiceFactory
{
   /**
    * @see org.activebpel.b4p.war.service.IAeHtClientServiceFactory#createAeClientService(java.net.URL, java.lang.String, java.lang.String)
    */
   public IAeTaskAeClientService createAeClientService(URL aEndpoint, String aUsername, String aPassword)
   {
      return new AeInternalClientService( aEndpoint, aUsername, aPassword );      
   }

   /**
    * @see org.activebpel.b4p.war.service.IAeHtClientServiceFactory#createHtClientService(java.net.URL, java.lang.String, java.lang.String)
    */
   public IAeTaskHtClientService createHtClientService(URL aEndpoint, String aUsername, String aPassword)
   {
      return new AeHtClientService( aEndpoint, aUsername, aPassword );
   }
}
