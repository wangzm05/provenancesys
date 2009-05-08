<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:htp="http://www.example.org/WS-HT/protocol">

    <xsl:import href="common.xsl"/>
    
	<xsl:param name="peopleAssignments"/>

	<xsl:template match="htp:humanTaskContext/htp:peopleAssignments">
		<xsl:copy-of select="$peopleAssignments"/>
	</xsl:template>

</xsl:stylesheet>
