<%@include file="init_page_incl.jspf" %>
<jsp:useBean id="loginBean" class="org.activebpel.b4p.war.web.bean.AeTaskLoginBean" />
<%
	loginBean.setRequestResponse(request, response);
%>
<ae:IfParamMatches property="authenticate" value="true">
   <ae:SetStringProperty name="loginBean" property="username" param="username" />
   <ae:SetStringProperty name="loginBean" property="password" param="password" />
   <ae:SetStringProperty name="loginBean" property="authenticate" param="authenticate" />
</ae:IfParamMatches>
<ae:IfTrue name="loginBean" property="authorized">
	<% response.sendRedirect("inbox/inbox.jsp"); %>	
</ae:IfTrue>
<ae:IfTrue name="loginBean" property="hasMessage">
	<ae:SetResource key="login_error" name="aeRequestContext" property="message" />
</ae:IfTrue>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
	<title>
		<ae:GetResource name="page_signin"/>
	</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/aeworkflow.css" />
	<link rel="shortcut icon" href="<%= request.getContextPath() %>/images/favicon.ico" type="image/x-icon" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
</head>
<body>
	<div id="body">
		<div id="header">
			<img src="<%= request.getContextPath() %>/images/logo.gif" style="border:0px" />
			<h1> </h1>
		</div><!-- header -->		
		<div id="content">
			<%@include file="messages_incl.jspf" %>
			<div class="center">
				<form id="loginform" method="post" action="login.jsp">
					<input type="hidden" name="authenticate" value="true" />
					<table>
						<tr>
							<th colspan="2">
								<ae:GetResource name="page_signin" />
							</th>
						</tr>
						<tr>
							<td class="relabel"><ae:GetResource name="username" /></td><td><input class="login" size="20" type="text" name="username" value="" /></td>
						</tr>
						<tr>
							<td class="label"><ae:GetResource name="password" /></td><td><input class="login" size="20" type="password" name="password" value=""/></td>
						</tr>
						<tr >
							<td colspan="2" class="label"><input type="submit" class="formbutton" value=" <ae:GetResource name="sign_in_button" /> "/></td>
						</tr>
					</table>
				</form>
			</div>
		</div><!-- content -->
		<%@include file="footer_incl.jspf" %>
	</div>
	<!-- body -->
</body>
</html>