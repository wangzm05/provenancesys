// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeLogicalPeopleGroupDefRule25Validator.java,v 1.2 2008/02/15 17:40:57 EWittmann Exp $
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
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupsDef;
import org.activebpel.rt.util.AeUtil;

/**
 * Logical people group name unique within parent
 */
public class AeLogicalPeopleGroupDefRule25Validator extends AeAbstractHtValidator
{

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef)
    */
   public void visit(AeLogicalPeopleGroupDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * Rule logic
    *  
    * @param aDef
    */
   protected void executeRule(AeLogicalPeopleGroupDef aDef)
   {
      AeLogicalPeopleGroupsDef lpgParent = (AeLogicalPeopleGroupsDef) aDef.getParentDef();
      
      for (Iterator iter = lpgParent.getLogicalPeopleGroupDefs(); iter.hasNext();)
      {
         AeLogicalPeopleGroupDef lgpDef = (AeLogicalPeopleGroupDef) iter.next();
         
         if (lgpDef != null && lgpDef != aDef && AeUtil.compareObjects(lgpDef.getName(), aDef.getName()))
         {
            reportProblem(AeMessages.getString("AeLogicalPeopleGroupDefRule25Validator.0"), aDef); //$NON-NLS-1$
            break;
         }
      }
   }

}
