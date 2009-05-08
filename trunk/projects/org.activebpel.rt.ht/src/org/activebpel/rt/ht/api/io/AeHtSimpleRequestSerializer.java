//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeHtSimpleRequestSerializer.java,v 1.1 2008/01/18 22:51:53 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api.io;

import org.activebpel.rt.AeException;
import org.w3c.dom.Element;

/**
 * Serializer for basic command such as claim.
 */
public class AeHtSimpleRequestSerializer extends AeHtSerializerWithIdentifierBase
{
   /**
    * Ctor
    * @param aRequestName
    * @param aIdentifier
    */
   public AeHtSimpleRequestSerializer(String aRequestName, String aIdentifier)
   {
      super(aRequestName, aIdentifier);
   }

   /**
    * @see org.activebpel.rt.ht.api.io.AeHtSerializerBase#serialize(org.w3c.dom.Element)
    */
   public Element serialize(Element aParentElement) throws AeException
   {
      Element commandElement = createRootElement(aParentElement, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, getRequestName());
      createElementWithText(commandElement, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "identifier", getIdentifier()); //$NON-NLS-1$
      return commandElement;
   }
}
