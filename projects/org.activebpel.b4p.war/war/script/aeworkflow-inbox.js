/**
* PROPRIETARY RIGHTS STATEMENT
* The contents of this file represent confidential information that is the
* proprietary property of Active Endpoints, Inc.  Viewing or use of
* this information is prohibited without the express written consent of
* Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
* is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
*
* aeworkflow-inbox.js:
* Defines workflow application inbox script.
*
* Dependencies:
* aetaskdefs.js
* aeworkflow-common.js
* aeworkflow-util.js
* jquery.js, jquery.block.js (http://www.jquery.com, jQuery 1.1.2)
* jqModal.js (http://dev.iceburg.net/jquery/jqModal/)
*/

var gAeInboxStartIndex = 0;
var gAeTaskListingResult = null;
var gAeListInboxTasksAjaxUrl = "_listtasks.jsp";
var gAeTaskEditorXslUrl = "task";
var gAeCurrFilter = "";
var gAeCurrRole = "";
var gAeCurrOrderBy = "";
var gAeCurrSearchBy = "";
/* List of task property names to be displayed in the inbox table. */
var gAeInboxColumnPropertyNames = ["state", "priority", "presentationname", "summary", "owner", "creationtime", "expirationtime"];

/* List of well known attribute (metadata) names for a task details. */
var gAeTaskDetailPropertyNames = ["taskid","tasktype", "priority", "escalated", "pid", "state", "creationtime", "modifiedtime", "expirationtime", "lastescalationtime", "owner", "presentationname", "summary", "createdby", "modifiedby", "input", "output", "isskipable", "hasfault", "permissions", "taskinitiator"];
var gPropertyNameToColumnIdMap = {};
gPropertyNameToColumnIdMap["priority"] = "ae_fid_Priority";
gPropertyNameToColumnIdMap["state"] = "ae_fid_State";
gPropertyNameToColumnIdMap["presentationname"] = "ae_fid_PresentationName";
gPropertyNameToColumnIdMap["summary"] = "ae_fid_Summary";
gPropertyNameToColumnIdMap["owner"] = "ae_fid_Owner";
gPropertyNameToColumnIdMap["creationtime"] = "ae_fid_Created";
gPropertyNameToColumnIdMap["expirationtime"] = "ae_fid_Expiration";

var gViewAllTasksCols = ["state", "priority", "presentationname", "summary", "owner", "creationtime", "expirationtime"];
var gViewUnclaimedTasksCols = ["state", "priority", "presentationname", "summary", "creationtime", "expirationtime"];
var gViewMyTasksCols = ["state", "priority", "presentationname", "summary", "creationtime", "expirationtime"];
var gViewMyCompletedTasksCols = ["state", "priority",  "presentationname", "summary"];
var gViewNotificationsCols = ["state", "priority", "presentationname", "summary", "creationtime"];

var gFilterToColsMap = {};
gFilterToColsMap["allnotifications"] = gViewNotificationsCols;
gFilterToColsMap["unclaimed"] = gViewUnclaimedTasksCols;
gFilterToColsMap["reserved"] = gViewMyTasksCols;
gFilterToColsMap["started"] = gViewMyTasksCols;
gFilterToColsMap["allclosed"] = gViewMyCompletedTasksCols;

var gAdminFilterToColsMap = {};
gAdminFilterToColsMap["allnotifications"] = gViewNotificationsCols;
gAdminFilterToColsMap["unclaimed"] = gViewUnclaimedTasksCols;


