// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AePeopleActivityDefRule62Validator.java,v 1.3 2008/03/15 22:18:35 dvilaverde Exp $
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
import org.activebpel.rt.util.AeUtil;

/** 
 * no 'outputVariable' attribute or 'fromPart' element for notification
 */
public class AePeopleActivityDefRule62Validator extends AeAbstractB4PValidator
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
      if (aDef.isNotification())
      {
         if (aDef.getOutputVariable() != null && !AeUtil.compareObjects("(none)", aDef.getOutputVariable())) //$NON-NLS-1$
         {
            reportProblem(AeMessages.getString("AePeopleActivityDefRule62Validator.0"), aDef); //$NON-NLS-1$
         }
         if (aDef.getFromPartsDef() != null)
         {
            reportProblem(AeMessages.getString("AePeopleActivityDefRule62Validator.1"), aDef); //$NON-NLS-1$
         }
      }
   }
}
