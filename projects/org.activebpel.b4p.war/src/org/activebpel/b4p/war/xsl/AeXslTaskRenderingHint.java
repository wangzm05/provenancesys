//$Header: /Development/AEDevelopment/projects/org.activebpel.b4p.war/src/org/activebpel/b4p/war/xsl/AeXslTaskRenderingHint.java,v 1.1 2008/01/11 15:05:48 PJayanetti Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.b4p.war.xsl;

/**
 * AeXslTaskRenderingHint struct contains the
 * URIs to the presentation and command processing
 * xsl stylesheets.
 */
public class AeXslTaskRenderingHint
{
   /** XSL stylesheet used to render the task detail presentation. */
   private String mPresentationXslUri;
   /** XSL stylesheet used to generate the task commands from the request params. */
   private String mCommandXslUri;
   
   /**
    * Ctor.
    * @param aPresentationXslUri
    * @param aCommandXslUri
    */
   public AeXslTaskRenderingHint(String aPresentationXslUri, String aCommandXslUri)
   {
      setPresentationXslUri(aPresentationXslUri);
      setCommandXslUri(aCommandXslUri);
   }
   
   /** 
    * @return command xsl uril
    */
   public String getCommandXslUri()
   {
      return mCommandXslUri;
   }
   
   /**
    * Sets the command xsl uri.
    * @param aCommandXslUri
    */
   protected void setCommandXslUri(String aCommandXslUri)
   {
      mCommandXslUri = aCommandXslUri;
   }
   
   /** 
    * @return xsl uri used to render the presentation.
    */
   public String getPresentationXslUri()
   {
      return mPresentationXslUri;
   }
   
   /**
    * Sets the xsl uri used to render the presentation.
    * @param aPresentationXslUri
    */
   protected void setPresentationXslUri(String aPresentationXslUri)
   {
      mPresentationXslUri = aPresentationXslUri;
   }
}
