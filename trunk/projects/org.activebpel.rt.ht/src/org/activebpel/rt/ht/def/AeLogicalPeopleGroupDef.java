//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeLogicalPeopleGroupDef.java,v 1.5 2007/11/27 14:06:39 mford Exp $$
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
import org.activebpel.rt.xml.def.IAeNamedDef;

/**
 * Impl. for the 'logicalPeopleGroup' Def object.
 */
public class AeLogicalPeopleGroupDef extends AeAbstractParameterListDef
implements IAeNamedDef
{
   /** 'name' attribute value */
   private String mName;
   /** 'reference' attribute value */
   private QName mReference;

   /**
    * @see org.activebpel.rt.xml.def.IAeNamedDef#getName()
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
    * @return the reference
    */
   public QName getReference()
   {
      return mReference;
   }

   /**
    * @param aReference the reference to set
    */
   public void setReference(QName aReference)
   {
      mReference = aReference;
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
      AeLogicalPeopleGroupDef def = (AeLogicalPeopleGroupDef)super.clone();
      if (getReference() != null)
         def.setReference(new QName(getReference().getNamespaceURI(), getReference().getLocalPart()));
      
      return def;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeLogicalPeopleGroupDef))
         return false;
      
      AeLogicalPeopleGroupDef otherDef = (AeLogicalPeopleGroupDef)aOther;
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(otherDef.getName(), getName()); 
      same &= AeUtil.compareObjects(otherDef.getReference(), getReference()); 
      
      return same; 
   }
}