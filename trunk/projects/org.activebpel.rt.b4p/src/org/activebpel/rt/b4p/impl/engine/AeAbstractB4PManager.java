//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/engine/AeAbstractB4PManager.java,v 1.2 2008/03/11 03:10:26 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.engine;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.function.AeB4PFunctionContext;
import org.activebpel.rt.b4p.impl.lpg.AeLiteralOnlyLPGProviderFactory;
import org.activebpel.rt.b4p.impl.lpg.IAeLPGConstants;
import org.activebpel.rt.b4p.impl.lpg.IAeLogicalPeopleGroupProvider;
import org.activebpel.rt.b4p.impl.lpg.IAeLogicalPeopleGroupProviderFactory;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.config.AeFunctionContextExistsException;
import org.activebpel.rt.bpel.config.IAeUpdatableEngineConfig;
import org.activebpel.rt.bpel.impl.AeManagerAdapter;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.function.AeInvalidFunctionContextException;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.AeOrganizationalEntityDef;
import org.activebpel.rt.ht.def.io.AeHtIO;
import org.activebpel.rt.util.AeXPathUtil;
import org.w3c.dom.Element;

/**
 * Basic version of the B4P manager which is not dependent on the server
 * or deployment of ae specific resources.
 */
public abstract class AeAbstractB4PManager extends AeManagerAdapter implements IAeB4PManager
{
   /** xpath query to select the LPG from the extensions element */
   private static final String sQueryPattern = "aeb4p:logicalPeopleGroups/aeb4p:logicalPeopleGroup[@name=''{0}'' or @locationPath=\"{1}\"]"; //$NON-NLS-1$
   /** The logical people group provider factory. */
   private IAeLogicalPeopleGroupProviderFactory mLPGProviderFactory = new AeLiteralOnlyLPGProviderFactory();

   /**
    * Constructs B4P manager.
    *
    * @param aConfig The configuration map for this manager.
    */
   public AeAbstractB4PManager(Map aConfig)
   {
   }
   
   /**
    * @see org.activebpel.rt.b4p.impl.engine.IAeB4PManager#cancelTask(long, int, long)
    */
   public void cancelTask(long aProcessId, int aPeopleActivityId, long aTransmissionId) throws AeBusinessProcessException
   {
   }

   /**
    * Overrides method to deploy resources and custom functions.
    * @see org.activebpel.rt.bpel.impl.AeManagerAdapter#prepareToStart()
    */
   public void prepareToStart() throws Exception
   {
      deployResources();
      super.prepareToStart();
   }

   /**
    * Deploy all dependent resources, such as the b4p bprs and any custom
    * functions needed by the task system processes.
    * @throws IOException
    * @throws AeException
    * @throws AeInvalidFunctionContextException
    */
   protected void deployResources() throws IOException, AeException, AeInvalidFunctionContextException
   {
      installFunctions();
   }

   /**
    * Installs the function context for task manager functions.
    * @throws AeInvalidFunctionContextException
    */
   protected void installFunctions() throws AeInvalidFunctionContextException
   {
      try
      {
         IAeUpdatableEngineConfig config = getEngine().getEngineConfiguration().getUpdatableEngineConfig();
         addFunctionContext(config);
         config.update();
      }
      catch (AeFunctionContextExistsException e)
      {
         // ignore this fault since it's expected on a restart or in a cluster
      }
   }

   /**
    * Called by the <code>installFunctions()</code> to install custom function contexts.
    * This method adds the WS HT B4P namespace functions. Subclasss may override to add internal
    * functions.
    * @param aEngineConfig
    * @throws AeInvalidFunctionContextException
    * @throws AeFunctionContextExistsException
    */
   protected void addFunctionContext(IAeUpdatableEngineConfig aEngineConfig) throws AeInvalidFunctionContextException, AeFunctionContextExistsException
   {
      aEngineConfig.addNewFunctionContext("AeB4PManager", IAeB4PConstants.B4P_NAMESPACE, AeB4PFunctionContext.class.getName(), null); //$NON-NLS-1$
   }
   
   /**
    * @see org.activebpel.rt.b4p.impl.engine.IAeB4PManager#evaluateLogicalPeopleGroup(org.activebpel.rt.bpel.impl.IAeBpelObject, org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef)
    */
   public Element evaluateLogicalPeopleGroup(IAeBpelObject aBpelObject, AeLogicalPeopleGroupDef aLPGDef)
   {
      Element extensions = getDeploymentExtensionsElement(aBpelObject);

      Element result = null;
      if (extensions != null)
      {
         try
         {
            Object[] args = { aLPGDef.getName(), aLPGDef.getLocationPath() };
            String query = MessageFormat.format(sQueryPattern, args);
            Element lpgElement = (Element) AeXPathUtil.selectSingleNode(extensions, query, IAeLPGConstants.LPG_PREFIX, IAeLPGConstants.LPG_NAMESPACE);
            if (lpgElement != null)
            {
               IAeLogicalPeopleGroupProvider provider = getLPGProviderFactory().createProvider(lpgElement);
               result = provider.evaluate(aLPGDef, lpgElement);
            }
         }
         catch (AeException e)
         {
            // exceptions are logged but don't fail the process
            AeException.logError(e);
         }
      }

      if (result == null)
      {
         AeOrganizationalEntityDef def = new AeOrganizationalEntityDef();
         result = AeHtIO.serialize2Element(def);
      }

      return result;
   }

   /**
    * @see org.activebpel.rt.b4p.impl.engine.IAeB4PManager#taskResponseReceived(long, int, long)
    */
   public void taskResponseReceived(long aProcessId, int aPeopleActivityId,
         long aTransmissionId)
   {
   }

   /**
    * Returns the Element representing the 'extensions' section of the 
    * deployment information.  This class does not make any assumptions
    * about where that deployment information might come from.
    * 
    * @param aBpelObject
    */
   protected abstract Element getDeploymentExtensionsElement(IAeBpelObject aBpelObject);

   /**
    * @return Returns the lPGProviderFactory.
    */
   protected IAeLogicalPeopleGroupProviderFactory getLPGProviderFactory()
   {
      return mLPGProviderFactory;
   }

   /**
    * @param aProviderFactory the lPGProviderFactory to set
    */
   protected void setLPGProviderFactory(IAeLogicalPeopleGroupProviderFactory aProviderFactory)
   {
      mLPGProviderFactory = aProviderFactory;
   }
}