//
// Build filter title such as "Administrative - All Open"
// given current filter element with class = inboxfilter
//
function aeBuildFilterTitleFromElem(aFilterElem)
{
   if ( aeIsNullOrUndefined(aFilterElem) )
   {
      return "";
   }
   // find title from filter click elem. E.g. "Open" or "Unclaimed"
   var title = aFilterElem.attr("title");
   // find parent folder title. E.g. "My Tasks" or "Administrative Tasks".
   var parentElems = aFilterElem.parents("li.inboxfiltermeta");
   if (aeIsNotNullOrUndefined(parentElems) && parentElems.length == 1)
   {
      var parentTitle = $(parentElems[0]).attr("title");
      if ( aeIsNotNullOrEmpty(parentTitle) )
      {
        title = parentTitle + " - " + title;
      }
   }
   return title;
}
// end aeBuildFilterTitleFromElem

//
// Build filter title such as "Administrative - All Open"
// given filter and role string values,
//
function aeBuildFilterTitle(aFilter, aRole)
{
   if ( aeIsNullOrEmpty(aFilter) )
   {
      return "";
   }
   // find filter elment
   var selectedFilterElem = null;
   // get all matching elements
   var filterElems = $(".inboxfilter[filter*=" + filter + "]");
   if (aeIsNotNullOrUndefined(filterElems))
   {
      //loop thru each elem to find the elem which has the given role as an attribute or in a  parent element attribute.
      $.each(filterElems, function (i, elem) {
         if (selectedFilterElem == null && aeIsNotNullOrEmpty($(elem).attr("role")) && $(elem).attr("role") == aRole)
         {
            // found match on the filter element itself.
            selectedFilterElem = $(elem);
         }
         // also look in parent element (with given role)
         var roleEle = $(elem).parents("[role*=" + role + "]");
         //alert("role=" + roleEle.length);
         if (selectedFilterElem == null && roleEle.length == 1)
         {
             selectedFilterElem = $(elem);
         }
      });
   }
   return aeBuildFilterTitleFromElem(selectedFilterElem);
}

//
//
//
function aeGetAllColumnIds()
{
   var rval = [];
   for (var n in gPropertyNameToColumnIdMap)
   {
      rval.push( gPropertyNameToColumnIdMap[n] );
   }
   return rval;
}
// end aeGetAllColumnIds

//
//
function aeGetColumnPropertyNamesForFilter(aFilterName)
{
  var cols = null;
  
  if (aeIsNotNullOrUndefined(aFilterName)
  		&& (gAeCurrRole == "admin" || gAeCurrRole == "initiator" || gAeCurrRole == "stakeholder") )
  {  		
		cols = gAdminFilterToColsMap[aFilterName];  	
  }
  else if ( aeIsNotNullOrUndefined(aFilterName) )
  {
      cols = gFilterToColsMap[aFilterName];
  }
  if ( aeIsNullOrUndefined(cols) )
  {
      // default - show all colums
      cols = gViewAllTasksCols;
  }
  return cols;
}
// end aeGetColumnPropertyNamesForFilter

//
//
function aeGetColumnIdsForFiler(aFilterName)
{
   cols = aeGetColumnPropertyNamesForFilter(aFilterName);
   colIds = [];
   for (i = 0; i < cols.length; i++)
   {
      colIds.push( gPropertyNameToColumnIdMap[cols[i]] );
   }
   return colIds;
}
// end aeGetColumnIdsForFiler

