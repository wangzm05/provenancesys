<xsl:stylesheet version="1.0"
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
   xmlns:ns="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd">      
   <xsl:import href="common.xsl"/>
   <xsl:param name="state"/>
   <xsl:template match="ns:status/text()" priority="2">
      <xsl:value-of select="$state" />
   </xsl:template>
</xsl:stylesheet>
