<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd" 
	xmlns:htd="http://www.example.org/WS-HT" 
	xmlns:htda="http://www.example.org/WS-HT/api">
	<xsl:output method="html" indent="yes" encoding="UTF-8" />
	<xsl:template name="ae_task_comments" xml:space="default">
		<xsl:param name="taskId" />		
		<xsl:param name="taskStatus" />	
		<xsl:param name="finalState" />
		<div id="addcomment_section">
			<xsl:if test="trt:permissions/trt:addComment">
				<div id="addcomment">
					<a href="" id="aetask_cmd_addcomment" title="Add Comment"> Add Comment </a>
				</div>
			</xsl:if>				
		   <table class="ruledlisting" style="border:1px solid #666;" >
		      <thead><tr><td style="width:20%"></td><td></td></tr></thead>
		      <tfoot></tfoot>
		      <tbody id="commentlisting">
			      <xsl:choose>
				      <xsl:when test="count(trt:operational/trt:comments/htda:comment)= 0">
			         	<tr><td colspan="2" >No Comments</td></tr>
		         	</xsl:when>
		         	<xsl:otherwise>
		         		<xsl:apply-templates select="trt:operational/trt:comments/htda:comment" >
				            <xsl:with-param name="taskId" select="$taskId" />		            
								<xsl:with-param name="taskStatus" select="$taskStatus" />
								<xsl:with-param name="finalState" select="$finalState" />
		         		</xsl:apply-templates>
		         	</xsl:otherwise>
		         </xsl:choose>
		      </tbody>
		   </table>              
		</div>		
	</xsl:template>
	<xsl:template match="htda:comment" xml:space="default">			
		<xsl:param name="taskId" />		
		<xsl:param name="taskStatus" />	
		<xsl:param name="finalState" />
		<xsl:variable name="commentId"><xsl:value-of select="trt:commentId/text()"/></xsl:variable>
		<xsl:variable name="addedBy"><xsl:value-of select="htda:addedBy/text()"/></xsl:variable>
		<tr>
         <td class="commentmeta">         
            <span style="font-size:0.9em"> Added By: <em><xsl:value-of select="$addedBy"/></em></span> <br/>
            <span style="font-size:0.9em"> Date Added: <xsl:value-of select="htda:addedAt/text()"/></span>
         </td>
         <td>
            <div id="commenttext_{$commentId}" class="commenttext" style="padding:1px;margin:1px;">
            	<xsl:value-of select="htda:text/text()"/>
            </div>            
            <div style="text-align:right;">
	            <xsl:if test="trt:modified">
		            <em>Modified by <xsl:value-of select="trt:modified/trt:modifiedBy/text()"/> on <xsl:value-of select="trt:modified/trt:modifiedAt/text()"/>.</em><br/>
	            </xsl:if>
	            <xsl:if test="$finalState='false' and ($addedBy = $principalName or //trt:permissions/@isBusinessAdministrator = 'true')">
	               <span style="font-size:0.9em" class="commentoplinks" aetaskid="{$taskId}" aecommentid="{$commentId}" aeaddedby="{$addedBy}" >
	                  <a class="updatecommentop" href="#">Update</a><xsl:text>&#160;|&#160;</xsl:text>
	                  <a class="deletecommentop" href="#">Delete</a>
	              </span>
              </xsl:if>
            </div>                        
         </td>	
		</tr>
	</xsl:template>  
</xsl:stylesheet>
