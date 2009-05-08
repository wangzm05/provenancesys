<%@ include file="init_page_incl.jspf" %>
<ae:SetResource key="login_denied" name="aeRequestContext" property="message" />
<%
	session.invalidate();
%>
<jsp:forward page="login.jsp" />
