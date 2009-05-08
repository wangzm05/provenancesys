//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeLiteralDef.java,v 1.5 2007/11/13 16:57:34 rnaylor Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Impl for the 'literal' Def
 */
public class AeLiteralDef extends AeHtBaseDef
{
   /** The list of child nodes in the literal. */
   private List mChildNodes = new ArrayList();

   /**
    * Default c'tor.
    */
   public AeLiteralDef()
   {
      super();
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeLiteralDef))
         return false;
      
      return AeUtil.compareObjects(((AeLiteralDef)aOther).getOrganizationalEntity(), getOrganizationalEntity());
   }
   
   /**
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      AeLiteralDef literal = (AeLiteralDef)super.clone();
      literal.mChildNodes = new ArrayList();
      
      // Make copy first to avoid ConcurrentModificationException
      List copyList = new ArrayList(getChildNodes());
      for (Iterator iter=copyList.iterator(); iter.hasNext();)
         literal.addChildNode((Node)iter.next());
      return literal;
   }
   
   /**
    * Returns a String representation of the text nodes
    */
   public String getOrganizationalEntity()
   {
      if (getChildNodes().size() == 0)
         return ""; //$NON-NLS-1$
      
      return AeXMLParserBase.documentToString((Node)getChildNodes().get(0));
   }
   
   /**
    * Sets the given value as the new text representation.
    * @param aSerializedXml
    */
   public void setOrganizationalEntity(String aSerializedXml)
   {
      try
      {
         getChildNodes().clear();
         if (AeUtil.notNullOrEmpty(aSerializedXml))
            addChildNode(AeXmlUtil.toDoc(aSerializedXml).getDocumentElement());
      }
      catch (AeException e)
      {
         AeException.logError(e);
      }
   }

   /**
    * The literal can only have a single child node but we're allowing for multiple here in order to preserve any
    * extra child nodes that we may have read in. We'll produce an error message for multiple children during validation.
    * @return Returns the childNodes.
    */
   public List getChildNodes()
   {
      return mChildNodes;
   }

   /**
    * Adds a child node to the list of child nodes.
    * 
    * @param aNode
    */
   public void addChildNode(Node aNode)
   {
      if (aNode instanceof Element)
      {
         mChildNodes.add(AeXmlUtil.cloneElement((Element) aNode));
      }
      else
      {
         mChildNodes.add(aNode.cloneNode(true));
      }
   }

   /**
    * @see org.activebpel.rt.ht.def.AeHtBaseDef#accept(org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor)
    */
   public void accept(IAeHtDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
   
   /**
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      StringBuffer buff = new StringBuffer();
      Element el = null;
      for (Iterator iter=getChildNodes().iterator();iter.hasNext() && el == null;)
      {
         Node node = (Node)iter.next();
         if (node.getNodeType() == Node.ELEMENT_NODE)
            el = (Element)node;
      }
      
      if (el != null)
      {
         Element child = AeXmlUtil.getFirstSubElement(el);
         if (child != null)
         {
            buff.append(child.getLocalName()).append(" ("); //$NON-NLS-1$
            
            boolean addComma = false;
            NodeList nodes=child.getChildNodes();
            for (int i=0,len=nodes.getLength(); i < len; i++)
            {
               if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE)
               {
                  if (addComma)
                     buff.append(", "); //$NON-NLS-1$
                  else
                     addComma = true;
                  
                  buff.append(AeXmlUtil.getText((Element)nodes.item(i)));
               }
            }
            buff.append(")"); //$NON-NLS-1$
         }
      }

      return buff.toString(); 
   }
   
   /**
    * @see org.activebpel.rt.xml.def.AeBaseXmlDef#addExtensionElementDef(org.activebpel.rt.xml.def.AeExtensionElementDef)
    */
   public void addExtensionElementDef(AeExtensionElementDef aExtension)
   {
      // Do nothing - the extension element has already been consumed
      // when the literal was read.
   }
}