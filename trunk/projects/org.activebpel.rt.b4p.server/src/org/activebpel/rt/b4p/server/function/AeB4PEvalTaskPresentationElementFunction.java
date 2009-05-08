//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/AeB4PEvalTaskPresentationElementFunction.java,v 1.10.2.2 2008/04/14 21:24:48 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.def.io.AeB4PIO;
import org.activebpel.rt.b4p.function.ht.AeTaskStateBasedHtFunctionContext;
import org.activebpel.rt.b4p.server.AeMessages;
import org.activebpel.rt.b4p.server.function.eval.AeB4PEvalFunction;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerVariableResolver;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;
import org.activebpel.rt.ht.def.AeDescriptionDef;
import org.activebpel.rt.ht.def.AePresentationElementsDef;
import org.activebpel.rt.ht.def.AePresentationParameterDef;
import org.activebpel.rt.ht.def.AePresentationParametersDef;
import org.activebpel.rt.ht.def.AeSubjectDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.IAeNamespaceContext;
import org.activebpel.rt.xml.def.AeBaseDefNamespaceContext;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * This is a custom b4p function for task presentation elements
 */
public class AeB4PEvalTaskPresentationElementFunction extends AeAbstractBpelFunction
{

   /** name of the function */
   public static final String FUNCTION_NAME = "evalTaskPresentationElements"; //$NON-NLS-1$

   /** min num of args for function */
   private static final int MIN_ARGS = 4;
   /** max number of args for function */
   private static final int MAX_ARGS = 5;
   
   /** offset for all of the args */
   private static final int ARG_PRESENTATION_ELEMENTS = 0;
   private static final int ARG_PROCESS_VARS = 1;
   private static final int ARG_TASK_INSTANCE = 2;
   private static final int ARG_PARENT_TASK_INSTANCE = 3;
   private static final int ARG_LANGUAGE = 4;
   
   /**
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {
      // Required Arguments 
      //  arg[0] = htd:presentationElements element
      //  arg[1] = aeb4p:processVariables element
      //  arg[2] = taskInstance element
      //  arg[3] = parentTaskInstance element
      // Optional Arguments
      //  arg[4] = language 
      
      // Validate args count
      if (aArgs.size() < MIN_ARGS || aArgs.size() > MAX_ARGS)
      {
         throwFunctionException(INVALID_PARAMS, FUNCTION_NAME);
      }
      
      // Extract params
      Element taskPresentationElem = (Element) aArgs.get(ARG_PRESENTATION_ELEMENTS);
      Element processVars = (Element) aArgs.get(ARG_PROCESS_VARS);
      Element taskInstance = (Element) aArgs.get(ARG_TASK_INSTANCE);
      Element parentTaskInstance = (Element) aArgs.get(ARG_PARENT_TASK_INSTANCE);
      
      String lang = null;
      if (aArgs.size() == MAX_ARGS)
         lang = (String) aArgs.get(ARG_LANGUAGE);

      // deserialize presentation elements to def
      AePresentationElementsDef presentationElemsDef = deserializeToDef(taskPresentationElem);
      
      // create a map of presentation parameter name to its expression result
      Map map;
      try
      {
         map = createNameValueMap(aContext, presentationElemsDef, processVars, taskInstance, parentTaskInstance);
      }
      catch (AeException e)
      {
         throw new AeFunctionCallException(e);
      }

      // resolve expressions in each subject and description of presentation elements
      replaceVarsInSubjectAndDescription(presentationElemsDef, map);
      
      // serialize task presentation data
      presentationElemsDef.trim(lang);
      presentationElemsDef.setPresentationParameters(null);
      Element taskPresentationData = serialize(presentationElemsDef);
      return taskPresentationData;
   }
   

   /**
    * Deserializes htd:presentationElements to def 
    * @param aElem
    * @throws AeFunctionCallException
    */
   private AePresentationElementsDef deserializeToDef(Element aElem) throws AeFunctionCallException
   {
      try
      {
         AePresentationElementsDef presentationElemsDef = (AePresentationElementsDef) AeB4PIO.deserializeFragment(aElem);
         return presentationElemsDef;
      }
      catch (Exception ex)
      {
         throw new AeFunctionCallException(ex.getLocalizedMessage());
      }
   }

