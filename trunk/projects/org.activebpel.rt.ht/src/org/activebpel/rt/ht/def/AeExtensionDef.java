//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeExtensionDef.java,v 1.3.4.1 2008/04/21 16:15:16 ppatruni Exp $$
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.ht.def;

import org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * The &lt;extension&gt; element is used to specify namespaces of WSHumanTask extension attributes and extension
 * elements, and indicate whether they are mandatory or optional. Attribute mustUnderstand is used to specify
 * whether the extension must be understood by a WS-HumanTask compliant implementation. If the attribute has
 * value 'yes' the extension is mandatory. Otherwise, the extension is optional. If a WS-HumanTask
 * implementation does not support one or more of the extensions with mustUnderstand="yes", then the human
 * interactions definition MUST be rejected. Optional extensions MAY be ignored. It is not required to declare
 * optional extension. The same extension URI MAY be declared multiple times in the &lt;extensions&gt; element. If
 * an extension URI is identified as mandatory in one &lt;extension&gt; element and optional in another, then the
 * mandatory semantics have precedence and MUST be enforced. The extension declarations in an &lt;extensions&gt;
 * element MUST be treated as an unordered set.
 */
public class AeExtensionDef extends AeHtBaseDef
{
   /** namespace' attribute value */
   private String mNamespace;
   /** mustUnderstand' attribute value */
   private boolean mMustUnderstand;

   /**
    * @return the mustUnderstand
    */
   public boolean isMustUnderstand()
   {
      return mMustUnderstand;
   }

   /**
    * @param aMustUnderstand the mustUnderstand to set
    */
   public void setMustUnderstand(boolean aMustUnderstand)
   {
      mMustUnderstand = aMustUnderstand;
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
    * @see org.activebpel.rt.ht.def.AeHtBaseDef#accept(org.activebpel.rt.ht.def.visitors.IAeHtDefVisitor)
    */
   public void accept(IAeHtDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (! (aOther instanceof AeExtensionDef))
         return false;
      
      AeExtensionDef otherDef = (AeExtensionDef)aOther;
      boolean same = super.equals(aOther);
      same &= AeUtil.compareObjects(otherDef.getNamespace(), getNamespace());
      same &= (otherDef.isMustUnderstand() == isMustUnderstand()) ;
      
      return same;
   }
}