// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeReassignmentDefRule123Validator.java,v 1.2 2008/03/20 15:49:48 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AePotentialOwnersDef;
import org.activebpel.rt.ht.def.AeReassignmentDef;

/**
 * If present, the element MUST specify potential owners
 */
public class AeReassignmentDefRule123Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeReassignmentDef)
    */
   public void visit(AeReassignmentDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * 
    * @param aDef
    */
   protected void executeRule(AeReassignmentDef aDef)
   {  
      AePotentialOwnersDef owners = aDef.getPotentialOwners();
      
      // if there are potential owners continue checking the from def
      if (owners != null)
      {
         AeFromDef fromDef = owners.getFrom();
         
         //report the problem if there is a null from def or the fromDef is not defined
         if (fromDef == null || !fromDef.isDefined())
         {
            reportProblem(AeMessages.getString("AeReassignmentDefRule123Validator.0"), aDef); //$NON-NLS-1$
         }
      }
      else
      {
         reportProblem(AeMessages.getString("AeReassignmentDefRule123Validator.0"), aDef); //$NON-NLS-1$
      }
   }
}
