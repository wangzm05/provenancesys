// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeDelegationDefRule11Validator.java,v 1.1.4.1 2008/04/21 16:15:16 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeDelegationDef;
import org.activebpel.rt.util.AeUtil;

/**
 * potentialDelegatees value:
 * 
 *       &lt;xsd:enumeration value="anybody" /&gt;
 *       &lt;xsd:enumeration value="nobody" /&gt;
 *       &lt;xsd:enumeration value="potentialOwners" /&gt;
 *       &lt;xsd:enumeration value="other" /&gt;
 */
public class AeDelegationDefRule11Validator extends AeAbstractHtValidator
{
   private static Set sAllowedValues = new HashSet(Arrays.asList(new String[] {"anybody", "nobody", "potentialOwners", "other"})); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
   
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
    * @param aDef
    */
   protected void executeRule(AeDelegationDef aDef)
   {
      if (AeUtil.isNullOrEmpty(aDef.getPotentialDelegatees()) || !sAllowedValues.contains(aDef.getPotentialDelegatees()) )
      {
         String message = AeMessages.format("AeDelegationDefRule11Validator.0", aDef.getPotentialDelegatees()); //$NON-NLS-1$
         reportProblem(message, aDef);
      }
   }
}
