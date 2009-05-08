// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AeParameterDefRule38Validator.java,v 1.2 2008/02/15 17:40:57 EWittmann Exp $
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
import org.activebpel.rt.ht.def.AeParameterDef;
import org.activebpel.rt.xml.schema.AeTypeMapping;

/**
 * Parameter type is simple type
 */
public class AeParameterDefRule38Validator extends AeAbstractHtValidator
{
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeParameterDef)
    */
   public void visit(AeParameterDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }
   
   /**
    * rule logic 
    * 
    * @param aDef
    */
   protected void executeRule(AeParameterDef aDef)
   {
      if (!AeTypeMapping.isSimpleTypeSupported(aDef.getType()))
      {
         reportProblem(AeMessages.getString("AeParameterDefRule38Validator.0"), aDef); //$NON-NLS-1$
      }
   }

}