   /**
    * Creates a Map of presentation parameter names to the values 
    * 
    * @param aContext
    * @param aDef
    * @param aProcessVars
    * @param aTaskInstance
    * @param aParentTaskInstance
    */
   private Map createNameValueMap(IAeFunctionExecutionContext aContext, AePresentationElementsDef aDef, Element aProcessVars, Element aTaskInstance, Element aParentTaskInstance) throws AeException
   {
      Map nameValueMap = new HashMap();
      AeTypeMapping typeMapping = aContext.getAbstractBpelObject().getProcess().getEngine().getTypeMapping();
      AePresentationParametersDef presentationParamsDef = aDef.getPresentationParameters();
      
      if (presentationParamsDef != null)
      {
         AeB4PEvalFunction eval = new AeB4PEvalFunction();
         IAeExpressionRunnerVariableResolver resolver = eval.toVariableResolver(aContext, aProcessVars);
         eval.setVariableResolver(resolver);
         eval.setExpressionLanguage(presentationParamsDef.getExpressionLanguage());
         if (aTaskInstance != null)
            eval.setHtFunctionContext(new AeTaskStateBasedHtFunctionContext(aTaskInstance, aParentTaskInstance));

         for(Iterator iter=presentationParamsDef.getPresentationParameterDefs(); iter.hasNext(); )
         {
            AePresentationParameterDef presentationParamDef = (AePresentationParameterDef) iter.next();
            IAeNamespaceContext nsContext = new AeBaseDefNamespaceContext(presentationParamDef);
            eval.setExpression(presentationParamDef.getExpression());
            eval.setNamespaceContext(nsContext);
            try
            {
               Object data = eval.eval(aContext);
               if (data instanceof Element)
               {
                  data = typeMapping.deserialize(presentationParamDef.getType(), AeXmlUtil.getText((Element)data));
               }
               else if ( (data instanceof Attr) || (data instanceof Text) ) 
               {
                 data = typeMapping.deserialize(presentationParamDef.getType(),((Node) data).getNodeValue());  
               }
               if (data instanceof Double)
               {
                  data = AeUtil.doubleToLong((Double) data);
               }
               nameValueMap.put(presentationParamDef.getName(), data);
            }
            catch(Throwable t)
            {
               Object[] args = {eval.getExpression(), t.getLocalizedMessage()};
               String message = AeMessages.format("AeB4PEvalTaskPresentationElementFunction.ExprError", args); //$NON-NLS-1$
               AeException.logError(t, message);
               nameValueMap.put(presentationParamDef.getName(), AeMessages.format("AeB4PEvalTaskPresentationElementFunction.ValueForError", presentationParamDef.getName())); //$NON-NLS-1$
            }
            
         }
      }
      return nameValueMap;
   }

   /**
    * Replace variables with their values in subject and descript elements
    * @param aDef
    * @param aVarNameValueMap
    */
   private void replaceVarsInSubjectAndDescription(AePresentationElementsDef aDef, Map aVarNameValueMap)
   {
      if (AeUtil.isNullOrEmpty(aVarNameValueMap))
         return;
      
      // Replace Variables with values in subject
      for(Iterator iter=aDef.getSubjectDefs(); iter.hasNext(); )
      {
         AeSubjectDef subjectDef = (AeSubjectDef) iter.next();
         String subject = subjectDef.getTextValue();
         subjectDef.setTextValue(AeUtil.replaceXQueryStyleParamsInString(subject, aVarNameValueMap));
      }
      // Replace Variables with values in description
      for(Iterator iter=aDef.getDescriptionDefs(); iter.hasNext(); )
      {
         AeDescriptionDef descDef = (AeDescriptionDef) iter.next();
         // check for element first
         List extensionElements = descDef.getExtensionElementDefs();
         if (AeUtil.notNullOrEmpty(extensionElements))
         {
            for(Iterator it=extensionElements.iterator(); it.hasNext();)
            {
               AeExtensionElementDef elementDef = (AeExtensionElementDef) it.next();
               Element element = elementDef.getExtensionElement();
               AeUtil.replaceXQueryStyleParamsInElement(element, aVarNameValueMap);
            }
         }
         else
         {
            String desc = descDef.getTextValue();
            descDef.setTextValue(AeUtil.replaceXQueryStyleParamsInString(desc, aVarNameValueMap));
         }
      }
   }
   
   /**
    * Creates presentation data element from presentation elements def
    * @param aDef
    */
   private Element serialize(AePresentationElementsDef aDef)
   {
      return AeB4PIO.serializePresentations(aDef);
   }
}
