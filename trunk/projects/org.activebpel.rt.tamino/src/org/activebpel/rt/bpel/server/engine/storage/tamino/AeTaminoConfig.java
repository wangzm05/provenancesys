// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/AeTaminoConfig.java,v 1.7 2007/08/17 00:57:36 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.bpel.server.engine.storage.tamino;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBConfig;

/**
 * This class encapsulates the Tamino XQuery statements used by the Active BPEL Tamino based persistence
 * layer. This class uses a SAX parser to first parse an xml file that contains the XQuery queries.
 */
public class AeTaminoConfig extends AeXMLDBConfig
{
   /**
    * Creates a Tamino config object.
    */
   public AeTaminoConfig()
   {
      this(Collections.EMPTY_MAP);
   }

   /**
    * Creates a Tamino config object with the given map of constant overrides.
    * 
    * @param aConstantOverrides
    */
   public AeTaminoConfig(Map aConstantOverrides)
   {
      super(aConstantOverrides);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.AeStorageConfig#getStatementConfigFilenames()
    */
   protected List getStatementConfigFilenames()
   {
      String fileName = "tamino-queries.xml"; //$NON-NLS-1$

      List list = super.getStatementConfigFilenames();
      list.add(new AeFilenameClassTuple(fileName, AeTaminoConfig.class));
      
      return list;
   }
}
