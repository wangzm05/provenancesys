//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/io/AeB4PIO.java,v 1.15.4.2 2008/04/14 21:25:30 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.io;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.b4p.def.io.readers.AeB4PDefReaderRegistry;
import org.activebpel.rt.b4p.def.io.readers.AeB4PFragmentReaderRegistry;
import org.activebpel.rt.b4p.def.io.writers.AeB4PDefWriterRegistry;
import org.activebpel.rt.b4p.def.io.writers.AeB4PDelegatingDefWriterVisitor;
import org.activebpel.rt.b4p.def.visitors.AeB4PDefTraverser;
import org.activebpel.rt.b4p.def.visitors.AeB4PTraversalVisitor;
import org.activebpel.rt.bpel.def.activity.AeChildExtensionActivityDef;
import org.activebpel.rt.ht.IAeWSHTConstants;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.ht.def.AePresentationElementsDef;
import org.activebpel.rt.ht.def.io.readers.AeHtDefReaderRegistry;
import org.activebpel.rt.ht.def.io.writers.AeHtDefWriterRegistry;
import org.activebpel.rt.ht.def.io.writers.AeHtDelegatingDefWriterVisitor;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.io.AeDefIORegistry;
import org.activebpel.rt.xml.def.io.readers.AeDomTraverser;
import org.activebpel.rt.xml.def.io.writers.AeMashupDefWriterRegistry;
import org.activebpel.rt.xml.def.visitors.AeDefAssignParentVisitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * BPEL4People Def model deserializer/serializer
 */
public class AeB4PIO
{
   private static final AeDefIORegistry sProtocolRegistry = new AeDefIORegistry(new AeHtDefReaderRegistry(), new AeHtDefWriterRegistry(IAeWSHTConstants.WSHT_PROTOCOL_NS));
   private static final AeDefIORegistry sInternalRegistry = new AeDefIORegistry(new AeHtDefReaderRegistry(), new AeHtDefWriterRegistry(IAeB4PConstants.AEB4P_NAMESPACE));

   private static final List sWriterRegistryParticipants = new LinkedList();
   static
   {
      sWriterRegistryParticipants.add(new AeB4PDefWriterRegistry());
      sWriterRegistryParticipants.add(new AeHtDefWriterRegistry());
   };

   private static final AeDefIORegistry sRegistry = new AeDefIORegistry(new AeB4PDefReaderRegistry(),
         new AeMashupDefWriterRegistry(sWriterRegistryParticipants));

   private static final AeDefIORegistry sFragmentRegistry = new AeDefIORegistry(new AeB4PFragmentReaderRegistry(),
         new AeMashupDefWriterRegistry(sWriterRegistryParticipants));
   
   /**
    * private x-tor - no instantiation
    */
   private AeB4PIO()
   {
   }

   /**
    * Deserialize input source to AeHtTestDef
    * @param aInputSource input source that contains the Human Task xml
    * @return AeBaseXmlDef object model
    */
   public static AeBaseXmlDef deserialize(InputSource aInputSource) throws Exception
   {
      DocumentBuilder builder = AeXmlUtil.getDocumentBuilder();
      Document doc = builder.parse(aInputSource);
      return deserialize(doc);
   }

   /**
    * Deserialize the test xml to BPEL4People AeB4PHumanInteractionsDef.
    * @param aDocument
    * @return deserialized Document object model
    */
   public static AeBaseXmlDef deserialize(Document aDocument) throws Exception
   {
      return deserialize(aDocument.getDocumentElement());
   }

   /**
    * Deserialize the test xml to BPEL4People AeB4PHumanInteractionsDef.
    * @param aElement
    * @return AeBaseXmlDef object model
    */
   public static AeBaseXmlDef deserialize(Element aElement) throws AeException
   {
      AeDomTraverser traverser = new AeDomTraverser(sRegistry);
      traverser.traverseRootElement(aElement);

      return applyVisitors(traverser);
   }
   
   /**
    * Deserializes the element as an organizationalEntity
    * @param aElement
    * @throws AeException
    */
   public static AeOrganizationalEntityDef deserializeAsOrganizationalEntity(Element aElement) throws AeException
   {
      AeDefIORegistry registry = new AeDefIORegistry(new AeOrganizationalEntityRegistry(aElement), null);
      
      AeDomTraverser traverser = new AeDomTraverser(registry);
      traverser.traverseRootElement(aElement);

      return (AeOrganizationalEntityDef) applyVisitors(traverser);
   }
   
