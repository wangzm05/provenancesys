//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/IAeHtFunctionContext.java,v 1.3.4.2 2008/04/14 21:26:06 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht;

import org.w3c.dom.Element;

/**
 * Interface for supporting ht extension functions.
 * 
 * The methods below that accept a task name can either accept the name of the current
 * task, null (in which the current task is assumed), or the name of the parent
 * task in which case we're dealing with an expression within an escalation
 * notification.
 */
public interface IAeHtFunctionContext
{
   /**
    * Returns potential owners 
    * @param aTaskName name of the task. (see note in class javadoc)  
    */
   public Element getPotentialOwners(String aTaskName);

   /**
    * Returns actual owner 
    * @param aTaskName name of the task. (see note in class javadoc)  
    */
   public Element getActualOwner(String aTaskName);
   
   /**
    * Returns task initiator
    * @param aTaskName name of the task. (see note in class javadoc)  
    */
   public Element getTaskInitiator(String aTaskName);

   /**
    * Returns task stakeholders
    * @param aTaskName name of the task. (see note in class javadoc)  
    */
   public Element getTaskStakeholders(String aTaskName);

   /**
    * Returns Excluded owners
    * @param aTaskName name of the task. (see note in class javadoc)  
    */
   public Element getExcludedOwners(String aTaskName);

   /**
    * Returns Task Priority
    * @param aTaskName name of the task. (see note in class javadoc)  
    */
   public int getTaskPriority(String aTaskName);


   /**
    * Returns Input message for the task's part
    * @param aPartName
    * @param aTaskName name of the task. (see note in class javadoc)  
    */
   public Object getInput(String aPartName, String aTaskName);
   
   /**
    * Returns business admins
    * @param aTaskName name of the task. (see note in class javadoc)  
    */
   public Element getBusinessAdministrators(String aTaskName);
}
