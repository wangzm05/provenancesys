/**
* PROPRIETARY RIGHTS STATEMENT
* The contents of this file represent confidential information that is the
* proprietary property of Active Endpoints, Inc.  Viewing or use of
* this information is prohibited without the express written consent of
* Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
* is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
*
* aeworkflow.js:
* Defines workflow application related script.
*
* Dependencies:
* aeworkflow-taskdefs.js
* aeworkflow-util.js
* aeworkflow-common.js
* jquery.js, jquery.block.js (http://www.jquery.com, jQuery 1.1.2)
* jqModal.js (http://dev.iceburg.net/jquery/jqModal/)
*/

var gBlockUiMessage = '<p><img style="float:left;" src="/aetask/images/busywait.gif" /> Please wait...</p>';
var gAeCurrentState = '';
var gAeTaskWorkItemFormId = "taskdetail_workitem_form";
var gAeTaskWorkItemFormModified = false;
var gAeAddAttachmentAjaxUrl = "ajax/_addattachment.jsp";
var gAeDeleteAttachmentAjaxUrl = "ajax/_deleteattachment.jsp";
var gAeGetAttachmentsAjaxUrl = "ajax/_getattachments.jsp";
var gAeOpenAttachmentUrl = "getAttachment"; // servlet

function aeXslInitTask(aInitialTabIndex)
{
   // create buttons
	aeInitTaskActionButtons(aeOnXslTaskCommandAction);
	// disable buttons
	aeEnableTaskCommandButtons(false);
	aeEnableImageLink("aetask_cmd_setpriority", false);
	// conditionally enable the buttons based on permissions.
   $("a.aeEnableCommandButton").each(function(i) {
      $(this).removeClass("aeEnableCommandButton");
      var id = $(this).attr("id");      
      aeEnableImageButton(id,true);
   });
   $("a.aeEnableCommandLink").each(function(i) {
      $(this).removeClass("aeEnableCommandLink");
      var id = $(this).attr("id");
      aeEnableImageLink(id,true);
   });

   aeMakeImageButton(gAeTaskCommandIdPrefix + "addcomment", "../images/comment_add.png", aeOnXslTaskDetailAddCommentAction);
   aeMakeImageButton(gAeTaskCommandIdPrefix + "addfile", "../images/attach.png", aeOnXslTaskDetailAddFileAction);

   aeXslInitTabs(aInitialTabIndex);
   aeXslInitComments();
   aeXslInitAttachments();
   
   // hook in click event handler for 'show more details' link.
   $("#aeshowstatusmessagedetail").click( function() {
      $("#aestatusmessagedetail").show();
      $(this).hide();
      return false;
   });      
}

function aeXslInitNotification()
{
   // create buttons
	aeInitTaskActionButtons(aeOnXslTaskCommandAction);
	// disable buttons
	aeEnableTaskCommandButtons(false);
	// enable remove button
	aeEnableTaskCommandButton("remove", true);   
   // hook in click event handler for 'show more details' link.
   $("#aeshowstatusmessagedetail").click( function() {
      $("#aestatusmessagedetail").show();
      $(this).hide();
      return false;
   });      
}

function aeXslInitWorkItem()
{
   // init buttons for saving and discarding forms.
   aeXslInitWorkItemSubmitButtons();   
   // init work item form controls
   aeOnWorkItemFormLoaded();
   // show fault section if state is failed.
   if ("FAILED" == gAeCurrentState)
   {
   	aeShowFaultData();
   }
}
// end aeXslInitWorkItem()

//
// Creates the work item form save and cacel buttons.
//
function aeXslInitWorkItemSubmitButtons()
{
   aeMakeImageButton(gAeTaskCommandIdPrefix + "save", "../images/accept.png", aeOnXslSubmitWorkItemFormAction);
   aeMakeImageButton(gAeTaskCommandIdPrefix + "cancel", "../images/cancel.png", aeOnXslResetWorkItemFormAction);
}
// end aeXslInitWorkItemSubmitButtons()

function aeXslInitComments()
{
   $("#commentlisting tr:even").addClass("altrule");

   // updated/delete comment operation handler
   $("a.updatecommentop").click( function() {
      var ref = $(this).parent("span").attr("aetaskid");
      var id = $(this).parent("span").attr("aecommentid");
      aeXslShowCommentDialog(id);
      return false;
   });

   $("a.deletecommentop").click( function() {
      var ref = $(this).parent("span").attr("aetaskid");
      var id = $(this).parent("span").attr("aecommentid");
      var ok = confirm("Delete comment?");
      if (ok == true)
      {
      	  aeXslRunCommand("deleteComment", id, "");
      }
      return false;
   });

}
// end aeXslInitComments()

