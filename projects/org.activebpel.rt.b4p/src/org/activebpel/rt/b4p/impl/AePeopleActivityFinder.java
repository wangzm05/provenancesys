//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/AePeopleActivityFinder.java,v 1.4 2008/03/03 01:36:46 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.AeBpelState;
import org.activebpel.rt.bpel.impl.AeBusinessProcess;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.AeActivityChildExtensionActivityImpl;
import org.activebpel.rt.bpel.impl.activity.AeActivityFlowImpl;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.bpel.impl.activity.AeActivitySequenceImpl;
import org.activebpel.rt.bpel.impl.activity.IAeActivityLifeCycleAdapter;
import org.activebpel.rt.bpel.impl.activity.support.AeCompensationHandler;
import org.activebpel.rt.bpel.impl.activity.support.AeDefaultFaultHandler;
import org.activebpel.rt.bpel.impl.activity.support.AeEventHandlersContainer;
import org.activebpel.rt.bpel.impl.activity.support.AeFaultHandler;
import org.activebpel.rt.bpel.impl.activity.support.AeTerminationHandler;
import org.activebpel.rt.bpel.impl.activity.support.AeWSBPELFaultHandler;
import org.activebpel.rt.bpel.impl.visitors.AeImplTraversingVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Visitor that traverses bottomup inside sequence and flow containers to find 
 * people activity
 */
public class AePeopleActivityFinder extends AeImplTraversingVisitor
{
   // fixme (MF) should try to leverage some code between the def and impl finders
   
   /** people activity name */
   private String mPeopleActivityName;
   /** people activity impl */
   private AePeopleActivityImpl mPeopleActivityImpl;
   /** determines the direction of the traversal */
   private boolean mTraverseUp;
   /** controls whether we traverse into scopes looking for the people activity */
   private boolean mTraverseIntoScope;
   /** set of objects that we've already traversed into */
   private Set mTraversedInto;
   
   /**
    * C'tor
    */
   public AePeopleActivityFinder()
   {
      setTraverseUp(true);
      setTraversedInto(new HashSet());
   }
   
