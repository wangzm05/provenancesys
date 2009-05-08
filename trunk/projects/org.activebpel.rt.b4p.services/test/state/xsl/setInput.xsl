<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:tew="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-events-wsdl.xsd"
	xmlns:tsw="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-state-wsdl.xsd"
	xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd">

    <xsl:import href="common.xsl"/>

	<xsl:param name="input"/>

	<xsl:template match="trt:initialState/trt:input">
		<xsl:copy-of select="$input"/>
	</xsl:template>
</xsl:stylesheet>
