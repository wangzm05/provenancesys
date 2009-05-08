//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/AeB4PProcessVariableSerializer.java,v 1.2 2007/11/20 18:51:10 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <p>Serializes a IAeVariable to a <code>aeb4p:processVariable</code> element.</p>
 */
public class AeB4PProcessVariableSerializer
{
   /** Engine's data type mappings. */   
   private AeTypeMapping mTypeMapping;
   /** Variable root element. */
   private Element mElement;

   /**
    * Ctor.
    * @param aTypeMapping
    */
   public AeB4PProcessVariableSerializer(AeTypeMapping aTypeMapping)
   {
      mTypeMapping = aTypeMapping;
   }

   /** 
    * @return Engine data type mapping.
    */
   protected AeTypeMapping getTypeMapping()
   {
      return mTypeMapping;
   }
   
   /**
    * Resets the element data.
    */
   protected void reset()
   {
      mElement = null;
   }

   /**
    * @return <code>aeb4p:processVariable</code> element
    */
   protected Element getElement()
   {
      if (mElement == null)
      {
         Document doc = AeXmlUtil.newDocument();
         mElement = AeXmlUtil.addElementNS(doc, IAeB4PConstants.AEB4P_NAMESPACE, "aeb4p:processVariable", null); //$NON-NLS-1$
         mElement.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:aeb4p", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
      }
      return mElement;
   }

   /**
    * Serializes a variable and returns <code>aeb4p:processVariable</code> element
    * @param aVariable
    * @return <code>aeb4p:processVariable</code> element
    * @throws AeBusinessProcessException
    */
   public Element serialize(IAeVariable aVariable) throws AeBusinessProcessException
   {
      reset();
      getElement().setAttribute("name", aVariable.getName());       //$NON-NLS-1$
      boolean hasData = aVariable.hasData();
      if (hasData)
      {
         if (aVariable.isType())
         {
            serializeType(aVariable);
         }
         else if (aVariable.isElement())
         {
            serializeElement(aVariable);
         }
         else if (aVariable.isMessageType())
         {
            serializeMessage(aVariable);
         }
         else
         {
            // data not found.
            throw new AeBusinessProcessException();
         }
      }
      return getElement();
   }

   /**
    * Serialize variable type data.
    * @param aVariable
    * @throws AeBusinessProcessException
    */
   protected void serializeType(IAeVariable aVariable) throws AeBusinessProcessException
   {
      serializeType(getElement(), aVariable.getType(), aVariable.getTypeData(), false);
   }

   /**
    * Serialize schema type data given container element, type and data.
    * @param aContainerElem
    * @param aType
    * @param aData
    * @throws AeBusinessProcessException
    */
   protected void serializeType(Element aContainerElem, QName aType, Object aData, boolean aIgnoreType) throws AeBusinessProcessException
   {
      if (aType == null)
      {
         aType =  getTypeMapping().getXSIType(aData);
      }
      if ((aType != null) && !aIgnoreType)
      {
         AeXmlUtil.setAttributeQName(aContainerElem, "type", aType); //$NON-NLS-1$
      }
      Node child = null;
      if (aData instanceof Node)
      {
         // Complex type.
         if (aData instanceof Document)
         {
            child = aContainerElem.getOwnerDocument().importNode(((Document) aData).getDocumentElement(), true);
         }
         else
         {
            child = aContainerElem.getOwnerDocument().importNode(((Node) aData), true);
         }
      }
      else
      {
         // Simple type.
         child = aContainerElem.getOwnerDocument().createTextNode( getTypeMapping().serialize(aData) );
      }
      aContainerElem.appendChild(child);
   }

   /**
    * Serialize variable element types
    * @param aVariable
    * @throws AeBusinessProcessException
    */
   protected void serializeElement(IAeVariable aVariable) throws AeBusinessProcessException
   {
      serializeElement(getElement(), aVariable.getElement(), aVariable.getElementData());
   }

   /**
    * Serialize element types given parent element,  type and element data.
    * @param aContainerElem
    * @param aType
    * @param aDataElem
    * @throws AeBusinessProcessException
    */
   protected void serializeElement(Element aContainerElem, QName aType, Element aDataElem) throws AeBusinessProcessException
   {
      AeXmlUtil.setAttributeQName(aContainerElem, "element", aType); //$NON-NLS-1$
      Node child = aContainerElem.getOwnerDocument().importNode( aDataElem, true);
      aContainerElem.appendChild(child);
   }

   /**
    * Serialize message data.
    * @param aVariable
    * @throws AeBusinessProcessException
    */
   protected void serializeMessage(IAeVariable aVariable) throws AeBusinessProcessException
   {
      AeXmlUtil.setAttributeQName(getElement(), "message", aVariable.getMessageType()); //$NON-NLS-1$
      IAeMessageData messageData = aVariable.getMessageData();
      serializeMessage(messageData, getElement(), IAeB4PConstants.AEB4P_NAMESPACE, IAeB4PConstants.AEB4P_PREFIX, "messagePart", false); //$NON-NLS-1$
   }
   
   /**
    * Serializes an instance of IAeMessageData  
    * @param aData
    * @param aElement
    * @param aNameSpace
    * @param aPrefix
    * @param aPartElementName
    * @throws AeBusinessProcessException
    */
   public void serializeMessage(IAeMessageData aData, Element aElement, String aNameSpace, String aPrefix, String aPartElementName, boolean aIgnoreType) throws AeBusinessProcessException
   {
      for (Iterator i = aData.getPartNames(); i.hasNext(); )
      {
         String name = (String) i.next();
         Element partElem = AeXmlUtil.addElementNS(aElement, aNameSpace, aPrefix+":"+aPartElementName, null);  //$NON-NLS-1$
         partElem.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:"+aPrefix, aNameSpace); //$NON-NLS-1$
         partElem.setAttribute("name", name); //$NON-NLS-1$
         Object data = aData.getData(name);
         serializeType(partElem, null, data, aIgnoreType);
      }
   }
}
