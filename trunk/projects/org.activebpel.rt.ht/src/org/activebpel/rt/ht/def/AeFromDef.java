//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeFromDef.java,v 1.13 2008/02/28 20:12:27 vvelusamy Exp $$
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

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeCloneUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * Impl. for 'from' element Def.
 */
public class AeFromDef extends AeAbstractExpressionDef
{
   /** list of associated 'argument' Def objects */
   private List mArgumentList = new ArrayList();
   /** 'literal' element value */
   private AeLiteralDef mLiteral;
   /** The attribute with the name of the logical people group */
   private QName mLogicalPeopleGroup;
   /** inlined LPG */
   private AeLogicalPeopleGroupDef mLogicalPeopleGroupDef;

   /**
    * Adds an argument child def.
    * 
    * @param aArgument
    */
   public void addArgument(AeArgumentDef aArgument)
   {
      mArgumentList.add(aArgument);
      assignParent(aArgument);
   }

   /**
    * @return List of ArgumentList
    */
   public List getArgumentDefs()
   {
      return mArgumentList;
   }
   
   /**
    * Gets the arg with the given name or returns null.
    * @param aName
    */
   public AeArgumentDef getArgumentDef(String aName)
   {
      for (Iterator it = getArgumentDefs().iterator(); it.hasNext();)
      {
         AeArgumentDef def = (AeArgumentDef) it.next();
         if (AeUtil.compareObjects(aName, def.getName()))
            return def;
      }
      return null;
   }

   /**
    * @return the literal
    */
   public AeLiteralDef getLiteral()
   {
      return mLiteral;
   }

   /**
    * @param aLiteral the literal to set
    */
   public void setLiteral(AeLiteralDef aLiteral)
   {
      mLiteral = aLiteral;
      assignParent(aLiteral);
   }

   /**
    * @return the logicalPeopleGroup
    */
   public QName getLogicalPeopleGroup()
   {
      return mLogicalPeopleGroup;
   }

   /**
    * @param aLogicalPeopleGroup the logicalPeopleGroup to set
    */
   public void setLogicalPeopleGroup(QName aLogicalPeopleGroup)
   {
      mLogicalPeopleGroup = aLogicalPeopleGroup;
   }

   /**
    * @see org.activebpel.rt.ht.def.AeHtBaseDef#accept(org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor)
    */
   public void accept(IAeHtDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * Returns true if this is a literal assignment
    */
   public boolean isLiteral()
   {
      return getLiteral() != null;
   }

   /**
    * Returns true if this is an LPG assignment
    */
   public boolean isLPG()
   {
      return AeUtil.notNullOrEmpty(getLogicalPeopleGroup());
   }
   
   /**
    * Returns true if this is an expression assignment 
    */
   public boolean isExpression()
   {
      return AeUtil.notNullOrEmpty(getExpression());
   }

   /**
    * Returns True if this FromDef has been defined.
    */
   public boolean isDefined()
   {
      boolean literalDefined = getLiteral() != null && getLiteral().getOrganizationalEntity().trim().length() != 0;
      boolean exprDefined = AeUtil.notNullOrEmpty(getExpression());
      boolean lpgDefined = AeUtil.notNullOrEmpty(getLogicalPeopleGroup());
      
      return (literalDefined || exprDefined || lpgDefined );
   }
   
   /**
    * Clears the definition.
    */
   public void clear()
   {
      mLiteral = null;
      mArgumentList.clear();
      mLogicalPeopleGroup = null;
      
      getTextNodes().clear();
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeFromDef))
         return false;
      
      AeFromDef otherFrom = (AeFromDef)aOther;
      boolean same = AeUtil.compareObjects(otherFrom.getExpression(), getExpression());
      same &= AeUtil.compareObjects(AeUtil.getSafeString(otherFrom.getExpressionLanguage()), AeUtil.getSafeString(getExpressionLanguage()));
      same &= AeUtil.compareObjects(otherFrom.getArgumentDefs(), getArgumentDefs());

      boolean sameLPG = AeUtil.compareObjects(otherFrom.getLogicalPeopleGroup(), getLogicalPeopleGroup());
      if (! sameLPG)
         sameLPG = AeUtil.isNullOrEmpty(getLogicalPeopleGroup()) && AeUtil.isNullOrEmpty(otherFrom.getLogicalPeopleGroup());
      
      same &= sameLPG;

      boolean sameLiteral = AeUtil.compareObjects(otherFrom.getLiteral(), getLiteral());
      if (! sameLiteral)
      {
         if (otherFrom.getLiteral() == null && AeUtil.isNullOrEmpty(getLiteral().getOrganizationalEntity()))
            sameLiteral = true;
         else if (getLiteral() == null && AeUtil.isNullOrEmpty(otherFrom.getLiteral().getOrganizationalEntity()))
            sameLiteral = true;
      }
      same &= sameLiteral;
      
      return same;
   }

   /**
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      AeFromDef fromDef = (AeFromDef)super.clone();
      fromDef.mArgumentList = new ArrayList();
      
      if (getLiteral() != null)
         fromDef.setLiteral((AeLiteralDef)getLiteral().clone());
      
      try
      {
         fromDef.mArgumentList = AeCloneUtil.deepClone(mArgumentList);
      }
      catch (CloneNotSupportedException ex)
      {
         // This should never happen in a closed system
         AeException.logError(ex);
         throw new InternalError(ex.getLocalizedMessage());
      }
      
      return fromDef;
   }

   /**
    * Overrides method to produce a user friendly representation of this object. 
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      StringBuffer buff = new StringBuffer();
      if (AeUtil.notNullOrEmpty(getExpression()))
         buff.append(getExpression());
      else if (getLiteral() != null)
         buff.append(getLiteral());
      else if (AeUtil.notNullOrEmpty(getLogicalPeopleGroup()))
      {
         buff.append(getLogicalPeopleGroup());
         
         Iterator iter=getArgumentDefs().iterator();
         if(iter.hasNext())
         {
            buff.append(" ("); //$NON-NLS-1$

            boolean addComma = false;
            for (; iter.hasNext();)
            {
               AeArgumentDef arg = (AeArgumentDef)iter.next();
               if (addComma)
                  buff.append(", "); //$NON-NLS-1$
               else
                  addComma = true;
               buff.append(arg.getName()).append("=").append(arg.getTextValue()); //$NON-NLS-1$
            }
            buff.append(")"); //$NON-NLS-1$
         }
      }
      
      return buff.toString();
   }

   /**
    * @return the logicalPeopleGroupDef
    */
   public AeLogicalPeopleGroupDef getInlineLogicalPeopleGroupDef()
   {
      return mLogicalPeopleGroupDef;
   }

   /**
    * @param aLogicalPeopleGroupDef the logicalPeopleGroupDef to set
    */
   public void setInlineLogicalPeopleGroupDef(AeLogicalPeopleGroupDef aLogicalPeopleGroupDef)
   {
      mLogicalPeopleGroupDef = aLogicalPeopleGroupDef;
   }
}