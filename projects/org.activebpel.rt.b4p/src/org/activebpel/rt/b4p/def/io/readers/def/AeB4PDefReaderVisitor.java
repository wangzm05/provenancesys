//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/io/readers/def/AeB4PDefReaderVisitor.java,v 1.7 2007/12/26 17:34:03 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.io.readers.def;

import org.activebpel.rt.b4p.def.AeAttachmentPropagationDef;
import org.activebpel.rt.b4p.def.AeB4PForDef;
import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef;
import org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef;
import org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef;
import org.activebpel.rt.b4p.def.AeB4PUntilDef;
import org.activebpel.rt.b4p.def.AeBusinessAdministratorsDef;
import org.activebpel.rt.b4p.def.AeDeferActivationDef;
import org.activebpel.rt.b4p.def.AeExpirationDef;
import org.activebpel.rt.b4p.def.AeLocalTaskDef;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.b4p.def.AeProcessInitiatorDef;
import org.activebpel.rt.b4p.def.AeProcessStakeholdersDef;
import org.activebpel.rt.b4p.def.AeScheduledActionsDef;
import org.activebpel.rt.b4p.def.IAeB4PAlarmDef;
import org.activebpel.rt.b4p.def.IAeB4PDefConstants;
import org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor;
import org.activebpel.rt.ht.def.io.readers.def.AeHtDefReaderVisitor;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.w3c.dom.Element;

/**
 * Implementation of the reader visitor logic for BPEL4People
 */
public class AeB4PDefReaderVisitor extends AeHtDefReaderVisitor implements IAeB4PDefVisitor, IAeB4PDefConstants
{
   /**
    * Constructor
    * @param aParentDef child will be added to this
    * @param aElement current element to read from
    */
   public AeB4PDefReaderVisitor(AeBaseXmlDef aParentDef, Element aElement)
   {
      super(aParentDef, aElement);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeHumanInteractionsDef)
    */
   public void visit(AeB4PHumanInteractionsDef aDef)
   {
      readAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeHumanInteractionsDef)
    */
   public void visit(AePeopleActivityDef aDef)
   {
      readAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor#visit(org.activebpel.rt.ht.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      readAttributes(aDef);
      ((AeB4PPeopleAssignmentsDef)getParentXmlDef()).setBusinessAdministrators(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeProcessInitiatorDef)
    */
   public void visit(AeProcessInitiatorDef aDef)
   {
      readAttributes(aDef);
      ((AeB4PPeopleAssignmentsDef)getParentXmlDef()).setProcessInitiator(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeProcessStakeholdersDef)
    */
   public void visit(AeProcessStakeholdersDef aDef)
   {
      readAttributes(aDef);
      ((AeB4PPeopleAssignmentsDef)getParentXmlDef()).setProcessStakeholders(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef)
    */
   public void visit(AeB4PPeopleAssignmentsDef aDef)
   {
      readAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeLocalTaskDef)
    */
   public void visit(AeLocalTaskDef aDef)
   {
      readAttributes(aDef);
      aDef.setReference(getAttributeQName(ATTR_REFERENCE));
      ((AePeopleActivityDef)getParentXmlDef()).setLocalTask(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeLocalTaskDef)
    */
   public void visit(AeB4PLocalNotificationDef aDef)
   {
      readAttributes(aDef);
      aDef.setReference(getAttributeQName(ATTR_REFERENCE));
      ((AePeopleActivityDef)getParentXmlDef()).setLocalNotification(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeScheduledActionsDef)
    */
   public void visit(AeScheduledActionsDef aDef)
   {
      readAttributes(aDef);
      ((AePeopleActivityDef)getParentXmlDef()).setScheduledActions(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeAttachmentPropagationDef)
    */
   public void visit(AeAttachmentPropagationDef aDef)
   {
      readAttributes(aDef);
      aDef.setFromProcess(getAttribute(TAG_FROM_PROCESS));
      aDef.setToProcess(getAttribute(TAG_TO_PROCESS));
      ((AePeopleActivityDef)getParentXmlDef()).setAttachmentPropagation(aDef);
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeDeferActivationDef)
    */
   public void visit(AeDeferActivationDef aDef)
   {
      readAttributes(aDef);
      ((AeScheduledActionsDef)getParentXmlDef()).setDeferActivation(aDef);
   }
   
  /**
   * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeExpirationDef)
   */
   public void visit(AeExpirationDef aDef)
   {
      readAttributes(aDef);
      ((AeScheduledActionsDef)getParentXmlDef()).setExpiration(aDef);
   }
   
   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PForDef)
    */
    public void visit(AeB4PForDef aDef)
    {
       readAttributes(aDef);
       aDef.setExpressionLanguage(getAttribute(ATTR_EXPRESSION_LANGUAGE));
       aDef.setExpression(AeXmlUtil.getText(getCurrentElement()));
       ((IAeB4PAlarmDef)getParentXmlDef()).setForDef(aDef);
    }
    
    /**
     * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PUntilDef)
     */
     public void visit(AeB4PUntilDef aDef)
     {
        readAttributes(aDef);
        aDef.setExpressionLanguage(getAttribute(ATTR_EXPRESSION_LANGUAGE));
        aDef.setExpression(AeXmlUtil.getText(getCurrentElement()));
        ((IAeB4PAlarmDef)getParentXmlDef()).setUntilDef(aDef);
     }
}
