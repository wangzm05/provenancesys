//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/xsl/AeXslTaskConstants.java,v 1.3 2008/02/07 22:37:49 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.xsl;

import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.ht.api.IAeHtWsIoConstants;

/**
 * Contains constants for namespaces as well as a prefix to NS look up map.
 *
 */
public class AeXslTaskConstants
{
   public static final String PARAMS_NS = "http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/formparams";  //$NON-NLS-1$
   public static final String COMMANDS_NS = "http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/taskcommands";  //$NON-NLS-1$
   public static final String ERROR_NS = "http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/errors";  //$NON-NLS-1$

   /** Prefix to NS map */
   public static final Map NSS_MAP = new HashMap();
   
   static {
      NSS_MAP.put("aefp", PARAMS_NS); //$NON-NLS-1$
      NSS_MAP.put("aetc", COMMANDS_NS); //$NON-NLS-1$
      NSS_MAP.put("trt", IAeHtWsIoConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
      NSS_MAP.put("hdt", IAeHtWsIoConstants.WSHT_NAMESPACE); //$NON-NLS-1$
      NSS_MAP.put("aefe", ERROR_NS); //$NON-NLS-1$
   }   
}
