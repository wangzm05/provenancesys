//$$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeImportDef.java,v 1.3 2007/11/13 16:57:34 rnaylor Exp $$
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
 * Impl of 'import' Def
 */
public class AeImportDef extends AeHtBaseDef
{
   /** Import namespace. */
   private String mNamespace;
   /** The location of the import. */
   private String mLocation;
   /** The import type. */
   private String mImportType;

   /**
    * @return the importType
    */
   public String getImportType()
   {
      return mImportType;
   }

   /**
    * @param aImportType the importType to set
    */
   public void setImportType(String aImportType)
   {
      mImportType = aImportType;
   }

   /**
    * @return the location
    */
   public String getLocation()
   {
      return mLocation;
   }

   /**
    * @param aLocation the location to set
    */
   public void setLocation(String aLocation)
   {
      mLocation = aLocation;
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
      if (! (aOther instanceof AeImportDef))
         return false;
      
      AeImportDef otherDef = (AeImportDef)aOther;
      boolean same = super.equals(aOther); 
      same &= AeUtil.compareObjects(otherDef.getNamespace(), getNamespace());
      same &= AeUtil.compareObjects(otherDef.getLocation(), getLocation());
      same &= AeUtil.compareObjects(otherDef.getImportType(), getImportType());
      
      return same;
   }
}