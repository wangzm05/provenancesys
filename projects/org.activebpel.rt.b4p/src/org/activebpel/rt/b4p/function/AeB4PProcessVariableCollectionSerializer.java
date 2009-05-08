//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/AeB4PProcessVariableCollectionSerializer.java,v 1.2 2007/11/30 21:47:33 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function;

import java.util.Collection;
import java.util.Iterator;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Utility class that is responsible for serializing a collection
 * of process variables into a &lt;aeb4p:processVariables/&gt; element.
 */
public class AeB4PProcessVariableCollectionSerializer
{
   /** Engine's data type mappings. */
   private AeTypeMapping mTypeMapping;
   /** Variable root element. */
   private Element mElement;

   /**
    * Ctor.
    * @param aTypeMapping
    */
   public AeB4PProcessVariableCollectionSerializer(AeTypeMapping aTypeMapping)
   {
      mTypeMapping = aTypeMapping;
   }

   /**
    * @return Engine type mapping.
    */
   protected AeTypeMapping getTypeMapping()
   {
      return mTypeMapping;
   }

   /**
    * @return the root &lt;aeb4p:processVariables/&gt; element
    */
   protected Element getElement()
   {
      if (mElement == null)
      {
         Document doc = AeXmlUtil.newDocument();
         mElement = AeXmlUtil.addElementNS(doc, IAeB4PConstants.AEB4P_NAMESPACE, "aeb4p:processVariables", null); //$NON-NLS-1$
         mElement.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:aeb4p", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
      }
      return mElement;
   }

   /**
    * Serializes a collection of <code>IAeVariable</code> objects and returns
    * &lt;aeb4p:processVariables/&gt; element.
    * @param aVariables
    * @return process variables element.
    * @throws AeBusinessProcessException
    */
   public Element serializeVariables(Collection aVariables) throws AeBusinessProcessException
   {
      Element root = getElement();
      AeB4PProcessVariableSerializer varSerializer = new AeB4PProcessVariableSerializer(getTypeMapping());
      for (Iterator it = aVariables.iterator(); it.hasNext();)
      {
         IAeVariable variable = (IAeVariable) it.next();
         Element elem = varSerializer.serialize(variable);
         root.appendChild( root.getOwnerDocument().importNode(elem, true));
      }
      return root;
   }
}
