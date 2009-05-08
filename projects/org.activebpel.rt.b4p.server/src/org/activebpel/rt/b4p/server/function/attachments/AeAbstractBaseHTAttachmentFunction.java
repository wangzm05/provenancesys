//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/attachments/AeAbstractBaseHTAttachmentFunction.java,v 1.1 2008/02/11 17:09:24 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function.attachments;

import java.util.Iterator;

import org.activebpel.rt.attachment.AeAttachmentContainer;
import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.attachment.IAeAttachmentItem;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.function.attachment.AeAbstractAttachmentFunction;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.wsio.IAeWebServiceAttachment;

/**
 * Base class with common functionality for copy and remove ht attachment functions 
 */
public abstract class AeAbstractBaseHTAttachmentFunction extends AeAbstractAttachmentFunction
{

   /**
    * @param aFunctionName
    */
   public AeAbstractBaseHTAttachmentFunction(String aFunctionName)
   {
      super(aFunctionName);
   }

   /**
    * Returns an attachment container with the attchment item matching attachment id.
    * @param aBpelObject
    * @param aVariableName
    * @param aAttachmentId
    */
   protected IAeAttachmentContainer getAttachmentsById(AeAbstractBpelObject aBpelObject, String aVariableName, long aAttachmentId) 
   {
      IAeVariable variable = getVariable(aBpelObject, aVariableName);
      IAeAttachmentContainer attachments = new AeAttachmentContainer();
      for (Iterator iter = variable.getAttachmentData().iterator(); iter.hasNext();)
      {
         IAeAttachmentItem attachment = (IAeAttachmentItem)iter.next();
         long attachmentIdInAttachment = attachment.getAttachmentId();
         if ( attachmentIdInAttachment == aAttachmentId)
         {
            attachments.add(attachment);
            break;
         }
      }
      return attachments;
   }

   /**
    * Returns an attachment container with all the attchment items matching attachment name.
    * If attachment name is null then attachment container associated with from variable is returned. 
    * @param aBpelObject
    * @param aVariableName
    * @param aAttachmentName
    */
   protected IAeAttachmentContainer getAttachmentsByName(AeAbstractBpelObject aBpelObject, String aVariableName, String aAttachmentName)
   {
      IAeVariable variable = getVariable(aBpelObject, aVariableName);
      IAeAttachmentContainer attachments = new AeAttachmentContainer();
      if (aAttachmentName == null)
      {
         attachments = variable.getAttachmentData();
      }
      else
      {
         for (Iterator iter = variable.getAttachmentData().iterator(); iter.hasNext();)
         {
            IAeAttachmentItem attachment = (IAeAttachmentItem)iter.next();
            String nameInAttachment = attachment.getHeader(IAeWebServiceAttachment.AE_CONTENT_LOCATION_MIME);
            if ( AeUtil.notNullOrEmpty(nameInAttachment) && nameInAttachment.equals(aAttachmentName) )
               attachments.add(attachment);
         }
      }
      return attachments;
   }
   
   /**
    * Returns attachment container of the variable for given variable name 
    * @param aBpelObject
    * @param aVariableName
    */
   protected IAeAttachmentContainer getAttachmentContainer(AeAbstractBpelObject aBpelObject, String aVariableName)
   {
      IAeVariable variable = getVariable(aBpelObject, aVariableName);
      return variable.getAttachmentData();
   }
}
