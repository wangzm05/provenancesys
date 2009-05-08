// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeDelegationDefRule12Validator.java,v 1.2 2008/02/15 17:40:56 EWittmann Exp $
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
import org.activebpel.rt.ht.def.AeDelegationDef;

/**
 * If potentialDelegatees is 'other' then the element 'from' is required to determine
 * the people to whom the task may be delegated, otherwise 'from' is not required.
 */
public class AeDelegationDefRule12Validator extends AeAbstractHtValidator
{
   private static final String OTHER = "other"; //$NON-NLS-1$
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeDelegationDef)
    */
   public void visit(AeDelegationDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeDelegationDef aDef)
   {
      if (OTHER.equalsIgnoreCase(aDef.getPotentialDelegatees()) && aDef.getFrom() == null)
      {
         reportProblem(AeMessages.getString("AeDelegationDefRule12Validator.0"), aDef); //$NON-NLS-1$
      }
   }

}
