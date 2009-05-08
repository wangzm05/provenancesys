//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AePresentationElementsDef.java,v 1.12 2008/02/17 21:51:26 mford Exp $$
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeCloneUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * Def implementation for the presentationElements construct
 */
public class AePresentationElementsDef extends AeHtBaseDef
{
   /** 'presentationParameters' element */
   private AePresentationParametersDef mPresentationParameters;
   /** 'name' element list */
   private List mNameList = new ArrayList();
   /** 'subject' element list */
   private List mSubjectList = new ArrayList();
   /** 'description' element list */
   private List mDescriptionList = new ArrayList();
   
   /**
    * @param aName 'name' element Def to be added.
    */
   public void addName(AeNameDef aName)
   {
      mNameList.add(aName);
      assignParent(aName);
   }
   
   /**
    * @return iterator for the 'name' element Def object list
    */
   public Iterator getNameDefs()
   {
      return mNameList.iterator();
   }

   /**
    * Sets the list of 'name' element Defs.
    * 
    * @param aNames
    */
   public void setNameDefs(List aNames)
   {
      mNameList.clear();
      mNameList.addAll(aNames);
      assignParent(aNames);
   }

   /**
    * @return the 'presentationParameters' element Def object.
    */
   public AePresentationParametersDef getPresentationParameters()
   {
      return mPresentationParameters;
   }

   /**
    * @param aPresentationParameters the 'presentationParameters' element Def object to set.
    */
   public void setPresentationParameters(AePresentationParametersDef aPresentationParameters)
   {
      mPresentationParameters = aPresentationParameters;
      assignParent(aPresentationParameters);
   }

   /**
    * @param aSubject 'subject' element Def to be added.
    */
   public void addSubject(AeSubjectDef aSubject)
   {
      mSubjectList.add(aSubject);
      assignParent(aSubject);
   }

   /**
    * @return iterator for the 'subject' element Def object list
    */
   public Iterator getSubjectDefs()
   {
      return mSubjectList.iterator();
   }

   /**
    * Sets the list of 'subject' element Defs.
    * 
    * @param aSubjects
    */
   public void setSubjectDefs(List aSubjects)
   {
      mSubjectList.clear();
      mSubjectList.addAll(aSubjects);
      assignParent(aSubjects);
   }
   
   /**
    * @return the number of 'subject' element Def objects in the list
    */
   public int size()
   {
      return mSubjectList.size();
   }
   
   /**
    * @param aDescription 'description' element Def to be added.
    */
   public void addDescription(AeDescriptionDef aDescription)
   {
      mDescriptionList.add(aDescription);
      assignParent(aDescription);
   }

   /**
    * @return iterator for the 'description' element Def object list
    */
   public Iterator getDescriptionDefs()
   {
      return mDescriptionList.iterator();
   }

   /**
    * Sets the list of 'description' element Defs.
    * 
    * @param aDescriptions
    */
   public void setDescriptionDefs(List aDescriptions)
   {
      mDescriptionList.clear();
      mDescriptionList.addAll(aDescriptions);
      assignParent(aDescriptions);
   }

