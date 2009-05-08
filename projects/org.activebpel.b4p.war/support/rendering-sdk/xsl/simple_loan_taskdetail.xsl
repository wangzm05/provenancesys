<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
   xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd" 
   xmlns:htd="http://www.example.org/WS-HT" 
   xmlns:htda="http://www.example.org/WS-HT/api"  
   xmlns:aefe="http://schemas.active-endpoints.com/humanworkflow/2007/07/renderxsl/errors"
   xmlns:loan="http://schemas.active-endpoints.com/sample/LoanRequest/2008/02/loanRequest.xsd" >
   
   
   <!--
      ===============================
      BEGIN - Define input parameters
      Remove this when deployed.
      ===============================
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
      ===============================
      END - Define input parameters
      ===============================
   -->   
   
   <xsl:template match="trt:taskInstance" xml:space="default">
   
      <!--  loan input data - loanApprovalRequest elem  -->
      <xsl:variable name="loanApprovalRequest" select="//trt:operational/trt:input/trt:part[@name='Document']/loan:loanProcessRequest" />
      <!-- output data - loanApprovalResponse elem  -->
      <xsl:variable name="loanApprovalResponse" select="//trt:operational/trt:output/trt:part[@name='Document']/loan:loanApprovalResponse" />
   
       <html>
          <body>
            <p>
                <h1>Active Mortgage, Inc. </h1>
               <table border="1" width="100%">
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
                                                      
               </table>             
            </p>
          </body>
      </html>        
   </xsl:template>   
   
</xsl:stylesheet>
