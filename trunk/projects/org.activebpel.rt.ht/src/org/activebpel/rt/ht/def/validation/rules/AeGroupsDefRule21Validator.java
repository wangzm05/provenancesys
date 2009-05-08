// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeGroupsDefRule21Validator.java,v 1.2 2008/02/15 17:40:57 EWittmann Exp $
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
import org.activebpel.rt.ht.def.AeGroupsDef;
import org.activebpel.rt.ht.def.AeLiteralDef;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.io.AeHtIO;

/**
 * An AeGroupsDef must have children. 
 */
public class AeGroupsDefRule21Validator extends AeAbstractHtValidator
{

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeGroupsDef)
    */
   public void visit(AeGroupsDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLiteralDef)
    */
   public void visit(AeLiteralDef aDef)
   {
      try
      {
         AeOrganizationalEntityDef def = (AeOrganizationalEntityDef) AeHtIO.deserialize(aDef);
         if (def != null)
         {
            def.accept(getTraverser());
         }
      }
      catch(Exception ex)
      {
         reportException(ex, aDef);
      }
      finally 
      {
         super.visit(aDef);
      }
   }
   
   /**
    * 
    * @param aDef
    */
   protected void executeRule(AeGroupsDef aDef)
   {
      if(aDef.size() <= 0)
      {
         reportProblem(AeMessages.getString("AeGroupsDefRule21Validator.0"), aDef); //$NON-NLS-1$
      }
   }

}
