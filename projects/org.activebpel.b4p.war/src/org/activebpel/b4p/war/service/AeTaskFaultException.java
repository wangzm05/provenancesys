//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/AeTaskFaultException.java,v 1.1 2008/01/11 15:05:48 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.service;


/**
 * Class encompassing all of the faults declared on the AETS operations. A code
 * is used to indicate the underlying fault along with an error message. 
 */
public class AeTaskFaultException extends AeTaskServiceException
{
   /** indicates an error on the client prior to sending the message */
   public static final int CLIENT = -1;
   
   /** thrown when the operation is invalid based on the task's current state */
   public static final int ILLEGAL_STATE = 1;
   /** thrown when the caller doesn't have access to the specified task or operation */
   public static final int ILLEGAL_ACCESS = 2;
   /** thrown when one or more arguments are illegal (i.e. delete an attachment but the attachment doesn't exist)  */
   public static final int ILLEGAL_ARGUMENT = 3;
   /** thrown when trying to assign a task to a user that is specifically excluded from the task */
   public static final int RECIPIENT_NOT_ALLOWED = 4;

   /** Optional state information */
   private String mState;
   /** code indicates which fault is represented */
   private int mCode;

   /**
    * Construct with a root exception message.
    */
   public AeTaskFaultException(int aCode, String aInfo)
   {
      this(aCode, aInfo, null);
   }

   /**
    * Construct with a root exception (used prinarily for rethrowing an underlying exception).
    * @param aCode - code indicating underlying cause of fault
    * @param aInfo Informational message for the exception
    * @param aRootCause Root cause of the exception
    */
   public AeTaskFaultException(int aCode, String aInfo, Throwable aRootCause)
   {
      super(aInfo, aRootCause);
      setCode(aCode);
   }   
   
   /**
    * @return the state
    */
   public String getState()
   {
      return mState;
   }

   /**
    * @param aState the state to set
    */
   public void setState(String aState)
   {
      mState = aState;
   }

   /**
    * @return the code
    */
   public int getCode()
   {
      return mCode;
   }

   /**
    * @param aCode the code to set
    */
   public void setCode(int aCode)
   {
      mCode = aCode;
   }
}
