// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeParameterDefRule36Validator.java,v 1.2 2008/02/15 17:40:56 EWittmann Exp $
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

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeAbstractParameterListDef;
import org.activebpel.rt.ht.def.AeParameterDef;
import org.activebpel.rt.util.AeUtil;

/**
 * Parameter name must be unique within collection.
 */
public class AeParameterDefRule36Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeParameterDef)
    */
   public void visit(AeParameterDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeParameterDef aDef)
   {
      AeAbstractParameterListDef params = (AeAbstractParameterListDef) aDef.getParentDef();
      
      for (Iterator iter = params.getParameterDefs().iterator(); iter.hasNext();)
      {
         AeParameterDef param = (AeParameterDef) iter.next();
         
         if (param != aDef && AeUtil.compareObjects(aDef.getName(), param.getName()) )
         {
            reportProblem(AeMessages.getString("AeParameterDefRule36Validator.0"), aDef); //$NON-NLS-1$
         }
      }
   }

}
