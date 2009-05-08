//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/IAeMixedContentElement.java,v 1.1 2007/10/04 15:44:44 jbik Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import java.util.Iterator;

import org.w3c.dom.Element;

/**
 * Implemented by elements with mixed content
 */
public interface IAeMixedContentElement
{
   /**
    * reads text nodes of the passed Element 
    * @param aElement
    */
   public void readMixedText(Element aElement);
   
   /**
    * @return iterator to the list of AeTextNodeDef objects.
    */
   public Iterator getMixedTextDef();
}
