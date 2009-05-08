//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeAbstractMixedTextDef.java,v 1.7.4.1 2008/04/21 16:15:16 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeCloneUtil;
import org.activebpel.rt.util.AeUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A Def implementation of mixed text construct.
 * <p>
 * For Mixed elements the intent assumption is for the element to have mutually exclusive <code>Text</code>
 * or <code>Element</code> nodes as children.
 * </p>
 * <p>
 * Add the <code>Text</code> children of the given Element only if there are no <code>Element</code>
 * children.
 * </p>
 */
public abstract class AeAbstractMixedTextDef extends AeHtBaseDef implements IAeMixedContentElement
{
   /**
    * The list of mixed Text nodes
    * @see AeTextNodeDef
    */
   private List mTextNodes;
   
   /**
    * Returns True if object is defined and False if no data has been set
    */
   public boolean isDefined()
   {
      return AeUtil.notNullOrEmpty(mTextNodes);
   }
   
   /**
    * Returns a String representation of the text nodes
    */
   public String getTextValue()
   {
      StringBuffer buff = new StringBuffer();
      for (Iterator iter=getMixedTextDef(); iter.hasNext();)
      {
         if (buff.length() > 0)
            buff.append(" "); //$NON-NLS-1$
         buff.append(((AeTextNodeDef)iter.next()).getValue());
      }
      
      return buff.toString().trim();
   }
   
   /**
    * Sets the given value as the new text representation.
    * @param aValue
    */
   public void setTextValue(String aValue)
   {
      getTextNodes().clear();
      if (aValue != null)
         getTextNodes().add(new AeTextNodeDef(aValue, false));
   }
   
   /**
    * @see org.activebpel.rt.ht.def.IAeMixedContentElement#getMixedTextDef()
    */
   public Iterator getMixedTextDef()
   {
      return getTextNodes().iterator();
   }

   /**
    * @return list of Text nodes
    */
   protected List getTextNodes()
   {
      if ( mTextNodes == null )
      {
         mTextNodes = new ArrayList();
      }
      return mTextNodes;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeMixedContentElement#readMixedText(org.w3c.dom.Element)
    */
   public void readMixedText(Element aElement)
   {
      aElement.normalize();
      if ( aElement.hasChildNodes() )
      {
         NodeList children = aElement.getChildNodes();
         for (int i = 0; i < children.getLength(); i++)
         {
            Node child = children.item(i);
            short type = child.getNodeType();
            if ( type == Node.TEXT_NODE || type == Node.CDATA_SECTION_NODE )
            {
               if ( AeUtil.notNullOrEmpty(child.getNodeValue()) )
               {
                  getTextNodes().add(new AeTextNodeDef(child.getNodeValue(), type == Node.CDATA_SECTION_NODE));
               }
            }
            else if ( type == Node.ELEMENT_NODE )
            {
               getTextNodes().clear();
               break;
            }
         }
      }
   }
   
   /**
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      AeAbstractMixedTextDef mixedText = (AeAbstractMixedTextDef)super.clone();
      mixedText.mTextNodes = null;
      
      try
      {
         mixedText.mTextNodes = AeCloneUtil.deepClone(mTextNodes);
      }
      catch (CloneNotSupportedException ex)
      {
         // This should never happen in a closed system
         AeException.logError(ex);
         throw new InternalError(ex.getLocalizedMessage());
      }
      
      return mixedText;
   }
   
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeAbstractMixedTextDef))
         return false;

      AeAbstractMixedTextDef other = (AeAbstractMixedTextDef) aOther;

      boolean same = super.equals(other);
      same &= AeUtil.compareObjects(other.getTextValue(), getTextValue()); 
      return same;
   }
}