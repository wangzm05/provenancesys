<%@ include file="init_page_incl.jspf" %>
<jsp:setProperty name="aeRequestContext" property="pageName" value="page_inbox" />
<jsp:useBean id="taskInbox" class="org.activebpel.b4p.war.web.bean.AeTaskInboxBean" />
<ae:SetProperty name="taskInbox" property="principal" fromName="aeWorkFlowUserSession" fromProperty="principalName" />
<%
   taskInbox.setRequestResponse(request, response);
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<%@ include file="htmlhead_internal_inbox_incl.jspf" %>
   <script type="text/javascript">
   gAeTaskEditorUrl = "task.jsp";
   gAePrincipal = "<jsp:getProperty name="aeWorkFlowUserSession" property="principalName" />";   
   var filter = "<jsp:getProperty name="taskInbox" property="cookieFilter" />";
   var role = "<jsp:getProperty name="taskInbox" property="cookieRole" />";
   var orderby = "<jsp:getProperty name="taskInbox" property="cookieOrderByList" />";
   var searchby = "<jsp:getProperty name="taskInbox" property="cookieSearchBy" />";
   
   $(document).ready(function(){      
      aeInitInbox(filter, role, orderby, searchby);      
   });

   </script>
</head>
<body>
   <div id="body">
      <%@ include file="header_incl.jspf" %>
      <div id="content">
         <table cellpadding="4" cellspacing="0" style="width:98%;border:0px">
            <tr>
               <td class="filterheader" width="200px" style="border:1px solid #666; padding:2px;">
                  <strong><ae:GetResource name="inbox_filters" /></strong>
               </td>
               <td class="filterheader" style="border-top:1px solid #666;">
                  <strong><span id="aefilter_label">&nbsp;</span></strong>&nbsp;
               </td>
               <td style="text-align:right;border-top:1px solid #666; border-right:1px solid #666; padding:2px;">
                  <div class="aenavcontrolbar" style="display:inline;">
                     <div class="aenavrefresh" style="display:inline;margin:0px 2em 0px 2em;">
                        <a href="" id="aenav_refresh" title="<ae:GetResource name="inbox_listing_refresh_page_desc" />"><ae:GetResource name="inbox_listing_refresh_page" /></a>
                     </div>
                     <div class="aenavcontrols" style="display:inline;">
                        <a href="" id="aenav_firstpage" title="<ae:GetResource name="inbox_listing_first_page_desc" />">&nbsp;</a>
                        <a href="" id="aenav_prevpage" title="<ae:GetResource name="inbox_listing_previous_page_desc" />">&nbsp;</a>
                        <span class="aenav_fromindex"></span> &nbsp; - &nbsp;<span class="aenav_toindex"></span>&nbsp; of &nbsp;<span class="aenav_total"></span>
                        <a href="" id="aenav_nextpage" title="<ae:GetResource name="inbox_listing_next_page_desc" />">&nbsp;</a>
                        <a href="" id="aenav_lastpage" title="<ae:GetResource name="inbox_listing_last_page_desc" />">&nbsp;</a>
                     </div>
                  </div>
               </td>
            </tr>
            <tr>
               <td align="left" valign="top" style="border:1px solid #666;">
						<ul id="inboxfilteroutline" class="filetree">
													
	                  <li>
	                  	<a class="file inboxfilter aetaskstateglyph aetaskstateglyph_NOTIFICATION" role="notificationrecipients" href="" filter="notifications" title="<ae:GetResource name="inbox_filter_notifications" />"><ae:GetResource name="inbox_filter_notifications" /></a>
	                  </li>
	                 
							<li class="inboxfiltermeta" role="owner" title="">
								<span class="folder"><ae:GetResource name="inbox_filter_tasks" /></span>
								<ul>
									
									<li>
										<span class="folder inboxfilter" filter="open" role="default" title="<ae:GetResource name="inbox_filter_open" />"><ae:GetResource name="inbox_filter_open" /></span>
										<ul>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_READY" href="" filter="unclaimed" role="potentialowner" title="<ae:GetResource name="inbox_filter_unclaimed" />"><ae:GetResource name="inbox_filter_unclaimed" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_RESERVED" href="" filter="reserved" title="<ae:GetResource name="inbox_filter_reserved" />"><ae:GetResource name="inbox_filter_reserved" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_IN_PROGRESS" href="" filter="started" title="<ae:GetResource name="inbox_filter_started" />"><ae:GetResource name="inbox_filter_started" /></a></li>                         	 	
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_SUSPENDED" href="" filter="suspended" title="<ae:GetResource name="inbox_filter_suspended" />"><ae:GetResource name="inbox_filter_suspended" /></a></li>
										</ul>
									</li>
									
									<li>
										<span class="folder inboxfilter" filter="closed" title="<ae:GetResource name="inbox_filter_closed" />"><ae:GetResource name="inbox_filter_closed" /></span>
										<ul>
										   <li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_COMPLETED" href="" filter="completed" title="<ae:GetResource name="inbox_filter_completed" />"><ae:GetResource name="inbox_filter_completed" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_FAILED" href="" filter="failed" title="<ae:GetResource name="inbox_filter_failed" />"><ae:GetResource name="inbox_filter_failed" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_EXITED" href="" filter="exited" title="<ae:GetResource name="inbox_filter_exited" />"><ae:GetResource name="inbox_filter_exited" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_OBSOLETE" href="" filter="obsolete" title="<ae:GetResource name="inbox_filter_obsolete" />"><ae:GetResource name="inbox_filter_obsolete" /></a></li>                         	 	
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_ERROR" href="" filter="error" title="<ae:GetResource name="inbox_filter_error" />"><ae:GetResource name="inbox_filter_error" /></a></li>
										</ul>
									</li>									
								</ul>
							</li>
							
							<li class="closed inboxfiltermeta" role="admin" title="<ae:GetResource name="inbox_filter_admin" />">
								<span class="folder"><ae:GetResource name="inbox_filter_admin" /></span>
								<ul>
																
			                  <li>
			                  	<a class="file inboxfilter aetaskstateglyph aetaskstateglyph_NOTIFICATION" href="" filter="notifications" title="<ae:GetResource name="inbox_filter_notifications" />"><ae:GetResource name="inbox_filter_notifications" /></a>
			                  </li>
									
									<li>
										<span class="folder inboxfilter" filter="open" title="<ae:GetResource name="inbox_filter_open" />" ><ae:GetResource name="inbox_filter_open" /></span>
										<ul>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_READY" href="" filter="unclaimed" title="<ae:GetResource name="inbox_filter_unclaimed" />"><ae:GetResource name="inbox_filter_unclaimed" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_RESERVED" href="" filter="reserved" title="<ae:GetResource name="inbox_filter_reserved" />"><ae:GetResource name="inbox_filter_reserved" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_IN_PROGRESS" href="" filter="started" title="<ae:GetResource name="inbox_filter_started" />"><ae:GetResource name="inbox_filter_started" /></a></li>                         	 	
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_SUSPENDED" href="" filter="suspended" title="<ae:GetResource name="inbox_filter_suspended" />"><ae:GetResource name="inbox_filter_suspended" /></a></li>
										</ul>
									</li>
									
									<li class="closed">
										<span class="folder inboxfilter" filter="closed" title="<ae:GetResource name="inbox_filter_closed" />"><ae:GetResource name="inbox_filter_closed" /></span>
										<ul>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_COMPLETED" href="" filter="completed" title="<ae:GetResource name="inbox_filter_completed" />"><ae:GetResource name="inbox_filter_completed" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_FAILED" href="" filter="failed" title="<ae:GetResource name="inbox_filter_failed" />"><ae:GetResource name="inbox_filter_failed" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_EXITED" href="" filter="exited" title="<ae:GetResource name="inbox_filter_exited" />"><ae:GetResource name="inbox_filter_exited" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_OBSOLETE" href="" filter="obsolete" title="<ae:GetResource name="inbox_filter_obsolete" />"><ae:GetResource name="inbox_filter_obsolete" /></a></li>                         	 	
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_ERROR" href="" filter="error" title="<ae:GetResource name="inbox_filter_error" />"><ae:GetResource name="inbox_filter_error" /></a></li>
										</ul>
									</li>									
								</ul>
							</li>
							
							
							<li class="closed inboxfiltermeta" role="stakeholder" title="<ae:GetResource name="inbox_filter_task_stakeholder" />">
								<span class="folder"><ae:GetResource name="inbox_filter_task_stakeholder" /></span>
								<ul>
															
			                  <li>
			                  	<a class="file inboxfilter aetaskstateglyph aetaskstateglyph_NOTIFICATION" href="" filter="notifications" title="<ae:GetResource name="inbox_filter_notifications" />"><ae:GetResource name="inbox_filter_notifications" /></a>
			                  </li>								
									
									<li>
										<span class="folder inboxfilter" filter="open" title="<ae:GetResource name="inbox_filter_open" />"><ae:GetResource name="inbox_filter_open" /></span>
										<ul>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_READY" href="" filter="unclaimed" title="<ae:GetResource name="inbox_filter_unclaimed" />"><ae:GetResource name="inbox_filter_unclaimed" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_RESERVED" href="" filter="reserved" title="<ae:GetResource name="inbox_filter_reserved" />"><ae:GetResource name="inbox_filter_reserved" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_IN_PROGRESS" href="" filter="started" title="<ae:GetResource name="inbox_filter_started" />"><ae:GetResource name="inbox_filter_started" /></a></li>                         	 	
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_SUSPENDED" href="" filter="suspended" title="<ae:GetResource name="inbox_filter_suspended" />"><ae:GetResource name="inbox_filter_suspended" /></a></li>
										</ul>
									</li>
									
									<li class="closed">
										<span class="folder inboxfilter" filter="closed" title="<ae:GetResource name="inbox_filter_closed" />"><ae:GetResource name="inbox_filter_closed" /></span>
										<ul>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_COMPLETED" href="" filter="completed" title="<ae:GetResource name="inbox_filter_completed" />"><ae:GetResource name="inbox_filter_completed" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_FAILED" href="" filter="failed" title="<ae:GetResource name="inbox_filter_failed" />"><ae:GetResource name="inbox_filter_failed" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_EXITED" href="" filter="exited" title="<ae:GetResource name="inbox_filter_exited" />"><ae:GetResource name="inbox_filter_exited" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_OBSOLETE" href="" filter="obsolete" title="<ae:GetResource name="inbox_filter_obsolete" />"><ae:GetResource name="inbox_filter_obsolete" /></a></li>                         	 	
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_ERROR" href="" filter="error" title="<ae:GetResource name="inbox_filter_error" />"><ae:GetResource name="inbox_filter_error" /></a></li>
										</ul>
									</li>									
								</ul>
							</li>
							
							<li class="closed inboxfiltermeta" role="initiator" title="<ae:GetResource name="inbox_filter_task_initiator" />">
								<span class="folder"><ae:GetResource name="inbox_filter_task_initiator" /></span>
								<ul>
														
			                  <li>
			                  	<a class="file inboxfilter aetaskstateglyph aetaskstateglyph_NOTIFICATION" href="" filter="notifications" title="<ae:GetResource name="inbox_filter_notifications" />"><ae:GetResource name="inbox_filter_notifications" /></a>
			                  </li>								
									
									<li>
										<span class="folder inboxfilter" filter="open" title="<ae:GetResource name="inbox_filter_open" />"><ae:GetResource name="inbox_filter_open" /></span>
										<ul>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_READY" href="" filter="unclaimed" title="<ae:GetResource name="inbox_filter_unclaimed" />"><ae:GetResource name="inbox_filter_unclaimed" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_RESERVED" href="" filter="reserved" title="<ae:GetResource name="inbox_filter_reserved" />"><ae:GetResource name="inbox_filter_reserved" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_IN_PROGRESS" href="" filter="started" title="<ae:GetResource name="inbox_filter_started" />"><ae:GetResource name="inbox_filter_started" /></a></li>                         	 	
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_SUSPENDED" href="" filter="suspended" title="<ae:GetResource name="inbox_filter_suspended" />"><ae:GetResource name="inbox_filter_suspended" /></a></li>
										</ul>
									</li>
									
									<li class="closed">
										<span class="folder inboxfilter" filter="closed" title="<ae:GetResource name="inbox_filter_closed" />"><ae:GetResource name="inbox_filter_closed" /></span>
										<ul>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_COMPLETED" href="" filter="completed" title="<ae:GetResource name="inbox_filter_completed" />"><ae:GetResource name="inbox_filter_completed" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_FAILED" href="" filter="failed" title="<ae:GetResource name="inbox_filter_failed" />"><ae:GetResource name="inbox_filter_failed" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_EXITED" href="" filter="exited" title="<ae:GetResource name="inbox_filter_exited" />"><ae:GetResource name="inbox_filter_exited" /></a></li>
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_OBSOLETE" href="" filter="obsolete" title="<ae:GetResource name="inbox_filter_obsolete" />"><ae:GetResource name="inbox_filter_obsolete" /></a></li>                         	 	
											<li><a class="file inboxfilter aetaskstateglyph aetaskstateglyph_ERROR" href="" filter="error" title="<ae:GetResource name="inbox_filter_error" />"><ae:GetResource name="inbox_filter_error" /></a></li>
										</ul>
									</li>									
								</ul>
							</li>														
														
						</ul>
					</td>
               <td colspan="2" align="left" valign="top" style="border:1px solid #666;">
                  <div class="resultset">
                     <div class="toolbar" style="border:1px solid #666; margin-top:5px; margin-bottom:5px;">
                        <table class="toolbarbuttons">
                           <tr>
                              <td><a href="" id="aetask_cmd_edit" title="<ae:GetResource name="task_cmd_edit_desc" />"><ae:GetResource name="task_cmd_edit" /></a></td>
                              <td><a href="" id="aetask_cmd_claim" title="<ae:GetResource name="task_cmd_claim_desc" />"><ae:GetResource name="task_cmd_claim" /></a></td>
                              <td><a href="" id="aetask_cmd_start" title="<ae:GetResource name="task_cmd_start_desc" />"><ae:GetResource name="task_cmd_start" /></a></td>
                              <td>&nbsp;</td>
                              <td><a href="" id="aetask_cmd_remove" title="<ae:GetResource name="task_cmd_remove_desc" />"><ae:GetResource name="task_cmd_remove" /></a></td>
                              <td>&nbsp;</td>
                              <td width="80%" align="right">
                                 <form id="ae_searchby_form">
                                    <span id="ae_searchby_label"><ae:GetResource name="inbox_searchby" /></span>:
                                    <input id="searchby" type="text" name="searchby" size="20" value =""/>
                                 </form>
                              </td>
                              <td ><a href="" id="ae_run_searchby" title="<ae:GetResource name="inbox_searchby" />"></a></td>
                              <td ><a href="" id="ae_clear_searchby" title="<ae:GetResource name="inbox_clear_searchby" />">&nbsp;</a></td>
                           </tr>
                        </table>
                     </div><!-- end toolbar -->
                     <div class="inboxtasklistingnotasks">
                        <p>
                           <ae:GetResource name="notasksfound" />
                        </p>
                     </div>                     
                     <table cellpadding="2" id="aeinboxtasklisting" class="ruledlisting inboxtasklisting">
                        <thead>
                           <tr>
	                           <th id="ae_fid_State"><a href=""  class="ae_inbox_col" title="<ae:GetResource name="inbox_col_state" />"> &nbsp; </a> <span class="ae_inbox_col_sortval"></span></th>
                              <th id="ae_fid_Priority"><a href=""  class="ae_inbox_col" ><span class="aetaskpriorityglyph"><ae:GetResource name="inbox_col_priority" /></span></a> <span class="ae_inbox_col_sortval"></span></th>                              
                              <th id="ae_fid_PresentationName" ><a href="" class="ae_inbox_col"><ae:GetResource name="inbox_col_name" /></a> <span class="ae_inbox_col_sortval"></span></th>
                              <th id="ae_fid_Summary"><a href="" class="ae_inbox_col"><ae:GetResource name="inbox_col_subject" /></a> <span class="ae_inbox_col_sortval"></span></th>
                              <th id="ae_fid_Owner"><a href=""  class="ae_inbox_col"><ae:GetResource name="inbox_col_owner" /></a> <span class="ae_inbox_col_sortval"></span></th>
                              <th id="ae_fid_Created"><a href=""  class="ae_inbox_col"><ae:GetResource name="inbox_col_created" /></a> <span class="ae_inbox_col_sortval"></span></th>
                              <th id="ae_fid_Expiration"><a href=""  class="ae_inbox_col"><ae:GetResource name="inbox_col_expiration" /></a> <span class="ae_inbox_col_sortval"></span></th>
                           </tr>
                        </thead>
                        <tfoot></tfoot>
                        <tbody></tbody>
                     </table>
                  </div><!-- end resultset -->
               </td>
            </tr>
         </table>
      </div> <!--  content -->
      <%@ include file="../footer_incl.jspf" %>
   </div><!-- body -->
</body>
</html>