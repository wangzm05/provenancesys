<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:trt="http://schemas.active-endpoints.com/b4p/wshumantask/2007/10/aeb4p-task-rt.xsd"
	xmlns:htd="http://www.example.org/WS-HT"
	xmlns:htapi="http://www.example.org/WS-HT/api"
	xmlns:htp="http://www.example.org/WS-HT/protocol"
	xmlns:htdt="http://www.example.org/WS-HT/api/xsd"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

	<xsl:import href="common.xsl" />

	<xsl:template match="trt:taskInstance">
   	<htdt:getTaskInfoResponse>
   		<htdt:task>
   			<htapi:id><xsl:value-of select="trt:identifier"/></htapi:id>
   			<htapi:taskType><xsl:value-of select="trt:taskType"/></htapi:taskType>
   			<htapi:name><xsl:value-of select="trt:name"/></htapi:name>
   			<htapi:status><xsl:value-of select="trt:context/trt:status"/></htapi:status>
   			<htapi:priority><xsl:value-of select="trt:context/trt:priority"/></htapi:priority>
   			<xsl:apply-templates select="trt:context/trt:taskInitiator"/>
   			<xsl:apply-templates select="trt:context/trt:taskStakeholders"/>
   			<xsl:apply-templates select="trt:context/trt:potentialOwners"/>
   			<xsl:apply-templates select="trt:context/trt:businessAdministrators"/>
   			<xsl:apply-templates select="trt:context/trt:actualOwner"/>
   			<xsl:apply-templates select="trt:context/trt:notificationRecipients"/>
   			<htapi:createdOn><xsl:value-of select="trt:context/trt:createdOn"/></htapi:createdOn>
   			<htapi:createdBy><xsl:value-of select="trt:context/trt:createdBy"/></htapi:createdBy>
   			<xsl:apply-templates select="trt:context/trt:activationTime"/>
   			<xsl:apply-templates select="trt:context/trt:expirationTime"/>
   			<htapi:isSkipable><xsl:value-of select="trt:context/trt:isSkipable"/></htapi:isSkipable>
   			<htapi:hasPotentialOwners><xsl:value-of select="count(trt:context/trt:potentialOwners/*) > 0"/></htapi:hasPotentialOwners>
   			<htapi:startByExists><xsl:value-of select="trt:context/trt:startByMillis != ''"/></htapi:startByExists>
   			<htapi:completeByExists><xsl:value-of select="trt:context/trt:completeByMillis != ''"/></htapi:completeByExists>
            <xsl:apply-templates select="trt:presentation/trt:name"/>
            <xsl:apply-templates select="trt:presentation/trt:subject"/>
   			<htapi:renderingMethodExists>true</htapi:renderingMethodExists>
   			<htapi:hasOutput><xsl:value-of select="count(trt:operational/trt:output/trt:part) > 0"/></htapi:hasOutput>
   			<htapi:hasFault><xsl:value-of select="trt:operational/trt:fault/@name != ''"/></htapi:hasFault>
   			<htapi:hasAttachments><xsl:value-of select="count(trt:operational/trt:attachments/trt:attachment) > 0"/></htapi:hasAttachments>
   			<htapi:hasComments><xsl:value-of select="count(trt:operational/trt:comments/trt:comment) > 0"/></htapi:hasComments>
   			<htapi:escalated><xsl:value-of select="trt:context/trt:lastEscalatedTime != ''"/></htapi:escalated>
   			<xsl:apply-templates select="trt:context/trt:primarySearchBy"/>
   		</htdt:task>
   	</htdt:getTaskInfoResponse>
	</xsl:template>
	
	<xsl:template match="trt:taskInitiator | trt:taskStakeholders | trt:potentialOwners | trt:businessAdministrators | trt:actualOwner | trt:notificationRecipients | trt:activationTime | trt:expirationTime | trt:primarySearchBy | trt:name | trt:subject">
		<xsl:if test="@xsi:nil != 'true'">
	      <xsl:element name="{concat('htapi:',local-name(.))}" namespace="http://www.example.org/WS-HT/api">
	         <xsl:apply-templates select="@*|node()|comment()" />
	      </xsl:element>
      </xsl:if>
    </xsl:template>
    
    <xsl:template match="trt:name">
      <htapi:presentationName>
         <xsl:value-of select="."/>
         <xsl:copy-of select="@*"/>
      </htapi:presentationName>
    </xsl:template>

    <xsl:template match="trt:subject">
      <htapi:presentationSubject>
         <xsl:value-of select="."/>
         <xsl:copy-of select="@*"/>
      </htapi:presentationSubject>
    </xsl:template>
</xsl:stylesheet>
