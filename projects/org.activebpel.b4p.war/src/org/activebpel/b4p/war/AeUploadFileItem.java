//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/AeUploadFileItem.java,v 1.1 2008/01/11 15:05:45 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war;

import java.io.File;

import org.activebpel.rt.util.AeFileUtil;

/**
 * Utility wrapper class to keep file upload information.
 */
public class AeUploadFileItem
{
   /* Uploaded file content type. */
   private String mContentType;
   /* Uploaded file name. */
   private String mName;
   /* Uploaded file handle. */
   private File mFile;

   /***
    * Ctor.
    * @param aFile
    * @param aContentType file mime type
    * @param aFileName path and name of uploaded file
    */
   public AeUploadFileItem(File aFile, String aContentType, String aFileName)
   {
      mContentType = aContentType;
      mFile = aFile;
      // The filename on IE contains the fullpath where as on FF, then it is only the filename is given
      // E.g. IE=C:/temp/hello.txt, and FF=hello.txt
      // Use util method to get the filename only.
      String paths[] = AeFileUtil.splitFilePathAndName(aFileName);
      mName = paths[1];
   }

   /**
    * @return file name.
    */
   public String getName()
   {
      return mName;
   }

   /**
    * @return file mime content type.
    */
   public String getContentType()
   {
      return mContentType;
   }
   /**
    * @return handle to uploaded file.
    */
   public File getFile()
   {
      return mFile;
   }
   
}
