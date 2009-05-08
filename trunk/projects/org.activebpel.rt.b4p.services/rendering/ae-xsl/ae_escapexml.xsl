<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" indent="yes" />
  	
  	<!--  ======================================  -->
  	<!--  Convert XHTML node to a plain HTML node -->
  	<!--  ======================================  -->
	<xsl:template match="*" mode="xhtml2html">
		<xsl:variable name="elementName" select="local-name(.)" />
		<xsl:element name="{$elementName}">
			<xsl:for-each select="@*">
				<xsl:variable name="attrName" select="name()" />
				<xsl:attribute name="{$attrName}"><xsl:value-of select="." /></xsl:attribute>
			</xsl:for-each>			
			<xsl:apply-templates mode="xhtml2html" />
		</xsl:element>
	</xsl:template>
	
  	<!--  ======================================  -->
  	<!--  Convert escaped html to a plain HTML node -->
  	<!--  ======================================  -->
	<xsl:template match="*" mode="html2html">
		<xsl:variable name="elementName" select="local-name(.)" />
		<xsl:element name="{$elementName}">
			<xsl:apply-templates mode="html2html" />
		</xsl:element>
	</xsl:template>	


  	<!--  ==================================================== -->
  	<!--  Escapes XML node so that it can be displayed in html -->
  	<!--  ==================================================== -->
  	  
	<!--  source: Lars Huttar, http://www.biglist.com/lists/xsl-list/archives/200312/msg00693.html -->
   <!-- escape-xml mode: serialize XML tree to text, with indent
     Based very loosely on templates by Wendell Piez 
   -->

  <xsl:variable name="nl"><xsl:text>&#10;</xsl:text></xsl:variable>
  <xsl:variable name="indent-increment" select="'  '" />
  <xsl:variable name="ns-decl-extra-indent" select="'     '" />

  <xsl:template match="*" mode="escape-xml">
    <xsl:param name="indent-string" select="$indent-increment" />
    <xsl:param name="is-top" select="'true'" /> <!-- true if this is the top of the tree being
serialized -->
    <xsl:param name="exclude-prefixes" select="''" /> <!-- ns-prefixes to avoid declaring -->

    <xsl:value-of select="$indent-string" />
    <xsl:call-template name="write-starttag">
      <xsl:with-param name="is-top" select="$is-top" />
      <xsl:with-param name="indent-string" select="$indent-string" />
      <xsl:with-param name="exclude-prefixes" select="$exclude-prefixes" />
    </xsl:call-template>
    <xsl:if test="*"><xsl:value-of select="$nl" /></xsl:if>
    <xsl:apply-templates mode="escape-xml">
      <xsl:with-param name="indent-string" select="concat($indent-string, $indent-increment)" />
      <xsl:with-param name="is-top" select="'false'" />
    </xsl:apply-templates>
    <xsl:if test="*"><xsl:value-of select="$indent-string" /></xsl:if>
     <xsl:if test="*|text()|comment()|processing-instruction()"><xsl:call-template
name="write-endtag" /></xsl:if>
    <xsl:value-of select="$nl" />
  </xsl:template>

  <xsl:template name="write-starttag">
    <xsl:param name="is-top" select="'false'" />
    <xsl:param name="exclude-prefixes" select="''" /> <!-- ns-prefixes to avoid declaring -->
    <xsl:param name="indent-string" select="''" />

    <xsl:text>&lt;</xsl:text>
    <xsl:value-of select="name()"/>
    <xsl:for-each select="@*">
     <xsl:call-template name="write-attribute"/>
    </xsl:for-each>
    <xsl:call-template name="write-namespace-declarations">
      <xsl:with-param name="is-top" select="$is-top" />
      <xsl:with-param name="exclude-prefixes" select="$exclude-prefixes" />
      <xsl:with-param name="indent-string" select="$indent-string" />
    </xsl:call-template>
    <xsl:if test="not(*|text()|comment()|processing-instruction())"> /</xsl:if>
    <xsl:text>></xsl:text>
  </xsl:template>

  <xsl:template name="write-endtag">
     <xsl:text>&lt;/</xsl:text>
     <xsl:value-of select="name()"/>
     <xsl:text>></xsl:text>
  </xsl:template>

  <xsl:template name="write-attribute">
     <xsl:text> </xsl:text>
     <xsl:value-of select="name()"/>
     <xsl:text>="</xsl:text>
     <xsl:value-of select="."/>
     <xsl:text>"</xsl:text>
  </xsl:template>

  <!-- Output namespace declarations for the current element. -->
  <!-- Assumption: if an attribute in the source tree uses a particular namespace, its parent
element
   will have a namespace node for that namespace (because the declaration for the namespace
   must be on the parent element or one of its ancestors). -->
  <xsl:template name="write-namespace-declarations">
    <xsl:param name="is-top" select="'false'" />
    <xsl:param name="indent-string" select="''" />
    <xsl:param name="exclude-prefixes" select="''" />

    <xsl:variable name="current" select="." />
    <xsl:variable name="parent-nss" select="../namespace::*" />
    <xsl:for-each select="namespace::*">
      <xsl:variable name="ns-prefix" select="name()" />
      <xsl:variable name="ns-uri" select="string(.)" />
      <xsl:if test="not(contains(concat(' ', $exclude-prefixes, ' xml '), concat(' ', $ns-prefix, '
')))
                  and ($is-top = 'true' or not($parent-nss[name() = $ns-prefix and string(.) =
$ns-uri]))
                  ">
        <!-- This namespace node doesn't exist on the parent, at least not with that URI,
          so we need to add a declaration. -->
        <!--
          We could add the test
              and ($ns-prefix = '' or ($current//.|$current//@*)[substring-before(name(), ':') =
$ns-prefix])
          i.e. "and it's used by this element or some descendant (or descendant-attribute)
thereof:"
         Only problem with the above test is that sometimes namespace declarations are needed even
though
          they're not used by a descendant element or attribute: e.g. if the input is a stylesheet,
prefixes have
          to be declared if they're used in XPath expressions [which are in attribute values]. We
could have
          problems in this area with regard to xsp-request.
        -->
        <xsl:value-of select="concat($nl, $indent-string, $ns-decl-extra-indent)" />
        <xsl:choose>
          <xsl:when test="$ns-prefix = ''">
            <xsl:value-of select="concat('xmlns=&quot;', $ns-uri, '&quot;')" />
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="concat('xmlns:', $ns-prefix, '=&quot;', $ns-uri, '&quot;')" />
          </xsl:otherwise>
        </xsl:choose>
      </xsl:if>
    </xsl:for-each>
  </xsl:template>

</xsl:stylesheet>