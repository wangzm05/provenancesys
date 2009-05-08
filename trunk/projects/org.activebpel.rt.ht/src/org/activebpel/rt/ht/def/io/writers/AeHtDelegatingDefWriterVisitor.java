// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/io/writers/AeHtDelegatingDefWriterVisitor.java,v 1.5 2007/11/15 22:32:01 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def.io.writers;

import org.activebpel.rt.ht.def.visitors.AeAbstractTraversingHtDefVisitor;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.io.IAeDefRegistry;
import org.activebpel.rt.xml.def.io.writers.AeBaseXmlDefElementCreator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Work horse class for traversing and serializing the AeHumanInteractionsDef object model.
 */
public class AeHtDelegatingDefWriterVisitor extends AeAbstractTraversingHtDefVisitor
{
   /** XML element creator. */
   private AeBaseXmlDefElementCreator mCreator;

   /**
    * Constructor.
    * @param aRegistry to retrieve IAeHtDefWriter impls
    */
   public AeHtDelegatingDefWriterVisitor(IAeDefRegistry aRegistry)
   {
      setCreator(new AeBaseXmlDefElementCreator(aRegistry, getTraversalVisitor()));
   }

   /**
    * Accessor for the serialized document.
    * 
    * @return the xml
    */
   public Document getDocument()
   {
      return getCreator().getDocument();
   }

   /**
    * Accessor for the serialized element.
    * 
    * @return the dom element
    */
   public Element getElement()
   {
      return getCreator().getElement();
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractTraversingHtDefVisitor#traverse(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   protected void traverse(AeBaseXmlDef aDef)
   {
      // call the traverse method with the current node from the stack which serves as the parent element
      // for the element we're going to create for this def.
      getCreator().createElementAndTraverse(aDef);
   }

   /**
    * @return the creator
    */
   public AeBaseXmlDefElementCreator getCreator()
   {
      return mCreator;
   }

   /**
    * @param aCreator the creator to set
    */
   public void setCreator(AeBaseXmlDefElementCreator aCreator)
   {
      mCreator = aCreator;
   }
}
