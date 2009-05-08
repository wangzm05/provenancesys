//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/task/data/AeInterfaceMetadata.java,v 1.1 2008/02/16 22:29:49 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2004-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.task.data;

import java.util.List;

import org.activebpel.rt.message.AeMessagePartsMap;

/**
 * This class models interfaceMetadata element in the taskLifeCycle request
 */
public class AeInterfaceMetadata
{
   /** describes the input message */
   private AeMessagePartsMap mInput;
   /** describes the output message */
   private AeMessagePartsMap mOutput;
   /** Fault part names */
   private List mFaultNames;
   
   /**
    * @return the faultNames
    */
   public List getFaultNames()
   {
      return mFaultNames;
   }
   /**
    * @param aFaultNames the faultNames to set
    */
   public void setFaultNames(List aFaultNames)
   {
      mFaultNames = aFaultNames;
   }
   /**
    * @return the input
    */
   public AeMessagePartsMap getInput()
   {
      return mInput;
   }
   /**
    * @param aInput the input to set
    */
   public void setInput(AeMessagePartsMap aInput)
   {
      mInput = aInput;
   }
   /**
    * @return the output
    */
   public AeMessagePartsMap getOutput()
   {
      return mOutput;
   }
   /**
    * @param aOutput the output to set
    */
   public void setOutput(AeMessagePartsMap aOutput)
   {
      mOutput = aOutput;
   }
}
