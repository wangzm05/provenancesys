//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/AeWorkflowApplicationShutdownListenerRegistry.java,v 1.1 2008/01/11 15:05:45 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * AeWorkflowApplicationShutdownListenerRegistry maintainser a list
 * of listeners that require notification when the application
 * is shutdown. 
 */
public class AeWorkflowApplicationShutdownListenerRegistry
{
   /** List of listeners. */
   private List mListeners = new ArrayList();
   
   /**
    * Adds a listner.
    * @param aListener
    */
   public synchronized void addListener(IAeWorkflowApplicationShutdownListener aListener)
   {
      if (aListener != null && !mListeners.contains(aListener))
      {
         mListeners.add(aListener);
      }
   }
   
   /**
    * Notifies all listeners of a shutdown.
    */
   public synchronized void notifyShutdown()
   {
      Iterator it = mListeners.iterator();
      while (it.hasNext())
      {
         ((IAeWorkflowApplicationShutdownListener) it.next()).onWorkflowApplicationShutdown();
      }
   }
}
