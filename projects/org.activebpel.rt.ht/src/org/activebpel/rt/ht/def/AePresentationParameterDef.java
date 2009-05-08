//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AePresentationParameterDef.java,v 1.2 2007/11/13 16:57:35 rnaylor Exp $$
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

import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Def impl for 'parameter' element
 */
public class AePresentationParameterDef extends AeAbstractExpressionDef 
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
      AePresentationParameterDef def = (AePresentationParameterDef)super.clone();
      
      if (getType() != null)
         def.setType(new QName(getType().getNamespaceURI(), getType().getLocalPart()));
         
      return def;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AePresentationParameterDef))
         return false;
      
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(((AePresentationParameterDef)aOther).getName(), getName());
      same &= AeUtil.compareObjects(((AePresentationParameterDef)aOther).getType(), getType());
      
      return same;
   }
}