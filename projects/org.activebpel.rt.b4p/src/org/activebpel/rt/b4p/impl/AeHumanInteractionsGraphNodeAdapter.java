//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/AeHumanInteractionsGraphNodeAdapter.java,v 1.3 2008/02/17 21:36:03 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl;

import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNode;
import org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNodeAdapter;
import org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNodeProperty;
import org.w3c.dom.Document;

/**
 * This is graph node adapter that builds the graph node tree for human interactions extension element
 */
public class AeHumanInteractionsGraphNodeAdapter implements IAeXmlDefGraphNodeAdapter
{

   /** def object */
   private AeB4PHumanInteractionsDef mDef;
   
   /**
    * C'tor
    * @param aDef
    */
   public AeHumanInteractionsGraphNodeAdapter(AeB4PHumanInteractionsDef aDef)
   {
      mDef = aDef;
   }

   /**
    * @see org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNodeAdapter#getIcon()
    */
   public String getIcon()
   {
      return null;
   }

   /**
    * @see org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNodeAdapter#getProperties(org.activebpel.rt.xml.def.AeBaseXmlDef, org.w3c.dom.Document, java.lang.String)
    */
   public IAeXmlDefGraphNodeProperty[] getProperties(AeBaseXmlDef aDef, Document aStateDoc, String aInstancePath)
   {
      AeHIPropertyBuilder builder = new AeHIPropertyBuilder();
      return builder.createProperties(aDef, aStateDoc);
   }

   /**
    * @see org.activebpel.rt.xml.def.graph.IAeXmlDefGraphNodeAdapter#getTreeNode()
    */
   public IAeXmlDefGraphNode getTreeNode()
   {
      AeHIGraphNodeVisitor graphNodeVisitor = new AeHIGraphNodeVisitor();
      return graphNodeVisitor.createXmlGraphNode(getDef());
   }

   /**
    * @return the def
    */
   protected AeB4PHumanInteractionsDef getDef()
   {
      return mDef;
   }

   /**
    * @param aDef the def to set
    */
   protected void setDef(AeB4PHumanInteractionsDef aDef)
   {
      mDef = aDef;
   }

}
