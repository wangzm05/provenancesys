<%@ include file="init_page_incl.jspf" %>
<jsp:setProperty name="aeRequestContext" property="pageName" value="page_inbox" />
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<%@ include file="htmlhead_internal_inbox_incl.jspf" %>
   <script type="text/javascript">
   $(document).ready(function(){
      aeShowProgressMessage("");
      $("#aestatusmessagecontainer").addClass("erroricon");
      $("#aestatusmessagecontainer").show();
   });
   </script>

</head>
<body>
   <div id="body">
      <%@ include file="header_incl.jspf" %>
      <div id="content">
          <div id="aestatusmessagecontainer">
            <p>
               You do not have permission to access this task.
            </p
            <p>
               Click <a href="..">here to go back</a>.
            </p>
          </div>
      </div><!-- content -->
      <%@ include file="../footer_incl.jspf" %>
   </div><!-- body -->
</body>
</html>