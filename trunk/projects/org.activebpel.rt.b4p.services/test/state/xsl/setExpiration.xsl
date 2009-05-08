<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:htp="http://www.example.org/WS-HT/protocol">

    <xsl:import href="common.xsl"/>

	<xsl:param name="expiration"/>

	<xsl:template match="htp:expirationTime">
		<xsl:copy-of select="$expiration"/>
	</xsl:template>

</xsl:stylesheet>
