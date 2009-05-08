// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AePeopleActivityDefRule60Validator.java,v 1.2 2008/02/15 17:47:59 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;

/**
 * skipable for tasks only
 */
public class AePeopleActivityDefRule60Validator extends AeAbstractB4PValidator
{
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AePeopleActivityDef)
    */
   public void visit(AePeopleActivityDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * rule logic
    * @param aDef
    */
   protected void executeRule(AePeopleActivityDef aDef)
   {
      if (aDef.isSkipable() && aDef.getTask() == null && aDef.getLocalTask() == null)
      {
         reportProblem(AeMessages.getString("AePeopleActivityDefRule60Validator.0"), aDef); //$NON-NLS-1$
      }
   }
}
