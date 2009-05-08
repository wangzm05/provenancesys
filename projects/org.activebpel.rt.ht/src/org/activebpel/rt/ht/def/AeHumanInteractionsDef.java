//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeHumanInteractionsDef.java,v 1.7.2.1 2008/04/21 16:15:16 ppatruni Exp $$
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
 * Impl. for 'humanInteractions' element Def
 */
public class AeHumanInteractionsDef extends AeHtBaseDef implements IAeLogicalPeopleGroupsDefParent, IAeTasksDefParent, IAeNotificationsDefParent
{
   /**
    * The &lt;extension&gt; element is used to specify namespaces of WSHumanTask extension attributes and extension
    * elements, and indicate whether they are mandatory or optional. Attribute mustUnderstand is used to
    * specify whether the extension must be understood by a WS-HumanTask compliant implementation. If the
    * attribute has value <code>yes</code> the extension is mandatory. Otherwise, the extension is optional. If a
    * WS-HumanTask implementation does not support one or more of the extensions with mustUnderstand="yes",
    * then the human interactions definition MUST be rejected. Optional extensions MAY be ignored. It is not
    * required to declare optional extension. The same extension URI MAY be declared multiple times in the
    * &lt;extensions&gt; element. If an extension URI is identified as mandatory in one &lt;extension&gt; element and
    * optional in another, then the mandatory semantics have precedence and MUST be enforced. The extension
    * declarations in an &lt;extensions&gt; element MUST be treated as an unordered set.
    */
   private AeExtensionsDef mExtensions;
   /** 'import' element */
   private List mImports = new ArrayList();
   /** 'logicalPeopleGroups' element */
   private AeLogicalPeopleGroupsDef mLogicalPeopleGroups;
   /** 'tasks' element */
   private AeTasksDef mTasks;
   /** 'notifications' element */
   private AeNotificationsDef mNotifications;
   /** The namespace of the WS-HT. */
   private String mNamespace;
   /** The 'targetNamespace' attribute of the Human Interactions Task. */
   private String mTargetNamespace;
   /**
    * The attribute specifies the query language used in the enclosing elements. The default value for this
    * attribute is urn:wsht: sublang:xpath1.0 which represents the usage of XPath 1.0 within WSHumanTask. The
    * WS-HumanTask constructs that use query expressions may override the default query language for
    * individual query expressions. A WSHumanTask compliant implementation MUST support the use of XPath 1.0
    * as the query language.
    */
   private String mQueryLanguage;
   /**
    * The attribute specifies the expression language used in the enclosing elements. The default value for
    * this attribute is urn:wsht: sublang:xpath1.0 which represents the usage of XPath 1.0 within WSHumanTask.
    * The WS-HumanTask constructs that use expressions may override the default expression language for
    * individual expressions. A WSHumanTask compliant implementation MUST support the use of XPath 1.0 as the
    * expression language.
    */
   private String mExpressionLanguage;

   /**
    * @return the 'extensions' element Def object.
    */
   public AeExtensionsDef getExtensionsDef()
   {
      return mExtensions;
   }

   /**
    * @param aExtensions the 'extensions' element Def object to set.
    */
   public void setExtensionsDef(AeExtensionsDef aExtensions)
   {
      mExtensions = aExtensions;
      assignParent(aExtensions);
   }

   /**
    * @param aImport the 'import' element Def object to add.
    */
   public void addImport(AeImportDef aImport)
   {
      mImports.add(aImport);
      assignParent(aImport);
   }

   /**
    * @return Returns the 'import' element Def object.
    */
   public Iterator getImportDefs()
   {
      return mImports.iterator();
   }

   /**
    * @return the 'logicalPeopleGroups' element Def object
    */
   public AeLogicalPeopleGroupsDef getLogicalPeopleGroups()
   {
      return mLogicalPeopleGroups;
   }

   /**
    * @param aLogicalPeopleGroups the 'logicalPeopleGroups' element Def object to set.
    */
   public void setLogicalPeopleGroupsDef(AeLogicalPeopleGroupsDef aLogicalPeopleGroups)
   {
      mLogicalPeopleGroups = aLogicalPeopleGroups;
      assignParent(aLogicalPeopleGroups);
   }

