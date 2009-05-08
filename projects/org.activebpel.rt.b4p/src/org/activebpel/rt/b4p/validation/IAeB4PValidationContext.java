// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/IAeB4PValidationContext.java,v 1.2 2008/01/11 20:00:58 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation;

import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.ht.def.validation.IAeHtValidationContext;

/**
 * Interface defining the Human Interactions validation context.
 */
public interface IAeB4PValidationContext extends IAeHtValidationContext
{
   /** Returns the BPEL process containing the B4P extension. */
   public AeProcessDef getProcessDef();

}
