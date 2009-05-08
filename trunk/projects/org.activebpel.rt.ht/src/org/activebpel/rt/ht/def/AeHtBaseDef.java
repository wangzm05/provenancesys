//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/AeHtBaseDef.java,v 1.7 2008/02/17 21:51:26 mford Exp $
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
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * Base definition object for all Human Task model definitions
 */
public abstract class AeHtBaseDef extends AeBaseXmlDef
{
   /**
    * C'tor.
    */
   public AeHtBaseDef()
   {
      super();
   }

   /**
    * Gets the parent HT def.
    */
   public AeHtBaseDef getParentDef()
   {
      // Note: the parent could be a B4P def.
      if (getParentXmlDef() instanceof AeHtBaseDef)
         return (AeHtBaseDef) getParentXmlDef();
      else
         return null;
   }

   /**
    * @see org.activebpel.rt.xml.def.AeBaseXmlDef#accept(org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
    */
   public void accept(IAeBaseXmlDefVisitor aVisitor)
   {
      // This instanceof check is necessary in order to handle visitors that
      // reference the object via its AeBaseDefXml type. If the visitor is
      // actually a domain specific visitor then it'll dispatch accordingly.
      // Otherwise it'll use the standard visit method.
      if(aVisitor instanceof IAeHtDefVisitor)
      {
         accept((IAeHtDefVisitor)aVisitor);
      }
      else
      {
         aVisitor.visit(this);
      }
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      return compare(aOther);
   }

   /**
     * @see java.lang.Object#hashCode()
     */
    public final int hashCode()
    {
       return getHashCode();
    }

   /**
    * Accept a HT def visitor.
    *
    * @param aVisitor
    */
   public abstract void accept(IAeHtDefVisitor aVisitor);
}