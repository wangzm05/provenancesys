//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeAbstractDeadlineDef.java,v 1.7 2008/03/20 16:18:32 rnaylor Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeCloneUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * HumanTask  model, Deadline Def
 *
 */
public abstract class AeAbstractDeadlineDef extends AeHtBaseDef
{
   /** Duration deadline */
   private AeForDef mFor;
   /** Until deadline */
   private AeUntilDef mUntil;
   /** Escalations activated upon reaching the deadline */
   private List mEscalationList = new ArrayList();
   /** Process invokes activated upon reaching the deadline */
   private List mEscalationProcessList = new ArrayList();

   /** 
    * @return duration deadline def
    */
   public AeForDef getFor()
   {
      return mFor;
   }

   /**
    * Set Duration deadline
    * @param aFor
    */
   public void setFor(AeForDef aFor)
   {
      mFor = aFor;
      assignParent(aFor);
   }

   /**
    * @return Until deadline def
    */
   public AeUntilDef getUntil()
   {
      return mUntil;
   }

   /**
    * Set until deadline
    * @param aUntil
    */
   public void setUntil(AeUntilDef aUntil)
   {
      mUntil = aUntil;
      assignParent(aUntil);
   }

   /**
    * Add a deadline escalation
    * @param aEscalation
    */
   public void addEscalation(AeEscalationDef aEscalation)
   {
      addEscalation(getEscalationCount(), aEscalation);
   }

   /**
    * Adds the escalation at the given index
    * @param aIndex
    * @param aEscalation
    */
   public void addEscalation(int aIndex, AeEscalationDef aEscalation)
   {
      getEscalationList().add(aIndex, aEscalation);
      assignParent(aEscalation);
   }
   
   /**
    * Removes the escalation from the deadline
    * @param aEscalation
    */
   public void removeEscalation(AeEscalationDef aEscalation)
   {
      if (getEscalationList().remove(aEscalation))
      {
         aEscalation.setParentXmlDef(null);
      }
   }
   
   /**
    * Gets the number of escalations
    */
   public int getEscalationCount()
   {
      return getEscalationList().size();
   }

   /**
    * @return iterator for the list of 'escalation' Def objects
    */
   public Iterator getEscalationDefs()
   {
      return getEscalationList().iterator();
   }
   
   /**
    * Adds the escalation process at the given index
    * @param aInvoke
    */
   public void addEscalationProcess(AeEscalationProcessDef aInvoke)
   {
      addEscalationProcess(getEscalationProcessCount(), aInvoke);
   }

   /**
    * Adds the escalation process at the given index
    * @param aIndex
    * @param aEscalationProcess
    */
   public void addEscalationProcess(int aIndex, AeEscalationProcessDef aEscalationProcess)
   {
      getEscalationProcessList().add(aIndex, aEscalationProcess);
      assignParent(aEscalationProcess);
   }
   
   /**
    * Removes the escalation process from the deadline
    * @param aEscalationProcess
    */
   public void removeEscalation(AeEscalationProcessDef aEscalationProcess)
   {
      if (getEscalationProcessList().remove(aEscalationProcess))
      {
         aEscalationProcess.setParentXmlDef(null);
      }
   }
   
   /**
    * Gets the number of escalation processes
    */
   public int getEscalationProcessCount()
   {
      return getEscalationProcessList().size();
   }

   /**
    * @return iterator for the list of escalation process def objects
    */
   public Iterator getEscalationProcessDefs()
   {
      return getEscalationProcessList().iterator();
   }

   /**
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      AeAbstractDeadlineDef def = (AeAbstractDeadlineDef)super.clone();
      
      if (getFor() != null)
         def.setFor((AeForDef)getFor().clone());
      if (getUntil() != null)
         def.setUntil((AeUntilDef)getUntil().clone());
      
      try
      {
         if (!getEscalationList().isEmpty())
            def.mEscalationList = AeCloneUtil.deepClone(getEscalationList());
         else
            def.mEscalationList = new ArrayList();
            

         if (!getEscalationProcessList().isEmpty())
            def.mEscalationProcessList = AeCloneUtil.deepClone(getEscalationProcessList());
         else
            def.mEscalationProcessList = new ArrayList();
            
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
      if (! (aOther instanceof AeAbstractDeadlineDef))
         return false;
      
      AeAbstractDeadlineDef otherDef = (AeAbstractDeadlineDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.getFor(), getFor());
      same &= AeUtil.compareObjects(otherDef.getUntil(), getUntil());
      same &= AeUtil.compareObjects(otherDef.getEscalationList(), getEscalationList());
      same &= AeUtil.compareObjects(otherDef.getEscalationProcessList(), getEscalationProcessList());
      
      return same;
   }
   
   /**
    * Getter for the escalation list
    */
   public List getEscalationList()
   {
      return mEscalationList;
   }
   
   /**
    * Setter for the escalation list
    */
   public void setEscalationList(List aList)
   {
      mEscalationList = aList;
      assignParent(aList);
   }
   
   /**
    * Getter for the invokes list
    */
   public List getEscalationProcessList()
   {
      return mEscalationProcessList;
   }
   
   /**
    * Setter for the invokes list
    */
   public void setEscalationProcessList(List aList)
   {
      mEscalationProcessList = aList;
      assignParent(aList);
   }
   
   /**
    * Convenience method to return a list of all escalations, with process escalations appearing first
    */
   public List getAllEscalations()
   {
      List escalations = new ArrayList();
      escalations.addAll(getEscalationProcessList());
      escalations.addAll(getEscalationList());
      
      return escalations;
   }
   
   /**
    * Overrides method to provide count of escalations 
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (getEscalationCount() + getEscalationProcessCount()) + "";  //$NON-NLS-1$
   }
}