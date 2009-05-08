<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://activebpel.org/aetaglib" prefix="ae" %>
<ae:RequestEncoding value="UTF-8" />
<html>
	<head>
		<title>
			<ae:GetResource name="page_notify_unsecured" />
		</title>
		<link rel="stylesheet" href="../css/aeworkflow.css" />
		<link rel="shortcut icon" href="../images/favicon.ico" type="image/x-icon" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
		<script src="script/jquery.js"></script>
	</head>
	<body>
		<div id="body">
			<div id="header">
				<div id="logout"></div>
				<img src="../images/logo.gif" style="border:0px"/>
			</div><!-- header -->	
			<div id="content">
				<p>
					Access to this application needs to be protected.
					Once the application is secured, you will need to sign in using
					your username and password.
				</p>																
			</div><!-- content -->
			<%@include file="../footer_incl.jspf" %>
		</div>
	</body>
</html>