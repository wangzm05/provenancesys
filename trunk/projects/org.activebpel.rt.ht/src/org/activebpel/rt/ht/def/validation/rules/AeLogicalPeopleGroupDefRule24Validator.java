// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeLogicalPeopleGroupDefRule24Validator.java,v 1.2 2008/02/15 17:40:56 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.util.AeXmlUtil;

/**
 * NCName for task must be valid.
 */
public class AeLogicalPeopleGroupDefRule24Validator extends AeAbstractHtValidator
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
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AeLogicalPeopleGroupDef aDef)
   {
      if (!AeXmlUtil.isValidNCName(aDef.getName()))
      {
         reportProblem(AeMessages.getString("AeLogicalPeopleGroupDefRule24Validator.0"), aDef); //$NON-NLS-1$
      }
   }

}
