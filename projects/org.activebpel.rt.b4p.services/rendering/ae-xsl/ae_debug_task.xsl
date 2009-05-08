<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
   xmlns:tns="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd"
   xmlns:htd="http://www.example.org/WS-HT"
   xmlns:api="http://www.example.org/WS-HT/api"
   xmlns:htdp="http://www.example.org/WS-HT/protocol"
	xmlns:aeren="urn:aetask:rendering"
	xmlns:aefe="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/errors">

	<!--  
		=========================================================================
		This extends the default task (generic) template to contribute
		a tab that displays the task xml content.
		=========================================================================
	-->	 
 	<xsl:import href="ae_escapexml.xsl" />
 	<xsl:import href="ae_default_task.xsl" />

  <!--  Tab contribution points -->
  <xsl:template name="ae_tab_header_extension" xml:space="default">
	  <li><a href="#fragment-taskxml">Task XML</a></li>
  </xsl:template>
  <xsl:template name="ae_tab_content_extension" xml:space="default">
	<div id="fragment-taskxml" class="taskdetailtabdiv">
		<textarea style="width:98%;" rows="20" wrap="off" class="workitemdata_output workitemdata_complex workitemdata_editable" ><xsl:apply-templates mode="escape-xml" select="." /></textarea>
	</div>
  </xsl:template>	
</xsl:stylesheet>
