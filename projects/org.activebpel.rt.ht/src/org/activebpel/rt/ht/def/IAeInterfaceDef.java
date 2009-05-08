// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAeInterfaceDef.java,v 1.1 2008/01/21 22:03:19 dvilaverde Exp $
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
 * Interface for ht interface defs. This element appears in ht and b4p so we
 * need a common interface to share code that deals with task and 
 * notification interfaces.
 */
public interface IAeInterfaceDef
{
   /**
    * @return the operation
    */
   public abstract String getOperation();

   /**
    * @return the portType
    */
   public abstract QName getPortType();
}
