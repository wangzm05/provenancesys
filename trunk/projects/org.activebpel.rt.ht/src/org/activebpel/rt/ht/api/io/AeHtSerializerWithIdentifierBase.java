//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/api/io/AeHtSerializerWithIdentifierBase.java,v 1.1 2008/01/18 22:51:53 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.api.io;


/** 
 * Base class for serializers which have the task identitifier, 
 */
public abstract class AeHtSerializerWithIdentifierBase extends AeHtSerializerBase
{
   /** Request element local name */
   private String mRequestName;
   /** Task id */
   private String mIdentifier;

   /**
    * Ctor. 
    * @param aIdentifier
    */
   protected AeHtSerializerWithIdentifierBase(String aRequestName, String aIdentifier)
   {
      setRequestName(aRequestName);
      setIdentifier(aIdentifier);
   }
   
   /**
    * @return the identifier
    */
   public String getIdentifier()
   {
      return mIdentifier;
   }

   /**
    * @param aIdentifier the identifer to set
    */
   public void setIdentifier(String aIdentifier)
   {
      mIdentifier = aIdentifier;
   }

   /**
    * @return the requestName
    */
   public String getRequestName()
   {
      return mRequestName;
   }

   /**
    * @param aRequestName the requestName to set
    */
   public void setRequestName(String aRequestName)
   {
      mRequestName = aRequestName;
   }
      
}
