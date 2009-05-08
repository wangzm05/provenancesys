//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/AeTaskDataSourceWrapper.java,v 1.2 2008/02/17 21:08:40 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import org.activebpel.rt.util.AeMimeUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * Provides a datasource wrapper to return data source content type and name.
 * @see javax.activation.DataHandler
 */
public class AeTaskDataSourceWrapper implements DataSource
{

   /** Delegate data source */
   private DataSource mDelegate;
   /** Mime type. */
   private String mContentType;
   /** Data name or id. */
   private String mName;

   /**
    * Ctor.
    * @param aName
    * @param aContentType
    * @param aDelegate
    */
   public AeTaskDataSourceWrapper(String aName, String aContentType, DataSource aDelegate)
   {
      mName = aName;
      mDelegate = aDelegate;
      // check if mime ct is available, if not look up the mime registry.
      if (AeUtil.isNullOrEmpty(aContentType) || "application/octet-stream".equals(aContentType)) //$NON-NLS-1$
      {
         aContentType = AeMimeUtil.getContentType(aName, null);
      }
      mContentType = aContentType;
   }

   /**
    *
    * Overrides method to
    * @see javax.activation.DataSource#getContentType()
    */
   public String getContentType()
   {
      return mContentType;
   }

   /**
    *
    * Overrides method to get input stream from delegate.
    * @see javax.activation.DataSource#getInputStream()
    */
   public InputStream getInputStream() throws IOException
   {
      return mDelegate.getInputStream();
   }

   /**
    * Overrides method to data source name.
    * @see javax.activation.DataSource#getName()
    */
   public String getName()
   {
      return mName;
   }

   /**
    * Overrides method to return outputstream of delegate.
    * @see javax.activation.DataSource#getOutputStream()
    */
   public OutputStream getOutputStream() throws IOException
   {
      return mDelegate.getOutputStream();
   }

}