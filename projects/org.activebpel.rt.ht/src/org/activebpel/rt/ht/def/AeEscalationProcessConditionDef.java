// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeEscalationProcessConditionDef.java,v 1.2 2008/03/01 04:54:56 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def;

/**
 * Specific class to model a custom escalation process condition.  This
 * is necessary for proper serialization of the defs (our serializer
 * registry needs to differentiate this from a regular condition).
 */
public class AeEscalationProcessConditionDef extends AeConditionDef
{
   /**
    * Default c'tor.
    */
   public AeEscalationProcessConditionDef()
   {
   }
   
   /**
    * Copy c'tor.
    * 
    * @param aConditionDef
    */
   public AeEscalationProcessConditionDef(AeConditionDef aConditionDef)
   {
      setExpressionLanguage(aConditionDef.getExpressionLanguage());
      setExpression(aConditionDef.getExpression());
      setNamespace(aConditionDef.getNamespace());
      
      setComment(aConditionDef.getComment());
      setDefaultNamespace(aConditionDef.getDefaultNamespace());
      setLocationId(aConditionDef.getLocationId());
      setParentXmlDef(aConditionDef.getParentXmlDef());
   }
}
