//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/lpg/AeLiteralLogicalPeopleGroupProvider.java,v 1.2 2008/02/01 22:41:37 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.lpg; 

import org.activebpel.rt.AeException;
import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.util.AeXPathUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Uses an organizationalEntity in the LPG element in order to initialize the LPG.
 */
public class AeLiteralLogicalPeopleGroupProvider implements IAeLogicalPeopleGroupProvider
{
   /** xpath query to select the orgEntity from the LPG element */
   private static final String sQueryPattern = "htd:organizationalEntity"; //$NON-NLS-1$

   /**
    * @see org.activebpel.rt.b4p.impl.lpg.IAeLogicalPeopleGroupProvider#evaluate(org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef, org.w3c.dom.Element)
    */
   public Element evaluate(AeLogicalPeopleGroupDef aDef, Element aLPGElement)
   {
      Element result = null;
      try
      {
         result = (Element) AeXPathUtil.selectSingleNode(aLPGElement, sQueryPattern,
               IAeHtDefConstants.DEFAULT_HT_PREFIX, IAeHtDefConstants.DEFAULT_HT_NS);
      }
      catch (AeException e)
      {
         // exceptions are logged but don't fail the process
         AeException.logError(e);
      }

      if (result == null)
      {
         Document doc = AeXmlUtil.newDocument();
         result = doc.createElementNS(IAeHtDefConstants.DEFAULT_HT_NS, "htd:organizationalEntity");  //$NON-NLS-1$
         result.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:htd", IAeHtDefConstants.DEFAULT_HT_NS); //$NON-NLS-1$
      }
      
      return result;
   }
}
 