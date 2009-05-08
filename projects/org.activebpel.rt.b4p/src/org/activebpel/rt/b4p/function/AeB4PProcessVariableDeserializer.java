//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/AeB4PProcessVariableDeserializer.java,v 1.2 2008/02/17 21:36:03 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.bpel.AeMessageDataFactory;
import org.activebpel.rt.bpel.IAeVariableView;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.w3c.dom.Element;

/**
 * Deserializes variable data given <code>aeb4p:processVariable</code> element.
 */
public class AeB4PProcessVariableDeserializer
{
   public static QName ELEMENT_NAME = new QName(IAeB4PConstants.AEB4P_NAMESPACE, "processVariable"); //$NON-NLS-1$

   /** Prefix to NS map */
   public static final Map NSS_MAP = new HashMap();
   static
   {
      NSS_MAP.put("aeb4p", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
   }

   /** Engine's data type mappings. */
   private AeTypeMapping mTypeMapping;
   /** Process variable */
   private AeB4PProcessVariable mVariable;

   /**
    * Ctor.
    * @param aTypeMapping
    */
   public AeB4PProcessVariableDeserializer(AeTypeMapping aTypeMapping)
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
    * @return current variable instance.
    */
   protected AeB4PProcessVariable getVariable()
   {
      return mVariable;
   }

   /**
    * Creates a AeB4PProcessVariable with given name.
    * @param aName variable name.
    */
   protected void createVariable(String aName)
   {
      mVariable = new AeB4PProcessVariable(aName);
   }

   /**
    * Validates the variable element.
    * @param aProcessVariableElement
    * @throws AeException
    */
   protected void validate(Element aProcessVariableElement) throws AeException
   {
      // fixme (PJ): throw type exceptions instead of generic AeExceptions.
      if (aProcessVariableElement == null)
      {
         throw new AeException(AeMessages.getString("AeB4PProcessVariableDeserializer.PROCESS_VARIABLES_ELEMENT_NULL")); //$NON-NLS-1$
      }
      if (!ELEMENT_NAME.equals( AeXmlUtil.getElementType(aProcessVariableElement)) )
      {
         throw new AeException(AeMessages.getString("AeB4PProcessVariableDeserializer.INVALID_VARIABLES_ELEMENT")); //$NON-NLS-1$
      }
      String name = aProcessVariableElement.getAttribute("name"); //$NON-NLS-1$
      if (AeUtil.isNullOrEmpty(name))
      {
         throw new AeException(AeMessages.getString("AeB4PProcessVariableDeserializer.VARIABLE_NAME_REQUIRED")); //$NON-NLS-1$
      }
   }

   /**
    * Deserializes a <code>aeb4p:processVariable</code> element and returns a variable instance.
    * @param aProcessVariableElement
    * @throws AeException
    */
   public IAeVariableView deserialize(Element aProcessVariableElement) throws AeException
   {
      validate(aProcessVariableElement);
      createVariable(aProcessVariableElement.getAttribute("name")); //$NON-NLS-1$)
      if (isType(aProcessVariableElement))
      {
         deserializeTypeData(aProcessVariableElement);
      }
      else if (isElementType(aProcessVariableElement))
      {
         deserializeElementData(aProcessVariableElement);
      }
      else if (isMessageType(aProcessVariableElement))
      {
         deserializeMessageData(aProcessVariableElement);
      }
      return getVariable();
   }

   /**
    * Deserializes message data.
    * @param aProcessVariableElement
    * @throws AeException
    */
   protected void deserializeElementData(Element aProcessVariableElement) throws AeException
   {
      QName type = AeXmlUtil.getAttributeQName(aProcessVariableElement, "element"); //$NON-NLS-1$
      getVariable().setElement(type);
      getVariable().setElementData( AeXmlUtil.getFirstSubElement(aProcessVariableElement) );
   }

   /**
    * Deserializes type data.
    * @param aProcessVariableElement
    * @throws AeException
    */
   protected void deserializeTypeData(Element aProcessVariableElement) throws AeException
   {
      QName type = AeXmlUtil.getAttributeQName(aProcessVariableElement, "type"); //$NON-NLS-1$
      getVariable().setTypeDataType(type);
      Object data = createData(aProcessVariableElement, type);
      getVariable().setTypeData(data);
   }

   /**
    * Creates a native type data given data type and container element.
    * @param aElement
    * @param type
    * @return deserialized type.
    * @throws AeException
    */
   protected Object createData(Element aElement, QName type) throws AeException
   {
      Object data = AeXmlUtil.getFirstSubElement(aElement);
      // If the data is not an element (for a complex type), then the data is
      // the string serialization of a simple type.
      if (data == null)
      {
         data = AeXmlUtil.getText(aElement);
         data = getTypeMapping().deserialize(type, (String)data);
      }
      return data;
   }

   /**
    * Deserializes type data.
    * @param aProcessVariableElement
    * @throws AeException
    */
   protected void deserializeMessageData(Element aProcessVariableElement) throws AeException
   {
      QName messageType = AeXmlUtil.getAttributeQName(aProcessVariableElement, "message"); //$NON-NLS-1$
      List partElements = AeXPathUtil.selectNodes(aProcessVariableElement, "aeb4p:messagePart", NSS_MAP); //$NON-NLS-1$
      IAeMessageData messageData = AeMessageDataFactory.instance().createMessageData(messageType);
      getVariable().setMessageData(messageData);
      for (Iterator i = partElements.iterator(); i.hasNext(); )
      {
         Element partElement = (Element) i.next();
         String name = partElement.getAttribute("name"); //$NON-NLS-1$
         QName type = AeXmlUtil.getAttributeQName(partElement, "type"); //$NON-NLS-1$
         Object data = createData(partElement, type);
         messageData.setData(name, data);
      }
   }

   /**
    * Returns true if the variable is an element type.
    * @param aProcessVariableElement
    * @return true if the variable is of element type.
    */
   protected boolean isElementType(Element aProcessVariableElement)
   {
      return AeUtil.notNullOrEmpty(aProcessVariableElement.getAttribute("element")); //$NON-NLS-1$
   }

   /**
    * @param aProcessVariableElement
    * @return true if the variable is of message type.
    */
   protected boolean isMessageType(Element aProcessVariableElement)
   {
      return AeUtil.notNullOrEmpty(aProcessVariableElement.getAttribute("message")); //$NON-NLS-1$
   }

   /**
    * @param aProcessVariableElement
    * @return true if the variable is of schema type.
    */
   protected boolean isType(Element aProcessVariableElement)
   {
      return AeUtil.notNullOrEmpty(aProcessVariableElement.getAttribute("type")); //$NON-NLS-1$
   }

}