function aeXslInitAttachments()
{
   $(".#attachmentlisting tr a.openattachment").click(function() {
      var taskId = $(this).parent("td").parent("tr").attr("aetaskid");
      var attachmentName = $(this).parent("td").parent("tr").attr("attachmentName");
      var attachmentId = $(this).parent("td").parent("tr").attr("attachmentId");
      aeXslOpenAttachment(taskId, attachmentName, attachmentId);
      return false;
   });

   $(".#attachmentlisting tr a.deleteattachment").click(function() {
      var taskId = $(this).parent("td").parent("tr").attr("aetaskid");
      var attachmentName = $(this).parent("td").parent("tr").attr("attachmentName");
      var attachmentId = $(this).parent("td").parent("tr").attr("attachmentId");
      aeXslDeleteAttachment(taskId, attachmentName, attachmentId);
      return false;
   });
 } 
// end aeXslInitAttachments()

function aeXslInitTabs(aInitialTabIndex)
{
   // hook in tabbing.
   $('#taskdetailtabcontainer').tabs(aInitialTabIndex, { fxAutoHeight: true });
   // show work item tabs
   $("#taskdetailtabcontainer").show();
}
// aeInitXslTabs()

function aeXslOpenAttachment(aTaskId, aName, aAttachmentId)
{
   var url = gAeOpenAttachmentUrl + "?taskId=" + encodeURIComponent(aTaskId);
   url = url + "&attachmentId=" + encodeURIComponent(aAttachmentId);
   url = url + "&attachmentName=" + encodeURIComponent(aName);
   window.location = url;
   return false;
}
// end aeXslOpenAttachment

function aeXslDeleteAttachment(aTaskId, aName, aAttachmentId)
{
   if (confirm("Delete '" + aName + "' file ?") == false)
   {
      return false;
   }
   // update attachment form input data.
   var cmdNameInputField = $("#aetaskattachmentform_command_name");
   cmdNameInputField.val("deleteAttachment");
   var cmdIdInputField = $("#aetaskattachmentform_command_id");
   cmdIdInputField.val(aAttachmentId);
   $.blockUI(gBlockUiMessage); 
   var form = $("#aetask_attachment_form");
   form.submit();   
   return false;
}
//  end aeXslDeleteAttachment

function aeOnXslTaskDetailAddCommentAction(actionId)
{
  aeXslShowCommentDialog(null);
}
// end aeOnXslTaskDetailAddCommentAction

function aeOnXslTaskDetailUpdateCommentAction(aCommentId)
{
   aeXslShowCommentDialog(aCommentId);
}
// end  aeOnXslTaskDetailUpdateCommentAction

function aeOnXslTaskDetailAddFileAction(actionId)
{
	$.blockUI(gBlockUiMessage); 
   var form = $("#aetask_attachment_form");
   form.submit();
}
// end aeOnXslTaskDetailAddFileAction

function aeOnXslSubmitWorkItemFormAction(actionId)
{
   if ( aeValidateWorkItemFormFields() )
   {
	   $.blockUI(gBlockUiMessage); 
	   var form = $("#taskdetail_workitem_form");
	   form.submit();
   }	
}
// end aeOnXslSubmitWorkItemFormAction

function aeOnXslResetWorkItemFormAction(actionId)
{
   aeResetTaskDetailWorkItemForm();
}
// end aeOnXslResetWorkItemFormAction

function aeResetTaskDetailWorkItemForm()
{
	var sel = $("#workitemdata_selection :selected").val();
	var workItemForm = $("#" + gAeTaskWorkItemFormId);
   workItemForm.resetForm();
   // preserve previous state of output/fault selection
   if ("fault" == sel)
   {
   	aeShowFaultData();
   }
   aeSetWorkItemFormModified(false);
   if (gAeCurrentTask != null)
   {
      aeUpdateTaskActionButtonsState();
   }   
}
// aeResetTaskDetailWorkItemForm

function aeOnXslTaskCommandAction(actionId)
{
   var cmd = aeGetCommandFromActionID(actionId);
   if ("delegate" == cmd)
   {
      aeXslShowAssignDialog(cmd);
   }
   else if ("setpriority" == cmd)
   {
      aeXslShowSetPriorityDialog(cmd);
   }
   else if ("complete" == cmd || "fail" == cmd)
   {
      // validate and confirm operation
	   if (aeHandleTaskDetailCommandPreprocess(null, cmd) == true)
	   {
	   	aeXslRunCommand(cmd, "", "");
	   }
   }
   else
   {
	   aeXslRunCommand(cmd, "", "");
   }
}
// aeOnXslTaskCommandAction()

