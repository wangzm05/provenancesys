//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/io/writers/AeB4PDefWriterRegistry.java,v 1.4 2007/11/15 22:32:29 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.io.writers;

import org.activebpel.rt.b4p.def.io.writers.def.AeB4PDefWriterVisitor;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.io.writers.IAeDefWriter;
import org.activebpel.rt.xml.def.io.writers.IAeDefWriterFactory;
import org.w3c.dom.Element;

/**
 * Implementation of a BPEL4People Def writer Registry
 *
 */
public class AeB4PDefWriterRegistry extends AeAbstractB4PWriterRegistry
{

   /**
    * Constructor
    */
   public AeB4PDefWriterRegistry()
   {
      super(B4P_NAMESPACE, sFactory);
   }

   /**
    * Factory that returns a BPEL4People Def Writer
    */
   private static final IAeDefWriterFactory sFactory = new IAeDefWriterFactory()
      {
         /**
          * @see org.activebpel.rt.xml.def.io.writers.IAeDefWriterFactory#createDefWriter(org.activebpel.rt.xml.def.AeBaseXmlDef,
          *      org.w3c.dom.Element, java.lang.String, java.lang.String)
          */
         public IAeDefWriter createDefWriter(AeBaseXmlDef aDef, Element aParentElement, String aNamespaceUri, String aTagName)
         {
            return new AeB4PDefWriterVisitor(aDef, aParentElement, aNamespaceUri, aTagName);
         }
      };
}
