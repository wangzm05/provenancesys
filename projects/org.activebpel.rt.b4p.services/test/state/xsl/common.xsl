<xsl:stylesheet version="1.0"
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
   xmlns:aet="http://schemas.active-endpoints.com/humanworkflow/2007/01/taskDef.xsd"
   xmlns:aeid="http://schemas.active-endpoints.com/identity/2007/01/identity.xsd"
   xmlns:aetsst="http://schemas.activebpel.org/humanworkflow/2007/01/tasks/taskStateService.xsd">
   
   <xsl:output method="xml" encoding="ISO-8859-1" indent="yes" />

   <xsl:template match="@*|node()" priority="1">
      <xsl:copy>
         <xsl:apply-templates select="@*|node()" />
      </xsl:copy>
   </xsl:template>

</xsl:stylesheet>