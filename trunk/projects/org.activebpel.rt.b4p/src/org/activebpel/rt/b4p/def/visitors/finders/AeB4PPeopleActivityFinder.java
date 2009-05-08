//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/finders/AeB4PPeopleActivityFinder.java,v 1.4 2008/03/15 22:18:35 dvilaverde Exp $
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.def.IAeB4PDefConstants;
import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.AeCompensationHandlerDef;
import org.activebpel.rt.bpel.def.AeEventHandlersDef;
import org.activebpel.rt.bpel.def.AeFaultHandlersDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.AeScopeDef;
import org.activebpel.rt.bpel.def.AeTerminationHandlerDef;
import org.activebpel.rt.bpel.def.IAeSingleActivityContainerDef;
import org.activebpel.rt.bpel.def.activity.AeActivityFlowDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.bpel.def.activity.AeActivitySequenceDef;
import org.activebpel.rt.bpel.def.activity.AeChildExtensionActivityDef;
import org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor;
import org.activebpel.rt.bpel.def.visitors.AeDefTraverser;
import org.activebpel.rt.bpel.def.visitors.AeTraversalVisitor;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeXmlDefUtil;
import org.activebpel.rt.xml.def.IAeExtensionObject;
import org.activebpel.rt.xml.def.IAeGetBaseXmlDefAdapter;
import org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor;


/**
 * Bi-directional visitor that searches for a people activity by its name.
 * This visitor is used to resolve references to people activities by the 
 * b4p functions like getActualOwner(). The logic is as follows:
 * 1) start from a single def (i.e. an AeFromDef)
 * 2) traverse up until you hit the people activity or a container that may 
 *    contain the people activity
 * 3) traverse into any containers (flow, sequence, scope) that may contain the 
 *    people activity and search for it by name
 * 4) report all of the PA's found in a Collection.
 *   
 */
public class AeB4PPeopleActivityFinder extends AeAbstractDefVisitor
{
   /** QName of the PeopleAcivity extension activity */
   private static QName sPeopleActivity = new QName(IAeB4PConstants.B4P_NAMESPACE, IAeB4PDefConstants.TAG_PEOPLE_ACTIVITY);
   /** name of the PA we're looking for */
   private String mPeopleActivityName;
   /** flag to indicate the direction we're traversing  */
   private boolean mTraverseUp;
   /** PA's that have been matched */
   private Collection mResults = new LinkedList();
   /** provides context of where the search originated from */
   private AeBaseXmlDef mStartDef;
   /** starting point's enclosing activity */
   private AeActivityDef mEnclosingStartActivityDef;
   /** flag if we should traverse into a scope, set whenever we encounter an FCT handler */
   private boolean mTraverseIntoScope;
   /** set of defs that we have traversed into already */
   private Set mTraversedInto;
   
   /**
    * Find all in scope people activities that match the supplied people activity name.
    *  
    * @param aDef
    */
   public static Collection findPeopleActivities(AeBaseXmlDef aDef, String aPeopleActivityName)
   {
      AeB4PPeopleActivityFinder finder = new AeB4PPeopleActivityFinder(aDef, aPeopleActivityName);
      aDef.accept(finder);
      return finder.getResults();
   }

