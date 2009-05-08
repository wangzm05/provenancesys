<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd" 
	xmlns:htd="http://www.example.org/WS-HT" 
	xmlns:htda="http://www.example.org/WS-HT/api"  
	xmlns:aefe="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/errors">

  <xsl:output method="html" indent="yes" encoding="UTF-8" />  
	<xsl:template name="ae_task_description" xml:space="default">
		<xsl:choose>
	    	<!--  application/xhtml+xml child node  -->
			<xsl:when test="count(trt:presentation/trt:description[@contentType='application/xhtml+xmll']) &gt; 0 and string-length(local-name(trt:presentation/trt:description[@contentType='application/xhtml+xmll']/child::*)) &gt; 0 ">
				 <xsl:apply-templates mode="xhtml2html" select="trt:presentation/trt:description[@contentType='application/xhtml+xml']/child::*" /> 					         	
	    	</xsl:when>
	    	<!--  text/xhtml child node  -->
			<xsl:when test="count(trt:presentation/trt:description[@contentType='text/xhtml']) &gt; 0 and string-length(local-name(trt:presentation/trt:description[@contentType='text/xhtml']/child::*)) &gt; 0">
				<xsl:apply-templates mode="xhtml2html" select="trt:presentation/trt:description[@contentType='text/xhtml']/child::*" /> 					         	
	    	</xsl:when>
			<xsl:when test="count(trt:presentation/trt:description[@contentType='xhtml']) &gt; 0 and string-length(local-name(trt:presentation/trt:description[@contentType='xhtml']/child::*)) &gt; 0 ">
				 <xsl:apply-templates mode="xhtml2html" select="trt:presentation/trt:description[@contentType='xhtml']/child::*" />
	    	</xsl:when>
	    	<!--  text/html child node  -->
			<xsl:when test="count(trt:presentation/trt:description[@contentType='text/html']) &gt; 0 and string-length(local-name(trt:presentation/trt:description[@contentType='text/html']/child::*)) &gt; 0">
				<xsl:apply-templates mode="xhtml2html" select="trt:presentation/trt:description[@contentType='text/html']/child::*" /> 					         	
	    	</xsl:when>	    	
	    	<!--  text/html escaped (string) -->
			<xsl:when test="count(trt:presentation/trt:description[@contentType='text/html']) &gt; 0">
				<xsl:value-of select="trt:presentation/trt:description[@contentType='text/html']" disable-output-escaping="yes" /> 					         	
	    	</xsl:when>
			<xsl:when test="count(trt:presentation/trt:description[@contentType='html']) &gt; 0">
				 <xsl:value-of select="trt:presentation/trt:description[@contentType='html']" disable-output-escaping="yes" /> 					         	
	    	</xsl:when>	    		    		    			
		   <!--  simple text/plain value -->
			<xsl:when test="count(trt:presentation/trt:description[@contentType='text/plain']) &gt; 0">
				 <xsl:value-of select="trt:presentation/trt:description[@contentType='text/plain']" /> 					         	
	    	</xsl:when>
			<xsl:when test="count(trt:presentation/trt:description[@contentType='text']) &gt; 0">
				 <xsl:value-of select="trt:presentation/trt:description[@contentType='text']" /> 					         	
	    	</xsl:when>	 
		   <!--  TODO: contentType = "" -->
	    	<!--  default case  -->
	    	<xsl:otherwise>
	    		<xsl:apply-templates mode="escape-xml" select="trt:presentation/trt:description/text()"/>
	    	</xsl:otherwise>
		</xsl:choose>	
	    <!--  end detailed description -->						
	</xsl:template>
	
</xsl:stylesheet>