// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/AeHtValidationPreferences.java,v 1.2 2008/01/10 19:25:55 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation;

import org.activebpel.rt.wsresource.validation.AeWSResourceValidationPreferences;

/**
 * Default Human Interactions validation preferences class.  Schema validation is always disabled 
 * and all rules have a severity of SEVERITY_ERROR.
 */
public class AeHtValidationPreferences extends AeWSResourceValidationPreferences
{
   /**
    * @see org.activebpel.rt.wsresource.validation.IAeWSResourceValidationPreferences#isSchemaValidationEnabled(java.lang.String)
    */
   public boolean isSchemaValidationEnabled(String aWSResourceType)
   {
      return false;
   }

}
