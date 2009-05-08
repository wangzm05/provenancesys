//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/io/readers/AeB4PDefReaderRegistry.java,v 1.3 2007/10/23 12:34:35 ewittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.io.readers;

import org.activebpel.rt.b4p.def.io.readers.def.AeB4PDefReaderVisitor;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.io.readers.IAeReaderFactory;
import org.activebpel.rt.xml.def.io.readers.IAeReportingDefReader;
import org.w3c.dom.Element;

/**
 * Implementation of BPEL4People Read registry
 */
public class AeB4PDefReaderRegistry extends AeAbstractB4PReaderRegistry
{

   private static final IAeReaderFactory sFactory = new IAeReaderFactory()
      {
         /**
          * @see org.activebpel.rt.xml.def.io.readers.IAeReaderFactory#createReportingDefReader(org.activebpel.rt.xml.def.AeBaseXmlDef,
          *      org.activebpel.rt.xml.def.AeBaseXmlDef, org.w3c.dom.Element)
          */
         public IAeReportingDefReader createReportingDefReader(AeBaseXmlDef aParentDef, AeBaseXmlDef aNewDef, Element aElement)
         {
            return new AeB4PDefReaderVisitor(aParentDef, aElement);
         }
      };

   /**
    * Default c'tor.
    */
   public AeB4PDefReaderRegistry()
   {
      super(sFactory);
   }
}
