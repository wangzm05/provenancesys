// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.ht/src/org/activebpel/rt/ht/def/validation/rules/AePresentationElementValidator.java,v 1.2 2008/02/15 17:40:57 EWittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.ht.def.validation.rules;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.activebpel.rt.ht.AeMessages;
import org.activebpel.rt.ht.def.AePresentationElementDef;
import org.activebpel.rt.ht.def.AePresentationElementsDef;
import org.activebpel.rt.ht.def.AePresentationParameterDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;

/**
 * Any parameters must resolve to the parameter bindings
 */
public class AePresentationElementValidator extends AeAbstractHtValidator
{
   /** regex pattern for valid NCName parameter variables in presentation elements */
   private static Pattern mPattern = Pattern.compile("[^\\{]\\{\\$(" + AeXmlUtil.NCNAME_PATTERN + ")\\}"); //$NON-NLS-1$ //$NON-NLS-2$
   
   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AePresentationElementDef aDef)
   {
      String defText = aDef.getTextValue();
      if(AeUtil.notNullOrEmpty(defText))
      {
         Set params = new HashSet();
         AePresentationElementsDef parent = (AePresentationElementsDef) aDef.getParentDef();
         
         Set vars = findVariables(aDef.getTextValue());
         
         if (parent.getPresentationParameters() != null)
         {
            for (Iterator iter = parent.getPresentationParameters().getPresentationParameterDefs(); iter.hasNext();)
            {
               AePresentationParameterDef param = (AePresentationParameterDef) iter.next();
               params.add(param.getName());
            }
         }
         
         for (Iterator iter = vars.iterator(); iter.hasNext();)
         {
            if (!params.contains((String) iter.next()))
            {
               reportProblem(AeMessages.getString("AePresentationElementValidator.0"), aDef); //$NON-NLS-1$
               break;
            }
         }
      }
   }
   
   /**
    * Find all variables (i.e. $variableName$) in the supplied string and return a list.
    * @param aString
    * @return set of all the variables found.
    */
   protected Set findVariables(String aString)
   {
      Set found = new HashSet();
      
      Matcher matcher = mPattern.matcher(aString);
      
      while (matcher.find())
      {
         found.add(matcher.group(1));
      }
      
      return found;
   }
   
}
