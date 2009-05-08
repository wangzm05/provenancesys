//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/xsl/AeImportNotFoundTransformerException.java,v 1.1 2008/01/11 15:05:48 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.xsl;

import javax.xml.transform.TransformerException;

import org.activebpel.b4p.war.AeMessages;

/**
 * Exception tht is thrown if a resolved resource is not available.
 */
public class AeImportNotFoundTransformerException extends TransformerException
{
   /**
    * Constructs the AeImportNotFoundTransformerException with the given message.
    * @param aMessage
    */
   public AeImportNotFoundTransformerException(String aMessage)
   {
      super(aMessage);
   }

   /**
    * Constructs the AeImportNotFoundTransformerException with the given the parameters used to
    * resolve the import.
    * @param aBase base uri
    * @param aHref resource href
    * @param aUri the resolved uri that was not found.
    */
   public AeImportNotFoundTransformerException(String aBase, String aHref, String aUri)
   {
      super(AeMessages.format("AeImportNotFoundTransformerException.IMPORT_NOT_FOUND", new String[]{aUri, aBase, aHref})); //$NON-NLS-1$
   }

   /**
    * Constructs the AeImportNotFoundTransformerException with the given error.
    * @param aError
    */
   public AeImportNotFoundTransformerException(Throwable aError)
   {
      super(aError);
   }

}
