//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/eval/AeB4PEvalExpressionRunnerVariableResolver.java,v 1.1.4.1 2008/04/21 16:08:59 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function.eval;

import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.function.AeB4PProcessVariableDeserializer;
import org.activebpel.rt.bpel.IAeVariableView;
import org.activebpel.rt.bpel.impl.expr.AeExpressionRunnerVariableResolver;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.w3c.dom.Element;

/**
 * Variable resolver that resolves variables using a &lt;aeb4p:processVariables&gt; root element
 * as the data model.
 */
public class AeB4PEvalExpressionRunnerVariableResolver extends AeExpressionRunnerVariableResolver
{
   /** Prefix to NS map */
   public static final Map NSS_MAP = new HashMap();
   static
   {
      NSS_MAP.put("aeb4p", IAeB4PConstants.AEB4P_NAMESPACE); //$NON-NLS-1$
   }

   /** Engine's data type mappings. */   
   private AeTypeMapping mTypeMapping;
   /** Variables root element. */
   private Element mElement;
   /** Map containing deserialized variables.*/
   private Map mVariables;
   
   /**
    * Creates variable resolver for ht eval function.
    * @param aElement The &lt;aeb4p:processVariables&gt; root element.
    * @param aTypeMapping engine type mapping.
    */
   public AeB4PEvalExpressionRunnerVariableResolver(Element aElement, AeTypeMapping aTypeMapping)
   {
      super(null);
      mElement = aElement;
      mTypeMapping = aTypeMapping;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.AeExpressionRunnerVariableResolver#internalFindVariable(java.lang.String)
    */
   protected IAeVariableView internalFindVariable(String aVariableName)
   {
      if (getVariables().containsKey(aVariableName))
      {
         // Variable already deserialized.
         return (IAeVariableView) getVariables().get(aVariableName);
      }
      // find variable
      try
      {
         Element procVarElem = (Element)AeXPathUtil.selectSingleNode(getElement(), "aeb4p:processVariable[@name='" + aVariableName + "']", NSS_MAP); //$NON-NLS-1$ //$NON-NLS-2$
         AeB4PProcessVariableDeserializer des = new AeB4PProcessVariableDeserializer( getTypeMapping() );
         IAeVariableView var = des.deserialize(procVarElem);
         getVariables().put(aVariableName, var);
         return var;
      }
      catch(Exception e)
      {
         AeException.logError(e);
         // ignore?
         return null;
      }
   }

   /**
    * @return The &lt;aeb4p:processVariables&gt; root element.
    */
   protected Element getElement()
   {
      return mElement;
   }

   /**
    * @return Engine type mapping.
    */
   protected AeTypeMapping getTypeMapping()
   {
      return mTypeMapping;
   }

   /** 
    * @return Map containing deserialized maps.
    */
   protected Map getVariables()
   {
      if (mVariables == null)
      {
         mVariables = new HashMap();
      }
      return mVariables;
   }
}
