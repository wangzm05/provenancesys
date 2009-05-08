// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/AeHtValidator.java,v 1.3 2008/02/17 21:51:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation;

import org.activebpel.rt.ht.def.AeHtBaseDef;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.wsresource.validation.AeWSResourceValidationEngine;
import org.activebpel.rt.wsresource.validation.IAeWSResourceProblemHandler;
import org.activebpel.rt.wsresource.validation.IAeWSResourceValidationContext;
import org.activebpel.rt.wsresource.validation.IAeWSResourceValidationPreferences;
import org.activebpel.rt.wsresource.validation.rules.AeWSResourceValidationRuleRegistry;

/**
 * This class is a holder for the static instance of the validation engine
 * which is lazily instantiated when HT validation is requested.
 */
public class AeHtValidator
{
   /** Rule registry loaded with ht rules */
   private static AeWSResourceValidationRuleRegistry sRuleRegistry;
   
   static
   {
      sRuleRegistry = new AeWSResourceValidationRuleRegistry();
      
      //load the rules

      sRuleRegistry.loadRules(AeHtValidator.class.getResource("ht-rules.xml")); //$NON-NLS-1$
      
   }

   /**
    * Validate the resource using the cached validation engine
    * 
    * @param aHtDef
    * @param aResourceLocation
    * @param aPreferences
    * @param aProblemHandler
    */
   public static void validate(AeHtBaseDef aHtDef,
         String aResourceLocation,
         IAeWSResourceValidationPreferences aPreferences, 
         IAeWSResourceProblemHandler aProblemHandler)
   {
      AeWSResourceValidationEngine engine = new AeWSResourceValidationEngine(
                                                               getRuleRegistryInstance(), 
                                                               createContext(aHtDef, aResourceLocation) );
      
      engine.validate(aHtDef, IAeHtDefConstants.DEFAULT_HT_NS, aPreferences, aProblemHandler);
   }
   
   /**
    * Create a context factory
    * 
    * @param aHtDef
    * @param aResourceLocation
    */
   protected static IAeWSResourceValidationContext createContext(AeHtBaseDef aHtDef, String aResourceLocation)
   {
      return new AeHtValidationContext();
   }
   
   /**
    * get the rule registry preloaded with rules for ht
    * 
    */
   protected static AeWSResourceValidationRuleRegistry getRuleRegistryInstance()
   {
      return sRuleRegistry;
   }
}
