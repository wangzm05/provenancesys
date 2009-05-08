//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/ext/AeAbstractB4PExtensionObject.java,v 1.18 2008/03/26 14:52:54 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.ext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.def.AeB4PBaseDef;
import org.activebpel.rt.b4p.def.IAeB4PContext;
import org.activebpel.rt.b4p.def.IAeB4PContextParent;
import org.activebpel.rt.b4p.def.visitors.AeB4PInlineDefVisitor;
import org.activebpel.rt.b4p.def.visitors.AeB4PLocationPathVisitor;
import org.activebpel.rt.b4p.def.visitors.AeB4PTaskFaultVisitor;
import org.activebpel.rt.b4p.def.visitors.AeB4PVariableReferencesVisitor;
import org.activebpel.rt.b4p.def.visitors.AeB4PVariableUsageVisitor;
import org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor;
import org.activebpel.rt.b4p.validation.AeB4PValidationPreferencesFactory;
import org.activebpel.rt.b4p.validation.AeB4PValidator;
import org.activebpel.rt.bpel.IAeExpressionLanguageFactory;
import org.activebpel.rt.bpel.def.adapter.IAeCorePreprocessingAdapter;
import org.activebpel.rt.bpel.def.adapter.IAeValidationPreprocessingAdapter;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.validation.AeExtensionNamespaceInfo;
import org.activebpel.rt.bpel.def.validation.IAeValidationAdapter;
import org.activebpel.rt.bpel.def.validation.IAeValidationContext;
import org.activebpel.rt.expr.def.AeExpressionAnalyzerContext;
import org.activebpel.rt.expr.def.IAeExpressionAnalyzerContext;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.wsdl.IAeContextWSDLProvider;
import org.activebpel.rt.wsresource.validation.IAeWSResourceValidationPreferences;
import org.activebpel.rt.wsresource.validation.IAeWSValidationPreferencesFactory;
import org.activebpel.rt.xml.def.AeBaseDefNamespaceContext;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.IAeAdapter;
import org.activebpel.rt.xml.def.IAeExtensionObject;
import org.activebpel.rt.xml.def.IAeGetBaseXmlDefAdapter;
import org.activebpel.rt.xml.def.visitors.AeLocationPathVisitor;

/**
 * Abstract base class for BPEL4People extension object implementations cache for the deserialized B4P Def
 * model
 * @see IAeExtensionObject
 */
public abstract class AeAbstractB4PExtensionObject implements IAeExtensionObject,
      IAeCorePreprocessingAdapter, IAeValidationPreprocessingAdapter, IAeGetBaseXmlDefAdapter,
      IAeValidationAdapter
{
   protected static List sRequiredExtensions = new ArrayList(2);
   static
   {
      sRequiredExtensions.add(new AeExtensionNamespaceInfo(IAeHtDefConstants.DEFAULT_HT_NS, IAeHtDefConstants.DEFAULT_HT_PREFIX, true));
      sRequiredExtensions.add(new AeExtensionNamespaceInfo(IAeB4PConstants.B4P_NAMESPACE, IAeB4PConstants.B4P_PREFIX, true));
   };

   /**
    * Cached extension Def object
    */
   private AeB4PBaseDef mDef;

   /**
    * @see org.activebpel.rt.xml.def.IAeExtensionObject#getAdapter(java.lang.Class)
    */
   public IAeAdapter getAdapter(Class aClass)
   {
      if (aClass.isAssignableFrom(getClass()))
         return this;
      return null;
   }

   /**
    * @see org.activebpel.rt.bpel.def.adapter.IAeValidationPreprocessingAdapter#preprocessForValidation(org.activebpel.rt.wsdl.IAeContextWSDLProvider, org.activebpel.rt.bpel.IAeExpressionLanguageFactory)
    */
   public void preprocessForValidation(IAeContextWSDLProvider aContextProvider, IAeExpressionLanguageFactory aExpressionLanguageFactory)
   {
      IAeB4PDefVisitor usageVisitor = new AeB4PVariableUsageVisitor(aExpressionLanguageFactory);
      getDef().accept(usageVisitor);
      
      IAeB4PDefVisitor taskFaultVisitor = new AeB4PTaskFaultVisitor(aContextProvider);
      getDef().accept(taskFaultVisitor);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.adapter.IAeCorePreprocessingAdapter#preprocessForCore(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   public void preprocessForCore(AeBaseXmlDef aDef) throws AeException
   {
      AeB4PBaseDef def = deserialize(aDef);

      if (def instanceof IAeB4PContextParent)
      {
         IAeB4PContextParent b4pCtxParentDef = (IAeB4PContextParent) def;
         b4pCtxParentDef.setContext(createB4PContext(aDef));
      }
      
      def.setParentXmlDef(aDef);
      setDef(def);

      // build xpaths
      AeLocationPathVisitor pathBuilder = new AeB4PLocationPathVisitor(aDef.getParentXmlDef().getLocationPath());
      getDef().accept(pathBuilder);

      // Resolve references for 'localTask' 'localNotification'
      IAeB4PDefVisitor inlineVisitor = new AeB4PInlineDefVisitor();
      getDef().accept(inlineVisitor);
   }
   
   /**
    * Create a IAeB4PContext context that will be applied to any def which extends IAeB4PContextParent.
    * 
    * @param aDef
    */
   protected abstract IAeB4PContext createB4PContext(AeBaseXmlDef aDef);

   /**
    * FIXMEQ (b4p-validation) need to do schema validation of b4p def - where are we going to get the schema(s) from?
    *
    * @see org.activebpel.rt.bpel.def.validation.IAeValidationAdapter#validate(org.activebpel.rt.bpel.def.validation.IAeValidationContext)
    */
   public void validate(IAeValidationContext aValidationContext)
   {
      IAeWSValidationPreferencesFactory prefFactory = AeB4PValidationPreferencesFactory.getInstance();
      IAeWSResourceValidationPreferences prefs = prefFactory.createPreferences();
      AeB4PValidator.validate(getDef(), prefs, aValidationContext);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.IAeValidationAdapter#getVariableUsage(org.activebpel.rt.bpel.def.validation.IAeValidationContext)
    */
   public Set getVariableUsage(IAeValidationContext aValidationContext)
   {
      AeBaseXmlDef def = getDef();
      IAeExpressionLanguageFactory factory = aValidationContext.getExpressionLanguageFactory();
      IAeExpressionAnalyzerContext ctx = new AeExpressionAnalyzerContext(new AeBaseDefNamespaceContext(def));
      String bpelNamespace = AeDefUtil.getProcessDef(def).getNamespace();
      String defaultExpressionLang = AeDefUtil.getProcessDef(def).getExpressionLanguage();
      AeB4PVariableReferencesVisitor visitor = new AeB4PVariableReferencesVisitor(bpelNamespace, defaultExpressionLang, factory, ctx);
      def.accept(visitor);
      return visitor.getReferencedVariables();
   }

   /**
    * Deserializes extension element used in the def
    *
    * @param aDef
    * @throws AeException
    */
   protected abstract AeB4PBaseDef deserialize(AeBaseXmlDef aDef) throws AeException;

    /**
    * @see org.activebpel.rt.xml.def.IAeGetBaseXmlDefAdapter#getExtensionAsBaseXmlDef()
    */
   public AeBaseXmlDef getExtensionAsBaseXmlDef()
   {
      return getDef();
   }

   /**
    * @return the def
    */
   public AeB4PBaseDef getDef()
   {
      return mDef;
   }

   /**
    * @param aDef the def to set
    */
   protected void setDef(AeB4PBaseDef aDef)
   {
      mDef = aDef;
   }
}
