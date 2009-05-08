<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd" 
	xmlns:htd="http://www.example.org/WS-HT" 
	xmlns:htda="http://www.example.org/WS-HT/api"  
	xmlns:aefe="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/errors">
 
	<!--  
		=========================================================================
		This is the default template that is used to render a generic task detail
		page with multiple text area input fields for each of the output parts 
		
		This uses the ae_base_taskdetail.xsl to render all but the work item area.
		=========================================================================
	-->	 
 
 	<xsl:import  href="ae_base_taskdetail.xsl" />
 	<xsl:import  href="ae_imp_taskinput.xsl" />
 	<xsl:import  href="ae_escapexml.xsl" />
 	
	<xsl:template name="ae_html_header_custom" xml:space="preserve">
		<xsl:param name="taskId" select="trt:identifier/text()" />
		<xsl:param name="taskStatus" select="trt:context/trt:status/text()" />
		<script type="text/javascript">
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
			});
			
		</script>
	</xsl:template>	
	
	<!--  Template to display a generic work item area which lists all input and output parts  -->
	<xsl:template name="ae_task_workitem" xml:space="default">
		<xsl:param name="taskId" />		
		<xsl:param name="taskStatus" />	
		<xsl:param name="finalState" />	
			
		<form class="workitemdatagrid" id="taskdetail_workitem_form"  method="POST"><xsl:attribute name="action">task?taskId=<xsl:value-of select="$taskId" /></xsl:attribute>
			<input class="aetaskworkitem_taskid" name="taskId" type="hidden" ><xsl:attribute name="value"><xsl:value-of select="$taskId" /></xsl:attribute></input>
			<input class="aetaskworkitem_submitmode" name="aetaskworkitem_submitmode" type="hidden" value="xsl" />
			<!--  create list of output part names -->
			<input name="_aexml_aetaskworkitem_outputparts" type="hidden" >
				<xsl:attribute name="value"><xsl:call-template name="emit_output_partnames_as_xml" /></xsl:attribute>
			</input>
			
			<!-- INPUT -->
			<div  id="workitemdata_inputsection" style="width:100%;">   
			  <table style="width:100%;">
			     <tr>
			     		<td colspan="2" style="border-bottom:1px solid #333;background-color:#9cf;"><strong>Input Section</strong>							
			     		</td>
			     </tr>
					<!--  display input parts -->
					<xsl:call-template name="ae_task_input" >
		            <xsl:with-param name="taskId" select="$taskId" />	
		            <xsl:with-param name="taskStatus" select="$taskStatus" />			
					</xsl:call-template>
			     <tr><td colspan="2"> </td></tr>
			      <tr>
			      	<td colspan="2" style="border-top:1px solid #333;background-color:#9cf;">
			            <select id="workitemdata_selection" name="aetaskworkitem_selection"><option value="output">Output</option><option value="fault">Fault</option></select>
			      	</td>
			      </tr>			      			     
				</table>
			</div>				
			
	      <!--  OUTPUT -->
	      <div id="workitemdata_outputsection" style="width:100%;">
		      <table style="width:100%;">
					<!-- output data -->
	            <tr><td colspan="2"></td></tr>
							<xsl:for-each select="trt:interfaceMetadata/trt:output/trt:part">
							      <xsl:call-template name="task_workitem_output_part" >
						            <xsl:with-param name="taskId" select="$taskId" />	
						            <xsl:with-param name="taskStatus" select="$taskStatus" />	            
						            <xsl:with-param name="partMetadata" select="." />
							      </xsl:call-template>
							</xsl:for-each>	            
		         <tr><td colspan="2"></td></tr>
		      </table>
		    </div>			
						
		   <!--  FAULT --> 
			<div  id="workitemdata_faultsection" style="width:100%;">
	      	<table style="width:100%;">
			      <xsl:choose>
				      <xsl:when test="count(trt:interfaceMetadata/trt:faults/trt:fault) = 0 ">
			         	<tr><td colspan="2" >No Faults.</td></tr>
		         	</xsl:when>
		         	<xsl:otherwise>
					      <xsl:call-template name="task_workitem_fault_data" >
				            <xsl:with-param name="taskId" select="$taskId" />	
				            <xsl:with-param name="taskStatus" select="$taskStatus" />	            
				            <xsl:with-param name="fault" select="//trt:operational/trt:fault" />
					      </xsl:call-template>
		         	</xsl:otherwise>
	         	</xsl:choose>
	      	</table>
	      </div>
	      
	      <!--  Submit buttons  -->
	      <xsl:if test="$taskStatus='IN_PROGRESS'">
			<div style="display:inline;">
				<p style="padding:0;margin:10px;text-align:left;">
					<a style="display:inline;margin:0 10px 0 0;" href="" id="aetask_cmd_save" title="Save data"> Save </a>
					<a style="display:inline;margin:0 10px 0 0;" href="" id="aetask_cmd_cancel" title="Reset form"> Clear </a>
				</p>
			</div>
			</xsl:if>
	      	      
		</form>
	</xsl:template>
	

	<!--  
		============================================
		OUTPUT PART
		Display each output part in a single table row 
		=============================================
	 -->	
	<xsl:template name="task_workitem_output_part" xml:space="default">
		<xsl:param name="taskId" />	
		<xsl:param name="taskStatus" />
		<xsl:param name="partMetadata" />	
		<xsl:variable name="partName" select="$partMetadata/@name" />
		<xsl:variable name="partData" select="//trt:operational/trt:output/trt:part[@name=$partName]" />
		<xsl:choose>
			<xsl:when test="$taskStatus ='IN_PROGRESS' and count(//trt:permissions/trt:setOutput) = 1">
		      <xsl:call-template name="task_workitem_output_part_editable" >
		           <xsl:with-param name="taskId" select="$taskId" />	
		           <xsl:with-param name="taskStatus" select="$taskStatus" />	            
		           <xsl:with-param name="partMetadata" select="$partMetadata" />
		           <xsl:with-param name="partName" select="$partName" />
		           <xsl:with-param name="partData" select="$partData" />
		      </xsl:call-template>			
			</xsl:when>
			<xsl:otherwise>
		      <xsl:call-template name="task_workitem_part_readonly_view" >
		           <xsl:with-param name="taskId" select="$taskId" />	
		           <xsl:with-param name="taskStatus" select="$taskStatus" />	            
		           <xsl:with-param name="partMetadata" select="$partMetadata" />
		           <xsl:with-param name="partName" select="$partName" />
		           <xsl:with-param name="partData" select="$partData" />
		      </xsl:call-template>			
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>	 
	 
	 <!--  output part data in editable fields -->
	<xsl:template name="task_workitem_output_part_editable" xml:space="default">
		<xsl:param name="taskId" />	
		<xsl:param name="taskStatus" />
		<xsl:param name="partMetadata" />	
		<xsl:param name="partName" />
		<xsl:param name="partData"/>
		<!--  
			Create name for the html form input tag: prefix part name with 'aetaskworkitem_outputpart_'
			and suffix '_aexml_' for xml data.
		 -->
		<xsl:variable name="formInputName">
			<xsl:if test="$partMetadata/@typeHint='complex'"><xsl:text>_aexml_</xsl:text></xsl:if>	
			<xsl:text>aetaskworkitem_outputpart_</xsl:text><xsl:value-of select="$partName" />			
		</xsl:variable>
      <tr>
         <td style="width:15%;">
         	<xsl:attribute name="class"><xsl:text>label</xsl:text>
         	<xsl:if test="$errorDoc/aefe:errors/aefe:parameter-error[@name=concat('aetaskworkitem_outputpart_',$partName)]">	
         		<xsl:text> workitem_input_error_icon</xsl:text>
         	</xsl:if>
         	</xsl:attribute>
         	<strong><xsl:value-of select="$partName"/> : </strong>
         </td>
         <td>            
	         <xsl:choose>
	         	<xsl:when test="$partMetadata/@typeHint='complex'">
