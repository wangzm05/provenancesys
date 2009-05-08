//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/request/AeExpressionDef.java,v 1.1 2008/02/08 21:40:59 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.request;

import org.activebpel.rt.bpel.def.IAeExpressionDef;

/**
 * Local implementation of IAeExpressionDef to execute expressions defined in tasks and people assignments
 */
public class AeExpressionDef implements IAeExpressionDef
{
   /** Expression */
   private String mExpression;

   /** Expression language */
   private String mExpressionLanguage;

   /** BPEL namespace */
   private String mBpelNamespace;

   /**
    * @return the expression
    */
   public String getExpression()
   {
      return mExpression;
   }

   /**
    * @param aExpression the expression to set
    */
   public void setExpression(String aExpression)
   {
      mExpression = aExpression;
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

   /**
    * @return the bpelNamespace
    */
   public String getBpelNamespace()
   {
      return mBpelNamespace;
   }

   /**
    * @param aBpelNamespace the bpelNamespace to set
    */
   protected void setBpelNamespace(String aBpelNamespace)
   {
      mBpelNamespace = aBpelNamespace;
   }

}