   /**
    * @return the 'tasks' element Def object.
    */
   public AeTasksDef getTasks()
   {
      return mTasks;
   }

   /**
    * @param aTasks the 'tasks' element Def object to set.
    */
   public void setTasksDef(AeTasksDef aTasks)
   {
      mTasks = aTasks;
      assignParent(aTasks);
   }

   /**
    * @return the 'notifications' element Def object.
    */
   public AeNotificationsDef getNotifications()
   {
      return mNotifications;
   }

   /**
    * @param aNotifications the 'notifications' element Def object to set.
    */
   public void setNotificationsDef(AeNotificationsDef aNotifications)
   {
      mNotifications = aNotifications;
      assignParent(aNotifications);
   }

   /**
    * @return the namespace
    */
   public String getNamespace()
   {
      return mNamespace;
   }

   /**
    * @param aNamespace the namespace to set
    */
   public void setNamespace(String aNamespace)
   {
      mNamespace = aNamespace;
   }

   /**
    * @return the 'targetNamespace' attribute Def object.
    */
   public String getTargetNamespace()
   {
      return mTargetNamespace;
   }

   /**
    * @param aTargetNamespace the 'targetNamespace' attribute Def object to set.
    */
   public void setTargetNamespace(String aTargetNamespace)
   {
      mTargetNamespace = aTargetNamespace;
   }

   /**
    * @return the 'queryLanguage' attribute value.
    */
   public String getQueryLanguage()
   {
      return mQueryLanguage;
   }

   /**
    * @param aQueryLanguage the 'queryLanguage' attribute value to set.
    */
   public void setQueryLanguage(String aQueryLanguage)
   {
      mQueryLanguage = aQueryLanguage;
   }

   /**
    * @return the 'expressionLanguage' attribute value.
    */
   public String getExpressionLanguage()
   {
      return mExpressionLanguage;
   }

   /**
    * @param aExpressionLanguage the 'expressionLanguage' attribute value to set.
    */
   public void setExpressionLanguage(String aExpressionLanguage)
   {
      mExpressionLanguage = aExpressionLanguage;
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
      AeHumanInteractionsDef def = (AeHumanInteractionsDef)super.clone();
      def.mImports = new ArrayList();
      
      if (getExtensionsDef() != null)
         def.setExtensionsDef((AeExtensionsDef)getExtensionsDef().clone());
      if (getLogicalPeopleGroups() != null)
         def.setLogicalPeopleGroupsDef((AeLogicalPeopleGroupsDef)getLogicalPeopleGroups().clone());
      if (getTasks() != null)
         def.setTasksDef((AeTasksDef)getTasks().clone());
      if (getNotifications() != null)
         def.setNotificationsDef((AeNotificationsDef)getNotifications().clone());
      
      try
      {
         if (mImports.size() > 0)
            def.mImports = AeCloneUtil.deepClone(mImports);
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
      if (! (aOther instanceof AeHumanInteractionsDef))
         return false;
      
      AeHumanInteractionsDef otherDef = (AeHumanInteractionsDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.getExtensionsDef(), getExtensionsDef());
      same &= AeUtil.compareObjects(otherDef.getLogicalPeopleGroups(), getLogicalPeopleGroups());
      same &= AeUtil.compareObjects(otherDef.getTasks(), getTasks());
      same &= AeUtil.compareObjects(otherDef.getNotifications(), getNotifications());
      same &= AeUtil.compareObjects(otherDef.mImports, mImports);
      same &= AeUtil.compareObjects(otherDef.getNamespace(), getNamespace());
      same &= AeUtil.compareObjects(otherDef.getTargetNamespace(), getTargetNamespace());
      same &= AeUtil.compareObjects(otherDef.getQueryLanguage(), getQueryLanguage());
      same &= AeUtil.compareObjects(otherDef.getExpressionLanguage(), getExpressionLanguage());
      
      return same;
   }
}