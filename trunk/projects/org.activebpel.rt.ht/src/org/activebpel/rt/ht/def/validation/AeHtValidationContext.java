// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/AeHtValidationContext.java,v 1.4 2008/02/07 17:54:16 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation;

import java.util.Collections;
import java.util.List;

import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.OperationType;
import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.expr.validation.IAeExpressionValidationResult;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeQueryDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.ht.def.AeTaskInterfaceDef;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.ht.def.IAeHtExpressionDef;
import org.activebpel.rt.ht.def.IAeInterfaceDef;
import org.activebpel.rt.ht.def.IAeInterfaceDefParent;
import org.activebpel.rt.ht.def.IAeLocalNotificationDef;
import org.activebpel.rt.ht.def.IAeLocalResourceDef;
import org.activebpel.rt.ht.def.visitors.AeValidatingTraversingHtDefVisitor;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.IAeContextWSDLProvider;
import org.activebpel.rt.wsresource.validation.AeWSResourceValidationContext;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * A validation context for Human Interactions that provides access to the resources of
 * a standalone HT resource.
 */
public class AeHtValidationContext extends AeWSResourceValidationContext implements IAeHtValidationContext
{
   /**
    * C'tor
    */
   public AeHtValidationContext()
   {
      super(null);
   }
   
