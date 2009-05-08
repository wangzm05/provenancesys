//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/xsl/AeXslTaskUtil.java,v 1.2.4.1 2008/04/21 16:04:36 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.xsl;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Utility class to support XSL command processing.
 */
public class AeXslTaskUtil
{

   /**
    * Creates a document with the &lt;aefe:error&gt; root element.
    * @return error document.
    */
   public static Document createErrorDocument()
   {
      Document dom = AeXmlUtil.newDocument();
      createElementWithText(dom, AeXslTaskConstants.ERROR_NS, "aefe", "errors", null); //$NON-NLS-1$ //$NON-NLS-2$
      return dom;
   }

   /**
    * Creates and returns an document with root element aefp:parameters.
    * @return parameters document.
    */
   public static Document createRequestParameterDocument()
   {
      Document dom = AeXmlUtil.newDocument();
      createElementWithText(dom, AeXslTaskConstants.PARAMS_NS, "aefp", "parameters", null); //$NON-NLS-1$ //$NON-NLS-2$
      return dom;
   }

   /**
    * Convenience method to return the message from the root cause of the error.
    * @param aError
    * @return root cause message.
    */
   public static String createExceptionMessage(Throwable aError)
   {
      if (aError.getCause() != null && AeUtil.notNullOrEmpty( aError.getCause().getMessage() ) )
      {
         return aError.getCause().getMessage();
      }
      else
      {
         return aError.getMessage();
      }
   }

   /**
    * Creates a &lt;aefp:parameter /&gt; element
    * @param aParentElement
    * @param aName
    * @param aValue
    * @return &lt;aefp:parameter /&gt; element
    */
   protected static Element createRequestParameterElement(Element aParentElement, String aName, String aValue)
   {
      Element elem = createElementWithText(aParentElement, AeXslTaskConstants.PARAMS_NS, "aefp", "parameter", AeUtil.getSafeString(aValue)); //$NON-NLS-1$ //$NON-NLS-2$
      elem.setAttribute("name", aName); //$NON-NLS-1$
      if (aValue != null)
      {
         elem.setAttribute("type", "text"); //$NON-NLS-1$ //$NON-NLS-2$
      }
      return elem;
   }

   /**
    * Creates a &lt;aefp:header /&gt; element
    * @param aParentElement
    * @param aName
    * @param aValue
    * @return &lt;aefp:parameter /&gt; element
    */
   protected static Element createRequestHeaderElement(Element aParentElement, String aName, String aValue)
   {
      Element elem = createElementWithText(aParentElement, AeXslTaskConstants.PARAMS_NS, "aefp", "header", AeUtil.getSafeString(aValue)); //$NON-NLS-1$ //$NON-NLS-2$
      elem.setAttribute("name", aName); //$NON-NLS-1$
      if (aValue != null)
      {
         elem.setAttribute("type", "text"); //$NON-NLS-1$ //$NON-NLS-2$
      }
      return elem;
   }

   /**
    * Creates a &lt;aefe:parameter-error /&gt; element. This element contains a form data processing error.
    * @param aParentElement
    * @param aName formdata parameter name
    * @param aValue error message
    * @return element
    */
   public static Element createParameterErrorElement(Element aParentElement, String aName, String aValue)
   {
      Element elem = createElementWithText(aParentElement, AeXslTaskConstants.ERROR_NS, "aefe", "parameter-error", AeUtil.getSafeString(aValue)); //$NON-NLS-1$ //$NON-NLS-2$
      elem.setAttribute("name", aName); //$NON-NLS-1$
      return elem;
   }

   /**
    * Creates a &lt;aefe:command-error /&gt; element. This element contains a form data processing error.
    * @param aParentElement
    * @param aName command name
    * @param aType error type (taskfault or error)
    * @param aValue
    * @return element
    */
   protected static Element createCommandErrorElement(Element aParentElement, String aName, String aType, String aValue)
   {
      Element elem = createElementWithText(aParentElement, AeXslTaskConstants.ERROR_NS, "aefe", "command-error", AeUtil.getSafeString(aValue)); //$NON-NLS-1$ //$NON-NLS-2$
      elem.setAttribute("name", aName); //$NON-NLS-1$
      elem.setAttribute("type", aType); //$NON-NLS-1$
      return elem;
   }

   /**
    * Convinience method to create a element.
    * @param aParentNode parent node
    * @param aNameSpace
    * @param aPrefix
    * @param aElementName
    * @param aText
    * @return element
    */
   protected static Element createElementWithText(Node aParentNode, String aNameSpace, String aPrefix,
         String aElementName, String aText)
   {
      Node parent = aParentNode == null? AeXmlUtil.newDocument() : aParentNode;
      Element ele = AeXmlUtil.addElementNS(parent, aNameSpace,
            aPrefix + ":" + aElementName, aText); //$NON-NLS-1$
      ele.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:" + aPrefix, aNameSpace); //$NON-NLS-1$
      return ele;
   }

}
