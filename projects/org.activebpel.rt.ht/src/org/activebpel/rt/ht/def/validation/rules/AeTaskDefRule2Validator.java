// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeTaskDefRule2Validator.java,v 1.4 2008/02/15 17:40:57 EWittmann Exp $
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
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTasksDef;
import org.activebpel.rt.util.AeUtil;

/**
 * AeTaskDef is unique within the collection of AeTasksDef
 */
public class AeTaskDefRule2Validator extends AeAbstractHtValidator
{
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeTaskDef)
    */
   public void visit(AeTaskDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * Rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeTaskDef aDef)
   {
      if (aDef.getParentXmlDef() instanceof AeTasksDef)
      {
         AeTasksDef parent = (AeTasksDef) aDef.getParentXmlDef();
         
         for (Iterator iter = parent.getTaskDefs(); iter.hasNext();)
         {
            AeTaskDef task = (AeTaskDef) iter.next();
            if (task != null && aDef != task && AeUtil.compareObjects(aDef.getName(), task.getName()))
            {
               reportProblem(AeMessages.getString("AeTaskDefRule2Validator.0"), aDef);  //$NON-NLS-1$
               break;
            }
         }  
      }
   }
}
