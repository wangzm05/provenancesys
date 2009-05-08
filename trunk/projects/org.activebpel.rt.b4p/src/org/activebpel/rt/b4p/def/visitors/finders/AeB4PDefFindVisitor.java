// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/finders/AeB4PDefFindVisitor.java,v 1.2 2008/01/21 22:11:03 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.def.visitors.finders;

import org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor;
import org.activebpel.rt.xml.def.AeBaseXmlDef;

/**
 * Find the Def for a B4P extension object using a location path.
 */
public class AeB4PDefFindVisitor extends AeAbstractTraversingB4PDefVisitor
{
   /** the path to find. */
   private String mPath;
   /** The definition object which is not null if it has been found. */
   private AeBaseXmlDef mFoundDef = null;
   
   /**
    * C'tor the location path of the def to find
    * @param aPath
    */
   public AeB4PDefFindVisitor(String aPath)
   {
      setPath(aPath);
   }
   
   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractTraversingHtDefVisitor#visitBaseXmlDef(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   protected void visitBaseXmlDef(AeBaseXmlDef aDef)
   {
      if(getFoundDef() == null)
      {
         if(getPath().equals(aDef.getLocationPath()))
         {
            setFoundDef(aDef);
         }
         else
         {
            super.traverse(aDef);
         }
      }
   }
   
   /**
    * @return Returns the Def found.
    */
   public AeBaseXmlDef getFoundDef()
   {
      return mFoundDef;
   }

   /**
    * @return Returns the path.
    */
   public String getPath()
   {
      return mPath;
   }

   /**
    * @param aPath the path to set
    */
   public void setPath(String aPath)
   {
      mPath = aPath;
   }

   /**
    * @param aFoundDef the foundDef to set
    */
   protected void setFoundDef(AeBaseXmlDef aFoundDef)
   {
      mFoundDef = aFoundDef;
   }
}
