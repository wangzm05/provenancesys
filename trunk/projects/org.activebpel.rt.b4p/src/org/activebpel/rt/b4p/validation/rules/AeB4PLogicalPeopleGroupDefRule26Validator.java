// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeB4PLogicalPeopleGroupDefRule26Validator.java,v 1.1 2008/02/21 22:06:39 dvilaverde Exp $
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

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor;
import org.activebpel.rt.b4p.function.ht.AeHTExtensionFunctionFactory;
import org.activebpel.rt.b4p.validation.IAeB4PValidationContext;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.def.activity.support.AeToDef;
import org.activebpel.rt.bpel.def.activity.support.AeVarDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;
import org.activebpel.rt.expr.def.AeScriptFuncDef;
import org.activebpel.rt.expr.validation.IAeExpressionValidationResult;
import org.activebpel.rt.ht.IAeHtFunctionNames;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.ht.def.IAeHtExpressionDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeExtensionAttributeDef;

/**
 * Report problem if LogicalPeopleGroup not used (constellation 2 only)
 * 
 */
public class AeB4PLogicalPeopleGroupDefRule26Validator extends AeAbstractB4PValidator
{
   /** function factory for XPath HT Extension functions */
   private static AeHTExtensionFunctionFactory sFunctionFactory = new AeHTExtensionFunctionFactory();
   /** QName for b4p:logicalPeopleGroup extension attribute */
   private static QName sAttributeQName = new QName(IAeB4PConstants.B4P_NAMESPACE, IAeHtDefConstants.TAG_LOGICAL_PEOPLE_GROUP);
   /** set of defined logical people groups */
   private Set mLogicalPeopleGroups = new HashSet();
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef)
    */
   public void visit(AeLogicalPeopleGroupsDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * Rule logic
    * @param aDef
    */
   protected void executeRule(AeLogicalPeopleGroupsDef aDef)
   {
      // collect a list of all the LPGs 
      for (Iterator lpgs = aDef.getLogicalPeopleGroupDefs(); lpgs.hasNext();)
      {
         AeLogicalPeopleGroupDef lpg = (AeLogicalPeopleGroupDef) lpgs.next();
         getLogicalPeopleGroups().add(lpg);
      }
      
      // get the enclosing scope and visit for all extensions
      AeBaseDef def = AeDefUtil.getEnclosingScopeDef(aDef);
      
      //visit to/from specs
      AeB4PExpressionDefRule26Visitor expressionViz = new AeB4PExpressionDefRule26Visitor();
      AeToFromSpecRule26Visitor specViz = new AeToFromSpecRule26Visitor(expressionViz);
      def.accept(specViz);
      
      getLogicalPeopleGroups().removeAll(expressionViz.getFoundLogicalPeopleGroups());
      getLogicalPeopleGroups().removeAll(specViz.getFoundLogicalPeopleGroups());
      
      // report a problem on the unused LPG
      for (Iterator unusedIter = getLogicalPeopleGroups().iterator(); unusedIter.hasNext();)
      {
         AeLogicalPeopleGroupDef lpg = (AeLogicalPeopleGroupDef) unusedIter.next();
         if (lpg != null)
         {
            String message = AeMessages.format("AeB4PLogicalPeopleGroupDefRule26Validator.LPG_NOT_REFERENCED", new Object[] {lpg.getName()}); //$NON-NLS-1$
            reportProblem(message, lpg);
         }
      }
   }
   
   /**
    * @return Returns the logicalPeopleGroups.
    */
   protected Set getLogicalPeopleGroups()
   {
      return mLogicalPeopleGroups;
   }
   
   /**
    * Helper inner class.  This visitor will find all declared logical people groups
    * and any references to them and determine if any are unused.
    */
   private class AeB4PExpressionDefRule26Visitor extends AeAbstractB4PExpressionDefVisitor
   { 
      /** Set of referenced LPGs on all visited expressions */
      private Set mFoundLogicalPeopleGroups = new HashSet();
      
      /**
       * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor#visit(org.activebpel.rt.ht.def.AeFromDef)
       */
      public void visit(org.activebpel.rt.ht.def.AeFromDef aDef)
      {
         if (AeUtil.notNullOrEmpty(aDef.getLogicalPeopleGroup()))
         {
            AeLogicalPeopleGroupDef lpg = getValidationContext().findLogicalPeopleGroup(aDef, aDef.getLogicalPeopleGroup());
            getFoundLogicalPeopleGroups().add(lpg);
         }
         super.visit(aDef);
      }

      /**
       * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor#visitExpressionDef(org.activebpel.rt.ht.def.IAeHtExpressionDef)
       */
      protected void visitExpressionDef(IAeHtExpressionDef aDef)
      {
         try
         {
            // parse the expression to get the parse results.
            IAeB4PValidationContext ctx = getValidationContext();
            IAeExpressionValidationResult result = ctx.validateExpression((AeBaseXmlDef) aDef, aDef.getExpression(), aDef.getExpressionLanguage());
            for (Iterator functionsIter = result.getParseResult().getFunctions().iterator(); functionsIter.hasNext();)
            {
               AeScriptFuncDef functionDef = (AeScriptFuncDef) functionsIter.next();
               
               // check the argument if the function is getLogicalPeopleGroup and the param is a string argument
               boolean isLPGFunction = IAeHtFunctionNames.LOGICAL_PEOPLE_GROUP_FUNCTION_NAME.equals(functionDef.getName());
               if (functionDef != null && functionDef.isStringArgument(0) && isLPGFunction)
               {
                  IAeFunction function = sFunctionFactory.getFunction(functionDef.getNamespace(), functionDef.getName());
                  
                  if (function instanceof AeAbstractBpelFunction)
                  {
                     AeAbstractBpelFunction lpgFunction = (AeAbstractBpelFunction) function;
                     QName lpgName = new QName(lpgFunction.getStringArg(functionDef.getArgs(), 0));
                     AeLogicalPeopleGroupDef lpg = getValidationContext().findLogicalPeopleGroup((AeBaseXmlDef)aDef, lpgName);
                     getFoundLogicalPeopleGroups().add(lpg);
                  }
               }
            }
         }
         catch (Exception ex)
         {
            // NO-OP
         }
      }

      /**
       * @return Returns the foundLogicalPeopleGroups.
       */
      public Set getFoundLogicalPeopleGroups()
      {
         return mFoundLogicalPeopleGroups;
      }
   }
   
   
   /**
    * Helper inner class to find any LPG references in a to-spec or from-spec.
    * This class is contructed with an <code>AeAbstractB4PExpressionDefVisitor</code> 
    * visitor so that any extension defs can be visited and checked for LPG references.
    */
   private class AeToFromSpecRule26Visitor extends AeExtensionDefRuleVisitor
   {
      /** Set of referenced LPGs on all visited defs */
      private Set mFoundLogicalPeopleGroups = new HashSet();
      /** a visitor that will be passed to all expression defs */
      private AeAbstractB4PExpressionDefVisitor mExpressionVisitor = null;
      
      /**
       * C'tor
       * @param aExpressionVisitor
       */
      public AeToFromSpecRule26Visitor(AeAbstractB4PExpressionDefVisitor aExpressionVisitor)
      {
         setExpressionVisitor(aExpressionVisitor);
      }

      /**
       * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeFromDef)
       */
      public void visit(AeFromDef aDef)
      {
         processExtensionAttributes(aDef);
         super.visit(aDef);
      }

      /**
       * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeToDef)
       */
      public void visit(AeToDef aDef)
      {
         processExtensionAttributes(aDef);
         super.visit(aDef);
      }
      
      /**
       * Process the extension attribute looking for b4p:logicalPeopleGroup attribute and store its value.
       * 
       * @param aDef
       */
      protected void processExtensionAttributes(AeVarDef aDef)
      {
         if (aDef.getExtensionAttributeDefs() != null && aDef.getExtensionAttributeDefs().size() > 0)
         {
            AeExtensionAttributeDef attrDef = aDef.getExtensionAttributeDef(sAttributeQName);
            if (attrDef != null)
            {
               QName lpgName = new QName(attrDef.getValue());
               AeLogicalPeopleGroupDef lpg = getValidationContext().findLogicalPeopleGroup(aDef, lpgName);
               getFoundLogicalPeopleGroups().add(lpg);
            }
         }
      }

      /**
       * @see org.activebpel.rt.b4p.validation.rules.AeExtensionDefRuleVisitor#acceptExtensionBaseXmlDef(org.activebpel.rt.xml.def.AeBaseXmlDef)
       */
      protected void acceptExtensionBaseXmlDef(AeBaseXmlDef aDef)
      {
         aDef.accept(getExpressionVisitor());
      }

      /**
       * @return Returns the foundLogicalPeopleGroups.
       */
      public Set getFoundLogicalPeopleGroups()
      {
         return mFoundLogicalPeopleGroups;
      }

      /**
       * @return Returns the visitor.
       */
      protected AeAbstractB4PExpressionDefVisitor getExpressionVisitor()
      {
         return mExpressionVisitor;
      }

      /**
       * @param aVisitor the visitor to set
       */
      protected void setExpressionVisitor(AeAbstractB4PExpressionDefVisitor aVisitor)
      {
         mExpressionVisitor = aVisitor;
      }
   }
}
