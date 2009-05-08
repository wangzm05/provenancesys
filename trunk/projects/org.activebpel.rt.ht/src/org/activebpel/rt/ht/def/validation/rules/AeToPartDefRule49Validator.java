// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeToPartDefRule49Validator.java,v 1.5 2008/03/15 22:09:04 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import org.activebpel.rt.ht.def.AeToPartDef;
import org.activebpel.rt.util.AeUtil;

/**
 * validate expression
 */
public class AeToPartDefRule49Validator extends AeAbstractHtExpressionValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartDef)
    */
   public void visit(AeToPartDef aDef)
   {
      // make sure there is an expression to validate, if not report a problem.
      // the else case for this if is handled by a separate rule.
      if ( AeUtil.notNullOrEmpty(aDef.getExpression()) )
      {
         validateExpressionAndReportProblems(aDef);
      }
      
      super.visit(aDef);
   }
}
