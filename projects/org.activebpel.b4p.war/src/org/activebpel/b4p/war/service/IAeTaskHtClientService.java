//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/IAeTaskHtClientService.java,v 1.3 2008/02/13 06:55:07 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.service;

import org.activebpel.rt.ht.api.AeGetTasksParam;
import org.w3c.dom.Element;

/**
 * Defines core methods needed to access the WS-HT task client operations.
 */
public interface IAeTaskHtClientService
{
   /**
    * Queries the ht server and returns list of htd:tTaskAbstract objects.
    * @param aParam getMyTaskAbstracts detail
    * @return wsht api getMyTaskAbstractsResponse
    * @throws AeTaskServiceException
    */
   public Element getMyTaskAbstracts(AeGetTasksParam aParam) throws AeTaskServiceException;

   /**
    * Queries the ht server and returns list of htd:tTask objects.
    * @param aParam getMyTasks detail
    * @return wsht api getMyTasksResponse
    * @throws AeTaskServiceException
    */
   public Element getMyTasks(AeGetTasksParam aParam) throws AeTaskServiceException;

   /**
    * Executes a task command operation such as claim.
    * @param aRequestMessageElement wsht api command element
    * @throws AeTaskServiceException
    */
   public Element executeRequest(Element aRequestMessageElement) throws AeTaskServiceException;

   /**
    * Adds given data as an attachment.
    * @param aIdentifier
    * @param aAttachmentName
    * @param aContentType
    * @param aData
    * @throws AeTaskServiceException
    */
   public void addAttachment(String aIdentifier, String aAttachmentName, String aContentType, Object aData) throws AeTaskServiceException;

}
