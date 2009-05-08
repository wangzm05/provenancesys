<xsl:stylesheet version="1.0"
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   
   <xsl:output method="xml" encoding="ISO-8859-1" indent="yes" />

   <xsl:template match="@*|node()" priority="1">
      <xsl:copy>
         <xsl:apply-templates select="@*|node()" />
      </xsl:copy>
   </xsl:template>

</xsl:stylesheet>