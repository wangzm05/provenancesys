// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeArgumentDefRule8Validator.java,v 1.2 2008/02/15 17:40:56 EWittmann Exp $
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
import org.activebpel.rt.ht.def.AeArgumentDef;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.util.AeUtil;

/**
 * Argument name is unique within collection of arguments
 */
public class AeArgumentDefRule8Validator extends AeAbstractHtValidator
{

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeArgumentDef)
    */
   public void visit(AeArgumentDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   

   /**
    * Rule Logic
    * 
    * @param aDef
    */
   protected void executeRule(AeArgumentDef aDef)
   {
      AeFromDef argParent = (AeFromDef) aDef.getParentDef();
      
      for (Iterator iter = argParent.getArgumentDefs().iterator(); iter.hasNext();)
      {
         AeArgumentDef argDef = (AeArgumentDef) iter.next();
         
         if (argDef != null && argDef != aDef && AeUtil.compareObjects(argDef.getName(), aDef.getName()))
         {
            reportProblem(AeMessages.getString("AeArgumentDefRule8Validator.0"), aDef); //$NON-NLS-1$
            break;
         }
         
      }
   }

}
