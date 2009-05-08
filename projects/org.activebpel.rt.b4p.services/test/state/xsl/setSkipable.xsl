<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:htp="http://www.example.org/WS-HT/protocol">

    <xsl:import href="common.xsl"/>

	<xsl:param name="skipable"/>

	<xsl:template match="htp:isSkipable">
		<htp:isSkipable><xsl:value-of select="$skipable"/></htp:isSkipable>
	</xsl:template>

</xsl:stylesheet>
