package org.activebpel.rt.b4p.def.io;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.def.AePeopleActivityDef;
import org.activebpel.rt.b4p.def.visitors.AeAbstractB4PDefVisitor;
import org.activebpel.rt.bpel.def.activity.AeChildExtensionActivityDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromPartsDef;
import org.activebpel.rt.bpel.def.activity.support.AeToPartsDef;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeExtensionAttributeDef;
import org.activebpel.rt.xml.def.AeExtensionElementDef;

/**
  * Convenience deserializer for the elements of interest
  */
 public class AePeopleActivityDeserializer extends AeAbstractB4PDefVisitor
 {
    /** Def that we're populating */
    private AePeopleActivityDef mDef;
    /** Exception thrown during deserialization */
    private AeException mException;

   /**
    * Ctor
    * @param aDef
    * @throws AeException
    */
   public AePeopleActivityDeserializer(AeChildExtensionActivityDef aDef) throws AeException
    {
       setDef(new AePeopleActivityDef());

       getDef().setName(aDef.getName());
       
       AeExtensionAttributeDef inputVariableDef = aDef.getExtensionAttributeDef(new QName("", "inputVariable")); //$NON-NLS-1$//$NON-NLS-2$
       if (inputVariableDef != null)
          getDef().setInputVariable(inputVariableDef.getValue()); 
       
       AeExtensionAttributeDef outputVariableDef = aDef.getExtensionAttributeDef(new QName("", "outputVariable")); //$NON-NLS-1$ //$NON-NLS-2$
       if (outputVariableDef != null)
          getDef().setOutputVariable(outputVariableDef.getValue()); 

       AeExtensionAttributeDef skipableDef = aDef.getExtensionAttributeDef(new QName("", "isSkipable")); //$NON-NLS-1$//$NON-NLS-2$
       if (skipableDef != null)
          getDef().setSkipable("yes".equals(skipableDef.getValue()));  //$NON-NLS-1$

       for (Iterator iterator = aDef.getOrderedDefs().iterator(); iterator.hasNext();)
       {
          // Visit each def with our deserializer which copies over only the values
          // that we care about. Some of the defs in the ordered list are ignored.
          // for example, we don't care about sources or targets, we only need the defs
          // necessary to execute the people activity.
          AeBaseXmlDef type = (AeBaseXmlDef)iterator.next();
          type.accept(this);

          if (getException() != null)
             throw getException();
       }
    }

    /**
     * @see org.activebpel.rt.xml.def.visitors.AeBaseXmlDefVisitor#visit(org.activebpel.rt.xml.def.AeBaseXmlDef)
     */
    public void visit(AeBaseXmlDef aDef)
    {
       if ( aDef instanceof AeFromPartsDef )
       {
          getDef().setFromPartsDef((AeFromPartsDef)aDef);
       }
       else if ( aDef instanceof AeToPartsDef )
       {
          getDef().setToPartsDef((AeToPartsDef)aDef);
       }
    }

    /**
     * @see org.activebpel.rt.xml.def.visitors.AeBaseXmlDefVisitor#visit(org.activebpel.rt.xml.def.AeExtensionElementDef)
     */
    public void visit(AeExtensionElementDef aDef)
    {
       try
       {
          AeB4PIO.deserialize(getDef(), aDef.getExtensionElement());
       }
       catch (AeException ex)
       {
          setException(ex);
       }
    }

   /**
    * @return the def
    */
   public AePeopleActivityDef getDef()
   {
      return mDef;
   }

   /**
    * @param aDef the def to set
    */
   public void setDef(AePeopleActivityDef aDef)
   {
      mDef = aDef;
   }

   /**
    * @return the exception
    */
   public AeException getException()
   {
      return mException;
   }

   /**
    * @param aException the exception to set
    */
   public void setException(AeException aException)
   {
      mException = aException;
   }
 }