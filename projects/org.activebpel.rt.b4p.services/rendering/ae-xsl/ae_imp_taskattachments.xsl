<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd" 
	xmlns:htd="http://www.example.org/WS-HT" 
	xmlns:htda="http://www.example.org/WS-HT/api">
	<xsl:output method="html" indent="yes" encoding="UTF-8" />
	<xsl:template name="ae_task_attachments" xml:space="default">
		<xsl:param name="taskId" />		
		<xsl:if test="trt:permissions/trt:addAttachment">
			<div id="attachfile" class="toolbar" style="border:1px solid #999;">
			   <form id="aetask_attachment_form" action="" method="post" enctype="multipart/form-data">
					<table style="border:none;width:100%;">
			        <tr>
			            <td>
				      		<input name="taskId" type="hidden" ><xsl:attribute name="value"><xsl:value-of select="$taskId" /></xsl:attribute></input>
				      		<input name="ae_command_name" id="aetaskattachmentform_command_name" type="hidden" value="addAttachment"/>
				      		<input name="ae_command_id" id="aetaskattachmentform_command_id" type="hidden" value="task_attachment"/>
			               <input id="task_attachment" size="60" name="task_attachment" type="file" />
			               <a style="display:inline;" href="" id="aetask_cmd_addfile" title="Attach File"> Attach File </a>
			            </td>
			         </tr>
			      </table>
			   </form>
			</div>
		</xsl:if>			
		<div>
		   <table class="ruledlisting" style="border:1px solid #666;" >
		      <thead><tr><th>File Name</th><th>Type</th><th>Attached By</th><th>Date</th><th>Download</th><th>Delete</th></tr></thead>
		      <tfoot></tfoot>
		      <tbody id="attachmentlisting">
			      <xsl:choose>
				      <xsl:when test="count(trt:operational/trt:attachments/htda:attachment)=0">
			         	<tr><td colspan="6" >No Attachments.</td></tr>
		         	</xsl:when>
		         	<xsl:otherwise>
		         		<xsl:apply-templates select="trt:operational/trt:attachments/htda:attachment" >
				            <xsl:with-param name="taskId" select="$taskId" />		            
							</xsl:apply-templates>		         		
		         	</xsl:otherwise>
		         </xsl:choose>		         		         

		      </tbody>
		   </table>
		</div>
	</xsl:template>

	<xsl:template match="htda:attachment" xml:space="default">
		<xsl:param name="taskId" />
		<xsl:variable name="attachmentName"><xsl:value-of select="htda:attachmentInfo/htda:name/text()"/></xsl:variable>
		<xsl:variable name="attachmentId"><xsl:value-of select="htda:attachmentInfo/trt:attachmentId/text()"/></xsl:variable>
		<xsl:variable name="contentType"><xsl:value-of select="htda:attachmentInfo/htda:contentType/text()"/></xsl:variable>
		<!--  Content Type hint used as a CSS class to display the file type icon  -->
		<xsl:variable name="typeHint">
			<xsl:choose>
			   <!--  if octet-stream, then use the file extension -->
				<xsl:when test="$contentType='application/octet-stream' and contains($attachmentName,'.')"><xsl:value-of select="substring-after($attachmentName,'.')"/></xsl:when>
				<!--  if content type is given, then use the content type, after replaycing [/.-] chars with underscore ('_')  -->
				<xsl:when test="contains($contentType,'/')"><xsl:value-of select="translate(translate(translate($contentType,'/', '_'),'-','_'),'.','_')"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="$contentType"/></xsl:otherwise>
			</xsl:choose>			
		</xsl:variable>
				
		<tr attachmentName="{$attachmentName}"  attachmentId="{$attachmentId}" aetaskid="{$taskId}">
			<td><span class="filename filename_{$typeHint}"><xsl:value-of select="$attachmentName"/></span></td>
			<td><xsl:value-of select="$contentType"/></td>
			<td><xsl:value-of select="htda:attachmentInfo/htda:attachedBy/text()"/></td>
			<td><xsl:value-of select="htda:attachmentInfo/htda:attachedAt/text()"/></td>
			<td>
				<a>
					<xsl:if test="htda:attachmentInfo/htda:accessType/text() = 'URL'">
						<xsl:attribute name="href"><xsl:value-of select="htda:value/text()" /></xsl:attribute>
						<xsl:attribute name="target"><xsl:text>b4pattachment</xsl:text></xsl:attribute>
					</xsl:if>
					<xsl:if test="htda:attachmentInfo/htda:accessType/text() != 'URL'">
						<xsl:attribute name="class"><xsl:text>openattachment</xsl:text></xsl:attribute>
						<xsl:attribute name="href"></xsl:attribute>
						<xsl:attribute name="target"><xsl:text>b4pattachment</xsl:text></xsl:attribute>
					</xsl:if>					
				 Download
				</a>
			</td>
			<td><xsl:if test="//trt:permissions/trt:deleteAttachments "><a class="deleteattachment" href="">Delete</a></xsl:if></td>
		</tr>
	</xsl:template>  

</xsl:stylesheet>