/*
* Initializes the inbox.
*/
function aeInitInbox(aFilter, aRole, aOrderby, aSearchby)
{
   if ( aeIsNotNullOrUndefined(aFilter) )
   {
      gAeCurrFilter = aFilter;
   }
   if ( aeIsNotNullOrUndefined(aRole) )
   {
      gAeCurrRole = aRole;
   }
   if ( aeIsNotNullOrUndefined(aOrderby) )
   {
      gAeOrderBy = aOrderby;
   }
   if ( aeIsNotNullOrUndefined(aSearchby) )
   {
      gAeCurrSearchBy = aSearchby;
      aeSetSearchByField(aSearchby);
   }
   aeInitStatusMessage();
   // init inbox table
   aeInitInboxListTable();
   // create image buttons for task commands
   aeInitInboxTaskActionButtons();
   /*
   $("#inboxresultsetcontainer").ajaxStart( function(){
     aeEnableInbox(false);
   });
   $("#inboxresultsetcontainer").ajaxStop(function() {
      aeEnableInbox(true);
   });
   */
   // hide 'you got mail' icon message
   $("#aefilter_notification_glyph").hide();
   // add event handler for column order by selections
   $(".ae_inbox_col").click( function() {
      var parentTH = $(this).parent("th");
      var id = parentTH.attr("id");
      if (id != "")
      {
         // expect ID field to have form ae_fid_FIELDID. e.g. "ae_fid_Priority".
         var fieldID = id.substring(7);
         gAeCurrOrderBy = gAeTaskListingResult.createOrderByForFieldID(fieldID);
         aeOnRefreshInboxAction();
      }
      return false;
   });

   // search by submit
   $("#ae_searchby_form").submit(function() {
      aeOnSearchByAction("ignore");
      return false;
   });

   // tree control
   aeInitInboxTreeView();

   // load first page of data
   aeLoadInboxTasks(0);
}
// end aeInitInbox()

//
//
//
function aeInitInboxTreeView()
{
   // init jquery tree control
   $("#inboxfilteroutline").treeview();

   // bind click events
   $(".inboxfilter").click(function() {
      var title = aeBuildFilterTitleFromElem( $(this) );
      var filter = $(this).attr("filter");
      var role = $(this).attr("role");
      var meta = $(this).parents("li.inboxfiltermeta");
      if (meta && meta != null && meta.length == 1)
      {
          var metaRole = meta[0].getAttribute("role");
          if (  aeIsNullOrEmpty(role) && aeIsNotNullOrEmpty(metaRole) )
          {
            role = metaRole;
          }
      }
      if (gAeCurrRole != role || gAeCurrFilter != filter)
      {
         gAeCurrRole = role;
         gAeCurrFilter = filter;
         aeDisplayFilterTitle(filter, title);
         aeOnRefreshInboxAction();
      }
      return false;
   });
   // end bind

   // display current query/filter label
   var title = aeBuildFilterTitle(gAeCurrFilter, gAeCurrRole);
   aeDisplayFilterTitle(gAeCurrFilter, title);
}
// end aeInitInboxTreeView()

function aeDisplayFilterTitle(aFilter, aTitle)
{
 $("#aefilter_label").removeClass();
 $("#aefilter_label").addClass("aetaskfilterglyph aetaskfilterglyph_" + aFilter);
 $("#aefilter_label").text(aTitle);
}
// end aeDisplayFilterTitle()

function aeInitInboxTaskActionButtons()
{
   aeMakeImageLink("aenav_refresh", "../images/arrow_refresh.png", "left", aeOnRefreshInboxAction, "");
   // result set paging
   aeMakeImageLink("aenav_firstpage", "../images/resultset_first.png", "left", aeOnPagingAction, "../images/resultset_first_dis.png");
   aeMakeImageLink("aenav_lastpage", "../images/resultset_last.png", "right", aeOnPagingAction, "../images/resultset_last_dis.png");
   aeMakeImageLink("aenav_prevpage", "../images/resultset_previous.png", "left", aeOnPagingAction, "../images/resultset_previous_dis.png");
   aeMakeImageLink("aenav_nextpage", "../images/resultset_next.png", "right", aeOnPagingAction, "../images/resultset_next_dis.png");

   // task actions
   aeInitTaskActionButtons(aeOnInboxTaskCommandAction);
   aeEnableTaskCommandButtons(false);
   aeEnableTaskCommandButton("edit", false);

   // search by
   aeMakeImageButton("ae_run_searchby", "../images/Search20.png",  aeOnSearchByAction);
   aeMakeImageButton("ae_clear_searchby", "../images/EraseSearch20.png",  aeOnClearSearchByAction);
}
// end aeInitInboxTaskActionButtons()

function aeOnClearSearchByAction(actionId)
{
   aeSetSearchByField("");
   aeOnSearchByAction("ignore");
   return true;
}

