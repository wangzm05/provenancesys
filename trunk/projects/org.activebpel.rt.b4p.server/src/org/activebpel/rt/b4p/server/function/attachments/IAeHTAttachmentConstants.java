//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.b4p.server/src/org/activebpel/rt/b4p/server/function/attachments/IAeHTAttachmentConstants.java,v 1.1 2008/02/11 17:09:24 mford Exp $
/////////////////////////////////////////////////////////////////////////////
//PROPRIETARY RIGHTS STATEMENT
//The contents of this file represent confidential information that is the
//proprietary property of Active Endpoints, Inc.  Viewing or use of
//this information is prohibited without the express written consent of
//Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
//is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
/////////////////////////////////////////////////////////////////////////////
package org.activebpel.rt.b4p.server.function.attachments;

/**
 * Constants for custom ht attachment functions
 */
public interface IAeHTAttachmentConstants
{
   /** Constants for attachment header properties */
   public static final String ATTACHED_AT = "Attachment-Created-At"; //$NON-NLS-1$
   public static final String ATTACHED_BY = "Attached-By"; //$NON-NLS-1$
   public static final String ACCESS_TYPE = "Access-Type"; //$NON-NLS-1$
   public static final String CONTENT_TYPE = "URL-Content-Type"; //$NON-NLS-1$

   /** Constants for content type */
   public static final String INLINE_ACCESS_TYPE = "inline"; //$NON-NLS-1$
   public static final String URL_ACCESS_TYPE = "URL"; //$NON-NLS-1$
   public static final String MIME_ACCESS_TYPE = "MIME"; //$NON-NLS-1$

   /** constants for default header values */
   public static final String DEFAULT_ATTACHED_BY = "anonymous"; //$NON-NLS-1$
   public static final String DEFAULT_NAME_PREFIX = "attachment"; //$NON-NLS-1$
}
