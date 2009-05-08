// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeFromDefRule20Validator.java,v 1.2 2008/02/15 17:40:57 EWittmann Exp $
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

/**
 * valid style: lpg, literal, or expression
 */
public class AeFromDefRule20Validator extends AeAbstractHtValidator
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
    * rule logic
    * @param aDef
    */
   protected void executeRule(AeFromDef aDef)
   {
      int styleCount = 0;
      styleCount += aDef.isLiteral() ? 1 : 0;
      styleCount += aDef.isLPG() ? 1 : 0;
      styleCount += aDef.isExpression() ? 1 : 0;
      
      if (styleCount != 1)
      {
         reportProblem(AeMessages.getString("AeFromDefRule20Validator.0"), aDef); //$NON-NLS-1$
      }
   }
}