<textarea style="width:95%;" rows="5" wrap="off" class="workitemdata_output workitemdata_complex workitemdata_editable" aeoutputpartname="{$partName}" name="{$formInputName}"><xsl:apply-templates mode="escape-xml" select="$partData/child::*" /></textarea>
<div id="" class="workitemdata_sample" aeoutputpartname="{$partName}"><xsl:apply-templates mode="escape-xml" select="$partMetadata/child::*" /></div>
	         	</xsl:when>
	         	<xsl:when test="$partMetadata/@typeHint='string'">
<textarea style="width:95%;" rows="5" wrap="off" class="workitemdata_output workitemdata_complex workitemdata_editable" aeoutputpartname="{$partName}" name="{$formInputName}"><xsl:value-of select="$partData/text()" /></textarea>
	         	</xsl:when>	         	
	         	<xsl:otherwise>
						<textarea style="width:95%;" rows="5" wrap="off" class="workitemdata_output workitemdata_complex workitemdata_editable" aeoutputpartname="{$partName}" name="{$formInputName}"><xsl:apply-templates mode="escape-xml" select="$partData/child::*" /></textarea>
	         	</xsl:otherwise>
	         </xsl:choose>
         </td>
      </tr>
	</xsl:template>	
	
	<!--  
		============================================
		FAULT PART
		Display fault part in a single table row 
		=============================================
	 -->		
	<xsl:template name="task_workitem_fault_data" xml:space="default">
		<xsl:param name="taskId" />	
		<xsl:param name="taskStatus" />	
		<xsl:param name="fault" />
		<xsl:variable name="currentFaultLocalName">
			<xsl:if test="contains($fault/@name,':')">
				<xsl:value-of select="substring-after($fault/@name,':')"/>
		   </xsl:if>	   
			<xsl:if test="contains($fault/@name,':') = false()">
				<xsl:value-of select="$fault/@name"/>
		   </xsl:if>	   		   
		</xsl:variable>
		<xsl:variable name="cansetfault">
			<xsl:choose>
				<xsl:when test="$taskStatus='IN_PROGRESS' and (//trt:permissions/trt:setFault or //trt:permissions/trt:deleteFault)"><xsl:value-of select="true()"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="false()"/></xsl:otherwise>
			</xsl:choose>		
		</xsl:variable>
      <tr>
         <td class="label" style="width:15%;"><strong>Fault Name: </strong></td>
         <td>
         	<xsl:choose>
	        		<xsl:when test="$cansetfault = 'true'">
		            <select id="aetaskworkitem_faultnames" name="aetaskworkitem_faultname" class="workitemdata_editable">
		               <option value="">(none)</option>
				         <xsl:for-each select="trt:interfaceMetadata/trt:faults/trt:fault">
								<xsl:variable name="faultLocalName">
									<xsl:if test="contains(@name,':')">
										<xsl:value-of select="substring-after(@name,':')"/>
								   </xsl:if>	   
									<xsl:if test="contains(@name,':') = false()">
										<xsl:value-of select="@name"/>
								   </xsl:if>	   		   
								</xsl:variable>		         
								<option >
					          	<xsl:attribute name="value"><xsl:value-of select="@name" /></xsl:attribute>
					          	<xsl:if test="$faultLocalName = $currentFaultLocalName">
					          		<xsl:attribute name="selected"><xsl:value-of select="'true'" /></xsl:attribute>
					          	</xsl:if>
					          	<xsl:value-of select="$faultLocalName"/>
								</option>
				         </xsl:for-each>               
		           </select>
					</xsl:when>
					<xsl:otherwise>
						<!--  read only view  -->
						<span style="width:95%;" class="workitemdata_input workitemdata_simple workitemdata_readonly"><xsl:value-of select="$currentFaultLocalName"/></span>
					</xsl:otherwise>
				</xsl:choose>	           
         </td>	
      </tr>
      <tr>	
         <td style="width:15%;">
         	<xsl:attribute name="class"><xsl:text>label</xsl:text>
         	<xsl:if test="$errorDoc/aefe:errors/aefe:parameter-error[@name='aetaskworkitem_faultdata']">	
         		<xsl:text> workitem_input_error_icon</xsl:text>
         	</xsl:if>
         	</xsl:attribute>         
	         <strong>Fault Data: </strong></td>
         <td>
         	<xsl:choose>
	        		<xsl:when test="$cansetfault = 'true'">         
