// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/filter/IAeWhereConditionVisitor.java,v 1.1 2008/02/02 19:11:35 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.server.storage.filter;

/**
 * Where condition visitor interface.
 */
public interface IAeWhereConditionVisitor
{
   /**
    * Visits the condition.
    * 
    * @param aCondition
    */
   public void visit(AeCompositeWhereCondition aCondition);
   
   /**
    * Visits the condition.
    * 
    * @param aCondition
    */
   public void visit(AeWhereCondition aCondition);
}
