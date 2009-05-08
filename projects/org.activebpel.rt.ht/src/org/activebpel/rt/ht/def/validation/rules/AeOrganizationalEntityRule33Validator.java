// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeOrganizationalEntityRule33Validator.java,v 1.1 2008/03/03 22:44:54 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AeHtBaseDef;
import org.activebpel.rt.ht.def.AeLiteralDef;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.io.AeHtIO;

/**
 * choice of users or groups
 */
public class AeOrganizationalEntityRule33Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLiteralDef)
    */
   public void visit(AeLiteralDef aDef)
   {
      try
      {
         AeHtBaseDef def = AeHtIO.deserialize(aDef);
         def.accept(this);
      }
      catch (Exception ex)
      {
         reportException(ex, aDef);
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeOrganizationalEntityDef)
    */
   public void visit(AeOrganizationalEntityDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * rule logic
    * @param aDef
    */
   protected void executeRule(AeOrganizationalEntityDef aDef)
   {
      if (aDef.getUsers() != null && aDef.getGroups() != null)
      {
         reportProblem(AeMessages.getString("AeOrganizationalEntityRule33Validator.0"), aDef); //$NON-NLS-1$
      }
      
      //TODO (DV) check for no users and no groups elements
   }

}