   /**
    * Launches the visitor and returns the People Activity Impl that was found or null if not
    * found.
    * @param aBpelObject
    * @param aName
    * @throws AeBusinessProcessException
    */
   public static AePeopleActivityImpl find(IAeBpelObject aBpelObject, String aName) throws AeBusinessProcessException
   {
      AePeopleActivityFinder finder = new AePeopleActivityFinder();
      finder.setPeopleActivityName(aName);
      aBpelObject.accept(finder);
      return finder.getPeopleActivityImpl();
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor#visit(org.activebpel.rt.bpel.impl.activity.AeActivitySequenceImpl)
    */
   public void visit(AeActivitySequenceImpl aImpl) throws AeBusinessProcessException
   {
      if (isTraverseUp())
      {
         visitChildren(aImpl);
      }
      super.visit(aImpl);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor#visit(org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl)
    */
   public void visit(AeActivityScopeImpl aImpl) throws AeBusinessProcessException
   {
      if (isTraverseUp() && isTraverseIntoScope())
      {
         setTraverseIntoScope(false);
         traverseInto(aImpl);
      }
      super.visit(aImpl);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor#visit(org.activebpel.rt.bpel.impl.AeBusinessProcess)
    */
   public void visit(AeBusinessProcess aImpl) throws AeBusinessProcessException
   {
      if (isTraverseUp() && isTraverseIntoScope())
      {
         setTraverseIntoScope(false);
         traverseInto(aImpl);
      }
      super.visit(aImpl);
   }

   /**
    * Traverses into the impl
    * @param aImpl
    * @throws AeBusinessProcessException
    */
   protected void traverseInto(IAeBpelObject aImpl)
         throws AeBusinessProcessException
   {
      setTraverseUp(false);
      aImpl.accept(this);
      setTraverseUp(true);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor#visit(org.activebpel.rt.bpel.impl.activity.support.AeDefaultFaultHandler)
    */
   public void visit(AeDefaultFaultHandler aImpl)
         throws AeBusinessProcessException
   {
      if (isTraverseUp())
      {
         setTraverseIntoScope(true);
      }
      super.visit(aImpl);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor#visit(org.activebpel.rt.bpel.impl.activity.support.AeFaultHandler)
    */
   public void visit(AeFaultHandler aImpl) throws AeBusinessProcessException
   {
      if (isTraverseUp())
      {
         setTraverseIntoScope(true);
      }
      super.visit(aImpl);
   }
   

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor#visit(org.activebpel.rt.bpel.impl.activity.support.AeWSBPELFaultHandler)
    */
   public void visit(AeWSBPELFaultHandler aImpl)
         throws AeBusinessProcessException
   {
      if (isTraverseUp())
      {
         setTraverseIntoScope(true);
      }
      super.visit(aImpl);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor#visit(org.activebpel.rt.bpel.impl.activity.support.AeCompensationHandler)
    */
   public void visit(AeCompensationHandler aImpl)
         throws AeBusinessProcessException
   {
      if (isTraverseUp())
      {
         setTraverseIntoScope(true);
      }
      super.visit(aImpl);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor#visit(org.activebpel.rt.bpel.impl.activity.support.AeEventHandlersContainer)
    */
   public void visit(AeEventHandlersContainer aImpl)
         throws AeBusinessProcessException
   {
      if (isTraverseUp())
      {
         setTraverseIntoScope(true);
      }
      super.visit(aImpl);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor#visit(org.activebpel.rt.bpel.impl.activity.support.AeTerminationHandler)
    */
   public void visit(AeTerminationHandler aImpl)
         throws AeBusinessProcessException
   {
      if (isTraverseUp())
      {
         setTraverseIntoScope(true);
      }
      super.visit(aImpl);
   }

   /**
    * Overrides method to 
    * @see org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor#visit(org.activebpel.rt.bpel.impl.activity.AeActivityFlowImpl)
    */
   public void visit(AeActivityFlowImpl aImpl) throws AeBusinessProcessException
   {
      if (isTraverseUp())
      {
         visitChildren(aImpl);
      }
      super.visit(aImpl);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor#visit(org.activebpel.rt.bpel.impl.activity.AeActivityChildExtensionActivityImpl)
    */
   public void visit(AeActivityChildExtensionActivityImpl aImpl) throws AeBusinessProcessException
   {
      IAeActivityLifeCycleAdapter adapter = aImpl.getLifeCycleAdapter();
      if ((adapter != null) && (adapter instanceof AePeopleActivityImpl))
      {
         String paName = ((AePeopleActivityImpl) adapter).getDef().getName();
         if (paName.equals(getPeopleActivityName()))
         {
            setPeopleActivityImpl((AePeopleActivityImpl)adapter);
         }
      }
      super.visit(aImpl);
   }

   protected void visitChildren(IAeBpelObject aBpelObject) throws AeBusinessProcessException
   {
      // fixme (P) this case handles sequences well but not control depedencies within a <flow>. This seems like an issue with the b4p spec.
      // The spec should be more explicit regarding how the PA is resolved with respect to concurrent PA's and amiguous names.
      List list = AeUtil.toList(aBpelObject.getChildrenForStateChange());
      Collections.reverse(list);
      for (Iterator iterator = list.iterator(); iterator.hasNext();)
      {
         IAeBpelObject child = (IAeBpelObject)iterator.next();
         if (child.getState() == AeBpelState.FINISHED)
         {
            traverseInto(child);
            
            if (isFound())
               break;
         }
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor#visitBase(org.activebpel.rt.bpel.impl.AeAbstractBpelObject)
    */
   protected void visitBase(AeAbstractBpelObject aObject) throws AeBusinessProcessException
   {
      if (!isFound())
      {
         if (isTraverseUp())
         {
            if (aObject.getParent() != null)
               aObject.getParent().accept(this);
         }
         else if (notAlreadyTraversedInto(aObject))
         {
            super.visitBase(aObject);
         }
      }
   }

   /**
    * Returns true if this object hasn't already been traversed
    * @param aObject
    */
   protected boolean notAlreadyTraversedInto(AeAbstractBpelObject aObject)
   {
      return getTraversedInto().add(aObject);
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
    * @return the peopleActivityImpl
    */
   protected AePeopleActivityImpl getPeopleActivityImpl()
   {
      return mPeopleActivityImpl;
   }
   /**
    * @param aPeopleActivityImpl the peopleActivityImpl to set
    */
   protected void setPeopleActivityImpl(AePeopleActivityImpl aPeopleActivityImpl)
   {
      mPeopleActivityImpl = aPeopleActivityImpl;
   }
   
   /**
    * Returns true if people activity impl is found
    */
   protected boolean isFound()
   {
      return getPeopleActivityImpl() != null;
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
