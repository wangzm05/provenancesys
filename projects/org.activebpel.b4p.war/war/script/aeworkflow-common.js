/**
* PROPRIETARY RIGHTS STATEMENT
* The contents of this file represent confidential information that is the
* proprietary property of Active Endpoints, Inc.  Viewing or use of
* this information is prohibited without the express written consent of
* Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
* is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
*
* aeworkflow-common.js:
* Defines workflow application common script.
*
* Dependencies:
* aetaskdefs.js
* jquery.js, jquery.block.js (http://www.jquery.com, jQuery 1.1.2)
* jqModal.js (http://dev.iceburg.net/jquery/jqModal/)
*/

var gAeTaskListingResult = null;
var gAeCurrentSelectionTaskId = null;
var gAeCurrentTask = null;
/** User principal name */
var gAePrincipal = "";
var gAeRunTaskCommandAjaxUrl = "_runtaskcommand.jsp";

/* List of support task commands. */
var gAeTaskCommandIdPrefix = "aetask_cmd_";
var gAeSupportedTaskCommands = ["claim", "start", "stop", "skip", "release", "complete", "fail", "suspend", "resume", "suspendUntil", "forward", "delegate", "remove", ];

function aeIsCommandSupported(aCommand)
{
   var rval = false;
   $.each( gAeSupportedTaskCommands, function (i, cmd) {
      if (cmd == aCommand)
      {
         rval = true;
      };
   });
   return rval;
}
// aeIsCommandSupported()

function aeGetCommandFromActionID(actionId)
{
   aeAssertNotNull(actionId, "actionId in handleTaskCommand()");
   aeAssertNotNull(gAeCurrentSelectionTaskId, "gAeCurrentSelectionTaskId in handleTaskCommand()");
   var cmd = null;
   if (actionId.length > gAeTaskCommandIdPrefix.length && actionId.indexOf(gAeTaskCommandIdPrefix) != -1)
   {
      cmd = actionId.substring(actionId.indexOf(gAeTaskCommandIdPrefix) + gAeTaskCommandIdPrefix.length);
   }
   aeAssertNotNull(cmd, "cmd in handleTaskCommand()");
   return cmd;
}
// aeGetCommandFromActionID()

function aeInitTaskActionButtons(handler)
{
   // task actions
   aeMakeImageButton(gAeTaskCommandIdPrefix + "edit", "../images/TaskEdit20.png", handler);
   aeMakeImageButton(gAeTaskCommandIdPrefix + "start", "../images/StartTask20.png", handler);
   aeMakeImageButton(gAeTaskCommandIdPrefix + "claim", "../images/ClaimedTask20.png", handler);
   aeMakeImageButton(gAeTaskCommandIdPrefix + "release", "../images/ReleaseTask20.png", handler);
   aeMakeImageButton(gAeTaskCommandIdPrefix + "complete", "../images/CompleteTask20.png", handler);
   aeMakeImageButton(gAeTaskCommandIdPrefix + "fail", "../images/TaskFail20.png", handler);

   aeMakeImageButton(gAeTaskCommandIdPrefix + "stop", "../images/StopTask20.png", handler);
   aeMakeImageButton(gAeTaskCommandIdPrefix + "skip", "../images/TaskSkip20.png", handler);
   aeMakeImageButton(gAeTaskCommandIdPrefix + "suspend", "../images/TaskSuspend20.png", handler);
   aeMakeImageButton(gAeTaskCommandIdPrefix + "suspendUntil", "../images/TaskSuspend20.png", handler);
   aeMakeImageButton(gAeTaskCommandIdPrefix + "resume", "../images/TaskResume20.png", handler);
   aeMakeImageButton(gAeTaskCommandIdPrefix + "forward", "../images/TaskAssign20.png", handler);
   aeMakeImageButton(gAeTaskCommandIdPrefix + "delegate", "../images/TaskAssign20.png", handler);
   aeMakeImageButton(gAeTaskCommandIdPrefix + "remove", "../images/RemoveNotification20.png", handler);
   aeMakeImageButton(gAeTaskCommandIdPrefix + "activate", "../images/tick.png", handler);

   aeMakeImageLink(gAeTaskCommandIdPrefix + "setpriority", "../images/SetPriority20.png", "left", handler,  "../images/SetPriorityDisabled20.png");
}
// end aeInitTaskActionButtons()

