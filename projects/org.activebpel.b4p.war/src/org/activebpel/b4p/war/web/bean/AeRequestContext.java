//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/bean/AeRequestContext.java,v 1.2 2008/02/17 21:08:40 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.web.bean;

/**
 * Simple bean to store page level request data.
 */
public class AeRequestContext
{
   /** Title of current page. */
   private String mPageName;
   /** Message. */
   private String mMessage;

   /**
    * Default ctor.
    */
   public AeRequestContext()
   {
   }

   /**
    * @return the pageName
    */
   public String getPageName()
   {
      return mPageName;
   }

   /**
    * @param aPageName the pageName to set
    */
   public void setPageName(String aPageName)
   {
      mPageName = aPageName;
   }

   /**
    * returns true if there is a message to be displayed.
    */
   public boolean isHasMessage()
   {
      return getMessage() != null;
   }

   /**
    * @return the message
    */
   public String getMessage()
   {
      return mMessage;
   }

   /**
    * @param aMessage the message to set
    */
   public void setMessage(String aMessage)
   {
      mMessage = aMessage;
   }

}
