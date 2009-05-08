// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeEscalationDefRule15Validator.java,v 1.2 2008/02/15 17:40:57 EWittmann Exp $
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
import org.activebpel.rt.ht.def.AeAbstractDeadlineDef;
import org.activebpel.rt.ht.def.AeEscalationDef;
import org.activebpel.rt.util.AeUtil;

/**
 * Escalation name unique within deadline
 */
public class AeEscalationDefRule15Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationDef)
    */
   public void visit(AeEscalationDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeEscalationDef aDef)
   {
      AeAbstractDeadlineDef deadLine = (AeAbstractDeadlineDef) aDef.getParentDef();
      
      for (Iterator iter = deadLine.getEscalationDefs(); iter.hasNext(); )
      {
         AeEscalationDef escalation = (AeEscalationDef) iter.next();
         if (escalation != aDef && AeUtil.compareObjects(escalation.getName(), aDef.getName()))
         {
            reportProblem(AeMessages.getString("AeEscalationDefRule15Validator.0"), aDef); //$NON-NLS-1$
         }
      }
   }
}
