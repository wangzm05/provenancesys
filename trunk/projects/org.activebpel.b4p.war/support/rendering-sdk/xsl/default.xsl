<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

   <!-- Import your xsl which is responsible for rendering the task detail -->
   <xsl:import href="ae-xsl/ae_debug_task.xsl" />
   
   <!--
      ========================
      Define input parameters
      ======================== 
   --> 
   
   <!-- principal name -->
   <xsl:variable name="principalName" select="'JSmith'" />
      
   <!-- location of sample content for parameter document -->
   <xsl:variable name="parameterDoc" select="document('../xml-input/dotransform-parameters/parameterDoc.xml')" />
   <!-- location of sample content for command document -->
   <xsl:variable name="commandDoc" select="document('../xml-input/dotransform-parameters/commandDoc.xml')" />
   <!-- location of sample content for error document -->
   <xsl:variable name="errorDoc" select="document('../xml-input/dotransform-parameters/errorDoc.xml')" />      
   
</xsl:stylesheet>