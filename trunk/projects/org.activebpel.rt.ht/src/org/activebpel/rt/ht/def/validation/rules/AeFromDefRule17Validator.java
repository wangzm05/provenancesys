// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeFromDefRule17Validator.java,v 1.2 2008/02/15 17:40:57 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeArgumentDef;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AeParameterDef;

/**
 * Argument list matches logicalPeopleGroup definition
 */
public class AeFromDefRule17Validator extends AeAbstractHtValidator
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
    * 
    * @param aDef
    */
   protected void executeRule(AeFromDef aDef)
   {
      if (aDef.isLPG() && aDef.getArgumentDefs().size() > 0)
      {
         if (getValidationContext().findLogicalPeopleGroup(aDef, aDef.getLogicalPeopleGroup()) != null)
         {
            Set args = new HashSet(aDef.getArgumentDefs().size());
            List paramDefs = getValidationContext().getLPGParameters(aDef, aDef.getLogicalPeopleGroup());
            Set params = new HashSet(paramDefs.size());
            
            for (Iterator iter = aDef.getArgumentDefs().iterator(); iter.hasNext();)
            {
               AeArgumentDef argument = (AeArgumentDef) iter.next();
               args.add(argument.getName());
            }
            
            for (Iterator iter = paramDefs.iterator(); iter.hasNext();)
            {
               AeParameterDef parameter = (AeParameterDef) iter.next();
               params.add(parameter.getName());
            }
            
            if (!args.equals(params))
            {
               reportProblem(AeMessages.getString("AeFromDefRule17Validator.0"), aDef); //$NON-NLS-1$
            }
         }
      }
   }
}
