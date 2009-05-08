/**
* PROPRIETARY RIGHTS STATEMENT
* The contents of this file represent confidential information that is the
* proprietary property of Active Endpoints, Inc.  Viewing or use of
* this information is prohibited without the express written consent of
* Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
* is strictly forbidden. Copyright (c) 2002-2007 All rights reserved.
*
* aetaskdefs.js:
* Defines workflow application related support objects.
*
*/

/*
* Light weight object for the inbox task item.
* @param aTaskref task ref
* @param aData array of task data.
*/
function AeTaskDetail(aTaskId, aData)
{
	// data attr names: priority, state, creation-date, owner, name.
	this.mTaskId = aTaskId;
	this.mData = aData;
	// supported permissions
	this.permissions = {}
	this.previousState = null;
	// state display names
   this.stateDisplayNames = [];
   this.stateDisplayNames["READY"] = "Unclaimed";
   this.stateDisplayNames["RESERVED"] = "Claimed";
   this.stateDisplayNames["IN_PROGRESS"] = "Started";
   this.stateDisplayNames["COMPLETED"] = "Completed";
   this.stateDisplayNames["FAILED"] = "Failed";
   this.stateDisplayNames["ERROR"] = "Error";
   this.stateDisplayNames["OBSOLETE"] = "Obsolete";
   this.stateDisplayNames["EXITED"] = "Exited";
   this.stateDisplayNames["SUSPENDED"] = "Suspended";
   //priority
   this.priorityDisplayNames = {};


	// initialize the member function references
	// for the class prototype
	if (typeof(_AeTaskDetail_prototype_called) == 'undefined')
	{
		_AeTaskDetail_prototype_called = true;
		AeTaskDetail.prototype.getTaskId = getTaskId;
		AeTaskDetail.prototype.getState = getState;
		AeTaskDetail.prototype.setState = setState;
		AeTaskDetail.prototype.restoreState = restoreState;
		AeTaskDetail.prototype.getProperty = getProperty;
		AeTaskDetail.prototype.getStateDisplayName = getStateDisplayName;
		AeTaskDetail.prototype.getPriorityDisplayName = getPriorityDisplayName;
		AeTaskDetail.prototype.claim = claim;
		AeTaskDetail.prototype.start = start;
		AeTaskDetail.prototype.addPermission = addPermission;
		AeTaskDetail.prototype.revokePermission = revokePermission;
		AeTaskDetail.prototype.hasPermission = hasPermission;
		AeTaskDetail.prototype.isFinalState = isFinalState;
		AeTaskDetail.prototype.isTask = isTask;
		AeTaskDetail.prototype.isNotification = isNotification;
	}

	/**
	 * Returns task reference.
	 */
	function getTaskId()
	{
		return this.mTaskId;
	}
	// getTaskId()

	/**
	 * Returns true if this is TASK type.
	 */
	function isTask()
	{
		return !this.isNotification();
	}
	
	/**
	 * Returns true if this is NOTIFICATION type.
	 */
	function isNotification()
	{
		return "NOTIFICATION" == this.getProperty("tasktype");
	}	

	/**
	 * Returns state.
	 */
	function getState()
	{
		return this.getProperty("state");
	}

	/**
	 * Sets the new state.
	 */
	function setState(aNewState)
	{
		this.previousState = this.getProperty("state");
		this.mData["state"] = aNewState;
	}

	/**
	 * Restores state to previous value.
	 */
	function restoreState()
	{
		this.mData["state"] = this.previousState;
		// clear owner field.
		if (this.mData["state"] == "READY")
		{
			this.mData["owner"] = "";
		}
		// restore permissions - work around due to client side state changes (e.g. claim() and start() )
		if ("IN_PROGRESS" == this.previousState)
		{
			this.addPermission("start");
			this.addPermission("claim");
		}
		else if ("RESERVED" == this.previousState)
		{
			this.addPermission("claim");
		}		
	}

	/**
	 * adds an operation permission name.
	 */
	function addPermission(aOperationName)
	{
		if (aOperationName && aOperationName != "")
		{
			this.permissions[aOperationName] = true;
		}
	}

	/**
	 * Revokes an operation permission name.
	 */
	function revokePermission(aOperationName)
	{
		if (aOperationName && aOperationName != "")
		{
			this.permissions[aOperationName] = false;
		}
	}

	/**
	 * Returns true if permission is available for the given operation.
	 */ 
	function hasPermission(aOperationName)
	{
		if (aOperationName && aOperationName != "")
		{
			if (typeof(this.permissions[aOperationName]) != 'undefined')
			{
				return (this.permissions[aOperationName] == true);
			}
		}
		return false;
	}

	/**
	 * Updates the task state to reserved.
	 */
	function claim(aOwner)
	{
		this.setState("RESERVED");
		this.revokePermission("claim");
		if (typeof(aOwner) != 'undefined' && aOwner != null && aOwner != "")
		{
			this.mData["owner"] = aOwner;
		}
	}

	/**
	 * Updates the task state to in-progress.
	 */
	function start(aOwner)
	{
		this.setState("IN_PROGRESS");
		this.revokePermission("start");
		this.revokePermission("claim");
		if (typeof(aOwner) != 'undefined' && aOwner != null && aOwner != "")
		{
			this.mData["owner"] = aOwner;
		}
	}

	/**
	 * Returns task detail property.
	 */
	function getProperty(aName)
	{
		if (typeof(this.mData[aName]) == 'undefined')
		{
			return null;
		}
		else
		{
			return this.mData[aName];
		}
	}
	// end getProperty()

	/**
	 * Returns state or status display name instead of internal status code.
	 */
	function getStateDisplayName()
	{
      var key = this.getState();
      if (key == null)
      {
         return "";
      }
      var rval = this.stateDisplayNames[key];
      if (typeof(rval) == 'undefined')
      {
         rval = key;
      }
      return rval;
	}

	/**
	 * Returns the priority display name.
	 */
	function getPriorityDisplayName()
	{
	   var key = this.getProperty("priority");
	   if (key == null)
	   {
	   	return "";
	   }
		var rval = this.priorityDisplayNames[key];
		if (typeof(rval) == 'undefined')
		{
			rval = key;
		}
		return rval;
	}
	
	function isFinalState()
	{
	   var state = this.getState();
	   if (state != null 
	   	&& (state == "COMPLETED" || state == "OBSOLETE" || state == "ERROR" || state == "FAILED" || state == "EXITED") )
	   {
	   	return true;
	   }
		return false;
	}	
}
// end AeTaskDetail()

