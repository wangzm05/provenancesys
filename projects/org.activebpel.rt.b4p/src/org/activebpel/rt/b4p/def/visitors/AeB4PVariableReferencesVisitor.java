// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/AeB4PVariableReferencesVisitor.java,v 1.1 2008/02/27 20:56:53 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.def.visitors;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.bpel.IAeExpressionLanguageFactory;
import org.activebpel.rt.bpel.def.activity.support.AeFromPartDef;
import org.activebpel.rt.bpel.def.activity.support.AeToPartDef;
import org.activebpel.rt.bpel.def.validation.AeVariableReference;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.expr.def.IAeExpressionAnalyzer;
import org.activebpel.rt.expr.def.IAeExpressionAnalyzerContext;
import org.activebpel.rt.ht.def.IAeHtExpressionDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;


/**
 * Finds all variable name references in a b4p def.
 */
public class AeB4PVariableReferencesVisitor extends AeAbstractB4PExpressionDefVisitor
{
   /** The expression language factory. */
   private IAeExpressionLanguageFactory mExpressionLanguageFactory;
   /** The expression analyzer context. */
   private IAeExpressionAnalyzerContext mExpressionAnalyzerContext;
   /** The bpel namespace. */
   private String mBpelNamespace;
   /** The default expression language. */
   private String mDefaultExpressionLanguage;
   /** The set of variables referenced. */
   private Set mReferencedVariables = new HashSet();

   /**
    * C'tor.
    *
    * @param aBpelNamespace
    * @param aDefaultExpressionLanguage
    * @param aExpressionLanguageFactory
    * @param aContext
    */
   public AeB4PVariableReferencesVisitor(String aBpelNamespace, String aDefaultExpressionLanguage,
         IAeExpressionLanguageFactory aExpressionLanguageFactory,
         IAeExpressionAnalyzerContext aContext)
   {
      setBpelNamespace(aBpelNamespace);
      setDefaultExpressionLanguage(aDefaultExpressionLanguage);
      setExpressionLanguageFactory(aExpressionLanguageFactory);
      setExpressionAnalyzerContext(aContext);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AePeopleActivityDef)
    */
   public void visit(AePeopleActivityDef aDef)
   {
      super.visit(aDef);

      addVariableReference(aDef.getInputVariable(), AeVariableValidator.VARIABLE_READ_WSIO, aDef);
      addVariableReference(aDef.getOutputVariable(), AeVariableValidator.VARIABLE_WRITE_WSIO, aDef);

      if (aDef.getFromPartsDef() != null)
      {
         for (Iterator iter = aDef.getFromPartsDef().getFromPartDefs(); iter.hasNext(); )
         {
            AeFromPartDef fromPartDef = (AeFromPartDef) iter.next();
            addVariableReference(fromPartDef.getToVariable(), AeVariableValidator.VARIABLE_WRITE_WSIO, fromPartDef);
         }
      }

      if (aDef.getToPartsDef() != null)
      {
         for (Iterator iter = aDef.getToPartsDef().getToPartDefs(); iter.hasNext(); )
         {
            AeToPartDef toPartDef = (AeToPartDef) iter.next();
            addVariableReference(toPartDef.getFromVariable(), AeVariableValidator.VARIABLE_READ_WSIO, toPartDef);
         }
      }
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PExpressionDefVisitor#visitExpressionDef(org.activebpel.rt.ht.def.IAeHtExpressionDef)
    */
   protected void visitExpressionDef(IAeHtExpressionDef aDef)
   {
      String languageUri = getLanguageUri(aDef);
      try
      {
         IAeExpressionAnalyzer analyzer = getExpressionLanguageFactory().createExpressionAnalyzer(getBpelNamespace(), languageUri);
         for (Iterator iter = analyzer.getVariables(getExpressionAnalyzerContext(), aDef.getExpression()).iterator(); iter.hasNext(); )
         {
            String varName = (String) iter.next();
            addVariableReference(varName, AeVariableValidator.VARIABLE_READ_OTHER, (AeBaseXmlDef) aDef);
         }
      }
      catch (AeException ex)
      {
         AeException.logError(ex);
      }
   }

   /**
    * Gets the language URI specified in the ht expression or the default
    * language if none is specified.
    *
    * @param aDef
    */
   private String getLanguageUri(IAeHtExpressionDef aDef)
   {
      if (AeUtil.notNullOrEmpty(aDef.getExpressionLanguage()))
      {
         return aDef.getExpressionLanguage();
      }
      return getDefaultExpressionLanguage();
   }

   /**
    * Adds the given variable reference to the set of variable references.
    *
    * @param aVariableName
    * @param aMode
    * @param aDef
    */
   private void addVariableReference(String aVariableName, int aMode, AeBaseXmlDef aDef)
   {
      if (aVariableName != null)
         getReferencedVariables().add(new AeVariableReference(aVariableName, aMode, aDef));
   }

   /**
    * @return Returns the referencedVariableNames.
    */
   public Set getReferencedVariables()
   {
      return mReferencedVariables;
   }

   /**
    * Gets the set of variable names being referenced.
    */
   public Set getReferencedVariableNames()
   {
      Set rval = new HashSet(getReferencedVariables().size());
      for (Iterator iter = getReferencedVariables().iterator(); iter.hasNext(); )
      {
         AeVariableReference reference = (AeVariableReference) iter.next();
         rval.add(reference.getVariableName());
      }
      return rval;
   }

   /**
    * @return Returns the expressionAnalyzerContext.
    */
   protected IAeExpressionAnalyzerContext getExpressionAnalyzerContext()
   {
      return mExpressionAnalyzerContext;
   }

   /**
    * @param aExpressionAnalyzerContext the expressionAnalyzerContext to set
    */
   protected void setExpressionAnalyzerContext(IAeExpressionAnalyzerContext aExpressionAnalyzerContext)
   {
      mExpressionAnalyzerContext = aExpressionAnalyzerContext;
   }

   /**
    * @return Returns the expressionLanguageFactory.
    */
   protected IAeExpressionLanguageFactory getExpressionLanguageFactory()
   {
      return mExpressionLanguageFactory;
   }

   /**
    * @param aExpressionLanguageFactory the expressionLanguageFactory to set
    */
   protected void setExpressionLanguageFactory(IAeExpressionLanguageFactory aExpressionLanguageFactory)
   {
      mExpressionLanguageFactory = aExpressionLanguageFactory;
   }

   /**
    * @return Returns the bpelNamespace.
    */
   protected String getBpelNamespace()
   {
      return mBpelNamespace;
   }

   /**
    * @param aBpelNamespace the bpelNamespace to set
    */
   protected void setBpelNamespace(String aBpelNamespace)
   {
      mBpelNamespace = aBpelNamespace;
   }

   /**
    * @return Returns the defaultExpressionLanguage.
    */
   protected String getDefaultExpressionLanguage()
   {
      return mDefaultExpressionLanguage;
   }

   /**
    * @param aDefaultExpressionLanguage the defaultExpressionLanguage to set
    */
   protected void setDefaultExpressionLanguage(String aDefaultExpressionLanguage)
   {
      mDefaultExpressionLanguage = aDefaultExpressionLanguage;
   }
}
