<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd"
	xmlns:tsst="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-state-wsdl.xsd"
	xmlns:aefp="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/formparams"
	xmlns:aetc="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/taskcommands"
	xmlns:aefe="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/errors">
	
	<xsl:import href="ae_base_commands.xsl" />
	<!--  
		=========================================================================
		The base style sheet responsible for transforming html form data submitted by
		the user (e.g. from task detail page) to a set of declarative commands.
		
		The input to this style sheet is a simple name value parameter document
		containing the html form data. The output of this xsl should produce
		a set of <aetc:taskcommand> elements.
		
		INPUT XML FORMAT:
			<aefp:parameters xmlns:aefp="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/formparams">
				<aefp:parameter type="" name="">form data</aefp:parameter>
			</aefp:parameters>
			
		The parameters named ae_principalName and ae_taskId should always exist in the input XML.
		The xsl that is used to render the presentation should also include command name, id and data
		hidden fields if commands (Claim etc.) should also be executed.
		
		OUTPUT XML FORMAT:
			<aetc:taskcommands xmlns:aetc="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/taskcommands"
				principalName="" taskId="">
				<aetc:taskcommand name="" id="">
					command specific (optional) data
				</aetc:taskcommand>
			</aetc:taskcommands>
		===================================================================================
	-->	

	<xsl:output method="xml" indent="yes" encoding="UTF-8" />
	
	<!--
		======================================  
			Main Parameters  
		======================================
	 -->
	<!-- errorDom: document  -->
	<xsl:param name="errorDom" />	
	
	<xsl:template match="aefp:parameters" xml:space="default">
		<xsl:variable name="taskId"><xsl:value-of select="@taskId" /></xsl:variable>
		<aetc:taskcommands>
			<!--  set principal name and task ref as a attribute in the root -->
			<xsl:attribute name="principalName"><xsl:value-of select="@principalName" /></xsl:attribute>
			<xsl:attribute name="taskId"><xsl:value-of select="@taskId" /></xsl:attribute>
			
			<!--  
				Allow custom xsl templates to report param error such as missing data
			 -->
			<xsl:call-template name="ae_validate_parameters" />
			
			<!--  
				===================================================================================
				Generate a generic command button action . This asssumes that the xml input has a parameter 
				named 'ae_command_name' (and the value of the parameter being the command name) and 
				with the command's (optional) data. For example:
				
				<aefp:parameter type="text" name="ae_command_name">claim</aefp:parameter>
				generates:
				<aetc:taskcommand name="claim" />

				and 
				
				<aefp:parameter type="text" name="ae_command_name">assign</aefp:parameter>
				<aefp:parameter type="text" name="ae_command_data">mford</aefp:parameter>
				generates:
				<aetc:taskcommand name="assign">mford</aetc:taskcommand>
				
				This template converts html parameters into commands for the following:
				claim
				start
				revoke
				assign
				setpriority
				complete
				fail
				===================================================================================
			-->
			<xsl:if test="aefp:parameter[@name='ae_command_name']/text() and count($errorDom/aefe:errors/aefe:parameter-error) = 0">
				<xsl:variable name="name"><xsl:value-of select="aefp:parameter[@name='ae_command_name']/text()" /></xsl:variable>
				<xsl:variable name="id"><xsl:value-of select="aefp:parameter[@name='ae_command_id']/text()" /></xsl:variable>
				<xsl:variable name="data"><xsl:value-of select="aefp:parameter[@name='ae_command_data']/text()" /></xsl:variable>
				
				<xsl:choose>				
					<xsl:when test="$name = 'claim'">
						<xsl:call-template name="ae_claim_command">
							<xsl:with-param name="taskId" select="$taskId" />
						</xsl:call-template>
					</xsl:when>		
								
					<xsl:when test="$name = 'start'">
						<xsl:call-template name="ae_start_command" >
							<xsl:with-param name="taskId" select="$taskId" />
						</xsl:call-template>
					</xsl:when>
					
					<xsl:when test="$name = 'stop'">
						<xsl:call-template name="ae_stop_command" >
							<xsl:with-param name="taskId" select="$taskId" />
						</xsl:call-template>
					</xsl:when>	
									
					<xsl:when test="$name = 'release'">
						<xsl:call-template name="ae_release_command" >
							<xsl:with-param name="taskId" select="$taskId" />
						</xsl:call-template>
					</xsl:when>
					
					<xsl:when test="$name = 'skip'">
						<xsl:call-template name="ae_skip_command" >
							<xsl:with-param name="taskId" select="$taskId" />
						</xsl:call-template>
					</xsl:when>
					
					<xsl:when test="$name = 'complete'">
						<xsl:call-template name="ae_complete_command" >
							<xsl:with-param name="taskId" select="$taskId" />
						</xsl:call-template>
					</xsl:when>
					
					<xsl:when test="$name = 'activate'">
						<xsl:call-template name="ae_activate_command" >
							<xsl:with-param name="taskId" select="$taskId" />
						</xsl:call-template>
					</xsl:when>
					
					<xsl:when test="$name = 'setpriority'">
						<xsl:call-template name="ae_setpriority_command" >
							<xsl:with-param name="taskId" select="$taskId" />
							<xsl:with-param name="priority" select="$data" />
						</xsl:call-template>
					</xsl:when>
					
					<xsl:when test="$name = 'delegate'">
						<xsl:call-template name="ae_delegate_command" >
							<xsl:with-param name="taskId" select="$taskId" />
							<xsl:with-param name="username" select="$data" />
						</xsl:call-template>
					</xsl:when>		
					
					<xsl:when test="$name = 'forward'">
						<xsl:call-template name="ae_forward_command" >
							<xsl:with-param name="taskId" select="$taskId" />
							<xsl:with-param name="username" select="$data" />
						</xsl:call-template>
					</xsl:when>					
								
										
					<xsl:when test="$name = 'suspend'">
						<xsl:call-template name="ae_suspend_command" >
							<xsl:with-param name="taskId" select="$taskId" />
						</xsl:call-template>
					</xsl:when>
					
					<xsl:when test="$name = 'resume'">
						<xsl:call-template name="ae_resume_command" >
							<xsl:with-param name="taskId" select="$taskId" />
						</xsl:call-template>
					</xsl:when>

					<xsl:when test="$name = 'addComment'">
						<xsl:call-template name="ae_addcomment_command">
							<xsl:with-param name="taskId" select="$taskId" />
							<xsl:with-param name="text" select="$data" />	
						</xsl:call-template>		
					</xsl:when>

					<xsl:when test="$name = 'updateComment'">
						<xsl:call-template name="ae_updatecomment_command">
							<xsl:with-param name="taskId" select="$taskId" />
							<xsl:with-param name="commentId" select="$id" />	
							<xsl:with-param name="text" select="$data" />	
						</xsl:call-template>		
					</xsl:when>
					
					<xsl:when test="$name = 'deleteComment'">
						<xsl:call-template name="ae_deletecomment_command">
							<xsl:with-param name="taskId" select="$taskId" />
							<xsl:with-param name="commentId" select="$id" />		
						</xsl:call-template>		
					</xsl:when>
					
					<xsl:when test="$name = 'fail'">
						<xsl:call-template name="ae_fail_command">
							<xsl:with-param name="taskId" select="$taskId" />
							<xsl:with-param name="faultName" select="$id" />	
							<xsl:with-param name="faultData" select="$data" />			
						</xsl:call-template>		
					</xsl:when>	
					
					<xsl:when test="$name = 'remove'">
						<xsl:call-template name="ae_remove_command" >
							<xsl:with-param name="taskId" select="$taskId" />
						</xsl:call-template>
					</xsl:when>									
															
					<xsl:when test="$name = 'addAttachment'">
						<xsl:call-template name="ae_addattachment_command">
							<xsl:with-param name="taskId" select="$taskId" />
							<xsl:with-param name="attachmentName" select="$id" />	
						</xsl:call-template>		
					</xsl:when>
					
					<xsl:when test="$name = 'deleteAttachment'">
						<xsl:call-template name="ae_deleteattachment_command">
							<xsl:with-param name="taskId" select="$taskId" />
							<xsl:with-param name="attachmentId" select="$id" />	
						</xsl:call-template>		
					</xsl:when>
					
					
				</xsl:choose>
			</xsl:if>
			
			 <!--  
 				===================================================================================
			 		Also process work item form data. (unless there were parse errors)
				===================================================================================
			 -->
			<xsl:if test="count($errorDom/aefe:errors/aefe:parameter-error) = 0">
				 <xsl:call-template name="ae_workitem_param2command" />		 
			</xsl:if>
		</aetc:taskcommands>	
	</xsl:template>	
	
	
	 <!--  
			===================================================================================
	 		Template allow one to report input parameter errors. This template is left empty by design
	 		to allow custom param2command xsl sheets ("subclasses") to import this
	 		xsl and implemen the templated named "ae_report_command_parameter_errors".
	 		The errors should be reported with in <aefe:parameter-error> elements. 
	 		
	 		Note: The current implemetation does not process any commands if there 
	 		are one or more parameter errors.
		===================================================================================
	 -->	
	<xsl:template name="ae_validate_parameters" />
	
	 <!--  
			===================================================================================
	 		Template to process work item form data. This template is left empty by design
	 		to allow custom param2command xsl sheets ("subclasses") to import this
	 		xsl and implement the templated named "ae_workitem_param2command" 
		===================================================================================
	 -->		
	<xsl:template name="ae_workitem_param2command" />
	
</xsl:stylesheet>
