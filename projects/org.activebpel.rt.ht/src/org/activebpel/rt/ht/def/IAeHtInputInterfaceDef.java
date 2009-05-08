// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAeHtInputInterfaceDef.java,v 1.1 2007/12/28 14:56:11 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def;

import javax.xml.namespace.QName;

/**
 * Both the task and notification interfaces implement this interface.
 */
public interface IAeHtInputInterfaceDef
{
   /**
    * Gets the port type.
    */
   public QName getPortType();

   /**
    * Sets the port type.
    * 
    * @param aPortType
    */
   public void setPortType(QName aPortType);

   /**
    * Gets the operation.
    */
   public String getOperation();

   /**
    * Sets the operation.
    * 
    * @param aOperation
    */
   public void setOperation(String aOperation);
}
