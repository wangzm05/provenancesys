//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeB4PLocalNotificationDef.java,v 1.3 2007/12/26 17:34:03 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.IAeLocalNotificationDef;
import org.activebpel.rt.util.AeUtil;

/**
 * Models the local notification child element for a people activity. 
 */
public class AeB4PLocalNotificationDef extends AeReferenceOverrideElementsDef implements IAeLocalNotificationDef
{
   /** 'reference' attribute */
   private QName mReference;

   /** The referenced notification def resolved */
   private AeNotificationDef mInlineNotificationDef;

   /**
    * @see org.activebpel.rt.ht.def.IAeLocalResourceDef#getReference()
    */
   public QName getReference()
   {
      return mReference;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeLocalResourceDef#setReference(javax.xml.namespace.QName)
    */
   public void setReference(QName aReference)
   {
      mReference = aReference;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeLocalNotificationDef#getInlineNotificationDef()
    */
   public AeNotificationDef getInlineNotificationDef()
   {
      return mInlineNotificationDef;
   }

   /**
    * @param aInlineNotificationDef the inlineNotificationDef to set
    */
   public void setInlineNotificationDef(AeNotificationDef aInlineNotificationDef)
   {
      mInlineNotificationDef = aInlineNotificationDef;
   }

   /**
    * @see org.activebpel.rt.b4p.def.AeReferenceOverrideElementsDef#accept(org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor)
    */
   public void accept(IAeB4PDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @see org.activebpel.rt.b4p.def.AeB4PBaseDef#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (super.equals(aOther))
      {
         AeB4PLocalNotificationDef other = (AeB4PLocalNotificationDef) aOther;
         return AeUtil.compareObjects(getReference(), other.getReference());
      }
      return false;
   }
}
