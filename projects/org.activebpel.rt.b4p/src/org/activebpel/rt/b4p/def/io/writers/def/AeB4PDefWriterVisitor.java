//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/io/writers/def/AeB4PDefWriterVisitor.java,v 1.8 2008/02/17 21:36:03 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.io.writers.def;

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
import org.activebpel.rt.b4p.def.IAeB4PDefConstants;
import org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor;
import org.activebpel.rt.ht.def.IAeHtExpressionDef;
import org.activebpel.rt.ht.def.io.writers.def.AeHtDefWriterVisitor;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * Impl. of the def visitor that serializes the BPEL4 def model to a DOM. People
 */
public class AeB4PDefWriterVisitor extends AeHtDefWriterVisitor implements IAeB4PDefVisitor, IAeB4PDefConstants
{

   /**
    * Constructor for wsht writer visitor
    * @param aDef
    * @param aParentElement
    * @param aNamespace
    * @param aTagName
    */
   public AeB4PDefWriterVisitor(AeBaseXmlDef aDef, Element aParentElement, String aNamespace, String aTagName)
   {
      super(aDef, aParentElement, aNamespace, aTagName);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef)
    */
   public void visit(AeB4PHumanInteractionsDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AePeopleActivityDef)
    */
   public void visit(AePeopleActivityDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.ht.def.io.writers.def.AeHtDefWriterVisitor#visit(org.activebpel.rt.ht.def.AeBusinessAdministratorsDef)
    */
   public void visit(AeBusinessAdministratorsDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeProcessInitiatorDef)
    */
   public void visit(AeProcessInitiatorDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeProcessStakeholdersDef)
    */
   public void visit(AeProcessStakeholdersDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef)
    */
   public void visit(AeB4PPeopleAssignmentsDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeLocalTaskDef)
    */
   public void visit(AeLocalTaskDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(ATTR_REFERENCE, aDef.getReference());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PLocalNotificationDef)
    */
   public void visit(AeB4PLocalNotificationDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(ATTR_REFERENCE, aDef.getReference());
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeScheduledActionsDef)
    */
   public void visit(AeScheduledActionsDef aDef)
   {
       writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeDeferActivationDef)
    */
   public void visit(AeDeferActivationDef aDef)
   {
       writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeExpirationDef)
    */
   public void visit(AeExpirationDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PForDef)
    */
   public void visit(AeB4PForDef aDef)
   {
      writeStandardAttributes(aDef);
      writeExpression(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PUntilDef)
    */
   public void visit(AeB4PUntilDef aDef)
   {
      writeStandardAttributes(aDef);
      writeExpression(aDef);
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeScheduledActionsDef)
    */
   public void visit(AeAttachmentPropagationDef aDef)
   {
      writeStandardAttributes(aDef);
      setAttribute(TAG_FROM_PROCESS, aDef.getFromProcess());
      setAttribute(TAG_TO_PROCESS, aDef.getToProcess());
   }

   /**
    * Convenience method to write a B4P expression.
    * @param aDef
    * @throws DOMException
    */
   protected void writeExpression(IAeHtExpressionDef aDef) throws DOMException
   {
      setAttribute(ATTR_EXPRESSION_LANGUAGE, aDef.getExpressionLanguage());
      if ( AeUtil.notNullOrEmpty(aDef.getExpression()) )
      {
         Text textNode = getElement().getOwnerDocument().createTextNode(aDef.getExpression());
         getElement().appendChild(textNode);
      }
   }
}
