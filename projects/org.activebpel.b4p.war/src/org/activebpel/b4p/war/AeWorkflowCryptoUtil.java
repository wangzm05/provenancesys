//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/AeWorkflowCryptoUtil.java,v 1.2 2008/02/17 21:08:40 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war;

import org.activebpel.rt.util.AeJCEUtil;

/**
 * Utility class for managing password encryption.
 */
public class AeWorkflowCryptoUtil
{

   /**
    * Decrypts a string using AE server's default internal key
    * @param aInput String to decrypt
    * @return String decrypted
    */
   public static String decryptString(String aInput)
   {
      return AeJCEUtil.getDefaultInstance().decryptString(aInput);
   }

   /**
    * Encrypts a string using our internal key
    *
    * @param aInput String to encrypt
    * @return decrypted string
    */
   public static String encryptString(String aInput)
   {
      return AeJCEUtil.getDefaultInstance().encryptString(aInput);
   }

}
