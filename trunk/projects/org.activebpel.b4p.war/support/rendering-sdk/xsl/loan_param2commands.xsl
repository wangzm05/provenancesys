<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
      xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd" 
   xmlns:htd="http://www.example.org/WS-HT" 
   xmlns:htda="http://www.example.org/WS-HT/api"  
   xmlns:loan="http://schemas.active-endpoints.com/sample/LoanRequest/2008/02/loanRequest.xsd"
   xmlns:aefp="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/formparams"
   xmlns:aefe="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/errors"
   xmlns:aetc="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/taskcommands" >
   <!--  Import base stylesheet -->
   <xsl:import href="ae-xsl/ae_base_param2command.xsl" />
   <xsl:output method="xml" indent="yes" encoding="UTF-8" />   
   
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
   

   <!--  
      =========================================================================
      Override base template to handle loan approval task work item html form.
      =========================================================================
   -->   
   <xsl:template name="ae_workitem_param2command" xml:space="default">  
      <xsl:variable name="taskId"><xsl:value-of select="@taskId" /></xsl:variable>
      <!--  
         Handle Output data If the html submit button named 'save-loan-form' was pressed.
         In this example, we set the output only if response or rejection description is given.
         [If the response or rejection description is not given, then an error is reported back to the user
         via template named 'ae_report_command_parameter_errors'].
      -->
      <xsl:if test="aefp:parameter[@name='save-loan-form']/text()
         and (string-length( normalize-space(aefp:parameter[@name='response_description']/text()) ) &gt; 0
         or string-length( normalize-space(aefp:parameter[@name='rejection_description']/text()) ) &gt; 0)">
         <!--  Create Output Part Data -->
         <xsl:variable name="loanApprovalResponse">
               <loan:loanApprovalResponse xmlns:loan="http://schemas.active-endpoints.com/sample/LoanRequest/2008/02/loanRequest.xsd">                                        
                  <loan:responseToLoanRequest><xsl:value-of select="aefp:parameter[@name='approval_status']" /></loan:responseToLoanRequest>             
                        <loan:responseDescription><xsl:value-of select="aefp:parameter[@name='response_description']" /></loan:responseDescription>                        
                  <xsl:if test="aefp:parameter[@name='approval_status']/text() = 'declined'">
                     <loan:rejectionReason>
                        <loan:reason><xsl:value-of select="aefp:parameter[@name='rejection_reason']" /></loan:reason>
                        <loan:description><xsl:value-of select="aefp:parameter[@name='rejection_description']" /></loan:description>
                     </loan:rejectionReason>                
                  </xsl:if>                                                                        
                  </loan:loanApprovalResponse>                
         </xsl:variable>
         <!--  Set the output part data by calling the  'ae_setoutputpart_command' command -->
         <xsl:call-template name="ae_setoutputpart_command">
            <xsl:with-param name="taskId" select="$taskId" />
            <xsl:with-param name="partName" select="string('Document')" />
            <xsl:with-param name="partData" select="$loanApprovalResponse" /> 
         </xsl:call-template>                                           
      </xsl:if>
   </xsl:template>   
   
   <!--  
       Form Validation:
       Override validate template to report errors.  
   -->
   <xsl:template name="ae_validate_parameters" >
      <!--  simple test: approval or underreview response_description must be at least 10 chars -->
      <xsl:if test="aefp:parameter[@name='approval_status']/text() != 'declined' and aefp:parameter[@name='save-loan-form']/text()
         and string-length( normalize-space(aefp:parameter[@name='response_description']/text()) ) &lt; 10">
   
         <aefe:parameter-error name="response_description">Please enter more details for the loan response description. </aefe:parameter-error>
      </xsl:if>
      
      <!--  test if loan is delcined, then rejection desc. is required.  -->
      <xsl:if test="aefp:parameter[@name='approval_status']/text() = 'declined'
         and string-length( normalize-space(aefp:parameter[@name='rejection_description']/text()) ) = 0">
   
         <aefe:parameter-error name="rejection_description"> The reason for declining the loan is required. </aefe:parameter-error>
      </xsl:if>
      
      
   </xsl:template>
</xsl:stylesheet>
