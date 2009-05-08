// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeB4PTaskDefRule43Validator.java,v 1.1 2008/02/21 22:06:39 dvilaverde Exp $
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
import org.activebpel.rt.b4p.def.AeLocalTaskDef;
import org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTasksDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * Report problem if Task not used (constellation 2 only) 
 */
public class AeB4PTaskDefRule43Validator extends AeAbstractB4PValidator
{
   /** set of all task names */
   private Set mTasks = new HashSet();
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTasksDef)
    */
   public void visit(AeTasksDef aDef)
   {
      executeRule( aDef );
      super.visit( aDef );
   }

   /**
    * Rule logic
    * @param aDef
    */
   protected void executeRule(AeTasksDef aDef)
   {
      // load the Set of all the tasks 
      for (Iterator tasks = aDef.getTaskDefs(); tasks.hasNext();)
      {
         AeTaskDef def = (AeTaskDef) tasks.next();
         getTasks().add(def);
      }
      
      //get the enclosing scope or process and visit all extensions
      AeBaseDef def = AeDefUtil.getEnclosingScopeDef(aDef);
      
      final AeTaskRule43Visitor extensionViz = new AeTaskRule43Visitor();
      def.accept(new AeExtensionDefRuleVisitor() {
            protected void acceptExtensionBaseXmlDef(AeBaseXmlDef aDef)
            {
               aDef.accept(extensionViz);
            }
         }
      );
      
      // remove all found references only leaving the unused tasks
      getTasks().removeAll(extensionViz.getFoundTaskReferences());
      
      // report a problem on the unused tasks
      for (Iterator unusedIter = getTasks().iterator(); unusedIter.hasNext();)
      {
         AeTaskDef task = (AeTaskDef) unusedIter.next();
         
         if (task != null)
         {
            String message = AeMessages.format("AeB4PTaskDefRule43Validator.TASK_NOT_REFERENCED", new Object[] {task.getName()}); //$NON-NLS-1$
            reportProblem(message, task);
         }
      }
   }

   /**
    * @return Returns the tasks.
    */
   protected Set getTasks()
   {
      return mTasks;
   }
   
   
   /**
    * Helper inner class to find references to the Human Interaction tasks.
    */
   private class AeTaskRule43Visitor extends AeAbstractTraversingB4PDefVisitor
   {
      /** Set is a collection of strings representing found task reference names */
      private Set mFoundTaskReferences = new HashSet();

      /**
       * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
       */
      public void visit(AeLocalTaskDef aDef)
      {
         if (AeUtil.notNullOrEmpty(aDef.getReference()))
         {
            AeTaskDef task = getValidationContext().findTask(aDef, aDef.getReference());
            getFoundTaskReferences().add(task);
         }
         super.visit(aDef);
      }
      
      /**
       * @return Returns the foundNotificationReferences.
       */
      public Set getFoundTaskReferences()
      {
         return mFoundTaskReferences;
      }
   }
}
