//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/tamino/AeMessages.java,v 1.1 2005/07/27 14:27:46 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.tamino;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Accessor class for externalized strings
 */
public class AeMessages
{
   private static final String BUNDLE_NAME = "org.activebpel.rt.tamino.messages"; //$NON-NLS-1$

   private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

   private AeMessages()
   {
   }
   
   /**
    * Convenience method for formatting an externalized string with
    * a single replacement value.
    * @param aKey key of string from bundle
    * @param aParam first parameter to substitute
    * @return the formatted string
    */
   public static String format( String aKey, Object aParam )
   {
      return format( aKey, new Object[]{aParam} );
   }
   
   /**
    * Return message formatted externalized string.
    * @param aKey key of string from bundle
    * @param aArgs array of arguments for substitution
    * @return the formatted string
    */
   public static String format( String aKey, Object[] aArgs )
   {
      String templateString = getString( aKey );
      return MessageFormat.format( templateString, aArgs );
   }

   /**
    * Returns externalized string from resource bundle or key surrounded by exclamation points
    * if not found.
    * @param key - Key of string to return
    * @return - String from bundle
    */   
   public static String getString(String key)
   {
      try
      {
         return RESOURCE_BUNDLE.getString(key);
      }
      catch (MissingResourceException e)
      {
         return '!' + key + '!';
      }
   }
}