function aeXslRunCommand(aCommand, aId, aData)
{
   var cmdNameInputField = $("#aetaskcommandform_command_name");
   cmdNameInputField.val(aCommand);
   var cmdIdInputField = $("#aetaskcommandform_command_id");
   cmdIdInputField.val(aId);
   var cmdDataInputField = $("#aetaskcommandform_command_data");
   cmdDataInputField.val(aData);
   $.blockUI(gBlockUiMessage); 
   var form = $("#aetaskcommandform");
   form.submit();
}
// aeXslRunCommand()

function aeXslShowCommentDialog(aCommentId)
{
   var text = "";
   if (aCommentId != null)
   {
      text = $("#commenttext_" + aCommentId).text();
      $("#aecommentdialog_title").text("Edit Comment");
   }
   else
   {
      aCommentId = "";
      $("#aecommentdialog_title").text("Add Comment");
   }
   $("#aecommentdialog_taskcomment_text").val(text);
   $("#aecommentdialog_taskcomment_id").val(aCommentId);

   var dlgshow = function(hash) {
      var doSaveComment = function(aId) {
         var id = $.trim( $("#aecommentdialog_taskcomment_id").val() );
         var text = $.trim($("#aecommentdialog_taskcomment_text").val() );
         if ("" == id)
         {
            aeXslRunCommand("addComment", "", text);
         }
         else
         {
            aeXslRunCommand("updateComment", id, text);
         }
         hash.w.jqmHide();
         return false;
       };
       aeMakeImageButton("aetask_cmd_savecomment", "../images/accept.png", doSaveComment);
       aeMakeImageButton("aetask_cmd_cancelcomment", "../images/cancel.png", null);
       $("#aetask_cmd_cancelcomment").addClass("jqmClose");
       hash.w.show();
   };
   var params = {};
   params["onShow"] = dlgshow;
   $('#aecommentdialog').jqm(params).jqmShow();
}
// end aeShowCommentDialog

function aeXslShowAssignDialog(aCommand)
{
   var dlgshow = function(hash) {
       var doAssign = function(aId) {
          var newowner =  $.trim( $("#aeassigncommanddialog_newowner").val() );
          if ("" == newowner)
          {
             alert("Please enter name for new owner.");
          }
          else
          {
             aeXslRunCommand(aCommand, "", newowner);
             hash.w.jqmHide();
          }
          return true;
       };
       aeMakeImageButton("aetask_assigndialog_ok", "../images/accept.png", doAssign);
       aeMakeImageButton("aetask_assigndialog_cancel", "../images/cancel.png", null);
       $("#aetask_setprioritydialog_cancel").addClass("jqmClose");
       hash.w.show();
      };
      var params = {};
      params["onShow"] = dlgshow;
      $('#aeassigncommanddialog').jqm(params).jqmShow();
}
// end aeShowAssignDialog

function aeXslShowSetPriorityDialog(aCommand)
{
   var dlgshow = function(hash) {
       var doSetPriority = function(aId) {
          var s =  $.trim( $("#aesetprioritydialog_priority").val() );
          var priority = parseInt(s);
          if (isNaN(priority) || priority < 0)
          {
             alert("Priority value must be a positive integer.");
          }
          else
          {
             aeXslRunCommand(aCommand, "", priority);
             hash.w.jqmHide();
          }
          return false;
       };
       aeMakeImageButton("aetask_setprioritydialog_ok", "../images/accept.png", doSetPriority);
       aeMakeImageButton("aetask_setprioritydialog_cancel", "../images/cancel.png", null);
       $("#aetask_setprioritydialog_cancel").addClass("jqmClose");
       hash.w.show();
      };
      var params = {};
      params["onShow"] = dlgshow;
      $('#aesetprioritydialog').jqm(params).jqmShow();
}
// end aeShowSetPriorityDialog

