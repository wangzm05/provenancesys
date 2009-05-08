//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/bean/AeTaskRequestBase.java,v 1.1 2008/01/11 15:05:51 PJayanetti Exp $
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
 * Base class used to send request to the task service.
 */
public abstract class AeTaskRequestBase
{
   /** Principal Name. */
   private String mPrincipal;

   /**
    * @return the principal
    */
   public String getPrincipal()
   {
      return mPrincipal;
   }

   /**
    * @param aPrincipal the principal to set
    */
   public void setPrincipal(String aPrincipal)
   {
      mPrincipal = aPrincipal;
   }
}