function aeUpdateTaskActionButtonsState()
{
   aeEnableTaskCommandButtons(false);
   aeEnableImageLink("aetask_cmd_setpriority", false);
   aeEnableTaskCommandButton("edit", gAeCurrentTask != null && gAeTaskListingResult != null && gAeTaskListingResult.getTotal() > 0);
   var state = null;
   var hasFault = null;
   var owner = null;
   if (gAeCurrentTask != null)
   {

      state = gAeCurrentTask.getProperty("state");
      hasFault = gAeCurrentTask.getProperty("hasfault");
      owner = gAeCurrentTask.getProperty("owner");
   }
   if (state == null)
   {
      return;
   }
   var workItemCombo = $("#workitemdata_selection :selected");
   var canComplete = true;
   var canFail = true;
   if (workItemCombo && workItemCombo != null && workItemCombo.val())
   {
      var val = workItemCombo.val();
      if (val && val == "fault")
      {
         canComplete = false;
      }
      if (val && val == "output")
      {
         canFail = false;
      }

   }

   aeEnableTaskCommandButton("claim",       gAeCurrentTask.hasPermission("claim") );
   aeEnableTaskCommandButton("start",       gAeCurrentTask.hasPermission("start"));
   aeEnableTaskCommandButton("stop",        gAeCurrentTask.hasPermission("stop"));
   aeEnableTaskCommandButton("fail",        gAeCurrentTask.hasPermission("fail") && hasFault != null && "true" == hasFault && canFail);
   aeEnableTaskCommandButton("complete",    gAeCurrentTask.hasPermission("complete") && canComplete);
   aeEnableTaskCommandButton("release",      gAeCurrentTask.hasPermission("release"));
   aeEnableTaskCommandButton("suspend",      gAeCurrentTask.hasPermission("suspend"));
   aeEnableTaskCommandButton("suspendUntil", gAeCurrentTask.hasPermission("suspendUntil"));
   aeEnableTaskCommandButton("resume",      gAeCurrentTask.hasPermission("resume"));
   aeEnableTaskCommandButton("skip",        gAeCurrentTask.hasPermission("skip"));
   aeEnableTaskCommandButton("forward",     gAeCurrentTask.hasPermission("forward"));
   aeEnableTaskCommandButton("delegate",    gAeCurrentTask.hasPermission("delegate"));
   aeEnableTaskCommandButton("remove",      gAeCurrentTask.hasPermission("remove"));
   aeEnableImageLink("aetask_cmd_setpriority", gAeCurrentTask.hasPermission("setPriority"));
}
// aeUpdateTaskActionButtonsState



//
// Common method to handle task operations such as claim, task etc.
// @param actionId - UI button id.
// @param aPreprocessCallback callback with (taskref, cmd) prior to sending the command back to the server.
// @param aResultHandlerCallback ajax result handler.
function aeHandleTaskCommand(actionId, aPreprocessCallback, aResultHandlerCallback)
{
   var cmd = aeGetCommandFromActionID(actionId);

   // call back for 'preprocessing'.
   if (aPreprocessCallback)
   {
      if (aPreprocessCallback(gAeCurrentSelectionTaskId, cmd) == false)
      {
         return;
      }
   }
   if ("edit" == cmd)
   {
      aeOpenTaskEditor(gAeCurrentSelectionTaskId);
   }
   else if ("assign" == cmd)
   {
      aeShowAssignDialog(gAeCurrentSelectionTaskId, cmd, aResultHandlerCallback);
   }
   else if ("setpriority" == cmd)
   {
      aeShowSetPriorityDialog(gAeCurrentSelectionTaskId, cmd, aResultHandlerCallback);
   }
   else if (aeIsCommandSupported(cmd))
   {
      aeRunTaskCommand(gAeCurrentSelectionTaskId, cmd, aResultHandlerCallback);
   }
   else
   {
      alert("Unknown task command:" + cmd);
   }
}
// handleTaskCommand()

function aeEnableTaskCommandButtons(aEnable)
{
   $.each( gAeSupportedTaskCommands, function (i, cmd) {
      aeEnableTaskCommandButton(cmd, aEnable);
   });
}
// end aeEnableTaskCommandButtons()

function aeEnableTaskCommandButton(aCommand, aEnable)
{
   var btnid = gAeTaskCommandIdPrefix + aCommand;
   aeEnableImageButton(btnid, aEnable);
}
// end aeEnableTaskCommandButton()

// ---------------------------------------------------
// RUN TASK COMMAND AJAX
// ---------------------------------------------------

function aeRunTaskCommand(aTaskId, aCommand, aCallback, aCommandParamMap)
{
   aeShowProgressMessage("Performing command: " + aCommand);
   aeEnableTaskCommandButtons(false);

   var rnddata = "" + Math.random();
   var data = { taskId: aTaskId, action: aCommand , rnd: rnddata };
   // copy over extra param
   if (aeIsNotNullOrUndefined(aCommandParamMap))
   {
      for(var k in aCommandParamMap) {
         var v = aCommandParamMap[k];
         data[k] = v;
      }
   }

   var onloadfn = function(aXml) {
      aeOnLoadRunTaskCommand(aXml, aTaskId, aCommand, aCallback);
   };
   var onerrorfn = function(request, settings) {
      aeOnLoadRunTaskCommandError(request, settings, aTaskId, aCommand, aCallback);
   };
   aeAjaxGetXml(gAeRunTaskCommandAjaxUrl, data, gAeAjaxTimeoutMs, onerrorfn, onloadfn);
}
// end aeRunTaskCommand()

function aeOnLoadRunTaskCommandError(aRequest, aSettings, aTaskId, aCommand, aCallback)
{
   aCallback(1,"HTTP Error: " + aRequest.status + " - " + aRequest.statusText, aTaskId, aCommand);
}
// end aeOnLoadRunTaskCommandError();

function aeOnLoadRunTaskCommand(aXml, aTaskId, aCommand, aCallback)
{
   var code = parseInt($("statuscode", aXml).text());
   var message = $("statusmessage", aXml).text();
   aCallback(code, message, aTaskId, aCommand);
}
// end aeOnLoadRunTaskCommand()


