// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AeActualOwnerRule124Validator.java,v 1.1 2008/03/15 22:18:35 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef;
import org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor;
import org.activebpel.rt.expr.def.AeScriptFuncDef;
import org.activebpel.rt.ht.IAeHtFunctionNames;
import org.activebpel.rt.ht.def.AeLocalNotificationDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.ht.def.IAeHtExpressionDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * task argument must be present for getActualOwner functions used within local notifications or notifications
 */
public class AeActualOwnerRule124Validator extends AeAbstractB4PValidator
{
   /** QName for the function being validated */
   private static QName sFunctionName = new QName(IAeHtDefConstants.DEFAULT_HT_NS, IAeHtFunctionNames.ACTUAL_OWNER_FUNCTION_NAME);
   
   /**
    * @see org.activebpel.rt.b4p.validation.rules.AeAbstractB4PValidator#individualFunctionValidation(org.activebpel.rt.ht.def.IAeHtExpressionDef, org.activebpel.rt.expr.def.AeScriptFuncDef)
    */
   protected void individualFunctionValidation(IAeHtExpressionDef aDef, AeScriptFuncDef aFunction)
   {
      if (AeUtil.compareObjects(aFunction.getQName(), sFunctionName) && aFunction.getArgs().size() <= 0)
      {
         ((AeBaseXmlDef)aDef).accept(new AeRule124Visitor((AeBaseXmlDef) aDef));
      }
   }
   
   
   /**
    * Helper visitor to report issues on the appropriate defs.  This visitor will walk up 
    * the parents until there are no more parents, and will report an error on notification defs
    * or local notification defs.
    */
   private class AeRule124Visitor extends AeAbstractB4PDefVisitor
   {
      /** the def that contains the expression in order to report the problem on it. */
      private AeBaseXmlDef mExpressionDef;
      
      /**
       * C'tor
       * @param aExpressionDef
       */
      public AeRule124Visitor(AeBaseXmlDef aExpressionDef)
      {
         mExpressionDef = aExpressionDef;
      }

      /**
       * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
       */
      public void visit(AeB4PLocalNotificationDef aDef)
      {
         reportProblem(AeMessages.format("AeActualOwnerRule124Validator.0", sFunctionName.getLocalPart()), mExpressionDef); //$NON-NLS-1$
      }

      /**
       * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeLocalNotificationDef)
       */
      public void visit(AeLocalNotificationDef aDef)
      {
         reportProblem(AeMessages.format("AeActualOwnerRule124Validator.0", sFunctionName.getLocalPart()), mExpressionDef); //$NON-NLS-1$
      }

      /**
       * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeNotificationDef)
       */
      public void visit(AeNotificationDef aDef)
      {
         reportProblem(AeMessages.format("AeActualOwnerRule124Validator.0", sFunctionName.getLocalPart()), mExpressionDef); //$NON-NLS-1$
      }

      /**
       * @see org.activebpel.rt.xml.def.visitors.AeBaseXmlDefVisitor#visitBaseXmlDef(org.activebpel.rt.xml.def.AeBaseXmlDef)
       */
      protected void visitBaseXmlDef(AeBaseXmlDef aDef)
      {
         // traverse up 
         if (aDef.getParentXmlDef() != null)
            aDef.getParentXmlDef().accept(this);
      }

   }
}
