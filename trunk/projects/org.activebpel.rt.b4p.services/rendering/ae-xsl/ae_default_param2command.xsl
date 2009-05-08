<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"   
   xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd"
	xmlns:aefp="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/formparams"
	xmlns:aetc="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/taskcommands" >
 	<xsl:import href="ae_base_param2command.xsl" />
	<xsl:output method="xml" indent="yes" encoding="UTF-8" />

	<!--  
		=========================================================================
		This is the default template that is used to process a generic task detail
		page(with multiple text area input fields for each of the output parts) 
		form submission.
		
		Only the work item area inputs (template name='ae_workitem_param2command') are
		process. The rest (commands such as Claim, Revoke, Add comment etc. are 
		handled in the base ae_base_param2command.xsl file).
		=========================================================================
	-->	
		
	<!--  
		=========================================================================
		Override base template to process the AE default (generic) work item form  
		=========================================================================
	-->	
	<xsl:template name="ae_workitem_param2command" xml:space="default">
		<xsl:choose>
			<!--  Set output data (if specified 'selection' mode is output)  -->
			<xsl:when test="aefp:parameter[@name='aetaskworkitem_selection']/text()='output' 
				and count(aefp:parameter[@name='aetaskworkitem_outputparts']/aepartnames/aepartname) > 0">
				<xsl:call-template name="ae_workitem_setoutput" />
			</xsl:when>	
			<!--  Set or Clear fault data (if specified 'mode' is fault  -->
			<xsl:when test="aefp:parameter[@name='aetaskworkitem_selection']/text()='fault'">
				<xsl:call-template name="ae_workitem_set_or_clear_fault" />
			</xsl:when>				
		</xsl:choose>	
	</xsl:template>
	
	<!--  
		=====================================================================
		Create trt:setoutput command for each of the outputs in the html form
		This template uses the 'aetaskworkitem_outputparts' parameter
		(which contains a list of partnames) to iterate thru the list
		of output parts.
		=====================================================================
	 -->
	<xsl:template name="ae_workitem_setoutput" xml:space="default">
			<xsl:variable name="taskId"><xsl:value-of select="@taskId" /></xsl:variable>
			<!--  Iterate thru each output part and call setOutput command -->				
			<xsl:for-each select="aefp:parameter[@name='aetaskworkitem_outputparts']/aepartnames/aepartname">
				<xsl:variable name="partName"><xsl:value-of select="normalize-space(text())" /></xsl:variable>
				<xsl:variable name="paramName"><xsl:value-of select="concat('aetaskworkitem_outputpart_',$partName)" /></xsl:variable>		     		
				<xsl:variable name="partData">
					<xsl:choose>
						<xsl:when test="//aefp:parameters/aefp:parameter[@name=$paramName]/@type='xml'">
							<xsl:copy-of select="//aefp:parameters/aefp:parameter[@name=$paramName]/child::*" /> 					
						</xsl:when>
						<xsl:otherwise><xsl:value-of select="//aefp:parameters/aefp:parameter[@name=$paramName]" /></xsl:otherwise>
					</xsl:choose>							     		
				</xsl:variable>
				<xsl:call-template name="ae_setoutputpart_command">
					<xsl:with-param name="taskId" select="$taskId" />
					<xsl:with-param name="partName" select="$partName" />
					<xsl:with-param name="partData" select="$partData" />	
				</xsl:call-template>		     			     				     	     	
			</xsl:for-each>
	</xsl:template>	 
	 
	<!-- 
	<xsl:template name="ae_workitem_setoutput" xml:space="default">
			<xsl:variable name="taskId"><xsl:value-of select="@taskId" /></xsl:variable>
			<xsl:variable name="aetPartList">				
				<xsl:for-each select="aefp:parameter[@name='aetaskworkitem_outputparts']/aepartnames/aepartname">
		     		<xsl:variable name="partName"><xsl:value-of select="normalize-space(text())" /></xsl:variable>
		     		<xsl:variable name="paramName"><xsl:value-of select="concat('aetaskworkitem_outputpart_',$partName)" /></xsl:variable>		     		
		     		<xsl:variable name="partData">
							<xsl:choose>
								<xsl:when test="//aefp:parameters/aefp:parameter[@name=$paramName]/@type='xml'">
									<xsl:copy-of select="//aefp:parameters/aefp:parameter[@name=$paramName]/child::*" /> 					
								</xsl:when>
								<xsl:otherwise><xsl:value-of select="//aefp:parameters/aefp:parameter[@name=$paramName]" /></xsl:otherwise>
							</xsl:choose>							     		
		     		</xsl:variable>
					<trt:part name="{$partName}">
						<xsl:copy-of select="$partData" />
					</trt:part>
				</xsl:for-each>
			</xsl:variable>
			<xsl:call-template name="ae_setoutputpartlist_command">
				<xsl:with-param name="taskId" select="$taskId" />
				<xsl:with-param name="partDataList" select="$aetPartList" />	
			</xsl:call-template>		     			     				     
	</xsl:template>	
	-->
	<!--  
		=======================================================
		Create setFault or clearFault command
		=======================================================
	 -->	
	<xsl:template name="ae_workitem_set_or_clear_fault" xml:space="default">
			<xsl:variable name="taskId"><xsl:value-of select="@taskId" /></xsl:variable>
			<xsl:choose>
	      	<xsl:when test="string-length(aefp:parameter[@name='aetaskworkitem_faultname']/text()) &gt; 0
	      		and count(aefp:parameter[@name='aetaskworkitem_faultdata']/child::*) &gt; 0">

					<xsl:variable name="namespace"><xsl:value-of select="aefp:parameter[@name='aetaskworkitem_faultnamespace']/text()" /></xsl:variable>
					<xsl:variable name="faultData"><xsl:copy-of select="aefp:parameter[@name='aetaskworkitem_faultdata']/child::*" /></xsl:variable>
	
					<xsl:call-template name="ae_setfault_command">
						<xsl:with-param name="taskId" select="$taskId" />
						<xsl:with-param name="faultName" select="aefp:parameter[@name='aetaskworkitem_faultname']/text()" />	
						<xsl:with-param name="faultData" select="$faultData" />	
					</xsl:call-template>				
					
	      	</xsl:when>
	      	<xsl:when test="string-length(aefp:parameter[@name='aetaskworkitem_faultname']/text()) = 0
	      		and count(aefp:parameter[@name='aetaskworkitem_faultdata']/child::*) = 0">
					<xsl:call-template name="ae_deletefault_command" >
						<xsl:with-param name="taskId" select="$taskId" />
					</xsl:call-template>
	      	</xsl:when>      	
      	</xsl:choose>
	</xsl:template>	
	
</xsl:stylesheet>
