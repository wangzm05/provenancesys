// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeFromDefRule19Validator.java,v 1.4 2008/02/15 17:40:57 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.validation.IAeHtValidationContext;
import org.activebpel.rt.util.AeUtil;

/**
 * Referenced logicalPeopleGroup is resolved
 */
public class AeFromDefRule19Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * Rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeFromDef aDef)
   {
      IAeHtValidationContext context = getValidationContext();
      
      if(AeUtil.notNullOrEmpty(aDef.getLogicalPeopleGroup()))
      {
         AeLogicalPeopleGroupDef lpgDef = context.findLogicalPeopleGroup(aDef, aDef.getLogicalPeopleGroup());
         
         if (lpgDef == null)
         {
            reportProblem(AeMessages.getString("AeFromDefRule19Validator.0"), aDef); //$NON-NLS-1$
         }
      }
   }

}
