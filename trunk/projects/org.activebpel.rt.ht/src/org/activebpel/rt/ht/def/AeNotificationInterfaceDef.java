//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeNotificationInterfaceDef.java,v 1.6 2008/01/21 22:03:19 dvilaverde Exp $$
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
 * Impl. for the child 'interface' element Def of a 'notification' element
 */
public class AeNotificationInterfaceDef extends AeHtBaseDef implements IAeHtInputInterfaceDef, IAeInterfaceDef
{
   /** 'portType' attribute */
   private QName mPortType;
   /** 'operation' attribute */
   private String mOperation;

   /**
    * Gets the port type.
    */
   public QName getPortType()
   {
      return mPortType;
   }

   /**
    * Sets the port type.
    * 
    * @param aPortType
    */
   public void setPortType(QName aPortType)
   {
      mPortType = aPortType;
   }

   /**
    * Gets the operation.
    */
   public String getOperation()
   {
      return mOperation;
   }

   /**
    * Sets the operation.
    * 
    * @param aOperation
    */
   public void setOperation(String aOperation)
   {
      mOperation = aOperation;
   }

   /**
    * @see org.activebpel.rt.ht.def.AeHtBaseDef#accept(org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor)
    */
   public void accept(IAeHtDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeNotificationInterfaceDef))
         return false;
      
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(((AeNotificationInterfaceDef)aOther).getPortType(), getPortType());
      same &= AeUtil.compareObjects(((AeNotificationInterfaceDef)aOther).getOperation(), getOperation());
      
      return same;
   }
}