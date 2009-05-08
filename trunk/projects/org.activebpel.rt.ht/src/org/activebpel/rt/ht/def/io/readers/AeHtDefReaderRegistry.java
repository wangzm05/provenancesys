//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/io/readers/AeHtDefReaderRegistry.java,v 1.4 2007/11/15 22:32:00 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def.io.readers;

import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.ht.def.io.readers.def.AeHtDefReaderVisitor;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.io.readers.IAeReaderFactory;
import org.activebpel.rt.xml.def.io.readers.IAeReportingDefReader;
import org.w3c.dom.Element;

/**
 * The concrete HT def reader registry.
 */
public class AeHtDefReaderRegistry extends AeAbstractHtReaderRegistry implements IAeHtDefConstants
{
   /**
    * Factory to use when reading.
    */
   private static final IAeReaderFactory sFactory = new IAeReaderFactory()
      {
         /**
          * @see org.activebpel.rt.xml.def.io.readers.IAeReaderFactory#createReportingDefReader(org.activebpel.rt.xml.def.AeBaseXmlDef,
          *      org.activebpel.rt.xml.def.AeBaseXmlDef, org.w3c.dom.Element)
          */
         public IAeReportingDefReader createReportingDefReader(AeBaseXmlDef aParentDef, AeBaseXmlDef aNewDef, Element aElement)
         {
            return new AeHtDefReaderVisitor(aParentDef, aElement);
         }
      };

   /**
    * C'tor.
    */
   public AeHtDefReaderRegistry()
   {
      super(DEFAULT_HT_NS, sFactory);
   }
}
