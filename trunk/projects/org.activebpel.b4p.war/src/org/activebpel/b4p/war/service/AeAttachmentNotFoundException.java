//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/service/AeAttachmentNotFoundException.java,v 1.1 2008/01/11 15:05:48 PJayanetti Exp $
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
 * Exception representing task service wsdl attachment not found fault.
 */
public class AeAttachmentNotFoundException extends AeTaskServiceException
{
   /**
    * Constructs the exception with attachment name.
    * @param aAttachmentName
    */
   public AeAttachmentNotFoundException(String aAttachmentName)
   {
      super(aAttachmentName);
   }
}
