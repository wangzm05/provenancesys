<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd" 
	xmlns:htd="http://www.example.org/WS-HT" 
	xmlns:htda="http://www.example.org/WS-HT/api"  
	xmlns:aeren="urn:aetask:rendering"
	xmlns:aefp="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/formparams"	
	xmlns:aefe="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/errors">
 
	<!--  
		=========================================================================
		This is the default template that is used to render a notication.		
		This uses the ae_base_taskdetail.xsl to render header, footer etc.
		=========================================================================
	-->	 
 
 	<xsl:import href="ae_escapexml.xsl" />
 	<xsl:import href="ae_default_task.xsl" />
 	<xsl:import href="ae_imp_taskdesc.xsl" />
 	<xsl:output method="html" indent="yes" encoding="UTF-8" />  
 	
	<xsl:template name="ae_html_header_custom" xml:space="preserve">
		<xsl:param name="taskId" select="trt:identifier/text()" />
		<xsl:param name="taskStatus" select="trt:context/trt:status/text()" />
		<script type="text/javascript">
			<xsl:if test="$parameterDoc/aefp:parameters/aefp:parameter[@name='ae_command_name']/text()= 'remove'
			and $parameterDoc/aefp:parameters/@method= 'post'">
				<!--  Go back to back up one to inbox if the Remove button was pressed.  -->
				window.location = "..";
			</xsl:if>								
		   gAeCurrentState = '<xsl:value-of select="$taskStatus"/>';
		   gAeCurrentSelectionTaskId = '<xsl:value-of select="$taskId" />';
		   var taskProps = {};
		   taskProps["taskid"] = gAeCurrentSelectionTaskId;
		   taskProps["state"] = gAeCurrentState;
		   taskProps["owner"] = "<xsl:value-of select="trt:context/trt:actualOwner/text()"/>";
		   <xsl:if test="trt:operational/trt:fault">
		   taskProps["hasfault"] =  'true';
		   </xsl:if>
		   gAeCurrentTask = new AeTaskDetail(gAeCurrentSelectionTaskId, taskProps);
		   <xsl:for-each select="trt:permissions/*">gAeCurrentTask.addPermission("<xsl:value-of select="local-name()" />"); </xsl:for-each>		   
			$(document).ready(function(){	 
				aeXslInitWorkItem();
				aeEnableTaskCommandButton("remove", true); 
			});
			
		</script>
	</xsl:template>	
	 	 	
	<!--  Override base template to change html title -->		
	<xsl:template name="ae_html_header_title" xml:space="default">
     Notification - <xsl:value-of select="trt:presentation/trt:name/text()"/>
     for principal <xsl:value-of select="$principalName"/> 	
	</xsl:template>	
	
	<xsl:template name="ae_task_command_buttons" xml:space="default">
		<table class="toolbarbuttons">
			<tr>								
				<td>
					<a href="" id="aetask_cmd_remove" title="remove">
						<xsl:attribute name="class">
							<xsl:if test="trt:permissions/trt:remove">aeEnableCommandButton</xsl:if>
						</xsl:attribute>Remove</a>					
				</td>
			</tr>
		</table>
  </xsl:template>
  
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
		     <td class="gridlabel">Priority:</td><td colspan="3"  class="gridvalue gridvline" style="width:90%"><span class="aetaskdata_priority"><xsl:value-of select="trt:context/trt:priority/text()"/></span></td>
		  </tr>						  		  
		  <tr>
		     <td class="gridlabel">ID:</td><td colspan="3"  class="gridvalue gridvline" style="width:90%"><span class="aetaskdata_pid"><xsl:value-of select="trt:identifier/text()"/></span></td>
		  </tr>						  
		</table>	
		<!--  display description -->
		<div style="font-size:1.0em;padding:10px;" class="aetaskdata_detaileddescription">		
			<xsl:call-template name="ae_task_description" />
		</div>
  </xsl:template>
  
	<!--  Template to display a generic work item area which lists all input and output parts  -->
	<xsl:template name="ae_task_workitem" xml:space="default">
		<xsl:param name="taskId" />		
		<xsl:param name="taskStatus" />	
		<xsl:param name="finalState" />	
			
		<form class="workitemdatagrid" id="taskdetail_workitem_form"  method="POST">			
			<!-- INPUT -->
			<div  id="workitemdata_inputsection" style="width:100%;">   
			  <table style="width:100%;">
			     <tr>
			     		<td colspan="2" style="border-bottom:1px solid #333;background-color:#9cf;"><strong>Notification Input</strong>							
			     		</td>
			     </tr>
					<!--  display input parts -->
					<xsl:call-template name="ae_task_input" >
		            <xsl:with-param name="taskId" select="$taskId" />	
		            <xsl:with-param name="taskStatus" select="$taskStatus" />			
					</xsl:call-template>
			     <tr><td colspan="2"> </td></tr>
				</table>
			</div>
		</form>				
  </xsl:template>
</xsl:stylesheet>
