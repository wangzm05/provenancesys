<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd" 
	xmlns:htd="http://www.example.org/WS-HT" 
	xmlns:htda="http://www.example.org/WS-HT/api">
	
	<xsl:import href="ae_imp_taskdesc.xsl" />
   <xsl:output method="html" indent="yes" encoding="UTF-8" />   
   
	<xsl:template name="ae_task_metadata_grid" xml:space="default">
		<xsl:param name="taskStatus" />
		<xsl:param name="taskDisplayStatus" />
		<table class="taskdetailgrid" >
		  <tr>
		     <td class="gridlabel">Name:</td><td colspan="3"  class="gridvalue gridvline" style="width:90%"><span class="aetaskdata_presentationname"><xsl:value-of select="trt:presentation/trt:name/text()"/></span></td>
		  </tr>						
		  <tr>
		     <td class="gridlabel">Subject:</td><td colspan="3"  class="gridvalue gridvline" style="width:90%"><span class="aetaskdata_summary"><xsl:value-of select="trt:presentation/trt:subject/text()"/></span></td>
		  </tr>						  
		  <tr>
				<td class="gridlabel" >Priority:</td><td class="gridvalue gridvline"><span class="aetaskdata_priority"><xsl:value-of select="trt:context/trt:priority/text()"/></span>(
					<a href="" id="aetask_cmd_setpriority" title="Set Priority">
						<xsl:attribute name="class">
							<xsl:if test="trt:permissions/trt:setPriority">aeEnableCommandLink</xsl:if>
						</xsl:attribute>Set Priority</a>)
				</td>
				<td class="gridlabel" >Status:</td><td class="gridvalue gridvline"><span class="aetaskdata_state"><span>
					<xsl:attribute name="class">
					  aetaskstateglyph aetaskstateglyph_<xsl:value-of select="$taskStatus"/>									
					</xsl:attribute><xsl:value-of select="$taskDisplayStatus"/></span></span></td>
		  </tr>
		  <tr>
			<td class="gridlabel" >Owner:</td><td class="gridvalue gridvline"><span class="aetaskdata_owner"><xsl:value-of select="trt:context/trt:actualOwner/text()"/></span></td>
			<td class="gridlabel" >ID:</td><td class="gridvalue gridvline"><span class="aetaskdata_pid"><xsl:value-of select="trt:identifier/text()"/></span></td>
		  </tr>
		  <tr>
		     <td class="gridlabel" >Created:</td><td class="gridvalue gridvline"><span class="aetaskdata_creationtime"><xsl:value-of select="trt:context/trt:createdOn/text()"/></span></td>
		     <td class="gridlabel" >Created By:</td><td class="gridvalue gridvline"><span class="aetaskdata_createdby"><xsl:value-of select="trt:context/trt:createdBy/text()"/></span></td>
		  </tr>  
		  <tr>
		     <td class="gridlabel" >Modified:</td><td class="gridvalue gridvline"><span class="aetaskdata_modifiedtime"><xsl:value-of select="trt:context/trt:lastModifiedTime/text()"/></span></td>
		     <td class="gridlabel" >Modified By:</td><td class="gridvalue gridvline"><span class="aetaskdata_modifiedby"><xsl:value-of select="trt:context/trt:lastModifiedBy/text()"/></span></td>
		  </tr>  						  
		  <tr>
		     <td class="gridlabel" >Expiration:</td><td class="gridvalue gridvline"><span class="aetaskdata_expirationtime"><xsl:value-of select="trt:context/trt:expirationTime/text()"/></span></td>
		     <td class="gridlabel" >Escalated:</td><td class="gridvalue gridvline"><xsl:if test="trt:context/trt:lastEscalatedTime/text()"><span class="aetaskdata_lastescalationtime check_icon"><xsl:value-of select="trt:context/trt:lastEscalatedTime/text()"/></span></xsl:if></td>
		  </tr>  						  							  
		</table>	
		<!--  display description -->
		<div style="font-size:1.0em;padding:10px;" class="aetaskdata_detaileddescription">		
			<xsl:call-template name="ae_task_description" />
		</div>
  </xsl:template>
</xsl:stylesheet>
