// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeToPartDefRule122Validator.java,v 1.1 2008/03/15 22:09:04 dvilaverde Exp $
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
import org.activebpel.rt.ht.def.AeToPartDef;
import org.activebpel.rt.util.AeUtil;

/**
 * must have an expression
 * 
 * This rule was created to address defect #3985 
 *    "Static analysis is not reporting an error for missing expressions in a notification's toParts."
 */
public class AeToPartDefRule122Validator extends AeAbstractHtValidator
{  
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartDef)
    */
   public void visit(AeToPartDef aDef)
   {
      if (AeUtil.isNullOrEmpty(aDef.getExpression()))
      {
         String message = AeMessages.format("AeToPartDefRule122Validator.0", new Object[] {aDef.getName()}); //$NON-NLS-1$
         reportProblem(message, aDef);
      }
   }
}
