//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeHtSuspendUntilRequestSerializer.java,v 1.1 2008/01/18 22:51:53 PJayanetti Exp $
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
import org.activebpel.rt.xml.schema.AeSchemaDateTime;
import org.activebpel.rt.xml.schema.AeSchemaDuration;
import org.w3c.dom.Element;

/**
 * SuspendUntil ht api command serializer.
 */
public class AeHtSuspendUntilRequestSerializer extends AeHtSimpleRequestSerializer
{
   /** Suspend until date and time. */
   private AeSchemaDateTime mDateTime;
   /** Suspend until duration. */
   private AeSchemaDuration mDuration;

   /**
    * Ctor with datetime.
    * @param aRequestName
    * @param aIdentifier
    * @param aDateTime
    */
   public AeHtSuspendUntilRequestSerializer(String aRequestName, String aIdentifier, AeSchemaDateTime aDateTime)
   {
      super(aRequestName, aIdentifier);
      mDateTime = aDateTime;
   }

   /**
    * Ctor with duration.
    * @param aCommand
    * @param aIdentifier
    * @param aDuration
    */
   public AeHtSuspendUntilRequestSerializer(String aCommand, String aIdentifier, AeSchemaDuration aDuration)
   {
      super(aCommand, aIdentifier);
      mDuration = aDuration;
   }

   /**
    * @return the dateTime
    */
   protected AeSchemaDateTime getDateTime()
   {
      return mDateTime;
   }

   /**
    * @return the duration
    */
   protected AeSchemaDuration getDuration()
   {
      return mDuration;
   }

   /**
    * @see org.activebpel.rt.ht.api.io.AeHtSimpleRequestSerializer#serialize(org.w3c.dom.Element)
    */
   public Element serialize(Element aParentElement) throws AeException
   {
      // call base to create root element along with the child htdt:identifier element.
      Element commandElement = super.serialize(aParentElement);
      // create either the datetime or duration element.
      if (getDateTime() != null)
      {
         createElementWithText(commandElement, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "pointOfTime", getDateTime().toString()); //$NON-NLS-1$
      }
      else if (getDuration() != null)
      {
         createElementWithText(commandElement, WSHT_API_XSD_NAMESPACE, WSHT_API_XSD_PREFIX, "timePeriod", getDuration().toString()); //$NON-NLS-1$
      }
      return commandElement;
   }
}
