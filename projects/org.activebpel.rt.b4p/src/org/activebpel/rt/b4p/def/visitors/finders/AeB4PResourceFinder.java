//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/visitors/finders/AeB4PResourceFinder.java,v 1.8 2008/03/14 20:46:52 EWittmann Exp $
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
import java.util.Collections;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.IAeB4PConstants;
import org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef;
import org.activebpel.rt.b4p.def.IAeB4PContext;
import org.activebpel.rt.b4p.def.IAeB4PContextParent;
import org.activebpel.rt.b4p.def.IAeB4PDefConstants;
import org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.AeNotificationDef;
import org.activebpel.rt.ht.def.AeTaskDef;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeExtensionElementDef;
import org.activebpel.rt.xml.def.IAeExtensionObject;
import org.activebpel.rt.xml.def.IAeGetBaseXmlDefAdapter;

/**
 * Visits the model traversing up through the parent hierarchy looking for
 * a specific b4p resource like a logical people group, notification, or task.
 */
public class AeB4PResourceFinder extends AeAbstractB4PDefVisitor
{
   /** constant for the HI def */
   private static final QName sHumanInteractions = new QName(IAeB4PConstants.B4P_NAMESPACE, IAeB4PDefConstants.TAG_HUMAN_INTERACTIONS);

   /** finder we use to find the resource in the human interaction */
   private IAeHumanIteractionDefFinder mFinder;

   /**
    * Find the logical people group with the given name
    * @param aContext
    * @param aLPGName
    */
   public static AeLogicalPeopleGroupDef findLogicalPeopleGroup(AeBaseXmlDef aContext, QName aLPGName)
   {
      AeB4PResourceFinder finder = new AeB4PResourceFinder(new AeLPGFinder(aLPGName));
      aContext.accept(finder);
      return (AeLogicalPeopleGroupDef) finder.getFoundDef();
   }

   /**
    * Finds all of the LPG's that are in scope for the given context.
    * @param aContext
    */
   public static Collection findLogicalPeopleGroups(AeBaseXmlDef aContext)
   {
      return find(aContext, new AeLPGFinder(null));
   }

   /**
    * Finds the task with the given name
    * @param aContext
    * @param aTaskName
    */
   public static AeTaskDef findTask(AeBaseXmlDef aContext, QName aTaskName)
   {
      AeB4PResourceFinder finder = new AeB4PResourceFinder(new AeTaskFinder(aTaskName));
      aContext.accept(finder);
      return (AeTaskDef) finder.getFoundDef();
   }

   /**
    * Finds all of the tasks that are in scope for the given context.
    * @param aContext
    */
   public static Collection findTasks(AeBaseXmlDef aContext)
   {
      return find(aContext, new AeTaskFinder(null));
   }

   /**
    * Finds the notification with the given name
    * @param aContext
    * @param aNotificationName
    */
   public static AeNotificationDef findNotification(AeBaseXmlDef aContext, QName aNotificationName)
   {
      AeB4PResourceFinder finder = new AeB4PResourceFinder(new AeNotificationFinder(aNotificationName));
      aContext.accept(finder);
      return (AeNotificationDef) finder.getFoundDef();
   }

   /**
    * Finds all of the notifications that are in scope for the given context.
    * @param aContext
    */
   public static Collection findNotifications(AeBaseXmlDef aContext)
   {
      return find(aContext, new AeNotificationFinder(null));
   }

   /**
    * Launches the finder with the given context.
    * @param aContext
    * @param aFinderImpl
    */
   private static Collection find(AeBaseXmlDef aContext, IAeHumanIteractionDefFinder aFinderImpl)
   {
      if (aContext == null)
         return Collections.EMPTY_LIST;
      AeB4PResourceFinder finder = new AeB4PResourceFinder(aFinderImpl);
      aContext.accept(finder);
      return finder.getResults();
   }

   /**
    * Ctor
    * @param aFinder
    */
   private AeB4PResourceFinder(IAeHumanIteractionDefFinder aFinder)
   {
      setFinder(aFinder);
   }

