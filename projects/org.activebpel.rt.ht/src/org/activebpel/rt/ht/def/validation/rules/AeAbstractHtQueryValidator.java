// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeAbstractHtQueryValidator.java,v 1.2 2008/02/15 17:40:57 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import java.util.Iterator;

import org.activebpel.rt.expr.validation.IAeExpressionValidationResult;
import org.activebpel.rt.ht.def.AeOutcomeDef;
import org.activebpel.rt.ht.def.AeQueryDef;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * Base class for all HT query validation rules.
 */
public class AeAbstractHtQueryValidator extends AeAbstractHtValidator
{  
   /**
    * Called by all ht query visit methods.
    *  
    * @param aDef
    */
   protected void validateHtQuery(AeQueryDef aDef)
   {
      //NO-OP, to be implemented by rules
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeOutcomeDef)
    */
   public void visit(AeOutcomeDef aDef)
   {
      validateHtQuery(aDef);
      super.visit(aDef);
   }
   
   /**
    * validate query and report all errors via the problem reporter
    * 
    * @param aDef
    */
   protected void validateQueryAndReportErrors(AeQueryDef aDef)
   {
      try
      {
         IAeExpressionValidationResult vResult = getValidationContext().validateQuery(aDef);
         
         for (Iterator iter = vResult.getErrors().iterator(); iter.hasNext();)
         {
            reportProblem(String.valueOf(iter.next()), (AeBaseXmlDef) aDef);
         }
      }
      catch (Exception ex)
      {
         reportProblem(ex.getMessage(), aDef);
      }  
   }
}