//
// returns true if the work item form with ID 'gAeTaskWorkItemFormId' exists with the required input fields.
// (e.g. taskId is required).
function aeWorkItemFormExists()
{
  var enable = aeIsNotNullOrUndefined($("#" + gAeTaskWorkItemFormId).attr("action"));
  if ( enable )
  {
      // form exists, so check for required (hidden) fields
      var taskIdInputName = $("#" + gAeTaskWorkItemFormId + " input.aetaskworkitem_taskid[@name='taskId']").attr("name");
      enable = aeIsNotNullOrUndefined(taskIdInputName);
      aeAssertNotNull(taskIdInputName, "WorkItem form taskId input field is missing!");
      //form submit mode is also required.
      var submitModeInputName = $("#" + gAeTaskWorkItemFormId + " input.aetaskworkitem_submitmode[@name='aetaskworkitem_submitmode']").attr("name");
      enable = enable && aeIsNotNullOrUndefined(submitModeInputName);
  }
  return enable;
}
// end aeWorkItemFormExists()


//
// Returns true if the work item form should 'editable'.
// The form is 'editable' when it exists, and the principal has permission to set the output.
function aeIsWorkItemFormEditable()
{
   var editable = aeWorkItemFormExists();
   var state = null;
   if (gAeCurrentTask != null)
   {
      editable = editable && (gAeCurrentTask.hasPermission("setOutput") || gAeCurrentTask.hasPermission("setFault"));
   }
   return editable;
}
// end aeIsWorkItemFormEditable

//
// called prior to form is submitted.
//
function aeValidateWorkItemFormFields()
{
   // check for missing/empty output and fault data fields. (based on form being submitted).
   var sel = $("#workitemdata_selection :selected").val();
   if ("fault" == sel)
   {
      return aeCheckWorkItemFormFaultData();
   }
   else
   {
      return aeCheckWorkItemFormOutputData();
   }
}
//end aeValidateWorkItemFormFields()

//
// called when the work item form has been loaded.
//
function aeOnWorkItemFormLoaded()
{
   // clear dirty flag.
   aeSetWorkItemFormModified(false);

   // hook event handler to toggle workitem form output data and fault data.
   // add event handler for filter selection
   $("#workitemdata_selection").change( function() {
         aeOnWorkItemFormDataSelection();
         return false;
      }
   );

   // hook in on change listeners to the for all editable fields in the work item form.
   $("#taskdetail_workitem_form .workitemdata_editable").change( function() {
        aeOnWorkItemFormDataChanged($(this));
        return true;
      }
   );

   // hook in on change listener to fault names - to enable/disable the FAIL button.
   $("#aetaskworkitem_faultnames").change( function() {
        var faultName = $("#aetaskworkitem_faultnames :selected").val();
        var en = ("" != faultName && aeIsWorkItemFormEditable() );
        aeEnableTaskCommandButton("fail",  en);
        return false;
      }
   );

   var hasOutputData =  aeHasWorkItemFormOutputData();
   var hasFaultData = aeHasWorkItemFormFaultData();
   if (gAeCurrentTask != null)
   {
      if ("failed" == gAeCurrentTask.getProperty("state"))
      {
      	// force/show fault info.
      	hasFaultData = true;
      	hasOutputData = false;
      }
   }
   if (hasOutputData == false && hasFaultData == true)
   {
      // select fault
      aeShowFaultData();
   }

   // add show sample data event handler
   $("a.show_workitemdata_sample").click( function() {
        var partName = $(this).attr('aeoutputpartname');
        aeShowSetSampleDataDialog(partName);
        return false;
      }
   );

   // call handler to show either output data or fault data section.
   aeOnWorkItemFormDataSelection();
}
// end aeOnWorkItemFormLoaded

// invoked when the work item form controls have been modified.
function aeOnWorkItemFormDataChanged(aControl)
{
   aeSetWorkItemFormModified(true);
}
// end aeOnWorkItemFormDataChanged

function aeSetWorkItemFormModified(aDirty)
{
   gAeTaskWorkItemFormModified = aDirty;
}
// end aeSetWorkItemFormModified

function aeIsWorkItemFormModified()
{
   return gAeTaskWorkItemFormModified;
}
// end aeIsWorkItemFormModified()

// Return true if output data is available. i.e. at least one of the parts have data.
function aeHasWorkItemFormOutputData()
{
   var rval = false;
   var outputElems = $("#taskdetail_workitem_form .workitemdata_editable");
   if (aeIsNotNullOrUndefined(outputElems))
   {
      $.each( outputElems, function (i, outputElem) {
         var partName = outputElem.getAttribute('aeoutputpartname');
         if (partName != null)
         {
            var text =  $.trim(outputElem.value);
            if ("" != text)
            {
               rval = true;
            }
         }
      });
   }
   return rval;
}
// end aeHasWorkItemFormOutputData()

