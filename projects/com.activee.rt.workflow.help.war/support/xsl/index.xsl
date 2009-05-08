<?xml version="1.0"?>

<!-- DocFrame XSL transformation for HTML 4 (CSS) output. Copyright (c) 2003 Scriptorium Publishing Services, Inc.

This XSL transformation file is part of the DocFrame authoring environment. Only licensed users may use or modify this file. Distribution is limited to other DocFrame licensees.

Revision history:
2/18/03	DocFrame 1.0 released
2/20/03	added copyright statement to top of file

-->

<xsl:stylesheet version="1.1"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:saxon="http://icl.com/saxon"
  extension-element-prefixes="saxon">
<xsl:output method="html"/>

<xsl:template match = "/">
	<xsl:apply-templates/>
</xsl:template>


<xsl:template match="Index">
	<xsl:document href="html/index_temp2.html" saxon:next-in-chain="index2.xsl">
		<Index>
			<xsl:copy-of select="@*"/>
			<xsl:apply-templates>
			<xsl:sort/>
			</xsl:apply-templates>
		</Index>
	</xsl:document>
</xsl:template>
	
<xsl:template match="IXEntry">
	<IXEntry>
		<xsl:copy-of select="@*"/>
		<xsl:apply-templates/>
	</IXEntry>
</xsl:template>
	
</xsl:stylesheet>
