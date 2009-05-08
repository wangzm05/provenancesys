//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/AeB4PVariableUsageVisitor.java,v 1.8.2.1 2008/04/21 16:08:04 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.visitors;

import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.IAeExpressionLanguageFactory;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.expr.def.AeExpressionAnalyzerContext;
import org.activebpel.rt.expr.def.IAeExpressionAnalyzer;
import org.activebpel.rt.expr.def.IAeExpressionAnalyzerContext;
import org.activebpel.rt.ht.def.AeArgumentDef;
import org.activebpel.rt.ht.def.AeConditionDef;
import org.activebpel.rt.ht.def.AeForDef;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AeLocalNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AePresentationParameterDef;
import org.activebpel.rt.ht.def.AePriorityDef;
import org.activebpel.rt.ht.def.AeProcessDataExpressionDef;
import org.activebpel.rt.ht.def.AeSearchByDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeToPartDef;
import org.activebpel.rt.ht.def.IAeHtExpressionDef;
import org.activebpel.rt.ht.def.IAeVariableUsageContainer;
import org.activebpel.rt.ht.def.visitors.AeHtTraversalVisitor;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseDefNamespaceContext;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * implementation of B4P visitor to collect all used variables in the expressions of <code>task</code> and
 * <code>notification</code> elements and assign their locationPath Def object to the respective
 * task/notification Def object.
 * <p>
 * Note that the following visitors must be run before this visitor runs.
 * <ol>
 * <li>visitor that deserializes BPEL4People extension elements.</li>
 * <li>visitor that inlines <code>localTask</code> and <code>localNotification</code> references.</li>
 * </ol>
 * </p>
 * @see org.activebpel.rt.ht.def.IAeVariableUsageContainer
 */
public class AeB4PVariableUsageVisitor extends AeAbstractTraversingB4PDefVisitor
{

   /** The expression language factory. */
   private IAeExpressionLanguageFactory mExpressionLanguageFactory;

   /**
    * <code>task</code> and <code>notification</code> Def objects are pushed and popped from the stack as
    * we traverse the tree
    */
   private Stack mStack = new Stack();

