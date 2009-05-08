// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeB4PExpressionBaseDef.java,v 1.1 2007/11/16 02:43:14 jbik Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2006 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.def;

import org.activebpel.rt.ht.def.IAeHtExpressionDef;

/**
 * Base class for all defs that specify an expression, such as for, until.
 */
public abstract class AeB4PExpressionBaseDef extends AeB4PBaseDef implements IAeHtExpressionDef
{
   /** The 'expressionLanguage' attribute. */
   private String mExpressionLanguage;

   /** The value of the expression. */
   private String mExpression;

   /**
    * Default c'tor.
    */
   public AeB4PExpressionBaseDef()
   {
      super();
   }

   /**
    * @return Returns the expressionLanguage.
    */
   public String getExpressionLanguage()
   {
      return mExpressionLanguage;
   }

   /**
    * @param aExpressionLanguage The expressionLanguage to set.
    */
   public void setExpressionLanguage(String aExpressionLanguage)
   {
      mExpressionLanguage = aExpressionLanguage;
   }

   /**
    * @return Returns the expression.
    */
   public String getExpression()
   {
      return mExpression;
   }

   /**
    * @param aExpression The expression to set.
    */
   public void setExpression(String aExpression)
   {
      mExpression = aExpression;
   }

}
