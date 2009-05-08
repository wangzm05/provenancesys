//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/AeTaskServiceException.java,v 1.1 2008/01/11 15:05:48 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.service;

import org.activebpel.rt.AeException;

/**
 * Base class for all task service exceptions. All declared exceptions will be
 * modeled using a subclass. Unexecpected exceptions or anything else out of 
 * band is represented using this class.
 */
public class AeTaskServiceException extends AeException
{
   /**
    * Construct with a root exception message.
    * @param aInfo
    */
   public AeTaskServiceException(String aInfo)
   {
      this(aInfo, null);
   }
   
   /**
    * Construct with a root exception (used primarily for rethrowing an underlying exception).
    * @param aRootCause
    */
   public AeTaskServiceException(Throwable aRootCause)
   {
      super(aRootCause);
   }

   /**
    * Construct with a root exception (used prinarily for rethrowing an underlying exception).
    * @param aInfo Informational message for the exception
    * @param aRootCause Root cause of the exception
    */
   public AeTaskServiceException(String aInfo, Throwable aRootCause)
   {
      super(aInfo, aRootCause);
   }

}
