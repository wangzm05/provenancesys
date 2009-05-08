//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/AeB4PProcessVariable.java,v 1.1 2007/11/01 18:36:14 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.IAeVariableView;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.impl.AeUninitializedVariableException;
import org.activebpel.rt.message.IAeMessageData;
import org.w3c.dom.Element;

/**
 * Implements a IAeImmutableVariable representing data
 * deserialized from a <code>aeb4p:processVariable</code> element.
 * This variable is normally used by the aebp4:eval() function.
 */
public class AeB4PProcessVariable implements IAeVariableView
{
   /** BPEL namespace. */
   private String mBPELNamespace;
   /** Variable name. */
   private String mName;
   /** The associated message data if any. */
   private IAeMessageData mMessageData;
   /** The associated type data if any. ComplexTypes are stored as a Document for reasons described in getElementData() */
   private Object mTypeData;
   /** The associated element if any. This is stored as a Document for reasons described in getElementData()*/
   private Element mElementData;
   /** Location path override if different from the definition object */

   /** Element type */
   private QName mElementType;
   /** Message type. */
   private QName mMessageType;
   /** Simple or complex data type. */
   private QName mTypeDataType;

   /**
    * Ctor.
    * @param aName variable name.
    * @param aBPELNamespace BPEL namespace
    */
   public AeB4PProcessVariable(String aName, String aBPELNamespace)
   {
      mName = aName;
      mBPELNamespace = aBPELNamespace;
   }

   /**
    * Contructs AeB4PProcessVariable using defaul WS-BPEL 2.0 namespace.
    * @param aName variable name.
    */
   public AeB4PProcessVariable(String aName)
   {
      this(aName, IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI);
   }

   /**
    * @return BPEL Namespace.
    */
   private String getBPELNamespace()
   {
      return mBPELNamespace;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeVariableView#getElement()
    */
   public QName getElement()
   {
      return mElementType;
   }

   /**
    * Sets the element type QName.
    * @param aElementType
    */
   public void setElement(QName aElementType)
   {
      mElementType = aElementType;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeVariableView#getElementData()
    */
   public Element getElementData() throws AeUninitializedVariableException
   {
      if (mElementData == null)
      {
         throw new AeUninitializedVariableException( getBPELNamespace() );
      }
      else
      {
         return mElementData;
      }
   }

   /**
    * Sets the element data.
    * @param aElementData
    */
   public void setElementData(Element aElementData)
   {
      mElementData = aElementData;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeVariableView#getMessageData()
    */
   public IAeMessageData getMessageData() throws AeUninitializedVariableException
   {
      if (mMessageData == null)
      {
         throw new AeUninitializedVariableException( getBPELNamespace() );
      }
      else
      {
         return mMessageData;
      }
   }

   /**
    * Sets the message data.
    * @param aMessageData
    */
   public void setMessageData(IAeMessageData aMessageData)
   {
      mMessageData = aMessageData;
   }

   public QName getMessageType()
   {
      return mMessageType;
   }

   /**
    * Sets the message type.
    * @param aMessageType
    */
   public void setMessageType(QName aMessageType)
   {
      mMessageType = aMessageType;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeVariableView#getName()
    */
   public String getName()
   {
      return mName;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeVariableView#getType()
    */
   public QName getType()
   {
      return mTypeDataType;
   }

   /**
    * Sets schema type name.
    * @param aTypeDataType
    */
   public void setTypeDataType(QName aTypeDataType)
   {
      mTypeDataType = aTypeDataType;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeVariableView#getTypeData()
    */
   public Object getTypeData() throws AeUninitializedVariableException
   {
      return mTypeData;
   }

   /**
    * Sets type data.
    * @param aData
    */
   public void setTypeData(Object aData)
   {
      mTypeData = aData;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeVariableView#hasData()
    */
   public boolean hasData()
   {
      return mMessageData != null || mElementData != null || mTypeData != null;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeVariableView#hasMessageData()
    */
   public boolean hasMessageData()
   {
      return mMessageData != null;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeVariableView#isElement()
    */
   public boolean isElement()
   {
      return mElementData != null;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeVariableView#isMessageType()
    */
   public boolean isMessageType()
   {
      return mMessageData != null;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeVariableView#isType()
    */
   public boolean isType()
   {
      return mTypeData != null;
   }
}
