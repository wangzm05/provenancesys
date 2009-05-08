<?xml version="1.0"?>

<!-- DocFrame XSL transformation for HTML 4 (CSS) output. Copyright (c) 2003 Scriptorium Publishing Services, Inc.

This XSL transformation file is part of the DocFrame authoring environment. Only licensed users may use or modify this file. Distribution is limited to other DocFrame licensees.

Revision history:
02/18/03	DocFrame 1.0 released
02/20/03	added copyright statement to top of file
03/05/03	added meta tag with DocFrame identifier to each page template
		created variables for Next/Previous for localization or
		customization (button, etc.)
03/20/03	fixed links in nav bar at the top of the index and added nav bar at the
		bottom of the index


-->

<xsl:stylesheet version="1.1"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:saxon="http://icl.com/saxon"
  extension-element-prefixes="saxon">
<xsl:output method="html"/>

<xsl:variable name="copyright">Copyright (c) 2004 Active Endpoints, Inc.</xsl:variable>
<xsl:variable name="prev1" select="/Index/@prev1"/>
<xsl:variable name="rootname" select="/Index/@rootname"/>
<xsl:variable name="indexpos" select="/Index/@indexpos" />
<xsl:variable name="booktitle" select="/Index/@booktitle"/>
<xsl:variable name="prevlinkcount1" select="/Index/@prevlinkcount1"/>
<xsl:variable name="prevlinkcountsub1" select="/Index/@prevlinkcountsub1"/>

<xsl:variable name="next_label">Next</xsl:variable>
<xsl:variable name="prev_label">Previous</xsl:variable>
<xsl:variable name="toc_label">Contents</xsl:variable>
<xsl:variable name="ix_label">Index</xsl:variable>


<xsl:template match = "/">
	<xsl:apply-templates/>
</xsl:template>


<xsl:template match="Index">
<xsl:document href="html/{$rootname}{$indexpos}.html">
	<html>
	<head>
	<meta name="generator" description="DocFrame 1.1 from Scriptorium Publishing"/>
	<link rel="stylesheet" type="text/css" href="docframe.css" />
	<title><xsl:value-of select="$ix_label"/>
	</title>
	</head>
	<body>

	<xsl:call-template name="header"/>
	<p class="NavBarTop">

	<xsl:choose>
		<xsl:when test="$prevlinkcountsub1 &gt; 0">
			<a href="{$rootname}{$prev1}-{$prevlinkcount1}-{$prevlinkcountsub1}.html"><xsl:value-of select="$prev_label"/></a>
		</xsl:when>
		<xsl:when test="$prevlinkcount1 &gt; 0">
			<a href="{$rootname}{$prev1}-{$prevlinkcount1}.html"><xsl:value-of select="$prev_label"/></a>
		</xsl:when>
		<xsl:otherwise>
			<a href="{$rootname}{$prev1}.html"><xsl:value-of select="$prev_label"/></a>
		</xsl:otherwise>
	</xsl:choose>
	<xsl:text> | </xsl:text>
	<xsl:value-of select="$next_label"/>
	<xsl:text> | </xsl:text>
	<!-- <a href="index.html"><xsl:value-of select="$toc_label"/></a> | -->
	<xsl:value-of select="$ix_label"/>
	</p>

	<h1 class="Index">
	<xsl:value-of select="name()"/>
	</h1>

	<xsl:apply-templates/>
<hr color="#9999cc" size="1"/>
<p class="Copyright"><xsl:value-of select="$copyright"/></p>
<!--  COMMENTING OUT NAV BAR TABLE IN FOOTER
<table border="0" width="100%">
<tr valign="top">
<td class="Copyright" valign="top"><xsl:value-of select="$copyright"/></td>
<td class="NavBarBottom" valign="top">

	<xsl:choose>
	<xsl:when test="$prevlinkcountsub1 != 0">
			<a href="{$rootname}{$prev1}-{$prevlinkcount1}-{$prevlinkcountsub1}.html"><xsl:value-of select="$prev_label"/></a>
			</xsl:when>
			<xsl:when test="$prevlinkcount1 != 0">
			<a href="{$rootname}{$prev1}-{$prevlinkcount1}.html"><xsl:value-of select="$prev_label"/></a>
			</xsl:when>
			<xsl:otherwise>
			<a href="{$rootname}{$prev1}.html"><xsl:value-of select="$prev_label"/></a>
			</xsl:otherwise>
		</xsl:choose>
	 | <xsl:value-of select="$next_label"/><br/>
	 <a href="index.html"><xsl:value-of select="$toc_label"/></a> | <xsl:value-of select="$ix_label"/> 
</td>
</tr>
</table>
END OF COMMENT --> 
	</body>
	</html>
</xsl:document>
</xsl:template>


<xsl:template match="IXEntry">

<xsl:variable name="ancestors" select="count(ancestor-or-self::IXEntry)"/>
<xsl:variable name="cur_text"><xsl:value-of select="text()"/></xsl:variable>
<xsl:variable name="prev_text"><xsl:value-of select="ancestor-or-self::IXEntry[$ancestors]/preceding-sibling::*[1]/descendant-or-self::IXEntry[$ancestors]/text()"/></xsl:variable>
<xsl:variable name="next_text"><xsl:value-of select="ancestor-or-self::IXEntry[$ancestors]/following-sibling::*[1]/descendant-or-self::IXEntry[$ancestors]/text()"/></xsl:variable>

<!--number of ancestors: <xsl:value-of select="$ancestors"/><br/>
current text: <xsl:value-of select="$cur_text"/><br/>
previous text: <xsl:value-of select="$prev_text"/><br/>
next text: <xsl:value-of select="$next_text"/><br/>-->


<xsl:choose>
	<xsl:when test="$cur_text = $prev_text"></xsl:when>
	<xsl:when test="@see='yes'">
		<xsl:text disable-output-escaping="yes">&lt;ul&gt;</xsl:text>
		<li>
			<xsl:attribute name="class">IX<xsl:value-of select="$ancestors"/></xsl:attribute>
			<xsl:value-of select="text()"/>
		</li>
	</xsl:when>
	<xsl:otherwise>
		<xsl:text disable-output-escaping="yes">&lt;ul&gt;</xsl:text>
		<xsl:text disable-output-escaping="yes">&lt;li class="IX</xsl:text><xsl:value-of select="$ancestors"/><xsl:text disable-output-escaping="yes">"&gt;</xsl:text>
		<xsl:if test="@linkname !=''">
			<xsl:text disable-output-escaping="yes">&lt;a href="</xsl:text><xsl:value-of select="@linkname"/><xsl:text disable-output-escaping="yes">"&gt;</xsl:text>
		</xsl:if>
		<xsl:value-of select="text()"/>
		<xsl:if test="@linkname !=''">
			<xsl:text disable-output-escaping="yes">&lt;/a&gt;</xsl:text>
		</xsl:if>
		<xsl:text disable-output-escaping="yes">&lt;/li&gt;</xsl:text>
	</xsl:otherwise>
</xsl:choose>

<xsl:apply-templates select="IXEntry"/>

<xsl:choose>
	<xsl:when test="$cur_text = $next_text"></xsl:when>
	<xsl:otherwise>
		<xsl:text disable-output-escaping="yes">&lt;/ul&gt;</xsl:text>
	</xsl:otherwise>
</xsl:choose>

</xsl:template>


<xsl:template name="header">
	<table width="100%" bgcolor="#9999cc">
		<tr>
			<td class="Banner"><xsl:value-of select="$booktitle"/></td>
		</tr>
	</table>
</xsl:template>



</xsl:stylesheet>