   /**
    * C'tor
    */
   public AeB4PVariableUsageVisitor(IAeExpressionLanguageFactory aExpressionLanguageFactory)
   {
      setExpressionLanguageFactory(aExpressionLanguageFactory);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#createTraversalVisitor()
    */
   protected AeHtTraversalVisitor createTraversalVisitor()
   {
      return new AeB4PTraversalVisitor(new AeB4PNotificationFirstDefTraverser(), this);
   }

   /**
    * Peeks the stack 
    */
   protected AeBaseXmlDef peek()
   {
      return (AeBaseXmlDef) getStack().peek();
   }

   /**
    * @return the task Def object on the bottom of the stack; otherwise return null
    */
   protected AeTaskDef peekTask()
   {
      IAeVariableUsageContainer def = (IAeVariableUsageContainer)getStack().lastElement();
      if ( def instanceof AeTaskDef )
         return (AeTaskDef)def;
      else
         return null;
   }

   /** put the variable usage container Def object on the stack */
   protected void push(AeBaseXmlDef aDef)
   {
      getStack().push(aDef);
   }

   /** check for an empty stack */
   protected boolean isEmptyStack()
   {
      return getStack().isEmpty();
   }

   protected IAeVariableUsageContainer pop()
   {
      return (IAeVariableUsageContainer)getStack().pop();
   }

   /**
    * Called by all of the visit methods in order to traverse the tree. Serves as a good place to record the
    * def if it's an activity because we'll be revisiting all of the def objects to install empty sets if
    * there are no serialized scopes within this process def.
    * @param aDef
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#traverse(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   protected void traverse(AeBaseXmlDef aDef)
   {
      // keep track of task and notification Def objects that are containers of used variables
      if ( aDef instanceof IAeVariableUsageContainer )
      {
         push(aDef);

         aDef.accept(getTraversalVisitor());

         IAeVariableUsageContainer topDef = pop();

         if ( !isEmptyStack() )
            addVariablesToTask((IAeVariableUsageContainer)topDef);
      }
      else
      {
         aDef.accept(getTraversalVisitor());
      }
   }

   /**
    * Adds the notification usage variables to the parent task
    * @param aDef the notification Def object
    */
   private void addVariablesToTask(IAeVariableUsageContainer aDef)
   {
      if ( peekTask() != null )
      {
         Set vars = aDef.getUsedVariables();
         peekTask().getUsedVariables().addAll(vars);
         if ( aDef instanceof AeLocalNotificationDef )
         {
            AeNotificationDef localNotification = ((AeLocalNotificationDef)aDef).getInlineNotificationDef();
            if (localNotification != null)
               addVariablesToTask(localNotification);
         }
      }
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeArgumentDef)
    */
   public void visit(AeArgumentDef aDef)
   {
      parseForVariables(aDef);
      super.visit(aDef);
   }


   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeProcessDataExpressionDef)
    */
   public void visit(AeProcessDataExpressionDef aDef)
   {
      parseForVariables(aDef);
      super.visit(aDef);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      parseForVariables(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeToPartDef)
    */
   public void visit(AeToPartDef aDef)
   {
      parseForVariables(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeConditionDef)
    */
   public void visit(AeConditionDef aDef)
   {
      parseForVariables(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeForDef)
    */
   public void visit(AeForDef aDef)
   {
      parseForVariables(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePriorityDef)
    */
   public void visit(AePriorityDef aDef)
   {
      parseForVariables(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeSearchByDef)
    */
   public void visit(AeSearchByDef aDef)
   {
      parseForVariables(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationParameterDef)
    */
   public void visit(AePresentationParameterDef aDef)
   {
      parseForVariables(aDef);
      super.visit(aDef);
   }

   /**
    * Getter for the stack
    */
   protected Stack getStack()
   {
      return mStack;
   }

   /**
    * @return Returns the expressionLanguageFactory.
    */
   protected IAeExpressionLanguageFactory getExpressionLanguageFactory()
   {
      return mExpressionLanguageFactory;
   }

   /**
    * @param aExpressionLanguageFactory The expressionLanguageFactory to set.
    */
   protected void setExpressionLanguageFactory(IAeExpressionLanguageFactory aExpressionLanguageFactory)
   {
      mExpressionLanguageFactory = aExpressionLanguageFactory;
   }

   /**
    * Uses the expression parser to look for any signs of variable usage within the string passed in. This
    * includes the built in bpel functions getVariableProperty and getVariableData. If these are found then a
    * shared lock is added since we're only reading the variable and not updating it.
    * @param aExpressionDef
    */
   protected void parseForVariables(IAeHtExpressionDef aExpressionDef)
   {
      if ( aExpressionDef == null || AeUtil.isNullOrEmpty(aExpressionDef.getExpression()) || isEmptyStack() )
         return;

      try
      {
         AeProcessDef processDef = AeDefUtil.getProcessDef((AeBaseXmlDef) aExpressionDef);
         String language = getExpressionLanguage(aExpressionDef, processDef);
         IAeExpressionAnalyzer analyzer = getExpressionLanguageFactory().createExpressionAnalyzer(processDef.getNamespace(), language);
         IAeExpressionAnalyzerContext ctx = new AeExpressionAnalyzerContext(new AeBaseDefNamespaceContext(peek()));

         IAeVariableUsageContainer variableUsageContainer = findVariableUsageContainer((AeBaseXmlDef)aExpressionDef);
         
         Set usedVariables = analyzer.getVariables(ctx, aExpressionDef.getExpression());
         for (Iterator itr = usedVariables.iterator(); itr.hasNext();)
         {
            // get the variable and add it to the usage container Def object
            String varName = (String)itr.next();
            AeVariableDef variableDef = AeDefUtil.getVariableByName(varName, (AeBaseXmlDef) aExpressionDef);
            if (variableDef != null)
            {
               variableUsageContainer.addUsedVariable(variableDef.getLocationPath());
            }
         }
      }
      catch (AeException e)
      {
         AeException.logError(e, e.getLocalizedMessage());
      }
   }

   /**
    * Walks the def looking for the nearest variable usage container
    * @param aDef
    */
   private IAeVariableUsageContainer findVariableUsageContainer(
         AeBaseXmlDef aDef)
   {
      for(AeBaseXmlDef current = aDef; current != null; current = current.getParentXmlDef())
      {
         if (current instanceof IAeVariableUsageContainer)
            return (IAeVariableUsageContainer) current;
      }
      return null;
   }

   /**
    * Gets the expression language to use parsing the expression.
    * @param aExpressionDef
    * @param aProcessDef
    */
   private String getExpressionLanguage(IAeHtExpressionDef aExpressionDef, AeProcessDef aProcessDef)
   {
      String expressionLanguage = aExpressionDef.getExpressionLanguage();
      if ( AeUtil.isNullOrEmpty(expressionLanguage) )
      {
         expressionLanguage = aProcessDef.getExpressionLanguage();
      }
      return expressionLanguage;
   }
}
