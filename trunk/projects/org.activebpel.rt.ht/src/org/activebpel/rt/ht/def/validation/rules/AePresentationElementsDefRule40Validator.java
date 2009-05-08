// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AePresentationElementsDefRule40Validator.java,v 1.2 2008/02/15 17:40:57 EWittmann Exp $
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
import org.activebpel.rt.ht.def.AePresentationElementsDef;

/**
 * Active-Endpoints requires a subject for use in with our inbox.
 */
public class AePresentationElementsDefRule40Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationElementsDef)
    */
   public void visit(AePresentationElementsDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AePresentationElementsDef aDef)
   {
      if (aDef.size() == 0)
      {
         reportProblem(AeMessages.getString("AePresentationElementsDefRule40Validator.0"), aDef); //$NON-NLS-1$
      }
   }

}
