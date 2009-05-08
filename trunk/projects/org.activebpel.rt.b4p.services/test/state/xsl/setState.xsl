<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:tew="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-events-wsdl.xsd"
   xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd"
	xmlns:tsw="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-state-wsdl.xsd">

   <xsl:import href="common.xsl"/>

   <xsl:param name="state"/>

   <xsl:template match="tsw:state">
      <tsw:state><xsl:value-of select="$state"/></tsw:state>
   </xsl:template>

   <xsl:template match="trt:status">
      <trt:status><xsl:value-of select="$state"/></trt:status>
   </xsl:template>

</xsl:stylesheet>
