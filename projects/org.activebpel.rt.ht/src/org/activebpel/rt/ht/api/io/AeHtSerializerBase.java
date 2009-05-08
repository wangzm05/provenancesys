//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeHtSerializerBase.java,v 1.2 2008/02/02 19:40:16 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api.io;

import java.util.Iterator;

import org.activebpel.rt.ht.api.IAeHtWsIoConstants;
import org.activebpel.rt.ht.def.AeGroupDef;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.AeUserDef;
import org.activebpel.rt.xml.AeXMLSerializerBase;
import org.w3c.dom.Element;

/**
 * Base class used to serialize htd:api elements.
 */
public abstract class AeHtSerializerBase extends AeXMLSerializerBase implements IAeHtWsIoConstants
{
   /**
    * Serializes a <code>AeOrganizationalEntityDef</code> to a wsht htd:tOrganizationalEntity type element
    * and add it as a child to the given parent element.
    * The child element is not added if the <code>AeOrganizationalEntityDef</code> is <code>null</code>
    * to support schemas where the element is optional.
    * @param aParentElement
    * @param aOrgEntityDef def to serialize
    * @param aElementName the type root element name. E.g. wsht api:businessAdministrators.
    * @param aElementNs root element ns
    * @param aElementPrefix root element ns.
    * @return serialize element (with given element name) of type htd:tOrganizationalEntity or <code>null</code> if the orgEntity is null.
    */
   protected Element serializeOrganizationalEntity(Element aParentElement, AeOrganizationalEntityDef aOrgEntityDef,
         String aElementName, String aElementNs, String aElementPrefix)
   {
      if (aOrgEntityDef == null)
      {
         return null;
      }
      //fixme (PJ) leverage serializer code from AeHTIO/AeB4PIO.
      // create root element such as api:businessAdministrators or api:organizationalEntity.
      Element orgEntityElem = createRootElement(aParentElement, aElementNs,aElementPrefix, aElementName);

      // serialize groups, if any.
      if (aOrgEntityDef.getGroups() != null && aOrgEntityDef.getGroups().size() > 0)
      {

         Element htdGroupsElem = createElement(orgEntityElem, WSHT_NAMESPACE, WSHT_PREFIX, "groups"); //$NON-NLS-1$
         Iterator it = aOrgEntityDef.getGroups().getGroupDefs();
         while ( it.hasNext() )
         {
            createElementWithText(htdGroupsElem, WSHT_NAMESPACE, WSHT_PREFIX, "group", ((AeGroupDef) it.next()).getValue() ); //$NON-NLS-1$
         }
      }
      else
      {
         // default choice is users.
         Element htdUsersElem = createElement(orgEntityElem, WSHT_NAMESPACE, WSHT_PREFIX, "users"); //$NON-NLS-1$
         if (aOrgEntityDef.getUsers() != null)
         {
            Iterator it = aOrgEntityDef.getUsers().getUserDefs();
            while ( it.hasNext() )
            {
               createElementWithText(htdUsersElem, WSHT_NAMESPACE, WSHT_PREFIX, "user", ((AeUserDef) it.next()).getValue() ); //$NON-NLS-1$
            }
         }
      }
      return orgEntityElem;
   }
}
