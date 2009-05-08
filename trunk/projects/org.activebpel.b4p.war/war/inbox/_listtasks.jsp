<%@page contentType="text/xml; charset=UTF-8" %>
<%@ taglib uri="http://activebpel.org/aetaglib" prefix="ae" %>
<ae:RequestEncoding value="UTF-8" />
<ae:SetNamespaceMapping prefix="htda" uri="http://www.example.org/WS-HT/api"  />
<ae:SetNamespaceMapping prefix="htdat" uri="http://www.example.org/WS-HT/api/xsd"  />
<jsp:useBean id="aeRequestContext" class="org.activebpel.b4p.war.web.bean.AeRequestContext" scope="request"/>
<jsp:useBean id="aeWorkFlowUserSession" class="org.activebpel.b4p.war.web.bean.AeUserSession" scope="session"/>
<resultset>   
<jsp:useBean id="taskInbox" class="org.activebpel.b4p.war.web.bean.AeTaskInboxBean" />
<%
	taskInbox.setRequestResponse(request, response);
%>
<ae:SetProperty name="taskInbox" property="principal" fromName="aeWorkFlowUserSession" fromProperty="principalName" />
<ae:SetIntProperty name="taskInbox" property="startIndex" param="startIndex" min="0" />
<ae:SetIntProperty name="taskInbox" property="pageSize" param="pageSize" min="1" default="20"/>
<ae:SetProperty name="taskInbox" property="filter" param="filter"/>
<ae:SetProperty name="taskInbox" property="role" param="role"/>
<ae:SetProperty name="taskInbox" property="orderByList" param="orderByList"/>
<ae:SetProperty name="taskInbox" property="searchBy" param="searchBy"/>
<jsp:setProperty name="taskInbox" property="fetchTasks" value="true"/> 
	<statuscode><ae:GetProperty  name="taskInbox" property="statusCode" /></statuscode>	
   <statusmessage><ae:IfTrue name="taskInbox" property="hasMessage"><ae:XMLStringFormatter name="taskInbox" property="message" /></ae:IfTrue></statusmessage>
   <ae:IfFalse name="taskInbox" property="error">
      <filter><ae:GetProperty name="taskInbox" property="currentFilter" /></filter>
      <role><ae:GetProperty name="taskInbox" property="currentRole" /></role>
		<count><ae:GetProperty name="taskInbox" property="listResult.count" /></count>
		<pagesize><ae:GetProperty name="taskInbox" property="listResult.pageSize" /></pagesize>
		<total><ae:GetProperty name="taskInbox" property="listResult.totalCount" /></total>
		<displayfromindex><ae:GetProperty name="taskInbox" property="listResult.displayFromIndex" /></displayfromindex>
		<displaytoindex><ae:GetProperty name="taskInbox" property="listResult.displayToIndex" /></displaytoindex>
		<ae:IfTrue name="taskInbox" property="listResult.hasPrevious" >
			<prevstartindex><ae:GetProperty name="taskInbox" property="listResult.previousStartIndex" />"></prevstartindex>
		</ae:IfTrue>														
		<ae:IfFalse name="taskInbox" property="listResult.hasPrevious" >
			<prevstartindex>-1</prevstartindex>
		</ae:IfFalse>														
		<ae:IfTrue name="taskInbox" property="listResult.hasNext" >
			<nextstartindex><ae:GetProperty name="taskInbox" property="listResult.nextStartIndex" />"></nextstartindex>
		</ae:IfTrue>														
		<ae:IfFalse name="taskInbox" property="listResult.hasNext" >
			<nextstartindex>-1</nextstartindex>
		</ae:IfFalse>	
		<orderbys><ae:GetProperty name="taskInbox" property="orderByList" /></orderbys>			
		<tasks>	
		   <ae:XpathNodeIterator name="taskInbox" property="listResult.getTasksResponseElement" query="//htdat:taskAbstract" id="abxTask" >
	  		  <task>
				<taskid><ae:XPathSelectNodeText name="abxTask" query="htda:id" /></taskid>
				<tasktype><ae:XPathSelectNodeText name="abxTask" query="htda:taskType" /></tasktype>
				<pid><ae:XPathSelectNodeText name="abxTask" query="htda:id" /></pid>
				<priority><ae:XPathSelectNodeText name="abxTask" query="htda:priority" /></priority>
				<escalated><ae:XPathSelectNodeText name="abxTask" query="htda:escalated" /></escalated>
				<state><ae:XPathSelectNodeText name="abxTask" query="htda:status" /></state>
				<taskinitiator><ae:XPathSelectNodeText name="abxTask" query="htda:taskInitiator" /></taskinitiator>
				<creationtime><ae:XpathSelectNodeDate name="abxTask" query="htda:createdOn" patternKey="date_time_pattern" /></creationtime>
				<owner><ae:XPathSelectNodeText name="abxTask" query="htda:actualOwner" /></owner>
				<presentationname><ae:XPathSelectNodeText name="abxTask" query="htda:presentationName" /></presentationname>
				<summary><ae:XPathSelectNodeText name="abxTask" query="htda:presentationSubject" /></summary>
				<isskipable><ae:XPathSelectNodeText name="abxTask" query="htda:isSkipable" /></isskipable>
				<modifiedtime><ae:XpathSelectNodeDate name="abxTask" query="htda:modifiedTime" patternKey="date_time_pattern" /></modifiedtime>
				<expirationtime><ae:XpathSelectNodeDate name="abxTask" query="htda:expirationTime" patternKey="date_time_pattern" /></expirationtime>
				<lastescalationtime><ae:XpathSelectNodeDate name="abxTask" query="htda:escalationTime" patternKey="date_time_pattern" /></lastescalationtime>
			 </task>
	  		</ae:XpathNodeIterator>						  
		</tasks>									
	</ae:IfFalse>
</resultset>
