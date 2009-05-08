//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeRenderingDef.java,v 1.5 2008/01/17 23:45:58 JPerrotto Exp $
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
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.w3c.dom.Element;

/**
 * Models the ht:rendering element.
 */
public class AeRenderingDef extends AeHtBaseDef
{
   /** The rendering type. */
   private QName mType;

   /**
    * Gets the rendering's type.
    */
   public QName getType()
   {
      return mType;
   }

   /**
    * Sets the rendering's type.
    *
    * @param aType
    */
   public void setType(QName aType)
   {
      mType = aType;
   }
   
   /**
    * Gets the rendering element.
    * 
    * @return Element or null if not defined.
    */
   public Element getRenderingElement()
   {
      // We'll assume that the rendering element is the first extension element.
      AeExtensionElementDef extDef = getFirstExtensionElementDef();

      if ( extDef != null )
         return extDef.getExtensionElement();

      return null;
   }
   
   /**
    * Sets the rendering element.
    * 
    * @param aElement
    */
   public void setRenderingElement(Element aElement)
   {
      // First remove all existing extension elements.
      getExtensionElementDefs().clear();
      
      if ( aElement != null )
      {
         addExtensionElementDef(new AeExtensionElementDef(aElement));
      }
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
      AeRenderingDef def = (AeRenderingDef)super.clone();
      if (getType() != null)
         def.setType(new QName(getType().getNamespaceURI(), getType().getLocalPart()));
      
      return def;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeRenderingDef))
         return false;
      
      AeRenderingDef otherDef = (AeRenderingDef)aOther;
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(otherDef.getType(), getType()); 
      
      return same; 
   }
}