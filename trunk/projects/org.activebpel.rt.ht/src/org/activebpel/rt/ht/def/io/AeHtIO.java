//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/io/AeHtIO.java,v 1.12 2008/02/17 21:51:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def.io;

import org.activebpel.rt.ht.def.AeHtBaseDef;
import org.activebpel.rt.ht.def.AeHumanInteractionsDef;
import org.activebpel.rt.ht.def.AeLiteralDef;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.ht.def.io.readers.AeHtDefReaderRegistry;
import org.activebpel.rt.ht.def.io.writers.AeHtDefWriterRegistry;
import org.activebpel.rt.ht.def.io.writers.AeHtDelegatingDefWriterVisitor;
import org.activebpel.rt.ht.def.visitors.AeHtDefTraverser;
import org.activebpel.rt.ht.def.visitors.AeHtLocationPathVisitor;
import org.activebpel.rt.ht.def.visitors.AeHtTraversalVisitor;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.io.AeDefIORegistry;
import org.activebpel.rt.xml.def.io.readers.AeDomTraverser;
import org.activebpel.rt.xml.def.visitors.AeDefAssignParentVisitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * WS-HT Def model deserializer/serializer
 */
public class AeHtIO
{
   private static final AeDefIORegistry sRegistry = new AeDefIORegistry(new AeHtDefReaderRegistry(), new AeHtDefWriterRegistry());

   /**
    * private x-tor - no instantiation
    */
   private AeHtIO()
   {
   }

   /**
    * Deserialize the test xml to AeHumanInteractionsDef.
    * @param aDocument
    * @return AeHumanInteractionsDef object model
    */
   public static AeHumanInteractionsDef deserialize(Document aDocument) throws Exception
   {
      AeDomTraverser traverser = new AeDomTraverser(sRegistry);
      traverser.traverseRootElement(aDocument.getDocumentElement());

      AeHumanInteractionsDef humanInteractionsDef = (AeHumanInteractionsDef)traverser.getRootDef();

      // Assign parent-to-child relationships for the def model
      AeDefAssignParentVisitor parentVisitor = new AeDefAssignParentVisitor();
      parentVisitor.setTraversalVisitor(new AeHtTraversalVisitor(new AeHtDefTraverser(), parentVisitor));
      humanInteractionsDef.accept(parentVisitor);

      return humanInteractionsDef;
   }
   
   /**
    * Deserialize a literal def to an HT base def.  Null is returned if the element 
    * within the literal is not within the HT namespace or is invalid.
    * 
    * @param aLiteral
    * @return an AeOrganizationalEntityDef def
    * @throws Exception
    */
   public static AeHtBaseDef deserialize(AeLiteralDef aLiteral) throws Exception
   {
      AeHtBaseDef htDef = null;
      
      Element extElem = (Element)aLiteral.getChildNodes().get(0);
      if (extElem != null && AeUtil.compareObjects(extElem.getNamespaceURI(), IAeHtDefConstants.DEFAULT_HT_NS))
      {
         AeDomTraverser traverser = new AeDomTraverser(sRegistry);
         traverser.traverseRootElement(extElem);
         
         htDef = (AeHtBaseDef) traverser.getRootDef();
         
         // Assign parent-to-child relationships for the def model
         AeDefAssignParentVisitor parentVisitor = new AeDefAssignParentVisitor();
         parentVisitor.setTraversalVisitor(new AeHtTraversalVisitor(new AeHtDefTraverser(), parentVisitor));
         htDef.accept(parentVisitor);
         
         AeHtLocationPathVisitor lpVisitor = new AeHtLocationPathVisitor();
         lpVisitor.setPath(aLiteral.getLocationPath());
         htDef.accept(lpVisitor);
      }
      
      return htDef;
   }

   /**
    * @param aDef
    */
   public static Element serialize2Element(AeHtBaseDef aDef)
   {
      aDef.addNamespacesInScope();
      AeHtDelegatingDefWriterVisitor writerVisitor = new AeHtDelegatingDefWriterVisitor(sRegistry);
      aDef.accept(writerVisitor);
      return writerVisitor.getElement();
   }
   
   /**
    * Serialize the AeHumanInteractionsDef to its xml representation.
    * @param aHumanInteractionsDef the object model
    * @return Dom Document of the serialized WS-HT Def object model
    */
   public static Document serialize(AeHumanInteractionsDef aHumanInteractionsDef)
   { 
      String defaultNS = aHumanInteractionsDef.getDefaultNamespace();
      aHumanInteractionsDef.removeDefaultNamespace();

      AeHtDelegatingDefWriterVisitor writerVisitor = new AeHtDelegatingDefWriterVisitor(sRegistry);
      try
      {
         aHumanInteractionsDef.accept(writerVisitor);
      }
      finally
      {
         // Restore the previously saved default namespace, if any.
         aHumanInteractionsDef.setDefaultNamespace(defaultNS);
      }
      return writerVisitor.getDocument();
   }
}
