//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/function/AeToIdentityQueryFunction.java,v 1.1 2007/12/18 04:06:28 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.function; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.AeFunctionExceptions;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Custom function that converts an organizational entity element to an identity
 * query element. 
 */
public class AeToIdentityQueryFunction extends AeAbstractBpelFunction
{
   public static final String FUNCTION_NAME = "toIdentityQuery"; //$NON-NLS-1$
   
   /** cached source for xsl transform */
   private static Source sCachedSource;
   
   /**
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs)
         throws AeFunctionCallException
   {
      if (aArgs.size() < 1 || aArgs.size() > 2)
      {
         // Hey, this actually throws !!
         AeFunctionExceptions.INVALID_PARAMS.error(new Object[]{FUNCTION_NAME});
      }
      
      Element include = (Element) aArgs.get(0);
      Element exclude = (Element) (aArgs.size() == 2 ? aArgs.get(1) : null);
      try
      {
         return convert(include, exclude);
      }
      catch (Exception e)
      {
         throw new AeFunctionCallException(e);
      }
   }

   /**
    * Converts an include organization entity and an optional exclude organizational
    * entity into a single identity query. 
    * @param aOrganizationalEntity
    * @param aExclude
    */
   protected Element convert(Element aOrganizationalEntity, Element aExclude) throws Exception
   {
      Map params = new HashMap();
      if (aExclude != null)
      {
         params.put("exclude", aExclude); //$NON-NLS-1$
      }
      Document doc = (Document) AeXmlUtil.doTransform(getXsl(), new DOMSource(aOrganizationalEntity), params);
      return doc.getDocumentElement();
   }
   
   /**
    * Gets the xsl to execute, caching it in a static if not loaded
    * @throws AeException
    */
   private Source getXsl() throws AeException
   {
      if (sCachedSource == null)
      {
         // don't need to synchronize here, worst that'll happen is that
         // we'll load the same doc multiple times.
         Document doc = new AeXMLParserBase(true, false).loadDocument(getClass().getResourceAsStream("orgUnitToIdentityQuery.xsl"), null); //$NON-NLS-1$
         
         sCachedSource = new DOMSource(doc);
      }
      return sCachedSource;
   }
}
 