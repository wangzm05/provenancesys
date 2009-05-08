//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/task/data/AeHumanTaskContext.java,v 1.4.4.1 2008/04/28 21:49:51 vvelusamy Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.task.data;

import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;
import org.w3c.dom.Element;

/**
 * Models humanTaskContext construct in human task protocol spec 
 */
public class AeHumanTaskContext
{
   /** Task Priority */
   private int mPriority;
   /** people assignments for the task */
   private AePeopleAssignmentsDef mPeopleAssignments;
   /** skippable indicator of task */
   private boolean mSkippable;
   /** attachments */
   private Element mAttachments;
   /** attachment propgation to value */
   private String mTo;
   /** task expiration time */
   private AeSchemaDateTime mExpiration;
   /** task expiration time */
   private AeSchemaDateTime mDeferActivationTime;
   
   /**
    * @return the priority
    */
   public int getPriority()
   {
      return mPriority;
   }
   /**
    * @param aPriority the priority to set
    */
   public void setPriority(int aPriority)
   {
      mPriority = aPriority;
   }
   /**
    * @return the peopleAssignments
    */
   public AePeopleAssignmentsDef getPeopleAssignments()
   {
      return mPeopleAssignments;
   }
   /**
    * @param aPeopleAssignments to set
    */
   public void setPeopleAssignments(AePeopleAssignmentsDef aPeopleAssignments)
   {
      mPeopleAssignments = aPeopleAssignments;
   }
   /**
    * @return the skippable
    */
   public boolean isSkippable()
   {
      return mSkippable;
   }
   /**
    * @param aSkippable the skippable to set
    */
   public void setSkippable(boolean aSkippable)
   {
      mSkippable = aSkippable;
   }
   /**
    * @return the expiration
    */
   public AeSchemaDateTime getExpiration()
   {
      return mExpiration;
   }
   /**
    * @param aExpiration the expiration to set
    */
   public void setExpiration(AeSchemaDateTime aExpiration)
   {
      mExpiration = aExpiration;
   }
   /**
    * @return the attachments
    */
   public Element getAttachments()
   {
      return mAttachments;
   }
   /**
    * @param aAttachments the attachments to set
    */
   public void setAttachments(Element aAttachments)
   {
      mAttachments = aAttachments;
   }
   /**
    * @return the to
    */
   public String getTo()
   {
      return mTo;
   }
   /**
    * @param aTo the to to set
    */
   public void setTo(String aTo)
   {
      mTo = aTo;
   }
   /**
    * @return the deferActivationTime
    */
   public AeSchemaDateTime getDeferActivationTime()
   {
      return mDeferActivationTime;
   }
   /**
    * @param aDeferActivationTime the deferActivationTime to set
    */
   public void setDeferActivationTime(AeSchemaDateTime aDeferActivationTime)
   {
      mDeferActivationTime = aDeferActivationTime;
   }
}