<textarea id="aetaskworkitem_faultdata" name="_aexml_aetaskworkitem_faultdata" rows="4" wrap="off" class="workitemdata_fault workitemdata_complex workitemdata_editable" ><xsl:apply-templates mode="escape-xml" select="$fault/child::*" />
</textarea>
					</xsl:when>
					<xsl:otherwise>
						<!--  read only view  -->
<textarea style="width:95%;" rows="4" wrap="off" readonly="readonly" class="workitemdata_input workitemdata_complex workitemdata_readonly"><xsl:apply-templates mode="escape-xml" select="$fault/child::*" /></textarea>												
					</xsl:otherwise>
				</xsl:choose>		           

         </td>		         
      </tr>
	</xsl:template>
	
	<!--  Emits list of output part names as an element list. The result is use in hidden field '_aexml_aetaskworkitem_outputparts'  -->
	<xsl:template name="emit_output_partnames_as_xml" xml:space="default">	
		<xsl:text disable-output-escaping="yes" >&lt;aepartnames &gt;</xsl:text>		                         
			<xsl:for-each select="trt:interfaceMetadata/trt:output/trt:part">
				<xsl:text disable-output-escaping="yes" >&lt;aepartname &gt;</xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes" >&lt;/aepartname &gt;</xsl:text>
			</xsl:for-each>
		<xsl:text disable-output-escaping="yes" >&lt;/aepartnames &gt;</xsl:text>
	</xsl:template>						
	
</xsl:stylesheet>
