// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeB4PFromDefRule20Validator.java,v 1.3 2008/02/26 15:20:53 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef;
import org.activebpel.rt.b4p.validation.IAeB4PValidationContext;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.validation.IAeHtValidationContext;
import org.activebpel.rt.ht.def.validation.rules.AeFromDefRule20Validator;
import org.activebpel.rt.xml.def.AeXmlDefUtil;

/**
 * valid style: lpg, literal, or expression - only perform this logic when the AeFrom def isn't parented 
 * by a AeB4PPeopleAssignmentsDef
 */
public class AeB4PFromDefRule20Validator extends AeFromDefRule20Validator
{
   /**
    * @see org.activebpel.rt.ht.def.validation.rules.AeFromDefRule20Validator#visit(org.activebpel.rt.ht.def.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      IAeHtValidationContext context = getValidationContext();
      // only execute the rule when we have a B4P validation context and the From def isn't parented
      // by a AeB4PPeopleAssignmentsDef
      if (context instanceof IAeB4PValidationContext)
      {
         if (!AeXmlDefUtil.isParentedByType(aDef, AeB4PPeopleAssignmentsDef.class))
         {
            super.visit(aDef);
         }
      }
   }
}
