//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/io/readers/AeHTExtensionElementReader.java,v 1.1 2008/02/29 23:45:53 vvelusamy Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def.io.readers;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.def.AeDescriptionDef;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.activebpel.rt.xml.def.io.readers.AeExtensionElementReader;
import org.w3c.dom.Element;

/**
 * HT specific extension element reader.
 * 
 */
public class AeHTExtensionElementReader extends AeExtensionElementReader
{
   /*
    * @see org.activebpel.rt.xml.def.io.readers.AeExtensionElementReader#read(org.activebpel.rt.xml.def.AeBaseXmlDef,
    *      org.w3c.dom.Element)
    */
   public AeBaseXmlDef read(AeBaseXmlDef aParent, Element aElement) throws AeException
   {
      // If the def is a description def, handle the extension element without gathering all namespace declarations from
      // the entire document. The description def is assumed to be self-sufficient and we do not want users to see the
      // description content with extraneous namespace declarations.
      if (aParent instanceof AeDescriptionDef)
      {
         AeExtensionElementDef extElemDef = new AeExtensionElementDef(aElement, false);
         extElemDef.setParentXmlDef(aParent);
         aParent.addExtensionElementDef(extElemDef);
         return extElemDef;
      }
      else
      {
         return super.read(aParent, aElement);
      }
   }
}
