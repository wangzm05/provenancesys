// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AePeopleActivityDefRule61Validator.java,v 1.3 2008/02/26 15:20:53 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.ht.def.IAeHtDefConstants;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeExtensionElementDef;

/**
 * task data is one of task, notification, local task, local notification
 * and all should be mutually exclusive with each other
 */
public class AePeopleActivityDefRule61Validator extends AeAbstractB4PValidator
{
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AePeopleActivityDef)
    */
   public void visit(AePeopleActivityDef aDef)
   {
      executeRule(aDef);
      checkExtensionElements(aDef);
      super.visit(aDef);
   }

   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AePeopleActivityDef aDef)
   {
      int taskCount = 0;
      taskCount += aDef.getLocalNotification() != null ? 1 : 0;
      taskCount += aDef.getNotification() != null ? 1 : 0;
      taskCount += aDef.getTask() != null ? 1 : 0;
      taskCount += aDef.getLocalTask() != null ? 1 : 0;
      
      if (taskCount == 0)
      {
         reportProblem(AeMessages.getString("AePeopleActivityDefRule61Validator.0"), aDef); //$NON-NLS-1$
      } else if (taskCount > 1)
      {
         reportProblem(AeMessages.getString("AePeopleActivityDefRule61Validator.1"), aDef); //$NON-NLS-1$
      }
      else
      {
         // NO PROBLEM, everything is fine.
      }
   }
   
   /**
    * Check the extension elements for duplicate task/notification entries
    * @param aDef
    */
   protected void checkExtensionElements(AePeopleActivityDef aDef)
   {
      AeBaseXmlDef defToCheck = aDef;
      if (aDef.getParentXmlDef() != null)
      {
         defToCheck = aDef.getParentXmlDef();
      }
      
      Set qNameList = new HashSet();
      for (Iterator extIter = defToCheck.getExtensionElementDefs().iterator(); extIter.hasNext();)
      {
         AeExtensionElementDef def = (AeExtensionElementDef) extIter.next();
         String namespace = def.getElementQName().getNamespaceURI();
         
         //only continue for elements in the HT or B4P namespace
         if (AeUtil.compareObjects(IAeHtDefConstants.DEFAULT_HT_NS, namespace) ||
             AeUtil.compareObjects(IAeB4PConstants.B4P_NAMESPACE, namespace ))
         {
            if (qNameList.contains(def.getElementQName()))
            {
               reportProblem(AeMessages.getString("AePeopleActivityDefRule61Validator.1"), aDef); //$NON-NLS-1$
            }
            else
            {
               qNameList.add(def.getElementQName());
            }
         }
      }
   }
   
}
