// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/AeB4PProblemHandler.java,v 1.3 2008/01/31 23:42:50 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.def.AeB4PBaseDef;
import org.activebpel.rt.b4p.def.visitors.finders.AeB4PDefFindVisitor;
import org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter;
import org.activebpel.rt.wsresource.validation.IAeWSResourceProblemHandler;
import org.activebpel.rt.wsresource.validation.IAeWSResourceValidationPreferences;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.xml.sax.SAXParseException;

/**
 * Problem handler that acts as a proxy to the IAeBaseErrorReporter in order to 
 * report problems found by the AeWSResourceValidationEngine to the existing BPEL
 * process validation error reporter.
 */
public class AeB4PProblemHandler implements IAeWSResourceProblemHandler
{
   /** The error reporter for the BPEL process */
   private IAeBaseErrorReporter mBPELErrorReporter;
   /** The def in which this problem reporter will be reporting problems for. */
   private AeB4PBaseDef mDef;
   /** problem message format for reporting */
   private String mMessageFormat = "{0}"; //$NON-NLS-1$
   
   /**
    * C'tor
    * @param aErrorReporter
    */
   public AeB4PProblemHandler(AeB4PBaseDef aDef, IAeBaseErrorReporter aErrorReporter)
   {
      setBPELErrorReporter(aErrorReporter);
      setDef(aDef);
   }

   /**
    * @see org.activebpel.rt.wsresource.validation.IAeWSResourceProblemHandler#reportFatalError(java.lang.Exception)
    */
   public void reportFatalError(Exception aException)
   {
      getBPELErrorReporter().addError("Error: {0}", new Object[] {aException.getMessage()}, getDef()); //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.wsresource.validation.IAeWSResourceProblemHandler#reportParseProblem(org.xml.sax.SAXParseException, int)
    */
   public void reportParseProblem(SAXParseException aException, int aSeverity)
   {
      getBPELErrorReporter().addError(aException.getMessage(), new Object[]{}, getDef());
   }

   /**
    * @see org.activebpel.rt.wsresource.validation.IAeWSResourceProblemHandler#reportProblem(javax.xml.namespace.QName, int, java.lang.String, java.lang.String)
    */
   public void reportProblem(QName aProblemId, int aSeverity, String aProblemMessage, String aXPath)
   {
      AeB4PDefFindVisitor finder = getLocationNode(aXPath);
      String[] args = new String[3];
      
      args[0] = aProblemMessage;
      args[1] = aProblemId.getLocalPart();
      args[2] = aProblemId.getNamespaceURI();
      
      reportError(getMessageFormat(), aSeverity, args, finder.getFoundDef());
   }
   
   /**
    * Report the error to the b4p error reporter.
    * 
    * @param aMessage
    * @param aSeverity
    * @param aProblemArgs
    * @param aNode
    */
   private void reportError(String aMessage, int aSeverity, Object[] aProblemArgs, Object aNode)
   {
      switch(aSeverity)
      {
         case(IAeWSResourceValidationPreferences.SEVERITY_WARNING ):
            getBPELErrorReporter().addWarning(aMessage, aProblemArgs, aNode);       
            break;
         case(IAeWSResourceValidationPreferences.SEVERITY_INFO ):
            getBPELErrorReporter().addInfo(aMessage, aProblemArgs, aNode);
            break;
         case(IAeWSResourceValidationPreferences.SEVERITY_ERROR ):
            getBPELErrorReporter().addError(aMessage, aProblemArgs, aNode);
            break;
         default:
            break;
      }
   }
   
   /**
    * Find the node at the location path or the nearest parent
    * 
    * @param aXPath
    * @return
    */
   private AeB4PDefFindVisitor getLocationNode(String aXPath)
   {
      String locationPath = aXPath;
      AeB4PDefFindVisitor finder = new AeB4PDefFindVisitor(locationPath);
      if( getDef() != null)
      {
         AeBaseXmlDef def = finder.getFoundDef();
         while (def == null)
         {
            getDef().accept(finder);
            def = finder.getFoundDef();
            int lastIdx = locationPath.lastIndexOf('/');
            locationPath = locationPath.substring(0, lastIdx);
            finder.setPath(locationPath);
         }
      }
      return finder;
   }

   /**
    * @return Returns the b4PErrorReporter.
    */
   protected IAeBaseErrorReporter getBPELErrorReporter()
   {
      return mBPELErrorReporter;
   }

   /**
    * @param aErrorReporter the b4PErrorReporter to set
    */
   protected void setBPELErrorReporter(IAeBaseErrorReporter aErrorReporter)
   {
      mBPELErrorReporter = aErrorReporter;
   }

   /**
    * @return Returns the def.
    */
   protected AeB4PBaseDef getDef()
   {
      return mDef;
   }

   /**
    * @param aDef the def to set
    */
   protected void setDef(AeB4PBaseDef aDef)
   {
      mDef = aDef;
   }

   /**
    * 
    * @return set the message format
    */
   public String getMessageFormat()
   {
      return mMessageFormat;
   }

   /**
    * 
    * @param aMessageFormat get the message format
    */
   public void setMessageFormat(String aMessageFormat)
   {
      mMessageFormat = aMessageFormat;
   }

}
