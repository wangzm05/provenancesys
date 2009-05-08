<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd" 
	xmlns:htd="http://www.example.org/WS-HT" 
	xmlns:htda="http://www.example.org/WS-HT/api">
	
	<xsl:template name="ae_task_command_buttons" xml:space="default">
		<table class="toolbarbuttons">
			<tr>								
				<td>
					<a href="" id="aetask_cmd_claim" title="Claim">
						<xsl:attribute name="class">
							<xsl:if test="//trt:permissions/trt:claim">aeEnableCommandButton</xsl:if>
						</xsl:attribute>Claim</a>					
				</td>
				<td>
					<a href="" id="aetask_cmd_start" title="Start">
						<xsl:attribute name="class">
							<xsl:if test="//trt:permissions/trt:start">aeEnableCommandButton</xsl:if>
						</xsl:attribute>Start</a>
				</td>		
				<td>
					<a href="" id="aetask_cmd_stop" title="Stop">
						<xsl:attribute name="class">
							<xsl:if test="//trt:permissions/trt:stop">aeEnableCommandButton</xsl:if>
						</xsl:attribute>Stop</a>
				</td>											

				<td>
					<a href="" id="aetask_cmd_release" title="Release">
						<xsl:attribute name="class">
							<xsl:if test="//trt:permissions/trt:release">aeEnableCommandButton</xsl:if>
						</xsl:attribute>Release</a>
				</td>									
				
				<td>
					<a href="" id="aetask_cmd_complete" title="Complete">
						<xsl:attribute name="class">
							<xsl:if test="//trt:permissions/trt:complete"><xsl:text>aeEnableCommandButton</xsl:text></xsl:if>
						</xsl:attribute>Complete</a>
				</td>																		
				<td>
					<a href="" id="aetask_cmd_fail" title="Fail">
						<xsl:attribute name="class">
							<xsl:if test="//trt:permissions/trt:fail and count(//trt:context/trt:interfaceMetadata/trt:fault)&gt; 0">aeEnableCommandButton</xsl:if>
						</xsl:attribute>Fail</a>
				</td>
				
				<td>
					<a href="" id="aetask_cmd_skip" title="Skip">
						<xsl:attribute name="class">
							<xsl:if test="//trt:permissions/trt:skip">aeEnableCommandButton</xsl:if>
						</xsl:attribute>Skip</a>
				</td>
				<!-- 
				<td>
					<a href="" id="aetask_cmd_forward" title="Forward">
						<xsl:attribute name="class">
							<xsl:if test="trt:permissions/trt:forward">aeEnableCommandButton</xsl:if>
						</xsl:attribute>Forward</a>
				</td>
				 -->				
				<td>
					<a href="" id="aetask_cmd_delegate" title="Assign">
						<xsl:attribute name="class">
							<xsl:if test="//trt:permissions/trt:delegate">aeEnableCommandButton</xsl:if>
						</xsl:attribute>Assign</a>
				</td>													
				<td>
					<a href="" id="aetask_cmd_suspend" title="Suspend">
						<xsl:attribute name="class">
							<xsl:if test="//trt:permissions/trt:suspend">aeEnableCommandButton</xsl:if>
						</xsl:attribute>Suspend</a>
				</td>				
				<td>
					<a href="" id="aetask_cmd_resume" title="Resume">
						<xsl:attribute name="class">
							<xsl:if test="//trt:permissions/trt:resume">aeEnableCommandButton</xsl:if>
						</xsl:attribute>Resume</a>
				</td>
			</tr>
		</table>
  </xsl:template>
</xsl:stylesheet>
