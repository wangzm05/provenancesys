// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeLocalTaskDefRule119Validator.java,v 1.1 2008/02/21 22:06:39 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef;
import org.activebpel.rt.b4p.def.AeLocalTaskDef;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.bpel.def.util.AeLocationPathUtils;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * warning if variables from inlined tasks overlap
 */
public class AeLocalTaskDefRule119Validator extends AeAbstractB4PValidator
{
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeLocalTaskDef)
    */
   public void visit(AeLocalTaskDef aDef)
   {
      if (aDef.getInlineTaskDef() != null)
      {
         executeRule(aDef.getInlineTaskDef().getUsedVariables(), aDef);
      }
      super.visit(aDef);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
    */
   public void visit(AeNotificationDef aDef)
   {
      if (aDef.getParentXmlDef() instanceof AePeopleActivityDef)
      {
         executeRule(aDef.getUsedVariables(), aDef);
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      if (aDef.getParentXmlDef() instanceof AePeopleActivityDef)
      {
         executeRule(aDef.getUsedVariables(), aDef);
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
    */
   public void visit(AeB4PLocalNotificationDef aDef)
   {
      if (aDef.getInlineNotificationDef() != null)
      {
         executeRule(aDef.getInlineNotificationDef().getUsedVariables(), aDef);
      }
      super.visit(aDef);
   }

   /**
    * rule logic
    * @param aDef
    */
   protected void executeRule(Set aUsedVariables, AeBaseXmlDef aDef)
   {
      Set nameSet = new HashSet(aUsedVariables.size());
      
      // convert the set to a list of just variable names
      for (Iterator variableIter = aUsedVariables.iterator(); variableIter.hasNext();)
      {
         String variable = AeLocationPathUtils.getActivityName(String.valueOf(variableIter.next()));
         if (nameSet.contains(variable))
         {  
            String message = AeMessages.format("AeLocalTaskDefRule119Validator.AMBIGUOUS_VARIABLE", new Object[]{variable}); //$NON-NLS-1$
            reportProblem(message, aDef);
         }
         else
         {
            nameSet.add(variable);
         }
      }
      
      
   }
}
