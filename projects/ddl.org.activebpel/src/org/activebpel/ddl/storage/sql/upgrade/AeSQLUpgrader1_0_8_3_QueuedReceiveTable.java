//$Header: /Development/AEDevelopment/projects/ddl.org.activebpel/src/org/activebpel/ddl/storage/sql/upgrade/AeSQLUpgrader1_0_8_3_QueuedReceiveTable.java,v 1.3 2005/10/17 20:43:08 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.ddl.storage.sql.upgrade;

import java.io.Reader;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.namespace.QName;

import org.activebpel.ddl.AeMessages;
import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig;
import org.activebpel.rt.bpel.server.engine.storage.sql.upgrade.AeAbstractSQLUpgrader;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.apache.commons.dbutils.ResultSetHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A SQL upgrader that fixes up the values in the match hash and correlation hash table columns in the
 * AeQueuedReceive table.  The values in those columns were not consistent due to differing impls
 * of the QName object.  We changed the code that creates the hash values to use a more robust 
 * technique for generating the QName hash code.  This upgrader changes any current values to match
 * what will be generated by the new code.
 */
public class AeSQLUpgrader1_0_8_3_QueuedReceiveTable extends AeAbstractSQLUpgrader
{
   /** The name of the attribute used for the type of correlated property. */
   private static final String TYPE_ATTRNAME = "type"; //$NON-NLS-1$
   /** The name of the attribute used for the type of correlated property. */
   private static final String NAMESPACE_ATTRNAME = "namespace"; //$NON-NLS-1$
   /** The name of the attribute used for the type of correlated property. */
   private static final String LOCAL_PART_ATTRNAME = "localPart"; //$NON-NLS-1$
   /** The name of the xml element used for the root of a  property. */
   private static final String PROPERTY_TAGNAME = "property"; //$NON-NLS-1$

