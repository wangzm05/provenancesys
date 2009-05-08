//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeAbstractExpressionDef.java,v 1.7 2008/01/28 18:37:10 EWittmann Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.activebpel.rt.util.AeUtil;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;

/**
 * Base class for expression defs.
 */
public abstract class AeAbstractExpressionDef extends AeAbstractMixedTextDef implements IAeHtExpressionDef
{
   /** The condition construct's 'expressionLanguage' attribute. */
   private String mExpressionLanguage;
   /** namespace of the expression */
   private String mNamespace;
   
   /**
    * @see org.activebpel.rt.ht.def.AeAbstractMixedTextDef#isDefined()
    */
   public boolean isDefined()
   {
      // The expression is defined if EITHER the language or the expression has been set
      return AeUtil.notNullOrEmpty(getExpressionLanguage()) || super.isDefined();
   }
   
   /**
    * @see org.activebpel.rt.ht.def.IAeHtExpressionDef#getExpressionLanguage()
    */
   public String getExpressionLanguage()
   {
      return mExpressionLanguage;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeHtExpressionDef#setExpressionLanguage(java.lang.String)
    */
   public void setExpressionLanguage(String aExpressionLanguage)
   {
      mExpressionLanguage = aExpressionLanguage;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeHtExpressionDef#getExpression()
    */
   public String getExpression()
   {
      StringBuffer buffer = new StringBuffer();

      for (Iterator itr = getMixedTextDef(); itr.hasNext();)
      {
         AeTextNodeDef node = (AeTextNodeDef)itr.next();
         if ( node.isCData() )
         {
            CDATASection cdataNode = createDomDocument().createCDATASection(node.getValue());
            buffer.append(cdataNode.getNodeValue());
         }
         else
         {
            buffer.append(node.getValue());
         }
      }
      return buffer.toString();
   }
   
   /**
    * @see org.activebpel.rt.ht.def.IAeHtExpressionDef#setExpression(java.lang.String)
    */
   public void setExpression(String aExpr)
   {
      setTextValue(aExpr);
   }

   /**
    * helper method to create an empty Dom Document
    * @return
    */
   private Document createDomDocument()
   {
      try
      {
         DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         Document doc = builder.newDocument();
         return doc;
      }
      catch (ParserConfigurationException e)
      {
         // should never happen, but just in case
         throw new RuntimeException(e.getMessage());
      }
   }

   /**
    * @return the namespace
    */
   public String getNamespace()
   {
      return mNamespace;
   }

   /**
    * @param aNamespace the namespace to set
    */
   public void setNamespace(String aNamespace)
   {
      mNamespace = aNamespace;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeAbstractExpressionDef))
         return false;
      
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(((AeAbstractExpressionDef)aOther).getNamespace(), getNamespace());
      same &= AeUtil.compareObjects(((AeAbstractExpressionDef)aOther).getExpressionLanguage(), getExpressionLanguage());
      
      return same;
   }
}