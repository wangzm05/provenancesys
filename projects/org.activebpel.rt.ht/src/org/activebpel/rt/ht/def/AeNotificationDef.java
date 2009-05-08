//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeNotificationDef.java,v 1.10 2008/01/29 21:28:11 JPerrotto Exp $$
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
 * Impl for the 'notification' element Def
 */
public class AeNotificationDef extends AeHtBaseDef implements IAePriorityDefParent, IAePeopleAssignmentsDefParent, IAePresentationElementsDefParent,
      IAeRenderingsDefParent, IAeVariableUsageContainer, IAeNamedDef, IAeInterfaceDefParent
{
   /** 'interface' element value */
   private AeNotificationInterfaceDef mInterface;
   /** 'priority' element value */
   private AePriorityDef mPriority;
   /** 'peopleAssigments' element value */
   private AePeopleAssignmentsDef mPeopleAssignments;
   /** presentationElements' element value */
   private AePresentationElementsDef mPresentationElements;
   /** 'renderings' element value */
   private AeRenderingsDef mRenderings;
   /** 'name' attribute value */
   private String mName = AeMessages.getString("AeNotificationDef.DefaultName"); //$NON-NLS-1$
   /** map of used variable location paths */
   private Set mUsedVariables;

   /**
    * @return the interface
    */
   public AeNotificationInterfaceDef getNotificationInterfaceDef()
   {
      return mInterface;
   }

   /**
    * @param aInterface the interface to set
    */
   public void setInterface(AeNotificationInterfaceDef aInterface)
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
      AeNotificationDef def = (AeNotificationDef)super.clone();
      if (getNotificationInterfaceDef() != null)
         def.setInterface((AeNotificationInterfaceDef)getNotificationInterfaceDef().clone());
      if (getPriority() != null)
         def.setPriority((AePriorityDef)getPriority().clone());
      if (getPeopleAssignments() != null)
         def.setPeopleAssignments((AePeopleAssignmentsDef)getPeopleAssignments().clone());
      if (getPresentationElements() != null)
         def.setPresentationElements((AePresentationElementsDef)getPresentationElements().clone());
      if (getRenderings() != null)
         def.setRenderings((AeRenderingsDef)getRenderings().clone());
      
      return def;
   }
   
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeNotificationDef))
         return false;
      
      AeNotificationDef otherDef = (AeNotificationDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.getName(), getName());
      same &= AeUtil.compareObjects(otherDef.getNotificationInterfaceDef(), getNotificationInterfaceDef());
      same &= AeUtil.compareObjects(otherDef.getPriority(), getPriority());
      same &= AeUtil.compareObjects(otherDef.getPeopleAssignments(), getPeopleAssignments());
      same &= AeUtil.compareObjects(otherDef.getPresentationElements(), getPresentationElements());
      same &= AeUtil.compareObjects(otherDef.getRenderings(), getRenderings());
      
      return same;
   }

   /**
    * @see org.activebpel.rt.ht.def.IAeInterfaceDefParent#getInterfaceDef()
    */
   public IAeInterfaceDef getInterfaceDef()
   {
      return mInterface;
   }
}