   /**
    * Overridden to traverse up the model, stopping to inspect the enclosing scopes
    * and process to see if there is a human interaction def that has the
    * resource(s) we're looking for.
    *
    * @see org.activebpel.rt.xml.def.visitors.AeBaseXmlDefVisitor#visitBaseXmlDef(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   protected void visitBaseXmlDef(AeBaseXmlDef aDef)
   {
      IAeB4PContextParent contextParent = null;

      // If we're running inside the engine, then the defs will eventually hit a
      // context that will bridge the HI defs with the BPEL defs.
      // However, it's possible that we could be searching directly from some
      // BPEL activity during validation (assign or some transition condition)
      // so we still need to account for the scope or process here.
      if (aDef instanceof AeActivityScopeDef || aDef instanceof AeProcessDef)
      {
         // fixme (MF-b4p3) need to check the imports for constellation three
         AeExtensionElementDef extension = aDef.getExtensionElementDef(sHumanInteractions);
         if (extension != null)
         {
            AeB4PHumanInteractionsDef def = getHumanInteractionsDef(extension);
            if (def != null)
            {
               visitHumanInteraction(def);
               contextParent = def;
            }
         }
      }
      // In the designer, the HT defs will be parented by a peopleActivity or
      // a b4p:humanInteraction. In order to bridge these defs with the BPEP
      // models we use a context interface which can report the enclosing HI
      // def (if there is one).
      else if (aDef instanceof IAeB4PContextParent && ((IAeB4PContextParent)aDef).getContext() != null)
      {
         contextParent = (IAeB4PContextParent)aDef;
      }

      // If we encounter a context parent, then we should keep walking this
      // context parent's enclosing HI defs until there are no more HI defs
      // found. At this point, there will be nothing left to traverse.
      if (contextParent != null)
      {
         searchAllEnclosingHumanInteractions(contextParent);
      }
      else if (keepTraversingUp() && aDef.getParentXmlDef() != null)
      {
         aDef.getParentXmlDef().accept(this);
      }
      // fixme (MF-peopleAssignments) need special case for the editing of the b4p people assignments.
   }

   /**
    * Keep traversing up until there's no parent or the finder reports that it's done.
    */
   private boolean keepTraversingUp()
   {
      return !getFinder().isDone();
   }

   /**
    * Searches all of the enclosing human interactions starting with this context parent.
    * @param aContextParent
    */
   protected void searchAllEnclosingHumanInteractions(IAeB4PContextParent aContextParent)
   {
      // keep looping up until there are no more contexts found
      for(IAeB4PContext context = aContextParent.getContext(); context != null && keepTraversingUp();)
      {
         AeB4PHumanInteractionsDef hi = context.getEnclosingHumanInteractionsDef();
         if (hi != null)
         {
            // The context provided has an HI def. Visit this def.
            visitHumanInteraction(hi);
            context = hi.getContext();
         }
         else
         {
            context = null;
         }
      }
   }

   /**
    * Overridden to visit the HI and set a flag to skip its enclosing parent
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AeB4PHumanInteractionsDef)
    */
   public void visit(AeB4PHumanInteractionsDef aDef)
   {
      visitHumanInteraction(aDef);
      super.visit(aDef);
   }

   /**
    * Uses the finder to find the resource on the HI def
    * @param aDef
    */
   protected void visitHumanInteraction(AeB4PHumanInteractionsDef aDef)
   {
      getFinder().find(aDef);
   }

   /**
    * Gets the HI def from the extension.
    * @param aDef
    */
   protected AeB4PHumanInteractionsDef getHumanInteractionsDef(AeExtensionElementDef aDef)
   {
      IAeExtensionObject extObj = (aDef).getExtensionObject();
      if ( extObj != null )
      {
         IAeGetBaseXmlDefAdapter adapter = (IAeGetBaseXmlDefAdapter) extObj.getAdapter(IAeGetBaseXmlDefAdapter.class);
         if (adapter != null)
            return (AeB4PHumanInteractionsDef)adapter.getExtensionAsBaseXmlDef();
      }
      return null;
   }

   /**
    * @return the selector
    */
   protected IAeHumanIteractionDefFinder getFinder()
   {
      return mFinder;
   }

   /**
    * @param aFinder the finder to set
    */
   protected void setFinder(IAeHumanIteractionDefFinder aFinder)
   {
      mFinder = aFinder;
   }

   /**
    * Returns the first result from the list of found defs, or null if nothing
    * was found.
    */
   protected AeBaseXmlDef getFoundDef()
   {
      Collection results = getFinder().getResults();
      if (results.isEmpty())
         return null;
      return (AeBaseXmlDef) results.iterator().next();
   }

   /**
    * Gets the list of results that were found.
    */
   protected Collection getResults()
   {
      return getFinder().getResults();
   }
}