// Returns true if a fault is selected and it has data.
function aeHasWorkItemFormFaultData()
{
   var rval = false;
   var faultNameEle = $("#aetaskworkitem_faultnames :selected");
   var faultDataEle = $("#aetaskworkitem_faultdata");
   if (aeIsNotNullOrUndefined(faultNameEle) && faultNameEle.val() != null
      && aeIsNotNullOrUndefined(faultDataEle) && faultDataEle.val() != null)
   {
      var faultName = $.trim( faultNameEle.val());
      var faultData =  $.trim( faultDataEle.val() );
      rval = ("" != faultName && "" != faultData);
   }
   return rval;
}
// end aeHasWorkItemFormFaultData()

// Return true if the work item form outputdata field has content.
function aeCheckWorkItemFormOutputData()
{
   var outputElems = $("#taskdetail_workitem_form .workitemdata_editable");
   var missingParts = "";
   $.each( outputElems, function (i, outputElem) {
      var partName = outputElem.getAttribute('aeoutputpartname');
      if (partName != null)
      {
         var text =  $.trim(outputElem.value);
         if ("" == text)
         {
            missingParts = missingParts + "Output part '" + partName + "' \n";
         }
      }
   });
   var rval = true;
   if ("" != missingParts)
   {
      var msg = "The following part(s) do not have any data:\n\n";
      msg = msg + missingParts + "\n";
      alert(msg);
      rval = false;
   }
   return rval;
}
// end aeCheckWorkItemFormOutputData()

// Return true if the work item form fault data field has content.
function aeCheckWorkItemFormFaultData()
{
   var faultNameEle = $("#aetaskworkitem_faultnames :selected");
   var faultName = "";
   // NPE check for IE
   if (faultNameEle.val() != null)
   {   
      faultName = $.trim( faultNameEle.val() );
   }
   var faultData =  $.trim( $("#aetaskworkitem_faultdata").val() );
   var rval = ("" == faultName) || ("" != faultName && "" != faultData);
   if (rval == false)
   {
       var msg = "Data is required for fault " + faultName + ".";
       alert(msg);
   }
   return rval;
}
// end aeCheckWorkItemOuputFormData()

//
// Called prior to task command is issued from the task details page.
//
function aeHandleTaskDetailCommandPreprocess(aTaskRef, aCommand)
{
   if ("complete" == aCommand)
   {
      // check if form field have been modified, but not saved.
      if (aeIsWorkItemFormModified())
      {
         var msg = "The work item form has been modified. Please save or discard your changes prior to completing the task.";
         alert(msg);
         return false;
      }
      // check if form fields are empty
      else if (aeCheckWorkItemFormOutputData() == false)
      {
         return false;
      }
      else
      {
         var msg = "Complete task?";
         var rval = confirm(msg);
         return rval;
      }
   }
   else if ("fail" == aCommand)
   {
      // check, if fault is selected  then fault data is also provided.
      if (aeCheckWorkItemFormFaultData() == false)
      {
         return false;
      }
      if (aeIsWorkItemFormModified())
      {
         var msg = "The work item form has been modified. Please save or discard your changes prior to failing the task.";
         alert(msg);
         return false;
      }
      var msg = "Fail task?";
      var rval = confirm(msg);
      return rval;
   }
   return true;
}

function aeShowFaultData()
{
   $("#workitemdata_selection:first-child option")[1].selected = true;
   aeShowHideOutputAndFaultData(false);
}
// end aeShowFaultData
function aeShowOutputData()
{
   $("#workitemdata_selection:first-child option")[0].selected = true;
   aeShowHideOutputAndFaultData(true);
}
// end aeShowOutputData

//
// Event handler that is called when the work item output vs fault data
// selection control is changed.
function aeOnWorkItemFormDataSelection()
{
   var sel = $("#workitemdata_selection :selected").val();
   aeShowHideOutputAndFaultData("output" == sel);
   if (gAeCurrentTask != null)
   {
      aeUpdateTaskActionButtonsState();
   }
}
// end aeOnWorkItemFormDataSelection()

function aeShowHideOutputAndFaultData(bShowOutput)
{
   if (bShowOutput == true)
   {
      $("#workitemdata_faultsection").hide();
      $("#workitemdata_outputsection").show();
   }
   else
   {
      $("#workitemdata_outputsection").hide();
      $("#workitemdata_faultsection").show();
   }
}
// end aeShowHideOutputAndFaultData

