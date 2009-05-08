// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeLocalTaskDefRule56Validator.java,v 1.3 2008/02/16 20:52:17 dvilaverde Exp $
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
import org.activebpel.rt.b4p.def.AeLocalTaskDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.def.AeBaseDefNamespaceContext;

/**
 * reference resolves to task
 */
public class AeLocalTaskDefRule56Validator extends AeAbstractB4PValidator
{
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeLocalTaskDef)
    */
   public void visit(AeLocalTaskDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * rule logic 
    * @param aDef
    */
   protected void executeRule(AeLocalTaskDef aDef)
   {
      if (AeUtil.notNullOrEmpty(aDef.getReference()))
      {
         if (getValidationContext().findTask(aDef, aDef.getReference()) == null)
         {
            String qName = AeXmlUtil.encodeQName(aDef.getReference(), new AeBaseDefNamespaceContext(aDef), null);
            String message = AeMessages.format("AeLocalTaskDefRule56Validator.0", new Object[] {qName}); //$NON-NLS-1$
            
            reportProblem(message, aDef);
         }
      }
      else
      {
         String message = AeMessages.getString("AeLocalTaskDefRule56Validator.1");  //$NON-NLS-1$
         reportProblem(message, aDef);
      }
   }
}
