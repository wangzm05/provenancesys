//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeLocalNotificationDef.java,v 1.7 2007/12/18 04:04:34 mford Exp $$
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

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeCloneUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * impl. for the 'localNotification' element Def.
 */
public class AeLocalNotificationDef extends AeHtBaseDef implements IAeVariableUsageContainer, IAeLocalNotificationDef
{
   /** 'reference' attribute */
   private QName mReference;
   /** 'priority' element */
   private AePriorityDef mPriority;
   /** 'peopleAssigments' element */
   private AePeopleAssignmentsDef mPeopleAssignments;
   
   /** The referenced notification def resolved */
   private AeNotificationDef mInlineNotificationDef;
  
   /** map of used variable location paths */
   private Set mUsedVariables;

   /**
    * @return the peopleAssignments
    */
   public AePeopleAssignmentsDef getPeopleAssignments()
   {
      return mPeopleAssignments;
   }

   /**
    * @param aPeopleAssignments the peopleAssignments to set
    */
   public void setPeopleAssignments(AePeopleAssignmentsDef aPeopleAssignments)
   {
      mPeopleAssignments = aPeopleAssignments;
      assignParent(aPeopleAssignments);
   }

   /**
    * @return the priority
    */
   public AePriorityDef getPriority()
   {
      return mPriority;
   }

   /**
    * @param aPriority the priority to set
    */
   public void setPriority(AePriorityDef aPriority)
   {
      mPriority = aPriority;
      assignParent(aPriority);
   }

   /**
    * @return the reference
    */
   public QName getReference()
   {
      return mReference;
   }

   /**
    * @param aReference the reference to set
    */
   public void setReference(QName aReference)
   {
      mReference = aReference;
   }

   /**
    * @return the inlineNotificationDef
    */
   public AeNotificationDef getInlineNotificationDef()
   {
      return mInlineNotificationDef;
   }

   /**
    * @param aInlineNotificationDef the inlineNotificationDef to set
    */
   public void setInlineNotificationDef(AeNotificationDef aInlineNotificationDef)
   {
      mInlineNotificationDef = aInlineNotificationDef;
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
      AeLocalNotificationDef def = (AeLocalNotificationDef)super.clone();
      if (getPriority() != null)
         def.setPriority((AePriorityDef)getPriority().clone());
      if (getPeopleAssignments() != null)
         def.setPeopleAssignments((AePeopleAssignmentsDef)getPeopleAssignments().clone());
      if (getInlineNotificationDef() != null)
         def.setInlineNotificationDef((AeNotificationDef)getInlineNotificationDef().clone());
      if (getReference() != null)
         def.setReference(new QName(getReference().getNamespaceURI(), getReference().getLocalPart()));
      
      try
      {
         if (mUsedVariables != null)
            def.mUsedVariables = AeCloneUtil.deepClone(mUsedVariables);
      }
      catch (CloneNotSupportedException ex)
      {
         // This should never happen in a closed system
         AeException.logError(ex);
         throw new InternalError(ex.getLocalizedMessage());
      }
      
      return def;
   }
   
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeLocalNotificationDef))
         return false;
      
      AeLocalNotificationDef otherDef = (AeLocalNotificationDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.getPriority(), getPriority());
      same &= AeUtil.compareObjects(otherDef.getPeopleAssignments(), getPeopleAssignments());
      same &= AeUtil.compareObjects(otherDef.getInlineNotificationDef(), getInlineNotificationDef());
      same &= AeUtil.compareObjects(otherDef.getReference(), getReference());
      same &= AeUtil.compareObjects(otherDef.mUsedVariables, mUsedVariables);
      
      return same;
   }
}