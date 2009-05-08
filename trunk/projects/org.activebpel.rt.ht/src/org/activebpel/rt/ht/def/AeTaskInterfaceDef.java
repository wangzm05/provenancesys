//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeTaskInterfaceDef.java,v 1.8 2008/02/17 21:51:26 mford Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Impl. for the child 'interface' element Def of a 'task' element
 */
public class AeTaskInterfaceDef extends AeHtBaseDef implements IAeHtInputInterfaceDef, IAeInterfaceDef
{
   /** 'portType' attribute */
   private QName mPortType;
   /** 'operation' attribute */
   private String mOperation;
   /** 'responsePortType' attribute */
   private QName mResponsePortType;
   /** 'responseOperation' attribute */
   private String mResponseOperation;
   /** collection of fault names defined for this interface */
   private Set mFaultNames;

   /**
    * @see org.activebpel.rt.ht.def.IAeInterfaceDef#getOperation()
    */
   public String getOperation()
   {
      return mOperation;
   }

   /**
    * @param aOperation the operation to set
    */
   public void setOperation(String aOperation)
   {
      mOperation = aOperation;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeInterfaceDef#getPortType()
    */
   public QName getPortType()
   {
      return mPortType;
   }

   /**
    * @param aPortType the portType to set
    */
   public void setPortType(QName aPortType)
   {
      mPortType = aPortType;
   }

   /**
    * Getter for the response operation
    */
   public String getResponseOperation()
   {
      return mResponseOperation;
   }

   /**
    * @param aResponseOperation the responseOperation to set
    */
   public void setResponseOperation(String aResponseOperation)
   {
      mResponseOperation = aResponseOperation;
   }

   /**
    * Getter for the response port type
    */
   public QName getResponsePortType()
   {
      return mResponsePortType;
   }

   /**
    * @param aResponsePortType the responsePortType to set
    */
   public void setResponsePortType(QName aResponsePortType)
   {
      mResponsePortType = aResponsePortType;
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
      AeTaskInterfaceDef def = (AeTaskInterfaceDef)super.clone();
      if (getPortType() != null)
         def.setPortType(new QName(getPortType().getNamespaceURI(), getPortType().getLocalPart()));
      if (getResponsePortType() != null)
         def.setResponsePortType(new QName(getResponsePortType().getNamespaceURI(), getResponsePortType().getLocalPart()));
      
      return def;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeTaskInterfaceDef))
         return false;
      
      AeTaskInterfaceDef otherDef = (AeTaskInterfaceDef)aOther;
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(otherDef.getPortType(), getPortType()); 
      same &= AeUtil.compareObjects(otherDef.getResponsePortType(), getResponsePortType()); 
      same &= AeUtil.compareObjects(otherDef.getOperation(), getOperation()); 
      same &= AeUtil.compareObjects(otherDef.getResponseOperation(), getResponseOperation()); 
      
      return same; 
   }
   
   /**
    * Returns true if there are faults defined for this interface.
    * 
    * The fault information is inlined here during the preprocessing of the task
    * data in the engine. This is done to avoid having to look up the WSDL at
    * runtime. 
    */
   public boolean hasFaults()
   {
      return AeUtil.notNullOrEmpty(getFaultNames());
   }
   
   /**
    * Getter for the fault names
    * 
    * The fault information is inlined here during the preprocessing of the task
    * data in the engine. This is done to avoid having to look up the WSDL at
    * runtime. 
    */
   public Iterator getFaults()
   {
      if (hasFaults())
         return getFaultNames().iterator();
      return Collections.EMPTY_SET.iterator();
   }
   
   /**
    * Adds a fault on this interface
    * @param aFaultName
    */
   public void addFault(String aFaultName)
   {
      if (getFaultNames() == null)
         setFaultNames(new LinkedHashSet());
      getFaultNames().add(aFaultName);
   }

   /**
    * @return the faultNames
    */
   protected Set getFaultNames()
   {
      return mFaultNames;
   }

   /**
    * @param aFaultNames the faultNames to set
    */
   protected void setFaultNames(Set aFaultNames)
   {
      mFaultNames = aFaultNames;
   }
}