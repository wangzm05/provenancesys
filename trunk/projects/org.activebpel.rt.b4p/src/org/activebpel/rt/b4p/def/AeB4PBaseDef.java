//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeB4PBaseDef.java,v 1.5 2008/02/17 21:36:03 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def;

import org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;

/**
 * Base definition object for all BPEL4People model definitions
 */
public abstract class AeB4PBaseDef extends AeBaseXmlDef
{
   /**
    * C'tor.
    */
   public AeB4PBaseDef()
   {
      super();
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
      if ( aVisitor instanceof IAeB4PDefVisitor )
      {
         accept((IAeB4PDefVisitor)aVisitor);
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
      return aOther != null && getClass().equals(aOther.getClass()) && compare(aOther); 
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
   abstract public void accept(IAeB4PDefVisitor aVisitor);
}
