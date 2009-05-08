//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/task/data/AeInitialState.java,v 1.4.4.3 2008/04/28 21:49:51 vvelusamy Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.task.data;

import java.util.Set;

import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;


/**
 * Models Initial state set on the process request message
 */
public class AeInitialState
{
   /** Input def */
   private IAeMessageData mInput;
   /** Name of user that created the task */
   private String mCreatedBy;
   /** Human task context */
   private AeHumanTaskContext mHumanTaskContext;
   /** start by */
   private AeSchemaDateTime mStartBy;
   /** complete by */
   private AeSchemaDateTime mCompleteBy;
   /** process variables */
   private Set mProcessVariables;
   /**
    * @return the inputDef
    */
   public IAeMessageData getInput()
   {
      return mInput;
   }
   /**
    * @param aInputDef the inputDef to set
    */
   public void setInput(IAeMessageData aInputDef)
   {
      mInput = aInputDef;
   }
   /**
    * @return the createdBy
    */
   public String getCreatedBy()
   {
      return mCreatedBy;
   }
   /**
    * @param aCreatedBy the createdBy to set
    */
   public void setCreatedBy(String aCreatedBy)
   {
      mCreatedBy = aCreatedBy;
   }
   /**
    * @return the humanTaskContext
    */
   public AeHumanTaskContext getHumanTaskContext()
   {
      return mHumanTaskContext;
   }
   /**
    * @param aHumanTaskContext the humanTaskContext to set
    */
   public void setHumanTaskContext(AeHumanTaskContext aHumanTaskContext)
   {
      mHumanTaskContext = aHumanTaskContext;
   }
   /**
    * @return the processVariables
    */
   public Set getProcessVariables()
   {
      return mProcessVariables;
   }
   /**
    * @param aProcessVariables the processVariables to set
    */
   public void setProcessVariables(Set aProcessVariables)
   {
      mProcessVariables = aProcessVariables;
   }
   
   /**
    * @return the startBy
    */
   public AeSchemaDateTime getStartBy()
   {
      return mStartBy;
   }
   /**
    * @param aStartBy the startBy to set
    */
   public void setStartBy(AeSchemaDateTime aStartBy)
   {
      mStartBy = aStartBy;
   }
   /**
    * @return the completeBy
    */
   public AeSchemaDateTime getCompleteBy()
   {
      return mCompleteBy;
   }
   /**
    * @param aCompleteBy the completeBy to set
    */
   public void setCompleteBy(AeSchemaDateTime aCompleteBy)
   {
      mCompleteBy = aCompleteBy;
   }
}
