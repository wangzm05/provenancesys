//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/web/bean/AeTaskListResult.java,v 1.4 2008/02/27 19:20:21 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.web.bean;

import java.util.Collections;
import java.util.Map;

import org.activebpel.b4p.war.service.AeGetTasksSerializer;
import org.activebpel.rt.ht.api.IAeHtWsIoConstants;
import org.activebpel.rt.util.AeXPathUtil;
import org.w3c.dom.Element;

/**
 * Bean that represents the taskService wsdl tTaskListResult schema type.
 */
public class AeTaskListResult
{
   /** Result set start index. */
   private int mStartIndex;
   /** Total returned in the result set. */
   private int mCount;
   /** Total tasks that matched the request criteria. */
   private int mTotalCount;
   /** page size */
   private int mPageSize;
   /** Response element - htdt:getMyTaskAbstractsResponse or htdt:getMyTasksResponse */
   private Element mGetTasksResponseElement;

   /**
    * Default ctor for jsp bean.
    */
   public AeTaskListResult()
   {
   }

   /**
    * Default ctor for jsp bean.
    */
   public AeTaskListResult(Element aGetTasksResponse, int aStartIndex, int aPageSize)
   {
      setGetTasksResponseElement(aGetTasksResponse);
      setStartIndex(aStartIndex);
      try
      {
         int count = AeXPathUtil.selectNodes(aGetTasksResponse, "//htdt:taskAbstract", "htdt", IAeHtWsIoConstants.WSHT_API_XSD_NAMESPACE).size();  //$NON-NLS-1$//$NON-NLS-2$
         setCount(count);
         Map nsMap = Collections.singletonMap(AeGetTasksSerializer.TASK_STATE_WSDL_PREFIX, AeGetTasksSerializer.TASK_STATE_WSDL_NS);
         int totalCount = AeXPathUtil.selectInt(aGetTasksResponse, "tsst:taskTotalCount", nsMap); //$NON-NLS-1$
         setTotalCount(totalCount);
         setPageSize(aPageSize);
      }
      catch(Exception e)
      {

      }
   }

   /**
    * @return Task list response element - htdt:getMyTaskAbstractsResponse or htdt:getMyTasksResponse
    */
   public Element getGetTasksResponseElement()
   {
      return mGetTasksResponseElement;
   }

   /**
    * Set the list response element - htdt:getMyTaskAbstractsResponse or htdt:getMyTasksResponse
    * @param aGetTasksResponseElement task list response element
    */
   protected void setGetTasksResponseElement(Element aGetTasksResponseElement)
   {
      mGetTasksResponseElement = aGetTasksResponseElement;
   }

   /**
    * @return the count
    */
   public int getCount()
   {
      return mCount;
   }

   /**
    * @param aCount the count to set
    */
   public void setCount(int aCount)
   {
      mCount = aCount;
   }

   /**
    * @return the startIndex
    */
   public int getStartIndex()
   {
      return mStartIndex;
   }

   /**
    * @param aStartIndex the startIndex to set
    */
   public void setStartIndex(int aStartIndex)
   {
      mStartIndex = aStartIndex;
   }

   /**
    * @return the pageSize
    */
   public int getPageSize()
   {
      return mPageSize;
   }

   /**
    * @param aPageSize the pageSize to set
    */
   public void setPageSize(int aPageSize)
   {
      mPageSize = aPageSize;
   }

   /**
    * @return the totalCount
    */
   public int getTotalCount()
   {
      return mTotalCount;
   }

   /**
    * @param aTotalCount the totalCount to set
    */
   public void setTotalCount(int aTotalCount)
   {
      mTotalCount = aTotalCount;
   }

   /**
    * Returns true if there are more results to be returned.
    * @return true if total returned is less than total count.
    */
   public boolean isHasPrevious()
   {
      return getStartIndex() > 0;
   }

   /**
    * @return the previous start position.
    */
   public int getPreviousStartIndex()
   {
      int idx =  getStartIndex() - getPageSize();
      return idx >= 0 ? idx : 0;
   }

   /**
    * Returns true if there are more results to be returned.
    * @return true if total returned is less than total count.
    */
   public boolean isHasNext()
   {
      return getNextStartIndex() < getTotalCount();
   }

   /**
    * @return the next start position.
    */
   public int getNextStartIndex()
   {
      return getStartIndex() + getPageSize();
   }

   /**
    * Returns the display start index.
    * @return from index
    */
   public int getDisplayFromIndex()
   {
      return getStartIndex() + 1;
   }

   /**
    * Returns the display end index.
    * @return end index
    */
   public int getDisplayToIndex()
   {
      return getStartIndex() + getCount();
   }
}