function aeSetSearchByField(aValue)
{
   var searchByInput = $("#searchby");
   searchByInput.val(aValue);
}

function aeOnSearchByAction(actionId)
{
    var newValue = $.trim( $("#searchby").val() );
    var oldValue = $.trim(gAeCurrSearchBy);
    if (newValue != oldValue)
    {
      gAeCurrSearchBy = newValue;
      aeOnRefreshInboxAction("ignore");
   }
   return true;
}
// end aeOnSearchByAction()

function aeOnRefreshInboxAction(actionId)
{
   aeLoadInboxTasks(gAeInboxStartIndex);
   return true;
}
// end aeOnRefreshInboxAction()

function aeOnPagingAction(actionId)
{
   var index = -1;
   if (gAeTaskListingResult != null)
   {
      if (actionId == "aenav_prevpage")
      {
         index = gAeTaskListingResult.getPreviousIndex()
      }
      else if (actionId == "aenav_nextpage")
      {
         index = gAeTaskListingResult.getNextIndex()
      }
      else if (actionId == "aenav_firstpage")
      {
         index = gAeTaskListingResult.getFirstIndex()
      }
      else if (actionId == "aenav_lastpage")
      {
         index = gAeTaskListingResult.getLastIndex();
      }
   }
   if (index != -1)
   {
      aeLoadInboxTasks(index);
   }
}
// end aeOnPagingAction()

function aeOnInboxTaskCommandAction(actionId)
{
   aeHandleTaskCommand(actionId, aeHandleInboxCommandPreprocess, aeHandleInboxCommandResult);
}
// aeOnInboxTaskCommandAction()

function aeGetInboxTaskById(aTaskId)
{
   var task = null;
   if (aTaskId != null && gAeTaskListingResult != null)
   {
      task = gAeTaskListingResult.getTaskList().getTaskById(aTaskId);
   }
   return task;
}
// end aeGetInboxTaskById

function aeRemoveInboxTaskById(aTaskId)
{
   var removedTask = null;
   if (aTaskId != null && gAeTaskListingResult != null)
   {
      removedTask = gAeTaskListingResult.getTaskList().removeTaskById(aTaskId);
   }
   return removedTask;
}
// end aeRemoveInboxTaskById


//
// Called prior to task command is issued from the inbox listing page.
//
function aeHandleInboxCommandPreprocess(aTaskId, aCommand)
{
   var task = aeGetInboxTaskById(aTaskId);
   if (task == null)
   {
      // task should never be null, but test again.
      return false;
   }
   var update = false;
   if ("claim" == aCommand)
   {
      task.claim(gAePrincipal);
      update = true;
   }
   else if ("start" == aCommand)
   {
      task.start(gAePrincipal);
      update = true;
   }
   else if ("remove" == aCommand)
   {
      $("tr[@aetaskid=" + aTaskId + "]").remove();
      aeRemoveInboxTaskById(aTaskId);
   }

   if (update)
   {
      aeUpdateInboxListingView();
      aeOnInboxTaskClick(aTaskId);
   }
   return true;
}
// end aeHandleInboxCommandPreprocess

function aeHandleInboxCommandResult(aCode, aMessage, aTaskId, aCommand)
{
   aeShowProgressMessage("");
   if (aCode == 0)
   {
      if (aCommand != null && aCommand == "complete")
      {
         aeClearInboxSelection();
      }
      else if (aCommand != null && aCommand == "claim")
      {
         // defect 2796 work around. Simulate click event to select current task and update toolbar status.
         aeOnInboxTaskClick(aTaskId);
      }

      // reload iff command != claim/start since view for these commands would have been updated before command was issued in preprocess method.
      if (aCommand != null && aCommand != "claim" && aCommand != "start")
      {
         aeLoadInboxTasks(gAeInboxStartIndex);
      }
   }
   else
   {
      if (aCommand != null && (aCommand == "claim" || aCommand == "start"))
      {
         // if command was claim/start, then restore UI on error.
         var task = aeGetInboxTaskById(aTaskId);
         if (task != null)
         {
            task.restoreState();
            aeUpdateInboxListingView();
            aeOnInboxTaskClick(aTaskId);
         }
      }
      if (aCode == 1)
      {
         aeShowFailureMessage(aMessage);
      }
      else
      {
         aeShowErrorMessage("Command " + aCommand + " failed: " + aMessage);
      }
   }
}

