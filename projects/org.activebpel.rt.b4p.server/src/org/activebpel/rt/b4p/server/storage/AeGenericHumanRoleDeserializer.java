//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/AeGenericHumanRoleDeserializer.java,v 1.2 2008/02/17 21:36:32 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage;

import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.ht.api.io.AeHtDeserializerBase;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Deserializes  htd:tOrganizationalEntity type elements from the storage layer.
 */
public class AeGenericHumanRoleDeserializer extends AeHtDeserializerBase
{
   /** static singleton. */
   public static final AeGenericHumanRoleDeserializer INSTANCE = new AeGenericHumanRoleDeserializer();
   
   /**
    * @see org.activebpel.rt.xml.AeXMLDeserializerBase#initNamespaceMap(java.util.Map)
    */
   protected void initNamespaceMap(Map aMap)
   {
      super.initNamespaceMap(aMap);
      aMap.put("trt", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
   }

   /** 
    * @param aOrganizationalEntityEle
    */
   public AeOrganizationalEntityDef deserializeGenericHumanRole(Element aOrganizationalEntityEle) throws AeException
   {
      return deserializeOrganizationalEntity(aOrganizationalEntityEle);
   }
   
   /**
    * Deserializes a htd:tOrganizationalEntity given the element as a xml string. 
    * @param aOrganizationalEntityXml
    * @return the AeOrganizationalEntityDef or null if string is empty or null or element is not defined in the xml.
    * @throws AeException
    */
   public AeOrganizationalEntityDef deserializeGenericHumanRole(String aOrganizationalEntityXml) throws AeException
   {
      if (AeUtil.notNullOrEmpty(aOrganizationalEntityXml))
      {
         Document doc = AeXmlUtil.toDoc(aOrganizationalEntityXml);
         return deserializeGenericHumanRole(doc.getDocumentElement());
      }
      else
      {
         return null;
      }
   }   
}
