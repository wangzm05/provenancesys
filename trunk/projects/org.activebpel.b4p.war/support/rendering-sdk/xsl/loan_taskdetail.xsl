<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
   xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd" 
   xmlns:htd="http://www.example.org/WS-HT" 
   xmlns:htda="http://www.example.org/WS-HT/api"  
   xmlns:aefe="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/errors"
   xmlns:loan="http://schemas.active-endpoints.com/sample/LoanRequest/2008/02/loanRequest.xsd" >
    
   <!--  
      Import AE default template which is a generic display.
      The template named 'ae_task_workitem' will be overridden
      in this file to render the custom work item area.
    -->   
   <xsl:import href="ae-xsl/ae_base_taskdetail.xsl" />
   <xsl:import href="ae-xsl/ae_escapexml.xsl" />
   
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
   

   <!--  START DEBUGGING ONLY: Tab contribution points to display task xml -->
   <xsl:template name="ae_tab_header_extension" xml:space="default">
      <li><a href="#fragment-taskxml">Task XML</a></li>
   </xsl:template>
   <xsl:template name="ae_tab_content_extension" xml:space="default">
      <div id="fragment-taskxml" class="taskdetailtabdiv">
         <textarea style="width:98%;" rows="20" wrap="off" class="workitemdata_output workitemdata_complex workitemdata_editable" ><xsl:apply-templates mode="escape-xml" select="." /></textarea>
      </div>
   </xsl:template>
   <!--  END DEBUGGING --> 

   <!--  override 'ae_html_header' template to add your own html header content -->
   <xsl:template name="ae_html_header_custom" xml:space="preserve">
      <!--  example of how to inject html or javascript to the html header element -->
      <script type="text/javascript">
         // Example code: validate response-reason field 
         function validate_loan_form(aWorkItemForm)
         {
            var resp = aWorkItemForm.approval_status.value;
            var respReason = aWorkItemForm.response_description.value;
            if ('declined' != resp &amp;&amp; (respReason == null || respReason == ""))
            {
               alert("Response reason is required.");
               return false;
            }
            return true;
         }
      </script>      
   </xsl:template>   
   
   
   <!-- 
      ======================================================   
      Render the custom work item  
      ======================================================      
   -->
   <xsl:template name="ae_task_workitem" xml:space="default">
      <xsl:param name="taskId" />      
      <xsl:param name="taskStatus" />  
      <xsl:param name="finalState" />  
                  
      <!--  loan input data - loanApprovalRequest elem  -->
      <xsl:variable name="loanApprovalRequest" select="//trt:operational/trt:input/trt:part[@name='Document']/loan:loanProcessRequest" />
      <!-- output data - loanApprovalResponse elem  -->
      <xsl:variable name="loanApprovalResponse" select="//trt:operational/trt:output/trt:part[@name='Document']/loan:loanApprovalResponse" />
      
      
      <!--  Set up work item html form. Use POST method and submit the same URI.  -->
      <form class="workitemdatagrid" id="taskdetail_workitem_form" method="POST" onsubmit="return validate_loan_form(this);">
         <!--  *** NOTE*** The taskId parameter is required.  -->
         <input class="aetaskworkitem_taskid" name="taskId" type="hidden" ><xsl:attribute name="value"><xsl:value-of select="$taskId" /></xsl:attribute></input>
      
          <div  id="workitemdata_inputsection" style="width:100%;">        
          
             <!--  
               Loan Information  
             -->
            <table style="width:100%;">       
                  <tr><td colspan="2" style="border-bottom:1px solid #333;background-color:#9cf;"><strong>Loan Information</strong></td></tr>
                  <tr>
                      <td class="label" style="width:15%;"><strong>Loan Type:</strong></td>
                      <td><xsl:value-of select="$loanApprovalRequest/loan:loanType/text()" /></td>
                  </tr>
                  <tr>
                      <td class="label" style="width:15%;"><strong>Description:</strong></td>
                      <td><xsl:value-of select="$loanApprovalRequest/loan:loanDescription/text()" /></td>
                  </tr>
                  <tr>
                      <td class="label" style="width:15%;"><strong>Amount:</strong></td>
                      <td><xsl:value-of select="$loanApprovalRequest/loan:amountRequested" /></td>
                  </tr>
                  
                  <!--  
                     Personal Information  
                  -->
                  <tr><td colspan="2" style="border-bottom:1px solid #333;background-color:#9cf;"><strong>Personal Information</strong></td></tr>
                  <tr>
                     <td colspan="2">
                        <table style="width:100%;border:0px;">
                          <tr>
                              <td class="label" style="width:15%;"><strong>Name:</strong></td>
                              <td style="width:35%;"><xsl:value-of select="$loanApprovalRequest/loan:lastName" />, <xsl:value-of select="$loanApprovalRequest/loan:loanRequestInfo/loan:firstName" /></td>
                              <td class="label" style="width:15%;"><strong>Day Phone:</strong></td>
                              <td style="width:35%;"><xsl:value-of select="$loanApprovalRequest/loan:dayPhone" /></td>
                          </tr>
                          <tr>
                              <td class="label" style="width:15%;"><strong>Social Security #:</strong></td>
                              <td style="width:35%;"><xsl:value-of select="$loanApprovalRequest/loan:socialSecurityNumber" /></td>
                              <td class="label" style="width:15%;"><strong>Evening Phone:</strong></td>
                              <td style="width:35%;"><xsl:value-of select="$loanApprovalRequest/loan:nightPhone" /></td>
                          </tr> 
                          <tr>
                              <td class="label" style="width:15%;"><strong>e-mail:</strong></td>
                              <td style="width:35%;">
                                 <a>
                                    <xsl:attribute name="href"><xsl:value-of select="concat('mailto:', $loanApprovalRequest/loan:responseEmail)" /></xsl:attribute>
                                    <xsl:value-of select="$loanApprovalRequest/loan:responseEmail" />
                                 </a>
                        </td>
                              <td class="label" style="width:15%;"><strong></strong></td>
                              <td style="width:35%;"></td>                       
                          </tr>
                        </table>
                     </td>
                  </tr>
                  
               <!--  Loan Status -->
               <tr><td colspan="2" style="border-bottom:1px solid #333;background-color:#9cf;"><strong>Loan Status</strong></td></tr>
               <xsl:choose>
                  <xsl:when test="$taskStatus= 'IN_PROGRESS'">
                     <xsl:call-template name="editable_loan_status">
                        <xsl:with-param name="loanApprovalResponse" select="$loanApprovalResponse" /> 
                     </xsl:call-template>
                  </xsl:when>
                  <xsl:otherwise>
                     <xsl:call-template name="readonly_loan_status">
                        <xsl:with-param name="loanApprovalResponse" select="$loanApprovalResponse" />
                     </xsl:call-template>             
                  </xsl:otherwise>              
               </xsl:choose>                          
               
               <!--  form submit buttons -->
               <tr><td colspan="2"><xsl:text>&#160;</xsl:text></td></tr>
               <xsl:if test="$taskStatus= 'IN_PROGRESS'">
                  <tr>
                     <td colspan="2">
                        <input type="submit" name="save-loan-form" value="Save" />
                        <xsl:text>&#160;&#160;</xsl:text>
                        <input type="reset" value="Reset" />
                     </td>
                  </tr>                                     
               </xsl:if>
            </table>          
         </div>      
      </form>
   </xsl:template>

   <!--  
      ======================================================   
       Display editable fields when task state = IN_PROGRESS  
      ======================================================       
    -->  
   <xsl:template name="editable_loan_status" xml:space="default">
      <xsl:param name="loanApprovalResponse" />          
         <tr>
             <td class="label" style="width:15%;"><strong>Loan Response:</strong></td>
              <td>
                 <select name="approval_status" class="workitemdata_editable" style="font-family: Arial;font-size:1em;background:#ffffff;">
                    <option value="underReview"><xsl:if test="$loanApprovalResponse/loan:responseToLoanRequest/text() = 'underReview'"><xsl:attribute name="selected"><xsl:text>true</xsl:text></xsl:attribute></xsl:if>Under Review</option>
                    <option value="approved"><xsl:if test="$loanApprovalResponse/loan:responseToLoanRequest/text() = 'approved'"><xsl:attribute name="selected"><xsl:text>true</xsl:text></xsl:attribute></xsl:if>Approved</option>
                    <option value="declined"><xsl:if test="$loanApprovalResponse/loan:responseToLoanRequest/text() = 'declined'"><xsl:attribute name="selected"><xsl:text>true</xsl:text></xsl:attribute></xsl:if>Declined</option>
                 </select>
              </td>             
         </tr>
         <tr>
             <td class="label" style="width:15%;"><strong>Response Reason:</strong></td>
             <td>
               <xsl:if test="$errorDoc/aefe:errors/aefe:parameter-error[@name='response_description']">  
                  <span class="workitem_input_error_icon"><xsl:value-of select="$errorDoc/aefe:errors/aefe:parameter-error[@name='response_description']" /></span><br/>
               </xsl:if>             
                <textarea name="response_description" class="workitemdata_output" style="font-family: Arial;font-size:1em;background:#eeeeee;width:85%" ><xsl:value-of select="$loanApprovalResponse/loan:responseDescription" /> </textarea>
             </td>
         </tr>
         <tr>
             <td class="label" style="width:15%;"><strong>Rejection Reason:</strong></td>
             <td>
                 <select name="rejection_reason" class="workitemdata_editable" style="font-family: Arial;font-size:1em;background:#ffffff;">
                    <option value="loanValue"><xsl:if test="$loanApprovalResponse/loan:rejectionReason/loan:reason/text() = 'loanValue'"><xsl:attribute name="selected"><xsl:text>true</xsl:text></xsl:attribute></xsl:if>Loan Value</option>
                    <option value="lowCredit"><xsl:if test="$loanApprovalResponse/loan:rejectionReason/loan:reason/text() = 'lowCredit'"><xsl:attribute name="selected"><xsl:text>true</xsl:text></xsl:attribute></xsl:if>Low Credit</option>
                    <option value="infoRequired"><xsl:if test="$loanApprovalResponse/loan:rejectionReason/loan:reason/text() = 'infoRequired'"><xsl:attribute name="selected"><xsl:text>true</xsl:text></xsl:attribute></xsl:if>More Information Required</option>
                 </select>
            <br/>             
               <xsl:if test="$errorDoc/aefe:errors/aefe:parameter-error[@name='rejection_description']"> 
                  <span class="workitem_input_error_icon"><xsl:value-of select="$errorDoc/aefe:errors/aefe:parameter-error[@name='rejection_description']" /></span><br/>
               </xsl:if>                          
                <textarea name="rejection_description" class="workitemdata_output" style="font-family: Arial;font-size:1em;background:#eeeeee;width:85%"><xsl:value-of select="$loanApprovalResponse/loan:rejectionReason/loan:description" /></textarea>
             </td>
         </tr>    
      
   </xsl:template>   
   
   <!--  
      ======================================================
      Display read only data (task is not in an IN_PROGRESS state ) 
      ======================================================      
   -->
   <xsl:template name="readonly_loan_status" xml:space="default">
      <xsl:param name="loanApprovalResponse" />    
         <tr>
             <td class="label" style="width:15%;"><strong>Loan Response:</strong></td>
             <td><xsl:value-of select="$loanApprovalResponse/loan:responseToLoanRequest" />
             </td>
         </tr>
         <tr>
             <td class="label" style="width:15%;"><strong>Response Reason:</strong></td>
             <td>
                <textarea name="response_description" class="workitemdata_readonly" readonly="readonly" style="font-family: Arial, Helvetica, sans-serif;font-size:1em;background:#eeeeee;width:85%" ><xsl:value-of select="$loanApprovalResponse/loan:responseDescription" /> </textarea>
             </td>
         </tr>
         <tr>
             <td class="label" style="width:15%;"><strong>Rejection Reason:</strong></td>
             <td>
               <em><xsl:value-of select="$loanApprovalResponse/loan:rejectionReason/loan:reason" /></em>: <br/>
                <textarea name="rejection_description" class="workitemdata_readonly" readonly="readonly" style="font-family: Arial, Helvetica, sans-serif;font-size:1em;background:#eeeeee;width:85%">                
<xsl:value-of select="$loanApprovalResponse/loan:rejectionReason/loan:description" /></textarea>
             </td>
         </tr>    
      
   </xsl:template>   
   
   
</xsl:stylesheet>