   /**
    * Ctor
    * @param aStartDef
    * @param aPeopleActivityName
    */
   public AeB4PPeopleActivityFinder(AeBaseXmlDef aStartDef, String aPeopleActivityName)
   {
      setPeopleActivityName(aPeopleActivityName);
      setStartDef(aStartDef);
      setTraversalVisitor(new AeTraversalVisitor(new AeNoForwardReferenceTraverser(), this));
      AeActivityDef enclosingActivityDef = (AeActivityDef) AeXmlDefUtil.getAncestorByType(aStartDef, AeActivityDef.class);
      setEnclosingStartActivityDef(enclosingActivityDef);
      setTraverseUp(true);
      setTraversedInto(new HashSet());
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityFlowDef)
    */
   public void visit(AeActivityFlowDef aDef)
   {
      if (isTraverseUp())
      {
         traverseInto(aDef);
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivitySequenceDef)
    */
   public void visit(AeActivitySequenceDef aDef)
   {
      if (isTraverseUp())
      {
         traverseInto(aDef);
      }
      super.visit(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.AeScopeDef)
    */
   public void visit(AeScopeDef aDef)
   {
      resetEnclosingActivityDef(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityScopeDef)
    */
   public void visit(AeActivityScopeDef aDef)
   {
      if (isTraverseUp() && isTraverseIntoScope())
      {
         setTraverseIntoScope(false);
         traverseInto(aDef);
      }
      super.visit(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.AeProcessDef)
    */
   public void visit(AeProcessDef aDef)
   {
      if (isTraverseUp() && isTraverseIntoScope())
      {
         setTraverseIntoScope(false);
         traverseInto(aDef);
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.AeFaultHandlersDef)
    */
   public void visit(AeFaultHandlersDef aDef)
   {
      if (isTraverseUp())
      {
         setTraverseIntoScope(true);
         resetEnclosingActivityDef(aDef);
      }
      super.visit(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.AeCompensationHandlerDef)
    */
   public void visit(AeCompensationHandlerDef aDef)
   {
      if (isTraverseUp())
      {
         setTraverseIntoScope(true);
         resetEnclosingActivityDef(aDef);
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.AeEventHandlersDef)
    */
   public void visit(AeEventHandlersDef aDef)
   {
      if (isTraverseUp())
      {
         setTraverseIntoScope(true);
         resetEnclosingActivityDef(aDef);
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.AeTerminationHandlerDef)
    */
   public void visit(AeTerminationHandlerDef aDef)
   {
      if (isTraverseUp())
      {
         setTraverseIntoScope(true);
         resetEnclosingActivityDef(aDef);
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeChildExtensionActivityDef)
    */
   public void visit(AeChildExtensionActivityDef aDef)
   {
      // if the extension activity is a PeopleActivity and has the same name then
      // add it to the list of found people activities.
      if ( notAlreadyTraversedInto(aDef) && 
           AeUtil.compareObjects(aDef.getElementName(), sPeopleActivity) && 
           AeUtil.compareObjects(getPeopleActivityName(), aDef.getName()) )
      {
         AeBaseXmlDef foundDef = getExtensionActivity(aDef);
         if (foundDef != null)
         {
            getResults().add(foundDef);
         }
      }
      super.visit(aDef);
   }
   
   /**
    * Return the BaseXmlDef from the aChildExtensionActivity object.
    * 
    * @return AeBaseXmlDef
    */
   protected AeBaseXmlDef getExtensionActivity(AeChildExtensionActivityDef aChildExtensionActivity)
   {
      AeBaseXmlDef def = null;
      IAeExtensionObject object = aChildExtensionActivity.getExtensionObject();
      if (object != null)
      {
         IAeGetBaseXmlDefAdapter adapter = (IAeGetBaseXmlDefAdapter) object.getAdapter(IAeGetBaseXmlDefAdapter.class);
         if (adapter != null)
         {
            def = adapter.getExtensionAsBaseXmlDef();
         }
      }
      return def;
   }
   
   /**
    * @see org.activebpel.rt.xml.def.visitors.AeTraversingXmlDefVisitor#traverse(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   protected void traverse(AeBaseXmlDef aDef)
   {
      if (isTraverseUp())
      {
         if (aDef.getParentXmlDef() != null)
            aDef.getParentXmlDef().accept(this);
      }
      else if (notAlreadyTraversedInto(aDef))
      {
         super.traverse(aDef);
         resetEnclosingActivityDef(aDef);
      }
   }

   /**
    * True if we haven't already traversed into this def
    * @param aDef
    */
   protected boolean notAlreadyTraversedInto(AeBaseXmlDef aDef)
   {
      return getTraversedInto().add(aDef);
   }
   
   /**
    * Reset the enclosing activity to handle cases of PA usage within nested sequences
    * @param aDef
    */
   protected void resetEnclosingActivityDef(AeBaseXmlDef aDef)
   {
      if (aDef instanceof IAeSingleActivityContainerDef)
      {
         if (aDef == getEnclosingStartActivityDef().getParentXmlDef())
         {
            setEnclosingStartActivityDef((AeActivityDef) AeXmlDefUtil.getAncestorByType(aDef, AeActivityDef.class));
         }
      }
      else
      {
         if ( aDef instanceof AeActivityDef && aDef == getEnclosingStartActivityDef().getParentXmlDef())
         {
            setEnclosingStartActivityDef((AeActivityDef)aDef);
         }
      }
   }

   /**
    * Changes the direction of traversal for this one def so we traverse into it.
    * @param aDef
    */
   protected void traverseInto(AeBaseXmlDef aDef)
   {
      setTraverseUp(false);
      aDef.accept(this);
      setTraverseUp(true);
   }

   /**
    * @return the peopleActivityName
    */
   protected String getPeopleActivityName()
   {
      return mPeopleActivityName;
   }

   /**
    * @param aPeopleActivityName the peopleActivityName to set
    */
   protected void setPeopleActivityName(String aPeopleActivityName)
   {
      mPeopleActivityName = aPeopleActivityName;
   }

   /**
    * @return the traverseUp
    */
   protected boolean isTraverseUp()
   {
      return mTraverseUp;
   }

   /**
    * @param aTraverseUp the traverseUp to set
    */
   protected void setTraverseUp(boolean aTraverseUp)
   {
      mTraverseUp = aTraverseUp;
   }

   /**
    * @return the results
    */
   protected Collection getResults()
   {
      return mResults;
   }
   
   /**
    * @return the startDef
    */
   protected AeBaseXmlDef getStartDef()
   {
      return mStartDef;
   }

   /**
    * @param aStartDef the startDef to set
    */
   protected void setStartDef(AeBaseXmlDef aStartDef)
   {
      mStartDef = aStartDef;
   }

   /**
    * @return the traverseIntoScope
    */
   protected boolean isTraverseIntoScope()
   {
      return mTraverseIntoScope;
   }

   /**
    * @param aTraverseIntoScope the traverseIntoScope to set
    */
   protected void setTraverseIntoScope(boolean aTraverseIntoScope)
   {
      mTraverseIntoScope = aTraverseIntoScope;
   }
   
   /**
    * Returns true if the given sequence is the parent of the start 
    * activity.
    * @param aDef
    */
   protected boolean isParentOfStartActivity(AeActivitySequenceDef aDef)
   {
      AeActivityDef activityDef = getEnclosingStartActivityDef();
      if (activityDef != null)
         return aDef == activityDef.getParentXmlDef();
      else
         return false;
   }

   /**
    * @return the enclosingStartActivityDef
    */
   protected AeActivityDef getEnclosingStartActivityDef()
   {
      return mEnclosingStartActivityDef;
   }

   /**
    * @param aEnclosingStartActivityDef the enclosingStartActivityDef to set
    */
   protected void setEnclosingStartActivityDef(
         AeActivityDef aEnclosingStartActivityDef)
   {
      mEnclosingStartActivityDef = aEnclosingStartActivityDef;
   }

   protected class AeNoForwardReferenceTraverser extends AeDefTraverser
   {
      /**
       * @see org.activebpel.rt.bpel.def.visitors.AeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivitySequenceDef, org.activebpel.rt.xml.def.visitors.IAeBaseXmlDefVisitor)
       */
      public void traverse(AeActivitySequenceDef aDef,
            IAeBaseXmlDefVisitor aVisitor)
      {
         if (isParentOfStartActivity(aDef))
         {
            traverseDocumentationDefs(aDef, aVisitor);
            traverseExtensionDefs(aDef, aVisitor);
            traverseSourceAndTargetLinks(aDef, aVisitor);
            // stop traversing children once you hit the start def
            for(Iterator it=aDef.getActivityDefs(); it.hasNext();)
            {
               AeBaseXmlDef child = (AeBaseXmlDef) it.next();
               child.accept(aVisitor);
               if (child == getEnclosingStartActivityDef())
                  break; 
            }
            setEnclosingStartActivityDef(aDef);
         }
         else
         {
            super.traverse(aDef, aVisitor);
         }
      }
   }

   /**
    * @return the traversedInto
    */
   protected Set getTraversedInto()
   {
      return mTraversedInto;
   }

   /**
    * @param aTraversedInto the traversedInto to set
    */
   protected void setTraversedInto(Set aTraversedInto)
   {
      mTraversedInto = aTraversedInto;
   }
}