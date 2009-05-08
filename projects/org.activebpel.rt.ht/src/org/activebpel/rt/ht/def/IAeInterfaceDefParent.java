// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAeInterfaceDefParent.java,v 1.2 2008/02/17 21:51:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def;

/**
 * Parent elements of the 'interface' element must implement this interface
 */
public interface IAeInterfaceDefParent
{
   /**
    * Returns the IAeInterfaceDefA (i.e. taskInterface, notificationInterface) for
    * the task or notification.
    * 
    */
   public IAeInterfaceDef getInterfaceDef();
}
