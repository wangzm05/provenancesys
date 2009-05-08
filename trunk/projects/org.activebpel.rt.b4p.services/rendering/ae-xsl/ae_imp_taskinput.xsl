<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd" 
	xmlns:htd="http://www.example.org/WS-HT" 
	xmlns:htda="http://www.example.org/WS-HT/api"  
	xmlns:aefe="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/errors">

	<xsl:template name="ae_task_input" xml:space="default">
		<xsl:param name="taskId" />		
		<xsl:param name="taskStatus" />			
     <xsl:for-each select="trt:interfaceMetadata/trt:input/trt:part">
           <xsl:call-template name="task_workitem_input_part" >
            <xsl:with-param name="taskId" select="$taskId" />	
            <xsl:with-param name="taskStatus" select="$taskStatus" />	            
            <xsl:with-param name="partMetadata" select="." />
           </xsl:call-template>
     </xsl:for-each>
	</xsl:template>
	
	<!--  
		============================================
		INPUT PART
		Display each input part in a single table row 
		=============================================
	 -->
	<xsl:template name="task_workitem_input_part" xml:space="default">
		<xsl:param name="taskId" />	
		<xsl:param name="taskStatus" />
		<xsl:param name="partMetadata" />	
		<xsl:variable name="partName" select="$partMetadata/@name" />
      <xsl:variable name="partData" select="//trt:operational/trt:input/trt:part[@name= $partName]" />
      <xsl:call-template name="task_workitem_part_readonly_view" >
           <xsl:with-param name="taskId" select="$taskId" />	
           <xsl:with-param name="taskStatus" select="$taskStatus" />	            
           <xsl:with-param name="partMetadata" select="$partMetadata" />
           <xsl:with-param name="partName" select="$partName" />
           <xsl:with-param name="partData" select="$partData"/>
      </xsl:call-template>            
	</xsl:template>
	
	<!--  
		============================================
		Display data in  read-only fields
		=============================================
	 -->
	<xsl:template name="task_workitem_part_readonly_view" xml:space="default">
		<xsl:param name="taskId" />	
		<xsl:param name="taskStatus" />
		<xsl:param name="partMetadata" />	
		<xsl:param name="partName" />
		<xsl:param name="partData" />
      <tr>
         <td class="label" style="width:15%;"><strong><xsl:value-of select="$partName"/> :</strong></td>
         <td>
	         <xsl:choose>
	         	<xsl:when test="$partMetadata/@typeHint='boolean' or $partMetadata/@typeHint='number' or $partMetadata/@typeHint='date'">
						<span style="width:95%;" class="workitemdata_input workitemdata_simple workitemdata_readonly"><xsl:value-of select="$partData/text()"/></span>		         	
	         	</xsl:when>
	         	<xsl:when test="$partMetadata/@typeHint='string'">
<textarea style="width:95%;" rows="4" wrap="off" readonly="readonly" class="workitemdata_input workitemdata_complex workitemdata_readonly"><xsl:value-of select="$partData/text()"/></textarea>
	         	</xsl:when>
	         	<!--  case where data is simple and  type hints are not available  -->
	         	<xsl:when test="string-length(local-name($partData/child::*)) = 0">
<textarea style="width:95%;" rows="4" wrap="off" readonly="readonly" class="workitemdata_input workitemdata_complex workitemdata_readonly"><xsl:value-of select="$partData/text()"/></textarea>
	         	</xsl:when>	         		         		         	
	         	<xsl:otherwise>
<textarea style="width:95%;" rows="4" wrap="off" readonly="readonly" class="workitemdata_input workitemdata_complex workitemdata_readonly"><xsl:apply-templates mode="escape-xml" select="$partData/child::*" /></textarea>
	         	</xsl:otherwise>
	         </xsl:choose>
         </td>
      </tr>      
	</xsl:template>	
			
</xsl:stylesheet>