function aeEnablePagingButtons(aEnable)
{
   aeEnableImageLink("aenav_firstpage", aEnable);
   aeEnableImageLink("aenav_prevpage", aEnable);
   aeEnableImageLink("aenav_nextpage", aEnable);
   aeEnableImageLink("aenav_lastpage", aEnable);
}
// end aeEnablePagingButtons

function aeUpdatePagingButtonsState()
{
   var enFirst = false;
   var enLast = false;
   var enPrev = false;
   var enNext = false;
   var fromIndex = "";
   var toIndex = "";
   var total = "";
   var hasresults = false;
   if (gAeTaskListingResult != null)
   {
      enPrev = gAeTaskListingResult.getPreviousIndex() != -1;
      enNext = gAeTaskListingResult.getNextIndex() != -1;
      enFirst = enPrev;
      enLast = enNext;
      fromIndex = "" + gAeTaskListingResult.getDisplayFrom();
      toIndex = "" + gAeTaskListingResult.getDisplayTo();
      total = "" + gAeTaskListingResult.getTotal();
      hasresults = (gAeTaskListingResult.getTotal() > 0);
   }
   aeEnableImageLink("aenav_firstpage", enFirst);
   aeEnableImageLink("aenav_lastpage", enLast);
   aeEnableImageLink("aenav_prevpage", enPrev);
   aeEnableImageLink("aenav_nextpage", enNext);

   $(".aenav_fromindex").empty().html(fromIndex);
   $(".aenav_toindex").empty().html(toIndex);
   $(".aenav_total").empty().html(total);
   // show or hide
   aeShowByClass("aenavcontrols", hasresults);
}
// end aeUpdatePagingButtonsState()

/*
* Initialize the inbox listing table with alternating colors.
*/
function aeInitInboxListTable()
{
   gAeCurrentSelectionTaskId = null;
   gAeCurrentTask = null;
   aeShowInboxEntryPreview(false);
   $(".inboxtasklisting tr").mouseover(function()
   {
      $(this).addClass("over");
   });

   $(".inboxtasklisting tr").mouseout(function() {
      $(this).removeClass("over");
   });

   $(".inboxtasklisting tr.aetaskrow").click(function() {
      aeOnInboxTaskClick( $(this).attr("aetaskid") );
   });
   $(".inboxtasklisting tr.aetaskrow").dblclick(function() {
      aeOnInboxTaskDblClick( $(this).attr("aetaskid") );
   });
   $(".inboxtasklisting tr:even").addClass("alt");
}
// end aeInitInboxListTable()

function aeSelectInboxTask(aTaskId)
{
   if (gAeCurrentSelectionTaskId != null)
   {
      $("tr[@aetaskid=" + gAeCurrentSelectionTaskId + "]").removeClass("selrow");
   }

   if (aTaskId != null)
   {
      gAeCurrentSelectionTaskId = aTaskId;
      gAeCurrentTask = aeGetInboxTaskById(gAeCurrentSelectionTaskId);
      $("tr[@aetaskid=" + gAeCurrentSelectionTaskId + "]").addClass("selrow");
      aePopulateInboxEntryPreview(gAeCurrentTask);
      aeShowInboxEntryPreview(true);
   }
   else
   {
      aeClearInboxSelection();
   }
}
// end aeSelectInboxTask()