   /**
    * C'tor
    * 
    * @param aProvider
    */
   public AeHtValidationContext(IAeContextWSDLProvider aProvider)
   {
      super(aProvider);
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.IAeHtValidationContext#findLogicalPeopleGroup(org.activebpel.rt.xml.def.AeBaseXmlDef, javax.xml.namespace.QName)
    */
   public AeLogicalPeopleGroupDef findLogicalPeopleGroup(AeBaseXmlDef aDef, QName aLPGName)
   {
      //TODO (DV) implement this method for HT
      throw new UnsupportedOperationException("This method not yet implemented.  To be done with standalone HT validation is supported."); //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.IAeHtValidationContext#getLPGParameters(AeBaseXmlDef, QName)
    */
   public List getLPGParameters(AeBaseXmlDef aDef, QName aLPGName)
   {
      AeLogicalPeopleGroupDef def = findLogicalPeopleGroup(aDef, aLPGName);
      if (def != null)
      {
         return def.getParameterDefs();
      }
      else
      {
         return Collections.EMPTY_LIST;
      }
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.IAeHtValidationContext#findNotification(org.activebpel.rt.xml.def.AeBaseXmlDef, javax.xml.namespace.QName)
    */
   public AeNotificationDef findNotification(AeBaseXmlDef aContext, QName aNotificationName)
   {
      //TODO (DV) implement this method for HT
      throw new UnsupportedOperationException("This method not yet implemented.  To be done with standalone HT validation is supported."); //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.IAeHtValidationContext#findTask(org.activebpel.rt.xml.def.AeBaseXmlDef, javax.xml.namespace.QName)
    */
   public AeTaskDef findTask(AeBaseXmlDef aContext, QName aTaskName)
   {
      //TODO (DV) implement this method for HT
      throw new UnsupportedOperationException("This method not yet implemented.  To be done with standalone HT validation is supported."); //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.IAeHtValidationContext#getOutputMessage(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   public Message getOutputMessage(AeBaseXmlDef aDef) throws AeException
   {
      Message message = null;
      
      IAeInterfaceDef interfaceDef = findInScopeInterface(aDef);
      
      if (interfaceDef != null)
      {
         Operation operation = getOperation(interfaceDef.getPortType(), interfaceDef.getOperation());
      
         if (operation != null && operation.getStyle().equals(OperationType.ONE_WAY))
         {
            if (interfaceDef instanceof AeTaskInterfaceDef)
            {
               AeTaskInterfaceDef task = (AeTaskInterfaceDef) interfaceDef; 
               operation = getOperation(task.getResponsePortType(), task.getResponseOperation());
               if (operation != null && operation.getStyle().equals(OperationType.ONE_WAY))
               {
                  message = operation.getInput().getMessage();
               }
            }
         }
         else if (operation != null && operation.getStyle().equals(OperationType.REQUEST_RESPONSE))
         {
            message = operation.getOutput().getMessage();
         }
      }
      
      return message;
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.IAeHtValidationContext#getInputMessage(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   public Message getInputMessage(AeBaseXmlDef aDef) throws AeException
   {
      Message result = null;

      IAeInterfaceDef interfaceDef = findInScopeInterface(aDef);
      
      if (interfaceDef != null)
      {
         Operation operation = getOperation(interfaceDef.getPortType(), interfaceDef.getOperation());
         
         if (operation != null)
         {
            Input input = operation.getInput();
            if (input != null)
            {
               result = input.getMessage();
            }
         }
      }
      
      return result;
   }
   
   /**
    * @see org.activebpel.rt.ht.def.validation.IAeHtValidationContext#findInScopeInterface(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   public IAeInterfaceDef findInScopeInterface(AeBaseXmlDef aContextDef)
   {
      IAeInterfaceDefParent interfaceParent = null;
      AeBaseXmlDef def = null;
      
      // if the context def is already a interface parent or a local task/notification then return the 
      if (aContextDef instanceof IAeInterfaceDefParent)
      {
         interfaceParent = (IAeInterfaceDefParent) aContextDef;
      } 
      else if (aContextDef instanceof IAeLocalResourceDef)
      {
         interfaceParent = findInScopeTaskOrNotification((IAeLocalResourceDef) aContextDef);
      }
      else
      {
         if (aContextDef != null)
         {
            def = aContextDef.getParentXmlDef();
            while (def != null)
            {
               if ( !(def instanceof IAeInterfaceDefParent) && !(def instanceof IAeLocalResourceDef) )
               {
                  def = def.getParentXmlDef();
               }
               else
               {
                  if (def instanceof IAeLocalResourceDef)
                  {
                     interfaceParent = findInScopeTaskOrNotification((IAeLocalResourceDef) def);
                  }
                  else
                  {
                     interfaceParent = (IAeInterfaceDefParent) def;
                  }
                  break;
               }
            }
         }
      }
      
      return interfaceParent != null ? interfaceParent.getInterfaceDef() : null ;  
   }
   
   /**
    * Find the AeTaskDef or AeNotificationDef that is in scope for the supplied context def
    * 
    * @param aContextDef
    * @return the found IAeInterfaceDefParent 
    */
   public IAeInterfaceDefParent findInScopeTaskOrNotification(IAeLocalResourceDef aContextDef)
   {
      IAeInterfaceDefParent parent = null;
      
      if (aContextDef instanceof IAeLocalNotificationDef)
      {
         parent = findNotification((AeBaseXmlDef)aContextDef, aContextDef.getReference());
      }
      else
      {
         parent = findTask((AeBaseXmlDef)aContextDef, aContextDef.getReference());
      }
      
      return parent;
   }
   
   /**
    * @see org.activebpel.rt.ht.def.validation.IAeHtValidationContext#validateExpression(org.activebpel.rt.xml.def.AeBaseXmlDef, java.lang.String, java.lang.String)
    */
   public IAeExpressionValidationResult validateExpression(AeBaseXmlDef aContextDef, String aExpression,
         String aExpressionLanguage) throws Exception
   {
      // TODO (DV) implement this method for HT
      return null;
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.IAeHtValidationContext#validateQuery(org.activebpel.rt.ht.def.AeQueryDef)
    */
   public IAeExpressionValidationResult validateQuery(AeQueryDef aQuery) throws Exception
   {
      // TODO (DV) implement this method for HT
      return null;
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.IAeHtValidationContext#getExpressionLanguage(org.activebpel.rt.ht.def.IAeHtExpressionDef)
    */
   public String getExpressionLanguage(IAeHtExpressionDef aDef)
   {
      String expressionLanguage = aDef.getExpressionLanguage();
      
      if (AeUtil.isNullOrEmpty(expressionLanguage))
      {
         expressionLanguage = IAeHtDefConstants.WSBPEL_EXPR_LANGUAGE_URI;
      }
      
      return expressionLanguage;
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.IAeHtValidationContext#getNamespaceURI(java.lang.String)
    */
   public String getNamespaceURI(String aPrefix)
   {
      return null;
   }

   /**
    * @see org.activebpel.rt.ht.def.validation.IAeHtValidationContext#createRuleTraverser(org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor)
    */
   public IAeBaseXmlDefVisitor createRuleTraverser(IAeHtDefVisitor aRule)
   {
      return new AeValidatingTraversingHtDefVisitor(aRule);
   }
}
