<%@page contentType="text/xml; charset=UTF-8" %>
<%@ taglib uri="http://activebpel.org/aetaglib" prefix="ae" %>
<ae:RequestEncoding value="UTF-8" />
<jsp:useBean id="aeRequestContext" class="org.activebpel.b4p.war.web.bean.AeRequestContext" scope="request"/>
<jsp:useBean id="aeWorkFlowUserSession" class="org.activebpel.b4p.war.web.bean.AeUserSession" scope="session"/>
<jsp:useBean id="taskCmd" class="org.activebpel.b4p.war.web.bean.AeTaskCommandBean" />
<%
	taskCmd.setRequestResponse(request, response);
%>
<ae:SetProperty name="taskCmd" property="principal" fromName="aeWorkFlowUserSession" fromProperty="principalName" />
<ae:SetStringProperty name="taskCmd" property="id" param="taskId"  />
<ae:SetStringProperty name="taskCmd" property="newOwner" param="newOwner"  />
<ae:SetIntProperty name="taskCmd" property="newPriority" param="newPriority"  />
<ae:SetIntProperty name="taskCmd" property="newFaultName" param="faultName"  />
<ae:SetStringProperty name="taskCmd" property="command" param="action"  />
<resultset>   
	<statuscode><ae:GetProperty  name="taskCmd" property="statusCode" /></statuscode>
   <statusmessage>
<ae:IfTrue name="taskCmd" property="hasMessage"><ae:XMLStringFormatter name="taskCmd" property="message" /></ae:IfTrue>
   </statusmessage>
</resultset>