function aeClearInboxSelection()
{
   gAeCurrentSelectionTaskId = null;
   gAeCurrentTask = null;
   aePopulateInboxEntryPreview(null);
   aeShowInboxEntryPreview(false);
}
//end aeClearInboxSelection()

//
// Shows and hides the inbox task abstract preview div
// if it exists. The div should have a css class 'entrypreview'.
//
function aeShowInboxEntryPreview(aShow)
{

   var previewDiv = $(".entrypreview");
   if (aeIsNotNullOrUndefined(previewDiv))
   {
      if (aShow)
      {
         previewDiv.show();
      }
      else
      {
         previewDiv.hide();
      }
   }
}
// end aeShowInboxEntryPreview

function aePopulateInboxEntryPreview(aTaskAbstact)
{
 // Not Yet Implemented
}

function aeOnInboxTaskClick(aTaskId)
{
   aeSelectInboxTask(aTaskId);
   aeUpdateTaskActionButtonsState();
}
// aeOnInboxTaskClick()

function aeOnInboxTaskDblClick(aTaskId)
{
   aeOpenTaskEditor(aTaskId);
}
// aeOnInboxTaskDblClick()

function aeOpenTaskEditor(aTaskId)
{
   task = aeGetInboxTaskById(aTaskId);
   aeAssertNotNull(task, "task " + aTaskId + " not found");
   //alert("aeOpenTaskEditor(" + aTaskId + ") notification ? " + task.isNotification());
   var url = gAeTaskEditorXslUrl + "?taskId=" + encodeURIComponent(aTaskId);
   window.location = url;
}
// end aeOpenTaskEditor()

function aeEnableInbox(aEnable)
{
   // uses jQuery BlockUI plug-in (http://malsup.com/jquery/block)
   var inbox = $("#inboxresultsetcontainer");
   if (aeIsNotNullOrUndefined(inbox) && !aEnable)
   {
      inbox.block();
   }
   else if (aeIsNotNullOrUndefined(inbox))
   {
      inbox.unblock();
   }
}
// end aeEnableInbox

// ---------------------------------------------------
// LOAD INBOX TASK LIST AJAX
// ---------------------------------------------------

function aeLoadInboxTasks(aStartIndex)
{
   aeShowProgressMessage("Loading tasks ...");
   var elem = $("#inboxresultsetcontainer");
   aeEnableTaskCommandButtons(false);
   aeEnablePagingButtons(false);
   var rnddata = "" + Math.random();
   var filtervalue = gAeCurrFilter;
   var filterrole = gAeCurrRole;
   var orderby = gAeCurrOrderBy;
   var searchby = gAeCurrSearchBy;
   var data = { startIndex : aStartIndex , rnd : rnddata, filter : filtervalue, role: filterrole, orderByList : orderby, searchBy: searchby  };
   var onloadfn = function(xml) {
      aeOnLoadInboxData(xml, aStartIndex);
    };
   var onerrorfn = function(aRequest, aSettings) {
      aeHandleLoadInboxError("HTTP Error: " + aRequest.status + " - " + aRequest.statusText);
   };
   aeAjaxGetXml(gAeListInboxTasksAjaxUrl, data, gAeAjaxTimeoutMs, onerrorfn, onloadfn);
}
// end aeLoadInboxTasks()

function aeHandleLoadInboxError(aMessage)
{
   //
   // FIXME allow 3rd party to hook in
   //
   aeUpdatePagingButtonsState();
   aeShowErrorMessage("Error fetching inbox tasks: " + aMessage);
}

function aeOnLoadInboxData(aXml, aStartIndex)
{
   // parse result set
   var tmpNoChange = true;
   var firstTask = null;
   var resultSet = aeParseInboxListXml(aXml);
   if (resultSet.getStatus() == 0)
   {
      gAeTaskListingResult = resultSet;
      aePopulateInboxListingView(gAeTaskListingResult, aStartIndex);
   }// end if result set status == 0
   else
   {
      // show error.
      aeHandleLoadInboxError( resultSet.getMessage() );
   }
   aeUpdateTaskActionButtonsState();
}
// end aeOnLoadInboxData()

