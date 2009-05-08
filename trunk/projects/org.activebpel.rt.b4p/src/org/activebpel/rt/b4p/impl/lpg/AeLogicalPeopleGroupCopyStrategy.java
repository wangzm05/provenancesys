package org.activebpel.rt.b4p.impl.lpg;

import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.activity.assign.AeMismatchedAssignmentException;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation;
import org.activebpel.rt.bpel.impl.activity.assign.IAeExtensionCopyStrategy;
import org.w3c.dom.Element;

/**
 * Strategy for copying data into an LPG 
 */
public class AeLogicalPeopleGroupCopyStrategy implements IAeExtensionCopyStrategy
{
   /** LPG that we're copying data into */
   private AeLogicalPeopleGroupImpl mLogicalPeopleGroup;
   
   /**
    * Ctor accepts the LPG
    * @param aLogicalPeopleGroup
    */
   public AeLogicalPeopleGroupCopyStrategy(AeLogicalPeopleGroupImpl aLogicalPeopleGroup)
   {
      setLogicalPeopleGroup(aLogicalPeopleGroup);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeExtensionCopyStrategy#copy(org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation, java.lang.Object)
    */
   public void copy(IAeCopyOperation aCopyOperation, Object aFromData) throws AeBpelException
   {
      if (aFromData instanceof Element)
      {
         mLogicalPeopleGroup.assign((Element) aFromData);
      }
      else
      {
         throw new AeMismatchedAssignmentException(aCopyOperation.getContext().getBPELNamespace());
      }
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
   protected void setLogicalPeopleGroup(AeLogicalPeopleGroupImpl aLogicalPeopleGroup)
   {
      mLogicalPeopleGroup = aLogicalPeopleGroup;
   }
   
}