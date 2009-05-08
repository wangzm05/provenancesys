//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeDeadlinesDef.java,v 1.7 2008/03/20 16:18:32 rnaylor Exp $$
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
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeCloneUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * Impl. for 'deadlines' element Def.
 */
public class AeDeadlinesDef extends AeHtBaseDef
{
   /** List of 'startDeadline' element Def objects */
   private List mStartDeadlineList = new ArrayList();
   /** List of 'completionDeadline' element Def objects */
   private List mCompletionDeadlineList = new ArrayList();

   /**
    * @param aStartDeadline 'startDeadline' element Def object to add.
    */
   public void addStartDeadline(AeStartDeadlineDef aStartDeadline)
   {
      addStartDeadline(getStartCount(), aStartDeadline);
   }
   
   /**
    * Adds a start deadline at the given index
    * @param aIndex
    * @param aStartDeadline
    */
   public void addStartDeadline(int aIndex, AeStartDeadlineDef aStartDeadline)
   {
      mStartDeadlineList.add(aIndex, aStartDeadline);
      assignParent(aStartDeadline);
   }

   /**
    * Removes a start deadline
    * @param aStartDeadline
    */
   public void removeStartDeadline(AeStartDeadlineDef aStartDeadline)
   {
      if (mStartDeadlineList.remove(aStartDeadline))
      {
         aStartDeadline.setParentXmlDef(null);
      }
   }
   
   /**
    * Gets the number of start deadlines
    */
   public int getStartCount()
   {
      return mStartDeadlineList.size();
   }

   /**
    * @return iterator for the list of 'startDeadline' element Def objects.
    */
   public Iterator getStartDeadlineDefs()
   {
      return mStartDeadlineList.iterator();
   }

   /**
    * @param aCompletionDeadline 'completionDeadline' element Def object to add.
    */
   public void addCompletionDeadline(AeCompletionDeadlineDef aCompletionDeadline)
   {
      addCompletionDeadline(getCompletionCount(), aCompletionDeadline);
   }

   /**
    * Adds a completion deadline at the given index
    * @param aIndex
    * @param aCompletionDeadline
    */
   public void addCompletionDeadline(int aIndex, AeCompletionDeadlineDef aCompletionDeadline)
   {
      mCompletionDeadlineList.add(aIndex, aCompletionDeadline);
      assignParent(aCompletionDeadline);
   }

   /**
    * Removes a completion deadline
    * @param aCompletionDeadline
    */
   public void removeCompletionDeadline(AeCompletionDeadlineDef aCompletionDeadline)
   {
      if (mCompletionDeadlineList.remove(aCompletionDeadline))
      {
         aCompletionDeadline.setParentXmlDef(null);
      }
   }
   
   /**
    * Gets the number of completion deadlines
    */
   public int getCompletionCount()
   {
      return mCompletionDeadlineList.size();
   }

   /**
    * @return iterator for the list of 'completionDeadline' element Def objects.
    */
   public Iterator getCompletionDeadlineDefs()
   {
      return mCompletionDeadlineList.iterator();
   }
   
   /**
    * Sets the start deadlines for this task
    * @param aDeadlines
    */
   public void setStartDeadlines(List aDeadlines)
   {
      mStartDeadlineList = aDeadlines;
      assignParent(aDeadlines);
   }
   
   /**
    * Sets the completion deadlines for this task
    * @param aDeadlines
    */
   public void setCompletionDeadlines(List aDeadlines)
   {
      mCompletionDeadlineList = aDeadlines;
      assignParent(aDeadlines);
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
      AeDeadlinesDef def = (AeDeadlinesDef)super.clone();
      def.mStartDeadlineList = new ArrayList();
      def.mCompletionDeadlineList = new ArrayList();
      
      try
      {
         if (mStartDeadlineList.size() > 0)
            def.mStartDeadlineList = AeCloneUtil.deepClone(mStartDeadlineList); 
         if (mCompletionDeadlineList.size() > 0)
            def.mCompletionDeadlineList = AeCloneUtil.deepClone(mCompletionDeadlineList); 
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
      if (! (aOther instanceof AeDeadlinesDef))
         return false;
      
      AeDeadlinesDef otherDef = (AeDeadlinesDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.mStartDeadlineList, mStartDeadlineList);
      same &= AeUtil.compareObjects(otherDef.mCompletionDeadlineList, mCompletionDeadlineList);
      
      return same;
   }

   /**
    * Returns true if there is at least one start or completion deadline defined.
    */
   public boolean isDefined()
   {
      return getStartCount() > 0 || getCompletionCount() > 0;
   }
}