// update the inbox listing based on the current gAeTaskListingResult object.
function aeUpdateInboxListingView()
{
   aePopulateInboxListingView(gAeTaskListingResult, gAeInboxStartIndex);
} // end aeUpdateInboxView

function aePopulateInboxListingView(aTaskListingResult, aStartIndex)
{
   // clear table header deocorators (col sort indicators)
   $(".ae_inbox_col_sortval").html("");
   // hide notification indicator
   $("#aefilter_notification_glyph").hide();

   // hide all table column headers
   $.each(aeGetAllColumnIds(), function (j, colId) {
      $("#" + colId).hide();
   });

   // show only col headers for this filter
   var visibleColHeaderIds = aeGetColumnIdsForFiler(gAeCurrFilter);
   $.each(visibleColHeaderIds, function (j, colId) {
      $("#" + colId).show();
   });

   if (aTaskListingResult.getTotal() == 0)
   {
      // no data. hide table and show message.
      $(".inboxtasklisting tbody").empty();
      $(".inboxtasklisting").hide();
      $(".inboxtasklistingnotasks").show();
   }
   else
   {
      $(".inboxtasklistingnotasks").hide();
      $(".inboxtasklisting").show();
   }

   // first task in result set - use as default selection.
   var firstTask = null;
   // flag to indicate that the start position has changed.
   var posChanged = gAeInboxStartIndex != aStartIndex;
   gAeInboxStartIndex = aStartIndex;
   // if start position has changed i.e next/prev page, then clear current selection so that first entry is selected.
   if (posChanged)
   {
      gAeCurrentSelectionTaskId = null;
   }

   // update table header decorators (col sort indicators)
   $.each(aTaskListingResult.getOrderByList(), function (j, field) {
      var order = '+';
      var clazz = 'sortasc';
      if (field.charAt(0) == '-')
      {
         order = '-';
         clazz = 'sortdesc';
         field = field.substring(1);
      }
      // show only primary sort
      if (j == 0)
      {
         $("#ae_fid_" + field + " span.ae_inbox_col_sortval").html("<span class='" + clazz + "'>&nbsp;</span>");
      }
   });

   //
   // FIXME allow 3rd party to hook in
   //

   // add new entries
   var tasks = aTaskListingResult.getTaskList().getTasks();
   var tbodyEle = $(".inboxtasklisting tbody");
   var hasNotifications = false;
   // remove current entries
   tbodyEle.empty();
   $.each( tasks, function (i, task) {
      if (firstTask == null)
      {
         firstTask = task;
      }
      var clazz = "aetaskrow";
      if ("true" == task.getProperty("escalated"))
      {
         clazz = clazz + " escalated";
      }
      var  html = '<tr class="' + clazz + '" aetaskid="' + task.getTaskId() + '" aeidx="' + i + '" >';
      var colPropNames = aeGetColumnPropertyNamesForFilter(gAeCurrFilter);
      $.each( colPropNames, function (j, colName) {
         if (colName == "priority")
         {
            html =   html + '<td><span class="aetaskpriorityglyph aetaskpriorityglyph_' + task.getProperty("priority") + '">' + task.getPriorityDisplayName() + '</span>&nbsp;</td>';
         }
         else if (colName == "state")
         {
            if (task.isTask())
            {
               html =   html + '<td><span class="aetaskstateglyph aetaskstateglyph_' + task.getState() + '" title="' + task.getStateDisplayName() + '">&nbsp;</span></td>';
            }
            else
            {
               hasNotifications = true;
               html =   html + '<td>&nbsp;</td>';
            }
         }
         else
         {
            html =   html + '<td>' + task.getProperty(colName) + '</td>';
         }
      });
      html =   html + '</tr>';
      $(html).appendTo(".inboxtasklisting tbody");
   } );

   // show notification indicator
   if (hasNotifications)
   {
      $("#aefilter_notification_glyph").show();
   }
   if (aTaskListingResult.getTotal() == 0)
   {
      // if result set is empty, then deselect current item.
      gAeCurrentSelectionTaskId = null;
   }
   else if (gAeCurrentSelectionTaskId == null && firstTask != null)
   {
      // choose 1st item as default if there is no current selection.
      gAeCurrentSelectionTaskId = firstTask.getTaskId();
   }
   var tmpPrevTaskId = gAeCurrentSelectionTaskId;
   // decorate table and add event handlers
   aeInitInboxListTable();
   aeUpdatePagingButtonsState();
   aeShowStatusMessage("");
   if (tmpPrevTaskId != null)
   {
      aeOnInboxTaskClick(tmpPrevTaskId);
   }
}
// end aePopulateInboxView

