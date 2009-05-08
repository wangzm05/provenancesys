//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/xsl/AeXslTaskInputParameters.java,v 1.2 2008/02/20 15:58:19 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.xsl;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * AeXslTaskInputParameters is a simple wrapper
 * around the form data input parameter document specified by
 * the http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/formparams schema
 */
public class AeXslTaskInputParameters
{
   /** HTML form data name prefix that is used to indicate xml content. */
   public static final String XML_CONTENT_HINT_PREFIX = "_aexml_"; //$NON-NLS-1$

   /**
    * Principal name.
    */
   private String mPrincipalName;
   /** Task reference id. */
   private String mTaskId;

   /** parameter document. */
   private Document mDocument;

   /**
    * Constructs AeXslTaskInputParameters given the document.
    * @param aDocument document containing the aefp:parameters element document.
    */
   public AeXslTaskInputParameters(String aPrincipalName, String aTaskId, Document aDocument)
   {
      mPrincipalName = aPrincipalName;
      mTaskId = aTaskId;
      mDocument = aDocument;
   }

   /**
    * @return principal name.
    */
   public String getPrincipalName()
   {
      return mPrincipalName;
   }

   /**
    * @return task reference id.
    */

   public String getTaskId()
   {
      return mTaskId;
   }

   /**
    * @return Document containing the error messages.
    */
   public Document getDocument()
   {
      return mDocument;
   }

   /**
    * Creates and returns AeXslTaskInputParameters with a  name value parameter document
    * given the html form data
    * inputs.
    * @param aPrincipalName
    * @param aTaskId
    * @param aHttpMethod POST or GET
    * @param aUserAgent http request user-agent.
    * @param aPathInfo servlet extra path information
    * @param aHttpHeaderParam http request header entries 
    * @param aParamErrors error document used to report errors during parsing of the form data
    * @param aParamMap html form data map.
    * @return AeXslTaskInputParameters
    */
   public static AeXslTaskInputParameters createRequestParameters(String aPrincipalName, String aTaskId,
         String aHttpMethod, String aUserAgent, String aPathInfo, Map aHttpHeaderParam,
         AeXslTaskRenderingErrors aParamErrors, Map aParamMap)
   {
      Document dom = AeXslTaskUtil.createRequestParameterDocument();

      Element rootElem = dom.getDocumentElement();
      rootElem.setAttribute("principalName", aPrincipalName); //$NON-NLS-1$
      rootElem.setAttribute("taskId", aTaskId); //$NON-NLS-1$
      rootElem.setAttribute("method", aHttpMethod.toLowerCase()); //$NON-NLS-1$
      rootElem.setAttribute("user-agent", aUserAgent); //$NON-NLS-1$
      rootElem.setAttribute("path-info", aPathInfo); //$NON-NLS-1$
      // add http request header data.
      Iterator it = aHttpHeaderParam.keySet().iterator();
      while (it.hasNext() )
      {
         String headerName = (String) it.next();
         String headerValue = (String) aHttpHeaderParam.get(headerName);
         AeXslTaskUtil.createRequestHeaderElement(rootElem, headerName, headerValue);
      }      
      // add html form data params
      it = aParamMap.keySet().iterator();
      while (it.hasNext() )
      {
         String paramName = (String) it.next();
         String paramValue = getParameter(aParamMap, paramName);
         if (paramName.startsWith(XML_CONTENT_HINT_PREFIX))
         {
            // value should be processes as xml content
            paramName = paramName.substring(XML_CONTENT_HINT_PREFIX.length());
            if (AeUtil.notNullOrEmpty(paramValue))
            {
               try
               {
                  Document doc = parseFormDataDocumentFragment(paramValue);
                  Element paramElem = AeXslTaskUtil.createRequestParameterElement(rootElem, paramName, null);
                  Node node = paramElem.getOwnerDocument().importNode(doc.getDocumentElement(), true);
                  paramElem.appendChild(node);
                  paramElem.setAttribute("type", "xml"); //$NON-NLS-1$ //$NON-NLS-2$
               }
               catch(Exception exception)
               {
                  aParamErrors.addParameterProcessError(paramName, exception);
               }
            }
            else
            {
               AeXslTaskUtil.createRequestParameterElement(rootElem, paramName, paramValue);
            }
         }
         else
         {
            AeXslTaskUtil.createRequestParameterElement(rootElem, paramName, paramValue);
         }
      }
      return new AeXslTaskInputParameters(aPrincipalName, aTaskId, dom);
   }

   /**
    * Returns a single parameter value if the value is a java.util.List of strings.
    * @param aName request parameter name
    * @return first parameter value.
    */
   protected static String getParameter(Map aParamMap, String aName)
   {
      Object obj =  aParamMap.get(aName);
      if (obj instanceof String)
      {
         // case when using file upload util.
         return (String)obj;
      }
      String values[] = (String []) obj;
      if (values != null && values.length > 0)
      {
         return values[0];
      }
      else
      {
         return null;
      }
   }

   /**
    * Converts the editable form data to a <code>Document</code>.
    * @param aFormDataXmlFragment a well formed xml fragment.
    * @return parsed document.
    * @throws AeException
    */
   protected static Document parseFormDataDocumentFragment(String aFormDataXmlFragment) throws AeException
   {
      AeXMLParserBase parser = new AeXMLParserBase();
      parser.setValidating( false );
      parser.setNamespaceAware( true );
      return parser.loadDocument( new StringReader(aFormDataXmlFragment), null );
   }

}
