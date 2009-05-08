//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeHtAttachmentInfoDeserializerBase.java,v 1.1 2008/01/18 22:51:53 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api.io;

import org.activebpel.rt.ht.api.AeAttachmentInfo;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;
import org.w3c.dom.Element;

/**
 * Base class for deserializing api:tAttachmentInfo objects.
 */
public abstract class AeHtAttachmentInfoDeserializerBase extends AeHtDeserializerBase
{
   /**
    * Task Id
    */
   private String mTaskId;
   
   /**
    * @return the taskId
    */
   public String getTaskId()
   {
      return mTaskId;
   }

   /**
    * @param aTaskId the taskId to set
    */
   public void setTaskId(String aTaskId)
   {
      mTaskId = aTaskId;
   }

   /**
    * Deserializes a wsht api:tAeAttachmentInfo type element into a AeAttachmentInfo.
    * @param aTaskId task to which attachment is associated with.
    * @param aAttachmentInfoElement
    * @return AeAttachmentInfo
    */
   protected AeAttachmentInfo deserializeAttachmentInfo(String aTaskId, Element aAttachmentInfoElement)
   {
      String name = getText( aAttachmentInfoElement, "htda:name"); //$NON-NLS-1$
      String accessType = getText( aAttachmentInfoElement, "htda:accessType"); //$NON-NLS-1$
      String contentType = getText( aAttachmentInfoElement, "htda:contentType"); //$NON-NLS-1$
      AeSchemaDateTime addedAt = getDateTime( aAttachmentInfoElement, "htda:attachedAt"); //$NON-NLS-1$
      String attachedBy = getText( aAttachmentInfoElement, "htda:attachedBy"); //$NON-NLS-1$
      AeAttachmentInfo info = new AeAttachmentInfo(aTaskId, name, contentType, accessType);
      if (addedAt != null)
      {
         info.setAttachedAt(addedAt.toDate());
      }
      info.setAttachedBy(attachedBy);
      return info;
   }
}
