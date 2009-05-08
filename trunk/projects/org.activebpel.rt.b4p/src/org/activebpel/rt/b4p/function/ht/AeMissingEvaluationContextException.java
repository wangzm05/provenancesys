// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/ht/AeMissingEvaluationContextException.java,v 1.1 2008/02/07 02:05:37 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.function.ht;

import java.text.MessageFormat;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.bpel.function.AeFunctionCallException;

/**
 * An exception that gets thrown if the ws-ht context is not present when
 * trying to evaluate a ws-ht expression.
 */
public class AeMissingEvaluationContextException extends AeFunctionCallException
{
   private static String sFormatString = AeMessages.getString("AeMissingEvaluationContextException.MissingContextErrorMessage"); //$NON-NLS-1$
   
   /**
    * C'tor.
    * 
    * @param aFunctionName
    */
   public AeMissingEvaluationContextException(String aFunctionName)
   {
      super(MessageFormat.format(sFormatString, new Object[] { aFunctionName }));
   }
}
