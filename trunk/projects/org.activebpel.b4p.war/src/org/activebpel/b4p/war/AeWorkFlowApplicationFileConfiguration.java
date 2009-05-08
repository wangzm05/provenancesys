//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/AeWorkFlowApplicationFileConfiguration.java,v 1.2 2008/02/17 21:08:40 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.config.AeConfigurationUtil;

/**
 * XML file based configuration.
 */
public class AeWorkFlowApplicationFileConfiguration extends AeWorkFlowApplicationConfiguration
{

   /**
    * Creates a configuration based on the given xml configuration reader.
    * @param aXmlConfigReader
    * @throws AeException
    */
   public AeWorkFlowApplicationFileConfiguration(Reader aXmlConfigReader) throws AeException
   {
      Map entries = AeConfigurationUtil.loadConfig(aXmlConfigReader);
      setEntries( entries );
   }   
   
   /**
    * Creates a configuration based on the given xml configuration file.
    * @param aXmlConfigFile
    * @throws AeException
    */
   public AeWorkFlowApplicationFileConfiguration(File aXmlConfigFile) throws AeException
   {
      try
      {
         Map entries = AeConfigurationUtil.loadConfig(new InputStreamReader(new FileInputStream(aXmlConfigFile)));
         setEntries( entries );
      }
      catch(IOException ioe)
      {
         throw new AeException(ioe);
      }
   }      

}