   /**
    * One-off reader that maps the root element name to the org entity def. 
    */
   private static class AeOrganizationalEntityRegistry extends AeHtDefReaderRegistry
   {
      /**
       * Ctor
       * @param aElement
       */
      public AeOrganizationalEntityRegistry(Element aElement)
      {
         // map whatever the element name is to orgEntity
         getGenericReadersMap().put(AeXmlUtil.getElementType(aElement), createReader(AeOrganizationalEntityDef.class) );
      }
   }

   /**
    * Deserialize the xml fragments to BPEL4People AeB4PHumanInteractionsDef.
    * @param aElement
    * @return AeBaseXmlDef object model
    */
   public static AeBaseXmlDef deserializeFragment(Element aElement) throws AeException
   {
      AeDomTraverser traverser = new AeDomTraverser(sFragmentRegistry);
      traverser.traverseRootElement(aElement);

      AeBaseXmlDef baseDef = applyVisitors(traverser);
      Map nsMap = new HashMap();
      AeXmlUtil.getDeclaredNamespaces(aElement, nsMap);
      baseDef.addNamespaces(nsMap);
      return baseDef;
   }
   
   /**
    * Deserializes the PA from the def
    * @param extensionDef
    * @throws AeException
    */
   public static AePeopleActivityDef deserializePeopleActivity(AeChildExtensionActivityDef extensionDef) throws AeException
   {
      AePeopleActivityDeserializer deserializer = new AePeopleActivityDeserializer(extensionDef);
      return deserializer.getDef();
   }


   /**
    * Deserialize an element as children of the passed aRootDef
    * @param aRootDef
    * @param aElement
    * @throws AeException
    */
   public static void deserialize(AePeopleActivityDef aRootDef, Element aElement) throws AeException
   {
      AeDomTraverser traverser = new AeDomTraverser(sRegistry);
      traverser.traverseElement(aRootDef,aElement);
      applyVisitors(traverser);
   }

   /**
    * Apply base visitors to the deserialized def
    * 
    * @param aTraverser
    */
   private static AeBaseXmlDef applyVisitors(AeDomTraverser aTraverser)
   {
      AeBaseXmlDef b4PBaseDefContainer = (AeBaseXmlDef) aTraverser.getRootDef();

      //Assign parent-to-child relationships for the def model
      AeDefAssignParentVisitor parentVisitor = new AeDefAssignParentVisitor();
      parentVisitor.setTraversalVisitor( new AeB4PTraversalVisitor(new AeB4PDefTraverser(), parentVisitor));
      b4PBaseDefContainer.accept(parentVisitor);

      return b4PBaseDefContainer;
   }

   /**
    * Serialize the AeB4PHumanInteractionsDef to its xml representation.
    * @param aDef
    * @throws Exception
    */
   public static Document serialize2Doc(AeBaseXmlDef aDef) throws Exception
   {
      aDef.addNamespacesInScope();
      AeB4PDelegatingDefWriterVisitor writerVisitor = serialize(aDef);
      return writerVisitor.getDocument();
   }

   /**
    * @param aDef
    */
   public static Element serialize2Element(AeBaseXmlDef aDef)
   {
      aDef.addNamespacesInScope();
      AeB4PDelegatingDefWriterVisitor writerVisitor = serialize(aDef);
      return writerVisitor.getElement();
   }

   /**
    * Serializes the people assignments def into the ht protocol schema
    * @param aDef
    */
   public static Element serialize2Protocol(AePeopleAssignmentsDef aDef)
   {
      return serialize(aDef, sProtocolRegistry);
   }

   /**
    * Serializes the presentations elements def to our internal schema for storage
    * @param aDef
    */
   public static Element serializePresentations(AePresentationElementsDef aDef)
   {
      return serialize(aDef, sInternalRegistry);
   }

   /**
    * Serializes the element using the provided registry
    * @param aDef
    * @param aRegistry
    */
   private static Element serialize(AeBaseXmlDef aDef,
         AeDefIORegistry aRegistry)
   {
      aDef.addNamespacesInScope();
      AeHtDelegatingDefWriterVisitor writerVisitor = new AeHtDelegatingDefWriterVisitor(aRegistry);
      aDef.accept(writerVisitor);
      return writerVisitor.getElement();
   }
   
   /**
    * @param aDef
    */
   private static AeB4PDelegatingDefWriterVisitor serialize(AeBaseXmlDef aDef)
   {
      AeB4PDelegatingDefWriterVisitor writerVisitor = new AeB4PDelegatingDefWriterVisitor(sRegistry);
      aDef.accept(writerVisitor);
      return writerVisitor;
   }
}
