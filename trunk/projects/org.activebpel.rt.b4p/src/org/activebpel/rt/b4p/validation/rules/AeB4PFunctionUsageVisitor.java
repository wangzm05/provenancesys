// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeB4PFunctionUsageVisitor.java,v 1.2.4.2 2008/04/14 21:25:30 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Set;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.def.AeAlarmDef;
import org.activebpel.rt.b4p.def.AeB4PForDef;
import org.activebpel.rt.b4p.def.AeB4PUntilDef;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.expr.def.AeScriptFuncDef;
import org.activebpel.rt.expr.def.IAeExpressionParseResult;
import org.activebpel.rt.ht.IAeHtFunctionNames;
import org.activebpel.rt.ht.def.AeAbstractDeadlineDef;
import org.activebpel.rt.ht.def.AeArgumentDef;
import org.activebpel.rt.ht.def.AeForDef;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AePriorityDef;
import org.activebpel.rt.ht.def.AeToPartDef;
import org.activebpel.rt.ht.def.AeUntilDef;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.ht.def.IAeHtExpressionDef;
import org.activebpel.rt.ht.def.IAeTasksDefParent;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeXmlDefUtil;

/**
 * This visitor will check a process for B4P function usage to ensure that B4P functions are 
 * not used anywhere they are not allowed.  If any violations are found, a problem is reported.
 * 
 * Rules for reporting problems:
 * 
 * 1) Any B4P function used anywhere within a HumanInteractions element
 * 2) Anywhere B4P functions are restricted also restrict the WS-HT function getLogicalPeopleGroup()
 * 3) No B4P functions in or HT getLogicalPeopleGroup:
 *          a) escalation conditions
 *          b) escalation deadlines
 *          c) escalation notification args/reassignment/custom process invoke
 *          d) stat presentation parameters
 * 
 */
public class AeB4PFunctionUsageVisitor extends AeAbstractB4PValidator
{
   /**
    * @see org.activebpel.rt.b4p.validation.rules.AeAbstractB4PValidator#individualFunctionValidation(org.activebpel.rt.ht.def.IAeHtExpressionDef, org.activebpel.rt.expr.def.AeScriptFuncDef)
    */
   protected void individualFunctionValidation(IAeHtExpressionDef aDef, AeScriptFuncDef aFunction)
   {
      String displayText = AeMessages.getString("AeB4PFunctionUsageVisitor.NO_B4P_IN_EXPRESSION"); //$NON-NLS-1$
      
      // disallow any functions in the BPEL4People namespace that execute outside of the
      // calling process
      if (AeUtil.compareObjects(IAeB4PConstants.B4P_NAMESPACE, aFunction.getNamespace()))
      {
         String message = MessageFormat.format(displayText, new Object[] {IAeB4PConstants.B4P_NAMESPACE, aFunction.getName()});
         reportProblem(message, (AeBaseXmlDef) aDef);
      }
      
      // also disallow the HT getLogicalPeopleGroup function
      if (AeUtil.compareObjects(IAeHtDefConstants.DEFAULT_HT_NS, aFunction.getNamespace()) &&
          AeUtil.compareObjects(IAeHtFunctionNames.LOGICAL_PEOPLE_GROUP_FUNCTION_NAME, aFunction.getName()))
      {
         String message = MessageFormat.format(displayText, new Object[] {IAeHtDefConstants.DEFAULT_HT_NS, aFunction.getName()});
         reportProblem(message, (AeBaseXmlDef) aDef);
      }
   }

   /**
    * Check the aDef argument to find out if it is parented by a HumanInteractionsDef, 
    * if so check to see if any functions used in the expression are in the BPEL4People
    * namespace and if so report a problem
    *  
    * @param aDef
    */
   protected void checkParentIsHumanInteractionsDef(IAeHtExpressionDef aDef)
   {
      boolean isParentedByHI = AeXmlDefUtil.isParentedByType((AeBaseXmlDef) aDef, IAeTasksDefParent.class);
      boolean isParentedByPA = AeXmlDefUtil.isParentedByType((AeBaseXmlDef) aDef, AePeopleActivityDef.class);
      boolean isParentedByDeadline = AeXmlDefUtil.isParentedByType((AeBaseXmlDef) aDef, AeAbstractDeadlineDef.class);
      
      if ( isParentedByHI || (isParentedByPA && isParentedByDeadline) )
      {
         // get the parse result for the expression
         IAeExpressionParseResult parseResult = getParseResult(aDef);
         if (parseResult != null)
         {
            Set functions = parseResult.getFunctions();
            // check all the functions for any in the BPEL4People namespace
            for (Iterator iter = functions.iterator(); iter.hasNext();)
            {
               AeScriptFuncDef function = (AeScriptFuncDef) iter.next();
               
               if (AeUtil.compareObjects(IAeB4PConstants.B4P_NAMESPACE, function.getNamespace()))
               {
                  String displayText = AeMessages.getString("AeB4PFunctionUsageVisitor.NO_B4P_IN_HI"); //$NON-NLS-1$
                  String message = MessageFormat.format(displayText, new Object[] {IAeB4PConstants.B4P_NAMESPACE, function.getName()});
                  reportProblem(message, (AeBaseXmlDef) aDef);
               }
            }
         }
      }
   }
   
   /**
    * The following visit methods are overridden so that we don't check for B4P function usage within them unless
    * they are parented by a Human Interactions def.
    */
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor#visit(org.activebpel.rt.ht.def.AeArgumentDef)
    */
   public void visit(AeArgumentDef aDef)
   {
      checkParentIsHumanInteractionsDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor#visit(org.activebpel.rt.ht.def.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      checkParentIsHumanInteractionsDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor#visit(org.activebpel.rt.ht.def.AePriorityDef)
    */
   public void visit(AePriorityDef aDef)
   {
      checkParentIsHumanInteractionsDef(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartDef)
    */
   public void visit(AeToPartDef aDef)
   {
      
      checkParentIsHumanInteractionsDef(aDef);
   }
   
   /**
    * The following visit methods are overridden so that we don't check for B4P function usage within them unless
    * they are parented by a Alarm def.
    */
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PForDef)
    */
   public void visit(AeB4PForDef aDef)
   {
      if (!AeXmlDefUtil.isParentedByType((AeBaseXmlDef) aDef, AeAlarmDef.class))
      {
         super.visit(aDef);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PUntilDef)
    */
   public void visit(AeB4PUntilDef aDef)
   {
      if (!AeXmlDefUtil.isParentedByType((AeBaseXmlDef) aDef, AeAlarmDef.class))
      {
         super.visit(aDef);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor#visit(org.activebpel.rt.ht.def.AeForDef)
    */
   public void visit(AeForDef aDef)
   {
      if (!AeXmlDefUtil.isParentedByType((AeBaseXmlDef) aDef, AeAlarmDef.class))
      {
         super.visit(aDef);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor#visit(org.activebpel.rt.ht.def.AeUntilDef)
    */
   public void visit(AeUntilDef aDef)
   {
      if (!AeXmlDefUtil.isParentedByType((AeBaseXmlDef) aDef, AeAlarmDef.class))
      {
         super.visit(aDef);
      }
   }

}
