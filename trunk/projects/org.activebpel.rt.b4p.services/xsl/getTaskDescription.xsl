<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd"
	xmlns:htd="http://www.example.org/WS-HT"
	xmlns:htapi="http://www.example.org/WS-HT/api"
	xmlns:htp="http://www.example.org/WS-HT/protocol"
	xmlns:htdt="http://www.example.org/WS-HT/api/xsd"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

	<xsl:import href="common.xsl" />
	
	<xsl:param name="contentType"/>

	<xsl:template match="trt:taskInstance">
   	<htdt:getTaskDescriptionResponse>
   		<htdt:description>
   			<xsl:choose>
   				<xsl:when test="$contentType = ''">
   					<xsl:value-of select="//trt:description[not(@contentType)]"/>
   				</xsl:when>
   				<xsl:otherwise>
   					<xsl:value-of select="//trt:description[@contentType=$contentType]"/>
   				</xsl:otherwise>
   			</xsl:choose>
   		</htdt:description>
   	</htdt:getTaskDescriptionResponse>
	</xsl:template>
</xsl:stylesheet>