function aeParseInboxListXml(aXml)
{
   var code = parseInt($("statuscode", aXml).text());
   var message = $("statusmessage", aXml).text();
   // short return if there is an error message.
   if (code > 0)
   {
      return new AeTaskListingResult(code, message, 0, 0, 0, -1, -1);
   }
   var filter = $("filter", aXml).text();
   var role = $("role", aXml).text();
   // enable permissions for all filters except for initiator and stakeholder
   var enablePermissions = role != "initiator";
   var count = parseInt($("count", aXml).text());
   var pageSize = parseInt($("pagesize", aXml).text());
   var total = parseInt($("total", aXml).text());
   var prevIndex = parseInt($("prevstartindex", aXml).text());
   var nextIndex = parseInt($("nextstartindex", aXml).text());
   var displayFrom = parseInt($("displayfromindex", aXml).text());
   var displayTo = parseInt($("displaytoindex", aXml).text());
   var orderByList = $("orderbys", aXml).text();
   var resultSet = new AeTaskListingResult(code, message, count, pageSize, total, prevIndex, nextIndex, displayFrom, displayTo, orderByList);
   var taskEleList = $("tasks>task",aXml);
   // task property element names in the result set are same as the meta data names (gAeTaskDetailPropertyNames). E.g <status/>, <priority/> etc.
   var eleNames = gAeTaskDetailPropertyNames;
   $.each( taskEleList, function (i, ctx) {
       var data = {};
       var taskid = $("taskid", ctx).text();
       $.each( eleNames, function (j, eleName) {
         data[eleName] = $(eleName, ctx).text()
       });
       var task = new AeTaskDetail(taskid, data);
       // manually create operation permissions.
       var taskType = task.getProperty("tasktype");
       var state = task.getProperty("state");
       var owner = task.getProperty("owner");
       var skippable = task.getProperty("isskipable");
       var initiator = task.getProperty("taskinitiator");
       var principalIsOwner = owner != null && owner == gAePrincipal;
       var principalIsInitiator = initiator != null && initiator == gAePrincipal;

       if (taskType != null && "NOTIFICATION" == taskType)
       {
         // set notification permissions
         task.addPermission("remove");
         // state/status does not apply to notifications
         task.setState("");
       }
       else if (enablePermissions)
       {
         // set task permissions
         if ("READY" == state)
         {
            task.addPermission("claim");
         }
         if ("READY" == state || ("RESERVED" == state && principalIsOwner))
         {
            task.addPermission("start");
         }
         if ( ("RESERVED" == state || "IN_PROGRESS" == state) && principalIsOwner)
         {
            task.addPermission("release");
         }
         if ("IN_PROGRESS" == state && principalIsOwner)
         {
            task.addPermission("stop");
         }
         if (skippable && (principalIsInitiator || principalIsOwner))
         {
            task.addPermission("skip");
         }
      } // end if tasktype = task
      resultSet.getTaskList().addTask(task);
   });
   return resultSet;
}
// end aeParseInboxListXml()


