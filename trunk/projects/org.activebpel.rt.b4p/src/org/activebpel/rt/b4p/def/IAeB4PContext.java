//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/IAeB4PContext.java,v 1.2 2007/11/14 19:14:31 jbik Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def;

/**
 * Interface which provides a gateway from a HumanInteractions element to enclosing 
 * HumanInteractions elements.
 */
public interface IAeB4PContext
{
   /**
    * Returns the next enclosing parent {@link AeB4PHumanInteractionsDef} relative to this
    * objects location. If we are the highest enclosing level, null will be returned.
    */
   public AeB4PHumanInteractionsDef getEnclosingHumanInteractionsDef();
}