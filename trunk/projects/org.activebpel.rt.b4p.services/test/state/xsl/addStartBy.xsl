<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd">

    <xsl:import href="common.xsl"/>

	<xsl:template match="trt:initialState/trt:createdBy">
		<xsl:copy-of select="."/>
		<trt:startBy>2008-02-09T15:59:18.503Z</trt:startBy>
	</xsl:template>

</xsl:stylesheet>
