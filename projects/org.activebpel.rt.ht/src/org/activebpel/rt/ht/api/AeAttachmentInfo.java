//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/AeAttachmentInfo.java,v 1.2 2008/02/17 21:51:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api;

import java.util.Date;

import org.activebpel.rt.util.AeFileUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * Contains data about a task attachment as per schema wsht api:tAttachmentInfo type.
 */
public class AeAttachmentInfo
{
   /** Task this attachment is for. */
   private String mTaskId;
   /** Attachment name. */
   private String mName;
   /** Access type. */
   private String mAccessType;
   /** Attachment mime content type. */
   private String mContentType;
   /** Name of person who added the comment. */
   private String mAttachedBy;
   /** Date comment added. */
   private Date mAttachedAt;

   /**
    * Constructs attachment information with name and mime type.
    * @param aTaskId attachment task ref.
    * @param aName attachment name.
    * @param aContentType mime type.
    * @param aAccessType access type.
    */
   public AeAttachmentInfo(String aTaskId, String aName, String aContentType, String aAccessType)
   {
      mTaskId = aTaskId;
      mName = aName;
      mContentType = aContentType;
      mAccessType = aAccessType;
   }

   /**
    * Returns task ref id.
    * @return task reference id.
    */
   public String getTaskId()
   {
      return mTaskId;
   }

   /**
    * @return Attachment name.
    */
   public String getName()
   {
      return AeUtil.getSafeString(mName);
   }

   /**
    * @return Attachment access type.
    */
   public String getAccessType()
   {
      return AeUtil.getSafeString(mAccessType);
   }

   /**
    * @return attachment mime type.
    */
   public String getContentType()
   {
      return AeUtil.getSafeString(mContentType);
   }

   /**
    * Date added.
    * @return returns date attachment was added.
    */
   public Date getAttachedAt()
   {
      return mAttachedAt;
   }

   /**
    * Sets the date added.
    * @param aAttachedAt
    */
   public void setAttachedAt(Date aAttachedAt)
   {
      mAttachedAt = aAttachedAt;
   }

   /**
    * Person who added attachment.
    * @return name of user who added attachment.
    */
   public String getAttachedBy()
   {
      return mAttachedBy;
   }

   /**
    * Sets the name of person who added attachment.
    * @param aAttachedBy
    */
   public void setAttachedBy(String aAttachedBy)
   {
      mAttachedBy = aAttachedBy;
   }

   /**
    * Returns attachment file name extension.
    * @return extension.
    */
   public String getExtension()
   {
      return AeFileUtil.getExtension( getName() );
   }

   /**
    * Returns hint to content or file type icon. This method returns mime content-type if it is not
    * application/octet-stream, otherwise returns the file extension  if that is available, and finally,
    * defaults to empty string if the mime type and the file extension is not available.
    *
    * The returned string value is processed to replace periods (.), hyphen (-) and forward slash (/)
    * with an underscore. For example, if the mime type is application/vnd.ms.excel, then the hint
    * that is return is application_vnd_ms_excel.
    *
    * @return icon type name or hint.
    */
   public String getIconTypeHint()
   {
      String iconType = null;
      String mimeType = getContentType();
      // remove trailing ';' after mime type. E.g: multipart/mixed; boundary="frontier"
      if (mimeType.indexOf(';') != -1)
      {
         mimeType = mimeType.substring(0, mimeType.indexOf(';'));
      }
      String ext = getExtension();
      if (mimeType.length() > 0 && !"application/octet-stream".equals(mimeType) ) //$NON-NLS-1$
      {
         iconType = mimeType;
      }
      else if (ext.length() > 0)
      {
         iconType = ext;
      }
      else
      {
         iconType = ""; //$NON-NLS-1$
      }
      return iconType.replace('.', '_').replace('-', '_').replace('/', '_');
   }

}
