//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/bean/AeTaskBeanBase.java,v 1.2 2008/02/06 02:20:38 PJayanetti Exp $
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
 * Base class for per task related beans.
 */
public abstract class AeTaskBeanBase extends AeTaskApiBeanBase
{
   /**
    * Task Id.
    */
   private String mId;

   /**
    * @return the id
    */
   public String getId()
   {
      return mId;
   }

   /**
    * @param aId the id to set
    */
   public void setId(String aId)
   {
      mId = aId;
   }
}
