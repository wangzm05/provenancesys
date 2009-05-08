<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:htd="http://www.example.org/WS-HT">

    <xsl:import href="common.xsl"/>

	<xsl:param name="delegation"/>

	<xsl:template match="htd:delegation">
		<xsl:copy-of select="$delegation"/>
	</xsl:template>

</xsl:stylesheet>
