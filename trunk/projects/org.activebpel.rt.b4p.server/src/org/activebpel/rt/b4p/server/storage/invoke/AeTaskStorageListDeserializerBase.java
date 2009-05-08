//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/storage/invoke/AeTaskStorageListDeserializerBase.java,v 1.4.2.1 2008/04/21 16:08:59 ppatruni Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.storage.invoke;

import java.util.LinkedHashSet;
import java.util.Set;

import org.activebpel.rt.AeException;
import org.activebpel.rt.ht.api.AeGetTasksParam;
import org.activebpel.rt.ht.api.io.AeGetMyTaskAbstractsDeserializer;
import org.activebpel.rt.util.AeUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Base deserializer classes used by tss:listTasks and listTaskAbstracts.
 */
public abstract class AeTaskStorageListDeserializerBase extends AeTaskStorageDeserializerBase
{

   /** Principal name */
   private String mPrincipal;

   /** Request filtering parameters */
   private AeGetTasksParam mGetTasksParam;

   /** Set of access roles */
   private Set mRoles;

   /**
    * Ctor.
    * @param aListTaskDocument
    */
   public AeTaskStorageListDeserializerBase(Document aListTaskDocument)
   {
      setElement(aListTaskDocument.getDocumentElement());
   }

   /**
    * Returns principal name.
    */
   public String getPrincipal()
   {
      if (mPrincipal == null)
      {
         mPrincipal = getText(getElement(), "tss:principal"); //$NON-NLS-1$
      }
      return mPrincipal;
   }

   /**
    * Returns the ws-ht request element such the htdt:getMyTaskAbstracts or htdt:getMyTasks element.
    * @return ws-ht request element
    */
   protected abstract Element getHtdTaskListElement()  throws AeException;

   /**
    * Deserialize and returns the wsht request data
    * @throws AeException
    */
   protected AeGetTasksParam deserializeTasksParam() throws Exception
   {
      AeGetMyTaskAbstractsDeserializer des = new AeGetMyTaskAbstractsDeserializer( getHtdTaskListElement() );
      return des.getGetTasksParam();
   }

   /**
    * Deserializes &lt;tsst:taskIndexOffset&gt; value and sets it in the aTaskParam.
    * @param aTaskParam
    * @throws Exception
    */
   protected void deserializeTaskIndexOffset(AeGetTasksParam aTaskParam) throws Exception
   {
      int index = AeUtil.parseInt(getText( getElement(), "tsst:taskIndexOffset"), 0); //$NON-NLS-1$
      aTaskParam.setTaskIndexOffset(index);
   }

   /**
    * Deserializes &lt;tsst:orderBy&gt; value and sets it in the aTaskParam.
    * @param aTaskParam
    * @throws Exception
    */
   protected void deserializeOrderBy(AeGetTasksParam aTaskParam) throws Exception
   {
      Set orderBys = new LinkedHashSet();
      orderBys.addAll( getTextList(getElement(), "//tsst:orderBy/tsst:fieldId") ); //$NON-NLS-1$
      aTaskParam.setOrderBys(orderBys);
   }

   /**
    * Deserializes &lt;tsst:searchBy&gt; value and sets it in the aTaskParam.
    * @param aTaskParam
    * @throws Exception
    */
   protected void deserializeSearchBy(AeGetTasksParam aTaskParam) throws Exception
   {
      aTaskParam.setSearchBy( getText(getElement(), "tsst:searchBy")  ); //$NON-NLS-1$
   }
   
   /**
    * Returns request data
    */
   public AeGetTasksParam getTasksParam() throws AeException
   {
      if (mGetTasksParam == null)
      {
         try
         {
            // deserialize  ht element.
            mGetTasksParam = deserializeTasksParam();
            // ae specific items - startIndex and col.sort orders
            deserializeTaskIndexOffset(mGetTasksParam);
            deserializeOrderBy(mGetTasksParam);
            deserializeSearchBy(mGetTasksParam);
         }
         catch(AeException aex)
         {
            throw aex;
         }
         catch(Exception e)
         {
            throw new AeException(e);
         }
      }
      return mGetTasksParam;
   }

   /**
    * @return List of roles.
    * @throws AeException
    */
   public Set getRoles() throws AeException
   {
      if (mRoles == null)
      {
         mRoles = new LinkedHashSet();
         mRoles.addAll( getTextList(getElement(), "//tss:roleList/tss:role") ); //$NON-NLS-1$
      }
      return mRoles;
   }

}
