package org.activebpel.rt.b4p.impl.lpg;

import java.util.Collection;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.impl.AeHumanInteractionsImpl;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.AeBusinessProcess;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.bpel.impl.activity.IAeExtensionLifecycleAdapter;
import org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Visitor that traverses up the hierarchy in order to find the referenced LPG 
 */
public class AeLogicalPeopleGroupImplFinder extends AeAbstractImplVisitor
{
   /** name of the LPG */
   private QName mName;
   /** LPG that is found. */
   private AeLogicalPeopleGroupImpl mLogicalPeopleGroup;
   
   /**
    * Ctor 
    */
   public AeLogicalPeopleGroupImplFinder()
   {
   }
   
   /**
    * Launches the visitor and returns the LPG that was found or null if not
    * found.
    * @param aBpelObject
    * @param aName
    * @throws AeBusinessProcessException
    */
   public AeLogicalPeopleGroupImpl find(IAeBpelObject aBpelObject, QName aName) throws AeBusinessProcessException
   {
      setName(aName);
      aBpelObject.accept(this);
      return getLogicalPeopleGroup();
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor#visitScope(org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl)
    */
   protected void visitScope(AeActivityScopeImpl aImpl)
         throws AeBusinessProcessException
   {
      findLogicalPeopleGroupExtension(aImpl);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor#visit(org.activebpel.rt.bpel.impl.AeBusinessProcess)
    */
   public void visit(AeBusinessProcess aImpl) throws AeBusinessProcessException
   {
      findLogicalPeopleGroupExtension(aImpl);
   }
   
   /**
    * Looks for the LPG on the extension objects for the given scope/process.
    * @param aObject
    * @throws AeBusinessProcessException
    */
   protected void findLogicalPeopleGroupExtension(AeAbstractBpelObject aObject) throws AeBusinessProcessException
   {
      Collection extensions = aObject.getExtensions();
      if (AeUtil.notNullOrEmpty(extensions))
      {
         for (Iterator it = extensions.iterator(); it.hasNext();)
         {
            IAeExtensionLifecycleAdapter adapter = (IAeExtensionLifecycleAdapter) it.next();
            if (adapter instanceof AeHumanInteractionsImpl)
            {
               AeHumanInteractionsImpl hi = (AeHumanInteractionsImpl) adapter;
               // fixme (MF-b4p3) need to handle imports
               AeLogicalPeopleGroupImpl lpg = hi.getLogicalPeopleGroup(getName().getLocalPart());
               if (lpg != null)
               {
                  setLogicalPeopleGroup(lpg);
                  break;
               }
            }
         }
      }
      
      if (getLogicalPeopleGroup() == null)
      {
         // keep looking
         visitBase(aObject);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.AeAbstractImplVisitor#visitBase(org.activebpel.rt.bpel.impl.AeAbstractBpelObject)
    */
   protected void visitBase(AeAbstractBpelObject aObject)
         throws AeBusinessProcessException
   {
      if (aObject.getParent() != null)
         aObject.getParent().accept(this);
   }

   /**
    * @return the logicalPeopleGroup
    */
   protected AeLogicalPeopleGroupImpl getLogicalPeopleGroup()
   {
      return mLogicalPeopleGroup;
   }

   /**
    * @param aLogicalPeopleGroup the logicalPeopleGroup to set
    */
   protected void setLogicalPeopleGroup(
         AeLogicalPeopleGroupImpl aLogicalPeopleGroup)
   {
      mLogicalPeopleGroup = aLogicalPeopleGroup;
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
}