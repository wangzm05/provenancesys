//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeParameterDef.java,v 1.6 2008/01/22 03:19:20 mford Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import javax.xml.namespace.QName;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Parameter for a LPG. These params are name value pairs. When a LPG is used,
 * the user can pass in one or more arguments which map to these parameters.
 */
public class AeParameterDef extends AeHtBaseDef 
{
   /** 'name' attribute value */
   private String mName;
   /** 'type' attribute value */
   private QName mType;

   /**
    * @return the name
    */
   public String getName()
   {
      return mName;
   }

   /**
    * @param aName the name to set
    */
   public void setName(String aName)
   {
      mName = aName;
   }

   /**
    * @return the type
    */
   public QName getType()
   {
      return mType;
   }

   /**
    * @param aType the type to set
    */
   public void setType(QName aType)
   {
      mType = aType;
   }
   
   /**
    * Setter for the type. Treats the value passed as the local part of a QName.
    * @param aSimpleType
    */
   public void setSimpleType(String aSimpleType)
   {
      setType(new QName(IAeConstants.W3C_XML_SCHEMA, aSimpleType));
   }
   
   /**
    * Getter for the type. Returns the local part of the type if set or null if not.
    */
   public String getSimpleType()
   {
      if (getType() == null)
         return null;
      return getType().getLocalPart();
   }

   /**
    * @see org.activebpel.rt.ht.def.AeHtBaseDef#accept(org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor)
    */
   public void accept(IAeHtDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      AeParameterDef def = (AeParameterDef)super.clone();
      if (getType() != null)
         def.setType(new QName(getType().getNamespaceURI(), getType().getLocalPart()));
      
      return def;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeParameterDef))
         return false;
      
      AeParameterDef otherDef = (AeParameterDef)aOther;
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(otherDef.getName(), getName());
      same &= AeUtil.compareObjects(otherDef.getType(), getType()); 

      return same;
   }
}