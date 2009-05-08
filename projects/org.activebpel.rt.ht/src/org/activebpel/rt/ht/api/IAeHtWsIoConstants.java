//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/IAeHtWsIoConstants.java,v 1.1 2008/01/18 22:51:53 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api;

import org.activebpel.rt.ht.IAeWSHTConstants;

/**
 * WS-HT API constants
 */
public interface IAeHtWsIoConstants extends IAeWSHTConstants
{
   public static final String AEB4P_NAMESPACE = "http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd"; //$NON-NLS-1$
   public static final String AEB4P_TASKSTATE_NAMESPACE = "http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-state-wsdl.xsd"; //$NON-NLS-1$

   public static final String AEB4P_PREFIX = "aeb4p"; //$NON-NLS-1$
   public static final String AEB4P_TASKSTATE_PREFIX = "tsst"; //$NON-NLS-1$
   
   public static final String IDENTITY_SERVICE_NAMESPACE = "http://docs.active-endpoints/wsdl/identity/2007/03/identity.wsdl"; //$NON-NLS-1$
}
