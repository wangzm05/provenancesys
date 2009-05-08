// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/validation/rules/AePeopleActivityDefRule59Validator.java,v 1.3 2008/03/15 22:18:35 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.validation.rules;

import javax.xml.namespace.QName;

import org.activebpel.rt.b4p.AeMessages;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.visitors.preprocess.strategies.wsio.IAeMessageDataStrategyNames;
import org.activebpel.rt.message.AeMessagePartsMap;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;

/**
 * valid message producer/consumer strategy
 */
public class AePeopleActivityDefRule59Validator extends AeAbstractB4PValidator
{
   /**
    * @see org.activebpel.rt.b4p.def.visitors.AeAbstractTraversingB4PDefVisitor#visit(org.activebpel.rt.b4p.def.AePeopleActivityDef)
    */
   public void visit(AePeopleActivityDef aDef)
   {
      executeRule(aDef);
      super.visit(aDef);
   }

   /**
    * rule logic
    * 
    * @param aDef
    */
   protected void executeRule(AePeopleActivityDef aDef)
   {
      boolean hasConsumerStrategy = true;
      boolean hasProducerStrategy = true;
      
      // if the consumer strategy is an empty message with zero parts then the strategy is valid
      
      if (IAeMessageDataStrategyNames.EMPTY_MESSAGE.equals(aDef.getMessageDataConsumerStrategy()))
      {
         hasConsumerStrategy = getPartsCount(aDef.getConsumerMessagePartsMap()) == 0;
      }
      
      // if the producer strategy is an empty message with zero parts then the strategy is valid
      
      if (IAeMessageDataStrategyNames.EMPTY_MESSAGE.equals(aDef.getMessageDataProducerStrategy()))
      {
         hasProducerStrategy = getPartsCount(aDef.getProducerMessagePartsMap()) == 0;
      }
   
      // for people activity tasks, there must be a producer/consumer strategy
      
      if (aDef.isTask())
      {
         if ( AeUtil.isNullOrEmpty(aDef.getMessageDataConsumerStrategy()) || 
              AeUtil.isNullOrEmpty(aDef.getMessageDataProducerStrategy()) ||
              !(hasConsumerStrategy || hasProducerStrategy))
         {
            reportProblem(AeMessages.getString("AePeopleActivityDefRule59Validator.0"), aDef); //$NON-NLS-1$
         }
         else
         {
            // The logic is:
            // 1) Get the def of the variable
            // 2a) If strategy is element then variable def must be same element (Singlepartelement)
            // 2b) If stragegy is msg then variable must be message and must match messagepartsmap message type (QName)
            // 3) Repeat for output
            
            // check for to part from parts on people activities
            
            QName inputVar = getTypeQName(aDef, false);
            QName outputVar = getTypeQName(aDef, true);
            
            // allow to-parts and from-parts to disable this validation, the other validator will handle it.
            boolean producerOk = AeUtil.notNullOrEmpty(inputVar);
            producerOk |= AeUtil.compareObjects(IAeMessageDataStrategyNames.TO_PARTS, aDef.getMessageDataProducerStrategy());
            
            boolean consumerOk = AeUtil.notNullOrEmpty(outputVar);
            consumerOk |= AeUtil.compareObjects(IAeMessageDataStrategyNames.FROM_PARTS, aDef.getMessageDataConsumerStrategy());
            
            if (!(producerOk && consumerOk))
            {
               reportProblem(AeMessages.getString("AePeopleActivityDefRule59Validator.0"), aDef); //$NON-NLS-1$
            }
         }
      }
      
      // for people activity notifications, there must be a producer strategy
      
      if (aDef.isNotification())
      {
         if (AeUtil.isNullOrEmpty(aDef.getMessageDataProducerStrategy()) || !hasProducerStrategy)
         {
            reportProblem(AeMessages.getString("AePeopleActivityDefRule59Validator.1"), aDef); //$NON-NLS-1$
         }
         else
         {
            // check to make sure the input variable is valid for the port type
            QName inputVar = getTypeQName(aDef, false);
            
            //boolean notifInputValid = isPortTypeValid(aDef.getProducerPortType(), aDef.getProducerOperation(), inputVar, null, strategy);
            if (inputVar == null && !AeUtil.compareObjects(IAeMessageDataStrategyNames.TO_PARTS, aDef.getMessageDataProducerStrategy()))
            {
               reportProblem(AeMessages.getString("AePeopleActivityDefRule59Validator.1"), aDef); //$NON-NLS-1$
            }
         }
      }
   }
   
   /**
    * Return the type of the element or message of a variable def
    * 
    * @param aStrategy
    * @param aPartMap
    */
   private QName getTypeQName(AePeopleActivityDef aDef, boolean aConsumer)
   {
      QName type = null;
      
      AeMessagePartsMap partMap = aConsumer ? aDef.getConsumerMessagePartsMap() : aDef.getProducerMessagePartsMap();
      
      if (partMap == null)
         return type;
      
      AeVariableDef variable = aConsumer ? aDef.getMessageDataConsumerVariable() : aDef.getMessageDataProducerVariable();
      String strategy = aConsumer ? aDef.getMessageDataConsumerStrategy() : aDef.getMessageDataProducerStrategy();
      
      if (IAeMessageDataStrategyNames.ELEMENT_VARIABLE.equals(strategy) && variable.getElement() != null )
      {
         // if its an element variable and the element qname matchese the variable qname then return the 
         // message type QName
         
         // incase the singleElementPartInfo is null
         QName singlePart = partMap.getSingleElementPartInfo() != null ? partMap.getSingleElementPartInfo().getElementName() : null;
         QName portType = aConsumer ? aDef.getConsumerPortType() : aDef.getProducerPortType();
         
         if ( singlePart != null && isCompatible(portType, variable.getElement(), singlePart) )
            type = singlePart;
      }
      else if(IAeMessageDataStrategyNames.MESSAGE_VARIABLE.equals(strategy) && variable.isMessageType() &&
              AeUtil.compareObjects(partMap.getMessageType(), variable.getMessageType()))
      {
         // if its a message variable and the variable message type message matches the message type in the parts
         // map then return the message type qname.
         type = partMap.getMessageType();
      }
      
      return type;
   }
   
   /**
    * Determine if the variable type and the single part type are compatible.
    * 
    * @param aPortType
    * @param aVariableType
    * @param aSinglePartQName
    */
   protected boolean isCompatible(QName aPortType, QName aVariableType, QName aSinglePartQName)
   {
      AeBPELExtendedWSDLDef wsdl =  getValidationContext().findWsdlByPortType(aPortType);
      return (AeUtil.compareObjects(aVariableType, aSinglePartQName) || wsdl.isCompatibleSGElement(aVariableType, aSinglePartQName));
      
   }
   
   
   /**
    * Return the number of parts in the map and if the map is null return zero.
    * @param aMap
    * @return
    */
   private int getPartsCount(AeMessagePartsMap aMap)
   {
      return aMap != null ? aMap.getPartsCount() : 0;
   }
}
