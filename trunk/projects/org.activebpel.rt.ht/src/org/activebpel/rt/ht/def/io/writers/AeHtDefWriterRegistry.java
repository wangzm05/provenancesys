//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/io/writers/AeHtDefWriterRegistry.java,v 1.4 2007/11/20 18:49:59 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def.io.writers;

import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.ht.def.io.writers.def.AeHtDefWriterVisitor;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.io.writers.IAeDefWriter;
import org.activebpel.rt.xml.def.io.writers.IAeDefWriterFactory;
import org.w3c.dom.Element;

/**
 * Implementation of a WS-HT Def writer Registry
 *
 */
public class AeHtDefWriterRegistry extends AeAbstractHtWriterRegistry implements IAeHtDefConstants
{

   /**
    * Constructor
    */
   public AeHtDefWriterRegistry()
   {
      this(DEFAULT_HT_NS);
   }

   /**
    * Constructor
    */
   public AeHtDefWriterRegistry(String aNamespaceURI)
   {
      super(aNamespaceURI, sFactory);
   }

   /**
    * Factory that returns a WS-HT Def Writer
    */
   private static final IAeDefWriterFactory sFactory = new IAeDefWriterFactory()
      {
         /**
          * @see org.activebpel.rt.xml.def.io.writers.IAeDefWriterFactory#createDefWriter(org.activebpel.rt.xml.def.AeBaseXmlDef,
          *      org.w3c.dom.Element, java.lang.String, java.lang.String)
          */
         public IAeDefWriter createDefWriter(AeBaseXmlDef aDef, Element aParentElement, String aNamespaceUri, String aTagName)
         {
            return new AeHtDefWriterVisitor(aDef, aParentElement, aNamespaceUri, aTagName);
         }
      };
}
