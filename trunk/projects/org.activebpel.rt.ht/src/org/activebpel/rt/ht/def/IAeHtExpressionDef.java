//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAeHtExpressionDef.java,v 1.2 2007/11/26 16:05:53 rnaylor Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

/**
 * Interface for all WS-HT defs that can be expressions.
 */
public interface IAeHtExpressionDef
{

   /**
    * Gets the expression language configured for this def. This may be null or empty, in which case the
    * process level expressionLanguage setting should be used.
    */
   public String getExpressionLanguage();

   /**
    * Sets the expression language URI on the def.
    * @param aLanguageURI
    */
   public void setExpressionLanguage(String aLanguageURI);

   /**
    * Gets the expression.
    */
   public String getExpression();

   /**
    * Sets the expression.
    */
   public void setExpression(String aExpr);
}