   /**
    * Constructs a queue storage upgrader.
    * 
    * @param aUpgradeName
    * @param aSQLConfig
    */
   public AeSQLUpgrader1_0_8_3_QueuedReceiveTable(String aUpgradeName, AeSQLConfig aSQLConfig)
   {
      super(aUpgradeName, aSQLConfig);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.upgrade.AeAbstractSQLUpgrader#wrapSQLConfig(org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig)
    */
   public AeSQLConfig wrapSQLConfig(AeSQLConfig aSQLConfig)
   {
      return new AeUpgraderSQLConfig(aSQLConfig);
   }

   /**
    * Upgrades the queue storage to the most recent version.  Does any data conversion necessary
    * to bring the information in the databse in line with what is required for successful
    * engine operation.
    * 
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.upgrade.AeAbstractSQLUpgrader#doUpgrade(java.sql.Connection)
    */
   protected void doUpgrade(Connection aConnection) throws AeException
   {
      try
      {
         fixHashColumns(aConnection);
      }
      catch (Exception e)
      {
         throw new AeException(e);
      }
   }

   /**
    * This method is called in order to fix up the data found in the two hash columns of 
    * the AeQueuedReceive table.  The algorithm used to generate the hash values has changed
    * slightly and any old values must be recalculated.
    */
   protected void fixHashColumns(Connection aConnection) throws Exception
   {
      List queuedReceives = (List) query(getQueryName(), null, new AeQueuedReceiveListResultSetHandler());

      // Iterate through the list, updating each alarm.
      for (Iterator iter = queuedReceives.iterator(); iter.hasNext(); )
      {
         AeQueuedReceive1_0_8_3 qr = (AeQueuedReceive1_0_8_3) iter.next();
         Object [] params = new Object[] {
               new Integer(qr.getNewMatchHash()),
               new Integer(qr.getNewCorrelationHash()),
               new Integer(qr.getQueuedReceiveId())
         };
         int cols = update(aConnection, IAeUpgraderSQLKeys.UPDATE_HASH_VALUES, params);
         if (cols != 1)
         {
            AeException.info(AeMessages.getString("AeSQLUpgrader1_0_8_3_QueuedReceiveTable.FAILED_TO_FIX_HASH_VALUES_ERROR")); //$NON-NLS-1$
         }
      }
   }

   /**
    * Gets the name of the query to run for selecting the queued receives
    */
   protected String getQueryName()
   {
      return IAeUpgraderSQLKeys.GET_QUEUED_RECEIVES;
   }

   /**
    * Deserializes a correlation set from an XML string representation back to a correlation map.
    * 
    * @param aReader The XML string representation of the correlation map.
    * @return The correlation <code>Map</code>.
    */
   protected Map deserializeCorrelationProperties(Reader aReader) throws Exception
   {
      Map map = new HashMap();

      AeXMLParserBase parser = new AeXMLParserBase();
      parser.setValidating(false);
      Document doc = parser.loadDocument(aReader, null);

      NodeList nl = doc.getElementsByTagName(PROPERTY_TAGNAME);
      for (int i = 0; i < nl.getLength(); i++)
      {
         Element propElem = (Element) nl.item(i);
         String nameLocal = propElem.getAttribute(LOCAL_PART_ATTRNAME);
         String nameNamespace = propElem.getAttribute(NAMESPACE_ATTRNAME);
         QName name = new QName(nameNamespace, nameLocal);
         String valueType = propElem.getAttribute(TYPE_ATTRNAME);
         String value = AeXmlUtil.getText(propElem);
         Class c = Class.forName(valueType);
         Constructor constructor = c.getConstructor( new Class[] { String.class } );
         map.put(name, constructor.newInstance(new Object[] { value }));
      }

      return map;
   }
   
   /**
    * Gets the hash code of a QName.
    * 
    * NOTE: Copied from the queue storage class found in version 1.0.8.3 of ActiveBPEL.
    * 
    * @param aQName
    */
   protected int getQNameHashCode(QName aQName)
   {
      return ("{" + aQName.getNamespaceURI() + "}" + aQName.getLocalPart()).hashCode(); //$NON-NLS-1$ //$NON-NLS-2$
   }

   /**
    * This method returns a Hash value that gets stored in the queued receive 
    * table.  This hash value is used to select potentially matching receives.
    * Note that this hash value does not guarantee equality, but is a good 
    * starting point for quickly selecting receives that might match.  A final
    * greedy comparison is needed to fully determine equality.  In addition, 
    * this hash value is only useful for finding receives with the same 
    * correlation data.  The combination of this hash value and the "matches"
    * hash value can be used to find a list of matching AND correlating receives.
    * 
    * NOTE: Copied from the queue storage class found in version 1.0.8.3 of ActiveBPEL.
    * 
    * @param aCorrelationMap The correlation map for the receive.
    * @return A hash value.
    */
   protected int getReceiveCorrelatesHash(Map aCorrelationMap)
   {
      int hash = 0;
      int count = 1;
      // Sort the keys - this ensures that the correlation set hash is always calculated the
      // same, even if the data is in the map in a different order.
      SortedSet ss = new TreeSet(new Comparator()
      {
         public int compare(Object o1, Object o2)
         {
            String str1 = o1.toString();
            String str2 = o2.toString();
            return str1.compareTo(str2);
         }

         public boolean equals(Object obj)
         {
            return false;
         }
      });
      ss.addAll(aCorrelationMap.keySet());
      for (Iterator iter = ss.iterator(); iter.hasNext(); count++)
      {
         QName key = (QName) iter.next();
         Object val = aCorrelationMap.get(key);
         hash += getQNameHashCode(key) + val.hashCode() * count++;
      }
      return hash;
   }

   /**
    * This method returns a Hash value that gets stored in the queued receive 
    * table.  This hash value is used to select potentially matching receives.
    * Note that this hash value does not guarantee equality, but is a good 
    * starting point for quickly selecting receives that might match.  A final
    * greedy comparison is needed to fully determine equality.
    * 
    * NOTE: Copied from the queue storage class found in version 1.0.8.3 of ActiveBPEL.
    * 
    * @param aMessageReceiverPathId The message receiver path id.
    * @param aOperation The receive's operation.
    * @param aPartnerLinkName The receive's partner link name.
    * @param aPortType The receive's port type.
    * @return A hash value.
    */
   protected int getReceiveMatchHash(String aOperation, String aPartnerLinkName, QName aPortType)
   {
      int hash = 0;
      hash += aOperation.hashCode();
      hash += aPartnerLinkName.hashCode();
      hash += getQNameHashCode(aPortType);
      return hash;
   }

   /**
    * Implements a result set handler that returns a list of alarm info objects from data in the alarm
    * table.
    */
   private class AeQueuedReceiveListResultSetHandler implements ResultSetHandler
   {
      /**
       * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
       */
      public Object handle(ResultSet aResult) throws SQLException
      {
         List rval = new ArrayList();
         while (aResult.next())
         {
            try
            {
               int queuedReceiveId = aResult.getInt("QueuedReceiveId"); //$NON-NLS-1$
               String operation = aResult.getString("Operation"); //$NON-NLS-1$
               String plinkName = aResult.getString("PartnerLinkName"); //$NON-NLS-1$
               String portTypeNamespace = aResult.getString("PortTypeNamespace"); //$NON-NLS-1$
               String portTypeLocalPart = aResult.getString("PortTypeLocalPart"); //$NON-NLS-1$
               QName portType = new QName(portTypeNamespace, portTypeLocalPart);
               int matchHash = getReceiveMatchHash(operation, plinkName, portType);

               Reader corrPropsReader = aResult.getClob("CorrelationProperties").getCharacterStream(); //$NON-NLS-1$
               Map corrProps = deserializeCorrelationProperties(corrPropsReader);
               int corrHash = getReceiveCorrelatesHash(corrProps);

               rval.add(new AeQueuedReceive1_0_8_3(queuedReceiveId, matchHash, corrHash));
            }
            catch (Exception e)
            {
               throw new SQLException(e.getLocalizedMessage());
            }
         }
         return rval;
      }
   }

}
