//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeDescriptionDef.java,v 1.5 2008/02/29 23:45:53 vvelusamy Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Impl. for 'description' element Def.
 */
public class AeDescriptionDef extends AePresentationElementDef
{
   /** 'contentType' attribute */
   private String mContentType;
   
   /**
    * @return the contentType
    */
   public String getContentType()
   {
      return mContentType;
   }

   /**
    * @param aContentType the contentType to set
    */
   public void setContentType(String aContentType)
   {
      mContentType = aContentType;
   }
   
   /**
    * Gets the value of the description. The value, if available, woulb be stored either as an {@link AeTextNodeDef} or
    * as an {@link Element}.
    */
   public String getValue()
   {
      String value = AeUtil.getSafeString(getTextValue());
      if (AeUtil.isNullOrEmpty(value))
      {
         if (!getExtensionElementDefs().isEmpty())
         {
            Element extElement = ((AeExtensionElementDef) getExtensionElementDefs().get(0)).getExtensionElement();
            value = AeXmlUtil.serialize(extElement);
         }
      }
      return value;
   }
   
   /**
    * Sets the value of the description. Depending on the content type, the value is stored as text or as an extension
    * element.
    */
   public void setValue(String aValue)
   {
      getExtensionElementDefs().clear();
      getTextNodes().clear();

      Document valueDoc = null;
      if(AeUtil.isNullOrEmpty(getContentType()) || getContentType().equals("text/plain")) //$NON-NLS-1$
      {
         // Treat value as text.
      }
      // If content type is anything else, including text/xhtml, make an attempt to load the value as XML, as-is.
      else
      {
         // Check if the value is well-formed XML as-is.
         try
         {
            valueDoc = AeXmlUtil.toDoc(aValue);
         }
         catch (AeException e)
         {
            if (getContentType().equals("text/html")) //$NON-NLS-1$
            {
               // Now add a wrapping <div/> with a namespace declaration (httask schema requires a description's element
               // to be of type ##other, and try again.
               aValue = AeMessages.format("AeDescriptionDef.WRAPPER_HTML_TAG", aValue); //$NON-NLS-1$
               try
               {
                  valueDoc = AeXmlUtil.toDoc(aValue);
               }
               catch (AeException e1)
               {
                  // Unable to parse the description content as XML. Set value as text.
               }
            }
         }

         // Root element has to have a namespace URI
         if (null != valueDoc && AeUtil.isNullOrEmpty(valueDoc.getDocumentElement().getNamespaceURI()))
         {
            valueDoc = null;
         }
      }
      
      // If the value was/was made well-formed, store the value as an def extension, else store it as text.
      if(null != valueDoc)
      {
         AeExtensionElementDef extension = new AeExtensionElementDef(valueDoc.getDocumentElement());
         addExtensionElementDef(extension);
      }
      else
      {
         setTextValue(aValue);
      }
   }
   
   /* (non-Javadoc)
    * @see org.activebpel.rt.ht.def.AeAbstractMixedTextDef#getTextNodes()
    */
   protected List getTextNodes()
   {
      return super.getTextNodes();
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
      if (! (aOther instanceof AeDescriptionDef))
         return false;
      
      AeDescriptionDef other = (AeDescriptionDef) aOther;
      
      boolean same = super.equals(other);
      same &= AeUtil.compareObjects(other.getContentType(), getContentType());
      
      return same;
   }
}