   /**
    * @see org.activebpel.rt.ht.def.AeHtBaseDef#accept(org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor)
    */
   public void accept(IAeHtDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
   
   /**
    * Trims values that we don't want for our serialized form. This includes
    * the parameters def along with any defs that don't match the lang attribute.
    * @param aLang
    */
   public void trim(String aLang)
   {
      if (aLang != null)
      {
         // only accept names that match our xml:lang value or the default
         List matchList = getMatchingPresentationElements(AeUtil.toList(getNameDefs()), aLang);
         setNameDefs(matchList);
         
         // only accept subjects that match our xml:lang value or the default
         matchList = getMatchingPresentationElements(AeUtil.toList(getSubjectDefs()), aLang);
         setSubjectDefs(matchList);
         
         // only accept descriptions that match our xml:lang value or the default
         matchList = getMatchingDescriptions(AeUtil.toList(getDescriptionDefs()), aLang);
         setDescriptionDefs(matchList);
      }
   }
   
   /**
    * Gets all of the descriptions that match the language
    * @param aDefList
    * @param aLang
    */
   private List getMatchingDescriptions(List aDefList, String aLang)
   {
      Set matched = new HashSet();
      Set contentTypes = new HashSet();
      
      // accept all that match exactly
      for (Iterator it = aDefList.iterator(); it.hasNext();)
      {
         AeDescriptionDef desc = (AeDescriptionDef) it.next();
         if (aLang.equals(desc.getLanguage()))
         {
            matched.add(desc);
            contentTypes.add(desc.getContentType());
         }
      }
      
      // walk through again and add all the default langs with contentTypes we haven't found
      for (Iterator it = aDefList.iterator(); it.hasNext();)
      {
         AeDescriptionDef desc = (AeDescriptionDef) it.next();
         if (!desc.isLanguageSpecified() && !contentTypes.contains(desc.getContentType()))
         {
            matched.add(desc);
            contentTypes.add(desc.getContentType());
         }
      }
      
      // final walk through in order to preserve the original order (nice for tests, not required for runtime)
      List list = new ArrayList(aDefList);
      list.retainAll(matched);
      return list;
   }

   /**
    * Gets the matching presentation element
    * @param aDefs
    * @param aLang
    */
   private List getMatchingPresentationElements(List aDefs, String aLang)
   {
      AePresentationElementDef matched = getMatchingPresentationElement(aDefs, aLang);
      
      boolean nonDefault = !aLang.equals(""); //$NON-NLS-1$
      if (matched == null && nonDefault)
      {
         matched = getMatchingPresentationElement(aDefs, ""); //$NON-NLS-1$
      }
      
      List matchList = null;
      if (matched != null)
         matchList = Collections.singletonList(matched);
      else
         matchList = Collections.EMPTY_LIST;
      return matchList;
   }

   /**
    * Gets the matching presentation element
    * @param aDefs
    * @param aLang
    */
   private AePresentationElementDef getMatchingPresentationElement(List aDefs, String aLang )
   {
      AePresentationElementDef matched = null;
      for(Iterator it=aDefs.iterator(); it.hasNext();)
      {
         AePresentationElementDef def = (AePresentationElementDef) it.next();
         if (aLang.equals(def.getLanguage()))
         {
            matched = def;
            break;
         }
      }
      return matched;
   }

   /**
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      AePresentationElementsDef def = (AePresentationElementsDef)super.clone();
      def.mNameList = new ArrayList();
      def.mSubjectList = new ArrayList();
      def.mDescriptionList = new ArrayList();
      
      if (getPresentationParameters() != null)
         def.setPresentationParameters((AePresentationParametersDef)getPresentationParameters().clone());
      
      try
      {
         if (mNameList.size() > 0)
            def.mNameList = AeCloneUtil.deepClone(mNameList);
         if (mSubjectList.size() > 0)
            def.mSubjectList = AeCloneUtil.deepClone(mSubjectList);
         if (mDescriptionList.size() > 0)
            def.mDescriptionList = AeCloneUtil.deepClone(mDescriptionList);
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
      if (! (aOther instanceof AePresentationElementsDef))
         return false;
      
      AePresentationElementsDef otherDef = (AePresentationElementsDef)aOther;
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(otherDef.getPresentationParameters(), getPresentationParameters());
      same &= AeUtil.compareObjects(otherDef.mNameList, mNameList);
      same &= AeUtil.compareObjects(otherDef.mSubjectList, mSubjectList);
      same &= AeUtil.compareObjects(otherDef.mDescriptionList, mDescriptionList);
      
      return same;
   }
   
   /**
    * Overrides method to produce a useful string representation for this object.
    * 
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      // Figure out the number of languages defined. Note that each name, subject and description
      // list will only have a single entry per language and entries are optional.
      
      Set descLangs = new HashSet();
      for ( Iterator itr=mDescriptionList.iterator(); itr.hasNext(); )
      {
         AeDescriptionDef desc = (AeDescriptionDef)itr.next();      
         descLangs.add(desc.getLanguage());
      }
      
      int langCount = Math.max(Math.max(mNameList.size(), mSubjectList.size()), descLangs.size());
      
      return AeMessages.format("AePresentationElementsDef.LangaugesDefined", new Integer(langCount)); //$NON-NLS-1$ 
   }
}