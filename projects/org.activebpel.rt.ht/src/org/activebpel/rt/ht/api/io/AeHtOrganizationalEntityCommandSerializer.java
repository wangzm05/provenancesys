//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeHtOrganizationalEntityCommandSerializer.java,v 1.1 2008/01/18 22:51:53 PJayanetti Exp $
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
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.AeUserDef;
import org.activebpel.rt.ht.def.AeUsersDef;
import org.activebpel.rt.util.AeUtil;
import org.w3c.dom.Element;

/**
 * Serializer for ht api forward, delegate and nominate commands.
 */
public class AeHtOrganizationalEntityCommandSerializer extends AeHtSimpleRequestSerializer
{
   /** Collection of organizationalEntity users. */
   private AeUsersDef mUsersDef = new AeUsersDef();
   /**
    * Constructs serializer with one user.
    * @param aCommand
    * @param aIdentifier
    * @param aUser
    */
   public AeHtOrganizationalEntityCommandSerializer(String aCommand, String aIdentifier, String aUser)
   {
      super(aCommand, aIdentifier);
      addUser(aUser);
   }

   /**
    * Adds the organizationalEntity user to collection.
    * @param aUser
    */
   protected void addUser(String aUser)
   {
      if (AeUtil.notNullOrEmpty(aUser))
      {
         AeUserDef userdef = new AeUserDef();
         userdef.setValue(aUser);
         getUsersDef().addUser( userdef );
      }
   }

   /**
    * @return set of organizationalEntity users def.
    */
   protected AeUsersDef getUsersDef()
   {
      return mUsersDef;
   }

   /**
    * Overrides method to add htdt:organizationalEntity and its htd:users child element with users.
    * @see org.activebpel.rt.ht.api.io.AeHtSimpleRequestSerializer#serialize(org.w3c.dom.Element)
    */
   public Element serialize(Element aParentElement) throws AeException
   {
      // call base to create root element along with the child htdt:identifier element.
      Element commandElement = super.serialize(aParentElement);
      AeOrganizationalEntityDef orgEntityDef = new AeOrganizationalEntityDef();
      orgEntityDef.setUsers( getUsersDef() );
      serializeOrganizationalEntity(commandElement, orgEntityDef,"organizationalEntity", WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX); //$NON-NLS-1$
      return commandElement;
   }
}
