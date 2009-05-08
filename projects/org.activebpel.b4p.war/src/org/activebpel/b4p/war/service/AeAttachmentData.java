//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/AeAttachmentData.java,v 1.2 2008/01/18 22:28:07 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.service;

import javax.activation.DataHandler;

import org.activebpel.rt.ht.api.AeAttachmentInfo;

/**
 * Contains information about a downloaded attachment.
 */
public class AeAttachmentData extends AeAttachmentInfo
{
   /** Data handler for attachment data. */
   private DataHandler mDataHandler;

   /**
    * Ctor.
    * @param aInfo
    * @param aHandler
    */
   public AeAttachmentData(AeAttachmentInfo aInfo, DataHandler aHandler)
   {
      this(aInfo.getTaskId(), aInfo.getName(), aInfo.getContentType(), aInfo.getAccessType(), aHandler);
   }

   /**
    * Constructs the AeAttachmentData.
    * @param aTaskRef attachment task ref.
    * @param aName attachment name
    * @param aContentType mime type.
    * @param aAccessType access type.
    * @param aHandler content data handler.
    */
   public AeAttachmentData(String aTaskRef, String aName, String aContentType, String aAccessType, DataHandler aHandler)
   {
      super(aTaskRef, aName, aContentType, aAccessType);
      mDataHandler = aHandler;
   }

   /**
    * @return content data as data handler.
    */
   public DataHandler getDataHandler()
   {
      return mDataHandler;
   }

   /**
    * @return true if data is available.
    */
   public boolean isHasData()
   {
      return getDataHandler() != null;
   }
}
