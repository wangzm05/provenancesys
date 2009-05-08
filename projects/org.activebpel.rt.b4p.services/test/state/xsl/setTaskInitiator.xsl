<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:htp="http://www.example.org/WS-HT/protocol"
	xmlns:htd="http://www.example.org/WS-HT">

    <xsl:import href="common.xsl"/>
    
	<xsl:param name="initiator"/>

	<xsl:template match="htp:humanTaskContext/htp:peopleAssignments/htp:taskInitiator">
		<xsl:if test="$initiator != ''">
			<htp:taskInitiator>
				<htd:organizationalEntity>
					<htd:users>
						<htd:user>
							<xsl:value-of select="$initiator" />
						</htd:user>
					</htd:users>
				</htd:organizationalEntity>
			</htp:taskInitiator>
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>
