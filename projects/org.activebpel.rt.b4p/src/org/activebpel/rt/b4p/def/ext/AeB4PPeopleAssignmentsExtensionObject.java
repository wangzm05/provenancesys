// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p/src/org/activebpel/rt/b4p/def/ext/AeB4PPeopleAssignmentsExtensionObject.java,v 1.9 2008/01/11 20:01:29 dvilaverde Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

package org.activebpel.rt.b4p.def.ext;

import org.activebpel.rt.AeException;
import org.activebpel.rt.b4p.def.AeB4PBaseDef;
import org.activebpel.rt.b4p.def.AeB4PPeopleAssignmentsDef;
import org.activebpel.rt.b4p.def.IAeB4PContext;
import org.activebpel.rt.b4p.def.io.AeB4PIO;
import org.activebpel.rt.xml.def.AeBaseXmlDef;
import org.activebpel.rt.xml.def.AeExtensionElementDef;

/**
 * People Assignments extension object impl.
 */
public class AeB4PPeopleAssignmentsExtensionObject extends AeAbstractB4PExtensionObject
{
   /**
    * Getter for the def
    */
   public AeB4PPeopleAssignmentsDef getPeopleAssignmentsDef()
   {
      return (AeB4PPeopleAssignmentsDef) getDef();
   }

   /**
    * @see org.activebpel.rt.b4p.def.ext.AeAbstractB4PExtensionObject#deserialize(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   protected AeB4PBaseDef deserialize(AeBaseXmlDef aDef) throws AeException
   {
      // deserialize to Def model
      return (AeB4PBaseDef)AeB4PIO.deserialize(((AeExtensionElementDef)aDef).getExtensionElement());
   }

   /**
    * @see org.activebpel.rt.b4p.def.ext.AeAbstractB4PExtensionObject#createB4PContext(org.activebpel.rt.xml.def.AeBaseXmlDef)
    */
   protected IAeB4PContext createB4PContext(AeBaseXmlDef aDef)
   {
      // AeB4PPeopleAssignmentsDef objects will not have their IAeB4PContext context set and will 
      // default to their parent def.
      return null;
   }
}
