//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/request/AeExpressionLanguageVisitor.java,v 1.1 2008/02/07 02:07:22 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.request;

import org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor;
import org.activebpel.rt.ht.def.AePresentationParametersDef;
import org.activebpel.rt.ht.def.IAeHtExpressionDef;
import org.activebpel.rt.util.AeUtil;

/**
 * This visitor replaces expression language attributes in defs that can
 * have an expressionLanguage with the default expression language of a process 
 * when they are not set.
 */
public class AeExpressionLanguageVisitor extends AeAbstractB4PExpressionDefVisitor
{
   /** Current expression language. */
   private String mExpressionLanguage;

   /**
    * C'tor.
    *
    * @param aExpressionLanguage
    */
   public AeExpressionLanguageVisitor(String aExpressionLanguage)
   {
      mExpressionLanguage = aExpressionLanguage;
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AePresentationParametersDef)
    */
   public void visit(AePresentationParametersDef aDef)
   {
      if (AeUtil.isNullOrEmpty(aDef.getExpressionLanguage()))
         aDef.setExpressionLanguage(getExpressionLanguage());
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor#visitExpressionDef(org.activebpel.rt.ht.def.IAeHtExpressionDef)
    */
   protected void visitExpressionDef(IAeHtExpressionDef aDef)
   {
      if (AeUtil.notNullOrEmpty(aDef.getExpression()) && AeUtil.isNullOrEmpty(aDef.getExpressionLanguage()))
         aDef.setExpressionLanguage(getExpressionLanguage());
   }

   /**
    * @return the expressionLanguage
    */
   public String getExpressionLanguage()
   {
      return mExpressionLanguage;
   }

   /**
    * @param aExpressionLanguage the expressionLanguage to set
    */
   public void setExpressionLanguage(String aExpressionLanguage)
   {
      mExpressionLanguage = aExpressionLanguage;
   }
}
