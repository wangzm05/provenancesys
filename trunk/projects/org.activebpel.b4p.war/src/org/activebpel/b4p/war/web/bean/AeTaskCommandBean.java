//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/bean/AeTaskCommandBean.java,v 1.5 2008/02/11 22:38:59 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.web.bean;

import org.activebpel.b4p.war.service.AeTaskFaultException;
import org.activebpel.rt.ht.api.io.AeHtSimpleRequestSerializer;


/**
 * Bean which handles task commands from AJAX requests - usually via the
 * inbox listing page.
 *
 * Note: The task detail page normally uses the XSL command framework instead
 * of this bean.
 */
public class AeTaskCommandBean extends AeTaskBeanBase
{
   /** New owner for transfer ownership commands. */
   private String mNewOwner;
   /** Name of fault to set fault and fail operations.*/
   private String mFaultName;
   /** New priority to be set on the task. */
   private int mNewPriority = -1;

   /**
    * @return the newOwner
    */
   public String getNewOwner()
   {
      return mNewOwner;
   }

   /**
    * @param aNewOwner the newOwner to set
    */
   public void setNewOwner(String aNewOwner)
   {
      mNewOwner = aNewOwner;
   }

   /**
    * @return the faultName
    */
   public String getFaultName()
   {
      return mFaultName;
   }

   /**
    * @param aFaultName the faultName to set
    */
   public void setFaultName(String aFaultName)
   {
      mFaultName = aFaultName;
   }

   /**
    * @return the newPriority
    */
   public int getNewPriority()
   {
      return mNewPriority;
   }

   /**
    * @param aNewPriority the newPriority to set
    */
   public void setNewPriority(int aNewPriority)
   {
      mNewPriority = aNewPriority;
   }

   /**
    * Sets the task action such as claim, start, setPriority etc.
    * @param aCommand
    */
   public void setCommand(String aCommand)
   {
      try
      {
         if ("claim".equals(aCommand)  //$NON-NLS-1$
          || "start".equals(aCommand) //$NON-NLS-1$
          || "stop".equals(aCommand)  //$NON-NLS-1$
          || "release".equals(aCommand)  //$NON-NLS-1$
          || "suspend".equals(aCommand)  //$NON-NLS-1$
          || "resume".equals(aCommand)  //$NON-NLS-1$
          || "skip".equals(aCommand)  //$NON-NLS-1$
          || "remove".equals(aCommand)  //$NON-NLS-1$
          )
         {
            AeHtSimpleRequestSerializer ser = new AeHtSimpleRequestSerializer(aCommand, getId());
            getHtClientService().executeRequest(ser.serialize() );
         }
      }
      catch(AeTaskFaultException tfe)
      {
         setTaskFault(tfe);
      }
      catch(Throwable t)
      {
         setError(t);
      }
   }
}
