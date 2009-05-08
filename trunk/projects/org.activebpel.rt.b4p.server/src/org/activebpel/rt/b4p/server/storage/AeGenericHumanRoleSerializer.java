//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/AeGenericHumanRoleSerializer.java,v 1.2 2008/02/17 21:36:32 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.ht.api.io.AeHtSerializerBase;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * Serializes a <code>AeOrganizationalEntityDef</code> to a wsht htd:tOrganizationalEntity type element
 */
public class AeGenericHumanRoleSerializer extends AeHtSerializerBase
{
   /** Role name */
   private String mGenericRole;
   /**
    * AeOrganizationalEntityDef aOrgEntityDef to be serialized.
    */
   private AeOrganizationalEntityDef mOrgEntityDef;

   /**
    * Ctor.
    * @param aGenericRole
    * @param aOrgEntityDef
    */
   public AeGenericHumanRoleSerializer(String aGenericRole, AeOrganizationalEntityDef aOrgEntityDef)
   {
      mGenericRole = aGenericRole;
      mOrgEntityDef = aOrgEntityDef;
   }

   /**
    * @see org.activebpel.rt.xml.AeXMLSerializerBase#serialize(org.w3c.dom.Element)
    */
   public Element serialize(Element aParentElement) throws AeException
   {
      return serializeOrganizationalEntity(aParentElement, mOrgEntityDef,  mGenericRole, IAeB4PConstants.AEB4P_NAMESPACE, "trt"); //$NON-NLS-1$
   }

   /**
    * Serializes Organizational Entity as a xml string if it exists or null otherwise.
    * @throws AeException
    */
   public String serializeAsXmlString()  throws AeException
   {
      Element ele = serialize();
      if (ele != null)
      {
         return AeXmlUtil.serialize(ele);
      }
      else
      {
         return null;
      }
   }
}