/*
* Contains a list of task items.
* @param aTaskref task ref
* @param aData array of task data.
*/
function AeTaskList()
{
   this.mId = 0;
   this.mTasks = [];
	if (typeof(_AeTaskList_prototype_called) == 'undefined')
	{
		_AeTaskList_prototype_called = true;
		AeTaskList.prototype.addTask = addTask;
		AeTaskList.prototype.getTask = getTask;
		AeTaskList.prototype.getTasks = getTasks;
		AeTaskList.prototype.getSize = getSize;
		AeTaskList.prototype.getTaskById = getTaskById;
		AeTaskList.prototype.removeTaskById = removeTaskById;
	}

	/*
	 * Returns the number of items in the list.
	 */
	function getSize()
	{
		return this.mTasks.length;
	}

	/*
	 * Adds a task to the list and returns the index
	 */
	function addTask(aTaskDetail)
	{
		this.mTasks.push(aTaskDetail);
		return this.mTasks.length - 1;
	}
	// end addTask()

	/**
	 * Returns task by ref.
	 */
	function getTaskById(aTaskId)
	{
		rval = null;
		for (i = 0; i < this.mTasks.length; i++)
		{
			if (this.mTasks[i].getTaskId() == aTaskId)
			{
				rval = this.mTasks[i];
				break;
			}
		}
		return rval;
	}
	// end getTaskById()
	
	/**
	 * removes given task
	 */
	function removeTaskById(aTaskId)
	{
		rval = null;
		for (i = 0; i < this.mTasks.length; i++)
		{
			if (this.mTasks[i].getTaskId() == aTaskId)
			{
				rval = this.mTasks[i];
				this.mTasks.splice(i,1);
				break;
			}
		}
		return rval;
	}
	// end removeTaskById()	

	/*
	 * Returns the task at the named .
	 */
	function getTask(aIndex)
	{
		if (this.mTasks.length > 0)
		{
			if (aIndex >=0 && aIndex < this.mTasks.length)
			{
				return this.mTasks[aIndex];
			}
		}
		return null;
	}
	// end getTask()

	function getTasks()
	{
		return this.mTasks;
	}
	// end getTasks()
}
// end AeTaskList()

/*
* Object containing task detail result.
*/
function AeTaskDetailResult(aStatusCode, aMessage, aTask)
{
	this.mStatus = aStatusCode;
	this.mMessage = aMessage;
	this.mTask = aTask;

	if (typeof(_AeTaskDetailResult_prototype_called) == 'undefined')
	{
		_AeTaskDetailResult_prototype_called = true;
		AeTaskDetailResult.prototype.getTask = getTask;
		AeTaskDetailResult.prototype.getStatus = getStatus;
		AeTaskDetailResult.prototype.getMessage = getMessage;
	}

	/**
	 * Returns AeTaskDetail object.
	 */
	function getTask()
	{
		return this.mTask;
	}

	/**
	 * Response status code. A value of zero indicates success.
	 */
	function getStatus()
	{
		return this.mStatus;
	}

	/**
	 * Returns response status message.
	 */
	function getMessage()
	{
		return this.mMessage;
	}
}
// end AeTaskDetailResult()

