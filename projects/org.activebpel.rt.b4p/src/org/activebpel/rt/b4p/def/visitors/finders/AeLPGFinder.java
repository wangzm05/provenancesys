//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/finders/AeLPGFinder.java,v 1.3 2008/03/14 20:46:52 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.visitors.finders; 

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;

/**
 * Finds a logical people group within a human interaction
 */
public class AeLPGFinder extends AeNamedDefFinder
{
   /**
    * Ctor
    * @param aName
    */
   public AeLPGFinder(QName aName)
   {
      super(aName);
   }

   /**
    * Returns the logical people group with the matching name or null if not
    * found on this def.
    * 
    * @see org.activebpel.rt.b4p.def.visitors.finders.AeNamedDefFinder#find(org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef)
    */
   public void find(AeB4PHumanInteractionsDef aDef)
   {
      if (getName() != null)
      {
         addResult(aDef.getLogicalPeopleGroupDef(getName().getLocalPart()));
      }
      else
      {
         if (aDef.getLogicalPeopleGroupsDef() != null)
         {
            for (Iterator it = aDef.getLogicalPeopleGroupsDef().getLogicalPeopleGroupDefs(); it.hasNext();)
            {
               AeLogicalPeopleGroupDef lpg = (AeLogicalPeopleGroupDef) it.next();
               addResult(lpg);
            }
         }
      }
   }
} 