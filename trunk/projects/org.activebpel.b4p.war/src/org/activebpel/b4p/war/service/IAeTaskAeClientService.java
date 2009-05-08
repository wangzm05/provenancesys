//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/IAeTaskAeClientService.java,v 1.5 2008/02/27 19:20:21 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.service;

import org.activebpel.rt.ht.api.AeGetTasksParam;
import org.w3c.dom.Element;

/**
 * Defines core methods needed to access the internal ae task operations.
 */
public interface IAeTaskAeClientService
{
   /**
    * Authenticates the given credentials.
    * @return true if succesful.
    * @throws AeTaskServiceException
    */
   public boolean authenticate() throws AeTaskServiceException;

   /**
    * Returns the task instance data.
    * @param aIdentifier task id
    * @return trt:taskInstance element.
    * @throws AeTaskServiceException
    */
   public Element getTaskInstance(String aIdentifier) throws AeTaskServiceException;
   
   /**
    * Queries the ht server and returns list of htd:tTask objects using the AE API.
    * This method allows additional filtering such as paging and column sorting
    * when compared to the wsht api getMyTasks.
    * @param aParam getMyTasks detail
    * @return wsht api getMyTasksResponse
    * @throws AeTaskServiceException
    */
   public Element getTasks(AeGetTasksParam aParam) throws AeTaskServiceException;
   
   /**
    * Executes a task command operation such as updateComment.
    * @param aRequestMessageElement ae taskOperation api command element
    * @throws AeTaskServiceException
    */
   public Element executeRequest(Element aRequestMessageElement) throws AeTaskServiceException;
   
   /**
    * Returns attachment data including attachment.
    * @param aIdentifier
    * @param aAttachmentId
    * @throws AeTaskServiceException
    */
   public AeAttachmentData getAttachmentData(String aIdentifier, String aAttachmentId) throws AeTaskServiceException;


}
