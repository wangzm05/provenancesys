//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/finders/AeNamedDefFinder.java,v 1.1 2007/12/18 04:06:28 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.def.visitors.finders; 

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.namespace.QName;

import org.activebpel.rt.xml.def.IAeNamedDef;

/**
 * Base class for a finder that looks for a specific resource from an enclosing
 * or imported human interaction. The results are kept in a map with the name
 * of the resource as the key. The map will not add a result to the map if it
 * already exists. This accounts for overriding named elements in a scope.
 */
public abstract class AeNamedDefFinder implements IAeHumanIteractionDefFinder
{
   /** Maps the name to the def */
   private Map mResultsMap = new TreeMap();
   
   /** name of the resource */
   private QName mName;

   /**
    * Ctor
    * @param aName
    */
   public AeNamedDefFinder(QName aName)
   {
      setName(aName);
   }
   
   /**
    * @return the name
    */
   protected QName getName()
   {
      return mName;
   }

   /**
    * @param aName the name to set
    */
   protected void setName(QName aName)
   {
      mName = aName;
   }
   
   /**
    * Adds a result to the list of results. This will add the result only if
    * the named def doesn't already exist within the map.
    * @param aNamedDef
    */
   protected void addResult(IAeNamedDef aNamedDef)
   {
      if (aNamedDef != null && !mResultsMap.containsKey(aNamedDef.getName()))
      {
         mResultsMap.put(aNamedDef.getName(), aNamedDef);
      }
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.finders.IAeHumanIteractionDefFinder#getResults()
    */
   public Collection getResults()
   {
      return mResultsMap.values();
   }

   /**
    * @see org.activebpel.rt.b4p.def.visitors.finders.IAeHumanIteractionDefFinder#isDone()
    */
   public boolean isDone()
   {
      if (getName() != null)
      {
         // if we're looking for a named def, then stop searching when we've found it
         return !mResultsMap.isEmpty();
      }
      else
      {
         // otherwise keep looking
         return false;
      }
   }
} 