/*
* Object containing inbox listing result set.
*/
function AeTaskListingResult(aStatusCode, aMessage, aCount, aPageSize, aTotal,
		aPrevIndex, aNextIndex, aDisplayFrom, aDisplayTo, aOrderBys)
{
	this.mTaskList = new AeTaskList();
	this.mStatus = aStatusCode;
	this.mMessage = aMessage;
	this.mCount = aCount;
	this.mPageSize = aPageSize;
	this.mTotal = aTotal;
	this.mPrevIndex = aPrevIndex;
	this.mNextIndex = aNextIndex;
	this.mDisplayFrom = aDisplayFrom;
	this.mDisplayTo = aDisplayTo;
	this.orderBys = aOrderBys; // pipe separated list

	if (typeof(_AeTaskListingResult_prototype_called) == 'undefined')
	{
		_AeTaskListingResult_prototype_called = true;
		AeTaskListingResult.prototype.getTaskList = getTaskList;
		AeTaskListingResult.prototype.getStatus = getStatus;
		AeTaskListingResult.prototype.getMessage = getMessage;
		AeTaskListingResult.prototype.getPreviousIndex = getPreviousIndex;
		AeTaskListingResult.prototype.getNextIndex = getNextIndex;
		AeTaskListingResult.prototype.getFirstIndex = getFirstIndex;
		AeTaskListingResult.prototype.getLastIndex = getLastIndex;
		AeTaskListingResult.prototype.getTotal = getTotal;
		AeTaskListingResult.prototype.getDisplayFrom = getDisplayFrom;
		AeTaskListingResult.prototype.getDisplayTo = getDisplayTo;
		AeTaskListingResult.prototype.getOrderBys = getOrderBys;
		AeTaskListingResult.prototype.getOrderByList = getOrderByList;
		AeTaskListingResult.prototype.createOrderByForFieldID = createOrderByForFieldID;
	}

	/**
	 * Returns total number of tasks.
	 */
	function getTotal()
	{
		return this.mTotal;
	}

	/**
	 * Returns the index number of the first task in the result set. Used for paging.
	 */
	function getDisplayFrom()
	{
		return this.mDisplayFrom;
	}

	/**
	 * Returns the last index number of the task in the result set.
	 */
	function getDisplayTo()
	{
		return this.mDisplayTo;
	}

	/**
	 * The start index to get to the first page.
	 */
	function getFirstIndex()
	{
		return 0;
	}

	/**
	 * The start index to get to the last page.
	 */
	function getLastIndex()
	{
		idx = this.mTotal - this.mPageSize;
		if (idx < 0)
		{
			idx = 0;
		}
		return idx;
	}

	/**
	 * List of AeTaskDetail objects.
	 */
	function getTaskList()
	{
		return this.mTaskList;
	}

	/**
	 * Response status code.
	 */
	function getStatus()
	{
		return this.mStatus;
	}

	/**
	 * Returns response status message.
	 */
	function getMessage()
	{
		return this.mMessage;
	}

	/**
	 * Returns the previous page start index.
	 */
	function getPreviousIndex()
	{
		return this.mPrevIndex
	}

	/**
	 * Returns the next page start index to be used for paging.
	 */
	function getNextIndex()
	{
		return this.mNextIndex
	}
	
	/**
	 * Returns a pipe (|) separated list of order-by fields IDs.
	 */
	function getOrderBys()
	{
		return this.orderBys;
	}
	
	/**
	 * Returns an array  list of order-by fields IDs.
	 */
	function getOrderByList()
	{
		// create array of order by field ids.
		var orderByList = [];
   	$.each( this.getOrderBys().split("|"), function (j, field) {
	   	orderByList.push(field);
   	});
   	return orderByList;
	}
	
	/**
	 * Creates and returns new orderby clause give the new column field id.
	 */
	function createOrderByForFieldID(aFieldID)
	{
		// create array of
		var orderByList = this.getOrderByList();
   	if (orderByList.length == 0)
   	{
   		return aFieldID;
   	}	
   	var descFieldID = "-" + aFieldID;
   	// check first elem. and negated if needed.
   	if (orderByList[0] == aFieldID)
   	{
   		orderByList[0] = descFieldID;
   	}
   	else if (orderByList[0] == descFieldID)
   	{
   		orderByList[0] = aFieldID;
   	}
   	else
   	{
   		orderByList.unshift(aFieldID);   		
   	}
  		if (orderByList.length > 2)
  		{
   		orderByList = orderByList.slice(0, 2);
   	}
   	
   	var rval = "";
   	$.each( orderByList, function (j, field) {
	   	rval = rval + field;
	   	if (j != orderByList.length-1)
	   	{
	   		rval = rval + "|";
	   	}
   	});   	
		return rval;
	}
}
