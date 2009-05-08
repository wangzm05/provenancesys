//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/impl/lpg/AeConvertLPGToLiteral.java,v 1.4 2008/02/21 17:14:57 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the 
//proprietary property of Active Endpoints, Inc.  Viewing or use of 
//this information is prohibited without the express written consent of 
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT 
//is strictly forbidden. Copyright (c) 2002-2007 All rights reserved. 
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.impl.lpg; 

import java.util.Map;

import org.activebpel.rt.ht.def.AeEscalationDef;
import org.activebpel.rt.ht.def.AeFromDef;
import org.activebpel.rt.ht.def.AeLiteralDef;
import org.activebpel.rt.ht.def.AeLogicalPeopleGroupDef;
import org.activebpel.rt.ht.def.visitors.AeAbstractTraversingHtDefVisitor;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.w3c.dom.Element;

/**
 * Visits the def and inlines any LPG that are within escalations.
 */
public class AeConvertLPGToLiteral extends AeAbstractTraversingHtDefVisitor
{
   /** map of paths to LPG orgEntity values */
   private Map mLogicalPeopleGroups;
   /** true if we're nested within an escalation */
   private boolean mInEscalation;
   
   /**
    * Converts the LPG's in escalations from logical people groups to literals
    * @param aLogicalPeopleGroups
    * @param aDef
    */
   public static void convert(Map aLogicalPeopleGroups, AeBaseXmlDef aDef)
   {
      new AeConvertLPGToLiteral(aLogicalPeopleGroups, aDef);
   }
   
   /**
    * Accepts map of LPG and base def to visit.
    * @param aLogicalPeopleGroups
    * @param aDef
    */
   private AeConvertLPGToLiteral(Map aLogicalPeopleGroups, AeBaseXmlDef aDef)
   {
      setLogicalPeopleGroups(aLogicalPeopleGroups);
      
      aDef.accept(this);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeEscalationDef)
    */
   public void visit(AeEscalationDef aDef)
   {
      setInEscalation(true);
      super.visit(aDef);
      setInEscalation(false);
   }

   /**
    * @see org.activebpel.rt.ht.def.visitors.AeAbstractHtDefVisitor#visit(org.activebpel.rt.ht.def.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      if (aDef.isLPG() && isInEscalation())
      {
         // replace the LPG with the literal results, but only for an escalation
         aDef.setLogicalPeopleGroup(null);
         aDef.getArgumentDefs().clear();
         
         Element orgEntity = getOrgEntity(aDef.getInlineLogicalPeopleGroupDef()); 

         AeLiteralDef literalDef = new AeLiteralDef();
         aDef.setLiteral(literalDef);
         literalDef.addChildNode(orgEntity);
      }
      super.visit(aDef);
   }

   /**
    * @return the inEscalation
    */
   protected boolean isInEscalation()
   {
      return mInEscalation;
   }

   /**
    * @param aInEscalation the inEscalation to set
    */
   protected void setInEscalation(boolean aInEscalation)
   {
      mInEscalation = aInEscalation;
   }
   
   protected Element getOrgEntity(AeLogicalPeopleGroupDef aDef)
   {
      return (Element) getLogicalPeopleGroups().get(aDef);
   }

   /**
    * @return the logicalPeopleGroups
    */
   protected Map getLogicalPeopleGroups()
   {
      return mLogicalPeopleGroups;
   }

   /**
    * @param aLogicalPeopleGroups the logicalPeopleGroups to set
    */
   protected void setLogicalPeopleGroups(Map aLogicalPeopleGroups)
   {
      mLogicalPeopleGroups = aLogicalPeopleGroups;
   }

}
 