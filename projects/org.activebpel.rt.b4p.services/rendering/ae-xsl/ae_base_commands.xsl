<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
   xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd"
	xmlns:aefp="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/formparams"
	xmlns:aetc="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/taskcommands"
	xmlns:htdt="http://www.example.org/WS-HT/api/xsd"
	xmlns:htd="http://www.example.org/WS-HT"
	xmlns:tsst="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-state-wsdl.xsd"
	xmlns:aefe="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/errors">	
	<xsl:output method="xml" indent="yes" encoding="UTF-8" />	
		
	<!--  
		=========================================================================
		The base style sheet responsible for generating task commands.
		These templates can be overridden to support ws-ht.
		
		To use any of the templates, use the xsl:call-template element
		and xsl:with-param to pass arguement where needed.
		
		Any errors that occur during the execution of a command or operation
		will reported in document containing errors, specifically aefe:command-error 
		elements with type attribute = 'taskfault' or 'error'.
		
		=========================================================================
	-->	
	
	<!-- 
		=========================================================================
		function: ae_claim_command()
		=========================================================================	
	 -->
	<xsl:template name="ae_claim_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:call-template name="simpleTaskOperation">
			<xsl:with-param name="taskId" select="$taskId" />	
			<xsl:with-param name="elementName" select="'claim'" />
		</xsl:call-template>	
	</xsl:template>
	
	
	<!-- 
		=========================================================================
		function: ae_start_command()
		=========================================================================	
	 -->	
	<xsl:template name="ae_start_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:call-template name="simpleTaskOperation">
			<xsl:with-param name="taskId" select="$taskId" />	
			<xsl:with-param name="elementName" select="'start'" />
		</xsl:call-template>	
	</xsl:template>
	
	<!-- 
		=========================================================================
		function: ae_stop_command()
		=========================================================================	
	 -->	
	<xsl:template name="ae_stop_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:call-template name="simpleTaskOperation">
			<xsl:with-param name="taskId" select="$taskId" />	
			<xsl:with-param name="elementName" select="'stop'" />
		</xsl:call-template>			
	</xsl:template>
			
	<!-- 
		=========================================================================
		function: ae_release_command()
		=========================================================================	
	 -->	
	<xsl:template name="ae_release_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:call-template name="simpleTaskOperation">
			<xsl:with-param name="taskId" select="$taskId" />	
			<xsl:with-param name="elementName" select="'release'" />
		</xsl:call-template>	
	</xsl:template>
	
	<!-- 
		=========================================================================
		function: ae_skip_command()
		=========================================================================	
	 -->	
	<xsl:template name="ae_skip_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:call-template name="simpleTaskOperation">
			<xsl:with-param name="taskId" select="$taskId" />	
			<xsl:with-param name="elementName" select="'skip'" />
		</xsl:call-template>			
	</xsl:template>
	
	<!-- 
		=========================================================================
		function: ae_complete_command()
		Complete command with out data
		=========================================================================	
	 -->	
	<xsl:template name="ae_complete_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:call-template name="simpleTaskOperation">
			<xsl:with-param name="taskId" select="$taskId" />	
			<xsl:with-param name="elementName" select="'complete'" />
		</xsl:call-template>			
	</xsl:template>
	
	<!-- 
		=========================================================================
		function: ae_setpriority_command()
		=========================================================================	
	 -->	
	<xsl:template name="ae_setpriority_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:param name="priority" />
		<xsl:variable name="htElement">
			<htdt:setPriority>
				<htdt:identifier><xsl:value-of select="$taskId"/></htdt:identifier>
				<htdt:priority><xsl:value-of select="$priority"/></htdt:priority>
			</htdt:setPriority>		
		</xsl:variable>	
		<xsl:call-template name="taskOperation">
			<xsl:with-param name="id" select="$taskId" />	
			<xsl:with-param name="htElement" select="$htElement" />
		</xsl:call-template>
	</xsl:template>	

	<!-- 
		=========================================================================
		function: ae_delegate_command()
		=========================================================================	
	 -->	
	<xsl:template name="ae_delegate_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:param name="username" />
		<xsl:variable name="htElement">
			<htdt:delegate>
				<htdt:identifier><xsl:value-of select="$taskId"/></htdt:identifier>
				<htdt:organizationalEntity>
					<htd:users>
						<htd:user><xsl:value-of select="$username"/></htd:user>
					</htd:users>
				</htdt:organizationalEntity>
			</htdt:delegate>
		</xsl:variable>	
		<xsl:call-template name="taskOperation">
			<xsl:with-param name="id" select="$taskId" />	
			<xsl:with-param name="htElement" select="$htElement" />
		</xsl:call-template>
	</xsl:template>
		
	<!-- 
		=========================================================================
		function: ae_forward_command()
		=========================================================================	
	 -->	
	<xsl:template name="ae_forward_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:param name="username" />
		<xsl:variable name="htElement">
			<htdt:forward>
				<htdt:identifier><xsl:value-of select="$taskId"/></htdt:identifier>
				<htdt:organizationalEntity>
					<htd:users>
						<htd:user><xsl:value-of select="username"/></htd:user>
					</htd:users>
				</htdt:organizationalEntity>
			</htdt:forward>
		</xsl:variable>	
		<xsl:call-template name="taskOperation">
			<xsl:with-param name="id" select="$taskId" />	
			<xsl:with-param name="htElement" select="$htElement" />
		</xsl:call-template>
	</xsl:template>			

	
	<!-- 
		=========================================================================
		function: ae_suspend_command()
		=========================================================================	
	 -->	
	<xsl:template name="ae_suspend_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:call-template name="simpleTaskOperation">
			<xsl:with-param name="taskId" select="$taskId" />	
			<xsl:with-param name="elementName" select="'suspend'" />
		</xsl:call-template>			
	</xsl:template>
	
	<!-- 
		=========================================================================
		function: ae_resume_command()
		=========================================================================	
	 -->	
	<xsl:template name="ae_resume_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:call-template name="simpleTaskOperation">
			<xsl:with-param name="taskId" select="$taskId" />	
			<xsl:with-param name="elementName" select="'resume'" />
		</xsl:call-template>			
	</xsl:template>
	
	<!-- 
		=========================================================================
		function: ae_activate_command()
		=========================================================================	
	 -->	
	<xsl:template name="ae_activate_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:call-template name="simpleTaskOperation">
			<xsl:with-param name="taskId" select="$taskId" />	
			<xsl:with-param name="elementName" select="'activate'" />
		</xsl:call-template>			
	</xsl:template>	
	
	<!-- 
		=========================================================================
		function: ae_remove_command()
		=========================================================================	
	 -->	
	<xsl:template name="ae_remove_command" xml:space="default"> 
		<xsl:param name="taskId" />
		<xsl:call-template name="simpleTaskOperation">
			<xsl:with-param name="taskId" select="$taskId" />	
			<xsl:with-param name="elementName" select="'remove'" />
		</xsl:call-template>			
	</xsl:template>									
	
	<!-- 
		=========================================================================
		function: ae_setoutputpart_command(nodelist:partDataList)
		Sets the output data given a list of the output part data <trt:part>
		elements.
		=========================================================================	
	 -->			
	<xsl:template name="ae_setoutputpart_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:param name="partName" />
		<xsl:param name="partData" />
		<xsl:variable name="htElement">
			<htdt:setOutput>
				<htdt:identifier><xsl:value-of select="$taskId"/></htdt:identifier>
				<htdt:part><xsl:value-of select="$partName"/></htdt:part>
				<htdt:taskData><xsl:copy-of select="$partData" /></htdt:taskData>
			</htdt:setOutput>		
		</xsl:variable>	
		<xsl:call-template name="taskOperation">
			<xsl:with-param name="id" select="$taskId" />	
			<xsl:with-param name="htElement" select="$htElement" />
		</xsl:call-template>			
	</xsl:template>	
			
	<!-- 
		=========================================================================
		function: ae_fail_command(string:faultName, node:faultData)
		Assigns fails the given task. The faultName and faultData tuple is
		optional (i.e fail with current fault already set in the task).
		=========================================================================	
	 -->		
	<xsl:template name="ae_fail_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:param name="faultName" />	
		<xsl:param name="faultData" />
		<xsl:variable name="htElement">
			<htdt:fail>
 				<htdt:identifier><xsl:value-of select="$taskId"/></htdt:identifier>		
				<xsl:if test="$faultName and $faultData">
					<xsl:variable name="faultNcName">
						<xsl:choose>
							<xsl:when test="contains($faultName,':')">
								<xsl:value-of select="substring-after($faultName,':')"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="$faultName"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>						
					<htdt:faultName><xsl:value-of select="normalize-space($faultNcName)"/></htdt:faultName>
					<htdt:faultData><xsl:copy-of select="$faultData" /></htdt:faultData>					
				</xsl:if>
			</htdt:fail>
		</xsl:variable>
		<xsl:call-template name="taskOperation">
			<xsl:with-param name="id" select="$taskId" />	
			<xsl:with-param name="htElement" select="$htElement" />
		</xsl:call-template>			
	</xsl:template>
	
	<!-- 
		=========================================================================
		function: ae_setfault_command(string:faultName, node:faultData)
		Sets the task fault. The fault name (string) and data (node) are required.
		=========================================================================	
	 -->		
	<xsl:template name="ae_setfault_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:param name="faultName" />	
		<xsl:param name="faultData" />
		<xsl:variable name="htElement">
			<htdt:setFault>
 				<htdt:identifier><xsl:value-of select="$taskId"/></htdt:identifier>
				<xsl:variable name="faultNcName">
					<xsl:choose>
						<xsl:when test="contains($faultName,':')">
							<xsl:value-of select="substring-after($faultName,':')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$faultName"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>						
				<htdt:faultName><xsl:value-of select="normalize-space($faultNcName)"/></htdt:faultName>
				<htdt:faultData><xsl:copy-of select="$faultData" /></htdt:faultData>
			</htdt:setFault>
		</xsl:variable>		
		<xsl:call-template name="taskOperation">
			<xsl:with-param name="id" select="$taskId" />	
			<xsl:with-param name="htElement" select="$htElement" />
		</xsl:call-template>			
	</xsl:template>
	
	<!-- 
		=========================================================================
		function: ae_deletefault_command()
		=========================================================================	
	 -->		
	<xsl:template name="ae_deletefault_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:call-template name="simpleTaskOperation">
			<xsl:with-param name="taskId" select="$taskId" />	
			<xsl:with-param name="elementName" select="'deleteFault'" />
		</xsl:call-template>			
	</xsl:template>
	
	<!-- 
		=========================================================================
		function: ae_addcomment_command(string:text)
		Adds an task comment given the comment text.
		=========================================================================	
	 -->		
	<xsl:template name="ae_addcomment_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:param name="text" />
		<xsl:variable name="htElement">
			<htdt:addComment>
				<htdt:identifier><xsl:value-of select="$taskId"/></htdt:identifier>
				<htdt:text><xsl:value-of select="$text"/></htdt:text>
			</htdt:addComment>		
		</xsl:variable>	
		<xsl:call-template name="taskOperation">
			<xsl:with-param name="id" select="$taskId" />	
			<xsl:with-param name="htElement" select="$htElement" />
		</xsl:call-template>
	</xsl:template>	
	
	
	<!-- 
		=========================================================================
		function: ae_updatecomment_command(string:commentId, string:text)
		Updates the ask comment given the comment id and the new text.
		=========================================================================	
	 -->		
	<xsl:template name="ae_updatecomment_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:param name="commentId" />
		<xsl:param name="text" />
		<xsl:variable name="aeElement">
			<tsst:updateComment>
			 <trt:identifier><xsl:value-of select="$taskId"/></trt:identifier>
			 <trt:commentId><xsl:value-of select="$commentId"/></trt:commentId>
			 <tsst:text><xsl:value-of select="$text"/></tsst:text>
			</tsst:updateComment>		
		</xsl:variable>	
		<xsl:call-template name="aeTaskOperation">
			<xsl:with-param name="id" select="$taskId" />	
			<xsl:with-param name="aeElement" select="$aeElement" />
		</xsl:call-template>
	</xsl:template>
	
	
	<!-- 
		=========================================================================
		function: ae_deletecomment_command(string:commentId)
		Deletes comment given the comment id.
		=========================================================================	
	 -->		
	<xsl:template name="ae_deletecomment_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:param name="commentId" />
		<xsl:variable name="aeElement">
			<tsst:deleteComment>
			 <trt:identifier><xsl:value-of select="$taskId"/></trt:identifier>
			 <trt:commentId><xsl:value-of select="$commentId"/></trt:commentId>
			</tsst:deleteComment>		
		</xsl:variable>	
		<xsl:call-template name="aeTaskOperation">
			<xsl:with-param name="id" select="$taskId" />	
			<xsl:with-param name="aeElement" select="$aeElement" />
		</xsl:call-template>
	</xsl:template>
	
	<!-- 
		=========================================================================
		function: ae_addattachment_command(string:attachmentName)
		Adds an attachment given the name of attachment defined in the file 
		upload form. For example, if the html form had the following field:
		    <input type="file" name="sample_attachment">
		Use the <input> field name 'sample_attachment' to identify the file to be attached
		to the task.
		=========================================================================	
	 -->		
	<xsl:template name="ae_addattachment_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:param name="attachmentName" />
		<xsl:call-template name="ae_generic_command">
			<xsl:with-param name="name" select="'addAttachment'" />	
			<xsl:with-param name="id" select="$attachmentName" />
		</xsl:call-template>	
	</xsl:template>
	
	<!-- 
		=========================================================================
		function: ae_deleteattachment_command(string:attachmentName)
		Deletes attachment given attachment id.
		=========================================================================	
	 -->			
	<xsl:template name="ae_deleteattachment_command" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:param name="attachmentId" />
		<xsl:variable name="aeElement">
			<tsst:deleteAttachmentById>
			 <trt:identifier><xsl:value-of select="$taskId"/></trt:identifier>
			 <tsst:attachmentId><xsl:value-of select="$attachmentId"/></tsst:attachmentId>
			</tsst:deleteAttachmentById>		
		</xsl:variable>	
		<xsl:call-template name="aeTaskOperation">
			<xsl:with-param name="id" select="$taskId" />	
			<xsl:with-param name="aeElement" select="$aeElement" />
		</xsl:call-template>
	</xsl:template>
		
	<!--  
		===================================================================================
		[ For internal use only - this template is used by the above command templates.]
		
		Generates a generic command action . This asssumes that the xml input has a parameter 
		named 'ae_command_name' (and the value of the parameter being the command name) and 
		with the command's (optional) data. For example:
		
		with parameter name = "claim"
		generates:
		<aetc:taskcommand name="claim" />

		and 
		
		with parameter name = "assign" and data = "mford"
		generates:
		<aetc:taskcommand name="assign">mford</aetc:taskcommand>
		===================================================================================
	-->	
	<xsl:template name="ae_generic_command" xml:space="default">
		<xsl:param name="name" />	
		<xsl:param name="id" />	
		<xsl:param name="data" />	
		<aetc:taskcommand name="{$name}" id="{$id}">
			<xsl:copy-of select="$data" />
		</aetc:taskcommand>		
	</xsl:template>

	<!--
		[ For internal use only - this template is used by the above command templates.]
		Generates a command which simply passes thru a ws-ht api task operation element.
		E.g:
		Param ID is the task id : urn:b4p:3
		Param elementName is the ht api element name:		 
	 -->	
   <xsl:template name="simpleTaskOperation" xml:space="default">
      <xsl:param name="taskId" /> 
      <xsl:param name="elementName" />
		<xsl:variable name="htElement">
	      <xsl:element name="{concat('htdt:',$elementName)}" namespace="http://www.example.org/WS-HT/api/xsd">
	         <htdt:identifier><xsl:value-of select="$taskId"/></htdt:identifier>
	      </xsl:element>
		</xsl:variable>	
		<xsl:call-template name="taskOperation">
			<xsl:with-param name="id" select="$taskId" />	
			<xsl:with-param name="htElement" select="$htElement" />
		</xsl:call-template>        
   </xsl:template>	
   
	<!--
		[ For internal use only - this template is used by the above command templates.]
		Generates a ws-ht command which simply passes thru a ws-ht api task operation element.
		E.g:
		Param ID is the task id : urn:b4p:3
		Param htElement is the ht api element:
		 
		<htdt:skip xmlns:htdt="http://www.example.org/WS-HT/api/xsd">
		 <htdt:identifier>
		    urn:b4p:3
		 </htdt:identifier>
		</htdt:skip>		 
	 -->	
   <xsl:template name="taskOperation" xml:space="default">
      <xsl:param name="id" /> 
      <xsl:param name="htElement" />  
      <aetc:taskcommand name="taskOperation" id="{$id}">
         <xsl:copy-of select="$htElement" />
      </aetc:taskcommand>     
   </xsl:template>
   
	<!--
		[ For internal use only - this template is used by the above command templates.]
		Generates a command which simply passes thru a AE specific task operation element.
		E.g:
		Param ID is the task id : urn:b4p:3
		Param aeElement is the ht api element:
		 
		<tsst:updateComment>
		 <trt:identifier><trt:identifier>
		 <trt:commentId></trt:commentId>
		 <tsst:text></tsst:text>
		</tsst:updateComment>
				 
	 -->	
   <xsl:template name="aeTaskOperation" xml:space="default">
      <xsl:param name="id" /> 
      <xsl:param name="aeElement" />  
      <aetc:taskcommand name="aeTaskOperation" id="{$id}">
         <xsl:copy-of select="$aeElement" />
      </aetc:taskcommand>     
   </xsl:template>	   	
		
</xsl:stylesheet>
