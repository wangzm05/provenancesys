//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeReferenceOverrideElementsDef.java,v 1.5 2007/12/18 04:06:29 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def;

import java.util.HashSet;
import java.util.Set;

import org.activebpel.rt.ht.def.AePeopleAssignmentsDef;
import org.activebpel.rt.ht.def.AePriorityDef;
import org.activebpel.rt.ht.def.IAePeopleAssignmentsDefParent;
import org.activebpel.rt.ht.def.IAePriorityDefParent;
import org.activebpel.rt.ht.def.IAeVariableUsageContainer;

/**
 * Implementation of the overridable elements  
 */
public abstract class AeReferenceOverrideElementsDef extends AeB4PBaseDef implements IAeVariableUsageContainer,IAePeopleAssignmentsDefParent, IAePriorityDefParent
{
   /** 'priority' element */
   private AePriorityDef mPriority;

   /** 'peopleAssigments' element */
   private AePeopleAssignmentsDef mPeopleAssignments;
   
   /** map of used variable location paths */
   private Set mUsedVariables;

   /**
    * @see org.activebpel.rt.ht.def.IAePeopleAssignmentsDefParent#getPeopleAssignments()
    */
   public AePeopleAssignmentsDef getPeopleAssignments()
   {
      return mPeopleAssignments;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAePeopleAssignmentsDefParent#setPeopleAssignments(org.activebpel.rt.ht.def.AePeopleAssignmentsDef)
    */
   public void setPeopleAssignments(AePeopleAssignmentsDef aPeopleAssignments)
   {
      mPeopleAssignments = aPeopleAssignments;
      assignParent(aPeopleAssignments);
   }

   /**
    * @see org.activebpel.rt.ht.def.IAePriorityDefParent#getPriority()
    */
   public AePriorityDef getPriority()
   {
      return mPriority;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAePriorityDefParent#setPriority(org.activebpel.rt.ht.def.AePriorityDef)
    */
   public void setPriority(AePriorityDef aPriority)
   {
      mPriority = aPriority;
      assignParent(aPriority);
   }

   /**
    * @return the usedVariables
    */
   public Set getUsedVariables()
   {
      if ( mUsedVariables == null )
      {
         mUsedVariables = new HashSet();
      }
      return mUsedVariables;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeVariableUsageContainer#addUsedVariable(java.lang.String)
    */
   public void addUsedVariable(String aLocationPath)
   {
      getUsedVariables().add(aLocationPath);
   }

   /**
    * @see org.activebpel.rt.xml.def.AeBaseXmlDef#clone()
    */
   public Object clone()
   {
      AeReferenceOverrideElementsDef copy = (AeReferenceOverrideElementsDef) super.clone();
      
      if (getPriority() != null)
         copy.setPriority((AePriorityDef) getPriority().clone());
      
      if (getPeopleAssignments() != null)
         copy.setPeopleAssignments((AePeopleAssignmentsDef) getPeopleAssignments().clone());
      
      return copy;
   }
   
   
}
