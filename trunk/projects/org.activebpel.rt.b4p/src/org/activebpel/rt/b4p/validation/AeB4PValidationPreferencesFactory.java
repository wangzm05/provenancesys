// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/AeB4PValidationPreferencesFactory.java,v 1.1 2008/02/21 22:07:23 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation;

import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsresource.validation.IAeWSResourceValidationPreferences;
import org.activebpel.rt.wsresource.validation.IAeWSValidationPreferencesFactory;

/**
 * Use this class to get an instance of a IAeWSValidationPreferencesFactory for B4P.  Calling
 * getInstance will check the "b4p.validation.preferences.factory" property to see if a 
 * preferences factory has been defined and if so create/return an instance of it, otherwise return an
 * instance of this class.
 */
public class AeB4PValidationPreferencesFactory implements IAeWSValidationPreferencesFactory
{
   /** name of the preferred factory class */
   protected static final String PREFERRED_FACTORY_PROPERTY = "b4p.validation.preferences.factory"; //$NON-NLS-1$
   /** lazily instantiated instance of this factory */
   private static IAeWSValidationPreferencesFactory sInstance = null;
   
   /**
    * @see org.activebpel.rt.wsresource.validation.IAeWSValidationPreferencesFactory#createPreferences()
    */
   public IAeWSResourceValidationPreferences createPreferences()
   {
      return new AeB4PValidationPreferences();
   }
   
   /**
    * Return an instance of an IAeWSValidationPreferencesFactory class for the supplied factory class name.
    */
   public static IAeWSValidationPreferencesFactory getInstance()
   {
      // check if this factory has already been instantiated.
      if(sInstance != null)
      {
         return sInstance;
      }
      
      // short return with a default factory if the property isn't defined.
      if (AeUtil.isNullOrEmpty(PREFERRED_FACTORY_PROPERTY) || 
          AeUtil.isNullOrEmpty(System.getProperty(PREFERRED_FACTORY_PROPERTY)))
      {
         return new AeB4PValidationPreferencesFactory();
      }
      
      // create an instance of the factory in the system properties and cache it.
      try
      {
         String propertyValue = System.getProperty(PREFERRED_FACTORY_PROPERTY);
         Class factoryClass = Class.forName(propertyValue);
         sInstance = (IAeWSValidationPreferencesFactory) factoryClass.newInstance();
         return sInstance;
      }
      catch (Exception ex)
      {
         throw new RuntimeException(ex);
      }
   }
}
