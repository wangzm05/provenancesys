//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeTaskDef.java,v 1.15 2008/02/19 23:46:17 mford Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import java.util.HashSet;
import java.util.Set;

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.IAeNamedDef;

/**
 * Impl. for 'task' element Def.
 */
public class AeTaskDef extends AeHtBaseDef implements IAePriorityDefParent, IAePeopleAssignmentsDefParent, IAePresentationElementsDefParent,
      IAeVariableUsageContainer, IAeRenderingsDefParent, IAeNamedDef, IAeInterfaceDefParent
{
   /** 'interface' element value */
   private AeTaskInterfaceDef mInterface;
   /** 'priority' element value */
   private AePriorityDef mPriority;
   /** 'peopleAssigments' element value */
   private AePeopleAssignmentsDef mPeopleAssignments;
   /** 'delegation' element value */
   private AeDelegationDef mDelegation;
   /** 'presentationElements' element value */
   private AePresentationElementsDef mPresentationElements;
   /** 'outcome' element value */
   private AeQueryDef mOutcome;
   /** 'searchBy' element value */
   private AeSearchByDef mSearchBy;
   /** 'renderings' element value */
   private AeRenderingsDef mRenderings;
   /** 'deadlines' element value */
   private AeDeadlinesDef mDeadlines;
   /** 'name' attribute value */
   private String mName = AeMessages.getString("AeTaskDef.DefaultName"); //$NON-NLS-1$
   /** map of used variable location paths and the associated variable Def object. */
   private Set mUsedVariables;

   /**
    * @return the deadlines
    */
   public AeDeadlinesDef getDeadlines()
   {
      return mDeadlines;
   }

   /**
    * @param aDeadlines the deadlines to set
    */
   public void setDeadlines(AeDeadlinesDef aDeadlines)
   {
      mDeadlines = aDeadlines;
      assignParent(aDeadlines);
   }

   /**
    * @return the delegation
    */
   public AeDelegationDef getDelegation()
   {
      return mDelegation;
   }

   /**
    * @param aDelegation the delegation to set
    */
   public void setDelegation(AeDelegationDef aDelegation)
   {
      mDelegation = aDelegation;
      assignParent(aDelegation);
   }

   /**
    * @return the interface def
    */
   public IAeInterfaceDef getInterfaceDef()
   {
      return mInterface;
   }
   
   /**
    * @return the task interface def
    */
   public AeTaskInterfaceDef getTaskInterfaceDef()
   {
      return mInterface;
   }

   /**
    * @param aInterface the interface to set
    */
   public void setInterface(AeTaskInterfaceDef aInterface)
   {
      mInterface = aInterface;
      assignParent(aInterface);
   }

   /**
    * @see org.activebpel.rt.xml.def.IAeNamedDef#getName()
    */
   public String getName()
   {
      return mName;
   }

   /**
    * @param aName the name to set
    */
   public void setName(String aName)
   {
      mName = aName;
   }

   /**
    * @return the outcome
    */
   public AeQueryDef getOutcome()
   {
      return mOutcome;
   }

   /**
    * @param aOutcome the outcome to set
    */
   public void setOutcome(AeQueryDef aOutcome)
   {
      mOutcome = aOutcome;
      assignParent(aOutcome);
   }

   /**
    * @return the peopleAssignments
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
    * @return the presentationElements
    */
   public AePresentationElementsDef getPresentationElements()
   {
      return mPresentationElements;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAePresentationElementsDefParent#setPresentationElements(org.activebpel.rt.ht.def.AePresentationElementsDef)
    */
   public void setPresentationElements(AePresentationElementsDef aPresentationElements)
   {
      mPresentationElements = aPresentationElements;
      assignParent(aPresentationElements);
   }

   /**
    * @return the priority
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
    * @return the renderings
    */
   public AeRenderingsDef getRenderings()
   {
      return mRenderings;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeRenderingsDefParent#setRenderings(org.activebpel.rt.ht.def.AeRenderingsDef)
    */
   public void setRenderings(AeRenderingsDef aRenderings)
   {
      mRenderings = aRenderings;
      assignParent(aRenderings);
   }

   /**
    * @return the searchBy
    */
   public AeSearchByDef getSearchBy()
   {
      return mSearchBy;
   }

   /**
    * @param aSearchBy the searchBy to set
    */
   public void setSearchBy(AeSearchByDef aSearchBy)
   {
      mSearchBy = aSearchBy;
      assignParent(aSearchBy);
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeVariableUsageContainer#getUsedVariables()
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
    * @see org.activebpel.rt.ht.def.AeHtBaseDef#accept(org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor)
    */
   public void accept(IAeHtDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      AeTaskDef def = (AeTaskDef)super.clone();
      if (getTaskInterfaceDef() != null)
         def.setInterface((AeTaskInterfaceDef)getTaskInterfaceDef().clone());
      if (getPriority() != null)
         def.setPriority((AePriorityDef)getPriority().clone());
      if (getPeopleAssignments() != null)
         def.setPeopleAssignments((AePeopleAssignmentsDef)getPeopleAssignments().clone());
      if (getDelegation() != null)
         def.setDelegation((AeDelegationDef)getDelegation().clone());
      if (getPresentationElements() != null)
         def.setPresentationElements((AePresentationElementsDef)getPresentationElements().clone());
      if (getOutcome() != null)
         def.setOutcome((AeQueryDef)getOutcome().clone());
      if (getSearchBy() != null)
         def.setSearchBy((AeSearchByDef) getSearchBy().clone());
      if (getRenderings() != null)
         def.setRenderings((AeRenderingsDef)getRenderings().clone());
      if (getDeadlines() != null)
         def.setDeadlines((AeDeadlinesDef)getDeadlines().clone());

      if (AeUtil.notNullOrEmpty(mUsedVariables))
         def.mUsedVariables = new HashSet(mUsedVariables);
      return def;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeTaskDef))
         return false;

      AeTaskDef otherDef = (AeTaskDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.getTaskInterfaceDef(), getTaskInterfaceDef());
      same &= AeUtil.compareObjects(otherDef.getPriority(), getPriority());
      same &= AeUtil.compareObjects(otherDef.getPeopleAssignments(), getPeopleAssignments());
      same &= AeUtil.compareObjects(otherDef.getDelegation(), getDelegation());
      same &= AeUtil.compareObjects(otherDef.getPresentationElements(), getPresentationElements());
      same &= AeUtil.compareObjects(otherDef.getOutcome(), getOutcome());
      same &= AeUtil.compareObjects(otherDef.getSearchBy(), getSearchBy());
      same &= AeUtil.compareObjects(otherDef.getRenderings(), getRenderings());
      same &= AeUtil.compareObjects(otherDef.getDeadlines(), getDeadlines());
      same &= AeUtil.compareObjects(otherDef.getName(), getName());

      return same;
   }
}