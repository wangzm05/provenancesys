// $Header$
// ///////////////////////////////////////////////////////////////////////////
// PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc. Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2006 All rights reserved.
// ///////////////////////////////////////////////////////////////////////////

package org.activebpel.ddl.storage.tamino.upgrade;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoConfig;
import org.activebpel.rt.bpel.server.engine.storage.tamino.upgrade.AeAbstractTaminoUpgrader;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBListResponseHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A Tamino database upgrader. This class does additional upgrading of data in an existing Tamino database,
 * bringing it from version 1.2 to version 2.1.
 */
public class AeTaminoJournalUpgrader2_1 extends AeAbstractTaminoUpgrader
{
   /**
    * Default constructor.
    * 
    * @param aSchemaName
    * @param aStorageImpl
    */
   public AeTaminoJournalUpgrader2_1(String aSchemaName, IAeXMLDBStorageImpl aStorageImpl)
   {
      super(aSchemaName, aStorageImpl);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.tamino.upgrade.AeAbstractTaminoUpgrader#createTaminoConfig()
    */
   protected AeTaminoConfig createTaminoConfig()
   {
      return new AeTaminoConfig()
      {
         /**
          * @see org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoConfig#getStatementConfigFilenames()
          */
         protected List getStatementConfigFilenames()
         {
            String fileName = "journal-queries-2.1.xml"; //$NON-NLS-1$

            List list = new LinkedList();
            list.add(new AeFilenameClassTuple(fileName, AeTaminoJournalUpgrader2_1.class));
            
            return list;
         }
      };
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.tamino.upgrade.AeAbstractTaminoUpgrader#upgrade()
    */
   public void upgrade() throws AeStorageException
   {
      // First, get the list of AeReceivedItem instances.
      List list = (List) query("GetAllReceivedItems", new AeXMLDBListResponseHandler() //$NON-NLS-1$
      {
         /**
          * @see org.activebpel.rt.bpel.server.engine.storage.tamino.handlers.AeTaminoListResponseHandler#handleElement(org.w3c.dom.Element)
          */
         protected Object handleElement(Element aElement) throws AeXMLDBException
         {
            Map map = new HashMap();
            map.put("ProcessID", getLongFromElement(aElement, "ProcessID")); //$NON-NLS-1$ //$NON-NLS-2$
            map.put("EngineID", getLongFromElement(aElement, "EngineID")); //$NON-NLS-1$ //$NON-NLS-2$
            map.put("LocationPathID", getLongFromElement(aElement, "LocationPathID")); //$NON-NLS-1$ //$NON-NLS-2$
            Document doc = getDocumentFromElement(aElement, "MessageDocument"); //$NON-NLS-1$
            if (doc != null)
            {
               map.put("MessageDocument", doc); //$NON-NLS-1$
            }
            return map;
         }
      });
      
      // Now re-insert the received items as journal items
      for (Iterator iter = list.iterator(); iter.hasNext(); )
      {
         Map map = (Map) iter.next();
         LinkedHashMap params = new LinkedHashMap();
         params.put("ProcessID", map.get("ProcessID")); //$NON-NLS-1$ //$NON-NLS-2$
         params.put("EngineID", map.get("EngineID")); //$NON-NLS-1$ //$NON-NLS-2$
         params.put("LocationPathID", map.get("LocationPathID")); //$NON-NLS-1$ //$NON-NLS-2$
         Document doc = (Document) map.get("MessageDocument"); //$NON-NLS-1$
         if (doc == null)
         {
            // Entry type of 1 indicates Alarm
            params.put("EntryType", new Integer(1)); //$NON-NLS-1$
         }
         else
         {
            // Entry type of 2 indicates Message
            params.put("EntryType", new Integer(2)); //$NON-NLS-1$
            params.put("EntryDocument", doc); //$NON-NLS-1$
         }
         insertDocument("InsertJournalItem", params); //$NON-NLS-1$
      }
   }
}
