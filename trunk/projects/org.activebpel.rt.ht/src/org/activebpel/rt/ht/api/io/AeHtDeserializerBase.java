//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeHtDeserializerBase.java,v 1.2 2008/02/02 19:40:16 PJayanetti Exp $
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
import java.util.List;
import java.util.Map;
import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.api.IAeHtWsIoConstants;
import org.activebpel.rt.ht.def.AeGroupDef;
import org.activebpel.rt.ht.def.AeGroupsDef;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.AeUserDef;
import org.activebpel.rt.ht.def.AeUsersDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.AeXMLDeserializerBase;
import org.w3c.dom.Element;

/**
 * Base class used in create ht:api deserializers. Implementations of this
 * classes should be designed to be re-entrant.
 */
public abstract class AeHtDeserializerBase extends AeXMLDeserializerBase implements IAeHtWsIoConstants
{
   /**
    * Initializes. Subclasses should override this method to set the mapping.
    * @param aMap prefix to namespace map.
    */
   protected void initNamespaceMap(Map aMap)
   {
      aMap.put(WSHT_PREFIX, WSHT_NAMESPACE);
      aMap.put(WSHT_API_PREFIX, WSHT_API_NAMESPACE);
      aMap.put(WSHT_API_XSD_PREFIX, WSHT_API_XSD_NAMESPACE);
      aMap.put(WSHT_API_WSDL_PREFIX, WSHT_API_WSDL_NAMESPACE);
   }
   
   /**
    * Deserializes a htd:tOrganizationalEntity element.
    * @param aOrganizationalEntityElem
    * @return AeOrganizationalEntityDef or <code>null</code> if element <code>null</code> or  is xsi:nil
    */
   protected AeOrganizationalEntityDef deserializeOrganizationalEntity(Element aOrganizationalEntityElem) throws AeException
   {
      if (AeXmlUtil.isNil(aOrganizationalEntityElem))
      {
         return null;
      }
      // fixme (PJ) leverage code from AeB4PIO/AeHtIO.
      AeOrganizationalEntityDef orgEntityDef = new AeOrganizationalEntityDef();
      // choice1: check for user entries
      List entries = AeXPathUtil.selectNodes(aOrganizationalEntityElem, "htd:users/htd:user", getNamespaceMap()); //$NON-NLS-1$
      if (AeUtil.notNullOrEmpty(entries))
      {
         AeUsersDef usersDef = new AeUsersDef();
         Iterator it = entries.iterator();
         while (it.hasNext())
         {
            AeUserDef userDef = new AeUserDef();
            userDef.setValue( AeXmlUtil.getText((Element) it.next()) );
            usersDef.addUser( userDef );
         }
         orgEntityDef.setUsers(usersDef);         
      }
      else
      {
         // choice2: check for groups
         entries = AeXPathUtil.selectNodes(aOrganizationalEntityElem, "htd:groups/htd:group", getNamespaceMap()); //$NON-NLS-1$
         if (AeUtil.notNullOrEmpty(entries))
         {
            AeGroupsDef groupsDef = new AeGroupsDef();
            Iterator it = entries.iterator();
            while (it.hasNext())
            {
               AeGroupDef groupDef = new AeGroupDef();
               groupDef.setValue( AeXmlUtil.getText((Element) it.next()) );
               groupsDef.addGroup( groupDef );
            }
            orgEntityDef.setGroups(groupsDef);    
         }         
      }
      return orgEntityDef;
   }
}
