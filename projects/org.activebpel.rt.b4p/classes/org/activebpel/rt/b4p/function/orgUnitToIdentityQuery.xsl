<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
   version="1.0"
   xmlns:aeid="http://schemas.active-endpoints.com/identity/2007/01/identity.xsd"
   xmlns:htd="http://www.example.org/WS-HT">

   <xsl:output method="xml"/>
   
   <!-- 
      optional param passed in for an exclude list. 
      defaults to empty node set 
   -->
   <xsl:param name="exclude" select="/.."/>

   <!-- 
      match on wildcard since we don't know what the root element will be.
      we're dealing with types here, not elements. 
   -->
   <xsl:template match="*">
      <aeid:identityQuery>
         <aeid:include>
            <xsl:apply-templates select="htd:groups/htd:group"/>
            <xsl:apply-templates select="htd:users/htd:user"/>
         </aeid:include>
         <xsl:if test="$exclude/htd:users | $exclude/htd:groups">
            <aeid:exclude>
               <xsl:apply-templates select="$exclude/htd:users/htd:user"/>
               <xsl:apply-templates select="$exclude/htd:groups/htd:group"/>
            </aeid:exclude>
         </xsl:if>
      </aeid:identityQuery>
   </xsl:template>

   <xsl:template match="htd:user">
      <aeid:user>
         <xsl:value-of select="."/>
      </aeid:user>
   </xsl:template>

   <xsl:template match="htd:group">
      <aeid:group>
         <xsl:value-of select="."/>
      </aeid:group>
   </xsl:template>
</xsl:stylesheet>
