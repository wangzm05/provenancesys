//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/AeAttachmentPropagationDef.java,v 1.5 2008/02/07 02:07:36 EWittmann Exp $
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
import org.activebpel.rt.util.AeUtil;

/**
 * Impl. for 'attachmentPropagation' element Def.
 */
public class AeAttachmentPropagationDef extends AeB4PBaseDef
{
   /** 'fromProcess' attribute */
   private String mFromProcess;
   /** 'toProcess' attribute */
   private String mToProcess;

   /**
    * @return the fromProcess
    */
   public String getFromProcess()
   {
      return mFromProcess;
   }

   /**
    * @param aFromProcess the fromProcess to set
    */
   public void setFromProcess(String aFromProcess)
   {
      mFromProcess = aFromProcess;
   }

   /**
    * @return the toProcess
    */
   public String getToProcess()
   {
      return mToProcess;
   }

   /**
    * @param aToProcess the toProcess to set
    */
   public void setToProcess(String aToProcess)
   {
      mToProcess = aToProcess;
   }

   /**
    * @see org.activebpel.rt.b4p.def.AeB4PBaseDef#accept(org.activebpel.rt.b4p.def.visitors.IAeB4PDefVisitor)
    */
   public void accept(IAeB4PDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @see org.activebpel.rt.b4p.def.AeB4PBaseDef#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (super.equals(aOther))
      {
         AeAttachmentPropagationDef other = (AeAttachmentPropagationDef) aOther;
         return AeUtil.compareObjects(getFromProcess(), other.getFromProcess()) &&
                AeUtil.compareObjects(getToProcess(), other.getToProcess());
      }
      return false;
   }
}
