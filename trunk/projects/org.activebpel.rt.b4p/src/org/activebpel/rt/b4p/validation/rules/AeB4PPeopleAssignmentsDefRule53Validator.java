// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeB4PPeopleAssignmentsDefRule53Validator.java,v 1.2 2008/02/15 17:47:59 EWittmann Exp $
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
import org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef;
import org.activebpel.rt.bpel.def.AeProcessDef;

/**
 * must be parented by process
 */
public class AeB4PPeopleAssignmentsDefRule53Validator extends AeAbstractB4PValidator
{
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef)
    */
   public void visit(AeB4PPeopleAssignmentsDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   protected void executeRule(AeB4PPeopleAssignmentsDef aDef)
   {
      // since B4PPeopleAssignments are extensions to WS-BPEL 2.0 
      // the immediate parent is an extension object, so the grand parent 
      // will be evaluated.      
      
      if ( !(aDef.getParentXmlDef().getParentXmlDef() instanceof AeProcessDef) )
      {
         reportProblem(AeMessages.getString("AeB4PPeopleAssignmentsDefRule53Validator.0"), aDef); //$NON-NLS-1$
      }
   }
}
