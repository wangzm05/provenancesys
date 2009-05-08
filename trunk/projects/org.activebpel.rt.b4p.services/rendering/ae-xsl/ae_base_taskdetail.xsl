<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd" 
	xmlns:tsst="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-state-wsdl.xsd"
	xmlns:htd="http://www.example.org/WS-HT" 
	xmlns:htda="http://www.example.org/WS-HT/api"  
   xmlns:aefp="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/formparams"
   xmlns:aefe="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/errors">
 
 	<!--  
		=========================================================================
		This is the base template that is used to render a general task detail
		page with the following:
		1. Command button bar (e.g. to Claim, Complete) at the top of the page.
		   (implemented by template ae_task_command_buttons in ae_imp_taskbuttons.xsl).

		2. Table showing the task meta data such as owner, state, date time etc.
		   (implemented by template ae_task_metadata_grid in ae_imp_taskmetadatagrid.xsl).
		   
		3. Tab content area for work item. This section is left empty. 
		   Custom renders should render the work item area in the template named ae_task_workitem.
		   See ae_deault_task.xsl for an example.
		   
		4. Tab content area and markup for attachments.
		   (implemented by template ae_task_attachments in ae_imp_taskattachments.xsl).
		   
		5. Tab content area and markup for comments.
		   (implemented by template ae_task_comments in ae_imp_taskcomments.xsl).
		
		6. Calls a template named 'ae_html_header' to allow implemented to inject
		   their own content to the html header. E.g links to CSS and Javascript files.
		
		=========================================================================
	-->	
	<xsl:import href="ae_escapexml.xsl" />
 	<xsl:import href="ae_imp_taskbuttons.xsl" />
 	<xsl:import href="ae_imp_taskmetadatagrid.xsl" />
 	<xsl:import href="ae_imp_taskcomments.xsl" /> 	
 	<xsl:import href="ae_imp_taskattachments.xsl" /> 	
	<xsl:output method="html" indent="yes" encoding="UTF-8" />
	
	<!--
		======================================  
			Main Parameters  
		======================================
	 -->
	<!-- principalName: string  -->
	<xsl:param name="principalName" />
	<!-- parameterDoc: document  -->
	<xsl:param name="parameterDoc" />
	<!-- commandDoc: document  -->
	<xsl:param name="commandDoc" />
	<!-- errorDoc: document  -->
	<xsl:param name="errorDoc" />
		
	<!-- 
		=====================================================
		Main entry point to the template. 
		Renders (via template calls) button bar, metadata table,
		and tabs (along with content) for the work item, attachments
		and comments.
		=====================================================
	 -->		
	<xsl:template match="trt:taskInstance" xml:space="default">
	
		<!--  Global variables  -->
		<xsl:variable name="taskId"><xsl:value-of select="trt:identifier/text()"/></xsl:variable>
		<xsl:variable name="taskStatus"><xsl:value-of select="trt:context/trt:status/text()"/></xsl:variable>
		<!--  Display version of the task state -->
		<xsl:variable name="taskDisplayStatus">
		  <xsl:choose>
		    <xsl:when test="$taskStatus='READY'">
		      <xsl:text>Unclaimed</xsl:text>
		    </xsl:when>
		    <xsl:when test="$taskStatus='RESERVED'">
		      <xsl:text>Claimed</xsl:text>
		    </xsl:when>
		    <xsl:when test="$taskStatus='IN_PROGRESS'">
		      <xsl:text>Started</xsl:text>
		    </xsl:when>
		    <xsl:when test="$taskStatus='COMPLETED'">
		      <xsl:text>Completed</xsl:text>
		    </xsl:when>
		    <xsl:when test="$taskStatus='FAILED'">
		      <xsl:text>Failed</xsl:text>
		    </xsl:when>
		    <xsl:when test="$taskStatus='OBSOLETE'">
		      <xsl:text>Obsolete</xsl:text>
		    </xsl:when>
		    <xsl:when test="$taskStatus='EXITED'">
		      <xsl:text>Exited</xsl:text>
		    </xsl:when>	    		    	    
		    <xsl:when test="$taskStatus='SUSPENDED'">
		      <xsl:text>Suspended</xsl:text>
		    </xsl:when>	    		    	    		    	    		    	    
		    <xsl:otherwise>
		      <xsl:value-of select="$taskStatus"/>
		    </xsl:otherwise>
		  </xsl:choose>
		</xsl:variable>
		<!--  Boolean to indicate if the task is in final state -->	
		<xsl:variable name="finalState">
		  <xsl:choose>
		    <xsl:when test="$taskStatus='COMPLETED' or $taskStatus='OBSOLETE' or $taskStatus='ERROR' or $taskStatus='FAILED' or $taskStatus='EXITED'"><xsl:value-of select="true()"/></xsl:when>
		    <xsl:otherwise><xsl:value-of select="false()"/></xsl:otherwise>
		  </xsl:choose>
		</xsl:variable>		
			
    <html>
    	<head>
	      <title><xsl:call-template name="ae_html_header_title" /></title>
	      <xsl:call-template name="ae_html_header_imports" />
			<xsl:call-template name="ae_html_header_script">
				<xsl:with-param name="taskId" select="$taskId" />		
				<xsl:with-param name="taskStatus" select="$taskStatus" />
			</xsl:call-template>
			<xsl:call-template name="ae_html_header_custom" >
				<xsl:with-param name="taskId" select="$taskId" />		
				<xsl:with-param name="taskStatus" select="$taskStatus" />
			</xsl:call-template>						
		</head>
      <body>
      	<!--  
      		Form with hidden fields to generate form data to create action commands
      		when the command buttons are pressed.
      	 -->      
      	<form id="aetaskcommandform" name="aetaskcommandform" method="post" action="" >
      		<input name="taskId" type="hidden" ><xsl:attribute name="value"><xsl:value-of select="$taskId" /></xsl:attribute></input>
      		<input name="ae_command_name" id="aetaskcommandform_command_name" type="hidden" value=""/>
      		<input name="ae_command_id" id="aetaskcommandform_command_id" type="hidden" value=""/>
      		<input name="ae_command_data" id="aetaskcommandform_command_data" type="hidden" value=""/>
      	</form>      	
			<div id="body">
				<div id="header">
					<div id="headermenucontainer">					
						<span>Signed in as <em><xsl:value-of select="$principalName"/></em></span>&#160;|&#160;
						<span><a href="../logout.jsp">Sign Off</a></span>&#160;|&#160; 
						<span><a class="backtoinbox" href="../">Back to Inbox</a></span>&#160;|&#160;
						<span><a href="task?taskId={$taskId}">Refresh</a></span>&#160;|&#160; 
						<span><a href="../help" target="aeworkflowhelp" >Help</a></span>
					</div>
					<xsl:call-template name="ae_header_logo_image" />								
				</div><!-- header -->			
				<div id="content">      
					<xsl:call-template name="ae_error_messages" />
					<div class="entrypreview">						
						<div class="toolbar" style="border-bottom:1px solid #999;">	
							<xsl:call-template name="ae_task_command_buttons" />
						</div><!-- end toolbar -->						
						<div class="previewcontent" style="">	
							<xsl:call-template name="ae_task_metadata_grid" >
								<xsl:with-param name="taskStatus" select="$taskStatus" />
								<xsl:with-param name="taskDisplayStatus" select="$taskDisplayStatus" />
						   </xsl:call-template>
						</div> <!--  end previewcontent -->											
					</div><!-- entry preview -->	
					<xsl:call-template name="ae_body_tabcontainer">
						<xsl:with-param name="taskId" select="$taskId" />
						<xsl:with-param name="taskStatus" select="$taskStatus" />
						<xsl:with-param name="finalState" select="$finalState" />
					</xsl:call-template>
				</div><!--  end content -->
				<div id="footer">
						<span id="copy">&#169; 2008 Active Endpoints, Inc. All rights reserved.</span>
				</div><!-- footer -->						
			</div><!--  end body -->			
		<xsl:call-template name="ae_body_dialogs" />							
      </body>
    </html>
  </xsl:template>
  
  <!--  Tab contribution points -->
  <xsl:template name="ae_tab_header_extension" xml:space="default">
  </xsl:template>
  <xsl:template name="ae_tab_content_extension" xml:space="default">
  </xsl:template>
  
  <!--  Template to display error messages -->
  <xsl:template name="ae_error_messages" xml:space="default">
		<xsl:if test="count($errorDoc/aefe:errors/aefe:parameter-error) > 0">		
			 <xsl:call-template name="ae_parameter_error_messages" />		 
		</xsl:if>  
		<xsl:if test="count($errorDoc/aefe:errors/aefe:command-error[@type='taskfault']) > 0">
			 <xsl:call-template name="ae_command_fault_messages" /> 
		</xsl:if>  		
		<xsl:if test="count($errorDoc/aefe:errors/aefe:command-error[@type='error']) > 0">
			 <xsl:call-template name="ae_command_error_messages" /> 
		</xsl:if>  			
  </xsl:template>
  
    <!--  Template to display form data parameter parsing error messages -->
  <xsl:template name="ae_parameter_error_messages" xml:space="default">
		 <div id="aestatusmessagecontainer" class="erroricon" style="display:block">
		 	<p>Error processing part data:</p>
		 	<div id="aestatusmessagedetailcontainer" style="display:block">
		 		<ul id="aestatusmessagedetail">
		 			<xsl:apply-templates select="$errorDoc/aefe:errors/aefe:parameter-error" />
		 		</ul>
		 	</div>
		 </div>  
  </xsl:template>
  
    <!--  Template to display task command fault messages -->
  <xsl:template name="ae_command_fault_messages" xml:space="default">
		 <div id="aestatusmessagecontainer" style="display:block">
	 		<ul>
	 			<xsl:for-each select="$errorDoc/aefe:errors/aefe:command-error[@type='taskfault']">
	 				<li><xsl:value-of select="text()" /> (command: <xsl:value-of select="@name" />) </li>
	 			</xsl:for-each>
	 		</ul>
		 </div>  
  </xsl:template>  
  
  <!--  Template to display command processing error messages -->
  <xsl:template name="ae_command_error_messages" xml:space="default">
		 <div id="aestatusmessagecontainer" class="erroricon" style="display:block">
		 	<div id="aestatusmessagedetailcontainer" style="display:block">
		 		Error processing command. <a id="aeshowstatusmessagedetail">(click for details)</a>
		 		<ul id="aestatusmessagedetail" style="display:none">
		 			<xsl:apply-templates select="$errorDoc/aefe:errors/aefe:command-error[@type='error']" />
		 		</ul>
		 	</div>
		 </div>  
  </xsl:template>  
  
  <!--  Form data parameter parsing specific errors -->
  <xsl:template match="aefe:parameter-error" xml:space="default">
  	<li>Part <strong><xsl:value-of select="substring-after(@name,'aetaskworkitem_outputpart_')" /></strong>: <xsl:value-of select="text()" /></li>
  </xsl:template>
  
  <!--  Command specific errors -->
  <xsl:template match="aefe:command-error[@type='error']" xml:space="default">
  		<li>
	  		Command: <xsl:value-of select="@name" /> <br/>
  			<textarea style="width:80%" rows="4" wrap="off" readonly="readonly" ><xsl:value-of select="text()" /></textarea>
  			<br/>
  		</li>
  </xsl:template> 
  
	<!--  Template that is called to generate tabs -->		
	<xsl:template name="ae_body_tabcontainer" xml:space="default">
		<xsl:param name="taskId" />		
		<xsl:param name="taskStatus" />
		<xsl:param name="finalState" />
		
		<div id="taskdetailtabcontainer">
			<ul id="taskdetailtablist">
               <li><a href="#fragment-workitem">Work Item</a></li>
               <li><a href="#fragment-attachments">Attachments</a></li>
               <li><a href="#fragment-comments">Comments</a></li>
               <xsl:call-template name="ae_tab_header_extension" />
			</ul>
           <div id="fragment-workitem" class="taskdetailtabdiv">
				<div id="workitem-content">
	            <xsl:call-template name="ae_task_workitem" >
		            <xsl:with-param name="taskId" select="$taskId" />		            
						<xsl:with-param name="taskStatus" select="$taskStatus" />		            
						<xsl:with-param name="finalState" select="$finalState" />		            							
	            </xsl:call-template>
            </div>
           </div>
           <div id="fragment-attachments" class="taskdetailtabdiv">
            <xsl:call-template name="ae_task_attachments" >
	            <xsl:with-param name="taskId" select="$taskId" />		            
            </xsl:call-template>
           </div>
           <div id="fragment-comments" class="taskdetailtabdiv">
            <xsl:call-template name="ae_task_comments" >
	            <xsl:with-param name="taskId" select="$taskId" />		            
					<xsl:with-param name="taskStatus" select="$taskStatus" />		            
					<xsl:with-param name="finalState" select="$finalState" />		            							
            </xsl:call-template>
           </div>
           <xsl:call-template name="ae_tab_content_extension" />	            
		</div><!--  end taskdetailtabcontainer -->								
	</xsl:template>
	
	<!--  Template that is called to jquery modal dialog divs -->		
	<xsl:template name="ae_body_dialogs" xml:space="default">
		<!--  hidden div containing set priority dialog (used with jqModal.js scripts)  -->
		<div id="aesetprioritydialog" style="padding:0px;" class="jqmWindow aedialog" >
		   <table class="aedialog_header">
		      <tr>
		         <td style=""><strong>Set Priority</strong></td>
		         <td style="width:18px;text-align:right;" ><input type="image" src="../images/cancel.png" class="jqmClose aedialog_close" /></td>
		      </tr>
		   </table>
		   <div id="aesetprioritydialog_content" style="font-size:0.8em;">
		      <p>
		      	New Priority: <input id="aesetprioritydialog_priority" name="priority" type="text" size="3" /> 
		      </p>
				<div style="display:inline;">
		  			<p style="padding:0;margin:10px;text-align:left;">
		     			<a style="display:inline;margin:0 10px 0 0;" href="" id="aetask_setprioritydialog_ok" title="Set Priority"> OK </a>
		     			<a style="display:inline;margin:0 10px 0 0;" class="jqmClose" href="" id="aetask_setprioritydialog_cancel" title="Cancel"> Cancel </a>
					</p>
				</div>         
			</div>
		</div>		
		<!--  hidden div containing Assign dialog (used with jqModal.js scripts)  -->
		<div id="aeassigncommanddialog" style="padding:0px;" class="jqmWindow aedialog" >
		   <table class="aedialog_header">
		      <tr>
		         <td style=""><strong>Assign</strong></td>
		         <td style="width:18px;text-align:right;" ><input type="image" src="../images/cancel.png" class="jqmClose aedialog_close" /></td>
		      </tr>
		   </table>
		   <div id="aeassigncommanddialog_content" style="font-size:0.8em;">
		      <p>
		      New Owner: <input id="aeassigncommanddialog_newowner" name="newowner" type="text" size="15" />
		      </p>
				<div style="display:inline;">
		  			<p style="padding:0;margin:10px;text-align:left;">
		     			<a style="display:inline;margin:0 10px 0 0;" href="" id="aetask_assigndialog_ok" title="Assign Task"> OK </a>
		     			<a style="display:inline;margin:0 10px 0 0;" class="jqmClose" href="" id="aetask_assigndialog_cancel" title="Cancel"> Cancel </a>
					</p>
				</div>         
			</div>
		</div>				
		<!--  hidden div containing Add (or Edit) Comment dialog (used with jqModal.js scripts)  -->
		<div id="aecommentdialog" style="padding:0px;" class="jqmWindow aedialog" >
		   <table class="aedialog_header">
		      <tr>
		         <td style=""><strong id="aecommentdialog_title">Comments</strong></td>
		         <td style="width:18px;text-align:right;" ><input type="image" src="../images/cancel.png" class="jqmClose aedialog_close" /></td>
		      </tr>
		   </table>
		   <div id="aecommentdialog_content" style="font-size:0.8em;">
				<div style="padding:0;margin:10px;text-align:left;">
		    		<input id="aecommentdialog_taskcomment_id"  type="hidden"/>
					<textarea id="aecommentdialog_taskcomment_text" name="taskcomment_text" rows="8" class="workitemdata_complex workitemdata_editable" > </textarea>
		      </div>
				<div style="display:inline;">
		  			<p style="padding:0;margin:10px;text-align:left;">
		     			<a style="display:inline;margin:0 10px 0 0;" href="" id="aetask_cmd_savecomment" title="Add comment"> OK </a>
		     			<a style="display:inline;margin:0 10px 0 0;" class="jqmClose" href="" id="aetask_cmd_cancelcomment" title="Cancel"> Cancel</a>
					</p>
				</div>         
			</div>
		</div>
		<!--  hidden div containing Set Sample Data dialog (used with jqModal.js scripts)  -->
		<div id="aesampledatadialog" style="padding:0px;" class="jqmWindow aedialog" >
		   <table class="aedialog_header">
		      <tr>
		         <td style=""><strong>Output Data Template</strong></td>
		         <td style="width:18px;text-align:right;" ><input type="image" src="../images/cancel.png" class="jqmClose aedialog_close" /></td>
		      </tr>
		   </table>
		   <div id="aesampledatadialog_content" style="font-size:0.8em;">
		      <div style="padding:0;margin:10px;text-align:left;">
		      	<strong>Part: </strong><em><span id="aesampledatadialog_partname"></span></em><br/>
		      	<textarea id="aesampledatadialog_preview" style="width:90%;" rows="8" wrap="off"  class="workitemdata_complex workitemdata_readonly" readonly="readonly" ></textarea>                  	
		      </div>
				<div style="display:inline;">
		  			<p style="padding:0;margin:10px;text-align:left;">
		     			<a style="display:inline;margin:0 10px 0 0;" href="" id="aetask_sampledatadialog_ok" title="Set template data"> OK </a>
		     			<a style="display:inline;margin:0 10px 0 0;" class="jqmClose" href="" id="aetask_sampledatadialog_cancel" title="Cancel"> Cancel </a>
					</p>
				</div>         
			</div>
		</div>	
	</xsl:template>	
	  
	<!--  Template that is called to inject javascript initialization code into the html header -->		
	<xsl:template name="ae_html_header_script" xml:space="default">
		<xsl:param name="taskId" />		
		<xsl:param name="taskStatus" />
		 	
		<script type="text/javascript">
			$(document).ready(function(){
				<xsl:variable name="lastCommand"><xsl:value-of select="$parameterDoc/aefp:parameters/aefp:parameter[@name='ae_command_name']/text()"/></xsl:variable>
				<xsl:choose>
					<xsl:when test="$lastCommand='addAttachment' or $lastCommand='deleteAttachment'">
						var initialTabIndex = 2;
					</xsl:when>
					<xsl:when test="$lastCommand='addComment' or $lastCommand='updateComment' or $lastCommand='deleteComment'">
						var initialTabIndex = 3;
					</xsl:when>										   
					<xsl:otherwise>
						var initialTabIndex = 0;
					</xsl:otherwise>					
				</xsl:choose>				   
			   gAeCurrentSelectionTaskId = '<xsl:value-of select="$taskId" />';
				aeXslInitTask(initialTabIndex);	 
			});
		</script>	
	</xsl:template>
			
	<!--  Template that is called to inject html header title -->		
	<xsl:template name="ae_html_header_title" xml:space="default">
     Task Detail - <xsl:value-of select="trt:presentation/trt:name/text()"/>
     for user <xsl:value-of select="$principalName"/> 	
	</xsl:template>
	
	<!--  Template that is called to inject logo image -->	
	<xsl:template name="ae_header_logo_image" xml:space="default">
		<img src="../images/logo.gif" style="border:0px" />
	</xsl:template>	
	
	<!--  Template that is called to inject html header imports such as css and javascript files -->		
	<xsl:template name="ae_html_header_imports" xml:space="default">
			<link rel="stylesheet" href="../css/aeworkflow.css" media="print,screen"  />
		   <link rel="stylesheet" href="../css/aetaskstate.css" type="text/css" media="print,screen" /> 
		   <link rel="stylesheet" href="../css/aetaskpriority.css" type="text/css" media="print,screen" /> 	
			<link rel="stylesheet" href="../css/jqModal.css" />
		
			<link rel="shortcut icon" href="../images/favicon.ico" type="image/x-icon" />
			<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
			<script type="text/javascript" src="../script/jquery.js"></script>
			<script type="text/javascript" src="../script/jquery.block.js"></script>
			<script type="text/javascript" src="../script/jqModal.js"></script>
			<script type="text/javascript" src="../script/aeworkflow-taskdefs.js"></script>	
			<script type="text/javascript" src="../script/aeworkflow-util.js"></script>
			<script type="text/javascript" src="../script/aeworkflow-common.js"></script>	
			<script type="text/javascript" src="../script/aeworkflow-xsltask.js"></script>	
		   <link rel="stylesheet" href="../css/jquery.tabs.css" type="text/css" media="print, projection, screen" />
		
		   <script type="text/javascript" src="../script/jquery.block.js"></script>
		   <script type="text/javascript" src="../script/jquery.form.js"></script>    
			<script type="text/javascript" src="../script/jquery.tabs.pack.js"></script>    
		    <xsl:comment> Additional IE/Win specific style sheet (Conditional Comments)</xsl:comment>
         <link rel="stylesheet" href="../css/jquery.tabs-ie.css" type="text/css" media="print, projection, screen" /> 
		   <link rel="stylesheet" href="../css/aeworkitem.css" type="text/css" media="print, projection, screen" /> 
		   <link rel="stylesheet" href="../css/aefiletypes.css" type="text/css" media="print, projection, screen" />
	</xsl:template>	
		
	<!--  Template that is called to inject html header data such as references to css or javascript files --> 		
	<xsl:template name="ae_html_header_custom2" xml:space="default">
		<xsl:param name="taskId"  />
		<xsl:param name="taskStatus" />
	</xsl:template>
	
	<!--  Template that is used to render the work item.  -->
	<xsl:template name="ae_task_workitem" xml:space="preserve">
		<xsl:param name="taskId" />		
		<xsl:param name="taskStatus" />	
		<xsl:param name="finalState" />	
		<p>This is the default work item</p>
	</xsl:template>
			   
</xsl:stylesheet>
